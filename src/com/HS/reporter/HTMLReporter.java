/**
 * 
 */
package com.HS.reporter;

import com.HS.utils.Log;

/**
 * @author hemasundar
 *
 */
public class HTMLReporter implements Reporter {
    public Log logger;

    /**
    * 
    */
    public HTMLReporter() {
	logger = new Log(getClass().getSimpleName());
    }

    /**
     * 
     */
    public void setFilePaths() {
	// TODO Auto-generated method stub

    }

}
