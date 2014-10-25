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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import org.spinsuite.view.lookup.InfoField;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih
 *
 */
public class DisplayMenuItem implements Parcelable {
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 09:39:39
	 */
	public DisplayMenuItem(){
		//	
	}
	
	/**
	 * Copy From Field
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/04/2014, 21:17:16
	 * @param m_field
	 */
	public DisplayMenuItem(InfoField m_field){
		this();
		if(m_field.AD_Form_ID != 0)
			setAction(DisplayMenuItem.ACTION_Form);
		else if(m_field.AD_Process_ID != 0)
			setAction(DisplayMenuItem.ACTION_Process);
		//	
		setAD_Process_ID(m_field.AD_Process_ID);
		setAD_Form_ID(m_field.AD_Form_ID);
		setName(m_field.Name);
		
	}
	
	
	/**
	 * New Menu Item Manual
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 22:01:08
	 * @param m_SPS_Menu_ID
	 * @param m_Name
	 * @param m_Description
	 * @param m_ImageURL
	 */
	public DisplayMenuItem(int m_SPS_Menu_ID, String m_Name, String m_Description, String m_ImageURL){
		this.m_SPS_Menu_ID = m_SPS_Menu_ID;
		this.m_Name = m_Name;
		this.m_Description = m_Description;
		this.m_ImageURL = m_ImageURL;
	}
	
	/**
	 * Constructor with Resource ID 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/09/2014, 17:01:29
	 * @param m_SPS_Menu_ID
	 * @param m_Name
	 * @param m_Description
	 * @param m_AttResourceID
	 */
	public DisplayMenuItem(int m_SPS_Menu_ID, String m_Name, String m_Description, int m_AttResourceID){
		this.m_SPS_Menu_ID = m_SPS_Menu_ID;
		this.m_Name = m_Name;
		this.m_Description = m_Description;
		this.m_AttResourceID = m_AttResourceID;
	}

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/10/2014, 19:14:13
	 * @param m_SPS_Menu_ID
	 * @param m_Name
	 * @param m_Description
	 * @param m_Action
	 * @param m_ImageURL
	 * @param m_SPS_Table_ID
	 * @param m_WhereClause
	 * @param m_GroupByClause
	 * @param m_OrderByClause
	 * @param m_Parent_ID
	 * @param m_IsSummary
	 * @param m_DeploymentType
	 * @param m_AD_Form_ID
	 * @param m_SPS_Window_ID
	 * @param m_AD_Process_ID
	 * @param m_ActivityMenu_ID
	 * @param m_isReadWrite
	 * @param m_IsInsertRecord
	 * @param m_SeqNo
	 * @param m_IsSOTrx
	 */
	public DisplayMenuItem(int m_SPS_Menu_ID, String m_Name, String m_Description, String m_Action, 
			String m_ImageURL, int m_SPS_Table_ID, String m_WhereClause, String m_GroupByClause, 
			String m_OrderByClause, int m_Parent_ID, boolean m_IsSummary, String m_DeploymentType, 
			int m_AD_Form_ID, int m_SPS_Window_ID, int m_AD_Process_ID, int m_ActivityMenu_ID, 
			String m_isReadWrite, String m_IsInsertRecord, int m_SeqNo, boolean m_IsSOTrx){
		this.m_SPS_Menu_ID = m_SPS_Menu_ID;
		this.m_Name = m_Name;
		this.m_Description = m_Description;
		this.m_Action = m_Action;
		this.m_ImageURL = m_ImageURL;
		this.m_SPS_Table_ID = m_SPS_Table_ID;
		this.m_WhereClause = m_WhereClause;
		this.m_GroupByClause = m_GroupByClause;
		this.m_OrderByClause = m_OrderByClause;
		this.m_Parent_ID = m_Parent_ID;
		this.m_IsSummary = m_IsSummary;
		this.m_DeploymentType = m_DeploymentType;
		this.m_AD_Form_ID = m_AD_Form_ID;
		this.m_SPS_Window_ID = m_SPS_Window_ID;
		this.m_AD_Process_ID = m_AD_Process_ID;
		this.m_ActivityMenu_ID = m_ActivityMenu_ID;
		this.m_IsReadWrite = m_isReadWrite;
		this.m_IsInsertRecord = m_IsInsertRecord;
		this.m_SeqNo = m_SeqNo;
		this.m_IsSOTrx = m_IsSOTrx;
	}
	
