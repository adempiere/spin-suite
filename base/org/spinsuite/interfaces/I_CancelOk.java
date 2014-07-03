/*************************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                                   *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 of the GNU General Public License as published   		 *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com				  		 *
 *************************************************************************************/
package org.spinsuite.interfaces;

import android.content.Intent;

/**
 * @author Yamel Senih
 *
 */
public interface I_CancelOk {
	
	/**
	 * Ok Action
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/02/2014, 10:08:08
	 * @return
	 * @return boolean
	 */
	public boolean processActionOk();
	
	/**
	 * Cancel Action
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/02/2014, 10:07:56
	 * @return
	 * @return boolean
	 */
	public boolean processActionCancel();
	
	/**
	 * Get Activity Parameters
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/02/2014, 10:07:44
	 * @return
	 * @return Intent
	 */
	public Intent getParam();
	
}
