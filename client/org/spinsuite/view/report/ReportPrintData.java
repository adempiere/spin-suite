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
package org.spinsuite.view.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.process.ProcessInfo;
import org.spinsuite.process.ProcessInfoParameter;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class ReportPrintData {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 15:00:00
	 * @param ctx
	 * @param m_pi
	 * @param m_AD_PrintFormat_ID
	 * @param conn
	 */
	public ReportPrintData(Context ctx, ProcessInfo m_pi, int m_AD_PrintFormat_ID, DB conn) {
		this.ctx = ctx;
		this.m_pi = m_pi;
		this.conn = conn;
		//	New Query
		if(m_AD_PrintFormat_ID != 0)
			m_reportQuery = new ReportPrintQuery(ctx, m_AD_PrintFormat_ID, conn);
		//	Get Total Prefix
		m_Value = ctx.getResources().getString(R.string.msg_Total);
		//	Function Name
		instanceFunctionName();
	}
	
	/**	Context							*/
	private Context						ctx = null;
	/**	Process Information				*/
	private ProcessInfo					m_pi = null;
	/**	Connection						*/
	private DB							conn = null;
	/**	Report Data						*/
	private ArrayList<RowPrintData> 	m_data = null;
	/**	Report Print SQL				*/
	private ReportPrintQuery			m_reportQuery = null;
	/**	Current Summary Function  Row	*/
	private PrintDataFunction[]			m_currentSummaryFunctionRow = null;
	/**	Current Function  Row			*/
	private PrintDataFunction[]			m_currentFunctionRow = null;
	/**	Current Row per Changed			*/
	private ColumnPrintData[]			m_currentRow = null;
	/**	Columns							*/
	private InfoReportField[] 			m_columns = null;
	/**	Is Aggregate Function			*/
	private boolean 					m_IsAggregateFunction = false;
	/**	Is First Record					*/
	private boolean 					m_IsFirst = true;
	/**	Is Loaded						*/
	private boolean 					m_IsLoaded = true;
	/**	First Group						*/
	private int 						m_FirstGroup = 0;
	/**	First Value						*/
	private String 						m_FirstValue = null;
	/**	Total Prefix					*/
	private String 						m_Value = "";
	/** Symbols							*/
	private String[]					FUNCTION_NAME = null;
	
	/**
	 * Instance Function Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/04/2014, 10:20:01
	 * @return void
	 */
	private void instanceFunctionName(){
		FUNCTION_NAME = new String[]
				{
				//	Sum
				" (" + ctx.getString(R.string.msg_FunctionSum) + ")",
				//	Mean
				" (" + ctx.getString(R.string.msg_FunctionMean) + ")",
				//	Count
				" (" + ctx.getString(R.string.msg_FunctionCount) + ")",
				//	Minimum
				" (" + ctx.getString(R.string.msg_FunctionMinimum) + ")",
				//	Maximum
				" (" + ctx.getString(R.string.msg_FunctionMaximum) + ")", 
				//	Variance
				" (" + ctx.getString(R.string.msg_FunctionVariance) + ")", 
				//	Standard Deviation
				" (" + ctx.getString(R.string.msg_FunctionDeviation) + ")"
				};
	}
	
	/**
	 * Load Data from Print Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 15:00:04
	 * @param m_AD_PrintFormat_ID
	 * @return
	 * @return ArrayList<RowPrintData>
	 */
	public ArrayList<RowPrintData> loadData(int m_AD_PrintFormat_ID){
		//	Load new Print Format
		if(m_AD_PrintFormat_ID != 0) {
			m_reportQuery = new ReportPrintQuery(ctx, m_AD_PrintFormat_ID, conn);
		}
		//	Valid Query
		if(m_reportQuery == null){
			//	Get Default Print Format
			int defaultPrintFormat_ID = getDefaultPritFormat_ID();
			//	
			if(defaultPrintFormat_ID != -1){
				m_AD_PrintFormat_ID  = defaultPrintFormat_ID;
				m_reportQuery = new ReportPrintQuery(ctx, m_AD_PrintFormat_ID, conn);
			} else
				throw new InvalidParameterException("Print Format not Found");
		}
		//	Set to default
		m_IsLoaded = false;
		m_IsFirst = true;
		//	Load Data Query
		try {
			loadResult();
		} catch(Exception e){
			m_pi.setSummary(ctx.getResources().getString(R.string.msg_Error) + ":" + e.getMessage(), true);
			LogM.log(ctx, getClass(), Level.SEVERE, "Error In Load Report:", e);
		}
		//	Result
		return m_data;
	}
	
	/**
	 * Instance Data Function
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 11:41:41
	 * @return void
	 */
	private void instanceDataFunction(){
		// 
		m_currentSummaryFunctionRow = new PrintDataFunction[m_columns.length];
		m_currentFunctionRow = new PrintDataFunction[m_columns.length];
		//	Current Row
		m_currentRow = new ColumnPrintData[m_columns.length];
		//	
		for(int i = 0; i < m_columns.length; i++){
			m_currentRow[i] = new ColumnPrintData(m_columns[i].PrintName, m_columns[i].PrintNameSuffix);
			m_currentSummaryFunctionRow[i] = new PrintDataFunction();
			m_currentFunctionRow[i] = new PrintDataFunction();
			//	Set Aggregate Function
			if(m_columns[i].IsSummarized
					|| m_columns[i].IsCounted
					|| m_columns[i].IsMaxCalc
					|| m_columns[i].IsMinCalc
					|| m_columns[i].IsAveraged
					|| m_columns[i].IsVarianceCalc
					|| m_columns[i].IsDeviationCalc)
				m_IsAggregateFunction = true;
		}
		//	
		loadFirstGroup();
	}
	
	/**
	 * Reset Current Data Function
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 12:22:40
	 * @param columns
	 * @return void
	 */
	private void resetDataFunction(){
		for(int i = 0; i < m_columns.length; i++){
			m_currentFunctionRow[i].reset();
			//	Set Aggregate Function
			if(m_columns[i].IsSummarized
					|| m_columns[i].IsCounted
					|| m_columns[i].IsMaxCalc
					|| m_columns[i].IsMinCalc
					|| m_columns[i].IsAveraged
					|| m_columns[i].IsVarianceCalc
					|| m_columns[i].IsDeviationCalc)
				m_IsAggregateFunction = true;
		}
	}
	
	/**
	 * Load Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 15:58:47
	 * @return
	 * @return ArrayList<RowPrintData>
	 */
	public ArrayList<RowPrintData> loadData() {
		return loadData(0);
	}
	
	/**
	 * Get Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 20:55:42
	 * @return
	 * @return ArrayList<RowPrintData>
	 */
	public ArrayList<RowPrintData> getData(){
		return m_data;
	}
	
	/**
	 * Load Criteria filter from Parameters
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 16:42:11
	 * @param tableName
	 * @return void
	 */
	private FilterValue loadCriteria(String tableName){
		ProcessInfoParameter[] param = m_pi.getParameter();
		//	Get Parameter
		if(param != null){
			StringBuffer sqlWhere = new StringBuffer();
			FilterValue m_criteria = new FilterValue();
			//	Iterate
			for(ProcessInfoParameter para : param){
				//	Get SQL Name
				String name = tableName + "." + para.getParameterName();
				//	
				if (para.getParameter() == null
						&& para.getParameter_To() == null)
					continue;
				else {
					//	Add And
					if(sqlWhere.length() > 0)
		    			sqlWhere.append(" AND ");
					//	
					//	From and To is filled
					if(para.getParameter() != null
							&& para.getParameter_To() != null){
						//	
						sqlWhere.append(name).append(" >= ?")
								.append(" AND ")
								.append(name).append(" <= ?");
			    		//	Add Value
						m_criteria.addValue(DisplayType.getJDBC_Value(
										para.getDisplayType(), 
										para.getParameter()));
						//	Add To
						m_criteria.addValue(DisplayType.getJDBC_Value(
								para.getDisplayType(), 
								para.getParameter_To()));
						//	Only From
					} else if(para.getParameter() != null){
						//	
						sqlWhere.append(name).append(" = ?");
			    		//	Add Value
						m_criteria.addValue(DisplayType.getJDBC_Value(
										para.getDisplayType(), 
										para.getParameter()));
						//	Only To
					} else if(para.getParameter_To() != null){
						//	
						sqlWhere.append(name).append(" <= ?");
			    		//	Add Value
						m_criteria.addValue(DisplayType.getJDBC_Value(
										para.getDisplayType(), 
										para.getParameter_To()));
					}
				}
			}
			//	Add Where
			m_criteria.setWhereClause(sqlWhere.toString());
			//	Return Criteria
			return m_criteria;
		}
		//	Return
		return null;
	}
	
	/**
	 * Load Result from Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 16:19:07
	 * @return void
	 */
	private void loadResult(){
		boolean handleConnection = false;
		//	New Connection
		if(conn == null){
			conn = new DB(ctx);
			handleConnection = true;
		}
		//	Open
		if(!conn.isOpen())
			conn.openDB(DB.READ_ONLY);
		//	Add Criteria if exist
		FilterValue m_criteria = loadCriteria(m_reportQuery.getTableName());
		//	Where
		String where = null;
		//	Values
		String[] values = null;
		//	
		if(m_criteria != null){
			where = m_criteria.getWhereClause();
			values = m_criteria.getValues();
		}
		//	
		String sql = m_reportQuery.getSQL(where);
		//	Get ResultSet
		Cursor rs = null;
		rs = conn.querySQL(sql, values);
		//	New Data
		m_data = new ArrayList<RowPrintData>();
		//	
		if(rs.moveToFirst()){
			//	Columns
			m_columns = m_reportQuery.getColumns();
			//	
			instanceDataFunction();
			m_IsLoaded = true;
			//	Add Header
			do {
				//	Add Row
				m_data.add(getRowData(rs));
				//	Not Is First
				m_IsFirst = false;
			}while(rs.moveToNext());
			//	Add Summary Function
			addFooterFunctionRow();
		}
		//	Close Connection
		if(handleConnection)
			conn.closeDB(rs);
	}	
	
	/**
	 * Get Row Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 21:35:57
	 * @param rs
	 * @return
	 * @return RowPrintData
	 */
	private RowPrintData getRowData(Cursor rs){
		//	New Row
		RowPrintData rPrintData = new RowPrintData();
		//	Get Values
		for (int i = 0; i < m_columns.length; i++) {
			//	Current Column
			InfoReportField column = m_columns[i];
			String value = rs.getString(i);
			//	Add Data
			if(!isChanged(m_currentRow[i].getValue(), value)
					&& column.IsSuppressRepeats)
				rPrintData.addColumn("", column.PrintNameSuffix);
			else
				rPrintData.addColumn(value, column.PrintNameSuffix);
			//	Set Name on first row
			if(m_IsFirst)
				m_currentRow[i].setValue(value);
			//	Add To Function
			m_currentSummaryFunctionRow[i].addRowValue(value, column);
			m_currentFunctionRow[i].addRowValue(value, column);
			if(i == m_FirstGroup)
				m_FirstValue = value;
			//	
			if(m_IsAggregateFunction
					&& !m_IsFirst){
				//	Add Function Row
				if(column.IsGroupBy
						&& isChanged(m_currentRow[i].getValue(), value)){
					//	Add Function
					addRowFunction(i);
					//	Change
					m_currentRow[i].setValue(value);
					//	Reset Current Function
					resetDataFunction();
				}
			}
		}
		//	Return 
		return rPrintData;
	}
	
	/**
	 * Search First Column Group
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/04/2014, 23:27:55
	 * @return void
	 */
	private void loadFirstGroup(){
		for (int i = 0; i < m_columns.length; i++) {
			if(m_columns[i].IsGroupBy){
				m_FirstGroup = i;
				break;
			}
		}
	}
	
	/**
	 * Add Row Function
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 14:31:59
	 * @param indexGroup
	 * @return void
	 */
	private void addRowFunction(int indexGroup){
		//	
		if(!m_IsAggregateFunction
				|| !m_IsLoaded)
				return;
		//	Get Prefix
		String value = m_currentRow[indexGroup].getValue();
		//	
		if(indexGroup == m_FirstGroup){
			//	Load
			//	
			for(int index = m_columns.length - 1; index >= indexGroup; index--){
				//	
				InfoReportField column = m_columns[index];
				//	Get Prefix
				value = m_currentRow[index].getValue();
				//	
				if(!column.IsGroupBy)
					continue;
				//	
				for(int function = 0; function < PrintDataFunction.getSupportedFunctionQty(); function++){
					//	Get Row Function
					RowPrintData functionRow = getRowFunction(value, 
							index, function, m_currentFunctionRow);
					//	Have Function
					if(functionRow != null)
						m_data.add(functionRow);
				}
			}
		} else {
			if(!isChanged(m_currentRow[m_FirstGroup].getValue(), m_FirstValue))
				return;
			for(int function = 0; function < PrintDataFunction.getSupportedFunctionQty(); function++){
				//	Get Row Function
				RowPrintData functionRow = getRowFunction(value, 
						indexGroup, function, m_currentFunctionRow);
				//	Have Function
				if(functionRow != null)
					m_data.add(functionRow);
			}
		}
	}
	
	/**
	 * Add Function to Report
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 14:41:44
	 * @return void
	 */
	private void addFooterFunctionRow(){
		//	
		if(!m_IsAggregateFunction
				|| !m_IsLoaded)
			return;
		//	
		for (int i = m_columns.length - 1; i >= 0; i--) {
			InfoReportField column = m_columns[i];
			//	Only Group By
			if(!column.IsGroupBy)
				continue;
			//	Get Functions
			for(int function = 0; function < PrintDataFunction.getSupportedFunctionQty(); function++){
				//	Get Prefix
				String value = m_Value + " " + column.PrintName 
							+ (column.PrintNameSuffix != null
							? " " + column.PrintNameSuffix
									:"");
				//	Get Row Function
				RowPrintData functionRow = getRowFunction(value, 
						i, function, m_currentSummaryFunctionRow);
				//	Have Function
				if(functionRow != null)
					m_data.add(functionRow);
			}
		}
	}
	
	/**
	 * Get Data Function Row
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 11:15:23
	 * @param columns
	 * @param label
	 * @param indexGroup
	 * @param function
	 * @param m_function
	 * @return
	 * @return RowPrintData
	 */
	private RowPrintData getRowFunction(String label, int indexGroup, 
			int function, PrintDataFunction[] m_function){
		//	
		RowPrintData rPrintFunctionData = new RowPrintData(false, true);
		String functionSymbolLabel = "";
		String functionNameLabel = "";
		//	
		boolean isFunction = false;
		for(int i = 0; i < m_columns.length; i++){
			ColumnPrintData column = new ColumnPrintData(null, null);
			PrintDataFunction currentFuctionColumn = m_function[i];
			InfoReportField printFormatItem = m_columns[i];
			//	
			if(function == PrintDataFunction.F_SUM
					&& printFormatItem.IsSummarized){		//	Sum
				column.setValue(currentFuctionColumn.getSum().toString());
				functionSymbolLabel = PrintDataFunction.getFunctionSymbol(function);
				functionNameLabel = PrintDataFunction.getFunctionName(function, FUNCTION_NAME);
				isFunction = true;
			} else if(function == PrintDataFunction.F_COUNT
					&& printFormatItem.IsCounted){			//	Count
				column.setValue(String.valueOf(currentFuctionColumn.getCount()));
				functionSymbolLabel = PrintDataFunction.getFunctionSymbol(function);
				functionNameLabel = PrintDataFunction.getFunctionName(function, FUNCTION_NAME);
				isFunction = true;
			} else if(function == PrintDataFunction.F_MAX
					&& printFormatItem.IsMaxCalc){			//	Maximum
				column.setValue(currentFuctionColumn.getMax().toString());
				functionSymbolLabel = PrintDataFunction.getFunctionSymbol(function);
				functionNameLabel = PrintDataFunction.getFunctionName(function, FUNCTION_NAME);
				isFunction = true;
			} else if(function == PrintDataFunction.F_MIN
					&& printFormatItem.IsMinCalc){			//	Minimum
				column.setValue(currentFuctionColumn.getMin().toString());
				functionSymbolLabel = PrintDataFunction.getFunctionSymbol(function);
				functionNameLabel = PrintDataFunction.getFunctionName(function, FUNCTION_NAME);
				isFunction = true;
			} else if(function == PrintDataFunction.F_MEAN
					&& printFormatItem.IsAveraged){			//	Average
				column.setValue(currentFuctionColumn.getAvgValue().toString());
				functionSymbolLabel = PrintDataFunction.getFunctionSymbol(function);
				functionNameLabel = PrintDataFunction.getFunctionName(function, FUNCTION_NAME);
				isFunction = true;
			} else if(function == PrintDataFunction.F_VARIANCE
					&& printFormatItem.IsVarianceCalc){		//	Variance
				column.setValue(currentFuctionColumn.getVariance().toString());
				functionSymbolLabel = PrintDataFunction.getFunctionSymbol(function);
				functionNameLabel = PrintDataFunction.getFunctionName(function, FUNCTION_NAME);
				isFunction = true;
			} else if(function == PrintDataFunction.F_DEVIATION
					&& printFormatItem.IsDeviationCalc){	//	Deviation
				column.setValue(currentFuctionColumn.getDeviation().toString());
				functionSymbolLabel = PrintDataFunction.getFunctionSymbol(function);
				functionNameLabel = PrintDataFunction.getFunctionName(function, FUNCTION_NAME);
				isFunction = true;
			}
			//	Add
			rPrintFunctionData.addColumn(column);
		}
		//	Function
		if(isFunction){
			//	Set Value
			if(functionSymbolLabel != null
					&& functionSymbolLabel.length() > 0){
				//	
				ColumnPrintData functionColumnGroup = rPrintFunctionData.get(indexGroup);
				//	Set Value
				functionColumnGroup.setValue(label);
				//	Function Value
				functionColumnGroup.setFunctionValue(label);
				//	
				functionColumnGroup.setValue(label + functionSymbolLabel);
				functionColumnGroup.setFunctionValue(label + functionNameLabel);
			}
			//	Return
			return rPrintFunctionData;
		}
		//	
		return null;
	}
	
	/**
	 * Verify if exists changes
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 16:48:36
	 * @param value1
	 * @param value2
	 * @return
	 * @return boolean
	 */
	private boolean isChanged(String value1, String value2){
		if((value1 == null
				&& value2 != null)
			|| (value1 != null
					&& value2 == null))
			return true;
		//	
		return !value1.equals(value2);
	}
	
	/**
	 * Get a default print format or any if exists
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 14:20:21
	 * @return
	 * @return int
	 */
	private int getDefaultPritFormat_ID(){
		StringBuffer sql = new StringBuffer("SELECT MAX(pf.AD_PrintFormat_ID) " +
				"FROM AD_PrintFormat pf " +
				"WHERE ");
		//	Report View
		if(m_pi.getAD_ReportView_ID() != 0)
			sql.append("pf.AD_ReportView_ID = ")
					.append(m_pi.getAD_ReportView_ID());
		//	Print Format
		else if(m_pi.getAD_PrintFormat_ID() != 0)
			sql.append("pf.AD_PrintFormat_ID = ")
					.append(m_pi.getAD_PrintFormat_ID());
		//	Show Log
		LogM.log(ctx, getClass(), Level.FINE, "SQL=" + sql.toString());
		//	Get Value
		return DB.getSQLValue(ctx, sql.toString());
	}
	
	/**
	 * Get Columns
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 10:32:04
	 * @return
	 * @return InfoReportField[]
	 */
	public InfoReportField[] getColumns(){
		return m_reportQuery.getColumns();
	}
	
	/**
	 * Create a XLS from Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 23:05:54
	 * @return
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @return String
	 */
	public String createXLS() throws IOException, RowsExceededException, WriteException{
		String path = Env.getAppDirName(ctx);
		String title = m_reportQuery.getInfoReport().getName();
		path = path + File.separator + title + ".xls";
		File export = new File(path);
		//	Delete before generate
		if(export.exists())
			export.delete();
		//	Create
		createXLS(export);
		return path;
	}
	
	/**
	 * Create a XLS File
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 15:09:51
	 * @param outFile
	 * @return void
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void createXLS(File outFile) throws IOException, RowsExceededException, WriteException{
		//	
		if(outFile == null)
			return;
		//	Create Sheet
		WritableWorkbook workbook = Workbook.createWorkbook(outFile);
		WritableSheet sheet = workbook.createSheet(m_pi.getTitle(), 0);
		//	
		InfoReportField[] columns = m_reportQuery.getColumns();
		//	Add Header
		for(int col = 0; col < columns.length; col++){
			sheet.addCell(new Label(col, 0, columns[col].PrintName));
		}
		//	Add Detail
		for(int row = 0; row < m_data.size(); row++){
			//	Get Row
			RowPrintData rPrintData = m_data.get(row);
			//	Iterate
			for(int col = 0; col < columns.length; col++){
				InfoReportField column = columns[col];
				ColumnPrintData cPrintData = rPrintData.get(col);
				WritableCell cell = null;
				WritableCellFormat cellFormat = null;
				if(cPrintData.getValue() != null){
					//	
					if(DisplayType.INTEGER == column.DisplayType){				//	Is Integer
						//	Integer
						cellFormat = new WritableCellFormat(NumberFormats.INTEGER);
						//	
						cell = new Number(col, row+1, DisplayType
											.getNumber(cPrintData.getValue()).doubleValue(), cellFormat);
					} else if(DisplayType.isBigDecimal(column.DisplayType)){	//	Is Big Decimal
						//	Float
						cellFormat = new WritableCellFormat(NumberFormats.FLOAT);
						//	
						cell = new Number(col, row+1, DisplayType
											.getNumber(cPrintData.getValue()).doubleValue(), cellFormat);					
					}else if(DisplayType.isText(column.DisplayType)
							|| DisplayType.isLookup(column.DisplayType)){		//	Is String
						cell = new Label(col, row+1, cPrintData.getValue());
					} else if(DisplayType.isDate(column.DisplayType)){			//	Is Date
						String pattern = DisplayType.getDateFormat(ctx, column.DisplayType).toPattern();
						DateFormat customDateFormat = new DateFormat (pattern); 
						WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat); 
						long date = Long.parseLong(cPrintData.getValue());
						cell = new DateTime(col, row+1, new Date(date), dateFormat); 
					}

				}
				//	Add to Sheet
				if(cell != null)
					sheet.addCell(cell);
			}
		}
		//	Close File
		workbook.write(); 
		workbook.close();
	}
	
	/**
	 * Create a PDF File from Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 22:54:14
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @return String
	 */
	public String createPDF() throws FileNotFoundException, DocumentException{
		String path = Env.getAppDirName(ctx);
		String title = m_reportQuery.getInfoReport().getName();
		path = path + File.separator + title + ".pdf";
		File export = new File(path);
		//	Delete before generate
		if(export.exists())
			export.delete();
		//	Create
		createPDF(export);
		return path;
	}
	
	/**
	 * Create a PDF File
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 22:52:09
	 * @param outFile
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @return void
	 */
	private void createPDF(File outFile) throws FileNotFoundException, DocumentException{
		Document document = new Document(PageSize.LETTER);
		
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outFile));
		PDFHeaderAndFooter event = new PDFHeaderAndFooter();
        writer.setPageEvent(event);
		document.open();
		//	
		document.addAuthor(ctx.getResources().getString(R.string.app_name));
		document.addCreationDate();
		//	
		Paragraph title = new Paragraph(m_reportQuery.getInfoReport().getName());
		//	Set Font
		title.getFont().setStyle(Font.BOLD);
		//	Set Alignment
		title.setAlignment(Paragraph.ALIGN_CENTER);
		//	Add Title
		document.add(title);
		//	Add New Line
		document.add(Chunk.NEWLINE);
		//	Parameters
		ProcessInfoParameter[] param = m_pi.getParameter();
		//	Get Parameter
		if(param != null){
			//	
			boolean isFirst = true;
			//	Iterate
			for(ProcessInfoParameter para : param){
				//	Get SQL Name
				String name = para.getInfo();
				StringBuffer textParameter = new StringBuffer();
				if (para.getParameter() == null
						&& para.getParameter_To() == null)
					continue;
				else {
					//	Add Parameters Title
					if(isFirst){
						Paragraph titleParam = new Paragraph(ctx.getResources()
																.getString(R.string.msg_ReportParameters));
						//	Set Font
						titleParam.getFont().setStyle(Font.BOLDITALIC);
						//	Add to Document
						document.add(titleParam);
						isFirst = false;
					}
					//	Add Parameters Name
					if(para.getParameter() != null
							&& para.getParameter_To() != null){	//	From and To is filled
						//	
						textParameter.append(name).append(" => ")
								.append(para.getDisplayValue())
								.append(" <= ")
								.append(para.getDisplayValue_To());
					} else if(para.getParameter() != null){		//	Only From
						//	
						textParameter.append(name).append(" = ")
								.append(para.getDisplayValue());
					} else if(para.getParameter_To() != null){	//	Only To
						//	
						textParameter.append(name).append(" <= ")
								.append(para.getDisplayValue_To());
					}
				}
				//	Add to Document
				Paragraph viewParam = new Paragraph(textParameter.toString());
				document.add(viewParam);
			}
		}
		document.add(Chunk.NEWLINE);
		//	
		InfoReportField[] columns = m_reportQuery.getColumns();
		//	Add Table
		PdfPTable table = new PdfPTable(columns.length);
		table.setSpacingBefore(4);
		//	Add Header
		PdfPCell headerCell = new PdfPCell(new Phrase(m_reportQuery.getInfoReport().getName()));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		headerCell.setColspan(columns.length);
		//	Add to Table
		table.addCell(headerCell);
		//	Add Header
		//	Decimal and Date Format
		DecimalFormat[]	cDecimalFormat = new DecimalFormat[columns.length];
		SimpleDateFormat[] cDateFormat = new SimpleDateFormat[columns.length];
		for(int i = 0; i < columns.length; i ++){
			InfoReportField column = columns[i];
			//	Only Numeric
			if(DisplayType.isNumeric(column.DisplayType))
				cDecimalFormat[i] = DisplayType.getNumberFormat(ctx, column.DisplayType, 
					column.FormatPattern);
			//	Only Date
			else if(DisplayType.isDate(column.DisplayType))
				cDateFormat[i] = DisplayType.getDateFormat(ctx, column.DisplayType, 
						column.FormatPattern);	
			//	
			Phrase phrase = new Phrase(column.PrintName);
			PdfPCell cell = new PdfPCell(phrase);
			if(column.FieldAlignmentType
					.equals(InfoReportField.FIELD_ALIGNMENT_TYPE_TRAILING_RIGHT))
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			else if(column.FieldAlignmentType
					.equals(InfoReportField.FIELD_ALIGNMENT_TYPE_CENTER))
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			else 
				cell.setHorizontalAlignment(PdfPCell.ALIGN_UNDEFINED);
			//	
			table.addCell(cell);
		}
		//	Add Detail
		for(int row = 0; row < m_data.size(); row++){
			//	Get Row
			RowPrintData rPrintData = m_data.get(row);
			//	Iterate
			for(int col = 0; col < columns.length; col++){
				InfoReportField column = columns[col];
				ColumnPrintData cPrintData = rPrintData.get(col);
				Phrase phrase = null;
				PdfPCell cell = new PdfPCell();
				//	
				String value = cPrintData.getValue();
				//	Only Values
				if(value != null){
					if(DisplayType.isNumeric(column.DisplayType)){				//	Number
						//	Format
						DecimalFormat decimalFormat = cDecimalFormat[col];
						//	Format
						if(decimalFormat != null)
							value = decimalFormat.format(DisplayType.getNumber(value));
						//	Set Value
						cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					} else if(DisplayType.isDate(column.DisplayType)){			//	Is Date
						SimpleDateFormat dateFormat = cDateFormat[col];
						if(dateFormat != null){
							long date = Long.parseLong(value);
							value = dateFormat.format(new Date(date));
						}
					}
				}
				//	Set Value
				phrase = new Phrase(value);
				//	
				if(column.FieldAlignmentType
						.equals(InfoReportField.FIELD_ALIGNMENT_TYPE_TRAILING_RIGHT))
					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				else if(column.FieldAlignmentType
						.equals(InfoReportField.FIELD_ALIGNMENT_TYPE_CENTER))
					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				else 
					cell.setHorizontalAlignment(PdfPCell.ALIGN_UNDEFINED);
				//	Set Font
				if(rPrintData.isFunctionRow()){
					//	Set Function Value
					if(cPrintData.getFunctionValue() != null
							&& cPrintData.getFunctionValue().length() > 0)
						phrase = new Phrase(cPrintData.getFunctionValue());
					//	Set Font
					phrase.getFont().setStyle(Font.BOLDITALIC);
				}
				//	Add to Table
				cell.setPhrase(phrase);
				table.addCell(cell);
			}
		}
		//	Add Table to Document
        document.add(table);
        //	New Line
        document.add(Chunk.NEWLINE);
        //	Add Footer
        StringBuffer footerText = new StringBuffer(Env.getContext(ctx, "#SUser"));
        footerText.append("(");
        footerText.append(Env.getContext(ctx, "#AD_Role_Name"));
        footerText.append("@");
        footerText.append(Env.getContext(ctx, "#AD_Client_Name"));
        footerText.append(".");
        footerText.append(Env.getContext(ctx, "#AD_Org_Name"));
        footerText.append("{")
        				.append(Build.MANUFACTURER).append(".")
        				.append(Build.MODEL)
        				.append("}) ");
        //	Date
        SimpleDateFormat pattern = DisplayType.getDateFormat(ctx, DisplayType.DATE_TIME);
        footerText.append(" ")
        	.append(ctx.getResources().getString(R.string.Date))
        	.append(" = ");
        footerText.append(pattern.format(new Date()));
        //	
        Paragraph footer = new Paragraph(footerText.toString());
        footer.setAlignment(Paragraph.ALIGN_CENTER);
        //	Set Font
        footer.getFont().setSize(8);
        //	Add Footer
        document.add(footer);
		//	Close Document
		document.close();
	}
	
	/**
	 * Get Report Information
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 21:44:32
	 * @return
	 * @return InfoReport
	 */
	public InfoReport getInfoReport(){
		return m_reportQuery.getInfoReport();
	}
}
