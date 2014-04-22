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

/** Generated Model for ASP_Process_Para
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_ASP_Process_Para extends PO implements I_ASP_Process_Para
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_ASP_Process_Para (Context ctx, int ASP_Process_Para_ID, DB conn)
    {
      super (ctx, ASP_Process_Para_ID, conn);
      /** if (ASP_Process_Para_ID == 0)
        {
			setASP_Status (null);
// U
        } */
    }

    /** Load Constructor */
    public X_ASP_Process_Para (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_ASP_Process_Para[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Process Parameter.
		@param AD_Process_Para_ID Process Parameter	  */
	public void setAD_Process_Para_ID (int AD_Process_Para_ID)
	{
		if (AD_Process_Para_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_Para_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_Para_ID, Integer.valueOf(AD_Process_Para_ID));
	}

	/** Get Process Parameter.
		@return Process Parameter	  */
	public int getAD_Process_Para_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_Para_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ASP Process.
		@param ASP_Process_ID ASP Process	  */
	public void setASP_Process_ID (int ASP_Process_ID)
	{
		if (ASP_Process_ID < 1) 
			set_Value (COLUMNNAME_ASP_Process_ID, null);
		else 
			set_Value (COLUMNNAME_ASP_Process_ID, Integer.valueOf(ASP_Process_ID));
	}

	/** Get ASP Process.
		@return ASP Process	  */
	public int getASP_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ASP Process Parameter.
		@param ASP_Process_Para_ID ASP Process Parameter	  */
	public void setASP_Process_Para_ID (int ASP_Process_Para_ID)
	{
		if (ASP_Process_Para_ID < 1) 
			set_Value (COLUMNNAME_ASP_Process_Para_ID, null);
		else 
			set_Value (COLUMNNAME_ASP_Process_Para_ID, Integer.valueOf(ASP_Process_Para_ID));
	}

	/** Get ASP Process Parameter.
		@return ASP Process Parameter	  */
	public int getASP_Process_Para_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Process_Para_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ASP_Status AD_Reference_ID=53234 */
	public static final int ASP_STATUS_AD_Reference_ID=53234;
	/** Hide = H */
	public static final String ASP_STATUS_Hide = "H";
	/** Show = S */
	public static final String ASP_STATUS_Show = "S";
	/** Undefined = U */
	public static final String ASP_STATUS_Undefined = "U";
	/** Set ASP Status.
		@param ASP_Status ASP Status	  */
	public void setASP_Status (String ASP_Status)
	{

		set_Value (COLUMNNAME_ASP_Status, ASP_Status);
	}

	/** Get ASP Status.
		@return ASP Status	  */
	public String getASP_Status () 
	{
		return (String)get_Value(COLUMNNAME_ASP_Status);
	}
}