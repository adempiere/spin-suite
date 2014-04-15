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

import org.spinsuite.util.contribution.ActionItem;

import android.graphics.drawable.Drawable;

/**
 * @author Yamel Senih
 *
 */
public class ActionItemList extends ActionItem {

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 09/05/2012, 15:12:35
	 * @param actionId
	 * @param title
	 * @param icon
	 */
	public ActionItemList(int actionId, String title, Drawable icon) {
		super(actionId, title, icon);
	}

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 09/05/2012, 15:12:35
	 * @param actionId
	 * @param title
	 */
	public ActionItemList(int actionId, String title) {
		super(actionId, title);
	}

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 09/05/2012, 15:12:35
	 * @param icon
	 */
	public ActionItemList(Drawable icon) {
		super(icon);
	}

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 09/05/2012, 15:12:35
	 * @param actionId
	 * @param icon
	 */
	public ActionItemList(int actionId, Drawable icon) {
		super(actionId, icon);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 09/05/2012, 15:16:23
	 * @param value
	 * @param title
	 * @param icon
	 */
	public ActionItemList(String value, String title, Drawable icon){
		super(0, title, icon);
		this.value = value;
	}
	
	/**
	 * Utilizado para validar permisos de acceso a Formulario
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 13/08/2012, 15:32:00
	 * @param value
	 * @param title
	 * @param icon
	 * @param className
	 */
	public ActionItemList(String value, String title, Drawable icon, String className){
		this(value, title, icon);
	}
	
	/**	Value					*/
	private String value = null;
	
	/**	Class Name				*/
	private String className = null;
	
	/**
	 * Obtiene el Value que sustitye el ID
	 * @author Yamel Senih 09/05/2012, 15:14:15
	 * @return
	 * @return String
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * Establece el Nombre de la Clase a llamar al momento 
	 * de lanzar la actividad
	 * @author Yamel Senih 13/08/2012, 15:35:21
	 * @param className
	 * @return void
	 */
	public void setClassName(String className){
		this.className = className;
	}
	
	/**
	 * Obtiene el Nombre de la Actividad
	 * @author Yamel Senih 13/08/2012, 15:36:31
	 * @return
	 * @return String
	 */
	public String getClassName(){
		return className;
	}

}
