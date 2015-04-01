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
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MUOM extends X_C_UOM {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 14:19:01
	 * @param ctx
	 * @param C_UOM_ID
	 * @param conn
	 */
	public MUOM(Context ctx, int C_UOM_ID, DB conn) {
		super(ctx, C_UOM_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 14:19:01
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MUOM(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	@Override
	protected boolean loadDefaultValues() {
		boolean ok = super.loadDefaultValues();
		//	Set Default Values
		setIsDefault (false);
		setStdPrecision (2);
		setCostingPrecision (6);
		//	
		return ok;
	}
	
	/**
	 * Get UOM
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:14:15
	 * @param ctx
	 * @param p_M_UOM_ID
	 * @return
	 * @return MRegion
	 */
	public static MUOM get(Context ctx, int p_M_UOM_ID) {
		return get(ctx, p_M_UOM_ID, null);
	}
	
	/**
	 * Get UOM
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:29:40
	 * @param ctx
	 * @param p_M_UOM_ID
	 * @param conn
	 * @return
	 * @return MRegion
	 */
	public static MUOM get(Context ctx, int p_M_UOM_ID, DB conn) {
		//	
		if(p_M_UOM_ID <= 0)
			return null;
		//	Default
		return new MUOM(ctx, p_M_UOM_ID, conn);
	}
	
	/** X12 Element 355 Code	Second							*/
	public static final String		X12_SECOND 			= "03";
	/** X12 Element 355 Code	Minute							*/
	public static final String		X12_MINUTE 			= "MJ";
	/** X12 Element 355 Code	Hour							*/
	public static final String		X12_HOUR 			= "HR";
	/** X12 Element 355 Code	Day 							*/
	public static final String		X12_DAY 			= "DA";
	/** X12 Element 355 Code	Work Day (8 hours / 5days)		*/
	public static final String		X12_DAY_WORK 		= "WD";
	/** X12 Element 355 Code	Week 							*/
	public static final String		X12_WEEK 			= "WK";
	/** X12 Element 355 Code	Month 							*/
	public static final String		X12_MONTH 			= "MO";
	/** X12 Element 355 Code	Work Month (20 days / 4 weeks) 	*/
	public static final String		X12_MONTH_WORK 		= "WM";
	/** X12 Element 355 Code	Year 							*/
	public static final String		X12_YEAR 			= "YR";

	/**
	 * 	Get Minute C_UOM_ID
	 *  @param ctx context
	 * 	@return C_UOM_ID for Minute
	 */
	public static int getMinute_UOM_ID (Context ctx) {
		//	Server
		String sql = "SELECT C_UOM_ID "
				+ "FROM C_UOM "
				+ "WHERE IsActive = 'Y' "
				+ "AND X12DE355 = ?";
		return DB.getSQLValue(null, sql, X12_MINUTE);
	}	//	getMinute_UOM_ID

	/**
	 * 	Get Default C_UOM_ID
	 *	@param ctx context for AD_Client
	 *	@return C_UOM_ID
	 */
	public static int getDefault_UOM_ID (Context ctx) {
		String sql = "SELECT C_UOM_ID "
			+ "FROM C_UOM "
			+ "WHERE AD_Client_ID IN (0, ?) "
			+ "ORDER BY IsDefault DESC, AD_Client_ID DESC, C_UOM_ID";
		return DB.getSQLValue(null, sql, String.valueOf(Env.getAD_Client_ID()));
	}	//	getDefault_UOM_ID

	/*************************************************************************/

	/**
	 * 	Get Precision
	 * 	@param ctx context
	 *	@param C_UOM_ID ID
	 * 	@return Precision
	 */
	public static int getPrecision (Context ctx, int C_UOM_ID) {
		MUOM uom = get(ctx, C_UOM_ID);
		return uom.getStdPrecision();
	}	//	getPrecision

	/**
	 * 	Load All UOMs
	 * 	@param ctx context
	 */
	//private static void loadUOMs (Context ctx) {
		//List<MUOM> list = new Query(ctx, Table_Name, "IsActive='Y'", null)
			//					.setApplyAccessFilter(MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)
				//				.list();
		//
		//for (MUOM uom : list) {
			//s_cache.put(uom.get_ID(), uom);
		//}
	//}	//	loadUOMs

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("UOM[");
		sb.append("ID=").append(get_ID())
			.append(", Name=").append(getName());
		return sb.toString();
	}	//	toString

	/**
	 * 	Round qty
	 *	@param qty quantity
	 *	@param stdPrecision true if std precisison
	 *	@return rounded quantity
	 */
	public BigDecimal round (BigDecimal qty, boolean stdPrecision) {
		int precision = getStdPrecision();
		if (!stdPrecision)
			precision = getCostingPrecision();
		if (qty.scale() > precision)
			return qty.setScale(getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		return qty;
	}	//	round

	/**
	 * 	Second
	 *	@return true if UOM is second
	 */
	public boolean isSecond() {
		return X12_SECOND.equals(getX12DE355());
	}
	/**
	 * 	Minute
	 *	@return true if UOM is minute
	 */
	public boolean isMinute() {
		return X12_MINUTE.equals(getX12DE355());
	}
	/**
	 * 	Hour
	 *	@return true if UOM is hour
	 */
	public boolean isHour() {
		return X12_HOUR.equals(getX12DE355());
	}
	/**
	 * 	Day
	 *	@return true if UOM is Day
	 */
	public boolean isDay() {
		return X12_DAY.equals(getX12DE355());
	}
	/**
	 * 	WorkDay
	 *	@return true if UOM is work day
	 */
	public boolean isWorkDay() {
		return X12_DAY_WORK.equals(getX12DE355());
	}
	/**
	 * 	Week
	 *	@return true if UOM is Week
	 */
	public boolean isWeek() {
		return X12_WEEK.equals(getX12DE355());
	}
	/**
	 * 	Month
	 *	@return true if UOM is Month
	 */
	public boolean isMonth() {
		return X12_MONTH.equals(getX12DE355());
	}
	/**
	 * 	WorkMonth
	 *	@return true if UOM is Work Month
	 */
	public boolean isWorkMonth() {
		return X12_MONTH_WORK.equals(getX12DE355());
	}
	/**
	 * 	Year
	 *	@return true if UOM is year
	 */
	public boolean isYear() {
		return X12_YEAR.equals(getX12DE355());
	}

}
