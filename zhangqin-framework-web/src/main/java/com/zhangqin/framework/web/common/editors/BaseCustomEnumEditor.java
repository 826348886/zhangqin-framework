package com.zhangqin.framework.web.common.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.utils.EnumUtils;

/**
 * 字符串转换为com.zhangqin.framewok.common.enums.BaseEnum
 * @author zhangqin
 *
 */
public class BaseCustomEnumEditor<T> extends PropertyEditorSupport  {
	
	
	private final Class<? extends BaseEnum<Enum<?>, T>> clazz;
	private final boolean allowEmpty;
	
	/**
	 * Create a new CustomBooleanEditor instance, with "true"/"on"/"yes"
	 * and "false"/"off"/"no" as recognized String values.
	 * <p>The "allowEmpty" parameter states if an empty String should
	 * be allowed for parsing, i.e. get interpreted as null value.
	 * Else, an IllegalArgumentException gets thrown in that case.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public BaseCustomEnumEditor(Class<? extends BaseEnum<Enum<?>, T>> clazz,boolean allowEmpty) {
		this.clazz = clazz;
		this.allowEmpty = allowEmpty;
	}


	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String input = (text != null ? text.trim() : null);
		if (this.allowEmpty && !StringUtils.hasLength(input)) {
			// Treat empty String as null value.
			setValue(null);
		}else {
			BaseEnum<? extends Enum<?>, ?> en = EnumUtils.getEnumObj(clazz, input);
			setValue(en);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		BaseEnum<Enum<?>, T> value = (BaseEnum<Enum<?>, T>)getValue();
		return value.getValue().toString();
	}
}
