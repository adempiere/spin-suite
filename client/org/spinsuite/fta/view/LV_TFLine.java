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
import org.spinsuite.fta.adapters.DisplayTFLine;
import org.spinsuite.fta.adapters.TFLineAdapter;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.model.I_FTA_Farming;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.util.TabParameter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LV_TFLine extends Fragment 
							implements I_DynamicTab {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/05/2014, 16:59:56
	 */
	public LV_TFLine() {
		// 
	}
	
	/**	Parameters	*/
	private 	TabParameter	 		tabParam					= null;
	private 	ListView				v_list						= null;
	private 	Button					v_button					= null;
	private 	View 					m_View						= null;
	private 	boolean					m_IsLoadOk					= false;
	private 	boolean 				m_Processed					= false;
	private 	int 					m_FTA_Farming_ID 			= 0;
	private 	int 					m_FTA_TechnicalForm_ID 		= 0;
	private 	int 					m_FTA_TechnicalFormLine_ID 	= 0;
	private 	boolean 				m_IsParentModifying			= false;
	//	
	private static final int 			O_SUGGEST_PRODUCT 			= 1;
	private static final int 			O_APPLIED_PRODUCT 			= 2;
	private static final int 			O_DELETE 					= 3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	Current
		if(m_View != null)
			return m_View;
		//	Re-Load
		m_View = inflater.inflate(R.layout.t_technical_form_line, container, false);
    	//	Scroll
		v_button = (Button) m_View.findViewById(R.id.bt_Add);
		v_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//	
				if(m_IsParentModifying) {
	    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
	    			return;
	    		}
				Bundle bundle = new Bundle();
				bundle.putParcelable("TabParam", tabParam);
				Intent intent = new Intent(getActivity(), V_AddTFLine.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		});
		//	
		v_list = (ListView) m_View.findViewById(R.id.lv_TFLPA);
		//	
		v_list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				//	Valid Processed
				if(m_Processed)
					return;
				//	
				DisplayTFLine item = (DisplayTFLine) v_list.getAdapter().getItem(position);
				//	Show Record
				if(item.getFTA_TechnicalForm_ID() != 0){
					//	
					if(m_IsParentModifying) {
		    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
		    			return;
		    		}
					Bundle bundle = new Bundle();
					bundle.putParcelable("TabParam", tabParam);
					bundle.putInt("FTA_TechnicalFormLine_ID", item.getFTA_TechnicalFormLine_ID());
					Intent intent = new Intent(getActivity(), V_AddTFLine.class);
					intent.putExtras(bundle);
					startActivityForResult(intent, 0);
				}
			}
        });
		//	Event
		registerForContextMenu(v_list);
		return m_View;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		//	Add Delete Option
		if (v.getId() == R.id.lv_TFLPA
				&& !m_Processed) {
			//	Add Suggest Option
			menu.add(Menu.NONE, O_SUGGEST_PRODUCT, 
					Menu.NONE, getString(R.string.Action_Suggest_Product));
			//	Load Applied Product
			menu.add(Menu.NONE, O_APPLIED_PRODUCT, 
					Menu.NONE, getString(R.string.Action_Applied_Product));
			//	Delete
		    menu.add(Menu.NONE, O_DELETE, 
					Menu.NONE, getString(R.string.Action_Delete));
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
	            .getMenuInfo();
	    //	Options
	    switch (item.getItemId()) {
	    	case O_SUGGEST_PRODUCT:
	    		if(m_IsParentModifying) {
	    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
	    			return false;
	    		}
	    		actionSuggestAppliedProduct(info.position, item.getItemId());
	    		return true;
	    	case O_APPLIED_PRODUCT:
	    		if(m_IsParentModifying) {
	    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
	    			return false;
	    		}
	    		actionSuggestAppliedProduct(info.position, item.getItemId());
	    		return true;
	    	case O_DELETE:
	    		if(m_IsParentModifying) {
	    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
	    			return false;
	    		}
	    		actionDelete(info.position);
		        return true;
		    default:
		        return super.onContextItemSelected(item);
	    }
	}
	
	/**
	 * Suggest Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/09/2014, 21:50:27
	 * @param position
	 * @param option
	 * @return void
	 */
	private void actionSuggestAppliedProduct(int position, int option) {
		final DisplayTFLine item = (DisplayTFLine) v_list.getAdapter().getItem(position);
		//	Set Category to Context
		Env.setContext(getActivity(), tabParam.getActivityNo(), tabParam.getTabNo(), 
				I_FTA_Farming.COLUMNNAME_Category_ID, item.getCategory_ID());
		//	
		Bundle bundle = new Bundle();
		bundle.putParcelable("TabParam", tabParam);
		m_FTA_TechnicalForm_ID = item.getFTA_TechnicalForm_ID();
		m_FTA_TechnicalFormLine_ID = item.getFTA_TechnicalFormLine_ID();
		bundle.putInt("FTA_TechnicalForm_ID", m_FTA_TechnicalForm_ID);
		bundle.putInt("FTA_TechnicalFormLine_ID", m_FTA_TechnicalFormLine_ID);
		//	Add Farming
		if(option == O_APPLIED_PRODUCT) {
			m_FTA_Farming_ID = item.getFTA_Farming_ID();
			bundle.putInt("FTA_Farming_ID", m_FTA_Farming_ID);
		}
		//	
		Intent intent = new Intent(getActivity(), V_AddSuggestedProduct.class);
		intent.putExtras(bundle);
		startActivityForResult(intent, 0);
	}
	
	
	/**
	 * Delete Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/06/2014, 09:04:50
	 * @param position
	 * @return void
	 */
	private void actionDelete(int position) {
		final DisplayTFLine item = (DisplayTFLine) v_list.getAdapter().getItem(position);
		String msg_Acept = this.getResources().getString(R.string.msg_Acept);
		Builder ask = Msg.confirmMsg(getActivity(), getResources().getString(R.string.msg_AskDelete));
		ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//	Delete Child
				DB.executeUpdate(getActivity(), "DELETE FROM FTA_ProductsToApply " +
						"WHERE FTA_TechnicalFormLine_ID = ?", item.getFTA_TechnicalFormLine_ID());
				//	Delete
				DB.executeUpdate(getActivity(), "DELETE FROM FTA_TechnicalFormLine " +
						"WHERE FTA_TechnicalFormLine_ID = ?", item.getFTA_TechnicalFormLine_ID());
				//	Re-Query
				load();
			}
		});
		ask.show();
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	//	
    	Bundle bundle = getArguments();
    	if(bundle != null)
			tabParam = (TabParameter)bundle.getParcelable("TabParam");
		//	Is Not ok Load
    	if(tabParam == null)
    		return;
    	//	Set Processed
    	m_Processed = Env.getContextAsBoolean(getActivity(), 
    			tabParam.getActivityNo(), "Processed")
    			|| !Env.getWindowsAccess(getActivity(), tabParam.getSPS_Window_ID());
    	//	Load Data
    	if(!m_IsLoadOk)
    		load();
    	//	Load the view
    	loadView();
	}
	
	/**
	 * Load View
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 11:27:16
	 * @return void
	 */
	private void loadView() {
		//	
		v_button.setEnabled(
				Env.getTabRecord_ID(getActivity(), tabParam.getActivityNo(), 0)[0] > 0
				&& !m_Processed);
	}
	
	/**
	 * Load Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 10:25:57
	 * @return void
	 */
	private void load() {
		if(Env.getTabRecord_ID(getActivity(), tabParam.getActivityNo(), 0)[0] <= 0)
			return;
		//	Load DB
		DB conn = new DB(getActivity());
		DB.loadConnection(conn, DB.READ_ONLY);
		//	
		String sql = new String("SELECT tf.FTA_TechnicalForm_ID, tfl.FTA_TechnicalFormLine_ID, fm.Category_ID, " +
				"tfl.FTA_Farm_ID, f.Name Farm, " +
				"tfl.FTA_FarmDivision_ID, fd.Name FarmDivision, " +
				"tfl.FTA_Farming_ID, (pr.Value || ' - ' || pr.Name) Farming, " +
				"tfl.FTA_FarmingStage_ID, fs.Name FarmingStage, " +
				"tfl.FTA_ObservationType_ID, ot.Name ObservationType, " +
				"tfl.Comments " +
				"FROM FTA_TechnicalForm tf " +
				"INNER JOIN FTA_TechnicalFormLine tfl ON(tfl.FTA_TechnicalForm_ID = tf.FTA_TechnicalForm_ID) " +
				"INNER JOIN FTA_FarmDivision fd ON(fd.FTA_FarmDivision_ID = tfl.FTA_FarmDivision_ID) " +
				"INNER JOIN FTA_Farm f ON(f.FTA_Farm_ID = fd.FTA_Farm_ID) " +
				"INNER JOIN FTA_Farming fm ON(fm.FTA_Farming_ID = tfl.FTA_Farming_ID) " +
				"INNER JOIN M_Product pr ON(pr.M_Product_ID = fm.Category_ID) " +
				"INNER JOIN FTA_FarmingStage fs ON(fs.FTA_FarmingStage_ID = tfl.FTA_FarmingStage_ID) " +
				"INNER JOIN FTA_ObservationType ot ON(ot.FTA_ObservationType_ID = tfl.FTA_ObservationType_ID) " +
				"WHERE tf.FTA_TechnicalForm_ID = " + Env.getContextAsInt(getActivity(), tabParam.getActivityNo(), "FTA_TechnicalForm_ID"));
		LogM.log(getActivity(), getClass(), Level.FINE, "SQL=" + sql);
		Cursor rs = conn.querySQL(sql, null);
		//	
		ArrayList<DisplayTFLine> data = new ArrayList<DisplayTFLine>();
		if(rs.moveToFirst()){
			do {
				int index = 0;
				//	
				data.add(new DisplayTFLine(
						rs.getInt(index++), 
						rs.getInt(index++),
						rs.getInt(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						rs.getString(index++)));
				//	
				index = 0;
			}while(rs.moveToNext());
			//	Set Load Ok
			m_IsLoadOk = true;
		}
		//	Close Connection
		DB.closeConnection(conn);
		//	Set Adapter
		TFLineAdapter adapter = new TFLineAdapter(getActivity(), data);
		adapter.setDropDownViewResource(R.layout.i_tf_line);
		v_list.setAdapter(adapter);
	}

	@Override
	public void handleMenu() {
		// 

	}

	@Override
	public TabParameter getTabParameter() {
		// 
		return tabParam;
	}

	@Override
	public void setTabParameter(TabParameter tabParam) {
		// 
	}

	@Override
	public boolean refreshFromChange(boolean reQuery) {
		//	
		m_IsLoadOk = false;
		return false;
	}

	@Override
	public boolean save() {
		// 
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 
		return false;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//	Load when is Ok
		if (resultCode == Activity.RESULT_OK) {
			if(data != null){
	    		Bundle bundle = data.getExtras();
	    		if(bundle.getBoolean("IsTechnicalFormLine"))
	    			load();
			}
		}
	}

	@Override
	public boolean isModifying() {
		return false;
	}

	@Override
	public void setIsParentModifying(boolean isParentModifying) {
		m_IsParentModifying = isParentModifying;
	}
}
