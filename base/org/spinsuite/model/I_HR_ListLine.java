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

/** Generated Interface for HR_ListLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765)
 */
public interface I_HR_ListLine 
{

    /** TableName=HR_ListLine */
    public static final String Table_Name = "HR_ListLine";

    /** SPS_Table_ID=1000699 */
    public static final int SPS_Table_ID = 1000699;
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

    /** Column name Col_1 */
    public static final String COLUMNNAME_Col_1 = "Col_1";

	/** Set Col_1	  */
	public void setCol_1 (BigDecimal Col_1);

	/** Get Col_1	  */
	public BigDecimal getCol_1();

    /** Column name Col_2 */
    public static final String COLUMNNAME_Col_2 = "Col_2";

	/** Set Col_2	  */
	public void setCol_2 (BigDecimal Col_2);

	/** Get Col_2	  */
	public BigDecimal getCol_2();

    /** Column name Col_3 */
    public static final String COLUMNNAME_Col_3 = "Col_3";

	/** Set Col_3	  */
	public void setCol_3 (BigDecimal Col_3);

	/** Get Col_3	  */
	public BigDecimal getCol_3();

    /** Column name Col_4 */
    public static final String COLUMNNAME_Col_4 = "Col_4";

	/** Set Col_4	  */
	public void setCol_4 (BigDecimal Col_4);

	/** Get Col_4	  */
	public BigDecimal getCol_4();

    /** Column name Col_5 */
    public static final String COLUMNNAME_Col_5 = "Col_5";

	/** Set Col_5	  */
	public void setCol_5 (BigDecimal Col_5);

	/** Get Col_5	  */
	public BigDecimal getCol_5();

    /** Column name Col_6 */
    public static final String COLUMNNAME_Col_6 = "Col_6";

	/** Set Col_6	  */
	public void setCol_6 (BigDecimal Col_6);

	/** Get Col_6	  */
	public BigDecimal getCol_6();

    /** Column name Col_7 */
    public static final String COLUMNNAME_Col_7 = "Col_7";

	/** Set Col_7	  */
	public void setCol_7 (BigDecimal Col_7);

	/** Get Col_7	  */
	public BigDecimal getCol_7();

    /** Column name Col_8 */
    public static final String COLUMNNAME_Col_8 = "Col_8";

	/** Set Col_8	  */
	public void setCol_8 (BigDecimal Col_8);

	/** Get Col_8	  */
	public BigDecimal getCol_8();

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

    /** Column name HR_ListLine_ID */
    public static final String COLUMNNAME_HR_ListLine_ID = "HR_ListLine_ID";

	/** Set Payroll List Line	  */
	public void setHR_ListLine_ID (int HR_ListLine_ID);

	/** Get Payroll List Line	  */
	public int getHR_ListLine_ID();

    /** Column name HR_ListVersion_ID */
    public static final String COLUMNNAME_HR_ListVersion_ID = "HR_ListVersion_ID";

	/** Set Payroll List Version	  */
	public void setHR_ListVersion_ID (int HR_ListVersion_ID);

	/** Get Payroll List Version	  */
	public int getHR_ListVersion_ID();

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

    /** Column name MaxValue */
    public static final String COLUMNNAME_MaxValue = "MaxValue";

	/** Set Max Value	  */
	public void setMaxValue (BigDecimal MaxValue);

	/** Get Max Value	  */
	public BigDecimal getMaxValue();

    /** Column name MinValue */
    public static final String COLUMNNAME_MinValue = "MinValue";

	/** Set Min Value	  */
	public void setMinValue (BigDecimal MinValue);

	/** Get Min Value	  */
	public BigDecimal getMinValue();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

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
