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

/** Generated Model for C_CommissionDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_CommissionDetail extends PO implements I_C_CommissionDetail
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_CommissionDetail (Context ctx, int C_CommissionDetail_ID, DB conn)
    {
      super (ctx, C_CommissionDetail_ID, conn);
      /** if (C_CommissionDetail_ID == 0)
        {
			setActualAmt (Env.ZERO);
			setActualQty (Env.ZERO);
			setC_CommissionAmt_ID (0);
			setC_CommissionDetail_ID (0);
			setC_Currency_ID (0);
			setConvertedAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_CommissionDetail (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_CommissionDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Actual Amount.
		@param ActualAmt 
		The actual amount
	  */
	public void setActualAmt (BigDecimal ActualAmt)
	{
		set_Value (COLUMNNAME_ActualAmt, ActualAmt);
	}

	/** Get Actual Amount.
		@return The actual amount
	  */
	public BigDecimal getActualAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Quantity.
		@param ActualQty 
		The actual quantity
	  */
	public void setActualQty (BigDecimal ActualQty)
	{
		set_Value (COLUMNNAME_ActualQty, ActualQty);
	}

	/** Get Actual Quantity.
		@return The actual quantity
	  */
	public BigDecimal getActualQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Commission Amount.
		@param C_CommissionAmt_ID 
		Generated Commission Amount 
	  */
	public void setC_CommissionAmt_ID (int C_CommissionAmt_ID)
	{
		if (C_CommissionAmt_ID < 1) 
			set_Value (COLUMNNAME_C_CommissionAmt_ID, null);
		else 
			set_Value (COLUMNNAME_C_CommissionAmt_ID, Integer.valueOf(C_CommissionAmt_ID));
	}

	/** Get Commission Amount.
		@return Generated Commission Amount 
	  */
	public int getC_CommissionAmt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CommissionAmt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Commission Detail.
		@param C_CommissionDetail_ID 
		Supporting information for Commission Amounts
	  */
	public void setC_CommissionDetail_ID (int C_CommissionDetail_ID)
	{
		if (C_CommissionDetail_ID < 1) 
			set_Value (COLUMNNAME_C_CommissionDetail_ID, null);
		else 
			set_Value (COLUMNNAME_C_CommissionDetail_ID, Integer.valueOf(C_CommissionDetail_ID));
	}

	/** Get Commission Detail.
		@return Supporting information for Commission Amounts
	  */
	public int getC_CommissionDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CommissionDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Converted Amount.
		@param ConvertedAmt 
		Converted Amount
	  */
	public void setConvertedAmt (BigDecimal ConvertedAmt)
	{
		set_Value (COLUMNNAME_ConvertedAmt, ConvertedAmt);
	}

	/** Get Converted Amount.
		@return Converted Amount
	  */
	public BigDecimal getConvertedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConvertedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Info.
		@param Info 
		Information
	  */
	public void setInfo (String Info)
	{
		set_Value (COLUMNNAME_Info, Info);
	}

	/** Get Info.
		@return Information
	  */
	public String getInfo () 
	{
		return (String)get_Value(COLUMNNAME_Info);
	}

	/** Set Reference.
		@param Reference 
		Reference for this record
	  */
	public void setReference (String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	/** Get Reference.
		@return Reference for this record
	  */
	public String getReference () 
	{
		return (String)get_Value(COLUMNNAME_Reference);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getReference());
    }
}