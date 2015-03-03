/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.spinsuite.model;


import android.os.Parcel;
import android.os.Parcelable;


/**
 *	PO Info Column Info Value Object
 *	
 *  @author Jorg Janke
 *  @version $Id: POInfoColumn.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class POInfoColumn implements Parcelable {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 08:45:49
	 */
	public POInfoColumn(){
		
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 08:45:55
	 * @param parcel
	 */
	public POInfoColumn(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/** Column ID		*/
	public int          AD_Column_ID;
	/**	Element ID		*/
	public int          AD_Element_ID;
	/** Display Type 	*/
	public int			DisplayType;
	/** Reference Value	*/
	public int			AD_Reference_Value_ID;
	/** Value Rule		*/
	public int			AD_Val_Rule_ID;
	/**	Call Out		*/
	public String		Callout;
	/** Column Name		*/
	public String       ColumnName;
	/** Virtual Column 	*/
	public String       ColumnSQL;
	/**	Default Value	*/
	public String       DefaultValue;
	/**	Description		*/
	public String       Description;
	/**	Entity Type		*/
	public String       EntityType;
	/** Field Length	*/
	public int			FieldLength;
	/**	Format Pattern	*/
	public String       FormatPattern;
	/**	Always Updateable*/
	public boolean      IsAlwaysUpdateable;
	/**	Is Centrally Maintained*/ 
	public boolean      IsCentrallyMaintained;
	/**	Encrypted		*/
	public boolean		IsEncrypted;
	/**	Identifier		*/
	public boolean		IsIdentifier;
	/**	PK				*/
	public boolean		IsKey;
	/**	Mandatory		*/
	public boolean      IsMandatory;
	/**	FK to Parent	*/
	public boolean		IsParent;
	/**	Selection Column*/
	public boolean		IsSelectionColumn;
	/**	Updateable		*/
	public boolean      IsUpdateable;
	/**	Name			*/
	public String       Name;
	/**	Selection Sequence*/
	public int 			SelectionSeqNo;
	/**	Sequence		*/
	public int 			SeqNo;
	/**	Sync Column ID	*/
	public int 			SPS_Column_ID;
	/**	Sync Table ID	*/
	public int 			SPS_Table_ID;
	/**	Max Value		*/
	public String		ValueMax;
	/**	Min Value		*/
	public String		ValueMin;
	/**	View Format		*/
	public String		VFormat;
	/**	Info Factory	*/
	public String 		InfoFactoryClass;
	/**	Process ID		*/
	public int 			AD_Process_ID;
	/**	Form ID			*/
	public int 			AD_Form_ID;
	/**	Allow Logging		*/
	public boolean		IsAllowLogging;
	
	/**
	 * Is Column SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 23:06:56
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(){
		return (ColumnSQL != null && ColumnSQL.length() != 0);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public POInfoColumn createFromParcel(Parcel parcel) {
			return new POInfoColumn(parcel);
		}
		public POInfoColumn[] newArray(int size) {
			return new POInfoColumn[size];
		}
	};
	
	@Override
	public int describeContents() {
		return hashCode();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(AD_Column_ID);
		dest.writeInt(AD_Element_ID);
		dest.writeInt(DisplayType);
		dest.writeInt(AD_Reference_Value_ID);
		dest.writeInt(AD_Val_Rule_ID);
		dest.writeString(Callout);
		dest.writeString(ColumnName);
		dest.writeString(ColumnSQL);
		dest.writeString(DefaultValue);
		dest.writeString(Description);
		dest.writeString(EntityType);
		dest.writeInt(FieldLength);
		dest.writeString(FormatPattern);
		dest.writeString(IsAlwaysUpdateable? "Y": "N");
		dest.writeString(IsCentrallyMaintained? "Y": "N");
		dest.writeString(IsEncrypted? "Y": "N");
		dest.writeString(IsIdentifier? "Y": "N");
		dest.writeString(IsKey? "Y": "N");
		dest.writeString(IsMandatory? "Y": "N");
		dest.writeString(IsParent? "Y": "N");
		dest.writeString(IsSelectionColumn? "Y": "N");
		dest.writeString(IsUpdateable? "Y": "N");
		dest.writeString(Name);
		dest.writeInt(SelectionSeqNo);
		dest.writeInt(SeqNo);
		dest.writeInt(SPS_Column_ID);
		dest.writeInt(SPS_Table_ID);
		dest.writeString(ValueMax);
		dest.writeString(ValueMin);
		dest.writeString(VFormat);
		dest.writeString(InfoFactoryClass);
		dest.writeInt(AD_Process_ID);
		dest.writeInt(AD_Form_ID);
		dest.writeString(IsAllowLogging? "Y": "N");
	}
	
	/**
	 * Read to Parcel
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 14:06:38
	 * @param parcel
	 * @return void
	 */
	public void readToParcel(Parcel parcel){
		AD_Column_ID = parcel.readInt();
		AD_Element_ID = parcel.readInt();
		DisplayType = parcel.readInt();
		AD_Reference_Value_ID = parcel.readInt();
		AD_Val_Rule_ID = parcel.readInt();
		Callout = parcel.readString();
		ColumnName = parcel.readString();
		ColumnSQL = parcel.readString();
		DefaultValue = parcel.readString();
		Description = parcel.readString();
		EntityType = parcel.readString();
		FieldLength = parcel.readInt();
		FormatPattern = parcel.readString();
		String bool = parcel.readString();
		IsAlwaysUpdateable = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsCentrallyMaintained = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsEncrypted = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsIdentifier = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsKey = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsMandatory = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsParent = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsSelectionColumn = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsUpdateable = (bool != null && bool.equals("Y"));
		Name = parcel.readString();
		SelectionSeqNo = parcel.readInt();
		SeqNo = parcel.readInt();
		SPS_Column_ID = parcel.readInt();
		SPS_Table_ID = parcel.readInt();
		ValueMax = parcel.readString();
		ValueMin = parcel.readString();
		VFormat = parcel.readString();
		InfoFactoryClass = parcel.readString();
		AD_Process_ID = parcel.readInt();
		AD_Form_ID = parcel.readInt();
		bool = parcel.readString();
		IsAllowLogging = (bool != null && bool.equals("Y"));
	}
	
	@Override
	public String toString() {
		return "POInfoColumn [ColumnName=" + ColumnName
				+ ", AD_Column_ID=" + AD_Column_ID
				+ ", AD_Element_ID=" + AD_Element_ID + ", DisplayType="
				+ DisplayType + ", AD_Reference_Value_ID="
				+ AD_Reference_Value_ID + ", AD_Val_Rule_ID=" + AD_Val_Rule_ID
				+ ", Callout=" + Callout
				+ ", ColumnSQL=" + ColumnSQL + ", DefaultValue=" + DefaultValue
				+ ", Description=" + Description + ", EntityType=" + EntityType
				+ ", FieldLength=" + FieldLength + ", FormatPattern="
				+ FormatPattern + ", IsAlwaysUpdateable=" + IsAlwaysUpdateable
				+ ", IsCentrallyMaintained=" + IsCentrallyMaintained
				+ ", IsEncrypted=" + IsEncrypted + ", IsIdentifier="
				+ IsIdentifier + ", IsKey=" + IsKey + ", IsMandatory="
				+ IsMandatory + ", IsParent=" + IsParent
				+ ", IsSelectionColumn=" + IsSelectionColumn
				+ ", IsUpdateable=" + IsUpdateable + ", Name=" + Name
				+ ", SelectionSeqNo=" + SelectionSeqNo + ", SeqNo=" + SeqNo
				+ ", SPS_Column_ID=" + SPS_Column_ID + ", SPS_Table_ID="
				+ SPS_Table_ID + ", ValueMax=" + ValueMax + ", ValueMin="
				+ ValueMin + ", VFormat=" + VFormat 
				+ ", InfoFactoryClass=" + InfoFactoryClass + ", AD_Process_ID="
				+ AD_Process_ID + ", AD_Form_ID=" + AD_Form_ID 
				+ ", IsAllowLogging=" + IsAllowLogging +"]";
	}

}	//	POInfoColumn
