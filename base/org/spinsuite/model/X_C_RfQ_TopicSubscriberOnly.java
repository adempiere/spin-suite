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

/** Generated Model for C_RfQ_TopicSubscriberOnly
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_RfQ_TopicSubscriberOnly extends PO implements I_C_RfQ_TopicSubscriberOnly
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_RfQ_TopicSubscriberOnly (Context ctx, int C_RfQ_TopicSubscriberOnly_ID, DB conn)
    {
      super (ctx, C_RfQ_TopicSubscriberOnly_ID, conn);
      /** if (C_RfQ_TopicSubscriberOnly_ID == 0)
        {
			setC_RfQ_TopicSubscriber_ID (0);
			setC_RfQ_TopicSubscriberOnly_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_RfQ_TopicSubscriberOnly (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_RfQ_TopicSubscriberOnly[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set RfQ Subscriber.
		@param C_RfQ_TopicSubscriber_ID 
		Request for Quotation Topic Subscriber
	  */
	public void setC_RfQ_TopicSubscriber_ID (int C_RfQ_TopicSubscriber_ID)
	{
		if (C_RfQ_TopicSubscriber_ID < 1) 
			set_Value (COLUMNNAME_C_RfQ_TopicSubscriber_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQ_TopicSubscriber_ID, Integer.valueOf(C_RfQ_TopicSubscriber_ID));
	}

	/** Get RfQ Subscriber.
		@return Request for Quotation Topic Subscriber
	  */
	public int getC_RfQ_TopicSubscriber_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_TopicSubscriber_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Topic Subscriber Restriction.
		@param C_RfQ_TopicSubscriberOnly_ID 
		Include Subscriber only for certain products or product categories
	  */
	public void setC_RfQ_TopicSubscriberOnly_ID (int C_RfQ_TopicSubscriberOnly_ID)
	{
		if (C_RfQ_TopicSubscriberOnly_ID < 1) 
			set_Value (COLUMNNAME_C_RfQ_TopicSubscriberOnly_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQ_TopicSubscriberOnly_ID, Integer.valueOf(C_RfQ_TopicSubscriberOnly_ID));
	}

	/** Get RfQ Topic Subscriber Restriction.
		@return Include Subscriber only for certain products or product categories
	  */
	public int getC_RfQ_TopicSubscriberOnly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_TopicSubscriberOnly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_Product_Category_ID()));
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
}