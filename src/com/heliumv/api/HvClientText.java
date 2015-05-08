package com.heliumv.api;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.IGlobalInfo;

public class HvClientText {
	private static final String RESOURCE_BUNDEL_ALLG = "com.heliumv.res.messages";
	
	@Autowired
	private IGlobalInfo globalInfo ;

	private ResourceBundle messagesBundle ;
	
	private ResourceBundle getMessagesBundle() {
		if(messagesBundle == null) {
			messagesBundle = ResourceBundle.getBundle(
					RESOURCE_BUNDEL_ALLG, globalInfo.getTheClientDto().getLocUi()) ;
		}
		return messagesBundle ;
	}
	
	/**
	 * Einen Text zum token holen.
	 * 
	 * @param token
	 * @return der Text
	 */
	public String get(String token) {
		return  getMessagesBundle().containsKey(token)
				? getMessagesBundle().getString(token)
				: token ;
	}
	
	/**
	 * Einen Text mit Parametern formatieren. Beispiel: Im Token ist hinterlegt:
	 * "Sie haben {0} St&uuml;ck erhalten"
	 * 
	 * @param token
	 *            ist der Name des zu verwendenden Tokens. Beispiel:
	 *            fb.konten.result
	 * @param values
	 *            nimmt die einzusetzenden Werte auf
	 * @return den formatierten String
	 */
	public String getFormatted(String token, Object... values) {
		String msg = get(token);
		return MessageFormat.format(msg, values);
	}	
}
