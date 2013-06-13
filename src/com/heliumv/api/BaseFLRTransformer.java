package com.heliumv.api;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFLRTransformer<T> {
	public BaseFLRTransformer() {
	}
	
	/**
	 * Wandelt alle FLR Objekte in typisierte Objekte um.</br>
	 * Dabei wird transformOne(Object[] o) verwendet um ein FLR Objekt
	 * zu transformieren.
	 * 
	 * @param flrObjects
	 * @return eine (leere) Liste von transformierten FLR Objekten
	 */
	public List<T> transform(Object[][] flrObjects) {
		ArrayList<T> entities = new ArrayList<T>() ;
		if(flrObjects == null || flrObjects.length == 0) return entities ;

		for(Object[] objects : flrObjects) {
			entities.add (transformOne(objects)) ;
		}

		return entities ;
	}
	
	public abstract T transformOne(Object[] flrObject) ;
}
