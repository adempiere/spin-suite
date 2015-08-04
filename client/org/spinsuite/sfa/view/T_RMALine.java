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
import org.spinsuite.model.MOrderLine;
import org.spinsuite.sfa.adapters.RMALineAdapter;
import org.spinsuite.sfa.util.DisplayRMALine;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.view.T_FormTab;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
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
public class T_RMALine extends T_FormTab {
	
	private 	ListView				v_list					= null;
	private 	View 					m_View					= null;
	private 	TextView				tv_Amt					= null;
	private 	TextView				tv_LineNetAmt			= null;
	private 	TextView				tv_lb_Amt				= null;
	private 	TextView				tv_lb_LineNetAmt		= null;
	private		int 					m_M_RMA_ID				= 0;
	private 	RMALineAdapter 			m_Adapter 				= null;
	private 	int						m_LinesNo				= 0;
	private 	BigDecimal 				m_Amt					= Env.ZERO;
	private 	BigDecimal 				m_LineNetAmt 			= Env.ZERO;
	
	/**
	 * *** Constructor ***
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_RMALine() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	Current
		if(m_View != null)
			return m_View;
		
		//	Re-Load
		m_View 				= inflater.inflate(R.layout.t_rma_line, container, false);
		v_list 				= (ListView) m_View.findViewById(R.id.lv_RMALine);
		tv_Amt 				= (TextView) m_View.findViewById(R.id.tv_Amt);
		tv_LineNetAmt 		= (TextView) m_View.findViewById(R.id.tv_LineNetAmt);
		tv_lb_Amt			= (TextView) m_View.findViewById(R.id.tv_lb_Amt);
		tv_lb_LineNetAmt	= (TextView) m_View.findViewById(R.id.tv_lb_LineNetAmt);
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
					if(isParentModifying()) {
		    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
		    			return false;
		    		} else if(isProcessed()) {
		    			Msg.toastMsg(getActivity(), "@Record@ @Processed@");
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
									DisplayRMALine selectedItem = m_Adapter
											.getItem(selectedItems.keyAt(i));
									//	Add Value
									ids[i] = selectedItem.getC_OrderLine_ID();
									MOrderLine oLine = new MOrderLine(getCallback(), selectedItem.getC_OrderLine_ID(), null);
									try {
										oLine.deleteEx();
										//	Remove Item
										m_Adapter.remove(selectedItem);
										
									} catch (Exception e) {
										LogM.log(getActivity(), getClass(), Level.SEVERE, "Delete Ordel Line Error", e);
										Msg.toastMsg(getCallback(), e.getMessage());
									}
								}
							}
							//	Refresh
							refreshAmt();
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
	
	/**
	 * Refresh Amount
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void refreshAmt() {
		//	Load DB
		DB conn = new DB(getActivity());
		DB.loadConnection(conn, DB.READ_ONLY);
		
		int p_M_RMA_ID = Env.getContextAsInt(getActivity(), getActivityNo(), "M_RMA_ID");
		//
		String sql = "SELECT "
				+ "SUM(rl.Amt) Amt, "
				+ "SUM(rl.LineNetAmt) LineNetAmt, "
				+ "c.CurSymbol "
				+ "FROM M_RMA r "
				+ "INNER JOIN M_RMALine rl ON(rl.M_RMA_ID = r.M_RMA_ID) "
				+ "INNER JOIN M_InOut i ON(i.M_InOut_ID = r.InOut_ID) "
				+ "INNER JOIN C_Order o ON(o.C_Order_ID = i.C_Order_ID) "
				+ "INNER JOIN C_Currency c ON(c.C_Currency_ID = o.C_Currency_ID) "
				+ "WHERE r.M_RMA_ID = ? ";
		//	Log
		LogM.log(getActivity(), getClass(), Level.FINE, "SQL=" + sql);
		conn.compileQuery(sql);
		conn.addInt(p_M_RMA_ID);
		//	Get SQL
		Cursor rs = conn.querySQL();
		//	
		m_Amt = Env.ZERO;
		m_LineNetAmt = Env.ZERO;
		//	
		if(rs != null 
				&& rs.moveToFirst()){
			int index = 0;
			m_Amt 	= new BigDecimal(rs.getDouble(index++));
			m_LineNetAmt 	= new BigDecimal(rs.getDouble(index++));
			String m_CurSymbol		= rs.getString(index++);
			//	Add Symbol
			if(m_CurSymbol != null) {
				tv_lb_Amt.setText(getString(R.string.Amt) + " (" + m_CurSymbol + ")");
				tv_lb_LineNetAmt.setText(getString(R.string.LineNetAmt) + " (" + m_CurSymbol + ")");
			}
		}
		DecimalFormat format = DisplayType
				.getNumberFormat(getActivity(), DisplayType.AMOUNT, "###,###,###,##0.00");
		//	Set Totals
		tv_Amt.setText(format.format(m_Amt));
		tv_LineNetAmt.setText(format.format(m_LineNetAmt));
		//	
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
    	if(!isLoadOk()) {
    		setIsLoadOk(true);
    	}
    	//	Visible Add
    	mi_Add.setEnabled(
				Env.getTabRecord_ID(getActivity(), getActivityNo(), 0)[0] > 0
				&& !isProcessed());
    	//	Load Parent Data
    	loadParent();
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int itemId = item.getItemId();
    	switch (itemId) {
		case R.id.action_add:
			if(isParentModifying()) {
    			Msg.toastMsg(getActivity(), "@ParentRecordModified@");
    			return false;
    		} else if(isProcessed()) {
    			Msg.toastMsg(getActivity(), "@Record@ @Processed@");
    			return false;
    		}
			Bundle bundle = new Bundle();
			bundle.putInt("M_RMA_ID", m_M_RMA_ID);
			
			Intent intent = new Intent(getActivity(), V_AddRMALine.class);
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
    	loadParent();
    	//	Load Data
		load(); 
    }
	
	/**
	 * Load Parent
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void loadParent() {
		if(getCallback() != null) {
	    	//	Get Sales Order Identifier
			m_M_RMA_ID = Env.getContextAsInt(getActivity(), getActivityNo(), "M_RMA_ID");
		}
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		load();
	}
   
	/**
	 * Load Data
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void load() {
		if(getCallback() == null
				 || Env.getTabRecord_ID(getActivity(), getActivityNo(), 0)[0] <= 0)
			return;
		//	Load DB
		DB conn = new DB(getActivity());
		DB.loadConnection(conn, DB.READ_ONLY);
		
		int p_M_RMA_ID = Env.getContextAsInt(getActivity(), getActivityNo(), "M_RMA_ID");
		//
		String sql = "SELECT "
				+ "rl.M_RMALine_ID, "
				+ "pc.Name ProductCategory, "
				+ "p.Value, "
				+ "p.Name ProductName, "
				+ "COALESCE(p.Description, '') Description, "
				+ "u.UOMSymbol, "
				+ "t.C_Tax_ID, "
				+ "t.TaxIndicator, "
				+ "t.Rate, "
				+ "ol.PriceEntered, "
				+ "rl.Qty "
				+ "FROM M_RMA r "
				+ "INNER JOIN M_RMALine rl ON(rl.M_RMA_ID = r.M_RMA_ID) "
				+ "INNER JOIN M_InOutLine il ON(il.M_InOutLine_ID = rl.M_InOutLine_ID) "
				+ "INNER JOIN C_OrderLine ol ON (il.C_OrderLine_ID = ol.C_OrderLine_ID) "
				+ "INNER JOIN M_InOut io ON(io.M_InOut_ID = r.InOut_ID) "
				+ "INNER JOIN C_Order o ON(o.C_Order_ID = io.C_Order_ID) "
				+ "INNER JOIN M_Product p ON (il.M_Product_ID = p.M_Product_ID) "
				+ "INNER JOIN C_UOM u ON (il.C_UOM_ID = u.C_UOM_ID) "
				+ "INNER JOIN C_Currency c ON(c.C_Currency_ID = o.C_Currency_ID) "
				+ "INNER JOIN M_Product_Category pc ON(pc.M_Product_Category_ID = p.M_Product_Category_ID) "
				+ "INNER JOIN C_Tax t ON(t.C_Tax_ID = ol.C_Tax_ID) "
				+ "WHERE r.M_RMA_ID = ? "
				+ "ORDER BY rl.Line";
		
		
		LogM.log(getActivity(), getClass(), Level.FINE, "SQL=" + sql);
		conn.compileQuery(sql);
		conn.addInt(p_M_RMA_ID);
		//	Get SQL
		Cursor rs = conn.querySQL();
		//	
		ArrayList<DisplayRMALine> data = new ArrayList<DisplayRMALine>();
		m_LinesNo = 0;
		if(rs != null 
				&& rs.moveToFirst()){
			int index = 0;
			//	
			do {
				m_LinesNo++;
				//
				data.add(
						new DisplayRMALine(
								rs.getInt(index++),						//	RMA Line
								rs.getString(index++),					//	Product Category
								rs.getString(index++),					//	Product Value
								rs.getString(index++),					//	Product Name 
								rs.getString(index++),					//	Product Description
								rs.getString(index++),					//	UOM Symbol
								rs.getInt(index++),						//	Tax ID
								rs.getString(index++),					//	Tax Indicator
								new BigDecimal(rs.getDouble(index++)),	//	Tax Rate
								new BigDecimal(rs.getDouble(index++)),	//	Price Entered
								new BigDecimal(rs.getDouble(index++))	//	Qty Return
								)
						);
				//	
				index = 0;
			} while(rs.moveToNext());
		}
		//	Refresh
		refreshAmt();
		//	Close Connection
		DB.closeConnection(conn);
		//	Set Adapter
		m_Adapter = new RMALineAdapter(getActivity(), data);
		v_list.setAdapter(m_Adapter);
	}

	@Override
	public boolean refreshFromChange(boolean reQuery) {
		if(reQuery) {
			load();
		}
		return false;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//	Hide Keyboard
		getActivity().getWindow()
					.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		if (resultCode == Activity.RESULT_OK) {
			getCallback().requestRefreshAll(true);
			load();
		}
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