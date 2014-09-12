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

import org.spinsuite.model.MSPSTab;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TabParameter implements Parcelable {

	/**	Record Identifier	*/
	private int 			m_Record_ID = 0;
	/**	Activity No			*/
	private int 			m_ActivityNo = 0;
	/**	Tab No				*/
	private int 			m_TabNo = 0;
	/**	Sequence No			*/
	private int 			m_SeqNo = 0;
	/**	Tab Level			*/
	private int 			m_TabLevel = 0;
	/**	Process ID			*/
	private int 			m_AD_Process_ID = 0;
	/**	Menu is Insert Record*/
	private boolean			m_IsInsertRecord = false;
	/**	Menu is Read and Write*/
	private boolean		 	m_IsReadOnly = false;
	/**	Name Item			*/
	private String  		m_Name = null;
	/**	Short Description	*/
	private String 			m_Description = null;
	/**	Order By			*/
	private String			m_OrderByClause = null;
	/**	Tab					*/
	private int 			m_SPS_Tab_ID = 0;
	/**	Table				*/
	private int 			m_SPS_Table_ID = 0;
	/**	Window				*/
	private int				m_SPS_Window_ID = 0;
	/**	Where				*/
	private String			m_WhereClause = null;
	/**	Class Name			*/
	private String 			m_Classname = null;
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public TabParameter createFromParcel(Parcel parcel) {
			return new TabParameter(parcel);
		}
		public TabParameter[] newArray(int size) {
			return new TabParameter[size];
		}
	};
	
	public TabParameter(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 21:54:47
	 */
	public TabParameter(){
		//	
	}
	
	/**
	 * Create from MSFATab
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:40:26
	 * @param from
	 */
	public TabParameter(MSPSTab from){
		createFromMSFATab(from);
	}
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(m_Record_ID);
		dest.writeInt(m_ActivityNo);
		dest.writeInt(m_TabNo);
		dest.writeInt(m_SeqNo);
		dest.writeInt(m_TabLevel);
		dest.writeInt(m_AD_Process_ID);
		dest.writeString(m_IsInsertRecord? "Y": "N");
		dest.writeString(m_IsReadOnly? "Y": "N");
		dest.writeString(m_Name);
		dest.writeString(m_Description);
		dest.writeString(m_OrderByClause);
		dest.writeInt(m_SPS_Tab_ID);
		dest.writeInt(m_SPS_Table_ID);
		dest.writeInt(m_SPS_Window_ID);
		dest.writeString(m_WhereClause);
		dest.writeString(m_Classname);
	}
	
	public void readToParcel(Parcel parcel){
		m_Record_ID = parcel.readInt();
		m_ActivityNo = parcel.readInt();
		m_TabNo = parcel.readInt();
		m_SeqNo = parcel.readInt();
		m_TabLevel = parcel.readInt();
		m_AD_Process_ID = parcel.readInt();
		String bool = parcel.readString();
		m_IsInsertRecord = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		m_IsReadOnly = (bool != null && bool.equals("Y"));
		m_Name = parcel.readString();
		m_Description = parcel.readString();
		m_OrderByClause = parcel.readString();
		m_SPS_Tab_ID = parcel.readInt();
		m_SPS_Table_ID = parcel.readInt();
		m_SPS_Window_ID = parcel.readInt();
		m_WhereClause = parcel.readString();
		m_Classname = parcel.readString();
	}
	
	/**
	 * Create from Tab
	 * @author Yamel Senih 01/08/2012, 12:10:41
	 * @param fromMenu
	 * @return
	 */
	public void createFromMSFATab(MSPSTab from){
		if(from == null)
			return;
		//	Set Values
		//m_ParentTabNo = parcel.readInt();
		//m_TabNo = from.gett
		m_SeqNo = from.getSeqNo();
		m_TabLevel = from.getTabLevel();
		//m_AD_Process_ID = from
		//m_IsInsertRecord = from
		m_IsReadOnly = from.isReadOnly();
		m_Name = from.getName();
		m_Description = from.getDescription();
		m_OrderByClause = from.getOrderByClause();
		m_SPS_Tab_ID = from.getSPS_Tab_ID();
		m_SPS_Table_ID = from.getSPS_Table_ID();
		m_SPS_Window_ID = from.getSPS_Window_ID();
		m_WhereClause = from.getWhereClause();
		m_Classname = from.getClassname();
	}
	
	/**
	 * Set Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 00:08:15
	 * @param m_ActivityNo
	 * @return void
	 */
	public void setActivityNo(int m_ActivityNo){
		this.m_ActivityNo = m_ActivityNo;
	}
	
	/**
	 * Get Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 00:07:25
	 * @return
	 * @return int
	 */
	public int getActivityNo(){
		return this.m_ActivityNo;
	}
	
	/**
	 * Get Parent Tab number
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:51:32
	 * @return
	 * @return int
	 */
	public int getParentTabNo(){
		return m_TabLevel - 1;
	}
	
	/**
	 * Set Tab Number
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:50:53
	 * @param m_TabNo
	 * @return void
	 */
	public void setTabNo(int m_TabNo){
		this.m_TabNo = m_TabNo;
	}
	
	/**
	 * Get Tab Number
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:50:21
	 * @return
	 * @return int
	 */
	public int getTabNo(){
		return this.m_TabNo;
	}
	
	/**
	 * Set Tab Level
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:48:28
	 * @param m_TabLevel
	 * @return void
	 */
	public void setTabLevel(int m_TabLevel){
		this.m_TabLevel = m_TabLevel;
	}
	
	/**
	 * Get TabLevel
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:46:21
	 * @return
	 * @return int
	 */
	public int getTabLevel(){
		return this.m_TabLevel;
	}
	
	/**
	 * Set Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:45:07
	 * @param m_SeqNo
	 * @return void
	 */
	public void setSeqNo(int m_SeqNo){
		this.m_SeqNo = m_SeqNo;
	}
	
	/**
	 * Get Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:44:33
	 * @return
	 * @return int
	 */
	public int getSeqNo(){
		return this.m_SeqNo;
	}
	
	/**
	 * Set Class Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:43:05
	 * @param m_Classname
	 * @return void
	 */
	public void setClassname(String m_Classname){
		this.m_Classname = m_Classname;
	}
	
	/**
	 * Get Class Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:42:07
	 * @return
	 * @return String
	 */
	public String getClassname(){
		return this.m_Classname;
	}
	
	/**
	 * Get Tab Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:40:00
	 * @return
	 * @return int
	 */
	public int getSPS_Tab_ID(){
		return this.m_SPS_Tab_ID;
	}
	
	/**
	 * Set Tab Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 23:41:00
	 * @param m_SPS_Tab_ID
	 * @return void
	 */
	public void setSPS_Tab_ID(int m_SPS_Tab_ID){
		this.m_SPS_Tab_ID = m_SPS_Tab_ID;
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
	 * Is Read Only
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 01:00:31
	 * @return
	 * @return boolean
	 */
	public boolean isReadOnly(){
		return m_IsReadOnly;
	}
	
	/**
	 * Set Read Only
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 01:00:43
	 * @param m_IsReadOnly
	 * @return void
	 */
	public void setIsReadOnly(boolean m_IsReadOnly){
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
	public void setWhereClause(String whereClause){
		this.m_WhereClause = whereClause;
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
	 * Is Insert Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 01:02:28
	 * @return
	 * @return boolean
	 */
	public boolean isInsertRecord(){
		return m_IsInsertRecord;
	}
	
	/**
	 * Set is Insert Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 09:40:45
	 * @param m_IsInsertRecord
	 * @return void
	 */
	public void setIsInsertRecord(boolean m_IsInsertRecord){
		this.m_IsInsertRecord = m_IsInsertRecord;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
