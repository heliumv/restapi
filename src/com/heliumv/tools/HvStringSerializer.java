package com.heliumv.tools;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Strings immer als Strings - also mit "value" ausgeben.</br>
 * Default Jackson Converter macht daraus manchmal Integer/BigDecimal
 * @author Gerold
 */
public class HvStringSerializer extends JsonSerializer<String> {
	@Override
	public void serialize(String value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeString(value) ;
	}

	@Override
	public Class<String> handledType() {
		return String.class ;
	}
}