	/**	Action Menu			*/
	private String  		m_Action = null;
	/**	Form				*/
	private int 			m_AD_Form_ID = 0;
	/**	Process ID			*/
	private int 			m_AD_Process_ID = 0;
	/**	Activity Menu		*/
	private int 			m_ActivityMenu_ID = 0;
	/**	Activity Type		*/
	private String			m_DeploymentType = "D";
	/**	Short Description	*/
	private String 			m_Description = null;
	/**	Error Image URL		*/
	private String			m_ErrImgURL = null;
	/**	Order By			*/
	private String			m_GroupByClause = null;
	/**	Image URL			*/
	private String 			m_ImageURL = null;
	/**	Menu is Insert Record*/
	private String			m_IsInsertRecord = null;
	/**	Menu is Read and Write*/
	private String		 	m_IsReadWrite = null;
	/**	Is Summary Menu		*/
	private boolean			m_IsSummary = false;
	/**	Name Item			*/
	private String  		m_Name = null;
	/**	Order By			*/
	private String			m_OrderByClause = null;
	/**	Quick Action Menu	*/
	private int 			m_QuickActionMenu_ID = 0;
	/**	Menu ID				*/
	private int				m_SPS_Menu_ID = 0;
	/**	Sync Menu			*/
	private int 			m_SPS_SyncMenu_ID = 0;
	/**	Table				*/
	private int 			m_SPS_Table_ID = 0;
	/**	Window				*/
	private int 			m_SPS_Window_ID = 0;
	/**	Where				*/
	private String			m_WhereClause = null;
	/**	Menu ID				*/
	private int				m_Parent_ID = 0;
	/**	Sequence			*/
	private int 			m_SeqNo = 0;
	/**	Resource			*/
	private int 			m_AttResourceID = 0;
	/**	Is Sales Transaction*/
	private boolean			m_IsSOTrx = false;

	
	/** Action AD_Reference_ID=53504 	*/
	public static final int 	ACTION_AD_Reference_ID				= 53504;
	/** Form = X 						*/
	public static final String 	ACTION_Form 						= "X";
	/** Process = P 					*/
	public static final String 	ACTION_Process 						= "P";
	/** Report = R 						*/
	public static final String 	ACTION_Report 						= "R";
	/**	Window = W 						*/
	public static final String 	ACTION_Window 						= "W";
	/** DeploymentType 					*/
	public static final int 	DEPLOYMENTTYPE_AD_Reference_ID		= 53506;
	/** Direct Form = D 				*/
	public static final String 	DEPLOYMENTTYPE_DirectForm 			= "D";
	/** List = L 						*/
	public static final String 	DEPLOYMENTTYPE_List 				= "L";
	/** Menu with Quick Action = M 		*/
	public static final String	DEPLOYMENTTYPE_MenuWithQuickAction 	= "M";
	/** List with Quick Action = W 		*/
	public static final String 	DEPLOYMENTTYPE_ListWithQuickAction 	= "W";
	
	/**	Context Menu Type 				*/
	public static final String	CONTEXT_ACTIVITY_TYPE				= "Context_Activity_Type";
	public static final int		CONTEXT_ACTIVITY_TYPE_Form			= 1;
	public static final int		CONTEXT_ACTIVITY_TYPE_Process		= 2;
	public static final int		CONTEXT_ACTIVITY_TYPE_Report		= 3;
	public static final int		CONTEXT_ACTIVITY_TYPE_Window		= 4;
	public static final int		CONTEXT_ACTIVITY_TYPE_SearchWindow	= 5;
	public static final int		CONTEXT_ACTIVITY_TYPE_SearchColumn	= 6;
	
