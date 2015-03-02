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

import org.spinsuite.model.POInfoColumn;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class InfoPara extends POInfoColumn {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/03/2014, 14:06:11
	 * @param parcel
	 */
	public InfoPara(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/03/2014, 14:06:07
	 */
	public InfoPara(){
		
	}

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
	/**	Range				*/
	public boolean		IsRange = false;
	/**	Sequence			*/
	public int 			FieldSeqNo = 0;
	/**	Default Value 2		*/
	public String		DefaultValue2 = null;
	/**	Process Parameter	*/
	public int 			AD_Process_Para_ID = 0;
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public InfoPara createFromParcel(Parcel parcel) {
			return new InfoPara(parcel);
		}
		public InfoPara[] newArray(int size) {
			return new InfoPara[size];
		}
	};
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(DisplayLogic);
		dest.writeString(Help);
		dest.writeString(IsActive? "Y": "N");
		dest.writeString(IsDisplayed? "Y": "N");
		dest.writeString(IsReadOnly? "Y": "N");
		dest.writeString(IsRange? "Y": "N");
		dest.writeInt(FieldSeqNo);
		dest.writeString(DefaultValue2);
		dest.writeInt(AD_Process_Para_ID);
	}
	
	@Override
	public void readToParcel(Parcel parcel){
		super.readToParcel(parcel);
		DisplayLogic = parcel.readString();
		Help = parcel.readString();
		String bool = parcel.readString();
		IsActive = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsDisplayed = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsReadOnly = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsRange = (bool != null && bool.equals("Y"));
		FieldSeqNo = parcel.readInt();
		DefaultValue2 = parcel.readString();
		AD_Process_Para_ID = parcel.readInt();
	}

}
