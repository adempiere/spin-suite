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

import java.util.Date;
import org.spinsuite.util.KeyNamePair;

/** Generated Interface for FTA_TechnicalFormLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_FTA_TechnicalFormLine 
{

    /** TableName=FTA_TechnicalFormLine */
    public static final String Table_Name = "FTA_TechnicalFormLine";

    /** SPS_Table_ID=50221 */
    public static final int SPS_Table_ID = 50221;
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

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

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

    /** Column name FTA_FarmDivision_ID */
    public static final String COLUMNNAME_FTA_FarmDivision_ID = "FTA_FarmDivision_ID";

	/** Set Farm Division	  */
	public void setFTA_FarmDivision_ID (int FTA_FarmDivision_ID);

	/** Get Farm Division	  */
	public int getFTA_FarmDivision_ID();

    /** Column name FTA_Farm_ID */
    public static final String COLUMNNAME_FTA_Farm_ID = "FTA_Farm_ID";

	/** Set Farm	  */
	public void setFTA_Farm_ID (int FTA_Farm_ID);

	/** Get Farm	  */
	public int getFTA_Farm_ID();

    /** Column name FTA_Farming_ID */
    public static final String COLUMNNAME_FTA_Farming_ID = "FTA_Farming_ID";

	/** Set Farming	  */
	public void setFTA_Farming_ID (int FTA_Farming_ID);

	/** Get Farming	  */
	public int getFTA_Farming_ID();

    /** Column name FTA_FarmingStage_ID */
    public static final String COLUMNNAME_FTA_FarmingStage_ID = "FTA_FarmingStage_ID";

	/** Set Farming Stage	  */
	public void setFTA_FarmingStage_ID (int FTA_FarmingStage_ID);

	/** Get Farming Stage	  */
	public int getFTA_FarmingStage_ID();

    /** Column name FTA_ObservationType_ID */
    public static final String COLUMNNAME_FTA_ObservationType_ID = "FTA_ObservationType_ID";

	/** Set Observation Type	  */
	public void setFTA_ObservationType_ID (int FTA_ObservationType_ID);

	/** Get Observation Type	  */
	public int getFTA_ObservationType_ID();

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
