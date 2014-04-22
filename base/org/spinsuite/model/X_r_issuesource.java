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

/** Generated Model for r_issuesource
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_r_issuesource extends PO implements I_r_issuesource
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_r_issuesource (Context ctx, int r_issuesource_ID, DB conn)
    {
      super (ctx, r_issuesource_ID, conn);
      /** if (r_issuesource_ID == 0)
        {
			setR_IssueProject_ID (0);
			setr_issuesource_ID (0);
			setR_IssueSystem_ID (0);
			setR_IssueUser_ID (0);
        } */
    }

    /** Load Constructor */
    public X_r_issuesource (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_r_issuesource[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Issue Project.
		@param R_IssueProject_ID 
		Implementation Projects
	  */
	public void setR_IssueProject_ID (int R_IssueProject_ID)
	{
		if (R_IssueProject_ID < 1) 
			set_Value (COLUMNNAME_R_IssueProject_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueProject_ID, Integer.valueOf(R_IssueProject_ID));
	}

	/** Get Issue Project.
		@return Implementation Projects
	  */
	public int getR_IssueProject_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueProject_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set r_issuesource.
		@param r_issuesource_ID r_issuesource	  */
	public void setr_issuesource_ID (int r_issuesource_ID)
	{
		if (r_issuesource_ID < 1) 
			set_Value (COLUMNNAME_r_issuesource_ID, null);
		else 
			set_Value (COLUMNNAME_r_issuesource_ID, Integer.valueOf(r_issuesource_ID));
	}

	/** Get r_issuesource.
		@return r_issuesource	  */
	public int getr_issuesource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_r_issuesource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Issue System.
		@param R_IssueSystem_ID 
		System creating the issue
	  */
	public void setR_IssueSystem_ID (int R_IssueSystem_ID)
	{
		if (R_IssueSystem_ID < 1) 
			set_Value (COLUMNNAME_R_IssueSystem_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueSystem_ID, Integer.valueOf(R_IssueSystem_ID));
	}

	/** Get Issue System.
		@return System creating the issue
	  */
	public int getR_IssueSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IssueUser.
		@param R_IssueUser_ID 
		User who reported issues
	  */
	public void setR_IssueUser_ID (int R_IssueUser_ID)
	{
		if (R_IssueUser_ID < 1) 
			set_Value (COLUMNNAME_R_IssueUser_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueUser_ID, Integer.valueOf(R_IssueUser_ID));
	}

	/** Get IssueUser.
		@return User who reported issues
	  */
	public int getR_IssueUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueUser_ID);
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
}