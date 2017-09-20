package com.ryanliang.knockknock;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The knock knock app displays a new client GUI window.
 * @author Ryan L.
 * @version $Revision$
 * @since 1.7
 */
public class KKClient {
	
    private static Logger logger;
    static{
    	System.setProperty("logFileName", "client.log");
    	logger = LogManager.getLogger();
    }
    
	public static void main(String[] args) {
		logger.trace("Client app starts");
		
		EventQueue.invokeLater(() -> {

			KKClientGui kkClient = new KKClientGui();
			
			Toolkit kit = Toolkit.getDefaultToolkit();
			Dimension screenSize = kit.getScreenSize();
			int screenWidth = (int) (screenSize.width*0.2);
			int screenHeight = (int) (screenSize.height*0.2);
			kkClient.setSize(screenWidth, screenHeight);

			kkClient.setLocationRelativeTo(null);
			kkClient.setVisible(true);
			kkClient.connect();
			
	});
		
	}

}
