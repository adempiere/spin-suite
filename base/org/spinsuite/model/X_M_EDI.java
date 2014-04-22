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

/** Generated Model for M_EDI
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_EDI extends PO implements I_M_EDI
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_EDI (Context ctx, int M_EDI_ID, DB conn)
    {
      super (ctx, M_EDI_ID, conn);
      /** if (M_EDI_ID == 0)
        {
			setC_BP_EDI_ID (0);
			setDocumentNo (null);
			setEDIStatus (null);
			setLine (0);
			setM_EDI_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setProcessed (false);
			setRequest_Qty (Env.ZERO);
			setRequest_Shipdate (new Date( System.currentTimeMillis() ));
			setTrxSent (new Date( System.currentTimeMillis() ));
			setTrxType (null);
        } */
    }

    /** Load Constructor */
    public X_M_EDI (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_EDI[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set EDI Definition.
		@param C_BP_EDI_ID 
		Electronic Data Interchange
	  */
	public void setC_BP_EDI_ID (int C_BP_EDI_ID)
	{
		if (C_BP_EDI_ID < 1) 
			set_Value (COLUMNNAME_C_BP_EDI_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_EDI_ID, Integer.valueOf(C_BP_EDI_ID));
	}

	/** Get EDI Definition.
		@return Electronic Data Interchange
	  */
	public int getC_BP_EDI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_EDI_ID);
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

	/** EDIStatus AD_Reference_ID=202 */
	public static final int EDISTATUS_AD_Reference_ID=202;
	/** Draft = D */
	public static final String EDISTATUS_Draft = "D";
	/** Sent = S */
	public static final String EDISTATUS_Sent = "S";
	/** Acknowledged = A */
	public static final String EDISTATUS_Acknowledged = "A";
	/** Answered (complete) = C */
	public static final String EDISTATUS_AnsweredComplete = "C";
	/** Set EDI Status.
		@param EDIStatus EDI Status	  */
	public void setEDIStatus (String EDIStatus)
	{

		set_Value (COLUMNNAME_EDIStatus, EDIStatus);
	}

	/** Get EDI Status.
		@return EDI Status	  */
	public String getEDIStatus () 
	{
		return (String)get_Value(COLUMNNAME_EDIStatus);
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI Transaction.
		@param M_EDI_ID EDI Transaction	  */
	public void setM_EDI_ID (int M_EDI_ID)
	{
		if (M_EDI_ID < 1) 
			set_Value (COLUMNNAME_M_EDI_ID, null);
		else 
			set_Value (COLUMNNAME_M_EDI_ID, Integer.valueOf(M_EDI_ID));
	}

	/** Get EDI Transaction.
		@return EDI Transaction	  */
	public int getM_EDI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_EDI_ID);
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

	/** Set Reply Price.
		@param Reply_Price 
		Confirmed Price from EDI Partner
	  */
	public void setReply_Price (BigDecimal Reply_Price)
	{
		set_Value (COLUMNNAME_Reply_Price, Reply_Price);
	}

	/** Get Reply Price.
		@return Confirmed Price from EDI Partner
	  */
	public BigDecimal getReply_Price () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Reply_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reply Qty Available.
		@param Reply_QtyAvailable Reply Qty Available	  */
	public void setReply_QtyAvailable (BigDecimal Reply_QtyAvailable)
	{
		set_Value (COLUMNNAME_Reply_QtyAvailable, Reply_QtyAvailable);
	}

	/** Get Reply Qty Available.
		@return Reply Qty Available	  */
	public BigDecimal getReply_QtyAvailable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Reply_QtyAvailable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reply Qty Confirmed.
		@param Reply_QtyConfirmed Reply Qty Confirmed	  */
	public void setReply_QtyConfirmed (BigDecimal Reply_QtyConfirmed)
	{
		set_Value (COLUMNNAME_Reply_QtyConfirmed, Reply_QtyConfirmed);
	}

	/** Get Reply Qty Confirmed.
		@return Reply Qty Confirmed	  */
	public BigDecimal getReply_QtyConfirmed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Reply_QtyConfirmed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reply Received.
		@param Reply_Received Reply Received	  */
	public void setReply_Received (Date Reply_Received)
	{
		set_Value (COLUMNNAME_Reply_Received, Reply_Received);
	}

	/** Get Reply Received.
		@return Reply Received	  */
	public Date getReply_Received () 
	{
		return (Date)get_Value(COLUMNNAME_Reply_Received);
	}

	/** Set Reply Remarks.
		@param Reply_Remarks Reply Remarks	  */
	public void setReply_Remarks (String Reply_Remarks)
	{
		set_Value (COLUMNNAME_Reply_Remarks, Reply_Remarks);
	}

	/** Get Reply Remarks.
		@return Reply Remarks	  */
	public String getReply_Remarks () 
	{
		return (String)get_Value(COLUMNNAME_Reply_Remarks);
	}

	/** Set Reply Ship date.
		@param Reply_ShipDate Reply Ship date	  */
	public void setReply_ShipDate (Date Reply_ShipDate)
	{
		set_Value (COLUMNNAME_Reply_ShipDate, Reply_ShipDate);
	}

	/** Get Reply Ship date.
		@return Reply Ship date	  */
	public Date getReply_ShipDate () 
	{
		return (Date)get_Value(COLUMNNAME_Reply_ShipDate);
	}

	/** Set Request Price.
		@param Request_Price Request Price	  */
	public void setRequest_Price (BigDecimal Request_Price)
	{
		set_Value (COLUMNNAME_Request_Price, Request_Price);
	}

	/** Get Request Price.
		@return Request Price	  */
	public BigDecimal getRequest_Price () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Request_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Request Qty.
		@param Request_Qty Request Qty	  */
	public void setRequest_Qty (BigDecimal Request_Qty)
	{
		set_Value (COLUMNNAME_Request_Qty, Request_Qty);
	}

	/** Get Request Qty.
		@return Request Qty	  */
	public BigDecimal getRequest_Qty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Request_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Request Ship date.
		@param Request_Shipdate Request Ship date	  */
	public void setRequest_Shipdate (Date Request_Shipdate)
	{
		set_Value (COLUMNNAME_Request_Shipdate, Request_Shipdate);
	}

	/** Get Request Ship date.
		@return Request Ship date	  */
	public Date getRequest_Shipdate () 
	{
		return (Date)get_Value(COLUMNNAME_Request_Shipdate);
	}

	/** Set Transaction received.
		@param TrxReceived Transaction received	  */
	public void setTrxReceived (Date TrxReceived)
	{
		set_Value (COLUMNNAME_TrxReceived, TrxReceived);
	}

	/** Get Transaction received.
		@return Transaction received	  */
	public Date getTrxReceived () 
	{
		return (Date)get_Value(COLUMNNAME_TrxReceived);
	}

	/** Set Transaction sent.
		@param TrxSent Transaction sent	  */
	public void setTrxSent (Date TrxSent)
	{
		set_Value (COLUMNNAME_TrxSent, TrxSent);
	}

	/** Get Transaction sent.
		@return Transaction sent	  */
	public Date getTrxSent () 
	{
		return (Date)get_Value(COLUMNNAME_TrxSent);
	}

	/** TrxType AD_Reference_ID=203 */
	public static final int TRXTYPE_AD_Reference_ID=203;
	/** Inquiry = I */
	public static final String TRXTYPE_Inquiry = "I";
	/** Purchase Order = O */
	public static final String TRXTYPE_PurchaseOrder = "O";
	/** Set Transaction Type.
		@param TrxType 
		Type of credit card transaction
	  */
	public void setTrxType (String TrxType)
	{

		set_Value (COLUMNNAME_TrxType, TrxType);
	}

	/** Get Transaction Type.
		@return Type of credit card transaction
	  */
	public String getTrxType () 
	{
		return (String)get_Value(COLUMNNAME_TrxType);
	}
}