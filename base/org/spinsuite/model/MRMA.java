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
import java.util.List;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.process.DocAction;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/7/2015, 21:28:43
 *
 */
public class MRMA extends X_M_RMA implements DocAction{
	
	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param M_RMA_ID
	 * @param conn
	 */
	public MRMA(Context ctx, int M_RMA_ID, DB conn) {
		super(ctx, M_RMA_ID, conn);

		if (M_RMA_ID == 0) {
			setDocAction (DOCACTION_Complete);	// CO
			setDocStatus (DOCSTATUS_Drafted);	// DR
			setIsApproved(false);
			super.setProcessed (false);
		}
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MRMA(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		
	}
	
	@Override
	protected boolean loadDefaultValues() {
		boolean ok = super.loadDefaultValues();
		//	
		loadDefault();
		return ok;
	}


	/**
	 * Load Default Values
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public void loadDefault() {
		setDocStatus(DOCSTATUS_Drafted);
		setDocAction (DOCACTION_Prepare);
		//
		setIsApproved(false);
		//
		super.setProcessed(false);
		setProcessing(false);
		
	}

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
		
		MRMALine[] lines = getLines(false);
		
		//	Valid Lines
		if(lines.length == 0) {
			m_ProcessMsg = "@NoLines@";
			//	
			return STATUS_Invalid;
		}
		
		m_JustPrepared = true;
		return STATUS_InProgress;
	}
	
	

	@Override
	public boolean approveIt() {
		setIsApproved(true);
		return true;
	}
	

	@Override
	public boolean rejectIt() {
		return false;
	}

	@Override
	public String completeIt() {
		//	Re-Check
		if (!m_JustPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		
		m_ProcessMsg = null;
		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
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
		return getDocumentNo();
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
	public BigDecimal getApprovalAmt() {
		return null;
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
			+ "' WHERE M_RMA_ID=" + getM_RMA_ID();
		int noLine = DB.executeUpdate(getCtx(), "UPDATE M_RMA " + set, null);
		m_lines = null;
		LogM.log(getCtx(), getClass(), Level.FINE, 
				"setProcessed - " + Processed + " - Lines=" + noLine );
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
	

	/**
	 * 	Get Lines
	 *	@param requery requery
	 *	@return lines
	 */
	public MRMALine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery)
		{
			return m_lines;
		}
		List<MRMALine> list = new Query(getCtx(), I_M_RMALine.Table_Name, "M_RMA_ID=?", null)
		.setParameters(getM_RMA_ID())
		.setOrderBy(MRMALine.COLUMNNAME_Line)
		.list();

		m_lines = new MRMALine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines
	
	
	
	/**	Just Prepared Flag			*/
	private boolean		m_JustPrepared = false;
	/** Lines					*/
	private MRMALine[]		m_lines = null;
	
	/**	Process Message				*/
	private String 					m_ProcessMsg 			= null;

	
}
