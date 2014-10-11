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
package org.spinsuite.model;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MSPSTable extends X_SPS_Table {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 09:04:04
	 * @param ctx
	 * @param SPS_Table_ID
	 * @param conn
	 */
	public MSPSTable(Context ctx, int SPS_Table_ID, DB conn) {
		super(ctx, SPS_Table_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 09:04:04
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MSPSTable(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Class 
	 */
	public static Class<?> getClass (Context ctx, String tableName){
		//	Not supported
		if (tableName == null || tableName.endsWith("_Trl"))
			return null;
		
		String name = null;
		String className = Env.getContext(ctx, "ClassName|" + tableName);
		String etmodelpackage = null;
		if(className == null
				|| className.length() == 0){
			etmodelpackage = DB.getSQLValueStringEx(ctx, "SELECT et.ModelPackage " +
					"FROM AD_EntityType et " +
					"INNER JOIN SPS_Table t ON(t.EntityType = et.EntityType) " +
					"WHERE t.TableName = ?", new String[]{tableName});
			//	Strip table name prefix (e.g. AD_) Customizations are 3/4
			className = tableName;
			int index = className.indexOf('_');
			if (index > 0)
			{
				if (index < 3)		//	AD_, A_
					 className = className.substring(index+1);
			}
			//	Remove underlines
			className = className.replaceAll("_", "");
			name = etmodelpackage + ".M" + className;
		} else {
			name = className;
		}
		
		Class<?> clazz = getPOclass(name, tableName);
		if (clazz != null){
			//	Save in context
			Env.setContext(ctx, "ClassName|" + tableName, name);
			return clazz;
		}
		//	SFAndroid Class
		name = "org.spinsuite.model.X_" + tableName;
		clazz = getPOclass(name, tableName);
		if (clazz != null)
		{
			//	Save in context
			Env.setContext(ctx, "ClassName|" + tableName, name);
			return clazz;
		}
		//	Adempiere Extension
		name = "adempiere.model.X_" + tableName;
		clazz = getPOclass(name, tableName);
		if (clazz != null)
		{
			//	Save in context
			Env.setContext(ctx, "ClassName|" + tableName, name);
			return clazz;
		}
		
		//hengsin - allow compatibility with compiere plugins
		//Compiere Extension
		name = "compiere.model.X_" + tableName;
		clazz = getPOclass(name, tableName);
		if (clazz != null)
		{
			//	Save in context
			Env.setContext(ctx, "ClassName|" + tableName, name);
			return clazz;
		}

		//	Default
		name = "org.compiere.model.X_" + tableName;
		clazz = getPOclass(name, tableName);
		if (clazz != null)
		{
			//	Save in context
			Env.setContext(ctx, "ClassName|" + tableName, name);
			return clazz;
		}
		//	Default
		name = "org.compiere.model.GenericPO";
		//	Save in context
		Env.setContext(ctx, "ClassName|" + tableName, name);
		return null;
	}	//	getClass*/
	
	/**
	 * Get PO Class
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 10:09:38
	 * @param className
	 * @param tableName
	 * @return
	 * @return Class<?>
	 */
	private static Class<?> getPOclass (String className, String tableName)
	{
		try{
			Class<?> clazz = Class.forName(className);
			// Validate if the class is for specified tableName
			if (tableName != null){
				String classTableName = clazz.getField("Table_Name").get(null).toString();
				if (!tableName.equals(classTableName)){
					return null;
				}
			}
			//	Make sure that it is a PO class
			Class<?> superClazz = clazz.getSuperclass();
			while (superClazz != null){
				if (superClazz == PO.class){
					return clazz;
				}
				superClazz = superClazz.getSuperclass();
			}
		}
		catch (Exception e){}
		return null;
	}	//	getPOclass
	
	
	/**
	 * Get PO Class Instance
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 10:41:14
	 * @param Record_ID
	 * @param conn
	 * @return
	 * @return PO
	 */
	public static PO getPO (Context ctx, int Record_ID, String tableName, DB conn)
	{
		//	
		Class<?> clazz = getClass(ctx, tableName);
		if (clazz == null) {
			GenericPO po = new GenericPO(ctx, tableName, Record_ID, conn);
			return po;
		}
		//	any
		boolean errorLogged = false;
		try {
			Constructor<?> constructor = null;
			try {
				constructor = clazz.getDeclaredConstructor(new Class[]{Context.class, int.class, DB.class});
			}
			catch (Exception e) {
				String msg = e.getLocalizedMessage();
				if (msg == null)
					msg = e.toString();
				LogM.log(ctx, "MSFATable", Level.WARNING, "PO.getPO(int, DB) No transaction Constructor for " + clazz + " (" + msg + ")");
			}
			//	Valid Constructor
			if(constructor == null)
				return null;
			//	
			PO po = (PO)constructor.newInstance(new Object[] {ctx, Record_ID, conn});
			if (po != null 
					&& po.get_ID() != Record_ID 
					&& Record_ID > 0)
				return null;
			return po;
		}
		catch (Exception e)
		{
			if (e.getCause() != null)
			{
				Throwable t = e.getCause();
				LogM.log(ctx, "MSFATable", Level.SEVERE, "PO.getPO(int, DB) (id) - Table=" + tableName + ",Class=" + clazz, t);
				errorLogged = true;
				if (t instanceof Exception)
					LogM.log(ctx, "MSFATable", Level.SEVERE, "PO.getPO(int, DB) Error", (Exception)e.getCause());
				else
					LogM.log(ctx, "MSFATable", Level.SEVERE, "PO.getPO(int, DB) Table=" + tableName + ",Class=" + clazz);
			}
			else
			{
				LogM.log(ctx, "MSFATable", Level.SEVERE, "PO.getPO(int, DB) (id) - Table=" + tableName + ",Class=" + clazz, e);
				errorLogged = true;
				LogM.log(ctx, "MSFATable", Level.SEVERE, "PO.getPO(int, DB) Table=" + tableName + ",Class=" + clazz);
			}
		}
		if (!errorLogged)
			LogM.log(ctx, "MSFATable", Level.SEVERE, "PO.getPO(int, DB) (id) - Not found - Table=" + tableName 
				+ ", Record_ID=" + Record_ID);
		return null;
	}	//	getPO
	
	/**
	 * Get Table ID from Table Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/10/2014, 13:04:54
	 * @param ctx
	 * @param tableName
	 * @param conn
	 * @return
	 * @return int
	 */
	public static int getSPS_Table_ID(Context ctx, String tableName, DB conn)  {
		if(tableName == null
				|| tableName.length() == 0)
			return -1;
		//	Default Search
		return DB.getSQLValue(ctx, "SELECT t.SPS_Table_ID "
				+ "FROM SPS_Table t "
				+ "WHERE t.TableName = ?", conn, tableName);
	}
	

	@Override
	public String toString() {
		return "TableName=" + getTableName() 
				+ "\nName=" + getName() 
				+ "\nSPS_Table_ID=" + getSPS_Table_ID();
	}
	
}
