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

/** Generated Model for C_RfQResponseLineQty
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_RfQResponseLineQty extends PO implements I_C_RfQResponseLineQty
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_RfQResponseLineQty (Context ctx, int C_RfQResponseLineQty_ID, DB conn)
    {
      super (ctx, C_RfQResponseLineQty_ID, conn);
      /** if (C_RfQResponseLineQty_ID == 0)
        {
			setC_RfQLineQty_ID (0);
			setC_RfQResponseLine_ID (0);
			setC_RfQResponseLineQty_ID (0);
			setPrice (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_RfQResponseLineQty (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_RfQResponseLineQty[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set RfQ Line Quantity.
		@param C_RfQLineQty_ID 
		Request for Quotation Line Quantity
	  */
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID)
	{
		if (C_RfQLineQty_ID < 1) 
			set_Value (COLUMNNAME_C_RfQLineQty_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQLineQty_ID, Integer.valueOf(C_RfQLineQty_ID));
	}

	/** Get RfQ Line Quantity.
		@return Request for Quotation Line Quantity
	  */
	public int getC_RfQLineQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLineQty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Response Line.
		@param C_RfQResponseLine_ID 
		Request for Quotation Response Line
	  */
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID)
	{
		if (C_RfQResponseLine_ID < 1) 
			set_Value (COLUMNNAME_C_RfQResponseLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQResponseLine_ID, Integer.valueOf(C_RfQResponseLine_ID));
	}

	/** Get RfQ Response Line.
		@return Request for Quotation Response Line
	  */
	public int getC_RfQResponseLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponseLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_RfQResponseLine_ID()));
    }

	/** Set RfQ Response Line Qty.
		@param C_RfQResponseLineQty_ID 
		Request for Quotation Response Line Quantity
	  */
	public void setC_RfQResponseLineQty_ID (int C_RfQResponseLineQty_ID)
	{
		if (C_RfQResponseLineQty_ID < 1) 
			set_Value (COLUMNNAME_C_RfQResponseLineQty_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQResponseLineQty_ID, Integer.valueOf(C_RfQResponseLineQty_ID));
	}

	/** Get RfQ Response Line Qty.
		@return Request for Quotation Response Line Quantity
	  */
	public int getC_RfQResponseLineQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponseLineQty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ranking.
		@param Ranking 
		Relative Rank Number
	  */
	public void setRanking (int Ranking)
	{
		set_Value (COLUMNNAME_Ranking, Integer.valueOf(Ranking));
	}

	/** Get Ranking.
		@return Relative Rank Number
	  */
	public int getRanking () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ranking);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}