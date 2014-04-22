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

/** Generated Model for C_RevenueRecognition_Plan
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_RevenueRecognition_Plan extends PO implements I_C_RevenueRecognition_Plan
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_RevenueRecognition_Plan (Context ctx, int C_RevenueRecognition_Plan_ID, DB conn)
    {
      super (ctx, C_RevenueRecognition_Plan_ID, conn);
      /** if (C_RevenueRecognition_Plan_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Currency_ID (0);
			setC_InvoiceLine_ID (0);
			setC_RevenueRecognition_ID (0);
			setC_RevenueRecognition_Plan_ID (0);
			setP_Revenue_Acct (0);
			setRecognizedAmt (Env.ZERO);
			setTotalAmt (Env.ZERO);
			setUnEarnedRevenue_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_RevenueRecognition_Plan (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_RevenueRecognition_Plan[")
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

	/** Set Revenue Recognition.
		@param C_RevenueRecognition_ID 
		Method for recording revenue
	  */
	public void setC_RevenueRecognition_ID (int C_RevenueRecognition_ID)
	{
		if (C_RevenueRecognition_ID < 1) 
			set_Value (COLUMNNAME_C_RevenueRecognition_ID, null);
		else 
			set_Value (COLUMNNAME_C_RevenueRecognition_ID, Integer.valueOf(C_RevenueRecognition_ID));
	}

	/** Get Revenue Recognition.
		@return Method for recording revenue
	  */
	public int getC_RevenueRecognition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RevenueRecognition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_RevenueRecognition_ID()));
    }

	/** Set Revenue Recognition Plan.
		@param C_RevenueRecognition_Plan_ID 
		Plan for recognizing or recording revenue
	  */
	public void setC_RevenueRecognition_Plan_ID (int C_RevenueRecognition_Plan_ID)
	{
		if (C_RevenueRecognition_Plan_ID < 1) 
			set_Value (COLUMNNAME_C_RevenueRecognition_Plan_ID, null);
		else 
			set_Value (COLUMNNAME_C_RevenueRecognition_Plan_ID, Integer.valueOf(C_RevenueRecognition_Plan_ID));
	}

	/** Get Revenue Recognition Plan.
		@return Plan for recognizing or recording revenue
	  */
	public int getC_RevenueRecognition_Plan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RevenueRecognition_Plan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Revenue.
		@param P_Revenue_Acct 
		Account for Product Revenue (Sales Account)
	  */
	public void setP_Revenue_Acct (int P_Revenue_Acct)
	{
		set_Value (COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
	}

	/** Get Product Revenue.
		@return Account for Product Revenue (Sales Account)
	  */
	public int getP_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Revenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Recognized Amount.
		@param RecognizedAmt Recognized Amount	  */
	public void setRecognizedAmt (BigDecimal RecognizedAmt)
	{
		set_Value (COLUMNNAME_RecognizedAmt, RecognizedAmt);
	}

	/** Get Recognized Amount.
		@return Recognized Amount	  */
	public BigDecimal getRecognizedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RecognizedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Unearned Revenue.
		@param UnEarnedRevenue_Acct 
		Account for unearned revenue
	  */
	public void setUnEarnedRevenue_Acct (int UnEarnedRevenue_Acct)
	{
		set_Value (COLUMNNAME_UnEarnedRevenue_Acct, Integer.valueOf(UnEarnedRevenue_Acct));
	}

	/** Get Unearned Revenue.
		@return Account for unearned revenue
	  */
	public int getUnEarnedRevenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnEarnedRevenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}