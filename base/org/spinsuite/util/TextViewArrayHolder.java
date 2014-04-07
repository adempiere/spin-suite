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
package org.spinsuite.util;

import java.util.ArrayList;

import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TextViewArrayHolder {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/03/2014, 10:04:23
	 */
	public TextViewArrayHolder() {
		tv_array = new ArrayList<TextView>();
	}
	
	/**	Array Holder			*/
	private ArrayList<TextView> tv_array;
	
	/**
	 * Add Text View to Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/03/2014, 10:06:45
	 * @param tv_holder
	 * @return void
	 */
	public void addTextView(TextView tv_holder){
		tv_array.add(tv_holder);
	}
	
	/**
	 * Get Text View to Index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/03/2014, 10:09:12
	 * @param index
	 * @return
	 * @return TextView
	 */
	public TextView getTextView(int index){
		return tv_array.get(index);
	}
	
	/**
	 * Get String
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 22:19:01
	 * @return
	 * @return String
	 */
	public String getText(int index){
		return tv_array.get(index).getText().toString();
	}
	
	/**
	 * Set Text to Index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/03/2014, 10:11:22
	 * @param text
	 * @param index
	 * @return void
	 */
	public void setText(String text, int index){
		tv_array.get(index).setText(text);
	}

}
