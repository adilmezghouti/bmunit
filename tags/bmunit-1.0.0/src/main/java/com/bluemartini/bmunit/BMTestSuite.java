package com.bluemartini.bmunit;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.bluemartini.dna.BMContext;
import com.bluemartini.dna.BMException;
import com.bluemartini.dna.DNAList;
import com.bluemartini.dna.DNAListIterator;
import com.bluemartini.util.MainApp;

/**
 * This class loads bmunittestcases.dna, it creates a test suite
 * and add each test case defined in the file to this suite. 
 * 
 * @author Yannick Robin
 *
 */

public class BMTestSuite extends MainApp {
	
	boolean debug = false;
	
	public BMTestSuite()
	{
		super("BMTestSuite", "bmunit.appconfig.dna");
		
    	String[] args = new String[0];
	    super.init(args);
	    
	    try {
			run();
		} catch (BMException e) {
			e.printStackTrace();
		}

	}
	
    protected void run() throws BMException
    {
    	
    }
    
    public void setDebug()
    {
    	debug = true;
    }
    
	public static Test suite() {
		BMTestSuite bmTestSuite = new BMTestSuite();
		return bmTestSuite.runSuite();
	}

    
	public Test runSuite() {
		
		TestSuite suite = new TestSuite("Blue Martini Unit Test");
		
	    StringBuffer sb = new StringBuffer();
	    DNAList dnaConfig = BMContext.loadAndMergeDNAsFromModules("bmunittestcases.dna", sb);
	    DNAListIterator iter = dnaConfig.iterator();
		
	    String name;
        while ((name = iter.nextName()) != null) {
        	DNAList dnaTemp = iter.getList();
        	String bizact = dnaTemp.getString("bizact");
        	DNAList dnaIn = dnaTemp.getList("input");
        	DNAList dnaOut = dnaTemp.getList("output");
        	
        	BMTestCase bmTestCase = new BMTestCase(name, bizact, dnaIn, dnaOut, debug);
    		suite.addTest(bmTestCase);
        }
		
		return suite;
	}

}