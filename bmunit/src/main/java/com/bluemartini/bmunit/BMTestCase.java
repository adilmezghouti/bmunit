package com.bluemartini.bmunit;

import com.bluemartini.dna.BMException;
import com.bluemartini.dna.DNAList;
import com.bluemartini.server.BMClient;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class BMTestCase extends TestCase {
	
	String name;
	DNAList dnaIn = null;
	DNAList dnaOut = null;
	String bizact = null;
	boolean debug = false;
	String exceptionMessage;
	
	
	public BMTestCase(String name, String bizact, DNAList dnaIn, DNAList dnaOut, boolean debug)
	{
		this.name = name;
		this.dnaIn = dnaIn;
		this.dnaOut = dnaOut;
		this.bizact = bizact;
		this.debug = debug;
	}
    
    public String getName()
    {
    	//method name
    	String name = "[BMUnitTest] " + this.name;
    	return name;
    }
    
    public void run(TestResult testresult)
    {
    	DNAList dnaOut = null;
		try {
			dnaOut = BMClient.executeBusinessAction(bizact, dnaIn, null, "website");
		} catch (BMException bme) {
			if (debug)
				bme.printStackTrace();			
			AssertionFailedError assertFailed = new AssertionFailedError(bme.getMessage());
			testresult.addFailure(this, assertFailed);
		}
		
		if (dnaOut != null)
		{
			if (!isOutputValid(dnaOut))
			{
				AssertionFailedError assertFailed = new AssertionFailedError("\nOutput not valid : " + dnaOut + "\nOutput expected : " + this.dnaOut);
				testresult.addFailure(this, assertFailed);
			}
		}
    }

	private boolean isOutputValid(DNAList dnaOut) {
		int valid = 0;
		try {
			valid = this.dnaOut.compare(dnaOut);
		} catch (BMException e) {
			e.printStackTrace();
		}
		
		if (valid == 0)	
			return true;
		else
			return false;
	}

}
