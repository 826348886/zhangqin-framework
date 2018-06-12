package com.zhangqin.framework.web.common.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.zhangqin.framework.gpe.entity.SearchRuleMappingList;
import com.zhangqin.framework.web.common.editors.BaseCustomDateEditor;
import com.zhangqin.framework.web.common.editors.RuleMappingEditor;

public abstract class BaseGeneralController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(SearchRuleMappingList.class, new RuleMappingEditor());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new BaseCustomDateEditor(
                dateFormat, false));
        
        //String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
 		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
 			@Override
 			public void setAsText(String text) {
 				setValue(text == null ? null : 
 					StringEscapeUtils.escapeHtml4(text.trim()));
 			}
 			@Override
 			public String getAsText() {
 				Object value = getValue();
 				return value != null ? value.toString() : "";
 			}
 		});
    }
}
