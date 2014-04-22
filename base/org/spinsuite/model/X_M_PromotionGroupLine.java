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

/** Generated Model for M_PromotionGroupLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_PromotionGroupLine extends PO implements I_M_PromotionGroupLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_PromotionGroupLine (Context ctx, int M_PromotionGroupLine_ID, DB conn)
    {
      super (ctx, M_PromotionGroupLine_ID, conn);
      /** if (M_PromotionGroupLine_ID == 0)
        {
			setM_Product_ID (0);
			setM_PromotionGroup_ID (0);
			setM_PromotionGroupLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PromotionGroupLine (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_PromotionGroupLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Promotion Group Line.
		@param M_PromotionGroupLine_ID 
		Line items of a promotion group
	  */
	public void setM_PromotionGroupLine_ID (int M_PromotionGroupLine_ID)
	{
		if (M_PromotionGroupLine_ID < 1) 
			set_Value (COLUMNNAME_M_PromotionGroupLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_PromotionGroupLine_ID, Integer.valueOf(M_PromotionGroupLine_ID));
	}

	/** Get Promotion Group Line.
		@return Line items of a promotion group
	  */
	public int getM_PromotionGroupLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionGroupLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}