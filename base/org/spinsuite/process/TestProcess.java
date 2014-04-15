/*************************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                                   *
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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.process;

import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.util.GPSHandler;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TestProcess extends StdProcess {

	/**	Business Partner		*/
	private int 	p_C_BPartner_ID = 1;
	/**	Description				*/
	private String	p_Description	= null;
	/**	Document Date To		*/
	private Date 	p_DateDoc		= null;
	/**	Document Date To		*/
	private Date 	p_DateDoc_To	= null;
	/**	Is Sales Transaction	*/
	private boolean p_IsSOTrx		= false;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter para : params){
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para.getParameterAsInt();
			else if (name.equals("Description"))
				p_Description = (String)para.getParameter();
			else if (name.equals("DateDoc")){
				p_DateDoc = (Date)para.getParameter();
				p_DateDoc_To = (Date)para.getParameter_To();
			} else if(name.equals("IsSOTrx"))
				p_IsSOTrx = para.getParameterAsBoolean();
			else
				LogM.log(getCtx(), this.getClass(), Level.SEVERE, "Unknown Parameter: " + name);
		}
	}
	
	@Override
	protected String doIt() throws Exception {
		GPSHandler gpsHand = new GPSHandler(getCtx());
		gpsHand.startLocation();
		Msg.toastMsg(getCtx(), " GPS Hander Up? " + gpsHand.isProviderEnabled());
		Msg.toastMsg(getCtx(), " GPS Latitude = " + gpsHand.getLatitude() 
				+ " GPS Longitude = " + gpsHand.getLongitude() 
				+ " GPS Precision = " + gpsHand.getPrecision());
		return "";
	}

}
