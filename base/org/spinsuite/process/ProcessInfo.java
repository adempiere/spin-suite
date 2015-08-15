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
package org.spinsuite.process;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.process.ProcessInfoLog;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class ProcessInfo {
	/** Title of the Process/Report 	*/
	private String						m_Title;
	/** Process ID                  	*/
	private int							m_AD_Process_ID = 0;
	/** Table ID if the Process	    	*/
	private int							m_Table_ID = 0;
	/** Record ID if the Process    	*/
	private int							m_Record_ID = 0;
	/** User_ID        					*/
	private int	 						m_AD_User_ID = 0;
	/** Client_ID        				*/
	private int 						m_AD_Client_ID = 0;
	/**	Report View						*/
	private int 						m_AD_ReportView_ID = 0;
	/**	Print Format					*/
	private int 						m_AD_PrintFormat_ID = 0;
	/** Class Name 						*/
	private String						m_ClassName = null;
	/**	Procedure Name					*/
	private String						m_ProcedureName = null;
	/**	Name							*/
	private String 						m_Value = null;
	/**	Help							*/
	private String 						m_Help = null;
	/**	Description						*/
	private String 						m_Description = null;
	/**	Is a Report						*/
	private boolean 					m_IsReport = false;
	/**	Is Server Process				*/
	private boolean						m_IsServerProcess = false;
	/**	Show Help 						*/
	private String 						m_ShowHelp = null;
	/** Summary of Execution        	*/
	private String    					m_Summary = "";
	/** Execution had an error      	*/
	private boolean     				m_Error = false;
	/**	Window No						*/
	private int 						m_ActivityNo = 0;
	/**	Process Field Parameter			*/
	private InfoPara[] 					m_processFields = null;
	/**	Log Info						*/
	private ArrayList<ProcessInfoLog> 	m_logs = null;
	/**	Log Info						*/
	private ProcessInfoParameter[]		m_parameter = null;
	/**	Context							*/
	private Context						ctx = null;
	/**	Connection						*/
	private DB							conn = null;
	/**	Handle Connection				*/
	private boolean 					m_IsHandleConnection = false;
	/**	Context Value Prefix			*/
	private final String				CTX_VALUE_PREFIX 	= "PI|T|";
	/**	Header Information				*/
	private static final int 			HEADER_INDEX = 11;
	
	/**	Show Help 						*/
	public static final String SHOW_HELP_REF_SHOW_HELP 			= "Y";
	/**	Don't Show Help					*/
	public static final String SHOW_HELP_REF_DONT_SHOW_HELP 	= "N";
	/**	Run Silently - Take Defaults	*/
	public static final String SHOW_HELP_REF_RUN_SILENTLY 		= "S";
	
	/**
	 *  String representation
	 *  @return String representation
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer("ProcessInfo[");
		sb.append(m_Title)
			.append(",Process_ID=").append(m_AD_Process_ID);
		if (m_Record_ID != 0)
			sb.append(",Record_ID=").append(m_Record_ID);
		if (m_ClassName != null)
			sb.append(",ClassName=").append(m_ClassName);
		//	.append(getLogInfo(false));
		sb.append("]");
		return sb.toString();
	}   //  toString

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 19/03/2014, 23:25:19
	 * @param ctx
	 * @param m_AD_Process_ID
	 * @param m_AD_Table_ID
	 * @param m_Record_ID
	 * @param m_WindowNo
	 * @param conn
	 */
	public ProcessInfo (Context ctx, int m_AD_Process_ID, int m_AD_Table_ID, int m_Record_ID, int m_WindowNo, DB conn){
		this.ctx = ctx;
		this.m_AD_Process_ID = m_AD_Process_ID;
		this.m_Table_ID = m_AD_Table_ID;
		this.m_Record_ID = m_Record_ID;
		this.m_ActivityNo = m_WindowNo;
		this.conn = conn;
		//	Load Meta-data
		loadProcess();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 15:16:13
	 * @param ctx
	 * @param m_AD_Process_ID
	 * @param conn
	 */
	public ProcessInfo(Context ctx, int m_AD_Process_ID, DB conn){
		this(ctx, m_AD_Process_ID, 0, 0, 0, conn);
	}
	
	/**
	 * With Activity Parameter
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 21:37:11
	 * @param ctx
	 * @param activityParam
	 * @param conn
	 */
	public ProcessInfo(Context ctx, ActivityParameter activityParam, DB conn){
		this(ctx, activityParam.getAD_Process_ID(), 
				activityParam.getFrom_SPS_Table_ID(), 
				activityParam.getFrom_Record_ID(), activityParam.getActivityNo(), conn);
	}
	
	/**
	 * Load Process Meta-data
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 15:34:33
	 * @return
	 * @return boolean
	 */
	private boolean loadProcess() {
		String language = Env.getAD_Language();
		boolean isBaseLanguage = Env.isBaseLanguage();
		//	Create Value
		String ctx_info_column_value = CTX_VALUE_PREFIX + language + "|" + m_AD_Process_ID;
		//	
		InfoParaWraper wrapper = (InfoParaWraper) Env.getContextObject(ctx_info_column_value, InfoParaWraper.class);
		//	For get from cache
		if(wrapper != null) {
			setValue(wrapper.Value);
			setTitle(wrapper.Title);
			setDescription(wrapper.Description);
			setHelp(wrapper.Help);
			setClassName(wrapper.ClassName);
			setProcedureName(wrapper.ProcedureName);
			setIsReport(wrapper.IsReport);
			setShowHelp(wrapper.ShowHelp);
			setAD_ReportView_ID(wrapper.AD_ReportView_ID);
			setAD_PrintFormat_ID(wrapper.AD_PrintFormat_ID);
			setIsServerProcess(wrapper.IsServerProcess);
			//	Fields
			m_processFields = wrapper.ProcessFields;
			//	Return
			return true;
		}
		//	
		boolean loaded = false;
		//	Get Connection
		if(conn == null){
			conn = new DB(ctx);
			setIsHandleConnection(true);
		} else 
			setIsHandleConnection(false);
		//	Load Connection
		DB.loadConnection(conn, DB.READ_ONLY);
		//	Open Database
		conn.openDB(DB.READ_ONLY);
		//	SQL
		StringBuffer sql = new StringBuffer();
		//	if Base Language
		if(isBaseLanguage){
			sql.append("SELECT p.Value, p.Name, " + 
					"p.Description, p.Help, " +
					"p.ClassName, p.ProcedureName, p.isReport, p.ShowHelp, p.AD_ReportView_ID, p.AD_PrintFormat_ID, p.IsServerProcess, " +
					"pp.AD_Process_Para_ID, pp.AD_Element_ID, pp.AD_Reference_ID, " +
					"pp.AD_Reference_Value_ID, pp.AD_Val_Rule_ID, " +
					"pp.ColumnName, pp.Name, pp.Description, " + 
					"pp.Help, pp.DefaultValue, pp.DefaultValue2, pp.EntityType, " +
					"pp.FieldLength, pp.SeqNo, pp.IsCentrallyMaintained, pp.IsActive, " +
					"pp.IsMandatory, pp.IsRange, pp.ValueMax, pp.ValueMin, pp.VFormat " +
					"FROM AD_Process p " +
					"LEFT JOIN AD_Process_Para pp ON(pp.AD_Process_ID = p.AD_Process_ID) ");
		} else {
			sql.append("SELECT p.Value, COALESCE(pt.Name, p.Name) Name, " + 
					"COALESCE(pt.Description, p.Description) Description, COALESCE(pt.Help, p.Help) Help, " +
					"p.ClassName, p.ProcedureName, p.isReport, p.ShowHelp, p.AD_ReportView_ID, p.AD_PrintFormat_ID, p.IsServerProcess, " +
					"pp.AD_Process_Para_ID, pp.AD_Element_ID, pp.AD_Reference_ID, " +
					"pp.AD_Reference_Value_ID, pp.AD_Val_Rule_ID, " +
					"pp.ColumnName, COALESCE(ppt.Name, pp.Name) Name, COALESCE(ppt.Description, pp.Description) Description, " + 
					"COALESCE(ppt.Help, pp.Help) Help, pp.DefaultValue, pp.DefaultValue2, pp.EntityType, " +
					"pp.FieldLength, pp.SeqNo, pp.IsCentrallyMaintained, pp.IsActive, " +
					"pp.IsMandatory, pp.IsRange, pp.ValueMax, pp.ValueMin, pp.VFormat " +
					"FROM AD_Process p ");
			//	Translation		
			sql.append("LEFT JOIN AD_Process_Trl pt ON(pt.AD_Process_ID = p.AD_Process_ID AND pt.AD_Language = '")
					.append(language).append("') ");
			//	Process Parameter
			sql.append("LEFT JOIN AD_Process_Para pp ON(pp.AD_Process_ID = p.AD_Process_ID) ");
			//	Process Parameter Translation
			sql.append("LEFT JOIN AD_Process_Para_Trl ppt ON(ppt.AD_Process_Para_ID = pp.AD_Process_Para_ID AND ppt.AD_Language = '")
					.append(language).append("') ");
		}
		//	Where Clause
		sql.append("WHERE p.AD_Process_ID = ").append(m_AD_Process_ID)
			.append(" AND ").append("(pp.IsActive = 'Y' OR pp.IsActive IS NULL)").append(" ");
		//	Order By
		sql.append("ORDER BY pp.SeqNo");
		//	
		LogM.log(ctx, getClass(), Level.FINE, "SQL=" + sql);
		Cursor rs = conn.querySQL(sql.toString(), null);
		//	Parameter Meta-data
		ArrayList<InfoPara> fields = new ArrayList<InfoPara>();
		if(rs.moveToFirst()){
			int i = 0;
			//	New Process Info
			setValue(rs.getString(i++));
			setTitle(rs.getString(i++));
			setDescription(rs.getString(i++));
			setHelp(rs.getString(i++));
			setClassName(rs.getString(i++));
			setProcedureName(rs.getString(i++));
			setIsReport(DisplayType.getBooleanValue(rs.getString(i++)));
			setShowHelp(rs.getString(i++));
			setAD_ReportView_ID(rs.getInt(i++));
			setAD_PrintFormat_ID(rs.getInt(i++));
			setIsServerProcess(DisplayType.getBooleanValue(rs.getString(i++)));
			//	Loop for Parameters
			do {
				//	New Field
				InfoPara iFieldPara = new InfoPara();
				iFieldPara.AD_Process_Para_ID = rs.getInt(i++);
				iFieldPara.AD_Element_ID = rs.getInt(i++);
				iFieldPara.DisplayType = rs.getInt(i++);
				iFieldPara.AD_Reference_Value_ID = rs.getInt(i++);
				iFieldPara.AD_Val_Rule_ID = rs.getInt(i++);
				iFieldPara.ColumnName = rs.getString(i++);
				iFieldPara.Name = rs.getString(i++);
				iFieldPara.Description = rs.getString(i++);
				iFieldPara.Help = rs.getString(i++);
				iFieldPara.DefaultValue = rs.getString(i++);
				iFieldPara.DefaultValue2 = rs.getString(i++);
				iFieldPara.EntityType = rs.getString(i++);
				iFieldPara.FieldLength = rs.getInt(i++);
				iFieldPara.SeqNo = rs.getInt(i++);
				iFieldPara.IsCentrallyMaintained = DisplayType.getBooleanValue(rs.getString(i++));
				iFieldPara.IsActive = DisplayType.getBooleanValue(rs.getString(i++));
				iFieldPara.IsMandatory = DisplayType.getBooleanValue(rs.getString(i++));
				iFieldPara.IsRange = DisplayType.getBooleanValue(rs.getString(i++));
				iFieldPara.ValueMax = rs.getString(i++);
				iFieldPara.ValueMin = rs.getString(i++);
				iFieldPara.VFormat = rs.getString(i++);	
				//	Add to Array
				fields.add(iFieldPara);
				i = HEADER_INDEX;
			} while(rs.moveToNext());
			//	Set Fields
			m_processFields = new InfoPara[fields.size()];
			//	Populate Array
			fields.toArray(m_processFields);
			//	Is Loaded
			loaded = true;
			//	Save to Cache
			wrapper = new InfoParaWraper();
			wrapper.Value 				= getValue();
			wrapper.Title 				= getTitle();
			wrapper.Description 		= getDescription();
			wrapper.Help 				= getHelp();
			wrapper.ClassName 			= getClassName();
			wrapper.ProcedureName 		= getProcedureName();
			wrapper.IsReport 			= isReport();
			wrapper.ShowHelp 			= getShowHelp();
			wrapper.AD_ReportView_ID 	= getAD_ReportView_ID();
			wrapper.AD_PrintFormat_ID 	= getAD_PrintFormat_ID();
			wrapper.IsServerProcess 	= isServerProcess();
			//	Params
			wrapper.ProcessFields 		= m_processFields;
			//	Save
			Env.setContextObject(ctx_info_column_value, wrapper);
		}
		//	Close Connection
		if(isHandleConnection())
			DB.closeConnection(conn);
		return loaded;
	}
	
	/**************************************************************************
	 * 	Set Summary
	 * 	@param summary summary (will be translated)
	 */
	public void setSummary (String summary){
		m_Summary = summary;
	}	//	setSummary
	/**
	 * Method getSummary
	 * @return String
	 */
	public String getSummary (){
		return m_Summary;
	}	//	getSummary

	/**
	 * Method setSummary
	 * @param translatedSummary String
	 * @param error boolean
	 */
	public void setSummary (String translatedSummary, boolean error){
		setSummary (translatedSummary);
		setError(error);
	}	//	setSummary
	/**
	 * Method addSummary
	 * @param additionalSummary String
	 */
	public void addSummary (String additionalSummary){
		m_Summary += additionalSummary;
	}	//	addSummary

	/**
	 * Method setError
	 * @param error boolean
	 */
	public void setError (boolean error){
		m_Error = error;
	}	//	setError
	/**
	 * Method isError
	 * @return boolean
	 */
	public boolean isError (){
		return m_Error;
	}	//	isError
	
	/**
	 * Get Report View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:44:05
	 * @return
	 * @return int
	 */
	public int getAD_ReportView_ID(){
		return m_AD_ReportView_ID;
	}
	
	/**
	 * Get Report View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:44:24
	 * @param AD_ReportView_ID
	 * @return void
	 */
	public void setAD_ReportView_ID(int AD_ReportView_ID){
		m_AD_ReportView_ID = AD_ReportView_ID;
	}
	
	/**
	 * Get AD  Print Format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 09:54:14
	 * @return
	 * @return int
	 */
	public int getAD_PrintFormat_ID(){
		return m_AD_PrintFormat_ID;
	}
	
	/**
	 * Set Print Format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 09:53:46
	 * @param AD_PrintFormat_ID
	 * @return void
	 */
	public void setAD_PrintFormat_ID(int AD_PrintFormat_ID){
		m_AD_PrintFormat_ID = AD_PrintFormat_ID;
	}

	/**
	 * Method getAD_Process_ID
	 * @return int
	 */
	public int getAD_Process_ID(){
		return m_AD_Process_ID;
	}
	/**
	 * Method setAD_Process_ID
	 * @param AD_Process_ID int
	 */
	public void setAD_Process_ID(int AD_Process_ID){
		m_AD_Process_ID = AD_Process_ID;
	}

	/**
	 * Method getClassName
	 * @return String or null
	 */
	public String getClassName(){
		return m_ClassName;
	}
	
	/**
	 * Method setClassName
	 * @param ClassName String
	 */
	public void setClassName(String ClassName){
		m_ClassName = ClassName;
		if (m_ClassName != null && m_ClassName.length() == 0)
			m_ClassName = null;
	}	//	setClassName
	
	/**
	 * Method getTable_ID
	 * @return int
	 */
	public int getTable_ID(){
		return m_Table_ID;
	}
	/**
	 * Method setTable_ID
	 * @param AD_Table_ID int
	 */
	public void setTable_ID(int AD_Table_ID){
		m_Table_ID = AD_Table_ID;
	}

	/**
	 * Method getRecord_ID
	 * @return int
	 */
	public int getRecord_ID(){
		return m_Record_ID;
	}
	/**
	 * Method setRecord_ID
	 * @param Record_ID int
	 */
	public void setRecord_ID(int Record_ID){
		m_Record_ID = Record_ID;
	}

	/**
	 * Method getTitle
	 * @return String
	 */
	public String getTitle(){
		return m_Title;
	}
	/**
	 * Method setTitle
	 * @param Title String
	 */
	public void setTitle (String Title){
		m_Title = Title;
	}	//	setTitle


	/**
	 * Method setAD_Client_ID
	 * @param AD_Client_ID int
	 */
	public void setAD_Client_ID (int AD_Client_ID){
		m_AD_Client_ID = AD_Client_ID;
	}
	/**
	 * Method getAD_Client_ID
	 * @return Integer
	 */
	public Integer getAD_Client_ID(){
		return m_AD_Client_ID;
	}

	/**
	 * Method setAD_User_ID
	 * @param AD_User_ID int
	 */
	public void setAD_User_ID (int AD_User_ID){
		m_AD_User_ID = AD_User_ID;
	}
	/**
	 * Method getAD_User_ID
	 * @return Integer
	 */
	public Integer getAD_User_ID(){
		return m_AD_User_ID;
	}
	
	/**
	 * Get Procedure Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:45:09
	 * @return
	 * @return String
	 */
	public String getProcedureName(){
		return m_ProcedureName;
	}
	
	/**
	 * Set Procedure Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:45:57
	 * @param ProcedureName
	 * @return void
	 */
	public void setProcedureName(String ProcedureName){
		m_ProcedureName = ProcedureName;
	}
	
	/**
	 * Get Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:46:22
	 * @return
	 * @return String
	 */
	public String getValue(){
		return m_Value;
	}
	
	/**
	 * Set Process Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:46:56
	 * @param Value
	 * @return void
	 */
	public void setValue(String Value){
		m_Value = Value;
	}
	
	/**
	 * Get Description
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:47:26
	 * @return
	 * @return String
	 */
	public String getDescription(){
		return m_Description;
	}
	
	/**
	 * Set Description
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:48:15
	 * @param Description
	 * @return void
	 */
	public void setDescription(String Description){
		m_Description = Description;
	}
	
	/**
	 * Is Report
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 14:48:55
	 * @return
	 * @return boolean
	 */
	public boolean isReport(){
		return m_IsReport;
	}

	/**
	 * Set Is Report
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 15:06:18
	 * @param isReport
	 * @return void
	 */
	public void setIsReport(boolean isReport){
		this.m_IsReport = isReport;
	}
	
	/**
	 * Get Show Help
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 17:53:09
	 * @return
	 * @return String
	 */
	public String getShowHelp(){
		return m_ShowHelp;
	}
	
	/**
	 * Set Help
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 17:56:19
	 * @param help
	 * @return void
	 */
	public void setHelp(String help){
		this.m_Help = help;
	}
	
	/**
	 * Get Help
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 17:56:39
	 * @return
	 * @return String
	 */
	public String getHelp(){
		return m_Help;
	}
	
	/**
	 * Set Show Help
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 17:53:53
	 * @param showHelp
	 * @return void
	 */
	public void setShowHelp(String showHelp){
		this.m_ShowHelp = showHelp;
	}
	
	/**
	 * Set Is Server Process
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 21/03/2014, 14:48:28
	 * @param m_IsServerProcess
	 * @return void
	 */
	public void setIsServerProcess(boolean m_IsServerProcess){
		this.m_IsServerProcess = m_IsServerProcess;
	}
	
	/**
	 * Is Server Process
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 21/03/2014, 14:48:54
	 * @return
	 * @return boolean
	 */
	public boolean isServerProcess(){
		return m_IsServerProcess;
	}
	
	/**************************************************************************
	 * 	Get Parameter
	 *	@return Parameter Array
	 */
	public ProcessInfoParameter[] getParameter() {
		return m_parameter;
	}	//	getParameter
	
	/**
	 * 	Set Parameter
	 *	@param parameter Parameter Array
	 */
	public void setParameter (ProcessInfoParameter[] parameter)	{
		m_parameter = parameter;
	}	//	setParameter
	
	/**
	 * Verify if has parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 18:16:36
	 * @return
	 * @return boolean
	 */
	public boolean hasParameter() {
		if(m_parameter == null
				|| m_parameter.length == 0)
			return false;
		//	Default Return
		return true;
	}
	
	/**
	 * Return Process Field Parameters for Grid
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 21:32:40
	 * @return
	 * @return VOInfoPara[]
	 */
	public InfoPara[] getProcessInfoPara(){
		return m_processFields;
	}
	
	/**
	 * Set Window No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 21:20:22
	 * @param m_ActivityNo
	 * @return void
	 */
	public void setActivityNo(int m_ActivityNo){
		this.m_ActivityNo = m_ActivityNo;
	}
	
	/**
	 * Get Window No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 21:21:15
	 * @return
	 * @return int
	 */
	public int getActivityNo() {
		return m_ActivityNo;
	}
	
	/**
	 * Get Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 21:33:51
	 * @return
	 * @return Context
	 */
	public Context getCtx(){
		return ctx;
	}
	
	/**
	 * Get Connection and Current Transaction
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 21:34:11
	 * @return
	 * @return DB
	 */
	public DB getConnection(){
		return conn;
	}
	
	/**
	 * Set Is HandleConnection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 21/03/2014, 15:56:43
	 * @param m_IsHandleConnection
	 * @return void
	 */
	public void setIsHandleConnection(boolean m_IsHandleConnection) {
		this.m_IsHandleConnection = m_IsHandleConnection;
	}
	
	/**
	 * Is Handle Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 21/03/2014, 15:57:14
	 * @return
	 * @return boolean
	 */
	public boolean isHandleConnection() {
		return m_IsHandleConnection;
	}
	
	/**************************************************************************
	 * 	Add to Log
	 *	@param Log_ID Log ID
	 *	@param P_ID Process ID
	 *	@param P_Date Process Date
	 *	@param P_Number Process Number
	 *	@param P_Msg Process Message
	 */
	public void addLog (int Log_ID, int P_ID, Date P_Date, BigDecimal P_Number, String P_Msg){
		addLog (new ProcessInfoLog (Log_ID, P_ID, P_Date, P_Number, P_Msg));
	}	//	addLog

	/**
	 * 	Add to Log
	 *	@param P_ID Process ID
	 *	@param P_Date Process Date
	 *	@param P_Number Process Number
	 *	@param P_Msg Process Message
	 */
	public void addLog (int P_ID, Date P_Date, BigDecimal P_Number, String P_Msg){
		addLog (new ProcessInfoLog (P_ID, P_Date, P_Number, P_Msg));
	}	//	addLog
	
	/**
	 * 	Add to Log
	 *	@param logEntry log entry
	 */
	public void addLog (ProcessInfoLog logEntry){
		if (logEntry == null)
			return;
		if (m_logs == null)
			m_logs = new ArrayList<ProcessInfoLog>();
		m_logs.add (logEntry);
	}	//	addLog


	/**
	 * Method getLogs
	 * @return ProcessInfoLog[]
	 */
	public ProcessInfoLog[] getLogs(){
		if (m_logs == null)
			return null;
		ProcessInfoLog[] logs = new ProcessInfoLog[m_logs.size()];
		m_logs.toArray (logs);
		return logs;
	}	//	getLogs
	
	/**
	 * Get Log as ArrayList
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return ArrayList<ProcessInfoLog>
	 */
	public List<ProcessInfoLog> getLogsAsList() {
		return m_logs;
	}
	
	/**
	 * Clear Log Msg and Summary
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/09/2014, 17:48:49
	 * @return void
	 */
	public void clearInfoLog() {
		if(m_logs == null)
			return;
		//	Clear
		m_Summary = "";
		m_logs.clear();
	}
}