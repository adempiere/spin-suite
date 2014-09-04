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
package org.spinsuite.interfaces;

import org.spinsuite.util.TabParameter;

import android.view.KeyEvent;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public interface I_DynamicTab {
	/**
	 * Handle Tab Menu
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 08:45:42
	 * @return void
	 */
	public void handleMenu();
	
	/**
	 * Get Tab Parameter
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 08:48:26
	 * @return
	 * @return TabParameter
	 */
	public TabParameter getTabParameter();
	
	/**
	 * Set Tab Parameters
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 13:49:58
	 * @param tabParam
	 * @return void
	 */
	public void setTabParameter(TabParameter tabParam);
	
	/**
	 * Refresh when parent yet changed
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 14:22:52
	 * @param reQuery
	 * @return boolean
	 */
	public boolean refreshFromChange(boolean reQuery);
	
	/**
	 * Save Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/05/2014, 23:01:26
	 * @return
	 * @return boolean
	 */
	public boolean save();
	
	/**
	 * On Key Down
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 15:47:28
	 * @param keyCode
	 * @param event
	 * @return
	 * @return boolean
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event);
}
