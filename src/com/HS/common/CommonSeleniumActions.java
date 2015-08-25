/**
 * 
 */
package com.HS.common;

import java.net.InetAddress;

import org.openqa.selenium.WebDriver;

import com.HS.utils.Log;

/**
 * @author hemasundar
 *
 */
public class CommonSeleniumActions extends DriverConfiguration {
    public Log logger;
    public ExecutionEnvironment env;

    /**
    * 
    */
    public CommonSeleniumActions(ExecutionEnvironment env) {
	super(env);
	this.env = env;
	logger = new Log(getClass().getSimpleName());
	if (driver == null) {
	    initDriver();
	}
    }

    public WebDriver openBrowser(String Url) throws Exception {
	try {
	    // this.deleteAllCookies();
	    logger.info("Current ViewPort is: " + driver.manage().window().getSize().toString());
	    logger.info("Computer Name is : " + InetAddress.getLocalHost().getHostName());

	    logger.info("Opening..." + Url);
	    driver.get(Url);

	    logger.info("New ViewPort is: " + driver.manage().window().getSize().toString());
	    return driver;
	} catch (Exception e) {
	    throw e;
	}
    }
}