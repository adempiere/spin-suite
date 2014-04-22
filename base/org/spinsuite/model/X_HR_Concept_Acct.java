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

/** Generated Model for HR_Concept_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_HR_Concept_Acct extends PO implements I_HR_Concept_Acct
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_HR_Concept_Acct (Context ctx, int HR_Concept_Acct_ID, DB conn)
    {
      super (ctx, HR_Concept_Acct_ID, conn);
      /** if (HR_Concept_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setHR_Concept_Acct_ID (0);
			setHR_Concept_ID (0);
			setHR_Expense_Acct (0);
			setHR_Revenue_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_HR_Concept_Acct (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_HR_Concept_Acct[")
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

	/** Set Payroll Concept Account.
		@param HR_Concept_Acct_ID Payroll Concept Account	  */
	public void setHR_Concept_Acct_ID (int HR_Concept_Acct_ID)
	{
		if (HR_Concept_Acct_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_Acct_ID, Integer.valueOf(HR_Concept_Acct_ID));
	}

	/** Get Payroll Concept Account.
		@return Payroll Concept Account	  */
	public int getHR_Concept_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Concept.
		@param HR_Concept_ID Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Expense Account.
		@param HR_Expense_Acct Payroll Expense Account	  */
	public void setHR_Expense_Acct (int HR_Expense_Acct)
	{
		set_Value (COLUMNNAME_HR_Expense_Acct, Integer.valueOf(HR_Expense_Acct));
	}

	/** Get Payroll Expense Account.
		@return Payroll Expense Account	  */
	public int getHR_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Revenue Account.
		@param HR_Revenue_Acct Payroll Revenue Account	  */
	public void setHR_Revenue_Acct (int HR_Revenue_Acct)
	{
		set_Value (COLUMNNAME_HR_Revenue_Acct, Integer.valueOf(HR_Revenue_Acct));
	}

	/** Get Payroll Revenue Account.
		@return Payroll Revenue Account	  */
	public int getHR_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Revenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Balancing.
		@param IsBalancing 
		All transactions within an element value must balance (e.g. cost centers)
	  */
	public void setIsBalancing (boolean IsBalancing)
	{
		set_Value (COLUMNNAME_IsBalancing, Boolean.valueOf(IsBalancing));
	}

	/** Get Balancing.
		@return All transactions within an element value must balance (e.g. cost centers)
	  */
	public boolean isBalancing () 
	{
		Object oo = get_Value(COLUMNNAME_IsBalancing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set User List 1.
		@param User1_ID 
		User defined list element #1
	  */
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get User List 1.
		@return User defined list element #1
	  */
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User List 2.
		@param User2_ID 
		User defined list element #2
	  */
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get User List 2.
		@return User defined list element #2
	  */
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}