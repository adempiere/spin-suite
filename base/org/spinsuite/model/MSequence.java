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
package org.spinsuite.model;

import java.text.DecimalFormat;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

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
		
		boolean handleConnection = false;
		int retValue = -1;

		String selectSQL = "SELECT CurrentNext, AD_Sequence_ID "
				+ "FROM AD_Sequence "
				+ "WHERE Name=?"
				+ " AND IsActive='Y' AND IsTableID='Y' AND IsAutoSequence='Y'";
		
		LogM.log(ctx, "MSequence", Level.FINE, "Msequence.getNextID >> selectSQL:" + selectSQL);
		
		//	Connection
		if(conn == null){
			conn = new DB(ctx);
			handleConnection = true;
		} else if(!conn.isOpen()){
			handleConnection = true;
		}
		//	
		DB.loadConnection(conn, DB.READ_WRITE);
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
		//	Close Connection
		if(handleConnection)
			DB.closeConnection(conn);
		//	
		return retValue;
	}	//	getNextID
	
	
	/**
	 * Get Document No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/05/2014, 22:03:16
	 * @param ctx
	 * @param C_DocType_ID
	 * @param TableName
	 * @param definite
	 * @param conn
	 * @return
	 * @return String
	 */
	public static String getDocumentNo (Context ctx, int C_DocType_ID, String TableName, boolean definite, DB conn){
		//	
		boolean handleConnection = false;
		int m_AD_Sequence_ID = 0;
		int incrementNo = 0;
		int next = -1;
		String prefix = "";
		String suffix = "";
		String decimalPattern = "";
		//String calendarYear = "";
		
		String selectSQL = null;
		//	Connection
		if(conn == null){
			conn = new DB(ctx);
			handleConnection = true;
		} else if(!conn.isOpen()){
			handleConnection = true;
		}
		//	Load Connection
		DB.loadConnection(conn, DB.READ_WRITE);
		//	
		try {
			//	Result Set
			Cursor rs = null;
			//	By Table
			if (C_DocType_ID == 0){
				//	Sequence by User
				selectSQL = "SELECT s.CurrentNext, s.AD_Sequence_ID, s.IncrementNo, s.Prefix, s.Suffix, s.DecimalPattern " +
						"FROM AD_Sequence s " +
						"WHERE s.Name = ? " +
						"AND s.IsActive = ? " +
						"AND s.IsTableID = ? ";
				
				LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> selectSQL:" + selectSQL);
				//	
				rs = conn.querySQL(selectSQL, new String[]{TableName, "Y", "N"});

				//	Get Values
				if(rs.moveToFirst()){
					next = rs.getInt(0);
					m_AD_Sequence_ID = rs.getInt(1);
					incrementNo = rs.getInt(2);
					prefix = rs.getString(3);
					suffix = rs.getString(4);
					decimalPattern = rs.getString(5);
				}
			}
			//	Sequence by User
			if(next < 0){
				selectSQL = "SELECT s.CurrentNext, s.AD_Sequence_ID, s.IncrementNo, s.Prefix, s.Suffix, s.DecimalPattern " +
						"FROM SPS_UserDocSequence uds " +
						"INNER JOIN AD_Sequence s ON(s.AD_Sequence_ID = uds.AD_Sequence_ID) " +
						"WHERE uds.C_DocType_ID = ? " +
						"AND s.IsActive = ? " +
						"AND s.IsTableID = ? ";
				
				LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> selectSQL:" + selectSQL);
				LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> C_DocType_ID:" + C_DocType_ID);
				//	
				rs = conn.querySQL(selectSQL, new String[]{String.valueOf(C_DocType_ID), "Y", "N"});

				//	Get Values
				if(rs.moveToFirst()){
					next = rs.getInt(0);
					m_AD_Sequence_ID = rs.getInt(1);
					incrementNo = rs.getInt(2);
					prefix = rs.getString(3);
					suffix = rs.getString(4);
					decimalPattern = rs.getString(5);
				}
			}

			//	Sequence By Document
			if(next < 0){
				selectSQL = "SELECT s.CurrentNext, s.AD_Sequence_ID, s.IncrementNo, s.Prefix, s.Suffix, s.DecimalPattern " +
						"FROM C_DocType dt " +
						"INNER JOIN AD_Sequence s ON(s.AD_Sequence_ID = dt.DocNoSequence_ID) " +
						"WHERE dt.C_DocType_ID = ? " +
						"AND s.IsActive = ? " +
						"AND s.IsTableID = ? ";
				
				LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> selectSQL:" + selectSQL);
				LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> C_DocType_ID:" + C_DocType_ID);
				
				//	Result Set
				rs = null;
				rs = conn.querySQL(selectSQL, new String[]{String.valueOf(C_DocType_ID), "Y", "N"});
				//	Get Values
				
				if(rs.moveToFirst()){
					next = rs.getInt(0);
					m_AD_Sequence_ID = rs.getInt(1);
					incrementNo = rs.getInt(2);
					prefix = rs.getString(3);
					suffix = rs.getString(4);
					decimalPattern = rs.getString(5);
				}
			}
		} catch (Exception e) {
			LogM.log(ctx, MSequence.class, Level.SEVERE, "Msequence.getDocumentNo >> Error:" + e.getMessage(), e);
		}
		//	No Sequence
		if(next < 0) {
			//	Close Connection
			if(handleConnection)
				DB.closeConnection(conn);
			//	Return
			return null;
		}
		
		//	Log
		LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> next=" +  next);
		LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> m_AD_Sequence_ID" + m_AD_Sequence_ID);
		LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> incrementNo" + incrementNo);
		LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> prefix" + prefix);
		LogM.log(ctx, MSequence.class, Level.FINE, "Msequence.getDocumentNo >> suffix" + suffix);
		
		//	create DocumentNo
		StringBuffer doc = new StringBuffer();
		if (prefix != null && prefix.length() > 0)
			doc.append(Env.parseContext(ctx, prefix, true));
		if (decimalPattern != null && decimalPattern.length() > 0)
			doc.append(new DecimalFormat(decimalPattern).format(next));
		else
			doc.append(next);
		if (suffix != null && suffix.length() > 0)
			doc.append(Env.parseContext(ctx, suffix, true));
		//	Set Document No
		String documentNo = doc.toString();
		//	Log
		LogM.log(ctx, MSequence.class, Level.FINE, documentNo + " (" + incrementNo + ")"
				+ " - C_DocType_ID=" + C_DocType_ID);
		
		//	Update Sequence
		if(definite){
			//	Update Sequence
			String updateSQL = "UPDATE AD_Sequence SET CurrentNext = CurrentNext + IncrementNo " +
					"WHERE AD_Sequence_ID = ?";
			//	Log
			LogM.log(ctx, "MSequence", Level.FINE, "Msequence.getNextID >> updateSQL:" + updateSQL);
			
			conn.executeSQL(updateSQL, new Object[]{m_AD_Sequence_ID});
			LogM.log(ctx, "MSequence", Level.FINE, "m_AD_Sequence_ID=" + m_AD_Sequence_ID);
		}
		//	Close Connection
		if(handleConnection)
			DB.closeConnection(conn);
		//	
		return documentNo;
	}	//	getDocumentNo

}
