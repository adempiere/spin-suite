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

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.OnFieldChangeListener;
import org.spinsuite.model.I_FTA_TechnicalFormLine;
import org.spinsuite.model.MFTATechnicalFormLine;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.T_DynamicTab;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.Lookup;
import org.spinsuite.view.lookup.VLookupSpinner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class V_AddTFLine extends Activity {

	private 	TabParameter 			tabParam 					= null;
	private 	InfoTab 				tabInfo						= null;
	private 	DB						conn						= null;
	private 	TableLayout 			tl_TFLine					= null;
	private 	Activity				v_activity					= null;
	private 	GridField 				farmGridField				= null;
	private 	GridField 				farmDivisionGridField		= null;
	private 	GridField 				farmingGridField			= null;
	private 	GridField 				farmingStageGridField		= null;
	private 	GridField 				observationTypeGridField	= null;
	private 	GridField 				commentsGridField			= null;
	private 	MFTATechnicalFormLine 	line						= null;
	private 	int 					p_FTA_TechnicalFormLine_ID	= 0;
	/**	Listener						*/
	private 	OnFieldChangeListener	m_Listener 					= null;
	
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_add_technical_form_line);
		//	
		
    	Bundle bundle = getIntent().getExtras();
    	if(bundle != null) {
    		tabParam = (TabParameter)bundle.getParcelable("TabParam");
    		p_FTA_TechnicalFormLine_ID = bundle.getInt("FTA_TechnicalFormLine_ID");
    	}
		//	Is Not ok Load
    	if(tabParam == null)
    		return;
    	
    	v_activity = this;
    	tl_TFLine = (TableLayout) findViewById(R.id.tl_TFLine);
    	//	Instance Listener
    	m_Listener = new OnFieldChangeListener() {
    		@Override
    		public void onFieldEvent(GridField mField) {
    			LogM.log(v_activity, T_DynamicTab.class, 
    					Level.FINE, "Field Event = " + mField.getColumnName());
    			//	Process Reload
    			if(mField.getColumnName().equals(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farm_ID)) {
    				reloadDepending(farmDivisionGridField);
    			} else if(mField.getColumnName().equals(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_FarmDivision_ID)) {
    				reloadDepending(farmingGridField);
    			} else if(mField.getColumnName().equals(I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farming_ID)) {
    				reloadDepending(farmingStageGridField);
    			}
    		}
		};
    	//	
		new LoadViewTask().execute();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_tf_line, menu);
        //	Return
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this, LV_TFLine.class));
			return true;
		} else if (itemId == R.id.action_ok) {
			// Go to the previous step in the wizard. If there is no previous step,
			// setCurrentItem will do nothing.
			processActionOk();
			return true;
		} else if (itemId == R.id.action_cancel) {
			// Advance to the next step in the wizard. If there is no next step, setCurrentItem
			// will do nothing.
			processActionCancel();
			return true;
		}

        return super.onOptionsItemSelected(item);
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
	
	
	
	/**
	 * Process Action Ok
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 08:50:11
	 * @return
	 * @return boolean
	 */
	public boolean processActionOk() {
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		//	
		line.setFTA_Farm_ID(farmGridField.getValueAsInt());
		line.setFTA_FarmDivision_ID(farmDivisionGridField.getValueAsInt());
		line.setFTA_Farming_ID(farmingGridField.getValueAsInt());
		line.setFTA_FarmingStage_ID(farmingStageGridField.getValueAsInt());
		line.setFTA_ObservationType_ID(observationTypeGridField.getValueAsInt());
		line.setComments(commentsGridField.getValueAsString());
		line.setFTA_TechnicalForm_ID(Env.getContextAsInt(this, tabParam.getActivityNo(), "FTA_TechnicalForm_ID"));
		boolean ok = line.save();
		String msg = line.getError();
		if(msg != null)
			Msg.alertMsg(this, msg);
		
		if(!ok)
			return false;
		//	Put when is a Technical From Line added
		bundle.putBoolean("IsTechnicalFormLine", true);
		intent.putExtras(bundle);
		setResult(Activity.RESULT_OK, intent);
		//	Finish
		finish();
		return true;
	}
	
	/**
	 * Process Action Cancel
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 08:50:19
	 * @return
	 * @return boolean
	 */
	public boolean processActionCancel() {
		setResult(Activity.RESULT_CANCELED, null);
		finish();
		return true;
	}
	
	
	/**
	 * Include Class Thread
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
	 *
	 */
	private class LoadViewTask extends AsyncTask<Void, Integer, Integer> {

		/**	Layout					*/
		private LayoutParams		v_param	= null;
		/**	Progress Bar			*/
		private ProgressDialog 		v_PDialog;
		/**	Constant				*/
		private static final float 	WEIGHT_SUM 	= 2;
		private static final float 	WEIGHT 		= 1;
		/**	Lookups					*/
		private Lookup				farmLookup = null;
		private Lookup				farmDivisionLookup = null;
		private Lookup				farmingLookup = null;
		private Lookup				farmingStageLookup = null;
		private Lookup				observationTypeLookup = null;
		
		/**
		 * Init Values
		 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/05/2014, 12:18:42
		 * @return void
		 */
		private void init(){
	    	//	Set Parameter
	    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
	    			LayoutParams.MATCH_PARENT, WEIGHT);
	    	//	Load Table Info
	    	DB.loadConnection(conn, DB.READ_ONLY);
	    	tabInfo = new InfoTab(v_activity, tabParam.getSPS_Tab_ID(), conn);
	    	//	View
	    	v_PDialog.setMax(tabInfo.getLength());
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
			line = new MFTATechnicalFormLine(v_activity, p_FTA_TechnicalFormLine_ID, conn);
			//	Farm
			farmLookup = new Lookup(v_activity, 
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farm_ID, tabParam);
			farmLookup.load();
			//	Farm Division
			farmDivisionLookup = new Lookup(v_activity,
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_FarmDivision_ID, tabParam);
			farmDivisionLookup.load();
			//	Farming
			farmingLookup = new Lookup(v_activity,
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_Farming_ID, tabParam);
			farmingLookup.load();
			//	Farming Stage
			farmingStageLookup = new Lookup(v_activity, 
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_FarmingStage_ID, tabParam);
			farmingStageLookup.load();
			//	Observation Type
			observationTypeLookup = new Lookup(v_activity,
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_FTA_ObservationType_ID, tabParam);
			observationTypeLookup.load();
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
	    protected boolean loadView(){
	    	boolean ok = false;
	    	//	Farm and Farm Division
	    	LinearLayout v_rowFarm = new LinearLayout(v_activity);
			v_rowFarm.setOrientation(LinearLayout.HORIZONTAL);
			v_rowFarm.setWeightSum(WEIGHT_SUM);
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
			tl_TFLine.addView(v_rowFarm);
			//	Farming and Farming Stage
			LinearLayout v_rowFarming = new LinearLayout(v_activity);
			v_rowFarming.setOrientation(LinearLayout.HORIZONTAL);
			v_rowFarming.setWeightSum(WEIGHT_SUM);
			//	Farming
			farmingGridField = GridField.createLookup(v_activity, farmingLookup);
			farmingGridField.setLayoutParams(v_param);
			farmingGridField.setOnFieldChangeListener(m_Listener);
			v_rowFarming.addView(farmingGridField);
			//	Farming Stage
			farmingStageGridField = GridField.createLookup(v_activity, farmingStageLookup);
			farmingStageGridField.setLayoutParams(v_param);
			farmingStageGridField.setOnFieldChangeListener(m_Listener);
			v_rowFarming.addView(farmingStageGridField);
			tl_TFLine.addView(v_rowFarming);
			//	Observation Type
			observationTypeGridField = GridField.createLookup(v_activity, observationTypeLookup);
			observationTypeGridField.setOnFieldChangeListener(m_Listener);
			tl_TFLine.addView(observationTypeGridField);
			//	Comments
			commentsGridField = GridField.createLookup(v_activity, 
					I_FTA_TechnicalFormLine.Table_Name, 
					I_FTA_TechnicalFormLine.COLUMNNAME_Comments);
			tl_TFLine.addView(commentsGridField);
			//	Load Data
			if(p_FTA_TechnicalFormLine_ID != 0)
				loadData();
			//	
			return ok;
	    }
	    
	    /**
	     * Load Data from Parameter
	     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/08/2014, 16:02:07
	     * @return void
	     */
	    private void loadData() {
	    	farmGridField.setValue(line.getFTA_Farm_ID());
	    	farmDivisionGridField.setValue(line.getFTA_FarmDivision_ID());
	    	farmingGridField.setValue(line.getFTA_Farming_ID());
	    	farmingStageGridField.setValue(line.getFTA_FarmingStage_ID());
	    	observationTypeGridField.setValue(line.getFTA_ObservationType_ID());
	    	commentsGridField.setValue(line.getComments());
	    }
	}
}
