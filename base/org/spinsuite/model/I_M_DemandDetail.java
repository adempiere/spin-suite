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

/** Generated Interface for M_DemandDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765)
 */
public interface I_M_DemandDetail 
{

    /** TableName=M_DemandDetail */
    public static final String Table_Name = "M_DemandDetail";

    /** SPS_Table_ID=1000131 */
    public static final int SPS_Table_ID = 1000131;
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

    /** Column name M_DemandDetail_ID */
    public static final String COLUMNNAME_M_DemandDetail_ID = "M_DemandDetail_ID";

	/** Set Demand Detail.
	  * Material Demand Line Source Detail
	  */
	public void setM_DemandDetail_ID (int M_DemandDetail_ID);

	/** Get Demand Detail.
	  * Material Demand Line Source Detail
	  */
	public int getM_DemandDetail_ID();

    /** Column name M_DemandLine_ID */
    public static final String COLUMNNAME_M_DemandLine_ID = "M_DemandLine_ID";

	/** Set Demand Line.
	  * Material Demand Line
	  */
	public void setM_DemandLine_ID (int M_DemandLine_ID);

	/** Get Demand Line.
	  * Material Demand Line
	  */
	public int getM_DemandLine_ID();

    /** Column name M_ForecastLine_ID */
    public static final String COLUMNNAME_M_ForecastLine_ID = "M_ForecastLine_ID";

	/** Set Forecast Line.
	  * Forecast Line
	  */
	public void setM_ForecastLine_ID (int M_ForecastLine_ID);

	/** Get Forecast Line.
	  * Forecast Line
	  */
	public int getM_ForecastLine_ID();

    /** Column name M_RequisitionLine_ID */
    public static final String COLUMNNAME_M_RequisitionLine_ID = "M_RequisitionLine_ID";

	/** Set Requisition Line.
	  * Material Requisition Line
	  */
	public void setM_RequisitionLine_ID (int M_RequisitionLine_ID);

	/** Get Requisition Line.
	  * Material Requisition Line
	  */
	public int getM_RequisitionLine_ID();

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
