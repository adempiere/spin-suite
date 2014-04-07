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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.view.custom;

import org.spinsuite.base.R;
import org.spinsuite.util.ActionItemList;
import org.spinsuite.util.contribution.QuickAction;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * @author Yamel Senih
 *
 */
public class Cust_ButtonPaymentRule extends Button {

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 07/05/2012, 15:57:26
	 * @param context
	 */
	public Cust_ButtonPaymentRule(Context context) {
		super(context);
		init(context);
	}
	
	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 07/05/2012, 15:57:19
	 * @param context
	 * @param attrs
	 */
	public Cust_ButtonPaymentRule(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	//private Button butt_Text;
	private QuickAction mQAct;
	private String paymentRule = "B";
	
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
	
	
	private void init(Context ctx){
		isInEditMode();
		
		//	Load Button 
		//butt_Text = (Button) findViewById(R.id.butt_TextImage);
		
		setText(getResources().getString(R.string.PAYMENTRULE_Cash));
		setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.cash_m), null, null, null);
		
		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mQAct.show(v);
			}
		});
		
		mQAct = new QuickAction(ctx);
		
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
	 * Actualiza el Boton
	 * @author Yamel Senih 11/05/2012, 13:51:54
	 * @param actionItem
	 * @return void
	 */
	private void updateDisplay(ActionItemList actionItem){
		setText(actionItem.getTitle());
		setCompoundDrawablesWithIntrinsicBounds(actionItem.getIcon(), null, null, null);
	}
	
	/**
	 * Establece el Valor de la Regla de Pago
	 * @author Yamel Senih 10/05/2012, 00:35:12
	 * @param paymentRule
	 * @return void
	 */
	public void setPaymentRule(String paymentRule){
		if(paymentRule != null){
			if(paymentRule.equals(PAYMENTRULE_Cash)){
				setText(getResources().getString(R.string.PAYMENTRULE_Cash));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.cash_m), null, null, null);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_Check)){
				setText(getResources().getString(R.string.PAYMENTRULE_Check));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.check_m), null, null, null);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_CreditCard)){
				setText(getResources().getString(R.string.PAYMENTRULE_CreditCard));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.direct_credit_m), null, null, null);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_DirectDebit)){
				setText(getResources().getString(R.string.PAYMENTRULE_DirectDebit));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.direct_debit_m), null, null, null);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_DirectDeposit)){
				setText(getResources().getString(R.string.PAYMENTRULE_DirectDeposit));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.direct_deposit_m), null, null, null);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_Mixed)){
				setText(getResources().getString(R.string.PAYMENTRULE_Mixed));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.payment_mixed_m), null, null, null);
				this.paymentRule = paymentRule;
			} else if(paymentRule.equals(PAYMENTRULE_OnCredit)){
				setText(getResources().getString(R.string.PAYMENTRULE_OnCredit));
				setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.oncredit_m), null, null, null);
				this.paymentRule = paymentRule;
			}
		}
	}
	
	/**
	 * Obtiene la regla de Pago
	 * @author Yamel Senih 10/05/2012, 00:43:16
	 * @return
	 * @return String
	 */
	public String getPaymentRule(){
		return paymentRule;
	}
}
