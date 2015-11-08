/* @@author A0126421U */
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
import utils.UserCommandException;

/**
 * @@author A0126421U 
 * 
 * For handling the main logic
 * 
 * @author hiumengxiong
 *
 */
public class StorageManager {

	private static String fileName = null;
	private static List<String> inputData;
	private static String line;
	// private static Logger theLogger =
	// Logger.getLogger(StorageManager.class.getName());
	// FileHandler logFile;

	/**
	 * @@author A0126421U 
	 * 
	 * Constructor to initialize the main components of Storage Manager
	 */
	public StorageManager() {
		inputData = new ArrayList<String>();
		// try {
		// logFile = new FileHandler("storage.log", true);
		// } catch (SecurityException | IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// theLogger.addHandler(logFile);
		// theLogger.setLevel(Level.ALL);
		// theLogger.setUseParentHandlers(false);
	}

	/**
	 * @@author A0126421U 
	 * 
	 * return a list of String from text file
	 * 
	 * @return listOfString - a list of string capture from text file
	 * @throws UserCommandException
	 * 
	 */
	public List<String> load() throws UserCommandException {
		inputData.clear();
		processFile(fileName);
		return inputData;
	}

	/**
	 * @@author A0126421U 
	 * 
	 * Convert the current list of event to string
	 * 
	 * @return String - String the consist of all the events
	 * @throws UserCommandException
	 * 
	 */
	public String listToString() throws UserCommandException {
		int i;

		String result = "";
		List<String> data = new ArrayList<String>();
		data = load();

		for (i = 0; i < data.size(); i++) {
			result = result.concat(data.get(i).toString() + "\n");
		}
		return result;
	}

	/**
	 * @@author A0126421U 
	 * 
	 * Check for the file location
	 * 
	 * @param fileLocation
	 *            the location that provided by event handler
	 * 
	 * @throws UserCommandException
	 * 
	 */
	public void setStorageLocation(String fileLocation) throws UserCommandException {
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

			UserCommandException error = new UserCommandException();
			error.setMessage("Error! File Path detected. Please enter file name!");
			throw error;
		}
		// theLogger.log(Level.INFO, "set file location!");
	}

	/**
	 * @@author A0126421U 
	 * 
	 * Save the list of event into text file
	 * 
	 * @param data
	 *            the list of events to be save in text file.
	 * @throws UserCommandException
	 */
	public void save(List<Event> data) throws UserCommandException {
		int i;
		assert(data != null);

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

			UserCommandException error = new UserCommandException();
			error.setMessage("Error! Cannot be saved! " + e.getMessage());
			throw error;
		}
		// theLogger.log(Level.INFO, "Successfully save data into file!");
	}

	/**
	 * @@author A0126421U 
	 * 
	 * Read data from text file
	 * 
	 * @param fileLocation
	 *            location of the text file to be process
	 * @throws UserCommandException
	 */
	public void processFile(String fileLocation) throws UserCommandException {

		fileName = fileLocation;
		inputData = new ArrayList<String>();

		try {
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);

			// Read file line by line and store into arraylist
			while ((line = bufferReader.readLine()) != null) {
				inputData.add(line);
			}
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("Error while reading file: " + e.getMessage());

			UserCommandException error = new UserCommandException();
			error.setMessage("Error! File cannot be read! " + e.getMessage());
			throw error;
		}
		// theLogger.log(Level.INFO, "Successfully load data from file!");
	}
}
