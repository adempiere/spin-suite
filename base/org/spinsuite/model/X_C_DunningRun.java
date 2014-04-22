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
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for C_DunningRun
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_DunningRun extends PO implements I_C_DunningRun
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_DunningRun (Context ctx, int C_DunningRun_ID, DB conn)
    {
      super (ctx, C_DunningRun_ID, conn);
      /** if (C_DunningRun_ID == 0)
        {
			setC_Dunning_ID (0);
			setC_DunningRun_ID (0);
			setDunningDate (new Date( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_C_DunningRun (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_DunningRun[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Dunning.
		@param C_Dunning_ID 
		Dunning Rules for overdue invoices
	  */
	public void setC_Dunning_ID (int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
	}

	/** Get Dunning.
		@return Dunning Rules for overdue invoices
	  */
	public int getC_Dunning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dunning Level.
		@param C_DunningLevel_ID Dunning Level	  */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_Value (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_Value (COLUMNNAME_C_DunningLevel_ID, Integer.valueOf(C_DunningLevel_ID));
	}

	/** Get Dunning Level.
		@return Dunning Level	  */
	public int getC_DunningLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dunning Run.
		@param C_DunningRun_ID 
		Dunning Run
	  */
	public void setC_DunningRun_ID (int C_DunningRun_ID)
	{
		if (C_DunningRun_ID < 1) 
			set_Value (COLUMNNAME_C_DunningRun_ID, null);
		else 
			set_Value (COLUMNNAME_C_DunningRun_ID, Integer.valueOf(C_DunningRun_ID));
	}

	/** Get Dunning Run.
		@return Dunning Run
	  */
	public int getC_DunningRun_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningRun_ID);
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

	/** Set Dunning Date.
		@param DunningDate 
		Date of Dunning
	  */
	public void setDunningDate (Date DunningDate)
	{
		set_Value (COLUMNNAME_DunningDate, DunningDate);
	}

	/** Get Dunning Date.
		@return Date of Dunning
	  */
	public Date getDunningDate () 
	{
		return (Date)get_Value(COLUMNNAME_DunningDate);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getDunningDate()));
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

	/** Set Send.
		@param SendIt Send	  */
	public void setSendIt (String SendIt)
	{
		set_Value (COLUMNNAME_SendIt, SendIt);
	}

	/** Get Send.
		@return Send	  */
	public String getSendIt () 
	{
		return (String)get_Value(COLUMNNAME_SendIt);
	}
}