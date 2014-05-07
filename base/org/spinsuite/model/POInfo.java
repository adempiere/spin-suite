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
package org.spinsuite.model;

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * @author Yamel Senih
 *
 */
public class POInfo {
	
	/**
	 * Get PO Information
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 22:00:49
	 * @param ctx
	 * @param p_SPS_Table_ID
	 * @param conn
	 * @return
	 * @return POInfo
	 */
	public static POInfo getPOInfo(Context ctx, int p_SPS_Table_ID, DB conn){
		POInfo retValue = new POInfo(ctx, p_SPS_Table_ID, conn);
		return retValue;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 22:00:32
	 * @param ctx
	 * @param AD_Table_ID
	 * @param conn
	 */
	public POInfo(Context ctx, int AD_Table_ID, DB conn){
		loadInfoColumn(ctx, AD_Table_ID, null, conn);
	}
	
	/** Table_ID            	*/
	private int					m_SPS_Table_ID = 0;
	/** Table Name          	*/
	private String				m_TableName = null;
	/**	Is Deleteable Record	*/
	private boolean 			m_IsDeleteable = false;
	/** Columns             	*/
	private POInfoColumn[]		m_columns = null;
	/**	Count Column SQL		*/
	private int					m_CountColumnSQL = 0;
	
	/**
	 * Load Column Information
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 21:54:17
	 * @param ctx
	 * @param AD_Table_ID
	 * @param tableName
	 * @return void
	 */
	private void loadInfoColumn(Context ctx, int AD_Table_ID, String tableName, DB p_Conn){
		//	
		String language = Env.getAD_Language(ctx);
		boolean isBaseLanguage = Env.isBaseLanguage(ctx);
		//	
		StringBuffer sql = new StringBuffer();
		//	if Base Language
		if(isBaseLanguage){
			sql.append("SELECT " +
					"t.SPS_Table_ID, " +
					"t.TableName, " +
					"t.IsDeleteable, " +
					"c.AD_Element_ID, " +
					"c.AD_Reference_ID, " +
					"c.AD_Reference_Value_ID, " +
					"c.AD_Val_Rule_ID, " +
					"c.Callout, " +
					"c.ColumnName, " +
					"c.ColumnSQL, " +
					"c.DefaultValue, " +
					"c.Description, " +
					"c.EntityType, " +
					"c.FieldLength, " +
					"c.FormatPattern, " +
					"c.IsAlwaysUpdateable, " +
					"c.IsCentrallyMaintained, " +
					"c.IsEncrypted, " +
					"c.IsIdentifier, " +
					"c.IsKey, " +
					"c.IsMandatory, " +
					"c.IsParent, " +
					"c.IsSelectionColumn, " +
					"c.IsUpdateable, " +
					"c.Name, " +
					"c.SelectionSeqNo, " +
					"c.SeqNo, " +
					"c.SPS_Column_ID, " +
					"c.ValueMax, " +
					"c.ValueMin, " +
					"c.VFormat ");
			//	From
			sql.append("FROM SPS_Table t " +
					"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) ");
		} else {
			sql.append("SELECT " +
					"t.SPS_Table_ID, " +
					"t.TableName, " +
					"t.IsDeleteable, " +
					"c.AD_Element_ID, " +
					"c.AD_Reference_ID, " +
					"c.AD_Reference_Value_ID, " +
					"c.AD_Val_Rule_ID, " +
					"c.Callout, " +
					"c.ColumnName, " +
					"c.ColumnSQL, " +
					"c.DefaultValue, " +
					"c.Description, " +
					"c.EntityType, " +
					"c.FieldLength, " +
					"c.FormatPattern, " +
					"c.IsAlwaysUpdateable, " +
					"c.IsCentrallyMaintained, " +
					"c.IsEncrypted, " +
					"c.IsIdentifier, " +
					"c.IsKey, " +
					"c.IsMandatory, " +
					"c.IsParent, " +
					"c.IsSelectionColumn, " +
					"c.IsUpdateable, " +
					"ct.Name, " +
					"c.SelectionSeqNo, " +
					"c.SeqNo, " +
					"c.SPS_Column_ID, " +
					"c.ValueMax, " +
					"c.ValueMin, " +
					"c.VFormat ");
			//	From
			sql.append("FROM SPS_Table t " +
					"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " +
					"INNER JOIN SPS_Column_Trl ct ON(ct.SPS_Column_ID = c.SPS_Column_ID AND ct.AD_Language = '").append(language).append("') ");
		}
		sql.append("WHERE c.IsActive = 'Y' ");
		if(AD_Table_ID != 0)
			sql.append("AND t.SPS_Table_ID = ").append(AD_Table_ID).append(" ");
		else
			sql.append("AND t.TableName = '").append(tableName).append("' ");
		//	Order By
		sql.append(" ORDER BY c.Name");
		
		LogM.log(ctx, getClass(), Level.FINE, "SQL TableInfo SQL:" + sql);
		
		ArrayList<POInfoColumn> columns = new ArrayList<POInfoColumn>();
		DB conn = null;
		if(p_Conn != null)
			conn = p_Conn;
		else
			conn = new DB(ctx);
		if(!conn.isOpen())
			conn.openDB(DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL(sql.toString(), null);
		if(rs.moveToFirst()){
			int i = 0;
			m_SPS_Table_ID 	= rs.getInt(i++);
			m_TableName 	= rs.getString(i++);
			m_IsDeleteable	= "Y".equals(rs.getString(i++));
			do{
				POInfoColumn iColumn = new POInfoColumn();
				iColumn.SPS_Table_ID = m_SPS_Table_ID;
				iColumn.AD_Element_ID = rs.getInt(i++);
				iColumn.DisplayType = rs.getInt(i++);
				iColumn.AD_Reference_Value_ID = rs.getInt(i++);
				iColumn.AD_Val_Rule_ID = rs.getInt(i++);
				iColumn.Callout = rs.getString(i++);
				iColumn.ColumnName = rs.getString(i++);
				iColumn.ColumnSQL = rs.getString(i++);
				iColumn.DefaultValue = rs.getString(i++);
				iColumn.Description = rs.getString(i++);
				iColumn.EntityType = rs.getString(i++);
				iColumn.FieldLength = rs.getInt(i++);
				iColumn.FormatPattern = rs.getString(i++);
				iColumn.IsAlwaysUpdateable = rs.getString(i++).equals("Y");
				iColumn.IsCentrallyMaintained = rs.getString(i++).equals("Y");
				iColumn.IsEncrypted= rs.getString(i++).equals("Y");
				iColumn.IsIdentifier= rs.getString(i++).equals("Y");
				iColumn.IsKey= rs.getString(i++).equals("Y");
				iColumn.IsMandatory= rs.getString(i++).equals("Y");
				iColumn.IsParent= rs.getString(i++).equals("Y");
				iColumn.IsSelectionColumn= rs.getString(i++).equals("Y");
				iColumn.IsUpdateable= rs.getString(i++).equals("Y");
				iColumn.Name = rs.getString(i++);
				iColumn.SelectionSeqNo = rs.getInt(i++);
				iColumn.SeqNo = rs.getInt(i++);
				iColumn.SPS_Column_ID = rs.getInt(i++);
				iColumn.ValueMax = rs.getString(i++);
				iColumn.ValueMin = rs.getString(i++);
				iColumn.VFormat = rs.getString(i++);
				//Log.i("m_IsAlwaysUpdateable", " - " + m_ColumnName + " = " + m_IsAlwaysUpdateable);
				
				columns.add(iColumn);
				i = 3;
				//	Seek in Column SQL
				if(iColumn.isColumnSQL())
					m_CountColumnSQL ++;
			}while(rs.moveToNext());
		}
		//	Close DB
		if(p_Conn == null)
			conn.closeDB(rs);
		//	Convert Array
		
		m_columns = new POInfoColumn[columns.size()];
		columns.toArray(m_columns);
		Log.d("Size ", "- " + m_columns.length);
	}
	
	/**
	 * Obtiene el nombre de la tabla
	 * @author Yamel Senih 26/02/2012, 23:42:37
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return m_TableName;
	}
	
	/**
	 * Verifica si los registros son eliminables
	 * @author Yamel Senih 07/06/2012, 09:27:31
	 * @return
	 * @return boolean
	 */
	public boolean isDeleteable(){
		return m_IsDeleteable;
	}
	
	/**
	 * Obtiene el id de la tabla
	 * @author Yamel Senih 26/02/2012, 23:43:36
	 * @return
	 * @return int
	 */
	public int getSPS_Table_ID(){
		return m_SPS_Table_ID;
	}
	
	/**
	 * Obtiene la longitud del arreglo
	 * @author Yamel Senih 27/02/2012, 00:27:09
	 * @return
	 * @return int
	 */
	public int getColumnLength(){
		if(m_columns != null){
			return m_columns.length;
		}
		return 0;
	}
	
	/**
	 * Obtiene la cantidad de columnas SQL
	 * @author Yamel Senih 26/07/2012, 11:36:52
	 * @return
	 * @return int
	 */
	public int getCountColumnSQL(){
		return m_CountColumnSQL;
	}
	
	/**
	 * Obtiene el nombre de una Columna con el indice de la misma
	 * @author Yamel Senih 27/02/2012, 00:34:43
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnName(int index){
		if(m_columns != null || index < m_columns.length){
			return m_columns[index].ColumnName;
		}
		return null;
	}
	
	/**
	 * Obtiene las columnas separadas por coma ","
	 * @author Yamel Senih 29/03/2012, 23:58:58
	 * @return
	 * @return String
	 */
	public String getColumnsInsert(){
		StringBuffer columns = new StringBuffer();
		if(m_columns != null){
			for (int i = 0; i < m_columns.length; i++) {
				if(i > 0)
					columns.append(",");
				columns.append(m_columns[i].ColumnName);
			}	
		}
		return columns.toString();
	}
	
	
	/**
	 * Obtiene el ID de la columna a traves del nombre de la misma
	 * @author Yamel Senih 27/02/2012, 00:47:51
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getAD_Column_ID(String columnName){
		if(m_columns != null){
			for (int i = 0; i < m_columns.length; i++) {
				if(m_columns[i].ColumnName.equals(columnName))
					return m_columns[i].AD_Column_ID;
			}	
		}
		return -1;
	}
	
	/**
	 * Obtiene el indice de la columna pasandole el nombre
	 * @author Yamel Senih 27/02/2012, 00:50:45
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getColumnIndex(String columnName){
		columnName = columnName.trim();
		if(m_columns != null){
			for (int i = 0; i < m_columns.length; i++) {
				if(m_columns[i].ColumnName != null 
						&& m_columns[i].ColumnName.equals(columnName)){
					return i;
				}
			}	
		}
		return -1;
	}
	
	/**
	 * Verifica si la columna tiene una llamada asociada
	 * @author Yamel Senih 08/04/2012, 18:44:09
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isCallout(int index){
		if(m_columns != null && index >= 0){
			if(m_columns[index].Callout != null 
					&& m_columns[index].Callout.length() != 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Obtiene la columna SQL a partir del nombre de la columna
	 * @author Yamel Senih 27/02/2012, 00:55:03
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getColumnSQL(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].ColumnSQL;
		}
		return null;
	}
	
	/**
	 * Obtiene la columna SQL a partir del indice de la columna
	 * @author Yamel Senih 25/07/2012, 17:55:45
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnSQL(int index){
		if(index >= 0){
			return m_columns[index].ColumnSQL;
		}
		return null;
	}
	
	/**
	 * Obtiene el tipo de Visualizacion a partir del nombre de la columna
	 * @author Yamel Senih 27/02/2012, 01:01:40
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getDisplayType(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].DisplayType;
		}
		return 0;
	}
	
	/**
	 * Obtiene el tipo de Visualizacion a partir del indice de la columna
	 * @author Yamel Senih 30/03/2012, 22:36:55
	 * @param index
	 * @return
	 * @return int
	 */
	public int getDisplayType(int index){
		if(index >= 0){
			return m_columns[index].DisplayType;
		}
		return 0;
	}
	
	/**
	 * Obtiene informacion sobre si la columna es obligatoria
	 * @author Yamel Senih 27/02/2012, 01:02:45
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsMandatory;
		}
		return false;
	}
	
	/**
	 * Obtiene informacion sobre si la columna es obligatoria
	 * @author Yamel Senih 08/04/2012, 20:58:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(int index){
		if(index >= 0){
			return m_columns[index].IsMandatory;
		}
		return false;
	}
	
	/**
	 * Obtiene la logica predeterminada de la columna
	 * @author Yamel Senih 27/02/2012, 01:04:19
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getDefaultValue(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].DefaultValue;
		}
		return null;
	}
	
	/**
	 * Obtiene la logica predeterminada de la columna
	 * @author Yamel Senih 30/03/2012, 23:42:02
	 * @param index
	 * @return
	 * @return String
	 */
	public String getDefaultValue(int index){
		if(index >= 0){
			return m_columns[index].DefaultValue;
		}
		return null;
	}
	
	/**
	 * Obtiene informacion sobre si es actualizable
	 * @author Yamel Senih 27/02/2012, 01:05:24
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isUpdateable(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsUpdateable;
		}
		return false;
	}
	
	public boolean isUpdateable(int index){
		if(index >= 0){
			return m_columns[index].IsUpdateable;
		}
		return false;
	}
	
	/**
	 * Obtiene informacion sobre el atributo IsAlwaysUpdateable
	 * @author Yamel Senih 28/05/2012, 09:24:10
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsAlwaysUpdateable;
		}
		return false;
	}
	
	/**
	 * Obtiene informacion sobre el atributo IsAlwaysUpdateable
	 * @author Yamel Senih 28/05/2012, 09:25:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(int index){
		if(index >= 0){
			return m_columns[index].IsAlwaysUpdateable;
		}
		return false;
	}
	
	/**
	 * Obtiene la etiqueta de la columna
	 * @author Yamel Senih 27/02/2012, 01:06:27
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getName(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].Name;
		}
		return null;
	}
	
	/**
	 * Obtiene el nombre para mostrar de la columna
	 * @author Yamel Senih 08/04/2012, 18:22:05
	 * @param index
	 * @return
	 * @return String
	 */
	public String getName(int index){
		if(index >= 0){
			return m_columns[index].Name;
		}
		return null;
	}

	/**
	 * Obtiene informacion sobre si es traducida o no
	 * @author Yamel Senih 27/02/2012, 01:07:24
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	/*public boolean getIsTranslated(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsTranslated;
		}
		return false;
	}*/
	
	/**
	 * Obtiene informacion sobre si es encriptada
	 * @author Yamel Senih 27/02/2012, 01:08:04
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isEncrypted(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsEncrypted;
		}
		return false;
	}
	
	/**
	 * Obtiene la longitud de la columna
	 * @author Yamel Senih 27/02/2012, 01:08:52
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getFieldLength(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].FieldLength;
		}
		return -1;
	}
	
	/**
	 * Obtiene el valor minimo de la columna
	 * @author Yamel Senih 27/02/2012, 01:09:37
	 * @param columName
	 * @return
	 * @return String
	 */
	public String getValueMin(String columName){
		int index = getColumnIndex(columName);
		if(index >= 0){
			return m_columns[index].ValueMin;
		}
		return null;
	}
	
	/**
	 * Obtiene si la Columna es SQL
	 * a partir del Nombre de la Columna
	 * @author Yamel Senih 25/07/2012, 17:47:04
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].isColumnSQL();
		}
		return false;
	}
	
	/**
	 * Obtiene si la Columna es SQL
	 * a partir del indice de la Columna
	 * @author Yamel Senih 25/07/2012, 17:47:53
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(int index){
		if(index >= 0){
			return m_columns[index].isColumnSQL();
		}
		return false;
	}
	
	
	/**
	 * Obtiene el valor Maximo de la columna
	 * @author Yamel Senih 27/02/2012, 01:10:09
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getValueMax(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].ValueMax;
		}
		return null;
	}

	/**
	 * Convierte los valores de un parametro value 
	 * a su valor dependiendo del tipo de columna
	 * @author Yamel Senih 27/03/2012, 02:29:02
	 * @param index
	 * @param value
	 * @return
	 * @return Object
	 */
	public Object parseType(int index, Object value){
		if(index >= 0){
			
		}
		return null;
	}
	
	/**
	 * Convierte los valores de un parametro value 
	 * a su valor dependiendo del tipo de columna
	 * @author Yamel Senih 27/03/2012, 02:30:41
	 * @param columName
	 * @param value
	 * @return
	 * @return Object
	 */
	public Object parseType(String columName, Object value){
		int index = getColumnIndex(columName);
		if(index >= 0){
			return m_columns[index].ValueMax;
		}
		return null;
	}
	
	/**
	 * Obtiene el valor de la columna si es Virtual o no
	 * @author Yamel Senih 10/08/2012, 11:19:00
	 * @param ctx
	 * @param infoColumn
	 * @param index
	 * @return
	 * @return String
	 */
	public static String getColumnNameForSelect(Context ctx, POInfo infoColumn, int index){
    	String value = "''";
    	if(!infoColumn.isColumnSQL(index)){
    		value = infoColumn.getTableName() + "." + infoColumn.getColumnName(index);
    	} else {
    		//	Parse SQL Column
    		String columnSQL = Env.parseContext(ctx, infoColumn.getColumnSQL(index), false);
    		value = columnSQL;
    	}
    	
    	return value;
    }
	
	/**
	 * Get PO Info Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/02/2014, 19:35:36
	 * @param columnName
	 * @return
	 * @return POInfoColumn
	 */
	public POInfoColumn getPOInfoColumn(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index];
		}
		return null;
	}
	
	/**
	 * Get PO Info Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/02/2014, 19:36:41
	 * @param index
	 * @return
	 * @return POInfoColumn
	 */
	public POInfoColumn getPOInfoColumn(int index){
		if(index >= 0){
			return m_columns[index];
		}
		return null;
	}
	
	
}
