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

/** Generated Model for t_alter_column
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_t_alter_column extends PO implements I_t_alter_column
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_t_alter_column (Context ctx, int t_alter_column_ID, DB conn)
    {
      super (ctx, t_alter_column_ID, conn);
      /** if (t_alter_column_ID == 0)
        {
			sett_alter_column_ID (0);
        } */
    }

    /** Load Constructor */
    public X_t_alter_column (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_t_alter_column[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** DataType AD_Reference_ID=210 */
	public static final int DATATYPE_AD_Reference_ID=210;
	/** String = S */
	public static final String DATATYPE_String = "S";
	/** Number = N */
	public static final String DATATYPE_Number = "N";
	/** Date = D */
	public static final String DATATYPE_Date = "D";
	/** Constant = C */
	public static final String DATATYPE_Constant = "C";
	/** Set Data Type.
		@param DataType 
		Type of data
	  */
	public void setDataType (String DataType)
	{

		set_Value (COLUMNNAME_DataType, DataType);
	}

	/** Get Data Type.
		@return Type of data
	  */
	public String getDataType () 
	{
		return (String)get_Value(COLUMNNAME_DataType);
	}

	/** Set defaultclause.
		@param defaultclause defaultclause	  */
	public void setdefaultclause (String defaultclause)
	{
		set_Value (COLUMNNAME_defaultclause, defaultclause);
	}

	/** Get defaultclause.
		@return defaultclause	  */
	public String getdefaultclause () 
	{
		return (String)get_Value(COLUMNNAME_defaultclause);
	}

	/** Set nullclause.
		@param nullclause nullclause	  */
	public void setnullclause (String nullclause)
	{
		set_Value (COLUMNNAME_nullclause, nullclause);
	}

	/** Get nullclause.
		@return nullclause	  */
	public String getnullclause () 
	{
		return (String)get_Value(COLUMNNAME_nullclause);
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

	/** Set t_alter_column.
		@param t_alter_column_ID t_alter_column	  */
	public void sett_alter_column_ID (int t_alter_column_ID)
	{
		if (t_alter_column_ID < 1) 
			set_Value (COLUMNNAME_t_alter_column_ID, null);
		else 
			set_Value (COLUMNNAME_t_alter_column_ID, Integer.valueOf(t_alter_column_ID));
	}

	/** Get t_alter_column.
		@return t_alter_column	  */
	public int gett_alter_column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_t_alter_column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}