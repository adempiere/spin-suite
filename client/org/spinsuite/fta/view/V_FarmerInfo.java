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

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.fta.adapters.DisplayFarmingInfo;
import org.spinsuite.fta.adapters.FarmerInfoAdapter;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.OnFieldChangeListener;
import org.spinsuite.model.I_FTA_TechnicalForm;
import org.spinsuite.model.I_FTA_TechnicalFormLine;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.view.T_DynamicTab;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.Lookup;
import org.spinsuite.view.lookup.VLookupSearch;
import org.spinsuite.view.lookup.VLookupSpinner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class V_FarmerInfo extends Activity {

	private 	ActivityParameter 		m_ActivityParam 			= null;
	private 	DB						conn						= null;
	private 	TableLayout 			tl_FarmerDetail				= null;
	private 	Activity				v_activity					= null;
	private 	VLookupSearch 			farmerGridField				= null;
	private 	GridField 				farmGridField				= null;
	private 	GridField 				farmDivisionGridField		= null;
	private 	GridField 				farmingGridField			= null;
	private 	ListView				lv_FarmingDetail			= null;
	private 	TextView				tv_AppliedProducts			= null;
	/**	Listener						*/
	private 	OnFieldChangeListener	m_Listener 					= null;
	
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_farmer_info);
		//	
		
    	Bundle bundle = getIntent().getExtras();
    	if(bundle != null) {
    		m_ActivityParam = (ActivityParameter)bundle.getParcelable("Param");
    	}
		//	Is Not ok Load
    	if(m_ActivityParam == null)
    		return;
		//	Get Action Bar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setSubtitle(getString(R.string.FarmerInfo));
		//	
    	v_activity = this;
    	//	
    	tl_FarmerDetail = (TableLayout) findViewById(R.id.tl_FarmerDetail);
    	//	Get List
    	lv_FarmingDetail = (ListView) findViewById(R.id.lv_FarmingDetail);
    	tv_AppliedProducts = (TextView) findViewById(R.id.tv_AppliedProducts);
    	//	Instance Listener
    	m_Listener = new OnFieldChangeListener() {
    		@Override
    		public void onFieldEvent(GridField mField) {
    			LogM.log(v_activity, T_DynamicTab.class, 
    					Level.FINE, "Field Event = " + mField.getColumnName());
    			//	Process Reload
    			if(mField.getColumnName().equals(I_FTA_TechnicalForm.COLUMNNAME_C_BPartner_ID)) {
    				reloadDepending(farmGridField);
    			} if(mField.getColumnName().equals(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farm_ID)) {
    				reloadDepending(farmDivisionGridField);
    			} else if(mField.getColumnName().equals(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_FarmDivision_ID)) {
    				reloadDepending(farmingGridField);
    			} else if(mField.getColumnName().equals(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farming_ID)) {
    				loadList();
    			}
    		}
		};
    	//	
		new LoadViewTask().execute();
	}
	
	/**
	 * Reload Depending fields
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/08/2014, 02:39:26
	 * @param mField
	 * @return void
	 */
	private void reloadDepending(GridField mField) {
		//	Trigger refresh
		//	Load
		InfoField fieldMD = mField.getField();
		if(fieldMD == null)
			return;
		//	do it
		if(DisplayType.isLookup(fieldMD.DisplayType)) {
			if(fieldMD.DisplayType != DisplayType.SEARCH
					&& mField instanceof VLookupSpinner) {
				VLookupSpinner spinner = (VLookupSpinner) mField;
				Object oldValue = spinner.getValue();
				spinner.load(true);
				//	set old value
				spinner.setValueNoReload(oldValue);
			}
		}
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			finish();
			return true;
		}
		//	
        return super.onOptionsItemSelected(item);
    }
	
	/**
	 * Load List Information
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/09/2014, 11:02:41
	 * @return void
	 */
	private void loadList() {
		//	Create SQL
		String sql = new String("SELECT pc.M_Product_Category_ID, pc.Name ProductCategory, " +
				"fs.FTA_FarmingStage_ID, fs.Name FarmingStage, " +
				"p.M_Product_ID, (p.Value || '_' || p.Name) Product, " +
				"pta.QtyDosage, f.EffectiveArea, f.Area, uom.C_UOM_ID, uom.UOMSymbol " +
				"FROM FTA_ProductsToApply pta " +
				"INNER JOIN FTA_TechnicalFormLine tfl ON(tfl.FTA_TechnicalFormLine_ID = pta.FTA_TechnicalFormLine_ID) " +
				"INNER JOIN FTA_TechnicalForm tf ON(tf.FTA_TechnicalForm_ID = tfl.FTA_TechnicalForm_ID) " +
				"INNER JOIN FTA_Farming f ON(f.FTA_Farming_ID = tfl.FTA_Farming_ID) " + 
				"INNER JOIN FTA_FarmingStage fs ON(fs.FTA_FarmingStage_ID = tfl.FTA_FarmingStage_ID) " +
				"INNER JOIN M_Product p ON(p.M_Product_ID = pta.M_Product_ID) " +
				"INNER JOIN M_Product_Category pc ON(pc.M_Product_Category_ID = p.M_Product_Category_ID) " +
				"INNER JOIN C_UOM uom ON(uom.C_UOM_ID = pta.C_UOM_ID) " +
				"WHERE tfl.FTA_Farming_ID = " + farmingGridField.getValueAsInt() + " " + 
				"AND tf.DocStatus IN('CO', 'CL') " + 
				"AND pta.IsApplied = 'Y' " + 
				"ORDER BY ProductCategory, FarmingStage, Product");
		//	
		LogM.log(v_activity, getClass(), Level.FINE, "SQL=" + sql);
    	//	Load Table Info
		conn = new DB(v_activity);
    	DB.loadConnection(conn, DB.READ_ONLY);
    	//	
		Cursor rs = conn.querySQL(sql, null);
		//	
		ArrayList<DisplayFarmingInfo> data = new ArrayList<DisplayFarmingInfo>();
		if(rs.moveToFirst()){
			do {
				int index = 0;
				//	
				data.add(new DisplayFarmingInfo(
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getDouble(index++),
						rs.getDouble(index++), 
						rs.getDouble(index++), 
						rs.getInt(index++), 
						rs.getString(index++)));
				//	
				index = 0;
			}while(rs.moveToNext());
			//	
			tv_AppliedProducts.setVisibility(View.VISIBLE);
		} else {
			tv_AppliedProducts.setVisibility(View.GONE);
		}
		//	Close Connection
		DB.closeConnection(conn);
		//	Set Adapter
		FarmerInfoAdapter mi_adapter = new FarmerInfoAdapter(v_activity, data);
		mi_adapter.setDropDownViewResource(R.layout.i_farmer_info);
		lv_FarmingDetail.setAdapter(mi_adapter);
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null) {
	    		Bundle bundle = data.getExtras();
	    		DisplayRecordItem item = (DisplayRecordItem) bundle.getParcelable("Record");
	    		String columnName = bundle.getString("ColumnName");
	    		//	if a field or just search
	    		if(columnName != null
	    				&& columnName.equals(I_FTA_TechnicalForm.COLUMNNAME_C_BPartner_ID)) {
	    			//	Set Item
	    			farmerGridField.setItem(item);
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

		/**	Layout					*/
		private LayoutParams		v_param				= null;
		/**	Progress Bar			*/
		private ProgressDialog 		v_PDialog 			= null;
		/**	Constant				*/
		private static final float 	WEIGHT_SUM 			= 2;
		private static final float 	WEIGHT 				= 1;
		/**	Lookups					*/
		private Lookup				farmerLookup 		= null;
		private Lookup				farmLookup 			= null;
		private Lookup				farmDivisionLookup 	= null;
		private Lookup				farmingLookup 		= null;
		
		/**
		 * Init Values
		 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/05/2014, 12:18:42
		 * @return void
		 */
		private void init() {
	    	//	Set Parameter
	    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
	    			LayoutParams.MATCH_PARENT, WEIGHT);
	    	//	Load Table Info
	    	DB.loadConnection(conn, DB.READ_ONLY);
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
			//	Farmer
			farmerLookup = new Lookup(v_activity, 
					I_FTA_TechnicalForm.Table_Name, 
					I_FTA_TechnicalForm.COLUMNNAME_C_BPartner_ID, m_ActivityParam);
			farmerLookup.load();
			//	Farm
			farmLookup = new Lookup(v_activity, 
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farm_ID, m_ActivityParam);
			farmLookup.load();
			//	Farm Division
			farmDivisionLookup = new Lookup(v_activity,
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_FarmDivision_ID, m_ActivityParam);
			farmDivisionLookup.load();
			//	Farming
			farmingLookup = new Lookup(v_activity,
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farming_ID, m_ActivityParam);
			farmingLookup.load();
			//	Return
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
	    	boolean ok = false;
	    	//	Farm and Farm Division
	    	LinearLayout v_rowFarm = new LinearLayout(v_activity);
			v_rowFarm.setOrientation(LinearLayout.HORIZONTAL);
			v_rowFarm.setWeightSum(WEIGHT_SUM);
			//	Farmer
			farmerGridField = (VLookupSearch) GridField.createLookup(v_activity, farmerLookup);
			farmerGridField.setLayoutParams(v_param);
			farmerGridField.setOnFieldChangeListener(m_Listener);
			tl_FarmerDetail.addView(farmerGridField);
	    	//	Farm
			farmGridField = GridField.createLookup(v_activity, farmLookup);
			farmGridField.setLayoutParams(v_param);
			farmGridField.setOnFieldChangeListener(m_Listener);
			v_rowFarm.addView(farmGridField);
	    	//	Farm Division
			farmDivisionGridField = GridField.createLookup(v_activity, farmDivisionLookup);
			farmDivisionGridField.setLayoutParams(v_param);
			farmDivisionGridField.setOnFieldChangeListener(m_Listener);
			v_rowFarm.addView(farmDivisionGridField);
			tl_FarmerDetail.addView(v_rowFarm);
			//	Farming and Farming Stage
			LinearLayout v_rowFarming = new LinearLayout(v_activity);
			v_rowFarming.setOrientation(LinearLayout.HORIZONTAL);
			v_rowFarming.setWeightSum(WEIGHT_SUM);
			//	Farming
			farmingGridField = GridField.createLookup(v_activity, farmingLookup);
			farmingGridField.setLayoutParams(v_param);
			farmingGridField.setOnFieldChangeListener(m_Listener);
			v_rowFarming.addView(farmingGridField);
			tl_FarmerDetail.addView(v_rowFarming);
			//	Load
			loadData();
			//	Return
			return ok;
	    }
	    
	    /**
	     * Load Data from Parameter
	     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/08/2014, 16:02:07
	     * @return void
	     */
	    private void loadData() {
	    	farmerGridField.setValue(Env.getContextAsInt(v_activity, 
	    			m_ActivityParam.getActivityNo(), I_FTA_TechnicalForm.COLUMNNAME_C_BPartner_ID));
	    }
	}
}