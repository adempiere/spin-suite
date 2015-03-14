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

/** Generated Model for SPS_SyncTable
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_SPS_SyncTable extends PO implements I_SPS_SyncTable {
    /** Standard Constructor */
    public X_SPS_SyncTable (Context ctx, int SPS_SyncTable_ID, DB conn)
    {
      super (ctx, SPS_SyncTable_ID, conn);
      /** if (SPS_SyncTable_ID == 0)
        {
			setSPS_SyncTable_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SPS_SyncTable (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_SPS_SyncTable[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Synchronized.
		@param IsSynchronized Synchronized	  */
	public void setIsSynchronized (Object IsSynchronized)
	{
		set_Value (COLUMNNAME_IsSynchronized, IsSynchronized);
	}

	/** Get Synchronized.
		@return Synchronized	  */
	public Object getIsSynchronized () 
	{
				return get_Value(COLUMNNAME_IsSynchronized);
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

	/** Set Synchronization Table ID.
		@param SPS_SyncTable_ID Synchronization Table ID	  */
	public void setSPS_SyncTable_ID (int SPS_SyncTable_ID)
	{
		if (SPS_SyncTable_ID < 1) 
			set_Value (COLUMNNAME_SPS_SyncTable_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_SyncTable_ID, Integer.valueOf(SPS_SyncTable_ID));
	}

	/** Get Synchronization Table ID.
		@return Synchronization Table ID	  */
	public int getSPS_SyncTable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_SyncTable_ID);
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

	/** Set Synchronization Record ID.
		@param SyncRecord_ID Synchronization Record ID	  */
	public void setSyncRecord_ID (String SyncRecord_ID)
	{
		set_Value (COLUMNNAME_SyncRecord_ID, SyncRecord_ID);
	}

	/** Get Synchronization Record ID.
		@return Synchronization Record ID	  */
	public String getSyncRecord_ID () 
	{
		return (String)get_Value(COLUMNNAME_SyncRecord_ID);
	}
}