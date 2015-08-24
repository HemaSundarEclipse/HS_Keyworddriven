/**
 * 
 */
package com.HS.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author hemasundar
 *
 */
public class ExecutionEnvironment {
    public String browserName;
    public String testCaseSheet;
    public String isRemoteExecution;
    public String remoteURL;
    public int maxTimeOut;
    public String fileSeperator = "/";
    /*
     * HashMap that will have data from uiautomation.properties. It will be
     * populated through Utility.populateGlobalMap() method.
     */
    public HashMap<String, String> execEnvVarMap = new HashMap<String, String>();
    public String testCaseFileFormat;
    // Name and location of Property file location (uiautomation.properties)
    public String propertyFileLocation;
    // Name and location of Object Repository.
    public String objectRepositoryFileLocation;
    // Location of test cases.
    public String testCaseFileLocation;
    public String logFile;
    public String buildEnv;

    /**
     * it will load all the execution environment properties to ExecEnvVarMap
     */
    public void loadEnv4mPropFile() {
	Properties prop = new Properties();
	try {
	    prop.load(new FileInputStream(propertyFileLocation));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	for (final String name : prop.stringPropertyNames())
	    execEnvVarMap.put(name, prop.getProperty(name));
    }

    /**
     * it will update all the execution environment properties from current
     * System to ExecEnvVarMap
     */
    public void updateEnv4mSystemProp() {
	Properties prop = System.getProperties();
	for (final String name : prop.stringPropertyNames())
	    execEnvVarMap.put(name, prop.getProperty(name));
	/* Printing all the property values stored in ExecEnvVarMap hash map */
	for (Map.Entry<String, String> entry : execEnvVarMap.entrySet()) {
	    String key = entry.getKey().toString();
	    String value = entry.getValue();
	    System.out.println(key + " = " + value);
	}
    }

    /**
     * 
     */
    public void assigningEnv() {
	browserName = execEnvVarMap.get("browserName");
	isRemoteExecution = execEnvVarMap.get("browserName");
	remoteURL = execEnvVarMap.get("RemoteUrl");
	maxTimeOut = Integer.parseInt(execEnvVarMap.get("MaximumTimeout"));
	fileSeperator = execEnvVarMap.get("file.separator");
	testCaseFileFormat = execEnvVarMap.get("TestCaseFileFormat");
	buildEnv = execEnvVarMap.get("buildEnv");
    }

    /**
     * 
     */
    public void assigningFilePaths() {
	propertyFileLocation = "src" + fileSeperator + "com" + fileSeperator + "HS" + fileSeperator + "common"
		+ fileSeperator + "ExecEnv.properties";

	// Name and location of Object Repository.
	objectRepositoryFileLocation = "src" + fileSeperator + "com" + fileSeperator + "HS" + fileSeperator
		+ "executionData" + fileSeperator + "ObjectRepository";
	/*
	 * Location of test cases. Will be used in setFileLocation() method of
	 * ReaderUtility.java
	 */
	testCaseFileLocation = "src" + fileSeperator + "main" + fileSeperator + "resources" + fileSeperator + "TestCase"
		+ fileSeperator;
	logFile = "Logs";

    }
}
