package com.heliumv.api;

import java.util.List;

public abstract class BaseFLRTransformer<T> {
	public BaseFLRTransformer() {
	}
	
	public abstract List<T> transform(Object[][] flrObjects) ;
}
