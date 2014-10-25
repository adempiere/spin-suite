package org.spinsuite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.spinsuite.base.DB;
import org.spinsuite.util.LogM;
import android.content.Context;
import android.database.Cursor;

public class MSPSSyncMenu extends X_SPS_SyncMenu{

	public MSPSSyncMenu(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
		// TODO Auto-generated constructor stub
	}
	
	public MSPSSyncMenu(Context ctx, int SPS_SyncMenu_ID, DB conn) {
		super(ctx, SPS_SyncMenu_ID, conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 11/02/2014, 23:42:07
	 * @param p_ParentNode
	 * @param p_CurrentItems
	 * @return
	 * @return List<MSFASyncMenu>
	 */
	public static List<MSPSSyncMenu> getNodes(Context ctx,String p_ParentNode,String p_WebServiceDefinitionValue,String p_WebServiceMethodValue,String p_WebServiceTypeValue, DB conn) {
		
		boolean handleConnection = false;
		//		Connection
		if(conn == null){
			conn = new DB(ctx);
			handleConnection = true;
		} else if(!conn.isOpen()){
			handleConnection = true;
		}
		conn.openDB(DB.READ_ONLY);
		String sql = new String();
		/*DB conn = new DB(ctx);
		conn.openDB(DB.READ_ONLY);*/
		Cursor rs = null;
		List<MSPSSyncMenu> items = new ArrayList<MSPSSyncMenu>();
				
		sql= "SELECT treend.Parent_ID, " + // 0
				"treend.Node_ID, " + //1
				"treend.SeqNo, " + //2
				"CASE WHEN Qty IS NULL THEN 'N' ELSE 'Y' END As HasNodes, " + //3
				"wst.Value As ValueType, " + //4
				"wsm.Value As ValueMethod " + //5
			"FROM " +
			"AD_Tree tree " + 
			"INNER JOIN AD_Table tab ON tree.AD_Table_ID = tab.AD_Table_ID " +
			"INNER JOIN AD_TreeNode treend On treend.AD_Tree_ID = tree.AD_Tree_ID "+
			"LEFT JOIN (SELECT Count(1) Qty,Parent_ID,AD_Tree_ID FROM AD_TreeNode GROUP BY Parent_ID,AD_Tree_ID) hasnodes ON hasnodes.Parent_ID=treend.Node_ID AND hasnodes.AD_Tree_ID=treend.AD_Tree_ID " +
			"INNER JOIN SPS_SyncMenu sm ON treend.Node_ID = sm.SPS_SyncMenu_ID " +
			"INNER JOIN WS_WebService ws ON sm.WS_WebService_ID = ws.WS_WebService_ID " +
			"LEFT JOIN WS_WebServiceType wst ON sm.WS_WebServiceType_ID = wst.WS_WebServiceType_ID " +
			"LEFT JOIN WS_WebServiceMethod wsm ON sm.WS_WebServiceMethod_ID = wsm.WS_WebServiceMethod_ID OR wst.WS_WebServiceMethod_ID = wsm.WS_WebServiceMethod_ID " +
			"WHERE tab.TableName = ? AND treend.Parent_ID = ? AND ws.Value = ? AND sm.IsActive ='Y'" +
			"ORDER By treend.SeqNo ";
		
		try{
			
			rs = conn.querySQL(sql, new String[]{MSPSSyncMenu.Table_Name, p_ParentNode, p_WebServiceDefinitionValue});
			
			if(rs.moveToFirst()){
	    		do{
					MSPSSyncMenu item = new MSPSSyncMenu(ctx, rs.getInt(1), conn);
					
					if (rs.getString(3).equals("Y")){
							items.addAll(getNodes(ctx,rs.getString(1),p_WebServiceDefinitionValue,p_WebServiceMethodValue,p_WebServiceTypeValue,conn));					
					}
					else{
						if ((item.getWS_WebServiceType_ID()!=0 
								&& p_WebServiceTypeValue!=null
									&& p_WebServiceTypeValue.equals(rs.getString(4)))
							||
							(p_WebServiceTypeValue==null 
								&& p_WebServiceMethodValue!=null
									&&  p_WebServiceMethodValue.equals(rs.getString(5))))						
								items.add(item);
	
						
					}
	    		} while(rs.moveToNext());
			}
		}catch (Exception e){
			LogM.log(ctx, MSPSSyncMenu.class, Level.SEVERE, e.getLocalizedMessage(), e.getCause());
		}
		finally{
			if(handleConnection)
				DB.closeConnection(conn);
		}
		
		return items;
	}//getNodes


}
