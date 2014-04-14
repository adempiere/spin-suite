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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class GPSHandler implements LocationListener {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/04/2014, 20:57:28
	 * @param ctx
	 */
	public GPSHandler(Context ctx) {
		m_ctx = ctx;
	}
	
	private Context				m_ctx = null;
	/**	Location Manager		*/
	private LocationManager 	m_LocManager = null;
	/**	Location Listener		*/
	private Location 			m_Location = null;
	/**	Minimum Time			*/
	private final long			MIN_TIME = 30000;
	/**	Minimum Distance		*/
	private final float			MIN_DISTANCE = 0;
	
	/**
	 * Init Location
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/04/2014, 21:06:35
	 * @return void
	 */
	public void startLocation() {
    	//	Get Location Manager
		m_LocManager = (LocationManager)m_ctx.getSystemService(Context.LOCATION_SERVICE);
    	//	Get Last Position
		m_Location = m_LocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	//	Register in Location
    	m_LocManager.requestLocationUpdates(
    			LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
    }

	/** 
	 * Is Provider Enabled
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/04/2014, 21:16:16
	 * @return
	 * @return boolean
	 */
	public boolean isProviderEnabled() {
		return m_LocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	/**
	 * Stop Location Manager
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/04/2014, 21:09:30
	 * @return void
	 */
	public void stopLocation() {
		m_LocManager.removeUpdates(this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		m_Location = location;
	}

	/**
	 * Get Latitude
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/04/2014, 21:13:06
	 * @return
	 * @return double
	 */
	public double getLatitude() {
		if(m_Location == null)
			return 0;
		return m_Location.getLatitude();
	}
	
	/**
	 * Get Longitude
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/04/2014, 21:13:34
	 * @return
	 * @return double
	 */
	public double getLongitude() {
		if(m_Location == null)
			return 0;
		return m_Location.getLongitude();
	}
	
	/**
	 * Get Precision
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/04/2014, 21:14:08
	 * @return
	 * @return float
	 */
	public float getPrecision() {
		if(m_Location == null)
			return 0;
		return m_Location.getAccuracy();
	}

	@Override
	public void onProviderDisabled(String provider) {
		m_Location = null;
	}


	@Override
	public void onProviderEnabled(String provider) {
		//	
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		//	
	}
	
}
