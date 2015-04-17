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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

/**
 * 
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class SerializerUtil {
	
	/**
	 * Serialize a Object
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> Mar 24, 2015, 4:08:27 AM
	 * @param object
	 * @return
	 * @throws IOException
	 * @return byte[]
	 */
	public static byte[] serializeObjectEx(Object object) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream objectOS = new ObjectOutputStream(byteArray);
        objectOS.writeObject(object);
        return byteArray.toByteArray();
    }
	
	/**
	 * Serialize a Object and catch Exception
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> Mar 24, 2015, 4:13:32 AM
	 * @param object
	 * @return
	 * @return byte[]
	 */
	public static byte[] serializeObject(Object object) {
		try {
			return serializeObjectEx(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//	Default
		return null;
	}

	/**
	 * Deserialize a Object
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> Mar 24, 2015, 4:09:04 AM
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @return Object
	 */
    public static Object deserializeObjectEx(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
        ObjectInputStream objectIS = new ObjectInputStream(byteArray);
        return objectIS.readObject();
    }
    
    /**
     * Deserialize Object and catch Exception
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> Mar 24, 2015, 4:15:09 AM
     * @param bytes
     * @return
     * @return Object
     */
    public static Object deserializeObject(byte[] bytes) {
    	try {
			return deserializeObjectEx(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	//	Default
    	return null;
    }
    
    /**
     * Get Byte from File
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_FromFile
     * @return
     * @return byte[]
     */
    public static byte[] getFromFile(String p_FromFile) {
		//	For Image
		byte[] bytes = null;
		if(p_FromFile != null) {
			//	Get Destination File
			File destFile = new File(p_FromFile);
			if(destFile.exists()) {
				int size = (int) destFile.length();
				bytes = new byte[size];
				try {
				    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(destFile));
				    buf.read(bytes, 0, bytes.length);
				    buf.close();
				} catch (FileNotFoundException e) {
					LogM.log(Env.getCtx(), SerializerUtil.class, Level.SEVERE, "Error", e);
				} catch (IOException e) {
					LogM.log(Env.getCtx(), SerializerUtil.class, Level.SEVERE, "Error", e);
				}
			}
		}
		//	Default Return
		return bytes;
    }
    
}
