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

import org.spinsuite.model.POInfoColumn;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class InfoReportField extends POInfoColumn {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 14:06:11
	 * @param parcel
	 */
	public InfoReportField(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 14:06:07
	 */
	public InfoReportField(){
		
	}
	
	/**	Print Color			*/
	public int	 		AD_PrintColor_ID = 0;
	/**	Print Font			*/
	public int	 		AD_PrintFont_ID = 0;
	/**	Print Format Child	*/
	public int	 		AD_PrintFormatChild_ID = 0;
	/**	Print Format Item	*/
	public int	 		AD_PrintFormatItem_ID = 0;
	/**	Print Format Graph	*/
	public int	 		AD_PrintGraph_ID = 0;
	/**	Arc Diameter		*/
	public int	 		ArcDiameter = 0;
	/**	Bar Code Type		*/
	public String		BarcodeType = null;
	/**	Below Column		*/
	public int	 		BelowColumn = 0;
	/**	Field Alignment Type*/
	public String		FieldAlignmentType = null;
	/**	Is Image Attached	*/
	public boolean		ImageIsAttached = false;
	/**	Image URL			*/
	public String		ImageURL = null;
	/**	Is Average			*/
	public boolean		IsAveraged = false;
	/**	Is Counted			*/
	public boolean		IsCounted = false;
	/**	Is Deviation Calc	*/
	public boolean		IsDeviationCalc = false;
	/**	Is Filled Rectangle	*/
	//	Not yet supported
	public boolean		IsFilledRectangle = false;
	/**	Is Fixed With		*/
	public boolean		IsFixedWidth = false;
	/**	Is Group By			*/
	public boolean		IsGroupBy = false;
	/**	Is Height One Line	*/
	public boolean		IsHeightOneLine = false;
	/**	Is Image Field		*/
	public boolean		IsImageField = false;
	/**	Is Maximum Calc		*/
	public boolean		IsMaxCalc = false;
	/**	Is Minimum Calc		*/
	public boolean		IsMinCalc = false;
	/**	Is Next Line		*/
	public boolean		IsNextLine = false;
	/**	Is Next Page		*/
	public boolean		IsNextPage = false;
	/**	Is Order By			*/
	public boolean		IsOrderBy = false;
	/**	Is Page Break		*/
	public boolean		IsPageBreak = false;
	/**	Is Printed			*/
	public boolean		IsPrinted = false;
	/**	Is Relative Position*/
	public boolean		IsRelativePosition = false;
	/**	Is Runnung Total	*/
	public boolean		IsRunningTotal = false;
	/**	Is New Line Position*/
	public boolean		IsSetNLPosition = false;
	/**	Is Summarized		*/
	public boolean		IsSummarized = false;
	/**	Is SuppressNull		*/
	public boolean		IsSuppressNull = false;
	/**	Is Suppress Repeats	*/
	public boolean		IsSuppressRepeats = false;
	/**	Is Variance Calc	*/
	public boolean		IsVarianceCalc = false;
	/**	Line Alignment		*/
	public String		LineAlignmentType = null;
	/**	Line With			*/
	public int			LineWidth = 0;
	/**	Max Height			*/
	public int			MaxHeight = 0;
	/**	Max With			*/
	public int			MaxWidth = 0;
	/**	Field Name			*/
	public String 		FieldName = null;
	/**	Print Area Type		*/
	public String 		PrintAreaType = null;
	/**	Print Format Type	*/
	public String 		PrintFormatType = null;
	/**	Print Name			*/
	public String 		PrintName = null;
	/**	Print Name Suffix	*/
	public String 		PrintNameSuffix = null;
	/**	Running Total Lines	*/
	public int	 		RunningTotalLines = 0;
	/**	Shape Type			*/
	public String 		ShapeType = null;
	/**	Sort No				*/
	public int	 		SortNo = 0;
	/**	X Position			*/
	public int	 		XPosition = 0;
	/**	X Space				*/
	public int	 		XSpace = 0;
	/**	Y Position			*/
	public int	 		YPosition = 0;
	/**	Y Space				*/
	public int	 		YSpace = 0;	
	/**	Sequence			*/
	public int 			FieldSeqNo = 0;
	
	/**	Field Alignment Type*/
	/**	Block				*/
	public static final String 	FIELD_ALIGNMENT_TYPE_BLOCK 			= "B";
	/**	Center				*/
	public static final String 	FIELD_ALIGNMENT_TYPE_CENTER 		= "C";
	/**	Default				*/
	public static final String 	FIELD_ALIGNMENT_TYPE_DEFAULT 		= "D";
	/**	Leading (left)		*/
	public static final String 	FIELD_ALIGNMENT_TYPE_LEADING_LEFT 	= "L";
	/**	Trailing (right)	*/
	public static final String 	FIELD_ALIGNMENT_TYPE_TRAILING_RIGHT = "T";
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public InfoReportField createFromParcel(Parcel parcel) {
			return new InfoReportField(parcel);
		}
		public InfoReportField[] newArray(int size) {
			return new InfoReportField[size];
		}
	};
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeInt(AD_PrintColor_ID);
		dest.writeInt(AD_PrintFont_ID);
		dest.writeInt(AD_PrintFormatChild_ID);
		dest.writeInt(AD_PrintFormatItem_ID);
		dest.writeInt(AD_PrintGraph_ID);
		dest.writeInt(ArcDiameter);
		dest.writeString(BarcodeType);
		dest.writeInt(BelowColumn);
		dest.writeString(FieldAlignmentType);
		dest.writeString(ImageIsAttached? "Y": "N");
		dest.writeString(ImageURL);
		dest.writeString(IsAveraged? "Y": "N");
		dest.writeString(IsCounted? "Y": "N");
		dest.writeString(IsDeviationCalc? "Y": "N");
		dest.writeString(IsFilledRectangle? "Y": "N");
		dest.writeString(IsFixedWidth? "Y": "N");
		dest.writeString(IsGroupBy? "Y": "N");
		dest.writeString(IsHeightOneLine? "Y": "N");
		dest.writeString(IsImageField? "Y": "N");
		dest.writeString(IsMaxCalc? "Y": "N");
		dest.writeString(IsMinCalc? "Y": "N");
		dest.writeString(IsNextLine? "Y": "N");
		dest.writeString(IsNextPage? "Y": "N");
		dest.writeString(IsOrderBy? "Y": "N");
		dest.writeString(IsPageBreak? "Y": "N");
		dest.writeString(IsPrinted? "Y": "N");
		dest.writeString(IsRelativePosition? "Y": "N");
		dest.writeString(IsRunningTotal? "Y": "N");
		dest.writeString(IsSetNLPosition? "Y": "N");
		dest.writeString(IsSummarized? "Y": "N");
		dest.writeString(IsSuppressNull? "Y": "N");
		dest.writeString(IsSuppressRepeats? "Y": "N");
		dest.writeString(IsVarianceCalc? "Y": "N");
		dest.writeString(LineAlignmentType);
		dest.writeInt(LineWidth);
		dest.writeInt(MaxHeight);
		dest.writeInt(MaxWidth);
		dest.writeString(FieldName);
		dest.writeString(PrintAreaType);
		dest.writeString(PrintFormatType);
		dest.writeString(PrintName);
		dest.writeString(PrintNameSuffix);
		dest.writeInt(RunningTotalLines);
		dest.writeString(ShapeType);
		dest.writeInt(SortNo);
		dest.writeInt(XPosition);
		dest.writeInt(XSpace);
		dest.writeInt(YPosition);
		dest.writeInt(YSpace);
		dest.writeInt(FieldSeqNo);
	}
	
	@Override
	public void readToParcel(Parcel parcel){
		super.readToParcel(parcel);
		AD_PrintColor_ID = parcel.readInt();
		AD_PrintFont_ID = parcel.readInt();
		AD_PrintFormatChild_ID = parcel.readInt();
		AD_PrintFormatItem_ID = parcel.readInt();
		AD_PrintGraph_ID = parcel.readInt();
		ArcDiameter = parcel.readInt();
		BarcodeType = parcel.readString();
		BelowColumn = parcel.readInt();
		FieldAlignmentType = parcel.readString();
		String bool = parcel.readString();
		ImageIsAttached = (bool != null && bool.equals("Y"));
		ImageURL = parcel.readString();
		bool = parcel.readString();
		IsAveraged = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsCounted = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsDeviationCalc = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsFilledRectangle = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsFixedWidth = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsGroupBy = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsHeightOneLine = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsImageField = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsMaxCalc = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsMinCalc = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsNextLine = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsNextPage = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsOrderBy = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsPageBreak = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsPrinted = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsRelativePosition = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsRunningTotal = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsSetNLPosition = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsSummarized = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsSuppressNull = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsSuppressRepeats = (bool != null && bool.equals("Y"));
		bool = parcel.readString();
		IsVarianceCalc = (bool != null && bool.equals("Y"));
		LineAlignmentType = parcel.readString();
		LineWidth = parcel.readInt();
		MaxHeight = parcel.readInt();
		MaxWidth = parcel.readInt();
		FieldName = parcel.readString();
		PrintAreaType = parcel.readString();
		PrintFormatType = parcel.readString();
		PrintName = parcel.readString();
		PrintNameSuffix = parcel.readString();
		RunningTotalLines = parcel.readInt();
		ShapeType = parcel.readString();
		SortNo = parcel.readInt();
		XPosition = parcel.readInt();
		XSpace = parcel.readInt();
		YPosition = parcel.readInt();
		YSpace	 = parcel.readInt();
		FieldSeqNo = parcel.readInt();
	}
	
	@Override
	public String toString() {
		return "InfoReportField [AD_PrintColor_ID=" + AD_PrintColor_ID
				+ ", AD_PrintFont_ID=" + AD_PrintFont_ID
				+ ", AD_PrintFormatChild_ID=" + AD_PrintFormatChild_ID
				+ ", AD_PrintFormatItem_ID=" + AD_PrintFormatItem_ID
				+ ", AD_PrintGraph_ID=" + AD_PrintGraph_ID + ", ArcDiameter="
				+ ArcDiameter + ", BarcodeType=" + BarcodeType
				+ ", BelowColumn=" + BelowColumn + ", FieldAlignmentType="
				+ FieldAlignmentType + ", ImageIsAttached=" + ImageIsAttached
				+ ", ImageURL=" + ImageURL + ", IsAveraged=" + IsAveraged
				+ ", IsCounted=" + IsCounted + ", IsDeviationCalc="
				+ IsDeviationCalc + ", IsFilledRectangle=" + IsFilledRectangle
				+ ", IsFixedWidth=" + IsFixedWidth + ", IsGroupBy=" + IsGroupBy
				+ ", IsHeightOneLine=" + IsHeightOneLine + ", IsImageField="
				+ IsImageField + ", IsMaxCalc=" + IsMaxCalc + ", IsMinCalc="
				+ IsMinCalc + ", IsNextLine=" + IsNextLine + ", IsNextPage="
				+ IsNextPage + ", IsOrderBy=" + IsOrderBy + ", IsPageBreak="
				+ IsPageBreak + ", IsPrinted=" + IsPrinted
				+ ", IsRelativePosition=" + IsRelativePosition
				+ ", IsRunningTotal=" + IsRunningTotal + ", IsSetNLPosition="
				+ IsSetNLPosition + ", IsSummarized=" + IsSummarized
				+ ", IsSuppressNull=" + IsSuppressNull + ", IsSuppressRepeats="
				+ IsSuppressRepeats + ", IsVarianceCalc=" + IsVarianceCalc
				+ ", LineAlignmentType=" + LineAlignmentType + ", LineWidth="
				+ LineWidth + ", MaxHeight=" + MaxHeight + ", MaxWidth="
				+ MaxWidth + ", FieldName=" + FieldName + ", PrintAreaType="
				+ PrintAreaType + ", PrintFormatType=" + PrintFormatType
				+ ", PrintName=" + PrintName + ", PrintNameSuffix="
				+ PrintNameSuffix + ", RunningTotalLines=" + RunningTotalLines
				+ ", ShapeType=" + ShapeType + ", SortNo=" + SortNo
				+ ", XPosition=" + XPosition + ", XSpace=" + XSpace
				+ ", YPosition=" + YPosition + ", YSpace=" + YSpace
				+ ", FieldSeqNo=" + FieldSeqNo + "]";
	}
}
