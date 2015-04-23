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
package org.spinsuite.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 22, 2015, 12:43:39 AM
 *
 */
public class ImageCacheLru extends LruCache<String, Bitmap>{

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param maxSize
	 */
	public ImageCacheLru(int maxSize) {
		super(maxSize);
	}
	
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getByteCount();
	}
	 
	@Override
	protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
		oldValue.recycle();
	}
}
