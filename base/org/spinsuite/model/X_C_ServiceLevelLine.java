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

/** Generated Model for C_ServiceLevelLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_ServiceLevelLine extends PO implements I_C_ServiceLevelLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_ServiceLevelLine (Context ctx, int C_ServiceLevelLine_ID, DB conn)
    {
      super (ctx, C_ServiceLevelLine_ID, conn);
      /** if (C_ServiceLevelLine_ID == 0)
        {
			setC_ServiceLevel_ID (0);
			setC_ServiceLevelLine_ID (0);
			setServiceDate (new Date( System.currentTimeMillis() ));
			setServiceLevelProvided (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_ServiceLevelLine (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_ServiceLevelLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Service Level.
		@param C_ServiceLevel_ID 
		Product Revenue Recognition Service Level 
	  */
	public void setC_ServiceLevel_ID (int C_ServiceLevel_ID)
	{
		if (C_ServiceLevel_ID < 1) 
			set_Value (COLUMNNAME_C_ServiceLevel_ID, null);
		else 
			set_Value (COLUMNNAME_C_ServiceLevel_ID, Integer.valueOf(C_ServiceLevel_ID));
	}

	/** Get Service Level.
		@return Product Revenue Recognition Service Level 
	  */
	public int getC_ServiceLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ServiceLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Service Level Line.
		@param C_ServiceLevelLine_ID 
		Product Revenue Recognition Service Level Line
	  */
	public void setC_ServiceLevelLine_ID (int C_ServiceLevelLine_ID)
	{
		if (C_ServiceLevelLine_ID < 1) 
			set_Value (COLUMNNAME_C_ServiceLevelLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_ServiceLevelLine_ID, Integer.valueOf(C_ServiceLevelLine_ID));
	}

	/** Get Service Level Line.
		@return Product Revenue Recognition Service Level Line
	  */
	public int getC_ServiceLevelLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ServiceLevelLine_ID);
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Service date.
		@param ServiceDate 
		Date service was provided
	  */
	public void setServiceDate (Date ServiceDate)
	{
		set_Value (COLUMNNAME_ServiceDate, ServiceDate);
	}

	/** Get Service date.
		@return Date service was provided
	  */
	public Date getServiceDate () 
	{
		return (Date)get_Value(COLUMNNAME_ServiceDate);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getServiceDate()));
    }

	/** Set Quantity Provided.
		@param ServiceLevelProvided 
		Quantity of service or product provided
	  */
	public void setServiceLevelProvided (BigDecimal ServiceLevelProvided)
	{
		set_Value (COLUMNNAME_ServiceLevelProvided, ServiceLevelProvided);
	}

	/** Get Quantity Provided.
		@return Quantity of service or product provided
	  */
	public BigDecimal getServiceLevelProvided () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ServiceLevelProvided);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}