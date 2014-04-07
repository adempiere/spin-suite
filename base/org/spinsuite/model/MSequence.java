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
package org.spinsuite.model;

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MSequence extends X_AD_Sequence {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/02/2014, 08:28:08
	 * @param ctx
	 * @param AD_Sequence_ID
	 * @param conn
	 */
	public MSequence(Context ctx, int AD_Sequence_ID, DB conn) {
		super(ctx, AD_Sequence_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/02/2014, 08:28:08
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MSequence(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 *
	 *	Get next number for Key column = 0 is Error.
	 *  @param ctx
	 *  @param AD_Client_ID client
	 *  @param TableName table name
	 * 	@param conn Connection
	 *  @return next no or (-1=not found, -2=error)
	 */
	public static int getNextID (Context ctx, int AD_Client_ID, String TableName, DB conn)
	{
		if (TableName == null || TableName.length() == 0)
			throw new IllegalArgumentException("TableName missing");

		int retValue = -1;

		String selectSQL = "SELECT CurrentNext, AD_Sequence_ID "
				+ "FROM AD_Sequence "
				+ "WHERE Name=?"
				+ " AND IsActive='Y' AND IsTableID='Y' AND IsAutoSequence='Y'";
		
		LogM.log(ctx, "MSequence", Level.FINE, "Msequence.getNextID >> selectSQL:" + selectSQL);
		
		if(conn == null){
			conn = new DB(ctx);
			conn.openDB(DB.READ_WRITE);
		}
		//	Result Set
		Cursor rs = null;
		rs = conn.querySQL(selectSQL, new String[]{TableName});
		//	Get Values
		int m_AD_Sequence_ID = 0;
		
		if(rs.moveToFirst()){
			retValue = rs.getInt(0);
			m_AD_Sequence_ID = rs.getInt(1);
			
			LogM.log(ctx, "MSequence", Level.FINE, "Msequence.getNextID >> retValue=" +  retValue);
			LogM.log(ctx, "MSequence", Level.FINE, "Msequence.getNextID >> m_AD_Sequence_ID" + m_AD_Sequence_ID);
			//	Update Sequence
			String updateSQL = "UPDATE AD_Sequence SET CurrentNext = CurrentNext + IncrementNo " +
					"WHERE AD_Sequence_ID = ?";
			//	Log
			LogM.log(ctx, "MSequence", Level.FINE, "Msequence.getNextID >> updateSQL:" + updateSQL);
			
			conn.executeSQL(updateSQL, new Object[]{m_AD_Sequence_ID});
			LogM.log(ctx, "MSequence", Level.FINE, "m_AD_Sequence_ID=" + m_AD_Sequence_ID);
		}
		//	
		return retValue;
	}	//	getNextID

}
