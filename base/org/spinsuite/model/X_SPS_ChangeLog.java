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

/** Generated Model for SPS_ChangeLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_SPS_ChangeLog extends PO implements I_SPS_ChangeLog {
    /** Standard Constructor */
    public X_SPS_ChangeLog (Context ctx, int SPS_ChangeLog_ID, DB conn)
    {
      super (ctx, SPS_ChangeLog_ID, conn);
      /** if (SPS_ChangeLog_ID == 0)
        {
			setAD_Session_ID (0);
			setIsCustomization (false);
// N
			setRecord_ID (0);
			setSPS_ChangeLog_ID (0);
			setSPS_Column_ID (0);
			setSPS_Table_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SPS_ChangeLog (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_SPS_ChangeLog[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Session.
		@param AD_Session_ID 
		User Session Online or Web
	  */
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_Value (COLUMNNAME_AD_Session_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	/** Get Session.
		@return User Session Online or Web
	  */
	public int getAD_Session_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Session_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_Session_ID()));
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

	/** EventChangeLog AD_Reference_ID=53238 */
	public static final int EVENTCHANGELOG_AD_Reference_ID=53238;
	/** Insert = I */
	public static final String EVENTCHANGELOG_Insert = "I";
	/** Delete = D */
	public static final String EVENTCHANGELOG_Delete = "D";
	/** Update = U */
	public static final String EVENTCHANGELOG_Update = "U";
	/** Set Event Change Log.
		@param EventChangeLog 
		Type of Event in Change Log
	  */
	public void setEventChangeLog (String EventChangeLog)
	{

		set_Value (COLUMNNAME_EventChangeLog, EventChangeLog);
	}

	/** Get Event Change Log.
		@return Type of Event in Change Log
	  */
	public String getEventChangeLog () 
	{
		return (String)get_Value(COLUMNNAME_EventChangeLog);
	}

	/** Set Customization.
		@param IsCustomization 
		The change is a customization of the data dictionary and can be applied after Migration
	  */
	public void setIsCustomization (boolean IsCustomization)
	{
		set_Value (COLUMNNAME_IsCustomization, Boolean.valueOf(IsCustomization));
	}

	/** Get Customization.
		@return The change is a customization of the data dictionary and can be applied after Migration
	  */
	public boolean isCustomization () 
	{
		Object oo = get_Value(COLUMNNAME_IsCustomization);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Synchronized.
		@param IsSynchronized Synchronized	  */
	public void setIsSynchronized (boolean IsSynchronized)
	{
		set_Value (COLUMNNAME_IsSynchronized, Boolean.valueOf(IsSynchronized));
	}

	/** Get Synchronized.
		@return Synchronized	  */
	public boolean isSynchronized () 
	{
		Object oo = get_Value(COLUMNNAME_IsSynchronized);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set New Value.
		@param NewValue 
		New field value
	  */
	public void setNewValue (String NewValue)
	{
		set_Value (COLUMNNAME_NewValue, NewValue);
	}

	/** Get New Value.
		@return New field value
	  */
	public String getNewValue () 
	{
		return (String)get_Value(COLUMNNAME_NewValue);
	}

	/** Set Old Value.
		@param OldValue 
		The old file data
	  */
	public void setOldValue (String OldValue)
	{
		set_Value (COLUMNNAME_OldValue, OldValue);
	}

	/** Get Old Value.
		@return The old file data
	  */
	public String getOldValue () 
	{
		return (String)get_Value(COLUMNNAME_OldValue);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Redo.
		@param Redo Redo	  */
	public void setRedo (String Redo)
	{
		set_Value (COLUMNNAME_Redo, Redo);
	}

	/** Get Redo.
		@return Redo	  */
	public String getRedo () 
	{
		return (String)get_Value(COLUMNNAME_Redo);
	}

	/** Set Spin Suite Change Log.
		@param SPS_ChangeLog_ID Spin Suite Change Log	  */
	public void setSPS_ChangeLog_ID (int SPS_ChangeLog_ID)
	{
		if (SPS_ChangeLog_ID < 1) 
			set_Value (COLUMNNAME_SPS_ChangeLog_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_ChangeLog_ID, Integer.valueOf(SPS_ChangeLog_ID));
	}

	/** Get Spin Suite Change Log.
		@return Spin Suite Change Log	  */
	public int getSPS_ChangeLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_ChangeLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mobile Column.
		@param SPS_Column_ID Mobile Column	  */
	public void setSPS_Column_ID (int SPS_Column_ID)
	{
		if (SPS_Column_ID < 1) 
			set_Value (COLUMNNAME_SPS_Column_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_Column_ID, Integer.valueOf(SPS_Column_ID));
	}

	/** Get Mobile Column.
		@return Mobile Column	  */
	public int getSPS_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mobile Table.
		@param SPS_Table_ID Mobile Table	  */
	public void setSPS_Table_ID (int SPS_Table_ID)
	{
		if (SPS_Table_ID < 1) 
			set_Value (COLUMNNAME_SPS_Table_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_Table_ID, Integer.valueOf(SPS_Table_ID));
	}

	/** Get Mobile Table.
		@return Mobile Table	  */
	public int getSPS_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction.
		@param TrxName 
		Name of the transaction
	  */
	public void setTrxName (String TrxName)
	{
		set_Value (COLUMNNAME_TrxName, TrxName);
	}

	/** Get Transaction.
		@return Name of the transaction
	  */
	public String getTrxName () 
	{
		return (String)get_Value(COLUMNNAME_TrxName);
	}

	/** Set Undo.
		@param Undo Undo	  */
	public void setUndo (String Undo)
	{
		set_Value (COLUMNNAME_Undo, Undo);
	}

	/** Get Undo.
		@return Undo	  */
	public String getUndo () 
	{
		return (String)get_Value(COLUMNNAME_Undo);
	}
}