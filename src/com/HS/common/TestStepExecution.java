/**
 * 
 */
package com.HS.common;

import com.HS.utils.Log;

/**
 * @author Hema
 *
 */
public class TestStepExecution {
    private Log logger;
    public StepDetails tcStepData4TestStep;
    public TestObject currentStepTestObject;
    public CommonSeleniumActions perform;

    /**
     * @param currentStepTestObject
     * @param tcStepData4TestStep
     * 
     */
    public TestStepExecution(StepDetails tcStepData4TestStep, TestObject currentStepTestObject) {
	this.currentStepTestObject = currentStepTestObject;
	this.tcStepData4TestStep = tcStepData4TestStep;
	logger = new Log(getClass().getSimpleName());
	perform = new CommonSeleniumActions();
    }

    /**
     * 
     */
    public void executeTestStep() {
	if (tcStepData4TestStep.getStepAction().equalsIgnoreCase("openbrowser")) {
	    try {
		perform.openBrowser(tcStepData4TestStep.getDataContent());
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }
}
