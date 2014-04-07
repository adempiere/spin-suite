/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class FilterValue implements Parcelable {

	/**	Where					*/
	private String 				whereClause = null;
	/**	Values					*/
	private ArrayList<String> 	values = new ArrayList<String>();
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public FilterValue createFromParcel(Parcel parcel) {
			return new FilterValue(parcel);
		}
		public FilterValue[] newArray(int size) {
			return new FilterValue[size];
		}
	};
	
	public FilterValue(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	public FilterValue(){
		//	
	}
	
	@Override
	public int describeContents() {
		return hashCode();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(whereClause);
		dest.writeList(values);
	}
	
	public void readToParcel(Parcel parcel){
		whereClause = parcel.readString();
		values = new ArrayList<String>();
	    parcel.readList(values, getClass().getClassLoader());
		
	}
	
	/**
	 * Set Where Clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 21:20:48
	 * @param whereClause
	 * @return void
	 */
	public void setWhereClause(String whereClause){
		this.whereClause = whereClause;
	}
	
	/**
	 * Add value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 21:21:26
	 * @param value
	 * @return void
	 */
	public void addValue(Object value){
		if(value != null){
			values.add(String.valueOf(value));
		}
	}
	
	/**
	 * Get Where Clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 21:22:18
	 * @return
	 * @return String
	 */
	public String getWhereClause(){
		return whereClause;
	}
	
	/**
	 * Get Values
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 21:23:20
	 * @return
	 * @return String[]
	 */
	public String[] getValues(){
		String[] valuesToReturn = new String[values.size()];
		values.toArray(valuesToReturn);
		return valuesToReturn;
	}

	@Override
	public String toString() {
		return "FilterValue [whereClause=" + whereClause + ", values=" + values
				+ "]";
	}
	
}
