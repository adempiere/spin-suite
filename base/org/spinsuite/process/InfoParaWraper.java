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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.process;

import java.util.Arrays;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 13, 2015, 2:30:10 AM
 *
 */
public class InfoParaWraper {
	
	/**
	 * Default
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public InfoParaWraper() {
		
	}
	
	/**	Name							*/
	public String 						Value = null;
	/** Title of the Process/Report 	*/
	public String						Title;
	/**	Description						*/
	public String 						Description = null;
	/**	Help							*/
	public String 						Help = null;
	/** Class Name 						*/
	public String						ClassName = null;
	/**	Procedure Name					*/
	public String						ProcedureName = null;
	/**	Is a Report						*/
	public boolean 						IsReport = false;
	/**	Show Help 						*/
	public String 						ShowHelp = null;
	/**	Report View						*/
	public int 							AD_ReportView_ID = 0;
	/**	Print Format					*/
	public int 							AD_PrintFormat_ID = 0;
	/**	Is Server Process				*/
	public boolean						IsServerProcess = false;
	/**	Process Field Parameter			*/
	public InfoPara[] 					ProcessFields = null;
	
	@Override
	public String toString() {
		return "InfoParaWraper [m_Value=" + Value + ", Title=" + Title
				+ ", Description=" + Description + ", Help=" + Help
				+ ", ClassName=" + ClassName + ", ProcedureName="
				+ ProcedureName + ", IsReport=" + IsReport + ", ShowHelp="
				+ ShowHelp + ", AD_ReportView_ID=" + AD_ReportView_ID
				+ ", AD_PrintFormat_ID=" + AD_PrintFormat_ID
				+ ", IsServerProcess=" + IsServerProcess + ", ProcessFields="
				+ Arrays.toString(ProcessFields) + "]";
	}
}