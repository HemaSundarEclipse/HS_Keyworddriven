/**
 * 
 */
package com.HS.common;

/**
 * @author Hema
 *
 */
public class StepDetails {
    private final String step, parent, testObject, dataContent, StepAction;

    /**
     * @param testCaseID
     * @param step
     * @param parent
     * @param testObject
     * @param dataContent
     * @param StepAction
     * 
     */
    public StepDetails(String step, String parent, String testObject, String dataContent, String StepAction) {
	this.step = step;
	this.parent = parent;
	this.testObject = testObject;
	this.dataContent = dataContent;
	this.StepAction = StepAction;
    }

    /**
     * @return the step
     */
    public String getStep() {
	return step;
    }

    /**
     * @return the parent
     */
    public String getParent() {
	return parent;
    }

    /**
     * @return the testObject
     */
    public String getTestObject() {
	return testObject;
    }

    /**
     * @return the dataContent
     */
    public String getDataContent() {
	return dataContent;
    }

    /**
     * @return the stepAction
     */
    public String getStepAction() {
	return StepAction;
    }

}
