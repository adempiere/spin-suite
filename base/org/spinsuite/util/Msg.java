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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import org.spinsuite.base.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

/**
 * @author Yamel Senih
 *
 */
public class Msg {

	/**
	 * Show Alert message
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:02:54
	 * @param ctx
	 * @param title
	 * @param msg
	 * @return void
	 */
	public static void alertMsg(Context ctx, String title, String msg){
		Builder dialog = new AlertDialog.Builder(ctx);
		dialog.setPositiveButton(ctx.getResources().getString(R.string.msg_Acept), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.show();
	}
	
	/**
	 * Show a dialog with error message, must fill field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:02:20
	 * @param ctx
	 * @param label
	 * @param field
	 * @return void
	 */
	public static void alertMustFillField(Context ctx, String label, View field){
		Msg.alertMsg(ctx, ctx.getResources().getString(R.string.msg_ValidError), 
				ctx.getResources().getString(R.string.MustFillField) + 
				" " + label);
		if(field != null)
			field.requestFocus();
	}
	
	/**
	 * Show a dialog with error message, must fll field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:08:24
	 * @param ctx
	 * @param field_ID
	 * @param field
	 * @return void
	 */
	public static void alertMustFillField(Context ctx, int field_ID, View field){
		Msg.alertMsg(ctx, ctx.getResources().getString(R.string.msg_ValidError), 
				ctx.getResources().getString(R.string.MustFillField) + 
				" " + ctx.getResources().getString(field_ID));
		if(field != null)
			field.requestFocus();
	}
	
	/**
	 * Show a confirm dialog
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:03:08
	 * @param ctx
	 * @param msg
	 * @return
	 * @return Builder
	 */
	public static Builder confirmMsg(Context ctx, String msg) {
		return confirmMsg(ctx, ctx.getResources().getString(R.string.msg_Ask), msg);
	}
	
	/**
	 * Show confirm dialog with title
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:05:01
	 * @param ctx
	 * @param title
	 * @param msg
	 * @return
	 * @return Builder
	 */
	public static Builder confirmMsg(Context ctx, String title, String msg){
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setMessage(msg);
		//	
		builder.setNegativeButton(ctx.getResources().getString(R.string.msg_Cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		//	Return
		return builder;
	}
	
	/**
	 * Show toast message
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:05:41
	 * @param ctx
	 * @param msg
	 * @return void
	 */
	public static void toastMsg(Context ctx, String msg){
		Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * Lanza un mensaje en la barra de notificaciones de android y vuelve a actividad actual
	 * @author Carlos Parada 03/11/2012, 01:53:16
	 * @param ctx
	 * @param icon
	 * @param systemMsg
	 * @param tittleMsg
	 * @param bodyMsg
	 * @param notificationID
	 * @param activityReturn

	 * @return
	 * @return NotificationManager
	 */
	/*public static NotificationManager notificationMsg(Context ctx,int icon,String systemMsg,String tittleMsg,String bodyMsg,int notificationID,Intent currentIntent)
	{
		return notificationMsg(ctx,icon,systemMsg,tittleMsg,bodyMsg,notificationID,null,currentIntent);
	}*/
	/**
	 * Lanza un mensaje en la barra de notificaciones de android y vuelve a actividad nueva
	 * @author Carlos Parada 03/11/2012, 01:53:16
	 * @param ctx
	 * @param icon
	 * @param systemMsg
	 * @param tittleMsg
	 * @param bodyMsg
	 * @param notificationID
	 * @param activityReturn

	 * @return
	 * @return NotificationManager
	 */
	/*public static NotificationManager notificationMsg(Context ctx,int icon,String systemMsg,String tittleMsg,String bodyMsg,int notificationID,Class activityReturn)
	{
		return notificationMsg(ctx,icon,systemMsg,tittleMsg,bodyMsg,notificationID,activityReturn,null);
	}*/
	/**
	 * Lanza un mensaje en la barra de notificaciones de android
	 * @author Carlos Parada 05/08/2012, 08:55:06
	 * @param ctx
	 * @param icon
	 * @param systemMsg
	 * @param tittleMsg
	 * @param bodyMsg
	 * @param notificationID
	 * @param activityReturn
	 * @param currentIntent
	 * @return
	 * @return NotificationManager
	 */
	/*public static NotificationManager notificationMsg(Context ctx,int icon,String systemMsg,String tittleMsg,String bodyMsg,int notificationID,Class activityReturn,Intent currentIntent)
	{
		PendingIntent contIntent=null ;
		Msg.notification_ID=notificationID;
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notManager = (NotificationManager) ctx.getSystemService(ns);
		 
		Msg.notification= new Notification(icon, systemMsg, System.currentTimeMillis());
		
		if (activityReturn!=null)
		{	
		Intent notIntent = new Intent(ctx,activityReturn);
		
		contIntent = PendingIntent.getActivity(
				ctx, 0, notIntent, PendingIntent.FLAG_ONE_SHOT);
		}
		else if (currentIntent!=null)
			contIntent = PendingIntent.getActivity(
					ctx, 0, currentIntent, PendingIntent.FLAG_ONE_SHOT);
		
		
		Msg.notification.setLatestEventInfo(ctx, tittleMsg, bodyMsg, contIntent);
		//Borra el mensaje de notificacion al seleccionarlo 
		Msg.notification.flags|=Msg.notification.FLAG_AUTO_CANCEL;
		//Iluminacion del Telefono
		Msg.notification.flags|=Msg.notification.DEFAULT_LIGHTS;
		//Vibracion al Momento de lanzar la notificación
		Msg.notification.flags|=Msg.notification.DEFAULT_VIBRATE;
		
		notManager.notify(Msg.notification_ID, Msg.notification);
		
		return notManager;
	}*/
	/**
	 * Lanza un mensaje en la barra de notificacion mediante una vista personalizada y retorna a una ventana nueva
	 * @author Carlos Parada 03/11/2012, 03:24:43
	 * @param ctx
	 * @param icon
	 * @param systemMsg
	 * @param notificationID
	 * @param activityReturn
	 * @param contentView
	 * @return
	 * @return NotificationManager
	 */
	/*public static NotificationManager notificationMsg(Context ctx,int icon,String systemMsg,int notificationID,Class activityReturn,RemoteViews contentView)
	{
		return notificationMsg(ctx,icon,systemMsg,notificationID,activityReturn,null,contentView);
	}*/
	/**
	 * Lanza un mensaje en la barra de notificacion mediante una vista personalizada y retorna a la ventana actual
	 * @author Carlos Parada 03/11/2012, 03:28:33
	 * @param ctx
	 * @param icon
	 * @param systemMsg
	 * @param notificationID
	 * @param currentIntent
	 * @param contentView
	 * @return
	 * @return NotificationManager
	 */
	/*public static NotificationManager notificationMsg(Context ctx,int icon,String systemMsg,int notificationID,Intent currentIntent,RemoteViews contentView)
	{
		return notificationMsg(ctx,icon,systemMsg,notificationID,null,currentIntent,contentView);
	}*/
	/**
	 * Lanza un mensaje en la barra de notificacion mediante una vista personalizada
	 * @author Carlos Parada 05/08/2012, 18:55:37
	 * @param ctx
	 * @param icon
	 * @param systemMsg
	 * @param notificationID
	 * @param activityReturn
	 * @param contentView
	 * @return
	 * @return NotificationManager
	 */
	/*public static NotificationManager notificationMsg(Context ctx,int icon,String systemMsg,int notificationID,Class activityReturn,Intent currentIntent,RemoteViews contentView)
	{
		Intent notIntent =null;
		Msg.notification_ID=notificationID;
		Msg.contentView=contentView;
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notManager = (NotificationManager) ctx.getSystemService(ns);
		 
		Msg.notification= new Notification(icon, systemMsg, System.currentTimeMillis());
		Msg.notification.contentView=Msg.contentView;
		if (activityReturn!=null)
			notIntent = new Intent(ctx,activityReturn);
		else
			notIntent=currentIntent;
		 
		PendingIntent contIntent = PendingIntent.getActivity(ctx, 0, notIntent, PendingIntent.FLAG_ONE_SHOT);
		Msg.notification.contentIntent = contIntent;
		
		//Borra el mensaje de notificacion al seleccionarlo 
		//Msg.notification.flags|=Msg.notification.FLAG_AUTO_CANCEL;
		//Iluminacion del Telefono
		Msg.notification.flags|=Msg.notification.DEFAULT_LIGHTS;
		//Vibracion al Momento de lanzar la notificación
		Msg.notification.flags|=Msg.notification.DEFAULT_VIBRATE;
		notManager.notify(Msg.notification_ID, Msg.notification);
		
		return notManager;
	}*/


	//Nro Identificador de la notificación
	//public static int notification_ID;
	//Objeto de Notificación 
	//public static Notification notification;
	//Vista remota de Notificacion
	//public static RemoteViews contentView;
	
	
}
