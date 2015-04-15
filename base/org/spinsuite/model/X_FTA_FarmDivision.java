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
import java.math.BigDecimal;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for FTA_FarmDivision
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_FTA_FarmDivision extends PO implements I_FTA_FarmDivision {
    /** Standard Constructor */
    public X_FTA_FarmDivision (Context ctx, int FTA_FarmDivision_ID, DB conn)
    {
      super (ctx, FTA_FarmDivision_ID, conn);
      /** if (FTA_FarmDivision_ID == 0)
        {
			setArea (Env.ZERO);
			setFTA_FarmDivision_ID (0);
			setFTA_Farm_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_FTA_FarmDivision (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_FTA_FarmDivision[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Area.
		@param Area Area	  */
	public void setArea (BigDecimal Area)
	{
		set_Value (COLUMNNAME_Area, Area);
	}

	/** Get Area.
		@return Area	  */
	public BigDecimal getArea () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Area);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Farm Division.
		@param FTA_FarmDivision_ID Farm Division	  */
	public void setFTA_FarmDivision_ID (int FTA_FarmDivision_ID)
	{
		if (FTA_FarmDivision_ID < 1) 
			set_Value (COLUMNNAME_FTA_FarmDivision_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_FarmDivision_ID, Integer.valueOf(FTA_FarmDivision_ID));
	}

	/** Get Farm Division.
		@return Farm Division	  */
	public int getFTA_FarmDivision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_FarmDivision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Farm.
		@param FTA_Farm_ID Farm	  */
	public void setFTA_Farm_ID (int FTA_Farm_ID)
	{
		if (FTA_Farm_ID < 1) 
			set_Value (COLUMNNAME_FTA_Farm_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_Farm_ID, Integer.valueOf(FTA_Farm_ID));
	}

	/** Get Farm.
		@return Farm	  */
	public int getFTA_Farm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_Farm_ID);
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
}