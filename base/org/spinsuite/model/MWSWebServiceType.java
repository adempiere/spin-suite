package org.spinsuite.model;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

public class MWSWebServiceType extends X_WS_WebServiceType{

	public MWSWebServiceType(Context ctx, int WS_WebServiceType_ID, DB conn) {
		super(ctx, WS_WebServiceType_ID, conn);
		// TODO Auto-generated constructor stub
	}

	public MWSWebServiceType(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		// TODO Auto-generated constructor stub
	}
	

}
