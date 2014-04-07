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
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import java.util.Date;

import org.spinsuite.view.lookup.VDateBox;

/**
 * @author Yamel Senih
 *
 */
public class DateBoxHolder {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/05/2012, 19:26:46
	 * @param et_List
	 */
	public DateBoxHolder(VDateBox et_List){
		this.et_List = et_List;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:03:05
	 */
	public DateBoxHolder(){
	
	}
	
	/**	List View with Date handle	*/
	private VDateBox et_List;
	
	/**
	 * Get Lookup Date Box
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:31:10
	 * @return
	 * @return VDateBox
	 */
	public VDateBox getVDateBox(){
		return et_List;
	}
	
	/**
	 * Set Lookup
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:31:37
	 * @param et_List
	 * @return void
	 */
	public void setVDateBox(VDateBox et_List){
		this.et_List = et_List;
	}
	
	/**
	 * Get Date As String
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:31:49
	 * @return
	 * @return String
	 */
	public String getDateAsString(){
		return et_List.getDateAsString();
	}
	
	/**
	 * Set Date
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 12:13:57
	 * @param date
	 * @return void
	 */
	public void setDate(Date date){
		et_List.setDate(date);
	}
	
	/**
	 * Get date
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 12:14:44
	 * @return
	 * @return Date
	 */
	public Date getDate(){
		return et_List.getDate();
	}
	
}
