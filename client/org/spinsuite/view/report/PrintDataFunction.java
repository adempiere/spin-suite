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
package org.spinsuite.view.report;

import java.math.BigDecimal;
import java.math.MathContext;

import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class PrintDataFunction {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 10:11:06
	 */
	public PrintDataFunction() {
		reset();
	}
	
	/**	Numeric Value					*/
	private BigDecimal 		m_MinValue 			= null;
	/**	Max value						*/
	private BigDecimal 		m_MaxValue 			= null;
	/**	Sum Value						*/
	private BigDecimal 		m_SumValue 			= null;
	/** Sum of Squares					*/
	private BigDecimal		m_SumSquareValue 	= null;
	/**	Integer Value					*/
	private int				m_QtyCount			= 0;
	
	
	//	Copied from ADempiere
	/** Symbols									*/
	static private final String[]	FUNCTION_SYMBOLS = new String[]
		{
		//	Sum
		" \u03A3",
		//	Mean
		" \u03BC",
		//	Count
		" \u2116",
		//	Minimum
		" \u2193",
		//	Maximum
		" \u2191", 
		//	Variance
		" \u03C3\u00B2", 
		//	Deviation
		" \u03C3"
		};
	
	/** Sum			*/
	static public final int		F_SUM 		= 0;
	/** Mean		*/
	static public final int		F_MEAN 		= 1;
	/** Count		*/
	static public final int		F_COUNT 	= 2;
	/** Min			*/
	static public final int		F_MIN 		= 3;
	/** Max			*/
	static public final int		F_MAX 		= 4;
	/** Variance	*/
	static public final int		F_VARIANCE 	= 5;
	/** Deviation	*/
	static public final int		F_DEVIATION = 6;
	
	
	/**
	 * Reset Values
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 15:53:14
	 * @return void
	 */
	public void reset(){
		m_SumValue = Env.ZERO;
		m_SumSquareValue = Env.ZERO;
		m_MaxValue = Env.ZERO;
		m_MinValue = Env.ZERO;
		m_QtyCount = 0;
	}
	
	/**
	 * Add Row Values
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 15:52:30
	 * @param value
	 * @param fieldConfig
	 * @return void
	 */
	public void addRowValue(String value, InfoReportField fieldConfig){
		//	Is Summarized
		if(fieldConfig.IsSummarized
				|| fieldConfig.IsAveraged
				|| fieldConfig.IsVarianceCalc
				|| fieldConfig.IsDeviationCalc)
			addToSum(value);
		if(fieldConfig.IsVarianceCalc
				|| fieldConfig.IsDeviationCalc)
			addToSumSquare(value);
		//	Is Max Calc
		if(fieldConfig.IsMaxCalc)
			addToMax(value);
		//	Is Min Calc
		if(fieldConfig.IsMinCalc)
			addToMin(value);
		if(fieldConfig.IsAveraged)
			m_QtyCount++;
	}
	
	
	/**
	 * Add value to Max
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 12:00:30
	 * @param m_Value
	 * @return void
	 */
	public void addToMax(BigDecimal m_Value){
		//	Sum
		if(m_Value == null)
			return;
		//	Add
		m_MaxValue = m_MaxValue.max(m_Value);
	}
	
	/**
	 * Add Value to Min
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 12:02:03
	 * @param m_Value
	 * @return void
	 */
	public void addToMin(BigDecimal m_Value){
		//	Sum
		if(m_Value == null)
			return;
		//	Add
		m_MinValue = m_MinValue.min(m_Value);
	}
	
	/**
	 * Add to Sum
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 10:44:47
	 * @param m_Value
	 * @return void
	 */
	public void addToSum(BigDecimal m_Value){
		//	Sum
		if(m_Value == null)
			return;
		//	Add
		m_SumValue = m_SumValue.add(m_Value);
	}
	
	/**
	 * Add to Sum Square
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 10:29:23
	 * @param m_Value
	 * @return void
	 */
	public void addToSumSquare(BigDecimal m_Value){
		//	Sum
		if(m_Value == null)
			return;
		//	Add
		m_SumSquareValue = m_SumSquareValue.add(m_Value.multiply(m_Value));
	}
	
	/**
	 * Add String to Sum Square
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 10:30:24
	 * @param m_Value
	 * @return void
	 */
	public void addToSumSquare(String m_Value){
		addToSumSquare(DisplayType.getNumber(m_Value));
	}
	
	/**
	 * Add String to Sum
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 10:46:15
	 * @param m_Value
	 * @return void
	 */
	public void addToSum(String m_Value){
		addToSum(DisplayType.getNumber(m_Value));
	}
	
	/**
	 * Add String to Max
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 12:06:27
	 * @param m_Value
	 * @return void
	 */
	public void addToMax(String m_Value){
		addToMax(DisplayType.getNumber(m_Value));
	}
	
	/**
	 * Add String to Min
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 12:06:36
	 * @param m_Value
	 * @return void
	 */
	public void addToMin(String m_Value){
		addToMin(DisplayType.getNumber(m_Value));
	}
	
	/**
	 * Get Min Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 10:36:23
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal getMin() {
		return m_MinValue;
	}
	
	/**
	 * Get Max Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 10:35:28
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal getMax() {
		return m_MaxValue;
	}

	/**
	 * Get Sum Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 10:34:59
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal getSum() {
		return m_SumValue;
	}
	
	/**
	 * Get Variance
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 10:26:45
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal getVariance() {
		//	Copied from ADempiere class PrintDataFunction.java
		//	Variance = sum of squares - (square of sum / count)
		if(m_QtyCount == 0)
			return Env.ZERO;
		BigDecimal count = new BigDecimal(m_QtyCount);
		BigDecimal ss = m_SumValue.multiply(m_SumValue);
		ss = ss.divide(count, MathContext.DECIMAL128);
		BigDecimal variance = m_SumSquareValue.subtract(ss);
		//	Set Scale
		if (variance.scale() > 4)
			variance = variance.setScale(4, BigDecimal.ROUND_HALF_UP);
		//	
		return variance;
	}
	
	/**
	 * Get Standard Deviation
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 10:39:40
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal getDeviation(){
		//	Standard Deviation
		BigDecimal variance = getVariance();
		BigDecimal deviation = new BigDecimal(Math.sqrt(variance.doubleValue()));
		//	Set Scale
		if (deviation.scale() > 4)
			deviation = deviation.setScale(4, BigDecimal.ROUND_HALF_UP);
		return deviation;
	}
	
	/**
	 * Get Quantity Count
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 10:34:23
	 * @return
	 * @return int
	 */
	public int getCount() {
		return m_QtyCount;
	}
	
	/**
	 * Get AVG Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2014, 11:24:30
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal getAvgValue() {
		if(m_QtyCount == 0)
			return Env.ZERO;
		BigDecimal average = m_SumValue
				.divide(new BigDecimal(m_QtyCount), MathContext.DECIMAL128);
		//	Set Scale
		if (average.scale() > 4)
			average = average.setScale(4, BigDecimal.ROUND_HALF_UP);
		//	Return Average
		return average;
	}
	
	/**
	 * Get Function Symbol
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 11:09:21
	 * @param function
	 * @return
	 * @return String
	 */
	public static String getFunctionSymbol(int function){
		if(function < F_SUM
				|| function > F_DEVIATION)
			return "UnknownFunction";
		//	
		return FUNCTION_SYMBOLS[function];
	}	//	getFunctionName
	
	/**
	 * Get Function Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/04/2014, 10:35:26
	 * @param function
	 * @param names
	 * @return
	 * @return String
	 */
	public static String getFunctionName(int function, String [] names){
		if(function < F_SUM
				|| function > F_DEVIATION
				|| names == null
				|| names.length -1 < function)
			return "UnknownFunction";
		//	
		return names[function];
	}	//	getFunctionName
	
	/**
	 * Get Supported Function Quantity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2014, 11:19:22
	 * @return
	 * @return int
	 */
	public static int getSupportedFunctionQty(){
		return FUNCTION_SYMBOLS.length;
	}
}
