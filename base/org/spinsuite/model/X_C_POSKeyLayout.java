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

/** Generated Model for C_POSKeyLayout
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_POSKeyLayout extends PO implements I_C_POSKeyLayout
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_POSKeyLayout (Context ctx, int C_POSKeyLayout_ID, DB conn)
    {
      super (ctx, C_POSKeyLayout_ID, conn);
      /** if (C_POSKeyLayout_ID == 0)
        {
			setC_POSKeyLayout_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_POSKeyLayout (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_POSKeyLayout[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Print Color.
		@param AD_PrintColor_ID 
		Color used for printing and display
	  */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, Integer.valueOf(AD_PrintColor_ID));
	}

	/** Get Print Color.
		@return Color used for printing and display
	  */
	public int getAD_PrintColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Print Font.
		@param AD_PrintFont_ID 
		Maintain Print Font
	  */
	public void setAD_PrintFont_ID (int AD_PrintFont_ID)
	{
		if (AD_PrintFont_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFont_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFont_ID, Integer.valueOf(AD_PrintFont_ID));
	}

	/** Get Print Font.
		@return Maintain Print Font
	  */
	public int getAD_PrintFont_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFont_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Columns.
		@param Columns 
		Number of columns
	  */
	public void setColumns (int Columns)
	{
		set_Value (COLUMNNAME_Columns, Integer.valueOf(Columns));
	}

	/** Get Columns.
		@return Number of columns
	  */
	public int getColumns () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Columns);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set POS Key Layout.
		@param C_POSKeyLayout_ID 
		POS Function Key Layout
	  */
	public void setC_POSKeyLayout_ID (int C_POSKeyLayout_ID)
	{
		if (C_POSKeyLayout_ID < 1) 
			set_Value (COLUMNNAME_C_POSKeyLayout_ID, null);
		else 
			set_Value (COLUMNNAME_C_POSKeyLayout_ID, Integer.valueOf(C_POSKeyLayout_ID));
	}

	/** Get POS Key Layout.
		@return POS Function Key Layout
	  */
	public int getC_POSKeyLayout_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POSKeyLayout_ID);
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

	/** POSKeyLayoutType AD_Reference_ID=53351 */
	public static final int POSKEYLAYOUTTYPE_AD_Reference_ID=53351;
	/** Keyboard = K */
	public static final String POSKEYLAYOUTTYPE_Keyboard = "K";
	/** Numberpad = N */
	public static final String POSKEYLAYOUTTYPE_Numberpad = "N";
	/** Product = P */
	public static final String POSKEYLAYOUTTYPE_Product = "P";
	/** Set POS Key Layout Type.
		@param POSKeyLayoutType 
		The type of Key Layout
	  */
	public void setPOSKeyLayoutType (String POSKeyLayoutType)
	{

		set_Value (COLUMNNAME_POSKeyLayoutType, POSKeyLayoutType);
	}

	/** Get POS Key Layout Type.
		@return The type of Key Layout
	  */
	public String getPOSKeyLayoutType () 
	{
		return (String)get_Value(COLUMNNAME_POSKeyLayoutType);
	}
}