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

/** Generated Model for U_RoleMenu
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_U_RoleMenu extends PO implements I_U_RoleMenu
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_U_RoleMenu (Context ctx, int U_RoleMenu_ID, DB conn)
    {
      super (ctx, U_RoleMenu_ID, conn);
      /** if (U_RoleMenu_ID == 0)
        {
			setAD_Role_ID (0);
			setU_RoleMenu_ID (0);
			setU_WebMenu_ID (0);
        } */
    }

    /** Load Constructor */
    public X_U_RoleMenu (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_U_RoleMenu[")
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

	/** Set Role Menu.
		@param U_RoleMenu_ID Role Menu	  */
	public void setU_RoleMenu_ID (int U_RoleMenu_ID)
	{
		if (U_RoleMenu_ID < 1) 
			set_Value (COLUMNNAME_U_RoleMenu_ID, null);
		else 
			set_Value (COLUMNNAME_U_RoleMenu_ID, Integer.valueOf(U_RoleMenu_ID));
	}

	/** Get Role Menu.
		@return Role Menu	  */
	public int getU_RoleMenu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_U_RoleMenu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Menu.
		@param U_WebMenu_ID Web Menu	  */
	public void setU_WebMenu_ID (int U_WebMenu_ID)
	{
		if (U_WebMenu_ID < 1) 
			set_Value (COLUMNNAME_U_WebMenu_ID, null);
		else 
			set_Value (COLUMNNAME_U_WebMenu_ID, Integer.valueOf(U_WebMenu_ID));
	}

	/** Get Web Menu.
		@return Web Menu	  */
	public int getU_WebMenu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_U_WebMenu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}