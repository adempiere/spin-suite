/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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
import org.spinsuite.util.ActionItemList;
import org.spinsuite.util.contribution.QuickAction;
import org.spinsuite.util.contribution.QuickAction.OnActionItemClickListener;
import org.spinsuite.util.contribution.QuickAction.OnDismissListener;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VButtonDocAction extends Button implements OnActionItemClickListener, OnDismissListener{

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2012, 15:57:26
	 * @param context
	 */
	public VButtonDocAction(Context context) {
		super(context);
        init(context);
	}
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2012, 15:57:19
	 * @param context
	 * @param attrs
	 */
	public VButtonDocAction(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private QuickAction 		mQAct;
	private String 				documentStatus = "DR";
	private String [] 			actions = null;
	private String [] 			actionRole = null;
	private boolean 			loaded = false;
	/** Complete = CO */
	public static final String ACTION_Complete = "CO";
	/** Wait Complete = WC */
	public static final String ACTION_WaitComplete = "WC";
	/** Approve = AP */
	public static final String ACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String ACTION_Reject = "RJ";
	/** Post = PO */
	public static final String ACTION_Post = "PO";
	/** Void = VO */
	public static final String ACTION_Void = "VO";
	/** Close = CL */
	public static final String ACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String ACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String ACTION_Reverse_Accrual = "RA";
	/** ReActivate = RE */
	public static final String ACTION_ReActivate = "RE";
	/** <None> = -- */
	public static final String ACTION_None = "--";
	/** Prepare = PR */
	public static final String ACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String ACTION_Unlock = "XL";
	/** Invalidate = IN */
	public static final String ACTION_Invalidate = "IN";
	/** ReOpen = OP */
	public static final String ACTION_ReOpen = "OP";

	/** Drafted = DR */
	public static final String STATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String STATUS_Completed = "CO";
	/** Approved = AP */
	public static final String STATUS_Approved = "AP";
	/** Invalid = IN */
	public static final String STATUS_Invalid = "IN";
	/** Not Approved = NA */
	public static final String STATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String STATUS_Voided = "VO";
	/** Reversed = RE */
	public static final String STATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String STATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String STATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String STATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String STATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String STATUS_WaitingConfirmation = "WC";

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
	 * Init View
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 14:23:39
	 * @param ctx
	 * @return void
	 */
	private void init(Context ctx){
		isInEditMode();
		
		setText(getResources().getString(R.string.STATUS_Drafted));
		setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.edit_m), null, null, null);
		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadActions(v);
			}
		});
		
		mQAct = new QuickAction(ctx);
		
		mQAct.setOnActionItemClickListener(this);
				
		mQAct.setOnDismissListener(this);
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
		if(actionItem.getValue().equals(STATUS_Drafted)){
			setDocAction(STATUS_Completed);
		} else if(actionItem.getValue().equals(ACTION_Complete)){
			setDocAction(STATUS_Completed);
		} else if(actionItem.getValue().equals(ACTION_ReActivate)){
			setDocAction(STATUS_InProgress);
		} else if(actionItem.getValue().equals(ACTION_Void)){
			setDocAction(STATUS_Voided);
		} else if(actionItem.getValue().equals(ACTION_Prepare)){
			setDocAction(STATUS_InProgress);
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
		if(validAction(ACTION_Complete)
				&& validActionRole(ACTION_Complete)){
			mQAct.addActionItem(new ActionItemList(ACTION_Complete, 
					getResources().getString(R.string.ACTION_Complete), 
					getResources().getDrawable(R.drawable.doc_completed_m)));
		}
		//	Action Prepare
		if(validAction(ACTION_Prepare)
				&& validActionRole(ACTION_Prepare)){
			mQAct.addActionItem(new ActionItemList(ACTION_Prepare, 
					getResources().getString(R.string.ACTION_Prepare), 
					getResources().getDrawable(R.drawable.doc_progress_m)));	
		}
		//	Action Void
		if(validAction(ACTION_Void)
				&& validActionRole(ACTION_Void)){
			mQAct.addActionItem(new ActionItemList(ACTION_Void, 
					getResources().getString(R.string.ACTION_Void), 
					getResources().getDrawable(R.drawable.remove_m)));	
		}
		//	Action Void
		if(validAction(ACTION_ReActivate) 
				&& !documentStatus.equals(STATUS_Drafted)
				&& validActionRole(ACTION_ReActivate)){
			mQAct.addActionItem(new ActionItemList(ACTION_ReActivate, 
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
			if(docStatus.equals(STATUS_Drafted)){
				setText(getResources().getString(R.string.STATUS_Drafted));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.edit_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(new String[]{
						VButtonDocAction.ACTION_Complete, 
						VButtonDocAction.ACTION_Prepare, 
						VButtonDocAction.ACTION_Void});
			} else if(docStatus.equals(STATUS_Completed)){
				setText(getResources().getString(R.string.STATUS_Completed));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.doc_completed_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(new String[]{
						VButtonDocAction.ACTION_ReActivate, 
						VButtonDocAction.ACTION_Void});
			} else if(docStatus.equals(STATUS_Voided)){
				setText(getResources().getString(R.string.STATUS_Voided));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.remove_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(null);
			} else if(docStatus.equals(STATUS_InProgress)){
				setText(getResources().getString(R.string.STATUS_InProgress));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.doc_progress_m), null, null, null);
				this.documentStatus = docStatus;
				setActionOption(new String[]{
						VButtonDocAction.ACTION_Complete,
						VButtonDocAction.ACTION_Void});
			}
		} else {
			setText(getResources().getString(R.string.STATUS_Drafted));
			setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.edit_m), null, null, null);
			this.documentStatus = docStatus;
			setActionOption(new String[]{
					VButtonDocAction.ACTION_Complete, 
					VButtonDocAction.ACTION_Prepare, 
					VButtonDocAction.ACTION_Void});
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
	 * Get el QuickAction
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/06/2012, 00:05:24
	 * @return
	 * @return QuickAction
	 */
	public QuickAction getQuickAction(){
		return mQAct;
	}

	/* (non-Javadoc)
	 * @see org.appd.util.contribution.QuickAction.OnActionItemClickListener#onItemClick(org.appd.util.contribution.QuickAction, int, int)
	 */
	@Override
	public void onItemClick(QuickAction source, int pos, int actionId) {
		setStatus(source, pos);
	}

	/* (non-Javadoc)
	 * @see org.appd.util.contribution.QuickAction.OnDismissListener#onDismiss()
	 */
	@Override
	public void onDismiss() {
		
	}
	
}
