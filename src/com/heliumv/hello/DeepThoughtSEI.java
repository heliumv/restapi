package com.heliumv.hello;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@WebService(name = "DeepThoughtSEI", targetNamespace = "http://hello.heliumv.com/")
public interface DeepThoughtSEI {
	/**
	 * Rootpath zum Itemservice
	 */
	public static final String BASE_PATH = "/api/v1" ;
	
	@GET
	@Path("/answer/{question}/")
	@Produces("text/plain") 
	@WebMethod
	public String whatIsTheAnswer(
			@PathParam("question") String question);
	
	@GET
	@Path("/answer/{question}/{interviewer}/")
	@Produces("text/plain") 
	@WebMethod
	public String whatIsTheAnswerFor(
			 @PathParam("question") String question,
			 @PathParam("interviewer") String interviewer) ;

	
	public TheAnswer whatIsTheAnswerForExtended(
			 String question,
			 String interviewer) ;
}