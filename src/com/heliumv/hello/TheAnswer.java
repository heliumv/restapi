package com.heliumv.hello;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TheAnswer {
	private String answer ;
	private String question ;
	
	public TheAnswer() {
		answer = "42" ;
		question = "for all questions" ;
	}
	
	public TheAnswer(String question, String answer) {
		this.question = question ;
		this.answer = answer ;
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
}
