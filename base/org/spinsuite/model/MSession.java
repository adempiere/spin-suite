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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 *	Session Model.
 *	Maintained in AMenu.
 *	
 *  @author Jorg Janke
 *  @version $Id: MSession.java,v 1.3 2006/07/30 00:58:05 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1810182 ] Session lost after cache reset 
 * 			<li>BF [ 1892156 ] MSession is not really cached 
 */
public class MSession extends X_AD_Session
{
	/**
	 * 	Get existing or create local session
	 *	@param ctx context
	 *	@param createNew create if not found
	 *	@return session session
	 */
	public static MSession get (Context ctx, boolean createNew)
	{
		int AD_Session_ID = Env.getContextAsInt(ctx, "#AD_Session_ID");
		MSession session = null;
		if (AD_Session_ID > 0)
			session = (MSession)s_sessions.get(Integer.valueOf(AD_Session_ID));
		// Try to load
		if (session == null && AD_Session_ID > 0)
		{
			session = new MSession(ctx, AD_Session_ID, null);
			if (session.get_ID() != AD_Session_ID) {
				Env.setContext (ctx, "#AD_Session_ID", AD_Session_ID);
			}
			s_sessions.put(AD_Session_ID, session);
		}
		// Create New
		if (session == null && createNew)
		{
			session = new MSession (ctx, null);	//	local session
			session.save();
			AD_Session_ID = session.getAD_Session_ID();
			Env.setContext (ctx, "#AD_Session_ID", AD_Session_ID);
			s_sessions.put (Integer.valueOf(AD_Session_ID), session);
		}	
		return session;
	}	//	get
	
	/**
	 * 	Get existing or create remote session
	 *	@param ctx context
	 *	@param Remote_Addr remote address
	 *	@param Remote_Host remote host
	 *	@param WebSession web session
	 *	@return session
	 */
	public static MSession get (Context ctx, String Remote_Addr, String Remote_Host)
	{
		int AD_Session_ID = Env.getContextAsInt(ctx, "#AD_Session_ID");
		MSession session = null;
		if (AD_Session_ID > 0)
			session = (MSession)s_sessions.get(Integer.valueOf(AD_Session_ID));
		if (session == null)
		{
			session = new MSession (ctx, Remote_Addr, Remote_Host, null);	//	remote session
			session.save();
			AD_Session_ID = session.getAD_Session_ID();
			Env.setContext(ctx, "#AD_Session_ID", AD_Session_ID);
			s_sessions.put(Integer.valueOf(AD_Session_ID), session);
		}	
		return session;
	}	//	get

	/**	Sessions					*/
	private static HashMap <Integer,MSession> s_sessions = new HashMap <Integer,MSession>();		//	one client session 
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Session_ID id
	 *	@param conn Connection
	 */
	public MSession (Context ctx, int AD_Session_ID, DB conn)
	{
		
		super(ctx, AD_Session_ID, conn);
		if (AD_Session_ID == 0)
		{
			setProcessed (false);
		}
	}	//	MSession

	/**
	 * 	Load Costructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MSession(Context ctx, Cursor rs, DB conn)
	{
		super(ctx, rs, conn);
	}	//	MSession

	/**
	 * 	New (remote) Constructor
	 *	@param ctx context
	 *	@param Remote_Addr remote address
	 *	@param Remote_Host remote host
	 *	@param WebSession web session
	 *	@param trxName transaction
	 */
	public MSession (Context ctx, String Remote_Addr, String Remote_Host, DB conn)
	{
		this (ctx, 0, conn);
		if (Remote_Addr != null)
			setRemote_Addr(Remote_Addr);
		if (Remote_Host != null)
			setRemote_Host(Remote_Host);
		/*setDescription(Adempiere.MAIN_VERSION + "_"
				+ Adempiere.DATE_VERSION + " "
				+ Adempiere.getImplementationVersion());*/
		setAD_Role_ID(Env.getContextAsInt(ctx, "#AD_Role_ID"));
		setLoginDate(Env.getCurrentDate());
	}	//	MSession

