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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.model;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 16, 2015, 4:44:46 AM
 * <li> Add Support to Mobile
 */
public class OrgAccess {

	/**
	 * 	Org Access constructor
	 *	@param ad_Client_ID client
	 *	@param ad_Org_ID org
	 *	@param readonly r/o
	 */
	public OrgAccess (int ad_Client_ID, int ad_Org_ID, boolean readonly) {
		this.AD_Client_ID = ad_Client_ID;
		this.AD_Org_ID = ad_Org_ID;
		this.readOnly = readonly;
	}
	/** Client				*/
	public int 		AD_Client_ID = 0;
	/** Organization		*/
	public int 		AD_Org_ID = 0;
	/** Read Only			*/
	public boolean 	readOnly = true;
	
	
	/**
	 * 	Equals
	 *	@param obj object to compare
	 *	@return true if equals
	 */
	public boolean equals (Object obj) {
		if (obj != null && obj instanceof OrgAccess) {
			OrgAccess comp = (OrgAccess)obj;
			return comp.AD_Client_ID == AD_Client_ID 
				&& comp.AD_Org_ID == AD_Org_ID;
		}
		return false;
	}	//	equals
	
	
	/**
	 * 	Hash Code
	 *	@return hash Code
	 */
	public int hashCode () {
		return (AD_Client_ID*7) + AD_Org_ID;
	}	//	hashCode

	@Override
	public String toString() {
		return "OrgAccess [AD_Client_ID=" + AD_Client_ID + ", AD_Org_ID="
				+ AD_Org_ID + ", readOnly=" + readOnly + "]";
	}
}