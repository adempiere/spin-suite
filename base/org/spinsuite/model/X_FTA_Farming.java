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
import java.math.BigDecimal;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for FTA_Farming
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_FTA_Farming extends PO implements I_FTA_Farming {
    /** Standard Constructor */
    public X_FTA_Farming (Context ctx, int FTA_Farming_ID, DB conn)
    {
      super (ctx, FTA_Farming_ID, conn);
      /** if (FTA_Farming_ID == 0)
        {
			setArea (Env.ZERO);
			setCategory_ID (0);
			setFinancingType (null);
			setFTA_FarmDivision_ID (0);
			setFTA_Farming_ID (0);
			setPlantingCycle_ID (0);
			setStartDate (null);
			setStatus (null);
// M
        } */
    }

    /** Load Constructor */
    public X_FTA_Farming (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_FTA_Farming[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Area.
		@param Area Area	  */
	public void setArea (BigDecimal Area)
	{
		set_Value (COLUMNNAME_Area, Area);
	}

	/** Get Area.
		@return Area	  */
	public BigDecimal getArea () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Area);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Category.
		@param Category_ID Category	  */
	public void setCategory_ID (int Category_ID)
	{
		if (Category_ID < 1) 
			set_Value (COLUMNNAME_Category_ID, null);
		else 
			set_Value (COLUMNNAME_Category_ID, Integer.valueOf(Category_ID));
	}

	/** Get Category.
		@return Category	  */
	public int getCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
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

	/** Set Effective Area.
		@param EffectiveArea Effective Area	  */
	public void setEffectiveArea (BigDecimal EffectiveArea)
	{
		set_Value (COLUMNNAME_EffectiveArea, EffectiveArea);
	}

	/** Get Effective Area.
		@return Effective Area	  */
	public BigDecimal getEffectiveArea () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EffectiveArea);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Estimated Date.
		@param EstimatedDate Estimated Date	  */
	public void setEstimatedDate (Date EstimatedDate)
	{
		set_Value (COLUMNNAME_EstimatedDate, EstimatedDate);
	}

	/** Get Estimated Date.
		@return Estimated Date	  */
	public Date getEstimatedDate () 
	{
		return (Date)get_Value(COLUMNNAME_EstimatedDate);
	}

	/** Set Estimated Qty.
		@param EstimatedQty Estimated Qty	  */
	public void setEstimatedQty (BigDecimal EstimatedQty)
	{
		set_Value (COLUMNNAME_EstimatedQty, EstimatedQty);
	}

	/** Get Estimated Qty.
		@return Estimated Qty	  */
	public BigDecimal getEstimatedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EstimatedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Estimated Yield.
		@param EstimatedYield Estimated Yield	  */
	public void setEstimatedYield (BigDecimal EstimatedYield)
	{
		set_Value (COLUMNNAME_EstimatedYield, EstimatedYield);
	}

	/** Get Estimated Yield.
		@return Estimated Yield	  */
	public BigDecimal getEstimatedYield () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EstimatedYield);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Farming Validate.
		@param FarmingValidate Farming Validate	  */
	public void setFarmingValidate (String FarmingValidate)
	{
		set_Value (COLUMNNAME_FarmingValidate, FarmingValidate);
	}

	/** Get Farming Validate.
		@return Farming Validate	  */
	public String getFarmingValidate () 
	{
		return (String)get_Value(COLUMNNAME_FarmingValidate);
	}

	/** FinancingType AD_Reference_ID=53515 */
	public static final int FINANCINGTYPE_AD_Reference_ID=53515;
	/** Self-financed = S */
	public static final String FINANCINGTYPE_Self_Financed = "S";
	/** Apply Financing = A */
	public static final String FINANCINGTYPE_ApplyFinancing = "A";
	/** Set Financing Type.
		@param FinancingType Financing Type	  */
	public void setFinancingType (String FinancingType)
	{

		set_Value (COLUMNNAME_FinancingType, FinancingType);
	}

	/** Get Financing Type.
		@return Financing Type	  */
	public String getFinancingType () 
	{
		return (String)get_Value(COLUMNNAME_FinancingType);
	}

	/** Set Farm Division.
		@param FTA_FarmDivision_ID Farm Division	  */
	public void setFTA_FarmDivision_ID (int FTA_FarmDivision_ID)
	{
		if (FTA_FarmDivision_ID < 1) 
			set_Value (COLUMNNAME_FTA_FarmDivision_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_FarmDivision_ID, Integer.valueOf(FTA_FarmDivision_ID));
	}

	/** Get Farm Division.
		@return Farm Division	  */
	public int getFTA_FarmDivision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_FarmDivision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit/Loan.
		@param FTA_FarmerCredit_ID 
		Farmer Credit or Loan
	  */
	public void setFTA_FarmerCredit_ID (int FTA_FarmerCredit_ID)
	{
		if (FTA_FarmerCredit_ID < 1) 
			set_Value (COLUMNNAME_FTA_FarmerCredit_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_FarmerCredit_ID, Integer.valueOf(FTA_FarmerCredit_ID));
	}

	/** Get Credit/Loan.
		@return Farmer Credit or Loan
	  */
	public int getFTA_FarmerCredit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_FarmerCredit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getFTA_FarmerCredit_ID()));
    }

	/** Set Farming.
		@param FTA_Farming_ID Farming	  */
	public void setFTA_Farming_ID (int FTA_Farming_ID)
	{
		if (FTA_Farming_ID < 1) 
			set_Value (COLUMNNAME_FTA_Farming_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_Farming_ID, Integer.valueOf(FTA_Farming_ID));
	}

	/** Get Farming.
		@return Farming	  */
	public int getFTA_Farming_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_Farming_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Guide Generate.
		@param GuideGenerate Guide Generate	  */
	public void setGuideGenerate (String GuideGenerate)
	{
		set_Value (COLUMNNAME_GuideGenerate, GuideGenerate);
	}

	/** Get Guide Generate.
		@return Guide Generate	  */
	public String getGuideGenerate () 
	{
		return (String)get_Value(COLUMNNAME_GuideGenerate);
	}

	/** Set Harvest End Date.
		@param HarvestEndDate Harvest End Date	  */
	public void setHarvestEndDate (Date HarvestEndDate)
	{
		set_Value (COLUMNNAME_HarvestEndDate, HarvestEndDate);
	}

	/** Get Harvest End Date.
		@return Harvest End Date	  */
	public Date getHarvestEndDate () 
	{
		return (Date)get_Value(COLUMNNAME_HarvestEndDate);
	}

	/** Set Harvest Start Date.
		@param HarvestStartDate Harvest Start Date	  */
	public void setHarvestStartDate (Date HarvestStartDate)
	{
		set_Value (COLUMNNAME_HarvestStartDate, HarvestStartDate);
	}

	/** Get Harvest Start Date.
		@return Harvest Start Date	  */
	public Date getHarvestStartDate () 
	{
		return (Date)get_Value(COLUMNNAME_HarvestStartDate);
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Max Quantity.
		@param MaxQty Max Quantity	  */
	public void setMaxQty (BigDecimal MaxQty)
	{
		set_Value (COLUMNNAME_MaxQty, MaxQty);
	}

	/** Get Max Quantity.
		@return Max Quantity	  */
	public BigDecimal getMaxQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Planting Cycle.
		@param PlantingCycle_ID Planting Cycle	  */
	public void setPlantingCycle_ID (int PlantingCycle_ID)
	{
		if (PlantingCycle_ID < 1) 
			set_Value (COLUMNNAME_PlantingCycle_ID, null);
		else 
			set_Value (COLUMNNAME_PlantingCycle_ID, Integer.valueOf(PlantingCycle_ID));
	}

	/** Get Planting Cycle.
		@return Planting Cycle	  */
	public int getPlantingCycle_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PlantingCycle_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Planting End Date.
		@param PlantingEndDate Planting End Date	  */
	public void setPlantingEndDate (Date PlantingEndDate)
	{
		set_Value (COLUMNNAME_PlantingEndDate, PlantingEndDate);
	}

	/** Get Planting End Date.
		@return Planting End Date	  */
	public Date getPlantingEndDate () 
	{
		return (Date)get_Value(COLUMNNAME_PlantingEndDate);
	}

	/** Set Planting Start Date.
		@param PlantingStartDate Planting Start Date	  */
	public void setPlantingStartDate (Date PlantingStartDate)
	{
		set_Value (COLUMNNAME_PlantingStartDate, PlantingStartDate);
	}

	/** Get Planting Start Date.
		@return Planting Start Date	  */
	public Date getPlantingStartDate () 
	{
		return (Date)get_Value(COLUMNNAME_PlantingStartDate);
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

	/** Set Re-Estimated Qty.
		@param Re_EstimatedQty Re-Estimated Qty	  */
	public void setRe_EstimatedQty (BigDecimal Re_EstimatedQty)
	{
		set_Value (COLUMNNAME_Re_EstimatedQty, Re_EstimatedQty);
	}

	/** Get Re-Estimated Qty.
		@return Re-Estimated Qty	  */
	public BigDecimal getRe_EstimatedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Re_EstimatedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Date StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Date getStartDate () 
	{
		return (Date)get_Value(COLUMNNAME_StartDate);
	}

	/** Status AD_Reference_ID=53531 */
	public static final int STATUS_AD_Reference_ID=53531;
	/** Active = A */
	public static final String STATUS_Active = "A";
	/** Harvesting = H */
	public static final String STATUS_Harvesting = "H";
	/** Damaged = D */
	public static final String STATUS_Damaged = "D";
	/** Making = M */
	public static final String STATUS_Making = "M";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}
}