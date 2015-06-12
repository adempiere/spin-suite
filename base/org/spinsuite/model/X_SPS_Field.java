/******************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                            *
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
import org.spinsuite.base.DB;
import org.spinsuite.util.KeyNamePair;

/** Generated Model for SPS_Field
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_SPS_Field extends PO implements I_SPS_Field {
    /** Standard Constructor */
    public X_SPS_Field (Context ctx, int SPS_Field_ID, DB conn)
    {
      super (ctx, SPS_Field_ID, conn);
      /** if (SPS_Field_ID == 0)
        {
			setEntityType (null);
// ECA01
			setName (null);
			setSPS_Column_ID (0);
			setSPS_Field_ID (0);
			setSPS_Tab_ID (0);
        } */
    }

    /** Load Constructor */
    public X_SPS_Field (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_SPS_Field[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Field Group.
		@param AD_FieldGroup_ID 
		Logical grouping of fields
	  */
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID)
	{
		if (AD_FieldGroup_ID < 1) 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, null);
		else 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, Integer.valueOf(AD_FieldGroup_ID));
	}

	/** Get Field Group.
		@return Logical grouping of fields
	  */
	public int getAD_FieldGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_FieldGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Reference.
		@return System Reference and Validation
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference Key.
		@param AD_Reference_Value_ID 
		Required to specify, if data type is Table or List
	  */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
	}

	/** Get Reference Key.
		@return Required to specify, if data type is Table or List
	  */
	public int getAD_Reference_Value_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Value_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dynamic Validation.
		@param AD_Val_Rule_ID 
		Dynamic Validation Rule
	  */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamic Validation.
		@return Dynamic Validation Rule
	  */
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Default Logic.
		@param DefaultValue 
		Default value hierarchy, separated by ;
	  */
	public void setDefaultValue (String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	/** Get Default Logic.
		@return Default value hierarchy, separated by ;
	  */
	public String getDefaultValue () 
	{
		return (String)get_Value(COLUMNNAME_DefaultValue);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Display Logic.
		@param DisplayLogic 
		If the Field is displayed, the result determines if the field is actually displayed
	  */
	public void setDisplayLogic (String DisplayLogic)
	{
		set_Value (COLUMNNAME_DisplayLogic, DisplayLogic);
	}

	/** Get Display Logic.
		@return If the Field is displayed, the result determines if the field is actually displayed
	  */
	public String getDisplayLogic () 
	{
		return (String)get_Value(COLUMNNAME_DisplayLogic);
	}

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Centrally maintained.
		@param IsCentrallyMaintained 
		Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
	}

	/** Get Centrally maintained.
		@return Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained () 
	{
		Object oo = get_Value(COLUMNNAME_IsCentrallyMaintained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Encrypted.
		@param IsEncrypted 
		Display or Storage is encrypted
	  */
	public void setIsEncrypted (boolean IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, Boolean.valueOf(IsEncrypted));
	}

	/** Get Encrypted.
		@return Display or Storage is encrypted
	  */
	public boolean isEncrypted () 
	{
		Object oo = get_Value(COLUMNNAME_IsEncrypted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** IsMandatory AD_Reference_ID=319 */
	public static final int ISMANDATORY_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISMANDATORY_Yes = "Y";
	/** No = N */
	public static final String ISMANDATORY_No = "N";
	/** Set Mandatory.
		@param IsMandatory 
		Data entry is required in this column
	  */
	public void setIsMandatory (String IsMandatory)
	{

		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public String getIsMandatory () 
	{
		return (String)get_Value(COLUMNNAME_IsMandatory);
	}

	/** Set Read Only.
		@param IsReadOnly 
		Field is read only
	  */
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Read Only.
		@return Field is read only
	  */
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Same Line.
		@param IsSameLine 
		Displayed on same line as previous field
	  */
	public void setIsSameLine (boolean IsSameLine)
	{
		set_Value (COLUMNNAME_IsSameLine, Boolean.valueOf(IsSameLine));
	}

	/** Get Same Line.
		@return Displayed on same line as previous field
	  */
	public boolean isSameLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsSameLine);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mobile Column.
		@param SPS_Column_ID Mobile Column	  */
	public void setSPS_Column_ID (int SPS_Column_ID)
	{
		if (SPS_Column_ID < 1) 
			set_Value (COLUMNNAME_SPS_Column_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_Column_ID, Integer.valueOf(SPS_Column_ID));
	}

	/** Get Mobile Column.
		@return Mobile Column	  */
	public int getSPS_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Field.
		@param SPS_Field_ID Field	  */
	public void setSPS_Field_ID (int SPS_Field_ID)
	{
		if (SPS_Field_ID < 1) 
			set_Value (COLUMNNAME_SPS_Field_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_Field_ID, Integer.valueOf(SPS_Field_ID));
	}

	/** Get Field.
		@return Field	  */
	public int getSPS_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tab.
		@param SPS_Tab_ID Tab	  */
	public void setSPS_Tab_ID (int SPS_Tab_ID)
	{
		if (SPS_Tab_ID < 1) 
			set_Value (COLUMNNAME_SPS_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_SPS_Tab_ID, Integer.valueOf(SPS_Tab_ID));
	}

	/** Get Tab.
		@return Tab	  */
	public int getSPS_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SPS_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}