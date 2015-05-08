package com.heliumv.api.internal;

public class JenkinsChangesParser {
	private enum Stage {
		CHECK_CHANGES,
		EXTRACT_MESSAGE,
		ERROR,
		DONE
	}

	private String source ;
	private Stage stage ;
	private String commitMessage ;
	
	public JenkinsChangesParser(String allCommitMessages) {
		this.source = allCommitMessages ;
		stage = Stage.CHECK_CHANGES ;
	}
	
	public boolean parse() {
		if(stage == Stage.CHECK_CHANGES) {
			source = source.replaceAll("</changes>\n", "</changes>") ;
			if(source.startsWith("<changes>") && source.endsWith("</changes>")) {
				source = source.replaceFirst("<changes>", "") ;
				source = source.replaceFirst("</changes>", "").trim() ;
				stage = Stage.EXTRACT_MESSAGE ;
			} else {
				stage = Stage.DONE ;
			}
		}
		
		if(stage == Stage.EXTRACT_MESSAGE) {
			int msgStartIndex = source.indexOf("<msg>") ;
			if(msgStartIndex == -1) {
				stage = Stage.DONE ;
				commitMessage = null ;
				return false ;
			}
			
			int msgEndIndex = source.indexOf("</msg>", msgStartIndex + 5) ;
			if(msgEndIndex == -1) {
				stage = Stage.ERROR ;
				commitMessage = null ;
				return false ;
			}

			commitMessage = source.substring(msgStartIndex + 5, msgEndIndex) ;
			
			source = source.substring(msgEndIndex + 6).trim() ;
			return true ;
		}

		if(stage == Stage.DONE) {
			commitMessage = null ;
			return false ;
		}

		if(stage == Stage.ERROR) {
			commitMessage = null ;
			return false ;
		}
		
		return false ;
	}
	
	public String getCommitMessage() {
		return commitMessage ;
	}
	
	public boolean hasErrors() {
		return Stage.ERROR == stage ;
	}
}
