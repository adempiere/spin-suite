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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.view.custom;

import java.util.ArrayList;

import org.spinsuite.base.DB;
import org.spinsuite.model.POInfo;
import org.spinsuite.util.DisplaySpinner;
import org.spinsuite.util.LoadDataSpinner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * @author Yamel Senih 
 *
 */
public class Cust_Spinner extends Spinner {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih 10/05/2012, 16:09:14
	 * @param context
	 */
	public Cust_Spinner(Context context) {
		super(context);
		//LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //layoutInflater.inflate(R.layout.e_spinner_load, this);
        init(context);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih 10/05/2012, 16:09:07
	 * @param context
	 * @param attrs
	 */
	public Cust_Spinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		//LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//layoutInflater.inflate(R.layout.e_spinner_load, this);
        init(context);
	}
	
	private Context 			ctx;
	private DisplaySpinner 		sp_CurrentRecord;
	private int 				record_ID;
	private String 				tableName;
	private String 				identifierName;
	private String 				whereClause;
	private boolean 			loader = false;
	private boolean 			firstSpace = false;
	private DB 				con;
    /**	Table Info	*/
    private POInfo 		tInfo = null;
    private String 				sql = null;
	
	private void init(Context ctx){
		isInEditMode();
		this.ctx = ctx;
	}
	
	/**
	 * Set Connection
	 * @author Yamel Senih 10/05/2012, 21:40:13
	 * @param con
	 * @return void
	 */
	public void setConnection(DB con){
		this.con = con;
	}
	
	/**
	 * Set ID for show
	 * @author Yamel Senih 10/05/2012, 16:30:26
	 * @param record_ID
	 * @return void
	 */
	public void setRecord_ID(int record_ID){
		this.record_ID = record_ID;
		load(true);
	}
	
	/**
	 * Load Spinner from SQL
	 * @author Yamel Senih 21/08/2012, 19:25:25
	 * @param requery
	 * @return void
	 */
	public void load(boolean requery){
		if(!loader){
			if(sql == null)
				sql = getSql();
			
			String where = null;
			
			/*if(record_ID != 0){
				where = new String("WHERE " + tableName + "_ID = " + record_ID);
			}*/
			
			if(whereClause != null){
				if(where == null){
					where = new String("WHERE " + whereClause);
				}/* else {
					where += " AND " + whereClause;
				}*/
			}
			
			String sqlNew = sql + (where != null && where.length() > 0? where: "");
			
			//Msg.toastMsg(getContext(), " " + sqlNew + "   " + record_ID);
			if(sql != null){
				if(con != null)
					if(!con.isOpen()){
						con.openDB(DB.READ_ONLY);
						LoadDataSpinner.load(ctx, con, this, sqlNew, false, firstSpace);
						con.closeDB(null);
					} else {
						LoadDataSpinner.load(ctx, con, this, sqlNew, false, firstSpace);
					}
				else
					LoadDataSpinner.load(ctx, this, sqlNew, false, firstSpace);
				sp_CurrentRecord = (DisplaySpinner) getSelectedItem();
				//if(record_ID == 0){
					loader = true;
				//}
				
			}
		}
		//	Set Position
		setIDSpinner(this, record_ID);
	}
	
	
	/**
	 * get SQL
	 * en el objeto
	 * @author Yamel Senih 10/05/2012, 16:46:48
	 * @return
	 * @return String
	 */
	private String getSql(){
		if(tableName == null)
			return null;
		StringBuffer sql = new StringBuffer("SELECT ");
		
		sql = new StringBuffer();
		/*String sqlWhere = new String("(AD_Column.ColumnName IN('DocumentNo', 'Name', 'Description', 'DocAction', 'IsActive', 'ImgName') " +
		    	"OR AD_Column.IsIdentifier = 'Y') ");
		    String sqlOrderBy = new String("AD_Column.SeqNo, AD_Column.Name");*/
		sql.append("SELECT " + tableName + "_ID, ");
		if(identifierName != null){
			sql.append(identifierName + " ");
		} else {
			if(tInfo == null){
				if(con != null){
					//tInfo = new POInfo(con, 0, tableName, sqlWhere, sqlOrderBy, false);
				} else {
					if(con == null){
						con = new DB(ctx);
					}
					//tInfo = new POInfo(con, 0, tableName, sqlWhere, sqlOrderBy, true);
				}	
			}
			
			for(int i = 0; i < tInfo.getColumnLength(); i++){
				if(i > 0)
					sql.append(" || ");
				sql.append("IFNULL(" + POInfo.getColumnNameForSelect(ctx, tInfo, i) + ", '')");
			}
		}
		
		sql.append(" FROM " + tableName + " ");
		
		return sql.toString();
	}
		
