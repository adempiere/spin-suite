package org.spinsuite.model;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

public class MWSWebServiceFieldOutput extends X_WS_WebServiceFieldOutput {

	public MWSWebServiceFieldOutput(Context ctx,
			int WS_WebServiceFieldOutput_ID, DB conn) {
		super(ctx, WS_WebServiceFieldOutput_ID, conn);
		// TODO Auto-generated constructor stub
	}

	public MWSWebServiceFieldOutput(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		// TODO Auto-generated constructor stub
	}

}
