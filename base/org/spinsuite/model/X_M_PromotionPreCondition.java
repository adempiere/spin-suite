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
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;

/** Generated Model for M_PromotionPreCondition
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_PromotionPreCondition extends PO implements I_M_PromotionPreCondition
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_PromotionPreCondition (Context ctx, int M_PromotionPreCondition_ID, DB conn)
    {
      super (ctx, M_PromotionPreCondition_ID, conn);
      /** if (M_PromotionPreCondition_ID == 0)
        {
			setM_Promotion_ID (0);
			setM_PromotionPreCondition_ID (0);
			setSeqNo (0);
// 0
			setStartDate (new Date( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_M_PromotionPreCondition (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_PromotionPreCondition[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Date EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Date getEndDate () 
	{
		return (Date)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
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

	/** Set Promotion Pre Condition.
		@param M_PromotionPreCondition_ID 
		Pre condition for promotion
	  */
	public void setM_PromotionPreCondition_ID (int M_PromotionPreCondition_ID)
	{
		if (M_PromotionPreCondition_ID < 1) 
			set_Value (COLUMNNAME_M_PromotionPreCondition_ID, null);
		else 
			set_Value (COLUMNNAME_M_PromotionPreCondition_ID, Integer.valueOf(M_PromotionPreCondition_ID));
	}

	/** Get Promotion Pre Condition.
		@return Pre condition for promotion
	  */
	public int getM_PromotionPreCondition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionPreCondition_ID);
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

	/** Set Promotion Code.
		@param PromotionCode 
		User entered promotion code at sales time
	  */
	public void setPromotionCode (String PromotionCode)
	{
		set_Value (COLUMNNAME_PromotionCode, PromotionCode);
	}

	/** Get Promotion Code.
		@return User entered promotion code at sales time
	  */
	public String getPromotionCode () 
	{
		return (String)get_Value(COLUMNNAME_PromotionCode);
	}

	/** Set Usage Counter.
		@param PromotionCounter 
		Usage counter
	  */
	public void setPromotionCounter (int PromotionCounter)
	{
		set_Value (COLUMNNAME_PromotionCounter, Integer.valueOf(PromotionCounter));
	}

	/** Get Usage Counter.
		@return Usage counter
	  */
	public int getPromotionCounter () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PromotionCounter);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Usage Limit.
		@param PromotionUsageLimit 
		Maximum usage limit
	  */
	public void setPromotionUsageLimit (int PromotionUsageLimit)
	{
		set_Value (COLUMNNAME_PromotionUsageLimit, Integer.valueOf(PromotionUsageLimit));
	}

	/** Get Usage Limit.
		@return Maximum usage limit
	  */
	public int getPromotionUsageLimit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PromotionUsageLimit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Date StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Date getStartDate () 
	{
		return (Date)get_Value(COLUMNNAME_StartDate);
	}
}