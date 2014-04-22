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

/** Generated Model for AD_WF_ProcessData
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_WF_ProcessData extends PO implements I_AD_WF_ProcessData
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_WF_ProcessData (Context ctx, int AD_WF_ProcessData_ID, DB conn)
    {
      super (ctx, AD_WF_ProcessData_ID, conn);
      /** if (AD_WF_ProcessData_ID == 0)
        {
			setAD_WF_ProcessData_ID (0);
			setAD_WF_Process_ID (0);
			setAttributeName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_WF_ProcessData (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_AD_WF_ProcessData[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Workflow Process Data.
		@param AD_WF_ProcessData_ID 
		Workflow Process Context
	  */
	public void setAD_WF_ProcessData_ID (int AD_WF_ProcessData_ID)
	{
		if (AD_WF_ProcessData_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_ProcessData_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_ProcessData_ID, Integer.valueOf(AD_WF_ProcessData_ID));
	}

	/** Get Workflow Process Data.
		@return Workflow Process Context
	  */
	public int getAD_WF_ProcessData_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_ProcessData_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Workflow Process.
		@param AD_WF_Process_ID 
		Actual Workflow Process Instance
	  */
	public void setAD_WF_Process_ID (int AD_WF_Process_ID)
	{
		if (AD_WF_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Process_ID, Integer.valueOf(AD_WF_Process_ID));
	}

	/** Get Workflow Process.
		@return Actual Workflow Process Instance
	  */
	public int getAD_WF_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_WF_Process_ID()));
    }

	/** Set Attribute Name.
		@param AttributeName 
		Name of the Attribute
	  */
	public void setAttributeName (String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	/** Get Attribute Name.
		@return Name of the Attribute
	  */
	public String getAttributeName () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName);
	}

	/** Set Attribute Value.
		@param AttributeValue 
		Value of the Attribute
	  */
	public void setAttributeValue (String AttributeValue)
	{
		set_Value (COLUMNNAME_AttributeValue, AttributeValue);
	}

	/** Get Attribute Value.
		@return Value of the Attribute
	  */
	public String getAttributeValue () 
	{
		return (String)get_Value(COLUMNNAME_AttributeValue);
	}
}