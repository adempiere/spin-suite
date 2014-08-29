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


import org.ksoap2.serialization.SoapSerializationEnvelope;


/**
 * Envelope
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class Envelope extends SoapSerializationEnvelope{

	/** 
	* *** Constructor ***
	* @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:54:53
	* @param version
	*/
	public Envelope(int version) {
		super(version);
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * Setting Do Net Service
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:55:29
	 * @param dotNet
	 * @return void
	 */
	public void setDotNet(boolean dotNet) {
		this.dotNet = dotNet;
	}
	
	/**
	 * Is Do Net Service
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 22:55:51
	 * @return
	 * @return boolean
	 */
	public boolean isDotNet() {
		return dotNet;
	}
	
	/* Envuelve la peticion
	 * @see org.ksoap2.SoapEnvelope#setOutputSoapObject(java.lang.Object)
	 */
	@Override
	public void setOutputSoapObject(Object soapObject) {
		// TODO Auto-generated method stub
		super.setOutputSoapObject(soapObject);
	}
	


}
