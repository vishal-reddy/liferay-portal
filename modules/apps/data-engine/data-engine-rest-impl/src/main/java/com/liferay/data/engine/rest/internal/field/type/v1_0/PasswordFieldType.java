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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.rest.internal.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jeyvison Nascimento
 */
public class PasswordFieldType extends BaseFieldType {

	public PasswordFieldType(
		DataDefinitionField dataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SoyDataFactory soyDataFactory) {

		super(
			dataDefinitionField, httpServletRequest, httpServletResponse,
			soyDataFactory);
	}

	@Override
	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		DataDefinitionField dataDefinitionField = super.deserialize(jsonObject);

		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(), "placeholder",
				LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("placeholder"))));
		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(), "tooltip",
				LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("tooltip"))));

		return dataDefinitionField;
	}

	@Override
	public JSONObject toJSONObject() throws Exception {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put(
			"placeholder",
			LocalizedValueUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "placeholder"))
		).put(
			"tooltip",
			LocalizedValueUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "tooltip"))
		);
	}

	@Override
	protected void addContext(Map<String, Object> context) {
		context.put(
			"placeholder",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "placeholder")));
		context.put(
			"tooltip",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "tooltip")));
	}

}