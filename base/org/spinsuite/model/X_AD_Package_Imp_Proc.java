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

/** Generated Model for AD_Package_Imp_Proc
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_AD_Package_Imp_Proc extends PO implements I_AD_Package_Imp_Proc
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_AD_Package_Imp_Proc (Context ctx, int AD_Package_Imp_Proc_ID, DB conn)
    {
      super (ctx, AD_Package_Imp_Proc_ID, conn);
      /** if (AD_Package_Imp_Proc_ID == 0)
        {
			setAD_Package_Dir (null);
			setAD_Package_Imp_Proc_ID (0);
			setAD_Package_Source (null);
			setAD_Package_Source_Type (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Package_Imp_Proc (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_AD_Package_Imp_Proc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Update System Maintained Application Dictionary.
		@param AD_Override_Dict Update System Maintained Application Dictionary	  */
	public void setAD_Override_Dict (boolean AD_Override_Dict)
	{
		set_Value (COLUMNNAME_AD_Override_Dict, Boolean.valueOf(AD_Override_Dict));
	}

	/** Get Update System Maintained Application Dictionary.
		@return Update System Maintained Application Dictionary	  */
	public boolean isAD_Override_Dict () 
	{
		Object oo = get_Value(COLUMNNAME_AD_Override_Dict);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Package Directory.
		@param AD_Package_Dir 
		Package directory, default to AdempiereHome/packages
	  */
	public void setAD_Package_Dir (String AD_Package_Dir)
	{
		set_Value (COLUMNNAME_AD_Package_Dir, AD_Package_Dir);
	}

	/** Get Package Directory.
		@return Package directory, default to AdempiereHome/packages
	  */
	public String getAD_Package_Dir () 
	{
		return (String)get_Value(COLUMNNAME_AD_Package_Dir);
	}

	/** Set Package Imp. Proc..
		@param AD_Package_Imp_Proc_ID Package Imp. Proc.	  */
	public void setAD_Package_Imp_Proc_ID (int AD_Package_Imp_Proc_ID)
	{
		if (AD_Package_Imp_Proc_ID < 1) 
			set_Value (COLUMNNAME_AD_Package_Imp_Proc_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Package_Imp_Proc_ID, Integer.valueOf(AD_Package_Imp_Proc_ID));
	}

	/** Get Package Imp. Proc..
		@return Package Imp. Proc.	  */
	public int getAD_Package_Imp_Proc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Package_Imp_Proc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Package Source.
		@param AD_Package_Source 
		Fully qualified package source file name
	  */
	public void setAD_Package_Source (String AD_Package_Source)
	{
		set_Value (COLUMNNAME_AD_Package_Source, AD_Package_Source);
	}

	/** Get Package Source.
		@return Fully qualified package source file name
	  */
	public String getAD_Package_Source () 
	{
		return (String)get_Value(COLUMNNAME_AD_Package_Source);
	}

	/** AD_Package_Source_Type AD_Reference_ID=50005 */
	public static final int AD_PACKAGE_SOURCE_TYPE_AD_Reference_ID=50005;
	/** File = File */
	public static final String AD_PACKAGE_SOURCE_TYPE_File = "File";
	/** WebService = WS */
	public static final String AD_PACKAGE_SOURCE_TYPE_WebService = "WS";
	/** Set Package Source Type.
		@param AD_Package_Source_Type 
		Type of package source - file, ftp, webservice etc
	  */
	public void setAD_Package_Source_Type (String AD_Package_Source_Type)
	{

		set_Value (COLUMNNAME_AD_Package_Source_Type, AD_Package_Source_Type);
	}

	/** Get Package Source Type.
		@return Type of package source - file, ftp, webservice etc
	  */
	public String getAD_Package_Source_Type () 
	{
		return (String)get_Value(COLUMNNAME_AD_Package_Source_Type);
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
}