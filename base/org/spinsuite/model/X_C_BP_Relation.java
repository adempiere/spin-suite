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

/** Generated Model for C_BP_Relation
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_BP_Relation extends PO implements I_C_BP_Relation
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_BP_Relation (Context ctx, int C_BP_Relation_ID, DB conn)
    {
      super (ctx, C_BP_Relation_ID, conn);
      /** if (C_BP_Relation_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartnerRelation_ID (0);
			setC_BPartnerRelation_Location_ID (0);
			setC_BP_Relation_ID (0);
			setIsBillTo (false);
			setIsPayFrom (false);
			setIsRemitTo (false);
			setIsShipTo (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_BP_Relation (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_BP_Relation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Related Partner.
		@param C_BPartnerRelation_ID 
		Related Business Partner
	  */
	public void setC_BPartnerRelation_ID (int C_BPartnerRelation_ID)
	{
		if (C_BPartnerRelation_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, Integer.valueOf(C_BPartnerRelation_ID));
	}

	/** Get Related Partner.
		@return Related Business Partner
	  */
	public int getC_BPartnerRelation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRelation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Related Partner Location.
		@param C_BPartnerRelation_Location_ID 
		Location of the related Business Partner
	  */
	public void setC_BPartnerRelation_Location_ID (int C_BPartnerRelation_Location_ID)
	{
		if (C_BPartnerRelation_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_Location_ID, Integer.valueOf(C_BPartnerRelation_Location_ID));
	}

	/** Get Related Partner Location.
		@return Location of the related Business Partner
	  */
	public int getC_BPartnerRelation_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRelation_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Partner Relation.
		@param C_BP_Relation_ID 
		Business Partner Relation
	  */
	public void setC_BP_Relation_ID (int C_BP_Relation_ID)
	{
		if (C_BP_Relation_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Relation_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Relation_ID, Integer.valueOf(C_BP_Relation_ID));
	}

	/** Get Partner Relation.
		@return Business Partner Relation
	  */
	public int getC_BP_Relation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Relation_ID);
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

	/** Set Invoice Address.
		@param IsBillTo 
		Business Partner Invoice/Bill Address
	  */
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Invoice Address.
		@return Business Partner Invoice/Bill Address
	  */
	public boolean isBillTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pay-From Address.
		@param IsPayFrom 
		Business Partner pays from that address and we'll send dunning letters there
	  */
	public void setIsPayFrom (boolean IsPayFrom)
	{
		set_Value (COLUMNNAME_IsPayFrom, Boolean.valueOf(IsPayFrom));
	}

	/** Get Pay-From Address.
		@return Business Partner pays from that address and we'll send dunning letters there
	  */
	public boolean isPayFrom () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Remit-To Address.
		@param IsRemitTo 
		Business Partner payment address
	  */
	public void setIsRemitTo (boolean IsRemitTo)
	{
		set_Value (COLUMNNAME_IsRemitTo, Boolean.valueOf(IsRemitTo));
	}

	/** Get Remit-To Address.
		@return Business Partner payment address
	  */
	public boolean isRemitTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemitTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ship Address.
		@param IsShipTo 
		Business Partner Shipment Address
	  */
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Ship Address.
		@return Business Partner Shipment Address
	  */
	public boolean isShipTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
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
}