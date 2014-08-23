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
package org.spinsuite.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.spinsuite.adapters.ReportAdapter;
import org.spinsuite.adapters.ReportExportMenuAdapter;
import org.spinsuite.adapters.SearchAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.process.InfoPara;
import org.spinsuite.process.ProcessCtl;
import org.spinsuite.process.ProcessInfo;
import org.spinsuite.process.ProcessInfoLog;
import org.spinsuite.process.ProcessInfoParameter;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.KeyNamePair;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.VLookupCheckBox;
import org.spinsuite.view.lookup.VLookupDateBox;
import org.spinsuite.view.lookup.VLookupSearch;
import org.spinsuite.view.lookup.VLookupSpinner;
import org.spinsuite.view.lookup.VLookupString;
import org.spinsuite.view.report.ReportPrintData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.itextpdf.text.DocumentException;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class V_Process extends Activity {
	
	/**	Adapter					*/
	private SearchAdapter 			logAdapter 		= null;
	/**	Report Adapter			*/
	private ReportAdapter 			reportAdapter	= null;
	/**	Field					*/
	private InfoField 				m_field 		= null;
	/**	View Index Array		*/
	private ArrayList<GridField>	viewList 		= null;
	/**	Parameter				*/
	private ActivityParameter		m_activityParam = null;
	/**	Process Info			*/
	private ProcessInfo				m_pInfo			= null;
	/**	Process Control			*/
	private ProcessCtl 				m_pControl 		= null;
	/**	Row						*/
	private LinearLayout			v_row			= null;
	/**	Header Report			*/
	private LinearLayout			ll_HeaderReport = null;
	/**	Process Parameters		*/
	private LinearLayout			ll_ProcessPara 	= null;
	/**	Main Layout				*/
	private ScrollView				sv_Param 		= null;
	/**	List View				*/
	private ListView				lv_LogReport 	= null;
	/**	Row Parameter			*/
	private LayoutParams 			v_rowParam		= null;
	/**	Table Layout			*/
	private TableLayout				v_tableLayout 	= null;
	/**	Text Description		*/
	private TextView				tv_Description	= null;
	/**	Text Help				*/
	private TextView				tv_Help			= null;
	/**	Text Summary			*/
	private TextView				tv_Summary		= null;
	/**	Item Search				*/
	private MenuItem 				iSearch			= null;
	/**	Item Print Format		*/
	private MenuItem 				iPrintFormat	= null;
	/**	Item Process			*/
	private MenuItem 				iProcess		= null;
	/**	Print Format			*/
	private KeyNamePair[] 			m_PrintFormats	= null;
	/**	Popup Menu				*/
	private PopupMenu				pPrintFormat	= null;
	/**	Is Loaded				*/
	private boolean 				isLoaded		= false;
	/**	Drawer Layout			*/
	private DrawerLayout 			m_DLayout		= null;
	/**	List View with options	*/
    private ListView 				m_DList			= null;
    /**	Toggle					*/
    private ActionBarDrawerToggle 	m_DToggle		= null;
    /**	Flag (Drawer Loaded)	*/
    private boolean 				isDrawerLoaded 	= false;
	
	/**	View Weight				*/
	private static final float 		WEIGHT_SUM 		= 2;
	private static final float 		WEIGHT 			= 1;
	
	/**	Export					*/
	private static final int 		SHARE_FOR	 	= 0;
	private static final int 		EXPORT_TO_PDF 	= 1;
	private static final int 		EXPORT_TO_XLS 	= 2;
	private static final int 		EXPORT_TO_XML 	= 3;
	private static final int 		EXPORT_TO_HTML 	= 4;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_process);
    	//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			m_field = (InfoField) bundle.getParcelable("Field");
			m_activityParam = (ActivityParameter) bundle.getParcelable("Param");
		}
		//	
		if(m_field == null)
			m_field = new InfoField();
		//	
		if(m_activityParam == null)
			m_activityParam = new ActivityParameter();
		//	Get Elements
		ll_HeaderReport	= (LinearLayout) findViewById(R.id.ll_HeaderReport);
		ll_ProcessPara 	= (LinearLayout) findViewById(R.id.ll_ProcessPara);
		tv_Description 	= (TextView) findViewById(R.id.tv_Description);
		tv_Help 		= (TextView) findViewById(R.id.tv_Help);
		sv_Param 		= (ScrollView) findViewById(R.id.sv_Param);
		lv_LogReport 	= (ListView) findViewById(R.id.lv_LogReport);
    	//	Set Parameter
		v_rowParam = new LayoutParams(LayoutParams.MATCH_PARENT, 
    			LayoutParams.MATCH_PARENT, WEIGHT);  
		//	Table Layout
    	v_tableLayout = new TableLayout(this);
    	//	Add View
    	sv_Param.addView(v_tableLayout);
    	//	
    	viewList = new ArrayList<GridField>();
    	//	Title
    	getActionBar().setSubtitle(m_activityParam.getName());
    	//	
		loadProcessInfo();
		//	Load Drawer Option
		if(m_pInfo.isReport()){
			getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	    	//	Load Drawer
			loadDrawerOption();
		}
		//	Load Print Formats
		loadPrintFormat();
	}
	
	/**
     * Load Drawer
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 09:23:22
     * @return void
     */
    protected void loadDrawer(){
    	//	
    	m_DLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //	
    	m_DList = (ListView) findViewById(R.id.left_drawer);
        //	
        m_DLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        m_DList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				//	Selected Option
				onSelectedDrawerOption((DisplayMenuItem) adapter.getItemAtPosition(position));
			}
        });

        m_DToggle = new ActionBarDrawerToggle(this, m_DLayout, 
        		R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            
        	public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        //	Set Toggle
        m_DLayout.setDrawerListener(m_DToggle);
        //	
        isDrawerLoaded = true;
    }
    
    /**
     * Share Report
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 14:42:18
     * @param position
     * @param printData
     * @return void
     */
    private void shareReport(int position, ReportPrintData printData){
		//	Path
		String localPath = null;
    	String type = null;
    	try {
    		if(position == 0){
    			localPath = printData.createPDF();
    			type = "pdf";
    		} else if(position == 1){
    			localPath = printData.createXLS();
    			type = "excel";
    		}
    		//	
    		if(localPath != null) {
    			//	Share
    			Uri sourceUri = Uri.fromFile(new File(localPath));
    			//	
    			Intent shareIntent = new Intent();
    			shareIntent.setAction(Intent.ACTION_SEND);
    			shareIntent.putExtra(Intent.EXTRA_STREAM, sourceUri);
    			shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getText(R.string.msg_Report) 
    					+ " \"" + printData.getInfoReport().getName() + "\"");
    			shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_SharedFromSFAndroid));
    			//	
    			shareIntent.setType("application/" + type);
    			//	
    			startActivity(Intent.createChooser(shareIntent, 
    					getResources().getText(R.string.Action_Share)));
    		}
		} catch (Exception e) {}
    }
    
    /**
     * On Selected Item
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 22:13:32
     * @param item
     * @return void
     */
    protected void onSelectedDrawerOption(DisplayMenuItem item){
    	final ReportPrintData printData = m_pControl.getReportPrintData();
		if(printData == null
				|| !isLoaded)
			return;
		//	Message
		String msg = null;
		//	Path
		String path = null;
		//	App Type
		String appType = null;
		//	
		if(item.getSPS_Menu_ID() == SHARE_FOR){
			new AlertDialog.Builder(this)
	        .setSingleChoiceItems(new String[]{
	        			getString(R.string.msg_SendAsPDF),
	        			getString(R.string.msg_SendAsXLS)
	        			}, 0, null)
	        .setPositiveButton(R.string.msg_Acept, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                dialog.dismiss();
	                int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
	                //	Share Report
	                shareReport(selectedPosition, printData);
	            }
	        }).show();
    	} else if(item.getSPS_Menu_ID() == EXPORT_TO_PDF){
    		try {
    			path = printData.createPDF();
    			appType = "pdf";
			} catch (FileNotFoundException e) {
				LogM.log(getApplicationContext(), getClass(), 
						Level.SEVERE, "Error in Export to PDF:", e);
				msg = getResources().getString(R.string.msg_FileNotFoundException) 
							+ " " + e.getMessage();
			} catch (DocumentException e) {
				LogM.log(getApplicationContext(), getClass(), 
						Level.SEVERE, "Error in Export to PDF:", e);
				msg = getResources().getString(R.string.msg_DocumentException) 
							+ " " + e.getMessage();
			}
    	} else if(item.getSPS_Menu_ID() == EXPORT_TO_XLS){
    		try {
				path = printData.createXLS();
				appType = "vnd.ms-excel";
			} catch (RowsExceededException e) {
				LogM.log(getApplicationContext(), getClass(), 
						Level.SEVERE, "Error in Export to XLS:", e);
				msg = getResources().getString(R.string.msg_RowsExceededException) 
							+ " " + e.getMessage();
			} catch (WriteException e) {
				LogM.log(getApplicationContext(), getClass(), 
						Level.SEVERE, "Error in Export to XLS:", e);
				msg = getResources().getString(R.string.msg_WriteException) 
							+ " " + e.getMessage();
			} catch (IOException e) {
				LogM.log(getApplicationContext(), getClass(), 
						Level.SEVERE, "Error in Export to XLS:", e);
				msg = getResources().getString(R.string.msg_IOException) 
							+ " " + e.getMessage();
			}
    	} else if(item.getSPS_Menu_ID() == EXPORT_TO_XML){
    		
    	} else if(item.getSPS_Menu_ID() == EXPORT_TO_HTML){
    		
    	}
		//	Show Path
		if(path != null){
			try {
				//	Launch Application
				Uri uriPath = Uri.fromFile(new File(path));
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(uriPath, "application/" + appType);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//	Start Activity
				startActivity(intent);
			} catch (ActivityNotFoundException e){
				LogM.log(this, getClass(), Level.WARNING, 
						"Error Launch Application: " + e.getMessage());
			}
		} else if(msg != null){	//	Show Message
			Msg.alertMsg(this, getResources().getString(R.string.msg_Error), msg);
		}
    	//	Close Drawer
    	m_DLayout.closeDrawer(m_DList);
    }
    
    /**
     * Load Drawer Option
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 17:45:08
     * @return void
     */
    private void loadDrawerOption(){
		loadDrawer();
		//	Populate
		ArrayList<DisplayMenuItem> listMenu = new ArrayList<DisplayMenuItem>();
		listMenu.add(new DisplayMenuItem(SHARE_FOR, getResources().getString(R.string.Action_Share), null, "share_m"));
		listMenu.add(new DisplayMenuItem(EXPORT_TO_PDF, getResources().getString(R.string.Action_Export_PDF), null, "pdf_m"));
		listMenu.add(new DisplayMenuItem(EXPORT_TO_XLS, getResources().getString(R.string.Action_Export_XLS), null, "xls_m"));
		listMenu.add(new DisplayMenuItem(EXPORT_TO_XML, getResources().getString(R.string.Action_Export_XML), null, "xml_m"));
		listMenu.add(new DisplayMenuItem(EXPORT_TO_HTML, getResources().getString(R.string.Action_Export_HTML), null, "html_m"));
		
		//	Set Adapter
		ReportExportMenuAdapter mi_adapter = new ReportExportMenuAdapter(this, R.layout.i_activity_menu, true, listMenu);
		mi_adapter.setDropDownViewResource(R.layout.i_activity_menu);
		m_DList.setAdapter(mi_adapter);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isDrawerLoaded){
        	boolean drawerOpen = m_DLayout.isDrawerOpen(m_DList);
        	if(drawerOpen)
        		menu.setGroupVisible(R.id.group_process, false);
        	else {        		
        		if(isLoaded){
        			//	Show Search Menu
        			if((logAdapter != null
            				&& !m_pInfo.isReport())
            				|| (reportAdapter != null
            				&& m_pInfo.isReport()))
        				iSearch.setVisible(true);
        			if(m_pInfo.isReport()){
        				iPrintFormat.setVisible(true);
        			}
				}
    			//	Visible Process
    			iProcess.setVisible(true);
        	}
        }
        return super.onPrepareOptionsMenu(menu);
    }
    
    /**
     * Is Drawer Layout Open
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 10:32:25
     * @return
     * @return boolean
     */
    protected boolean isDrawerLayoutOpen(){
    	if(isDrawerLoaded)
        	return m_DLayout.isDrawerOpen(m_DList);
    	return false;
    }
    
    /**
     * Get Drawer List
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 17:43:59
     * @return
     * @return ListView
     */
    protected ListView getDrawerList(){
    	return m_DList;
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(isDrawerLoaded)
        	m_DToggle.syncState();
    }
    
    

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(isDrawerLoaded)
        	m_DToggle.onConfigurationChanged(newConfig);
    }
	
	/**
	 * Load Process Information
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/03/2014, 22:07:43
	 * @return void
	 */
	private void loadProcessInfo(){
		//	Instance Process
		m_pInfo = new ProcessInfo(getApplicationContext(), m_activityParam, null);
		//	Set Description
		String info = m_pInfo.getDescription();
		if(info != null
				&& info.length() > 0){
			//	Set Info
			tv_Description.setText(info);
			tv_Description.setVisibility(TextView.VISIBLE);
		}
		//	Set Help
		info = m_pInfo.getHelp();
		if(info != null
				&& info.length() > 0){
			//	Set Info
			tv_Help.setText(info);
			tv_Help.setVisibility(TextView.VISIBLE);
		}
		//	Get for Load
		InfoPara[] infoPara = m_pInfo.getProcessInfoPara();
		if(infoPara != null){
	    	//	Add Fields
	    	for(InfoPara pField : infoPara){
	    		//	Add View to Layout
	    		InfoField field = new InfoField(pField);
	    		//	
	    		addView(field);
	    		//	Add To Parameter
	    		if(pField.IsRange){
	    			InfoField field_To = new InfoField(pField);
	    			field_To.ColumnName = field.ColumnName + "_To";
	    			field_To.IsSameLine = true;
	    			field_To.DefaultValue = pField.DefaultValue2;
	    			addView(field_To);
	    		}
	    	}
		}
		//	Add Summary Text
		v_row = new LinearLayout(this);
		v_row.setOrientation(LinearLayout.HORIZONTAL);
		v_row.setWeightSum(WEIGHT_SUM);
		//	
		tv_Summary = new TextView(this);
		tv_Summary.setVisibility(TextView.GONE);
		v_row.addView(tv_Summary, v_rowParam);
		v_tableLayout.addView(v_row);
		//	Instance Process Control
		m_pControl = new ProcessCtl(m_pInfo);
	}
	
	/**
	 * Add View to Process Panel
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/03/2014, 22:10:08
	 * @param field
	 * @return void
	 */
	private void addView(InfoField field){
    	
		boolean isSameLine = field.IsSameLine;
    	boolean isFirst = false;
    	//	Add New Row
		if(isFirst = (v_row == null)
				|| !isSameLine) {
			v_row = new LinearLayout(this);
			v_row.setOrientation(LinearLayout.HORIZONTAL);
			v_row.setWeightSum(WEIGHT_SUM);
		}
    	GridField lookup = null;
		//	Add
		if(DisplayType.isDate(field.DisplayType)){
			lookup = new VLookupDateBox(this, field);
		} else if(DisplayType.isText(field.DisplayType)){
			VLookupString lookupString = new VLookupString(this, field);
			lookup = lookupString;
		} else if(DisplayType.isBoolean(field.DisplayType)){
			lookup = new VLookupCheckBox(this, field);
		} else if(DisplayType.isLookup(field.DisplayType)){
			//	Table Direct
			if(field.DisplayType == DisplayType.TABLE_DIR){
				lookup = new VLookupSpinner(this, field);
			} else if(field.DisplayType == DisplayType.SEARCH){
				lookup = new VLookupSearch(this, field);
			}
		}
		//	is Filled
		if(lookup != null){
			lookup.setLayoutParams(v_rowParam);
			viewList.add(lookup);
			v_row.addView(lookup);
			//	Add Row
			if(!isSameLine
					|| isFirst)
				v_tableLayout.addView(v_row);
		}
    }
	
	/**
	 * Get Values from Parameters
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 17:05:03
	 * @return
	 * @return boolean
	 */
	private boolean getValuesFromParameters(){
    	boolean ok = true;    	
    	//	Parameters
    	ArrayList<ProcessInfoParameter> arrayParameter = new ArrayList<ProcessInfoParameter>();
    	//	
    	ProcessInfoParameter parameter = null;
    	m_pInfo.setParameter(null);
		//	Get Values
    	for (GridField lookup: viewList) {
    		InfoField field = lookup.getField();
    		if((field.IsMandatory)
    				&& lookup.isEmpty()){
    			Msg.alertMustFillField(this, field.Name, lookup.getChildView());
    			//	set ok to false
    			ok = false;
    			break;
    		}
    		//	Get Value
    		Object value = DisplayType.getJDBC_Value(field.DisplayType, lookup.getValue(), true, true);
    		if(!field.IsSameLine) {
    			parameter = new ProcessInfoParameter(field.ColumnName, 
    					value, field.Name, lookup.getDisplayValue(), field.DisplayType);
    		} else {
    			if(parameter != null){
    				parameter.setParameter_To(value);
    				parameter.setInfo_To(field.Name);
    			}
    		}
    		//	Add to Array
    		arrayParameter.add(parameter);
    		//	Set on Context
    	}
    	//	Populate array
    	ProcessInfoParameter[] param = new ProcessInfoParameter[arrayParameter.size()];
    	arrayParameter.toArray(param);
    	//	
    	m_pInfo.setParameter(param);
    	//	Return
    	return ok;
	}
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//	Inflate menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.process, menu);
		//	Get Item
		iSearch = menu.findItem(R.id.action_search);
		//	Print Format
		iPrintFormat = menu.findItem(R.id.action_print_format);
		//	Process
		iProcess = menu.findItem(R.id.action_process);
		//	Search View
		final View searchView = SearchViewCompat.newSearchView(this);
		if (searchView != null) {
			//	Set Back ground Color
			int id = searchView.getContext().getResources()
					.getIdentifier("android:id/search_src_text", null, null);
			EditText searchText = (EditText) searchView.findViewById(id);
			//	Set Parameters
			if(searchText != null)
				searchText.setTextAppearance(this, R.style.TextSearch);
			//	
			SearchViewCompat.setOnQueryTextListener(searchView,
					new OnQueryTextListenerCompat() {
				@Override
				public boolean onQueryTextChange(String newText) {
					String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
					//	Search
					searchLinstView(mFilter);
					return true;
				}
			});
			SearchViewCompat.setOnCloseListener(searchView,
					new OnCloseListenerCompat() {
				@Override
				public boolean onClose() {
					if (!TextUtils.isEmpty(SearchViewCompat.getQuery(searchView))) {
						SearchViewCompat.setQuery(searchView, null, true);
					}
					return true;
				}
                    
			});
			MenuItemCompat.setActionView(iSearch, searchView);
			//	Set Visible
			iSearch.setVisible(false);
			iPrintFormat.setVisible(false);
		}		
		return true;
	}
	    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(isDrawerLoaded) {
    		if (m_DToggle.onOptionsItemSelected(item)) {
                return true;
            }
    	}
		int itemId = item.getItemId();
		if (itemId == R.id.action_process) {
			if(isLoaded){
				ll_ProcessPara.setVisibility(ScrollView.VISIBLE);
				item.setIcon(R.drawable.process_m);
				if(m_pInfo.isReport()){
					iSearch.setVisible(false);
					iPrintFormat.setVisible(false);
				}
				//
				isLoaded = false;
				return true;
			}
			//	Run Process
			if(!getValuesFromParameters())
				return false;
			m_pControl.runProcess();
			tv_Summary.setTextAppearance(this, R.style.TextStandard);
			tv_Summary.setVisibility(TextView.VISIBLE);
			//	If Error
			if(m_pInfo.isError())
				tv_Summary.setTextColor(Color.RED);
			else
				tv_Summary.setTextColor(Color.BLUE);
			//	Set Summary
			tv_Summary.setText(m_pInfo.getSummary());
			//	Show all logs
			if(!m_pInfo.isReport()
					|| m_pInfo.isError()){
				if(!m_activityParam.isFromActivity())
					showLog();
				else 
					setActivityResult();
			}
			//	Show report
			else if(m_pInfo.isReport()){
				iSearch.setVisible(true);
				iPrintFormat.setVisible(true);
				ll_HeaderReport.setVisibility(LinearLayout.VISIBLE);
				showReport(0);
			}
			//	Hide Parameter
			if(!m_pInfo.isError()){
				ll_ProcessPara.setVisibility(ScrollView.GONE);
				item.setIcon(R.drawable.config_m);
				//	Set Is Loaded
				isLoaded = true;
			}
			return true;
		} else if (itemId == R.id.action_print_format) {
			showPrintFormat();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Load Print Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 11:29:18
	 * @return
	 */
	private void loadPrintFormat(){
		String sql = new String("SELECT pf.AD_PrintFormat_ID, pf.Name " +
				"FROM AD_PrintFormat pf " +
				"WHERE pf.IsActive = ? " +
				"AND EXISTS(SELECT 1 " +
				"					FROM AD_Process p " +
				"					LEFT JOIN AD_ReportView rv ON(rv.AD_ReportView_ID = p.AD_ReportView_ID) " +
				"					LEFT JOIN AD_PrintFormat pft ON(pft.AD_PrintFormat_ID = p.AD_PrintFormat_ID) " +
				"					WHERE p.AD_Process_ID = ? " +
				"					AND pf.SPS_Table_ID = COALESCE(rv.SPS_Table_ID, pft.SPS_Table_ID)) " +
				"ORDER BY IsDefault DESC");
		m_PrintFormats = DB.getKeyNamePairs(getApplicationContext(), 
				sql, new String[]{"Y", String.valueOf(m_pInfo.getAD_Process_ID())});
	}
	
	/**
	 * Show Print Formats
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 11:57:26
	 * @return void
	 */
	private void showPrintFormat(){
		View menuItemView = findViewById(R.id.action_print_format);
		pPrintFormat = new PopupMenu(this, menuItemView);
		for(KeyNamePair pFormat: m_PrintFormats){
			pPrintFormat.getMenu().add(Menu.NONE, pFormat.getKey(), 
					Menu.NONE, pFormat.getName());
		}
		//	Listener
		pPrintFormat.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				int m_AD_PrintFormatItem_ID = item.getItemId();
				if(isLoaded){
					showReport(m_AD_PrintFormatItem_ID);
				}
				return false;
			}
		});
		//	Show
		pPrintFormat.show();
	}
	
	/**
	 * Show Process Log
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 18:28:06
	 * @return void
	 */
	private void showLog(){
		ArrayList<DisplayRecordItem> data = new ArrayList<DisplayRecordItem>();
		SimpleDateFormat formatDate = DisplayType.getDateFormat(getApplicationContext());
		//	Get Logs
		ProcessInfoLog[] logs = m_pInfo.getLogs();
		if(logs != null){
			for(ProcessInfoLog log : m_pInfo.getLogs()){
				String strLog = log.getP_Msg();
				//	Add Date
				if(log.getP_Date() != null)
					strLog += " | " + formatDate.format(log.getP_Date());
				//	Number
				if(log.getP_Number() != null)
					strLog += " | " + log.getP_Number().doubleValue();
				//	Add to array
				data.add(new DisplayRecordItem(log.getLog_ID(), strLog.toString()));
			}
		}
		//	Set Adapter
		logAdapter = new SearchAdapter(this, R.layout.i_search, data);
		logAdapter.setDropDownViewResource(R.layout.i_search);
		lv_LogReport.setAdapter(logAdapter);
	}
	
	/**
	 * Show report from data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 20:52:23
	 * @param m_AD_PrintFormat_ID
	 * @return void
	 */
	private void showReport(int m_AD_PrintFormat_ID){
		//	Get Print Data
		ReportPrintData printData = m_pControl.getReportPrintData(m_AD_PrintFormat_ID);
		if(printData != null){
			//	
			reportAdapter = new ReportAdapter(getApplicationContext(), 
					printData.getData(), printData.getColumns(), ll_HeaderReport);
			lv_LogReport.setAdapter(reportAdapter);
		}
	}
	
	/**
	 * Search in Adapters
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 16:35:46
	 * @param filter
	 * @return void
	 */
	private void searchLinstView(String filter){
		if(!isLoaded)
			return;
		if(logAdapter != null
				&& !m_pInfo.isReport()){
			logAdapter.getFilter().filter(filter);
		} else if(reportAdapter != null
				&& m_pInfo.isReport()){
			reportAdapter.getFilter().filter(filter);
		}
	}	
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null){
	    		Bundle bundle = data.getExtras();
	    		DisplayRecordItem item = (DisplayRecordItem) bundle.getParcelable("Record");
	    		String columnName = bundle.getString("ColumnName");
	    		//	if a field or just search
	    		if(columnName != null){
	    			for (GridField lookup: viewList) {
	    	    		if(lookup.getColumnName().equals(columnName)){
	    	    			((VLookupSearch) lookup).setItem(item);
	    	    			break;
	    	    		}
	    			}
	    		}
	    	}
    	}
    }
	
	/**
	 * Set Result to Activity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/04/2014, 14:54:22
	 * @return void
	 */
	private void setActivityResult(){
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putInt(DisplayMenuItem.CONTEXT_ACTIVITY_TYPE, (!m_pInfo.isReport()
				? DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_Process
						: DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_Report));
		bundle.putString("Summary", m_pInfo.getSummary());
		bundle.putBoolean("IsError", m_pInfo.isError());
		intent.putExtras(bundle);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}
