/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.spinsuite.model;

import android.content.Context;
import android.database.Cursor;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for K_IndexLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_K_IndexLog extends PO implements I_K_IndexLog
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_K_IndexLog (Context ctx, int K_IndexLog_ID, DB conn)
    {
      super (ctx, K_IndexLog_ID, conn);
      /** if (K_IndexLog_ID == 0)
        {
			setIndexQuery (null);
			setIndexQueryResult (0);
			setK_IndexLog_ID (0);
			setQuerySource (null);
        } */
    }

    /** Load Constructor */
    public X_K_IndexLog (Context ctx, Cursor rs, DB conn)
    {
      super (ctx, rs, conn);
    }


    /** Load Meta Data */
    protected POInfo initPO (Context ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, SPS_Table_ID, get_Connection());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_K_IndexLog[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Index Query.
		@param IndexQuery 
		Text Search Query 
	  */
	public void setIndexQuery (String IndexQuery)
	{
		set_Value (COLUMNNAME_IndexQuery, IndexQuery);
	}

	/** Get Index Query.
		@return Text Search Query 
	  */
	public String getIndexQuery () 
	{
		return (String)get_Value(COLUMNNAME_IndexQuery);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getIndexQuery());
    }

	/** Set Query Result.
		@param IndexQueryResult 
		Result of the text query
	  */
	public void setIndexQueryResult (int IndexQueryResult)
	{
		set_Value (COLUMNNAME_IndexQueryResult, Integer.valueOf(IndexQueryResult));
	}

	/** Get Query Result.
		@return Result of the text query
	  */
	public int getIndexQueryResult () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IndexQueryResult);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Index Log.
		@param K_IndexLog_ID Index Log	  */
	public void setK_IndexLog_ID (int K_IndexLog_ID)
	{
		if (K_IndexLog_ID < 1) 
			set_Value (COLUMNNAME_K_IndexLog_ID, null);
		else 
			set_Value (COLUMNNAME_K_IndexLog_ID, Integer.valueOf(K_IndexLog_ID));
	}

	/** Get Index Log.
		@return Index Log	  */
	public int getK_IndexLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_IndexLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** QuerySource AD_Reference_ID=391 */
	public static final int QUERYSOURCE_AD_Reference_ID=391;
	/** Collaboration Management = C */
	public static final String QUERYSOURCE_CollaborationManagement = "C";
	/** Java Client = J */
	public static final String QUERYSOURCE_JavaClient = "J";
	/** HTML Client = H */
	public static final String QUERYSOURCE_HTMLClient = "H";
	/** Self Service = W */
	public static final String QUERYSOURCE_SelfService = "W";
	/** Set Query Source.
		@param QuerySource 
		Source of the Query
	  */
	public void setQuerySource (String QuerySource)
	{

		set_Value (COLUMNNAME_QuerySource, QuerySource);
	}

	/** Get Query Source.
		@return Source of the Query
	  */
	public String getQuerySource () 
	{
		return (String)get_Value(COLUMNNAME_QuerySource);
	}
}