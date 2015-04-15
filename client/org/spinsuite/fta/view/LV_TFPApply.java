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
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.fta.adapters.DisplayTFPApply;
import org.spinsuite.fta.adapters.TFPApplyAdapter;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
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
import android.view.WindowManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LV_TFPApply extends Fragment 
							implements I_DynamicTab {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/05/2014, 16:59:56
	 */
	public LV_TFPApply() {
		// 
	}
	
	/**	Parameters	*/
	private 	TabParameter	 		tabParam				= null;
	private 	ListView				v_list					= null;
	private 	Button					v_button				= null;
	private 	View 					m_View					= null;
	private 	boolean					m_IsLoadOk				= false;
	private 	boolean 				m_Processed				= false;
	private 	int 					m_FTA_TechnicalForm_ID 	= 0;
	private 	boolean 				m_IsParentModifying		= false;
	//	
	private static final int 			O_DELETE 				= 1;
	
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
				bundle.putInt("FTA_TechnicalForm_ID", m_FTA_TechnicalForm_ID);
				bundle.putInt("FTA_TechnicalFormLine_ID", 0);
				Intent intent = new Intent(getActivity(), V_AddSuggestedProduct.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		});
		//	
		v_list = (ListView) m_View.findViewById(R.id.lv_TFLPA);
		//	Event
		registerForContextMenu(v_list);
		return m_View;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lv_TFLPA
				&& !m_Processed) {
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
	 * Delete Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/06/2014, 09:04:50
	 * @param position
	 * @return void
	 */
	private void actionDelete(int position) {
		final DisplayTFPApply item = (DisplayTFPApply) v_list.getAdapter().getItem(position);
		String msg_Acept = this.getResources().getString(R.string.msg_Acept);
		Builder ask = Msg.confirmMsg(getActivity(), getResources().getString(R.string.msg_AskDelete));
		ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//	Delete
				DB.executeUpdate(getActivity(), "DELETE FROM FTA_ProductsToApply " +
						"WHERE FTA_ProductsToApply_ID = ?", item.getFTA_ProductsToApply_ID());
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
    	//	Get Technical Form Identifier
    	m_FTA_TechnicalForm_ID = Env.getContextAsInt(getActivity(), 
				tabParam.getActivityNo(), "FTA_TechnicalForm_ID");
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
		String sql = new String("SELECT pa.FTA_TechnicalForm_ID, pa.FTA_ProductsToApply_ID, (p.Value || '_' || p.Name) Product, " +
				"(strftime('%s', pa.DateFrom)*1000) DateFrom, " +
				"(strftime('%s', pa.DateTo)*1000) DateTo, " +
				"pa.QtySuggested, su.UOMSymbol SuggestedUOM, " +
				"pa.QtyDosage, ud.UOMSymbol DosageUOM, " +
				"pa.Qty, u.UOMSymbol UOM, w.Name Warehouse, COALESCE(pa.IsApplied, 'N') IsApplied " +
				"FROM FTA_ProductsToApply pa " +
				"INNER JOIN M_Product p ON(p.M_Product_ID = pa.M_Product_ID) " +
				"LEFT JOIN C_UOM su ON(su.C_UOM_ID = pa.Suggested_Uom_ID) " +
				"LEFT JOIN C_UOM ud ON(ud.C_UOM_ID = pa.Dosage_Uom_ID) " +
				"LEFT JOIN C_UOM u ON(u.C_UOM_ID = pa.C_UOM_ID) " +
				"LEFT JOIN M_Warehouse w ON(w.M_Warehouse_ID = pa.M_Warehouse_ID) " +
				"WHERE pa.FTA_TechnicalForm_ID = " + Env.getContextAsInt(getActivity(), 
						tabParam.getActivityNo(), "FTA_TechnicalForm_ID"));
		LogM.log(getActivity(), getClass(), Level.FINE, "SQL=" + sql);
		Cursor rs = conn.querySQL(sql, null);
		//	
		ArrayList<DisplayTFPApply> data = new ArrayList<DisplayTFPApply>();
		if(rs.moveToFirst()){
			do {
				int index = 0;
				//	
				data.add(new DisplayTFPApply(
						rs.getInt(index++), 
						rs.getInt(index++), 
						rs.getString(index++), 
						new Date(rs.getLong(index++)), 
						new Date(rs.getLong(index++)), 
						rs.getDouble(index++), 
						rs.getString(index++), 
						rs.getDouble(index++), 
						rs.getString(index++), 
						rs.getDouble(index++), 
						rs.getString(index++), 
						rs.getString(index++), 
						rs.getString(index++).equals("Y")));
				//	
				index = 0;
			}while(rs.moveToNext());
			//	Set Load Ok
			m_IsLoadOk = true;
		}
		//	Close Connection
		DB.closeConnection(conn);
		//	Set Adapter
		TFPApplyAdapter mi_adapter = new TFPApplyAdapter(getActivity(), data);
		mi_adapter.setDropDownViewResource(R.layout.i_tf_suggested_product);
		v_list.setAdapter(mi_adapter);
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
		//	Hide Keyboard
		getActivity().getWindow()
					.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		if (resultCode == Activity.RESULT_OK)
			load();
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
