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

/** Generated Model for C_RfQResponseLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_RfQResponseLine extends PO implements I_C_RfQResponseLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_RfQResponseLine (Context ctx, int C_RfQResponseLine_ID, DB conn)
    {
      super (ctx, C_RfQResponseLine_ID, conn);
      /** if (C_RfQResponseLine_ID == 0)
        {
			setC_RfQLine_ID (0);
			setC_RfQResponse_ID (0);
			setC_RfQResponseLine_ID (0);
			setIsSelectedWinner (false);
			setIsSelfService (false);
        } */
    }

    /** Load Constructor */
    public X_C_RfQResponseLine (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_RfQResponseLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set RfQ Line.
		@param C_RfQLine_ID 
		Request for Quotation Line
	  */
	public void setC_RfQLine_ID (int C_RfQLine_ID)
	{
		if (C_RfQLine_ID < 1) 
			set_Value (COLUMNNAME_C_RfQLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQLine_ID, Integer.valueOf(C_RfQLine_ID));
	}

	/** Get RfQ Line.
		@return Request for Quotation Line
	  */
	public int getC_RfQLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Response.
		@param C_RfQResponse_ID 
		Request for Quotation Response from a potential Vendor
	  */
	public void setC_RfQResponse_ID (int C_RfQResponse_ID)
	{
		if (C_RfQResponse_ID < 1) 
			set_Value (COLUMNNAME_C_RfQResponse_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQResponse_ID, Integer.valueOf(C_RfQResponse_ID));
	}

	/** Get RfQ Response.
		@return Request for Quotation Response from a potential Vendor
	  */
	public int getC_RfQResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Response Line.
		@param C_RfQResponseLine_ID 
		Request for Quotation Response Line
	  */
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID)
	{
		if (C_RfQResponseLine_ID < 1) 
			set_Value (COLUMNNAME_C_RfQResponseLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQResponseLine_ID, Integer.valueOf(C_RfQResponseLine_ID));
	}

	/** Get RfQ Response Line.
		@return Request for Quotation Response Line
	  */
	public int getC_RfQResponseLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponseLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Work Complete.
		@param DateWorkComplete 
		Date when work is (planned to be) complete
	  */
	public void setDateWorkComplete (Date DateWorkComplete)
	{
		set_Value (COLUMNNAME_DateWorkComplete, DateWorkComplete);
	}

	/** Get Work Complete.
		@return Date when work is (planned to be) complete
	  */
	public Date getDateWorkComplete () 
	{
		return (Date)get_Value(COLUMNNAME_DateWorkComplete);
	}

	/** Set Work Start.
		@param DateWorkStart 
		Date when work is (planned to be) started
	  */
	public void setDateWorkStart (Date DateWorkStart)
	{
		set_Value (COLUMNNAME_DateWorkStart, DateWorkStart);
	}

	/** Get Work Start.
		@return Date when work is (planned to be) started
	  */
	public Date getDateWorkStart () 
	{
		return (Date)get_Value(COLUMNNAME_DateWorkStart);
	}

	/** Set Delivery Days.
		@param DeliveryDays 
		Number of Days (planned) until Delivery
	  */
	public void setDeliveryDays (int DeliveryDays)
	{
		set_Value (COLUMNNAME_DeliveryDays, Integer.valueOf(DeliveryDays));
	}

	/** Get Delivery Days.
		@return Number of Days (planned) until Delivery
	  */
	public int getDeliveryDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryDays);
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

	/** Set Selected Winner.
		@param IsSelectedWinner 
		The response is the selected winner
	  */
	public void setIsSelectedWinner (boolean IsSelectedWinner)
	{
		set_Value (COLUMNNAME_IsSelectedWinner, Boolean.valueOf(IsSelectedWinner));
	}

	/** Get Selected Winner.
		@return The response is the selected winner
	  */
	public boolean isSelectedWinner () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelectedWinner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Self-Service.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}