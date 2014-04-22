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
import org.spinsuite.util.KeyNamePair;

/** Generated Model for C_Subscription
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_Subscription extends PO implements I_C_Subscription
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_Subscription (Context ctx, int C_Subscription_ID, DB conn)
    {
      super (ctx, C_Subscription_ID, conn);
      /** if (C_Subscription_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Subscription_ID (0);
			setC_SubscriptionType_ID (0);
			setIsDue (false);
			setM_Product_ID (0);
			setName (null);
			setPaidUntilDate (new Date( System.currentTimeMillis() ));
			setRenewalDate (new Date( System.currentTimeMillis() ));
			setStartDate (new Date( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_Subscription (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_Subscription[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Subscription.
		@param C_Subscription_ID 
		Subscription of a Business Partner of a Product to renew
	  */
	public void setC_Subscription_ID (int C_Subscription_ID)
	{
		if (C_Subscription_ID < 1) 
			set_Value (COLUMNNAME_C_Subscription_ID, null);
		else 
			set_Value (COLUMNNAME_C_Subscription_ID, Integer.valueOf(C_Subscription_ID));
	}

	/** Get Subscription.
		@return Subscription of a Business Partner of a Product to renew
	  */
	public int getC_Subscription_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Subscription_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Subscription Type.
		@param C_SubscriptionType_ID 
		Type of subscription
	  */
	public void setC_SubscriptionType_ID (int C_SubscriptionType_ID)
	{
		if (C_SubscriptionType_ID < 1) 
			set_Value (COLUMNNAME_C_SubscriptionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_SubscriptionType_ID, Integer.valueOf(C_SubscriptionType_ID));
	}

	/** Get Subscription Type.
		@return Type of subscription
	  */
	public int getC_SubscriptionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SubscriptionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Due.
		@param IsDue 
		Subscription Renewal is Due
	  */
	public void setIsDue (boolean IsDue)
	{
		set_Value (COLUMNNAME_IsDue, Boolean.valueOf(IsDue));
	}

	/** Get Due.
		@return Subscription Renewal is Due
	  */
	public boolean isDue () 
	{
		Object oo = get_Value(COLUMNNAME_IsDue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Paid Until.
		@param PaidUntilDate 
		Subscription is paid/valid until this date
	  */
	public void setPaidUntilDate (Date PaidUntilDate)
	{
		set_Value (COLUMNNAME_PaidUntilDate, PaidUntilDate);
	}

	/** Get Paid Until.
		@return Subscription is paid/valid until this date
	  */
	public Date getPaidUntilDate () 
	{
		return (Date)get_Value(COLUMNNAME_PaidUntilDate);
	}

	/** Set Renewal Date.
		@param RenewalDate Renewal Date	  */
	public void setRenewalDate (Date RenewalDate)
	{
		set_Value (COLUMNNAME_RenewalDate, RenewalDate);
	}

	/** Get Renewal Date.
		@return Renewal Date	  */
	public Date getRenewalDate () 
	{
		return (Date)get_Value(COLUMNNAME_RenewalDate);
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