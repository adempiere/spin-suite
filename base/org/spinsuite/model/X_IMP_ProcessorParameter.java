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

/** Generated Model for IMP_ProcessorParameter
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_IMP_ProcessorParameter extends PO implements I_IMP_ProcessorParameter
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_IMP_ProcessorParameter (Context ctx, int IMP_ProcessorParameter_ID, DB conn)
    {
      super (ctx, IMP_ProcessorParameter_ID, conn);
      /** if (IMP_ProcessorParameter_ID == 0)
        {
			setIMP_Processor_ID (0);
			setIMP_ProcessorParameter_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_IMP_ProcessorParameter (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_IMP_ProcessorParameter[")
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

	/** Set Import Processor.
		@param IMP_Processor_ID Import Processor	  */
	public void setIMP_Processor_ID (int IMP_Processor_ID)
	{
		if (IMP_Processor_ID < 1) 
			set_Value (COLUMNNAME_IMP_Processor_ID, null);
		else 
			set_Value (COLUMNNAME_IMP_Processor_ID, Integer.valueOf(IMP_Processor_ID));
	}

	/** Get Import Processor.
		@return Import Processor	  */
	public int getIMP_Processor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IMP_Processor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import Processor Parameter.
		@param IMP_ProcessorParameter_ID Import Processor Parameter	  */
	public void setIMP_ProcessorParameter_ID (int IMP_ProcessorParameter_ID)
	{
		if (IMP_ProcessorParameter_ID < 1) 
			set_Value (COLUMNNAME_IMP_ProcessorParameter_ID, null);
		else 
			set_Value (COLUMNNAME_IMP_ProcessorParameter_ID, Integer.valueOf(IMP_ProcessorParameter_ID));
	}

	/** Get Import Processor Parameter.
		@return Import Processor Parameter	  */
	public int getIMP_ProcessorParameter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IMP_ProcessorParameter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Parameter Value.
		@param ParameterValue Parameter Value	  */
	public void setParameterValue (String ParameterValue)
	{
		set_Value (COLUMNNAME_ParameterValue, ParameterValue);
	}

	/** Get Parameter Value.
		@return Parameter Value	  */
	public String getParameterValue () 
	{
		return (String)get_Value(COLUMNNAME_ParameterValue);
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
}