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
package org.spinsuite.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.util.KeyNamePair;

/** Generated Interface for FTA_ProductsToApply
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765)
 */
public interface I_FTA_ProductsToApply 
{

    /** TableName=FTA_ProductsToApply */
    public static final String Table_Name = "FTA_ProductsToApply";

    /** SPS_Table_ID=50222 */
    public static final int SPS_Table_ID = 50222;
    KeyNamePair Model = new KeyNamePair(SPS_Table_ID, Table_Name);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Date getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Date DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Date getDateFrom();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Date To.
	  * End date of a date range
	  */
	public void setDateTo (Date DateTo);

	/** Get Date To.
	  * End date of a date range
	  */
	public Date getDateTo();

    /** Column name Dosage_Uom_ID */
    public static final String COLUMNNAME_Dosage_Uom_ID = "Dosage_Uom_ID";

	/** Set Dosage Uom	  */
	public void setDosage_Uom_ID (int Dosage_Uom_ID);

	/** Get Dosage Uom	  */
	public int getDosage_Uom_ID();

    /** Column name FTA_ProductsToApply_ID */
    public static final String COLUMNNAME_FTA_ProductsToApply_ID = "FTA_ProductsToApply_ID";

	/** Set Products To Apply	  */
	public void setFTA_ProductsToApply_ID (int FTA_ProductsToApply_ID);

	/** Get Products To Apply	  */
	public int getFTA_ProductsToApply_ID();

    /** Column name FTA_TechnicalForm_ID */
    public static final String COLUMNNAME_FTA_TechnicalForm_ID = "FTA_TechnicalForm_ID";

	/** Set Technical Form	  */
	public void setFTA_TechnicalForm_ID (int FTA_TechnicalForm_ID);

	/** Get Technical Form	  */
	public int getFTA_TechnicalForm_ID();

    /** Column name FTA_TechnicalFormLine_ID */
    public static final String COLUMNNAME_FTA_TechnicalFormLine_ID = "FTA_TechnicalFormLine_ID";

	/** Set Technical Form Line	  */
	public void setFTA_TechnicalFormLine_ID (int FTA_TechnicalFormLine_ID);

	/** Get Technical Form Line	  */
	public int getFTA_TechnicalFormLine_ID();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsApplied */
    public static final String COLUMNNAME_IsApplied = "IsApplied";

	/** Set Applied	  */
	public void setIsApplied (boolean IsApplied);

	/** Get Applied	  */
	public boolean isApplied();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name QtyDosage */
    public static final String COLUMNNAME_QtyDosage = "QtyDosage";

	/** Set Qty Dosage	  */
	public void setQtyDosage (BigDecimal QtyDosage);

	/** Get Qty Dosage	  */
	public BigDecimal getQtyDosage();

    /** Column name QtySuggested */
    public static final String COLUMNNAME_QtySuggested = "QtySuggested";

	/** Set Qty Suggested	  */
	public void setQtySuggested (BigDecimal QtySuggested);

	/** Get Qty Suggested	  */
	public BigDecimal getQtySuggested();

    /** Column name Suggested_Uom_ID */
    public static final String COLUMNNAME_Suggested_Uom_ID = "Suggested_Uom_ID";

	/** Set Suggested Uom	  */
	public void setSuggested_Uom_ID (int Suggested_Uom_ID);

	/** Get Suggested Uom	  */
	public int getSuggested_Uom_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Date getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
