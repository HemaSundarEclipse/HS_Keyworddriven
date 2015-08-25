/**
 * 
 */
package com.HS.scripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.HS.common.ExecutionEnvironment;
import com.HS.common.StepDetails;
import com.HS.common.TestCaseLocation;
import com.HS.common.TestObject;
import com.HS.common.TestStepExecution;
import com.HS.dataReader.CSVDataReader;
import com.HS.utils.Log;

/**
 * @author hemasundar
 *
 */
public class TestEngine {
    public Log logger;
    public ExecutionEnvironment env;
    public CSVDataReader reader;
    public ArrayList<TestCaseLocation> TestCaseIDsForExecution;
    private List<String[]> tcData, orData;

    /**
    * 
    */
    public TestEngine() {
	logger = new Log(getClass().getSimpleName());
	env = new ExecutionEnvironment();
	reader = new CSVDataReader();
	TestCaseIDsForExecution = new ArrayList<TestCaseLocation>();
    }

    @BeforeTest
    public void beforeTest() {
	/**
	 * Loading And assigning execution environment into java variables
	 */
	env.assigningFilePaths();
	reader.setFilePaths();
	env.loadEnv4mPropFile();
	env.updateEnv4mSystemProp();
	env.assigningEnv();
	env.loadEnv4mPropFile();
    }

    /**
     * 
     */
    @Test
    @Parameters({ "testGroup", "testCaseID" })
    public void exectingTests(String testGroup, String testCaseID) {
	logger.info("Sample");

	/* Loading test case data */
	orData = reader.getORData();
	tcData = reader.getTestCaseData();

	gettingTestCases4Execution(testGroup, testCaseID);
	for (TestCaseLocation currentTestCaseID : TestCaseIDsForExecution) {
	    // Need to write logic to check whether the TC found in TC sheet or
	    // not
	    executeTestCase(currentTestCaseID.getTCName(), currentTestCaseID.getTCRowNumber());
	}
    }

    /**
     * @param tcName
     * @param tcRowNumber
     */
    private void executeTestCase(String tcName, int tcRowNumber) {
	String step, parent, testObject, dataContent, StepAction;
	int tcRowCount = tcData.size();
	do {
	    StepDetails tcStepData4TestStep = getTCStepData4TestStep(tcRowNumber);
	    TestObject currentStepTestObject = getORData4TestStep(tcStepData4TestStep.getParent(),
		    tcStepData4TestStep.getTestObject());
	    TestStepExecution stepExecutor = new TestStepExecution(tcStepData4TestStep, currentStepTestObject, env);

	    stepExecutor.executeTestStep();
	    tcRowNumber++;

	    if (tcRowNumber == tcRowCount) {
		break;
	    }
	} while (reader.getCellValue(tcData, tcRowNumber, "testcase_id") == null
		|| reader.getCellValue(tcData, tcRowNumber, "testcase_id").trim() == "");

    }

    public TestObject getORData4TestStep(String parent, String testObject) {
	String how = null, what = null;
	try {
	    int rowCount = orData.size();
	    int iterativeRow = 0;
	    while (iterativeRow < rowCount) {
		if (reader.getCellValue(orData, iterativeRow, "parent").equals(parent)
			&& reader.getCellValue(orData, iterativeRow, "testObject").equals(testObject)) {
		    how = reader.getCellValue(orData, iterativeRow, "how");
		    what = reader.getCellValue(orData, iterativeRow, "what");

		    break;
		}
		iterativeRow++;
	    }
	} catch (Exception e) {
	    // throw e;
	}
	return new TestObject(how, what);
    }

    /**
     * @param tcRowNumber
     * @return
     * 
     */
    private StepDetails getTCStepData4TestStep(int tcRowNumber) {
	String step = reader.getCellValue(tcData, tcRowNumber, "step");
	String parent = reader.getCellValue(tcData, tcRowNumber, "parent");
	String testObject = reader.getCellValue(tcData, tcRowNumber, "testObject");
	String dataContent = reader.getCellValue(tcData, tcRowNumber, "dataContent");
	String StepAction = reader.getCellValue(tcData, tcRowNumber, "stepaction");
	return new StepDetails(step, parent, testObject, dataContent, StepAction);

    }

    /**
     * @param testGroup
     * @param testCaseID
     */
    private void gettingTestCases4Execution(String testGroup, String testCaseID) {
	int tcRowCount = tcData.size();
	int tcIndex = 0;
	if (!(testCaseID == null || testCaseID == "")) {
	    String[] TCids = testCaseID.split(",");
	    for (String ID : TCids) {
		ID = ID.trim();
		while (tcIndex < tcRowCount) {
		    if (reader.getCellValue(tcData, tcIndex, "testcase_id").equalsIgnoreCase(ID)) {
			TestCaseIDsForExecution.add(new TestCaseLocation(ID, tcIndex));
		    }
		    tcIndex++;
		}

	    }
	} else if (!(testGroup == null || testGroup == "")) {
	    String[] TcGrps = testGroup.split(",");
	    for (String Group : TcGrps) {
		Group = Group.trim();
		while (tcIndex < tcRowCount) {
		    try {
			String tGrp = reader.getCellValue(tcData, tcIndex, "TestGroups").trim();

			String[] TCGrpofID = tGrp.split(",");

			List<String> TCGrpofIDList = Arrays.asList(TCGrpofID);
			for (String Grp : TCGrpofIDList) {
			    Grp = Grp.trim();
			    if (Grp.equalsIgnoreCase(Group)) {
				String tid = reader.getCellValue(tcData, tcIndex, "testcase_id");
				TestCaseIDsForExecution.add(new TestCaseLocation(tid, tcIndex));
			    }
			}
		    } catch (NullPointerException e) {
			e.printStackTrace();
		    }
		    tcIndex++;
		}
	    }
	}
    }

    /**
     * 
     */
    @AfterTest
    private void AfterTestMethod() {
	logger.info("In AfterTest method");
    }
}
