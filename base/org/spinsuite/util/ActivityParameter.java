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
package org.spinsuite.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class ActivityParameter implements Parcelable {

	/**	From Record Identifier		*/
	private int 			m_From_Record_ID 	= 0;
	/**	From Table Identifier		*/
	private int 			m_From_SPS_Table_ID = 0;
	/**	Table Identifier			*/
	private int 			m_AD_Table_ID 		= 0;
	/**	Parent Identifier			*/
	private int 			m_Parent_ID 		= 0;
	/**	Action Menu					*/
	private String  		m_Action 			= null;
	/**	Form						*/
	private int 			m_AD_Form_ID 		= 0;
	/**	Process ID					*/
	private int 			m_AD_Process_ID 	= 0;
	/**	Activity Menu				*/
	private int 			m_ActivityMenu_ID 	= 0;
	/**	DEployment Type				*/
	private String			m_DeploymentType 	= "D";
	/**	Short Description			*/
	private String 			m_Description 		= null;
	/**	Order By					*/
	private String			m_GroupByClause 	= null;
	/**	Menu is Insert Record		*/
	private String			m_IsInsertRecord 	= null;
	/**	Is Summary Menu				*/
	private boolean			m_IsSummary 		= false;
	/**	Name Item					*/
	private String  		m_Name 				= null;
	/**	Order By					*/
	private String			m_OrderByClause 	= null;
	/**	Menu ID						*/
	private int				m_SPS_Menu_ID 		= 0;
	/**	Sync Menu					*/
	private int 			m_SPS_SyncMenu_ID 	= 0;
	/**	Table						*/
	private int 			m_SPS_Table_ID 		= 0;
	/**	Window						*/
	private int				m_SPS_Window_ID 	= 0;
	/**	Where						*/
	private String			m_WhereClause 		= null;
	/**	Window Number				*/
	private int 			m_ActivityNo 		= 0;
	/**	Is From Activity			*/
	private boolean 		m_IsFromActivity 	= false;
	/**	Is Sales Order Transaction	*/
	private boolean			m_IsSOTrx			= false;
	
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public ActivityParameter createFromParcel(Parcel parcel) {
			return new ActivityParameter(parcel);
		}
		public ActivityParameter[] newArray(int size) {
			return new ActivityParameter[size];
		}
	};
	
	public ActivityParameter(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 21:54:47
	 */
	public ActivityParameter(){
		//	
	}
	
	/**
	 * Create from Display Menu Item
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:40:26
	 * @param from
	 */
	public ActivityParameter(DisplayMenuItem from){
		createFromMenu(from);
	}
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(m_From_Record_ID);
		dest.writeInt(m_From_SPS_Table_ID);
		dest.writeInt(m_AD_Table_ID);
		dest.writeInt(m_Parent_ID);
		dest.writeString(m_Action);
		dest.writeInt(m_AD_Form_ID);
		dest.writeInt(m_AD_Process_ID);
		dest.writeInt(m_ActivityMenu_ID);
		dest.writeString(m_DeploymentType);
		dest.writeString(m_Description);
		dest.writeString(m_GroupByClause);
		dest.writeString(m_Name);
		dest.writeString(m_OrderByClause);
		dest.writeInt(m_SPS_Menu_ID);
		dest.writeInt(m_SPS_SyncMenu_ID);
		dest.writeInt(m_SPS_Table_ID);
		dest.writeInt(m_SPS_Window_ID);
		dest.writeString(m_WhereClause);
		//	Read Write
		dest.writeString(m_IsInsertRecord);
		dest.writeString((m_IsSummary? "Y": "N"));
		dest.writeInt(m_ActivityNo);
		dest.writeString((m_IsFromActivity? "Y": "N"));
		dest.writeString((m_IsSOTrx? "Y": "N"));
	}
	
	public void readToParcel(Parcel parcel){
		m_From_Record_ID = parcel.readInt();
		m_From_SPS_Table_ID = parcel.readInt();
		m_AD_Table_ID = parcel.readInt();
		m_Parent_ID = parcel.readInt();
		m_Action = parcel.readString();
		m_AD_Form_ID = parcel.readInt();
		m_AD_Process_ID = parcel.readInt();
		m_ActivityMenu_ID = parcel.readInt();
		m_DeploymentType = parcel.readString();
		m_Description = parcel.readString();
		m_GroupByClause = parcel.readString();
		m_Name = parcel.readString();
		m_OrderByClause = parcel.readString();
		m_SPS_Menu_ID = parcel.readInt();
		m_SPS_SyncMenu_ID = parcel.readInt();
		m_SPS_Table_ID = parcel.readInt();
		m_SPS_Window_ID = parcel.readInt();
		m_WhereClause = parcel.readString();
		//	Read Write
		m_IsInsertRecord = parcel.readString();
		String booleanValue = parcel.readString();
		m_IsSummary = (booleanValue != null 
								&& booleanValue.equals("Y"));
		m_ActivityNo = parcel.readInt();
		booleanValue = parcel.readString();
		m_IsFromActivity = (booleanValue != null 
								&& booleanValue.equals("Y"));
		booleanValue = parcel.readString();
		m_IsSOTrx = (booleanValue != null 
								&& booleanValue.equals("Y"));
	}
	
	/**
	 * Create Menu from other
	 * @author Yamel Senih 01/08/2012, 12:10:41
	 * @param fromMenu
	 * @return
	 */
	public void createFromMenu(DisplayMenuItem from){
		if(from == null)
			return;
		//	Set Values
		setSPS_Table_ID(from.getSPS_Table_ID());
		setParent_ID(from.getParent_ID());
		setAction(from.getAction());
		setAD_Form_ID(from.getAD_Form_ID());
		setAD_Process_ID(from.getAD_Process_ID());
		setActivityMenu_ID(from.getActivityMenu_ID());
		setDeploymentType(from.getDeploymentType());
		setDescription(from.getDescription());
		setGroupByClause(from.getGroupByClause());
		setName(from.getName());
		setOrderByClause(from.getOrderByClause());
		setSPS_Menu_ID(from.getSPS_Menu_ID());
		setSPS_Table_ID(from.getSPS_Table_ID());
		setSPS_Window_ID(from.getSPS_Window_ID());
		setWhereClause(from.getWhereClause());
		setParent_ID(from.getParent_ID());
		//	Read Write
		setInsertRecord(from.isInsertRecord());
		setIsSummary(from.isSummary());
		setIsSOTrx(from.isSOTrx());
	}
	
	/**
	 * Set Activity Menu Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/03/2014, 09:38:34
	 * @param m_ActivityMenu_ID
	 * @return void
	 */
	public void setActivityMenu_ID(int m_ActivityMenu_ID){
		this.m_ActivityMenu_ID = m_ActivityMenu_ID;
	}
	
	/**
	 * Get Activity Menu
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/03/2014, 09:39:05
	 * @return
	 * @return int
	 */
	public int getActivityMenu_ID(){
		return m_ActivityMenu_ID;
	}
	
	/**
	 * Get Sales Order Transaction
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/10/2014, 21:03:24
	 * @return
	 * @return boolean
	 */
	public boolean isSOTrx() {
		return m_IsSOTrx;
	}
	
	/**
	 * Set Sales Order Transaction
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/10/2014, 21:04:08
	 * @param m_IsSOTrx
	 * @return void
	 */
	public void setIsSOTrx(boolean m_IsSOTrx) {
		this.m_IsSOTrx = m_IsSOTrx;
	}
	
	/**
	 * Set Parent Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 21:19:23
	 * @param m_Parent_ID
	 * @return void
	 */
	public void setParent_ID(int m_Parent_ID){
		this.m_Parent_ID = m_Parent_ID;
	}
	
	/**
	 * Get Parent Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 22:05:17
	 * @return
	 * @return int
	 */
	public int getParent_ID(){
		return m_Parent_ID;
	}
	
	/**
	 * Is From Activity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/04/2014, 14:11:23
	 * @return
	 * @return boolean
	 */
	public boolean isFromActivity(){
		return m_IsFromActivity;
	}
	
	/**
	 * Set Is From Activity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/04/2014, 14:12:09
	 * @param m_IsFromActivity
	 * @return void
	 */
	public void setIsFromActivity(boolean m_IsFromActivity){
		this.m_IsFromActivity = m_IsFromActivity;
	}

	/**
	 * Get Process Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 11:28:58
	 * @return
	 * @return int
	 */
	public int getAD_Process_ID(){
		return m_AD_Process_ID;
	}
	
	/**
	 * Set PRocess Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 11:29:46
	 * @param m_AD_Process_ID
	 * @return void
	 */
	public void setAD_Process_ID(int m_AD_Process_ID){
		this.m_AD_Process_ID = m_AD_Process_ID;
	}
	
	/**
	 * Get Menu ID
	 * @author Yamel Senih 01/08/2012, 12:35:24
	 * @return
	 * @return int
	 */
	public int getSPS_Menu_ID(){
		return m_SPS_Menu_ID;
	}
	
	/**
	 * Set Menu ID
	 * @author Yamel Senih 01/08/2012, 12:38:45
	 * @param m_SPS_Menu_ID
	 * @return void
	 */
	public void setSPS_Menu_ID(int m_SPS_Menu_ID){
		this.m_SPS_Menu_ID = m_SPS_Menu_ID;
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
	public void setName(String name){
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
	public void setDescription(String description){
		this.m_Description = description;
	}
	
	/**
	 * Get Action Menu
	 * @author Yamel Senih 28/04/2012, 16:09:28
	 * @return
	 * @return String
	 */
	public String getAction(){
		return m_Action;
	}
	
	/**
	 * Set Action Menu
	 * @author Yamel Senih 01/08/2012, 10:10:58
	 * @param action
	 * @return void
	 */
	public void setAction(String action){
		this.m_Action = action;
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
	public void setSPS_Table_ID(int m_AD_Table_ID){
		this.m_SPS_Table_ID = m_AD_Table_ID;
	}
	
	/**
	 * Get From SFA Table
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 15:32:12
	 * @return
	 * @return int
	 */
	public int getFrom_SPS_Table_ID(){
		return m_From_SPS_Table_ID;
	}
	
	/**
	 * Set From SFA Table
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 15:32:02
	 * @param m_From_SPS_Table_ID
	 * @return void
	 */
	public void setFrom_SPS_Table_ID(int m_From_SPS_Table_ID){
		this.m_From_SPS_Table_ID = m_From_SPS_Table_ID;
	}
	
	/**
	 * Get From Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 15:33:21
	 * @return
	 * @return int
	 */
	public int getFrom_Record_ID(){
		return m_From_Record_ID;
	}
	
	/**
	 * Set From Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 15:33:12
	 * @param m_From_Record_ID
	 * @return void
	 */
	public void setFrom_Record_ID(int m_From_Record_ID){
		this.m_From_Record_ID = m_From_Record_ID;
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
	public void setWhereClause(String whereClause){
		this.m_WhereClause = whereClause;
	}
	
	
	/**
	 * Get Group by clause
	 * @author Yamel Senih 14/08/2012, 22:07:49
	 * @return
	 * @return String
	 */
	public String getGroupByClause(){
		return m_GroupByClause;
	}
	
	/**
	 * Set Group by clause
	 * @author Yamel Senih 14/08/2012, 22:08:25
	 * @param groupByClause
	 * @return void
	 */
	public void setGroupByClause(String groupByClause){
		this.m_GroupByClause = groupByClause;
	}
	
	/**
	 * Set Order By clause
	 * @author Yamel Senih 28/05/2012, 08:50:40
	 * @param orderByClause
	 * @return void
	 */
	public void setOrderByClause(String orderByClause){
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
	 * Set Summary
	 * @author Yamel Senih 07/05/2012, 12:12:56
	 * @param m_IsSummary
	 * @return void
	 */
	public void setIsSummary(boolean m_IsSummary){
		this.m_IsSummary = m_IsSummary;
	}
	
	/**
	 * Is Summary
	 * @author Yamel Senih 07/05/2012, 12:13:35
	 * @return
	 * @return boolean
	 */
	public boolean getIsSummary(){
		return m_IsSummary;
	}

	/**
	 * Get Activity Type
	 * @author Yamel Senih 12/05/2012, 03:41:19
	 * @return
	 * @return String
	 */
	public String getDeploymentType(){
		return m_DeploymentType;
	}
	
	/**
	 * Set Activity Type
	 * @author Yamel Senih 01/08/2012, 10:14:20
	 * @param activitType
	 * @return void
	 */
	public void setDeploymentType(String activitType){
		this.m_DeploymentType = activitType;
	}
	
	/**
	 * Is Insert Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 01:02:28
	 * @return
	 * @return boolean
	 */
	public String isInsertRecord(){
		return m_IsInsertRecord;
	}
	
	/**
	 * Set is Insert Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 09:40:45
	 * @param m_IsInsertRecord
	 * @return void
	 */
	public void setInsertRecord(String m_IsInsertRecord){
		this.m_IsInsertRecord = m_IsInsertRecord;
	}
	
	/**
	 * Set Form Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 11:34:28
	 * @param m_AD_Form_ID
	 * @return void
	 */
	public void setAD_Form_ID(int m_AD_Form_ID){
		this.m_AD_Form_ID = m_AD_Form_ID;
	}
	
	/**
	 * Get Form Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 11:35:00
	 * @return
	 * @return int
	 */
	public int getAD_Form_ID(){
		return m_AD_Form_ID;
	}
	
	/**
	 * Set Table Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 11:57:42
	 * @param m_AD_Table_ID
	 * @return void
	 */
	public void setAD_Table_ID(int m_AD_Table_ID){
		this.m_AD_Table_ID = m_AD_Table_ID;
	}
	
	/**
	 * Get Table Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 11:57:32
	 * @return
	 * @return int
	 */
	public int getAD_Table_ID(){
		return m_AD_Table_ID;
	}
	
	/**
	 * Set Window Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 09:32:39
	 * @param m_SPS_Window_ID
	 * @return void
	 */
	public void setSPS_Window_ID(int m_SPS_Window_ID){
		this.m_SPS_Window_ID = m_SPS_Window_ID;
	}
	
	/**
	 * Get Window Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 09:32:48
	 * @return
	 * @return int
	 */
	public int getSPS_Window_ID(){
		return m_SPS_Window_ID;
	}
	
	/**
	 * Get Window No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/03/2014, 21:01:16
	 * @return
	 * @return int
	 */
	public int getActivityNo(){
		return m_ActivityNo;
	}
	
	/**
	 * Set Window No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/03/2014, 21:01:58
	 * @param m_ActivityNo
	 * @return void
	 */
	public void setActivityNo(int m_ActivityNo){
		this.m_ActivityNo = m_ActivityNo;
	}

	@Override
	public String toString() {
		return "ActivityParameter [m_From_Record_ID=" + m_From_Record_ID
				+ ", m_From_SPS_Table_ID=" + m_From_SPS_Table_ID
				+ ", m_AD_Table_ID=" + m_AD_Table_ID + ", m_Parent_ID="
				+ m_Parent_ID + ", m_Action=" + m_Action + ", m_AD_Form_ID="
				+ m_AD_Form_ID + ", m_AD_Process_ID=" + m_AD_Process_ID
				+ ", m_ActivityMenu_ID=" + m_ActivityMenu_ID
				+ ", m_DeploymentType=" + m_DeploymentType + ", m_Description="
				+ m_Description + ", m_GroupByClause=" + m_GroupByClause
				+ ", m_IsInsertRecord=" + m_IsInsertRecord 
				+ ", m_IsSummary=" + m_IsSummary + ", m_Name="
				+ m_Name + ", m_OrderByClause=" + m_OrderByClause
				+ ", m_SPS_Menu_ID=" + m_SPS_Menu_ID + ", m_SPS_SyncMenu_ID="
				+ m_SPS_SyncMenu_ID + ", m_SPS_Table_ID=" + m_SPS_Table_ID
				+ ", m_SPS_Window_ID=" + m_SPS_Window_ID + ", m_WhereClause="
				+ m_WhereClause + ", m_ActivityNo=" + m_ActivityNo + "]";
	}
}
