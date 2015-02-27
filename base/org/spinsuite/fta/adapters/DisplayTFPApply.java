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
package org.spinsuite.fta.adapters;

import java.util.Date;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class DisplayTFPApply {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/09/2014, 21:13:08
	 * @param m_FTA_TechnicalForm_ID
	 * @param m_FTA_ProductsToApply_ID
	 * @param m_Product
	 * @param m_DateFrom
	 * @param m_DateTo
	 * @param m_QtySuggested
	 * @param m_SuggestedUOM
	 * @param m_QtyDosage
	 * @param m_DosageUOM
	 * @param m_Qty
	 * @param m_UOM
	 * @param m_Warehouse
	 * @param m_IsApplied
	 */
	public DisplayTFPApply(int m_FTA_TechnicalForm_ID, int m_FTA_ProductsToApply_ID, String m_Product, 
			Date m_DateFrom, Date m_DateTo, 
			double m_QtySuggested, String m_SuggestedUOM,
			double m_QtyDosage, String m_DosageUOM, 
			double m_Qty, String m_UOM,
			String m_Warehouse, boolean m_IsApplied) {
		this.m_FTA_TechnicalForm_ID = m_FTA_TechnicalForm_ID;
		this.m_FTA_ProductsToApply_ID = m_FTA_ProductsToApply_ID;
		this.m_Product = m_Product;
		this.m_DateFrom = m_DateFrom;
		this.m_DateTo = m_DateTo;
		this.m_QtySuggested = m_QtySuggested;
		this.m_SuggestedUOM = m_SuggestedUOM;
		this.m_QtyDosage = m_QtyDosage;
		this.m_DosageUOM = m_DosageUOM;
		this.m_Qty = m_Qty;
		this.m_UOM = m_UOM;
		this.m_Warehouse = m_Warehouse;
		this.m_IsApplied = m_IsApplied;
	}
	
	/**	Attributes				*/
	private int 	m_FTA_TechnicalForm_ID 		= 0;
	private int 	m_FTA_ProductsToApply_ID 	= 0;
	private String 	m_Product 					= null;
	private Date 	m_DateFrom					= null;
	private Date 	m_DateTo					= null;
	private double 	m_QtySuggested				= 0;
	private String 	m_SuggestedUOM				= null;
	private double 	m_QtyDosage					= 0;
	private String 	m_DosageUOM					= null;
	private double 	m_Qty						= 0;
	private String 	m_UOM						= null;
	private String 	m_Warehouse					= null;
	private boolean m_IsApplied					= false;
	
	/**
	 * Get Technical Form ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:08:35
	 * @return
	 * @return int
	 */
	public int getFTA_TechnicalForm_ID() {
		return m_FTA_TechnicalForm_ID;
	}
	
	/**
	 * Set Technical Form ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:08:50
	 * @param m_FTA_TechnicalForm_ID
	 * @return void
	 */
	public void setFTA_TechnicalForm_ID(int m_FTA_TechnicalForm_ID) {
		this.m_FTA_TechnicalForm_ID = m_FTA_TechnicalForm_ID;
	}
	
	/**
	 * Get Products To Apply
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 02:28:35
	 * @return
	 * @return int
	 */
	public int getFTA_ProductsToApply_ID() {
		return m_FTA_ProductsToApply_ID;
	}

	/**
	 * Set Products To Apply
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 02:29:39
	 * @param m_FTA_ProductsToApply_ID
	 * @return void
	 */
	public void setFTA_ProductsToApply_ID(int m_FTA_ProductsToApply_ID) {
		this.m_FTA_ProductsToApply_ID = m_FTA_ProductsToApply_ID;
	}
	
	/**
	 * Get Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:45:21
	 * @return
	 * @return String
	 */
	public String getProduct() {
		return m_Product;
	}

	/**
	 * Set Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:46:59
	 * @param m_Product
	 * @return void
	 */
	public void setProduct(String m_Product) {
		this.m_Product = m_Product;
	}

	/**
	 * Get Date From
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:47:06
	 * @return
	 * @return Date
	 */
	public Date getDateFrom() {
		return m_DateFrom;
	}

	/**
	 * Set Date From
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:47:13
	 * @param m_DateFrom
	 * @return void
	 */
	public void setDateFrom(Date m_DateFrom) {
		this.m_DateFrom = m_DateFrom;
	}

	/**
	 * Get Date To
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:47:26
	 * @return
	 * @return Date
	 */
	public Date getDateTo() {
		return m_DateTo;
	}

	/**
	 * Set Date To
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:47:37
	 * @param m_DateTo
	 * @return void
	 */
	public void setDateTo(Date m_DateTo) {
		this.m_DateTo = m_DateTo;
	}

	/**
	 * Get Quantity Suggested
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:47:45
	 * @return
	 * @return double
	 */
	public double getQtySuggested() {
		return m_QtySuggested;
	}

	/**
	 * Set Quantity Suggested
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:48:00
	 * @param m_QtySuggested
	 * @return void
	 */
	public void setQtySuggested(double m_QtySuggested) {
		this.m_QtySuggested = m_QtySuggested;
	}

	/**
	 * Get UOM Suggested
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:48:11
	 * @return
	 * @return String
	 */
	public String getSuggestedUOM() {
		return m_SuggestedUOM;
	}

	/**
	 * Set UOM Suggested
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:48:30
	 * @param m_SuggestedUOM
	 * @return void
	 */
	public void setSuggestedUOM(String m_SuggestedUOM) {
		this.m_SuggestedUOM = m_SuggestedUOM;
	}

	/**
	 * Get Quantity Dosage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:48:46
	 * @return
	 * @return double
	 */
	public double getQtyDosage() {
		return m_QtyDosage;
	}

	/**
	 * Set Quantity Dosage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:50:03
	 * @param m_QtyDosage
	 * @return void
	 */
	public void setQtyDosage(double m_QtyDosage) {
		this.m_QtyDosage = m_QtyDosage;
	}

	/**
	 * Get UOM Dosage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:50:14
	 * @return
	 * @return String
	 */
	public String getDosageUOM() {
		return m_DosageUOM;
	}

	/**
	 * Set UOM Dosage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:50:25
	 * @param m_DosageUOM
	 * @return void
	 */
	public void setDosageUOM(String m_DosageUOM) {
		this.m_DosageUOM = m_DosageUOM;
	}

	/**
	 * Get Quantity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:50:32
	 * @return
	 * @return double
	 */
	public double getQty() {
		return m_Qty;
	}

	/**
	 * Set Quantity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:50:41
	 * @param m_Qty
	 * @return void
	 */
	public void setQty(double m_Qty) {
		this.m_Qty = m_Qty;
	}

	/**
	 * Get UOM
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:50:49
	 * @return
	 * @return String
	 */
	public String getUOM() {
		return m_UOM;
	}

	/**
	 * Set UOM
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:50:57
	 * @param m_UOM
	 * @return void
	 */
	public void setUOM(String m_UOM) {
		this.m_UOM = m_UOM;
	}

	/**
	 * Get Warehouse
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:51:05
	 * @return
	 * @return String
	 */
	public String getWarehouse() {
		return m_Warehouse;
	}

	/**
	 * Set Warehouse
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/08/2014, 01:51:15
	 * @param m_Warehouse
	 * @return void
	 */
	public void setWarehouse(String m_Warehouse) {
		this.m_Warehouse = m_Warehouse;
	}
	
	/**
	 * Set Is Applied
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/09/2014, 21:13:50
	 * @param m_IsApplied
	 * @return void
	 */
	public void setIsApplied(boolean m_IsApplied) {
		this.m_IsApplied = m_IsApplied;
	}
	
	/**
	 * Is Applied
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/09/2014, 21:14:22
	 * @return
	 * @return boolean
	 */
	public boolean isApplied() {
		return m_IsApplied;
	}
} 