package com.bluemartini.bmunit;

import java.util.Vector;

import junit.framework.TestResult;

/**
 * BMTestResult
 * 
 * @author Yannick Robin
 *
 */

public class BMTestResult extends TestResult {

	private boolean fStop;
	
    public BMTestResult()
    {
    	fFailures = new Vector();
    	fErrors = new Vector();
    	fListeners = new Vector();
    	fRunTests = 0;
    	fStop = false;
    }
}
