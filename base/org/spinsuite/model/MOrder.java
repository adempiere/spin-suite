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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.process.DocAction;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MOrder extends X_C_Order implements DocAction {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/10/2014, 21:31:20
	 * @param ctx
	 * @param C_Order_ID
	 * @param conn
	 */
	public MOrder(Context ctx, int C_Order_ID, DB conn) {
		super (ctx, C_Order_ID, conn);
	}	//	MOrder

	/**
	 * Load Default Values
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 14:38:06
	 * @return void
	 */
	public void loadDefault() {
		setDocStatus(DOCSTATUS_Drafted);
		setDocAction (DOCACTION_Prepare);
		//
		setDeliveryRule (DELIVERYRULE_Availability);
		setFreightCostRule (FREIGHTCOSTRULE_FreightIncluded);
		setInvoiceRule (INVOICERULE_Immediate);
		setPaymentRule(PAYMENTRULE_OnCredit);
		setPriorityRule (PRIORITYRULE_Medium);
		setDeliveryViaRule (DELIVERYVIARULE_Pickup);
		//
		setIsDiscountPrinted (false);
		setIsSelected (false);
		setIsTaxIncluded (false);
		setIsSOTrx (true);
		setIsDropShip(false);
		setSendEMail (false);
		//
		setIsApproved(false);
		setIsPrinted(false);
		setIsCreditApproved(false);
		setIsDelivered(false);
		setIsInvoiced(false);
		setIsTransferred(false);
		setIsSelfService(false);
		//
		super.setProcessed(false);
		setProcessing(false);
		setPosted(false);

		setDateAcct (new Date(System.currentTimeMillis()));
		setDatePromised (new Date(System.currentTimeMillis()));
		setDateOrdered (new Date(System.currentTimeMillis()));

		setFreightAmt (Env.ZERO);
		setChargeAmt (Env.ZERO);
		setTotalLines (Env.ZERO);
		setGrandTotal (Env.ZERO);
	}
	
	@Override
	protected boolean loadDefaultValues() {
		boolean ok = super.loadDefaultValues();
		//	
		loadDefault();
		return ok;
	}
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/10/2014, 21:31:20
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MOrder(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	
	/**	Order Lines					*/
	private MOrderLine[] 	m_lines 		= null;
	/**	Tax Lines					*/
	private MOrderTax[] 	m_taxes 		= null;
	/** Force Creation of order		*/
	//private boolean			m_forceCreation	= false;
	
	/**
	 * 	Overwrite Client/Org if required
	 * 	@param AD_Client_ID client
	 * 	@param AD_Org_ID org
	 */
	public void setClientOrg (int AD_Client_ID, int AD_Org_ID) {
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg


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
	 * 	Set Business Partner (Ship+Bill)
	 *	@param C_BPartner_ID bpartner
	 */
	public void setC_BPartner_ID (int C_BPartner_ID) {
		super.setC_BPartner_ID (C_BPartner_ID);
		super.setBill_BPartner_ID (C_BPartner_ID);
	}	//	setC_BPartner_ID
	
	/**
	 * 	Set Business Partner Location (Ship+Bill)
	 *	@param C_BPartner_Location_ID bp location
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID) {
		super.setC_BPartner_Location_ID (C_BPartner_Location_ID);
		super.setBill_Location_ID(C_BPartner_Location_ID);
	}	//	setC_BPartner_Location_ID

	/**
	 * 	Set Business Partner Contact (Ship+Bill)
	 *	@param AD_User_ID user
	 */
	public void setAD_User_ID (int AD_User_ID) {
		super.setAD_User_ID (AD_User_ID);
		super.setBill_User_ID (AD_User_ID);
	}	//	setAD_User_ID

	/**
	 * 	Set Ship Business Partner
	 *	@param C_BPartner_ID bpartner
	 */
	public void setShip_BPartner_ID (int C_BPartner_ID) {
		super.setC_BPartner_ID (C_BPartner_ID);
	}	//	setShip_BPartner_ID
	
	/**
	 * 	Set Ship Business Partner Location
	 *	@param C_BPartner_Location_ID bp location
	 */
	public void setShip_Location_ID (int C_BPartner_Location_ID) {
		super.setC_BPartner_Location_ID (C_BPartner_Location_ID);
	}	//	setShip_Location_ID

	/**
	 * 	Set Ship Business Partner Contact
	 *	@param AD_User_ID user
	 */
	public void setShip_User_ID (int AD_User_ID) {
		super.setAD_User_ID (AD_User_ID);
	}	//	setShip_User_ID
	
	
	/**
	 * 	Set Warehouse
	 *	@param M_Warehouse_ID warehouse
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID) {
		super.setM_Warehouse_ID (M_Warehouse_ID);
	}	//	setM_Warehouse_ID
	
	/**
	 * 	Set Drop Ship
	 *	@param IsDropShip drop ship
	 */
	public void setIsDropShip (boolean IsDropShip) {
		super.setIsDropShip (IsDropShip);
	}	//	setIsDropShip
	
	@Override
	public int getC_DocType_ID() {
		return super.getC_DocTypeTarget_ID();
	}

	
	/*************************************************************************/

	/** Sales Order Sub Type - SO	*/
	public static final String		DocSubTypeSO_Standard 	= "SO";
	/** Sales Order Sub Type - OB	*/
	public static final String		DocSubTypeSO_Quotation 	= "OB";
	/** Sales Order Sub Type - ON	*/
	public static final String		DocSubTypeSO_Proposal 	= "ON";
	/** Sales Order Sub Type - PR	*/
	public static final String		DocSubTypeSO_Prepay 	= "PR";
	/** Sales Order Sub Type - WR	*/
	public static final String		DocSubTypeSO_POS 		= "WR";
	/** Sales Order Sub Type - WP	*/
	public static final String		DocSubTypeSO_Warehouse 	= "WP";
	/** Sales Order Sub Type - WI	*/
	public static final String		DocSubTypeSO_OnCredit 	= "WI";
	/** Sales Order Sub Type - RM	*/
	public static final String		DocSubTypeSO_RMA 		= "RM";
	
	/**	Process Message				*/
	private String 					m_ProcessMsg 			= null;

	/**
	 * 	Set Business Partner Defaults & Details.
	 * 	SOTrx should be set.
	 * 	@param bp business partner
	 */
	public void setBPartner (MBPartner bp) {
		if (bp == null)
			return;

		setC_BPartner_ID(bp.getC_BPartner_ID());
		//	Defaults Payment Term
		int ii = 0;
		if (isSOTrx())
			ii = bp.getC_PaymentTerm_ID();
		else
			ii = bp.getPO_PaymentTerm_ID();
		if (ii != 0)
			setC_PaymentTerm_ID(ii);
		//	Default Price List
		if (isSOTrx())
			ii = bp.getM_PriceList_ID();
		else
			ii = bp.getPO_PriceList_ID();
		if (ii != 0)
			setM_PriceList_ID(ii);
		//	Default Delivery/Via Rule
		String ss = bp.getDeliveryRule();
		if (ss != null)
			setDeliveryRule(ss);
		ss = bp.getDeliveryViaRule();
		if (ss != null)
			setDeliveryViaRule(ss);
		//	Default Invoice/Payment Rule
		ss = bp.getInvoiceRule();
		if (ss != null)
			setInvoiceRule(ss);
		ss = bp.getPaymentRule();
		if (ss != null)
			setPaymentRule(ss);
		//	Sales Rep
		ii = bp.getSalesRep_ID();
		if (ii != 0)
			setSalesRep_ID(ii);


		//	Set Locations
		/*MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null)
		{
			for (int i = 0; i < locs.length; i++)
			{
				if (locs[i].isShipTo())
					super.setC_BPartner_Location_ID(locs[i].getC_BPartner_Location_ID());
				if (locs[i].isBillTo())
					setBill_Location_ID(locs[i].getC_BPartner_Location_ID());
			}
			//	set to first
			if (getC_BPartner_Location_ID() == 0 && locs.length > 0)
				super.setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
			if (getBill_Location_ID() == 0 && locs.length > 0)
				setBill_Location_ID(locs[0].getC_BPartner_Location_ID());
		}
		if (getC_BPartner_Location_ID() == 0)
		{	
			throw new Exception("BPartnerNoShipToAddressException");
		}	
			
		if (getBill_Location_ID() == 0)
		{
			throw new Exception("BPartnerNoShipToAddressException");
		}	

		//	Set Contact
		MUser[] contacts = bp.getContacts(false);
		if (contacts != null && contacts.length == 1)
			setAD_User_ID(contacts[0].getAD_User_ID());*/
	}	//	setBPartner
	
	/**
	 * 	Set Price List (and Currency, TaxIncluded) when valid
	 * 	@param M_PriceList_ID price list
	 */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		MPriceList pl = new MPriceList(getCtx(), M_PriceList_ID, get_Connection());
		if (pl.get_ID() == M_PriceList_ID)
		{
			super.setM_PriceList_ID(M_PriceList_ID);
			setC_Currency_ID(pl.getC_Currency_ID());
			setIsTaxIncluded(pl.isTaxIncluded());
		}
	}	//	setM_PriceList_ID
	
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
		int lines = DB.getSQLValue(getCtx(), "SELECT COUNT(C_OrderLine_ID) " +
				"FROM C_OrderLine " +
				"WHERE C_Order_ID = " + getC_Order_ID());
		if(lines == 0) {
			m_ProcessMsg = "@NoLines@";
			//	
			return STATUS_Invalid;
		}
		//	Calculate Tax
		if (!calculateTaxTotal()){
			m_ProcessMsg = "Error calculating tax";
			return DocAction.STATUS_Invalid;
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
			+ "' WHERE C_Order_ID=" + getC_Order_ID();
		int noLine = DB.executeUpdate(getCtx(), "UPDATE C_OrderLine " + set, null);
		int noTax = DB.executeUpdate(getCtx(), "UPDATE C_OrderTax " + set, null);
		m_lines = null;
		m_taxes = null;
		LogM.log(getCtx(), getClass(), Level.FINE, 
				"setProcessed - " + Processed + " - Lines=" + noLine + ", Tax=" + noTax);
		//	
		setC_DocType_ID(getC_DocTypeTarget_ID());
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
						.append(I_C_OrderLine.Table_Name)
						.append(" WHERE ").append(I_C_OrderLine.COLUMNNAME_C_Order_ID).append(" = ?");
			//	Update
			int deleted = DB.executeUpdate(getCtx(), sql.toString(), getC_Order_ID(), false, get_DB());
			//	Log
			LogM.log(getCtx(), getClass(), Level.FINE, "Deleted Lines = " + deleted);
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
	
	/**************************************************************************
	 * 	Get Lines of Order
	 * 	@param whereClause where clause or null (starting with AND)
	 * 	@param orderClause order clause
	 * 	@return lines
	 */
	public MOrderLine[] getLines (String whereClause, String orderClause) {
		//red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuffer whereClauseFinal = new StringBuffer(MOrderLine.COLUMNNAME_C_Order_ID+"=? ");
		if (whereClause != null
				&& whereClause.trim().length() > 0)
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MOrderLine.COLUMNNAME_Line;
		//
		List<MOrderLine> list = new Query(getCtx(), I_C_OrderLine.Table_Name, whereClauseFinal.toString(), get_Connection())
										.setParameters(get_ID())
										.setOrderBy(orderClause)
										.list();
		//
		return list.toArray(new MOrderLine[list.size()]);		
	}	//	getLines
	
	/**
	 * 	Get Lines of Order
	 * 	@param requery requery
	 * 	@param orderBy optional order by column
	 * 	@return lines
	 */
	public MOrderLine[] getLines (boolean requery, String orderBy) {
		if (m_lines != null && !requery) {
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Lines of Order.
	 * 	(used by web store)
	 * 	@return lines
	 */
	public MOrderLine[] getLines() {
		return getLines(false, null);
	}	//	getLines
	
	/**
	 * 	Get Taxes of Order
	 *	@param requery requery
	 *	@return array of taxes
	 */
	public MOrderTax[] getTaxes(boolean requery) {
		if (m_taxes != null && !requery)
			return m_taxes;
		//
		List<MOrderTax> list = new Query(getCtx(), I_C_OrderTax.Table_Name, "C_Order_ID=?", get_Connection())
									.setParameters(get_ID())
									.list();
		m_taxes = list.toArray(new MOrderTax[list.size()]);
		return m_taxes;
	}	//	getTaxes
	
	/**
	 * 	Calculate Tax and Total
	 * 	@return true if tax total calculated
	 */
	public boolean calculateTaxTotal() {
		//	Delete Taxes
		DB.executeUpdate(getCtx(), "DELETE C_OrderTax "
				+ "WHERE C_Order_ID=" + getC_Order_ID(), get_Connection());
		m_taxes = null;
		
		//	Lines
		BigDecimal totalLines = Env.ZERO;
		ArrayList<Integer> taxList = new ArrayList<Integer>();
		MOrderLine[] lines = getLines();
		for (int i = 0; i < lines.length; i++) {
			MOrderLine line = lines[i];
			int taxID = line.getC_Tax_ID();
			if (!taxList.contains(taxID)) {
				MOrderTax oTax = MOrderTax.get(getCtx(), line, getPrecision(), 
					false, get_Connection());	//	current Tax
				oTax.setIsTaxIncluded(isTaxIncluded());
				if (!oTax.calculateTaxFromLines())
					return false;
				if (!oTax.save()) {
					m_ProcessMsg = oTax.getError();
					return false;
				}
				taxList.add(taxID);
			}
			totalLines = totalLines.add(line.getLineNetAmt());
		}
		
		//	Taxes
		BigDecimal grandTotal = totalLines;
		MOrderTax[] taxes = getTaxes(true);
		for (int i = 0; i < taxes.length; i++) {
			MOrderTax oTax = taxes[i];
			MTax tax = oTax.getTax();
			if (tax.isSummary())
			{
				MTax[] cTaxes = tax.getChildTaxes(false);
				for (int j = 0; j < cTaxes.length; j++)
				{
					MTax cTax = cTaxes[j];
					BigDecimal taxAmt = cTax.calculateTax(oTax.getTaxBaseAmt(), isTaxIncluded(), getPrecision());
					//
					MOrderTax newOTax = new MOrderTax(getCtx(), 0, get_Connection());
					newOTax.setClientOrg(this);
					newOTax.setC_Order_ID(getC_Order_ID());
					newOTax.setC_Tax_ID(cTax.getC_Tax_ID());
					newOTax.setPrecision(getPrecision());
					newOTax.setIsTaxIncluded(isTaxIncluded());
					newOTax.setTaxBaseAmt(oTax.getTaxBaseAmt());
					newOTax.setTaxAmt(taxAmt);
					if (!newOTax.save())
						return false;
					//
					if (!isTaxIncluded())
						grandTotal = grandTotal.add(taxAmt);
				}
				if (!oTax.delete())
					return false;
				if (!oTax.save())
					return false;
			}
			else
			{
				if (!isTaxIncluded())
					grandTotal = grandTotal.add(oTax.getTaxAmt());
			}
		}		
		//
		setTotalLines(totalLines);
		setGrandTotal(grandTotal);
		return true;
	}	//	calculateTaxTotal
	
	/**
	 * 	Get Currency Precision
	 *	@return precision
	 */
	public int getPrecision() {
		return MCurrency.getStdPrecision(getCtx(), getC_Currency_ID());
	}	//	getPrecision
	
	@Override
	public String getError() {
		return m_ProcessMsg;
	}

}
