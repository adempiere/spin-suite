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
package org.spinsuite.print;

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class InfoReport {
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:07:07
	 * @param ctx
	 * @param AD_PrintFormat_ID
	 * @param conn
	 */
	public InfoReport(Context ctx, int AD_PrintFormat_ID, DB conn){
		loadInfoColumnReport(ctx, AD_PrintFormat_ID, conn);
	}
	
	
	/** Fields	             		*/
	private InfoReportField[]		m_fields = null;
	
	/**	Print Format Color		*/
	private int 		AD_PrintColor_ID = 0;
	/**	Print Font				*/
	private int 		AD_PrintFont_ID = 0;
	/**	Print Format Identifier	*/
	private int 		AD_PrintFormat_ID = 0;
	/**	Print Paper				*/
	private int 		AD_PrintPaper_ID = 0;
	/**	Print Table Format		*/
	private int 		AD_PrintTableFormat_ID = 0;
	/**	Report View 			*/
	private int 		AD_ReportView_ID = 0;
	/**	Footer Margin			*/
	private int 		FooterMargin = 0;
	/**	Header Margin			*/
	private int 		HeaderMargin = 0;
	/**	Is Default				*/
	private boolean		IsDefault = false;
	/**	Is Form					*/
	private boolean		IsForm = false;
	/**	Is Std Header Footer	*/
	private boolean		IsStandardHeaderFooter = false;
	/**	Is Based in Table		*/
	private boolean		IsTableBased = false;
	/**	Name					*/
	private String 		Name = null;
	/**	Print Name				*/
	private String 		PrinterName = null;
	/**	Description				*/
	private String 		Description = null;
	/**	Table					*/
	private int 		SPS_Table_ID = 0;
	/**	Table Name				*/
	private String 		TableName = null;
	/**	Where Clause			*/
	private String 		WhereClause = null;
	/**	Order By Clause		*/
	private String 		OrderByClause = null;
	/**	Index Header			*/
	private static final int 		HEADER_INDEX = 19;
	
	
	/**
	 * Get Print Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:33:01
	 * @return
	 * @return String
	 */
	public String getPrinterName() {
		return PrinterName;
	}
	
	/**
	 * Is Table Based
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:32:31
	 * @return
	 * @return boolean
	 */
	public boolean IsTableBased() {
		return  IsTableBased;
	}
	
	/**
	 * Is Standard Header Footer
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:32:04
	 * @return
	 * @return boolean
	 */
	public boolean IsStandardHeaderFooter() {
		return IsStandardHeaderFooter;
	}
	
	/**
	 * Is Form
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:31:44
	 * @return
	 * @return boolean
	 */
	public boolean IsForm() {
		return IsForm;
	}
	
	/**
	 * Is Default Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:30:14
	 * @return
	 * @return boolean
	 */
	public boolean IsDefault() {
		return IsDefault;
	}
	
	/**
	 * Get Header Margin
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:29:30
	 * @return
	 * @return int
	 */
	public int getHeaderMargin() {
		return HeaderMargin;
	}
	
	/**
	 * Get Footer Margin
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:28:03
	 * @return
	 * @return int
	 */
	public int getFooterMargin() {
		return FooterMargin;
	}
	
	/**
	 * Get Print Table Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:26:52
	 * @return
	 * @return int
	 */
	public int getAD_PrintTableFormat_ID() {
		return AD_PrintTableFormat_ID;
	}
	
	/**
	 * Get Print Paper
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:25:03
	 * @return
	 * @return int
	 */
	public int getAD_PrintPaper_ID() {
		return AD_PrintPaper_ID;
	}
	
	/**
	 * Get Print Color
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:24:20
	 * @return
	 * @return int
	 */
	public int getAD_PrintColor_ID() {
		return AD_PrintColor_ID;
	}
	
	/**
	 * Get Print Color
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:22:43
	 * @return
	 * @return int
	 */
	public int getAD_PrintFont_ID() {
		return AD_PrintFont_ID;
	}
	
	/**
	 * Get Where Clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:16:35
	 * @return
	 * @return String
	 */
	public String getWhereClause() {
		return WhereClause;
	}
	
	/**
	 * Get Parent Column Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:14:19
	 * @return
	 * @return int
	 */
	public int getAD_ReportView_ID() {
		return AD_ReportView_ID;
	}
	
	/**
	 * Get Order By Clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:13:50
	 * @return
	 * @return String
	 */
	public String getOrderByClause() {
		return OrderByClause;
	}
	
	/**
	 * Get Tab Description
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:10:04
	 * @return
	 * @return String
	 */
	public String getDescription() {
		return Description;
	}
	
	/**
	 * Get Table Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:07:41
	 * @return
	 * @return int
	 */
	public int getSPS_Table_ID(){
		return SPS_Table_ID;
	}
	
	/**
	 * Get Tab Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:06:30
	 * @return
	 * @return String
	 */
	public String getName(){
		return Name;
	}
	
	/**
	 * Get Print Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:21:26
	 * @return
	 * @return int
	 */
	public int getAD_PrintFormat_ID(){
		return AD_PrintFormat_ID;
	}
	
	/**
	 * Set Print Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 15:21:56
	 * @param AD_PrintFormat_ID
	 * @return void
	 */
	public void setAD_PrintFormat_ID(int AD_PrintFormat_ID){
		this.AD_PrintFormat_ID = AD_PrintFormat_ID;
	}
	
	
	/**
	 * Load Info Print Fields
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/03/2014, 23:27:15
	 * @param ctx
	 * @param AD_PrintFormat_ID
	 * @param onlySelection
	 * @param p_Conn
	 * @return void
	 */
	private void loadInfoColumnReport(Context ctx, int AD_PrintFormat_ID, DB p_Conn){
		//	Is Mandatory
		if(AD_PrintFormat_ID == 0)
			return;
		//	
		String language = Env.getAD_Language(ctx);
		boolean isBaseLanguage = Env.isBaseLanguage(ctx);
		StringBuffer sql = new StringBuffer();
		//	if Base Language
		if(isBaseLanguage){
			sql.append("SELECT pc.AD_PrintColor_ID, pt.AD_PrintFont_ID, pf.AD_PrintFormat_ID, pp.AD_PrintPaper_ID, pft.AD_PrintTableFormat_ID, " + 
					"rv.AD_ReportView_ID, pf.FooterMargin, pf.HeaderMargin, pf.IsDefault, pf.IsForm, pf.IsStandardHeaderFooter, pf.IsTableBased, " +
					"pf.Name, pf.PrinterName, pf.Description, t.SPS_Table_ID, t.TableName, rv.WhereClause, rv.OrderByClause, " +
					//	Format Items
					"pfi.AD_PrintColor_ID, pfi.AD_PrintFont_ID, pfi.AD_PrintFormatChild_ID, pfi.AD_PrintFormatItem_ID, pfi.AD_PrintGraph_ID, " +
					"pfi.ArcDiameter, pfi.BarcodeType, pfi.BelowColumn, pfi.FieldAlignmentType, COALESCE(pfi.FormatPattern, c.FormatPattern) FormatPattern, " +
					"pfi.ImageIsAttached, pfi.ImageURL, pfi.IsAveraged, pfi.IsCounted, pfi.IsDeviationCalc, " +
					"pfi.IsFilledRectangle, pfi.IsFixedWidth, pfi.IsGroupBy, pfi.IsHeightOneLine, " +
					"pfi.IsImageField, pfi.IsMaxCalc, pfi.IsMinCalc, pfi.IsNextLine, pfi.IsNextPage, pfi.IsOrderBy, pfi.IsPageBreak, pfi.IsPrinted, " +
					"pfi.IsRelativePosition, pfi.IsRunningTotal, pfi.IsSetNLPosition, pfi.IsSummarized, pfi.IsSuppressNull, pfi.IsSuppressRepeats, " +
					"pfi.IsVarianceCalc, pfi.LineAlignmentType, pfi.LineWidth, pfi.MaxHeight, pfi.MaxWidth, pfi.Name, pfi.PrintAreaType, pfi.PrintFormatType, " +
					"COALESCE(pfi.PrintName, '') PrintName, pfi.PrintNameSuffix, pfi.RunningTotalLines, pfi.ShapeType, pfi.SortNo, pfi.XPosition, pfi.XSpace, pfi.YPosition, pfi.YSpace, pfi.SeqNo, " +
					//	Column
					"c.SPS_Column_ID, c.ColumnName, c.IsMandatory, c.AD_Reference_ID, c.AD_Reference_Value_ID " +
					//	From
					"FROM AD_PrintFormat pf " +
					"INNER JOIN AD_PrintFormatItem pfi ON(pfi.AD_PrintFormat_ID = pf.AD_PrintFormat_ID) " +
					"INNER JOIN SPS_Table t ON(t.SPS_Table_ID = pf.SPS_Table_ID) " +
					"INNER JOIN SPS_Column c ON(c.SPS_Column_ID = pfi.SPS_Column_ID) " +
					"INNER JOIN AD_PrintPaper pp ON(pp.AD_PrintPaper_ID = pf.AD_PrintPaper_ID) " +
					"INNER JOIN AD_PrintColor pc ON(pc.AD_PrintColor_ID = pf.AD_PrintColor_ID) " +
					"INNER JOIN AD_PrintFont pt ON(pt.AD_PrintFont_ID = pf.AD_PrintFont_ID) " +
					"LEFT JOIN AD_ReportView rv ON(rv.AD_ReportView_ID = pf.AD_ReportView_ID) " +
					"LEFT JOIN AD_PrintTableFormat pft ON(pft.AD_PrintTableFormat_ID = pf.AD_PrintTableFormat_ID) ");
		} else {
			sql.append("SELECT pc.AD_PrintColor_ID, pt.AD_PrintFont_ID, pf.AD_PrintFormat_ID, pp.AD_PrintPaper_ID, pft.AD_PrintTableFormat_ID, " +
					"rv.AD_ReportView_ID, pf.FooterMargin, pf.HeaderMargin, pf.IsDefault, pf.IsForm, pf.IsStandardHeaderFooter, pf.IsTableBased, " +
					"pf.Name, pf.PrinterName, pf.Description, t.SPS_Table_ID, t.TableName, rv.WhereClause, rv.OrderByClause, " +
					//	Format Items
					"pfi.AD_PrintColor_ID, pfi.AD_PrintFont_ID, pfi.AD_PrintFormatChild_ID, pfi.AD_PrintFormatItem_ID, pfi.AD_PrintGraph_ID, " +
					"pfi.ArcDiameter, pfi.BarcodeType, pfi.BelowColumn, pfi.FieldAlignmentType, COALESCE(pfi.FormatPattern, c.FormatPattern) FormatPattern, " +
					"pfi.ImageIsAttached, pfi.ImageURL, pfi.IsAveraged, pfi.IsCounted, pfi.IsDeviationCalc, " +
					"pfi.IsFilledRectangle, pfi.IsFixedWidth, pfi.IsGroupBy, pfi.IsHeightOneLine, " +
					"pfi.IsImageField, pfi.IsMaxCalc, pfi.IsMinCalc, pfi.IsNextLine, pfi.IsNextPage, pfi.IsOrderBy, pfi.IsPageBreak, pfi.IsPrinted, " +
					"pfi.IsRelativePosition, pfi.IsRunningTotal, pfi.IsSetNLPosition, pfi.IsSummarized, pfi.IsSuppressNull, pfi.IsSuppressRepeats, " +
					"pfi.IsVarianceCalc, pfi.LineAlignmentType, pfi.LineWidth, pfi.MaxHeight, pfi.MaxWidth, pfi.Name, pfi.PrintAreaType, pfi.PrintFormatType, " +
					"COALESCE(pfit.PrintName, '') PrintName, pfit.PrintNameSuffix, pfi.RunningTotalLines, pfi.ShapeType, pfi.SortNo, pfi.XPosition, pfi.XSpace, pfi.YPosition, pfi.YSpace, pfi.SeqNo, " +
					//	Column
					"c.SPS_Column_ID, c.ColumnName, c.IsMandatory, c.AD_Reference_ID, c.AD_Reference_Value_ID " +
					//	From
					"FROM AD_PrintFormat pf " +
					"INNER JOIN AD_PrintFormatItem pfi ON(pfi.AD_PrintFormat_ID = pf.AD_PrintFormat_ID) " +
					"INNER JOIN AD_PrintFormatItem_Trl pfit ON(pfit.AD_PrintFormatItem_ID = pfi.AD_PrintFormatItem_ID AND pfit.AD_Language = '").append(language).append("') " +
					"INNER JOIN SPS_Table t ON(t.SPS_Table_ID = pf.SPS_Table_ID) " +
					"INNER JOIN SPS_Column c ON(c.SPS_Column_ID = pfi.SPS_Column_ID) " +
					"INNER JOIN AD_PrintPaper pp ON(pp.AD_PrintPaper_ID = pf.AD_PrintPaper_ID) " +
					"INNER JOIN AD_PrintColor pc ON(pc.AD_PrintColor_ID = pf.AD_PrintColor_ID) " +
					"INNER JOIN AD_PrintFont pt ON(pt.AD_PrintFont_ID = pf.AD_PrintFont_ID) " +
					"LEFT JOIN AD_ReportView rv ON(rv.AD_ReportView_ID = pf.AD_ReportView_ID) " +
					"LEFT JOIN AD_PrintTableFormat pft ON(pft.AD_PrintTableFormat_ID = pf.AD_PrintTableFormat_ID) ");
		}
		//	Where
		sql.append("WHERE pf.IsActive = 'Y' ")
					.append("AND pfi.IsActive = 'Y' ")
					.append("AND pf.AD_PrintFormat_ID = ").append(AD_PrintFormat_ID).append(" ");
		//	Order By
		sql.append("ORDER BY pfi.SeqNo");
		
		LogM.log(ctx, getClass(), Level.FINE, "SQL InfoReport SQL:" + sql);
		
		ArrayList<InfoReportField> items = new ArrayList<InfoReportField>();
		DB conn = null;
		if(p_Conn != null)
			conn = p_Conn;
		else
			conn = new DB(ctx);
		if(!conn.isOpen())
			conn.openDB(DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL(sql.toString(), null);
		if(rs.moveToFirst()){
			int i = 0;
			//	
			AD_PrintColor_ID = rs.getInt(i++);
			AD_PrintFont_ID = rs.getInt(i++);
			AD_PrintFormat_ID = rs.getInt(i++);
			AD_PrintPaper_ID = rs.getInt(i++);
			AD_PrintTableFormat_ID = rs.getInt(i++);
			AD_ReportView_ID = rs.getInt(i++);
			FooterMargin = rs.getInt(i++);
			HeaderMargin = rs.getInt(i++);
			String booleanValue = rs.getString(i++);
			IsDefault = (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			IsForm = (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			IsStandardHeaderFooter = (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			IsTableBased = (booleanValue != null && booleanValue.equals("Y"));
			Name = rs.getString(i++);
			PrinterName = rs.getString(i++);
			Description = rs.getString(i++);
			SPS_Table_ID = rs.getInt(i++);
			TableName = rs.getString(i++);
			WhereClause = rs.getString(i++);
			OrderByClause = rs.getString(i++);
			//	
			do{
				InfoReportField iItemField = new InfoReportField();
				iItemField.AD_PrintColor_ID = rs.getInt(i++);
				iItemField.AD_PrintFont_ID = rs.getInt(i++);
				iItemField.AD_PrintFormatChild_ID = rs.getInt(i++);
				iItemField.AD_PrintFormatItem_ID = rs.getInt(i++);
				iItemField.AD_PrintGraph_ID = rs.getInt(i++);
				iItemField.ArcDiameter = rs.getInt(i++);
				iItemField.BarcodeType = rs.getString(i++);
				iItemField.BelowColumn = rs.getInt(i++);
				iItemField.FieldAlignmentType = rs.getString(i++);
				String value = rs.getString(i++);
				iItemField.FormatPattern = value;
				booleanValue = rs.getString(i++);
				iItemField.ImageIsAttached = (booleanValue != null && booleanValue.equals("Y"));
				iItemField.ImageURL = rs.getString(i++);
				booleanValue = rs.getString(i++);
				iItemField.IsAveraged = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsCounted = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsDeviationCalc = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsFilledRectangle = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsFixedWidth = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsGroupBy = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsHeightOneLine = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsImageField = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsMaxCalc = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsMinCalc = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsNextLine = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsNextPage = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsOrderBy = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsPageBreak = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsPrinted = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsRelativePosition = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsRunningTotal = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsSetNLPosition = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsSummarized = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsSuppressNull = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsSuppressRepeats = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iItemField.IsVarianceCalc = (booleanValue != null && booleanValue.equals("Y"));
				iItemField.LineAlignmentType = rs.getString(i++);
				iItemField.LineWidth = rs.getInt(i++);
				iItemField.MaxHeight = rs.getInt(i++);
				iItemField.MaxWidth = rs.getInt(i++);
				iItemField.Name = rs.getString(i++);
				iItemField.PrintAreaType = rs.getString(i++);
				iItemField.PrintFormatType = rs.getString(i++);
				iItemField.PrintName = rs.getString(i++);
				iItemField.PrintNameSuffix = rs.getString(i++);
				iItemField.RunningTotalLines = rs.getInt(i++);
				iItemField.ShapeType = rs.getString(i++);
				iItemField.SortNo = rs.getInt(i++); 
				iItemField.XPosition = rs.getInt(i++);
				iItemField.XSpace = rs.getInt(i++);
				iItemField.YPosition = rs.getInt(i++);
				iItemField.YSpace = rs.getInt(i++);
				iItemField.SeqNo = rs.getInt(i++);
				iItemField.SPS_Column_ID = rs.getInt(i++);
				iItemField.ColumnName = rs.getString(i++);
				booleanValue = rs.getString(i++);
				iItemField.IsMandatory = (booleanValue != null && booleanValue.equals("Y"));
				iItemField.DisplayType = rs.getInt(i++);
				iItemField.AD_Reference_Value_ID = rs.getInt(i++);
				//	Add Field
				items.add(iItemField);
				//	Set index
				i = HEADER_INDEX;
			}while(rs.moveToNext());
		}
		//	Close DB
		if(p_Conn == null)
			conn.closeDB(rs);
		//	Convert Array
		
		m_fields = new InfoReportField[items.size()];
		items.toArray(m_fields);
		Log.d("Size ", "- " + m_fields.length);
	}
	
	/**
	 * Return Field Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 16:25:29
	 * @return
	 * @return InfoReportField[]
	 */
	public InfoReportField[] getInfoReportFields(){
		return m_fields;
	}
	
	/**
	 * Get Table Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 20:47:27
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return TableName;
	}
	
	/**
	 * Get Key Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 12:04:31
	 * @return
	 * @return String
	 */
	public String getTableKeyName(){
		return TableName + "_ID";
	}
	
	/**
	 * Get AD_Element
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:36:22
	 * @param index
	 * @return
	 * @return int
	 */
	public int getAD_Element_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].AD_Element_ID;
	}
	
	/**
	 * Get Display Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:37:35
	 * @param index
	 * @return
	 * @return int
	 */
	public int getDisplayType(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].DisplayType;
	}
	
	/**
	 * Get Reference Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:39:00
	 * @param index
	 * @return
	 * @return int
	 */
	public int getAD_Reference_Value_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].AD_Reference_Value_ID;
	}
	
	/**
	 * Get Validation
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:39:11
	 * @param index
	 * @return
	 * @return int
	 */
	public int AD_Val_Rule_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].AD_Val_Rule_ID;
	}
	
	/**
	 * Get Call Out
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:39:21
	 * @param index
	 * @return
	 * @return String
	 */
	public String getCallout(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].Callout;
	}
	
	/**
	 * Get Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:41:37
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnName(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ColumnName;
	}
	
	/**
	 * Get Column SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:41:56
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnSQL(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ColumnSQL;
	}
	
	public String getDefaultValue(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].DefaultValue;
	}
	
	/**
	 * Get Entity Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:42:05
	 * @param index
	 * @return
	 * @return String
	 */
	public String getEntityType(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].EntityType;
	}
	
	/**
	 * Get Field Lenght
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:42:12
	 * @param index
	 * @return
	 * @return int
	 */
	public int getFieldLength(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].FieldLength;
	}
	
	/**
	 * Get Formatt Pattern
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:45:52
	 * @param index
	 * @return
	 * @return String
	 */
	public String getFormatPattern(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].FormatPattern;
	}
	
	/**
	 * Get Always Updateable
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsAlwaysUpdateable;
	}
	
	/**
	 * Is Centrally Maintained
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:18
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isCentrallyMaintained(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsCentrallyMaintained;
	}
	
	/**
	 * Is Encrypted
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:33
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isEncrypted(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsEncrypted;
	}
	
	/**
	 * Is Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:40
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isIdentifier(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsIdentifier;
	}
	
	/**
	 * Is Selection Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:52:58
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isSelectionColumn(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsSelectionColumn;
	}
	
	/**
	 * Is Key
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:08
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isKey(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsKey;
	}
	
	/**
	 * Is Mandatory
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:14
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsMandatory;
	}
	
	/**
	 * Is Parent Link Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:20
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isParent(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsParent;
	}
	
	/**
	 * Is Updateable
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:34
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isUpdateable(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsUpdateable;
	}
	
	/**
	 * Get Selection Column Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:20
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSelectionSeqNo(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SelectionSeqNo;
	}
	
	/**
	 * Get Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:30
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSeqNo(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SeqNo;
	}
	
	/**
	 * Get SFA Column Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:36
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSPS_Column_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SPS_Column_ID;
	}
	
	/**
	 * Get SFA Table Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:57
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSPS_Table_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SPS_Table_ID;
	}
	
	/**
	 * Get Max Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:15
	 * @param index
	 * @return
	 * @return String
	 */
	public String getValueMax(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ValueMax;
	}
	
	/**
	 * Get Min Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:30
	 * @param index
	 * @return
	 * @return String
	 */
	public String getValueMin(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ValueMin;
	}
	
	/**
	 * Get View Formatt
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:48
	 * @param index
	 * @return
	 * @return String
	 */
	public String getVFormat(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].VFormat;
	}
	
	/**
	 * Get Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:57
	 * @param index
	 * @return
	 * @return String
	 */
	public String getName(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].Name;
	}
	
	/**
	 * Get Description
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:59:31
	 * @param index
	 * @return
	 * @return String
	 */
	public String getDescription(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].Description;
	}
	
	/**
	 * Get Field Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:00:19
	 * @param index
	 * @return
	 * @return int
	 */
	public int getFieldSeqNo(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].FieldSeqNo;
	}
	
	/**
	 * Get Lenght
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:18:38
	 * @return
	 * @return int
	 */
	public int getLength(){
		if(m_fields == null)
			return 0;
		return m_fields.length;
	}
}
