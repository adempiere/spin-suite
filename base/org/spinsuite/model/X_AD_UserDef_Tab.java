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

/** Generated Model for AD_UserDef_Tab
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_UserDef_Tab extends PO implements I_AD_UserDef_Tab
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_UserDef_Tab (Context ctx, int AD_UserDef_Tab_ID, DB conn)
    {
      super (ctx, AD_UserDef_Tab_ID, conn);
      /** if (AD_UserDef_Tab_ID == 0)
        {
			setAD_Tab_ID (0);
			setAD_UserDef_Tab_ID (0);
			setAD_UserDef_Win_ID (0);
			setIsMultiRowOnly (false);
			setIsReadOnly (false);
			setIsSingleRow (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_UserDef_Tab (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_AD_UserDef_Tab[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Tab.
		@param AD_Tab_ID 
		Tab within a Window
	  */
	public void setAD_Tab_ID (int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_Value (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tab_ID, Integer.valueOf(AD_Tab_ID));
	}

	/** Get Tab.
		@return Tab within a Window
	  */
	public int getAD_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User defined Tab.
		@param AD_UserDef_Tab_ID User defined Tab	  */
	public void setAD_UserDef_Tab_ID (int AD_UserDef_Tab_ID)
	{
		if (AD_UserDef_Tab_ID < 1) 
			set_Value (COLUMNNAME_AD_UserDef_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_AD_UserDef_Tab_ID, Integer.valueOf(AD_UserDef_Tab_ID));
	}

	/** Get User defined Tab.
		@return User defined Tab	  */
	public int getAD_UserDef_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UserDef_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User defined Window.
		@param AD_UserDef_Win_ID User defined Window	  */
	public void setAD_UserDef_Win_ID (int AD_UserDef_Win_ID)
	{
		if (AD_UserDef_Win_ID < 1) 
			set_Value (COLUMNNAME_AD_UserDef_Win_ID, null);
		else 
			set_Value (COLUMNNAME_AD_UserDef_Win_ID, Integer.valueOf(AD_UserDef_Win_ID));
	}

	/** Get User defined Window.
		@return User defined Window	  */
	public int getAD_UserDef_Win_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UserDef_Win_ID);
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

	/** Set Multi Row Only.
		@param IsMultiRowOnly 
		This applies to Multi-Row view only
	  */
	public void setIsMultiRowOnly (boolean IsMultiRowOnly)
	{
		set_Value (COLUMNNAME_IsMultiRowOnly, Boolean.valueOf(IsMultiRowOnly));
	}

	/** Get Multi Row Only.
		@return This applies to Multi-Row view only
	  */
	public boolean isMultiRowOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsMultiRowOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Read Only.
		@param IsReadOnly 
		Field is read only
	  */
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Read Only.
		@return Field is read only
	  */
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Single Row Layout.
		@param IsSingleRow 
		Default for toggle between Single- and Multi-Row (Grid) Layout
	  */
	public void setIsSingleRow (boolean IsSingleRow)
	{
		set_Value (COLUMNNAME_IsSingleRow, Boolean.valueOf(IsSingleRow));
	}

	/** Get Single Row Layout.
		@return Default for toggle between Single- and Multi-Row (Grid) Layout
	  */
	public boolean isSingleRow () 
	{
		Object oo = get_Value(COLUMNNAME_IsSingleRow);
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
}