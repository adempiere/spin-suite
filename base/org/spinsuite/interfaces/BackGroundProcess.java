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
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
package org.spinsuite.interfaces;

/**
 * Background Process
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public interface BackGroundProcess {
		
	/**
	 * Publish Before Initialize Process
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 3:14:02 PM
	 * @return void
	 */
	public void publishBeforeInit();
	
	/**
	 *  Publish On Running
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 3:15:05 PM
	 * @return void
	 */
	public void publishOnRunning();
	
	/**
	 * Publish After End
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 3:15:33 PM
	 * @return void
	 */
	public void publishAfterEnd();
	
	/**
	 * Run Process
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 3:16:00 PM
	 * @return Object
	 */
	public Object run();
	
}
