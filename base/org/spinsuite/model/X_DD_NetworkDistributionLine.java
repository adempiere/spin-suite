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

/** Generated Model for DD_NetworkDistributionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_DD_NetworkDistributionLine extends PO implements I_DD_NetworkDistributionLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_DD_NetworkDistributionLine (Context ctx, int DD_NetworkDistributionLine_ID, DB conn)
    {
      super (ctx, DD_NetworkDistributionLine_ID, conn);
      /** if (DD_NetworkDistributionLine_ID == 0)
        {
			setDD_NetworkDistribution_ID (0);
			setDD_NetworkDistributionLine_ID (0);
			setM_Shipper_ID (0);
			setM_Warehouse_ID (0);
// @M_Warehouse_ID@
			setM_WarehouseSource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DD_NetworkDistributionLine (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_DD_NetworkDistributionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Network Distribution.
		@param DD_NetworkDistribution_ID Network Distribution	  */
	public void setDD_NetworkDistribution_ID (int DD_NetworkDistribution_ID)
	{
		if (DD_NetworkDistribution_ID < 1) 
			set_Value (COLUMNNAME_DD_NetworkDistribution_ID, null);
		else 
			set_Value (COLUMNNAME_DD_NetworkDistribution_ID, Integer.valueOf(DD_NetworkDistribution_ID));
	}

	/** Get Network Distribution.
		@return Network Distribution	  */
	public int getDD_NetworkDistribution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DD_NetworkDistribution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Network Distribution Line.
		@param DD_NetworkDistributionLine_ID 
		Network Distribution Line
	  */
	public void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID)
	{
		if (DD_NetworkDistributionLine_ID < 1) 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, null);
		else 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, Integer.valueOf(DD_NetworkDistributionLine_ID));
	}

	/** Get Network Distribution Line.
		@return Network Distribution Line
	  */
	public int getDD_NetworkDistributionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DD_NetworkDistributionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipper.
		@param M_Shipper_ID 
		Method or manner of product delivery
	  */
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Shipper.
		@return Method or manner of product delivery
	  */
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Source Warehouse.
		@param M_WarehouseSource_ID 
		Optional Warehouse to replenish from
	  */
	public void setM_WarehouseSource_ID (int M_WarehouseSource_ID)
	{
		if (M_WarehouseSource_ID < 1) 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, Integer.valueOf(M_WarehouseSource_ID));
	}

	/** Get Source Warehouse.
		@return Optional Warehouse to replenish from
	  */
	public int getM_WarehouseSource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_WarehouseSource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Percent.
		@param Percent 
		Percentage
	  */
	public void setPercent (BigDecimal Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	/** Get Percent.
		@return Percentage
	  */
	public BigDecimal getPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Relative Priority.
		@param PriorityNo 
		Where inventory should be picked from first
	  */
	public void setPriorityNo (int PriorityNo)
	{
		set_Value (COLUMNNAME_PriorityNo, Integer.valueOf(PriorityNo));
	}

	/** Get Relative Priority.
		@return Where inventory should be picked from first
	  */
	public int getPriorityNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PriorityNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transfert Time.
		@param TransfertTime Transfert Time	  */
	public void setTransfertTime (BigDecimal TransfertTime)
	{
		set_Value (COLUMNNAME_TransfertTime, TransfertTime);
	}

	/** Get Transfert Time.
		@return Transfert Time	  */
	public BigDecimal getTransfertTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TransfertTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Date ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Date getValidFrom () 
	{
		return (Date)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Date ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Date getValidTo () 
	{
		return (Date)get_Value(COLUMNNAME_ValidTo);
	}
}