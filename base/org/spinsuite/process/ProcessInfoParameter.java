/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.spinsuite.process;

import java.math.BigDecimal;


/**
 * 	Process Parameter
 *
 *  @author Jorg Janke
 *  @version $Id: ProcessInfoParameter.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2430845 ] Add ProcessInfoParameter.getParameterAsBoolean method
 */
public class ProcessInfoParameter {

	/**
	 *  Construct Parameter
	 *  @param parameterName parameter name
	 *  @param parameter parameter
	 *  @param parameter_To to parameter
	 *  @param info info
	 *  @param info_To to info
	 *  @param displayValue
	 *  @param displayValue_To
	 *  @param m_DisplayType
	 */
	public ProcessInfoParameter (String parameterName, Object parameter, Object parameter_To, 
			String info, String info_To, 
			String displayValue, String displayValue_To, int displayType){
		setParameterName (parameterName);
		setParameter (parameter);
		setParameter_To (parameter_To);
		setInfo (info);
		setInfo_To (info_To);
		setDisplayValue(displayValue);
		setDisplayValue_To(displayValue_To);
		setDisplayType(displayType);
	}   //  ProcessInfoParameter
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 16:52:53
	 * @param parameterName
	 * @param parameter
	 * @param parameter_To
	 * @param info
	 * @param info_To
	 */
	public ProcessInfoParameter (String parameterName, Object parameter, Object parameter_To, String info, String info_To){
		this(parameterName, parameter, parameter_To, info, info_To, null, null, 0);
	}
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:57:17
	 * @param parameterName
	 * @param parameter
	 * @param info
	 * @param displayValue
	 * @param displayType
	 */
	public ProcessInfoParameter (String parameterName, Object parameter, String info, 
			String displayValue, int displayType){
		setParameterName (parameterName);
		setParameter (parameter);
		setInfo (info);
		setDisplayValue(displayValue);
		setDisplayType(displayType);
	}   //  ProcessInfoParameter
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 16:53:50
	 * @param parameterName
	 * @param parameter
	 * @param info
	 */
	public ProcessInfoParameter (String parameterName, Object parameter, String info){
		this(parameterName, parameter, info, null, 0);
	}

	private String 	m_ParameterName;
	private Object 	m_Parameter;
	private	Object	m_Parameter_To;
	private String	m_Info = "";
	private String 	m_Info_To = "";
	//	Yamel Senih 2014-03-07, 16:49:59 Add DisplayType
	private int 	m_DisplayType = 0;
	//	Yamel Senih 2014-04-03, 10:46:50 Add Display Value
	private String	m_DisplayValue = "";
	private String	m_DisplayValue_To = "";
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString(){
		//	From .. To
		if (m_Parameter_To != null || m_Info_To.length() > 0)
			return "ProcessInfoParameter[" + m_ParameterName + "=" + m_Parameter
				+ (m_Parameter==null ? "" : "{" + m_Parameter.getClass().getName() + "}")
				+ " (" + m_Info + ") - "
				+ m_Parameter_To 
				+ (m_Parameter_To==null ? "" : "{" + m_Parameter_To.getClass().getName() + "}")
				+ " (" + m_Info_To + ")";
		//	Value
		return "ProcessInfoParameter[" + m_ParameterName + "=" + m_Parameter
			+ (m_Parameter==null ? "" : "{" + m_Parameter.getClass().getName() + "}")
			+ " (" + m_Info + ")";
	}	//	toString


	/**
	 * Method getInfo
	 * @return String
	 */
	public String getInfo (){
		return m_Info;
	}

	/**
	 * Method getInfo_To
	 * @return String
	 */
	public String getInfo_To (){
		return m_Info_To;
	}

	/**
	 * Method getParameter
	 * @return Object
	 */
	public Object getParameter (){
		return m_Parameter;
	}
	
