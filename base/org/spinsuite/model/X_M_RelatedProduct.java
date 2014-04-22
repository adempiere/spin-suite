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

/** Generated Model for M_RelatedProduct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_RelatedProduct extends PO implements I_M_RelatedProduct
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_RelatedProduct (Context ctx, int M_RelatedProduct_ID, DB conn)
    {
      super (ctx, M_RelatedProduct_ID, conn);
      /** if (M_RelatedProduct_ID == 0)
        {
			setM_Product_ID (0);
			setName (null);
			setRelatedProduct_ID (0);
			setRelatedProductType (null);
        } */
    }

    /** Load Constructor */
    public X_M_RelatedProduct (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_RelatedProduct[")
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

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
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

	/** Set Related Product.
		@param RelatedProduct_ID 
		Related Product
	  */
	public void setRelatedProduct_ID (int RelatedProduct_ID)
	{
		if (RelatedProduct_ID < 1) 
			set_Value (COLUMNNAME_RelatedProduct_ID, null);
		else 
			set_Value (COLUMNNAME_RelatedProduct_ID, Integer.valueOf(RelatedProduct_ID));
	}

	/** Get Related Product.
		@return Related Product
	  */
	public int getRelatedProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RelatedProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** RelatedProductType AD_Reference_ID=313 */
	public static final int RELATEDPRODUCTTYPE_AD_Reference_ID=313;
	/** Web Promotion = P */
	public static final String RELATEDPRODUCTTYPE_WebPromotion = "P";
	/** Alternative = A */
	public static final String RELATEDPRODUCTTYPE_Alternative = "A";
	/** Supplemental = S */
	public static final String RELATEDPRODUCTTYPE_Supplemental = "S";
	/** Set Related Product Type.
		@param RelatedProductType Related Product Type	  */
	public void setRelatedProductType (String RelatedProductType)
	{

		set_Value (COLUMNNAME_RelatedProductType, RelatedProductType);
	}

	/** Get Related Product Type.
		@return Related Product Type	  */
	public String getRelatedProductType () 
	{
		return (String)get_Value(COLUMNNAME_RelatedProductType);
	}
}