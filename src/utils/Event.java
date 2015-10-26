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
	private static String idRegex = "id: (.+?),";
	private static String titleRegex = "title: (.+?),";
	private static String startDateTimeRegex = "startDateTime: (.+?),";
	private static String endDateTimeRegex = "endDateTime: (.+?),";
	private static String priorityRegex = "priority: (.+?),";
	private static String locationRegex = "location: (.+?),";
	private static String notesRegex = "notes: (.+?),";
	private static String reminderRegex = "reminder: \\[(.+?)\\],";
	private static String groupsRegex = "groups: \\[(.+?)\\],";
	private static String recurrenceRegex = "recurrence: (.+?),";
	private static String subtasksRegex = "subtasks: \\[(.+?)\\],";

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
		eventString += String.format("startDateTime: %s, ",
				(this.startDateTime != null) ? this.startDateTime.getTime() : NULL);
		eventString += String.format("endDateTime: %s, ",
				(this.endDateTime != null) ? this.endDateTime.getTime() : NULL);
		eventString += String.format("priority: %s, ", (this.priority != null) ? this.priority.name() : NULL);
		eventString += String.format("location: %s, ", (this.location != null) ? this.location : NULL);
		eventString += String.format("notes: %s, ", (this.notes != null) ? this.notes : NULL);

		eventString += "reminder: [";
		for (int i = 0; i < this.reminder.size(); i++) {
			eventString += String.format("%s, ", this.reminder.get(i).getTime());
		}
		eventString += "], ";

		// eventString += String.format("reminder: %s, ", (this.reminder !=
		// null) ? this.reminder.getTime() : NULL);
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

	public void addReminder(Calendar reminder) {
		this.reminder.add(reminder);
	}

	public void setReminder(List<Calendar> reminders) {
		this.reminder.addAll(reminders);
	}

	public void removeReminder(Calendar reminder) {
		this.reminder.remove(reminder);
	}

	public void removeReminder(int position) {
		this.reminder.remove(position);
	}

	public List<String> getReminderList() {
		List<String> reminderList = new ArrayList<>();
		for (int i = 0; i < this.reminder.size(); i++) {
			String reminderString = toTimestamp(reminder.get(i));
			reminderList.add(reminderString);
		}

		return reminderList;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void addGroup(String group) {
		this.groups.add(group);
	}

	public void removeGroup(String group) {
		int position = -1;

		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(group)) {
				position = i;
				break;
			}
		}

		if (position >= 0) {
			this.groups.remove(position);
		}
	}

	public List<String> getSubtasks() {
		return subtasks;
	}

	public void addSubtask(String subtask) {
		this.subtasks.add(subtask);
	}

	public Recurrence getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

	public void fromString(String dataInput) {

		int[] startDate = new int[6], endDate = new int[6], remindDate = new int[6];
		String title, id, location, notes, group;
		String[] splitedData = new String[11];
		Priority prior;

		splitedData = dataInput.split(", ", 10);
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

	public void fromStorageString(String eventString) {
		parseId(eventString);
		parseTitle(eventString);
		parseStartDateTime(eventString);
		parseEndDateTime(eventString);
		parsePriority(eventString);
		parseLocation(eventString);
		parseNotes(eventString);
		parseReminder(eventString);
		parseGroups(eventString);
		parseRecurrence(eventString);
		parseSubtasks(eventString);
	}

	private void parseSubtasks(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(subtasksRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String subtasksString = matcher.group(1);
			subtasksString = subtasksString.replace("[", "");
			subtasksString = subtasksString.replace("]", "");
			subtasksString = subtasksString.replace(", ", ",");
			String[] subtasks = subtasksString.split(",");

			for (int i = 0; i < subtasks.length; i++) {
				if (subtasks[i] != null && subtasks[i].length() > 0) {
					this.addSubtask(subtasks[i]);
				}
			}
		}

	}

	private void parseRecurrence(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(recurrenceRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String recurrenceString = matcher.group(1);
			if(recurrenceString != null && recurrenceString.length() > 0){
				if(recurrenceString.equals(NULL)){
					this.setRecurrence(null);
				}
				else{
					Recurrence recurrence = Recurrence.valueOf(recurrenceString);
					this.setRecurrence(recurrence);
				}
			}
		}
	}

	private void parseGroups(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(groupsRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String groupString = matcher.group(1);
			groupString = groupString.replace("[", "");
			groupString = groupString.replace("]", "");
			groupString = groupString.replace(", ", ",");
			String[] groups = groupString.split(",");

			for (int i = 0; i < groups.length; i++) {
				if(groups[i] != null && groups[i].length() > 0){
					this.addGroup(groups[i]);
				}
			}
		}
	}

	private void parseReminder(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(reminderRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String reminderString = matcher.group(1);
			reminderString = reminderString.replace("[", "");
			reminderString = reminderString.replace("]", "");
			reminderString = reminderString.replace(", ", ",");
			String[] reminders = reminderString.split(",");

			for (int i = 0; i < reminders.length; i++) {
				if(reminders[i] != null && reminders[i].length() > 0){
					Calendar calendar = this.fromTimestamp(reminders[i]);
					this.addReminder(calendar);
				}
			}
		}
	}

	private void parseNotes(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Notes
		pattern = Pattern.compile(notesRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String notes = matcher.group(1);
			if(notes != null && notes.length() > 0){
				if(notes.equals(NULL)){
					notes = null;
				}
				this.setNotes(notes);
			}
		}
	}

	private void parseLocation(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Location
		pattern = Pattern.compile(locationRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String location = matcher.group(1);
			if(location != null && location.length() > 0){
				if(location.equals(NULL)){
					location = null;
				}
				this.setLocation(location);
			}
		}
	}

	private void parsePriority(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Priority
		pattern = Pattern.compile(priorityRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String priorityString = matcher.group(1);
			if(priorityString != null && priorityString.length() > 0){
				if(priorityString.equals(NULL)){
					this.setPriority(null);
				}
				else{
					Priority priority = Priority.valueOf(priorityString);
					this.setPriority(priority);
				}
			}
		}
	}

	private void parseEndDateTime(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// End Date Time
		pattern = Pattern.compile(endDateTimeRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String timestamp = matcher.group(1);
			if(timestamp != null && timestamp.length() > 0){
				Calendar endDateTime = fromTimestamp(timestamp);
				this.setEndDateTime(endDateTime);
			}
		}
	}

	private void parseStartDateTime(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Start Date Time
		pattern = Pattern.compile(startDateTimeRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String timestamp = matcher.group(1);
			if(timestamp != null && timestamp.length() > 0){
				Calendar startDateTime = fromTimestamp(timestamp);
				this.setStartDateTime(startDateTime);
			}
		}
	}

	private void parseTitle(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Title
		pattern = Pattern.compile(titleRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String title = matcher.group(1);
			if(title != null && title.length() > 0){
				if(title.equals(NULL)){
					title = null;
				}
				this.setTitle(title);
			}
		}
	}

	private void parseId(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// ID
		pattern = Pattern.compile(idRegex);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String id = matcher.group(1);
			if(id != null && id.length() > 0){
				if(id.equals(NULL)){
					id = null;
				}
				this.setId(id);
			}
		}
	}

	public String toTimestamp(Calendar calendar) {
		String timestamp = "";

		int year = calendar.get(calendar.YEAR);
		int month = calendar.get(calendar.MONTH) + 1;
		int date = calendar.get(calendar.DATE);
		int hour = calendar.get(calendar.HOUR_OF_DAY);
		int minute = calendar.get(calendar.MINUTE);

		timestamp = String.format("%d/%d/%d-%d:%d", year, month, date, hour, minute);

		return timestamp;
	}

	public Calendar fromTimestamp(String timestamp) {
		Calendar calendar = null;

		String regex = "\\d+";
		String fullRegex = "(\\d+)/(\\d+)/(\\d+)-(\\d+):(\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(timestamp);

		// Check if matches
		if (timestamp.matches(fullRegex)) {

			calendar = Calendar.getInstance();
			int year = 1970;
			int month = 0;
			int date = 0;
			int hour = 0;
			int minute = 0;

			// Reset to Epoch Time
			calendar.setTimeInMillis(0);

			// Year
			if (matcher.find()) {
				String group = matcher.group();
				year = Integer.valueOf(group);
			}

			// Month
			if (matcher.find()) {
				String group = matcher.group();
				month = Integer.valueOf(group) - 1;
			}

			// Date
			if (matcher.find()) {
				String group = matcher.group();
				date = Integer.valueOf(group);
			}

			// Hour
			if (matcher.find()) {
				String group = matcher.group();
				hour = Integer.valueOf(group);
			}

			// Minute
			if (matcher.find()) {
				String group = matcher.group();
				minute = Integer.valueOf(group);
			}

			calendar.set(year, month, date, hour, minute);
		}
		return calendar;
	}
}
