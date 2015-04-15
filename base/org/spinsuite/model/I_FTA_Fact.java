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
package org.spinsuite.model;

import java.math.BigDecimal;
import java.util.Date;
import org.spinsuite.util.KeyNamePair;

/** Generated Interface for FTA_Fact
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_FTA_Fact 
{

    /** TableName=FTA_Fact */
    public static final String Table_Name = "FTA_Fact";

    /** SPS_Table_ID=50295 */
    public static final int SPS_Table_ID = 50295;
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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

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

    /** Column name FTA_CDL_Category_ID */
    public static final String COLUMNNAME_FTA_CDL_Category_ID = "FTA_CDL_Category_ID";

	/** Set Credit Definition Line Category	  */
	public void setFTA_CDL_Category_ID (int FTA_CDL_Category_ID);

	/** Get Credit Definition Line Category	  */
	public int getFTA_CDL_Category_ID();

    /** Column name FTA_CreditDefinition_ID */
    public static final String COLUMNNAME_FTA_CreditDefinition_ID = "FTA_CreditDefinition_ID";

	/** Set Credit Definition	  */
	public void setFTA_CreditDefinition_ID (int FTA_CreditDefinition_ID);

	/** Get Credit Definition	  */
	public int getFTA_CreditDefinition_ID();

    /** Column name FTA_CreditDefinitionLine_ID */
    public static final String COLUMNNAME_FTA_CreditDefinitionLine_ID = "FTA_CreditDefinitionLine_ID";

	/** Set Credit Definition Line	  */
	public void setFTA_CreditDefinitionLine_ID (int FTA_CreditDefinitionLine_ID);

	/** Get Credit Definition Line	  */
	public int getFTA_CreditDefinitionLine_ID();

    /** Column name FTA_Fact_ID */
    public static final String COLUMNNAME_FTA_Fact_ID = "FTA_Fact_ID";

	/** Set Farmer Credit Fact	  */
	public void setFTA_Fact_ID (int FTA_Fact_ID);

	/** Get Farmer Credit Fact	  */
	public int getFTA_Fact_ID();

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

    /** Column name IsCreditFactManual */
    public static final String COLUMNNAME_IsCreditFactManual = "IsCreditFactManual";

	/** Set Is Credit Fact Manual	  */
	public void setIsCreditFactManual (boolean IsCreditFactManual);

	/** Get Is Credit Fact Manual	  */
	public boolean isCreditFactManual();

    /** Column name Line_ID */
    public static final String COLUMNNAME_Line_ID = "Line_ID";

	/** Set Line ID.
	  * Transaction line ID (internal)
	  */
	public void setLine_ID (int Line_ID);

	/** Get Line ID.
	  * Transaction line ID (internal)
	  */
	public int getLine_ID();

    /** Column name Multiplier */
    public static final String COLUMNNAME_Multiplier = "Multiplier";

	/** Set Multiplier.
	  * Type Multiplier (Credit = -1)
	  */
	public void setMultiplier (BigDecimal Multiplier);

	/** Get Multiplier.
	  * Type Multiplier (Credit = -1)
	  */
	public BigDecimal getMultiplier();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

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
