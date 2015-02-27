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

import java.math.BigDecimal;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.process.DocAction;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MFTATechnicalForm extends X_FTA_TechnicalForm implements DocAction {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/05/2014, 21:40:37
	 * @param ctx
	 * @param FTA_TechnicalForm_ID
	 * @param conn
	 */
	public MFTATechnicalForm(Context ctx, int FTA_TechnicalForm_ID, DB conn) {
		super(ctx, FTA_TechnicalForm_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/05/2014, 21:40:37
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MFTATechnicalForm(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}

	/**	Process Message			*/
	private String m_ProcessMsg = null;
	
	@Override
	public boolean processIt(String action) throws Exception {
		return false;
	}

	@Override
	public boolean unlockIt() {
		return false;
	}

	@Override
	public boolean invalidateIt() {
		return false;
	}

	@Override
	public String prepareIt() {
		m_ProcessMsg = null;
		//	Valid Lines
		int lines = DB.getSQLValue(getCtx(), "SELECT COUNT(FTA_TechnicalFormLine_ID) " +
				"FROM FTA_TechnicalFormLine " +
				"WHERE FTA_TechnicalForm_ID = " + getFTA_TechnicalForm_ID());
		if(lines == 0) {
			m_ProcessMsg = "@NoLines@";
			return STATUS_Invalid;
		}
		return STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		return false;
	}

	@Override
	public boolean rejectIt() {
		return false;
	}

	@Override
	public String completeIt() {
		m_ProcessMsg = null;
		setProcessed(true);
		return STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		//	Processing to true
		setProcessed(true);
		//	
		setDescription(getDescription() != null
				? getDescription() + " --> " + Msg.getMsg(getCtx(), "Voided")
				: Msg.getMsg(getCtx(), "Voided"));
		return true;
	}

	@Override
	public boolean closeIt() {
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		return false;
	}

	@Override
	public boolean reActivateIt() {
		m_ProcessMsg = null;
		//	Processed on false
		setProcessed(false);
		return true;
	}

	@Override
	public String getSummary() {
		return null;
	}

	@Override
	public String getDocumentInfo() {
		return getDocumentNo();
	}

	@Override
	public String getProcessMsg() {
		return m_ProcessMsg;
	}

	@Override
	public int getDoc_User_ID() {
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return null;
	}

	@Override
	public int get_Table_ID() {
		return SPS_Table_ID;
	}

	@Override
	public DB get_DB() {
		return super.get_Connection();
	}
	
	@Override
	public void setProcessed(boolean Processed) {
		//	Update Lines
		StringBuffer sql = new StringBuffer("UPDATE ")
					.append(I_FTA_TechnicalFormLine.Table_Name)
					.append(" SET ")
					.append(I_FTA_TechnicalFormLine.COLUMNNAME_Processed).append(" = ").append(Processed? "'Y'": "'N'")
					.append(" WHERE ").append(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_TechnicalForm_ID).append(" = ?");
		//	Update
		DB.executeUpdate(getCtx(), sql.toString(), getFTA_TechnicalForm_ID());
		//	Update Suggested Product
		sql = new StringBuffer("UPDATE ")
					.append(I_FTA_ProductsToApply.Table_Name)
					.append(" SET ")
					.append(I_FTA_ProductsToApply.COLUMNNAME_Processed).append(" = ").append(Processed? "'Y'": "'N'")
					.append(" WHERE ").append(I_FTA_ProductsToApply.COLUMNNAME_FTA_TechnicalForm_ID).append(" = ?");
		//	Update
		DB.executeUpdate(getCtx(), sql.toString(), getFTA_TechnicalForm_ID());
		//	Processed
		setIsApproved(Processed);
		super.setProcessed(Processed);
	}
	
	@Override
	protected boolean beforeDelete() {
		m_ProcessMsg = null;
		boolean ok = super.beforeDelete();
		//	
		if(!ok)
			return ok;
		//	
		try {
			//	Delete children
			StringBuffer sql = new StringBuffer("DELETE FROM ")
						.append(I_FTA_TechnicalFormLine.Table_Name)
						.append(" WHERE ").append(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_TechnicalForm_ID).append(" = ?");
			//	Update
			int deleted = DB.executeUpdate(getCtx(), sql.toString(), getFTA_TechnicalForm_ID(), false, get_DB());
			//	Log
			LogM.log(getCtx(), getClass(), Level.FINE, "Deleted Lines = " + deleted);
			//	Update Suggested Product
			sql = new StringBuffer("DELETE FROM ")
						.append(I_FTA_ProductsToApply.Table_Name)
						.append(" WHERE ").append(I_FTA_ProductsToApply.COLUMNNAME_FTA_TechnicalForm_ID).append(" = ?");
			//	Update
			deleted = DB.executeUpdate(getCtx(), sql.toString(), getFTA_TechnicalForm_ID(), false, get_DB());
			//	Log
			LogM.log(getCtx(), getClass(), Level.FINE, "Deleted Product to Apply = " + deleted);
			//	
			ok = true;
		} catch (Exception e) {
			ok = false;
			m_ProcessMsg = e.toString();
			LogM.log(getCtx(), getClass(), Level.SEVERE, "Error to Delete", e);
		}
		//	
		return ok;
	}
	
	@Override
	public String getError() {
		return m_ProcessMsg;
	}
}
