package calendrier;

import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Collections;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import utils.CalenderYear;
import utils.Event;
import utils.Priority;

import java.util.List;
import java.util.TimeZone;

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
	//static CalenderYear name;
	
	public StorageManager(){
		year= new ArrayList<CalenderYear>();
		backup= new ArrayList<List<String>>();
		floatingTasks = new ArrayList<Event>();
	}
	
	@SuppressWarnings("deprecation")
	public void add(Event event){
		try {
			int index;
	
			updateStatus();
			if (event.getStartDateTime()==null){
				floatingTasks.add(event);
			} else if(!isYearAvaliable(event.getStartDateTime().getTime().getYear())){
				year.add(new CalenderYear(event));
			}
			else{
				index= returnIndex(event.getStartDateTime().getTime().getYear());
				year.get(index).addMonth(event);
			}
		}catch (Exception e) {
			System.out.println(e.toString() + " ERROR: Start Date Time could not be empty!");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void remove(Event event){	
		int index=returnIndex(event.getStartDateTime().getTime().getYear());
		updateStatus();
		year.get(index).getMonth(event.getStartDateTime().getTime().getMonth()).getDate(event.getStartDateTime().getTime().getDate()).deleteTask(event);
	}
	
	public void update(Event eventOld, Event eventNew){
		updateStatus();
		remove(eventOld);
		add(eventNew);
	}
	
	public void update(String id,Event newEvent){
		Event oldEvent;
		
		if(view(id)!=null){
			oldEvent = view(id);
			newEvent = combineEvents(oldEvent,newEvent);
			remove(oldEvent);
			add(newEvent);
		}
		else{
			//throw new ErrorFromStorage("Update ID cannot be empty.");
		}
	}
	
	private Event combineEvents(Event oldEvent, Event newEvent) {
		newEvent.setId(oldEvent.getId());
		if(newEvent.getTitle().equals(null)){
			newEvent.setTitle(oldEvent.getTitle());
		}
		if(newEvent.getStartDateTime().equals(null)){
			newEvent.setStartDateTime(oldEvent.getStartDateTime());
		}
		if(newEvent.getEndDateTime().equals(null)){
			newEvent.setEndDateTime(oldEvent.getEndDateTime());
		}
		if(newEvent.getLocation().equals(null)){
			newEvent.setLocation(oldEvent.getLocation());
		}
		if(newEvent.getNotes().equals(null)){
			newEvent.setNotes(oldEvent.getNotes());
		}
		if(newEvent.getPriority().equals(null)){
			newEvent.setPriority(oldEvent.getPriority());
		}
		if(newEvent.getReminder().equals(null)){
			newEvent.setReminder(oldEvent.getReminder());
		}
		
		return newEvent;
	}

	public void delete(String id){
		if(view(id)!=null){
			remove(view(id));
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
		if(floatingTasks.size()!=0){
			backup.add(new ArrayList<String>());
			for(i=0;i<floatingTasks.size();i++){
				backup.get(0).add(floatingTasks.get(i).toString());
			}
		}
		backup.add(new ArrayList<String>());
		index=backup.size()-1;
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
		processInputFromFile(backup.get(index));
		backup.remove(index);
	}
	
	public void clear(){
		year.clear();
	}
	
	/**
	 * This method is to check for file location.
	 */
	public void setStorageLocation(String fileLocation) {
		if (fileLocation.length() == 0) {
			//printMessage(MESSAGE_ERRORFILE);
			System.out.println("Cannot detect the specific file!");
			//System.exit(0);
		}
		File file = new File(fileLocation);
		try {
			if (!file.exists()) {
				//printMessage(MESSAGE_NEWFILE);
				//System.out.println("File not found!");
				file.createNewFile();
				/*if(sc.nextLine().toUpperCase().equals("Y")){
					file.createNewFile();
				}*/
			}
			else{
				processFile(fileLocation);
			}
		} catch (IOException e) {
			//printMessage(MESSAGE_ERRORREADFILE);
			System.out.println("ERROR! unable to create file");
		}
	}
	
	/**
	 * This method is to print the arraylist into the specified file.
	 */
	public void save() {
		int i, j=0;
		List<Event> data= new ArrayList<Event>();
		if(fileName==null){
			setStorageLocation("src/calendrier/storageFile.txt");
		}
		try {
			FileWriter fileWrite = new FileWriter(fileName);
			BufferedWriter bufferWrite = new BufferedWriter(fileWrite);
			PrintWriter fileOut = new PrintWriter(bufferWrite);
			
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
		int[] startDate= new int[6], endDate= new int[6], remindDate = new int[6];
		String title, id, location, notes, group;
		String[] splitedData = new String[11];
		Priority prior;
		
		for(i=0; i<dataList.size();i++){
			
			Event event1 = new Event();
			
			splitedData=dataList.get(i).split(", ", 10);

			id=removeName(splitedData[0]);
			title=removeName(splitedData[1]);
			if(splitedData[4]!=null){
				prior=determinePrior(removeName(splitedData[4]));
			}
			else{
				prior=null;
			}
			location = removeName(splitedData[5]);
			notes = removeName(splitedData[6]);
			if(splitedData[2]!=null){
				startDate=convertDate(removeName(splitedData[2]));
				Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
				calendarStart.set(startDate[0], startDate[1], startDate[2], startDate[3], startDate[4], startDate[5]);
				event1.setStartDateTime(calendarStart);
			}
			else{
				startDate=null;
			}
			if(splitedData[3]!=null){
				endDate=convertDate(removeName(splitedData[3]));
				Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
				calendarEnd.set(endDate[0], endDate[1], endDate[2], endDate[3], endDate[4], endDate[5]);
				event1.setEndDateTime(calendarEnd);
			}
			else{
				endDate=null;
			}
			if(splitedData[3]!=null){
				remindDate=convertDate(removeName(splitedData[7]));
				Calendar calendarReminder = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
				calendarReminder.set(remindDate[0], remindDate[1], remindDate[2], remindDate[3], remindDate[4], remindDate[5]);
				event1.setReminder(calendarReminder);
			}
			else{
				remindDate=null;
			}
			group = removeName(splitedData[8]);
			
			event1.setId(id);
			event1.setTitle(title);
			event1.setPriority(prior);
			event1.setLocation(location);
			event1.setNotes(notes);
			

			add(event1);
		}
	}

	//remove the tag in string. Example: ID: abc, this method will remove ID: and return abc.
	private String removeName(String input){
		String[] splitedData = new String[2];
		splitedData=input.split(": ", 2);
		return splitedData[1];
	}
	
	//convert date from string format(MM DD HH:MM:SS YY) to int array
	private int[] convertDate(String input){
		String[] splitedData = new String[6];
		int[] date= new int[6];
		int[] time= new int[3];
	
		splitedData=input.split(" ", 6);
		date[0] = Integer.parseInt(splitedData[5]);
		date[1] = convertMonth(splitedData[1])-1;
		date[2] = Integer.parseInt(splitedData[2]);
		time=convertTime(splitedData[3]);
		date[3] = time[0];
		date[4] = time[1];
		date[5] = time[2];
		
		return date;
	}
	
	//convert time from string format(HH:MM:SS) to int array
	private int[] convertTime(String input){
		String[] splitedData = new String[3];
		int[] time= new int[3];
		
		splitedData=input.split(":", 3);
		time[0] = Integer.parseInt(splitedData[0]);
		time[1] = Integer.parseInt(splitedData[1]);
		time[2] = Integer.parseInt(splitedData[2]);
		return time;
	}
	
	//convert string month to int
	private int convertMonth(String input){
		switch(input){
		case"Jan":
			return 1;
		case"Feb":
			return 2;
		case"Mar":
			return 3;
		case"Apr":
			return 4;
		case"May":
			return 5;
		case"Jun":
			return 6;
		case"Jul":
			return 7;
		case"Aug":
			return 8;
		case"Sep":
			return 9;
		case"Oct":
			return 10;
		case"Nov":
			return 11;
		case"Dec":
			return 12;
		}
		return 0;
	}
	
	//convert string to priority data type
	private Priority determinePrior(String input){
		switch(input){
		case "HIGH":
			return Priority.HIGH;
		case "MEDIUM":
			return Priority.MEDIUM;
		case "VERY_LOW":
			return Priority.VERY_LOW;
		case "VERY_HIGH":
			return Priority.VERY_HIGH;
		case "LOW":
			return Priority.LOW;
		}
		return Priority.MEDIUM;
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
