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

package com.liferay.portal.instances.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Provides the local service interface for PortalInstances. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Michael C. Han
 * @see PortalInstancesLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface PortalInstancesLocalService extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortalInstancesLocalServiceUtil} to access the portal instances local service. Add custom service methods to <code>com.liferay.portal.instances.service.impl.PortalInstancesLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addCompanyId(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getCompanyId(HttpServletRequest httpServletRequest);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCompanyIds();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCompanyIdsBySQL() throws SQLException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getDefaultCompanyId();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String[] getWebIds();

	public void initializePortalInstance(
		ServletContext servletContext, String webId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isAutoLoginIgnoreHost(String host);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isAutoLoginIgnorePath(String path);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isCompanyActive(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isVirtualHostsIgnoreHost(String host);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isVirtualHostsIgnorePath(String path);

	public void reload(ServletContext servletContext);

	public void removeCompany(long companyId);

	@Clusterable
	public void synchronizePortalInstances();

}