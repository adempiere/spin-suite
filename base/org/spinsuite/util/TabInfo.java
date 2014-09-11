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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import android.os.Bundle;

/**
 * 
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TabInfo {
	/**	Class Name					*/
	private Class<?> clazz 		= 	null;
	/**	Icon ID						*/
	private int 	icon_ID 	= 	0;
	/**	Title						*/
	private String 	title		=	null;
	/**	Args						*/
	private Bundle 	args 		= 	null;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 14:57:54
	 * @param clazz
	 * @param icon_ID
	 * @param title
	 */
	public TabInfo(Class<?> clazz, int icon_ID, String title){
		this.clazz = clazz;
		this.icon_ID = icon_ID;
		this.title = title;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 14:58:56
	 * @param clazz
	 * @param title
	 * @param args
	 */
	public TabInfo(Class<?> clazz, String title, Bundle args){
		this.clazz = clazz;
		this.title = title;
		this.args = args;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 14:59:33
	 * @param clazz
	 * @param icon_ID
	 * @param args
	 */
	public TabInfo(Class<?> clazz, int icon_ID, Bundle args){
		this.clazz = clazz;
		this.icon_ID = icon_ID;
		this.args = args;
	}
	
	/**
	 * 
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 15:00:03
	 * @return
	 * @return Class
	 */
	public Class<?> getClazz(){
		return clazz;
	}
	
	/**
	 * Get Icon ID
	 * @author Yamel Senih 04/02/2013, 12:11:57
	 * @return
	 * @return int
	 */
	public int getIcon_ID(){
		return icon_ID;
	}
	
	/**
	 * Get Args
	 * @author Yamel Senih 04/02/2013, 15:23:18
	 * @return
	 * @return Bundle
	 */
	public Bundle getArgs(){
		return args;
	}
	
	/**
	 * Get Tab Title
	 * @author Yamel Senih 04/02/2013, 15:42:13
	 * @return
	 * @return String
	 */
	public String getTitle(){
		return title;
	}
}
