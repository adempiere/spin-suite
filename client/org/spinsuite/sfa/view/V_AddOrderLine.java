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
package org.spinsuite.sfa.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.model.I_C_OrderLine;
import org.spinsuite.model.MOrderLine;
import org.spinsuite.sfa.adapters.LP_SearchAdapter;
import org.spinsuite.sfa.util.DisplayListProduct;
import org.spinsuite.util.LogM;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 12:17:53
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class V_AddOrderLine extends Activity {
	
	/**	List View					*/
	private ListView				lv_Products = null;
	/**	Activity					*/
	private Activity				v_activity = null;
	/**	Adapter						*/
	private LP_SearchAdapter 		m_SP_SearchAdapter = null;
	/**	View Search					*/
	private View 					searchView = null;
	/**	Technical Form				*/
	private int						m_C_Order_ID = 0;
	/**	Data Result					*/
	private ArrayList<DisplayListProduct>	selectedData = null;
	
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_ol_add_product);
		//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			m_C_Order_ID = bundle.getInt("C_Order_ID");
		}
		//	Set Activity
		v_activity = this;
		
		lv_Products = (ListView) v_activity.findViewById(R.id.lv_Products);
		lv_Products.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
		lv_Products.setStackFromBottom(true);
		//
		new LoadViewTask().execute();
		//	Listener
		lv_Products.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				//	Load from Action
			}
        });
		//	Listen Scroll
		lv_Products.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//	
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//	
			}
		});
	}
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//	Inflate menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_action_ok, menu);
		//	Get Item
		MenuItem item = menu.findItem(R.id.action_search);
		MenuItem mi_Config = menu.findItem(R.id.action_config);
		mi_Config.setVisible(false);
		//	Search View
		searchView = SearchViewCompat.newSearchView(this);
		if (searchView != null) {
			//	Set Back ground Color
			int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
			EditText searchText = (EditText) searchView.findViewById(id);
			//	Set Parameters
			if(searchText != null)
				searchText.setTextAppearance(this, R.style.TextSearch);
			//	
			SearchViewCompat.setOnQueryTextListener(searchView,
					new OnQueryTextListenerCompat() {
				@Override
				public boolean onQueryTextChange(String newText) {
					if(m_SP_SearchAdapter != null) {
						String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
						m_SP_SearchAdapter.getFilter().filter(mFilter);
					}
					return true;
				}
			});
			SearchViewCompat.setOnCloseListener(searchView,
					new OnCloseListenerCompat() {
				@Override
				public boolean onClose() {
					if (!TextUtils.isEmpty(SearchViewCompat.getQuery(searchView))) {
						SearchViewCompat.setQuery(searchView, null, true);
					}
					return true;
				}
                    
			});
			MenuItemCompat.setActionView(item, searchView);
		}
		//	Return
		return true;
	}
	    
	  
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.action_close:
			setResult(Activity.RESULT_CANCELED, getIntent());
			finish();
			return true;
		case R.id.action_ok:
			//	Hide Keyboard
			getWindow().setSoftInputMode(
				      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			//	Change Focus
			lv_Products.requestFocus();
			saveResult();
			return true;
		default:
			break;
		}
		//	
		return super.onOptionsItemSelected(item);
	}	
	
	/**
	 * On Selected Record
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void saveResult() {
		//	Set Result
		LP_SearchAdapter adapter = (LP_SearchAdapter) lv_Products.getAdapter();
		selectedData = adapter.getSelectedData();
		//	Load Task
		new SaveDataTask().execute();
	}
	
	/**
	 * Include Class Thread with load Order Line
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 15/6/2015, 15:36:36
	 *
	 */
	private class LoadViewTask extends AsyncTask<Void, Void, Void> {

		/**	Progress Bar			*/
		private ProgressDialog 					v_PDialog;
		/**	Data					*/
		private ArrayList<DisplayListProduct> 	data = null;
		
		/**
		 * Init Values
		 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
		 * @return void
		 */
		private void init() {
	    	//	Load Table Info
			data = new ArrayList<DisplayListProduct>();
			//	View
		}
		
		@Override
		protected void onPreExecute() {
			v_PDialog = ProgressDialog.show(v_activity, null, 
					getString(R.string.msg_Loading), false, false);
			//	Set Max
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			init();
			//	Load Data
			loadData();
			//	
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... progress) {
			
		}

		@Override
		protected void onPostExecute(Void result) {
			loadView();
			v_PDialog.dismiss();
		}

		/**
		 * Load View Objects
		 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
		 * @return
		 * @return boolean
		 */
	    protected boolean loadView() {
	    	//	Set Adapter
			m_SP_SearchAdapter = new LP_SearchAdapter(getApplicationContext(), data);
			lv_Products.setAdapter(m_SP_SearchAdapter);
			//	
			return true;
	    }
	    
	    /**
	     * Load Data
	     * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	     * @return void
	     */
		private void loadData() {
			try{
				//	
				DB conn = new DB(v_activity);
				DB.loadConnection(conn, DB.READ_ONLY);
				Cursor rs = null;
				//	Query
				String sql = new String("SELECT pc.M_Product_Category_ID, "
						+ "pc.Name, "
						+ "p.M_Product_ID, "
						+ "p.Value, "
						+ "p.Name, "
						+ "COALESCE(p.Description,'') Description, "
						+ "uo.C_UOM_ID, "
						+ "uo.UOMSymbol, "
						+ "tc.C_TaxCategory_ID, "
						+ "tc.Name, "
						+ "pp.PriceList, "
						+ "CASE WHEN p.M_Product_ID = ol.M_Product_ID THEN ol.QtyEntered ELSE NULL END QtyEntered, "
						+ "CASE WHEN p.M_Product_ID = ol.M_Product_ID THEN ol.QtyOrdered ELSE NULL END QtyOrdered, "
						+ "CASE WHEN p.M_Product_ID = ol.M_Product_ID THEN ol.PriceEntered ELSE pp.PriceList END PriceEntered, "
						+ "CASE WHEN p.M_Product_ID = ol.M_Product_ID THEN ol.LineNetAmt ELSE NULL END LineNetAmt, "
						+ "plv.M_PriceList_ID, "
						+ "plv.M_PriceList_Version_ID, "
						+ "MAX(plv.ValidFrom) ValidFrom, "
						+ "pl.C_Currency_ID, "
						+ "cu.CurSymbol, "
						+ "ol.C_OrderLine_ID "
						+ "FROM C_Order o "
						+ "INNER JOIN M_PriceList pl ON(pl.M_PriceList_ID = o.M_PriceList_ID) "
						+ "INNER JOIN M_PriceList_Version plv ON(plv.M_PriceList_ID = pl.M_PriceList_ID) "
						+ "INNER JOIN M_ProductPrice pp ON(pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID) "
						+ "INNER JOIN M_Product p ON(p.M_Product_ID = pp.M_Product_ID) "
						+ "INNER JOIN M_Product_Category pc ON(pc.M_Product_Category_ID = p.M_Product_Category_ID) "
						+ "INNER JOIN C_TaxCategory tc ON(tc.C_TaxCategory_ID = p.C_TaxCategory_ID) "
						+ "INNER JOIN C_Currency cu ON(cu.C_Currency_ID = pl.C_Currency_ID)"
						+ "INNER JOIN C_UOM uo ON (p.C_UOM_ID = uo.C_UOM_ID) "
						+ "LEFT JOIN C_OrderLine ol ON (ol.C_Order_ID = o.C_Order_ID AND ol.M_Product_ID = p.M_Product_ID) "
						+ "WHERE pl.M_PriceList_ID = ? "
						+ "GROUP BY p.M_Product_ID, p.Value, p.Name "
						+ "HAVING MAX(plv.ValidFrom) <= o.DateOrdered");
				//	Compile Query
				conn.compileQuery(sql);
				conn.addInt(m_C_Order_ID);
				//	Query
				rs = conn.querySQL();
				//	
				if(rs.moveToFirst()) {
					//	Loop
					do{
						int index = 0;
						data.add(
									new DisplayListProduct(
											rs.getInt(index++),						//	Product Category ID
											rs.getString(index++), 					//	Product Category Value
											rs.getInt(index++),						//	Product ID
											rs.getString(index++),					//	Product Value
											rs.getString(index++),					//	Product Name
											rs.getString(index++),					//	Product Description
											rs.getInt(index++),						//	UOM ID
											rs.getString(index++),					//	UOM Symbol
											rs.getInt(index++),						//	Tax Category ID
											rs.getString(index++),					//	Tax Category Value
											new BigDecimal(rs.getDouble(index++)),	//	Price List
											new BigDecimal(rs.getDouble(index++)),	//	Quantity Entered
											new BigDecimal(rs.getDouble(index++)),	//	Quantity Ordered
											new BigDecimal(rs.getDouble(index++)),	//	Price Entered
											new BigDecimal(rs.getDouble(index++)),	//	Line Net Amount
											rs.getInt(index++),						//	Price List ID
											rs.getInt(index++),						//	Price List Version ID
											new Date(rs.getLong(index++)),			//	Valid From
											rs.getInt(index++),						//	Currency ID
											rs.getString(index++),					//	Currency Value
											rs.getInt(index++)						//	Order Line ID
										)
								);
					}while(rs.moveToNext());
				}
				//	Close
				DB.closeConnection(conn);
			} catch(Exception e) {
				LogM.log(v_activity, getClass(), Level.SEVERE, "Error in Load", e);
			}
		}
	}
	
	/**
	 * Save data in thread
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 15/6/2015, 15:37:51
	 *
	 */
	private class SaveDataTask extends AsyncTask<Void, Void, Void> {

		/**	Progress Bar			*/
		private ProgressDialog 		v_PDialog;
		
		@Override
		protected void onPreExecute() {
			v_PDialog = ProgressDialog.show(v_activity, null, 
					getString(R.string.msg_Saving), false, false);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			//	Save Data
			try {
				saveData(selectedData);
			} catch (Exception e) {
				LogM.log(v_activity, V_AddOrderLine.class, Level.SEVERE, "Error", e);
			}
			//	
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... progress) {
			
		}

		@Override
		protected void onPostExecute(Void result) {
			v_PDialog.dismiss();
			v_activity.setResult(Activity.RESULT_OK, getIntent());
			//	Exit
			v_activity.finish();
		}
	    
		/**
		 * Save Data from Array
		 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
		 * @param data
		 * @throws Exception
		 * @return void
		 */
		private void saveData(ArrayList<DisplayListProduct> data) throws Exception {
//			BigDecimal p_TotalLines = Env.ZERO;
			//	Valid Null value
			if(data == null)
				return;
			int p_C_OrderLine_ID = 0;
			//	
			StringBuffer sqlDelete = new StringBuffer("DELETE FROM ")
							.append(I_C_OrderLine.Table_Name)
							.append(" WHERE ")
							.append(I_C_OrderLine.COLUMNNAME_C_Order_ID)
							.append(" = ?");
			//	SQL NOT IN
			StringBuffer sqlIn = new StringBuffer(" AND ")
				.append(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.append(" NOT IN(");
			//	
			boolean first = true;
			//	
			for(DisplayListProduct item : data) {
				//	Add Items
				p_C_OrderLine_ID = item.getC_OrderLine_ID();
				MOrderLine oLine = new MOrderLine(v_activity, p_C_OrderLine_ID, null);
				oLine.setC_Order_ID(m_C_Order_ID);
				oLine.setM_Product_ID(item.getM_Product_ID());
				oLine.setQtyEntered(item.getQtyEntered());
				oLine.setQtyOrdered(item.getQtyEntered());
				oLine.saveEx();
				//	Add IDs
				if(!first) {
					sqlIn.append(", ");
				} else if(first) {
					first = false;
				}
				//	
				sqlIn.append(oLine.getC_OrderLine_ID());
//				p_TotalLines = p_TotalLines.add(oLine.getLineNetAmt());
				//	
//				updateHeader(oLine.getCtx(),oLine,p_TotalLines, null);
			}
			//	Add finish
			sqlIn.append(")");
			//	Execute
			if(!first)
				sqlDelete.append(sqlIn);
			//	Just delete by line
			DB.executeUpdate(v_activity, sqlDelete.toString(), m_C_Order_ID, false);
			//	Log
			LogM.log(v_activity, T_OrderLine.class, Level.FINE, 
					"SQL Delete Order Line =" + sqlDelete.toString());
		}
	}
	
}