	/**
	 * 	New (local) Constructor
	 *	@param ctx context
	 *	@param trxName transaction
	 */
	public MSession (Context ctx, DB conn)
	{
		this (ctx, 0, conn);
		//try
		//{
			//InetAddress lh = InetAddress.getLocalHost();
			//setRemote_Addr(lh.getHostAddress());
			//setRemote_Host(lh.getHostName());
			/*setDescription(Adempiere.MAIN_VERSION + "_"
					+ Adempiere.DATE_VERSION + " "
					+ Adempiere.getImplementationVersion());*/
			setAD_Role_ID(Env.getContextAsInt(ctx, "#AD_Role_ID"));
			setLoginDate(Env.getCurrentDate());
		//}
		//catch (UnknownHostException e)
		//{
			//LogM.log(ctx, MSession.class, Level.SEVERE, "No Local Host", e.getCause());
		//}
	}	//	MSession

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MSession[")
			.append(getAD_Session_ID())
			.append(",AD_User_ID=").append(getCreatedBy())
			.append(",").append(getCreated())
			.append(",Remote=").append(getRemote_Addr());
		String s = getRemote_Host();
		if (s != null && s.length() > 0)
			sb.append(",").append(s);
		sb.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * 	Session Logout
	 */
	public void logout()
	{
		setProcessed(true);
		save();
		s_sessions.remove(Integer.valueOf(getAD_Session_ID()));
		LogM.log(getCtx(), MSession.class, Level.INFO, "Created:" + getCreated() + ", Updated:" +getUpdated());
	}	//	logout

	/**
	 * 	Preserved for backward compatibility
	 *@deprecated
	 */
	public MSPSChangeLog changeLog (
		DB conn, int AD_ChangeLog_ID,
		int AD_Table_ID, int AD_Column_ID, int Record_ID,
		int AD_Client_ID, int AD_Org_ID,
		Object OldValue, Object NewValue)
	{
		return changeLog(conn, AD_ChangeLog_ID, AD_Table_ID, AD_Column_ID,
				Record_ID, AD_Client_ID, AD_Org_ID, OldValue, NewValue,
				(String) null);
	}	// changeLog

	/**
	 * 	Create Change Log only if table is logged
	 * 	@param TrxName transaction name
	 *	@param AD_ChangeLog_ID 0 for new change log
	 *	@param AD_Table_ID table
	 *	@param AD_Column_ID column
	 *	@param Record_ID record
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 *	@param OldValue old
	 *	@param NewValue new
	 *	@return saved change log or null
	 */
	public MSPSChangeLog changeLog (
		DB conn, int AD_ChangeLog_ID,
		int AD_Table_ID, int AD_Column_ID, int Record_ID,
		int AD_Client_ID, int AD_Org_ID,
		Object OldValue, Object NewValue, String event)
	{
		//	Null handling
		if (OldValue == null && NewValue == null)
			return null;
		//	Equal Value
		if (OldValue != null && NewValue != null && OldValue.equals(NewValue))
			return null;

		//	Role Logging
		X_AD_Role role = new X_AD_Role(getCtx(), Env.getAD_Role_ID(getCtx()), conn);
		
		//	Do we need to log
		if (MSPSChangeLog.isLogged(getCtx(), AD_Table_ID, conn)		//	im/explicit log
			|| (role != null && role.isChangeLog()))//	Role Logging
			;
		else
			return null;
		//
		LogM.log(getCtx(), MSession.class, Level.FINEST, "AD_ChangeLog_ID=" + AD_ChangeLog_ID
				+ ", AD_Session_ID=" + getAD_Session_ID()
				+ ", AD_Table_ID=" + AD_Table_ID + ", AD_Column_ID=" + AD_Column_ID
				+ ": " + OldValue + " -> " + NewValue);
		try
		{
			MSPSChangeLog cl = new MSPSChangeLog(getCtx(), 
				AD_ChangeLog_ID, conn, getAD_Session_ID(),
				AD_Table_ID, AD_Column_ID, Record_ID, AD_Client_ID, AD_Org_ID,
				OldValue, NewValue, event);
			if (cl.save())
				return cl;
		}
		catch (Exception e)
		{
			LogM.log(getCtx(), MSession.class, Level.SEVERE, "AD_ChangeLog_ID=" + AD_ChangeLog_ID
					+ ", AD_Session_ID=" + getAD_Session_ID()
					+ ", AD_Table_ID=" + AD_Table_ID + ", AD_Column_ID=" + AD_Column_ID,e.getCause());
			return null;
		}
		
		LogM.log(getCtx(), MSession.class, Level.SEVERE, "AD_ChangeLog_ID=" + AD_ChangeLog_ID
				+ ", AD_Session_ID=" + getAD_Session_ID()
				+ ", AD_Table_ID=" + AD_Table_ID + ", AD_Column_ID=" + AD_Column_ID);
		
		return null;
	}	//	changeLog
	
	public static boolean logMigration(PO po, POInfo pinfo) {
		
		String [] exceptionTables = new String[] {
				"AD_ACCESSLOG",			"AD_ALERTPROCESSORLOG",		"AD_CHANGELOG",
				"AD_ISSUE",				"AD_LDAPPROCESSORLOG",		"AD_PACKAGE_IMP",
				"AD_PACKAGE_IMP_BACKUP","AD_PACKAGE_IMP_DETAIL",	"AD_PACKAGE_IMP_INST",
				"AD_PACKAGE_IMP_PROC",	"AD_PINSTANCE",				"AD_PINSTANCE_LOG",
				"AD_PINSTANCE_PARA",	"AD_REPLICATION_LOG",		"AD_SCHEDULERLOG",
				"AD_SESSION",			"AD_WORKFLOWPROCESSORLOG",	"CM_WEBACCESSLOG",
				"C_ACCTPROCESSORLOG",	"K_INDEXLOG",				"R_REQUESTPROCESSORLOG",
				"T_AGING",				"T_ALTER_COLUMN",			"T_DISTRIBUTIONRUNDETAIL",
				"T_INVENTORYVALUE",		"T_INVOICEGL",				"T_REPLENISH",
				"T_REPORT",				"T_REPORTSTATEMENT",		"T_SELECTION",
				"T_SELECTION2",			"T_SPOOL",					"T_TRANSACTION",
				"T_TRIALBALANCE",		"AD_PROCESS_ACCESS",		"AD_WINDOW_ACCESS",
				"AD_WORKFLOW_ACCESS",	"AD_FORM_ACCESS",			"SPS_CHANGELOG",
				"AD_MIGRATION",			"AD_MIGRATIONSTEP",			"AD_MIGRATIONDATA"
				//
			};
		
		List<String> list = Arrays.asList(exceptionTables);
		if ( list.contains(pinfo.getTableName().toUpperCase()) )
				return false;
		
		// ignore statistic updates
		if ( pinfo.getTableName().equalsIgnoreCase("AD_Process") && !po.isNew() && po.is_ValueChanged("Statistic_Count") )
			return false;
		
		return true;
	}

}	//	MSession

