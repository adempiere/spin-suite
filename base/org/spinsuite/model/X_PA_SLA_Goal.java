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

/** Generated Model for PA_SLA_Goal
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_PA_SLA_Goal extends PO implements I_PA_SLA_Goal
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_PA_SLA_Goal (Context ctx, int PA_SLA_Goal_ID, DB conn)
    {
      super (ctx, PA_SLA_Goal_ID, conn);
      /** if (PA_SLA_Goal_ID == 0)
        {
			setC_BPartner_ID (0);
			setMeasureActual (Env.ZERO);
			setMeasureTarget (Env.ZERO);
			setName (null);
			setPA_SLA_Criteria_ID (0);
			setPA_SLA_Goal_ID (0);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_PA_SLA_Goal (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_PA_SLA_Goal[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Date last run.
		@param DateLastRun 
		Date the process was last run.
	  */
	public void setDateLastRun (Date DateLastRun)
	{
		set_Value (COLUMNNAME_DateLastRun, DateLastRun);
	}

	/** Get Date last run.
		@return Date the process was last run.
	  */
	public Date getDateLastRun () 
	{
		return (Date)get_Value(COLUMNNAME_DateLastRun);
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

	/** Set Measure Actual.
		@param MeasureActual 
		Actual value that has been measured.
	  */
	public void setMeasureActual (BigDecimal MeasureActual)
	{
		set_Value (COLUMNNAME_MeasureActual, MeasureActual);
	}

	/** Get Measure Actual.
		@return Actual value that has been measured.
	  */
	public BigDecimal getMeasureActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MeasureActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Measure Target.
		@param MeasureTarget 
		Target value for measure
	  */
	public void setMeasureTarget (BigDecimal MeasureTarget)
	{
		set_Value (COLUMNNAME_MeasureTarget, MeasureTarget);
	}

	/** Get Measure Target.
		@return Target value for measure
	  */
	public BigDecimal getMeasureTarget () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MeasureTarget);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set SLA Criteria.
		@param PA_SLA_Criteria_ID 
		Service Level Agreement Criteria
	  */
	public void setPA_SLA_Criteria_ID (int PA_SLA_Criteria_ID)
	{
		if (PA_SLA_Criteria_ID < 1) 
			set_Value (COLUMNNAME_PA_SLA_Criteria_ID, null);
		else 
			set_Value (COLUMNNAME_PA_SLA_Criteria_ID, Integer.valueOf(PA_SLA_Criteria_ID));
	}

	/** Get SLA Criteria.
		@return Service Level Agreement Criteria
	  */
	public int getPA_SLA_Criteria_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_SLA_Criteria_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SLA Goal.
		@param PA_SLA_Goal_ID 
		Service Level Agreement Goal
	  */
	public void setPA_SLA_Goal_ID (int PA_SLA_Goal_ID)
	{
		if (PA_SLA_Goal_ID < 1) 
			set_Value (COLUMNNAME_PA_SLA_Goal_ID, null);
		else 
			set_Value (COLUMNNAME_PA_SLA_Goal_ID, Integer.valueOf(PA_SLA_Goal_ID));
	}

	/** Get SLA Goal.
		@return Service Level Agreement Goal
	  */
	public int getPA_SLA_Goal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_SLA_Goal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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