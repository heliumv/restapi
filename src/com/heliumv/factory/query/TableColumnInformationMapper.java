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
				theMethod.invoke(theInstance, flrObject[columnIndex]) ;
				return ;
			} catch(InvocationTargetException e) {
				
			} catch(IllegalAccessException e) {
			} catch(ClassCastException e) {
				System.out.println();
			} catch(IllegalArgumentException e) {
				System.out.println();				
			}
		}		
	}
}
