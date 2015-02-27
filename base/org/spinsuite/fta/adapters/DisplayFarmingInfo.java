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

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class DisplayFarmingInfo {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 1/10/2014, 17:15:44
	 * @param m_M_Product_Category_ID
	 * @param m_ProductCategory
	 * @param m_FTA_FarmingStage_ID
	 * @param FarmingStage
	 * @param m_M_Product_ID
	 * @param m_Product
	 * @param m_QtyDosage
	 * @param m_EffectiveArea
	 * @param m_Area
	 * @param m_C_UOM_ID
	 * @param m_UOMSymbol
	 */
	public DisplayFarmingInfo(int m_M_Product_Category_ID, String m_ProductCategory, 
			int m_FTA_FarmingStage_ID, String FarmingStage, 
			int	m_M_Product_ID, String m_Product, 
			double m_QtyDosage, double m_EffectiveArea, double m_Area, 
			int m_C_UOM_ID, String m_UOMSymbol) {
		this.m_M_Product_Category_ID = m_M_Product_Category_ID;
		this.m_ProductCategory = m_ProductCategory;
		this.m_FTA_FarmingStage_ID = m_FTA_FarmingStage_ID;
		this.FarmingStage =FarmingStage;
		this.m_M_Product_ID = m_M_Product_ID;
		this.m_Product = m_Product;
		this.m_QtyDosage = m_QtyDosage;
		this.m_Area = m_Area;
		this.m_EffectiveArea = m_EffectiveArea;
		this.m_C_UOM_ID = m_C_UOM_ID;
		this.m_UOMSymbol = m_UOMSymbol;
	}
	
	/**	Product Category ID			*/
	private int 	m_M_Product_Category_ID = 0;
	/**	Product Category			*/
	private String	m_ProductCategory 		= null;
	/**	Farming Stage ID			*/
	private int 	m_FTA_FarmingStage_ID 	= 0;
	/**	Farming Stage				*/
	private String 	FarmingStage			= null;
	/**	Product ID					*/
	private	int		m_M_Product_ID			= 0;
	/**	Product						*/
	private String	m_Product				= null;
	/**	Qty Dosage					*/
	private double	m_QtyDosage				= 0;
	/**	Area						*/
	private double	m_Area					= 0;
	/**	Effective Area				*/
	private double	m_EffectiveArea			= 0;
	/**	UOM ID						*/
	private int 	m_C_UOM_ID				= 0;
	/**	UOM Symbol					*/
	private String	m_UOMSymbol				= null;
	
	/**
	 * @return the m_M_Product_Category_ID
	 */
	public int getM_Product_Category_ID() {
		return m_M_Product_Category_ID;
	}
	/**
	 * @return the m_ProductCategory
	 */
	public String getProductCategory() {
		return m_ProductCategory;
	}
	/**
	 * @return the m_FTA_FarmingStage_ID
	 */
	public int getFTA_FarmingStage_ID() {
		return m_FTA_FarmingStage_ID;
	}
	/**
	 * @return the farmingStage
	 */
	public String getFarmingStage() {
		return FarmingStage;
	}
	/**
	 * @return the m_M_Product_ID
	 */
	public int getM_Product_ID() {
		return m_M_Product_ID;
	}
	/**
	 * @return the m_Product
	 */
	public String getProduct() {
		return m_Product;
	}
	/**
	 * @return the m_QtyDosage
	 */
	public double getQtyDosage() {
		return m_QtyDosage;
	}
	/**
	 * @return the m_C_UOM_ID
	 */
	public int getC_UOM_ID() {
		return m_C_UOM_ID;
	}
	/**
	 * @return the m_UOMSymbol
	 */
	public String getUOMSymbol() {
		return m_UOMSymbol;
	}
	/**
	 * @param m_M_Product_Category_ID the m_M_Product_Category_ID to set
	 */
	public void setM_Product_Category_ID(int m_M_Product_Category_ID) {
		this.m_M_Product_Category_ID = m_M_Product_Category_ID;
	}
	/**
	 * @param m_ProductCategory the m_ProductCategory to set
	 */
	public void setProductCategory(String m_ProductCategory) {
		this.m_ProductCategory = m_ProductCategory;
	}
	/**
	 * @param m_FTA_FarmingStage_ID the m_FTA_FarmingStage_ID to set
	 */
	public void setFTA_FarmingStage_ID(int m_FTA_FarmingStage_ID) {
		this.m_FTA_FarmingStage_ID = m_FTA_FarmingStage_ID;
	}
	/**
	 * @param farmingStage the farmingStage to set
	 */
	public void setFarmingStage(String farmingStage) {
		FarmingStage = farmingStage;
	}
	/**
	 * @param m_M_Product_ID the m_M_Product_ID to set
	 */
	public void setM_Product_ID(int m_M_Product_ID) {
		this.m_M_Product_ID = m_M_Product_ID;
	}
	/**
	 * @param m_Product the m_Product to set
	 */
	public void setProduct(String m_Product) {
		this.m_Product = m_Product;
	}
	/**
	 * @param m_QtyDosage the m_QtyDosage to set
	 */
	public void setQtyDosage(double m_QtyDosage) {
		this.m_QtyDosage = m_QtyDosage;
	}
	/**
	 * @param m_C_UOM_ID the m_C_UOM_ID to set
	 */
	public void setC_UOM_ID(int m_C_UOM_ID) {
		this.m_C_UOM_ID = m_C_UOM_ID;
	}
	/**
	 * @param m_UOMSymbol the m_UOMSymbol to set
	 */
	public void setUOMSymbol(String m_UOMSymbol) {
		this.m_UOMSymbol = m_UOMSymbol;
	}
	/**
	 * @return the m_EffectiveArea
	 */
	public double getEffectiveArea() {
		return m_EffectiveArea;
	}
	/**
	 * @param m_EffectiveArea the m_EffectiveArea to set
	 */
	public void setEffectiveArea(double m_EffectiveArea) {
		this.m_EffectiveArea = m_EffectiveArea;
	}
	/**
	 * @return the m_Area
	 */
	public double getArea() {
		return m_Area;
	}
	/**
	 * @param m_Area the m_Area to set
	 */
	public void setArea(double m_Area) {
		this.m_Area = m_Area;
	}
}
