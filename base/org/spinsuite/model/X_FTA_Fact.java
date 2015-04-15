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
/** Generated Model - DO NOT CHANGE */
package org.spinsuite.model;

import android.content.Context;
import android.database.Cursor;
import java.math.BigDecimal;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;

/** Generated Model for FTA_Fact
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_FTA_Fact extends PO implements I_FTA_Fact {
    /** Standard Constructor */
    public X_FTA_Fact (Context ctx, int FTA_Fact_ID, DB conn)
    {
      super (ctx, FTA_Fact_ID, conn);
      /** if (FTA_Fact_ID == 0)
        {
			setAD_Table_ID (0);
			setAmt (Env.ZERO);
			setC_BPartner_ID (0);
			setDateDoc (null);
			setFTA_CreditDefinition_ID (0);
			setFTA_CreditDefinitionLine_ID (0);
			setFTA_Fact_ID (0);
			setFTA_FarmerCredit_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_FTA_Fact (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_FTA_Fact[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Date DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Date getDateDoc () 
	{
		return (Date)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Credit Definition Line Category.
		@param FTA_CDL_Category_ID Credit Definition Line Category	  */
	public void setFTA_CDL_Category_ID (int FTA_CDL_Category_ID)
	{
		throw new IllegalArgumentException ("FTA_CDL_Category_ID is virtual column");	}

	/** Get Credit Definition Line Category.
		@return Credit Definition Line Category	  */
	public int getFTA_CDL_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_CDL_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit Definition.
		@param FTA_CreditDefinition_ID Credit Definition	  */
	public void setFTA_CreditDefinition_ID (int FTA_CreditDefinition_ID)
	{
		if (FTA_CreditDefinition_ID < 1) 
			set_Value (COLUMNNAME_FTA_CreditDefinition_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_CreditDefinition_ID, Integer.valueOf(FTA_CreditDefinition_ID));
	}

	/** Get Credit Definition.
		@return Credit Definition	  */
	public int getFTA_CreditDefinition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_CreditDefinition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit Definition Line.
		@param FTA_CreditDefinitionLine_ID Credit Definition Line	  */
	public void setFTA_CreditDefinitionLine_ID (int FTA_CreditDefinitionLine_ID)
	{
		if (FTA_CreditDefinitionLine_ID < 1) 
			set_Value (COLUMNNAME_FTA_CreditDefinitionLine_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_CreditDefinitionLine_ID, Integer.valueOf(FTA_CreditDefinitionLine_ID));
	}

	/** Get Credit Definition Line.
		@return Credit Definition Line	  */
	public int getFTA_CreditDefinitionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_CreditDefinitionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Farmer Credit Fact.
		@param FTA_Fact_ID Farmer Credit Fact	  */
	public void setFTA_Fact_ID (int FTA_Fact_ID)
	{
		if (FTA_Fact_ID < 1) 
			set_Value (COLUMNNAME_FTA_Fact_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_Fact_ID, Integer.valueOf(FTA_Fact_ID));
	}

	/** Get Farmer Credit Fact.
		@return Farmer Credit Fact	  */
	public int getFTA_Fact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_Fact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Credit/Loan.
		@param FTA_FarmerCredit_ID 
		Farmer Credit or Loan
	  */
	public void setFTA_FarmerCredit_ID (int FTA_FarmerCredit_ID)
	{
		if (FTA_FarmerCredit_ID < 1) 
			set_Value (COLUMNNAME_FTA_FarmerCredit_ID, null);
		else 
			set_Value (COLUMNNAME_FTA_FarmerCredit_ID, Integer.valueOf(FTA_FarmerCredit_ID));
	}

	/** Get Credit/Loan.
		@return Farmer Credit or Loan
	  */
	public int getFTA_FarmerCredit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FTA_FarmerCredit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Is Credit Fact Manual.
		@param IsCreditFactManual Is Credit Fact Manual	  */
	public void setIsCreditFactManual (boolean IsCreditFactManual)
	{
		set_Value (COLUMNNAME_IsCreditFactManual, Boolean.valueOf(IsCreditFactManual));
	}

	/** Get Is Credit Fact Manual.
		@return Is Credit Fact Manual	  */
	public boolean isCreditFactManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreditFactManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line ID.
		@param Line_ID 
		Transaction line ID (internal)
	  */
	public void setLine_ID (int Line_ID)
	{
		if (Line_ID < 1) 
			set_Value (COLUMNNAME_Line_ID, null);
		else 
			set_Value (COLUMNNAME_Line_ID, Integer.valueOf(Line_ID));
	}

	/** Get Line ID.
		@return Transaction line ID (internal)
	  */
	public int getLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Multiplier.
		@param Multiplier 
		Type Multiplier (Credit = -1)
	  */
	public void setMultiplier (BigDecimal Multiplier)
	{
		set_Value (COLUMNNAME_Multiplier, Multiplier);
	}

	/** Get Multiplier.
		@return Type Multiplier (Credit = -1)
	  */
	public BigDecimal getMultiplier () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Multiplier);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}