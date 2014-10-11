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
import org.spinsuite.util.ActionItemList;
import org.spinsuite.util.Env;
import org.spinsuite.util.TabParameter;
import org.spinsuite.util.contribution.QuickAction;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LookupButtonPaymentRule extends VLookupButton {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 */
	public LookupButtonPaymentRule(Activity activity) {
		super(activity);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 * @param attrs
	 */
	public LookupButtonPaymentRule(Activity activity, AttributeSet attrs) {
		super(activity, attrs);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 * @param attrs
	 * @param defStyle
	 */
	public LookupButtonPaymentRule(Activity activity, AttributeSet attrs,
			int defStyle) {
		super(activity, attrs, defStyle);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/05/2014, 10:43:57
	 * @param activity
	 * @param m_field
	 */
	public LookupButtonPaymentRule(Activity activity, InfoField m_field) {
		super(activity, m_field);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/10/2014, 21:11:25
	 * @param activity
	 * @param m_field
	 * @param tabParam
	 */
	public LookupButtonPaymentRule(Activity activity, InfoField m_field, TabParameter tabParam) {
		super(activity, m_field, tabParam);
	}
	
	private QuickAction mQAct;
	private String 		paymentRule = "B";
	
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** Credit Card = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** Direct Deposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** On Credit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** Direct Debit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	
	@Override	
	protected void init() {
		super.init();
		v_Button.setText(getResources().getString(R.string.PAYMENTRULE_Cash));
		v_Button.setCompoundDrawablesWithIntrinsicBounds(
				getResources().getDrawable(R.drawable.cash_m), null, null, null);
		
		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mQAct.show(v);
			}
		});
		
		mQAct = new QuickAction(getContext());
		
		mQAct.addActionItem(new ActionItemList(PAYMENTRULE_Cash, 
				getResources().getString(R.string.PAYMENTRULE_Cash), 
				getResources().getDrawable(R.drawable.cash_m)));
		
		mQAct.addActionItem(new ActionItemList(PAYMENTRULE_Check, 
				getResources().getString(R.string.PAYMENTRULE_Check), 
				getResources().getDrawable(R.drawable.check_m)));
		
		mQAct.addActionItem(new ActionItemList(PAYMENTRULE_OnCredit, 
				getResources().getString(R.string.PAYMENTRULE_OnCredit), 
				getResources().getDrawable(R.drawable.oncredit_m)));
		
		mQAct.addActionItem(new ActionItemList(PAYMENTRULE_CreditCard, 
				getResources().getString(R.string.PAYMENTRULE_CreditCard), 
				getResources().getDrawable(R.drawable.direct_credit_m)));
		
		mQAct.addActionItem(new ActionItemList(PAYMENTRULE_DirectDebit, 
				getResources().getString(R.string.PAYMENTRULE_DirectDebit), 
				getResources().getDrawable(R.drawable.direct_debit_m)));
		
		mQAct.addActionItem(new ActionItemList(PAYMENTRULE_DirectDeposit, 
				getResources().getString(R.string.PAYMENTRULE_DirectDeposit), 
				getResources().getDrawable(R.drawable.direct_deposit_m)));
		
		mQAct.addActionItem(new ActionItemList(PAYMENTRULE_Mixed, 
				getResources().getString(R.string.PAYMENTRULE_Mixed), 
				getResources().getDrawable(R.drawable.payment_mixed_m)));
		
		//	Action Event
		mQAct.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItemList actionItem = (ActionItemList) quickAction.getActionItem(pos);
				updateDisplay(actionItem);
				paymentRule = actionItem.getValue();
			}
		});
				
		mQAct.setOnDismissListener(new QuickAction.OnDismissListener() {
			@Override
			public void onDismiss() {
				
			}
		});
	}
	
	/**
	 * Update Display
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/05/2012, 13:51:54
	 * @param actionItem
	 * @return void
	 */
	private void updateDisplay(ActionItemList actionItem){
		v_Button.setText(actionItem.getTitle());
		v_Button.setCompoundDrawablesWithIntrinsicBounds(
				actionItem.getIcon(), null, null, null);
	}
	
	/**
	 * Set Payment Rule
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 00:35:12
	 * @param paymentRule
	 * @return void
	 */
	public void setPaymentRule(String paymentRule){
		if(paymentRule != null){
			if(paymentRule.equals(PAYMENTRULE_Cash)){
				v_Button.setText(getResources().getString(R.string.PAYMENTRULE_Cash));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(
						Env.getResourceID(getContext(), R.attr.ic_payment_rule_cash), 0, 0, 0);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_Check)){
				v_Button.setText(getResources().getString(R.string.PAYMENTRULE_Check));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(
						Env.getResourceID(getContext(), R.attr.ic_payment_rule_check), 0, 0, 0);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_CreditCard)){
				v_Button.setText(getResources().getString(R.string.PAYMENTRULE_CreditCard));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(
						Env.getResourceID(getContext(), R.attr.ic_payment_rule_direct_credit), 0, 0, 0);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_DirectDebit)){
				v_Button.setText(getResources().getString(R.string.PAYMENTRULE_DirectDebit));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(
						Env.getResourceID(getContext(), R.attr.ic_payment_rule_direct_debit), 0, 0, 0);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_DirectDeposit)){
				v_Button.setText(getResources().getString(R.string.PAYMENTRULE_DirectDeposit));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(
						Env.getResourceID(getContext(), R.attr.ic_payment_rule_direct_deposit), 0, 0, 0);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_Mixed)){
				v_Button.setText(getResources().getString(R.string.PAYMENTRULE_Mixed));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(
						Env.getResourceID(getContext(), R.attr.ic_payment_rule_mixed), 0, 0, 0);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_OnCredit)){
				v_Button.setText(getResources().getString(R.string.PAYMENTRULE_OnCredit));
				v_Button.setCompoundDrawablesWithIntrinsicBounds(
						Env.getResourceID(getContext(), R.attr.ic_payment_rule_on_credit), 0, 0, 0);
				this.paymentRule = paymentRule;
			}
		}
	}
	
	/**
	 * Get Payment Rule
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 00:43:16
	 * @return
	 * @return String
	 */
	public String getPaymentRule(){
		return paymentRule;
	}

}
