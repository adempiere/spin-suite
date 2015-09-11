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
import java.security.spec.InvalidKeySpecException;
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
	private Context				m_ctx;
	/**	Instance				*/
	private static RSACrypt		m_Instance = null;
	/**	Public Key				*/
	private Key					m_PublicKey = null;
	/**	String Key Saved		*/
	private String 				m_KeySaved = null;
	/**	Cipher					*/
	private Cipher				m_Cipher = null;
	/**	Algorithm				*/
	public static final String 	RSA = "RSA";
	/** Standard Key			*/
	public static final String	KEY_FOR_KEY = "#KEY_FOR_KEY";
	
	
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
		initCipher(false);
	}
	
	/**
	 * Decode Text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Text
	 * @return
	 * @return String
	 */
	private String decodeText(String p_Text) {
		if (p_Text == null || p_Text.length() == 0)
			return p_Text;
		boolean isEncrypted = p_Text.startsWith(ENCRYPTEDVALUE_START) && p_Text.endsWith(ENCRYPTEDVALUE_END);
		if (isEncrypted)
			p_Text = p_Text.substring(ENCRYPTEDVALUE_START.length(), p_Text.length()-ENCRYPTEDVALUE_END.length());
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
		return p_Text;
	}
	
	/**
	 * Encode text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Text
	 * @return
	 * @return String
	 */
	private String encodeText(String p_Text) {
		//	Valid Key
		if(m_KeySaved == null) {
			LogM.log(m_ctx, getClass(), Level.WARNING, "Not Key for encrypte");
			return p_Text;
		}
		//	
		byte[] encodedBytes = null;
		String value = p_Text;
		if(value == null) {
			value = "";
		}
		//	Valid Encrypted
		boolean isEncrypted = isEncrypted(value);
		if(isEncrypted)
			return value;
		//	
		try {
			m_Cipher.init(Cipher.ENCRYPT_MODE, m_PublicKey);
			encodedBytes = m_Cipher.doFinal(p_Text.getBytes());
			//	Return
			return ENCRYPTEDVALUE_START + Base64.encodeToString(encodedBytes, Base64.DEFAULT) + ENCRYPTEDVALUE_END;
		} catch (Exception e) {
			LogM.log(Env.getCtx(), getClass(), Level.SEVERE, "Error while Encode text");
		}
		//	Return
		return CLEARVALUE_START + value + CLEARVALUE_END;
	}
	
	/**
	 * Verify if is encrypted
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return
	 * @return boolean
	 */
	public static boolean isEncrypted(String value) {
		if(value == null)
			return false;
		return value.startsWith(ENCRYPTEDVALUE_START) && value.endsWith(ENCRYPTEDVALUE_END);
	}
	
	/**
	 * Generate Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public void initCipher(boolean force) {
		//	
		if(m_Cipher != null
				&& !force)
			return;
		//	Generate
		try {
			m_KeySaved = Env.getContext(m_ctx, KEY_FOR_KEY);
			byte[] keyBytes = null;
			if(m_KeySaved == null) {
				KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
				kpg.initialize(1024);
				KeyPair kp = kpg.genKeyPair();
				Key publicKey = kp.getPublic();
				keyBytes = publicKey.getEncoded();
				//String keyForSave = Base64.encodeToString(keyBytes, Base64.DEFAULT);
				//	Set Key
				//Env.setContext(m_ctx, KEY_FOR_KEY, keyForSave);
			} else {
				keyBytes = Base64.decode(m_KeySaved, Base64.DEFAULT);
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
	
	/**
	 * Get Public Key from String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @return Key
	 */
	public static Key getPublicKeyFromStringEx(String p_Key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		//	Valid null
		if(p_Key == null)
			return null;
		//	Bytes
		byte[] keyBytes = Base64.decode(p_Key, Base64.DEFAULT);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
	    //	Return
	    return keyFactory.generatePublic(spec);
	}
	
	/**
	 * Get Key from String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Key
	 * @return
	 * @return Key
	 */
	public static Key getPublicKeyFromString(String p_Key) {
		try {
			return getPublicKeyFromStringEx(p_Key);
		} catch (NoSuchAlgorithmException e) {
			LogM.log(Env.getCtx(), RSACrypt.class, Level.SEVERE, "Error while get RSA Key", e);
		} catch (InvalidKeySpecException e) {
			LogM.log(Env.getCtx(), RSACrypt.class, Level.SEVERE, "Error while get RSA Key", e);
		}
		//	Default
		return null;
	}
	
	/**
	 * Generate Public Key with Exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @return Key
	 */
	public static Key generatePublicKeyEx() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
		kpg.initialize(1024);
		KeyPair kp = kpg.genKeyPair();
		return kp.getPublic();
	}
	
	/**
	 * Generate Public key, handle exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return Key
	 */
	public static Key generatePublicKey() {
		try {
			return generatePublicKeyEx();
		} catch (NoSuchAlgorithmException e) {
			LogM.log(Env.getCtx(), RSACrypt.class, Level.SEVERE, "Error while generate RSA Key", e);
		}
		//	Default
		return null;
	}

	@Override
	public String encrypt(String value) {
		return encodeText(value);
	}

	@Override
	public String decrypt(String value) {
		if (value == null)
			return null;
		//	
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