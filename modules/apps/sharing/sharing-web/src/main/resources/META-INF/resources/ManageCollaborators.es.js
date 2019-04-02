import 'clay-button';
import 'clay-select';
import 'clay-sticker';
import {ClayStripe} from 'clay-alert';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import templates from './ManageCollaborators.soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * Handles actions to delete or change permissions of the
 * collaborators for a file entry.
 */
class ManageCollaborators extends PortletBase {

	/**
	 * @inheritDoc
	 */
	attached() {
		this._deleteSharingEntryIds = [];
		this._sharingEntryIdsAndPermissions = new Map();
		this._sharingEntryIdsAndExpirationDate = new Map();
		this._sharingEntryIdsAndShareables = new Map();

		let tomorrow = new Date();
		tomorrow = tomorrow.setDate(tomorrow.getDate() + 1);

		this._tomorrowDate = new Date(tomorrow).toISOString().split('T')[0];
	}

	/**
	 * Checks if the date is after today.
	 * @param  {String} expirationDate
	 * @protected
	 *
	 * @return {Bool} returns true if the expiration date
	 * is after today, false in other case.
	 */
	_checkExpirationDate(expirationDate) {
		const date = new Date(expirationDate);
		return date >= new Date(this._tomorrowDate);
	}

	/**
	 * Closes the dialog.
	 * @protected
	 */
	_closeDialog() {
		const collaboratorsDialog = Liferay.Util.getWindow(this._dialogId);

		if (collaboratorsDialog && collaboratorsDialog.hide) {
			collaboratorsDialog.hide();
		}
	}

	/**
	 * Looks if there is a collaborator with an invalid
	 * expiration date.
	 *
	 * @return {Boolean} If a collaborator has an invalid expiration date
	 */
	_findExpirationDateError() {
		let collaborator = this.collaborators.find(
			collaborator => collaborator.sharingEntryExpirationDateError === true
		);

		this.expirationDateError = collaborator != null;

		return this.expirationDateError;
	}

	/**
	 * Finds a collaborator by his id
	 *
	 * @param  {String} collaboratorId The id of a collaborator
	 * @return {Object} Collaborator
	 */
	_getCollaborator(collaboratorId) {
		let collaborator = this.collaborators.find(
			collaborator => collaborator.id === collaboratorId
		);

		return collaborator;
	}

	/**
	 * Closes the dialog.
	 * @protected
	 */
	_handleCancelButtonClick() {
		this._closeDialog();
	}

	/**
	 * Gets the new permission key for the selected
	 * collaborator.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleChangePermission(event) {
		let sharingEntryId = event.target.getAttribute('name');
		let sharingEntryPermissionKey = event.target.value;

		this._sharingEntryIdsAndPermissions.set(sharingEntryId, sharingEntryPermissionKey);
	}

	/**
	 * Gets the selected expiration date.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleBlurExpirationDate(event) {
		let collaboratorId = event.target.dataset.collaboratorId;
		let sharingEntryExpirationDate = event.target.value;
		let sharingEntryId = event.target.dataset.sharingentryId;

		let dateError = !this._checkExpirationDate(sharingEntryExpirationDate);

		if (!dateError) {
			this._sharingEntryIdsAndExpirationDate.set(sharingEntryId, sharingEntryExpirationDate);
		}

		let collaborator = this._getCollaborator(collaboratorId);
		collaborator.sharingEntryExpirationDateError = dateError;

		this.collaborators = this.collaborators;

		setTimeout(
			() => this._findExpirationDateError(),
			dateError ? 250 : 0
		);
	}

	/**
	 * Get shareable permissions
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleChangeShareable(event) {
		const target = event.delegateTarget;

		const collaboratorId = target.dataset.collaboratorId;
		const shareable = target.checked;
		const sharingEntryId = target.dataset.sharingentryId;

		let collaborator = this._getCollaborator(collaboratorId);

		if (collaborator) {
			collaborator.shareable = shareable;

			this.collaborators = this.collaborators;
		}

		this._sharingEntryIdsAndShareables.set(sharingEntryId, shareable);
	}

	/**
	 * Deletes the collaborator.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handleDeleteCollaborator(event) {
		const target = event.delegateTarget;

		const collaboratorId = target.dataset.collaboratorId;
		const sharingEntryId = target.dataset.sharingentryId;

		event.stopPropagation();

		this.collaborators = this.collaborators.filter(
			collaborator => collaborator.id != collaboratorId
		);

		this._deleteSharingEntryIds.push(sharingEntryId);
	}

	/**
	 * Enable and disable the expiration date field
	 * @param  {Event} event
	 * @protected
	 */
	_handleEnableExpirationDate(event) {
		const target = event.delegateTarget;

		const collaboratorId = target.dataset.collaboratorId;
		const enabled = target.checked;

		let collaborator = this._getCollaborator(collaboratorId);

		if (collaborator) {
			collaborator.enabledExpirationDate = enabled;

			if (enabled) {
				collaborator.sharingEntryExpirationDate = this._tomorrowDate;
			}
			else {
				collaborator.sharingEntryExpirationDate = '';
				collaborator.sharingEntryExpirationDateError = false;
			}

			this.collaborators = this.collaborators;
		}
	}

