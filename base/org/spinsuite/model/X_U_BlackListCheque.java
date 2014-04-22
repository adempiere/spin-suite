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

/** Generated Model for U_BlackListCheque
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_U_BlackListCheque extends PO implements I_U_BlackListCheque
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_U_BlackListCheque (Context ctx, int U_BlackListCheque_ID, DB conn)
    {
      super (ctx, U_BlackListCheque_ID, conn);
      /** if (U_BlackListCheque_ID == 0)
        {
			setBankName (null);
			setChequeNo (null);
			setU_BlackListCheque_ID (0);
        } */
    }

    /** Load Constructor */
    public X_U_BlackListCheque (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_U_BlackListCheque[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Bank Name.
		@param BankName Bank Name	  */
	public void setBankName (String BankName)
	{
		set_Value (COLUMNNAME_BankName, BankName);
	}

	/** Get Bank Name.
		@return Bank Name	  */
	public String getBankName () 
	{
		return (String)get_Value(COLUMNNAME_BankName);
	}

	/** Set Cheque No.
		@param ChequeNo Cheque No	  */
	public void setChequeNo (String ChequeNo)
	{
		set_Value (COLUMNNAME_ChequeNo, ChequeNo);
	}

	/** Get Cheque No.
		@return Cheque No	  */
	public String getChequeNo () 
	{
		return (String)get_Value(COLUMNNAME_ChequeNo);
	}

	/** Set Black List Cheque.
		@param U_BlackListCheque_ID Black List Cheque	  */
	public void setU_BlackListCheque_ID (int U_BlackListCheque_ID)
	{
		if (U_BlackListCheque_ID < 1) 
			set_Value (COLUMNNAME_U_BlackListCheque_ID, null);
		else 
			set_Value (COLUMNNAME_U_BlackListCheque_ID, Integer.valueOf(U_BlackListCheque_ID));
	}

	/** Get Black List Cheque.
		@return Black List Cheque	  */
	public int getU_BlackListCheque_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_U_BlackListCheque_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}