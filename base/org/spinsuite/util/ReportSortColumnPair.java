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
package org.spinsuite.util;

import java.util.Comparator;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class ReportSortColumnPair extends NamePair implements Comparator<Object>{

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 09:28:22
	 * @param sortNo
	 * @param name
	 */
	public ReportSortColumnPair(int sortNo, String name) {
		super(name);
		m_sortNo = sortNo;
	}
	
	/**	Sort No			*/
	private int m_sortNo = 0;

	@Override
	public String getID() {
		if (m_sortNo == -1)
			return null;
		return String.valueOf(m_sortNo);
	}
	
	/**
	 * Get Sort No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 09:33:38
	 * @return
	 * @return int
	 */
	public int getSortNo(){
		return m_sortNo;
	}
	
	/**
	 * Compare
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 09:33:09
	 * @param o1
	 * @param o2
	 * @return
	 * @return int
	 */
	public int compare(ReportSortColumnPair o1, ReportSortColumnPair o2) {
		return o2.getSortNo() - o1.getSortNo();
	}
	
	/**
	 * Sort by Sort No
	 */
	public int compare (Object o1, Object o2){
		int s1 = o1 == null ? 0 : ((ReportSortColumnPair)o1).getSortNo();
		int s2 = o2 == null ? 0 : ((ReportSortColumnPair)o2).getSortNo();
		return s1 - s2;
	}	//	compare
}
