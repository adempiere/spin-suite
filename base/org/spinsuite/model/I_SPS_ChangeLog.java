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

/** Generated Interface for SPS_ChangeLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_SPS_ChangeLog 
{

    /** TableName=SPS_ChangeLog */
    public static final String Table_Name = "SPS_ChangeLog";

    /** SPS_Table_ID=50330 */
    public static final int SPS_Table_ID = 50330;
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

    /** Column name AD_Session_ID */
    public static final String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

	/** Set Session.
	  * User Session Online or Web
	  */
	public void setAD_Session_ID (int AD_Session_ID);

	/** Get Session.
	  * User Session Online or Web
	  */
	public int getAD_Session_ID();

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

    /** Column name IsCustomization */
    public static final String COLUMNNAME_IsCustomization = "IsCustomization";

	/** Set Customization.
	  * The change is a customization of the data dictionary and can be applied after Migration
	  */
	public void setIsCustomization (boolean IsCustomization);

	/** Get Customization.
	  * The change is a customization of the data dictionary and can be applied after Migration
	  */
	public boolean isCustomization();

    /** Column name IsSynchronized */
    public static final String COLUMNNAME_IsSynchronized = "IsSynchronized";

	/** Set Synchronized	  */
	public void setIsSynchronized (boolean IsSynchronized);

	/** Get Synchronized	  */
	public boolean isSynchronized();

    /** Column name NewValue */
    public static final String COLUMNNAME_NewValue = "NewValue";

	/** Set New Value.
	  * New field value
	  */
	public void setNewValue (String NewValue);

	/** Get New Value.
	  * New field value
	  */
	public String getNewValue();

    /** Column name OldValue */
    public static final String COLUMNNAME_OldValue = "OldValue";

	/** Set Old Value.
	  * The old file data
	  */
	public void setOldValue (String OldValue);

	/** Get Old Value.
	  * The old file data
	  */
	public String getOldValue();

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

    /** Column name Redo */
    public static final String COLUMNNAME_Redo = "Redo";

	/** Set Redo	  */
	public void setRedo (String Redo);

	/** Get Redo	  */
	public String getRedo();

    /** Column name SPS_ChangeLog_ID */
    public static final String COLUMNNAME_SPS_ChangeLog_ID = "SPS_ChangeLog_ID";

	/** Set Spin Suite Change Log	  */
	public void setSPS_ChangeLog_ID (int SPS_ChangeLog_ID);

	/** Get Spin Suite Change Log	  */
	public int getSPS_ChangeLog_ID();

    /** Column name SPS_Column_ID */
    public static final String COLUMNNAME_SPS_Column_ID = "SPS_Column_ID";

	/** Set Mobile Column	  */
	public void setSPS_Column_ID (int SPS_Column_ID);

	/** Get Mobile Column	  */
	public int getSPS_Column_ID();

    /** Column name SPS_Table_ID */
    public static final String COLUMNNAME_SPS_Table_ID = "SPS_Table_ID";

	/** Set Mobile Table	  */
	public void setSPS_Table_ID (int SPS_Table_ID);

	/** Get Mobile Table	  */
	public int getSPS_Table_ID();

    /** Column name TrxName */
    public static final String COLUMNNAME_TrxName = "TrxName";

	/** Set Transaction.
	  * Name of the transaction
	  */
	public void setTrxName (String TrxName);

	/** Get Transaction.
	  * Name of the transaction
	  */
	public String getTrxName();

    /** Column name Undo */
    public static final String COLUMNNAME_Undo = "Undo";

	/** Set Undo	  */
	public void setUndo (String Undo);

	/** Get Undo	  */
	public String getUndo();

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