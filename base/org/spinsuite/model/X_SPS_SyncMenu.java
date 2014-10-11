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

/** Generated Model for SPS_SyncMenu
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_SPS_SyncMenu extends PO implements I_SPS_SyncMenu
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141011L;

    /** Standard Constructor */
    public X_SPS_SyncMenu (Context ctx, int SPS_SyncMenu_ID, DB conn)
    {
      super (ctx, SPS_SyncMenu_ID, conn);
      /** if (SPS_SyncMenu_ID == 0)
        {
			setEntityType (null);
// ECA01
			setIsSummary (false);
// N
			setName (null);
			setSPS_SyncMenu_ID (0);
			setSPS_Table_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SPS_SyncMenu (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_SPS_SyncMenu[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Rule After Run.
		@param AD_RuleAfter_ID Rule After Run	  */
	public void setAD_RuleAfter_ID (int AD_RuleAfter_ID)
	{
		if (AD_RuleAfter_ID < 1) 
			set_Value (COLUMNNAME_AD_RuleAfter_ID, null);
		else 
			set_Value (COLUMNNAME_AD_RuleAfter_ID, Integer.valueOf(AD_RuleAfter_ID));
	}

	/** Get Rule After Run.
		@return Rule After Run	  */
	public int getAD_RuleAfter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_RuleAfter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rule Before Run.
		@param AD_RuleBefore_ID Rule Before Run	  */
	public void setAD_RuleBefore_ID (int AD_RuleBefore_ID)
	{
		if (AD_RuleBefore_ID < 1) 
			set_Value (COLUMNNAME_AD_RuleBefore_ID, null);
		else 
			set_Value (COLUMNNAME_AD_RuleBefore_ID, Integer.valueOf(AD_RuleBefore_ID));
	}

	/** Get Rule Before Run.
		@return Rule Before Run	  */
	public int getAD_RuleBefore_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_RuleBefore_ID);
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

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Error Image URL.
		@param ErrImgUrl Error Image URL	  */
	public void setErrImgUrl (String ErrImgUrl)
	{
		set_Value (COLUMNNAME_ErrImgUrl, ErrImgUrl);
	}

	/** Get Error Image URL.
		@return Error Image URL	  */
	public String getErrImgUrl () 
	{
		return (String)get_Value(COLUMNNAME_ErrImgUrl);
	}

	/** Set Image URL.
		@param ImageURL 
		URL of  image
	  */
	public void setImageURL (String ImageURL)
	{
		set_Value (COLUMNNAME_ImageURL, ImageURL);
	}

	/** Get Image URL.
		@return URL of  image
	  */
	public String getImageURL () 
	{
		return (String)get_Value(COLUMNNAME_ImageURL);
	}

	/** Set Summary Level.
		@param IsSummary 
		This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Sync/Option Menu.
		@param SPS_SyncMenu_ID Sync/Option Menu	  */
	public void setSPS_SyncMenu_ID (int SPS_SyncMenu_ID)
	{
		if (SPS_SyncMenu_ID < 1) 
			set_Value (COLUMNNAME_SPS_SyncMenu_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_SyncMenu_ID, Integer.valueOf(SPS_SyncMenu_ID));
	}

	/** Get Sync/Option Menu.
		@return Sync/Option Menu	  */
	public int getSPS_SyncMenu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_SyncMenu_ID);
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

	/** Set Sql WHERE.
		@param WhereClause 
		Fully qualified SQL WHERE clause
	  */
	public void setWhereClause (String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	/** Get Sql WHERE.
		@return Fully qualified SQL WHERE clause
	  */
	public String getWhereClause () 
	{
		return (String)get_Value(COLUMNNAME_WhereClause);
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

	/** Set Web Service Type.
		@param WS_WebServiceType_ID Web Service Type	  */
	public void setWS_WebServiceType_ID (int WS_WebServiceType_ID)
	{
		if (WS_WebServiceType_ID < 1) 
			set_Value (COLUMNNAME_WS_WebServiceType_ID, null);
		else 
			set_Value (COLUMNNAME_WS_WebServiceType_ID, Integer.valueOf(WS_WebServiceType_ID));
	}

	/** Get Web Service Type.
		@return Web Service Type	  */
	public int getWS_WebServiceType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WS_WebServiceType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}