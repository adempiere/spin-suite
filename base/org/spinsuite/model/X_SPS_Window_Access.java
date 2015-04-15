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

/** Generated Model for SPS_Window_Access
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_SPS_Window_Access extends PO implements I_SPS_Window_Access {
    /** Standard Constructor */
    public X_SPS_Window_Access (Context ctx, int SPS_Window_Access_ID, DB conn)
    {
      super (ctx, SPS_Window_Access_ID, conn);
      /** if (SPS_Window_Access_ID == 0)
        {
			setAD_Role_ID (0);
			setIsReadWrite (false);
			setSPS_Window_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SPS_Window_Access (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_SPS_Window_Access[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Read Write.
		@param IsReadWrite 
		Field is read / write
	  */
	public void setIsReadWrite (boolean IsReadWrite)
	{
		set_Value (COLUMNNAME_IsReadWrite, Boolean.valueOf(IsReadWrite));
	}

	/** Get Read Write.
		@return Field is read / write
	  */
	public boolean isReadWrite () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadWrite);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Window Mobile.
		@param SPS_Window_ID Window Mobile	  */
	public void setSPS_Window_ID (int SPS_Window_ID)
	{
		if (SPS_Window_ID < 1) 
			set_Value (COLUMNNAME_SPS_Window_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_Window_ID, Integer.valueOf(SPS_Window_ID));
	}

	/** Get Window Mobile.
		@return Window Mobile	  */
	public int getSPS_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}