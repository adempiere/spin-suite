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

/** Generated Model for AD_View_Column
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_View_Column extends PO implements I_AD_View_Column
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_View_Column (Context ctx, int AD_View_Column_ID, DB conn)
    {
      super (ctx, AD_View_Column_ID, conn);
      /** if (AD_View_Column_ID == 0)
        {
			setAD_View_Column_ID (0);
			setAD_View_Definition_ID (0);
			setEntityType (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_View_Column (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_AD_View_Column[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set View Column.
		@param AD_View_Column_ID 
		Column of View
	  */
	public void setAD_View_Column_ID (int AD_View_Column_ID)
	{
		if (AD_View_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_View_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_View_Column_ID, Integer.valueOf(AD_View_Column_ID));
	}

	/** Get View Column.
		@return Column of View
	  */
	public int getAD_View_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_View_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set View Definition.
		@param AD_View_Definition_ID 
		The View Definition allow defined the tables for a view.
	  */
	public void setAD_View_Definition_ID (int AD_View_Definition_ID)
	{
		if (AD_View_Definition_ID < 1) 
			set_Value (COLUMNNAME_AD_View_Definition_ID, null);
		else 
			set_Value (COLUMNNAME_AD_View_Definition_ID, Integer.valueOf(AD_View_Definition_ID));
	}

	/** Get View Definition.
		@return The View Definition allow defined the tables for a view.
	  */
	public int getAD_View_Definition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_View_Definition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set View.
		@param AD_View_ID 
		View allows you to create dynamic views of information from the dictionary application
	  */
	public void setAD_View_ID (int AD_View_ID)
	{
		if (AD_View_ID < 1) 
			set_Value (COLUMNNAME_AD_View_ID, null);
		else 
			set_Value (COLUMNNAME_AD_View_ID, Integer.valueOf(AD_View_ID));
	}

	/** Get View.
		@return View allows you to create dynamic views of information from the dictionary application
	  */
	public int getAD_View_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_View_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getColumnName());
    }

	/** Set Column SQL.
		@param ColumnSQL 
		Virtual Column (r/o)
	  */
	public void setColumnSQL (String ColumnSQL)
	{
		set_Value (COLUMNNAME_ColumnSQL, ColumnSQL);
	}

	/** Get Column SQL.
		@return Virtual Column (r/o)
	  */
	public String getColumnSQL () 
	{
		return (String)get_Value(COLUMNNAME_ColumnSQL);
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

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
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