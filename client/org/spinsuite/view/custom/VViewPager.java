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
package org.spinsuite.view.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VViewPager extends ViewPager {

	private boolean enabled;
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/05/2013, 00:32:30
	 * @param context
	 */
	public VViewPager(Context context) {
		super(context);
		enabled = true;
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/05/2013, 00:32:30
	 * @param context
	 * @param attrs
	 */
	public VViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		enabled = true;
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (enabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (enabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    /**
     * Set Enable Page
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/05/2013, 00:35:21
     * @param enabled
     * @return void
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Get Enable Page
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/05/2013, 00:35:32
     * @return
     * @return boolean
     */
    public boolean isPagingEnabled() {
        return enabled;
    }

}
