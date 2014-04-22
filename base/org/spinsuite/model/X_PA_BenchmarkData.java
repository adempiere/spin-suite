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

/** Generated Model for PA_BenchmarkData
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_PA_BenchmarkData extends PO implements I_PA_BenchmarkData
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_PA_BenchmarkData (Context ctx, int PA_BenchmarkData_ID, DB conn)
    {
      super (ctx, PA_BenchmarkData_ID, conn);
      /** if (PA_BenchmarkData_ID == 0)
        {
			setBenchmarkDate (new Date( System.currentTimeMillis() ));
			setBenchmarkValue (Env.ZERO);
			setName (null);
			setPA_BenchmarkData_ID (0);
			setPA_Benchmark_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PA_BenchmarkData (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_PA_BenchmarkData[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date.
		@param BenchmarkDate 
		Benchmark Date
	  */
	public void setBenchmarkDate (Date BenchmarkDate)
	{
		set_Value (COLUMNNAME_BenchmarkDate, BenchmarkDate);
	}

	/** Get Date.
		@return Benchmark Date
	  */
	public Date getBenchmarkDate () 
	{
		return (Date)get_Value(COLUMNNAME_BenchmarkDate);
	}

	/** Set Value.
		@param BenchmarkValue 
		Benchmark Value
	  */
	public void setBenchmarkValue (BigDecimal BenchmarkValue)
	{
		set_Value (COLUMNNAME_BenchmarkValue, BenchmarkValue);
	}

	/** Get Value.
		@return Benchmark Value
	  */
	public BigDecimal getBenchmarkValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BenchmarkValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Benchmark Data.
		@param PA_BenchmarkData_ID 
		Performance Benchmark Data Point
	  */
	public void setPA_BenchmarkData_ID (int PA_BenchmarkData_ID)
	{
		if (PA_BenchmarkData_ID < 1) 
			set_Value (COLUMNNAME_PA_BenchmarkData_ID, null);
		else 
			set_Value (COLUMNNAME_PA_BenchmarkData_ID, Integer.valueOf(PA_BenchmarkData_ID));
	}

	/** Get Benchmark Data.
		@return Performance Benchmark Data Point
	  */
	public int getPA_BenchmarkData_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_BenchmarkData_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Benchmark.
		@param PA_Benchmark_ID 
		Performance Benchmark
	  */
	public void setPA_Benchmark_ID (int PA_Benchmark_ID)
	{
		if (PA_Benchmark_ID < 1) 
			set_Value (COLUMNNAME_PA_Benchmark_ID, null);
		else 
			set_Value (COLUMNNAME_PA_Benchmark_ID, Integer.valueOf(PA_Benchmark_ID));
	}

	/** Get Benchmark.
		@return Performance Benchmark
	  */
	public int getPA_Benchmark_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Benchmark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}