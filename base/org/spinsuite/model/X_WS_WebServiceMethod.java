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
import org.spinsuite.base.DB;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for WS_WebServiceMethod
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_WS_WebServiceMethod extends PO implements I_WS_WebServiceMethod {
    /** Standard Constructor */
    public X_WS_WebServiceMethod (Context ctx, int WS_WebServiceMethod_ID, DB conn)
    {
      super (ctx, WS_WebServiceMethod_ID, conn);
      /** if (WS_WebServiceMethod_ID == 0)
        {
			setName (null);
			setValue (null);
			setWS_WebServiceMethod_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WS_WebServiceMethod (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_WS_WebServiceMethod[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Web Service.
		@param WS_WebService_ID Web Service	  */
	public void setWS_WebService_ID (int WS_WebService_ID)
	{
		if (WS_WebService_ID < 1) 
			set_Value (COLUMNNAME_WS_WebService_ID, null);
		else 
			set_Value (COLUMNNAME_WS_WebService_ID, Integer.valueOf(WS_WebService_ID));
	}

	/** Get Web Service.
		@return Web Service	  */
	public int getWS_WebService_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WS_WebService_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Service Method.
		@param WS_WebServiceMethod_ID Web Service Method	  */
	public void setWS_WebServiceMethod_ID (int WS_WebServiceMethod_ID)
	{
		if (WS_WebServiceMethod_ID < 1) 
			set_Value (COLUMNNAME_WS_WebServiceMethod_ID, null);
		else 
			set_Value (COLUMNNAME_WS_WebServiceMethod_ID, Integer.valueOf(WS_WebServiceMethod_ID));
	}

	/** Get Web Service Method.
		@return Web Service Method	  */
	public int getWS_WebServiceMethod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WS_WebServiceMethod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}