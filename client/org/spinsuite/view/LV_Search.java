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
import org.spinsuite.util.Msg;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.Lookup;
import org.spinsuite.view.lookup.VLookupSearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LV_Search extends Activity {
	
	/**	Adapter					*/
	private SearchAdapter 			m_SearchAdapter 	= null;
	/**	Main Layout				*/
	private ScrollView				sv_Search 			= null;
	/**	Table Layout			*/
	private TableLayout 			v_tableLayout 		= null;
	/**	List View				*/
	private ListView				lv_Search 			= null;
	/**	Field					*/
	private InfoField 				m_field 			= null;
	/**	Table Identifier		*/
	private int						m_SPS_Table_ID 		= 0;
	/**	Tab Identifier			*/
	private int						m_SPS_Tab_ID 		= 0;
	/**	Is Insert Record		*/
	private boolean 				m_IsInsertRecord	= true;
	/**	Criteria				*/
	private FilterValue				m_criteria 			= null;
	/**	Criteria Old			*/
	private String					m_oldWhereClause 	= null;
	/**	Is Changed				*/
	private boolean					m_IsChanged			= false;
	/**	Lookup 					*/
	private Lookup 					lookup 				= null;
	/**	Info Field				*/
	private InfoTab					tabInfo 			= null;
	/**	View Index Array		*/
	private ArrayList<GridField>	viewList 			= null;
	/**	Parameter				*/
	private LayoutParams			v_param				= null;
	/**	Activity				*/
	private Activity				v_activity 			= null;
	/**	Record Count			*/
	private TextView				tv_RecordCount		= null;
	/**	View Weight				*/
	private static final float 		WEIGHT 				= 1;
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_search);
    	//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			m_field = (InfoField)bundle.getParcelable("Field");
			m_SPS_Table_ID = bundle.getInt("SPS_Table_ID");
			m_SPS_Tab_ID = bundle.getInt("SPS_Tab_ID");
			String m_StringInsertRecord = bundle.getString("IsInsertRecord");
			m_criteria = bundle.getParcelable("Criteria");
			//	Valid Is Insert Record
			if(m_StringInsertRecord != null)
				m_IsInsertRecord = m_StringInsertRecord.equals("Y");
		}
		//	Set Activity
		v_activity = this;
		//	Get Record Count
		tv_RecordCount = (TextView) findViewById(R.id.tv_RecordCount);
		//	
		tv_RecordCount.setText(Msg.getMsg(v_activity, "record.found") + ": 0");
		//	
		sv_Search = (ScrollView) findViewById(R.id.sv_Search);
    	//	Table Layout
    	v_tableLayout = new TableLayout(this);
    	sv_Search.addView(v_tableLayout);
    	//	
		lv_Search = (ListView) findViewById(R.id.lv_Search);
		//	
		if(m_SPS_Table_ID != 0)
			lookup = new Lookup(getApplicationContext(), m_SPS_Table_ID);
		else if(m_field != null)
			lookup = new Lookup(getApplicationContext(), m_field);
		//	
		loadConfig();
		
		//	Load
		new LoadViewTask().execute();
    	//	Listener
		lv_Search.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				//	Load from Action
				selectedRecord(m_SearchAdapter.getItem(position));
			}
        });
	}
	
	/**
	 * Load Config
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/03/2014, 22:07:43
	 * @return void
	 */
	private void loadConfig() {
		if(m_SPS_Tab_ID != 0) {
			tabInfo = new InfoTab(getApplicationContext(), m_SPS_Tab_ID, true, null);
			viewList = new ArrayList<GridField>();
	    	//	Set Parameter
	    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
	    			LayoutParams.MATCH_PARENT, WEIGHT);
			//	Add Fields
	    	for(InfoField field : tabInfo.getFields()) {
	    		if(!field.IsDisplayed)
	    			continue;
	    		//	Add View to Layout
	    		addView(field);
	    	}
		}
		//	Hide
		sv_Search.setVisibility(LinearLayout.GONE);
	}
	
	/**
	 * Add Criteria Query
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 10:28:14
	 * @return void
	 */
	private void addCriteriaQuery() {
		m_criteria = new FilterValue();
    	//	Get Values
		m_IsChanged = false;
		StringBuffer sqlWhere = new StringBuffer();
    	for (GridField lookup: viewList) {
    		//	Only Filled
    		if(lookup.isEmpty())
    			continue;
    		InfoField field = lookup.getField();
    		//	Set to model
    		if(sqlWhere.length() > 0)
    			sqlWhere.append(" AND ");
    		//	Add Criteria Column Filter
    		//	Process Text
    		if(DisplayType.isText(field.DisplayType)) {
    			sqlWhere.append("UPPER(").append(field.ColumnName).append(")")
    					.append(" LIKE UPPER(?)");
    			//	Add Value
    			String value = (String) DisplayType.getJDBC_Value(field.DisplayType, 
    					lookup.getValue());
    			m_criteria.addValue("%" + value + "%");

    		} else {
    			sqlWhere.append(" = ?");
    			//	Add Value
    			m_criteria.addValue(DisplayType.getJDBC_Value(field.DisplayType, 
    					lookup.getValue()));
    		}
    		//	Set like a change
    		if(lookup.isChanged()
    				&& !m_IsChanged) {
    			m_IsChanged = true;
    		}
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
	private void addView(InfoField field) {
		field.IsMandatory = false;
		field.IsReadOnly = false;
		//	Get Lookup
    	GridField lookup = GridField.createLookup(this, field);
		//	is Filled
		if(lookup != null) {
			viewList.add(lookup);
			v_tableLayout.addView(lookup, v_param);
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
					if(m_SearchAdapter != null) {
						String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
						m_SearchAdapter.getFilter().filter(mFilter);
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
			itemAdd.setVisible(m_field == null && m_IsInsertRecord);
		//	Close
		MenuItem itemClose = menu.findItem(R.id.action_close);
		//	Visible
		if(itemClose != null)
			itemClose.setVisible(m_field != null);

		return true;
	}
	    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_add 
				|| itemId == R.id.action_close) {
			selectedRecord(new DisplayRecordItem(-1, null));
			return true;
		} else if (itemId == R.id.action_config) {
			//	Show
			if(sv_Search.getVisibility() == LinearLayout.GONE) {
				sv_Search.setVisibility(LinearLayout.VISIBLE);
				m_oldWhereClause = (m_criteria != null
											? m_criteria.getWhereClause()
													: "");
			} else {
				sv_Search.setVisibility(LinearLayout.GONE);
				//	Search
				search();
			}
			return true;
		} else if (itemId == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this, LV_Menu.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Search Records
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/08/2014, 15:39:31
	 * @return void
	 */
	private void search() {
		//	Add Criteria
		addCriteriaQuery();
		String whereClause = (m_criteria != null
									? m_criteria.getWhereClause()
											: "");
		//	Load New
		if(!m_oldWhereClause.equals(whereClause)
				|| m_IsChanged) {
			//	Set New Criteria
			m_oldWhereClause = m_criteria.getWhereClause();
			new LoadViewTask().execute();
		}
	}
	
	/**
	 * On Selected Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:23:17
	 * @param item
	 * @return void
	 */
	private void selectedRecord(DisplayRecordItem item) {
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putParcelable("Record", item);
		if(m_field != null) {
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
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//	
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null) {
	    		Bundle bundle = data.getExtras();
	    		//	Item
	    		DisplayRecordItem item = (DisplayRecordItem) bundle.getParcelable("Record");
	    		switch (bundle.getInt(DisplayMenuItem.CONTEXT_ACTIVITY_TYPE)) {
	    			case DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_SearchColumn:
						String columnName = bundle.getString("ColumnName");
			    		//	if a field or just search
			    		if(columnName != null) {
			    			for (GridField vField: viewList) {
			    	    		if(vField.getColumnName().equals(columnName)) {
			    	    			((VLookupSearch) vField).setItem(item);
			    	    			break;
			    	    		}
			    			}
			    		}
						break;
				default:
					break;
	    		}
	    	}
    	}
    }
	
	/**
	 * Include Class Thread
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
	 *
	 */
	private class LoadViewTask extends AsyncTask<Void, Integer, Integer> {

		/**	Progress Bar			*/
		private ProgressDialog 			v_PDialog;
		/**	Data					*/
		ArrayList<DisplayRecordItem> 	data = null;
		
		/**
		 * Init Values
		 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/05/2014, 12:18:42
		 * @return void
		 */
		private void init() {
	    	//	Load Table Info
			data = new ArrayList<DisplayRecordItem>();
			//	View
		}
		
		@Override
		protected void onPreExecute() {
			v_PDialog = ProgressDialog.show(v_activity, null, 
					getString(R.string.msg_Loading), false, false);
			//	Set Max
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			init();
			//	Load Data
			loadData();
			//	
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... progress) {
			
		}

		@Override
		protected void onPostExecute(Integer result) {
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
			m_SearchAdapter = new SearchAdapter(v_activity, R.layout.i_image_text, data);
			m_SearchAdapter.setDropDownViewResource(R.layout.i_image_text);
			lv_Search.setAdapter(m_SearchAdapter);
			//	
			tv_RecordCount.setText(Msg.getMsg(v_activity, "record.found") 
					+ ": " + String.valueOf(m_SearchAdapter.getCount()));
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
				if(lookup == null)
					throw new Exception("No Parameter");
				//	
				DB conn = new DB(v_activity);
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
				//	
				String[] keyColumns = lookup.getInfoLookup().KeyColumn;
				int keyCount = keyColumns.length;
				if(rs.moveToFirst()) {
					//	Loop
					do{
						//	Declare Keys
						int[] keys = new int[keyCount];
						//	Get Keys
						for(int i = 0; i < keyCount; i++) {
							keys[i] = rs.getInt(i);
						}
						//	Tmp Key count
						int keyCountAdd = keyCount;
						//	
						data.add(new DisplayRecordItem(
								keys, 
								keyColumns, 
								rs.getString(keyCountAdd++), 
								rs.getString(keyCountAdd++), 
								null));
					}while(rs.moveToNext());
				}
				//	Close
				DB.closeConnection(conn);
			} catch(Exception e) {
				LogM.log(v_activity, getClass(), Level.SEVERE, "Error in Load", e);
			}
		}
	}
}