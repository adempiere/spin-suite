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
package org.spinsuite.util;

/**
 *	(Numeric) Key Name Pair
 *
 *  @author     Jorg Janke
 *  @version    $Id: KeyNamePair.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 *  
 */
public final class StringNamePair extends NamePair {
	
	public static final StringNamePair EMPTY = new StringNamePair("", "");

	/**
	 *	Constructor KeyValue Pair -
	 *  @param key Key (-1 is considered as null)
	 *  @param name string representation
	 */
	public StringNamePair(String key, String name)
	{
		super(name);
		m_key = key;
	}   //  KeyNamePair

	/** The Key         */
	private String 	m_key = "";

	/**
	 *	Get Key
	 *  @return key
	 */
	public String getKey()
	{
		return m_key;
	}	//	getKey

	/**
	 *	Get ID (key as String)
	 *
	 *  @return String value of key or null if -1
	 */
	public String getID()
	{
		return m_key;
	}	//	getID


	/**
	 *	Equals
	 *  @param obj object
	 *  @return true if equal
	 */
	public boolean equals(Object obj)
	{
		if (obj instanceof StringNamePair)
		{
			StringNamePair pp = (StringNamePair)obj;
			if (pp.getKey() == m_key
				&& pp.getName() != null
				&& pp.getName().equals(getName()))
				return true;
			return false;
		}
		return false;
	}	//	equals

}	//	KeyNamePair
