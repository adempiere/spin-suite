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
package org.spinsuite.model;

import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.exceptions.TaxCriteriaNotFoundException;
import org.spinsuite.exceptions.TaxForChangeNotFoundException;
import org.spinsuite.exceptions.TaxNoExemptFoundException;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 *	Tax Handling
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Tax.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class Tax {
	
	/**************************************************************************
	 *	Get Tax ID - converts parameters to call Get Tax.
	 *  <pre>
	 *		M_Product_ID/C_Charge_ID	->	C_TaxCategory_ID
	 *		billDate, shipDate			->	billDate, shipDate
	 *		AD_Org_ID					->	billFromC_Location_ID
	 *		M_Warehouse_ID				->	shipFromC_Location_ID
	 *		billC_BPartner_Location_ID  ->	billToC_Location_ID
	 *		shipC_BPartner_Location_ID 	->	shipToC_Location_ID
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 *  </pre>
	 * 	@param ctx	context
	 * 	@param M_Product_ID product
	 * 	@param C_Charge_ID product
	 * 	@param billDate invoice date
	 * 	@param shipDate ship date (ignored)
	 * 	@param AD_Org_ID org
	 * 	@param M_Warehouse_ID warehouse (ignored)
	 * 	@param billC_BPartner_Location_ID invoice location
	 * 	@param shipC_BPartner_Location_ID ship location (ignored)
	 * 	@param IsSOTrx is a sales trx
	 * 	@return C_Tax_ID
	 *  @throws TaxCriteriaNotFoundException if a criteria was not found
	 */
	public static int get (Context ctx, int M_Product_ID, int C_Charge_ID,
		Date billDate, Date shipDate,
		int AD_Org_ID, int M_Warehouse_ID,
		int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID,
		boolean IsSOTrx) {
		if (M_Product_ID != 0)
			return getProduct (ctx, M_Product_ID, billDate, shipDate, AD_Org_ID, M_Warehouse_ID,
				billC_BPartner_Location_ID, shipC_BPartner_Location_ID, IsSOTrx);
		else if (C_Charge_ID != 0)
			return getCharge (ctx, C_Charge_ID, billDate, shipDate, AD_Org_ID, M_Warehouse_ID,
				billC_BPartner_Location_ID, shipC_BPartner_Location_ID, IsSOTrx);
		else
			return getExemptTax (ctx, AD_Org_ID);
	}	//	get

	/**
	 *	Get Tax ID - converts parameters to call Get Tax.
	 *  <pre>
	 *		C_Charge_ID					->	C_TaxCategory_ID
	 *		billDate					->	billDate
	 *		shipDate					->	shipDate (ignored)
	 *		AD_Org_ID					->	billFromC_Location_ID
	 *		M_Warehouse_ID				->	shipFromC_Location_ID (ignored)
	 *		billC_BPartner_Location_ID  ->	billToC_Location_ID
	 *		shipC_BPartner_Location_ID 	->	shipToC_Location_ID (ignored)
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 *  </pre>
	 * 	@param ctx	context
	 * 	@param C_Charge_ID product
	 * 	@param billDate invoice date
	 * 	@param shipDate ship date (ignored)
	 * 	@param AD_Org_ID org
	 * 	@param M_Warehouse_ID warehouse (ignored)
	 * 	@param billC_BPartner_Location_ID invoice location
	 * 	@param shipC_BPartner_Location_ID ship location (ignored)
	 * 	@param IsSOTrx is a sales trx
	 * 	@return C_Tax_ID
	 *  @throws TaxForChangeNotFoundException if criteria not found for given change
	 *  @throws TaxCriteriaNotFoundException if a criteria was not found
	 */
	public static int getCharge (Context ctx, int C_Charge_ID,
		Date billDate, Date shipDate,
		int AD_Org_ID, int M_Warehouse_ID,
		int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID,
		boolean IsSOTrx) {
		/* ship location from warehouse is plainly ignored below */
		// if (M_Warehouse_ID <= 0)
			// M_Warehouse_ID = Env.getContextAsInt(ctx, "M_Warehouse_ID");
		// if (M_Warehouse_ID <= 0)
		// {
			// throw new TaxForChangeNotFoundException(C_Charge_ID, AD_Org_ID, M_Warehouse_ID,
						// billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
						// "@NotFound@ @M_Warehouse_ID@");
		// }
		int C_TaxCategory_ID = 0;
		int shipFromC_Location_ID = 0;
		int shipToC_Location_ID = 0;
		int billFromC_Location_ID = 0;
		int billToC_Location_ID = 0;
		String IsTaxExempt = null;
		String IsSOTaxExempt = null;
		String IsPOTaxExempt = null;

		//	Get all at once
		String sql = "SELECT c.C_TaxCategory_ID, o.C_Location_ID, il.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt,"
			 + " w.C_Location_ID, sl.C_Location_ID "
			 + "FROM C_Charge c, AD_OrgInfo o,"
			 + " C_BPartner_Location il "
			 + "INNER JOIN C_BPartner b ON (il.C_BPartner_ID=b.C_BPartner_ID) "
			 + "LEFT JOIN M_Warehouse w ON (w.M_Warehouse_ID=?), "
			 + "C_BPartner_Location sl "
			 + "WHERE c.C_Charge_ID=? "
			 + "AND o.AD_Org_ID=? "
			 + "AND il.C_BPartner_Location_ID=? "
			 + "AND sl.C_BPartner_Location_ID=? ";
		Cursor rs = null;
		DB conn = new DB(ctx);
		try {
			DB.loadConnection(conn, DB.READ_ONLY);
			rs = conn.querySQL(sql, new String[]{String.valueOf(M_Warehouse_ID), 
					String.valueOf(C_Charge_ID), String.valueOf(AD_Org_ID), 
					String.valueOf(billC_BPartner_Location_ID), 
					String.valueOf(shipC_BPartner_Location_ID)});
			boolean found = false;
			if (rs.moveToFirst()) {
				C_TaxCategory_ID = rs.getInt (0);
				billFromC_Location_ID = rs.getInt (1);
				billToC_Location_ID = rs.getInt (2);
				IsSOTaxExempt = rs.getString (3);
				IsPOTaxExempt = rs.getString (4);
				IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
				shipFromC_Location_ID = rs.getInt (5);
				shipToC_Location_ID = rs.getInt (6);
				found = true;
			}
			//
			if (!found)
			{
				throw new TaxForChangeNotFoundException(C_Charge_ID, AD_Org_ID, M_Warehouse_ID,
						billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
						null);
			}
			else if ("Y".equals (IsTaxExempt))
			{
				return getExemptTax (ctx, AD_Org_ID);
			}
		} catch (Exception e) {
			LogM.log(ctx, Tax.class, Level.SEVERE, "Error", e);
		} finally {
			DB.closeConnection(conn);
		}

		//	Reverese for PO
		if (!IsSOTrx)
		{
			int temp = billFromC_Location_ID;
			billFromC_Location_ID = billToC_Location_ID;
			billToC_Location_ID = temp;
			temp = shipFromC_Location_ID;
			shipFromC_Location_ID = shipToC_Location_ID;
			shipToC_Location_ID = temp;
		}
		//
		LogM.log(ctx, Tax.class, Level.FINE, "getCharge - C_TaxCategory_ID=" + C_TaxCategory_ID
		  + ", billFromC_Location_ID=" + billFromC_Location_ID
		  + ", billToC_Location_ID=" + billToC_Location_ID
		  + ", shipFromC_Location_ID=" + shipFromC_Location_ID
		  + ", shipToC_Location_ID=" + shipToC_Location_ID);
		return get (ctx, C_TaxCategory_ID, IsSOTrx,
		  shipDate, shipFromC_Location_ID, shipToC_Location_ID,
		  billDate, billFromC_Location_ID, billToC_Location_ID);
	}	//	getCharge


	/**
	 *	Get Tax ID - converts parameters to call Get Tax.
	 *  <pre>
	 *		M_Product_ID				->	C_TaxCategory_ID
	 *		billDate					->	billDate
	 *		shipDate					->	shipDate (ignored)
	 *		AD_Org_ID					->	billFromC_Location_ID
	 *		M_Warehouse_ID				->	shipFromC_Location_ID (ignored)
	 *		billC_BPartner_Location_ID  ->	billToC_Location_ID
	 *		shipC_BPartner_Location_ID 	->	shipToC_Location_ID (ignored)
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 *  </pre>
	 * 	@param ctx	context
	 * 	@param M_Product_ID product
	 * 	@param billDate invoice date
	 * 	@param shipDate ship date (ignored)
	 * 	@param AD_Org_ID org
	 * 	@param M_Warehouse_ID warehouse (ignored)
	 * 	@param billC_BPartner_Location_ID invoice location
	 * 	@param shipC_BPartner_Location_ID ship location (ignored)
	 * 	@param IsSOTrx is a sales trx
	 * 	@return C_Tax_ID
	 *  If error it returns 0 and sets error log (TaxCriteriaNotFound)
	 */
	public static int getProduct (Context ctx, int M_Product_ID,
		Date billDate, Date shipDate,
		int AD_Org_ID, int M_Warehouse_ID,
		int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID,
		boolean IsSOTrx) {
		String variable = "";
		int C_TaxCategory_ID = 0;
		int shipFromC_Location_ID = 0;
		int shipToC_Location_ID = 0;
		int billFromC_Location_ID = 0;
		int billToC_Location_ID = 0;
		String IsTaxExempt = null;
		String IsSOTaxExempt = null;
		String IsPOTaxExempt = null;

		String sql = null;
		DB conn = new DB(ctx);
		Cursor rs = null;
		try {
			//	Get all at once
			sql = "SELECT p.C_TaxCategory_ID, o.C_Location_ID, il.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt, "
				+ " w.C_Location_ID, sl.C_Location_ID "
				+ "FROM M_Product p, AD_OrgInfo o,"
				+ " C_BPartner_Location il "
				+ "INNER JOIN C_BPartner b ON (il.C_BPartner_ID=b.C_BPartner_ID) "
				+ "LEFT JOIN M_Warehouse w ON (w.M_Warehouse_ID=?), C_BPartner_Location sl "
				+ "WHERE p.M_Product_ID=?"
				+ " AND o.AD_Org_ID=?"
				+ " AND il.C_BPartner_Location_ID=?"
				+ " AND sl.C_BPartner_Location_ID=?";
			DB.loadConnection(conn, DB.READ_ONLY);
			rs = conn.querySQL(sql, new String[]{String.valueOf(M_Warehouse_ID), 
					String.valueOf(M_Product_ID), String.valueOf(AD_Org_ID), 
					String.valueOf(billC_BPartner_Location_ID), 
					String.valueOf(shipC_BPartner_Location_ID)});
			
			boolean found = false;
			if (rs.moveToFirst()) {
				C_TaxCategory_ID = rs.getInt(0);
				billFromC_Location_ID = rs.getInt(1);
				billToC_Location_ID = rs.getInt(2);
				IsSOTaxExempt = rs.getString(3);
				IsPOTaxExempt = rs.getString(4);
				IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
				shipFromC_Location_ID = rs.getInt(5);
				shipToC_Location_ID = rs.getInt(6);
				found = true;
			}
			//
			if (found && "Y".equals(IsTaxExempt)) {
				LogM.log(ctx, Tax.class, Level.FINE, "getProduct - Business Partner is Tax exempt");
				return getExemptTax(ctx, AD_Org_ID);
			} else if (found) {
				if (!IsSOTrx) {
					int temp = billFromC_Location_ID;
					billFromC_Location_ID = billToC_Location_ID;
					billToC_Location_ID = temp;
					temp = shipFromC_Location_ID;
					shipFromC_Location_ID = shipToC_Location_ID;
					shipToC_Location_ID = temp;
				}
				LogM.log(ctx, Tax.class, Level.FINE, "getProduct - C_TaxCategory_ID=" + C_TaxCategory_ID
					+ ", billFromC_Location_ID=" + billFromC_Location_ID
					+ ", billToC_Location_ID=" + billToC_Location_ID
					+ ", shipFromC_Location_ID=" + shipFromC_Location_ID
					+ ", shipToC_Location_ID=" + shipToC_Location_ID);
				return get(ctx, C_TaxCategory_ID, IsSOTrx,
					shipDate, shipFromC_Location_ID, shipToC_Location_ID,
					billDate, billFromC_Location_ID, billToC_Location_ID);
			}

			// ----------------------------------------------------------------

			//	Detail for error isolation

		//	M_Product_ID				->	C_TaxCategory_ID
			variable = "M_Product_ID";
			sql = "SELECT C_TaxCategory_ID FROM M_Product WHERE M_Product_ID=?";
			C_TaxCategory_ID = DB.getSQLValueEx(ctx, sql, conn, String.valueOf(M_Product_ID));
			found = C_TaxCategory_ID != -1;
			if (C_TaxCategory_ID <= 0)
			{
				throw new TaxCriteriaNotFoundException(variable, M_Product_ID);
			}
			LogM.log(ctx, Tax.class, Level.FINE, "getProduct - C_TaxCategory_ID=" + C_TaxCategory_ID);

			//	AD_Org_ID	->	billFromC_Location_ID
			variable = "AD_Org_ID";
			sql = "SELECT C_Location_ID FROM AD_OrgInfo WHERE AD_Org_ID=?";
			billFromC_Location_ID = DB.getSQLValueEx(ctx, sql, conn, String.valueOf(AD_Org_ID));
			found = billFromC_Location_ID != -1;
			if (billFromC_Location_ID <= 0)
			{
				throw new TaxCriteriaNotFoundException(variable, AD_Org_ID);
			}

			//	billC_BPartner_Location_ID  ->	billToC_Location_ID
			variable = "BillTo_ID";
			sql = "SELECT l.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt "
				+ " FROM C_BPartner_Location l"
				+ " INNER JOIN C_BPartner b ON (l.C_BPartner_ID=b.C_BPartner_ID) "
				+ " WHERE C_BPartner_Location_ID=?";
			rs = conn.querySQL(sql, new String[]{String.valueOf(billC_BPartner_Location_ID)});
			found = false;
			if (rs.moveToFirst()) {
				billToC_Location_ID = rs.getInt(0);
				IsSOTaxExempt = rs.getString(1);
				IsPOTaxExempt = rs.getString(2);
				IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
				found = true;
			}
			
			if (billToC_Location_ID <= 0) {
				throw new TaxCriteriaNotFoundException(variable, billC_BPartner_Location_ID);
			}
			if ("Y".equals(IsTaxExempt))
				return getExemptTax(ctx, AD_Org_ID);

			//  Reverse for PO
			if (!IsSOTrx) {
				int temp = billFromC_Location_ID;
				billFromC_Location_ID = billToC_Location_ID;
				billToC_Location_ID = temp;
			}
			LogM.log(ctx, Tax.class, Level.FINE, "getProduct - billFromC_Location_ID = " + billFromC_Location_ID);
			LogM.log(ctx, Tax.class, Level.FINE, "getProduct - billToC_Location_ID = " + billToC_Location_ID);

			//-----------------------------------------------------------------

		//	M_Warehouse_ID				->	shipFromC_Location_ID
			variable = "M_Warehouse_ID";
			sql = "SELECT C_Location_ID FROM M_Warehouse WHERE M_Warehouse_ID=?";
			shipFromC_Location_ID = DB.getSQLValueEx(ctx, sql, conn, String.valueOf(M_Warehouse_ID));
			found = shipFromC_Location_ID != -1;
			if (shipFromC_Location_ID <= 0) {
				throw new TaxCriteriaNotFoundException(variable, M_Warehouse_ID);
			}

		//	shipC_BPartner_Location_ID 	->	shipToC_Location_ID
			variable = "C_BPartner_Location_ID";
			sql = "SELECT C_Location_ID FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
			shipToC_Location_ID = DB.getSQLValueEx(ctx, sql, conn, String.valueOf(shipC_BPartner_Location_ID));
			found = shipToC_Location_ID != -1;
			if (shipToC_Location_ID <= 0) {
				throw new TaxCriteriaNotFoundException(variable, shipC_BPartner_Location_ID);
			}

			//  Reverse for PO
			if (!IsSOTrx) {
				int temp = shipFromC_Location_ID;
				shipFromC_Location_ID = shipToC_Location_ID;
				shipToC_Location_ID = temp;
			}
			LogM.log(ctx, Tax.class, Level.FINE, "getProduct - shipFromC_Location_ID = " + shipFromC_Location_ID);
			LogM.log(ctx, Tax.class, Level.FINE, "getProduct - shipToC_Location_ID = " + shipToC_Location_ID);
		} catch (Exception e) {
			LogM.log(ctx, Tax.class, Level.SEVERE, "Error", e);
		} finally {
			DB.closeConnection(conn);
		}

		return get (ctx, C_TaxCategory_ID, IsSOTrx,
			shipDate, shipFromC_Location_ID, shipToC_Location_ID,
			billDate, billFromC_Location_ID, billToC_Location_ID);
	}	//	getProduct

	/**
	 * Get Exempt Tax Code
	 * @param ctx context
	 * @param AD_Org_ID org to find client
	 * @return C_Tax_ID
	 * @throws TaxNoExemptFoundException if no tax exempt found
	 */
	private static int getExemptTax (Context ctx, int AD_Org_ID) {
		final String sql = "SELECT t.C_Tax_ID "
			+ "FROM C_Tax t"
			+ " INNER JOIN AD_Org o ON (t.AD_Client_ID=o.AD_Client_ID) "
			+ "WHERE t.IsTaxExempt='Y' AND o.AD_Org_ID=? "
			+ "ORDER BY t.Rate DESC";
		int C_Tax_ID = DB.getSQLValueEx(ctx, sql, String.valueOf(AD_Org_ID));
		LogM.log(ctx, Tax.class, Level.FINE, "getExemptTax - TaxExempt=Y - C_Tax_ID=" + C_Tax_ID);
		if (C_Tax_ID <= 0) {
			LogM.log(ctx, Tax.class, Level.SEVERE, "TaxNoExemptFoundException");
			return 0;
		} else {
			return C_Tax_ID;
		}
	}	//	getExemptTax

	
	/**************************************************************************
	 *	Get Tax ID (Detail).
	 *  @param ctx context
	 *	@param C_TaxCategory_ID tax category
	 * 	@param IsSOTrx Sales Order Trx
	 *	@param shipDate ship date (ignored)
	 *	@param shipFromC_Location_ID ship from (ignored)
	 *	@param shipToC_Location_ID ship to (ignored)
	 *	@param billDate invoice date
	 *	@param billFromC_Location_ID invoice from
	 *	@param billToC_Location_ID invoice to
	 *	@return C_Tax_ID
	 *  @throws TaxNotFoundException if no tax found for given criteria
	 */
	protected static int get (Context ctx,
		int C_TaxCategory_ID, boolean IsSOTrx,
		Date shipDate, int shipFromC_Location_ID, int shipToC_Location_ID,
		Date billDate, int billFromC_Location_ID, int billToC_Location_ID) {
		//	C_TaxCategory contains CommodityCode
		
		//	API to Tax Vendor comes here

		LogM.log(ctx, Tax.class, Level.FINE, "get(Detail) - Category=" + C_TaxCategory_ID 
				+ ", SOTrx=" + IsSOTrx);
		LogM.log(ctx, Tax.class, Level.CONFIG, "get(Detail) - BillFrom=" + billFromC_Location_ID 
				+ ", BillTo=" + billToC_Location_ID + ", BillDate=" + billDate);

		MTax[] taxes = MTax.getAll(ctx);
		MLocation lFrom = new MLocation(ctx, billFromC_Location_ID, null); 
		MLocation lTo = new MLocation(ctx, billToC_Location_ID, null); 
		LogM.log(ctx, Tax.class, Level.FINER, "From=" + lFrom);
		LogM.log(ctx, Tax.class, Level.FINER, "To=" + lTo);
		
		for (int i = 0; i < taxes.length; i++) {
			MTax tax = taxes[i];
			LogM.log(ctx, Tax.class, Level.FINER, tax.toString());
			//
			if (tax.getC_TaxCategory_ID() != C_TaxCategory_ID
				|| !tax.isActive() 
				|| tax.getParent_Tax_ID() != 0)	//	user parent tax
				continue;
			if (IsSOTrx && MTax.SOPOTYPE_PurchaseTax.equals(tax.getSOPOType()))
				continue;
			if (!IsSOTrx && MTax.SOPOTYPE_SalesTax.equals(tax.getSOPOType()))
				continue;
			
			LogM.log(ctx, Tax.class, Level.FINEST, "From Country - " + (tax.getC_Country_ID() == lFrom.getC_Country_ID() 
				|| tax.getC_Country_ID() == 0));
			LogM.log(ctx, Tax.class, Level.FINEST, "From Region - " + (tax.getC_Region_ID() == lFrom.getC_Region_ID() 
				|| tax.getC_Region_ID() == 0));
			LogM.log(ctx, Tax.class, Level.FINEST, "To Country - " + (tax.getTo_Country_ID() == lTo.getC_Country_ID() 
				|| tax.getTo_Country_ID() == 0));
			LogM.log(ctx, Tax.class, Level.FINEST, "To Region - " + (tax.getTo_Region_ID() == lTo.getC_Region_ID() 
				|| tax.getTo_Region_ID() == 0));
			LogM.log(ctx, Tax.class, Level.FINEST, "Date valid - " + (!tax.getValidFrom().after(billDate)));
			
				//	From Country
			if ((tax.getC_Country_ID() == lFrom.getC_Country_ID() 
					|| tax.getC_Country_ID() == 0)
				//	From Region
				&& (tax.getC_Region_ID() == lFrom.getC_Region_ID() 
					|| tax.getC_Region_ID() == 0)
				//	To Country
				&& (tax.getTo_Country_ID() == lTo.getC_Country_ID() 
					|| tax.getTo_Country_ID() == 0)
				//	To Region
				&& (tax.getTo_Region_ID() == lTo.getC_Region_ID() 
					|| tax.getTo_Region_ID() == 0)
				//	Date
				&& !tax.getValidFrom().after(billDate)
				) {
				if (!tax.isPostal())
					return tax.getC_Tax_ID();
				//
				//MTaxPostal[] postals = tax.getPostals(false);
				//for (int j = 0; j < postals.length; j++)
				//{
					//MTaxPostal postal = postals[j];
					//if (postal.isActive()
						//	Postal From is mandatory
						//&& postal.getPostal().startsWith(lFrom.getPostal())
						//	Postal To is optional
						//&& (postal.getPostal_To() == null 
							//|| postal.getPostal_To().startsWith(lTo.getPostal()))
						//)
						//return tax.getC_Tax_ID();
				//}	//	for all postals
			}
		}	//	for all taxes

		//	Default Tax
		for (int i = 0; i < taxes.length; i++)
		{
			MTax tax = taxes[i];
			if (!tax.isDefault() || !tax.isActive()
				|| tax.getParent_Tax_ID() != 0)	//	user parent tax
				continue;
			if (IsSOTrx && MTax.SOPOTYPE_PurchaseTax.equals(tax.getSOPOType()))
				continue;
			if (!IsSOTrx && MTax.SOPOTYPE_SalesTax.equals(tax.getSOPOType()))
				continue;
			LogM.log(ctx, Tax.class, Level.FINE, "get (default) - " + tax);
			return tax.getC_Tax_ID();
		}	//	for all taxes
		
		//throw new TaxNotFoundException(C_TaxCategory_ID, IsSOTrx,
				//shipDate, shipFromC_Location_ID, shipToC_Location_ID,
				//billDate, billFromC_Location_ID, billToC_Location_ID);
		LogM.log(ctx, Tax.class, Level.SEVERE, "Error: TaxNotFoundException");
		//	
		return 0;
	}	//	get
	
}	//	Tax
