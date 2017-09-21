package com.ryanliang.knockknock;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BackgroundConnectionCheck is a SwingWorker subclass utilized by the knock knock server app for obtaining the current total number of client socket connections. 
 * @author Ryan L.
 * @version $Revision$
 * @since 1.7
 */
public class BackgroundConnectionCheck extends SwingWorker<Void, String> {
    private static Logger logger;
    static{
    	System.setProperty("logFileName", "server.log");
    	logger = LogManager.getLogger();
    }
    
	private JLabel totalClientConectionLabel;
    private boolean runLoop = false;
    
	/**
	 * This is the only constructor defined for this class.
	 * @param socketThreadList List of KKMultiServerThread objects
	 * @param totalClientConectionLabel A reference JLabel for updating current total number of client socket connections
	 */
    public BackgroundConnectionCheck(JLabel totalClientConectionLabel){
    	this.totalClientConectionLabel = totalClientConectionLabel;
    }

	/**
	 * This method performs a task in the background in a SwingWorker thread.
	 * @return null  
	 */
	@Override
	protected Void doInBackground(){
		logger.trace("doInBackground() is called");
		
		runLoop = true;
		
		while (runLoop){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("thread sleep method of connection checking SwingWorker is being interrupted.");
				logger.error("thread sleep method of connection checking SwingWorker is being interrupted.");
			}
			publish(String.valueOf(ConnectionCounter.getConnectionCounter()));
		}
		return null;
	}
	
	/**
	 * This method updates specific Swing components (UI) while doInBackground() is in progress. 
	 */
    @Override
    protected void process(List<String> chunks) {

    	totalClientConectionLabel.setText("Client connections: " + chunks.get(0));
	}
    
	/**
	 * This method stops the SwingwWorker execution. 
	 */
	public void stopCheckingConnection(){
		logger.trace("stopCheckingConnection() is called");
		
		runLoop = false;
	}
}
