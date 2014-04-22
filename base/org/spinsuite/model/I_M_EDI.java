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

/** Generated Interface for M_EDI
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765)
 */
public interface I_M_EDI 
{

    /** TableName=M_EDI */
    public static final String Table_Name = "M_EDI";

    /** SPS_Table_ID=1000153 */
    public static final int SPS_Table_ID = 1000153;
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

    /** Column name C_BP_EDI_ID */
    public static final String COLUMNNAME_C_BP_EDI_ID = "C_BP_EDI_ID";

	/** Set EDI Definition.
	  * Electronic Data Interchange
	  */
	public void setC_BP_EDI_ID (int C_BP_EDI_ID);

	/** Get EDI Definition.
	  * Electronic Data Interchange
	  */
	public int getC_BP_EDI_ID();

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

    /** Column name EDIStatus */
    public static final String COLUMNNAME_EDIStatus = "EDIStatus";

	/** Set EDI Status	  */
	public void setEDIStatus (String EDIStatus);

	/** Get EDI Status	  */
	public String getEDIStatus();

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

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

    /** Column name M_EDI_ID */
    public static final String COLUMNNAME_M_EDI_ID = "M_EDI_ID";

	/** Set EDI Transaction	  */
	public void setM_EDI_ID (int M_EDI_ID);

	/** Get EDI Transaction	  */
	public int getM_EDI_ID();

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

    /** Column name Reply_Price */
    public static final String COLUMNNAME_Reply_Price = "Reply_Price";

	/** Set Reply Price.
	  * Confirmed Price from EDI Partner
	  */
	public void setReply_Price (BigDecimal Reply_Price);

	/** Get Reply Price.
	  * Confirmed Price from EDI Partner
	  */
	public BigDecimal getReply_Price();

    /** Column name Reply_QtyAvailable */
    public static final String COLUMNNAME_Reply_QtyAvailable = "Reply_QtyAvailable";

	/** Set Reply Qty Available	  */
	public void setReply_QtyAvailable (BigDecimal Reply_QtyAvailable);

	/** Get Reply Qty Available	  */
	public BigDecimal getReply_QtyAvailable();

    /** Column name Reply_QtyConfirmed */
    public static final String COLUMNNAME_Reply_QtyConfirmed = "Reply_QtyConfirmed";

	/** Set Reply Qty Confirmed	  */
	public void setReply_QtyConfirmed (BigDecimal Reply_QtyConfirmed);

	/** Get Reply Qty Confirmed	  */
	public BigDecimal getReply_QtyConfirmed();

    /** Column name Reply_Received */
    public static final String COLUMNNAME_Reply_Received = "Reply_Received";

	/** Set Reply Received	  */
	public void setReply_Received (Date Reply_Received);

	/** Get Reply Received	  */
	public Date getReply_Received();

    /** Column name Reply_Remarks */
    public static final String COLUMNNAME_Reply_Remarks = "Reply_Remarks";

	/** Set Reply Remarks	  */
	public void setReply_Remarks (String Reply_Remarks);

	/** Get Reply Remarks	  */
	public String getReply_Remarks();

    /** Column name Reply_ShipDate */
    public static final String COLUMNNAME_Reply_ShipDate = "Reply_ShipDate";

	/** Set Reply Ship date	  */
	public void setReply_ShipDate (Date Reply_ShipDate);

	/** Get Reply Ship date	  */
	public Date getReply_ShipDate();

    /** Column name Request_Price */
    public static final String COLUMNNAME_Request_Price = "Request_Price";

	/** Set Request Price	  */
	public void setRequest_Price (BigDecimal Request_Price);

	/** Get Request Price	  */
	public BigDecimal getRequest_Price();

    /** Column name Request_Qty */
    public static final String COLUMNNAME_Request_Qty = "Request_Qty";

	/** Set Request Qty	  */
	public void setRequest_Qty (BigDecimal Request_Qty);

	/** Get Request Qty	  */
	public BigDecimal getRequest_Qty();

    /** Column name Request_Shipdate */
    public static final String COLUMNNAME_Request_Shipdate = "Request_Shipdate";

	/** Set Request Ship date	  */
	public void setRequest_Shipdate (Date Request_Shipdate);

	/** Get Request Ship date	  */
	public Date getRequest_Shipdate();

    /** Column name TrxReceived */
    public static final String COLUMNNAME_TrxReceived = "TrxReceived";

	/** Set Transaction received	  */
	public void setTrxReceived (Date TrxReceived);

	/** Get Transaction received	  */
	public Date getTrxReceived();

    /** Column name TrxSent */
    public static final String COLUMNNAME_TrxSent = "TrxSent";

	/** Set Transaction sent	  */
	public void setTrxSent (Date TrxSent);

	/** Get Transaction sent	  */
	public Date getTrxSent();

    /** Column name TrxType */
    public static final String COLUMNNAME_TrxType = "TrxType";

	/** Set Transaction Type.
	  * Type of credit card transaction
	  */
	public void setTrxType (String TrxType);

	/** Get Transaction Type.
	  * Type of credit card transaction
	  */
	public String getTrxType();

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
