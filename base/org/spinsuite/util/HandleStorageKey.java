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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Algorithm
	 * @param p_FilePath
	 */
	public HandleStorageKey(String p_Algorithm, String p_FilePath) {
		m_Algorithm = p_Algorithm;
		m_FilePath = p_FilePath;
	}

	/**	Algorithm			*/
	private String 			m_Algorithm = null;
	/**	Path				*/
	private String 			m_FilePath = null;
	
	/**
	 * Load Public Key from File
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return Key
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public Key loadPublicKeyEx() throws IOException, 
						NoSuchAlgorithmException, InvalidKeySpecException {
		//	Get Public Key from file
		File filePKey = new File(m_FilePath);
		FileInputStream fis;
		fis = new FileInputStream(m_FilePath);
		byte[] encodedPKey = new byte[(int) filePKey.length()];
		fis.read(encodedPKey);
		fis.close();
			
		// Generate KeyPair.
		KeyFactory keyF = KeyFactory.getInstance(m_Algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPKey);
		PublicKey publicKey = keyF.generatePublic(publicKeySpec);
		//	Return
		return publicKey;
	}
	
	/**
	 * Load Public Key without throws
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return Key
	 */
	public Key loadPublicKey() {
		try {
			return loadPublicKeyEx();
		} catch (IOException e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "loadPublicKey()", e);
		} catch (NoSuchAlgorithmException e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "loadPublicKey()", e);
		} catch (InvalidKeySpecException e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "loadPublicKey()", e);
		} catch (Exception e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "loadPublicKey()", e);
		}
		//	Default
		return null;
	}
	
	/**
	 * Save Public Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param publicKey
	 * @throws IOException
	 * @return void
	 */
	public void savePublicKeyEx(Key publicKey) throws IOException {
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(m_FilePath);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
	}
	
	/**
	 * Save Public Key without throws
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param publicKey
	 * @return void
	 */
	public void savePublicKey(Key publicKey) {
		try {
			savePublicKeyEx(publicKey);
		} catch (IOException e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "savePublicKey(Key)", e);
		} catch (Exception e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "savePublicKey(Key)", e);
		}
	}
	
}