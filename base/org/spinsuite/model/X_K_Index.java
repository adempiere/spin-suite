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

/** Generated Model for K_Index
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_K_Index extends PO implements I_K_Index
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_K_Index (Context ctx, int K_Index_ID, DB conn)
    {
      super (ctx, K_Index_ID, conn);
      /** if (K_Index_ID == 0)
        {
			setAD_Table_ID (0);
			setKeyword (null);
			setK_INDEX_ID (0);
			setRecord_ID (0);
			setSourceUpdated (new Date( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_K_Index (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_K_Index[")
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

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Project.
		@param CM_WebProject_ID 
		A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public void setCM_WebProject_ID (int CM_WebProject_ID)
	{
		if (CM_WebProject_ID < 1) 
			set_Value (COLUMNNAME_CM_WebProject_ID, null);
		else 
			set_Value (COLUMNNAME_CM_WebProject_ID, Integer.valueOf(CM_WebProject_ID));
	}

	/** Get Web Project.
		@return A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public int getCM_WebProject_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_WebProject_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Excerpt.
		@param Excerpt 
		Surrounding text of the keyword
	  */
	public void setExcerpt (String Excerpt)
	{
		set_Value (COLUMNNAME_Excerpt, Excerpt);
	}

	/** Get Excerpt.
		@return Surrounding text of the keyword
	  */
	public String getExcerpt () 
	{
		return (String)get_Value(COLUMNNAME_Excerpt);
	}

	/** Set Keyword.
		@param Keyword 
		Case insensitive keyword
	  */
	public void setKeyword (String Keyword)
	{
		set_Value (COLUMNNAME_Keyword, Keyword);
	}

	/** Get Keyword.
		@return Case insensitive keyword
	  */
	public String getKeyword () 
	{
		return (String)get_Value(COLUMNNAME_Keyword);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getKeyword());
    }

	/** Set Index.
		@param K_INDEX_ID 
		Text Search Index
	  */
	public void setK_INDEX_ID (int K_INDEX_ID)
	{
		if (K_INDEX_ID < 1) 
			set_Value (COLUMNNAME_K_INDEX_ID, null);
		else 
			set_Value (COLUMNNAME_K_INDEX_ID, Integer.valueOf(K_INDEX_ID));
	}

	/** Get Index.
		@return Text Search Index
	  */
	public int getK_INDEX_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_INDEX_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Request Type.
		@param R_RequestType_ID 
		Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public void setR_RequestType_ID (int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1) 
			set_Value (COLUMNNAME_R_RequestType_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
	}

	/** Get Request Type.
		@return Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public int getR_RequestType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Source Updated.
		@param SourceUpdated 
		Date the source document was updated
	  */
	public void setSourceUpdated (Date SourceUpdated)
	{
		set_Value (COLUMNNAME_SourceUpdated, SourceUpdated);
	}

	/** Get Source Updated.
		@return Date the source document was updated
	  */
	public Date getSourceUpdated () 
	{
		return (Date)get_Value(COLUMNNAME_SourceUpdated);
	}
}