/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.announcements.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.announcements.kernel.model.AnnouncementsDeliveryModel;
import com.liferay.announcements.kernel.model.AnnouncementsDeliverySoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the AnnouncementsDelivery service. Represents a row in the &quot;AnnouncementsDelivery&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link AnnouncementsDeliveryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AnnouncementsDeliveryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryImpl
 * @see AnnouncementsDelivery
 * @see AnnouncementsDeliveryModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class AnnouncementsDeliveryModelImpl extends BaseModelImpl<AnnouncementsDelivery>
	implements AnnouncementsDeliveryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a announcements delivery model instance should use the {@link AnnouncementsDelivery} interface instead.
	 */
	public static final String TABLE_NAME = "AnnouncementsDelivery";
	public static final Object[][] TABLE_COLUMNS = {
			{ "deliveryId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "type_", Types.VARCHAR },
			{ "email", Types.BOOLEAN },
			{ "sms", Types.BOOLEAN },
			{ "website", Types.BOOLEAN }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("deliveryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("email", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("sms", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("website", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE = "create table AnnouncementsDelivery (deliveryId LONG not null primary key,companyId LONG,userId LONG,type_ VARCHAR(75) null,email BOOLEAN,sms BOOLEAN,website BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table AnnouncementsDelivery";
	public static final String ORDER_BY_JPQL = " ORDER BY announcementsDelivery.deliveryId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY AnnouncementsDelivery.deliveryId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.announcements.kernel.model.AnnouncementsDelivery"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.announcements.kernel.model.AnnouncementsDelivery"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.announcements.kernel.model.AnnouncementsDelivery"),
			true);
	public static final long TYPE_COLUMN_BITMASK = 1L;
	public static final long USERID_COLUMN_BITMASK = 2L;
	public static final long DELIVERYID_COLUMN_BITMASK = 4L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static AnnouncementsDelivery toModel(
		AnnouncementsDeliverySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		AnnouncementsDelivery model = new AnnouncementsDeliveryImpl();

		model.setDeliveryId(soapModel.getDeliveryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setType(soapModel.getType());
		model.setEmail(soapModel.isEmail());
		model.setSms(soapModel.isSms());
		model.setWebsite(soapModel.isWebsite());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<AnnouncementsDelivery> toModels(
		AnnouncementsDeliverySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<AnnouncementsDelivery> models = new ArrayList<AnnouncementsDelivery>(soapModels.length);

		for (AnnouncementsDeliverySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.announcements.kernel.model.AnnouncementsDelivery"));

	public AnnouncementsDeliveryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _deliveryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setDeliveryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _deliveryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AnnouncementsDelivery.class;
	}

	@Override
	public String getModelClassName() {
		return AnnouncementsDelivery.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AnnouncementsDelivery, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AnnouncementsDelivery, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<AnnouncementsDelivery, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName,
				attributeGetterFunction.apply((AnnouncementsDelivery)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AnnouncementsDelivery, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AnnouncementsDelivery, Object> attributeSetterBiConsumer = attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((AnnouncementsDelivery)this,
					entry.getValue());
			}
		}
	}

	public Map<String, Function<AnnouncementsDelivery, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AnnouncementsDelivery, Object>> getAttributeSetterBiConsumers() {
		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<AnnouncementsDelivery, Object>> _attributeGetterFunctions;
	private static final Map<String, BiConsumer<AnnouncementsDelivery, Object>> _attributeSetterBiConsumers;

	static {
		Map<String, Function<AnnouncementsDelivery, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<AnnouncementsDelivery, Object>>();
		Map<String, BiConsumer<AnnouncementsDelivery, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<AnnouncementsDelivery, ?>>();


		attributeGetterFunctions.put(
			"deliveryId",
			new Function<AnnouncementsDelivery, Object>() {

				@Override
				public Object apply(AnnouncementsDelivery announcementsDelivery) {
					return announcementsDelivery.getDeliveryId();
				}

			});
		attributeSetterBiConsumers.put(
			"deliveryId",
			new BiConsumer<AnnouncementsDelivery, Object>() {

				@Override
				public void accept(AnnouncementsDelivery announcementsDelivery, Object deliveryId) {
					announcementsDelivery.setDeliveryId((Long)deliveryId);
				}

			});
		attributeGetterFunctions.put(
			"companyId",
			new Function<AnnouncementsDelivery, Object>() {

				@Override
				public Object apply(AnnouncementsDelivery announcementsDelivery) {
					return announcementsDelivery.getCompanyId();
				}

			});
		attributeSetterBiConsumers.put(
			"companyId",
			new BiConsumer<AnnouncementsDelivery, Object>() {

				@Override
				public void accept(AnnouncementsDelivery announcementsDelivery, Object companyId) {
					announcementsDelivery.setCompanyId((Long)companyId);
				}

			});
		attributeGetterFunctions.put(
			"userId",
			new Function<AnnouncementsDelivery, Object>() {

				@Override
				public Object apply(AnnouncementsDelivery announcementsDelivery) {
					return announcementsDelivery.getUserId();
				}

			});
		attributeSetterBiConsumers.put(
			"userId",
			new BiConsumer<AnnouncementsDelivery, Object>() {

				@Override
				public void accept(AnnouncementsDelivery announcementsDelivery, Object userId) {
					announcementsDelivery.setUserId((Long)userId);
				}

			});
		attributeGetterFunctions.put(
			"type",
			new Function<AnnouncementsDelivery, Object>() {

				@Override
				public Object apply(AnnouncementsDelivery announcementsDelivery) {
					return announcementsDelivery.getType();
				}

			});
		attributeSetterBiConsumers.put(
			"type",
			new BiConsumer<AnnouncementsDelivery, Object>() {

				@Override
				public void accept(AnnouncementsDelivery announcementsDelivery, Object type) {
					announcementsDelivery.setType((String)type);
				}

			});
		attributeGetterFunctions.put(
			"email",
			new Function<AnnouncementsDelivery, Object>() {

				@Override
				public Object apply(AnnouncementsDelivery announcementsDelivery) {
					return announcementsDelivery.getEmail();
				}

			});
		attributeSetterBiConsumers.put(
			"email",
			new BiConsumer<AnnouncementsDelivery, Object>() {

				@Override
				public void accept(AnnouncementsDelivery announcementsDelivery, Object email) {
					announcementsDelivery.setEmail((Boolean)email);
				}

			});
		attributeGetterFunctions.put(
			"sms",
			new Function<AnnouncementsDelivery, Object>() {

				@Override
				public Object apply(AnnouncementsDelivery announcementsDelivery) {
					return announcementsDelivery.getSms();
				}

			});
		attributeSetterBiConsumers.put(
			"sms",
			new BiConsumer<AnnouncementsDelivery, Object>() {

				@Override
				public void accept(AnnouncementsDelivery announcementsDelivery, Object sms) {
					announcementsDelivery.setSms((Boolean)sms);
				}

			});
		attributeGetterFunctions.put(
			"website",
			new Function<AnnouncementsDelivery, Object>() {

				@Override
				public Object apply(AnnouncementsDelivery announcementsDelivery) {
					return announcementsDelivery.getWebsite();
				}

			});
		attributeSetterBiConsumers.put(
			"website",
			new BiConsumer<AnnouncementsDelivery, Object>() {

				@Override
				public void accept(AnnouncementsDelivery announcementsDelivery, Object website) {
					announcementsDelivery.setWebsite((Boolean)website);
				}

			});


		_attributeGetterFunctions = Collections.unmodifiableMap(attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap((Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getDeliveryId() {
		return _deliveryId;
	}

	@Override
	public void setDeliveryId(long deliveryId) {
		_deliveryId = deliveryId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@JSON
	@Override
	public String getType() {
		if (_type == null) {
			return "";
		}
		else {
			return _type;
		}
	}

	@Override
	public void setType(String type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (_originalType == null) {
			_originalType = _type;
		}

		_type = type;
	}

	public String getOriginalType() {
		return GetterUtil.getString(_originalType);
	}

	@JSON
	@Override
	public boolean getEmail() {
		return _email;
	}

	@JSON
	@Override
	public boolean isEmail() {
		return _email;
	}

	@Override
	public void setEmail(boolean email) {
		_email = email;
	}

	@JSON
	@Override
	public boolean getSms() {
		return _sms;
	}

	@JSON
	@Override
	public boolean isSms() {
		return _sms;
	}

	@Override
	public void setSms(boolean sms) {
		_sms = sms;
	}

	@JSON
	@Override
	public boolean getWebsite() {
		return _website;
	}

	@JSON
	@Override
	public boolean isWebsite() {
		return _website;
	}

	@Override
	public void setWebsite(boolean website) {
		_website = website;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			AnnouncementsDelivery.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AnnouncementsDelivery toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (AnnouncementsDelivery)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		AnnouncementsDeliveryImpl announcementsDeliveryImpl = new AnnouncementsDeliveryImpl();

		announcementsDeliveryImpl.setDeliveryId(getDeliveryId());
		announcementsDeliveryImpl.setCompanyId(getCompanyId());
		announcementsDeliveryImpl.setUserId(getUserId());
		announcementsDeliveryImpl.setType(getType());
		announcementsDeliveryImpl.setEmail(isEmail());
		announcementsDeliveryImpl.setSms(isSms());
		announcementsDeliveryImpl.setWebsite(isWebsite());

		announcementsDeliveryImpl.resetOriginalValues();

		return announcementsDeliveryImpl;
	}

	@Override
	public int compareTo(AnnouncementsDelivery announcementsDelivery) {
		long primaryKey = announcementsDelivery.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AnnouncementsDelivery)) {
			return false;
		}

		AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)obj;

		long primaryKey = announcementsDelivery.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl = this;

		announcementsDeliveryModelImpl._originalUserId = announcementsDeliveryModelImpl._userId;

		announcementsDeliveryModelImpl._setOriginalUserId = false;

		announcementsDeliveryModelImpl._originalType = announcementsDeliveryModelImpl._type;

		announcementsDeliveryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AnnouncementsDelivery> toCacheModel() {
		AnnouncementsDeliveryCacheModel announcementsDeliveryCacheModel = new AnnouncementsDeliveryCacheModel();

		announcementsDeliveryCacheModel.deliveryId = getDeliveryId();

		announcementsDeliveryCacheModel.companyId = getCompanyId();

		announcementsDeliveryCacheModel.userId = getUserId();

		announcementsDeliveryCacheModel.type = getType();

		String type = announcementsDeliveryCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			announcementsDeliveryCacheModel.type = null;
		}

		announcementsDeliveryCacheModel.email = isEmail();

		announcementsDeliveryCacheModel.sms = isSms();

		announcementsDeliveryCacheModel.website = isWebsite();

		return announcementsDeliveryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AnnouncementsDelivery, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((4 * attributeGetterFunctions.size()) +
				2);

		sb.append("{");

		for (Map.Entry<String, Function<AnnouncementsDelivery, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<AnnouncementsDelivery, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((AnnouncementsDelivery)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<AnnouncementsDelivery, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((5 * attributeGetterFunctions.size()) +
				4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AnnouncementsDelivery, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<AnnouncementsDelivery, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((AnnouncementsDelivery)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = AnnouncementsDelivery.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			AnnouncementsDelivery.class, ModelWrapper.class
		};
	private long _deliveryId;
	private long _companyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _type;
	private String _originalType;
	private boolean _email;
	private boolean _sms;
	private boolean _website;
	private long _columnBitmask;
	private AnnouncementsDelivery _escapedModel;
}