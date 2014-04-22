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

/** Generated Model for M_PromotionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_PromotionLine extends PO implements I_M_PromotionLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_PromotionLine (Context ctx, int M_PromotionLine_ID, DB conn)
    {
      super (ctx, M_PromotionLine_ID, conn);
      /** if (M_PromotionLine_ID == 0)
        {
			setIsMandatoryPL (true);
// Y
			setM_Promotion_ID (0);
			setM_PromotionLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PromotionLine (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_PromotionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Mandatory Promotion Line.
		@param IsMandatoryPL 
		Order must have this promotion line
	  */
	public void setIsMandatoryPL (boolean IsMandatoryPL)
	{
		set_Value (COLUMNNAME_IsMandatoryPL, Boolean.valueOf(IsMandatoryPL));
	}

	/** Get Mandatory Promotion Line.
		@return Order must have this promotion line
	  */
	public boolean isMandatoryPL () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatoryPL);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Minimum Amt.
		@param MinimumAmt 
		Minimum Amount in Document Currency
	  */
	public void setMinimumAmt (BigDecimal MinimumAmt)
	{
		set_Value (COLUMNNAME_MinimumAmt, MinimumAmt);
	}

	/** Get Minimum Amt.
		@return Minimum Amount in Document Currency
	  */
	public BigDecimal getMinimumAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinimumAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Promotion Group.
		@param M_PromotionGroup_ID Promotion Group	  */
	public void setM_PromotionGroup_ID (int M_PromotionGroup_ID)
	{
		if (M_PromotionGroup_ID < 1) 
			set_Value (COLUMNNAME_M_PromotionGroup_ID, null);
		else 
			set_Value (COLUMNNAME_M_PromotionGroup_ID, Integer.valueOf(M_PromotionGroup_ID));
	}

	/** Get Promotion Group.
		@return Promotion Group	  */
	public int getM_PromotionGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Promotion.
		@param M_Promotion_ID Promotion	  */
	public void setM_Promotion_ID (int M_Promotion_ID)
	{
		if (M_Promotion_ID < 1) 
			set_Value (COLUMNNAME_M_Promotion_ID, null);
		else 
			set_Value (COLUMNNAME_M_Promotion_ID, Integer.valueOf(M_Promotion_ID));
	}

	/** Get Promotion.
		@return Promotion	  */
	public int getM_Promotion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Promotion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Promotion Line.
		@param M_PromotionLine_ID 
		Promotion line items
	  */
	public void setM_PromotionLine_ID (int M_PromotionLine_ID)
	{
		if (M_PromotionLine_ID < 1) 
			set_Value (COLUMNNAME_M_PromotionLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_PromotionLine_ID, Integer.valueOf(M_PromotionLine_ID));
	}

	/** Get Promotion Line.
		@return Promotion line items
	  */
	public int getM_PromotionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}