package org.spinsuite.interfaces;

public interface BackGroundProcess {

	/** Process State
	 * 0 - Not Initialize
	 * 1 - Beginning
	 * 2 - Running
	 * 3 - Ending
	 * */ 
	public int processState = 0;
	
	public static String MethodRunName="run";
	
	/** Publish Before Initialize Process*/
	public void publishBeforeInit();
	
	/** Publish On Running*/ 
	public void publishOnRunning();
	
	/** Publish After End*/ 
	public void publishAfterEnd();
	
	/** Run Process*/
	public Object run(Object... params);
	
}