	/**
	 * Sends a request to the server to update permissions
	 * or delete collaborators.
	 *
	 * @protected
	 */
	_handleSaveButtonClick() {
		const expirationDates = Array.from(this._sharingEntryIdsAndExpirationDate, pair => pair.join(','));
		const permissions = Array.from(this._sharingEntryIdsAndPermissions, pair => pair.join(','));
		const shareables = Array.from(this._sharingEntryIdsAndShareables, pair => pair.join(','));

		if (this._findExpirationDateError()) {
			return;
		}

		this.fetch(
			this.actionUrl,
			{
				deleteSharingEntryIds: this._deleteSharingEntryIds,
				sharingEntryIdActionIdPairs: permissions,
				sharingEntryIdExpirationDatePairs: expirationDates,
				sharingEntryIdsAndShareables: shareables
			}
		)
			.then(
				response => {
					this.submitting = false;

					const jsonResponse = response.json();

					return response.ok ?
						jsonResponse :
						jsonResponse.then(
							json => {
								const error = new Error(json.errorMessage || response.statusText);
								throw Object.assign(error, {response});
							}
						)
					;
				}
			)
			.then(
				json => {
					this._loadingResponse = false;
					this._showNotification(json.successMessage);
				}
			)
			.catch(
				error => {
					this._loadingResponse = false;
					this._showNotification(error.message, true);
				}
			);

		this._loadingResponse = true;
	}

	/**
	 * Expand configuration for sharing permissions and expiration
	 *
	 * @param {Event} event
	 * @protected
	 */
	_hideShowExtraActions(event) {
		if (!event.target.matches('select,button')) {
			this.expandedCollaboratorId = event.delegateTarget.dataset.collaboratorid;
		}
	}

	/**
	 * Get the formatted date that has to be shown
	 * in the tooltip.
	 *
	 * @param  {Date} expirationDate [description]
	 * @return {String}                [description]
	 */
	_getTooltipDate(expirationDate) {
		return new Date(expirationDate).toLocaleDateString(Liferay.ThemeDisplay.getBCP47LanguageId());
	}

	/**
	 * Cleans the error.
	 * @protected
	 */
	_removeExpirationDateError() {
		this.expirationDateError = false;
	}

	/**
	 * Show notification in the opener and closes dialog
	 * after is rendered
	 * @param {string} message message for notification
	 * @param {boolean} error Flag indicating if is an error or not
	 * @private
	 * @review
	 */
	_showNotification(message, error) {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {
			events: {
				'attached': this._closeDialog.bind(this)
			},
			message
		};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		parentOpenToast(openToastParams);
	}
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
ManageCollaborators.STATE = {

	/**
	 * Uri to send the manage collaborators fetch request.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	actionUrl: Config.string().required(),

	/**
	 * List of collaborators
	 * @type {Array.<Object>}
	 */
	collaborators: Config.array().required(),

	/**
	 * Id of the expanded collaborator
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	expandedCollaboratorId: Config.string(),

	/**
	 * Id of the dialog
	 * @type {String}
	 */
	dialogId: Config.string().required,

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */
	spritemap: Config.string().required()
};

// Register component

Soy.register(ManageCollaborators, templates);

export default ManageCollaborators;