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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.model.MOrderLine;
import org.spinsuite.sfa.adapters.OrderLineAdapter;
import org.spinsuite.sfa.util.DisplayOrderLine;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.TV_DynamicActivity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 0:34:00
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class T_OrderLine extends Fragment implements I_DynamicTab {

	/**	Parameters	*/
	private 	TabParameter	 		tabParam				= null;
	private 	ListView				v_list					= null;
	private 	View 					m_View					= null;
	private 	TextView				tv_TotalLines			= null;
	private 	TextView				tv_GrandTotal			= null;
	private 	TextView				tv_lb_TotalLines		= null;
	private 	TextView				tv_lb_GrandTotal		= null;
	private 	boolean					m_IsLoadOk				= false;
	private 	boolean 				m_IsParentModifying		= false;
	private 	boolean 				m_Processed				= false;
	private		int 					m_C_Order_ID			= 0;
	private		TV_DynamicActivity		m_Callback				= null;
	private 	OrderLineAdapter 		m_Adapter 				= null;
	private 	int						m_LinesNo				= 0;
	
	/**
	 * *** Constructor ***
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_OrderLine() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	Current
		if(m_View != null)
			return m_View;
		
		//	Re-Load
		m_View 				= inflater.inflate(R.layout.t_order_line, container, false);
		v_list 				= (ListView) m_View.findViewById(R.id.lv_OrderLine);
		tv_TotalLines 		= (TextView) m_View.findViewById(R.id.tv_TotalLines);
		tv_GrandTotal 		= (TextView) m_View.findViewById(R.id.tv_GrandTotal);
		tv_lb_TotalLines	= (TextView) m_View.findViewById(R.id.tv_lb_TotalLines);
		tv_lb_GrandTotal	= (TextView) m_View.findViewById(R.id.tv_lb_GrandTotal);
		//	Add Listener for List
		v_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		v_list.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// Capture total checked items
				final int checkedCount = v_list.getCheckedItemCount();
				// Set the CAB title according to total checked items
				mode.setTitle(checkedCount + " " + getString(R.string.Selected));
				// Calls toggleSelection method from ListViewAdapter Class
				m_Adapter.toggleSelection(position);
				
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_delete:
					if(m_IsParentModifying) {
		    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
		    			return false;
		    		}
					//	Delete
					String msg_Acept = getResources().getString(R.string.msg_Acept);
					Builder ask = Msg.confirmMsg(getActivity(), getResources().getString(R.string.msg_AskDelete));
					//	Get Items
					final SparseBooleanArray selectedItems = m_Adapter.getSelectedItems();
					//	
					ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							//	Delete
							int[] ids = new int[selectedItems.size()];
							for (int i = (selectedItems.size() - 1); i >= 0; i--) {
								if (selectedItems.valueAt(i)) {
									DisplayOrderLine selectedItem = m_Adapter
											.getItem(selectedItems.keyAt(i));
									//	Add Value
									ids[i] = selectedItem.getC_OrderLine_ID();
									MOrderLine oLine = new MOrderLine(m_Callback, selectedItem.getC_OrderLine_ID(), null);
									try {
										oLine.deleteEx();
										//	Remove Item
										m_Adapter.remove(selectedItem);
									} catch (Exception e) {
										LogM.log(getActivity(), getClass(), Level.SEVERE, "Delete Ordel Line Error", e);
										Msg.toastMsg(m_Callback, e.getMessage());
									}
								}
							}
						}
					});
					//	Re-Query
					load();
					ask.show();
					//	
					mode.finish();
					return true;
				default:
					return false;
				}
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.general_multi_selection, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				m_Adapter.removeSelection();
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
			
		});
		//	Event
		registerForContextMenu(v_list);
		return m_View;
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		m_Callback = (TV_DynamicActivity) activity;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //	
        menu.clear();
        inflater.inflate(R.menu.dynamic_tab, menu);
    	//	do it
        //	Get Items
        MenuItem mi_Search 	= menu.findItem(R.id.action_search);
        MenuItem mi_Edit 	= menu.findItem(R.id.action_edit);
        MenuItem mi_Add	 	= menu.findItem(R.id.action_more);
        MenuItem mi_More 	= menu.findItem(R.id.action_more);
        MenuItem mi_Cancel 	= menu.findItem(R.id.action_cancel);
        MenuItem mi_Save 	= menu.findItem(R.id.action_save);
        //	Hide
        mi_Search.setVisible(false);
        mi_Edit.setVisible(false);
        mi_More.setVisible(false);
        mi_Cancel.setVisible(false);
        mi_Save.setVisible(false);
    	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return;
    	//	Visible Add
    	mi_Add.setEnabled(
				Env.getTabRecord_ID(getActivity(), tabParam.getActivityNo(), 0)[0] > 0
				&& !m_Processed);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int itemId = item.getItemId();
    	switch (itemId) {
		case R.id.action_add:
			if(m_IsParentModifying) {
    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
    			return false;
    		}
			Bundle bundle = new Bundle();
			bundle.putParcelable("TabParam", tabParam);
			bundle.putInt("C_Order_ID", m_C_Order_ID);
			
			Intent intent = new Intent(getActivity(), V_AddOrderLine.class);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
				+ "o.TotalLines, "
				+ "o.GrandTotal, "
				+ "c.CurSymbol, "
				+ "ol.C_OrderLine_ID, "
				+ "pc.Name ProductCategory, "
				+ "p.Value, "
				+ "p.Name ProductName, "
				+ "COALESCE(p.Description, '') Description, "
				+ "u.UOMSymbol, "
				+ "t.C_Tax_ID, "
				+ "t.TaxIndicator, "
				+ "t.Rate, "
				+ "ol.PriceEntered, "
				+ "ol.LineNetAmt, "
				+ "ol.QtyEntered "
				+ "FROM C_Order o "
				+ "INNER JOIN C_OrderLine ol ON (o.C_Order_ID = ol.C_Order_ID) "
				+ "INNER JOIN M_Product p ON (ol.M_Product_ID = p.M_Product_ID) "
				+ "INNER JOIN C_UOM u ON (ol.C_UOM_ID = u.C_UOM_ID) "
				+ "INNER JOIN C_Currency c ON(c.C_Currency_ID = o.C_Currency_ID) "
				+ "INNER JOIN M_Product_Category pc ON(pc.M_Product_Category_ID = p.M_Product_Category_ID) "
				+ "INNER JOIN C_Tax t ON(t.C_Tax_ID = ol.C_Tax_ID) "
				+ "WHERE o.C_Order_ID = ? "
				+ "ORDER BY ol.Line";
		
		
		LogM.log(getActivity(), getClass(), Level.FINE, "SQL=" + sql);
		conn.compileQuery(sql);
		conn.addInt(p_C_Order_ID);
		//	Get SQL
		Cursor rs = conn.querySQL();
		//	
		BigDecimal m_TotalLines = Env.ZERO;
		BigDecimal m_GrandTotal = Env.ZERO;
		String m_CurSymbol = null;
		//	
		ArrayList<DisplayOrderLine> data = new ArrayList<DisplayOrderLine>();
		m_LinesNo = 0;
		if(rs != null 
				&& rs.moveToFirst()){
			int index = 0;
			m_TotalLines 	= new BigDecimal(rs.getDouble(index++));
			m_GrandTotal 	= new BigDecimal(rs.getDouble(index++));
			m_CurSymbol		= rs.getString(index++);
			//	
			do {
				index = 3;
				m_LinesNo++;
				//
				data.add(
						new DisplayOrderLine(
								rs.getInt(index++),		//	Order Line
								rs.getString(index++),	//	Product Category
								rs.getString(index++),	//	Product Value
								rs.getString(index++),	//	Product Name 
								rs.getString(index++),	//	Product Description
								rs.getString(index++),	//	UOM Symbol
								rs.getInt(index++),		//	Tax ID
								rs.getString(index++),	//	Tax Indicator
								new BigDecimal(rs.getDouble(index++)),	//	Tax Rate
								new BigDecimal(rs.getDouble(index++)),	//	Price Entered
								new BigDecimal(rs.getDouble(index++)),	//	Line Net Amt
								new BigDecimal(rs.getDouble(index++))	//	Qty Entered
								)
						);
				//	
				index = 0;
			} while(rs.moveToNext());
			//	Set Load Ok
			m_IsLoadOk = true;
		}
		//	Close Connection
		DB.closeConnection(conn);
		//	
		DecimalFormat format = DisplayType.getNumberFormat(getActivity(), DisplayType.AMOUNT, "###,###,###,##0.00");
		//	Set Totals
		tv_TotalLines.setText(format.format(m_TotalLines));
		tv_GrandTotal.setText(format.format(m_GrandTotal));
		//	Add Symbol
		if(m_CurSymbol != null) {
			tv_lb_TotalLines.setText(getString(R.string.TotalLines) + " (" + m_CurSymbol + ")");
			tv_lb_GrandTotal.setText(getString(R.string.GrandTotal) + " (" + m_CurSymbol + ")");
		}
		//	Set Adapter
		m_Adapter = new OrderLineAdapter(getActivity(), data);
		m_Adapter.setDropDownViewResource(R.layout.i_ol_add_product);
		v_list.setAdapter(m_Adapter);
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
		if(reQuery) {
			load();
		}
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
		if (resultCode == Activity.RESULT_OK) {
			m_Callback.requestRefreshAll(true);
			load();
		}
	}

	@Override
	public void setIsParentModifying(boolean isParentModifying) {
		m_IsParentModifying = isParentModifying;
	}

	@Override
	public String getTabSuffix() {
		if(m_LinesNo == 0) {
			return null;
		}
		//	Return Lines
		return "(" + m_LinesNo + ")";
	}

}
