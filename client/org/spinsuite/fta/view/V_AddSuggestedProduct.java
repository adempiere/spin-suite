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
package org.spinsuite.fta.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.fta.adapters.SP_SearchAdapter;
import org.spinsuite.base.R;
import org.spinsuite.fta.util.SP_DisplayRecordItem;
import org.spinsuite.model.I_FTA_ProductsToApply;
import org.spinsuite.model.I_FTA_SuggestedProduct;
import org.spinsuite.model.MFTAProductsToApply;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.VLookupCheckBox;

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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class V_AddSuggestedProduct extends Activity {
	
	/**	Adapter						*/
	private SP_SearchAdapter 		m_SP_SearchAdapter = null;
	/**	Main Layout					*/
	private LinearLayout			ll_ConfigSearch = null;
	/**	List View					*/
	private ListView				lv_SuggestedProducts = null;
	/**	Technical Form				*/
	private int						m_FTA_TechnicalForm_ID = 0;
	/**	Technical Form Line			*/
	private int						m_FTA_TechnicalFormLine_ID = 0;
	/**	Farming 					*/
	private int						m_FTA_Farming_ID = 0;
	/**	Criteria					*/
	private FilterValue				m_criteria = null;
	/**	Lookup of Check Box			*/
	private VLookupCheckBox 		lookupSugg_Applied = null;
	/**	Lookup of Farming Stage		*/
	private GridField	 			lookupFarmingStage = null; 
	/**	Lookup of Observation Type	*/
	private GridField	 			lookupObservationType = null; 
	/**	Parameter					*/
	private LayoutParams			v_param	= null;
	/**	Activity					*/
	private Activity				v_activity = null;
	/**	Tab Parameter				*/
	private TabParameter	 		tabParam = null;
	/**	Suggested Old				*/
	private boolean					m_IsSuggestedOld = false;
	/**	Old Value Farming Stage		*/
	private int						m_OldValueFarmingStage_ID = 0;
	/**	Old Value Observation Type	*/
	private int						m_OldValueObservationType_ID = 0;
	/**	View Search					*/
	private View 					searchView = null;
	/**	View Weight					*/
	private static final float 		WEIGHT = 1;
	/**	Data Result					*/
	private ArrayList<SP_DisplayRecordItem>	selectedData = null;
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_add_suggested_product);
    	//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			tabParam = (TabParameter)bundle.getParcelable("TabParam");
			m_FTA_TechnicalFormLine_ID = bundle.getInt("FTA_TechnicalFormLine_ID");
			m_FTA_TechnicalForm_ID = bundle.getInt("FTA_TechnicalForm_ID");
			m_FTA_Farming_ID = bundle.getInt("FTA_Farming_ID");
			//m_IsApplied = bundle.getBoolean("IsApplied");
		}
		//	Set Activity
		v_activity = this;
		//	
		ll_ConfigSearch = (LinearLayout) findViewById(R.id.ll_ConfigSearch);
		lv_SuggestedProducts = (ListView) findViewById(R.id.lv_SuggestedProducts);
		lv_SuggestedProducts.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
		//	
		loadConfig();
		
		//	Load
		new LoadViewTask().execute();
    	//	Listener
		lv_SuggestedProducts.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				//	Load from Action
			}
        });
		//	Listen Scroll
		lv_SuggestedProducts.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
					m_SP_SearchAdapter.setCurrentValue();
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//	
			}
		});
		//	Set Subtitle
		getActionBar().setSubtitle(getString(R.string.Suggested));
	}
	
	/**
	 * Load Config
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/03/2014, 22:07:43
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
	 * Add Criteria Query
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 10:28:14
	 * @return void
	 */
	private void addCriteriaQuery() {
		m_criteria = new FilterValue();
    	//	Get Values
		StringBuffer sqlWhere = new StringBuffer();
    	//	Only Filled
		if(!lookupFarmingStage.isEmpty()) {
			InfoField field = lookupFarmingStage.getField();
			//	Set to model
			if(sqlWhere.length() > 0)
				sqlWhere.append(" AND ");
			//	Add Criteria Column Filter
			sqlWhere.append("fsp.").append(field.ColumnName).append(" = ?");
			//	Add Value
			m_criteria.addValue(DisplayType.getJDBC_Value(field.DisplayType, 
					lookupFarmingStage.getValue()));
		} else if(!lookupObservationType.isEmpty()) {
			InfoField field = lookupObservationType.getField();
			//	Set to model
			if(sqlWhere.length() > 0)
				sqlWhere.append(" AND ");
			//	Add Criteria Column Filter
			sqlWhere.append("fsp.").append(field.ColumnName).append(" = ?");
			//	Add Value
			m_criteria.addValue(DisplayType.getJDBC_Value(field.DisplayType, 
					lookupObservationType.getValue()));
		}
		
    	//	Add SQL
    	m_criteria.setWhereClause(sqlWhere.toString());
	}
	
	/**
	 * Add View to Config Panel
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/03/2014, 22:10:08
	 * @return void
	 */
	private void addView() {
    	//	Suggested
		InfoField field = GridField.loadInfoColumnField(this, 
				I_FTA_SuggestedProduct.Table_Name, 
				I_FTA_SuggestedProduct.COLUMNNAME_IsActive);
		field.ColumnName = "Suggested";
		field.Name = getString((m_FTA_Farming_ID > 0
										? R.string.Applied
										: R.string.Suggested));
		
    	lookupSugg_Applied = (VLookupCheckBox) GridField.createLookup(this, field);
		//	is Filled
		if(lookupSugg_Applied != null) {
			ll_ConfigSearch.addView(lookupSugg_Applied, v_param);
			//	Set Enabled
			lookupSugg_Applied.setEnabled(!(m_FTA_Farming_ID > 0));
			//	Set Default
			lookupSugg_Applied.setValue(m_FTA_TechnicalFormLine_ID > 0);
		}
		//	Farming Stage
		lookupFarmingStage = GridField.createLookup(this, 
				I_FTA_SuggestedProduct.Table_Name, 
				I_FTA_SuggestedProduct.COLUMNNAME_FTA_FarmingStage_ID, tabParam);
		//	is Filled
		if(lookupFarmingStage != null) {
			ll_ConfigSearch.addView(lookupFarmingStage, v_param);
			lookupFarmingStage.setEnabled(!(m_FTA_Farming_ID > 0));
		}
		//	Observation Type
		lookupObservationType = GridField.createLookup(this, 
				I_FTA_SuggestedProduct.Table_Name, 
				I_FTA_SuggestedProduct.COLUMNNAME_FTA_ObservationType_ID, tabParam);
		//	is Filled
		if(lookupObservationType != null) {
			ll_ConfigSearch.addView(lookupObservationType, v_param);
			lookupObservationType.setEnabled(!(m_FTA_Farming_ID > 0));
		}
    }
	
	/**
	 * Search Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/08/2014, 14:26:10
	 * @return void
	 */
	private void search() {
		//	Add Criteria
		if(lookupSugg_Applied.getValueAsBoolean())
			addCriteriaQuery();
		//	Load New
		if(m_IsSuggestedOld != lookupSugg_Applied.getValueAsBoolean() 
				|| m_OldValueFarmingStage_ID != lookupFarmingStage.getValueAsInt()
				|| m_OldValueObservationType_ID != lookupObservationType.getValueAsInt()) {
			//	Set New Criteria
			new LoadViewTask().execute();
		}
	}
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//	Inflate menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sp_search, menu);
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
				m_IsSuggestedOld = lookupSugg_Applied.getValueAsBoolean();
				m_OldValueFarmingStage_ID = lookupFarmingStage.getValueAsInt();
				m_OldValueObservationType_ID = lookupObservationType.getValueAsInt();
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
			lv_SuggestedProducts.requestFocus();
			saveResult();
			return true;
		} 
		//	
		return super.onOptionsItemSelected(item);
	}	
	
	/**
	 * On Selected Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:23:17
	 * @return void
	 */
	private void saveResult() {
		//	Set Result
		SP_SearchAdapter adapter = (SP_SearchAdapter) lv_SuggestedProducts.getAdapter();
		selectedData = adapter.getSelectedData();
		//	Load Task
		new SaveDataTask().execute();
	}
	
	/**
	 * Get SQL from Parameters
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 15:55:40
	 * @param criteria
	 * @return
	 * @return String
	 */
	private String getSQL(FilterValue criteria) {
		StringBuffer sql = new StringBuffer();
		if(m_FTA_Farming_ID > 0) {								//	Is Applied
			sql.append("SELECT pa.M_Product_ID, " + 
					"ppac.Name ProductCategory, " +
					"ppa.Value ProductValue, " +
					"ppa.Name ProductName, " +
					"ppa.Description ProductDescription, " +
					"0 QtySuggested, " +
					"0 Suggested_Uom_ID, " +
					"'' SuggestedUOMSymbol, " +
					"CASE WHEN pa.M_Product_ID = cpa.M_Product_ID THEN cpa.QtyDosage ELSE NULL END QtyDosage, " +
					"CASE WHEN pa.M_Product_ID = cpa.M_Product_ID THEN cpa.Dosage_UOM_ID ELSE p.C_UOM_ID END Dosage_Uom_ID, " +
					"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN du.UOMSymbol ELSE pu.UOMSymbol END DosageUOMSymbol, " +
					"CASE WHEN pa.M_Product_ID = cpa.M_Product_ID THEN cpa.QtyDosage ELSE NULL END Qty, " +
					"CASE WHEN pa.M_Product_ID = cpa.M_Product_ID THEN cpa.Dosage_UOM_ID ELSE p.C_UOM_ID END Dosage_Uom_ID, " +
					"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN du.UOMSymbol ELSE pu.UOMSymbol END OrderUOMSymbol, " +
					"0 DayFrom, 0 DayTo, " +
					"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN cpa.FTA_ProductsToApply_ID ELSE 0 END FTA_ProductsToApply_ID " +
					"FROM M_Product p " +
					"INNER JOIN C_UOM pu ON(pu.C_UOM_ID = p.C_UOM_ID) " +
					"INNER JOIN FTA_ProductsToApply pa ON(pa.M_Product_ID = p.M_Product_ID) " +
					"INNER JOIN FTA_TechnicalFormLine tfl ON(tfl.FTA_TechnicalFormLine_ID = pa.FTA_TechnicalFormLine_ID) " +
					"LEFT JOIN FTA_ProductsToApply cpa ON(cpa.M_Product_ID = pa.M_Product_ID " +
					"	AND cpa.IsApplied = 'Y' AND cpa.FTA_TechnicalForm_ID = " + m_FTA_TechnicalForm_ID + ") " +
					"LEFT JOIN M_Product ppa ON(ppa.M_Product_ID = pa.M_Product_ID) " +
					"LEFT JOIN M_Product_Category ppac ON(ppac.M_Product_Category_ID = ppa.M_Product_Category_ID) " +
					"LEFT JOIN C_UOM su ON(su.C_UOM_ID = cpa.Suggested_UOM_ID) " +
					"LEFT JOIN C_UOM du ON(du.C_UOM_ID = cpa.Dosage_UOM_ID) " +
					"LEFT JOIN C_UOM ou ON(ou.C_UOM_ID = cpa.C_UOM_ID) " +
					"WHERE (pa.IsApplied = 'N' OR pa.IsApplied IS NULL) " +
					"AND tfl.FTA_Farming_ID = " + m_FTA_Farming_ID + " " +
					"GROUP BY pa.M_Product_ID, p.M_Product_ID, ppa.Value, ppac.Name, ppa.Name, ppa.Description, cpa.M_Product_ID, cpa.QtyDosage, " +
					"cpa.Dosage_UOM_ID, du.UOMSymbol, pu.UOMSymbol, cpa.FTA_TechnicalForm_ID, cpa.FTA_ProductsToApply_ID");
		} else if(lookupSugg_Applied.getValueAsBoolean()) {		//	Is Suggested
			sql.append("SELECT CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.M_Product_ID ELSE sp.M_Product_ID END M_Product_ID,  " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN ppac.Name ELSE spc.Name END ProductCategory, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN ppa.Value ELSE sp.Value END ProductValue, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN ppa.Name ELSE sp.Name END ProductName, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN ppa.Description ELSE sp.Description END ProductDescription, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.QtySuggested ELSE fsp.QtyDosage END QtySuggested, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.Suggested_UOM_ID ELSE su.C_UOM_ID END Suggested_Uom_ID, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN su.UOMSymbol ELSE su.UOMSymbol END SuggestedUOMSymbol, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.QtyDosage ELSE fsp.QtyDosage END QtyDosage, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.Dosage_UOM_ID ELSE su.C_UOM_ID END Dosage_Uom_ID, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN du.UOMSymbol ELSE su.UOMSymbol END DosageUOMSymbol, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.Qty ELSE 0 END Qty, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.C_UOM_ID ELSE sp.C_UOM_ID END C_UOM_ID, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN ou.UOMSymbol ELSE su.UOMSymbol END OrderUOMSymbol, " +
				"fsp.DayFrom, fsp.DayTo, " +
				"CASE WHEN pa.M_Product_ID = sp.M_Product_ID THEN pa.FTA_ProductsToApply_ID ELSE 0 END FTA_ProductsToApply_ID " +
				"FROM FTA_TechnicalFormLine tfl " +
				"INNER JOIN FTA_Farming fm ON (tfl.FTA_Farming_ID = fm.FTA_Farming_ID) " +
				"INNER JOIN M_Product pc ON (pc.M_Product_ID = fm.Category_ID) " +
				"INNER JOIN FTA_SuggestedProduct fsp ON (fsp.Category_ID = fm.Category_ID OR fsp.Category_ID IS NULL) " +
				"LEFT JOIN M_Product sp ON (sp.M_Product_Category_ID = fsp.M_Product_Category_ID OR sp.M_Product_ID=fsp.M_Product_ID) " +
				"LEFT JOIN M_Product_Category spc ON(spc.M_Product_Category_ID = sp.M_Product_Category_ID) " +
				"LEFT JOIN FTA_ProductsToApply pa ON(pa.FTA_TechnicalForm_ID = tfl.FTA_TechnicalForm_ID AND pa.M_Product_ID = sp.M_Product_ID) " +
				"LEFT JOIN M_Product ppa ON(ppa.M_Product_ID = pa.M_Product_ID) " +
				"LEFT JOIN M_Product_Category ppac ON(ppac.M_Product_Category_ID = ppa.M_Product_Category_ID) " +
				"LEFT JOIN C_UOM su ON(su.C_UOM_ID = COALESCE(pa.Suggested_UOM_ID, fsp.Dosage_UOM_ID)) " +
				"LEFT JOIN C_UOM du ON(du.C_UOM_ID = pa.Dosage_UOM_ID) " +
				"LEFT JOIN C_UOM ou ON(ou.C_UOM_ID = pa.C_UOM_ID) " +
				"WHERE (pa.IsApplied = 'N' OR pa.IsApplied IS NULL) " +
				"AND (tfl.FTA_FarmingStage_ID = fsp.FTA_FarmingStage_ID OR fsp.FTA_FarmingStage_ID IS NULL) " +
				"AND (tfl.FTA_ObservationType_ID = fsp.FTA_ObservationType_ID OR fsp.FTA_ObservationType_ID IS NULL) ");
			//	
			sql.append("AND tfl.FTA_TechnicalFormLine_ID = ");
			//	
			sql.append(m_FTA_TechnicalFormLine_ID);
		} else {												//	Is not suggested
			sql.append("SELECT CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.M_Product_ID ELSE p.M_Product_ID END M_Product_ID,  " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN ppac.Name ELSE pc.Name END ProductCategory, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN ppa.Value ELSE p.Value END ProductValue, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN ppa.Name ELSE p.Name END ProductName, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN ppa.Description ELSE p.Description END ProductDescription, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.QtySuggested ELSE NULL END QtySuggested, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.Suggested_UOM_ID ELSE p.C_UOM_ID END Suggested_Uom_ID, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN su.UOMSymbol ELSE pu.UOMSymbol END SuggestedUOMSymbol, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.QtyDosage ELSE NULL END QtyDosage, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.Dosage_UOM_ID ELSE p.C_UOM_ID END Dosage_Uom_ID, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN du.UOMSymbol ELSE pu.UOMSymbol END DosageUOMSymbol, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.Qty ELSE 0 END Qty, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.C_UOM_ID ELSE p.C_UOM_ID END C_UOM_ID, " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN ou.UOMSymbol ELSE pu.UOMSymbol END OrderUOMSymbol, " +
				"0 DayFrom, 0 DayTo , " +
				"CASE WHEN pa.M_Product_ID = p.M_Product_ID THEN pa.FTA_ProductsToApply_ID ELSE 0 END FTA_ProductsToApply_ID " +
				"FROM M_Product p " +
				"INNER JOIN M_Product_Category pc ON(pc.M_Product_Category_ID = p.M_Product_Category_ID) " +
				"INNER JOIN C_UOM pu ON(pu.C_UOM_ID = p.C_UOM_ID) " +
				"LEFT JOIN FTA_ProductsToApply pa ON(pa.M_Product_ID = p.M_Product_ID) " +
				"LEFT JOIN M_Product ppa ON(ppa.M_Product_ID = pa.M_Product_ID) " +
				"LEFT JOIN M_Product_Category ppac ON(ppac.M_Product_Category_ID = ppa.M_Product_Category_ID) " +
				"LEFT JOIN C_UOM su ON(su.C_UOM_ID = pa.Suggested_UOM_ID) " +
				"LEFT JOIN C_UOM du ON(du.C_UOM_ID = pa.Dosage_UOM_ID) " +
				"LEFT JOIN C_UOM ou ON(ou.C_UOM_ID = pa.C_UOM_ID) " +
				"WHERE (pa.IsApplied = 'N' OR pa.IsApplied IS NULL) " +
				"AND (pa.FTA_TechnicalForm_ID IS NULL OR pa.FTA_TechnicalForm_ID = ");
			//	
			sql.append(m_FTA_TechnicalForm_ID).append(")");
		}
		//	Add Criteria
		if(criteria != null
				&& criteria.getWhereClause() != null
				&& criteria.getWhereClause().length() > 0)
			sql.append(" AND ").append(criteria.getWhereClause());
		//	
		return sql.toString();
	}
	
	/**
	 * Include Class Thread with load Suggested Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
	 *
	 */
	private class LoadViewTask extends AsyncTask<Void, Void, Void> {

		/**	Progress Bar			*/
		private ProgressDialog 					v_PDialog;
		/**	Data					*/
		private ArrayList<SP_DisplayRecordItem> data = null;
		
		/**
		 * Init Values
		 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/05/2014, 12:18:42
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
	     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:37:56
	     * @return
	     * @return boolean
	     */
	    protected boolean loadView() {
	    	//	Set Adapter
			m_SP_SearchAdapter = new SP_SearchAdapter(getApplicationContext(), data);
			lv_SuggestedProducts.setAdapter(m_SP_SearchAdapter);
			//	
			return true;
	    }
	    
	    /**
		 * Load Data
		 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:32:16
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
						data.add(new SP_DisplayRecordItem(
								rs.getInt(index++), 
								rs.getString(index++), 
								rs.getString(index++), 
								rs.getString(index++), 
								rs.getString(index++), 
								new BigDecimal(rs.getDouble(index++)), 
								rs.getInt(index++), 
								rs.getString(index++), 
								new BigDecimal(rs.getDouble(index++)), 
								rs.getInt(index++), 
								rs.getString(index++), 
								new BigDecimal(rs.getDouble(index++)), 
								rs.getInt(index++), 
								rs.getString(index++), 
								rs.getInt(index++), 
								rs.getInt(index++), 
								rs.getInt(index++)));
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
	 *
	 */
	private class SaveDataTask extends AsyncTask<Void, Void, Void> {

		/**	Progress Bar			*/
		private ProgressDialog 		v_PDialog;
		/**	Error Message			*/
		private String 				m_ErrorMsg = null;
		
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
				LogM.log(v_activity, V_AddSuggestedProduct.class, Level.SEVERE, "Error", e);
				m_ErrorMsg = e.getLocalizedMessage();
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
			if(m_ErrorMsg != null) {
				Msg.toastMsg(v_activity, m_ErrorMsg);
			}
			//	Exit
			v_activity.finish();
		}
	    
	    /**
		 * Save Data from Array
		 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/08/2014, 03:37:22
		 * @param data
		 * @return void
		 * @throws Exception 
		 */
		private void saveData(ArrayList<SP_DisplayRecordItem> data) throws Exception {
			//	Valid Null value
			if(data == null)
				return;
			//	
			StringBuffer sqlDelete = new StringBuffer("DELETE FROM ")
							.append(I_FTA_ProductsToApply.Table_Name)
							.append(" WHERE ")
							.append(I_FTA_ProductsToApply.COLUMNNAME_FTA_TechnicalForm_ID)
							.append(" = ?");
			//	SQL NOT IN
			StringBuffer sqlIn = new StringBuffer(" AND ")
				.append(I_FTA_ProductsToApply.COLUMNNAME_FTA_ProductsToApply_ID)
				.append(" NOT IN(");
			//	
			boolean first = true;
			//	Add Items
			MFTAProductsToApply pApply = new MFTAProductsToApply(v_activity, 0, null);
			//	
			for(SP_DisplayRecordItem item : data) {
				pApply.clear(true);
				pApply.loadData(item.getFTA_ProductToApply_ID());
				pApply.setFTA_TechnicalForm_ID(m_FTA_TechnicalForm_ID);
				pApply.setFTA_TechnicalFormLine_ID(m_FTA_TechnicalFormLine_ID);
				pApply.setM_Product_ID(item.getM_Product_ID());
				
				if(m_FTA_Farming_ID > 0) {
					pApply.setQtySuggested(item.getQtySuggested());
					pApply.setSuggested_Uom_ID(item.getSuggested_UOM_ID());
					pApply.setQtyDosage(item.getQty());
					pApply.setDosage_Uom_ID(item.getC_UOM_ID());
					pApply.setQty(Env.ZERO);
					pApply.setC_UOM_ID(item.getC_UOM_ID());
					pApply.setIsApplied(true);
				} else {
					pApply.setQtySuggested(item.getQtySuggested());
					pApply.setSuggested_Uom_ID(item.getSuggested_UOM_ID());
					pApply.setQtyDosage(item.getQtyDosage());
					pApply.setDosage_Uom_ID(item.getDosage_UOM_ID());
					pApply.setQty(item.getQty());
					pApply.setC_UOM_ID(item.getC_UOM_ID());
					pApply.setIsApplied(false);
				}
				pApply.saveEx();
				//	Add IDs
				if(!first) {
					sqlIn.append(", ");
				} else if(first) {
					first = false;
				}
				//	
				sqlIn.append(pApply.getFTA_ProductsToApply_ID());
			}
			//	Add finish
			sqlIn.append(")");
			//	Execute
			if(!first)
				sqlDelete.append(sqlIn);
			//	Just delete by line
			if(m_FTA_TechnicalFormLine_ID != 0)
				sqlDelete.append(" AND ")
					.append(I_FTA_ProductsToApply.COLUMNNAME_FTA_TechnicalFormLine_ID)
					.append(" = ")
					.append(m_FTA_TechnicalFormLine_ID).append(" ");
			else
				sqlDelete.append(" AND ")
					.append(I_FTA_ProductsToApply.COLUMNNAME_FTA_TechnicalFormLine_ID)
					.append(" IS NULL ");
			//	Delete Applied
			sqlDelete.append("AND ").append(I_FTA_ProductsToApply.COLUMNNAME_IsApplied)
				.append(" = ")
				.append("'").append((m_FTA_Farming_ID > 0? "Y": "N")).append("'");
			
			DB.executeUpdate(v_activity, sqlDelete.toString(), m_FTA_TechnicalForm_ID, false);
			//	Log
			LogM.log(v_activity, LV_TFLine.class, Level.FINE, 
					"SQL Delete Products to Apply =" + sqlDelete.toString());
			//	
		}
	}
	
}
