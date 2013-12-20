package com.heliumv.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.heliumv.api.system.LocalPingResult;
import com.heliumv.api.system.PingResult;

public class HvSimpleStringMessageBodyProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object>{
	
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return MediaType.TEXT_HTML_TYPE.getType ().equals ( mediaType.getType () )
			    && MediaType.TEXT_HTML_TYPE.getSubtype ().equals ( mediaType.getSubtype () )
			    && isAcceptableType(genericType);
	}

	private boolean isAcceptableType(Type genericType) {
		return genericType.equals(PingResult.class) ||
				genericType.equals(LocalPingResult.class) ;
	}
	
	@Override
	public long getSize ( Object t, Class<?> type, Type genericType, 
	    Annotation[] annotations, MediaType mediaType ) {
	    // I'm being lazy - should compute the actual size
	    return -1;
	}
	
	@Override
	public void writeTo ( Object t, Class<?> type, Type genericType, 
	    Annotation[] annotations, MediaType mediaType, 
	    MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream ) throws IOException, WebApplicationException {
		String s = t.toString() ;
		entityStream.write(s.getBytes());
	}
	
	@Override
	public boolean isReadable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3) {
		return false;
	}
	
	@Override
	public Object readFrom(Class<Object> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3, MultivaluedMap<String, String> arg4,
			InputStream arg5) throws IOException, WebApplicationException {
		return null;
	}
}
