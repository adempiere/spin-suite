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

/** Generated Model for C_Project_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_Project_Acct extends PO implements I_C_Project_Acct
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_Project_Acct (Context ctx, int C_Project_Acct_ID, DB conn)
    {
      super (ctx, C_Project_Acct_ID, conn);
      /** if (C_Project_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Project_ID (0);
			setPJ_Asset_Acct (0);
			setPJ_WIP_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_Project_Acct (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_Project_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Project Asset.
		@param PJ_Asset_Acct 
		Project Asset Account
	  */
	public void setPJ_Asset_Acct (int PJ_Asset_Acct)
	{
		set_Value (COLUMNNAME_PJ_Asset_Acct, Integer.valueOf(PJ_Asset_Acct));
	}

	/** Get Project Asset.
		@return Project Asset Account
	  */
	public int getPJ_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PJ_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Work In Progress.
		@param PJ_WIP_Acct 
		Account for Work in Progress
	  */
	public void setPJ_WIP_Acct (int PJ_WIP_Acct)
	{
		set_Value (COLUMNNAME_PJ_WIP_Acct, Integer.valueOf(PJ_WIP_Acct));
	}

	/** Get Work In Progress.
		@return Account for Work in Progress
	  */
	public int getPJ_WIP_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PJ_WIP_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}