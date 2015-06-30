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
package org.spinsuite.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.adapters.DisplayProducts;
import org.spinsuite.adapters.OrderLineAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.model.MOrder;
import org.spinsuite.model.MOrderLine;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.util.TabParameter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 0:34:00
 *
 */
public class LV_COrderLine extends Fragment implements I_DynamicTab {

	/**	Parameters	*/
	private 	TabParameter	 		tabParam				= null;
	private 	ListView				v_list					= null;
	private 	Button					v_button				= null;
	private 	View 					m_View					= null;
	private 	boolean					m_IsLoadOk				= false;
	private 	boolean 				m_IsParentModifying		= false;
	private 	boolean 				m_Processed				= false;
	private		int 					m_C_Order_ID			= 0;
	
	private static final int 			O_DELETE 				= 1;
	
	/**
	 * *** Constructor ***
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public LV_COrderLine() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	Current
		if(m_View != null)
			return m_View;
		
		//	Re-Load
		m_View = inflater.inflate(R.layout.t_order_line, container, false);
		
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
				bundle.putInt("C_Order_ID", m_C_Order_ID);
				
				Intent intent = new Intent(getActivity(), V_AddOrderLine.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 0);
			}
		});
		
		
		v_list = (ListView) m_View.findViewById(R.id.lv_OrderLine);
		//	Event
		registerForContextMenu(v_list);
		return m_View;
		
	}
	
	@Override
	public void onResume() {
    	super.onResume();
    	//	Get Sales Order Identifier
		m_C_Order_ID = Env.getContextAsInt(getActivity(), tabParam.getActivityNo(), "C_Order_ID");

		//	Load Data
		load(); 

		//	Set Processed
		m_Processed = 
				Env.getContextAsBoolean(getActivity(), tabParam.getActivityNo(), "Processed") 
					|| !Env.getWindowsAccess(getActivity(), tabParam.getSPS_Window_ID());
		
		//	Load View
		loadView();
    	
    }

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lv_OrderLine
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
	 * Action Delete 
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param position
	 * @return void
	 */
	private void actionDelete(int position) {
		final DisplayProducts item = (DisplayProducts) v_list.getAdapter().getItem(position);
		String msg_Acept = this.getResources().getString(R.string.msg_Acept);
		Builder ask = Msg.confirmMsg(getActivity(), getResources().getString(R.string.msg_AskDelete));
		ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//	Delete
				MOrderLine oLine = new MOrderLine(getActivity(), item.getOrderLine_ID(), null);
				updateHeader(oLine.getCtx(),oLine, oLine.getLineNetAmt(),null);
				try {
					oLine.deleteEx();
				} catch (Exception e) {;}
				//	Re-Query
				load();
			}
		});
		ask.show();
	}
	
	/**
	 * Update Header
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param oLine
	 * @param p_TotalLines
	 * @param conn
	 * @return void
	 */
	private void updateHeader(Context ctx, MOrderLine oLine,BigDecimal p_TotalLines, DB conn) {
		MOrder order = new MOrder(ctx, oLine.getC_Order_ID(), conn);
		order.setTotalLines(order.getTotalLines().subtract(p_TotalLines));
		if(order.isTaxIncluded())
			order.setGrandTotal(order.getGrandTotal().subtract(p_TotalLines));
		else {
			String sql = "SELECT COALESCE(SUM(it.TaxAmt),0) "
					+ "FROM C_OrderTax it "
					+ "WHERE it.C_Order_ID = " + order.getC_Order_ID();
			BigDecimal taxAmt = new BigDecimal(DB.getSQLValueString(ctx, sql));
			order.setGrandTotal(order.getGrandTotal().subtract(p_TotalLines.add(taxAmt)));
		}
		
		try {
			order.saveEx();
		} catch (Exception e) {;}
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//	
		Bundle bundle = getArguments();
		if(bundle != null)
			tabParam = bundle.getParcelable("TabParam");
		//	Is Not ok Load
		if(tabParam == null)
			return;
		//	Set Processed
		m_Processed = 
				Env.getContextAsBoolean(getActivity(), tabParam.getActivityNo(), "Processed") 
					|| !Env.getWindowsAccess(getActivity(), tabParam.getSPS_Window_ID());
		
		//	Get Sales Order Identifier
		m_C_Order_ID = Env.getContextAsInt(getActivity(), tabParam.getActivityNo(), "C_Order_ID");

		//	Load Data
    	if(!m_IsLoadOk)
    		load();
    	//	Load the view
    	loadView();
	}
   
	/**
	 * Load Data
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void load() {
		if(Env.getTabRecord_ID(getActivity(), tabParam.getActivityNo(), 0)[0] <= 0)
			return;
		//	Load DB
		DB conn = new DB(getActivity());
		DB.loadConnection(conn, DB.READ_ONLY);
		
		int p_C_Order_ID = Env.getContextAsInt(getActivity(), tabParam.getActivityNo(), "C_Order_ID");
		//
		String sql = "SELECT "
				+ "ol.C_OrderLine_ID, "
				+ "p.Value, "
				+ "p.Name, "
				+ "COALESCE(p.Description, '') Description, "
				+ "u.UOMSymbol, "
				+ "ol.PriceEntered, "
				+ "ol.LineNetAmt, "
				+ "ol.QtyEntered "
				+ "FROM C_Order o "
				+ "INNER JOIN C_OrderLine ol ON (o.C_Order_ID = ol.C_Order_ID) "
				+ "INNER JOIN M_Product p ON (ol.M_Product_ID = p.M_Product_ID) "
				+ "INNER JOIN C_Uom u ON (ol.C_UOM_ID = u.C_UOM_ID) "
				+ "WHERE o.C_Order_ID = " + p_C_Order_ID;
		
		
		LogM.log(getActivity(), getClass(), Level.FINE, "SQL=" + sql);
		Cursor rs = conn.querySQL(sql, null);
		//
		ArrayList<DisplayProducts> data = new ArrayList<DisplayProducts>();
		if(rs.moveToFirst()){
			do {
				int index = 0;
				//
				data.add(
						new DisplayProducts(
								rs.getInt(index++),	//	Order Line
								rs.getString(index++),	//	Product Value
								rs.getString(index++),	//	Product Name 
								rs.getString(index++),	//	Product Description
								rs.getString(index++),	//	UOM Symbol
								new BigDecimal(rs.getDouble(index++)),	//	Price Entered
								new BigDecimal(rs.getDouble(index++)),	//	Line Net Amt
								new BigDecimal(rs.getDouble(index++))	//	Qty Entered
								) 
						);
				//	
				index = 0;
			}while(rs.moveToNext());
			//	Set Load Ok
			m_IsLoadOk = true;
		}
		//	Close Connection
		DB.closeConnection(conn);
		//	Set Adapter
		OrderLineAdapter p_Adapter = new OrderLineAdapter(getActivity(), data);
		p_Adapter.setDropDownViewResource(R.layout.i_ol_product);
		v_list.setAdapter(p_Adapter);
	}
	
	/**
	 * Load Data
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void loadView() {
		//	
		v_button.setEnabled(
				Env.getTabRecord_ID(getActivity(), tabParam.getActivityNo(), 0)[0] > 0
				&& !m_Processed);
	}
	
	@Override
	public void handleMenu() {

	}

	@Override
	public TabParameter getTabParameter() {
		return tabParam;
	}

	@Override
	public void setTabParameter(TabParameter tabParam) {

	}

	@Override
	public boolean refreshFromChange(boolean reQuery) {
		m_IsLoadOk = false;
		return false;
	}

	@Override
	public boolean save() {
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public boolean isModifying() {
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
	public void setIsParentModifying(boolean isParentModifying) {
		m_IsParentModifying = isParentModifying;
	}

}
