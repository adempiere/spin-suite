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

/** Generated Model for C_BP_Group_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_BP_Group_Acct extends PO implements I_C_BP_Group_Acct
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_BP_Group_Acct (Context ctx, int C_BP_Group_Acct_ID, DB conn)
    {
      super (ctx, C_BP_Group_Acct_ID, conn);
      /** if (C_BP_Group_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_BP_Group_ID (0);
			setC_Prepayment_Acct (0);
			setC_Receivable_Acct (0);
			setC_Receivable_Services_Acct (0);
			setNotInvoicedReceipts_Acct (0);
			setNotInvoicedReceivables_Acct (0);
			setNotInvoicedRevenue_Acct (0);
			setPayDiscount_Exp_Acct (0);
			setPayDiscount_Rev_Acct (0);
			setUnEarnedRevenue_Acct (0);
			setV_Liability_Acct (0);
			setV_Liability_Services_Acct (0);
			setV_Prepayment_Acct (0);
			setWriteOff_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_BP_Group_Acct (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_BP_Group_Acct[")
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

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_BP_Group_ID()));
    }

	/** Set Customer Prepayment.
		@param C_Prepayment_Acct 
		Account for customer prepayments
	  */
	public void setC_Prepayment_Acct (int C_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_C_Prepayment_Acct, Integer.valueOf(C_Prepayment_Acct));
	}

	/** Get Customer Prepayment.
		@return Account for customer prepayments
	  */
	public int getC_Prepayment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Prepayment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer Receivables.
		@param C_Receivable_Acct 
		Account for Customer Receivables
	  */
	public void setC_Receivable_Acct (int C_Receivable_Acct)
	{
		set_Value (COLUMNNAME_C_Receivable_Acct, Integer.valueOf(C_Receivable_Acct));
	}

	/** Get Customer Receivables.
		@return Account for Customer Receivables
	  */
	public int getC_Receivable_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Receivable_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Receivable Services.
		@param C_Receivable_Services_Acct 
		Customer Accounts Receivables Services Account
	  */
	public void setC_Receivable_Services_Acct (int C_Receivable_Services_Acct)
	{
		set_Value (COLUMNNAME_C_Receivable_Services_Acct, Integer.valueOf(C_Receivable_Services_Acct));
	}

	/** Get Receivable Services.
		@return Customer Accounts Receivables Services Account
	  */
	public int getC_Receivable_Services_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Receivable_Services_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Not-invoiced Receipts.
		@param NotInvoicedReceipts_Acct 
		Account for not-invoiced Material Receipts
	  */
	public void setNotInvoicedReceipts_Acct (int NotInvoicedReceipts_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedReceipts_Acct, Integer.valueOf(NotInvoicedReceipts_Acct));
	}

	/** Get Not-invoiced Receipts.
		@return Account for not-invoiced Material Receipts
	  */
	public int getNotInvoicedReceipts_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotInvoicedReceipts_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Not-invoiced Receivables.
		@param NotInvoicedReceivables_Acct 
		Account for not invoiced Receivables
	  */
	public void setNotInvoicedReceivables_Acct (int NotInvoicedReceivables_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedReceivables_Acct, Integer.valueOf(NotInvoicedReceivables_Acct));
	}

	/** Get Not-invoiced Receivables.
		@return Account for not invoiced Receivables
	  */
	public int getNotInvoicedReceivables_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotInvoicedReceivables_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Not-invoiced Revenue.
		@param NotInvoicedRevenue_Acct 
		Account for not invoiced Revenue
	  */
	public void setNotInvoicedRevenue_Acct (int NotInvoicedRevenue_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedRevenue_Acct, Integer.valueOf(NotInvoicedRevenue_Acct));
	}

	/** Get Not-invoiced Revenue.
		@return Account for not invoiced Revenue
	  */
	public int getNotInvoicedRevenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotInvoicedRevenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Discount Expense.
		@param PayDiscount_Exp_Acct 
		Payment Discount Expense Account
	  */
	public void setPayDiscount_Exp_Acct (int PayDiscount_Exp_Acct)
	{
		set_Value (COLUMNNAME_PayDiscount_Exp_Acct, Integer.valueOf(PayDiscount_Exp_Acct));
	}

	/** Get Payment Discount Expense.
		@return Payment Discount Expense Account
	  */
	public int getPayDiscount_Exp_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayDiscount_Exp_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Discount Revenue.
		@param PayDiscount_Rev_Acct 
		Payment Discount Revenue Account
	  */
	public void setPayDiscount_Rev_Acct (int PayDiscount_Rev_Acct)
	{
		set_Value (COLUMNNAME_PayDiscount_Rev_Acct, Integer.valueOf(PayDiscount_Rev_Acct));
	}

	/** Get Payment Discount Revenue.
		@return Payment Discount Revenue Account
	  */
	public int getPayDiscount_Rev_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayDiscount_Rev_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Vendor Liability.
		@param V_Liability_Acct 
		Account for Vendor Liability
	  */
	public void setV_Liability_Acct (int V_Liability_Acct)
	{
		set_Value (COLUMNNAME_V_Liability_Acct, Integer.valueOf(V_Liability_Acct));
	}

	/** Get Vendor Liability.
		@return Account for Vendor Liability
	  */
	public int getV_Liability_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_V_Liability_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vendor Service Liability.
		@param V_Liability_Services_Acct 
		Account for Vendor Service Liability
	  */
	public void setV_Liability_Services_Acct (int V_Liability_Services_Acct)
	{
		set_Value (COLUMNNAME_V_Liability_Services_Acct, Integer.valueOf(V_Liability_Services_Acct));
	}

	/** Get Vendor Service Liability.
		@return Account for Vendor Service Liability
	  */
	public int getV_Liability_Services_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_V_Liability_Services_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vendor Prepayment.
		@param V_Prepayment_Acct 
		Account for Vendor Prepayments
	  */
	public void setV_Prepayment_Acct (int V_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_V_Prepayment_Acct, Integer.valueOf(V_Prepayment_Acct));
	}

	/** Get Vendor Prepayment.
		@return Account for Vendor Prepayments
	  */
	public int getV_Prepayment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_V_Prepayment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Write-off.
		@param WriteOff_Acct 
		Account for Receivables write-off
	  */
	public void setWriteOff_Acct (int WriteOff_Acct)
	{
		set_Value (COLUMNNAME_WriteOff_Acct, Integer.valueOf(WriteOff_Acct));
	}

	/** Get Write-off.
		@return Account for Receivables write-off
	  */
	public int getWriteOff_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WriteOff_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}