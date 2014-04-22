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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
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
	
	private Context ctx = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.t_role, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	ctx = this.getActivity();
    	
    	sp_Role =		(Spinner) getActivity().findViewById(R.id.sp_Role);
    	sp_Client = 	(Spinner) getActivity().findViewById(R.id.sp_Client);
    	sp_Org = 		(Spinner) getActivity().findViewById(R.id.sp_Org);
    	sp_Warehouse = 	(Spinner) getActivity().findViewById(R.id.sp_Warehouse);
    	dp_Date = 		(DatePicker) getActivity().findViewById(R.id.dp_Date);
    	
    	
    	sp_Role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				role_ID = ((DisplaySpinner) sp_Role.getItemAtPosition(position)).getID();
				//if(role_ID != 0){
					client_ID = loadClient(role_ID);
					Env.setAD_Role_ID(ctx, role_ID);
				//}
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
				//if(client_ID != 0){
					org_ID = loadOrg(client_ID);
					Env.setAD_Client_ID(ctx, client_ID);
				//}
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
				//if(org_ID != 0){
					warehouse_ID = loadWarehouse(org_ID);
					Env.setAD_Org_ID(ctx, org_ID);
				//}
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
				Env.setM_Warehouse_ID(ctx, warehouse_ID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    	

    	//loadData();
    }
    
    @Override
	public boolean aceptAction() {
    	DisplaySpinner ds_Role = (DisplaySpinner) sp_Role.getSelectedItem();
    	DisplaySpinner ds_Client = (DisplaySpinner) sp_Client.getSelectedItem();
    	DisplaySpinner ds_Org = (DisplaySpinner) sp_Org.getSelectedItem();
    	DisplaySpinner ds_Warehouse = (DisplaySpinner) sp_Warehouse.getSelectedItem();
		if(ds_Role != null
				&& ds_Role.getID() > 0){
			if(ds_Client != null
					&& ds_Client.getID() > 0){
				if(ds_Org != null
						&& ds_Org.getID() > 0){
					if(ds_Warehouse != null
							&& ds_Warehouse.getID() > 0){
						
						Env.setAD_Role_ID(this.getActivity(), role_ID);
						Env.setContext(this.getActivity(), "#AD_Role_Name", ds_Role.getValue());
						Env.setAD_Client_ID(this.getActivity(), client_ID);
						Env.setContext(this.getActivity(), "#AD_Client_Name", ds_Client.getValue());
						Env.setAD_Org_ID(this.getActivity(), org_ID);
						Env.setContext(this.getActivity(), "#AD_Org_Name", ds_Org.getValue());
						Env.setM_Warehouse_ID(this.getActivity(), warehouse_ID);
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
						
						Env.setContext(this.getActivity(), "#Date", sdf.format(date.getTime()));
						
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						String curDate = sdf.format(currentDate.getTime());
						String ctxDate = sdf.format(date.getTime());
						
						//	Format Date yyyy-MM-dd
						
						Env.setContext(this.getActivity(), "#DateP", ctxDate);
						
						//Msg.toastMsg(this, " -- " + (currentDate.equals(date)));
						//	Set Context Is Current Date
						
						//Msg.toastMsg(this, " - - - " + curDate + " **** " + ctxDate);
						//currentDate.
						
						if(!(curDate.equals(ctxDate))){
							Env.setContext(this.getActivity(), "#IsCurrentDate", "N");
							Msg.toastMsg(ctx, getResources().getString(R.string.msg_LoginOffDate) + 
									"\n" + getResources().getString(R.string.msg_WritePermissionsBlocked));
							//	
							Msg.toastMsg(ctx, getResources().getString(R.string.msg_LoginOffDate) + 
									"\n" + getResources().getString(R.string.msg_WritePermissionsBlocked));
						} else {
							Env.setContext(this.getActivity(), "#IsCurrentDate", "Y");
						}
						
						return true;
					} //else
						//Msg.alertMustFillField(this.getActivity(), R.string.M_Warehouse_ID, sp_Warehouse);
				} else
					Msg.alertMustFillField(this.getActivity(), R.string.AD_Org_ID, sp_Org);
			} else
				Msg.alertMustFillField(this.getActivity(), R.string.AD_Client_ID, sp_Client);
		} else
			Msg.alertMustFillField(this.getActivity(), R.string.AD_Role_ID, sp_Role);
		return false;
	}

    /**
     * Load Role
     * @author Yamel Senih 04/02/2013, 19:47:38
     * @return
     * @return int
     */
    private int loadRole(){
		int role_ID = LoadDataSpinner.load(this.getActivity(), sp_Role, "SELECT r.AD_Role_ID, r.Name, r.IsUseUserOrgAccess " +
    			"FROM AD_Role r " +
    			"INNER JOIN AD_User_Roles ur ON(ur.AD_Role_ID = r.AD_Role_ID) " +
    			"WHERE ur.AD_User_ID = " + Env.getAD_User_ID(this.getActivity()), true, false);
		
		int id_ctx = Env.getAD_Role_ID(ctx);
		if(id_ctx != 0){
			role_ID = setIDSpinner(sp_Role, id_ctx);
		}

		m_IsUseUserOrgAccess = (String) ((DisplaySpinner) sp_Role.getSelectedItem()).getHiddenValue();
		
		return role_ID;
	}
    
    /**
     * Load Client (Tenant)
     * @author Yamel Senih 04/02/2013, 19:48:05
     * @param role_ID
     * @return
     * @return int
     */
    private int loadClient(int role_ID){
		int client_ID = LoadDataSpinner.load(this.getActivity(), sp_Client, "SELECT c.AD_Client_ID, c.Name " +
				"FROM AD_Role r " +
				"INNER JOIN AD_Client c ON(c.AD_Client_ID = r.AD_Client_ID) " +
				"WHERE r.AD_Role_ID = " + role_ID, false, false);
		
		int id_ctx = Env.getAD_Client_ID(ctx);
		if(id_ctx != 0){
			role_ID = setIDSpinner(sp_Client, id_ctx);
		}
		
		return client_ID;
	}
    
    /**
     * Load Organizarion
     * @author Yamel Senih 04/02/2013, 19:50:28
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
			sql.append("WHERE uo.AD_User_ID = " + Env.getAD_User_ID(ctx) + " ");
		} else {
			sql.append("INNER JOIN AD_Role_OrgAccess orga ON(orga.AD_Org_ID = o.AD_Org_ID) " +
					"INNER JOIN AD_User_Roles ur ON(ur.AD_Role_ID = orga.AD_Role_ID) ");
			sql.append("WHERE ur.AD_Role_ID = " + role_ID + " ");
		}
		
		sql.append("AND o.AD_Client_ID = " + client_ID);
		
		/*
		"SELECT o.AD_Org_ID, o.Name " +
		"FROM AD_Org o " +
		
		"INNER JOIN AD_User_OrgAccess uo ON(uo.AD_Org_ID = o.AD_Org_ID) " + 
		
		"INNER JOIN AD_Role_OrgAccess orga ON(orga.AD_Org_ID = o.AD_Org_ID) INNER JOIN AD_User_Roles ur ON(ur.AD_Role_ID = orga.AD_Role_ID) " +
		
		"WHERE ur.AD_Role_ID = " + role_ID + " " + 
		"AND o.AD_Client_ID = " + client_ID
		*/
		int org_ID = LoadDataSpinner.load(this.getActivity(), sp_Org, sql.toString(), false, false);
		
		int id_ctx = Env.getAD_Org_ID(ctx);
		if(id_ctx != 0){
			org_ID = setIDSpinner(sp_Org, id_ctx);
		}
		
    	return org_ID;
	}
    
    /**
     * Load Warehouse
     * @author Yamel Senih 04/02/2013, 19:51:10
     * @param org_ID
     * @return
     * @return int
     */
    private int loadWarehouse(int org_ID){
		int warehouse_ID = LoadDataSpinner.load(this.getActivity(), sp_Warehouse, "SELECT w.M_Warehouse_ID, w.Name " +
				"FROM M_Warehouse w " + 
				"WHERE w.AD_Org_ID = " + org_ID, false, false);
		
		int id_ctx = Env.getM_Warehouse_ID(ctx);
		if(id_ctx != 0){
			warehouse_ID = setIDSpinner(sp_Warehouse, id_ctx);
		}
		
		return warehouse_ID;
	}
    
    /**
     * Load Spinner
     * @author Yamel Senih 04/02/2013, 19:51:40
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadData() {
		if(Env.isEnvLoad(getActivity())){
			role_ID = loadRole();
		}
		return false;
	}
    
}
