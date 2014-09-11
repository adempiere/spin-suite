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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 
 * @description Set A Connection For Soap Protocol
 */
public class CommunicationSoap extends SoapObject {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:29:41
	 * @param p_Url
	 * @param p_NameSpace
	 * @param p_Method_Name
	 * @param isNetService
	 */
	public CommunicationSoap(String p_Url,String p_NameSpace, String p_Method_Name,boolean isNetService) {
		// TODO Auto-generated constructor stub
		super(p_NameSpace, p_Method_Name);
		setM_SoapMethodName(p_Method_Name);
		setM_NameSpace(p_NameSpace);
		setM_isNetService(isNetService);
		setM_SoapAction(p_NameSpace+p_Method_Name);
		setM_Url(p_Url);
	}
	
	/**
	 * Init Envelope
	 * @author <a href="carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:29:19
	 * @return void
	 */
	public void init_envelope(){
		//Create Envelope
		m_Envelope = new Envelope(getM_Soap_Version());
		//Setting Service Type
		m_Envelope.setDotNet(isM_isNetService());
		//Soap Envolving
		m_Envelope.setOutputSoapObject(this);
	}
	
	/**
	 * Serialization Soap Object And Transport Method Created 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:30:21
	 * @return void
	 */
	public void initTransport(){
		m_Transport = new Transport(m_Url);
	}
	
	/**
	 * Serialization Soap Object And Transport Method Created 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:30:21
	 * @return void
	 */
	public void initTransport(int timeout){
		m_Transport = new Transport(m_Url,timeout);
	}
	
	/**
	 * Call Soap Service
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:33:34
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @return void
	 */
	public void call() throws IOException, XmlPullParserException{
		m_Transport.call(getM_SoapAction(), m_Envelope);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	
	/** Set and gets*/
	
	/**
	 * Set Is Net Service 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:34:44
	 * @param m_isNetService
	 * @return void
	 */
	public void setM_isNetService(boolean m_isNetService) {
		this.m_isNetService = m_isNetService;
	}
	
	
	/**
	 * Set Soap Version  
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:40:21
	 * @param m_Soap_Version
	 * @return void
	 */
	public void setM_Soap_Version(int m_Soap_Version) {
		this.m_Soap_Version = m_Soap_Version;
	}
	

	/**
	 * get Is Net Service
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:42:48
	 * @return
	 * @return boolean
	 */
	public boolean isM_isNetService() {
		return m_isNetService;
	}
	
	/**
	 * Get Soap Version
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:43:17
	 * @return
	 * @return int
	 */
	public int getM_Soap_Version() {
		return m_Soap_Version;
	}
	
	/**
	 * Set URL 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:43:54
	 * @param m_Url
	 * @return void
	 */
	public void setM_Url(String m_Url) {
		this.m_Url = m_Url;
	}
	
	
	/**
	 * Get URL
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:44:32
	 * @return
	 * @return String
	 */
	public String getM_Url() {
		return m_Url;
	}
	
	/**
	 * Get Soap Action
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:45:08
	 * @return
	 * @return String
	 */
	public String getM_SoapAction() {
		return m_SoapAction;
	}
	
	/**
	 * Set Soap Action
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:45:37
	 * @param m_SoapAction
	 * @return void
	 */
	public void setM_SoapAction(String m_SoapAction) {
		this.m_SoapAction = m_SoapAction;
	}
	
	/**
	 * Get Soap Method Name
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:46:18
	 * @return
	 * @return String
	 */
	public String getM_SoapMethodName() {
		return m_SoapMethodName;
	}
	
	/**
	 * Set Soap Method Name
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:46:49
	 * @param m_SoapMethodName
	 * @return void
	 */
	public void setM_SoapMethodName(String m_SoapMethodName) {
		this.m_SoapMethodName = m_SoapMethodName;
	}
	
	/**
	 * Get Name Space
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:47:18
	 * @return
	 * @return String
	 */
	public String getM_NameSpace() {
		return m_NameSpace;
	}
	
	/**
	 * Set Name Space
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:47:38
	 * @param m_NameSpace
	 * @return void
	 */
	public void setM_NameSpace(String m_NameSpace) {
		this.m_NameSpace = m_NameSpace;
	}

	/**
	 * Get Envelope
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:47:55
	 * @return
	 * @return Envelope
	 */
	public Envelope getM_Envelope() {
		return m_Envelope;
	}
	
	/**
	 * Get Transport
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:48:13
	 * @return
	 * @return Transport
	 */
	public Transport getM_Transport() {
		return m_Transport;
	}
	
	/**
	 * Set Envelope
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:48:34
	 * @param m_Envelope
	 * @return void
	 */
	public void setM_Envelope(Envelope m_Envelope) {
		this.m_Envelope = m_Envelope;
	}
	
	/**
	 * Set Transport
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:48:58
	 * @param m_Transport
	 * @return void
	 */
	public void setM_Transport(Transport m_Transport) {
		this.m_Transport = m_Transport;
	}

	
	
	/**Is .Net Serice*/
	private boolean m_isNetService = true;

	/** Soap Version*/
	private int m_Soap_Version = SoapEnvelope.VER11;

	/** Serializer Soap Objects*/
	private Envelope m_Envelope;
	
	/** Transport*/
	private Transport m_Transport;
	
	/** URL Service*/
	private String m_Url;
	
	/** Soap Action to Call*/
	private String m_SoapAction;
	
	/** Soap Method To Call*/ 
	private String m_SoapMethodName;
	
	/** Name Space*/  
	private String m_NameSpace; 
}
