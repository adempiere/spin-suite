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
		boolean newDocNo = (oldDocNo == null);
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
					AD_Sequence_ID = rs.getInt(7);
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
				Env.setContext(ctx, WindowNo, "OrderType", DocSubTypeSO);
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
				Env.setContext(ctx, WindowNo, "HasCharges", rs.getString(rs.getColumnIndex("HasCharges")));

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
				int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
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

		boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
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
					int i = Env.getContextAsInt(ctx, "#M_PriceList_ID");
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
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
						shipTo_ID = Integer.parseInt(loc);
				}
				if (shipTo_ID == 0)
					mTab.setValue("C_BPartner_Location_ID", null);
				else
					mTab.setValue("C_BPartner_Location_ID", shipTo_ID);

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt(rs.getColumnIndex("AD_User_ID"));
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String cont = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "AD_User_ID");
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
				String OrderType = Env.getContext(ctx, WindowNo, "OrderType");
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

		boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
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
					int i = Env.getContextAsInt(ctx, "#M_PriceList_ID");
					if (i != 0)
						mTab.setValue("M_PriceList_ID", i);
				}

				int bill_Location_ID = rs.getInt(rs.getColumnIndex("Bill_Location_ID"));
				//	overwritten by InfoBP selection - works only if InfoWindow
				//	was used otherwise creates error (uses last value, may belong to different BP)
				if (bill_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
						bill_Location_ID = Integer.parseInt(loc);
				}
				if (bill_Location_ID == 0)
					mTab.setValue("Bill_Location_ID", null);
				else
					mTab.setValue("Bill_Location_ID", bill_Location_ID);

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt(rs.getColumnIndex("AD_User_ID"));
				if (bill_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String cont = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "AD_User_ID");
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
				String OrderType = Env.getContext(ctx, WindowNo, "OrderType");
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
	public String priceList (Context ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
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
			String date = mTab.getValueAsString("DateOrdered");
			//else if (mTab.getSPS_Table_ID() == I_C_Invoice.Table_ID)
				//date = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
			rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(M_PriceList_ID), date});
			if (rs.moveToFirst()) {
				//	Tax Included
				mTab.setValue("IsTaxIncluded", "Y".equals(rs.getString(1)));
				//	Price Limit Enforce
				Env.setContext(ctx, WindowNo, "EnforcePriceLimit", rs.getString(2));
				//	Currency
				int ii = rs.getInt(3);
				mTab.setValue("C_Currency_ID", ii);
				//	PriceList Version
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", rs.getInt(5));
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
		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
			&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
			mTab.setValue("M_AttributeSetInstance_ID", Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
		else
			mTab.setValue("M_AttributeSetInstance_ID", null);
			
		/*****	Price Calculation see also qty	****/
		int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
		BigDecimal Qty = (BigDecimal)mTab.getValue("QtyOrdered");
		boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		MProductPricing pp = new MProductPricing(ctx, M_Product_ID.intValue(), C_BPartner_ID, Qty, IsSOTrx);
		//
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		pp.setM_PriceList_ID(M_PriceList_ID);
		Date orderDate = (Date)mTab.getValue("DateOrdered");
		/** PLV is only accurate if PL selected in header */
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
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
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", M_PriceList_Version_ID );
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
		Env.setContext(ctx, WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");
		Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		
		//	Check/Update Warehouse Setting
		//	int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
		//	Integer wh = (Integer)mTab.getValue("M_Warehouse_ID");
		//	if (wh.intValue() != M_Warehouse_ID)
		//	{
		//		mTab.setValue("M_Warehouse_ID", new Integer(M_Warehouse_ID));
		//		ADialog.warn(,WindowNo, "WarehouseChanged");
		//	}

		
		if (Env.isSOTrx(ctx, WindowNo))
		{
			MProduct product = new MProduct(ctx, M_Product_ID.intValue(), null);
			if (product.isStocked())
			{
				BigDecimal QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
				int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
				int M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
				BigDecimal available = Env.ZERO;//MStorage.getQtyAvailable
					//(M_Warehouse_ID, M_Product_ID.intValue(), M_AttributeSetInstance_ID, null);
				if (available == null)
					available = Env.ZERO;
				if (available.signum() == 0)
					;//mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
				else if (available.compareTo(QtyOrdered) < 0)
					;//mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", available.toString(), false);
				else
				{
					Integer C_OrderLine_ID = (Integer)mTab.getValue("C_OrderLine_ID");
					if (C_OrderLine_ID == null)
						C_OrderLine_ID = 0;
					BigDecimal notReserved = Env.ZERO;//MOrderLine.getNotReserved(ctx, 
						//M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
						//C_OrderLine_ID.intValue());
					if (notReserved == null)
						notReserved = Env.ZERO;
					BigDecimal total = available.subtract(notReserved);
					if (total.compareTo(QtyOrdered) < 0)
					{
						String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available 
							+ " - @QtyNotReserved@=" + notReserved + " = " + total);
						//mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", 
							//info, false);
					}
				}
			}
		}
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
			M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int C_Charge_ID = 0;
		if (column.equals("C_Charge_ID"))
			C_Charge_ID = ((Integer)value).intValue();
		else
			C_Charge_ID = Env.getContextAsInt(ctx, WindowNo, "C_Charge_ID");
		LogM.log(ctx, getClass(), Level.FINE, "Product=" + M_Product_ID + ", C_Charge_ID=" + C_Charge_ID);
		if (M_Product_ID == 0 && C_Charge_ID == 0)
			return "";//amt(ctx, WindowNo, mTab, mField, value);		//

		//	Check Partner Location
		int shipC_BPartner_Location_ID = 0;
		if (column.equals("C_BPartner_Location_ID"))
			shipC_BPartner_Location_ID = ((Integer)value).intValue();
		else
			shipC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_Location_ID");
		if (shipC_BPartner_Location_ID == 0)
			return "";//amt(ctx, WindowNo, mTab, mField, value);		//
		LogM.log(ctx, getClass(), Level.FINE, "Ship BP_Location=" + shipC_BPartner_Location_ID);

		//
		String billDate = mTab.get_ValueAsString("DateOrdered");
		LogM.log(ctx, getClass(), Level.FINE, "Bill Date=" + billDate);

		String shipDate = mTab.get_ValueAsString("DatePromised");
		LogM.log(ctx, getClass(), Level.FINE, "Ship Date=" + shipDate);

		int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");
		LogM.log(ctx, getClass(), Level.FINE, "Org=" + AD_Org_ID);

		int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
		LogM.log(ctx, getClass(), Level.FINE, "Warehouse=" + M_Warehouse_ID);

		int billC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "Bill_Location_ID");
		if (billC_BPartner_Location_ID == 0)
			billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
		LogM.log(ctx, getClass(), Level.FINE, "Bill BP_Location=" + billC_BPartner_Location_ID);

		//
		int C_Tax_ID = 0;//Tax.get (ctx, M_Product_ID, C_Charge_ID, billDate, shipDate,
			//AD_Org_ID, M_Warehouse_ID, billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
			//"Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx")));
		LogM.log(ctx, getClass(), Level.FINE, "Tax ID=" + C_Tax_ID);
		//
		if (C_Tax_ID == 0)
			;//mTab.fireDataStatusEEvent(CLogger.retrieveError());
		else
			mTab.setValue("C_Tax_ID", C_Tax_ID);
		//
		return "";//amt(ctx, WindowNo, mTab, mField, value);
	}	//	tax

}
