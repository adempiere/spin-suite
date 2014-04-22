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

/** Generated Model for AD_AlertRecipient
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_AlertRecipient extends PO implements I_AD_AlertRecipient
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_AlertRecipient (Context ctx, int AD_AlertRecipient_ID, DB conn)
    {
      super (ctx, AD_AlertRecipient_ID, conn);
      /** if (AD_AlertRecipient_ID == 0)
        {
			setAD_Alert_ID (0);
			setAD_AlertRecipient_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_AlertRecipient (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_AD_AlertRecipient[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Alert.
		@param AD_Alert_ID 
		Adempiere Alert
	  */
	public void setAD_Alert_ID (int AD_Alert_ID)
	{
		if (AD_Alert_ID < 1) 
			set_Value (COLUMNNAME_AD_Alert_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Alert_ID, Integer.valueOf(AD_Alert_ID));
	}

	/** Get Alert.
		@return Adempiere Alert
	  */
	public int getAD_Alert_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Alert_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Alert Recipient.
		@param AD_AlertRecipient_ID 
		Recipient of the Alert Notification
	  */
	public void setAD_AlertRecipient_ID (int AD_AlertRecipient_ID)
	{
		if (AD_AlertRecipient_ID < 1) 
			set_Value (COLUMNNAME_AD_AlertRecipient_ID, null);
		else 
			set_Value (COLUMNNAME_AD_AlertRecipient_ID, Integer.valueOf(AD_AlertRecipient_ID));
	}

	/** Get Alert Recipient.
		@return Recipient of the Alert Notification
	  */
	public int getAD_AlertRecipient_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_AlertRecipient_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Usuario.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Usuario.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_User_ID()));
    }
}