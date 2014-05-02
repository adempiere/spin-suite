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
package org.spinsuite.view.lookup;

import java.util.ArrayList;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.process.DocAction;
import org.spinsuite.util.ActionItemList;
import org.spinsuite.util.contribution.QuickAction;
import org.spinsuite.util.contribution.QuickAction.OnActionItemClickListener;
import org.spinsuite.util.contribution.QuickAction.OnDismissListener;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupButtonDocAction extends VLookupButton implements OnActionItemClickListener, OnDismissListener{

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 */
	public VLookupButtonDocAction(Activity activity) {
		super(activity);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 * @param attrs
	 */
	public VLookupButtonDocAction(Activity activity, AttributeSet attrs) {
		super(activity, attrs);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupButtonDocAction(Activity activity, AttributeSet attrs,
			int defStyle) {
		super(activity, attrs, defStyle);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 * @param m_field
	 */
	public VLookupButtonDocAction(Activity activity, InfoField m_field) {
		super(activity, m_field);
	}
	
	private QuickAction 		mQAct;
	private String 				documentStatus = "DR";
	private String [] 			actions = null;
	private String [] 			actionRole = null;
	private boolean 			loaded = false;
	
	@Override	
	protected void init() {
		super.init();
		v_Button.setCompoundDrawablesWithIntrinsicBounds(
				getResources().getDrawable(R.drawable.edit_m), null, null, null);
		setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//	DocAction
				loadActions(v);
			}
		});
		
		mQAct = new QuickAction(getContext());
		
		mQAct.setOnActionItemClickListener(this);
				
		mQAct.setOnDismissListener(this);
	}
	
	/**
	 * Set show View Actions
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/05/2012, 16:57:11
	 * @param actions
	 * @return void
	 */
	private void setActionOption(String [] actions){
		this.actions = actions;
	}

	/**
	 * Set Status
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/06/2012, 23:54:54
	 * @param quickAction
	 * @param pos
	 * @return void
	 */
	public void setStatus(QuickAction quickAction, int pos){
		ActionItemList actionItem = (ActionItemList) quickAction.getActionItem(pos);
		if(actionItem.getValue().equals(DocAction.STATUS_Drafted)){
			setDocAction(DocAction.STATUS_Completed);
		} else if(actionItem.getValue().equals(DocAction.ACTION_Complete)){
			setDocAction(DocAction.STATUS_Completed);
		} else if(actionItem.getValue().equals(DocAction.ACTION_ReActivate)){
			setDocAction(DocAction.STATUS_InProgress);
		} else if(actionItem.getValue().equals(DocAction.ACTION_Void)){
			setDocAction(DocAction.STATUS_Voided);
		} else if(actionItem.getValue().equals(DocAction.ACTION_Prepare)){
			setDocAction(DocAction.STATUS_InProgress);
		}
	}
	
	/**
	 * Load available view
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/05/2012, 17:19:48
	 * @param v
	 * @return void
	 */
	private void loadActions(View v){
		mQAct.clear();
		
		//	Action Complete
		if(validAction(DocAction.ACTION_Complete)
				&& validActionRole(DocAction.ACTION_Complete)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Complete, 
					getResources().getString(R.string.ACTION_Complete), 
					getResources().getDrawable(R.drawable.doc_completed_m)));
		}
		//	Action Prepare
		if(validAction(DocAction.ACTION_Prepare)
				&& validActionRole(DocAction.ACTION_Prepare)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Prepare, 
					getResources().getString(R.string.ACTION_Prepare), 
					getResources().getDrawable(R.drawable.doc_progress_m)));	
		}
		//	Action Void
		if(validAction(DocAction.ACTION_Void)
				&& validActionRole(DocAction.ACTION_Void)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Void, 
					getResources().getString(R.string.ACTION_Void), 
					getResources().getDrawable(R.drawable.remove_m)));	
		}
		//	Action Void
		if(validAction(DocAction.ACTION_ReActivate) 
				&& !documentStatus.equals(DocAction.STATUS_Drafted)
				&& validActionRole(DocAction.ACTION_ReActivate)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_ReActivate, 
					getResources().getString(R.string.ACTION_ReActivate), 
					getResources().getDrawable(R.drawable.doc_progress_m)));	
		}
		
		mQAct.show(v);
	}
	
	/**
	 * Set Document Action
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 00:35:12
	 * @param docStatus
	 * @return void
	 */
	public void setDocAction(String docStatus){
		if(docStatus != null){
			if(docStatus.equals(DocAction.STATUS_Drafted)){
				v_Button.setText(getResources().getString(R.string.STATUS_Drafted));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.edit_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(new String[]{
						DocAction.ACTION_Complete, 
						DocAction.ACTION_Prepare, 
						DocAction.ACTION_Void});
			} else if(docStatus.equals(DocAction.STATUS_Completed)){
				v_Button.setText(getResources().getString(R.string.STATUS_Completed));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.doc_completed_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(new String[]{
						DocAction.ACTION_ReActivate, 
						DocAction.ACTION_Void});
			} else if(docStatus.equals(DocAction.STATUS_Voided)){
				v_Button.setText(getResources().getString(R.string.STATUS_Voided));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.remove_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(null);
			} else if(docStatus.equals(DocAction.STATUS_InProgress)){
				v_Button.setText(getResources().getString(R.string.STATUS_InProgress));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.doc_progress_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(new String[]{
						DocAction.ACTION_Complete,
						DocAction.ACTION_Void});
			}
		} else {
			v_Button.setText(getResources().getString(R.string.STATUS_Drafted));
			v_Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.edit_m), null, null, null);
			this.documentStatus = docStatus;
			setActionOption(new String[]{
					DocAction.ACTION_Complete, 
					DocAction.ACTION_Prepare, 
					DocAction.ACTION_Void});
		}
	}
	
	/**
	 * Get Document Status
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 00:43:16
	 * @return
	 * @return String
	 */
	public String getDocStatus(){
		//Msg.toastMsg(getContext(), documentStatus);
		return documentStatus;
	}
	
	/**
	 * Verify valid Action to Document wit role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/08/2012, 03:14:24
	 * @param conn
	 * @param ctx
	 * @param m_C_DocType_ID
	 * @param reloaded
	 * @return void
	 */
	public void setValRuleAction(DB con_tx, Context ctx, int m_C_DocType_ID, boolean reloaded){
		if(!loaded || reloaded) {
			boolean handConnection = false;
			DB con = null;
			if(con_tx == null){
				con = new DB(ctx);
				con.openDB(DB.READ_ONLY);
				handConnection = true;
			} else 
				con = con_tx;
			String sql = new String("SELECT DocAction " +
					"FROM AD_Document_Action_Access " +
					"WHERE C_DocType_ID = " + m_C_DocType_ID);
			//	Cursor
			Cursor rs = con.querySQL(sql, null);
	    	
			if(rs.moveToFirst()){
				ArrayList<String> docActions = new ArrayList<String>();
				do{
					docActions.add(rs.getString(0));
				} while(rs.moveToNext());
				
				if(docActions.size() != 0){
					actionRole = new String[docActions.size()];
					docActions.toArray(actionRole);
				}
	    	}
			//	Close DB
			if(handConnection)
				con.closeDB(rs);	
		}
	}
	
	/**
	 * Valid Action
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/05/2012, 17:00:21
	 * @param action
	 * @return
	 * @return boolean
	 */
	private boolean validAction(String action){
		if(actions != null){
			for (int i = 0; i < actions.length; i++) {
				//Msg.toastMsg(getContext(), action + " " + actions[i].equals(action));
				if(actions[i].equals(action))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Verify Valid Action with role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/08/2012, 03:11:32
	 * @param action
	 * @return
	 * @return boolean
	 */
	private boolean validActionRole(String action){
		if(actionRole != null){
			for (int i = 0; i < actionRole.length; i++) {
				//Msg.toastMsg(getContext(), action + " " + actions[i].equals(action));
				if(actionRole[i].equals(action))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Get el QuickAction
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/06/2012, 00:05:24
	 * @return
	 * @return QuickAction
	 */
	public QuickAction getQuickAction(){
		return mQAct;
	}

	@Override
	public void onItemClick(QuickAction source, int pos, int actionId) {
		setStatus(source, pos);
	}

	@Override
	public void onDismiss() {
		
	}

}
