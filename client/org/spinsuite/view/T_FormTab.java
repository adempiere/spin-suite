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
package org.spinsuite.view;

import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.util.Env;
import org.spinsuite.util.TabParameter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jul 6, 2015, 1:01:57 PM
 *
 */
public class T_FormTab extends Fragment implements I_DynamicTab {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_FormTab() {
		
	}

	/**	Tab Parameters		*/
	private TabParameter	 		m_TabParam				= null;
	/**	Callback Parent		*/
	private	TV_DynamicActivity		m_Callback				= null;
	/**	Is Load View		*/
	private boolean					m_IsLoadOk				= false;
	/**	Is PArent Modify	*/
	private boolean 				m_IsParentModifying		= false;
	/**	Is Processed		*/
//	private boolean 				m_Processed				= false;
	/**	Record Identifier	*/
	private int 					m_Record_ID 			= 0;
	/**	Activity No			*/
	private int 					m_ActivityNo 			= 0;
	/**	Tab No				*/
	private int 					m_TabNo 				= 0;
	/**	Sequence No			*/
	private int 					m_SeqNo 				= 0;
	/**	Tab Level			*/
	private int 					m_TabLevel 				= 0;
	/**	Process ID			*/
	private int 					m_AD_Process_ID 		= 0;
	/**	is Insert Record	*/
	private boolean					m_IsInsertRecord 		= false;
	/**	is Read and Write	*/
	private boolean		 			m_IsReadOnly 			= false;
	/**	Name Item			*/
	private String  				m_Name 					= null;
	/**	Short Description	*/
	private String 					m_Description 			= null;
	/**	Order By			*/
	private String					m_OrderByClause 		= null;
	/**	Tab					*/
	private int 					m_SPS_Tab_ID 			= 0;
	/**	Table				*/
	private int 					m_SPS_Table_ID 			= 0;
	/**	Window				*/
	private int						m_SPS_Window_ID 		= 0;
	/**	Where				*/
	private String					m_WhereClause 			= null;
	/**	Class Name			*/
	private String 					m_Classname 			= null;
	/**	Is Modifying			*/
	private boolean 				m_IsModifying			= false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//	
		Bundle bundle = getArguments();
		if(bundle != null) {
			setTabParameter((TabParameter) bundle.getParcelable("TabParam"));
		}
		//	
		setReadOnly();
	}
	
	/**
	 * Set Read Only Parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void setReadOnly() {
		if(m_TabParam !=null) {
			setIsReadOnly(!Env.getWindowsAccess(m_TabParam.getSPS_Window_ID()) 
					|| m_TabParam.isReadOnly());
			//	Is Insert Record
			setIsInsertRecord(m_TabParam.isInsertRecord());
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		m_Callback = (TV_DynamicActivity) activity;
	}
	
	@Override
	public void handleMenu() {
		
	}
	
	@Override
	public TabParameter getTabParameter() {
		return m_TabParam;
	}
	
	@Override
	public void setTabParameter(TabParameter tabParam) {
		m_TabParam = tabParam;
		setActivityNo(tabParam.getActivityNo());
		setTabNo(tabParam.getTabNo());
		setSeqNo(tabParam.getSeqNo());
		setTabLevel(tabParam.getTabLevel());
		setAD_Process_ID(tabParam.getAD_Process_ID());
		setIsInsertRecord(tabParam.isInsertRecord());
		setIsReadOnly(tabParam.isReadOnly());
		setName(tabParam.getName());
		setDescription(tabParam.getDescription());
		setOrderByClause(tabParam.getOrderByClause());
		setSPS_Tab_ID(tabParam.getSPS_Tab_ID());
		setSPS_Table_ID(tabParam.getSPS_Table_ID());
		setSPS_Window_ID(tabParam.getSPS_Window_ID());
		setWhereClause(tabParam.getWhereClause());
		setClassname(tabParam.getClassname());
		//	
		setReadOnly();
	}
	
	@Override
	public boolean refreshFromChange(boolean reQuery) {
		return false;
	}
	
	@Override
	public boolean save() {
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
	
	@Override
	public boolean isModifying() {
		return m_IsModifying;
	}
	
	/**
	 * Set Is Modifying
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_IsModifying
	 * @return void
	 */
	public void setIsModifying(boolean p_IsModifying) {
		m_IsModifying = p_IsModifying;
	}
	
	@Override
	public void setIsParentModifying(boolean isParentModifying) {
		m_IsParentModifying = isParentModifying;
	}
	
	@Override
	public String getTabSuffix() {
		return null;
	}
	
	/**.
	 * Get Callback
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return TV_DynamicActivity
	 */
	protected TV_DynamicActivity getCallback() {
		return m_Callback;
	}
	
	/**
	 * Is Processed
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	protected boolean isProcessed() {
		boolean processed = false;
		if(m_TabParam != null) {
			processed = Env.getContextAsBoolean(m_Callback, getActivityNo(), "Processed");
		}
		//	Default
		return processed;
	}
	
	/**
	 * Is Parent Modify
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	protected boolean isParentModifying() {
		return m_IsParentModifying;
	}
	
	/**
	 * Get Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	protected int getActivityNo() {
		return m_Callback.getActivityNo();
	}
	
	/**
	 * Verify if is load ok
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	protected boolean isLoadOk() {
		return m_IsLoadOk;
	}
	
	/**
	 * Set if is load ok
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_IsLoadOk
	 * @return void
	 */
	protected void setIsLoadOk(boolean p_IsLoadOk) {
		m_IsLoadOk = p_IsLoadOk;
	}
	
	/**
	 * Set Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/02/2014, 00:08:15
	 * @param m_ActivityNo
	 * @return void
	 */
	private void setActivityNo(int m_ActivityNo){
		this.m_ActivityNo = m_ActivityNo;
	}
	
	/**
	 * Get Parent Tab number
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:51:32
	 * @return
	 * @return int
	 */
	public int getParentTabNo(){
		return m_TabLevel - 1;
	}
	
	/**
	 * Set Tab Number
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:50:53
	 * @param m_TabNo
	 * @return void
	 */
	private void setTabNo(int m_TabNo){
		this.m_TabNo = m_TabNo;
	}
	
	/**
	 * Get Tab Number
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:50:21
	 * @return
	 * @return int
	 */
	public int getTabNo(){
		return this.m_TabNo;
	}
	
	/**
	 * Set Tab Level
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:48:28
	 * @param m_TabLevel
	 * @return void
	 */
	private void setTabLevel(int m_TabLevel){
		this.m_TabLevel = m_TabLevel;
	}
	
	/**
	 * Get TabLevel
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:46:21
	 * @return
	 * @return int
	 */
	public int getTabLevel(){
		return this.m_TabLevel;
	}
	
	/**
	 * Set Sequence
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:45:07
	 * @param m_SeqNo
	 * @return void
	 */
	private void setSeqNo(int m_SeqNo){
		this.m_SeqNo = m_SeqNo;
	}
	
	/**
	 * Get Sequence
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:44:33
	 * @return
	 * @return int
	 */
	public int getSeqNo(){
		return this.m_SeqNo;
	}
	
	/**
	 * Set Class Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:43:05
	 * @param m_Classname
	 * @return void
	 */
	private void setClassname(String m_Classname){
		this.m_Classname = m_Classname;
	}
	
	/**
	 * Get Class Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:42:07
	 * @return
	 * @return String
	 */
	public String getClassname(){
		return this.m_Classname;
	}
	
	/**
	 * Get Tab Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:40:00
	 * @return
	 * @return int
	 */
	public int getSPS_Tab_ID(){
		return this.m_SPS_Tab_ID;
	}
	
	/**
	 * Set Tab Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/02/2014, 23:41:00
	 * @param m_SPS_Tab_ID
	 * @return void
	 */
	private void setSPS_Tab_ID(int m_SPS_Tab_ID){
		this.m_SPS_Tab_ID = m_SPS_Tab_ID;
	}
	
	/**
	 * Get Process Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 11:28:58
	 * @return
	 * @return int
	 */
	public int getAD_Process_ID(){
		return m_AD_Process_ID;
	}
	
	/**
	 * Set PRocess Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 11:29:46
	 * @param m_AD_Process_ID
	 * @return void
	 */
	private void setAD_Process_ID(int m_AD_Process_ID){
		this.m_AD_Process_ID = m_AD_Process_ID;
	}
	
	/**
	 * Get Item Name
	 * @author Yamel Senih 28/04/2012, 00:24:01
	 * @return
	 * @return String
	 */
	public String getName(){
		return m_Name;
	}
	
	/**
	 * Set Item Name 
	 * @author Yamel Senih 01/08/2012, 09:55:37
	 * @param name
	 * @return void
	 */
	private void setName(String name){
		this.m_Name = name;
	}
	
	/**
	 * Set Image Name
	 * @author Yamel Senih 28/04/2012, 00:25:17
	 * @return
	 * @return String
	 */
	public String getDescription(){
		return m_Description;
	}
	
	/**
	 * Set Activity Description
	 * @author Yamel Senih 01/08/2012, 10:04:18
	 * @param description
	 * @return void
	 */
	private void setDescription(String description){
		this.m_Description = description;
	}
	
	/**
	 * Get Table ID
	 * @author Yamel Senih 30/04/2012, 19:25:23
	 * @return
	 * @return int
	 */
	public int getSPS_Table_ID(){
		return m_SPS_Table_ID;
	}
	
	/**
	 * Set Table ID
	 * @author Yamel Senih 01/08/2012, 10:11:49
	 * @param m_AD_Table_ID
	 * @return void
	 */
	private void setSPS_Table_ID(int m_AD_Table_ID){
		this.m_SPS_Table_ID = m_AD_Table_ID;
	}
	
	/**
	 * Is Read Only
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 01:00:31
	 * @return
	 * @return boolean
	 */
	public boolean isReadOnly(){
		return m_IsReadOnly;
	}
	
	/**
	 * Set Read Only
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 01:00:43
	 * @param m_IsReadOnly
	 * @return void
	 */
	private void setIsReadOnly(boolean m_IsReadOnly){
		this.m_IsReadOnly = m_IsReadOnly;
	}
	
	/**
	 * Get Where Clause
	 * @author Yamel Senih 04/05/2012, 17:34:31
	 * @return
	 * @return String
	 */
	public String getWhereClause(){
		return m_WhereClause;
	}
	
	/**
	 * Set Where Clause
	 * @author Yamel Senih 28/05/2012, 08:39:04
	 * @param whereClause
	 * @return void
	 */
	private void setWhereClause(String whereClause){
		this.m_WhereClause = whereClause;
	}
	
	/**
	 * Set Order By clause
	 * @author Yamel Senih 28/05/2012, 08:50:40
	 * @param orderByClause
	 * @return void
	 */
	private void setOrderByClause(String orderByClause){
		this.m_OrderByClause = orderByClause;
	}
	
	/**
	 * Get Order By Clause
	 * @author Yamel Senih 04/05/2012, 22:01:04
	 * @return
	 * @return String
	 */
	public String getOrderByClause(){
		return m_OrderByClause;
	}
	
	/**
	 * Is Insert Record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 01:02:28
	 * @return
	 * @return boolean
	 */
	public boolean isInsertRecord(){
		return m_IsInsertRecord;
	}
	
	/**
	 * Set is Insert Record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 09:40:45
	 * @param m_IsInsertRecord
	 * @return void
	 */
	private void setIsInsertRecord(boolean m_IsInsertRecord){
		this.m_IsInsertRecord = m_IsInsertRecord;
	}
	
	/**
	 * Set Window Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/02/2014, 09:32:39
	 * @param m_SPS_Window_ID
	 * @return void
	 */
	private void setSPS_Window_ID(int m_SPS_Window_ID){
		this.m_SPS_Window_ID = m_SPS_Window_ID;
	}
	
	/**
	 * Get Window Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/02/2014, 09:32:48
	 * @return
	 * @return int
	 */
	public int getSPS_Window_ID(){
		return m_SPS_Window_ID;
	}

	@Override
	public String toString() {
		return "TabParameter [m_Record_ID=" + m_Record_ID + ", m_ActivityNo="
				+ m_ActivityNo + ", m_TabNo=" + m_TabNo + ", m_SeqNo="
				+ m_SeqNo + ", m_TabLevel=" + m_TabLevel + ", m_AD_Process_ID="
				+ m_AD_Process_ID + ", m_IsInsertRecord=" + m_IsInsertRecord
				+ ", m_IsReadOnly=" + m_IsReadOnly + ", m_Name=" + m_Name
				+ ", m_Description=" + m_Description + ", m_OrderByClause="
				+ m_OrderByClause + ", m_SPS_Tab_ID=" + m_SPS_Tab_ID
				+ ", m_SPS_Table_ID=" + m_SPS_Table_ID + ", m_SPS_Window_ID="
				+ m_SPS_Window_ID + ", m_WhereClause=" + m_WhereClause
				+ ", m_Classname=" + m_Classname + "]";
	}
}