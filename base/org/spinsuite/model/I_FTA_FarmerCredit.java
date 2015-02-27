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

/** Generated Interface for FTA_FarmerCredit
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765)
 */
public interface I_FTA_FarmerCredit 
{

    /** TableName=FTA_FarmerCredit */
    public static final String Table_Name = "FTA_FarmerCredit";

    /** SPS_Table_ID=50217 */
    public static final int SPS_Table_ID = 50217;
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

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name AmtFarming */
    public static final String COLUMNNAME_AmtFarming = "AmtFarming";

	/** Set Farming Amount	  */
	public void setAmtFarming (BigDecimal AmtFarming);

	/** Get Farming Amount	  */
	public BigDecimal getAmtFarming();

    /** Column name ApprovedAmt */
    public static final String COLUMNNAME_ApprovedAmt = "ApprovedAmt";

	/** Set Approved Amount	  */
	public void setApprovedAmt (BigDecimal ApprovedAmt);

	/** Get Approved Amount	  */
	public BigDecimal getApprovedAmt();

    /** Column name ApprovedQty */
    public static final String COLUMNNAME_ApprovedQty = "ApprovedQty";

	/** Set Approved Quantity	  */
	public void setApprovedQty (BigDecimal ApprovedQty);

	/** Get Approved Quantity	  */
	public BigDecimal getApprovedQty();

    /** Column name BasedOnEffectiveQuantity */
    public static final String COLUMNNAME_BasedOnEffectiveQuantity = "BasedOnEffectiveQuantity";

	/** Set Based On Effective Quantity	  */
	public void setBasedOnEffectiveQuantity (boolean BasedOnEffectiveQuantity);

	/** Get Based On Effective Quantity	  */
	public boolean isBasedOnEffectiveQuantity();

    /** Column name Beneficiary_ID */
    public static final String COLUMNNAME_Beneficiary_ID = "Beneficiary_ID";

	/** Set Beneficiary.
	  * Business Partner to whom payment is made
	  */
	public void setBeneficiary_ID (int Beneficiary_ID);

	/** Get Beneficiary.
	  * Business Partner to whom payment is made
	  */
	public int getBeneficiary_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

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

    /** Column name CreditType */
    public static final String COLUMNNAME_CreditType = "CreditType";

	/** Set Credit Type.
	  * If is Credit, Loan and other
	  */
	public void setCreditType (String CreditType);

	/** Get Credit Type.
	  * If is Credit, Loan and other
	  */
	public String getCreditType();

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Date DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Date getDateDoc();

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

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name EffectiveQty */
    public static final String COLUMNNAME_EffectiveQty = "EffectiveQty";

	/** Set Effective Quantity	  */
	public void setEffectiveQty (BigDecimal EffectiveQty);

	/** Get Effective Quantity	  */
	public BigDecimal getEffectiveQty();

    /** Column name FarmingAlloc */
    public static final String COLUMNNAME_FarmingAlloc = "FarmingAlloc";

	/** Set Farming Allocation.
	  * Farming Allocation
	  */
	public void setFarmingAlloc (String FarmingAlloc);

	/** Get Farming Allocation.
	  * Farming Allocation
	  */
	public String getFarmingAlloc();

    /** Column name FTA_CreditAct_ID */
    public static final String COLUMNNAME_FTA_CreditAct_ID = "FTA_CreditAct_ID";

	/** Set Credit Act	  */
	public void setFTA_CreditAct_ID (int FTA_CreditAct_ID);

	/** Get Credit Act	  */
	public int getFTA_CreditAct_ID();

    /** Column name FTA_CreditDefinition_ID */
    public static final String COLUMNNAME_FTA_CreditDefinition_ID = "FTA_CreditDefinition_ID";

	/** Set Credit Definition	  */
	public void setFTA_CreditDefinition_ID (int FTA_CreditDefinition_ID);

	/** Get Credit Definition	  */
	public int getFTA_CreditDefinition_ID();

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

    /** Column name GenerateBillOfEx */
    public static final String COLUMNNAME_GenerateBillOfEx = "GenerateBillOfEx";

