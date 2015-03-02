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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.view.lookup;

import org.spinsuite.base.R;
import org.spinsuite.process.DocAction;
import org.spinsuite.process.DocumentEngine;
import org.spinsuite.util.ActionItemList;
import org.spinsuite.util.Env;
import org.spinsuite.util.contribution.QuickAction;
import org.spinsuite.util.contribution.QuickAction.OnActionItemClickListener;
import org.spinsuite.util.contribution.QuickAction.OnDismissListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class VLookupButtonDocAction extends VLookupButton 
					implements OnActionItemClickListener, OnDismissListener{

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 02/05/2014, 10:43:57
	 * @param activity
	 */
	public VLookupButtonDocAction(Activity activity) {
		super(activity);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 02/05/2014, 10:43:57
	 * @param activity
	 * @param attrs
	 */
	public VLookupButtonDocAction(Activity activity, AttributeSet attrs) {
		super(activity, attrs);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 02/05/2014, 10:43:57
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
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 02/05/2014, 10:43:57
	 * @param activity
	 * @param m_field
	 * @param po
	 * @param mTab
	 */
	public VLookupButtonDocAction(Activity activity, InfoField m_field, DocAction po) {
		super(activity, m_field);
		//	Set Document Status
		if(po != null)
			docEngine = new DocumentEngine(po, po.getDocStatus());
		else
			docEngine = new DocumentEngine(po);
	}
	
	/**	Quit Action					*/
	private QuickAction 		mQAct;
	/**	Document Engine				*/
	private DocumentEngine		docEngine = null;
	/**	Processed					*/
	private boolean				m_IsProcessed = false;
	/**	Process Message				*/
	private String 				m_ProcessMsg = null;
	/**	This Grid Field				*/
	private GridField			m_GridField = null;
	
	@Override	
	protected void init() {
		super.init();
		v_Button.setCompoundDrawablesWithIntrinsicBounds(
				Env.getResourceID(getContext(), R.attr.ic_doc_draft), 0, 0, 0);
		//	
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
		//	Update Display
		updateDisplay(null);
	}
	
	/**
	 * Load available view
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/05/2012, 17:19:48
	 * @param v
	 * @return void
	 */
	private void loadActions(View v){
		//	
		mQAct.clear();
		//	Action Complete
		if(docEngine.isValidAction(DocAction.ACTION_Complete)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Complete, 
					getResources().getString(R.string.ACTION_Complete), 
					getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_complete))));
		}
		//	Action Prepare
		if(docEngine.isValidAction(DocAction.ACTION_Prepare)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Prepare, 
					getResources().getString(R.string.ACTION_Prepare), 
					getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_prepare))));	
		}
		//	Action Void
		if(docEngine.isValidAction(DocAction.ACTION_Void)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Void, 
					getResources().getString(R.string.ACTION_Void), 
					getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_void))));	
		}
		//	Action Void
		if(docEngine.isValidAction(DocAction.ACTION_ReActivate) 
				&& !docEngine.getDocStatus().equals(DocAction.STATUS_Drafted)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_ReActivate, 
					getResources().getString(R.string.ACTION_ReActivate), 
					getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_reactivate))));	
		}
		
		mQAct.show(v);
	}
	
	/**
	 * Set Document Action
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/05/2012, 00:35:12
	 * @param action
	 * @return boolean
	 */
	public boolean processDocAction(String action){
		m_ProcessMsg = null;
		//	Process
		if(!docEngine.processIt(action)){
			m_ProcessMsg = docEngine.getProcessMsg();
			return false;
		}
		//	Return
		return true;
	}
	
	/**
	 * Update Display
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 06/05/2014, 15:26:38
	 * @param action
	 * @return void
	 */
	private void updateDisplay(String action){
		String label = null;
		Drawable img = null;
		//	
		if(action != null){
			if(action.equals(DocAction.ACTION_Prepare)){						//	Prepare
				label = getResources().getString(R.string.ACTION_Prepare);
				img = getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_prepare));
			} else if(action.equals(DocAction.ACTION_Complete)){				//	Complete
				label = getResources().getString(R.string.ACTION_Complete);
				img = getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_complete));
			} else if(action.equals(DocAction.ACTION_Close)){					//	Close
				label = getResources().getString(R.string.ACTION_Close);
				img = getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_close));
			} else if(action.equals(DocAction.ACTION_Void)){					//	Void
				label = getResources().getString(R.string.ACTION_Void);
				img = getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_void));
			} else if(action.equals(DocAction.ACTION_Reverse_Correct)){			//	Reverse Correct
				label = getResources().getString(R.string.ACTION_Reverse);
				img = getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_reverse_correct));
			} else if(action.equals(DocAction.ACTION_ReActivate)){				//	Re-Activate
				label = getResources().getString(R.string.ACTION_ReActivate);
				img = getResources().getDrawable(Env.getResourceID(getContext(), R.attr.ic_doc_reactivate));
			}
		} else {																//	Default
			label = getResources().getString(R.string.ACTION_Prepare);
			img = getResources().getDrawable(R.drawable.doc_progress_m);
		}
		//	
		v_Button.setText(label);
		v_Button.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
	}
	
	/**
	 * Get Document Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/05/2012, 00:43:16
	 * @return
	 * @return String
	 */
	public String getDocStatus(){
		return docEngine.getDocStatus();
	}
	
	/**
	 * Set Document Action
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 06/05/2014, 15:17:31
	 * @param status
	 * @return void
	 */
	public void setDocAction(String status){
		updateDisplay(status);
	}
	
	/**
	 * Get el QuickAction
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/06/2012, 00:05:24
	 * @return
	 * @return QuickAction
	 */
	public QuickAction getQuickAction(){
		return mQAct;
	}

	@Override
	public void onItemClick(QuickAction source, int pos, int actionId) {
		ActionItemList item = (ActionItemList) source.getActionItem(pos);
		//	Set Grid Field
		if(m_GridField == null)
			m_GridField = this;
		//	Execute thread
		new ProcessDocActionTask().execute(item.getValue());
	}

	@Override
	public void onDismiss() {
		
	}
	
	@Override
	public Object getValue() {
		return getDocStatus();
	}
	
	@Override
	public void setValue(Object value) {
		super.setValue(value);
		//	
		if(value != null
				&& value instanceof String)
			setDocAction((String)value);
		else
			setDocAction(null);
	}
	
	@Override
	public boolean isEmpty() {
		return (getDocStatus() == null 
				|| getDocStatus().length() == 0);
	}
	
	/**
	 * Is Processed
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/08/2014, 17:33:28
	 * @return
	 * @return boolean
	 */
	public boolean isProcessed() {
		return m_IsProcessed;
	}
	
	/**
	 * Get Process Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 18:29:54
	 * @return
	 * @return String
	 */
	public String getProcessMsg() {
		return m_ProcessMsg;
	}
	
	/**
	 * Process Document Action in Thread
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 *
	 */
	private class ProcessDocActionTask extends AsyncTask<String, Void, Void> {

		/**	Progress Bar			*/
		private ProgressDialog 		v_PDialog;
		private String 				m_Action;
		@Override
		protected void onPreExecute() {
			v_PDialog = ProgressDialog.show(getActivity(), null, 
					getActivity().getString(R.string.msg_Processing), false, false);
			//	Set Max
		}
		
		@Override
		protected Void doInBackground(String... params) {
			m_Action = params[0];
			//	Load Data
			m_IsProcessed = processDocAction(m_Action);
			//	
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... progress) {
			//	
		}

		@Override
		protected void onPostExecute(Void result) {
			v_PDialog.dismiss();
			//	Update Display
			updateDisplay(m_Action);
			//	Listener
			if(m_Listener != null)
				m_Listener.onFieldEvent(m_GridField);

		}
	}

}
