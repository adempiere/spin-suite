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

/** Generated Model for GL_BudgetControl
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_GL_BudgetControl extends PO implements I_GL_BudgetControl
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_GL_BudgetControl (Context ctx, int GL_BudgetControl_ID, DB conn)
    {
      super (ctx, GL_BudgetControl_ID, conn);
      /** if (GL_BudgetControl_ID == 0)
        {
			setBudgetControlScope (null);
			setC_AcctSchema_ID (0);
			setCommitmentType (null);
// C
			setGL_BudgetControl_ID (0);
			setGL_Budget_ID (0);
			setIsBeforeApproval (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_GL_BudgetControl (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_GL_BudgetControl[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** BudgetControlScope AD_Reference_ID=361 */
	public static final int BUDGETCONTROLSCOPE_AD_Reference_ID=361;
	/** Period only = P */
	public static final String BUDGETCONTROLSCOPE_PeriodOnly = "P";
	/** Year To Date = Y */
	public static final String BUDGETCONTROLSCOPE_YearToDate = "Y";
	/** Total = T */
	public static final String BUDGETCONTROLSCOPE_Total = "T";
	/** Set Control Scope.
		@param BudgetControlScope 
		Scope of the Budget Control
	  */
	public void setBudgetControlScope (String BudgetControlScope)
	{

		set_Value (COLUMNNAME_BudgetControlScope, BudgetControlScope);
	}

	/** Get Control Scope.
		@return Scope of the Budget Control
	  */
	public String getBudgetControlScope () 
	{
		return (String)get_Value(COLUMNNAME_BudgetControlScope);
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

	/** CommitmentType AD_Reference_ID=359 */
	public static final int COMMITMENTTYPE_AD_Reference_ID=359;
	/** PO Commitment only = C */
	public static final String COMMITMENTTYPE_POCommitmentOnly = "C";
	/** PO Commitment & Reservation = B */
	public static final String COMMITMENTTYPE_POCommitmentReservation = "B";
	/** None = N */
	public static final String COMMITMENTTYPE_None = "N";
	/** PO/SO Commitment & Reservation = A */
	public static final String COMMITMENTTYPE_POSOCommitmentReservation = "A";
	/** SO Commitment only = S */
	public static final String COMMITMENTTYPE_SOCommitmentOnly = "S";
	/** PO/SO Commitment = O */
	public static final String COMMITMENTTYPE_POSOCommitment = "O";
	/** Set Commitment Type.
		@param CommitmentType 
		Create Commitment and/or Reservations for Budget Control
	  */
	public void setCommitmentType (String CommitmentType)
	{

		set_Value (COLUMNNAME_CommitmentType, CommitmentType);
	}

	/** Get Commitment Type.
		@return Create Commitment and/or Reservations for Budget Control
	  */
	public String getCommitmentType () 
	{
		return (String)get_Value(COLUMNNAME_CommitmentType);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Budget Control.
		@param GL_BudgetControl_ID 
		Budget Control
	  */
	public void setGL_BudgetControl_ID (int GL_BudgetControl_ID)
	{
		if (GL_BudgetControl_ID < 1) 
			set_Value (COLUMNNAME_GL_BudgetControl_ID, null);
		else 
			set_Value (COLUMNNAME_GL_BudgetControl_ID, Integer.valueOf(GL_BudgetControl_ID));
	}

	/** Get Budget Control.
		@return Budget Control
	  */
	public int getGL_BudgetControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_BudgetControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Budget.
		@param GL_Budget_ID 
		General Ledger Budget
	  */
	public void setGL_Budget_ID (int GL_Budget_ID)
	{
		if (GL_Budget_ID < 1) 
			set_Value (COLUMNNAME_GL_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Budget_ID, Integer.valueOf(GL_Budget_ID));
	}

	/** Get Budget.
		@return General Ledger Budget
	  */
	public int getGL_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Before Approval.
		@param IsBeforeApproval 
		The Check is before the (manual) approval
	  */
	public void setIsBeforeApproval (boolean IsBeforeApproval)
	{
		set_Value (COLUMNNAME_IsBeforeApproval, Boolean.valueOf(IsBeforeApproval));
	}

	/** Get Before Approval.
		@return The Check is before the (manual) approval
	  */
	public boolean isBeforeApproval () 
	{
		Object oo = get_Value(COLUMNNAME_IsBeforeApproval);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }
}