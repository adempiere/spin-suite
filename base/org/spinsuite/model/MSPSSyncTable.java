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
	
	public static MSPSSyncTable getSyncTable(Context ctx,DB p_conn,String whereClause) throws Exception{
		return new Query(ctx, MSPSSyncTable.Table_Name, whereClause, p_conn)
		.first();
	}

}
