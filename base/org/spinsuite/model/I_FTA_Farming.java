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

/** Generated Interface for FTA_Farming
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765)
 */
public interface I_FTA_Farming 
{

    /** TableName=FTA_Farming */
    public static final String Table_Name = "FTA_Farming";

    /** SPS_Table_ID=50213 */
    public static final int SPS_Table_ID = 50213;
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

    /** Column name Area */
    public static final String COLUMNNAME_Area = "Area";

	/** Set Area	  */
	public void setArea (BigDecimal Area);

	/** Get Area	  */
	public BigDecimal getArea();

    /** Column name Category_ID */
    public static final String COLUMNNAME_Category_ID = "Category_ID";

	/** Set Category	  */
	public void setCategory_ID (int Category_ID);

	/** Get Category	  */
	public int getCategory_ID();

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Sales Order Line.
	  * Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Sales Order Line.
	  * Sales Order Line
	  */
	public int getC_OrderLine_ID();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name EffectiveArea */
    public static final String COLUMNNAME_EffectiveArea = "EffectiveArea";

	/** Set Effective Area	  */
	public void setEffectiveArea (BigDecimal EffectiveArea);

	/** Get Effective Area	  */
	public BigDecimal getEffectiveArea();

    /** Column name EstimatedDate */
    public static final String COLUMNNAME_EstimatedDate = "EstimatedDate";

	/** Set Estimated Date	  */
	public void setEstimatedDate (Date EstimatedDate);

	/** Get Estimated Date	  */
	public Date getEstimatedDate();

    /** Column name EstimatedQty */
    public static final String COLUMNNAME_EstimatedQty = "EstimatedQty";

	/** Set Estimated Qty	  */
	public void setEstimatedQty (BigDecimal EstimatedQty);

	/** Get Estimated Qty	  */
	public BigDecimal getEstimatedQty();

    /** Column name EstimatedYield */
    public static final String COLUMNNAME_EstimatedYield = "EstimatedYield";

	/** Set Estimated Yield	  */
	public void setEstimatedYield (BigDecimal EstimatedYield);

	/** Get Estimated Yield	  */
	public BigDecimal getEstimatedYield();

    /** Column name FarmingValidate */
    public static final String COLUMNNAME_FarmingValidate = "FarmingValidate";

	/** Set Farming Validate	  */
	public void setFarmingValidate (String FarmingValidate);

	/** Get Farming Validate	  */
	public String getFarmingValidate();

    /** Column name FinancingType */
    public static final String COLUMNNAME_FinancingType = "FinancingType";

	/** Set Financing Type	  */
	public void setFinancingType (String FinancingType);

	/** Get Financing Type	  */
	public String getFinancingType();

    /** Column name FTA_FarmDivision_ID */
    public static final String COLUMNNAME_FTA_FarmDivision_ID = "FTA_FarmDivision_ID";

	/** Set Farm Division	  */
	public void setFTA_FarmDivision_ID (int FTA_FarmDivision_ID);

	/** Get Farm Division	  */
	public int getFTA_FarmDivision_ID();

    /** Column name FTA_FarmerCredit_ID */
    public static final String COLUMNNAME_FTA_FarmerCredit_ID = "FTA_FarmerCredit_ID";

	/** Set Credit/Loan.
	  * Farmer Credit or Loan
	  */
	public void setFTA_FarmerCredit_ID (int FTA_FarmerCredit_ID);

	/** Get Credit/Loan.
	  * Farmer Credit or Loan
	  */
	public int getFTA_FarmerCredit_ID();

    /** Column name FTA_Farming_ID */
    public static final String COLUMNNAME_FTA_Farming_ID = "FTA_Farming_ID";

	/** Set Farming	  */
	public void setFTA_Farming_ID (int FTA_Farming_ID);

	/** Get Farming	  */
	public int getFTA_Farming_ID();

    /** Column name GuideGenerate */
    public static final String COLUMNNAME_GuideGenerate = "GuideGenerate";

	/** Set Guide Generate	  */
	public void setGuideGenerate (String GuideGenerate);

	/** Get Guide Generate	  */
	public String getGuideGenerate();

    /** Column name HarvestEndDate */
    public static final String COLUMNNAME_HarvestEndDate = "HarvestEndDate";

	/** Set Harvest End Date	  */
	public void setHarvestEndDate (Date HarvestEndDate);

	/** Get Harvest End Date	  */
	public Date getHarvestEndDate();

    /** Column name HarvestStartDate */
    public static final String COLUMNNAME_HarvestStartDate = "HarvestStartDate";

	/** Set Harvest Start Date	  */
	public void setHarvestStartDate (Date HarvestStartDate);

	/** Get Harvest Start Date	  */
	public Date getHarvestStartDate();

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

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

    /** Column name MaxQty */
    public static final String COLUMNNAME_MaxQty = "MaxQty";

	/** Set Max Quantity	  */
	public void setMaxQty (BigDecimal MaxQty);

	/** Get Max Quantity	  */
	public BigDecimal getMaxQty();

    /** Column name PlantingCycle_ID */
    public static final String COLUMNNAME_PlantingCycle_ID = "PlantingCycle_ID";

	/** Set Planting Cycle	  */
	public void setPlantingCycle_ID (int PlantingCycle_ID);

	/** Get Planting Cycle	  */
	public int getPlantingCycle_ID();

    /** Column name PlantingEndDate */
    public static final String COLUMNNAME_PlantingEndDate = "PlantingEndDate";

	/** Set Planting End Date	  */
	public void setPlantingEndDate (Date PlantingEndDate);

	/** Get Planting End Date	  */
	public Date getPlantingEndDate();

    /** Column name PlantingStartDate */
    public static final String COLUMNNAME_PlantingStartDate = "PlantingStartDate";

	/** Set Planting Start Date	  */
	public void setPlantingStartDate (Date PlantingStartDate);

	/** Get Planting Start Date	  */
	public Date getPlantingStartDate();

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

    /** Column name Re_EstimatedQty */
    public static final String COLUMNNAME_Re_EstimatedQty = "Re_EstimatedQty";

	/** Set Re-Estimated Qty	  */
	public void setRe_EstimatedQty (BigDecimal Re_EstimatedQty);

	/** Get Re-Estimated Qty	  */
	public BigDecimal getRe_EstimatedQty();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Date StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Date getStartDate();

    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/** Set Status.
	  * Status of the currently running check
	  */
	public void setStatus (String Status);

	/** Get Status.
	  * Status of the currently running check
	  */
	public String getStatus();

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
