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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.model;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.process.DocAction;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 5/8/2015, 23:10:42
 *
 */
public class MCash extends X_C_Cash implements DocAction{

	/**	Process Message				*/
	private String 					m_ProcessMsg 			= null;
	/**	Order Lines					*/
	private MCashLine[] 	mLines 		= null;
	
	public MCash(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		// TODO Auto-generated constructor stub
	}

	public MCash(Context ctx, int C_Cash_ID, DB conn) {
		super (ctx, C_Cash_ID, conn);
		if (C_Cash_ID == 0)
		{
		//	setC_CashBook_ID (0);		//	FK
			setBeginningBalance (Env.ZERO);
			setEndingBalance (Env.ZERO);
			setStatementDifference(Env.ZERO);
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			//
//			Timestamp today = TimeUtil.getDay(System.currentTimeMillis());
//			setStatementDate (today);	// @#Date@
//			setDateAcct (today);	// @#Date@
//			String name = DisplayType.getDagetDateFormat(DisplayType.DATE).format(today)
//				+ " " + MOrg.get(ctx, getAD_Org_ID()).getValue();
//			setName (name);	
			setIsApproved(false);
			setPosted (false);	// N
			setProcessed (false);
		}
	}

	@Override
	public boolean processIt(String action) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
		m_ProcessMsg = null;
		//	Valid Lines
		int lines = DB.getSQLValue(getCtx(), "SELECT COUNT(C_CashLine_ID) " +
				"FROM C_CashLine " +
				"WHERE C_Cash_ID = " + getC_Cash_ID());
		if(lines == 0) {
			m_ProcessMsg = "@NoLines@";
			//	
			return STATUS_Invalid;
		}
		return STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
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
		addDescription(" --> " + Msg.getMsg(getCtx(), "Voided"));
		return true;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
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
		return m_ProcessMsg;
	}

	@Override
	public String getDocumentNo() {
		return m_ProcessMsg;
	}

	@Override
	public String getDocumentInfo() {
		return m_ProcessMsg;
	}

	@Override
	public String getProcessMsg() {
		return m_ProcessMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getC_DocType_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_Table_ID() {
		return SPS_Table_ID;
	}

	@Override
	public DB get_DB() {
		return get_Connection();
	}
	
	@Override
	public void setProcessed(boolean Processed) {
		super.setProcessed (Processed);
		if (get_ID() == 0)
			return;
		String set = "SET Processed='"
			+ (Processed ? "Y" : "N")
			+ "' WHERE C_Cash_ID=" + getC_Cash_ID();
		int noLine = DB.executeUpdate(getCtx(), "UPDATE C_CashLine " + set, null);
		mLines = null;
		LogM.log(getCtx(), getClass(), Level.FINE, 
				"setProcessed - " + Processed + " - Lines=" + noLine );
	}

	@Override
	public String getError() {
		if(m_ProcessMsg == null)
			m_ProcessMsg = super.getError();
		//	Return
		return m_ProcessMsg;
	}
	
	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription
	
}
