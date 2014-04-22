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
/** Generated Model - DO NOT CHANGE */
package org.spinsuite.model;

import android.content.Context;
import android.database.Cursor;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for A_Asset
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_A_Asset extends PO implements I_A_Asset
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_A_Asset (Context ctx, int A_Asset_ID, DB conn)
    {
      super (ctx, A_Asset_ID, conn);
      /** if (A_Asset_ID == 0)
        {
			setA_Asset_Group_ID (0);
			setA_Asset_ID (0);
			setIsDepreciated (false);
			setIsDisposed (false);
			setIsFullyDepreciated (false);
// N
			setIsInPosession (false);
			setIsOwned (false);
			setM_AttributeSetInstance_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_A_Asset (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_A_Asset[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Asset Creation Date.
		@param A_Asset_CreateDate Asset Creation Date	  */
	public void setA_Asset_CreateDate (Date A_Asset_CreateDate)
	{
		set_Value (COLUMNNAME_A_Asset_CreateDate, A_Asset_CreateDate);
	}

	/** Get Asset Creation Date.
		@return Asset Creation Date	  */
	public Date getA_Asset_CreateDate () 
	{
		return (Date)get_Value(COLUMNNAME_A_Asset_CreateDate);
	}

	/** Set Asset Group.
		@param A_Asset_Group_ID 
		Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset Group.
		@return Group of Assets
	  */
	public int getA_Asset_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Asset Reval. Date.
		@param A_Asset_RevalDate Asset Reval. Date	  */
	public void setA_Asset_RevalDate (Date A_Asset_RevalDate)
	{
		set_Value (COLUMNNAME_A_Asset_RevalDate, A_Asset_RevalDate);
	}

	/** Get Asset Reval. Date.
		@return Asset Reval. Date	  */
	public Date getA_Asset_RevalDate () 
	{
		return (Date)get_Value(COLUMNNAME_A_Asset_RevalDate);
	}

	/** Set Usuario.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Usuario.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Asset ID.
		@param A_Parent_Asset_ID Asset ID	  */
	public void setA_Parent_Asset_ID (int A_Parent_Asset_ID)
	{
		if (A_Parent_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Parent_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Parent_Asset_ID, Integer.valueOf(A_Parent_Asset_ID));
	}

	/** Get Asset ID.
		@return Asset ID	  */
	public int getA_Parent_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Parent_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity.
		@param A_QTY_Current Quantity	  */
	public void setA_QTY_Current (BigDecimal A_QTY_Current)
	{
		set_Value (COLUMNNAME_A_QTY_Current, A_QTY_Current);
	}

	/** Get Quantity.
		@return Quantity	  */
	public BigDecimal getA_QTY_Current () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_QTY_Current);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Original Qty.
		@param A_QTY_Original Original Qty	  */
	public void setA_QTY_Original (BigDecimal A_QTY_Original)
	{
		set_Value (COLUMNNAME_A_QTY_Original, A_QTY_Original);
	}

	/** Get Original Qty.
		@return Original Qty	  */
	public BigDecimal getA_QTY_Original () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_QTY_Original);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Asset Depreciation Date.
		@param AssetDepreciationDate 
		Date of last depreciation
	  */
	public void setAssetDepreciationDate (Date AssetDepreciationDate)
	{
		set_Value (COLUMNNAME_AssetDepreciationDate, AssetDepreciationDate);
	}

	/** Get Asset Depreciation Date.
		@return Date of last depreciation
	  */
	public Date getAssetDepreciationDate () 
	{
		return (Date)get_Value(COLUMNNAME_AssetDepreciationDate);
	}

	/** Set Asset Disposal Date.
		@param AssetDisposalDate 
		Date when the asset is/was disposed
	  */
	public void setAssetDisposalDate (Date AssetDisposalDate)
	{
		set_Value (COLUMNNAME_AssetDisposalDate, AssetDisposalDate);
	}

	/** Get Asset Disposal Date.
		@return Date when the asset is/was disposed
	  */
	public Date getAssetDisposalDate () 
	{
		return (Date)get_Value(COLUMNNAME_AssetDisposalDate);
	}

	/** Set In Service Date.
		@param AssetServiceDate 
		Date when Asset was put into service
	  */
	public void setAssetServiceDate (Date AssetServiceDate)
	{
		set_Value (COLUMNNAME_AssetServiceDate, AssetServiceDate);
	}

	/** Get In Service Date.
		@return Date when Asset was put into service
	  */
	public Date getAssetServiceDate () 
	{
		return (Date)get_Value(COLUMNNAME_AssetServiceDate);
	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BPartner (Agent).
		@param C_BPartnerSR_ID 
		Business Partner (Agent or Sales Rep)
	  */
	public void setC_BPartnerSR_ID (int C_BPartnerSR_ID)
	{
		if (C_BPartnerSR_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, Integer.valueOf(C_BPartnerSR_ID));
	}

	/** Get BPartner (Agent).
		@return Business Partner (Agent or Sales Rep)
	  */
	public int getC_BPartnerSR_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerSR_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
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

	/** Set Guarantee Date.
		@param GuaranteeDate 
		Date when guarantee expires
	  */
	public void setGuaranteeDate (Date GuaranteeDate)
	{
		set_Value (COLUMNNAME_GuaranteeDate, GuaranteeDate);
	}

	/** Get Guarantee Date.
		@return Date when guarantee expires
	  */
	public Date getGuaranteeDate () 
	{
		return (Date)get_Value(COLUMNNAME_GuaranteeDate);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Depreciate.
		@param IsDepreciated 
		The asset will be depreciated
	  */
	public void setIsDepreciated (boolean IsDepreciated)
	{
		set_Value (COLUMNNAME_IsDepreciated, Boolean.valueOf(IsDepreciated));
	}

	/** Get Depreciate.
		@return The asset will be depreciated
	  */
	public boolean isDepreciated () 
	{
		Object oo = get_Value(COLUMNNAME_IsDepreciated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Disposed.
		@param IsDisposed 
		The asset is disposed
	  */
	public void setIsDisposed (boolean IsDisposed)
	{
		set_Value (COLUMNNAME_IsDisposed, Boolean.valueOf(IsDisposed));
	}

	/** Get Disposed.
		@return The asset is disposed
	  */
	public boolean isDisposed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisposed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fully depreciated.
		@param IsFullyDepreciated 
		The asset is fully depreciated
	  */
	public void setIsFullyDepreciated (boolean IsFullyDepreciated)
	{
		set_Value (COLUMNNAME_IsFullyDepreciated, Boolean.valueOf(IsFullyDepreciated));
	}

	/** Get Fully depreciated.
		@return The asset is fully depreciated
	  */
	public boolean isFullyDepreciated () 
	{
		Object oo = get_Value(COLUMNNAME_IsFullyDepreciated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set In Possession.
		@param IsInPosession 
		The asset is in the possession of the organization
	  */
	public void setIsInPosession (boolean IsInPosession)
	{
		set_Value (COLUMNNAME_IsInPosession, Boolean.valueOf(IsInPosession));
	}

	/** Get In Possession.
		@return The asset is in the possession of the organization
	  */
	public boolean isInPosession () 
	{
		Object oo = get_Value(COLUMNNAME_IsInPosession);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Owned.
		@param IsOwned 
		The asset is owned by the organization
	  */
	public void setIsOwned (boolean IsOwned)
	{
		set_Value (COLUMNNAME_IsOwned, Boolean.valueOf(IsOwned));
	}

	/** Get Owned.
		@return The asset is owned by the organization
	  */
	public boolean isOwned () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Maintenance.
		@param LastMaintenanceDate 
		Last Maintenance Date
	  */
	public void setLastMaintenanceDate (Date LastMaintenanceDate)
	{
		set_Value (COLUMNNAME_LastMaintenanceDate, LastMaintenanceDate);
	}

	/** Get Last Maintenance.
		@return Last Maintenance Date
	  */
	public Date getLastMaintenanceDate () 
	{
		return (Date)get_Value(COLUMNNAME_LastMaintenanceDate);
	}

	/** Set Last Note.
		@param LastMaintenanceNote 
		Last Maintenance Note
	  */
	public void setLastMaintenanceNote (String LastMaintenanceNote)
	{
		set_Value (COLUMNNAME_LastMaintenanceNote, LastMaintenanceNote);
	}

	/** Get Last Note.
		@return Last Maintenance Note
	  */
	public String getLastMaintenanceNote () 
	{
		return (String)get_Value(COLUMNNAME_LastMaintenanceNote);
	}

	/** Set Last Unit.
		@param LastMaintenanceUnit 
		Last Maintenance Unit
	  */
	public void setLastMaintenanceUnit (int LastMaintenanceUnit)
	{
		set_Value (COLUMNNAME_LastMaintenanceUnit, Integer.valueOf(LastMaintenanceUnit));
	}

	/** Get Last Unit.
		@return Last Maintenance Unit
	  */
	public int getLastMaintenanceUnit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LastMaintenanceUnit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set lastmaintenanceuseunit.
		@param lastmaintenanceuseunit lastmaintenanceuseunit	  */
	public void setlastmaintenanceuseunit (int lastmaintenanceuseunit)
	{
		set_Value (COLUMNNAME_lastmaintenanceuseunit, Integer.valueOf(lastmaintenanceuseunit));
	}

	/** Get lastmaintenanceuseunit.
		@return lastmaintenanceuseunit	  */
	public int getlastmaintenanceuseunit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_lastmaintenanceuseunit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set lastmaintenencedate.
		@param lastmaintenencedate lastmaintenencedate	  */
	public void setlastmaintenencedate (Date lastmaintenencedate)
	{
		set_Value (COLUMNNAME_lastmaintenencedate, lastmaintenencedate);
	}

	/** Get lastmaintenencedate.
		@return lastmaintenencedate	  */
	public Date getlastmaintenencedate () 
	{
		return (Date)get_Value(COLUMNNAME_lastmaintenencedate);
	}

	/** Set Lessor.
		@param Lease_BPartner_ID 
		The Business Partner who rents or leases
	  */
	public void setLease_BPartner_ID (int Lease_BPartner_ID)
	{
		if (Lease_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Lease_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Lease_BPartner_ID, Integer.valueOf(Lease_BPartner_ID));
	}

	/** Get Lessor.
		@return The Business Partner who rents or leases
	  */
	public int getLease_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Lease_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lease Termination.
		@param LeaseTerminationDate 
		Lease Termination Date
	  */
	public void setLeaseTerminationDate (Date LeaseTerminationDate)
	{
		set_Value (COLUMNNAME_LeaseTerminationDate, LeaseTerminationDate);
	}

	/** Get Lease Termination.
		@return Lease Termination Date
	  */
	public Date getLeaseTerminationDate () 
	{
		return (Date)get_Value(COLUMNNAME_LeaseTerminationDate);
	}

	/** Set Life use.
		@param LifeUseUnits 
		Units of use until the asset is not usable anymore
	  */
	public void setLifeUseUnits (int LifeUseUnits)
	{
		set_Value (COLUMNNAME_LifeUseUnits, Integer.valueOf(LifeUseUnits));
	}

	/** Get Life use.
		@return Units of use until the asset is not usable anymore
	  */
	public int getLifeUseUnits () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LifeUseUnits);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Location comment.
		@param LocationComment 
		Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment)
	{
		set_Value (COLUMNNAME_LocationComment, LocationComment);
	}

	/** Get Location comment.
		@return Additional comments or remarks concerning the location
	  */
	public String getLocationComment () 
	{
		return (String)get_Value(COLUMNNAME_LocationComment);
	}

	/** Set Lot No.
		@param Lot 
		Lot number (alphanumeric)
	  */
	public void setLot (String Lot)
	{
		set_Value (COLUMNNAME_Lot, Lot);
	}

	/** Get Lot No.
		@return Lot number (alphanumeric)
	  */
	public String getLot () 
	{
		return (String)get_Value(COLUMNNAME_Lot);
	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set nextmaintenanceuseunit.
		@param nextmaintenanceuseunit nextmaintenanceuseunit	  */
	public void setnextmaintenanceuseunit (int nextmaintenanceuseunit)
	{
		set_Value (COLUMNNAME_nextmaintenanceuseunit, Integer.valueOf(nextmaintenanceuseunit));
	}

	/** Get nextmaintenanceuseunit.
		@return nextmaintenanceuseunit	  */
	public int getnextmaintenanceuseunit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_nextmaintenanceuseunit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Next Maintenance.
		@param NextMaintenenceDate 
		Next Maintenance Date
	  */
	public void setNextMaintenenceDate (Date NextMaintenenceDate)
	{
		set_Value (COLUMNNAME_NextMaintenenceDate, NextMaintenenceDate);
	}

	/** Get Next Maintenance.
		@return Next Maintenance Date
	  */
	public Date getNextMaintenenceDate () 
	{
		return (Date)get_Value(COLUMNNAME_NextMaintenenceDate);
	}

	/** Set Next Unit.
		@param NextMaintenenceUnit 
		Next Maintenance Unit
	  */
	public void setNextMaintenenceUnit (int NextMaintenenceUnit)
	{
		set_Value (COLUMNNAME_NextMaintenenceUnit, Integer.valueOf(NextMaintenenceUnit));
	}

	/** Get Next Unit.
		@return Next Maintenance Unit
	  */
	public int getNextMaintenenceUnit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NextMaintenenceUnit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Serial No.
		@param SerNo 
		Product Serial Number 
	  */
	public void setSerNo (String SerNo)
	{
		set_Value (COLUMNNAME_SerNo, SerNo);
	}

	/** Get Serial No.
		@return Product Serial Number 
	  */
	public String getSerNo () 
	{
		return (String)get_Value(COLUMNNAME_SerNo);
	}

	/** Set Usable Life - Months.
		@param UseLifeMonths 
		Months of the usable life of the asset
	  */
	public void setUseLifeMonths (int UseLifeMonths)
	{
		set_Value (COLUMNNAME_UseLifeMonths, Integer.valueOf(UseLifeMonths));
	}

	/** Get Usable Life - Months.
		@return Months of the usable life of the asset
	  */
	public int getUseLifeMonths () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UseLifeMonths);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Usable Life - Years.
		@param UseLifeYears 
		Years of the usable life of the asset
	  */
	public void setUseLifeYears (int UseLifeYears)
	{
		set_Value (COLUMNNAME_UseLifeYears, Integer.valueOf(UseLifeYears));
	}

	/** Get Usable Life - Years.
		@return Years of the usable life of the asset
	  */
	public int getUseLifeYears () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UseLifeYears);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Use units.
		@param UseUnits 
		Currently used units of the assets
	  */
	public void setUseUnits (int UseUnits)
	{
		set_Value (COLUMNNAME_UseUnits, Integer.valueOf(UseUnits));
	}

	/** Get Use units.
		@return Currently used units of the assets
	  */
	public int getUseUnits () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UseUnits);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Version No.
		@param VersionNo 
		Version Number
	  */
	public void setVersionNo (String VersionNo)
	{
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	/** Get Version No.
		@return Version Number
	  */
	public String getVersionNo () 
	{
		return (String)get_Value(COLUMNNAME_VersionNo);
	}
}