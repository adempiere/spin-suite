package org.spinsuite.model;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

public class MSPSSyncTable extends X_SPS_SyncTable {

	public MSPSSyncTable(Context ctx, int SPS_SyncTable_ID, DB conn) {
		super(ctx, SPS_SyncTable_ID, conn);
		// TODO Auto-generated constructor stub
	}

	public MSPSSyncTable(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		// TODO Auto-generated constructor stub
	}
	
	public static MSPSSyncTable getSyncTable(Context ctx,DB p_conn,int p_SPS_Table_ID,int p_Record_ID) throws Exception{
		String whereClause = "SPS_Table_ID = ? AND Record_ID = ? AND EventChangeLog IN (?,?) AND IsSynchronized='N'";
		return new Query(ctx, MSPSSyncTable.Table_Name, whereClause, p_conn)
		.setParameters(new Object[]{p_SPS_Table_ID,p_Record_ID,X_SPS_SyncTable.EVENTCHANGELOG_Insert,X_SPS_SyncTable.EVENTCHANGELOG_Update})
		.first();
	}

}