	/**
	 * Load data from ArrayList
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 17:34:13
	 * @param data
	 * @return void
	 */
	public void load(ArrayList <DisplaySpinner> data){
		ArrayAdapter<DisplaySpinner> sp_adapter = new ArrayAdapter<DisplaySpinner>(ctx, android.R.layout.simple_spinner_item, data);
		sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		setAdapter(sp_adapter);
	}
	
	/**
	 * Set Table Name
	 * @author Yamel Senih 10/05/2012, 16:39:23
	 * @param tableName
	 * @return void
	 */
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	
	/**
	 * Get Table Name
	 * @author Yamel Senih 21/05/2012, 03:02:06
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return tableName;
	}
	
	/**
	 * Set Name First Identifier
	 * @author Yamel Senih 10/05/2012, 16:40:50
	 * @param identifierName
	 * @return void
	 */
	public void setIdentifierName(String identifierName){
		this.identifierName = identifierName;
	}
	
	/**
	 * Set Where Clause
	 * @author Yamel Senih 10/05/2012, 16:45:37
	 * @param whereClause
	 * @return void
	 */
	public void setWhereClause(String whereClause){
		this.whereClause = whereClause;
	}
	
	/**
	 * Set if First Space
	 * @author Yamel Senih 15/05/2012, 03:35:18
	 * @param firstSpace
	 * @return void
	 */
	public void setFirstSpace(boolean firstSpace){
		this.firstSpace = firstSpace;
	}
	
	/**
	 * is Mandatory
	 * @author Yamel Senih 15/05/2012, 03:36:18
	 * @return
	 * @return boolean
	 */
	public boolean isFirstSpace(){
		return firstSpace;
	}
	
	/**
	 * Get Record ID
	 * @author Yamel Senih 10/05/2012, 17:57:52
	 * @return
	 * @return int
	 */
	public int getID(){
		DisplaySpinner ds = (DisplaySpinner)getSelectedItem();
		if(ds != null){
			return ds.getID();
		}
		return 0;
	}
	
	/**
	 * Get ID from position
	 * @author Yamel Senih 21/05/2012, 02:46:25
	 * @param position
	 * @return
	 * @return int
	 */
	public int getID(int position){
		DisplaySpinner ds = (DisplaySpinner)getItemAtPosition(position);
		if(ds != null){
			return ds.getID();
		}
		return 0;
	}
	
	/**
	 * Get Value
	 * @author Yamel Senih 10/05/2012, 17:59:10
	 * @return
	 * @return String
	 */
	public String getValue(){
		return sp_CurrentRecord.getValue();
	}
	
	/**
	 * Set Loader
	 * @author Yamel Senih 09/08/2012, 20:42:55
	 * @param loader
	 * @return void
	 */
	public void setLoader(boolean loader){
		this.loader = loader;
	}
	
	/**
	 * Is Loaded?
	 * @author Yamel Senih 09/08/2012, 20:43:46
	 * @return
	 * @return boolean
	 */
	public boolean isLoader(){
		return loader;
	}
	
	/**
	 * Set Position from ID
	 * @author Yamel Senih 21/08/2012, 18:42:41
	 * @param sp
	 * @param record_ID
	 * @return
	 * @return boolean
	 */
	private boolean setIDSpinner(Spinner sp, int record_ID){
		int pos = 0;
		for(int i = 0; i < sp.getCount(); i++){
			DisplaySpinner ds = (DisplaySpinner)sp.getItemAtPosition(i);
			if(ds.getID() == record_ID){
				pos = i;
				break;
			}
		}
		sp.setSelection(pos);
		return (pos != 0);
	}
	
	/**
	 * get Position from value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 17:15:43
	 * @param value
	 * @return
	 * @return int
	 */
	public int getPositionFromValue(String value){
		int pos = 0;
		for(int i = 0; i < getCount(); i++){
			DisplaySpinner ds = (DisplaySpinner)getItemAtPosition(i);
			if(ds.getValue().equals(value)){
				pos = i;
				break;
			}
		}
		return pos;
	}
	
	/**
	 * Get position from hidden value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 17:15:56
	 * @param hiddenValue
	 * @return
	 * @return int
	 */
	public int getPositionFromHiddenValue(String hiddenValue){
		int pos = 0;
		for(int i = 0; i < getCount(); i++){
			DisplaySpinner ds = (DisplaySpinner)getItemAtPosition(i);
			if(ds.getHiddenValue().equals(hiddenValue)){
				pos = i;
				break;
			}
		}
		return pos;
	}
	
	/**
	 * Select a Item from Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 17:22:54
	 * @param value
	 * @return void
	 */
	public void setSelectedValue(String value){
		int position = getPositionFromValue(value);
		setSelection(position);
	}
	
	/**
	 * Selected a Item from Hidden Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 17:24:07
	 * @param hiddenValue
	 * @return void
	 */
	public void setSelectedHiddenValue(String hiddenValue){
		int position = getPositionFromHiddenValue(hiddenValue);
		setSelection(position);
	}
}
