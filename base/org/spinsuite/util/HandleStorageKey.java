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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 18, 2015, 12:50:07 AM
 *
 */
public class HandleStorageKey {
	
	/**
	 * Load Public Key from File
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Algorithm
	 * @param p_Input
	 * @return
	 * @return Key
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public static Key loadPublicKeyEx(String p_Algorithm, InputStream p_Input) throws IOException, 
						NoSuchAlgorithmException, InvalidKeySpecException {
		//	Get Public Key from file
		byte[] encodedPKey = new byte[(int) p_Input.available()];
		p_Input.read(encodedPKey);
		p_Input.close();
			
		// Generate KeyPair.
		KeyFactory keyF = KeyFactory.getInstance(p_Algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPKey);
		PublicKey publicKey = keyF.generatePublic(publicKeySpec);
		//	Return
		return publicKey;
	}
	
	/**
	 * Load Public Key without throws
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Algorithm
	 * @param p_Input
	 * @return
	 * @return Key
	 */
	public static Key loadPublicKey(String p_Algorithm, InputStream p_Input) {
		try {
			return loadPublicKeyEx(p_Algorithm, p_Input);
		} catch (IOException e) {
			LogM.log(Env.getCtx(), HandleStorageKey.class, Level.SEVERE, "loadPublicKey()", e);
		} catch (NoSuchAlgorithmException e) {
			LogM.log(Env.getCtx(), HandleStorageKey.class, Level.SEVERE, "loadPublicKey()", e);
		} catch (InvalidKeySpecException e) {
			LogM.log(Env.getCtx(), HandleStorageKey.class, Level.SEVERE, "loadPublicKey()", e);
		} catch (Exception e) {
			LogM.log(Env.getCtx(), HandleStorageKey.class, Level.SEVERE, "loadPublicKey()", e);
		}
		//	Default
		return null;
	}
	
	/**
	 * Save Public Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_PublicKey
	 * @param p_FilePath
	 * @throws IOException
	 * @return void
	 */
	public static void savePublicKeyEx(Key p_PublicKey, String p_FilePath) throws IOException {
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				p_PublicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(p_FilePath);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
	}
	
	/**
	 * Save Public Key without throws
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_PublicKey
	 * @param p_FilePath
	 * @return void
	 */
	public static void savePublicKey(Key p_PublicKey, String p_FilePath) {
		try {
			savePublicKeyEx(p_PublicKey, p_FilePath);
		} catch (IOException e) {
			LogM.log(Env.getCtx(), HandleStorageKey.class, Level.SEVERE, "savePublicKey(Key)", e);
		} catch (Exception e) {
			LogM.log(Env.getCtx(), HandleStorageKey.class, Level.SEVERE, "savePublicKey(Key)", e);
		}
	}
	
}