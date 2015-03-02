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
package org.spinsuite.print;

import java.util.ArrayList;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class RowPrintData {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/03/2014, 11:41:07
	 * @param isHeaderRow
	 * @param isFunctionRow
	 */
	public RowPrintData(boolean isHeaderRow, boolean isFunctionRow) {
		this.isHeaderRow = isHeaderRow;
		this.isFunctionRow = isFunctionRow;
		m_columns = new ArrayList<ColumnPrintData>();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/03/2014, 11:43:53
	 */
	public RowPrintData() {
		this(false, false);
	}
	
	/**	Columns				*/
	private ArrayList<ColumnPrintData> 	m_columns = null;
	/**	Is Header Row		*/
	private boolean						isHeaderRow = false;
	/**	Is Summary Row		*/
	private boolean 					isFunctionRow = false;
	
	/**
	 * Get at Index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/03/2014, 16:32:26
	 * @param index
	 * @return
	 * @return ColumnPrintData
	 */
	public ColumnPrintData get(int index){
		return m_columns.get(index);
	}
	
	/**
	 * Add Column
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/03/2014, 11:41:25
	 * @param m_Value
	 * @param m_Suffix
	 * @return void
	 */
	public void addColumn(String m_Value, String m_Suffix){
		m_columns.add(new ColumnPrintData(m_Value, m_Suffix));
	}
	
	/**
	 * Add Column
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/03/2014, 16:14:10
	 * @param column
	 * @return void
	 */
	public void addColumn(ColumnPrintData column){
		m_columns.add(column);
	}
	
	/**
	 * Get Prefix at index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 18:19:53
	 * @param index
	 * @return
	 * @return String
	 */
	public String getValue(int index){
		return m_columns.get(index).getValue();
	}
	
	/**
	 * Get Suffix at index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 18:20:20
	 * @param index
	 * @return
	 * @return String
	 */
	public String getSuffix(int index){
		return m_columns.get(index).getSuffix();
	}
	
	/**
	 * Get Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/03/2014, 16:12:46
	 * @return
	 * @return ColumnPrintData[]
	 */
	public ColumnPrintData[] getColumns(){
		ColumnPrintData[] columns = new ColumnPrintData[m_columns.size()];
		m_columns.toArray(columns);
		return columns;
	}
	
	/**
	 * Get Columns Quantity
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/03/2014, 20:35:35
	 * @return
	 * @return int
	 */
	public int getColumnsQty(){
		return m_columns.size();
	}
	
	/**
	 * Is Function Row Flag
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/03/2014, 11:38:28
	 * @return
	 * @return boolean
	 */
	public boolean isFunctionRow(){
		return isFunctionRow;
	}
	
	/**
	 * Is Header Row Flag
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/03/2014, 11:38:57
	 * @return
	 * @return boolean
	 */
	public boolean isHeaderRow(){
		return isHeaderRow;
	}

	@Override
	public String toString() {
		return "RowPrintData [m_columns=" + m_columns + ", isHeaderRow="
				+ isHeaderRow + ", isSummaryRow=" + isFunctionRow + "]";
	}
}
