
---

Update 6/3/2011: Project temporarily unavailable

---


# What is BMUnit? #

To test Blue Martini applications using JUnit, you need all the application context (configuration files, database access, etc...), the goal of this project is to make this configuration simpler.<br />

For automatic testing, we can imagine the following techniques:
  * Loading Blue Martini container inside the test runner but it appears to be painful to configure and slow as the initialization could be quite long.
  * Web testing by using an HttpUnit-like or Selenium. It is a very good approach for automatic testing but it is adapted to acceptance test only and it does not work for unit test.

BMUnit proposes an intermediate approach. Usually for automated testing, the most important layer to test is neither the view nor the model but the service layer meaning in BM language, core business actions. BMUnit loads a light BM command line application context in order to be able to execute remote business actions. Thus the code is tested directly on the WebSite server. You don't need to use any Java code for your testing, BMUnit provides a configuration file to define your test suite where you indicate all business actions to test with the input/output expected for a successful test.

# Releases #

  * [bmunit-1.0.0-bin.zip](http://code.google.com/p/bmunit/downloads/detail?name=bmunit-1.0.0-bin.zip): Tested with BM 10.1.1 and WebSphere 6

# Getting started #

  * Download `bmunit-<major>.<minor>.<inc>-bin.zip` and unzip to `<BMS_HOME>`
  * Build the test suite by adding test cases in `<BMS_HOME>/core/config/bmunit/bmunittestcases.dna`. For each test case, indicate the following information:
    * bizact: The business action name you want to test (as indicated in bizact.dna)
    * input: The DNA input send to the business action
    * output: The DNA output that will be compared with the DNA response of the business action in order to validate the test

Here is some examples, (`Ping` is a core business action defined by default in Blue Martini and `TestBizact` is a BMUnit core business action defined):

```
DNA {
	"Ping" DNA {
		"bizact" String "Ping",
		"input" DNA {
		},
		"output" DNA {
		}
	},
	"Incorrect input" DNA {
		"bizact" String "TestBizact",
		"input" DNA {
			"userid" String "blue",
			"password" String "martini"
		},
		"output" DNA {
		    "validLogin" Boolean "true"
		}
	},
	"Incorrect output" DNA {
		"bizact" String "TestBizact",
		"input" DNA {
		    "LOGIN" BO(USER_ACCOUNT) {
		        "userid" String "blue",
		        "password" String "martini"
		    }
		},
		"output" DNA {
		    "validLogin" Boolean "false"
		}
	},	
	"Valid" DNA {
		"bizact" String "TestBizact",
		"input" DNA {
		    "LOGIN" BO(USER_ACCOUNT) {
		        "userid" String "blue",
		        "password" String "martini"
		    }
		},
		"output" DNA {
		    "validLogin" Boolean "true"
		}
	}
}
```
  * If you use `TestBizact`, add bmunit to your module list and restart website

# Using it with Eclipse #
The following instructions are for Eclipse using WebSphere. Please adapt it for WebLogic or JBoss.
  * Ensure your website is running
  * Configure a new JUnit test associated to com.bluemartini.bmunit.BMTestSuite

http://bmunit.googlecode.com/svn/trunk/bmunit/wiki/images/bmunit1.PNG

  * Configure the classpath
    * BM libraries
      * `<BMS_HOME>/core/classes/martini.jar`
      * `<BMS_HOME>/core/classes/bmunit-1.0-SNAPSHOT.jar`
      * `<BMS_HOME>/thirdparty/classes/oramatcher2.0.2.jar`
      * `<BMS_HOME>/thirdparty/classes/bcprov-jdk14-136.jar`
    * WebSphere libraries
      * `<WAS_HOME>/lib/bootstrap.jar`
      * `<WAS_HOME>/java/lib/tools.jar`
      * `<WAS_HOME>/runtimes.jar`
  * Setup IBM JRE
    * In the JRE tab, click on Installed JRE
    * Add > Standard
      * JRE\_HOME=`<WAS_HOME>/java/jre`
      * JRE\_NAME=`jreWAS61`
  * Configure the following environment variables adapted to your machine
    * BMS\_HOME = `<BMS_HOME>`
    * BMS\_CONFIG\_HOME = `${BMS_HOME`}
    * BMS\_CONFIG\_DIR = `core/config`
    * BMS\_WEBSITE\_SERVER = `localhost`
    * BMS\_WEBSITE\_PORT = `2809`
  * Run the test
Below is the result for the example we provided above.

http://bmunit.googlecode.com/svn/trunk/bmunit/wiki/images/bmunit2.PNG