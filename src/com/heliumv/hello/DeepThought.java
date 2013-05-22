package com.heliumv.hello;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

@Service("deepThought")
@WebService(targetNamespace = "http://hello.heliumv.com/", endpointInterface = "com.heliumv.hello.DeepThoughtSEI", portName = "DeepThoughtPort", serviceName = "DeepThoughtService")
@Path(DeepThought.BASE_PATH)
public class DeepThought implements DeepThoughtSEI {

	public String whatIsTheAnswer(String question) {
		return "The answer for {" + question + "} is 42!" ;
 	}
	
	public String whatIsTheAnswerFor(String question, String interviewer) {
		return "The interviewer {" + interviewer + "} asked for {" + question + "} and got 42!" ;
	}

	@GET
	@Path("/answerEx/{question}/{interviewer}/")
	@Produces({"application/json", "application/xml", "text/plain", "text/xml"})
	@WebMethod
	public TheAnswer whatIsTheAnswerForExtended(
			@PathParam("question") String question,
			@PathParam("interviewer") String interviewer) {
		return new TheAnswer(question, interviewer) ;
	}
}
