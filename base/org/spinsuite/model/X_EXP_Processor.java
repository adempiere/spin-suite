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

/** Generated Model for EXP_Processor
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_EXP_Processor extends PO implements I_EXP_Processor
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_EXP_Processor (Context ctx, int EXP_Processor_ID, DB conn)
    {
      super (ctx, EXP_Processor_ID, conn);
      /** if (EXP_Processor_ID == 0)
        {
			setEXP_Processor_ID (0);
			setEXP_Processor_Type_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_EXP_Processor (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_EXP_Processor[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account.
		@param Account Account	  */
	public void setAccount (String Account)
	{
		set_Value (COLUMNNAME_Account, Account);
	}

	/** Get Account.
		@return Account	  */
	public String getAccount () 
	{
		return (String)get_Value(COLUMNNAME_Account);
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

	/** Set Export Processor.
		@param EXP_Processor_ID Export Processor	  */
	public void setEXP_Processor_ID (int EXP_Processor_ID)
	{
		if (EXP_Processor_ID < 1) 
			set_Value (COLUMNNAME_EXP_Processor_ID, null);
		else 
			set_Value (COLUMNNAME_EXP_Processor_ID, Integer.valueOf(EXP_Processor_ID));
	}

	/** Get Export Processor.
		@return Export Processor	  */
	public int getEXP_Processor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_Processor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Export Processor Type.
		@param EXP_Processor_Type_ID Export Processor Type	  */
	public void setEXP_Processor_Type_ID (int EXP_Processor_Type_ID)
	{
		if (EXP_Processor_Type_ID < 1) 
			set_Value (COLUMNNAME_EXP_Processor_Type_ID, null);
		else 
			set_Value (COLUMNNAME_EXP_Processor_Type_ID, Integer.valueOf(EXP_Processor_Type_ID));
	}

	/** Get Export Processor Type.
		@return Export Processor Type	  */
	public int getEXP_Processor_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_Processor_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Host.
		@param Host Host	  */
	public void setHost (String Host)
	{
		set_Value (COLUMNNAME_Host, Host);
	}

	/** Get Host.
		@return Host	  */
	public String getHost () 
	{
		return (String)get_Value(COLUMNNAME_Host);
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

	/** Set Password Info.
		@param PasswordInfo Password Info	  */
	public void setPasswordInfo (String PasswordInfo)
	{
		set_Value (COLUMNNAME_PasswordInfo, PasswordInfo);
	}

	/** Get Password Info.
		@return Password Info	  */
	public String getPasswordInfo () 
	{
		return (String)get_Value(COLUMNNAME_PasswordInfo);
	}

	/** Set Port.
		@param Port Port	  */
	public void setPort (int Port)
	{
		set_Value (COLUMNNAME_Port, Integer.valueOf(Port));
	}

	/** Get Port.
		@return Port	  */
	public int getPort () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Port);
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
}