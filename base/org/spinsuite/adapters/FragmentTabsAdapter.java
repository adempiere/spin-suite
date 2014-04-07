/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.adapters;

import java.util.ArrayList;

import org.spinsuite.util.Cust_TabFactory;
import org.spinsuite.util.TabInfo;
import org.spinsuite.view.custom.Cust_ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @author Yamel Senih
 *
 */
public class FragmentTabsAdapter extends FragmentPagerAdapter {
    private Context 			mContext;
    private TabHost 			mTabHost;
    private FragmentManager 	fm;
    private ArrayList<TabInfo> 	mTabs = new ArrayList<TabInfo>();

    /**
     * 
     * *** Constructor ***
     * @author Yamel Senih 06/02/2013, 12:49:26
     * @param fm
     * @param ctx
     */
    public FragmentTabsAdapter(FragmentActivity activity, TabHost mTabHost, Cust_ViewPager pager) {
        super(activity.getSupportFragmentManager());
        this.fm = activity.getSupportFragmentManager();
        mContext = activity;
        this.mTabHost = mTabHost;
        pager.setAdapter(this);
    }

    /**
     * Add Tab Fragment in ArrayList
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 14:55:15
     * @param clazz
     * @param tag
     * @param title
     * @param bundle
     * @return void
     */
    public void addTab(Class<?> clazz, String tag, String title, Bundle bundle) {
    	TabSpec tabSpec = mTabHost.newTabSpec(tag).setIndicator(title);
        tabSpec.setContent(new Cust_TabFactory(mContext));
        
        TabInfo info = new TabInfo(clazz, title, bundle);
        mTabs.add(info);
        mTabHost.addTab(tabSpec);
        notifyDataSetChanged();
    }
    
    /**
     * Add Tab Fragment in ArrayList
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 15:03:16
     * @param clazz
     * @param tag
     * @param titleID
     * @param bundle
     * @return void
     */
    public void addTab(Class<?> clazz, String tag, int titleID, Bundle bundle) {
    	addTab(clazz, tag, mContext.getResources().getString(titleID), bundle);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(mContext, mTabs.get(position).getClazz().getName(), mTabs.get(position).getArgs());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
	
	/*@Override
	public int getIconResId(int index) {
		return mTabs.get(index).getIcon_ID();
	}*/
	
	/**
     * get Active Fragment
     * @author Yamel Senih 01/02/2013, 10:19:54
     * @param position
     * @return
     * @return Fragment
     */
    public Fragment getActiveFragment(int position, ViewPager mViewPager) {
    	String name = makeFragmentName(mViewPager, position);
    	return  fm.findFragmentByTag(name);
    }
    
    /**
     * Get Current Fragment
     * @author Yamel Senih 04/02/2013, 17:14:33
     * @return
     * @return Fragment
     */
    public Fragment getCurrentFragment(ViewPager mViewPager) {
    	String name = makeFragmentName(mViewPager, mViewPager.getCurrentItem());
    	return  fm.findFragmentByTag(name);
    }
    
    /**
     * Make Fragment Name
     * @author Yamel Senih 01/02/2013, 10:20:05
     * @param mViewPager
     * @param index
     * @return
     * @return String
     */
	public String makeFragmentName(ViewPager mViewPager, int index) {
        return "android:switcher:" + mViewPager.getId() + ":" + index;
    }
}