	/** Set Generate Bill of Exchange	  */
	public void setGenerateBillOfEx (String GenerateBillOfEx);

	/** Get Generate Bill of Exchange	  */
	public String getGenerateBillOfEx();

    /** Column name GenerateOrder */
    public static final String COLUMNNAME_GenerateOrder = "GenerateOrder";

	/** Set Generate Order.
	  * Generate Order
	  */
	public void setGenerateOrder (String GenerateOrder);

	/** Get Generate Order.
	  * Generate Order
	  */
	public String getGenerateOrder();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsBillOfExchangeDocBased */
    public static final String COLUMNNAME_IsBillOfExchangeDocBased = "IsBillOfExchangeDocBased";

	/** Set Bill Of Exchange Document Based	  */
	public void setIsBillOfExchangeDocBased (boolean IsBillOfExchangeDocBased);

	/** Get Bill Of Exchange Document Based	  */
	public boolean isBillOfExchangeDocBased();

    /** Column name IsManagesPaymentProgram */
    public static final String COLUMNNAME_IsManagesPaymentProgram = "IsManagesPaymentProgram";

	/** Set Manages Payment Program	  */
	public void setIsManagesPaymentProgram (boolean IsManagesPaymentProgram);

	/** Get Manages Payment Program	  */
	public boolean isManagesPaymentProgram();

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

    /** Column name IsPayScheduleValid */
    public static final String COLUMNNAME_IsPayScheduleValid = "IsPayScheduleValid";

	/** Set Pay Schedule valid.
	  * Is the Payment Schedule is valid
	  */
	public void setIsPayScheduleValid (boolean IsPayScheduleValid);

	/** Get Pay Schedule valid.
	  * Is the Payment Schedule is valid
	  */
	public boolean isPayScheduleValid();

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

    /** Column name Parent_FarmerCredit_ID */
    public static final String COLUMNNAME_Parent_FarmerCredit_ID = "Parent_FarmerCredit_ID";

	/** Set Parent Credit/Loan.
	  * Parent Farmer Credit or Loan
	  */
	public void setParent_FarmerCredit_ID (int Parent_FarmerCredit_ID);

	/** Get Parent Credit/Loan.
	  * Parent Farmer Credit or Loan
	  */
	public int getParent_FarmerCredit_ID();

    /** Column name PreviousAmt */
    public static final String COLUMNNAME_PreviousAmt = "PreviousAmt";

	/** Set Previous Amt	  */
	public void setPreviousAmt (BigDecimal PreviousAmt);

	/** Get Previous Amt	  */
	public BigDecimal getPreviousAmt();

    /** Column name PreviousApprovedAmt */
    public static final String COLUMNNAME_PreviousApprovedAmt = "PreviousApprovedAmt";

	/** Set Previous Approved Amt	  */
	public void setPreviousApprovedAmt (BigDecimal PreviousApprovedAmt);

	/** Get Previous Approved Amt	  */
	public BigDecimal getPreviousApprovedAmt();

    /** Column name PreviousApprovedQty */
    public static final String COLUMNNAME_PreviousApprovedQty = "PreviousApprovedQty";

	/** Set Previous Approved Quantity	  */
	public void setPreviousApprovedQty (BigDecimal PreviousApprovedQty);

	/** Get Previous Approved Quantity	  */
	public BigDecimal getPreviousApprovedQty();

    /** Column name PreviousQty */
    public static final String COLUMNNAME_PreviousQty = "PreviousQty";

	/** Set Previous Qty	  */
	public void setPreviousQty (BigDecimal PreviousQty);

	/** Get Previous Qty	  */
	public BigDecimal getPreviousQty();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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

    /** Column name QtyFarming */
    public static final String COLUMNNAME_QtyFarming = "QtyFarming";

	/** Set Farming Quantity	  */
	public void setQtyFarming (BigDecimal QtyFarming);

	/** Get Farming Quantity	  */
	public BigDecimal getQtyFarming();

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
