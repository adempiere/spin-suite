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

/** Generated Model for M_Warehouse_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_Warehouse_Acct extends PO implements I_M_Warehouse_Acct
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_Warehouse_Acct (Context ctx, int M_Warehouse_Acct_ID, DB conn)
    {
      super (ctx, M_Warehouse_Acct_ID, conn);
      /** if (M_Warehouse_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setM_Warehouse_ID (0);
			setW_Differences_Acct (0);
			setW_InvActualAdjust_Acct (0);
			setW_Inventory_Acct (0);
			setW_Revaluation_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_M_Warehouse_Acct (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_Warehouse_Acct[")
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

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Warehouse Differences.
		@param W_Differences_Acct 
		Warehouse Differences Account
	  */
	public void setW_Differences_Acct (int W_Differences_Acct)
	{
		set_Value (COLUMNNAME_W_Differences_Acct, Integer.valueOf(W_Differences_Acct));
	}

	/** Get Warehouse Differences.
		@return Warehouse Differences Account
	  */
	public int getW_Differences_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Differences_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Inventory Adjustment.
		@param W_InvActualAdjust_Acct 
		Account for Inventory value adjustments for Actual Costing
	  */
	public void setW_InvActualAdjust_Acct (int W_InvActualAdjust_Acct)
	{
		set_Value (COLUMNNAME_W_InvActualAdjust_Acct, Integer.valueOf(W_InvActualAdjust_Acct));
	}

	/** Get Inventory Adjustment.
		@return Account for Inventory value adjustments for Actual Costing
	  */
	public int getW_InvActualAdjust_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_InvActualAdjust_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set (Not Used).
		@param W_Inventory_Acct 
		Warehouse Inventory Asset Account - Currently not used
	  */
	public void setW_Inventory_Acct (int W_Inventory_Acct)
	{
		set_Value (COLUMNNAME_W_Inventory_Acct, Integer.valueOf(W_Inventory_Acct));
	}

	/** Get (Not Used).
		@return Warehouse Inventory Asset Account - Currently not used
	  */
	public int getW_Inventory_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Inventory_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Inventory Revaluation.
		@param W_Revaluation_Acct 
		Account for Inventory Revaluation
	  */
	public void setW_Revaluation_Acct (int W_Revaluation_Acct)
	{
		set_Value (COLUMNNAME_W_Revaluation_Acct, Integer.valueOf(W_Revaluation_Acct));
	}

	/** Get Inventory Revaluation.
		@return Account for Inventory Revaluation
	  */
	public int getW_Revaluation_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Revaluation_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}