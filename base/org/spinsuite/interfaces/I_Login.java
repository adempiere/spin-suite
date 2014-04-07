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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.interfaces;


/**
 * @author Yamel Senih
 *
 */
public interface I_Login {

	/**
	 * Changes when press button Ok
	 * @author Yamel Senih 24/04/2012, 17:07:16
	 * @return
	 * @return boolean
	 */
	public boolean aceptAction();
	
	/**
	 * Changes when press button Cancel
	 * @author Yamel Senih 24/04/2012, 17:09:22
	 * @return
	 * @return boolean
	 */
	public boolean cancelAction();
	
	/**
	 * Load Data in Current Activity
	 * @author Yamel Senih 26/04/2012, 12:16:53
	 * @return
	 * @return boolean
	 */
	public boolean loadData();
}
