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

/** Generated Model for R_RequestProcessorLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_R_RequestProcessorLog extends PO implements I_R_RequestProcessorLog
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_R_RequestProcessorLog (Context ctx, int R_RequestProcessorLog_ID, DB conn)
    {
      super (ctx, R_RequestProcessorLog_ID, conn);
      /** if (R_RequestProcessorLog_ID == 0)
        {
			setIsError (false);
			setR_RequestProcessor_ID (0);
			setR_RequestProcessorLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_RequestProcessorLog (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_R_RequestProcessorLog[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Binary Data.
		@param BinaryData 
		Binary Data
	  */
	public void setBinaryData (byte[] BinaryData)
	{
		set_Value (COLUMNNAME_BinaryData, BinaryData);
	}

	/** Get Binary Data.
		@return Binary Data
	  */
	public byte[] getBinaryData () 
	{
		return (byte[])get_Value(COLUMNNAME_BinaryData);
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

	/** Set Error.
		@param IsError 
		An Error occurred in the execution
	  */
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Error.
		@return An Error occurred in the execution
	  */
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reference.
		@param Reference 
		Reference for this record
	  */
	public void setReference (String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	/** Get Reference.
		@return Reference for this record
	  */
	public String getReference () 
	{
		return (String)get_Value(COLUMNNAME_Reference);
	}

	/** Set Request Processor.
		@param R_RequestProcessor_ID 
		Processor for Requests
	  */
	public void setR_RequestProcessor_ID (int R_RequestProcessor_ID)
	{
		if (R_RequestProcessor_ID < 1) 
			set_Value (COLUMNNAME_R_RequestProcessor_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestProcessor_ID, Integer.valueOf(R_RequestProcessor_ID));
	}

	/** Get Request Processor.
		@return Processor for Requests
	  */
	public int getR_RequestProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Request Processor Log.
		@param R_RequestProcessorLog_ID Request Processor Log	  */
	public void setR_RequestProcessorLog_ID (int R_RequestProcessorLog_ID)
	{
		if (R_RequestProcessorLog_ID < 1) 
			set_Value (COLUMNNAME_R_RequestProcessorLog_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestProcessorLog_ID, Integer.valueOf(R_RequestProcessorLog_ID));
	}

	/** Get Request Processor Log.
		@return Request Processor Log	  */
	public int getR_RequestProcessorLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestProcessorLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Summary.
		@param Summary 
		Textual summary of this request
	  */
	public void setSummary (String Summary)
	{
		set_Value (COLUMNNAME_Summary, Summary);
	}

	/** Get Summary.
		@return Textual summary of this request
	  */
	public String getSummary () 
	{
		return (String)get_Value(COLUMNNAME_Summary);
	}

	/** Set Text Message.
		@param TextMsg 
		Text Message
	  */
	public void setTextMsg (String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	/** Get Text Message.
		@return Text Message
	  */
	public String getTextMsg () 
	{
		return (String)get_Value(COLUMNNAME_TextMsg);
	}
}