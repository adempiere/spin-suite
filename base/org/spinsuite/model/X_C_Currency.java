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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for C_Currency
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_Currency extends PO implements I_C_Currency
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141011L;

    /** Standard Constructor */
    public X_C_Currency (Context ctx, int C_Currency_ID, DB conn)
    {
      super (ctx, C_Currency_ID, conn);
      /** if (C_Currency_ID == 0)
        {
			setC_Currency_ID (0);
			setCostingPrecision (0);
// 4
			setDescription (null);
			setIsEMUMember (false);
// N
			setIsEuro (false);
// N
			setISO_Code (null);
			setRoundOffFactor (Env.ZERO);
// 1
			setStdPrecision (0);
// 2
        } */
    }

    /** Load Constructor */
    public X_C_Currency (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_Currency[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Costing Precision.
		@param CostingPrecision 
		Rounding used costing calculations
	  */
	public void setCostingPrecision (int CostingPrecision)
	{
		set_Value (COLUMNNAME_CostingPrecision, Integer.valueOf(CostingPrecision));
	}

	/** Get Costing Precision.
		@return Rounding used costing calculations
	  */
	public int getCostingPrecision () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CostingPrecision);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Symbol.
		@param CurSymbol 
		Symbol of the currency (opt used for printing only)
	  */
	public void setCurSymbol (String CurSymbol)
	{
		set_Value (COLUMNNAME_CurSymbol, CurSymbol);
	}

	/** Get Symbol.
		@return Symbol of the currency (opt used for printing only)
	  */
	public String getCurSymbol () 
	{
		return (String)get_Value(COLUMNNAME_CurSymbol);
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

	/** Set EMU Entry Date.
		@param EMUEntryDate 
		Date when the currency joined / will join the EMU
	  */
	public void setEMUEntryDate (Date EMUEntryDate)
	{
		set_Value (COLUMNNAME_EMUEntryDate, EMUEntryDate);
	}

	/** Get EMU Entry Date.
		@return Date when the currency joined / will join the EMU
	  */
	public Date getEMUEntryDate () 
	{
		return (Date)get_Value(COLUMNNAME_EMUEntryDate);
	}

	/** Set EMU Rate.
		@param EMURate 
		Official rate to the Euro
	  */
	public void setEMURate (BigDecimal EMURate)
	{
		set_Value (COLUMNNAME_EMURate, EMURate);
	}

	/** Get EMU Rate.
		@return Official rate to the Euro
	  */
	public BigDecimal getEMURate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EMURate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set EMU Member.
		@param IsEMUMember 
		This currency is member if the European Monetary Union
	  */
	public void setIsEMUMember (boolean IsEMUMember)
	{
		set_Value (COLUMNNAME_IsEMUMember, Boolean.valueOf(IsEMUMember));
	}

	/** Get EMU Member.
		@return This currency is member if the European Monetary Union
	  */
	public boolean isEMUMember () 
	{
		Object oo = get_Value(COLUMNNAME_IsEMUMember);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set The Euro Currency.
		@param IsEuro 
		This currency is the Euro
	  */
	public void setIsEuro (boolean IsEuro)
	{
		set_Value (COLUMNNAME_IsEuro, Boolean.valueOf(IsEuro));
	}

	/** Get The Euro Currency.
		@return This currency is the Euro
	  */
	public boolean isEuro () 
	{
		Object oo = get_Value(COLUMNNAME_IsEuro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ISO Currency Code.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	public void setISO_Code (String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Currency Code.
		@return Three letter ISO 4217 Code of the Currency
	  */
	public String getISO_Code () 
	{
		return (String)get_Value(COLUMNNAME_ISO_Code);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getISO_Code());
    }

	/** Set Round Off Factor.
		@param RoundOffFactor 
		Used to Round Off Payment Amount
	  */
	public void setRoundOffFactor (BigDecimal RoundOffFactor)
	{
		set_Value (COLUMNNAME_RoundOffFactor, RoundOffFactor);
	}

	/** Get Round Off Factor.
		@return Used to Round Off Payment Amount
	  */
	public BigDecimal getRoundOffFactor () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RoundOffFactor);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Standard Precision.
		@param StdPrecision 
		Rule for rounding  calculated amounts
	  */
	public void setStdPrecision (int StdPrecision)
	{
		set_Value (COLUMNNAME_StdPrecision, Integer.valueOf(StdPrecision));
	}

	/** Get Standard Precision.
		@return Rule for rounding  calculated amounts
	  */
	public int getStdPrecision () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StdPrecision);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}