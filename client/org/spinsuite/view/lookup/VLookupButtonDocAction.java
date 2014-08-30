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

import org.spinsuite.base.R;
import org.spinsuite.process.DocAction;
import org.spinsuite.process.DocumentEngine;
import org.spinsuite.util.ActionItemList;
import org.spinsuite.util.Msg;
import org.spinsuite.util.contribution.QuickAction;
import org.spinsuite.util.contribution.QuickAction.OnActionItemClickListener;
import org.spinsuite.util.contribution.QuickAction.OnDismissListener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupButtonDocAction extends VLookupButton 
					implements OnActionItemClickListener, OnDismissListener{

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
	
	@Override	
	protected void init() {
		super.init();
		v_Button.setCompoundDrawablesWithIntrinsicBounds(
				getResources().getDrawable(R.drawable.edit_m), null, null, null);
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/05/2012, 17:19:48
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
					getResources().getDrawable(R.drawable.doc_completed_m)));
		}
		//	Action Prepare
		if(docEngine.isValidAction(DocAction.ACTION_Prepare)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Prepare, 
					getResources().getString(R.string.ACTION_Prepare), 
					getResources().getDrawable(R.drawable.doc_progress_m)));	
		}
		//	Action Void
		if(docEngine.isValidAction(DocAction.ACTION_Void)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_Void, 
					getResources().getString(R.string.ACTION_Void), 
					getResources().getDrawable(R.drawable.remove_m)));	
		}
		//	Action Void
		if(docEngine.isValidAction(DocAction.ACTION_ReActivate) 
				&& !docEngine.getDocStatus().equals(DocAction.STATUS_Drafted)){
			mQAct.addActionItem(new ActionItemList(DocAction.ACTION_ReActivate, 
					getResources().getString(R.string.ACTION_ReActivate), 
					getResources().getDrawable(R.drawable.doc_progress_m)));	
		}
		
		mQAct.show(v);
	}
	
	/**
	 * Set Document Action
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 00:35:12
	 * @param action
	 * @return boolean
	 */
	public boolean processDocAction(String action){
		if(!docEngine.processIt(action)){
			Msg.alertMsg(getContext(), 
					getResources().getString(R.string.msg_Error), 
					Msg.parseTranslation(getContext(), docEngine.getProcessMsg()));
			return false;
		}
		//	
		updateDisplay(action);
		//	Return
		return true;
	}
	
	/**
	 * Update Display
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/05/2014, 15:26:38
	 * @param action
	 * @return void
	 */
	private void updateDisplay(String action){
		String label = null;
		Drawable img = null;
		//	
		if(action != null){
			if(action.equals(DocAction.ACTION_Complete)){						//	Complete
				label = getResources().getString(R.string.ACTION_Complete);
				img = getResources().getDrawable(R.drawable.doc_completed_m);
			} else if(action.equals(DocAction.ACTION_Prepare)){					//	Prepare
				label = getResources().getString(R.string.ACTION_Prepare);
				img = getResources().getDrawable(R.drawable.doc_progress_m);
			} else if(action.equals(DocAction.ACTION_Close)){					//	Close
				label = getResources().getString(R.string.ACTION_Close);
				img = getResources().getDrawable(R.drawable.errorsync_m);
			} else if(action.equals(DocAction.ACTION_Void)){					//	Void
				label = getResources().getString(R.string.ACTION_Void);
				img = getResources().getDrawable(R.drawable.remove_m);
			} else if(action.equals(DocAction.ACTION_Reverse_Correct)){			//	Reverse Correct
				label = getResources().getString(R.string.ACTION_Reverse);
				img = getResources().getDrawable(R.drawable.remove_m);
			} else if(action.equals(DocAction.ACTION_ReActivate)){				//	Re-Activate
				label = getResources().getString(R.string.ACTION_ReActivate);
				img = getResources().getDrawable(R.drawable.download_m);
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 00:43:16
	 * @return
	 * @return String
	 */
	public String getDocStatus(){
		return docEngine.getDocStatus();
	}
	
	/**
	 * Set Document Action
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/05/2014, 15:17:31
	 * @param status
	 * @return void
	 */
	public void setDocAction(String status){
		updateDisplay(status);
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
		ActionItemList item = (ActionItemList) source.getActionItem(pos);
		m_IsProcessed = processDocAction(item.getValue());
		//	Listener
		if(m_Listener != null)
			m_Listener.onFieldEvent(this);
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/08/2014, 17:33:28
	 * @return
	 * @return boolean
	 */
	public boolean isProcessed() {
		return m_IsProcessed;
	}

}
