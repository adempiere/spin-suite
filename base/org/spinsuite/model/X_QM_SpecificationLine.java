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
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;

/** Generated Model for QM_SpecificationLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_QM_SpecificationLine extends PO implements I_QM_SpecificationLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_QM_SpecificationLine (Context ctx, int QM_SpecificationLine_ID, DB conn)
    {
      super (ctx, QM_SpecificationLine_ID, conn);
      /** if (QM_SpecificationLine_ID == 0)
        {
			setAndOr (null);
			setM_Attribute_ID (0);
			setOperation (null);
			setQM_SpecificationLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_QM_SpecificationLine (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_QM_SpecificationLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AndOr AD_Reference_ID=204 */
	public static final int ANDOR_AD_Reference_ID=204;
	/** And = A */
	public static final String ANDOR_And = "A";
	/** Or = O */
	public static final String ANDOR_Or = "O";
	/** Set And/Or.
		@param AndOr 
		Logical operation: AND or OR
	  */
	public void setAndOr (String AndOr)
	{

		set_Value (COLUMNNAME_AndOr, AndOr);
	}

	/** Get And/Or.
		@return Logical operation: AND or OR
	  */
	public String getAndOr () 
	{
		return (String)get_Value(COLUMNNAME_AndOr);
	}

	/** Set Attribute.
		@param M_Attribute_ID 
		Product Attribute
	  */
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Attribute.
		@return Product Attribute
	  */
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Operation AD_Reference_ID=205 */
	public static final int OPERATION_AD_Reference_ID=205;
	/**  = = == */
	public static final String OPERATION_Eq = "==";
	/** >= = >= */
	public static final String OPERATION_GtEq = ">=";
	/** > = >> */
	public static final String OPERATION_Gt = ">>";
	/** < = << */
	public static final String OPERATION_Le = "<<";
	/**  ~ = ~~ */
	public static final String OPERATION_Like = "~~";
	/** <= = <= */
	public static final String OPERATION_LeEq = "<=";
	/** |<x>| = AB */
	public static final String OPERATION_X = "AB";
	/** sql = SQ */
	public static final String OPERATION_Sql = "SQ";
	/** != = != */
	public static final String OPERATION_NotEq = "!=";
	/** Set Operation.
		@param Operation 
		Compare Operation
	  */
	public void setOperation (String Operation)
	{

		set_Value (COLUMNNAME_Operation, Operation);
	}

	/** Get Operation.
		@return Compare Operation
	  */
	public String getOperation () 
	{
		return (String)get_Value(COLUMNNAME_Operation);
	}

	/** Set Quality Specification.
		@param QM_Specification_ID Quality Specification	  */
	public void setQM_Specification_ID (int QM_Specification_ID)
	{
		if (QM_Specification_ID < 1) 
			set_Value (COLUMNNAME_QM_Specification_ID, null);
		else 
			set_Value (COLUMNNAME_QM_Specification_ID, Integer.valueOf(QM_Specification_ID));
	}

	/** Get Quality Specification.
		@return Quality Specification	  */
	public int getQM_Specification_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QM_Specification_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QM Specification Line.
		@param QM_SpecificationLine_ID QM Specification Line	  */
	public void setQM_SpecificationLine_ID (int QM_SpecificationLine_ID)
	{
		if (QM_SpecificationLine_ID < 1) 
			set_Value (COLUMNNAME_QM_SpecificationLine_ID, null);
		else 
			set_Value (COLUMNNAME_QM_SpecificationLine_ID, Integer.valueOf(QM_SpecificationLine_ID));
	}

	/** Get QM Specification Line.
		@return QM Specification Line	  */
	public int getQM_SpecificationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QM_SpecificationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (String ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public String getValidFrom () 
	{
		return (String)get_Value(COLUMNNAME_ValidFrom);
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
}