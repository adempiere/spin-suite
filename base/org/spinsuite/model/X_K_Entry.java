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
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for K_Entry
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_K_Entry extends PO implements I_K_Entry
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_K_Entry (Context ctx, int K_Entry_ID, DB conn)
    {
      super (ctx, K_Entry_ID, conn);
      /** if (K_Entry_ID == 0)
        {
			setIsPublic (true);
// Y
			setK_Entry_ID (0);
			setK_Topic_ID (0);
			setName (null);
			setRating (0);
			setTextMsg (null);
        } */
    }

    /** Load Constructor */
    public X_K_Entry (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_K_Entry[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Session.
		@param AD_Session_ID 
		User Session Online or Web
	  */
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_Value (COLUMNNAME_AD_Session_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	/** Get Session.
		@return User Session Online or Web
	  */
	public int getAD_Session_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Session_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description URL.
		@param DescriptionURL 
		URL for the description
	  */
	public void setDescriptionURL (String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	/** Get Description URL.
		@return URL for the description
	  */
	public String getDescriptionURL () 
	{
		return (String)get_Value(COLUMNNAME_DescriptionURL);
	}

	/** Set Public.
		@param IsPublic 
		Public can read entry
	  */
	public void setIsPublic (boolean IsPublic)
	{
		set_Value (COLUMNNAME_IsPublic, Boolean.valueOf(IsPublic));
	}

	/** Get Public.
		@return Public can read entry
	  */
	public boolean isPublic () 
	{
		Object oo = get_Value(COLUMNNAME_IsPublic);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Entry.
		@param K_Entry_ID 
		Knowledge Entry
	  */
	public void setK_Entry_ID (int K_Entry_ID)
	{
		if (K_Entry_ID < 1) 
			set_Value (COLUMNNAME_K_Entry_ID, null);
		else 
			set_Value (COLUMNNAME_K_Entry_ID, Integer.valueOf(K_Entry_ID));
	}

	/** Get Entry.
		@return Knowledge Entry
	  */
	public int getK_Entry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_Entry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Keywords.
		@param Keywords 
		List of Keywords - separated by space, comma or semicolon
	  */
	public void setKeywords (String Keywords)
	{
		set_Value (COLUMNNAME_Keywords, Keywords);
	}

	/** Get Keywords.
		@return List of Keywords - separated by space, comma or semicolon
	  */
	public String getKeywords () 
	{
		return (String)get_Value(COLUMNNAME_Keywords);
	}

	/** Set Knowledge Source.
		@param K_Source_ID 
		Source of a Knowledge Entry
	  */
	public void setK_Source_ID (int K_Source_ID)
	{
		if (K_Source_ID < 1) 
			set_Value (COLUMNNAME_K_Source_ID, null);
		else 
			set_Value (COLUMNNAME_K_Source_ID, Integer.valueOf(K_Source_ID));
	}

	/** Get Knowledge Source.
		@return Source of a Knowledge Entry
	  */
	public int getK_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Knowledge Topic.
		@param K_Topic_ID 
		Knowledge Topic
	  */
	public void setK_Topic_ID (int K_Topic_ID)
	{
		if (K_Topic_ID < 1) 
			set_Value (COLUMNNAME_K_Topic_ID, null);
		else 
			set_Value (COLUMNNAME_K_Topic_ID, Integer.valueOf(K_Topic_ID));
	}

	/** Get Knowledge Topic.
		@return Knowledge Topic
	  */
	public int getK_Topic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_Topic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Rating.
		@param Rating 
		Classification or Importance
	  */
	public void setRating (int Rating)
	{
		set_Value (COLUMNNAME_Rating, Integer.valueOf(Rating));
	}

	/** Get Rating.
		@return Classification or Importance
	  */
	public int getRating () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Rating);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Text Message.
		@param TextMsg 
		Text Message
	  */
	public void setTextMsg (String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	/** Get Text Message.
		@return Text Message
	  */
	public String getTextMsg () 
	{
		return (String)get_Value(COLUMNNAME_TextMsg);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Date ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Date getValidTo () 
	{
		return (Date)get_Value(COLUMNNAME_ValidTo);
	}
}