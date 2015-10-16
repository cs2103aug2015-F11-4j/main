package calendrier;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import utils.CalenderYear;
import utils.Event;
import java.util.List;
import java.util.logging.Logger;

//class ErrorFromStorage extends Exception {
//	public ErrorFromStorage(String msg) {
//		super(msg);2221
//	}
//}

public class StorageManager {
	
	private static String fileName=null;
	private static List<String> inputData;
	private static String line;
	private static ArrayList<CalenderYear> year;
	private static ArrayList<Event> floatingTasks;
	private static List<List<String>> backup;
	private static Logger theLogger = Logger.getLogger(StorageManager.class.getName());
	
	public StorageManager(){
		year= new ArrayList<CalenderYear>();
		backup= new ArrayList<List<String>>();
		floatingTasks = new ArrayList<Event>();
	}
	
	@SuppressWarnings("deprecation")
	public void add(Event event){
		int index;
		
		updateStatus();
		delete(event.getId());
		if (event.getStartDateTime() == null || event.getEndDateTime() == null) {
			floatingTasks.add(event);
		} else if (!isYearAvaliable(event.getStartDateTime().getTime().getYear())) {
			year.add(new CalenderYear(event));
		} else {
			index = returnIndex(event.getStartDateTime().getTime().getYear());
			year.get(index).addMonth(event);
		}
		save();
		theLogger.info("Event Added!");
	}
	
	@SuppressWarnings("deprecation")
	public void remove(Event event){	

		updateStatus();
		if(event.getStartDateTime()!=null && event.getEndDateTime() != null){
			int index=returnIndex(event.getStartDateTime().getTime().getYear());
			year.get(index).getMonth(event.getStartDateTime().getTime().getMonth()).getDate(event.getStartDateTime().getTime().getDate()).deleteTask(event);
		}
		else{
			floatingTasks.remove(event);
		}
		save();
	}
	
	public void update(Event oldEvent, Event newEvent){
		updateStatus();
		newEvent = combineEvents(oldEvent,newEvent);
		remove(oldEvent);
		add(newEvent);
		removeStatus();
	}
	
	private void removeStatus() {
		int index=backup.size()-1;
		backup.remove(index);
		index--;
		backup.remove(index);
	}

	public void update(String id,Event newEvent){
		Event oldEvent;
		
		if(view(id)!=null){
			oldEvent = view(id);
			newEvent = combineEvents(oldEvent,newEvent);
			remove(oldEvent);
			add(newEvent);
		}
	}
	
	public void delete(String id){
//		assert id > 0: " Not valid";  

		if(view(id)!=null){
			remove(view(id));
			save();
		}
		else{
			//throw new ErrorFromStorage("Delete ID cannot be empty.");
		}
	}
	public Event view(String id){
		List<Event> events = new ArrayList<>();
		Event result=null;
		events=load();
		int i;
		
		for(i=0;i<events.size();i++){
			if(events.get(i).getId().equals(id)){
				result=events.get(i);
			}
		}
		return result;
	}
	
	public List<Event> load(){
		int i;
		List<Event> events = new ArrayList<>();
		events.addAll(floatingTasks);
		for(i=0;i<year.size();i++){
			events.addAll(year.get(i).getTask());
		}
		return events;
	}
	
	//This is use to check for the content stored in List. Will remove after coding is done.
	public String listToString(){
		List<Event> events = new ArrayList<>();
		String data="";
		events=load();
		int i;
		
		for(i=0;i<events.size();i++){
			data=data.concat(events.get(i).toString()+ "\n");
		}
		return data;
	}
	
	//update the current status that prepared for undo function.
	public void updateStatus() {
		int i, j = 0, index;
		List<Event> data = new ArrayList<Event>();
		
		backup.add(new ArrayList<String>());
		index=backup.size()-1;
		
		if(floatingTasks.size()!=0){
			for(i=0;i<floatingTasks.size();i++){
				backup.get(index).add(floatingTasks.get(i).toString());
			}
		}

		for (i = 0; i < year.size(); i++) {
			data = year.get(i).getTask();
			while (j < data.size()) {
				backup.get(index).add(data.get(j).toString());
				j++;
			}
			index++;
		}
	}
	public void undo(){
		int index=backup.size()-1;
		
		year.clear();
		floatingTasks.clear();
		processInputFromFile(backup.get(index));
		backup.remove(index);
		save();
	}
	
	public void clear(){
		year.clear();
		floatingTasks.clear();
		backup.clear();
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
	}
	
	/**
	 * This method is to print the arraylist into the specified file.
	 */
	public void save() {
		int i, j=0;
		List<Event> data= new ArrayList<Event>();
		
		if(fileName==null){
			setStorageLocation("storageFile.txt");
		}
		
		try {
			FileWriter fileWrite = new FileWriter(fileName);
			BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
			PrintWriter fileOut = new PrintWriter(bufferWrite);
			
			if(floatingTasks.size()!=0){
				for(i=0;i<floatingTasks.size();i++){
					fileOut.println(floatingTasks.get(i).toString());
				}
			}
		
			for(i=0;i<year.size();i++){
				data=year.get(i).getTask();
				while (j < data.size()) {
					fileOut.println(data.get(j).toString());
					j++;
				}
			}
			
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
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
			checkInputData();
		} catch (Exception e) {
			System.out.println("Error while reading file: " + e.getMessage());
			//System.exit(0);
		}
	}
	
	//check whether the input is empty
	private void checkInputData() {
		if(!inputData.isEmpty()){
			processInputFromFile(inputData);
		}
	}
	
	/**
	 * This method is to convert the string file to event as well as adding it to the List.
	 */
	private void processInputFromFile(List<String> dataList) {
		int i;

		for(i=0; i<dataList.size();i++){
			
			Event event1 = new Event();
			event1.fromString(dataList.get(i));
			add(event1);
		}
	}
	
	private Event combineEvents(Event oldEvent, Event newEvent) {
		newEvent.setId(oldEvent.getId());
		if(newEvent.getTitle()==null){
			newEvent.setTitle(oldEvent.getTitle());
		}
		if(newEvent.getStartDateTime()==null){
			newEvent.setStartDateTime(oldEvent.getStartDateTime());
		}
		if(newEvent.getEndDateTime()==null){
			newEvent.setEndDateTime(oldEvent.getEndDateTime());
		}
		if(newEvent.getLocation()==null){
			newEvent.setLocation(oldEvent.getLocation());
		}
		if(newEvent.getNotes()==null){
			newEvent.setNotes(oldEvent.getNotes());
		}
		if(newEvent.getPriority()==null){
			newEvent.setPriority(oldEvent.getPriority());
		}
		if(newEvent.getReminder()==null){
			newEvent.setReminder(oldEvent.getReminder());
		}
		
		return newEvent;
	}
	
	private static Boolean isYearAvaliable (int info) {
		
		int i, size = year.size();

		for (i = 0; i < size; i++) {
			if (year.get(i).getYear()==info) {
				return true;
			}
		}
		return false;
	}
	
	// it return the index for year in the List.
	private static int returnIndex(int info) {
		int i, index = 0;

		for (i = 0; i < year.size(); i++) {
			if (year.get(i).getYear() == info) {
				index = i;
			}
		}
		return index;
	}
}
