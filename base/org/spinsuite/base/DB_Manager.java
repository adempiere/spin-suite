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
package org.spinsuite.base;

import java.util.concurrent.atomic.AtomicInteger;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 22, 2015, 10:29:29 AM
 *
 */
public class DB_Manager {
	
	/**
	 * Create Instance from DB
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Helper
	 * @param
	 * @return void
	 */
    public static synchronized void createInstance(SQLiteOpenHelper p_Helper, boolean reload) {
        //	Verify if instance is created
    	if (m_Instance == null
    			|| reload) {
            m_Instance = new DB_Manager();
            m_DBH = p_Helper;
            m_Counter = new AtomicInteger();
        }
    }
    
    /**
     * Create Instance 
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_Helper
     * @return void
     */
    public static synchronized void createInstance(SQLiteOpenHelper p_Helper) {
    	createInstance(p_Helper, false);
    }
    
    /**	Database Helper					*/
    private static SQLiteOpenHelper 	m_DBH;
    /**	Data Base						*/
    private SQLiteDatabase 				m_DB;
    /**	Current Instance				*/
    private static DB_Manager 			m_Instance;
    /**	Counter							*/
    private static AtomicInteger 		m_Counter;

    /**
     * Get Current Instance
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return DB_Manager
     */
    public static synchronized DB_Manager getInstance() {
        //	Get Instance
    	return m_Instance;
    }

    /**
     * Open Database
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return SQLiteDatabase
     */
    public synchronized SQLiteDatabase open() {
        //	Verify if exists a database
    	if(m_Counter.incrementAndGet() == 1) {
            // Opening new database
            m_DB = m_DBH.getWritableDatabase();
        }
        return m_DB;
    }

    /**
     * Close Database
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    public synchronized void close() {
        if(m_Counter.decrementAndGet() == 0) {
            // Closing database
            m_DB.close();
        }
    }
}
