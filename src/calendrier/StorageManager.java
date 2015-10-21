package calendrier;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import utils.Event;
import java.util.List;
//import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.FileHandler;

public class StorageManager {
	
	private static String fileName=null;
	private static List<String> inputData;
	private static String line;
	//private static Logger theLogger = Logger.getLogger(StorageManager.class.getName());
//	FileHandler logFile;
	
	public StorageManager(){
		inputData=new ArrayList<String>();
//		try {
//			logFile = new FileHandler("storage.log", true);
//		} catch (SecurityException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//theLogger.addHandler(logFile);
//	    theLogger.setLevel(Level.ALL);
//	    theLogger.setUseParentHandlers(false);
	}
	
	public List<String> load(){
		inputData.clear();
		processFile(fileName);
		return inputData;
	}
	public String listToString(){
		int i;
		
		String result="";
		List<String> data=new ArrayList<String>();
		data=load();
		
		for(i=0;i<data.size();i++){
			result=result.concat(data.get(i).toString()+"\n");
		}
		return result;
	}
	/**
	 * This method is to check for file location.
	 */
	public void setStorageLocation(String fileLocation) {
		if (fileLocation.length() == 0) {
			System.out.println("Cannot detect the specific file!");
		}
		File file = new File(fileLocation);
		try {
			if (!file.exists()) {

				file.createNewFile();

			}
			processFile(fileLocation);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		//theLogger.log(Level.INFO, "set file location!");
	}

	/**
	 * This method is to print the arraylist into the specified file.
	 */
	public void save(List<Event> data) {
		int i;
		assert(data !=  null);  
		
		if (fileName == null) {
			setStorageLocation("storageFile.txt");
		}

		try {
			FileWriter fileWrite = new FileWriter(fileName);
			BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
			PrintWriter fileOut = new PrintWriter(bufferWrite);

			for (i = 0; i < data.size(); i++) {
				fileOut.println(data.get(i).toString());
			}

			fileOut.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		//theLogger.log(Level.INFO, "Successfully save data into file!");
	}

	/**
	 * This method is to read input from file.
	 */
	public void processFile(String fileLocation) {

		fileName = fileLocation;
		inputData = new ArrayList<String>();

		try {
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);

			// Read file line by line and store into arraylist
			while ((line = bufferReader.readLine())!=null) {
				inputData.add(line);
			}
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("Error while reading file: " + e.getMessage());
		}
		//theLogger.log(Level.INFO, "Successfully load data from file!");
	}
}
