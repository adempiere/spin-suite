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

/** Generated Model for CM_ChatType
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_CM_ChatType extends PO implements I_CM_ChatType
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_CM_ChatType (Context ctx, int CM_ChatType_ID, DB conn)
    {
      super (ctx, CM_ChatType_ID, conn);
      /** if (CM_ChatType_ID == 0)
        {
			setAD_Table_ID (0);
			setCM_ChatType_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_ChatType (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_CM_ChatType[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Chat Type.
		@param CM_ChatType_ID Chat Type	  */
	public void setCM_ChatType_ID (int CM_ChatType_ID)
	{
		if (CM_ChatType_ID < 1) 
			set_Value (COLUMNNAME_CM_ChatType_ID, null);
		else 
			set_Value (COLUMNNAME_CM_ChatType_ID, Integer.valueOf(CM_ChatType_ID));
	}

	/** Get Chat Type.
		@return Chat Type	  */
	public int getCM_ChatType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatType_ID);
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

	/** ModerationType AD_Reference_ID=395 */
	public static final int MODERATIONTYPE_AD_Reference_ID=395;
	/** Not moderated = N */
	public static final String MODERATIONTYPE_NotModerated = "N";
	/** Before Publishing = B */
	public static final String MODERATIONTYPE_BeforePublishing = "B";
	/** After Publishing = A */
	public static final String MODERATIONTYPE_AfterPublishing = "A";
	/** Set Moderation Type.
		@param ModerationType 
		Type of moderation
	  */
	public void setModerationType (String ModerationType)
	{

		set_Value (COLUMNNAME_ModerationType, ModerationType);
	}

	/** Get Moderation Type.
		@return Type of moderation
	  */
	public String getModerationType () 
	{
		return (String)get_Value(COLUMNNAME_ModerationType);
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
}