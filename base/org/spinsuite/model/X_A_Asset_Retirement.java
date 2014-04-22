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
import java.math.BigDecimal;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for A_Asset_Retirement
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_A_Asset_Retirement extends PO implements I_A_Asset_Retirement
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_A_Asset_Retirement (Context ctx, int A_Asset_Retirement_ID, DB conn)
    {
      super (ctx, A_Asset_Retirement_ID, conn);
      /** if (A_Asset_Retirement_ID == 0)
        {
			setA_Asset_ID (0);
			setA_Asset_Retirement_ID (0);
			setAssetMarketValueAmt (Env.ZERO);
			setAssetValueAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Retirement (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Retirement[")
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

	/** Set Asset Retirement.
		@param A_Asset_Retirement_ID 
		Internally used asset is not longer used.
	  */
	public void setA_Asset_Retirement_ID (int A_Asset_Retirement_ID)
	{
		if (A_Asset_Retirement_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Retirement_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Retirement_ID, Integer.valueOf(A_Asset_Retirement_ID));
	}

	/** Get Asset Retirement.
		@return Internally used asset is not longer used.
	  */
	public int getA_Asset_Retirement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Retirement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Asset_Retirement_ID()));
    }

	/** Set Market value Amount.
		@param AssetMarketValueAmt 
		Market value of the asset
	  */
	public void setAssetMarketValueAmt (BigDecimal AssetMarketValueAmt)
	{
		set_Value (COLUMNNAME_AssetMarketValueAmt, AssetMarketValueAmt);
	}

	/** Get Market value Amount.
		@return Market value of the asset
	  */
	public BigDecimal getAssetMarketValueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssetMarketValueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Asset value.
		@param AssetValueAmt 
		Book Value of the asset
	  */
	public void setAssetValueAmt (BigDecimal AssetValueAmt)
	{
		set_Value (COLUMNNAME_AssetValueAmt, AssetValueAmt);
	}

	/** Get Asset value.
		@return Book Value of the asset
	  */
	public BigDecimal getAssetValueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssetValueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}