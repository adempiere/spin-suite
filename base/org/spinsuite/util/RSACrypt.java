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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.util;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.logging.Level;

import javax.crypto.Cipher;

import android.util.Base64;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 5, 2015, 12:47:41 AM
 *
 */
public class RSACrypt {
	
	/**	Private Key				*/
	private Key				m_PrivateKey = null;
	/**	Public Key				*/
	private Key				m_PublicKey = null;
	/**	Algorithm				*/
	private final String 	RSA = "RSA";
	
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public RSACrypt() {
		generateKeys();
	}
	
	/**
	 * Decode Text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Text
	 * @return
	 * @return String
	 */
	public String decodeText(String p_Text) {
		byte[] decodedBytes = null;
		try {
			Cipher c = Cipher.getInstance(RSA);
			c.init(Cipher.DECRYPT_MODE, m_PublicKey);
			decodedBytes = c.doFinal(p_Text.getBytes());
			//	Return
			return new String(decodedBytes);
		} catch (Exception e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "Error while Decode text");
		}
		//	Return
		return null;
	}
	
	/**
	 * Encode text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Text
	 * @return
	 * @return String
	 */
	public String encodeText(String p_Text) {
		byte[] encodedBytes = null;
		try {
			Cipher c = Cipher.getInstance("RSA");
			c.init(Cipher.ENCRYPT_MODE, m_PrivateKey);
			encodedBytes = c.doFinal(p_Text.getBytes());
			//	Return
			return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
		} catch (Exception e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "Error while Encode text");
		}
		//	Return
		return null;
	}
	
	/**
	 * Generate Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void generateKeys() {
		//	
		if(m_PublicKey != null)
			return;
		//	Generate
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(1024);
			KeyPair kp = kpg.genKeyPair();
			m_PublicKey = kp.getPublic();
			m_PrivateKey = kp.getPrivate();		
		} catch (Exception e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "Error while generate RSA Key");
		}
	}
	
}