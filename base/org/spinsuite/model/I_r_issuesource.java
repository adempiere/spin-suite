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

/** Generated Interface for r_issuesource
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765)
 */
public interface I_r_issuesource 
{

    /** TableName=r_issuesource */
    public static final String Table_Name = "r_issuesource";

    /** SPS_Table_ID=1000903 */
    public static final int SPS_Table_ID = 1000903;
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

    /** Column name ProfileInfo */
    public static final String COLUMNNAME_ProfileInfo = "ProfileInfo";

	/** Set Profile.
	  * Information to help profiling the system for solving support issues
	  */
	public void setProfileInfo (String ProfileInfo);

	/** Get Profile.
	  * Information to help profiling the system for solving support issues
	  */
	public String getProfileInfo();

    /** Column name R_IssueProject_ID */
    public static final String COLUMNNAME_R_IssueProject_ID = "R_IssueProject_ID";

	/** Set Issue Project.
	  * Implementation Projects
	  */
	public void setR_IssueProject_ID (int R_IssueProject_ID);

	/** Get Issue Project.
	  * Implementation Projects
	  */
	public int getR_IssueProject_ID();

    /** Column name r_issuesource_ID */
    public static final String COLUMNNAME_r_issuesource_ID = "r_issuesource_ID";

	/** Set r_issuesource	  */
	public void setr_issuesource_ID (int r_issuesource_ID);

	/** Get r_issuesource	  */
	public int getr_issuesource_ID();

    /** Column name R_IssueSystem_ID */
    public static final String COLUMNNAME_R_IssueSystem_ID = "R_IssueSystem_ID";

	/** Set Issue System.
	  * System creating the issue
	  */
	public void setR_IssueSystem_ID (int R_IssueSystem_ID);

	/** Get Issue System.
	  * System creating the issue
	  */
	public int getR_IssueSystem_ID();

    /** Column name R_IssueUser_ID */
    public static final String COLUMNNAME_R_IssueUser_ID = "R_IssueUser_ID";

	/** Set IssueUser.
	  * User who reported issues
	  */
	public void setR_IssueUser_ID (int R_IssueUser_ID);

	/** Get IssueUser.
	  * User who reported issues
	  */
	public int getR_IssueUser_ID();

    /** Column name StatisticsInfo */
    public static final String COLUMNNAME_StatisticsInfo = "StatisticsInfo";

	/** Set Statistics.
	  * Information to help profiling the system for solving support issues
	  */
	public void setStatisticsInfo (String StatisticsInfo);

	/** Get Statistics.
	  * Information to help profiling the system for solving support issues
	  */
	public String getStatisticsInfo();

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
