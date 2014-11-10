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
 * Contributor(s): Carlos Parada www.erpcya.com                    					 *
 *************************************************************************************/
package com._3e.ADInterface;

import java.util.ArrayList;
import java.util.Hashtable;
import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import android.content.Context;
import android.database.Cursor;

/**
 * @author Carlos Parada
 *
 */
@SuppressWarnings("rawtypes")
public class WSListParameters extends AttributeContainer implements KvmSerializable{

	/** Context */
	private Context m_ctx;
	/** Name Space */
	protected String m_namespace;
	/** Parameters */
	private ArrayList<PField> m_properties = new ArrayList<PField>();
	/** Parameter Name */
	protected String m_Name;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:56:34
	 * @param ctx
	 * @param NameSpace
	 * @param Name
	 */
	public WSListParameters(Context ctx,String NameSpace,String Name) {
		// TODO Auto-generated constructor stub
		m_ctx=ctx;
		m_namespace = NameSpace;
		m_Name = Name;
	}
	
	/**
	 * Create Object From Cursor
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:56:59
	 * @param ctx
	 * @param NameSpace
	 * @param p_prop
	 * @param Name
	 */
	public WSListParameters(Context ctx,String NameSpace,Cursor p_prop,String Name) {
		// TODO Auto-generated constructor stub
		m_ctx=ctx;
		m_namespace = NameSpace;
		m_Name = Name;
		setProperties(p_prop);
	}

	/**
	 * Create Object From Arraylist
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:58:12
	 * @param ctx
	 * @param NameSpace
	 * @param p_prop
	 * @param Name
	 */
	public WSListParameters(Context ctx,String NameSpace,ArrayList<PField> p_prop,String Name) {
		// TODO Auto-generated constructor stub
		m_ctx=ctx;
		m_namespace = NameSpace;
		m_Name = Name;
		setProperties(p_prop);
	}
	
	/** 
	 * Set Variables to Arraylist
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:59:01
	 * @param p_prop
	 * @return void
	 */
	public void setProperties(ArrayList<PField> p_prop){		
		m_properties = p_prop;
	}
	
	/**
	 * Set Properties from Cursor
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:00:15
	 * @param p_prop
	 * @return void
	 */
	public void setProperties(Cursor p_prop)
	{
		int l_count_col = p_prop.getColumnCount();
		
		String l_campo;
		Object l_valor;
		
		for (int i=0;i<l_count_col;i++){
			l_campo = p_prop.getColumnName(i);
			l_valor = p_prop.getString(i);
			m_properties.add(new PField(l_campo, l_valor, PropertyInfo.STRING_CLASS));
		}
		
	}
    
	@Override
	public Object getProperty(int item) {
		// TODO Auto-generated method stub
		return m_properties.get(item).getM_value();
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return m_properties.size();
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		arg2.setName(m_properties.get(arg0).getM_field());
		arg2.setType(m_properties.get(arg0).getM_type());
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		m_properties.get(arg0).setM_value(arg1);
	}

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:03:01
	 * @param m_Name
	 * @return void
	 */
	public void setM_Name(String m_Name) {
		this.m_Name = m_Name;
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:03:28
	 * @return
	 * @return String
	 */
	public String getM_Name() {
		return m_Name;
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:03:49
	 * @param m_ctx
	 * @return void
	 */
	public void setM_ctx(Context m_ctx) {
		this.m_ctx = m_ctx;
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:04:08
	 * @return
	 * @return Context
	 */
	public Context getM_ctx() {
		return m_ctx;
	}
	

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:04:25
	 * @return
	 * @return String
	 */
	public String getM_namespace() {
		return m_namespace;
	}
	
	
}
