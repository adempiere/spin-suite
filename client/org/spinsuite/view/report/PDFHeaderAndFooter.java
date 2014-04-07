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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.view.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class PDFHeaderAndFooter extends PdfPageEventHelper {
	
	/**	Template 			*/
	//private PdfTemplate 	total = null;
	
	public void onOpenDocument(PdfWriter writer, Document document) {
        //total = writer.getDirectContent().createTemplate(30, 16);
    }
	
	public void onStartPage (PdfWriter writer, Document document) {
		/*Paragraph title = new Paragraph("Page " + (writer.getPageNumber()));
		title.setAlignment(Paragraph.ALIGN_RIGHT);
		title.setLeading(fixedLeading)
		try {
			document.add(title);
		} catch (DocumentException e) {
			Log.e("////", "//", e);
		}*/
		
		//ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("even header"),
          //      0, 0, 0);
		//Rectangle rect = writer.getBoxSize("art");
	    //switch(writer.getPageNumber() % 2) {
	    //case 0:
	        //ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("even header"),
	                //rect.getBorderWidthRight(), rect.getBorderWidthTop(), 0);
	      //  break;
	    //case 1:
	      //  ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, 
	        //		new Phrase(String.format("%d", writer.getPageNumber())),
	          //      300f, 62f, 0);
	        //break;
	    //}
		//Log.e("*-******", "***************");
	}
	
	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		//super.onCloseDocument(writer, document);
		/*ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                2, 2, 0);*/
		/*ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                 new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                 2, 2, 0);
		*/
	}
}
