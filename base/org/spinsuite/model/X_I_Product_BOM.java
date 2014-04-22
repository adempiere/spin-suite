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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import org.spinsuite.base.DB;
import org.spinsuite.util.Env;

/** Generated Model for I_Product_BOM
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS (1252452765) - $Id$ */
public class X_I_Product_BOM extends PO implements I_I_Product_BOM
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140422L;

    /** Standard Constructor */
    public X_I_Product_BOM (Context ctx, int I_Product_BOM_ID, DB conn)
    {
      super (ctx, I_Product_BOM_ID, conn);
      /** if (I_Product_BOM_ID == 0)
        {
			setI_IsImported (false);
// N
			setI_Product_BOM_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_Product_BOM (Context ctx, Cursor rs, DB conn)
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
      StringBuffer sb = new StringBuffer ("X_I_Product_BOM[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Quantity Assay.
		@param Assay 
		Indicated the Quantity Assay to use into Quality Order
	  */
	public void setAssay (BigDecimal Assay)
	{
		set_Value (COLUMNNAME_Assay, Assay);
	}

	/** Get Quantity Assay.
		@return Indicated the Quantity Assay to use into Quality Order
	  */
	public BigDecimal getAssay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Assay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Backflush Group.
		@param BackflushGroup 
		The Grouping Components to the Backflush
	  */
	public void setBackflushGroup (String BackflushGroup)
	{
		set_Value (COLUMNNAME_BackflushGroup, BackflushGroup);
	}

	/** Get Backflush Group.
		@return The Grouping Components to the Backflush
	  */
	public String getBackflushGroup () 
	{
		return (String)get_Value(COLUMNNAME_BackflushGroup);
	}

	/** BOMType AD_Reference_ID=347 */
	public static final int BOMTYPE_AD_Reference_ID=347;
	/** Current Active = A */
	public static final String BOMTYPE_CurrentActive = "A";
	/** Make-To-Order = O */
	public static final String BOMTYPE_Make_To_Order = "O";
	/** Previous = P */
	public static final String BOMTYPE_Previous = "P";
	/** Previous, Spare = S */
	public static final String BOMTYPE_PreviousSpare = "S";
	/** Future = F */
	public static final String BOMTYPE_Future = "F";
	/** Maintenance = M */
	public static final String BOMTYPE_Maintenance = "M";
	/** Repair = R */
	public static final String BOMTYPE_Repair = "R";
	/** Product Configure = C */
	public static final String BOMTYPE_ProductConfigure = "C";
	/** Make-To-Kit = K */
	public static final String BOMTYPE_Make_To_Kit = "K";
	/** Set BOM Type.
		@param BOMType 
		Type of BOM
	  */
	public void setBOMType (String BOMType)
	{

		set_Value (COLUMNNAME_BOMType, BOMType);
	}

	/** Get BOM Type.
		@return Type of BOM
	  */
	public String getBOMType () 
	{
		return (String)get_Value(COLUMNNAME_BOMType);
	}

	/** BOMUse AD_Reference_ID=348 */
	public static final int BOMUSE_AD_Reference_ID=348;
	/** Master = A */
	public static final String BOMUSE_Master = "A";
	/** Engineering = E */
	public static final String BOMUSE_Engineering = "E";
	/** Manufacturing = M */
	public static final String BOMUSE_Manufacturing = "M";
	/** Planning = P */
	public static final String BOMUSE_Planning = "P";
	/** Quality = Q */
	public static final String BOMUSE_Quality = "Q";
	/** Set BOM Use.
		@param BOMUse 
		The use of the Bill of Material
	  */
	public void setBOMUse (String BOMUse)
	{

		set_Value (COLUMNNAME_BOMUse, BOMUse);
	}

	/** Get BOM Use.
		@return The use of the Bill of Material
	  */
	public String getBOMUse () 
	{
		return (String)get_Value(COLUMNNAME_BOMUse);
	}

	/** ComponentType AD_Reference_ID=53225 */
	public static final int COMPONENTTYPE_AD_Reference_ID=53225;
	/** By-Product = BY */
	public static final String COMPONENTTYPE_By_Product = "BY";
	/** Component = CO */
	public static final String COMPONENTTYPE_Component = "CO";
	/** Phantom = PH */
	public static final String COMPONENTTYPE_Phantom = "PH";
	/** Packing = PK */
	public static final String COMPONENTTYPE_Packing = "PK";
	/** Planning = PL */
	public static final String COMPONENTTYPE_Planning = "PL";
	/** Tools = TL */
	public static final String COMPONENTTYPE_Tools = "TL";
	/** Option = OP */
	public static final String COMPONENTTYPE_Option = "OP";
	/** Variant = VA */
	public static final String COMPONENTTYPE_Variant = "VA";
	/** Co-Product = CP */
	public static final String COMPONENTTYPE_Co_Product = "CP";
	/** Set Component Type.
		@param ComponentType 
		Component Type for a Bill of Material or Formula
	  */
	public void setComponentType (String ComponentType)
	{

		set_Value (COLUMNNAME_ComponentType, ComponentType);
	}

	/** Get Component Type.
		@return Component Type for a Bill of Material or Formula
	  */
	public String getComponentType () 
	{
		return (String)get_Value(COLUMNNAME_ComponentType);
	}

	/** Set Cost Allocation Percent.
		@param CostAllocationPerc 
		Cost allocation percent in case of a co-product.
	  */
	public void setCostAllocationPerc (BigDecimal CostAllocationPerc)
	{
		set_Value (COLUMNNAME_CostAllocationPerc, CostAllocationPerc);
	}

	/** Get Cost Allocation Percent.
		@return Cost allocation percent in case of a co-product.
	  */
	public BigDecimal getCostAllocationPerc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostAllocationPerc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

	/** Set Import Error Message.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import process
	  */
	public String getI_ErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set I_Product_BOM_ID.
		@param I_Product_BOM_ID I_Product_BOM_ID	  */
	public void setI_Product_BOM_ID (int I_Product_BOM_ID)
	{
		if (I_Product_BOM_ID < 1) 
			set_Value (COLUMNNAME_I_Product_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_I_Product_BOM_ID, Integer.valueOf(I_Product_BOM_ID));
	}

	/** Get I_Product_BOM_ID.
		@return I_Product_BOM_ID	  */
	public int getI_Product_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Product_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Is Critical Component.
		@param IsCritical 
		Indicate that a Manufacturing Order can not begin without have this component
	  */
	public void setIsCritical (boolean IsCritical)
	{
		set_Value (COLUMNNAME_IsCritical, Boolean.valueOf(IsCritical));
	}

	/** Get Is Critical Component.
		@return Indicate that a Manufacturing Order can not begin without have this component
	  */
	public boolean isCritical () 
	{
		Object oo = get_Value(COLUMNNAME_IsCritical);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Qty Percentage.
		@param IsQtyPercentage 
		Indicate that this component is based in % Quantity
	  */
	public void setIsQtyPercentage (boolean IsQtyPercentage)
	{
		set_Value (COLUMNNAME_IsQtyPercentage, Boolean.valueOf(IsQtyPercentage));
	}

	/** Get Is Qty Percentage.
		@return Indicate that this component is based in % Quantity
	  */
	public boolean isQtyPercentage () 
	{
		Object oo = get_Value(COLUMNNAME_IsQtyPercentage);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** IssueMethod AD_Reference_ID=53226 */
	public static final int ISSUEMETHOD_AD_Reference_ID=53226;
	/** Issue = 0 */
	public static final String ISSUEMETHOD_Issue = "0";
	/** Backflush = 1 */
	public static final String ISSUEMETHOD_Backflush = "1";
	/** Floor Stock = 2 */
	public static final String ISSUEMETHOD_FloorStock = "2";
	/** Set Issue Method.
		@param IssueMethod 
		There are two methods for issue the components to Manufacturing Order
	  */
	public void setIssueMethod (String IssueMethod)
	{

		set_Value (COLUMNNAME_IssueMethod, IssueMethod);
	}

	/** Get Issue Method.
		@return There are two methods for issue the components to Manufacturing Order
	  */
	public String getIssueMethod () 
	{
		return (String)get_Value(COLUMNNAME_IssueMethod);
	}

	/** Set Lead Time Offset.
		@param LeadTimeOffset 
		Optional Lead Time offset before starting production
	  */
	public void setLeadTimeOffset (int LeadTimeOffset)
	{
		set_Value (COLUMNNAME_LeadTimeOffset, Integer.valueOf(LeadTimeOffset));
	}

	/** Get Lead Time Offset.
		@return Optional Lead Time offset before starting production
	  */
	public int getLeadTimeOffset () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LeadTimeOffset);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BOM Component.
		@param M_BOMProduct_ID 
		Bill of Material Component (Product)
	  */
	public void setM_BOMProduct_ID (int M_BOMProduct_ID)
	{
		if (M_BOMProduct_ID < 1) 
			set_Value (COLUMNNAME_M_BOMProduct_ID, null);
		else 
			set_Value (COLUMNNAME_M_BOMProduct_ID, Integer.valueOf(M_BOMProduct_ID));
	}

	/** Get BOM Component.
		@return Bill of Material Component (Product)
	  */
	public int getM_BOMProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BOMProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Change Notice.
		@param M_ChangeNotice_ID 
		Bill of Materials (Engineering) Change Notice (Version)
	  */
	public void setM_ChangeNotice_ID (int M_ChangeNotice_ID)
	{
		if (M_ChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, Integer.valueOf(M_ChangeNotice_ID));
	}

	/** Get Change Notice.
		@return Bill of Materials (Engineering) Change Notice (Version)
	  */
	public int getM_ChangeNotice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ChangeNotice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
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

	/** Set Org Key.
		@param OrgValue 
		Key of the Organization
	  */
	public void setOrgValue (String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	/** Get Org Key.
		@return Key of the Organization
	  */
	public String getOrgValue () 
	{
		return (String)get_Value(COLUMNNAME_OrgValue);
	}

	/** Set BOM & Formula.
		@param PP_Product_BOM_ID 
		BOM & Formula
	  */
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, Integer.valueOf(PP_Product_BOM_ID));
	}

	/** Get BOM & Formula.
		@return BOM & Formula
	  */
	public int getPP_Product_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BOM Line.
		@param PP_Product_BOMLine_ID 
		BOM Line
	  */
	public void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID)
	{
		if (PP_Product_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, Integer.valueOf(PP_Product_BOMLine_ID));
	}

	/** Get BOM Line.
		@return BOM Line
	  */
	public int getPP_Product_BOMLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOMLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Product BOM Key.
		@param Product_BOM_Value 
		Key of Product BOM
	  */
	public void setProduct_BOM_Value (String Product_BOM_Value)
	{
		set_Value (COLUMNNAME_Product_BOM_Value, Product_BOM_Value);
	}

	/** Get Product BOM Key.
		@return Key of Product BOM
	  */
	public String getProduct_BOM_Value () 
	{
		return (String)get_Value(COLUMNNAME_Product_BOM_Value);
	}

	/** Set Product Key.
		@param ProductValue 
		Key of the Product
	  */
	public void setProductValue (String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	/** Get Product Key.
		@return Key of the Product
	  */
	public String getProductValue () 
	{
		return (String)get_Value(COLUMNNAME_ProductValue);
	}

	/** Set Quantity in %.
		@param QtyBatch 
		Indicate the Quantity % use in this Formula
	  */
	public void setQtyBatch (BigDecimal QtyBatch)
	{
		set_Value (COLUMNNAME_QtyBatch, QtyBatch);
	}

	/** Get Quantity in %.
		@return Indicate the Quantity % use in this Formula
	  */
	public BigDecimal getQtyBatch () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBatch);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyBOM 
		Indicate the Quantity  use in this BOM
	  */
	public void setQtyBOM (BigDecimal QtyBOM)
	{
		set_Value (COLUMNNAME_QtyBOM, QtyBOM);
	}

	/** Get Quantity.
		@return Indicate the Quantity  use in this BOM
	  */
	public BigDecimal getQtyBOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scrap %.
		@param Scrap 
		Indicate the Scrap %  for calculate the Scrap Quantity
	  */
	public void setScrap (BigDecimal Scrap)
	{
		set_Value (COLUMNNAME_Scrap, Scrap);
	}

	/** Get Scrap %.
		@return Indicate the Scrap %  for calculate the Scrap Quantity
	  */
	public BigDecimal getScrap () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Scrap);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Date ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Date getValidFrom () 
	{
		return (Date)get_Value(COLUMNNAME_ValidFrom);
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

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set UOM Code.
		@param X12DE355 
		UOM EDI X12 Code
	  */
	public void setX12DE355 (String X12DE355)
	{
		set_Value (COLUMNNAME_X12DE355, X12DE355);
	}

	/** Get UOM Code.
		@return UOM EDI X12 Code
	  */
	public String getX12DE355 () 
	{
		return (String)get_Value(COLUMNNAME_X12DE355);
	}
}