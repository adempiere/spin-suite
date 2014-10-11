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

/** Generated Model for WS_WebServiceFieldOutput
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_WS_WebServiceFieldOutput extends PO implements I_WS_WebServiceFieldOutput
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141011L;

    /** Standard Constructor */
    public X_WS_WebServiceFieldOutput (Context ctx, int WS_WebServiceFieldOutput_ID, DB conn)
    {
      super (ctx, WS_WebServiceFieldOutput_ID, conn);
      /** if (WS_WebServiceFieldOutput_ID == 0)
        {
			setWS_WebServiceFieldOutput_ID (0);
			setWS_WebServiceType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WS_WebServiceFieldOutput (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_WS_WebServiceFieldOutput[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Service Field Output.
		@param WS_WebServiceFieldOutput_ID Web Service Field Output	  */
	public void setWS_WebServiceFieldOutput_ID (int WS_WebServiceFieldOutput_ID)
	{
		if (WS_WebServiceFieldOutput_ID < 1) 
			set_Value (COLUMNNAME_WS_WebServiceFieldOutput_ID, null);
		else 
			set_Value (COLUMNNAME_WS_WebServiceFieldOutput_ID, Integer.valueOf(WS_WebServiceFieldOutput_ID));
	}

	/** Get Web Service Field Output.
		@return Web Service Field Output	  */
	public int getWS_WebServiceFieldOutput_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WS_WebServiceFieldOutput_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Service Type.
		@param WS_WebServiceType_ID Web Service Type	  */
	public void setWS_WebServiceType_ID (int WS_WebServiceType_ID)
	{
		if (WS_WebServiceType_ID < 1) 
			set_Value (COLUMNNAME_WS_WebServiceType_ID, null);
		else 
			set_Value (COLUMNNAME_WS_WebServiceType_ID, Integer.valueOf(WS_WebServiceType_ID));
	}

	/** Get Web Service Type.
		@return Web Service Type	  */
	public int getWS_WebServiceType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WS_WebServiceType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}