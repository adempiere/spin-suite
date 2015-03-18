package org.spinsuite.model;

import java.util.List;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

public class MWSWebServiceType extends X_WS_WebServiceType{
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 4/3/2015, 15:22:22
	 * @param ctx
	 * @param WS_WebServiceType_ID
	 * @param conn
	 */
	public MWSWebServiceType(Context ctx, int WS_WebServiceType_ID, DB conn) {
		super(ctx, WS_WebServiceType_ID, conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 4/3/2015, 15:22:43
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MWSWebServiceType(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get Input for Web Service Type
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 4/3/2015, 15:23:05
	 * @return
	 * @return List<MWSWebServiceFieldInput>
	 */
	public List<MWSWebServiceFieldInput> getInput(){
		return new Query(getCtx(), MWSWebServiceFieldInput.Table_Name, "WS_WebServiceType_ID=?", conn).setParameters(getWS_WebServiceType_ID()).list();		
	}

	/**
	 * Get Output for Web Service Type
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 4/3/2015, 15:23:05
	 * @return
	 * @return List<MWSWebServiceFieldOutput>
	 */
	public List<MWSWebServiceFieldOutput> getOutput(){
		return new Query(getCtx(), MWSWebServiceFieldOutput.Table_Name, "WS_WebServiceType_ID=?", conn).setParameters(getWS_WebServiceType_ID()).list();		
	}

}
