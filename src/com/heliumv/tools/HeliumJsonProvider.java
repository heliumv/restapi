package com.heliumv.tools;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class HeliumJsonProvider extends JacksonJaxbJsonProvider {
	public HeliumJsonProvider() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
        mapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
        mapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false) ;

        SimpleModule testModule = new SimpleModule("MyModule", new Version(1, 0, 0, null))  ;
        testModule.addSerializer(String.class, new HvStringSerializer()) ;
        mapper.registerModule(testModule) ;
        _mapperConfig.setMapper(mapper);
        _mapperConfig.getConfiguredMapper().setAnnotationIntrospector(new JaxbAnnotationIntrospector());	
	}
}

