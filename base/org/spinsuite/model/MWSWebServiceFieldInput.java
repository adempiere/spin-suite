package org.spinsuite.model;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

public class MWSWebServiceFieldInput extends X_WS_WebServiceFieldInput {

	public MWSWebServiceFieldInput(Context ctx, int WS_WebServiceFieldInput_ID,
			DB conn) {
		super(ctx, WS_WebServiceFieldInput_ID, conn);
		// TODO Auto-generated constructor stub
	}

	public MWSWebServiceFieldInput(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		// TODO Auto-generated constructor stub
	}

}
