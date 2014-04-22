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

/** Generated Model for M_DemandLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_DemandLine extends PO implements I_M_DemandLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_DemandLine (Context ctx, int M_DemandLine_ID, DB conn)
    {
      super (ctx, M_DemandLine_ID, conn);
      /** if (M_DemandLine_ID == 0)
        {
			setC_Period_ID (0);
			setM_Demand_ID (0);
			setM_DemandLine_ID (0);
			setM_Product_ID (0);
			setQty (Env.ZERO);
			setQtyCalculated (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_DemandLine (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_DemandLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_Period_ID()));
    }

	/** Set Demand.
		@param M_Demand_ID 
		Material Demand
	  */
	public void setM_Demand_ID (int M_Demand_ID)
	{
		if (M_Demand_ID < 1) 
			set_Value (COLUMNNAME_M_Demand_ID, null);
		else 
			set_Value (COLUMNNAME_M_Demand_ID, Integer.valueOf(M_Demand_ID));
	}

	/** Get Demand.
		@return Material Demand
	  */
	public int getM_Demand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Demand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Demand Line.
		@param M_DemandLine_ID 
		Material Demand Line
	  */
	public void setM_DemandLine_ID (int M_DemandLine_ID)
	{
		if (M_DemandLine_ID < 1) 
			set_Value (COLUMNNAME_M_DemandLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_DemandLine_ID, Integer.valueOf(M_DemandLine_ID));
	}

	/** Get Demand Line.
		@return Material Demand Line
	  */
	public int getM_DemandLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DemandLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Calculated Quantity.
		@param QtyCalculated 
		Calculated Quantity
	  */
	public void setQtyCalculated (BigDecimal QtyCalculated)
	{
		set_Value (COLUMNNAME_QtyCalculated, QtyCalculated);
	}

	/** Get Calculated Quantity.
		@return Calculated Quantity
	  */
	public BigDecimal getQtyCalculated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCalculated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}