/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
 * This program is free software; you can redistribute it and/or modify it           *
 * under the terms version 2 of the GNU General Public License as published          *
 * by the Free Software Foundation. This program is distributed in the hope          *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied        *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                  *
 * See the GNU General Public License for more details.                              *
 * You should have received a copy of the GNU General Public License along           *
 * with this program; if not, write to the Free Software Foundation, Inc.,           *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                            *
 * For the text or an alternative of this public license, you may reach us           *
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
package org.spinsuite.conn;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Transport
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class Transport extends HttpTransportSE{

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:56:36
	 * @param proxy
	 * @param url
	 * @param timeout
	 */
	public Transport(Proxy proxy, String url, int timeout) {
		super(proxy, url, timeout);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:56:54
	 * @param proxy
	 * @param url
	 */
	public Transport(Proxy proxy, String url) {
		super(proxy, url);
	}
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:57:06
	 * @param url
	 */
	public Transport( String url) {
		super(url);
	}
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:57:24
	 * @param url
	 * @param timeout
	 */
	public Transport( String url,int timeout) {
		super(url,timeout);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List call(String arg0, SoapEnvelope arg1, List arg2)
			throws IOException, XmlPullParserException {
		return super.call(arg0, arg1, arg2);
	}
	
	@Override
	public void call(String soapAction, SoapEnvelope envelope)
			throws IOException, XmlPullParserException {
		super.call(soapAction, envelope);
	}
}
