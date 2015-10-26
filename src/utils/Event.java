package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event {
	private static final String NUMBER_REGEX = "\\d+";
	private static final String FULL_TIMESTAMP_REGEX = "(\\d+)/(\\d+)/(\\d+)-(\\d+):(\\d+)";
	private static final String DATETIME_FORMAT = "%d/%d/%d-%d:%d";
	private static final String NULL = "null";
	private static final String ID_REGEX = "id: (.+?),";
	private static final String TITLE_REGEX = "title: (.+?),";
	private static final String STARTDATETIME_REGEX = "startDateTime: (.+?),";
	private static final String ENDDATETIME_REGEX = "endDateTime: (.+?),";
	private static final String PRIORITY_REGEX = "priority: (.+?),";
	private static final String LOCATION_REGEX = "location: (.+?),";
	private static final String NOTES_REGEX = "notes: (.+?),";
	private static final String REMINDER_REGEX = "reminder: \\[(.+?)\\],";
	private static final String GROUPS_REGEX = "groups: \\[(.+?)\\],";
	private static final String RECURRENCE_REGEX = "recurrence: (.+?),";
	private static final String SUBTASKS_REGEX = "subtasks: \\[(.+?)\\],";

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

	public void fromString(String eventString) {
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

	public String toString() {
		String eventString = "";

		eventString = serializeId(eventString);
		eventString = serializeTitle(eventString);
		eventString = serializeStartDateTime(eventString);
		eventString = serializeEndDateTime(eventString);
		eventString = serializePriority(eventString);
		eventString = serializeLocation(eventString);
		eventString = serializeNotes(eventString);
		eventString = serializeReminder(eventString);
		eventString = serializeGroups(eventString);
		eventString = serializeRecurrence(eventString);
		eventString = serializeSubtasks(eventString);

		return eventString;
	}

	public String toTimestamp(Calendar calendar) {
		String timestamp = "";

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		timestamp = String.format(DATETIME_FORMAT, year, month, date, hour, minute);

		return timestamp;
	}

	public Calendar fromTimestamp(String timestamp) {
		Calendar calendar = null;

		String regex = NUMBER_REGEX;
		String fullRegex = FULL_TIMESTAMP_REGEX;
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

	private String serializeSubtasks(String eventString) {
		eventString += String.format("subtasks: %s, ", Arrays.toString(this.subtasks.toArray()));
		return eventString;
	}

	private void parseSubtasks(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(SUBTASKS_REGEX);
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

	private String serializeRecurrence(String eventString) {
		eventString += String.format("recurrence: %s, ", (this.recurrence != null) ? this.recurrence.name() : NULL);
		return eventString;
	}

	private void parseRecurrence(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(RECURRENCE_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String recurrenceString = matcher.group(1);
			if (recurrenceString != null && recurrenceString.length() > 0) {
				if (recurrenceString.equals(NULL)) {
					this.setRecurrence(null);
				} else {
					Recurrence recurrence = Recurrence.valueOf(recurrenceString);
					this.setRecurrence(recurrence);
				}
			}
		}
	}

	private String serializeGroups(String eventString) {
		eventString += String.format("groups: %s, ", Arrays.toString(this.groups.toArray()));
		return eventString;
	}

	private void parseGroups(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(GROUPS_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String groupString = matcher.group(1);
			groupString = groupString.replace("[", "");
			groupString = groupString.replace("]", "");
			groupString = groupString.replace(", ", ",");
			String[] groups = groupString.split(",");

			for (int i = 0; i < groups.length; i++) {
				if (groups[i] != null && groups[i].length() > 0) {
					this.addGroup(groups[i]);
				}
			}
		}
	}

	private String serializeReminder(String eventString) {
		List<String> reminders = new ArrayList<>();
		for (int i = 0; i < this.reminder.size(); i++) {
			reminders.add(toTimestamp(this.reminder.get(i)));
		}
		eventString += String.format("reminder: %s, ", Arrays.toString(reminders.toArray()));
		return eventString;
	}

	private void parseReminder(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(REMINDER_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String reminderString = matcher.group(1);
			reminderString = reminderString.replace("[", "");
			reminderString = reminderString.replace("]", "");
			reminderString = reminderString.replace(", ", ",");
			String[] reminders = reminderString.split(",");

			for (int i = 0; i < reminders.length; i++) {
				if (reminders[i] != null && reminders[i].length() > 0) {
					Calendar calendar = this.fromTimestamp(reminders[i]);
					this.addReminder(calendar);
				}
			}
		}
	}

	private String serializeNotes(String eventString) {
		eventString += String.format("notes: %s, ", (this.notes != null) ? this.notes : NULL);
		return eventString;
	}

	private void parseNotes(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Notes
		pattern = Pattern.compile(NOTES_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String notes = matcher.group(1);
			if (notes != null && notes.length() > 0) {
				if (notes.equals(NULL)) {
					notes = null;
				}
				this.setNotes(notes);
			}
		}
	}

	private String serializeLocation(String eventString) {
		eventString += String.format("location: %s, ", (this.location != null) ? this.location : NULL);
		return eventString;
	}

	private void parseLocation(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Location
		pattern = Pattern.compile(LOCATION_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String location = matcher.group(1);
			if (location != null && location.length() > 0) {
				if (location.equals(NULL)) {
					location = null;
				}
				this.setLocation(location);
			}
		}
	}

	private String serializePriority(String eventString) {
		eventString += String.format("priority: %s, ", (this.priority != null) ? this.priority.name() : NULL);
		return eventString;
	}

	private void parsePriority(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Priority
		pattern = Pattern.compile(PRIORITY_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String priorityString = matcher.group(1);
			if (priorityString != null && priorityString.length() > 0) {
				if (priorityString.equals(NULL)) {
					this.setPriority(null);
				} else {
					Priority priority = Priority.valueOf(priorityString);
					this.setPriority(priority);
				}
			}
		}
	}

	private String serializeEndDateTime(String eventString) {
		eventString += String.format("endDateTime: %s, ",
				(this.endDateTime != null) ? toTimestamp(this.endDateTime) : NULL);
		return eventString;
	}

	private void parseEndDateTime(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// End Date Time
		pattern = Pattern.compile(ENDDATETIME_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String timestamp = matcher.group(1);
			if (timestamp != null && timestamp.length() > 0) {
				Calendar endDateTime = fromTimestamp(timestamp);
				this.setEndDateTime(endDateTime);
			}
		}
	}

	private String serializeStartDateTime(String eventString) {
		eventString += String.format("startDateTime: %s, ",
				(this.startDateTime != null) ? toTimestamp(this.startDateTime) : NULL);
		return eventString;
	}

	private void parseStartDateTime(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Start Date Time
		pattern = Pattern.compile(STARTDATETIME_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String timestamp = matcher.group(1);
			if (timestamp != null && timestamp.length() > 0) {
				Calendar startDateTime = fromTimestamp(timestamp);
				this.setStartDateTime(startDateTime);
			}
		}
	}

	private String serializeTitle(String eventString) {
		eventString += String.format("title: %s, ", (this.title != null) ? this.title : NULL);
		return eventString;
	}

	private void parseTitle(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Title
		pattern = Pattern.compile(TITLE_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String title = matcher.group(1);
			if (title != null && title.length() > 0) {
				if (title.equals(NULL)) {
					title = null;
				}
				this.setTitle(title);
			}
		}
	}

	private String serializeId(String eventString) {
		eventString += String.format("id: %s, ", (this.id != null) ? this.id : NULL);
		return eventString;
	}

	private void parseId(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// ID
		pattern = Pattern.compile(ID_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String id = matcher.group(1);
			if (id != null && id.length() > 0) {
				if (id.equals(NULL)) {
					id = null;
				}
				this.setId(id);
			}
		}
	}
}
