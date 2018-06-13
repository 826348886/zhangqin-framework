package com.zhangqin.framework.web.common.editors;

import java.beans.PropertyEditorSupport;

import com.zhangqin.framework.common.utils.JsonMapper;
import com.zhangqin.framework.gpe.entity.NbsRuleMappingList;

public class RuleMappingEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String input = (text != null ? text.trim() : null);
//		if (this.allowEmpty && !StringUtils.hasLength(input)) {
//			// Treat empty String as null value.
//			setValue(null);
//		}else {
//			BaseEnum<? extends Enum<?>, ?> en = EnumUtils.getEnumObj(clazz, input);
//			setValue(en);
//		}
//		
		NbsRuleMappingList list = JsonMapper.fromJson(text, NbsRuleMappingList.class);
		setValue(list);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		NbsRuleMappingList list = (NbsRuleMappingList)getValue();
		return JsonMapper.toJson(list);
	}
}
