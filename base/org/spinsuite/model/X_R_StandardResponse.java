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
import org.spinsuite.util.KeyNamePair;

/** Generated Model for R_StandardResponse
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_R_StandardResponse extends PO implements I_R_StandardResponse
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_R_StandardResponse (Context ctx, int R_StandardResponse_ID, DB conn)
    {
      super (ctx, R_StandardResponse_ID, conn);
      /** if (R_StandardResponse_ID == 0)
        {
			setName (null);
			setResponseText (null);
			setR_StandardResponse_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_StandardResponse (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_R_StandardResponse[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Response Text.
		@param ResponseText 
		Request Response Text
	  */
	public void setResponseText (String ResponseText)
	{
		set_Value (COLUMNNAME_ResponseText, ResponseText);
	}

	/** Get Response Text.
		@return Request Response Text
	  */
	public String getResponseText () 
	{
		return (String)get_Value(COLUMNNAME_ResponseText);
	}

	/** Set Standard Response.
		@param R_StandardResponse_ID 
		Request Standard Response 
	  */
	public void setR_StandardResponse_ID (int R_StandardResponse_ID)
	{
		if (R_StandardResponse_ID < 1) 
			set_Value (COLUMNNAME_R_StandardResponse_ID, null);
		else 
			set_Value (COLUMNNAME_R_StandardResponse_ID, Integer.valueOf(R_StandardResponse_ID));
	}

	/** Get Standard Response.
		@return Request Standard Response 
	  */
	public int getR_StandardResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_StandardResponse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}