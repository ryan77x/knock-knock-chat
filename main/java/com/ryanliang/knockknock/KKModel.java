package com.ryanliang.knockknock;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * KKModel implements KKModellable interface to get data from a source (such a file or database) or save data to a source. 
 * @author Ryan L.
 * @version $Revision$
 * @since 1.7
 */
public class KKModel implements KKModellable {
    private static Logger logger;
    static{
    	System.setProperty("logFileName", "server.log");
    	logger = LogManager.getLogger();
    }
    
	private static Scanner input;
	private Path path;
	private static List<KKJoke> kkJokeList = null;
	private static String delimiter = "###";	
	
	/**
	 * Constructor that does not initialize anything.  It is just for instantiate the object.
	 */
	public KKModel(){
		//do nothing
	}
	
	/**
	 * Constructor for loading jokes from a text file.
	 * @param file Is joke's file name or name with absolute full path
	 */
	public KKModel(String file){
		this.path = Paths.get(file);

		openFile();
		readFile();
		closeFile();
	}
	
	/**
	 * This method is for opening a file. 
	 */
	private void openFile() {
		logger.trace("openFile() is called");
		
		try {
			input = new Scanner(path);
		} catch (IOException e) {
			System.err.println("Error opening file");
			logger.error("Error opening file", e);
			}
		
	}

	/**
	 * This method is for obtaining data from a file. 
	 */
	private void readFile() {
		logger.trace("readFile() is called");

		if (input != null){
			kkJokeList = new ArrayList<KKJoke>(50);
			String clue;
			String answer;
			String line;
			String [] lineParts;
			try {
				while (input.hasNextLine()){
					line = input.nextLine();
					lineParts = line.split(delimiter);	

					if (lineParts.length == 2){
						clue = lineParts[0].trim();
						answer = lineParts[1].trim();
						if (clue.length() > 0 && answer.length() > 0){
							kkJokeList.add(new KKJoke(clue, answer));
						}
					}
				}
			} 
			catch (NoSuchElementException e) {
				System.err.println("File improperly formed");
				logger.error("File improperly formed", e);
				//e.printStackTrace();
				kkJokeList = null;
			}
			catch (IllegalStateException e) {
				System.err.println("Error reading from file");
				logger.error("Error reading from file", e);
				kkJokeList = null;
			}
		}
	}

	/**
	 * This method is for freeing up resources. 
	 */
	private void closeFile() {
		logger.trace("closeFile() is called");
		
		if (input != null)
			input.close();
	}
	
	/**
	 * This method returns a duplicated copy of knock knock list of jokes.
	 * @return A List<KKJoke> object containing list of jokes.  
	 */
	@Override
	public List<KKJoke> getListOfKKJokes() {
		logger.trace("getListOfKKJokes() is called");
				
		//empty list
		List<KKJoke> copyList = new ArrayList<KKJoke>(0);
		
		if (kkJokeList != null){
			int listSize = kkJokeList.size();
			if (listSize < 1){
				kkJokeList = null;
			}
			else{
				copyList = new ArrayList<KKJoke>(listSize);

				for (KKJoke joke : kkJokeList){
					copyList.add(joke);
				}
			}
		}

		return copyList;
	}
}
