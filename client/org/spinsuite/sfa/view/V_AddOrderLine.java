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
import java.util.logging.Level;

import org.spinsuite.adapters.SP_SearchAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.model.I_C_OrderLine;
import org.spinsuite.model.I_M_Product;
import org.spinsuite.model.MOrder;
import org.spinsuite.model.MOrderLine;
import org.spinsuite.model.MOrderTax;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SP_DisplayRecordItem;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.InfoField;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

/**
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 12:17:53
 *
 */
public class V_AddOrderLine extends Activity {

	/**	Main Layout					*/
	private LinearLayout			ll_ConfigSearch = null;
	/**	List View					*/
	private ListView				lv_Products = null;
	/**	Activity					*/
	private Activity				v_activity = null;
	/**	Adapter						*/
	private SP_SearchAdapter 		m_SP_SearchAdapter = null;
	/**	Criteria					*/
	private FilterValue				m_criteria = null;
	/**	Parameter					*/
	private LayoutParams			v_param	= null;
	/**	View Weight					*/
	private static final float 		WEIGHT = 1;
	/**	Lookup of Farming Stage		*/
	private GridField	 			lookupProductCategory = null; 
	/**	Tab Parameter				*/
	private TabParameter	 		tabParam = null;
	/**	Old Value Farming Stage		*/
	private int						m_OldValueProductCategory_ID = 0;
	/**	View Search					*/
	private View 					searchView = null;
	/**	Technical Form				*/
	private int						m_C_Order_ID = 0;
	/**	Data Result					*/
	private ArrayList<SP_DisplayRecordItem>	selectedData = null;
	
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_add_product);
		//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			tabParam = (TabParameter)bundle.getParcelable("TabParam");
			m_C_Order_ID = bundle.getInt("C_Order_ID");
		}
		//	Set Activity
		v_activity = this;
		
		ll_ConfigSearch = (LinearLayout) v_activity.findViewById(R.id.ll_ConfigSearch);
		lv_Products = (ListView) v_activity.findViewById(R.id.lv_Products);
		//	
		loadConfig();
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
				lv_Products.requestFocus();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//	
			}
		});
	}
	

	/**
	 * Load Config
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void loadConfig() {
		//	Set Parameter
		v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT, WEIGHT);
		//	Add Fields
		addView();
		//	Hide
		ll_ConfigSearch.setVisibility(LinearLayout.GONE);
	}
	
	/**
	 * Add View to Config Panel
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void addView() {
		//	Product Category
		lookupProductCategory = GridField.createLookup(this,  
					I_M_Product.Table_Name, 
					I_M_Product.COLUMNNAME_M_Product_Category_ID,
					tabParam);

		//	is Filled
		if(lookupProductCategory != null) {
			ll_ConfigSearch.addView(lookupProductCategory, v_param);
			
		}
    }
	
	/**
	 * Add Criteria Query
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void addCriteriaQuery() {
		m_criteria = new FilterValue();
    	//	Get Values
		StringBuffer sqlWhere = new StringBuffer();
    	//	Only Filled
		if(!lookupProductCategory.isEmpty()) {
			InfoField field = lookupProductCategory.getField();
			//	Set to Model
			if(sqlWhere.length() > 0)
				sqlWhere.append(" AND ");
			//	Add Criteria Column Filter
			sqlWhere.append("p.")
					.append(field.ColumnName)
					.append(" = ? ");
			//	Add Value
			m_criteria.addValue(DisplayType.getJDBC_Value( field.DisplayType, lookupProductCategory.getValue()));
		}
    	//	Add SQL
    	m_criteria.setWhereClause(sqlWhere.toString());
	}
	
	/**
	 * Search Record
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void search() {
		//	Add Criteria
		addCriteriaQuery();
		//	Load New
		if(m_OldValueProductCategory_ID != lookupProductCategory.getValueAsInt()) {
			//	Set New Criteria
			new LoadViewTask().execute();
		}
	}
	
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//	Inflate menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_action_ok, menu);
		//	Get Item
		MenuItem item = menu.findItem(R.id.action_search);
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
		if(itemId == R.id.action_close) {
			setResult(Activity.RESULT_CANCELED, getIntent());
			finish();
			return true;
		} else if (itemId == R.id.action_config) {
			//	Show
			if(ll_ConfigSearch.getVisibility() == LinearLayout.GONE) {
				ll_ConfigSearch.setVisibility(LinearLayout.VISIBLE);
				m_OldValueProductCategory_ID = lookupProductCategory.getValueAsInt();
			} else {
				ll_ConfigSearch.setVisibility(LinearLayout.GONE);
				//	Search
				search();
			}
			return true;
		} else if(itemId == R.id.action_ok) {
			//	Hide Keyboard
			getWindow().setSoftInputMode(
				      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			//	Change Focus
			lv_Products.requestFocus();
			saveResult();
			return true;
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
		SP_SearchAdapter adapter = (SP_SearchAdapter) lv_Products.getAdapter();
		selectedData = adapter.getSelectedData();
		//	Load Task
		new SaveDataTask().execute();
	}
	
	/**
	 * Get SQL from Parameters
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param criteria
	 * @return
	 * @return String
	 */
	private String getSQL(FilterValue criteria) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT p.M_Product_ID, p.Value, p.Name, COALESCE(p.Description,'') Description, uo.UOMSymbol, "
				+ "CASE WHEN p.M_Product_ID = oll.M_Product_ID THEN oll.QtyEntered ELSE NULL END QtyEntered, oll.C_OrderLine_ID "
				+ "FROM M_Product p "
				+ "INNER JOIN C_UOM uo ON (p.C_UOM_ID = uo.C_UOM_ID) "
				+ "LEFT JOIN C_OrderLine oll ON (oll.M_Product_ID = p.M_Product_ID AND oll.C_Order_ID = " + m_C_Order_ID + ")");
		
		//	Add Criteria
		if(criteria != null
				&& criteria.getWhereClause() != null
				&& criteria.getWhereClause().length() > 0)
			sql.append(" AND ").append(criteria.getWhereClause());
		//	
		return sql.toString();
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
		private ArrayList<SP_DisplayRecordItem> data = null;
		
		/**
		 * Init Values
		 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
		 * @return void
		 */
		private void init() {
	    	//	Load Table Info
			data = new ArrayList<SP_DisplayRecordItem>();
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
			m_SP_SearchAdapter = new SP_SearchAdapter(getApplicationContext(), data);
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
				String[] values = null;
				if(m_criteria != null) {
					values = m_criteria.getValues();
				}	
				//	
				rs = conn.querySQL(getSQL(m_criteria), values);
				//	
				if(rs.moveToFirst()) {
					//	Loop
					do{
						int index = 0;
						data.add(
									new SP_DisplayRecordItem(
											rs.getInt(index++),	//	Product ID
											rs.getString(index++),	//	Product Value
											rs.getString(index++),	//	Product Name
											rs.getString(index++),	//	Product Description
											rs.getString(index++),	//	UOM Symbol
											new BigDecimal(rs.getDouble(index++)),
											rs.getInt(index++)
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
		private void saveData(ArrayList<SP_DisplayRecordItem> data) throws Exception {
			BigDecimal p_TotalLines = Env.ZERO;
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
			for(SP_DisplayRecordItem item : data) {
				//	Add Items
				p_C_OrderLine_ID = item.getP_C_OrderLine_ID();
				MOrderLine oLine = new MOrderLine(v_activity, p_C_OrderLine_ID, null);
				oLine.setC_Order_ID(m_C_Order_ID);
				oLine.setM_Product_ID(item.getProduct_ID());
				oLine.setQtyEntered(item.getQty());
				oLine.setQtyOrdered(item.getQty());
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
			LogM.log(v_activity, LV_COrderLine.class, Level.FINE, 
					"SQL Delete Order Line =" + sqlDelete.toString());
		}

		/**
		 * Update Order Header
		 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
		 * @param ctx
		 * @param oLine
		 * @param p_TotalLines
		 * @param conn
		 * @return void
		 */
		private void updateHeader(Context ctx, MOrderLine oLine,BigDecimal p_TotalLines, DB conn) {
			MOrder order = new MOrder(ctx, oLine.getC_Order_ID(), conn);
			updateOrderTax(order, oLine, true);
			order.setTotalLines(p_TotalLines);
			if(isTaxIncluded(ctx, conn, oLine.getC_Order_ID()))
				order.setGrandTotal(p_TotalLines);
			else {
				String sql = "SELECT COALESCE(SUM(it.TaxAmt),0) "
						+ "FROM C_OrderTax it "
						+ "WHERE it.C_Order_ID = " + order.getC_Order_ID();
				BigDecimal taxAmt = new BigDecimal(DB.getSQLValueString(ctx, sql));
				order.setGrandTotal(p_TotalLines.add(taxAmt));
			}
			
			try {
				order.saveEx();
			} catch (Exception e) {;}
		}
		
		/**
		 *	Is Tax Included in Amount
		 *  @param conn
		 *	@return true if tax calculated
		 */
		public boolean isTaxIncluded(Context ctx, DB conn, int p_C_Order_ID) {
			String m_IsTaxIncluded = DB.getSQLValueString(ctx,
					"SELECT pl.IsTaxIncluded "
					+ "FROM C_Order o "
					+ "INNER JOIN M_PriceList pl ON(pl.M_PriceList_ID = o.M_PriceList_ID) "
					+ "WHERE o.C_Order_ID = ?",
					conn, 
					String.valueOf(p_C_Order_ID));
			//	Verify if Tax Include
			return m_IsTaxIncluded != null && m_IsTaxIncluded.equals("Y");
		}	//	isTaxIncluded
		
		/**
		 * Recalculate order tax
		 * @param oldTax true if the old C_Tax_ID should be used
		 * @return true if success, false otherwise
		 * 
		 * @author teo_sarca [ 1583825 ]
		 */
		private boolean updateOrderTax(MOrder order, MOrderLine orderLine, boolean oldTax) {
			MOrderTax tax = MOrderTax.get (order.getCtx(), orderLine, order.getPrecision(), oldTax, null);
			if (tax != null) {
				if (!tax.calculateTaxFromLines())
					return false;
				if (tax.getTaxAmt().signum() != 0) {
					if (!tax.save())
						return false;
				}
				else {
					if (!tax.isNew() 
							&& !tax.delete())
						return false;
				}
			}
			return true;
		}
	}
	
}








