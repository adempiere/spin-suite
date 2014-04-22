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
import org.spinsuite.util.KeyNamePair;

/** Generated Model for C_PaymentBatch
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_PaymentBatch extends PO implements I_C_PaymentBatch
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_C_PaymentBatch (Context ctx, int C_PaymentBatch_ID, DB conn)
    {
      super (ctx, C_PaymentBatch_ID, conn);
      /** if (C_PaymentBatch_ID == 0)
        {
			setC_PaymentBatch_ID (0);
			setC_PaymentProcessor_ID (0);
			setName (null);
			setProcessed (false);
			setProcessing (false);
        } */
    }

    /** Load Constructor */
    public X_C_PaymentBatch (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_PaymentBatch[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Payment Batch.
		@param C_PaymentBatch_ID 
		Payment batch for EFT
	  */
	public void setC_PaymentBatch_ID (int C_PaymentBatch_ID)
	{
		if (C_PaymentBatch_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentBatch_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentBatch_ID, Integer.valueOf(C_PaymentBatch_ID));
	}

	/** Get Payment Batch.
		@return Payment batch for EFT
	  */
	public int getC_PaymentBatch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentBatch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Processor.
		@param C_PaymentProcessor_ID 
		Payment processor for electronic payments
	  */
	public void setC_PaymentProcessor_ID (int C_PaymentProcessor_ID)
	{
		if (C_PaymentProcessor_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentProcessor_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentProcessor_ID, Integer.valueOf(C_PaymentProcessor_ID));
	}

	/** Get Payment Processor.
		@return Payment processor for electronic payments
	  */
	public int getC_PaymentProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
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

	/** Set Processing date.
		@param ProcessingDate Processing date	  */
	public void setProcessingDate (Date ProcessingDate)
	{
		set_Value (COLUMNNAME_ProcessingDate, ProcessingDate);
	}

	/** Get Processing date.
		@return Processing date	  */
	public Date getProcessingDate () 
	{
		return (Date)get_Value(COLUMNNAME_ProcessingDate);
	}
}