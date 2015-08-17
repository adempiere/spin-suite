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
package org.spinsuite.view.lookup;

import java.util.ArrayList;

import org.spinsuite.adapters.MenuAdapter;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_MM_MenuOption;
import org.spinsuite.interfaces.I_MenuItemSelectListener;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.Env;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 17, 2015, 9:59:00 AM
 *
 */
public class MenuView {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_MainView
	 * @param isRegisterForContextMenu
	 */
	public MenuView(Activity p_Ctx, View p_MainView) {
		m_Callback = p_Ctx;
		v_MainView = p_MainView;
		//	Get Views
		tl_Menu = (TableLayout) v_MainView.findViewById(R.id.tl_Menu);
		lv_Menu = (ListView) v_MainView.findViewById(R.id.lv_Menu);
        //	
		lv_Menu.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				if(listener == null)
					return;
				//	Do it
				DisplayMenuItem item = (DisplayMenuItem) adapter.getItemAtPosition(position);
				listener.onItemClick(item);
			}
        });
    	//	Set Parameter
    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
    			LayoutParams.MATCH_PARENT, WEIGHT);
    	//	Set Parameter to Row
    	v_rowParam = new LayoutParams(LayoutParams.MATCH_PARENT, 
    			(int)m_Callback.getResources().getDimension(R.dimen.row_layout_height));
    	//	Set Padding
    	v_rowParam.leftMargin 	= MARGIN;
    	v_rowParam.rightMargin 	= MARGIN;
    	v_rowParam.topMargin 	= MARGIN;
    	v_rowParam.bottomMargin = MARGIN;
	}

	/**	Context				*/
	private Activity 		m_Callback;
	/**	Main View			*/
	private View 			v_MainView;
	/**	Table Menu			*/
	private TableLayout		tl_Menu;
	/**	List Menu			*/
	private ListView 		lv_Menu;
	/**	Constants for menu	*/
	public static final int	MENU_VIEW_TYPE_LIST 	= 0;
	public static final int	MENU_VIEW_TYPE_TABLE 	= 1;
	/**	For Table			*/
	private LinearLayout 	v_row;
	private	LayoutParams 	v_param ;
	private LayoutParams 	v_rowParam;
	/**	For distribution	*/
	private final float 	WEIGHT_SUM 	= 2;
	private final float 	WEIGHT 		= 1;
	private final int 		MARGIN 		= 3;
	/**	Columns Quantity	*/
	private int				m_QtyColumn;
	/**	Menu Adapter		*/
	private MenuAdapter 	m_Adapter;
	/**	Menu Option			*/
	private I_MM_MenuOption	m_MenuOption;
	
	
	/**	Listener			*/
	private I_MenuItemSelectListener listener;
	
	/**
	 * Set Listener
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Listener
	 * @return void
	 */
	public void setListener(I_MenuItemSelectListener p_Listener) {
		listener = p_Listener;
	}
	
	/**
	 * Set Menu Option
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_MenuOption
	 * @return void
	 */
	public void setMenuOption(I_MM_MenuOption p_MenuOption) {
		m_MenuOption = p_MenuOption;
	}
	
	/**
	 * Load View from Data
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param data
	 * @return void
	 */
	public void loadView(ArrayList<DisplayMenuItem> data) {
		int menuViewType = Env.getContextAsInt(m_Callback, "#MenuViewType");
		if(menuViewType == MENU_VIEW_TYPE_LIST) {
			//	Handle Visibility
			lv_Menu.setVisibility(View.VISIBLE);
			tl_Menu.setVisibility(View.GONE);
			loadListView(data);
		} else if(menuViewType == MENU_VIEW_TYPE_TABLE) {
			m_QtyColumn = Env.getContextAsInt(m_Callback, "#MenuViewTableColumnQty");
			if(m_QtyColumn == 0) {
				m_QtyColumn = 2;
			}
			//	Handle Visibility
			lv_Menu.setVisibility(View.GONE);
			tl_Menu.setVisibility(View.VISIBLE);
			//	
			loadTableView(data);
		}
	}
	
	/**
	 * Load View for Table
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param data
	 * @return void
	 */
	private void loadTableView(ArrayList<DisplayMenuItem> data) {
		//	Remove all views
		if(tl_Menu.getChildCount() > 0) {
			tl_Menu.removeViews(0, tl_Menu.getChildCount());
		}
		int position = 0;
		for(DisplayMenuItem item : data) {
			if((position % m_QtyColumn) == 0
					|| v_row == null) {
				v_row = new LinearLayout(m_Callback);
				v_row.setOrientation(LinearLayout.HORIZONTAL);
				v_row.setWeightSum(WEIGHT_SUM);
				v_row.setLayoutParams(v_rowParam);
				//	Add to Table
				tl_Menu.addView(v_row);
			}
			//	Add Row Column
			v_row.addView(getImageText(item, position));
			//	Change Value
			position++;
		}
	}
	
	/**
	 * Get new Item
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param item
	 * @param position
	 * @return
	 * @return VLookupImageText
	 */
	private VLookupImageText getImageText(DisplayMenuItem item, int position) {
		VLookupImageText itemMenu = new VLookupImageText(m_Callback);
		itemMenu.setName(item.getName());
		itemMenu.setDescription(item.getDescription());
		itemMenu.setDisplayMenuItem(item);
		itemMenu.setListener(listener);
		itemMenu.setMenuOption(m_MenuOption);
		//	Set Image
		if(item.getImageURL() != null 
				&& item.getImageURL().length() > 0) {
			Resources res = m_Callback.getResources();
			int resID = res.getIdentifier(item.getImageURL() , "drawable", m_Callback.getPackageName());
			if(resID != 0) {
				Drawable drawable = res.getDrawable(resID);
				itemMenu.setItemImageDrawable(drawable);
			}
		} else if(item.isSummary()) {
			itemMenu.setItemImageResource(
					Env.getResourceID(m_Callback, R.attr.ic_ml_folder));
		} else {
			if(item.getAction().equals(DisplayMenuItem.ACTION_Form)
					|| item.getAction().equals(DisplayMenuItem.ACTION_Window)) {
				itemMenu.setItemImageResource(
						Env.getResourceID(m_Callback, R.attr.ic_ml_window));
			} else if(item.getAction().equals(DisplayMenuItem.ACTION_Process)) {
				itemMenu.setItemImageResource(
						Env.getResourceID(m_Callback, R.attr.ic_ml_process));
			} else if(item.getAction().equals(DisplayMenuItem.ACTION_Report)) {
				itemMenu.setItemImageResource(
						Env.getResourceID(m_Callback, R.attr.ic_ml_report));
			}
			else if(item.getAction().equals(DisplayMenuItem.ACTION_WSDownload)) {
				itemMenu.setItemImageResource(
						Env.getResourceID(m_Callback, R.attr.ic_ml_download));
			}
			else if(item.getAction().equals(DisplayMenuItem.ACTION_WSUpload)) {
				itemMenu.setItemImageResource(
						Env.getResourceID(m_Callback, R.attr.ic_ml_upload));
			}
		}
		//	Set Parameters
		itemMenu.setLayoutParams(v_param);
		//	Default return
		return itemMenu;
	}
	
	/**
	 * Load for List View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param data
	 * @return void
	 */
	private void loadListView(ArrayList<DisplayMenuItem> data) {
		m_Adapter = new MenuAdapter(m_Callback, R.layout.i_image_text, true, data);
		m_Adapter.setMenuOption(m_MenuOption);
		m_Adapter.setDropDownViewResource(R.layout.i_image_text);
		lv_Menu.setAdapter(m_Adapter);
	}
	
	/**
	 * Get Table
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return TableLayout
	 */
	public TableLayout getTableView() {
		return tl_Menu;
	}
	
	/**
	 * Get List
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return ListView
	 */
	public ListView getListView() {
		return lv_Menu;
	}
	
	/**
	 * Get List Adapter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return MenuAdapter
	 */
	public MenuAdapter getListAdapter() {
		return m_Adapter;
	}
}