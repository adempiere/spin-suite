/*************************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                                   *
 * This program is free software; you can redistribute it and/or modify it           *
 * under the terms version 2 of the GNU General Public License as published          *
 * by the Free Software Foundation. This program is distributed in the hope          *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied        *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                  *
 * See the GNU General Public License for more details.                              *
 * You should have received a copy of the GNU General Public License along           *
 * with this program; if not, write to the Free Software Foundation, Inc.,           *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                            *
 * For the text or an alternative of this public license, you may reach us           *
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.model;

import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.GridTab;

import android.content.Context;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class CalloutTechnicalForm extends CalloutEngine {

	/**
	 * Set Document Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/05/2014, 09:56:27
	 * @param ctx
	 * @param m_ActivityNo
	 * @param m_Tab
	 * @param m_Field
	 * @param value
	 * @return
	 * @return String
	 */
	public String docType(Context ctx, int m_ActivityNo, GridTab m_Tab, GridField m_Field, Object value) {
		Integer p_DocType_ID = (Integer) value;
		//	
		if (p_DocType_ID == null || p_DocType_ID.intValue() == 0)
			return "";
		
		String documentNo = m_Tab.getValueAsString("DocumentNo");
		if(documentNo != null
				&& documentNo.length() > 0)
			return "";
		
		String seqNo = MSequence.getDocumentNo(ctx, p_DocType_ID.intValue(), null, true, null);
		m_Tab.setValue("DocumentNo", seqNo);
		return "";
	}

}
