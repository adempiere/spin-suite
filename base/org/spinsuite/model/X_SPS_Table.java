/******************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                            *
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
import org.spinsuite.base.DB;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for SPS_Table
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_SPS_Table extends PO implements I_SPS_Table {
    /** Standard Constructor */
    public X_SPS_Table (Context ctx, int SPS_Table_ID, DB conn)
    {
      super (ctx, SPS_Table_ID, conn);
      /** if (SPS_Table_ID == 0)
        {
			setEntityType (null);
// ECA01
			setName (null);
			setSPS_Table_ID (0);
			setTableName (null);
        } */
    }

    /** Load Constructor */
    public X_SPS_Table (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_SPS_Table[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Rule.
		@param AD_Rule_ID Rule	  */
	public void setAD_Rule_ID (int AD_Rule_ID)
	{
		if (AD_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Rule_ID, Integer.valueOf(AD_Rule_ID));
	}

	/** Get Rule.
		@return Rule	  */
	public int getAD_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Rule_ID);
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

	/** Set Copy Columns From Table.
		@param CopyColumnsFromTable Copy Columns From Table	  */
	public void setCopyColumnsFromTable (String CopyColumnsFromTable)
	{
		set_Value (COLUMNNAME_CopyColumnsFromTable, CopyColumnsFromTable);
	}

	/** Get Copy Columns From Table.
		@return Copy Columns From Table	  */
	public String getCopyColumnsFromTable () 
	{
		return (String)get_Value(COLUMNNAME_CopyColumnsFromTable);
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

	/** Set Generate Script From Table.
		@param GenerateScriptFromTable Generate Script From Table	  */
	public void setGenerateScriptFromTable (String GenerateScriptFromTable)
	{
		set_Value (COLUMNNAME_GenerateScriptFromTable, GenerateScriptFromTable);
	}

	/** Get Generate Script From Table.
		@return Generate Script From Table	  */
	public String getGenerateScriptFromTable () 
	{
		return (String)get_Value(COLUMNNAME_GenerateScriptFromTable);
	}

	/** Set Centrally maintained.
		@param IsCentrallyMaintained 
		Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
	}

	/** Get Centrally maintained.
		@return Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained () 
	{
		Object oo = get_Value(COLUMNNAME_IsCentrallyMaintained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Records deletable.
		@param IsDeleteable 
		Indicates if records can be deleted from the database
	  */
	public void setIsDeleteable (boolean IsDeleteable)
	{
		set_Value (COLUMNNAME_IsDeleteable, Boolean.valueOf(IsDeleteable));
	}

	/** Get Records deletable.
		@return Indicates if records can be deleted from the database
	  */
	public boolean isDeleteable () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeleteable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set View.
		@param IsView 
		This is a view
	  */
	public void setIsView (boolean IsView)
	{
		set_Value (COLUMNNAME_IsView, Boolean.valueOf(IsView));
	}

	/** Get View.
		@return This is a view
	  */
	public boolean isView () 
	{
		Object oo = get_Value(COLUMNNAME_IsView);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Mobile Table.
		@param SPS_Table_ID Mobile Table	  */
	public void setSPS_Table_ID (int SPS_Table_ID)
	{
		if (SPS_Table_ID < 1) 
			set_Value (COLUMNNAME_SPS_Table_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_Table_ID, Integer.valueOf(SPS_Table_ID));
	}

	/** Get Mobile Table.
		@return Mobile Table	  */
	public int getSPS_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Table Name.
		@param TableName 
		Name of the table in the database
	  */
	public void setTableName (String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	/** Get DB Table Name.
		@return Name of the table in the database
	  */
	public String getTableName () 
	{
		return (String)get_Value(COLUMNNAME_TableName);
	}
}