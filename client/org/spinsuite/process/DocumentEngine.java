/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.spinsuite.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.LogM;

import android.content.Context;

/**
 *	Document Action Engine
 *	
 *  @author Jorg Janke 
 *  @author Karsten Thiemann FR [ 1782412 ]
 *  @author victor.perez@e-evolution.com www.e-evolution.com FR [ 1866214 ]  http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335
 *  @contributor <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 2014-05-06, 11:09:45
 *  		Add Support to Mobile Application
 *  @version $Id: DocumentEngine.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public class DocumentEngine implements DocAction
{
	/**
	 * 	Doc Engine (Drafted)
	 * 	@param po document
	 */
	public DocumentEngine (DocAction po) {
		this (po, STATUS_Drafted);
	}	//	DocActionEngine
	
	/**
	 * 	Doc Engine
	 * 	@param po document
	 * 	@param docStatus initial document status
	 */
	public DocumentEngine (DocAction po, String docStatus) {
		m_document = po;
		if (docStatus != null)
			m_status = docStatus;
	}	//	DocActionEngine

	/** Persistent Document 	*/
	private DocAction	m_document;
	/** Document Status			*/
	private String		m_status = STATUS_Drafted;
	/**	Process Message 		*/
	private String		m_message = null;
	/** Actual Doc Action		*/
	private String		m_action = null;
	
	/**
	 * 	Get Doc Status
	 *	@return document status
	 */
	public String getDocStatus() {
		return m_status;
	}	//	getDocStatus

	/**
	 * 	Set Doc Status - Ignored
	 *	@param ignored Status is not set directly
	 * @see org.compiere.process.DocAction#processDocAction(String)
	 */
	public void setDocStatus(String ignored) {
	}	//	setDocStatus

	/**
	 * 	Document is Drafted
	 *	@return true if drafted
	 */
	public boolean isDrafted() {
		return STATUS_Drafted.equals(m_status);
	}	//	isDrafted
	
	/**
	 * 	Document is Invalid
	 *	@return true if Invalid
	 */
	public boolean isInvalid() {
		return STATUS_Invalid.equals(m_status);
	}	//	isInvalid
	
	/**
	 * 	Document is In Progress
	 *	@return true if In Progress
	 */
	public boolean isInProgress() {
		return STATUS_InProgress.equals(m_status);
	}	//	isInProgress
	
	/**
	 * 	Document is Approved
	 *	@return true if Approved
	 */
	public boolean isApproved() {
		return STATUS_Approved.equals(m_status);
	}	//	isApproved
	
	/**
	 * 	Document is Not Approved
	 *	@return true if Not Approved
	 */
	public boolean isNotApproved() {
		return STATUS_NotApproved.equals(m_status);
	}	//	isNotApproved
	
	/**
	 * 	Document is Waiting Payment or Confirmation
	 *	@return true if Waiting Payment
	 */
	public boolean isWaiting() {
		return STATUS_WaitingPayment.equals(m_status)
			|| STATUS_WaitingConfirmation.equals(m_status);
	}	//	isWaitingPayment
	
	/**
	 * 	Document is Completed
	 *	@return true if Completed
	 */
	public boolean isCompleted() {
		return STATUS_Completed.equals(m_status);
	}	//	isCompleted
	
	/**
	 * 	Document is Reversed
	 *	@return true if Reversed
	 */
	public boolean isReversed() {
		return STATUS_Reversed.equals(m_status);
	}	//	isReversed
	
	/**
	 * 	Document is Closed
	 *	@return true if Closed
	 */
	public boolean isClosed() {
		return STATUS_Closed.equals(m_status);
	}	//	isClosed
	
	/**
	 * 	Document is Voided
	 *	@return true if Voided
	 */
	public boolean isVoided() {
		return STATUS_Voided.equals(m_status);
	}	//	isVoided
	
	/**
	 * 	Document Status is Unknown
	 *	@return true if unknown
	 */
	public boolean isUnknown() {
		return STATUS_Unknown.equals(m_status) || 
			!(isDrafted() || isInvalid() || isInProgress() || isNotApproved()
				|| isApproved() || isWaiting() || isCompleted()
				|| isReversed() || isClosed() || isVoided());
	}	//	isUnknown

	
	/**
	 * 	Process actual document.
	 * 	Checks if user (document) action is valid and then process action 
	 * 	Calls the individual actions which call the document action
	 *	@param processAction document action based on workflow
	 *	@param docAction document action based on document
	 *	@return true if performed
	 */
	public boolean processIt (String processAction, String docAction) {
		m_message = null;
		m_action = null;
		//	Std User Workflows - see MWFNodeNext.isValidFor
		
		if (isValidAction(processAction))	//	WF Selection first
			m_action = processAction;
		//
		else if (isValidAction(docAction))	//	User Selection second
			m_action = docAction;
		//	Nothing to do
		else if (processAction.equals(ACTION_None)
			|| docAction.equals(ACTION_None)) {
			if (m_document != null)
				LogM.log(getCtx(), getClass(), Level.INFO, 
						"**** No Action (Prc=" + processAction + "/Doc=" + docAction + ") " + m_document);
			return true;	
		} else {
			throw new IllegalStateException("@InvalidAction@: "
				+ "@ProcessAction@="  + processAction 
				+ ", @DocAction@=" + docAction
				+ ", @DocStatus@=" + getDocStatus());
		}
		//	
		if (m_document != null)
			LogM.log(getCtx(), getClass(), Level.INFO, 
					"**** Action=" + m_action + " (Prc=" + processAction + "/Doc=" + docAction + ") " + m_document);
		boolean success = processIt (m_action);
		if (m_document != null) {
			m_message = m_document.getProcessMsg();
			LogM.log(getCtx(), getClass(), Level.INFO, 
					"**** Action=" + m_action + " - Success=" + success);
		}
		return success;
	}	//	process
	
	/**
	 * 	Process actual document - do not call directly.
	 * 	Calls the individual actions which call the document action
	 *	@param action document action
	 *	@return true if performed
	 */
	public boolean processIt (String action) {
		m_message = null;
		m_action = action;
		boolean success = false;
		//
		if (ACTION_Unlock.equals(m_action))
			success = unlockIt();
		else if (ACTION_Invalidate.equals(m_action))
			success = invalidateIt();
		else if (ACTION_Prepare.equals(m_action))
			success = STATUS_InProgress.equals(prepareIt());
		else if (ACTION_Approve.equals(m_action))
			success = approveIt();
		else if (ACTION_Reject.equals(m_action))
			success = rejectIt();
		else if (ACTION_Complete.equals(m_action) || ACTION_WaitComplete.equals(m_action)) {
			String status = null;
			if (isDrafted() || isInvalid()) {
				status = prepareIt();
				if (!STATUS_InProgress.equals(status))
					success = false;
				else
					success = true;
			}
			//	
			if(success) {
				status = completeIt();
				success = STATUS_Completed.equals(status)
							|| STATUS_InProgress.equals(status)
							|| STATUS_WaitingPayment.equals(status)
							|| STATUS_WaitingConfirmation.equals(status);
			}
		} 
		else if (ACTION_ReActivate.equals(m_action))
			success = reActivateIt();
		else if (ACTION_Reverse_Accrual.equals(m_action))
			success = reverseAccrualIt();
		else if (ACTION_Reverse_Correct.equals(m_action))
			success = reverseCorrectIt();
		else if (ACTION_Close.equals(m_action))
			success = closeIt();
		else if (ACTION_Void.equals(m_action))
			success = voidIt();
		else if (ACTION_Post.equals(m_action))
			success = postIt();
		//	Get Msg
		if (m_document != null) {
			m_message = m_document.getProcessMsg();
			LogM.log(getCtx(), getClass(), Level.INFO, 
					"**** Action = " + m_action + " Status = "  + m_status + " - Success=" + success);
		}
		//
		return success;
	}	//	processDocument
	
	/**
	 * 	Unlock Document.
	 * 	Status: Drafted
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#unlockIt()
	 */
	public boolean unlockIt() {
		if (!isValidAction(ACTION_Unlock))
			return false;
		if (m_document != null) {
			if (m_document.unlockIt()) {
				m_status = STATUS_Drafted;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Drafted;
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document.
	 * 	Status: Invalid
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#invalidateIt()
	 */
	public boolean invalidateIt() {
		if (!isValidAction(ACTION_Invalidate))
			return false;
		if (m_document != null) {
			if (m_document.invalidateIt()) {
				m_status = STATUS_Invalid;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Invalid;
		return true;
	}	//	invalidateIt
	
	/**
	 *	Process Document.
	 * 	Status is set by process
	 * 	@return new status (In Progress or Invalid) 
	 * 	@see org.compiere.process.DocAction#prepareIt()
	 */
	public String prepareIt() {
		if (!isValidAction(ACTION_Prepare))
			return m_status;
		if (m_document != null) {
			m_status = m_document.prepareIt();
			m_document.setDocStatus(m_status);
		}
		return m_status;
	}	//	processIt

	/**
	 * 	Approve Document.
	 * 	Status: Approved
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#approveIt()
	 */
	public boolean  approveIt() {
		if (!isValidAction(ACTION_Approve))
			return false;
		if (m_document != null) {
			if (m_document.approveIt()) {
				m_status = STATUS_Approved;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Approved;
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval.
	 * 	Status: Not Approved
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#rejectIt()
	 */
	public boolean rejectIt() {
		if (!isValidAction(ACTION_Reject))
			return false;
		if (m_document != null) {
			if (m_document.rejectIt()) {
				m_status = STATUS_NotApproved;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_NotApproved;
		return true;
	}	//	rejectIt
	
	/**
	 * 	Complete Document.
	 * 	Status is set by process
	 * 	@return new document status (Complete, In Progress, Invalid, Waiting ..)
	 * 	@see org.compiere.process.DocAction#completeIt()
	 */
	public String completeIt() {
		if (!isValidAction(ACTION_Complete))
			return m_status;
		if (m_document != null) {
			m_status = m_document.completeIt();
			m_document.setDocStatus(m_status);
		}
		return m_status;
	}	//	completeIt
	
	/**
	 * 	Post Document
	 * 	Does not change status
	 * 	@return true if success 
	 */
	public boolean postIt() {
		if (!isValidAction(ACTION_Post) 
			|| m_document == null)
			return false;

		String error = null;//DocumentEngine.postImmediate(Env.getCtx(), m_document.getAD_Client_ID(), m_document.get_Table_ID(), m_document.get_ID(), true, m_document.get_TrxName());
		return (error == null);
	}	//	postIt
	
	/**
	 * 	Void Document.
	 * 	Status: Voided
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#voidIt()
	 */
	public boolean voidIt() {
		if (!isValidAction(ACTION_Void))
			return false;
		if (m_document != null) {
			if (m_document.voidIt()) {
				m_status = STATUS_Voided;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Voided;
		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Status: Closed
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#closeIt()
	 */
	public boolean closeIt() {
		if (!isValidAction(ACTION_Close))
			return false;
		if (m_document != null) {
			if (m_document.closeIt()) {
				m_status = STATUS_Closed;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Closed;
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correct Document.
	 * 	Status: Reversed
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	public boolean reverseCorrectIt() {
		if (!isValidAction(ACTION_Reverse_Correct))
			return false;
		if (m_document != null) {
			if (m_document.reverseCorrectIt()) {
				m_status = STATUS_Reversed;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Reversed;
		return true;
	}	//	reverseCorrectIt
	
	/**
	 * 	Reverse Accrual Document.
	 * 	Status: Reversed
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	public boolean reverseAccrualIt() {
		if (!isValidAction(ACTION_Reverse_Accrual))
			return false;
		if (m_document != null) {
			if (m_document.reverseAccrualIt()) {
				m_status = STATUS_Reversed;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Reversed;
		return true;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate Document.
	 * 	Status: In Progress
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#reActivateIt()
	 */
	public boolean reActivateIt() {
		if (!isValidAction(ACTION_ReActivate))
			return false;
		if (m_document != null) {
			if (m_document.reActivateIt()) {
				m_status = STATUS_InProgress;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_InProgress;
		return true;
	}	//	reActivateIt

	
	/**
	 * 	Set Document Status to new Status
	 *	@param newStatus new status
	 */
	void setStatus (String newStatus) {
		m_status = newStatus;
	}	//	setStatus

	
	/**************************************************************************
	 * 	Get Action Options based on current Status
	 *	@return array of actions
	 */
	public String[] getActionOptions() {
		m_status = m_document.getDocStatus();
		//	Customized Valid Actions
		if(m_document instanceof DocOptions)
			return ((DocOptions) m_document).customizeValidActions(m_status);
		//	
		if (isInvalid())
			return new String[] {ACTION_Prepare, ACTION_Invalidate, 
				ACTION_Unlock, ACTION_Void};

		if (isDrafted())
			return new String[] {ACTION_Prepare, ACTION_Invalidate, ACTION_Complete, 
				ACTION_Unlock, ACTION_Void};
		
		if (isInProgress() || isApproved())
			return new String[] {ACTION_Complete, ACTION_WaitComplete, 
				ACTION_Approve, ACTION_Reject, 
				ACTION_Unlock, ACTION_Void, ACTION_Prepare};
		
		if (isNotApproved())
			return new String[] {ACTION_Reject, ACTION_Prepare, 
				ACTION_Unlock, ACTION_Void};
		
		if (isWaiting())
			return new String[] {ACTION_Complete, ACTION_WaitComplete,
				ACTION_ReActivate, ACTION_Void, ACTION_Close};
		if (isCompleted())
			return new String[] {ACTION_Reverse_Correct, ACTION_Close, 
				ACTION_ReActivate, ACTION_Reverse_Accrual, 
				ACTION_Post, ACTION_Void};
		if (isClosed())
			return new String[] {ACTION_Post, ACTION_ReOpen};
		
		if (isReversed() || isVoided())
			return new String[] {ACTION_Post};
		
		return new String[] {};
	}	//	getActionOptions

	/**
	 * 	Is The Action Valid based on current state
	 *	@param action action
	 *	@return true if valid
	 */
	public boolean isValidAction (String action) {
		String[] options = getActionOptions();
		for (int i = 0; i < options.length; i++) {
			if (options[i].equals(action))
				return true;
		}
		return false;
	}	//	isValidAction

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg () {
		return m_message;
	}	//	getProcessMsg
	
	/**
	 * 	Get Process Message
	 *	@param msg clear text error message
	 */
	public void setProcessMsg (String msg) {
		m_message = msg;
	}	//	setProcessMsg
	
	
	/**	Document Exception Message		*/
	private static String EXCEPTION_MSG = "Document Engine is no Document"; 
	
	/*************************************************************************
	 * 	Get Summary
	 *	@return throw exception
	 */
	public String getSummary() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Document No
	 *	@return throw exception
	 */
	public String getDocumentNo() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Info
	 *	@return throw exception
	 */
	public String getDocumentInfo() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Owner
	 *	@return throw exception
	 */
	public int getDoc_User_ID() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Document Currency
	 *	@return throw exception
	 */
	public int getC_Currency_ID() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Approval Amount
	 *	@return throw exception
	 */
	public BigDecimal getApprovalAmt() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Client
	 *	@return throw exception
	 */
	public int getAD_Client_ID() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Organization
	 *	@return throw exception
	 */
	public int getAD_Org_ID() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Doc Action
	 *	@return Document Action
	 */
	public String getDocAction() {
		return m_action;
	}

	/**
	 * 	Save Document
	 *	@return throw exception
	 */
	public boolean save() {
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Context
	 *	@return context
	 */
	public Context getCtx() {
		if (m_document != null)
			return m_document.getCtx();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	getCtx

	/**
	 * 	Get ID of record
	 *	@return ID
	 */
	public int get_ID() {
		if (m_document != null)
			return m_document.get_ID();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_ID
	
	/**
	 * 	Get AD_Table_ID
	 *	@return AD_Table_ID
	 */
	public int get_Table_ID() {
		if (m_document != null)
			return m_document.get_Table_ID();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_Table_ID
	
	/**
	 * 	Get Transaction
	 *	@return trx name
	 */
	public DB get_DB() {
		if (m_document != null)
			return m_document.get_DB();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_TrxName

	
	/**
	 * Get list of valid document action into the options array parameter. 
	 * Set default document action into the docAction array parameter.
	 * @param docStatus
	 * @param processing
	 * @param orderType
	 * @param isSOTrx
	 * @param AD_Table_ID
	 * @param docAction
	 * @param options
	 * @return Number of valid options
	 */
	/*public static int getValidActions(String docStatus, Object processing, 
			String orderType, String isSOTrx, int AD_Table_ID, String[] docAction, String[] options)
	{
		if (options == null)
			throw new IllegalArgumentException("Option array parameter is null");
		if (docAction == null)
			throw new IllegalArgumentException("Doc action array parameter is null");
		
		int index = 0;
		
		//	Locked
		if (processing != null)
		{
			boolean locked = "Y".equals(processing);
			if (!locked && processing instanceof Boolean)
				locked = ((Boolean)processing).booleanValue();
			if (locked)
				options[index++] = DocumentEngine.ACTION_Unlock;
		}

		//	Approval required           ..  NA
		if (docStatus.equals(DocumentEngine.STATUS_NotApproved))
		{
			options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Void;
		}
		//	Draft/Invalid				..  DR/IN
		else if (docStatus.equals(DocumentEngine.STATUS_Drafted)
			|| docStatus.equals(DocumentEngine.STATUS_Invalid))
		{
			options[index++] = DocumentEngine.ACTION_Complete;
		//	options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Void;
		}
		//	In Process                  ..  IP
		else if (docStatus.equals(DocumentEngine.STATUS_InProgress)
			|| docStatus.equals(DocumentEngine.STATUS_Approved))
		{
			options[index++] = DocumentEngine.ACTION_Complete;
			options[index++] = DocumentEngine.ACTION_Void;
		}
		//	Complete                    ..  CO
		else if (docStatus.equals(DocumentEngine.STATUS_Completed))
		{
			//options[index++] = DocumentEngine.ACTION_Close;
		}
		//	Waiting Payment
		else if (docStatus.equals(DocumentEngine.STATUS_WaitingPayment)
			|| docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
		{
			options[index++] = DocumentEngine.ACTION_Void;
			options[index++] = DocumentEngine.ACTION_Prepare;
		}
		//	Closed, Voided, REversed    ..  CL/VO/RE
		else if (docStatus.equals(DocumentEngine.STATUS_Closed) 
			|| docStatus.equals(DocumentEngine.STATUS_Voided) 
			|| docStatus.equals(DocumentEngine.STATUS_Reversed))
			return 0;		
		
		return index;
	}*/
	
	/**
	 * Fill Vector with DocAction Ref_List(135) values
	 * @param v_value
	 * @param v_name
	 * @param v_description
	 */
	/*public static void readReferenceList(ArrayList<String> v_value, ArrayList<String> v_name,
			ArrayList<String> v_description)
	{
		if (v_value == null) 
			throw new IllegalArgumentException("v_value parameter is null");
		if (v_name == null)
			throw new IllegalArgumentException("v_name parameter is null");
		if (v_description == null)
			throw new IllegalArgumentException("v_description parameter is null");
		
		String sql;
		if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
			sql = "SELECT Value, Name, Description FROM AD_Ref_List "
				+ "WHERE AD_Reference_ID=? ORDER BY Name";
		else
			sql = "SELECT l.Value, t.Name, t.Description "
				+ "FROM AD_Ref_List l, AD_Ref_List_Trl t "
				+ "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID"
				+ " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "'"
				+ " AND l.AD_Reference_ID=? ORDER BY t.Name";

		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, DocAction.AD_REFERENCE_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String value = rs.getString(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				if (description == null)
					description = "";
				//
				v_value.add(value);
				v_name.add(name);
				v_description.add(description);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}*/

	/**
	 * Checks the access rights of the given role/client for the given document actions.
	 * @param clientId
	 * @param roleId
	 * @param docTypeId
	 * @param options
	 * @param maxIndex
	 * @return number of valid actions in the String[] options
	 */
	/*public static int checkActionAccess(int clientId, int roleId, int docTypeId, String[] options, int maxIndex) {
		return MRole.get(Env.getCtx(), roleId).checkActionAccess(clientId, docTypeId, options, maxIndex);
	}*/
	
	/**
	 *  Post Immediate
	 *
	 *	@param	ctx Client Context
	 *  @param  AD_Client_ID    Client ID of Document
	 *  @param  AD_Table_ID     Table ID of Document
	 *  @param  Record_ID       Record ID of this document
	 *  @param  force           force posting
	 *  @param  trxName			ignore, retained for backward compatibility
	 *  @return null, if success or error message
	 */
	/*public static String postImmediate (Properties ctx, 
		int AD_Client_ID, int AD_Table_ID, int Record_ID, boolean force, String trxName)
	{
		// Ensure the table has Posted column / i.e. GL_JournalBatch can be completed but not posted
		if (MColumn.getColumn_ID(MTable.getTableName(ctx, AD_Table_ID), "Posted") <= 0)
			return null;
			
		String error = null;
		if (MClient.isClientAccounting()) {
			log.info ("Table=" + AD_Table_ID + ", Record=" + Record_ID);
			MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(ctx, AD_Client_ID);
			error = Doc.postImmediate(ass, AD_Table_ID, Record_ID, force, trxName);
			return error;
		}
		
		//  try to get from Server when enabled
		if (CConnection.get().isAppsServerOK(true))
		{
			log.config("trying server");
			try
			{
				Server server = CConnection.get().getServer();
				if (server != null)
				{
					Properties p = Env.getRemoteCallCtx(Env.getCtx());
					error = server.postImmediate(p, AD_Client_ID,
						AD_Table_ID, Record_ID, force, null); // don't pass transaction to server
					log.config("from Server: " + error== null ? "OK" : error);
				}
				else
				{
					error = "NoAppsServer";
				}
			}
			catch (Exception e)
			{
				log.log(Level.WARNING, "(RE)", e);
				error = e.getMessage();
			}
		}
		
		return error;
	}	//	postImmediate*/
	
}	//	DocumentEnine
