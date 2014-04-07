/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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

import java.io.File;

import android.content.Context;
import android.os.Environment;

/*import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfWriter;*/

/**
 * @author Yamel Senih
 *
 */
public class CreatePDFTest {

	//private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	
	public static void GenerarPDF(Context ctx){
        //
        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        if (!root.exists()) {
            root.mkdirs();
        }
        File gpxfile = new File(root, "generando.pdf");
        Msg.toastMsg(ctx, "gpxfile " + gpxfile);
        try {
        	
        	/*Document document = new Document(PageSize.LETTER,20,20,20,20);
			PdfWriter.getInstance(document,new FileOutputStream(gpxfile));
			document.open();
			document.addTitle("Hola");
			document.addAuthor("SFAndroid");
			document.addCreator("SFAndroid");
	        document.add(new Paragraph("Epale", catFont));
	        document.close();
	        //HandlerFileShare.sendMail(ctx, new String[]{"ysenih@erpconsultoresyasociados.com"}, "Adjunto", "Nada", gpxfile, "Enviar por", "test/plain");
        	 */
		} catch (Exception e) {
			Msg.alertMsg(ctx, "Error", " - " + e.getMessage());
		}
	} 
}
