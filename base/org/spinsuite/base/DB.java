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


import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.KeyNamePair;
import org.spinsuite.util.LogM;
import org.spinsuite.util.MultiKeyNamePair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * 
 * @author Yamel Senih
 *
 */
public class DB extends SQLiteOpenHelper {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:43:37
	 * @param ctx
	 * @param name
	 * @param factory
	 * @param version
	 * @param sqlCreate
	 */
	public DB(Context ctx, String name, CursorFactory factory,
			int version, String sqlCreate) {
		super(ctx, 	name, factory, version);
		this.sqlCreate = sqlCreate;
		this.ctx = ctx;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:43:29
	 * @param ctx
	 */
	public DB(Context ctx) {
		super(ctx, Env.getDB_PathName(ctx), null, DB_VERSION);
		this.ctx = ctx;
	}
	
	private String 				sqlCreate;
	private String 				sqlUpdate;
	private SQLiteDatabase 		db;
	private SQLiteStatement 	stm;
	public static final int 	READ_ONLY = 0;
	public static final int 	READ_WRITE = 1;
	public static final String 	DB_NAME = "SpinSuite";
	public static final int 	DB_VERSION = 1;
	/**	Context					*/
	private Context 			ctx;
	/**	SQL						*/
	private String				m_SQL = null;
	/**	Parameters				*/
	private ArrayList<String>	m_Parameters = null;
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase bd) {
		if(sqlCreate != null
				&& sqlCreate.length() > 0)
			bd.execSQL(sqlCreate);
	}
	
