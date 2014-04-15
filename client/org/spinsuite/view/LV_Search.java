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
package org.spinsuite.view;

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.adapters.SearchAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.LogM;
import org.spinsuite.util.ViewIndex;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.LookupDisplayType;
import org.spinsuite.view.lookup.VLookup;
import org.spinsuite.view.lookup.VLookupCheckBox;
import org.spinsuite.view.lookup.VLookupDateBox;
import org.spinsuite.view.lookup.VLookupSearch;
import org.spinsuite.view.lookup.VLookupSpinner;
import org.spinsuite.view.lookup.VLookupString;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LV_Search extends FragmentActivity {
	
	/**	Adapter					*/
	private SearchAdapter 			adapter = null;
	/**	Main Layout				*/
	private LinearLayout			llc_Search = null;
	/**	List View				*/
	private ListView				lv_Search = null;
	/**	Field					*/
	private InfoField 				m_field = null;
	/**	Table Identifier		*/
	private int						m_SPS_Table_ID = 0;
	/**	Tab Identifier			*/
	private int						m_SPS_Tab_ID = 0;
	/**	Criteria				*/
	private FilterValue				m_criteria = null;
	/**	Old Criteria			*/
	private FilterValue				m_oldCriteria = null;
	/**	Lookup 					*/
	private LookupDisplayType 		lookup = null;
	/**	Info Field				*/
	private InfoTab					tabInfo = null;
	/**	View Index Array		*/
	private ArrayList<ViewIndex>	viewList = null;
	/**	Parameter				*/
	private LayoutParams			v_param	= null;
	
	/**	View Weight				*/
	private static final float 		WEIGHT = 1;
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_search);
    	//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			m_field = (InfoField)bundle.getParcelable("Field");
			m_SPS_Table_ID = bundle.getInt("SPS_Table_ID");
			m_SPS_Tab_ID = bundle.getInt("SPS_Tab_ID");
			m_criteria = bundle.getParcelable("Criteria");
		}
		//	
		llc_Search = (LinearLayout) findViewById(R.id.llc_Search);
		lv_Search = (ListView) findViewById(R.id.lv_Search);
		//	
		if(m_SPS_Table_ID != 0)
			lookup = new LookupDisplayType(getApplicationContext(), m_SPS_Table_ID);
		else if(m_field != null)
			lookup = new LookupDisplayType(getApplicationContext(), m_field);
		//	
		loadConfig();
		
		//	Load
		load();
    	//	Listener
		lv_Search.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				//	Load from Action
				selectedRecord((DisplayRecordItem) adapter.getItemAtPosition(position));
			}
        });
	}
	
	/**
	 * Load Config
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/03/2014, 22:07:43
	 * @return void
	 */
	private void loadConfig(){
		if(m_SPS_Tab_ID != 0){
			tabInfo = new InfoTab(getApplicationContext(), m_SPS_Tab_ID, true, null);
			viewList = new ArrayList<ViewIndex>();
	    	//	Set Parameter
	    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
	    			LayoutParams.MATCH_PARENT, WEIGHT);
			//	Add Fields
	    	for(InfoField field : tabInfo.getFields()){
	    		if(!field.IsDisplayed)
	    			continue;
	    		//	Add View to Layout
	    		addView(field);
	    	}
	    	//	Add Button
	    	Button btn_Search = new Button(this);
	    	btn_Search.setText(getResources().getString(R.string.msg_Search));
	    	//	Action
	    	btn_Search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					m_criteria = new FilterValue();
					if(m_oldCriteria != null)
						m_criteria = m_oldCriteria;
					//	Add Criteria
					addCriteriaQuery();
					load();
				}
			});
	    	//	Add Button
	    	llc_Search.addView(btn_Search, v_param);
		}
		//	Hide
		llc_Search.setVisibility(LinearLayout.GONE);
	}
	
	/**
	 * Add Criteria Query
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 10:28:14
	 * @return void
	 */
	private void addCriteriaQuery(){
    	//	Get Values
		StringBuffer sqlWhere = new StringBuffer();
    	for (ViewIndex vIndex: viewList) {
    		VLookup lookup = vIndex.getVLookup();
    		//	Only Filled
    		if(lookup.isEmpty())
    			continue;
    		InfoField field = lookup.getField();
    		//	Set to model
    		if(sqlWhere.length() > 0)
    			sqlWhere.append(" AND ");
    		//	Add Criteria Column Filter
    		sqlWhere.append(field.ColumnName).append(" = ?");
    		//	Add Value
    		m_criteria.addValue(DisplayType.getJDBC_Value(field.DisplayType, 
    				lookup.getValue()));
		}
    	//	Add SQL
    	m_criteria.setWhereClause(sqlWhere.toString());
	}
	
	/**
	 * Add View to Config Panel
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/03/2014, 22:10:08
	 * @param field
	 * @return void
	 */
	private void addView(InfoField field){
    	
    	VLookup lookup = null;
		//	Add
		if(DisplayType.isDate(field.DisplayType)){
			lookup = new VLookupDateBox(this, field);
		} else if(DisplayType.isText(field.DisplayType)){
			VLookupString lookupString = new VLookupString(this, field);
			lookupString.setInputType(DisplayType.getInputType(field.DisplayType));
			lookup = lookupString;
		} else if(DisplayType.isBoolean(field.DisplayType)){
			lookup = new VLookupCheckBox(this, field);
		} else if(DisplayType.isLookup(field.DisplayType)){
			//	Table Direct
			if(field.DisplayType == DisplayType.TABLE_DIR){
				lookup = new VLookupSpinner(this, field);
			} else if(field.DisplayType == DisplayType.SEARCH){
				lookup = new VLookupSearch(this, field);
			}
		}
		//	is Filled
		if(lookup != null){
			ViewIndex index = new ViewIndex(lookup, field.ColumnName);
			viewList.add(index);
			llc_Search.addView(lookup, v_param);
		}
    }
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//	Inflate menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);
		//	Get Item
		MenuItem item = menu.findItem(R.id.action_search);
		//	Search View
		final View searchView = SearchViewCompat.newSearchView(this);
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
					if(adapter != null){
						String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
						adapter.getFilter().filter(mFilter);
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
		//	Valid Configuration
		MenuItem itemConfig = menu.findItem(R.id.action_config);
		if(itemConfig != null) {
			if(m_SPS_Tab_ID != 0)
				itemConfig.setVisible(true);
			else 
				itemConfig.setVisible(false);
		}
		//	
		MenuItem itemAdd = menu.findItem(R.id.action_add);
		//	Visible
		if(itemAdd != null)
			itemAdd.setVisible(m_field == null);
		return true;
	}
	    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add:
				selectedRecord(new DisplayRecordItem(0, null));
			return true;
			case R.id.action_config:
				//	Show
				if(llc_Search.getVisibility() == LinearLayout.GONE){
					llc_Search.setVisibility(LinearLayout.VISIBLE);
					m_oldCriteria = m_criteria;
				} else {
					llc_Search.setVisibility(LinearLayout.GONE);
					m_criteria = m_oldCriteria;
					//	Load New
					load();
				}
			return true;
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, LV_Menu.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Load Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:32:16
	 * @return void
	 */
	private void load(){
		try{
			if(lookup == null)
				throw new Exception("No Parameter");
			//	
			DB conn = new DB(this);
			DB.loadConnection(conn, DB.READ_ONLY);
			Cursor rs = null;
			//	Query
			String[] values = null;
			if(m_criteria != null) {
				lookup.setCriteria(m_criteria.getWhereClause());
				values = m_criteria.getValues();
			} else
				lookup.setCriteria(null);
			rs = conn.querySQL(lookup.getSQL(), values);
			ArrayList<DisplayRecordItem> data = new ArrayList<DisplayRecordItem>();
			if(rs.moveToFirst()){
				//	Loop
				do{
					data.add(new DisplayRecordItem(
							rs.getInt(0), 
							rs.getString(1)));
				}while(rs.moveToNext());
			}
			//	Close
			DB.closeConnection(conn);
			//	Set Adapter
			adapter = new SearchAdapter(this, R.layout.i_search, data);
			adapter.setDropDownViewResource(R.layout.i_search);
			lv_Search.setAdapter(adapter);
		} catch(Exception e){
			LogM.log(this, getClass(), Level.SEVERE, "Error in Load", e);
		}
	}
	
	
	/**
	 * On Selected Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:23:17
	 * @param item
	 * @return void
	 */
	private void selectedRecord(DisplayRecordItem item){
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putParcelable("Record", item);
		if(m_field != null){
			bundle.putInt(DisplayMenuItem.CONTEXT_ACTIVITY_TYPE, 
					DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_SearchColumn);
			bundle.putString("ColumnName", m_field.ColumnName);
		} else {
			bundle.putInt(DisplayMenuItem.CONTEXT_ACTIVITY_TYPE, 
					DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_SearchWindow);
		}
		intent.putExtras(bundle);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}	  
}
