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

/** Generated Model for A_RegistrationValue
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_A_RegistrationValue extends PO implements I_A_RegistrationValue
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_A_RegistrationValue (Context ctx, int A_RegistrationValue_ID, DB conn)
    {
      super (ctx, A_RegistrationValue_ID, conn);
      /** if (A_RegistrationValue_ID == 0)
        {
			setA_RegistrationAttribute_ID (0);
			setA_Registration_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_A_RegistrationValue (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_A_RegistrationValue[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Registration Attribute.
		@param A_RegistrationAttribute_ID 
		Asset Registration Attribute
	  */
	public void setA_RegistrationAttribute_ID (int A_RegistrationAttribute_ID)
	{
		if (A_RegistrationAttribute_ID < 1) 
			set_Value (COLUMNNAME_A_RegistrationAttribute_ID, null);
		else 
			set_Value (COLUMNNAME_A_RegistrationAttribute_ID, Integer.valueOf(A_RegistrationAttribute_ID));
	}

	/** Get Registration Attribute.
		@return Asset Registration Attribute
	  */
	public int getA_RegistrationAttribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_RegistrationAttribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_RegistrationAttribute_ID()));
    }

	/** Set Registration.
		@param A_Registration_ID 
		User Asset Registration
	  */
	public void setA_Registration_ID (int A_Registration_ID)
	{
		if (A_Registration_ID < 1) 
			set_Value (COLUMNNAME_A_Registration_ID, null);
		else 
			set_Value (COLUMNNAME_A_Registration_ID, Integer.valueOf(A_Registration_ID));
	}

	/** Get Registration.
		@return User Asset Registration
	  */
	public int getA_Registration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Registration_ID);
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
}