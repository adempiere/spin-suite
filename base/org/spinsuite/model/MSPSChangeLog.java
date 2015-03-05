/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.spinsuite.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 *	Change Log Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MChangeLog.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public class MSPSChangeLog extends X_SPS_ChangeLog
{
	/**
	 * 	Do we track changes for this table
	 *	@param AD_Table_ID table
	 *	@return true if changes are tracked
	 */
	public static boolean isLogged (Context ctx,int AD_Table_ID, DB conn)
	{
		if (s_changeLog == null || s_changeLog.length == 0)
			fillChangeLog(ctx, conn);
		//
		int index = Arrays.binarySearch(s_changeLog, AD_Table_ID);
		return index >= 0;
	}	//	trackChanges
	
	/**
	 *	Fill Log with tables to be logged 
	 */
	private static void fillChangeLog(Context ctx,DB conn)
	{
		boolean handleConnection = false;
		//		Connection
		if(conn == null){
			conn = new DB(ctx);
			conn.openDB(DB.READ_ONLY);
			handleConnection = true;
		}
		else if (!conn.isOpen())
			conn.openDB(DB.READ_ONLY);
			
		ArrayList<Integer> list = new ArrayList<Integer>(40);
		String sql = "SELECT t.SPS_Table_ID FROM SPS_Table t "
			+ "WHERE t.IsChangeLog='Y'"					//	also inactive
			+ " OR EXISTS (SELECT 1 FROM SPS_Column c "
				+ "WHERE t.SPS_Table_ID=c.SPS_Table_ID AND c.ColumnName='EntityType') "
			+ "ORDER BY t.SPS_Table_ID";
		
		Cursor rset = null;
		
		try
		{
			rset  = conn.querySQL(sql,new String[]{});
			if(rset.moveToFirst()){
				do{
					list.add(rset.getInt(rset.getColumnIndex("SPS_Table_ID")));
				} while(rset.moveToNext());
			}
		}
		catch (Exception e)
		{
			LogM.log(ctx, MSPSChangeLog.class, Level.SEVERE, sql, e.getCause());
		}
		finally
		{
			if (handleConnection)
				DB.closeConnection(conn);
		}
		//	Convert to Array
		s_changeLog = new int [list.size()];
		for (int i = 0; i < s_changeLog.length; i++)
		{
			Integer id = (Integer)list.get(i);
			s_changeLog[i] = id.intValue();
		}
		LogM.log(ctx, MSPSChangeLog.class, Level.INFO, "#" + s_changeLog.length);
	}	//	fillChangeLog

	/**	Change Log				*/
	private static int[]		s_changeLog = null;
	/** NULL Value				*/
	public static String		NULL = "NULL";
	
	
	/**************************************************************************
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MSPSChangeLog(Context ctx, Cursor rs, DB conn)
	{
		super(ctx, rs, conn);
	}	//	MChangeLog

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_ChangeLog_ID id
	 *	@param trxName transaction
	 */
	public MSPSChangeLog (Context ctx, int AD_ChangeLog_ID, DB conn)
	{
		super (ctx, 0, conn);
	}	//	MChangeLog
	
	/**
	 *	Preserved for backward compatibility
	 *@deprecated
	 */
	public MSPSChangeLog (Context ctx, 
			int AD_ChangeLog_ID, DB conn, int AD_Session_ID, 
			int SPS_Table_ID, int SPS_Column_ID, int Record_ID,
			int AD_Client_ID, int AD_Org_ID,
			Object OldValue, Object NewValue)
	{
		this(ctx, AD_ChangeLog_ID, conn, AD_Session_ID, SPS_Table_ID,
				SPS_Column_ID, Record_ID, AD_Client_ID, AD_Org_ID, OldValue,
				NewValue, (String) null /*event*/ );
	}	// MChangeLog

	/**
	 * 	Full Constructor
	 *	@param ctx context
	 *	@param AD_ChangeLog_ID 0 for new change log
	 *	@param TrxName transaction
	 *	@param AD_Session_ID session
	 *	@param AD_Table_ID table
	 *	@param AD_Column_ID column
	 *	@param Record_ID record
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 *	@param OldValue old
	 *	@param NewValue new
	 */
	public MSPSChangeLog (Context ctx, 
		int AD_ChangeLog_ID, DB conn, int AD_Session_ID, 
		int SPS_Table_ID, int SPS_Column_ID, int Record_ID,
		int AD_Client_ID, int AD_Org_ID,
		Object OldValue, Object NewValue, String event)
	{
		this (ctx, 0, conn);	
		if (AD_ChangeLog_ID == 0)
		{
			AD_ChangeLog_ID = MSequence.getNextID(ctx, AD_Client_ID, Table_Name, conn);
			if (AD_ChangeLog_ID <= 0)
				LogM.log(ctx, MSPSChangeLog.class, Level.SEVERE, "No NextID (" + AD_ChangeLog_ID + ")");
		}
		setSPS_ChangeLog_ID (AD_ChangeLog_ID);
		setAD_Session_ID (AD_Session_ID);
		//
		setSPS_Table_ID (SPS_Table_ID);
		setSPS_Column_ID (SPS_Column_ID);
		setRecord_ID (Record_ID);
		//
		setClientOrg (AD_Client_ID, AD_Org_ID);
		//
		setOldValue (OldValue);
		setNewValue (NewValue);
		setEventChangeLog(event);
		//	EVENT / Release 3.3.1t_2007-12-05 ADempiere
		// Drop description from AD_ChangeLog - pass it to AD_Session to save disk space
		// setDescription(Adempiere.MAIN_VERSION + "_" + Adempiere.DATE_VERSION + " " + Adempiere.getImplementationVersion());
	}	//	MChangeLog

	
	/**
	 * 	Set Old Value
	 *	@param OldValue old
	 */
	public void setOldValue (Object OldValue)
	{
		if (OldValue == null)
			super.setOldValue (NULL);
		else
			super.setOldValue (OldValue.toString());
	}	//	setOldValue

	/**
	 * 	Is Old Value Null
	 *	@return true if null
	 */
	public boolean isOldNull()
	{
		String value = getOldValue();
		return value == null || value.equals(NULL);
	}	//	isOldNull
	
	/**
	 * 	Set New Value
	 *	@param NewValue new
	 */
	public void setNewValue (Object NewValue)
	{
		if (NewValue == null)
			super.setNewValue (NULL);
		else
			super.setNewValue (NewValue.toString());
	}	//	setNewValue
	
	/**
	 * 	Is New Value Null
	 *	@return true if null
	 */
	public boolean isNewNull()
	{
		String value = getNewValue();
		return value == null || value.equals(NULL);
	}	//	isNewNull
	
}	//	MChangeLog
