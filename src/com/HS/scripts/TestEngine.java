/**
 * 
 */
package com.HS.scripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.HS.common.ExecutionEnvironment;
import com.HS.common.TestCaseLocation;
import com.HS.common.TestObject;
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
	    executeTestCase(currentTestCaseID.getTCName(), currentTestCaseID.getTCRowNumber());
	}
    }

    /**
     * @param tcName
     * @param tcRowNumber
     */
    private void executeTestCase(String tcName, int tcRowNumber) {
	// TODO Auto-generated method stub

    }

    public TestObject getORData4TestStep(String parent, String testObject) throws Exception {
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
	    return new TestObject(how, what);

	} catch (Exception e) {
	    throw e;
	}
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
}
