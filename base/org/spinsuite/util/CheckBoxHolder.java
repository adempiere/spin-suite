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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import android.widget.CheckBox;

/**
 * @author Yamel Senih
 *
 */
public class CheckBoxHolder {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 20/10/2012, 12:19:34
	 * @param et_List
	 */
	public CheckBoxHolder(CheckBox et_List){
		this.ch_List = et_List;
	}
	
	public CheckBoxHolder(){
	}
	
	private CheckBox ch_List;
	
	/**
	 * Obtiene el CheckBox
	 * @author Yamel Senih 20/10/2012, 12:20:10
	 * @return
	 * @return CheckBox
	 */
	public CheckBox getCheckBox(){
		return ch_List;
	}
	
	/**
	 * Establece el CheckBox
	 * @author Yamel Senih 20/10/2012, 12:20:19
	 * @param ch_List
	 * @return void
	 */
	public void setCheckBox(CheckBox ch_List){
		this.ch_List = ch_List;
	}
	
	/**
	 * Verifica el estado del CheckBox
	 * @author Yamel Senih 20/10/2012, 12:21:50
	 * @return
	 * @return boolean
	 */
	public boolean isChecked(){
		return ch_List.isChecked();
	}
	
	/**
	 * Establece el estado del CheckBox
	 * @author Yamel Senih 20/10/2012, 12:22:25
	 * @param checked
	 * @return void
	 */
	public void setString(boolean checked){
		ch_List.setChecked(checked);
	}
	
}
