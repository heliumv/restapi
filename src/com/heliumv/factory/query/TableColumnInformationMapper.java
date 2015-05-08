/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.factory.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.heliumv.annotation.HvFlrMapper;
import com.heliumv.api.item.ItemEntry;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

/**
 * Kann im Zusammenhang mit der Annotation HvFlrMapper flrObjects in
 * saubere Datentypen wandeln.</br>
 * <p>Speziell dann wertvoll, wenn im FLRHandler (UseCaseCandler auf EJB Seite)
 * viele Eigenschaften konfigurierbar sind. Wie beim ArtikellisteHandler.</p>
 * <p>Beispiel hierfür: @see {@link ItemEntry}
 * 
 * @author Gerold
 */
public class TableColumnInformationMapper {
	private Object theInstance ;
	private TableColumnInformation columnInfos ;
	private List<Method> cachedMethods ;
	
	public TableColumnInformationMapper() {
	}

	public TableColumnInformationMapper(TableColumnInformation infos) {
		columnInfos = infos ;
	}
	
	
	public void setTableColumnInformation(TableColumnInformation infos) {
		columnInfos = infos ;
	}
	
	protected TableColumnInformation getColumnInfos() {
		return columnInfos ;
	}
	
	protected List<Method> getMappingMethods(Object target) {
		if(cachedMethods != null) return cachedMethods ;

		cachedMethods = new ArrayList<Method>() ;
		Method[] methods = target.getClass().getMethods() ;
		
		for (Method theMethod : methods) {
			if(!theMethod.getName().startsWith("set")) continue ;
			if(!theMethod.isAnnotationPresent(HvFlrMapper.class)) continue ;
		
			cachedMethods.add(theMethod) ;
		}
		return cachedMethods ;
	}
	
//	public void setValues(Object target, Object[] flrObject) {
//		theInstance = target ; 
//		Method[] methods = target.getClass().getMethods() ;
//		
//		for (Method theMethod : methods) {
//			if(!theMethod.getName().startsWith("set")) continue ;
//			if(!theMethod.isAnnotationPresent(HvFlrMapper.class)) continue ;
//			
//			HvFlrMapper mapper = theMethod.getAnnotation(HvFlrMapper.class) ;
//			if(mapper.flrName().length() > 0) {
//				setOneValue(theMethod, mapper.flrName(), flrObject);
//			} else {
//				for (String flrName : mapper.flrNames()) {
//					setOneValue(theMethod, flrName, flrObject);					
//				}
//			}
//		}
//	}

	public void setValues(Object target, Object[] flrObject) {
		theInstance = target ; 

		for (Method theMethod : getMappingMethods(target)) {
			HvFlrMapper mapper = theMethod.getAnnotation(HvFlrMapper.class) ;
			if(mapper.flrName().length() > 0) {
				setOneValue(theMethod, mapper.flrName(), flrObject);
			} else {
				for (String flrName : mapper.flrNames()) {
					setOneValue(theMethod, flrName, flrObject);					
				}
			}
		}
	}
	
	private void setOneValue(Method theMethod, String flrColumnName, Object[] flrObject) {
		Integer columnIndex = getColumnInfos().getViewIndexObject(flrColumnName) ;
		if(columnIndex != null) {
			try {
				if(flrObject[columnIndex] == null) {
					theMethod.invoke(theInstance, flrObject[columnIndex]) ;					
				} else {
					if(theMethod.getParameterTypes()[0].equals(flrObject[columnIndex].getClass())) {
						theMethod.invoke(theInstance, flrObject[columnIndex]) ;
					}					
				}
			} catch(InvocationTargetException e) {
				System.out.println("InvocationTargetException " + e.getMessage());				
			} catch(IllegalAccessException e) {
				System.out.println("IllegalAccessException " + e.getMessage());
			} catch(ClassCastException e) {
				System.out.println("ClassCastException " + e.getMessage());
			} catch(IllegalArgumentException e) {
				System.out.println("IllegalArgumentException" + e.getMessage());				
			} catch(NullPointerException e) {
				System.out.println("NullPointerException" + e.getMessage());								
			}
		}		
	}
}
