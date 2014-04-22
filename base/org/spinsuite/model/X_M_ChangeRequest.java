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
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for M_ChangeRequest
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_M_ChangeRequest extends PO implements I_M_ChangeRequest
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_M_ChangeRequest (Context ctx, int M_ChangeRequest_ID, DB conn)
    {
      super (ctx, M_ChangeRequest_ID, conn);
      /** if (M_ChangeRequest_ID == 0)
        {
			setDocumentNo (null);
			setIsApproved (false);
// N
			setM_ChangeRequest_ID (0);
			setName (null);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_M_ChangeRequest (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_M_ChangeRequest[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Detail Information.
		@param DetailInfo 
		Additional Detail Information
	  */
	public void setDetailInfo (String DetailInfo)
	{
		set_Value (COLUMNNAME_DetailInfo, DetailInfo);
	}

	/** Get Detail Information.
		@return Additional Detail Information
	  */
	public String getDetailInfo () 
	{
		return (String)get_Value(COLUMNNAME_DetailInfo);
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

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Change Notice.
		@param M_ChangeNotice_ID 
		Bill of Materials (Engineering) Change Notice (Version)
	  */
	public void setM_ChangeNotice_ID (int M_ChangeNotice_ID)
	{
		if (M_ChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, Integer.valueOf(M_ChangeNotice_ID));
	}

	/** Get Change Notice.
		@return Bill of Materials (Engineering) Change Notice (Version)
	  */
	public int getM_ChangeNotice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ChangeNotice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Change Request.
		@param M_ChangeRequest_ID Change Request	  */
	public void setM_ChangeRequest_ID (int M_ChangeRequest_ID)
	{
		if (M_ChangeRequest_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeRequest_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeRequest_ID, Integer.valueOf(M_ChangeRequest_ID));
	}

	/** Get Change Request.
		@return Change Request	  */
	public int getM_ChangeRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ChangeRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fixed in.
		@param M_FixChangeNotice_ID 
		Fixed in Change Notice
	  */
	public void setM_FixChangeNotice_ID (int M_FixChangeNotice_ID)
	{
		if (M_FixChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_FixChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_FixChangeNotice_ID, Integer.valueOf(M_FixChangeNotice_ID));
	}

	/** Get Fixed in.
		@return Fixed in Change Notice
	  */
	public int getM_FixChangeNotice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FixChangeNotice_ID);
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

	/** Set BOM & Formula.
		@param PP_Product_BOM_ID 
		BOM & Formula
	  */
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, Integer.valueOf(PP_Product_BOM_ID));
	}

	/** Get BOM & Formula.
		@return BOM & Formula
	  */
	public int getPP_Product_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOM_ID);
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
}