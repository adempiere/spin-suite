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
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.GridTab;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class CalloutOrder extends CalloutEngine {

	/**
	 *	Order Header Change - DocType.
	 *		- InvoiceRule/DeliveryRule/PaymentRule
	 *		- temporary Document
	 *  Context:
	 *  	- DocSubTypeSO
	 *		- HasCharges
	 *	- (re-sets Business Partner info of required)
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String docType (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_DocType_ID = (Integer)value;		//	Actually C_DocTypeTarget_ID
		if (C_DocType_ID == null || C_DocType_ID.intValue() == 0)
			return "";

		//	Re-Create new DocNo, if there is a doc number already
		//	and the existing source used a different Sequence number
		String oldDocNo = (String)mTab.getValue("DocumentNo");
		boolean newDocNo = false;
		if(oldDocNo == null) {
			oldDocNo = "";
			newDocNo = true;
		}
		if (!newDocNo && oldDocNo.startsWith("<") && oldDocNo.endsWith(">"))
			newDocNo = true;
		Integer oldC_DocType_ID = (Integer)mTab.getValue("C_DocType_ID");

		String sql = "SELECT d.DocSubTypeSO,d.HasCharges,'N',"			//	1..3
			+ "d.IsDocNoControlled,s.CurrentNext,s.CurrentNextSys,"     //  4..6
			+ "s.AD_Sequence_ID,d.IsSOTrx, "                             //	7..8
			+ "s.StartNewYear, s.DateColumn "							//  9..10
			+ "FROM C_DocType d "
			+ "LEFT JOIN AD_Sequence s ON(s.AD_Sequence_ID = d.DocNoSequence_ID) "
			+ "WHERE C_DocType_ID = ?";
		//	
		DB conn = new DB(ctx);
		//	
		try {
			int AD_Sequence_ID = 0;
			//	
			DB.loadConnection(conn, DB.READ_ONLY);
			//	
			Cursor rs = null;
			//	
			rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(C_DocType_ID.intValue())});
			//	Get old AD_SeqNo for comparison
			if (!newDocNo && oldC_DocType_ID.intValue() != 0) {
				rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(oldC_DocType_ID.intValue())});
				if (rs.moveToFirst())
					AD_Sequence_ID = rs.getInt(6);
			}
			//	
			String DocSubTypeSO = "";
			boolean IsSOTrx = true;
			if (rs.moveToFirst())		//	we found document type
			{
				//	Set Context:	Document Sub Type for Sales Orders
				DocSubTypeSO = rs.getString(rs.getColumnIndex("DocSubTypeSO"));
				if (DocSubTypeSO == null)
					DocSubTypeSO = "--";
				Env.setContext(WindowNo, "OrderType", DocSubTypeSO);
				//	No Drop Ship other than Standard
				if (!DocSubTypeSO.equals(MOrder.DocSubTypeSO_Standard))
					mTab.setValue ("IsDropShip", "N");
				
				//	Delivery Rule
				if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS))
					mTab.setValue ("DeliveryRule", X_C_Order.DELIVERYRULE_Force);
				else if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_Prepay))
					mTab.setValue ("DeliveryRule", X_C_Order.DELIVERYRULE_AfterReceipt);
				else
					mTab.setValue ("DeliveryRule", X_C_Order.DELIVERYRULE_Availability);
				
				//	Invoice Rule
				if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS)
					|| DocSubTypeSO.equals(MOrder.DocSubTypeSO_Prepay)
					|| DocSubTypeSO.equals(MOrder.DocSubTypeSO_OnCredit) )
					mTab.setValue ("InvoiceRule", X_C_Order.INVOICERULE_Immediate);
				else
					mTab.setValue ("InvoiceRule", X_C_Order.INVOICERULE_AfterDelivery);
				
				//	Payment Rule - POS Order
				if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS))
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_Cash);
				else
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_OnCredit);

				//	IsSOTrx
				if ("N".equals(rs.getString(rs.getColumnIndex("IsSOTrx"))))
					IsSOTrx = false;

				//	Set Context:
				Env.setContext(WindowNo, "HasCharges", rs.getString(rs.getColumnIndex("HasCharges")));

				//	DocumentNo
				if (rs.getString(rs.getColumnIndex("IsDocNoControlled")).equals("Y"))			//	IsDocNoControlled
				{
					if (!newDocNo && AD_Sequence_ID != rs.getInt(rs.getColumnIndex("AD_Sequence_ID")))
						newDocNo = true;
					if (newDocNo) {
						if ("Y".equals(rs.getString(rs.getColumnIndex("StartNewYear")))) {
							String dateColumn = rs.getString(rs.getColumnIndex("DateColumn"));
							mTab.setValue("DocumentNo", 
									"<" 
										+ MSequence.getPreliminaryNoByYear(mTab, rs.getInt(rs.getColumnIndex("AD_Sequence_ID")), dateColumn, conn) 
									+ ">");
						} else {
							mTab.setValue("DocumentNo", "<" + rs.getString(rs.getColumnIndex("CurrentNext")) + ">");
						}
					}
				}
			}
			//  When BPartner is changed, the Rules are not set if
			//  it is a POS or Credit Order (i.e. defaults from Standard BPartner)
			//  This re-reads the Rules and applies them.
			if (DocSubTypeSO.equals(MOrder.DocSubTypeSO_POS) 
				|| DocSubTypeSO.equals(MOrder.DocSubTypeSO_Prepay))    //  not for POS/PrePay
				;
			else
			{
				sql = "SELECT PaymentRule,C_PaymentTerm_ID,"            //  1..2
					+ "InvoiceRule,DeliveryRule,"                       //  3..4
					+ "FreightCostRule,DeliveryViaRule, "               //  5..6
					+ "PaymentRulePO,PO_PaymentTerm_ID "
					+ "FROM C_BPartner "
					+ "WHERE C_BPartner_ID = ?";		//	#1
				int C_BPartner_ID = Env.getContextAsInt(WindowNo, "C_BPartner_ID");
				//	
				rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(C_BPartner_ID)});
				if (rs.moveToFirst()) {
					//	PaymentRule
					String s = rs.getString(rs.getColumnIndex(IsSOTrx ? "PaymentRule" : "PaymentRulePO"));
					if (s != null && s.length() != 0)
					{
						if (IsSOTrx && (s.equals("B") || s.equals("S") || s.equals("U")))	//	No Cash/Check/Transfer for SO_Trx
							s = "P";										//  Payment Term
						if (!IsSOTrx && (s.equals("B")))					//	No Cash for PO_Trx
							s = "P";										//  Payment Term
						mTab.setValue("PaymentRule", s);
					}
					//	Payment Term
					Integer ii = Integer.valueOf(rs.getInt(rs.getColumnIndex(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID")));
					if (s != null)
						mTab.setValue("C_PaymentTerm_ID", ii);
					//	InvoiceRule
					s = rs.getString(rs.getColumnIndex("InvoiceRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("InvoiceRule", s);
					//	DeliveryRule
					s = rs.getString(rs.getColumnIndex("DeliveryRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryRule", s);
					//	FreightCostRule
					s = rs.getString(rs.getColumnIndex("FreightCostRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("FreightCostRule", s);
					//	DeliveryViaRule
					s = rs.getString(rs.getColumnIndex("DeliveryViaRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryViaRule", s);
				}
			}
			//  re-read customer rules
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		} finally {
			DB.closeConnection(conn);
		}
		return "";
	}	//	docType
	
	/**
	 *	Order Header - BPartner.
	 *		- M_PriceList_ID (+ Context)
	 *		- C_BPartner_Location_ID
	 *		- Bill_BPartner_ID/Bill_Location_ID
	 *		- AD_User_ID
	 *		- POReference
	 *		- SO_Description
	 *		- IsDiscountPrinted
	 *		- InvoiceRule/DeliveryRule/PaymentRule/FreightCost/DeliveryViaRule
	 *		- C_PaymentTerm_ID
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String bPartner (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		Integer C_BPartner_ID = (Integer)value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
			return "";
		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
			+ " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
			+ " p.SO_Description,p.IsDiscountPrinted,"
			+ " p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
			+ " p.SO_CreditLimit, p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
			+ " lship.C_BPartner_Location_ID,c.AD_User_ID,"
			+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID," 
			+ " lbill.C_BPartner_Location_ID AS Bill_Location_ID, p.SOCreditStatus, "
			+ " p.SalesRep_ID "
			+ "FROM C_BPartner p"
			+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID)"			
			+ " LEFT OUTER JOIN C_BPartner_Location lbill ON (p.C_BPartner_ID=lbill.C_BPartner_ID AND lbill.IsBillTo='Y' AND lbill.IsActive='Y')"
			+ " LEFT OUTER JOIN C_BPartner_Location lship ON (p.C_BPartner_ID=lship.C_BPartner_ID AND lship.IsShipTo='Y' AND lship.IsActive='Y')"
			+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
			+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		//	#1

		boolean IsSOTrx = "Y".equals(Env.getContext(WindowNo, "IsSOTrx"));
		Cursor rs = null;
		DB conn = new DB(ctx);
		try {
			//	Load Connection
			DB.loadConnection(conn, DB.READ_ONLY);
			//	
			rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(C_BPartner_ID)});
			if (rs.moveToFirst())
			{
				// Sales Rep - If BP has a default SalesRep then default it
				Integer salesRep = rs.getInt(rs.getColumnIndex("SalesRep_ID"));
				if (IsSOTrx && salesRep != 0 )
				{
					mTab.setValue("SalesRep_ID", salesRep);
				}
				
				//	PriceList (indirect: IsTaxIncluded & Currency)
				int ii = rs.getInt(rs.getColumnIndex((IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID")));
				if (ii > 0)
					mTab.setValue("M_PriceList_ID", ii);
				else
				{	//	get default PriceList
					int i = Env.getContextAsInt("#M_PriceList_ID");
					if (i != 0)
						mTab.setValue("M_PriceList_ID", i);
				}

				//	Bill-To
				mTab.setValue("Bill_BPartner_ID", C_BPartner_ID);
				int bill_Location_ID = rs.getInt(rs.getColumnIndex("Bill_Location_ID"));
				if (bill_Location_ID == 0)
					mTab.setValue("Bill_Location_ID", null);
				else
					mTab.setValue("Bill_Location_ID", bill_Location_ID);
				// Ship-To Location
				int shipTo_ID = rs.getInt(rs.getColumnIndex("C_BPartner_Location_ID"));
				//	overwritten by InfoBP selection - works only if InfoWindow
				//	was used otherwise creates error (uses last value, may belong to different BP)
				if (C_BPartner_ID.toString().equals(Env.getContext(WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
						shipTo_ID = Integer.parseInt(loc);
				}
				if (shipTo_ID == 0)
					mTab.setValue("C_BPartner_Location_ID", null);
				else
					mTab.setValue("C_BPartner_Location_ID", shipTo_ID);

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt(rs.getColumnIndex("AD_User_ID"));
				if (C_BPartner_ID.toString().equals(Env.getContext(WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String cont = Env.getContext(WindowNo, Env.TAB_INFO, "AD_User_ID");
					if (cont.length() > 0)
						contID = Integer.parseInt(cont);
				}
				if (contID == 0)
					mTab.setValue("AD_User_ID", null);
				else
				{
					mTab.setValue("AD_User_ID", contID);
					mTab.setValue("Bill_User_ID", contID);
				}

				//	CreditAvailable 
				//if (IsSOTrx)
				//{
					//double CreditLimit = rs.getDouble(rs.getColumnIndex("SO_CreditLimit"));
					//String SOCreditStatus = rs.getString(rs.getColumnIndex("SOCreditStatus"));
					//if (CreditLimit != 0)
					//{
						//double CreditAvailable = rs.getDouble(rs.getColumnIndex("CreditAvailable"));
						//if (CreditAvailable < 0)
							//mTab.fireDataStatusEEvent("CreditLimitOver",
								//DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
								//false);
					//}
				//}

				//	PO Reference
				String s = rs.getString(rs.getColumnIndex("POReference"));
				if (s != null && s.length() != 0)
					mTab.setValue("POReference", s);
				// should not be reset to null if we entered already value! VHARCQ, accepted YS makes sense that way
				// TODO: should get checked and removed if no longer needed!
				/*else
					mTab.setValue("POReference", null);*/ 
				
				//	SO Description
				s = rs.getString(rs.getColumnIndex("SO_Description"));
				if (s != null && s.trim().length() != 0)
					mTab.setValue("Description", s);
				//	IsDiscountPrinted
				s = rs.getString(rs.getColumnIndex("IsDiscountPrinted"));
				if (s != null && s.length() != 0)
					mTab.setValue("IsDiscountPrinted", s);
				else
					mTab.setValue("IsDiscountPrinted", "N");

				//	Defaults, if not Walkin Receipt or Walkin Invoice
				String OrderType = Env.getContext(WindowNo, "OrderType");
				if(OrderType == null)
					OrderType = MOrder.DocSubTypeSO_Standard;
				//	
				mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_AfterDelivery);
				mTab.setValue("DeliveryRule", X_C_Order.DELIVERYRULE_Availability);
				mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_OnCredit);
				if (OrderType.equals(MOrder.DocSubTypeSO_Prepay))
				{
					mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_Immediate);
					mTab.setValue("DeliveryRule", X_C_Order.DELIVERYRULE_AfterReceipt);
				}
				else if (OrderType.equals(MOrder.DocSubTypeSO_POS))	//  for POS
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_Cash);
				else
				{
					//	PaymentRule
					s = rs.getString(rs.getColumnIndex(IsSOTrx ? "PaymentRule" : "PaymentRulePO"));
					if (s != null && s.length() != 0)
					{
						if (s.equals("B"))				//	No Cache in Non POS
							s = "P";
						if (IsSOTrx && (s.equals("S") || s.equals("U")))	//	No Check/Transfer for SO_Trx
							s = "P";										//  Payment Term
						mTab.setValue("PaymentRule", s);
					}
					//	Payment Term
					ii = rs.getInt(rs.getColumnIndex(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
					if (ii > 0)
						mTab.setValue("C_PaymentTerm_ID", ii);
					//	InvoiceRule
					s = rs.getString(rs.getColumnIndex("InvoiceRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("InvoiceRule", s);
					//	DeliveryRule
					s = rs.getString(rs.getColumnIndex("DeliveryRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryRule", s);
					//	FreightCostRule
					s = rs.getString(rs.getColumnIndex("FreightCostRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("FreightCostRule", s);
					//	DeliveryViaRule
					s = rs.getString(rs.getColumnIndex("DeliveryViaRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("DeliveryViaRule", s);
				}
			}
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, "bPartner (" + sql + ")", e);
			return e.getLocalizedMessage();
		} finally {
			DB.closeConnection(conn);
		}
		return "";
	}	//	bPartner
	
	/**
	 *	Order Header - Invoice BPartner.
	 *		- M_PriceList_ID (+ Context)
	 *		- Bill_Location_ID
	 *		- Bill_User_ID
	 *		- POReference
	 *		- SO_Description
	 *		- IsDiscountPrinted
	 *		- InvoiceRule/PaymentRule
	 *		- C_PaymentTerm_ID
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String bPartnerBill (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		if (isCalloutActive())
			return "";
		Integer bill_BPartner_ID = (Integer)value;
		if (bill_BPartner_ID == null || bill_BPartner_ID.intValue() == 0)
			return "";

		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
			+ "p.M_PriceList_ID,p.PaymentRule,p.POReference,"
			+ "p.SO_Description,p.IsDiscountPrinted,"
			+ "p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
			+ "p.SO_CreditLimit, p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
			+ "c.AD_User_ID,"
			+ "p.PO_PriceList_ID, p.PaymentRulePO, p.PO_PaymentTerm_ID,"
			+ "lbill.C_BPartner_Location_ID AS Bill_Location_ID "
			+ "FROM C_BPartner p"
			+ " LEFT JOIN C_BPartner_Location lbill ON (p.C_BPartner_ID=lbill.C_BPartner_ID AND lbill.IsBillTo='Y' AND lbill.IsActive='Y')"
			+ " LEFT JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
			+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		//	#1

		boolean IsSOTrx = "Y".equals(Env.getContext(WindowNo, "IsSOTrx"));
		Cursor rs = null;
		DB conn = new DB(ctx);
		try {
			//	Load Connection
			DB.loadConnection(conn, DB.READ_ONLY);
			//	
			rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(bill_BPartner_ID)});
			if (rs.moveToFirst()) {
				//	PriceList (indirect: IsTaxIncluded & Currency)
				int ii = rs.getInt(rs.getColumnIndex(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID"));
				if (ii > 0)
					mTab.setValue("M_PriceList_ID", ii);
				else
				{	//	get default PriceList
					int i = Env.getContextAsInt("#M_PriceList_ID");
					if (i != 0)
						mTab.setValue("M_PriceList_ID", i);
				}

				int bill_Location_ID = rs.getInt(rs.getColumnIndex("Bill_Location_ID"));
				//	overwritten by InfoBP selection - works only if InfoWindow
				//	was used otherwise creates error (uses last value, may belong to different BP)
				if (bill_BPartner_ID.toString().equals(Env.getContext(WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
						bill_Location_ID = Integer.parseInt(loc);
				}
				if (bill_Location_ID == 0)
					mTab.setValue("Bill_Location_ID", null);
				else
					mTab.setValue("Bill_Location_ID", bill_Location_ID);

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt(rs.getColumnIndex("AD_User_ID"));
				if (bill_BPartner_ID.toString().equals(Env.getContext(WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String cont = Env.getContext(WindowNo, Env.TAB_INFO, "AD_User_ID");
					if (cont.length() > 0)
						contID = Integer.parseInt(cont);
				}
				if (contID == 0)
					mTab.setValue("Bill_User_ID", null);
				else
					mTab.setValue("Bill_User_ID", contID);

				//	CreditAvailable 
				//if (IsSOTrx)
				//{
					//double CreditLimit = rs.getDouble(rs.getColumnIndex("SO_CreditLimit"));
					//if (CreditLimit != 0)
					//{
						//double CreditAvailable = rs.getDouble(rs.getColumnIndex("CreditAvailable"));
						//if (!rs.wasNull() && CreditAvailable < 0)
							//mTab.fireDataStatusEEvent("CreditLimitOver",
								//DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
								//false);
					//}
				//}

				//	PO Reference
				String s = rs.getString(rs.getColumnIndex("POReference"));
				if (s != null && s.length() != 0)
					mTab.setValue("POReference", s);
				else
					mTab.setValue("POReference", null);
				//	SO Description
				s = rs.getString(rs.getColumnIndex("SO_Description"));
				if (s != null && s.trim().length() != 0)
					mTab.setValue("Description", s);
				//	IsDiscountPrinted
				s = rs.getString(rs.getColumnIndex("IsDiscountPrinted"));
				if (s != null && s.length() != 0)
					mTab.setValue("IsDiscountPrinted", s);
				else
					mTab.setValue("IsDiscountPrinted", "N");

				//	Defaults, if not Walkin Receipt or Walkin Invoice
				String OrderType = Env.getContext(WindowNo, "OrderType");
				mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_AfterDelivery);
				mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_OnCredit);
				if (OrderType.equals(MOrder.DocSubTypeSO_Prepay))
					mTab.setValue("InvoiceRule", X_C_Order.INVOICERULE_Immediate);
				else if (OrderType.equals(MOrder.DocSubTypeSO_POS))	//  for POS
					mTab.setValue("PaymentRule", X_C_Order.PAYMENTRULE_Cash);
				else
				{
					//	PaymentRule
					s = rs.getString(rs.getColumnIndex(IsSOTrx ? "PaymentRule" : "PaymentRulePO"));
					if (s != null && s.length() != 0)
					{
						if (s.equals("B"))				//	No Cache in Non POS
							s = "P";
						if (IsSOTrx && (s.equals("S") || s.equals("U")))	//	No Check/Transfer for SO_Trx
							s = "P";										//  Payment Term
						mTab.setValue("PaymentRule", s);
					}
					//	Payment Term
					ii = rs.getInt(rs.getColumnIndex(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
					if (ii > 0)
						mTab.setValue("C_PaymentTerm_ID", ii);
					//	InvoiceRule
					s = rs.getString(rs.getColumnIndex("InvoiceRule"));
					if (s != null && s.length() != 0)
						mTab.setValue("InvoiceRule", s);
				}
			}
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, "bPartnerBill (" + sql + ")", e);
			return e.getLocalizedMessage();
		} finally {
			DB.closeConnection(conn);
		}
		return "";
	}	//	bPartnerBill

	/**
	 *	Order Header - PriceList.
	 *	(used also in Invoice)
	 *		- C_Currency_ID
	 *		- IsTaxIncluded
	 *	Window Context:
	 *		- EnforcePriceLimit
	 *		- StdPrecision
	 *		- M_PriceList_Version_ID
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String priceList (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		Integer M_PriceList_ID = (Integer) mTab.getValue("M_PriceList_ID");
		if (M_PriceList_ID == null || M_PriceList_ID.intValue()== 0)
			return "";
		//	SQL
		String sql = "SELECT pl.IsTaxIncluded,pl.EnforcePriceLimit,pl.C_Currency_ID,c.StdPrecision,"
			+ "plv.M_PriceList_Version_ID,plv.ValidFrom "
			+ "FROM M_PriceList pl "
			+ "INNER JOIN C_Currency c ON(c.C_Currency_ID = pl.C_Currency_ID) "
			+ "INNER JOIN M_PriceList_Version plv ON(plv.M_PriceList_ID = pl.M_PriceList_ID) "
			+ "WHERE pl.M_PriceList_ID = ? "						//	1
			+ " AND plv.ValidFrom <= ? "
			+ "ORDER BY plv.ValidFrom DESC";
		//	Use newest price list - may not be future
		Cursor rs = null;
		DB conn = new DB(ctx);
		try {
			//	Load Connection
			DB.loadConnection(conn, DB.READ_ONLY);
			//	
			Date date = (Date) mTab.getValue("DateOrdered");
			if(date == null)
				date = new Date(System.currentTimeMillis());
			//	
			conn.compileQuery(sql);
			conn.addInt(M_PriceList_ID).addDate(date);
			//else if (mTab.getSPS_Table_ID() == I_C_Invoice.Table_ID)
				//date = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
			rs = conn.querySQL();
			//	
			if (rs.moveToFirst()) {
				//	2015-06-12 Dixon Martinez
				//	Change Index values
				//	Tax Included
<<<<<<< HEAD
				mTab.setValue("IsTaxIncluded", "Y".equals(rs.getString(0)));//	1
=======
				//2015-05-11 Dixon Martinez Change Position of Columns 
				mTab.setValue("IsTaxIncluded", "Y".equals(rs.getString(0))); //1
>>>>>>> branch 'SPIN-1-Synchronization-Server' of ssh://git@github.com/adempiere/Spin-Suite.git
				//	Price Limit Enforce
<<<<<<< HEAD
				Env.setContext(WindowNo, "EnforcePriceLimit", rs.getString(1));//	2
=======
				Env.setContext(WindowNo, "EnforcePriceLimit", rs.getString(1)); //2
>>>>>>> branch 'SPIN-1-Synchronization-Server' of ssh://git@github.com/adempiere/Spin-Suite.git
				//	Currency
<<<<<<< HEAD
				int ii = rs.getInt(2);//	3
=======
				int ii = rs.getInt(2); //3
>>>>>>> branch 'SPIN-1-Synchronization-Server' of ssh://git@github.com/adempiere/Spin-Suite.git
				mTab.setValue("C_Currency_ID", ii);
				//	PriceList Version
<<<<<<< HEAD
				Env.setContext(WindowNo, "M_PriceList_Version_ID", rs.getInt(4));//	5
				//	End Dixon Martinez
=======
				Env.setContext(WindowNo, "M_PriceList_Version_ID", rs.getInt(4)); //5
				//End Dixon Martinez
>>>>>>> branch 'SPIN-1-Synchronization-Server' of ssh://git@github.com/adempiere/Spin-Suite.git
			}
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, "priceList (" + sql + ")", e);
			return e.getLocalizedMessage();
		} finally {
			DB.closeConnection(conn);
		}
		//	
		return "";
	}	//	priceList
	
	/*************************************************************************
	 *	Order Line - Product.
	 *		- reset C_Charge_ID / M_AttributeSetInstance_ID
	 *		- PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit
	 *		- UOM
	 *	Calls Tax
	 *
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String product (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";
		//
		mTab.setValue("C_Charge_ID", null);
		//	Set Attribute
		if (Env.getContextAsInt(WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
			&& Env.getContextAsInt(WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
			mTab.setValue("M_AttributeSetInstance_ID", Env.getContextAsInt(WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
		else
			mTab.setValue("M_AttributeSetInstance_ID", null);
			
		/*****	Price Calculation see also qty	****/
		int C_BPartner_ID = Env.getContextAsInt(WindowNo, "C_BPartner_ID");
		BigDecimal Qty = (BigDecimal)mTab.getValue("QtyOrdered");
		boolean IsSOTrx = Env.getContext(WindowNo, "IsSOTrx").equals("Y");
		MProductPricing pp = new MProductPricing(ctx, M_Product_ID.intValue(), C_BPartner_ID, Qty, IsSOTrx);
		//
		int M_PriceList_ID = Env.getContextAsInt(WindowNo, "M_PriceList_ID");
		pp.setM_PriceList_ID(M_PriceList_ID);
		Date orderDate = (Date)mTab.getValue("DateOrdered");
		/** PLV is only accurate if PL selected in header */
		int M_PriceList_Version_ID = Env.getContextAsInt(WindowNo, "M_PriceList_Version_ID");
		if ( M_PriceList_Version_ID == 0 && M_PriceList_ID > 0)
		{
			String sql = "SELECT plv.M_PriceList_Version_ID "
				+ "FROM M_PriceList_Version plv "
				+ "WHERE plv.M_PriceList_ID=? "						//	1
				+ " AND plv.ValidFrom <= ? "
				+ "ORDER BY plv.ValidFrom DESC";
			//	Use newest price list - may not be future
			
			M_PriceList_Version_ID = DB.getSQLValueEx(ctx, sql, 
					String.valueOf(M_PriceList_ID), String.valueOf(orderDate));
			if ( M_PriceList_Version_ID > 0 )
				Env.setContext(WindowNo, "M_PriceList_Version_ID", M_PriceList_Version_ID );
		}
		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID); 
		pp.setPriceDate(orderDate);
		//		
		mTab.setValue("PriceList", pp.getPriceList());
		mTab.setValue("PriceLimit", pp.getPriceLimit());
		mTab.setValue("PriceActual", pp.getPriceStd());
		mTab.setValue("PriceEntered", pp.getPriceStd());
		mTab.setValue("C_Currency_ID", pp.getC_Currency_ID());
		mTab.setValue("Discount", pp.getDiscount());
		mTab.setValue("C_UOM_ID", pp.getC_UOM_ID());
		mTab.setValue("QtyOrdered", mTab.getValue("QtyEntered"));
		Env.setContext(WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");
		Env.setContext(WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		
		//	Check/Update Warehouse Setting
		//	int M_Warehouse_ID = Env.getContextAsInt(WindowNo, "M_Warehouse_ID");
		//	Integer wh = (Integer)mTab.getValue("M_Warehouse_ID");
		//	if (wh.intValue() != M_Warehouse_ID)
		//	{
		//		mTab.setValue("M_Warehouse_ID", new Integer(M_Warehouse_ID));
		//		ADialog.warn(,WindowNo, "WarehouseChanged");
		//	}

		//	Not yet implemented
		//if (Env.isSOTrx(ctx, WindowNo))
		//{
			//MProduct product = new MProduct(ctx, M_Product_ID.intValue(), null);
			//if (product.isStocked())
			//{
				//BigDecimal QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
				//int M_Warehouse_ID = Env.getContextAsInt(WindowNo, "M_Warehouse_ID");
				//int M_AttributeSetInstance_ID = Env.getContextAsInt(WindowNo, "M_AttributeSetInstance_ID");
				//BigDecimal available = Env.ZERO;//MStorage.getQtyAvailable
					//(M_Warehouse_ID, M_Product_ID.intValue(), M_AttributeSetInstance_ID, null);
				//if (available == null)
					//available = Env.ZERO;
				//if (available.signum() == 0)
					//Msg.alertMsg(ctx, "@NoQtyAvailable@ (0)");
				//else if (available.compareTo(QtyOrdered) < 0)
					//Msg.alertMsg(ctx, "@InsufficientQtyAvailable@ (" + available.toString() + ")");
				//else
				//{
					//Integer C_OrderLine_ID = (Integer)mTab.getValue("C_OrderLine_ID");
					//if (C_OrderLine_ID == null)
						//C_OrderLine_ID = 0;
					//BigDecimal notReserved = MOrderLine.getNotReserved(ctx, 
						//M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
						//C_OrderLine_ID.intValue());
					//if (notReserved == null)
						//notReserved = Env.ZERO;
					//BigDecimal total = available.subtract(notReserved);
					//if (total.compareTo(QtyOrdered) < 0)
					//{
						//String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available 
							//+ " - @QtyNotReserved@=" + notReserved + " = " + total);
						//Msg.alertMsg(ctx, "@InsufficientQtyAvailable@ [" + info + "]");
						//LogM.log(ctx, getClass(), Level.SEVERE, info);
					//}
				//}
			//}
		//}
		//
		return tax (ctx, WindowNo, mTab, mField, value);
	}	//	product
	
	/**
	 *	Order Line - Tax.
	 *		- basis: Product, Charge, BPartner Location
	 *		- sets C_Tax_ID
	 *  Calls Amount
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String tax (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String column = mField.getColumnName();
		if (value == null)
			return "";
		//	Check Product
		int M_Product_ID = 0;
		if (column.equals("M_Product_ID"))
			M_Product_ID = ((Integer)value).intValue();
		else
			M_Product_ID = Env.getContextAsInt(WindowNo, "M_Product_ID");
		int C_Charge_ID = 0;
		if (column.equals("C_Charge_ID"))
			C_Charge_ID = ((Integer)value).intValue();
		else
			C_Charge_ID = Env.getContextAsInt(WindowNo, "C_Charge_ID");
		LogM.log(ctx, getClass(), Level.FINE, "Product=" + M_Product_ID + ", C_Charge_ID=" + C_Charge_ID);
		if (M_Product_ID == 0 && C_Charge_ID == 0)
			return amt(ctx, WindowNo, mTab, mField, value);		//

		//	Check Partner Location
		int shipC_BPartner_Location_ID = 0;
		if (column.equals("C_BPartner_Location_ID"))
			shipC_BPartner_Location_ID = ((Integer)value).intValue();
		else
			shipC_BPartner_Location_ID = Env.getContextAsInt(WindowNo, "C_BPartner_Location_ID");
		if (shipC_BPartner_Location_ID == 0)
			return amt(ctx, WindowNo, mTab, mField, value);		//
		LogM.log(ctx, getClass(), Level.FINE, "Ship BP_Location=" + shipC_BPartner_Location_ID);

		//
		Date billDate = (Date) DisplayType.parseValue(mTab.get_ValueAsString("DateOrdered"), DisplayType.DATE);
		LogM.log(ctx, getClass(), Level.FINE, "Bill Date=" + billDate);

		Date shipDate = (Date) DisplayType.parseValue(mTab.get_ValueAsString("DatePromised"), DisplayType.DATE);
		LogM.log(ctx, getClass(), Level.FINE, "Ship Date=" + shipDate);

		int AD_Org_ID = Env.getContextAsInt(WindowNo, "AD_Org_ID");
		LogM.log(ctx, getClass(), Level.FINE, "Org=" + AD_Org_ID);

		int M_Warehouse_ID = Env.getContextAsInt(WindowNo, "M_Warehouse_ID");
		LogM.log(ctx, getClass(), Level.FINE, "Warehouse=" + M_Warehouse_ID);

		int billC_BPartner_Location_ID = Env.getContextAsInt(WindowNo, "Bill_Location_ID");
		if (billC_BPartner_Location_ID == 0)
			billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
		LogM.log(ctx, getClass(), Level.FINE, "Bill BP_Location=" + billC_BPartner_Location_ID);

		//
		int C_Tax_ID = Tax.get(ctx, M_Product_ID, C_Charge_ID, billDate, shipDate,
			AD_Org_ID, M_Warehouse_ID, billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
			"Y".equals(Env.getContext(WindowNo, "IsSOTrx")));
		//	
		LogM.log(ctx, getClass(), Level.FINE, "Tax ID=" + C_Tax_ID);
		//
		if (C_Tax_ID == 0)
			Msg.alertMsg(ctx, "@C_Tax_ID@ @NotFound@");
		else
			mTab.setValue("C_Tax_ID", C_Tax_ID);
		//
		return amt(ctx, WindowNo, mTab, mField, value);
	}	//	tax
	
	/**
	 *	Order Line - Amount.
	 *		- called from QtyOrdered, Discount and PriceActual
	 *		- calculates Discount or Actual Amount
	 *		- calculates LineNetAmt
	 *		- enforces PriceLimit
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String amt (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		int C_UOM_To_ID = Env.getContextAsInt(WindowNo, "C_UOM_ID");
		int M_Product_ID = Env.getContextAsInt(WindowNo, "M_Product_ID");
		int M_PriceList_ID = Env.getContextAsInt(WindowNo, "M_PriceList_ID");
		int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
		BigDecimal QtyEntered, QtyOrdered, PriceEntered, PriceActual, PriceLimit, Discount, PriceList;
		//	get values
		QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
		QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
		LogM.log(ctx, getClass(), Level.FINE, "QtyEntered=" + QtyEntered + ", Ordered=" + QtyOrdered + ", UOM=" + C_UOM_To_ID);
		//
		PriceEntered = (BigDecimal)mTab.getValue("PriceEntered");
		PriceActual = (BigDecimal)mTab.getValue("PriceActual");
		Discount = (BigDecimal)mTab.getValue("Discount");
		PriceLimit = (BigDecimal)mTab.getValue("PriceLimit");
		PriceList = (BigDecimal)mTab.getValue("PriceList");
		LogM.log(ctx, getClass(), Level.FINE, "PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
		LogM.log(ctx, getClass(), Level.FINE, "PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

		//		No Product
		if (M_Product_ID == 0)
		{
			// if price change sync price actual and entered
			// else ignore
			if (mField.getColumnName().equals("PriceActual"))
			{
				PriceEntered = (BigDecimal) value;
				mTab.setValue("PriceEntered", value);
			}
			else if (mField.getColumnName().equals("PriceEntered"))
			{
				PriceActual = (BigDecimal) value;
				mTab.setValue("PriceActual", value);
			}
		}
		//	Product Qty changed - recalc price
		else if ((mField.getColumnName().equals("QtyOrdered") 
			|| mField.getColumnName().equals("QtyEntered")
			|| mField.getColumnName().equals("C_UOM_ID")
			|| mField.getColumnName().equals("M_Product_ID")) 
			&& !"N".equals(Env.getContext(WindowNo, "DiscountSchema")))
		{
			int C_BPartner_ID = Env.getContextAsInt(WindowNo, "C_BPartner_ID");
			if (mField.getColumnName().equals("QtyEntered"))
				QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean IsSOTrx = Env.getContext(WindowNo, "IsSOTrx").equals("Y");
			MProductPricing pp = new MProductPricing(ctx, M_Product_ID, C_BPartner_ID, QtyOrdered, IsSOTrx);
			pp.setM_PriceList_ID(M_PriceList_ID);
			int M_PriceList_Version_ID = Env.getContextAsInt(WindowNo, "M_PriceList_Version_ID");
			pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
			Date date = (Date)mTab.getValue("DateOrdered");
			pp.setPriceDate(date);
			//
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, pp.getPriceStd());
			if (PriceEntered == null)
				PriceEntered = pp.getPriceStd();
			//
			LogM.log(ctx, getClass(), Level.FINE, "QtyChanged -> PriceActual=" + pp.getPriceStd() 
				+ ", PriceEntered=" + PriceEntered + ", Discount=" + pp.getDiscount());
			PriceActual = pp.getPriceStd();
			mTab.setValue("PriceActual", pp.getPriceStd());
			mTab.setValue("Discount", pp.getDiscount());
			mTab.setValue("PriceEntered", PriceEntered);
			Env.setContext(WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		}
		else if (mField.getColumnName().equals("PriceActual"))
		{
			PriceActual = (BigDecimal)value;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			//
			LogM.log(ctx, getClass(), Level.FINE, "PriceActual=" + PriceActual 
				+ " -> PriceEntered=" + PriceEntered);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		else if (mField.getColumnName().equals("PriceEntered"))
		{
			PriceEntered = (BigDecimal)value;
			PriceActual = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceEntered);
			if (PriceActual == null)
				PriceActual = PriceEntered;
			//
			LogM.log(ctx, getClass(), Level.FINE, "PriceEntered=" + PriceEntered 
				+ " -> PriceActual=" + PriceActual);
			mTab.setValue("PriceActual", PriceActual);
		}
		
		//  Discount entered - Calculate Actual/Entered
		if (mField.getColumnName().equals("Discount"))
		{
			if ( PriceList.doubleValue() != 0 )
				PriceActual = new BigDecimal ((100.0 - Discount.doubleValue()) / 100.0 * PriceList.doubleValue());
			if (PriceActual.scale() > StdPrecision)
				PriceActual = PriceActual.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			mTab.setValue("PriceActual", PriceActual);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		//	calculate Discount
		else
		{
			if (PriceList.intValue() == 0)
				Discount = Env.ZERO;
			else
				Discount = new BigDecimal ((PriceList.doubleValue() - PriceActual.doubleValue()) / PriceList.doubleValue() * 100.0);
			if (Discount.scale() > 2)
				Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
			mTab.setValue("Discount", Discount);
		}
		LogM.log(ctx, getClass(), Level.FINE, "PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

		//	Check PriceLimit
		String epl = Env.getContext(WindowNo, "EnforcePriceLimit");
		boolean enforce = Env.isSOTrx(ctx, WindowNo) && epl != null && epl.equals("Y");
		if (enforce
				//	Not yet implemented
				//&& MRole.getDefault().isOverwritePriceLimit()
				)
			enforce = false;
		//	Check Price Limit?
		if (enforce && PriceLimit.doubleValue() != 0.0
		  && PriceActual.compareTo(PriceLimit) < 0)
		{
			PriceActual = PriceLimit;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceLimit);
			if (PriceEntered == null)
				PriceEntered = PriceLimit;
			LogM.log(ctx, getClass(), Level.FINE, "(under) PriceEntered=" + PriceEntered + ", Actual" + PriceLimit);
			mTab.setValue ("PriceActual", PriceLimit);
			mTab.setValue ("PriceEntered", PriceEntered);
			Msg.alertMsg(ctx, "@UnderLimitPrice@");
			//	Repeat Discount calc
			if (PriceList.intValue() != 0)
			{
				Discount = new BigDecimal ((PriceList.doubleValue () - PriceActual.doubleValue ()) / PriceList.doubleValue () * 100.0);
				if (Discount.scale () > 2)
					Discount = Discount.setScale (2, BigDecimal.ROUND_HALF_UP);
				mTab.setValue ("Discount", Discount);
			}
		}

		//	Line Net Amt
		BigDecimal LineNetAmt = QtyOrdered.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		LogM.log(ctx, getClass(), Level.FINE, "LineNetAmt=" + LineNetAmt);
		mTab.setValue("LineNetAmt", LineNetAmt);
		//
		return "";
	}	//	amt

	/**
	 *	Order Line - Quantity.
	 *		- called from C_UOM_ID, QtyEntered, QtyOrdered
	 *		- enforces qty UOM relationship
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String qty (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		if (isCalloutActive() || value == null)
			return "";
		int M_Product_ID = Env.getContextAsInt(WindowNo, "M_Product_ID");
		BigDecimal QtyOrdered = Env.ZERO;
		BigDecimal QtyEntered, PriceActual, PriceEntered;
		
		//	No Product
		if (M_Product_ID == 0) {
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			QtyOrdered = QtyEntered;
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		//	UOM Changed - convert from Entered -> Product
		else if (mField.getColumnName().equals("C_UOM_ID")) {
			int C_UOM_To_ID = ((Integer)value).intValue();
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0) {
				LogM.log(ctx, getClass(), Level.FINE, "Corrected QtyEntered Scale UOM=" + C_UOM_To_ID 
					+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);  
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			PriceActual = (BigDecimal)mTab.getValue("PriceActual");
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual; 
			LogM.log(ctx, getClass(), Level.FINE, "UOM=" + C_UOM_To_ID 
				+ ", QtyEntered/PriceActual=" + QtyEntered + "/" + PriceActual
				+ " -> " + conversion 
				+ " QtyOrdered/PriceEntered=" + QtyOrdered + "/" + PriceEntered);
			Env.setContext(WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		//	QtyEntered changed - calculate QtyOrdered
		else if (mField.getColumnName().equals("QtyEntered")) {
			int C_UOM_To_ID = Env.getContextAsInt(WindowNo, "C_UOM_ID");
			QtyEntered = (BigDecimal)value;
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0) {
				LogM.log(ctx, getClass(), Level.FINE, "Corrected QtyEntered Scale UOM=" + C_UOM_To_ID 
					+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);  
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			LogM.log(ctx, getClass(), Level.FINE, "UOM=" + C_UOM_To_ID 
				+ ", QtyEntered=" + QtyEntered
				+ " -> " + conversion 
				+ " QtyOrdered=" + QtyOrdered);
			Env.setContext(WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		//	QtyOrdered changed - calculate QtyEntered (should not happen)
		else if (mField.getColumnName().equals("QtyOrdered")) {
			int C_UOM_To_ID = Env.getContextAsInt(WindowNo, "C_UOM_ID");
			QtyOrdered = (BigDecimal)value;
			int precision = MProduct.getUOMPrecision(ctx, M_Product_ID); 
			BigDecimal QtyOrdered1 = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (QtyOrdered.compareTo(QtyOrdered1) != 0) {
				LogM.log(ctx, getClass(), Level.FINE, "Corrected QtyOrdered Scale " 
					+ QtyOrdered + "->" + QtyOrdered1);  
				QtyOrdered = QtyOrdered1;
				mTab.setValue("QtyOrdered", QtyOrdered);
			}
			QtyEntered = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
				QtyEntered = QtyOrdered;
			boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			LogM.log(ctx, getClass(), Level.FINE, "UOM=" + C_UOM_To_ID 
				+ ", QtyOrdered=" + QtyOrdered
				+ " -> " + conversion 
				+ " QtyEntered=" + QtyEntered);
			Env.setContext(WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyEntered", QtyEntered);
		} else {
		//	QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
		}
		
		//	Storage
		//if (M_Product_ID != 0 
			//&& Env.isSOTrx(ctx, WindowNo)
			//&& QtyOrdered.signum() > 0)		//	no negative (returns)
		//{
			//MProduct product = MProduct.get (ctx, M_Product_ID);
			//if (product.isStocked()) {
				//int M_Warehouse_ID = Env.getContextAsInt(WindowNo, "M_Warehouse_ID");
				//int M_AttributeSetInstance_ID = Env.getContextAsInt(WindowNo, "M_AttributeSetInstance_ID");
				//BigDecimal available = QtyOrdered;
				//	Not yet implemented
				//MStorage.getQtyAvailable
					//(M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
				//if (available == null)
					//available = Env.ZERO;
				//if (available.signum() == 0)
					//Msg.alertMsg(ctx, "@NoQtyAvailable@ (0)");
				//else if (available.compareTo(QtyOrdered) < 0)
					//Msg.alertMsg(ctx, "@InsufficientQtyAvailable@ [" + available.toString() + "]");
				//else {
					//int C_OrderLine_ID = mTab.getValueAsInt("C_OrderLine_ID");
					//BigDecimal notReserved = MOrderLine.getNotReserved(ctx, 
						//M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
						//C_OrderLine_ID);
					//if (notReserved == null)
						//notReserved = Env.ZERO;
					//BigDecimal total = available.subtract(notReserved);
					//if (total.compareTo(QtyOrdered) < 0) {
						//String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available 
							//+ "  -  @QtyNotReserved@=" + notReserved + "  =  " + total);
						//Msg.alertMsg(ctx, "@InsufficientQtyAvailable@ [" + info + "]");
					//}
				//}
			//}
		//}
		//
		return "";
	}	//	qty
	
}
