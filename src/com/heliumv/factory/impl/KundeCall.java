package com.heliumv.factory.impl;

import java.sql.Date;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IKundeCall;
import com.lp.client.frame.delegate.DelegateFactory;
import com.lp.server.partner.service.KundeFac;

public class KundeCall extends BaseCall<KundeFac> implements IKundeCall  {
	public KundeCall() {
		super(KundeFacBean) ;
	}
}
