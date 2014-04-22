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

/** Generated Model for AD_ClientShare
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_ClientShare extends PO implements I_AD_ClientShare
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_ClientShare (Context ctx, int AD_ClientShare_ID, DB conn)
    {
      super (ctx, AD_ClientShare_ID, conn);
      /** if (AD_ClientShare_ID == 0)
        {
			setAD_ClientShare_ID (0);
			setAD_Table_ID (0);
			setName (null);
			setShareType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_ClientShare (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_AD_ClientShare[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Client Share.
		@param AD_ClientShare_ID Client Share	  */
	public void setAD_ClientShare_ID (int AD_ClientShare_ID)
	{
		if (AD_ClientShare_ID < 1) 
			set_Value (COLUMNNAME_AD_ClientShare_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ClientShare_ID, Integer.valueOf(AD_ClientShare_ID));
	}

	/** Get Client Share.
		@return Client Share	  */
	public int getAD_ClientShare_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ClientShare_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
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

	/** ShareType AD_Reference_ID=365 */
	public static final int SHARETYPE_AD_Reference_ID=365;
	/** Client (all shared) = C */
	public static final String SHARETYPE_ClientAllShared = "C";
	/** Org (not shared) = O */
	public static final String SHARETYPE_OrgNotShared = "O";
	/** Client or Org = x */
	public static final String SHARETYPE_ClientOrOrg = "x";
	/** Set Share Type.
		@param ShareType 
		Type of sharing
	  */
	public void setShareType (String ShareType)
	{

		set_Value (COLUMNNAME_ShareType, ShareType);
	}

	/** Get Share Type.
		@return Type of sharing
	  */
	public String getShareType () 
	{
		return (String)get_Value(COLUMNNAME_ShareType);
	}
}