	/**
	 * Method getParameter as Int
	 * @return Object
	 */
	public int getParameterAsInt (){
		if (m_Parameter == null)
			return 0;
		if (m_Parameter instanceof Number)
			return ((Number)m_Parameter).intValue();
		BigDecimal bd = new BigDecimal(m_Parameter.toString());
		return bd.intValue();
	}	//	getParameterAsInt
	
	/**
	 * Method getParameter as Boolean
	 * @return boolean value
	 */
	public boolean getParameterAsBoolean (){
		if (m_Parameter == null)
			return false;
		if (m_Parameter instanceof Boolean)
			return ((Boolean)m_Parameter).booleanValue();
		return "Y".equals(m_Parameter);
	}

	/**
	 * Method getParameter_To
	 * @return Object
	 */
	public Object getParameter_To (){
		return m_Parameter_To;
	}

	/**
	 * Method getParameter as Int
	 * @return Object
	 */
	public int getParameter_ToAsInt (){
		if (m_Parameter_To == null)
			return 0;
		if (m_Parameter_To instanceof Number)
			return ((Number)m_Parameter_To).intValue();
		BigDecimal bd = new BigDecimal(m_Parameter_To.toString());
		return bd.intValue();
	}	//	getParameter_ToAsInt

	/**
	 * Method getParameter as Boolean
	 * @return boolean
	 */
	public boolean getParameter_ToAsBoolean (){
		if (m_Parameter_To == null)
			return false;
		if (m_Parameter_To instanceof Boolean)
			return ((Boolean)m_Parameter_To).booleanValue();
		return "Y".equals(m_Parameter_To);
	}
	
	
	/**
	 * Method getParameterName
	 * @return String
	 */
	public String getParameterName (){
		return m_ParameterName;
	}

	/**
	 * Method setInfo
	 * @param Info String
	 */
	public void setInfo (String Info){
		if (Info == null)
			m_Info = "";
		else
			m_Info = Info;
	}

	/**
	 * Method setInfo_To
	 * @param Info_To String
	 */
	public void setInfo_To (String Info_To){
		if (Info_To == null)
			m_Info_To = "";
		else
			m_Info_To = Info_To;
	}
	
	/**
	 * Set Display Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:48:46
	 * @param DisplayValue
	 * @return void
	 */
	public void setDisplayValue(String DisplayValue){
		if (DisplayValue == null)
			m_DisplayValue = "";
		else
			m_DisplayValue = DisplayValue;
	}
	
	/**
	 * Set Display Value To
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:49:31
	 * @param DisplayValue_To
	 * @return void
	 */
	public void setDisplayValue_To(String DisplayValue_To){
		if (DisplayValue_To == null)
			m_DisplayValue_To = "";
		else
			m_DisplayValue_To = DisplayValue_To;
	}
	
	/**
	 * Get Display Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:50:15
	 * @return
	 * @return String
	 */
	public String getDisplayValue() {
		return m_DisplayValue;
	}
	
	/**
	 * Get Display Value To
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:50:33
	 * @return
	 * @return String
	 */
	public String getDisplayValue_To() {
		return m_DisplayValue_To;
	}

	/**
	 * Method setParameter
	 * @param Parameter Object
	 */
	public void setParameter (Object Parameter){
		m_Parameter = Parameter;
	}

	/**
	 * Method setParameter_To
	 * @param Parameter_To Object
	 */
	public void setParameter_To (Object Parameter_To){
		m_Parameter_To = Parameter_To;
	}

	/**
	 * Method setParameterName
	 * @param ParameterName String
	 */
	public void setParameterName (String ParameterName){
		m_ParameterName = ParameterName;
	}
	
	/**
	 * Set Display Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 16:51:02
	 * @param m_DisplayType
	 * @return void
	 */
	public void setDisplayType(int m_DisplayType){
		this.m_DisplayType = m_DisplayType;
	}
	
	/**
	 * Get Display Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 16:51:33
	 * @return
	 * @return int
	 */
	public int getDisplayType(){
		return m_DisplayType;
	}

}   //  ProcessInfoParameter
