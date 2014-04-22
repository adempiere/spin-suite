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

/** Generated Model for CM_WebProject_Domain
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_CM_WebProject_Domain extends PO implements I_CM_WebProject_Domain
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_CM_WebProject_Domain (Context ctx, int CM_WebProject_Domain_ID, DB conn)
    {
      super (ctx, CM_WebProject_Domain_ID, conn);
      /** if (CM_WebProject_Domain_ID == 0)
        {
			setCM_WebProject_Domain_ID (0);
			setCM_WebProject_ID (0);
			setFQDN (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_WebProject_Domain (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_CM_WebProject_Domain[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Web Container.
		@param CM_Container_ID 
		Web Container contains content like images, text etc.
	  */
	public void setCM_Container_ID (int CM_Container_ID)
	{
		if (CM_Container_ID < 1) 
			set_Value (COLUMNNAME_CM_Container_ID, null);
		else 
			set_Value (COLUMNNAME_CM_Container_ID, Integer.valueOf(CM_Container_ID));
	}

	/** Get Web Container.
		@return Web Container contains content like images, text etc.
	  */
	public int getCM_Container_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Container_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WebProject Domain.
		@param CM_WebProject_Domain_ID 
		Web Project Domain handling
	  */
	public void setCM_WebProject_Domain_ID (int CM_WebProject_Domain_ID)
	{
		if (CM_WebProject_Domain_ID < 1) 
			set_Value (COLUMNNAME_CM_WebProject_Domain_ID, null);
		else 
			set_Value (COLUMNNAME_CM_WebProject_Domain_ID, Integer.valueOf(CM_WebProject_Domain_ID));
	}

	/** Get WebProject Domain.
		@return Web Project Domain handling
	  */
	public int getCM_WebProject_Domain_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_WebProject_Domain_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Project.
		@param CM_WebProject_ID 
		A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public void setCM_WebProject_ID (int CM_WebProject_ID)
	{
		if (CM_WebProject_ID < 1) 
			set_Value (COLUMNNAME_CM_WebProject_ID, null);
		else 
			set_Value (COLUMNNAME_CM_WebProject_ID, Integer.valueOf(CM_WebProject_ID));
	}

	/** Get Web Project.
		@return A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public int getCM_WebProject_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_WebProject_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Fully Qualified Domain Name.
		@param FQDN 
		Fully Qualified Domain Name i.e. www.comdivision.com
	  */
	public void setFQDN (String FQDN)
	{
		set_Value (COLUMNNAME_FQDN, FQDN);
	}

	/** Get Fully Qualified Domain Name.
		@return Fully Qualified Domain Name i.e. www.comdivision.com
	  */
	public String getFQDN () 
	{
		return (String)get_Value(COLUMNNAME_FQDN);
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
}