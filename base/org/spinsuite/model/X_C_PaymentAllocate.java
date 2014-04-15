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

/** Generated Model for C_PaymentAllocate
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_C_PaymentAllocate extends PO implements I_C_PaymentAllocate
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140415L;

    /** Standard Constructor */
    public X_C_PaymentAllocate (Context ctx, int C_PaymentAllocate_ID, DB conn)
    {
      super (ctx, C_PaymentAllocate_ID, conn);
      /** if (C_PaymentAllocate_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_PaymentAllocate (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_C_PaymentAllocate[")
        .append(get_ID()).append("]");
      return sb.toString();
    }
}