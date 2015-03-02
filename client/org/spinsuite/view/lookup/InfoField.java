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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.view.lookup;

import org.spinsuite.model.MSPSColumn;
import org.spinsuite.model.POInfoColumn;
import org.spinsuite.print.InfoReportField;
import org.spinsuite.process.InfoPara;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class InfoField extends POInfoColumn {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/03/2014, 14:06:11
	 * @param parcel
	 */
	public InfoField(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/03/2014, 14:06:07
	 */
	public InfoField(){
		
	}
	
	/**
	 * Create from Column
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/10/2014, 19:49:58
	 * @param from
	 */
	public InfoField(MSPSColumn from){
		this();
		if(from == null)
			return;
		//	Set from Info Parameter
		DisplayType 			= from.getAD_Reference_ID();
		IsActive 				= from.isActive();
		AD_Element_ID 			= from.getAD_Element_ID();
		AD_Reference_Value_ID 	= from.getAD_Reference_Value_ID();
		AD_Val_Rule_ID 			= from.getAD_Val_Rule_ID();
		ColumnName 				= from.getColumnName();
		DefaultValue 			= from.getDefaultValue();
		Description 			= from.getDescription();
		FieldLength 			= from.getFieldLength();
		FormatPattern 			= from.getFormatPattern();
		IsAlwaysUpdateable 		= from.isAlwaysUpdateable();
		IsCentrallyMaintained 	= from.isCentrallyMaintained();
		IsIdentifier 			= from.isIdentifier();
		IsKey 					= from.isKey();
		IsMandatory 			= from.isMandatory();
		IsParent 				= from.isParent();
		IsSelectionColumn 		= from.isSelectionColumn();
		IsUpdateable 			= from.isUpdateable();
		Name 					= from.getName();
		SelectionSeqNo 			= from.getSelectionSeqNo();
		SeqNo 					= from.getSeqNo();
		SPS_Column_ID 			= from.getSPS_Column_ID();
		SPS_Table_ID 			= from.getSPS_Table_ID();
		ValueMax 				= from.getValueMax();
		ValueMin 				= from.getValueMin();
		VFormat 				= from.getVFormat();
		//	
	}
	
	/**
	 * Create From Info Parameter
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/03/2014, 21:50:53
	 * @param from
	 */
	public InfoField(InfoPara from){
		this();
		//	Set from Info Parameter
		DisplayType 			= from.DisplayType;
		DisplayLogic 			= from.DisplayLogic;
		Help 					= from.Help;
		IsActive 				= from.IsActive;
		IsDisplayed 			= from.IsDisplayed;
		IsReadOnly 				= from.IsReadOnly;
		FieldSeqNo 				= from.FieldSeqNo;
		AD_Element_ID 			= from.AD_Element_ID;
		AD_Reference_Value_ID 	= from.AD_Reference_Value_ID;
		AD_Val_Rule_ID 			= from.AD_Val_Rule_ID;
		ColumnName 				= from.ColumnName;
		DefaultValue 			= from.DefaultValue;
		Description 			= from.Description;
		EntityType 				= from.EntityType;
		FieldLength 			= from.FieldLength;
		FormatPattern 			= from.FormatPattern;
		IsAlwaysUpdateable 		= from.IsAlwaysUpdateable;
		IsCentrallyMaintained 	= from.IsCentrallyMaintained;
		IsEncrypted 			= from.IsEncrypted;
		IsIdentifier 			= from.IsIdentifier;
		IsKey 					= from.IsKey;
		IsMandatory 			= from.IsMandatory;
		IsParent 				= from.IsParent;
		IsSelectionColumn 		= from.IsSelectionColumn;
		IsUpdateable 			= from.IsUpdateable;
		Name 					= from.Name;
		SelectionSeqNo 			= from.SelectionSeqNo;
		SeqNo 					= from.SeqNo;
		SPS_Column_ID 			= from.SPS_Column_ID;
		SPS_Table_ID 			= from.SPS_Table_ID;
		ValueMax 				= from.ValueMax;
		ValueMin 				= from.ValueMin;
		VFormat 				= from.VFormat;
		//	
		if(SPS_Column_ID == 0)
			SPS_Column_ID = from.AD_Process_Para_ID;
	}
	
	
	/**
	 * Create from Info Report Field
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 21:02:48
	 * @param from
	 */
	public InfoField(InfoReportField from){
		//	Set from Info Parameter
		DisplayType 			= from.DisplayType;
		IsActive 				= true;
		IsDisplayed 			= true;
		FieldSeqNo 				= from.FieldSeqNo;
		AD_Element_ID 			= from.AD_Element_ID;
		AD_Reference_Value_ID 	= from.AD_Reference_Value_ID;
		AD_Val_Rule_ID 			= from.AD_Val_Rule_ID;
		ColumnName 				= from.ColumnName;
		DefaultValue 			= from.DefaultValue;
		Description 			= from.Description;
		EntityType 				= from.EntityType;
		FieldLength 			= from.FieldLength;
		FormatPattern 			= from.FormatPattern;
		IsAlwaysUpdateable 		= from.IsAlwaysUpdateable;
		IsCentrallyMaintained 	= from.IsCentrallyMaintained;
		IsEncrypted 			= from.IsEncrypted;
		IsIdentifier 			= from.IsIdentifier;
		IsKey 					= from.IsKey;
		IsMandatory 			= from.IsMandatory;
		IsParent 				= from.IsParent;
		IsSelectionColumn 		= from.IsSelectionColumn;
		IsUpdateable 			= from.IsUpdateable;
		Name 					= from.Name;
		SelectionSeqNo 			= from.SelectionSeqNo;
		SeqNo 					= from.SeqNo;
		SPS_Column_ID 			= from.SPS_Column_ID;
		SPS_Table_ID 			= from.SPS_Table_ID;
		ValueMax 				= from.ValueMax;
		ValueMin 				= from.ValueMin;
		VFormat 				= from.VFormat;	
	}

	/**	Field Group			*/
	public int 			AD_FieldGroup_ID = 0;
	/**	Display Logic		*/
	public String		DisplayLogic = null;
	/**	Help				*/
	public String		Help = null;
	/**	Active				*/
	public boolean		IsActive = false;
	/**	Displayed			*/
	public boolean		IsDisplayed = false;
	/**	Read Only			*/
	public boolean		IsReadOnly = false;
	/**	Same Line			*/
	public boolean		IsSameLine = false;
	/**	Sequence			*/
	public int 			FieldSeqNo = 0;
	/**	Field				*/
	public int 			SPS_Field_ID = 0;
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public InfoField createFromParcel(Parcel parcel) {
			return new InfoField(parcel);
		}
		public InfoField[] newArray(int size) {
			return new InfoField[size];
		}
	};
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeInt(AD_FieldGroup_ID);
		dest.writeString(DisplayLogic);
		dest.writeString(Help);
		dest.writeString(IsActive? "Y": "N");
		dest.writeString(IsDisplayed? "Y": "N");
		dest.writeString(IsReadOnly? "Y": "N");
		dest.writeString(IsSameLine? "Y": "N");
		dest.writeInt(FieldSeqNo);
		dest.writeInt(SPS_Field_ID);
	}
	
	@Override
	public void readToParcel(Parcel parcel){
		super.readToParcel(parcel);
		AD_FieldGroup_ID = parcel.readInt();
		DisplayLogic = parcel.readString();
		Help = parcel.readString();
		String bool = parcel.readString();
		IsActive = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsDisplayed = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsReadOnly = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsSameLine = (bool != null && bool.equals("Y"));
		FieldSeqNo = parcel.readInt();
		SPS_Field_ID = parcel.readInt();
	}

}
