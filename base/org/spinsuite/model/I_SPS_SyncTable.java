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

/** Generated Interface for SPS_SyncTable
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_SPS_SyncTable 
{

    /** TableName=SPS_SyncTable */
    public static final String Table_Name = "SPS_SyncTable";

    /** SPS_Table_ID=50333 */
    public static final int SPS_Table_ID = 50333;
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

    /** Column name EventChangeLog */
    public static final String COLUMNNAME_EventChangeLog = "EventChangeLog";

	/** Set Event Change Log.
	  * Type of Event in Change Log
	  */
	public void setEventChangeLog (String EventChangeLog);

	/** Get Event Change Log.
	  * Type of Event in Change Log
	  */
	public String getEventChangeLog();

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

    /** Column name IsSynchronized */
    public static final String COLUMNNAME_IsSynchronized = "IsSynchronized";

	/** Set Synchronized	  */
	public void setIsSynchronized (Object IsSynchronized);

	/** Get Synchronized	  */
	public Object getIsSynchronized();

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

    /** Column name SPS_SyncTable_ID */
    public static final String COLUMNNAME_SPS_SyncTable_ID = "SPS_SyncTable_ID";

	/** Set Synchronization Table ID	  */
	public void setSPS_SyncTable_ID (int SPS_SyncTable_ID);

	/** Get Synchronization Table ID	  */
	public int getSPS_SyncTable_ID();

    /** Column name SPS_Table_ID */
    public static final String COLUMNNAME_SPS_Table_ID = "SPS_Table_ID";

	/** Set Mobile Table	  */
	public void setSPS_Table_ID (int SPS_Table_ID);

	/** Get Mobile Table	  */
	public int getSPS_Table_ID();

    /** Column name SyncRecord_ID */
    public static final String COLUMNNAME_SyncRecord_ID = "SyncRecord_ID";

	/** Set Synchronization Record ID	  */
	public void setSyncRecord_ID (String SyncRecord_ID);

	/** Get Synchronization Record ID	  */
	public String getSyncRecord_ID();

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
