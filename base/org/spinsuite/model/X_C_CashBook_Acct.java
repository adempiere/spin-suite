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

/** Generated Model for C_CashBook_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_CashBook_Acct extends PO implements I_C_CashBook_Acct
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_CashBook_Acct (Context ctx, int C_CashBook_Acct_ID, DB conn)
    {
      super (ctx, C_CashBook_Acct_ID, conn);
      /** if (C_CashBook_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setCB_Asset_Acct (0);
			setCB_CashTransfer_Acct (0);
			setCB_Differences_Acct (0);
			setCB_Expense_Acct (0);
			setCB_Receipt_Acct (0);
			setC_CashBook_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_CashBook_Acct (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_CashBook_Acct[")
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

	/** Set Cash Book Asset.
		@param CB_Asset_Acct 
		Cash Book Asset Account
	  */
	public void setCB_Asset_Acct (int CB_Asset_Acct)
	{
		set_Value (COLUMNNAME_CB_Asset_Acct, Integer.valueOf(CB_Asset_Acct));
	}

	/** Get Cash Book Asset.
		@return Cash Book Asset Account
	  */
	public int getCB_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cash Transfer.
		@param CB_CashTransfer_Acct 
		Cash Transfer Clearing Account
	  */
	public void setCB_CashTransfer_Acct (int CB_CashTransfer_Acct)
	{
		set_Value (COLUMNNAME_CB_CashTransfer_Acct, Integer.valueOf(CB_CashTransfer_Acct));
	}

	/** Get Cash Transfer.
		@return Cash Transfer Clearing Account
	  */
	public int getCB_CashTransfer_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_CashTransfer_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cash Book Differences.
		@param CB_Differences_Acct 
		Cash Book Differences Account
	  */
	public void setCB_Differences_Acct (int CB_Differences_Acct)
	{
		set_Value (COLUMNNAME_CB_Differences_Acct, Integer.valueOf(CB_Differences_Acct));
	}

	/** Get Cash Book Differences.
		@return Cash Book Differences Account
	  */
	public int getCB_Differences_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Differences_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cash Book Expense.
		@param CB_Expense_Acct 
		Cash Book Expense Account
	  */
	public void setCB_Expense_Acct (int CB_Expense_Acct)
	{
		set_Value (COLUMNNAME_CB_Expense_Acct, Integer.valueOf(CB_Expense_Acct));
	}

	/** Get Cash Book Expense.
		@return Cash Book Expense Account
	  */
	public int getCB_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cash Book Receipt.
		@param CB_Receipt_Acct 
		Cash Book Receipts Account
	  */
	public void setCB_Receipt_Acct (int CB_Receipt_Acct)
	{
		set_Value (COLUMNNAME_CB_Receipt_Acct, Integer.valueOf(CB_Receipt_Acct));
	}

	/** Get Cash Book Receipt.
		@return Cash Book Receipts Account
	  */
	public int getCB_Receipt_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Receipt_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cash Book.
		@param C_CashBook_ID 
		Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1) 
			set_Value (COLUMNNAME_C_CashBook_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
	}

	/** Get Cash Book.
		@return Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}