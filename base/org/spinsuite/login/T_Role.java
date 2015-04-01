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
package org.spinsuite.login;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.DisplaySpinner;
import org.spinsuite.util.Env;
import org.spinsuite.util.LoadDataSpinner;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * 	<li> Login Correct
 * 	@see https://adempiere.atlassian.net/browse/SPIN-2
 *
 */
public class T_Role extends Fragment implements I_Login {
	/**	Spinners			*/
	private Spinner sp_Role;
	private Spinner sp_Client;
	private Spinner sp_Org;
	private Spinner sp_Warehouse;
	private DatePicker dp_Date;
	
	/**	IDs					*/
	private int role_ID = 0;
	private int client_ID = 0;
	private int org_ID = 0;
	private int warehouse_ID = 0;
	private String m_IsUseUserOrgAccess = "N";
	private View m_View = null;
	private boolean	m_IsLoadOk = false;
	private Context ctx = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(m_View != null)
        	return m_View;
        //	Re-Load
        m_View = inflater.inflate(R.layout.t_role, container, false);
        //	
        return m_View;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	if(m_IsLoadOk)
    		return;
    	//	
    	ctx = getActivity();
    	
    	sp_Role 		= (Spinner) 	m_View.findViewById(R.id.sp_Role);
    	sp_Client 		= (Spinner) 	m_View.findViewById(R.id.sp_Client);
    	sp_Org 			= (Spinner) 	m_View.findViewById(R.id.sp_Org);
    	sp_Warehouse 	= (Spinner) 	m_View.findViewById(R.id.sp_Warehouse);
    	dp_Date 		= (DatePicker) 	m_View.findViewById(R.id.dp_Date);
    	
    	
    	sp_Role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				role_ID = ((DisplaySpinner) sp_Role.getItemAtPosition(position)).getID();
				client_ID = loadClient(role_ID);
				Env.setAD_Role_ID(role_ID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    	
    	sp_Client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				client_ID = ((DisplaySpinner) sp_Client.getItemAtPosition(position)).getID();
				org_ID = loadOrg(client_ID);
				Env.setAD_Client_ID(client_ID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    	
    	sp_Org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				org_ID = ((DisplaySpinner) sp_Org.getItemAtPosition(position)).getID();
				warehouse_ID = loadWarehouse(org_ID);
				Env.setAD_Org_ID(org_ID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    	
    	sp_Warehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				warehouse_ID = ((DisplaySpinner) sp_Warehouse.getItemAtPosition(position)).getID();
				Env.setM_Warehouse_ID(warehouse_ID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    }
    
    @Override
	public boolean aceptAction() {
    	DisplaySpinner ds_Role = (DisplaySpinner) sp_Role.getSelectedItem();
    	DisplaySpinner ds_Client = (DisplaySpinner) sp_Client.getSelectedItem();
    	DisplaySpinner ds_Org = (DisplaySpinner) sp_Org.getSelectedItem();
    	if(ds_Role != null
    			&& ds_Role.getValue() != null) {
			if(ds_Client != null
					&& ds_Client.getValue() != null) {
				if(ds_Org != null
						&& ds_Org.getValue() != null) {
						
					Env.setAD_Role_ID(role_ID);
					Env.setContext("#AD_Role_Name", ds_Role.getValue());
					Env.setAD_Client_ID(client_ID);
					Env.setContext("#AD_Client_Name", ds_Client.getValue());
					Env.setAD_Org_ID(org_ID);
					Env.setContext("#AD_Org_Name", ds_Org.getValue());
					Env.setM_Warehouse_ID(warehouse_ID);
					//	Date
					Calendar currentDate = Calendar.getInstance();
					Calendar date = Calendar.getInstance();
					date.set(Calendar.YEAR, dp_Date.getYear());
					date.set(Calendar.MONTH, dp_Date.getMonth());
					date.set(Calendar.DAY_OF_MONTH, dp_Date.getDayOfMonth());
					//Locale loc = Locale.getDefault();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					//Msg.toastMsg(this, " -- " + date.getTime());

					//	Format Date yyyy-MM-dd hh:mm:ss
					
					Env.setContext("#Date", sdf.format(date.getTime()));
					
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					String curDate = sdf.format(currentDate.getTime());
					String ctxDate = sdf.format(date.getTime());
					
					//	Format Date yyyy-MM-dd
					
					Env.setContext("#DateP", ctxDate);
					
					if(!(curDate.equals(ctxDate))){
						Env.setContext("#IsCurrentDate", "N");
						Msg.toastMsg(ctx, getResources().getString(R.string.msg_LoginOffDate) + 
								"\n" + getResources().getString(R.string.msg_WritePermissionsBlocked));
						//	
						Msg.toastMsg(ctx, getResources().getString(R.string.msg_LoginOffDate) + 
								"\n" + getResources().getString(R.string.msg_WritePermissionsBlocked));
					} else {
						Env.setContext("#IsCurrentDate", "Y");
					}
					return true;
				} else
					Msg.alertMustFillField(getActivity(), R.string.AD_Org_ID, sp_Org);
			} else
				Msg.alertMustFillField(getActivity(), R.string.AD_Client_ID, sp_Client);
		} else
			Msg.alertMustFillField(getActivity(), R.string.AD_Role_ID, sp_Role);
		return false;
	}

    /**
     * Load Role
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return int
     */
    private int loadRole(){
		int role_ID = LoadDataSpinner.load(getActivity(), sp_Role, "SELECT r.AD_Role_ID, r.Name, r.IsUseUserOrgAccess " +
    			"FROM AD_Role r " +
    			"INNER JOIN AD_User_Roles ur ON(ur.AD_Role_ID = r.AD_Role_ID) " +
    			"WHERE ur.AD_User_ID = " + Env.getAD_User_ID(), true, false);
		
		int id_ctx = Env.getAD_Role_ID();
		if(id_ctx != 0){
			role_ID = setIDSpinner(sp_Role, id_ctx);
		}

		m_IsUseUserOrgAccess = (String) ((DisplaySpinner) sp_Role.getSelectedItem()).getHiddenValue();
		
		return role_ID;
	}
    
    /**
     * Load Client
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param role_ID
     * @return
     * @return int
     */
    private int loadClient(int role_ID){
		int client_ID = LoadDataSpinner.load(getActivity(), sp_Client, "SELECT c.AD_Client_ID, c.Name " +
				"FROM AD_Role r " +
				"INNER JOIN AD_Client c ON(c.AD_Client_ID = r.AD_Client_ID) " +
				"WHERE r.AD_Role_ID = " + role_ID, false, false);
		
		int id_ctx = Env.getAD_Client_ID();
		if(id_ctx != 0){
			role_ID = setIDSpinner(sp_Client, id_ctx);
		}
		
		return client_ID;
	}
    
    /**
     * Load Org
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param client_ID
     * @return
     * @return int
     */
    private int loadOrg(int client_ID){
		StringBuffer sql = new StringBuffer("SELECT o.AD_Org_ID, o.Name ");
		sql.append("FROM AD_Org o ");
		//Msg.toastMsg(ctx, "-- " + m_IsUseUserOrgAccess);
		if(m_IsUseUserOrgAccess != null 
				&& m_IsUseUserOrgAccess.equals("Y")){
			sql.append("INNER JOIN AD_User_OrgAccess uo ON(uo.AD_Org_ID = o.AD_Org_ID) ");
			sql.append("WHERE uo.AD_User_ID = " + Env.getAD_User_ID() + " ");
		} else {
			sql.append("INNER JOIN AD_Role_OrgAccess orga ON(orga.AD_Org_ID = o.AD_Org_ID) " +
					"INNER JOIN AD_User_Roles ur ON(ur.AD_Role_ID = orga.AD_Role_ID) ");
			sql.append("WHERE ur.AD_Role_ID = " + role_ID + " ");
		}
		
		sql.append("AND o.AD_Client_ID = " + client_ID);
		
		int org_ID = LoadDataSpinner.load(getActivity(), sp_Org, sql.toString(), false, false);
		
		int id_ctx = Env.getAD_Org_ID();
		if(id_ctx != 0){
			org_ID = setIDSpinner(sp_Org, id_ctx);
		}
		
    	return org_ID;
	}
    
    /**
     * Load Warehouse
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param org_ID
     * @return
     * @return int
     */
    private int loadWarehouse(int org_ID){
		int warehouse_ID = LoadDataSpinner.load(getActivity(), sp_Warehouse, "SELECT w.M_Warehouse_ID, w.Name " +
				"FROM M_Warehouse w " + 
				"WHERE w.AD_Org_ID = " + org_ID, false, false);
		
		int id_ctx = Env.getM_Warehouse_ID();
		if(id_ctx != 0){
			warehouse_ID = setIDSpinner(sp_Warehouse, id_ctx);
		}
		
		return warehouse_ID;
	}
    
    /**
     * Load Spinner
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param sp
     * @param id_ctx
     * @return
     * @return int
     */
    private int setIDSpinner(Spinner sp, int id_ctx){
		for(int i = 0; i < sp.getCount(); i++){
			DisplaySpinner ds = (DisplaySpinner)sp.getItemAtPosition(i);
			if(ds.getID() == id_ctx){
				sp.setSelection(i);
				return id_ctx;
			}
		}
		return 0;
	}
    
    @Override
	public void onResume() {
        super.onResume();
        loadData();
    }

	@Override
	public boolean cancelAction() {
		return false;
	}

	@Override
	public boolean loadData() {
		if(Env.isEnvLoad()
				&& !m_IsLoadOk) {
			role_ID = loadRole();
	    	//	Set Load
	    	m_IsLoadOk = true;
		}
		return false;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		if(!m_IsLoadOk)
			return;
		sp_Role.setEnabled(enabled);
    	sp_Client.setEnabled(enabled);
    	sp_Org.setEnabled(enabled);
    	sp_Warehouse.setEnabled(enabled);
    	dp_Date.setEnabled(enabled);
	}    
}
