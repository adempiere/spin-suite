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

/** Generated Model for FTA_FarmingStage
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_FTA_FarmingStage extends PO implements I_FTA_FarmingStage {
    /** Standard Constructor */
    public X_FTA_FarmingStage (Context ctx, int FTA_FarmingStage_ID, DB conn)
    {
      super (ctx, FTA_FarmingStage_ID, conn);
      /** if (FTA_FarmingStage_ID == 0)
        {
			setCategory_ID (0);
			setDayFrom (Env.ZERO);
			setDayTo (Env.ZERO);
			setFTA_FarmingStage_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_FTA_FarmingStage (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_FTA_FarmingStage[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Category.
		@param Category_ID Category	  */
	public void setCategory_ID (int Category_ID)
	{
		if (Category_ID < 1) 
			set_Value (COLUMNNAME_Category_ID, null);
		else 
			set_Value (COLUMNNAME_Category_ID, Integer.valueOf(Category_ID));
	}

	/** Get Category.
		@return Category	  */
	public int getCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Day From.
		@param DayFrom Day From	  */
	public void setDayFrom (BigDecimal DayFrom)
	{
		set_Value (COLUMNNAME_DayFrom, DayFrom);
	}

	/** Get Day From.
		@return Day From	  */
	public BigDecimal getDayFrom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayFrom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Day To.
		@param DayTo Day To	  */
	public void setDayTo (BigDecimal DayTo)
	{
		set_Value (COLUMNNAME_DayTo, DayTo);
	}

	/** Get Day To.
		@return Day To	  */
	public BigDecimal getDayTo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DayTo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Farming Stage.
		@param FTA_FarmingStage_ID Farming Stage	  */
	public void setFTA_FarmingStage_ID (int FTA_FarmingStage_ID)
	{
		if (FTA_FarmingStage_ID < 1) 
			set_Value (COLUMNNAME_FTA_FarmingStage_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_FarmingStage_ID, Integer.valueOf(FTA_FarmingStage_ID));
	}

	/** Get Farming Stage.
		@return Farming Stage	  */
	public int getFTA_FarmingStage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_FarmingStage_ID);
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}