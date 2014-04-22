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
import org.spinsuite.util.KeyNamePair;

/** Generated Model for R_IssueSystem
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_R_IssueSystem extends PO implements I_R_IssueSystem
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_R_IssueSystem (Context ctx, int R_IssueSystem_ID, DB conn)
    {
      super (ctx, R_IssueSystem_ID, conn);
      /** if (R_IssueSystem_ID == 0)
        {
			setDBAddress (null);
			setR_IssueSystem_ID (0);
			setSystemStatus (null);
        } */
    }

    /** Load Constructor */
    public X_R_IssueSystem (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_R_IssueSystem[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Address.
		@param DBAddress 
		JDBC URL of the database server
	  */
	public void setDBAddress (String DBAddress)
	{
		set_Value (COLUMNNAME_DBAddress, DBAddress);
	}

	/** Get DB Address.
		@return JDBC URL of the database server
	  */
	public String getDBAddress () 
	{
		return (String)get_Value(COLUMNNAME_DBAddress);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDBAddress());
    }

	/** Set Profile.
		@param ProfileInfo 
		Information to help profiling the system for solving support issues
	  */
	public void setProfileInfo (String ProfileInfo)
	{
		set_Value (COLUMNNAME_ProfileInfo, ProfileInfo);
	}

	/** Get Profile.
		@return Information to help profiling the system for solving support issues
	  */
	public String getProfileInfo () 
	{
		return (String)get_Value(COLUMNNAME_ProfileInfo);
	}

	/** Set Issue System.
		@param R_IssueSystem_ID Issue System	  */
	public void setR_IssueSystem_ID (int R_IssueSystem_ID)
	{
		if (R_IssueSystem_ID < 1) 
			set_Value (COLUMNNAME_R_IssueSystem_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueSystem_ID, Integer.valueOf(R_IssueSystem_ID));
	}

	/** Get Issue System.
		@return Issue System	  */
	public int getR_IssueSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Statistics.
		@param StatisticsInfo 
		Information to help profiling the system for solving support issues
	  */
	public void setStatisticsInfo (String StatisticsInfo)
	{
		set_Value (COLUMNNAME_StatisticsInfo, StatisticsInfo);
	}

	/** Get Statistics.
		@return Information to help profiling the system for solving support issues
	  */
	public String getStatisticsInfo () 
	{
		return (String)get_Value(COLUMNNAME_StatisticsInfo);
	}

	/** SystemStatus AD_Reference_ID=374 */
	public static final int SYSTEMSTATUS_AD_Reference_ID=374;
	/** Evaluation = E */
	public static final String SYSTEMSTATUS_Evaluation = "E";
	/** Implementation = I */
	public static final String SYSTEMSTATUS_Implementation = "I";
	/** Production = P */
	public static final String SYSTEMSTATUS_Production = "P";
	/** Set System Status.
		@param SystemStatus 
		Status of the system - Support priority depends on system status
	  */
	public void setSystemStatus (String SystemStatus)
	{

		set_Value (COLUMNNAME_SystemStatus, SystemStatus);
	}

	/** Get System Status.
		@return Status of the system - Support priority depends on system status
	  */
	public String getSystemStatus () 
	{
		return (String)get_Value(COLUMNNAME_SystemStatus);
	}
}