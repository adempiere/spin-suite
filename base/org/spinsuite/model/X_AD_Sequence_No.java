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

/** Generated Model for AD_Sequence_No
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_Sequence_No extends PO implements I_AD_Sequence_No
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_Sequence_No (Context ctx, int AD_Sequence_No_ID, DB conn)
    {
      super (ctx, AD_Sequence_No_ID, conn);
      /** if (AD_Sequence_No_ID == 0)
        {
			setAD_Sequence_ID (0);
			setCalendarYear (null);
			setCurrentNext (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Sequence_No (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_AD_Sequence_No[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Sequence.
		@param AD_Sequence_ID 
		Document Sequence
	  */
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
	}

	/** Get Sequence.
		@return Document Sequence
	  */
	public int getAD_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Year.
		@param CalendarYear 
		Calendar Year
	  */
	public void setCalendarYear (String CalendarYear)
	{
		set_Value (COLUMNNAME_CalendarYear, CalendarYear);
	}

	/** Get Year.
		@return Calendar Year
	  */
	public String getCalendarYear () 
	{
		return (String)get_Value(COLUMNNAME_CalendarYear);
	}

	/** Set Current Next.
		@param CurrentNext 
		The next number to be used
	  */
	public void setCurrentNext (int CurrentNext)
	{
		set_Value (COLUMNNAME_CurrentNext, Integer.valueOf(CurrentNext));
	}

	/** Get Current Next.
		@return The next number to be used
	  */
	public int getCurrentNext () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CurrentNext);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}