/*******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution * Copyright (C)
 * 1999-2006 Adempiere, Inc. All Rights Reserved. * This program is free
 * software; you can redistribute it and/or modify it * under the terms version
 * 2 of the GNU General Public License as published * by the Free Software
 * Foundation. This program is distributed in the hope * that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied * warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. * See the GNU General
 * Public License for more details. * You should have received a copy of the GNU
 * General Public License along * with this program; if not, write to the Free
 * Software Foundation, Inc., * 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA. *
 * 
 * Copyright (C) 2004 Marco LOMBARDO. lombardo@mayking.com 
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 * Contributor(s): Yamel Senih yamelsenih@gmail.com
 * __________________________________________
 ******************************************************************************/

// ----------------------------------------------------------------------
// Generic PO.
package org.spinsuite.model;

// import for GenericPO
import org.spinsuite.base.DB;

import android.content.Context;

/**
 * Generic PO implementation, this can be use together with ModelValidator as alternative to the classic 
 * generated model class and extend ( X_ & M_ ) approach.
 * 
 * Originally for used to insert/update data from adempieredata.xml file in 2pack.
 * @author Marco LOMBARDO
 * @contributor Low Heng Sin
 * @contributor <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 2014-10-11, 12:33:25
 * <li> Add support to mobile Application
 */
public class GenericPO extends PO {
	
	/**
	 * Default with Identifier
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/10/2014, 12:35:50
	 * @param ctx
	 * @param ID
	 * @param tableName
	 * @param conn
	 */
	public GenericPO(Context ctx, String tableName, int ID, DB conn) {
		super(ctx, tableName, ID, conn);
	}

	/** Load Meta Data */
    protected POInfo initPO (Context ctx) {
      return null;
    }

    public String toString() {
      StringBuffer sb = new StringBuffer ("Generic PO[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public static final int AD_ORGTRX_ID_AD_Reference_ID = 130;

	/**
	 * Set Trx Organization. Performing or initiating organization
	 */
	public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
		if (AD_OrgTrx_ID == 0)
			set_Value("AD_OrgTrx_ID", null);
		else
			set_Value("AD_OrgTrx_ID", AD_OrgTrx_ID);
	}

	/**
	 * Get Trx Organization. Performing or initiating organization
	 */
	public int getAD_OrgTrx_ID() {
		Integer ii = (Integer) get_Value("AD_OrgTrx_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

} // GenericPO