	//	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DisplayMenuItem createFromParcel(Parcel parcel) {
			return new DisplayMenuItem(parcel);
		}
		public DisplayMenuItem[] newArray(int size) {
			return new DisplayMenuItem[size];
		}
	};
	
	public DisplayMenuItem(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		
		parcel.writeString(m_Action);
		parcel.writeInt(m_AD_Form_ID);
		parcel.writeInt(m_AD_Process_ID);
		parcel.writeInt(m_ActivityMenu_ID);
		parcel.writeString(m_DeploymentType);
		parcel.writeString(m_Description);
		parcel.writeString(m_ErrImgURL);
		parcel.writeString(m_GroupByClause);
		parcel.writeString(m_ImageURL);
		parcel.writeString(m_Name);
		parcel.writeString(m_OrderByClause);
		parcel.writeInt(m_QuickActionMenu_ID);
		parcel.writeInt(m_SPS_Menu_ID);
		parcel.writeInt(m_SPS_SyncMenu_ID);
		parcel.writeInt(m_SPS_Table_ID);
		parcel.writeInt(m_SPS_Window_ID);
		parcel.writeString(m_WhereClause);
		parcel.writeInt(m_Parent_ID);
		parcel.writeInt(m_SeqNo);
		//	Read Write
		parcel.writeString(m_IsReadWrite);
		parcel.writeString(m_IsInsertRecord);
		parcel.writeString((m_IsSummary? "Y": "N"));
		parcel.writeInt(m_AttResourceID);
		parcel.writeString((m_IsSOTrx? "Y": "N"));
	}
	
	public void readToParcel(Parcel parcel){
		m_Action = parcel.readString();
		m_AD_Form_ID = parcel.readInt();
		m_AD_Process_ID = parcel.readInt();
		m_ActivityMenu_ID = parcel.readInt();
		m_DeploymentType = parcel.readString();
		m_Description = parcel.readString();
		m_ErrImgURL = parcel.readString();
		m_GroupByClause = parcel.readString();
		m_ImageURL = parcel.readString();
		m_Name = parcel.readString();
		m_OrderByClause = parcel.readString();
		m_QuickActionMenu_ID = parcel.readInt();
		m_SPS_Menu_ID = parcel.readInt();
		m_SPS_SyncMenu_ID = parcel.readInt();
		m_SPS_Table_ID = parcel.readInt();
		m_SPS_Window_ID = parcel.readInt();
		m_WhereClause = parcel.readString();
		m_Parent_ID = parcel.readInt();
		m_SeqNo = parcel.readInt();
		//	Read Write
		m_IsReadWrite = parcel.readString();
		m_IsInsertRecord = parcel.readString();
		String booleanValue = parcel.readString();
		m_IsSummary = (booleanValue != null 
								&& booleanValue.equals("Y"));
		m_AttResourceID = parcel.readInt();
		booleanValue = parcel.readString();
		m_IsSOTrx = (booleanValue != null 
				&& booleanValue.equals("Y"));
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
	 * Get Image Name
	 * @author Yamel Senih 28/04/2012, 00:24:59
	 * @return
	 * @return String
	 */
	public String getImageURL(){
		return m_ImageURL;
	}
	
	/**
	 * Set Image Name
	 * @author Yamel Senih 01/08/2012, 10:05:32
	 * @param img
	 * @return void
	 */
	public void setImageURL(String img){
		this.m_ImageURL = img;
	}
	
	/**
	 * Get Attribute Resource ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/09/2014, 17:03:03
	 * @return
	 * @return int
	 */
	public int getAttResourceID() {
		return m_AttResourceID;
	}
	
	/**
	 * Set Attribute Resource ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/09/2014, 17:03:34
	 * @param m_AttResourceID
	 * @return void
	 */
	public void setAttResourceID(int m_AttResourceID) {
		this.m_AttResourceID = m_AttResourceID;
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
	 * Is Read and Write
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 01:00:31
	 * @return
	 * @return String
	 */
	public String isReadWrite(){
		return m_IsReadWrite;
	}
	
	/**
	 * Set Read and Write
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 01:00:43
	 * @param m_IsReadWrite
	 * @return void
	 */
	public void setIsReadWrite(boolean m_IsReadWrite){
		this.m_IsReadWrite = (m_IsReadWrite? "Y": "N");
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
	 * Set Parent Menu ID
	 * @author Yamel Senih 07/05/2012, 11:53:55
	 * @param m_SF_ParentMenuList_ID
	 * @return void
	 */
	public void setParent_ID(int m_SF_ParentMenuList_ID){
		this.m_Parent_ID = m_SF_ParentMenuList_ID;
	}
	
	/**
	 * Get PArent Menu ID
	 * @author Yamel Senih 07/05/2012, 11:52:15
	 * @return
	 * @return int
	 */
	public int getParent_ID(){
		return m_Parent_ID;
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
	public boolean isSummary(){
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
	 * Get Process ID
	 * @author Yamel Senih 29/07/2012, 16:09:14
	 * @return
	 * @return int
	 */
	public int getAD_Process_ID(){
		return m_AD_Process_ID;
	}
	
	/**
	 * Set Process ID
	 * @author Yamel Senih 01/08/2012, 12:09:15
	 * @param m_AD_Process_ID
	 * @return void
	 */
	public void setAD_Process_ID(int m_AD_Process_ID){
		this.m_AD_Process_ID = m_AD_Process_ID;
	}
	
	/**
	 * Is Read Write Menu Level
	 * @author Yamel Senih 31/07/2012, 18:46:44
	 * @return
	 * @return String
	 */
	public String isReadWriteM(){
		return m_IsReadWrite;
	}
	
	/**
	 * Set Read Write Menu Level
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 01:02:07
	 * @param m_IsReadWrite
	 * @return void
	 */
	public void setIsReadWriteM(String m_IsReadWrite){
		if(m_IsReadWrite != null){
			this.m_IsReadWrite = m_IsReadWrite;
		}
		this.m_IsReadWrite = m_IsReadWrite;
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
	 * Set Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 17:58:46
	 * @param m_SeqNo
	 * @return void
	 */
	public void setSeqNo(int m_SeqNo){
		this.m_SeqNo = m_SeqNo;
	}
	
	/**
	 * Get Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 17:59:09
	 * @return
	 * @return int
	 */
	public int getSeqNo(){
		return this.m_SeqNo;
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
	 * SEt Window Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 09:38:54
	 * @param m_SPS_Window_ID
	 * @return void
	 */
	public void setSPS_Window_ID(int m_SPS_Window_ID){
		this.m_SPS_Window_ID = m_SPS_Window_ID;
	}
	
	/**
	 * Get Window Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 09:39:03
	 * @return
	 * @return int
	 */
	public int getSPS_Window_ID(){
		return m_SPS_Window_ID;
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
	 * Get Is Sales Transaction
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/10/2014, 19:11:52
	 * @return
	 * @return boolean
	 */
	public boolean isSOTrx() {
		return m_IsSOTrx;
	}
	
	/**
	 * Set Is Sales Transaction
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/10/2014, 19:12:31
	 * @param m_IsSOTrx
	 * @return void
	 */
	public void setIsSOTrx(boolean m_IsSOTrx) {
		this.m_IsSOTrx = m_IsSOTrx;
	}

	@Override
	public String toString() {
		return "DisplayMenuItem [m_Action=" + m_Action + ", m_AD_Form_ID="
				+ m_AD_Form_ID + ", m_AD_Process_ID=" + m_AD_Process_ID
				+ ", m_ActivityMenu_ID=" + m_ActivityMenu_ID
				+ ", m_DeploymentType=" + m_DeploymentType + ", m_Description="
				+ m_Description + ", m_ErrImgURL=" + m_ErrImgURL
				+ ", m_GroupByClause=" + m_GroupByClause + ", m_ImageURL="
				+ m_ImageURL + ", m_IsInsertRecord=" + m_IsInsertRecord
				+ ", m_IsReadWrite=" + m_IsReadWrite + ", m_IsSummary="
				+ m_IsSummary + ", m_Name=" + m_Name + ", m_OrderByClause="
				+ m_OrderByClause + ", m_QuickActionMenu_ID="
				+ m_QuickActionMenu_ID + ", m_SPS_Menu_ID=" + m_SPS_Menu_ID
				+ ", m_SPS_SyncMenu_ID=" + m_SPS_SyncMenu_ID
				+ ", m_SPS_Table_ID=" + m_SPS_Table_ID + ", m_SPS_Window_ID="
				+ m_SPS_Window_ID + ", m_WhereClause=" + m_WhereClause
				+ ", m_Parent_ID=" + m_Parent_ID + ", m_SeqNo=" + m_SeqNo + "]";
	}
}