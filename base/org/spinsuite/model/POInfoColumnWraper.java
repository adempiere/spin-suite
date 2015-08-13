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
package org.spinsuite.model;

import java.util.Arrays;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 13, 2015, 2:30:10 AM
 *
 */
public class POInfoColumnWraper {
	
	/**
	 * Default
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public POInfoColumnWraper() {
		
	}
	
	/**	Table Identifier	*/
	public int 				SPS_Table_ID	= 0;
	/**	Table Name			*/
	public String 			TableName 		= null;
	/**	Is Deleteable		*/
	public boolean 			IsDeleteable	= true;
	/**	Is Change Log		*/
	public boolean 			IsChangeLog		= true;
	/**	Count Columns		*/
	public int 				CountColumnSQL	= 0;
	/**	Columns				*/
	public POInfoColumn[] 	Columns 		= null;
	
	@Override
	public String toString() {
		return "POInfoColumnWraper [SPS_Table_ID=" + SPS_Table_ID
				+ ", TableName=" + TableName + ", IsDeleteable=" + IsDeleteable
				+ ", IsChangeLog=" + IsChangeLog + ", CountColumnSQL="
				+ CountColumnSQL + ", Columns=" + Arrays.toString(Columns)
				+ "]";
	}
}