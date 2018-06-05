package com.zhangqin.framework.common.enums;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.zhangqin.framework.common.utils.EnumUtils;

/**
 * 枚举反序列化
 * @author zhangqin
 *
 */
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum<?, ?>> implements ContextualDeserializer {
	/**
	 * 枚举class
	 */
	private Class<?> javaClass;

	@SuppressWarnings("unchecked")
	@Override
	public BaseEnum<?, ?> deserialize(JsonParser parse, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec codec = parse.getCodec();
		JsonNode tree = codec.readTree(parse);
		if(tree.has("value")) {
			tree = tree.get("value");
		}
		Class<BaseEnum<?, ?>> clazz = (Class<BaseEnum<?, ?>>)getJavaClass();
		return EnumUtils.getEnumObj(clazz, tree.textValue());
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
			throws JsonMappingException {
		System.out.println(ctxt.getContextualType());
		Class<?> targetClass = ctxt.getContextualType().getRawClass();
		this.setJavaClass(targetClass);
		return this;
	}

	public Class<?> getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(Class<?> javaClass) {
		this.javaClass = javaClass;
	}

}
