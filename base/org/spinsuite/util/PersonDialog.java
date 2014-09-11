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

import android.app.Dialog;
import android.content.Context;

/**
 * @author Yamel Senih
 *
 */
public class PersonDialog extends Dialog {

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 26/04/2012, 21:04:41
	 * @param context
	 */
	public PersonDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private boolean response = false;
	
	/**
	 * Establece el mensaje de respuesta
	 * @author Yamel Senih 26/04/2012, 21:05:39
	 * @param response
	 * @return void
	 */
	public void setResponse(boolean response){
		this.response = response;
	}
	
	/**
	 * Obtiene la respuesta del mensaje
	 * @author Yamel Senih 26/04/2012, 21:07:21
	 * @return 
	 * @return boolean
	 */
	public boolean getResponse(){
		return this.response;
	}
	
}
