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

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_MM_MenuOption;
import org.spinsuite.interfaces.I_MenuItemSelectListener;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.Env;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 17, 2015, 7:22:11 AM
 *
 */
public class VLookupImageText extends LinearLayout implements OnClickListener {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 */
	public VLookupImageText(Context context) {
		super(context);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param attrs
	 */
	public VLookupImageText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupImageText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	public VLookupImageText(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}
	
	/**	Image					*/
	private ImageView					img_Item;
	/**	Menu Image				*/
	private ImageView					img_Menu;
	/**	Name					*/
	private TextView					tv_Name;
	/**	Description				*/
	private TextView					tv_Description;
	/**	Listener				*/
	private I_MenuItemSelectListener 	listener;
	/**	Array Position			*/
	private DisplayMenuItem 			m_MenuItem;
	/**	Menu Option				*/
	private I_MM_MenuOption				m_MenuOption;
	
	/**
	 * Init Layout
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void init() {
		inflate(getContext(), R.layout.i_image_text, this);
		img_Item = (ImageView) findViewById(R.id.img_Item);
		img_Menu = (ImageView) findViewById(R.id.img_Menu);
		tv_Name = (TextView) findViewById(R.id.tv_Name);
		tv_Description = (TextView) findViewById(R.id.tv_Description);
		//	Set Style
		tv_Name.setTextAppearance(getContext(), R.style.TextItemMenu);
		tv_Description.setTextAppearance(getContext(), R.style.TextItemSmallMenu);
	}

	/**
	 * Set Menu Option
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_MenuOption
	 * @return void
	 */
	public void setMenuOption(I_MM_MenuOption p_MenuOption) {
		m_MenuOption = p_MenuOption;
		if(m_MenuOption != null) {
			img_Menu.setVisibility(View.VISIBLE);
			img_Menu.setImageResource(Env.getResourceID(getContext(), R.attr.ic_st_menu));
			img_Menu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showPopupForTab(v);
				}
			});
		} else {
			img_Menu.setVisibility(View.GONE);
		}
	}
	
	/**
	 * Show Popup option
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param v
	 * @return void
	 */
	private void showPopupForTab(View v) {
	    if(m_MenuOption == null)
	    	return;
		PopupMenu popup = new PopupMenu(getContext(), v);
	    Menu menu = popup.getMenu();
	    //	Add Option Menu
	    int [] options = m_MenuOption.getMenuOption();
	    for(int i = 0; i < options.length; i++) {
	    	menu.add(Menu.NONE, options[i], 
					Menu.NONE, getContext().getString(options[i]));
	    }
	    //	Add Listener
	    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//	Valid Null
				if(m_MenuOption == null)
					return false;
				//	Process
				m_MenuOption.actionMenu(getContext(), 
						item.getItemId(), m_MenuItem);
				//	Default
				return false;
			}
		});
	    //	Show
	    popup.show();
	}
	
	/**
	 * Set Image of Menu
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param resid
	 * @return void
	 */
	public void setItemImageResource(int resid) {
		img_Item.setImageResource(resid);
	}
	
	/**
	 * Set Image Drawable
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param drawable
	 * @return void
	 */
	public void setItemImageDrawable(Drawable drawable) {
		img_Item.setImageDrawable(drawable);
	}
	
	/**
	 * Set Item Image Bitmap
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param image
	 * @return void
	 */
	public void setItemImageBitmap(Bitmap image) {
		img_Item.setImageBitmap(image);
	}
	
	/**
	 * Set Image of Menu
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param resid
	 * @return void
	 */
	public void setMenuImageResource(int resid) {
		img_Menu.setImageResource(resid);
	}
	
	/**
	 * Set Image Drawable
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param drawable
	 * @return void
	 */
	public void setMenuImageDrawable(Drawable drawable) {
		img_Menu.setImageDrawable(drawable);
	}
	
	/**
	 * Get Image Menu
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return ImageView
	 */
	public ImageView getImageMenu() {
		return img_Menu;
	}
	
	/**
	 * Get Image Item
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return ImageView
	 */
	public ImageView getImageItem() {
		return img_Item;
	}
	
	/**
	 * Set Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Name
	 * @return void
	 */
	public void setName(String p_Name) {
		tv_Name.setText(p_Name);
	}
	
	/**
	 * Set Description
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Description
	 * @return void
	 */
	public void setDescription(String p_Description) {
		tv_Description.setText(p_Description);
	}
	
	/**
	 * Set Position in Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Item
	 * @return void
	 */
	public void setDisplayMenuItem(DisplayMenuItem p_Item) {
		m_MenuItem = p_Item;
	}

	/**
	 * Set Listener
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Listener
	 * @return void
	 */
	public void setListener(I_MenuItemSelectListener p_Listener) {
		listener = p_Listener;
		if(listener != null) {
			//	Listener
			img_Item.setOnClickListener(this);
			tv_Name.setOnClickListener(this);
			tv_Description.setOnClickListener(this);
			setOnClickListener(this);
		}
	}
	
	@Override
	public void onClick(View v) {
		if(listener == null)
			return;
		//	Do it
		listener.onItemClick(m_MenuItem);
	}
}