	//@Override
	//public void onConfigure(SQLiteDatabase db) {
		//super.onConfigure(db);
		//	Enable Constraints
		//db.setForeignKeyConstraintsEnabled(true);
	//}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		if(sqlUpdate != null
				&& sqlUpdate.length() > 0)
		db.execSQL(sqlUpdate);
	}
	
	/**
	 * Is Datablase Integrity Ok
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/05/2014, 14:37:17
	 * @return
	 * @return boolean
	 */
	public boolean isDatabaseIntegrityOk() {
		return db.isDatabaseIntegrityOk();
	}
	
	/**
	 * Open database in mode read or read write
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:43:49
	 * @param type
	 * @return
	 * @return SQLiteDatabase
	 */
	public SQLiteDatabase openDB(int type) {
		if(type == READ_ONLY) {
			db = getReadableDatabase();
		}else if(type == READ_WRITE) {
			db = getWritableDatabase();
		}
		return db;
	}
	
	/**
	 * Get database
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:44:15
	 * @return
	 * @return SQLiteDatabase
	 */
	public SQLiteDatabase getDB() {
		return db;
	}

	/**
	 * Close DB
	 * @author Yamel Senih 03/12/2012, 01:47:20
	 * @param rs
	 * @return void
	 */
	public void closeDB(Cursor rs) {
		if(rs != null && !rs.isClosed())
			rs.close();
		db.close();
		LogM.log(ctx, getClass(), Level.INFO, "Closed");
	}
	
	/**
	 * Verifi if is open database
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:44:37
	 * @return
	 * @return boolean
	 */
	public boolean isOpen() {
		boolean ok = false;
		if(db != null) {
			ok = db.isOpen();
		}
		return ok;
	}
	
	/**
	 * End transaction
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:44:54
	 * @return void
	 */
	public void endTransaction() {
		db.endTransaction();
	}
	
	/**
	 * Begin transaction
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:45:07
	 * @return void
	 */
	public void beginTransaction() {
		db.beginTransaction();
	}
	
	/**
	 * Set Successful transaction
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:45:34
	 * @return void
	 */
	public void setTransactionSuccessful() {
		db.setTransactionSuccessful();
	}
	
	/**
	 * Execute SQL
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:46:02
	 * @param sql
	 * @return void
	 */
	public void executeSQL(String sql) {
		LogM.log(ctx, getClass(), Level.FINE, "SQL=" + sql);
		db.execSQL(sql);
	}
	
	/**
	 * Execute SQL with parameters
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:46:22
	 * @param sql
	 * @param params
	 * @return void
	 */
	public void executeSQL(String sql, Object [] params) {
		LogM.log(ctx, getClass(), Level.FINE, "SQL=" + sql);
		db.execSQL(sql, params);
	} 
	
	/**
	 * Insert on table
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:46:44
	 * @param table
	 * @param columnaNull
	 * @param values
	 * @return long
	 */
	public long insertSQL(String table, String columnaNull, ContentValues values) {
		return db.insert(table, columnaNull, values);
	}
	
	/**
	 * Update table
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:47:03
	 * @param table
	 * @param values
	 * @param where
	 * @param argmWhere
	 * @return int
	 */
	public int updateSQL(String table, ContentValues values, String where, String [] argmWhere) {
		return db.update(table, values, where, argmWhere);
	}
	
	/**
	 * Delete over table
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:47:14
	 * @param table
	 * @param where
	 * @param argmWhere
	 * @return int
	 */
	public int deleteSQL(String table, String where, String [] argmWhere) {
		return db.delete(table, where, argmWhere);
	}
	
	/**
	 * Get SQL with parameters
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:47:26
	 * @param sql
	 * @param values
	 * @return
	 * @return Cursor
	 */
	public Cursor querySQL(String sql, String [] values) {
		LogM.log(ctx, getClass(), Level.FINE, "SQL=" + sql);
		return db.rawQuery(sql, values);
	}
	
	/**
	 * Get SQL wit all values
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:48:10
	 * @param table
	 * @param col
	 * @param where
	 * @param argsWhere
	 * @param group
	 * @param having
	 * @param orden
	 * @param limit
	 * @return
	 * @return Cursor
	 */
	public Cursor querySQL(String table, String [] col, 
			String where, String [] argsWhere, String group, 
			String having, String orden, String limit) {
		return db.query(table, col, where, argsWhere, group, having, orden, limit);
	}
	
	/**
	 * Compile statement
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:48:23
	 * @param sql
	 * @return
	 * @return SQLiteStatement
	 */
	public SQLiteStatement compileSQL(String sql) {
		stm = db.compileStatement(sql);
		return stm;
	}
	
	/**
	 * Add Query for next Execution
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 20:28:46
	 * @param sql
	 * @return void
	 */
	public void compileQuery(String sql) {
		m_SQL = sql;
		m_Parameters = new ArrayList<String>();
	}
	
	/**
	 * Get Statement
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:48:35
	 * @return
	 * @return SQLiteStatement
	 */
	public SQLiteStatement getStatement() {
		return stm;
	}
	
	/**
	 * Add Integer Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 19:52:02
	 * @param value
	 * @return DB
	 */
	public DB addInt(int value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	
		m_Parameters.add(String.valueOf(value));
		//	Return
		return this;
	}
	
	/**
	 * Add Long Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 19:55:58
	 * @param value
	 * @return DB
	 */
	public DB addLong(long value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	
		m_Parameters.add(String.valueOf(value));
		//	Return
		return this;
	}
	
	/**
	 * Add String Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 19:58:11
	 * @param value
	 * @return DB
	 */
	public DB addString(String value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	
		m_Parameters.add(value);
		//	Return
		return this;
	}
	
	/**
	 * Add Double Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 19:59:26
	 * @param value
	 * @return DB
	 */
	public DB addDouble(double value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	
		m_Parameters.add(String.valueOf(value));
		//	Return
		return this;
	}
	
	/**
	 * Add Date Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 20:11:20
	 * @param value
	 * @return DB
	 */
	public DB addDate(Date value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	Get JDBC Value
		String date = (String) DisplayType
				.getJDBC_Value(DisplayType.DATE, value);
		//	
		m_Parameters.add(date);
		//	Return
		return this;
	}
	
	/**
	 * Add Boolean Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/10/2014, 20:04:52
	 * @param value
	 * @return DB
	 */
	public DB addBoolean(boolean value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	Get JDBC Value
		String stringValue = (String) DisplayType
				.getJDBC_Value(DisplayType.YES_NO, value);
		//	
		m_Parameters.add(stringValue);
		//	Return
		return this;
	}
	
	/**
	 * Add Date and Time Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 20:11:56
	 * @param value
	 * @return DB
	 */
	public DB addDateTime(Date value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	Get JDBC Value
		String date = (String) DisplayType
				.getJDBC_Value(DisplayType.DATE_TIME, value);
		//	
		m_Parameters.add(date);
		//	Return
		return this;
	}
	
	/**
	 * Add a Parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/10/2014, 20:16:32
	 * @param value
	 * @return DB
	 */
	public DB addParameter(Object value) {
		if(m_Parameters == null)
			m_Parameters = new ArrayList<String>();
		//	Validate
		if(value == null)
			addString(null);
		else if(value instanceof Integer)
			addInt((Integer)value);
		else if(value instanceof Long)
			addLong((Long)value);
		else if(value instanceof Double)
			addDouble((Double)value);
		else if(value instanceof Boolean)
			addBoolean((Boolean)value);
		else if(value instanceof String)
			addString((String)value);
		//	Return
		return this;
	}
	
	/**
	 * Clear Parameters
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 20:13:05
	 * @return void
	 */
	public void clearParameters() {
		m_Parameters = new ArrayList<String>();
	}
	
	/**
	 * Excecute Precompiled Query
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 20:48:58
	 * @return
	 * @return Cursor
	 */
	public Cursor querySQL() {
		String [] values = null;
		//	Add Parameters
		if(m_Parameters != null
				&& m_Parameters.size() > 0) {
			values = new String[m_Parameters.size()];
			m_Parameters.toArray(values);
		}
		//	Excecute Query
		return querySQL(m_SQL, values);
	}
	
	/**
	 * Load a Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/02/2014, 09:15:36
	 * @param conn
	 * @param type
	 * @return
	 */
	public static void loadConnection(DB conn, int type) {
		if(conn != null
				&& !conn.isOpen()) {
			conn.openDB(type);
			if(type == READ_WRITE)
				conn.beginTransaction();
		}
    }
	
	/**
	 * Instance and load Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/10/2014, 20:52:00
	 * @param ctx
	 * @param type
	 * @return void
	 */
	public static DB loadConnection(Context ctx, int type) {
		DB conn = new DB(ctx);
		conn.openDB(type);
		if(type == READ_WRITE)
			conn.beginTransaction();
		//	Return
		return conn;
    }
	
	/**
	 * Close a Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/02/2014, 09:17:46
	 * @param conn
	 * @return void
	 */
	public static void closeConnection(DB conn) {
		if(conn != null 
				&& conn.isOpen()) {
			if(conn.getDB().inTransaction())
				conn.endTransaction();
			conn.close();
		}
    }
	
	/**
	 * Execute Update.
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 11:58:14
	 * @param ctx
	 * @param sql
	 * @param param
	 * @param conn
	 * @return
	 * @return int
	 */
	public static int executeUpdate(Context ctx, String sql, int param, DB conn) {
		int no = -1;
		try {
			no = executeUpdate (ctx, sql, param, true, conn);
		} catch(Exception e) {
			LogM.log(ctx, DB.class, Level.SEVERE, "SQL=[" + sql + "] " +  e.getLocalizedMessage());
		}
		return no;
	}	//	executeUpdate
	
	/**
	 * Excecute Update Ignore Error
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/10/2014, 20:32:31
	 * @param ctx
	 * @param sql
	 * @param conn
	 * @return
	 * @return int
	 */
	public static int executeUpdate(Context ctx, String sql, DB conn) {
		int no = -1;
		try {
			no = executeUpdate(ctx, sql, null, true, conn);
		} catch(Exception e) {
			LogM.log(ctx, DB.class, Level.SEVERE, "SQL=[" + sql + "] " +  e.getLocalizedMessage());
		}
		return no;
	}	//	executeUpdate
	
	/**
	 * Execute Update
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 22:22:56
	 * @param ctx
	 * @param sql
	 * @param param
	 * @return
	 * @return int
	 */
	public static int executeUpdate(Context ctx, String sql, int param) {
		return executeUpdate(ctx, sql, param, null);
	}
	
	/**
	 * Excecute Update with throw
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/10/2014, 20:29:51
	 * @param ctx
	 * @param sql
	 * @param conn
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public static int executeUpdateEx(Context ctx, String sql, DB conn) throws Exception {
		return executeUpdate(ctx, sql, null, false, conn);
	}

	/**
	 * Execute Update.
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 11:56:05
	 * @param ctx
	 * @param sql
	 * @param param
	 * @param ignoreError
	 * @return
	 * @return int
	 * @throws Exception 
	 */
	public static int executeUpdate (Context ctx, String sql, int param, boolean ignoreError) throws Exception{
		return executeUpdate(ctx, sql, new Object[]{param}, ignoreError, null);
	}
	
	/**
	 * Execute Update
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 22:21:36
	 * @param ctx
	 * @param sql
	 * @param param
	 * @param ignoreError
	 * @param conn
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public static int executeUpdate (Context ctx, String sql, int param, boolean ignoreError, DB conn) throws Exception{
		return executeUpdate(ctx, sql, new Object[]{param}, ignoreError, conn);
	}
	
	/**
	 * Execute a Update
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 11:52:58
	 * @param ctx
	 * @param sql
	 * @param params
	 * @param ignoreError
	 * @param conn
	 * @return
	 * @return int
	 * @throws Exception 
	 */
	public static int executeUpdate (Context ctx, String sql, Object[] params, boolean ignoreError, DB conn) throws Exception{
		if (sql == null || sql.length() == 0)
			throw new IllegalArgumentException("Required parameter missing - " + sql);
		//	
		boolean handConnection = false;
		int no = -1;
		try {
			//	Instance Connection
			if(conn == null) {
				conn = new DB(ctx);
				//	load
				loadConnection(conn, READ_WRITE);
				handConnection = true;
			}
			//	
			if(params != null)
				conn.executeSQL(sql, params);
			else
				conn.executeSQL(sql);
			//	
			no = 1;
			//	End Transaction
			if(handConnection)
				conn.setTransactionSuccessful();
		} catch (Exception e) {
			if (ignoreError)
				LogM.log(ctx, DB.class, Level.SEVERE, "SQL=[" + sql + "] " +  e.getLocalizedMessage());
			else
				throw e;
		} finally {
			//	Close Connection
			if(handConnection)
				closeConnection(conn);
		}
		return no;
	}	//	executeUpdate
	
	/**
	 * Get Value from SQL
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/02/2014, 09:21:49
	 * @param ctx
	 * @param sql
	 * @param conn
	 * @param params
	 * @return
	 * @return String
	 */
	public static String getSQLValueStringEx(Context ctx, String sql, DB conn, String... params) {
		boolean handConnection = false;
		//	Instance Connection
		if(conn == null) {
			conn = new DB(ctx);
			//	load
			loadConnection(conn, READ_WRITE);
			handConnection = true;
		}
		//	Log
		LogM.log(ctx, "DB", Level.FINE, "SQL[" + sql + "]");
		//	
		Cursor rs = null;
		rs = conn.querySQL(sql, params);
		String retValue = null;
		if(rs.moveToFirst()) {
			retValue = rs.getString(0);
		}
		//	Close Connection
		if(handConnection)
			closeConnection(conn);
		//	Return
		return retValue;
	}
	
	/**
	 * Without connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 22:28:54
	 * @param ctx
	 * @param sql
	 * @param params
	 * @return
	 * @return String
	 */
	public static String getSQLValueStringEx(Context ctx, String sql, String... params) {
		 return getSQLValueStringEx(ctx, sql, null, params);
	}
	
	/**
	 * Get Key Name Pairs
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 31/03/2014, 11:14:56
	 * @param ctx
	 * @param sql
	 * @param optional
	 * @param conn
	 * @param params
	 * @return
	 * @return KeyNamePair[]
	 * @throws Exception 
	 */
	public static KeyNamePair[] getKeyNamePairsEx(Context ctx, String sql, boolean optional, DB conn, String... params) throws Exception {
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
        //	If is Optional
		if (optional) {
            list.add (new KeyNamePair(-1, ""));
        }
		boolean handConnection = false;
		try {
			//	Instance Connection
			if(conn == null) {
				conn = new DB(ctx);
				//	load
				loadConnection(conn, READ_WRITE);
				handConnection = true;
			}
			Cursor rs = null;
			rs = conn.querySQL(sql, params);
			//	Add to List
			if(rs.moveToFirst()) {
				do{
					list.add(new KeyNamePair(rs.getInt(0), rs.getString(1)));
				}while(rs.moveToNext());
			}
			//	Close Connection
			if(handConnection)
				closeConnection(conn);
		} catch (Exception e) {
			throw e;
		} finally {
			//	Close Connection
			if(handConnection)
				closeConnection(conn);
		}
		//	
		KeyNamePair[] retValue = new KeyNamePair[list.size()];
        list.toArray(retValue);
		//	Return
		return retValue;
	}
	
	/**
	 * Get Multikey Values
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:05:06
	 * @param ctx
	 * @param sql
	 * @param keyCount
	 * @param optional
	 * @param conn
	 * @param params
	 * @return
	 * @throws Exception
	 * @return MultiKeyNamePair[]
	 */
	public static MultiKeyNamePair[] getMultiKeyNamePairsEx(Context ctx, String sql, 
			int keyCount, boolean optional, DB conn, String... params) throws Exception {
		ArrayList<MultiKeyNamePair> list = new ArrayList<MultiKeyNamePair>();
        //	If is Optional
		if (optional) {
            list.add (MultiKeyNamePair.EMPTY);
        }
		//	
		boolean handConnection = false;
		try {
			//	Instance Connection
			if(conn == null) {
				conn = new DB(ctx);
				//	load
				loadConnection(conn, READ_WRITE);
				handConnection = true;
			}
			Cursor rs = null;
			rs = conn.querySQL(sql, params);
			//	Add to List
			if(rs.moveToFirst()) {
				do{
					//	Declare Keys
					int[] keys = new int[keyCount];
					//	Get Keys
					for(int i = 0; i < keyCount; i++) {
						keys[i] = rs.getInt(i);
					}
					//	
					list.add(new MultiKeyNamePair(keys, rs.getString(keyCount)));
				}while(rs.moveToNext());
			}
			//	Close Connection
			if(handConnection)
				closeConnection(conn);
		} catch (Exception e) {
			throw e;
		} finally {
			//	Close Connection
			if(handConnection)
				closeConnection(conn);
		}
		//	
		MultiKeyNamePair[] retValue = new MultiKeyNamePair[list.size()];
        list.toArray(retValue);
		//	Return
		return retValue;
	}
	
	/**
	 * Get Key Name Pairs without connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 22:30:30
	 * @param ctx
	 * @param sql
	 * @param optional
	 * @param params
	 * @return
	 * @return KeyNamePair[]
	 * @throws Exception 
	 */
	public static KeyNamePair[] getKeyNamePairsEx(Context ctx, String sql, boolean optional, String... params) throws Exception {
		return getKeyNamePairsEx(ctx, sql, optional, null, params);
	}
	
	/**
	 * Get MultiKey Name Pairs without connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:06:10
	 * @param ctx
	 * @param sql
	 * @param keyCount
	 * @param optional
	 * @param params
	 * @return
	 * @throws Exception
	 * @return MultiKeyNamePair[]
	 */
	public static MultiKeyNamePair[] getMultiKeyNamePairsEx(Context ctx, String sql, int keyCount, boolean optional, String... params) throws Exception {
		return getMultiKeyNamePairsEx(ctx, sql, keyCount, optional, null, params);
	}
	
	/**
	 * Get Key Name Pairs
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 31/03/2014, 11:15:56
	 * @param ctx
	 * @param sql
	 * @param conn
	 * @param params
	 * @return
	 * @return KeyNamePair[]
	 */
	public static KeyNamePair[] getKeyNamePairs(Context ctx, String sql, DB conn, String... params) {
		KeyNamePair[] retValue = null;
		try{
			retValue = getKeyNamePairsEx(ctx, sql, false, conn, params);
		} catch(Exception e) {
			LogM.log(ctx, "DB", Level.SEVERE, "SQLError", e);
		}
		return retValue;
	}
	
	/**
	 * Get MultiKey Name Pair
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:07:15
	 * @param ctx
	 * @param sql
	 * @param keyCount
	 * @param conn
	 * @param params
	 * @return
	 * @return MultiKeyNamePair[]
	 */
	public static MultiKeyNamePair[] getMultiKeyNamePairs(Context ctx, String sql, int keyCount, DB conn, String... params) {
		MultiKeyNamePair[] retValue = null;
		try{
			retValue = getMultiKeyNamePairsEx(ctx, sql, keyCount, false, conn, params);
		} catch(Exception e) {
			LogM.log(ctx, "DB", Level.SEVERE, "SQLError", e);
		}
		return retValue;
	}
	
	/**
	 * Get Key Name Pairs without connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 22:31:49
	 * @param ctx
	 * @param sql
	 * @param params
	 * @return
	 * @return KeyNamePair[]
	 */
	public static KeyNamePair[] getKeyNamePairs(Context ctx, String sql, String... params) {
		return getKeyNamePairs(ctx, sql, null, params);
	}
	
	/**
	 * Get Key Name Pairs without connection with a parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 16:30:40
	 * @param ctx
	 * @param sql
	 * @param param
	 * @return
	 * @return KeyNamePair[]
	 */
	public static KeyNamePair[] getKeyNamePairs(Context ctx, String sql, int param) {
		return getKeyNamePairs(ctx, sql, null, new String[]{String.valueOf(param)});
	}
	
	/**
	 * Get MultiKey Name Pairs without connection with a parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:11:20
	 * @param ctx
	 * @param sql
	 * @param keyCount
	 * @param param
	 * @return
	 * @return MultiKeyNamePair[]
	 */
	public static MultiKeyNamePair[] getMultiKeyNamePairs(Context ctx, String sql, int keyCount, int param) {
		return getMultiKeyNamePairs(ctx, sql, keyCount, null, new String[]{String.valueOf(param)});
	}
	
	/**
	 * Get MultiKey Name Pairs without connection with a parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:13:36
	 * @param ctx
	 * @param sql
	 * @param keyColumn
	 * @param params
	 * @return
	 * @return MultiKeyNamePair[]
	 */
	public static MultiKeyNamePair[] getMultiKeyNamePairs(Context ctx, String sql, int keyColumn, String... params) {
		return getMultiKeyNamePairs(ctx, sql, keyColumn, null, params);
	}
	
	/**
	 * Get Value from SQL as int
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/03/2014, 14:24:00
	 * @param ctx
	 * @param sql
	 * @param conn
	 * @param params
	 * @return
	 * @return int
	 */
	public static int getSQLValueEx(Context ctx, String sql, DB conn, String... params) {
		boolean handConnection = false;
		//	Instance Connection
		if(conn == null) {
			conn = new DB(ctx);
			//	load
			handConnection = true;
		}
		//	
		loadConnection(conn, READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL(sql, params);
		int retValue = -1;
		if(rs.moveToFirst()) {
			retValue = rs.getInt(0);
		}
		//	Close Connection
		if(handConnection)
			closeConnection(conn);
		//	Return
		return retValue;
	}
	
	/**
	 * Get SQL Value with exception without parameters
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/10/2014, 10:51:39
	 * @param ctx
	 * @param sql
	 * @param params
	 * @return
	 * @return int
	 */
	public static int getSQLValueEx(Context ctx, String sql, String... params) {
		return getSQLValueEx(ctx, sql, null, params);
	}
	
	/**
	 * Get Value SQL as int
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/03/2014, 14:25:42
	 * @param ctx
	 * @param sql
	 * @return
	 * @return int
	 */
	public static int getSQLValueEx(Context ctx, String sql) {
		return getSQLValueEx(ctx, sql, null, (String[])null);
	}
	
	/**
	 * Get Value without Exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/03/2014, 14:41:53
	 * @param ctx
	 * @param sql
	 * @return
	 * @return int
	 */
	public static int getSQLValue(Context ctx, String sql) {
		return getSQLValue(ctx, sql, null, (String[])null);
	}
	
	/**
	 * With parameters
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 10:55:44
	 * @param ctx
	 * @param sql
	 * @param conn
	 * @param params
	 * @return
	 * @return int
	 */
	public static int getSQLValue(Context ctx, String sql, DB conn, String... params) {
		int retValue = -1;
		try{
			retValue = getSQLValueEx(ctx, sql, conn, params);
		} catch(Exception e) {
			LogM.log(ctx, "DB", Level.SEVERE, "SQLError", e);
		}
		return retValue;
	}
	
	/**
	 * get SQL Value without connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 22:34:51
	 * @param ctx
	 * @param sql
	 * @param params
	 * @return
	 * @return int
	 */
	public static int getSQLValue(Context ctx, String sql, String... params) {
		return getSQLValue(ctx, sql, null, params);
	}
	
	/**
	 * Get Value from SQL without parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/02/2014, 09:25:04
	 * @param ctx
	 * @param sql
	 * @return
	 * @return String
	 */
	public static String getSQLValueStringEx(Context ctx, String sql) {
		return getSQLValueStringEx(ctx, sql, (String[])null);
	}
	
	/**
	 * Get Value from sql without Exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/02/2014, 09:43:18
	 * @param ctx
	 * @param sql
	 * @return
	 * @return String
	 */
	public static String getSQLValueString(Context ctx, String sql) {
		String retValue = null;
		try{
			retValue = getSQLValueStringEx(ctx, sql, (String[])null);
		} catch(Exception e) {
			LogM.log(ctx, "DB", Level.SEVERE, "SQLError", e);
		}
		return retValue;
	}
	
	/**
	 * Get Value from sql without Exception, with parameters
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/02/2014, 09:44:22
	 * @param ctx
	 * @param sql
	 * @param params
	 * @return
	 * @return String
	 */
	public static String getSQLValueString(Context ctx, String sql, String... params) {
		String retValue = null;
		try{
			retValue = getSQLValueStringEx(ctx, sql, params);
		} catch(Exception e) {
			LogM.log(ctx, "DB", Level.SEVERE, "SQLError", e);
		}
		return retValue;
	}
	
	/**
	 * Get Value from sql without Exception, with parameters and Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/09/2014, 21:58:46
	 * @param ctx
	 * @param sql
	 * @param conn
	 * @param params
	 * @return
	 * @return String
	 */
	public static String getSQLValueString(Context ctx, String sql, DB conn, String... params) {
		String retValue = null;
		try{
			retValue = getSQLValueStringEx(ctx, sql, conn, params);
		} catch(Exception e) {
			LogM.log(ctx, "DB", Level.SEVERE, "SQLError", e);
		}
		return retValue;
	}
}
