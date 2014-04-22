/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.spinsuite.model;

import android.content.Context;
import android.database.Cursor;
import java.util.Date;
import org.spinsuite.base.DB;

/** Generated Model for AD_MigrationStep
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_MigrationStep extends PO implements I_AD_MigrationStep
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_MigrationStep (Context ctx, int AD_MigrationStep_ID, DB conn)
    {
      super (ctx, AD_MigrationStep_ID, conn);
      /** if (AD_MigrationStep_ID == 0)
        {
			setAD_Migration_ID (0);
			setAD_MigrationStep_ID (0);
			setSeqNo (0);
			setStepType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_MigrationStep (Context ctx, Cursor rs, DB conn)
    {
      super (ctx, rs, conn);
    }


    /** Load Meta Data */
    protected POInfo initPO (Context ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, SPS_Table_ID, get_Connection());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_MigrationStep[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Action AD_Reference_ID=53238 */
	public static final int ACTION_AD_Reference_ID=53238;
	/** Insert = I */
	public static final String ACTION_Insert = "I";
	/** Delete = D */
	public static final String ACTION_Delete = "D";
	/** Update = U */
	public static final String ACTION_Update = "U";
	/** Set Action.
		@param Action 
		Indicates the Action to be performed
	  */
	public void setAction (String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Action.
		@return Indicates the Action to be performed
	  */
	public String getAction () 
	{
		return (String)get_Value(COLUMNNAME_Action);
	}

	/** Set Migration.
		@param AD_Migration_ID 
		Migration change management.
	  */
	public void setAD_Migration_ID (int AD_Migration_ID)
	{
		if (AD_Migration_ID < 1) 
			set_Value (COLUMNNAME_AD_Migration_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Migration_ID, Integer.valueOf(AD_Migration_ID));
	}

	/** Get Migration.
		@return Migration change management.
	  */
	public int getAD_Migration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Migration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Migration step.
		@param AD_MigrationStep_ID 
		A single step in the migration process
	  */
	public void setAD_MigrationStep_ID (int AD_MigrationStep_ID)
	{
		if (AD_MigrationStep_ID < 1) 
			set_Value (COLUMNNAME_AD_MigrationStep_ID, null);
		else 
			set_Value (COLUMNNAME_AD_MigrationStep_ID, Integer.valueOf(AD_MigrationStep_ID));
	}

	/** Get Migration step.
		@return A single step in the migration process
	  */
	public int getAD_MigrationStep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_MigrationStep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Apply AD_Reference_ID=53312 */
	public static final int APPLY_AD_Reference_ID=53312;
	/** Apply = A */
	public static final String APPLY_Apply = "A";
	/** Rollback = R */
	public static final String APPLY_Rollback = "R";
	/** Set Apply.
		@param Apply 
		Apply migration
	  */
	public void setApply (String Apply)
	{

		set_Value (COLUMNNAME_Apply, Apply);
	}

	/** Get Apply.
		@return Apply migration
	  */
	public String getApply () 
	{
		return (String)get_Value(COLUMNNAME_Apply);
	}

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** DBType AD_Reference_ID=50003 */
	public static final int DBTYPE_AD_Reference_ID=50003;
	/** All Database Types = ALL */
	public static final String DBTYPE_AllDatabaseTypes = "ALL";
	/** DB2 = DB2 */
	public static final String DBTYPE_DB2 = "DB2";
	/** Firebird = Firebird */
	public static final String DBTYPE_Firebird = "Firebird";
	/** MySQL = MySQL */
	public static final String DBTYPE_MySQL = "MySQL";
	/** Oracle = Oracle */
	public static final String DBTYPE_Oracle = "Oracle";
	/** Postgres = Postgres */
	public static final String DBTYPE_Postgres = "Postgres";
	/** SQL Server = SQL */
	public static final String DBTYPE_SQLServer = "SQL";
	/** Sybase = Sybase */
	public static final String DBTYPE_Sybase = "Sybase";
	/** Set DBType.
		@param DBType DBType	  */
	public void setDBType (String DBType)
	{

		set_Value (COLUMNNAME_DBType, DBType);
	}

	/** Get DBType.
		@return DBType	  */
	public String getDBType () 
	{
		return (String)get_Value(COLUMNNAME_DBType);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rollback Statement.
		@param RollbackStatement 
		SQL statement to rollback the current step.
	  */
	public void setRollbackStatement (String RollbackStatement)
	{
		set_Value (COLUMNNAME_RollbackStatement, RollbackStatement);
	}

	/** Get Rollback Statement.
		@return SQL statement to rollback the current step.
	  */
	public String getRollbackStatement () 
	{
		return (String)get_Value(COLUMNNAME_RollbackStatement);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SQLStatement.
		@param SQLStatement SQLStatement	  */
	public void setSQLStatement (String SQLStatement)
	{
		set_Value (COLUMNNAME_SQLStatement, SQLStatement);
	}

	/** Get SQLStatement.
		@return SQLStatement	  */
	public String getSQLStatement () 
	{
		return (String)get_Value(COLUMNNAME_SQLStatement);
	}

	/** StatusCode AD_Reference_ID=53311 */
	public static final int STATUSCODE_AD_Reference_ID=53311;
	/** Applied = A */
	public static final String STATUSCODE_Applied = "A";
	/** Unapplied = U */
	public static final String STATUSCODE_Unapplied = "U";
	/** Failed = F */
	public static final String STATUSCODE_Failed = "F";
	/** Partially applied = P */
	public static final String STATUSCODE_PartiallyApplied = "P";
	/** Set Status Code.
		@param StatusCode Status Code	  */
	public void setStatusCode (String StatusCode)
	{

		set_Value (COLUMNNAME_StatusCode, StatusCode);
	}

	/** Get Status Code.
		@return Status Code	  */
	public String getStatusCode () 
	{
		return (String)get_Value(COLUMNNAME_StatusCode);
	}

	/** StepType AD_Reference_ID=53313 */
	public static final int STEPTYPE_AD_Reference_ID=53313;
	/** Application Dictionary = AD */
	public static final String STEPTYPE_ApplicationDictionary = "AD";
	/** SQL Statement = SQL */
	public static final String STEPTYPE_SQLStatement = "SQL";
	/** Set Step type.
		@param StepType 
		Migration step type
	  */
	public void setStepType (String StepType)
	{

		set_Value (COLUMNNAME_StepType, StepType);
	}

	/** Get Step type.
		@return Migration step type
	  */
	public String getStepType () 
	{
		return (String)get_Value(COLUMNNAME_StepType);
	}
}