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
package org.spinsuite.fta.adapters;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class DisplayTFLine {

	/**
	 * Full Constructor
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 20:32:26
	 * @param m_FTA_TechnicalForm_ID
	 * @param m_FTA_TechnicalFormLine_ID
	 * @param m_Category_ID
	 * @param m_FTA_Farm_ID
	 * @param m_Farm
	 * @param m_FTA_FarmDivision_ID
	 * @param m_FarmDivision
	 * @param m_FTA_Farming_ID
	 * @param m_Farming
	 * @param m_FTA_FarmingStage_ID
	 * @param m_FarmingStage
	 * @param m_FTA_ObservationType_ID
	 * @param m_ObservationType
	 * @param m_Comments
	 */
	public DisplayTFLine(int m_FTA_TechnicalForm_ID, int m_FTA_TechnicalFormLine_ID, int m_Category_ID,
			int m_FTA_Farm_ID, String m_Farm, 
			int m_FTA_FarmDivision_ID, String m_FarmDivision,
			int m_FTA_Farming_ID, String m_Farming, 
			int m_FTA_FarmingStage_ID, String m_FarmingStage,
			int m_FTA_ObservationType_ID, String m_ObservationType, 
			String m_Comments) {
		this.m_FTA_TechnicalForm_ID = m_FTA_TechnicalForm_ID;
		this.m_FTA_TechnicalFormLine_ID = m_FTA_TechnicalFormLine_ID;
		this.m_Category_ID = m_Category_ID;
		this.m_FTA_Farm_ID = m_FTA_Farm_ID;
		this.m_Farm = m_Farm;
		this.m_FTA_FarmDivision_ID = m_FTA_FarmDivision_ID;
		this.m_FarmDivision = m_FarmDivision;
		this.m_FTA_Farming_ID = m_FTA_Farming_ID;
		this.m_Farming = m_Farming;
		this.m_FTA_FarmingStage_ID = m_FTA_FarmingStage_ID;
		this.m_FarmingStage = m_FarmingStage;
		this.m_FTA_ObservationType_ID = m_FTA_ObservationType_ID;
		this.m_ObservationType = m_ObservationType;
		this.m_Comments = m_Comments;
	}
	
	/**	Attributes				*/
	private int 	m_FTA_TechnicalForm_ID = 0;
	private int 	m_FTA_TechnicalFormLine_ID = 0;
	private int		m_Category_ID = 0;
	private int 	m_FTA_Farm_ID = 0;
	private String 	m_Farm = null;
	private int 	m_FTA_FarmDivision_ID = 0;
	private String 	m_FarmDivision = null;
	private int 	m_FTA_Farming_ID = 0;
	private String 	m_Farming = null;
	private int 	m_FTA_FarmingStage_ID = 0;
	private String 	m_FarmingStage = null;
	private int 	m_FTA_ObservationType_ID = 0;
	private String 	m_ObservationType = null;
	private String 	m_Comments = null;
	
	/**
	 * Get Technical Form ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:08:35
	 * @return
	 * @return int
	 */
	public int getFTA_TechnicalForm_ID() {
		return m_FTA_TechnicalForm_ID;
	}
	
	/**
	 * Set Technical Form ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:08:50
	 * @param m_FTA_TechnicalForm_ID
	 * @return void
	 */
	public void setFTA_TechnicalForm_ID(int m_FTA_TechnicalForm_ID) {
		this.m_FTA_TechnicalForm_ID = m_FTA_TechnicalForm_ID;
	}
	
	/**
	 * Get Technical Form Line ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:09:01
	 * @return
	 * @return int
	 */
	public int getFTA_TechnicalFormLine_ID() {
		return m_FTA_TechnicalFormLine_ID;
	}
	
	/**
	 * Set Technical Form Line ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:09:11
	 * @param m_FTA_TechnicalFormLine_ID
	 * @return void
	 */
	public void setFTA_TechnicalFormLine_ID(int m_FTA_TechnicalFormLine_ID) {
		this.m_FTA_TechnicalFormLine_ID = m_FTA_TechnicalFormLine_ID;
	}
	
	/**
	 * Get Category
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 20:33:34
	 * @return
	 * @return int
	 */
	public int getCategory_ID() {
		return m_Category_ID;
	}
	
	/**
	 * Get Farm ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:09:22
	 * @return
	 * @return int
	 */
	public int getFTA_Farm_ID() {
		return m_FTA_Farm_ID;
	}
	
	/**
	 * Set Farm ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:09:33
	 * @param m_FTA_Farm_ID
	 * @return void
	 */
	public void setFTA_Farm_ID(int m_FTA_Farm_ID) {
		this.m_FTA_Farm_ID = m_FTA_Farm_ID;
	}
	
	/**
	 * Get Farm Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:09:40
	 * @return
	 * @return String
	 */
	public String getFarm() {
		return m_Farm;
	}
	
	/**
	 * Set Farm Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:09:47
	 * @param m_Farm
	 * @return void
	 */
	public void setFarm(String m_Farm) {
		this.m_Farm = m_Farm;
	}
	
	/**
	 * Get Farm Division ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:09:54
	 * @return
	 * @return int
	 */
	public int getFTA_FarmDivision_ID() {
		return m_FTA_FarmDivision_ID;
	}
	
	/**
	 * Set Farm Division
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:10:07
	 * @param m_FTA_FarmDivision_ID
	 * @return void
	 */
	public void setFTA_FarmDivision_ID(int m_FTA_FarmDivision_ID) {
		this.m_FTA_FarmDivision_ID = m_FTA_FarmDivision_ID;
	}
	
	/**
	 * Get Farm Division Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:10:19
	 * @return
	 * @return String
	 */
	public String getFarmDivision() {
		return m_FarmDivision;
	}
	
	/**
	 * Set Farm Division Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:10:51
	 * @param m_FarmDivision
	 * @return void
	 */
	public void setFarmDivision(String m_FarmDivision) {
		this.m_FarmDivision = m_FarmDivision;
	}
	
	/**
	 * Get Farming ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:11:00
	 * @return
	 * @return int
	 */
	public int getFTA_Farming_ID() {
		return m_FTA_Farming_ID;
	}
	
	/**
	 * Set Farming ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:11:12
	 * @param m_FTA_Farming_ID
	 * @return void
	 */
	public void setFTA_Farming_ID(int m_FTA_Farming_ID) {
		this.m_FTA_Farming_ID = m_FTA_Farming_ID;
	}
	
	/**
	 * Get Farming Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:11:22
	 * @return
	 * @return String
	 */
	public String getFarming() {
		return m_Farming;
	}
	
	/**
	 * Set Farming Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:11:32
	 * @param m_Farming
	 * @return void
	 */
	public void setFarming(String m_Farming) {
		this.m_Farming = m_Farming;
	}
	
	/**
	 * Get Farming Stage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:11:43
	 * @return
	 * @return int
	 */
	public int getFTA_FarmingStage_ID() {
		return m_FTA_FarmingStage_ID;
	}
	
	/**
	 * Set Farming Stage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:11:51
	 * @param m_FTA_FarmingStage_ID
	 * @return void
	 */
	public void setFTA_FarmingStage_ID(int m_FTA_FarmingStage_ID) {
		this.m_FTA_FarmingStage_ID = m_FTA_FarmingStage_ID;
	}
	
	/**
	 * Get Farming Stage Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:12:01
	 * @return
	 * @return String
	 */
	public String getFarmingStage() {
		return m_FarmingStage;
	}
	
	/**
	 * Set Farming Stage Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:12:16
	 * @param m_FarmingStage
	 * @return void
	 */
	public void setFarmingStage(String m_FarmingStage) {
		this.m_FarmingStage = m_FarmingStage;
	}
	
	/**
	 * Get Observation Type ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:12:26
	 * @return
	 * @return int
	 */
	public int getFTA_ObservationType_ID() {
		return m_FTA_ObservationType_ID;
	}
	
	/**
	 * Set Observation Type ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:12:37
	 * @param m_FTA_ObservationType_ID
	 * @return void
	 */
	public void setFTA_ObservationType_ID(int m_FTA_ObservationType_ID) {
		this.m_FTA_ObservationType_ID = m_FTA_ObservationType_ID;
	}
	
	/**
	 * Get Observation Type Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:12:48
	 * @return
	 * @return String
	 */
	public String getObservationType() {
		return m_ObservationType;
	}
	
	/**
	 * Set Observation Type Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:13:01
	 * @param m_ObservationType
	 * @return void
	 */
	public void setObservationType(String m_ObservationType) {
		this.m_ObservationType = m_ObservationType;
	}
	
	/**
	 * Get Comments
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:13:13
	 * @return
	 * @return String
	 */
	public String getComments() {
		return m_Comments;
	}
	
	/**
	 * Set Comments
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 09:13:24
	 * @param m_Comments
	 * @return void
	 */
	public void setComments(String m_Comments) {
		this.m_Comments = m_Comments;
	}
} 