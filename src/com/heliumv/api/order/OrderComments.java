package com.heliumv.api.order;

import com.heliumv.api.BaseEntryId;

public class OrderComments extends BaseEntryId {
	private String internalComment ;
	private String externalComment ;

	public String getInternalComment() {
		return internalComment;
	}
	public void setInternalComment(String internalComment) {
		this.internalComment = internalComment;
	}
	public String getExternalComment() {
		return externalComment;
	}
	public void setExternalComment(String externalComment) {
		this.externalComment = externalComment;
	}
}
