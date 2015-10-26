package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event {
	private static final String NULL = "null";
	
	private String id;
	private String title;
	private Calendar startDateTime;
	private Calendar endDateTime;
	private Priority priority;
	private String location;
	private String notes;
	private List<Calendar> reminder;
	private List<String> groups;
	private Recurrence recurrence;
	private List<String> subtasks; // List of Subtask ID

	public Event() {
		this.id = null;
		this.title = null;
		this.startDateTime = null;
		this.endDateTime = null;
		this.priority = null;
		this.location = null;
		this.notes = null;
		this.reminder = new ArrayList<>();
		this.groups = new ArrayList<String>();
		this.recurrence = null;
		this.subtasks = new ArrayList<String>();
	}

	public String toString() {
		String eventString = "";

		eventString += String.format("id: %s, ", (this.id != null) ? this.id : NULL);
		eventString += String.format("title: %s, ", (this.title != null) ? this.title : NULL);
		eventString += String.format("startDateTime: %s, ", (this.startDateTime != null) ? this.startDateTime.getTime() : NULL);
		eventString += String.format("endDateTime: %s, ", (this.endDateTime != null) ? this.endDateTime.getTime() : NULL);
		eventString += String.format("priority: %s, ", (this.priority != null) ? this.priority.name() : NULL);
		eventString += String.format("location: %s, ", (this.location != null) ? this.location : NULL);
		eventString += String.format("notes: %s, ", (this.notes != null) ? this.notes : NULL);
		
		eventString += "reminder: [";
		for(int i = 0; i < this.reminder.size(); i++){
			eventString += String.format("%s, ", this.reminder.get(i).getTime());	
		}
		eventString += "], ";
		
//		eventString += String.format("reminder: %s, ", (this.reminder != null) ? this.reminder.getTime() : NULL);
		eventString += String.format("groups: %s, ", Arrays.toString(this.groups.toArray()));
		eventString += String.format("recurrence: %s, ", (this.recurrence != null) ? this.recurrence.name() : NULL);
		eventString += String.format("subtasks: %s, ", Arrays.toString(this.subtasks.toArray()));
		
		return eventString;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Calendar getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Calendar> getReminder() {
		return reminder;
	}

	public void setReminder(Calendar reminder) {
		this.reminder.add(reminder);
	}
	
	public void setReminder(List<Calendar> reminders){
		this.reminder.addAll(reminders);
	}
	
	public void removeReminder(Calendar reminder) {
		this.reminder.remove(reminder);
	}
	
	public void removeReminder(int position){
		this.reminder.remove(position);
	}

	public List<String> getGroups() {
		return groups;
	}
	
	public void addGroup(String group){
		this.groups.add(group);
	}
	
	public void removeGroup(String group){
		int position = -1;
		
		for(int i = 0; i < groups.size(); i++){
			if(groups.get(i).equals(group)){
				position = i;
				break;
			}
		}
		
		if(position >= 0){
			this.groups.remove(position);
		}
	}
	
	public void fromString(String dataInput){

		int[] startDate= new int[6], endDate= new int[6], remindDate = new int[6];
		String title, id, location, notes, group;
		String[] splitedData = new String[11];
		Priority prior;
		
		splitedData=dataInput.split(", ", 10);
		id = removeName(splitedData[0]);
		title = removeName(splitedData[1]);
		if (splitedData[4] != null) {
			prior = determinePrior(removeName(splitedData[4]));
		} else {
			prior = null;
		}
		location = removeName(splitedData[5]);
		notes = removeName(splitedData[6]);
		if (!removeName(splitedData[2]).equals("null")) {
			startDate = convertDate(removeName(splitedData[2]));
			Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
			calendarStart.set(startDate[0], startDate[1], startDate[2], startDate[3], startDate[4], startDate[5]);
			setStartDateTime(calendarStart);
		} else {
			startDate = null;
		}
		if (!removeName(splitedData[3]).equals("null")) {
			endDate = convertDate(removeName(splitedData[3]));
			Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
			calendarEnd.set(endDate[0], endDate[1], endDate[2], endDate[3], endDate[4], endDate[5]);
			setEndDateTime(calendarEnd);
		} else {
			endDate = null;
		}
		if (!removeName(splitedData[3]).equals("null")) {
			remindDate = convertDate(removeName(splitedData[7]));
			Calendar calendarReminder = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
			calendarReminder.set(remindDate[0], remindDate[1], remindDate[2], remindDate[3], remindDate[4],
					remindDate[5]);
			setReminder(calendarReminder);
		} else {
			remindDate = null;
		}
		group = removeName(splitedData[8]);

		setId(id);
		setTitle(title);
		setPriority(prior);
		setLocation(location);
		setNotes(notes);

	}
	
	// remove the tag in string. Example: ID: abc, this method will remove ID:
	// and return abc.
	private String removeName(String input) {
		String[] splitedData = new String[2];
		splitedData = input.split(": ", 2);
		return splitedData[1];
	}

	// convert date from string format(MM DD HH:MM:SS YY) to int array
	private int[] convertDate(String input) {
		String[] splitedData = new String[6];
		int[] date = new int[6];
		int[] time = new int[3];

		splitedData = input.split(" ", 6);
		date[0] = Integer.parseInt(splitedData[5]);
		date[1] = convertMonth(splitedData[1]) - 1;
		date[2] = Integer.parseInt(splitedData[2]);
		time = convertTime(splitedData[3]);
		date[3] = time[0];
		date[4] = time[1];
		date[5] = time[2];

		return date;
	}

	// convert time from string format(HH:MM:SS) to int array
	private int[] convertTime(String input) {
		String[] splitedData = new String[3];
		int[] time = new int[3];

		splitedData = input.split(":", 3);
		time[0] = Integer.parseInt(splitedData[0]);
		time[1] = Integer.parseInt(splitedData[1]);
		time[2] = Integer.parseInt(splitedData[2]);
		return time;
	}

	// convert string month to int
	private int convertMonth(String input) {
		switch (input) {
		case "Jan":
			return 1;
		case "Feb":
			return 2;
		case "Mar":
			return 3;
		case "Apr":
			return 4;
		case "May":
			return 5;
		case "Jun":
			return 6;
		case "Jul":
			return 7;
		case "Aug":
			return 8;
		case "Sep":
			return 9;
		case "Oct":
			return 10;
		case "Nov":
			return 11;
		case "Dec":
			return 12;
		}
		return 0;
	}

	// convert string to priority data type
	private Priority determinePrior(String input) {
		switch (input) {
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
		return null;
	}
	
	public String toTimestamp(Calendar calendar){
		String timestamp = "";
		
		int year = calendar.get(calendar.YEAR);
		int month = calendar.get(calendar.MONTH) + 1;
		int date = calendar.get(calendar.DATE);
		int hour = calendar.get(calendar.HOUR_OF_DAY);
		int minute = calendar.get(calendar.MINUTE);
		
		timestamp = String.format("%d/%d/%d-%d:%d", year, month, date, hour, minute);
		
		return timestamp;
	}
	
	public Calendar fromTimestamp(String timestamp){
		Calendar calendar = null;
		
		
		String regex = "\\d+";
		String fullRegex = "(\\d+)/(\\d+)/(\\d+)-(\\d+):(\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(timestamp);

		// Check if matches
		if(timestamp.matches(fullRegex)){
			
			calendar = Calendar.getInstance();
			int year = 1970;
			int month = 0;
			int date = 0;
			int hour = 0;
			int minute = 0;

			// Reset to Epoch Time
			calendar.setTimeInMillis(0);
			
			// Year
			if(matcher.find()){
				String group = matcher.group();
				year = Integer.valueOf(group);
			}
			
			// Month
			if(matcher.find()){
				String group = matcher.group();
				month = Integer.valueOf(group) - 1;
			}
			
			// Date
			if(matcher.find()){
				String group = matcher.group();
				date = Integer.valueOf(group);
			}
			
			// Hour
			if(matcher.find()){
				String group = matcher.group();
				hour = Integer.valueOf(group);
			}
			
			// Minute
			if(matcher.find()){
				String group = matcher.group();
				minute = Integer.valueOf(group);
			}
			

			calendar.set(year, month, date, hour, minute);
		}
		return calendar;
	}
}
