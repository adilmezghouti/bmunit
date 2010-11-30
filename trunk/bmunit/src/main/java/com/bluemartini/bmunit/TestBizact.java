package com.bluemartini.bmunit;

import com.bluemartini.dna.BusinessObject;
import com.bluemartini.dna.DNAList;
import com.bluemartini.server.BusinessAction;

public class TestBizact extends BusinessAction {

	public DNAList execute(DNAList dnaIn)
	{
        BusinessObject boLogin = dnaIn.getBusinessObject("LOGIN");
        String userid = boLogin.getString("userid");
        DNAList dnaOut = new DNAList();
        dnaOut.setBoolean("validLogin", (boLogin != null));
        return dnaOut;
	}
}
