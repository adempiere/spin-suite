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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;

/** Generated Model for FTA_ProductsToApply
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_FTA_ProductsToApply extends PO implements I_FTA_ProductsToApply
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140922L;

    /** Standard Constructor */
    public X_FTA_ProductsToApply (Context ctx, int FTA_ProductsToApply_ID, DB conn)
    {
      super (ctx, FTA_ProductsToApply_ID, conn);
      /** if (FTA_ProductsToApply_ID == 0)
        {
			setC_UOM_ID (0);
			setDosage_Uom_ID (0);
			setFTA_ProductsToApply_ID (0);
			setFTA_TechnicalForm_ID (0);
			setM_Product_ID (0);
			setQty (Env.ZERO);
			setQtyDosage (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_FTA_ProductsToApply (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_FTA_ProductsToApply[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Date DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Date getDateFrom () 
	{
		return (Date)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Date DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Date getDateTo () 
	{
		return (Date)get_Value(COLUMNNAME_DateTo);
	}

	/** Set Dosage Uom.
		@param Dosage_Uom_ID Dosage Uom	  */
	public void setDosage_Uom_ID (int Dosage_Uom_ID)
	{
		if (Dosage_Uom_ID < 1) 
			set_Value (COLUMNNAME_Dosage_Uom_ID, null);
		else 
			set_Value (COLUMNNAME_Dosage_Uom_ID, Integer.valueOf(Dosage_Uom_ID));
	}

	/** Get Dosage Uom.
		@return Dosage Uom	  */
	public int getDosage_Uom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Dosage_Uom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Products To Apply.
		@param FTA_ProductsToApply_ID Products To Apply	  */
	public void setFTA_ProductsToApply_ID (int FTA_ProductsToApply_ID)
	{
		if (FTA_ProductsToApply_ID < 1) 
			set_Value (COLUMNNAME_FTA_ProductsToApply_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_ProductsToApply_ID, Integer.valueOf(FTA_ProductsToApply_ID));
	}

	/** Get Products To Apply.
		@return Products To Apply	  */
	public int getFTA_ProductsToApply_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_ProductsToApply_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Technical Form.
		@param FTA_TechnicalForm_ID Technical Form	  */
	public void setFTA_TechnicalForm_ID (int FTA_TechnicalForm_ID)
	{
		if (FTA_TechnicalForm_ID < 1) 
			set_Value (COLUMNNAME_FTA_TechnicalForm_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_TechnicalForm_ID, Integer.valueOf(FTA_TechnicalForm_ID));
	}

	/** Get Technical Form.
		@return Technical Form	  */
	public int getFTA_TechnicalForm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_TechnicalForm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Technical Form Line.
		@param FTA_TechnicalFormLine_ID Technical Form Line	  */
	public void setFTA_TechnicalFormLine_ID (int FTA_TechnicalFormLine_ID)
	{
		if (FTA_TechnicalFormLine_ID < 1) 
			set_Value (COLUMNNAME_FTA_TechnicalFormLine_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_TechnicalFormLine_ID, Integer.valueOf(FTA_TechnicalFormLine_ID));
	}

	/** Get Technical Form Line.
		@return Technical Form Line	  */
	public int getFTA_TechnicalFormLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_TechnicalFormLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Applied.
		@param IsApplied Applied	  */
	public void setIsApplied (boolean IsApplied)
	{
		set_Value (COLUMNNAME_IsApplied, Boolean.valueOf(IsApplied));
	}

	/** Get Applied.
		@return Applied	  */
	public boolean isApplied () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplied);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Dosage.
		@param QtyDosage Qty Dosage	  */
	public void setQtyDosage (BigDecimal QtyDosage)
	{
		set_Value (COLUMNNAME_QtyDosage, QtyDosage);
	}

	/** Get Qty Dosage.
		@return Qty Dosage	  */
	public BigDecimal getQtyDosage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDosage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Suggested.
		@param QtySuggested Qty Suggested	  */
	public void setQtySuggested (BigDecimal QtySuggested)
	{
		set_Value (COLUMNNAME_QtySuggested, QtySuggested);
	}

	/** Get Qty Suggested.
		@return Qty Suggested	  */
	public BigDecimal getQtySuggested () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySuggested);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Suggested Uom.
		@param Suggested_Uom_ID Suggested Uom	  */
	public void setSuggested_Uom_ID (int Suggested_Uom_ID)
	{
		if (Suggested_Uom_ID < 1) 
			set_Value (COLUMNNAME_Suggested_Uom_ID, null);
		else 
			set_Value (COLUMNNAME_Suggested_Uom_ID, Integer.valueOf(Suggested_Uom_ID));
	}

	/** Get Suggested Uom.
		@return Suggested Uom	  */
	public int getSuggested_Uom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Suggested_Uom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}