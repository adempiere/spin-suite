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

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.util.logging.Level;

import javax.crypto.Cipher;

import android.content.Context;
import android.util.Base64;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 5, 2015, 12:47:41 AM
 *
 */
public class RSACrypt implements SecureInterface {
	
	/**	Context					*/
	private Context			m_ctx;
	/**	Instance				*/
	private static RSACrypt	m_Instance = null;
	/**	Public Key				*/
	private Key				m_PublicKey = null;
	/**	Cipher					*/
	private Cipher			m_Cipher = null;
	/**	Algorithm				*/
	private final String 	RSA = "RSA";
	/** Standard Key			*/
	private String			KEY_FOR_KEY = "#KEY_FOR_KEY";
	
	
	/**
	 * Create Instance
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return RSACrypt
	 */
	public static RSACrypt getInstance(Context p_Ctx) {
		if(m_Instance == null) {
			m_Instance = new RSACrypt(p_Ctx);
		}
		//	Default Return
		return m_Instance;
	}
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 */
	private RSACrypt(Context p_Ctx) {
		m_ctx = p_Ctx;
		initCipher();
	}
	
	/**
	 * Decode Text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Text
	 * @return
	 * @return String
	 */
	private String decodeText(String p_Text) {
//		byte[] decodedBytes = null;
//		try {
//			m_Cipher.init(Cipher.DECRYPT_MODE, m_PublicKey);
//			decodedBytes = m_Cipher.doFinal(p_Text.getBytes());
//			//	Return
//			return new String(decodedBytes);
//		} catch (Exception e) {
//			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "Error while Decode text");
//		}
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
	private String encodeText(String p_Text) {
		byte[] encodedBytes = null;
		try {
			m_Cipher.init(Cipher.ENCRYPT_MODE, m_PublicKey);
			encodedBytes = m_Cipher.doFinal(p_Text.getBytes());
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
	private void initCipher() {
		//	
		if(m_Cipher != null)
			return;
		//	Generate
		try {
			String keySaved = Env.getContext(m_ctx, KEY_FOR_KEY);
			byte[] keyBytes = null;
			if(keySaved == null) {
				KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
				kpg.initialize(1024);
				KeyPair kp = kpg.genKeyPair();
				Key publicKey = kp.getPublic();
				keyBytes = publicKey.getEncoded();
				String keyForSave = Base64.encodeToString(keyBytes, Base64.DEFAULT);
				//	Set Key
				Env.setContext(m_ctx, KEY_FOR_KEY, keyForSave);
			} else {
				keyBytes = Base64.decode(keySaved, Base64.DEFAULT);
			}
			//	Decode
		    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		    m_PublicKey = keyFactory.generatePublic(spec);
			m_Cipher = Cipher.getInstance(RSA);
		} catch (Exception e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "Error while generate RSA Key");
		}
	}

	@Override
	public String encrypt(String value) {
		return encodeText(value);
	}

	@Override
	public String decrypt(String value) {
		return decodeText(value);
	}

	@Override
	public Integer encrypt(Integer value) {
		//	not yet implemented
		return null;
	}

	@Override
	public Integer decrypt(Integer value) {
		//	not yet implemented
		return null;
	}

	@Override
	public BigDecimal encrypt(BigDecimal value) {
		//	not yet implemented
		return null;
	}

	@Override
	public BigDecimal decrypt(BigDecimal value) {
		//	not yet implemented
		return null;
	}

	@Override
	public Timestamp encrypt(Timestamp value) {
		//	not yet implemented
		return null;
	}

	@Override
	public Timestamp decrypt(Timestamp value) {
		//	not yet implemented
		return null;
	}

	@Override
	public String getDigest(String value) {
		//	not yet implemented
		return null;
	}

	@Override
	public boolean isDigest(String value) {
		//	not yet implemented
		return false;
	}

	@Override
	public String getSHA512Hash(int iterations, String value, byte[] salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//	not yet implemented
		return null;
	}
}