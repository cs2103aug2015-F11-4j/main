package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @@author A0088646M
 * @author yeehuipoh 
 **/
public class Event implements Comparable<Event> {
	private static final String NULL = "null";
	private static final String NUMBER_REGEX = "\\d+";
	private static final String FULL_TIMESTAMP_REGEX = "(\\d+)/(\\d+)/(\\d+)-(\\d+):(\\d+)";
	private static final String DATETIME_FORMAT = "%d/%d/%d-%02d:%02d";

	private static final String ID_STRING = "id: %s, ";
	private static final String MAIN_ID_STRING = "mainId: %s, ";
	private static final String TITLE_STRING = "title: %s, ";
	private static final String STARTDATETIME_STRING = "startDateTime: %s, ";
	private static final String ENDDATETIME_STRING = "endDateTime: %s, ";
	private static final String PRIORITY_STRING = "priority: %s, ";
	private static final String LOCATION_STRING = "location: %s, ";
	private static final String NOTES_STRING = "notes: %s, ";
	private static final String REMINDER_STRING = "reminder: %s, ";
	private static final String GROUPS_STRING = "groups: %s, ";
	private static final String RECURRENCE_STRING = "recurrence: %s, ";
	private static final String SUBTASKS_STRING = "subtasks: %s, ";
	private static final String DONE_STRING = "done: %s, ";

	private static final String ID_REGEX = "id: (.*?),";
	private static final String MAIN_ID_REGEX = "mainId: (.*?),";
	private static final String TITLE_REGEX = "title: (.*?),";
	private static final String STARTDATETIME_REGEX = "startDateTime: (.*?),";
	private static final String ENDDATETIME_REGEX = "endDateTime: (.*?),";
	private static final String PRIORITY_REGEX = "priority: (.*?),";
	private static final String LOCATION_REGEX = "location: (.*?),";
	private static final String NOTES_REGEX = "notes: (.*?),";
	private static final String REMINDER_REGEX = "reminder: \\[(.*?)\\],";
	private static final String GROUPS_REGEX = "groups: (.*?),";
	private static final String RECURRENCE_REGEX = "recurrence: (.*?),";
	private static final String SUBTASKS_REGEX = "subtasks: \\[(.*?)\\],";
	private static final String DONE_REGEX = "done: (.*?),";

	private String id;
	private String title;
	private Calendar startDateTime;
	private Calendar endDateTime;
	private Priority priority;
	private String location;
	private String notes;
	private String mainId;
	private List<Calendar> reminder;
	private String group;
	private Recurrence recurrence;
	private List<String> subtasks; // List of Subtask ID
	private Boolean done;

	/* @@author A0088646M */
	public Event() {
		this.id = null;
		this.title = null;
		this.startDateTime = null;
		this.endDateTime = null;
		this.priority = null;
		this.location = null;
		this.notes = null;
		this.mainId = null;
		this.reminder = new ArrayList<>();
		this.group = null;
		this.recurrence = null;
		this.subtasks = new ArrayList<String>();
		this.done = false;
	}

	/* @@author A0088646M */
	public void fromString(String eventString) {
		parseId(eventString);
		parseMainId(eventString);
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
		parseDone(eventString);
	}

	/* @@author A0088646M */
	public String toString() {
		String eventString = "";

		eventString = serializeId(eventString);
		eventString = serializeMainId(eventString);
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
		eventString = serializeDone(eventString);

		return eventString;
	}

	/* @@author A0088646M */
	public String toTimestamp(Calendar calendar) {
		String timestamp = null;

		if (calendar != null) {
			timestamp = "";
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int date = calendar.get(Calendar.DATE);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);

			timestamp = String.format(DATETIME_FORMAT, year, month, date, hour, minute);
		}
		return timestamp;
	}

	/* @@author A0088646M */
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
			matcher.find();
			String group = matcher.group();
			year = Integer.valueOf(group);

			// Month
			matcher.find();
			group = matcher.group();
			month = Integer.valueOf(group) - 1;

			// Date
			matcher.find();
			group = matcher.group();
			date = Integer.valueOf(group);

			// Hour
			matcher.find();
			group = matcher.group();
			hour = Integer.valueOf(group);

			// Minute
			matcher.find();
			group = matcher.group();
			minute = Integer.valueOf(group);

			calendar.set(year, month, date, hour, minute);
		}
		return calendar;
	}

	/* @@author A0088646M */
	public String getId() {
		return id;
	}

	/* @@author A0088646M */
	public void setId(String id) {
		this.id = id;
	}

	/* @@author A0088646M */
	public String getMainId() {
		return mainId;
	}

	/* @@author A0088646M */
	public void setMainId(String id) {
		this.mainId = id;
	}

	/* @@author A0088646M */
	public String getTitle() {
		return title;
	}

	/* @@author A0088646M */
	public void setTitle(String title) {
		this.title = title;
	}

	/* @@author A0088646M */
	public Calendar getStartDateTime() {
		return startDateTime;
	}

	/* @@author A0088646M */
	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}

	/* @@author A0088646M */
	public Calendar getEndDateTime() {
		return endDateTime;
	}

	/* @@author A0088646M */
	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}

	/* @@author A0088646M */
	public Priority getPriority() {
		return priority;
	}

	/* @@author A0088646M */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/* @@author A0088646M */
	public String getLocation() {
		return location;
	}

	/* @@author A0088646M */
	public void setLocation(String location) {
		this.location = location;
	}

	/* @@author A0088646M */
	public String getNotes() {
		return notes;
	}

	/* @@author A0088646M */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/* @@author A0088646M */
	public List<Calendar> getReminder() {
		return reminder;
	}

	/* @@author A0088646M */
	public void setReminder(Calendar reminder) {
		if (reminder != null) {
			this.reminder.add(reminder);
		}
	}

	/* @@author A0088646M */
	public void addReminder(Calendar reminder) {
		if (reminder != null) {
			this.reminder.add(reminder);
		}
	}

	/* @@author A0088646M */
	public void setReminder(List<Calendar> reminders) {
		if (reminders != null) {
			this.reminder.addAll(reminders);
		}
	}

	/* @@author A0088646M */
	public void removeReminder(Calendar reminder) {
		if (reminder != null) {
			this.reminder.remove(reminder);
		}
	}

	/* @@author A0088646M */
	public void removeReminder(int position) {
		this.reminder.remove(position);
	}

	/* @@author A0088646M */
	public void removeAllReminders() {
		this.reminder.clear();
	}

	/* @@author A0088646M */
	public List<String> getReminderList() {
		List<String> reminderList = new ArrayList<>();
		for (int i = 0; i < this.reminder.size(); i++) {
			String reminderString = toTimestamp(reminder.get(i));
			reminderList.add(reminderString);
		}

		return reminderList;
	}

	/* @@author A0088646M */
	public String getGroup() {
		return group;
	}

	/* @@author A0088646M */
	public void addGroup(String group) {
		this.group = group;
	}

	/* @@author A0088646M */
	public void removeGroup() {
		this.group = null;
	}

	/* @@author A0088646M */
	public List<String> getSubtasks() {
		return subtasks;
	}

	/* @@author A0088646M */
	public void addSubtask(Event event) {
		if (event != null && event.getId() != null) {
			this.subtasks.add(event.getId());
			event.setMainId(this.getId());
		}
	}

	/* @@author A0088646M */
	public void addSubtask(String id) {
		if (id != null) {
			this.subtasks.add(id);
		}
	}

	/* @@author A0088646M */
	public void removeSubtask(String id) {
		int removeIndex = -1;
		for (int i = 0; i < this.subtasks.size(); i++) {
			String subtask = this.subtasks.get(i);
			if (subtask.equals(id)) {
				removeIndex = i;
				break;
			}
		}

		if (removeIndex != -1) {
			this.subtasks.remove(removeIndex);
		}
	}

	/* @@author A0088646M */
	public void removeAllSubtasks() {
		this.subtasks.clear();
	}

	/* @@author A0088646M */
	public Recurrence getRecurrence() {
		return recurrence;
	}

	/* @@author A0088646M */
	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

	/* @@author A0088646M */
	public Boolean isDone() {
		return done;
	}

	/* @@author A0088646M */
	public void setDone(Boolean done) {
		this.done = done;
	}

	/* @@author A0088646M */
	private String serializeDone(String eventString) {
		eventString += String.format(DONE_STRING, Boolean.toString(done));
		return eventString;
	}

	/* @@author A0088646M */
	private void parseDone(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Done
		pattern = Pattern.compile(DONE_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String doneString = matcher.group(1);
			if (doneString.length() > 0) {
				boolean done = Boolean.valueOf(doneString);
				this.setDone(done);
			}
		}
	}

	/* @@author A0088646M */
	private String serializeSubtasks(String eventString) {
		eventString += String.format(SUBTASKS_STRING, Arrays.toString(this.subtasks.toArray()));
		return eventString;
	}

	/* @@author A0088646M */
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
				if (subtasks[i].length() > 0) {
					this.addSubtask(subtasks[i]);
				}
			}
		}

	}

	/* @@author A0088646M */
	private String serializeRecurrence(String eventString) {
		eventString += String.format(RECURRENCE_STRING, (this.recurrence != null) ? this.recurrence.name() : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseRecurrence(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(RECURRENCE_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String recurrenceString = matcher.group(1);
			if (recurrenceString.length() > 0) {
				if (recurrenceString.equals(NULL)) {
					this.setRecurrence(null);
				} else {
					Recurrence recurrence = Recurrence.valueOf(recurrenceString);
					this.setRecurrence(recurrence);
				}
			}
		}
	}

	/* @@author A0088646M */
	private String serializeGroups(String eventString) {
		eventString += String.format(GROUPS_STRING, this.group);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseGroups(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Recurrence
		pattern = Pattern.compile(GROUPS_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String groupString = matcher.group(1);
			if (groupString.length() > 0) {
				this.group = groupString;
			}
		}
	}

	/* @@author A0088646M */
	private String serializeReminder(String eventString) {
		List<String> reminders = new ArrayList<>();
		for (int i = 0; i < this.reminder.size(); i++) {
			reminders.add(toTimestamp(this.reminder.get(i)));
		}
		eventString += String.format(REMINDER_STRING, Arrays.toString(reminders.toArray()));
		return eventString;
	}

	/* @@author A0088646M */
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
				if (reminders[i].length() > 0) {
					Calendar calendar = this.fromTimestamp(reminders[i]);
					this.addReminder(calendar);
				}
			}
		}
	}

	/* @@author A0088646M */
	private String serializeNotes(String eventString) {
		eventString += String.format(NOTES_STRING, (this.notes != null) ? this.notes : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseNotes(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Notes
		pattern = Pattern.compile(NOTES_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String notes = matcher.group(1);
			if (notes.length() > 0) {
				if (notes.equals(NULL)) {
					notes = null;
				}
				this.setNotes(notes);
			}
		}
	}

	/* @@author A0088646M */
	private String serializeLocation(String eventString) {
		eventString += String.format(LOCATION_STRING, (this.location != null) ? this.location : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseLocation(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Location
		pattern = Pattern.compile(LOCATION_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String location = matcher.group(1);
			if (location.length() > 0) {
				if (location.equals(NULL)) {
					location = null;
				}
				this.setLocation(location);
			}
		}
	}

	/* @@author A0088646M */
	private String serializePriority(String eventString) {
		eventString += String.format(PRIORITY_STRING, (this.priority != null) ? this.priority.name() : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parsePriority(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Priority
		pattern = Pattern.compile(PRIORITY_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String priorityString = matcher.group(1);
			if (priorityString.length() > 0) {
				if (priorityString.equals(NULL)) {
					this.setPriority(null);
				} else {
					Priority priority = Priority.valueOf(priorityString);
					this.setPriority(priority);
				}
			}
		}
	}

	/* @@author A0088646M */
	private String serializeEndDateTime(String eventString) {
		eventString += String.format(ENDDATETIME_STRING,
				(this.endDateTime != null) ? toTimestamp(this.endDateTime) : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseEndDateTime(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// End Date Time
		pattern = Pattern.compile(ENDDATETIME_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String timestamp = matcher.group(1);
			if (timestamp.length() > 0) {
				Calendar endDateTime = fromTimestamp(timestamp);
				this.setEndDateTime(endDateTime);
			}
		}
	}

	/* @@author A0088646M */
	private String serializeStartDateTime(String eventString) {
		eventString += String.format(STARTDATETIME_STRING,
				(this.startDateTime != null) ? toTimestamp(this.startDateTime) : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseStartDateTime(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Start Date Time
		pattern = Pattern.compile(STARTDATETIME_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String timestamp = matcher.group(1);
			if (timestamp.length() > 0) {
				Calendar startDateTime = fromTimestamp(timestamp);
				this.setStartDateTime(startDateTime);
			}
		}
	}

	/* @@author A0088646M */
	private String serializeTitle(String eventString) {
		eventString += String.format(TITLE_STRING, (this.title != null) ? this.title : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseTitle(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Title
		pattern = Pattern.compile(TITLE_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String title = matcher.group(1);
			if (title.length() > 0) {
				if (title.equals(NULL)) {
					title = null;
				}
				this.setTitle(title);
			}
		}
	}

	/* @@author A0088646M */
	private String serializeId(String eventString) {
		eventString += String.format(ID_STRING, (this.id != null) ? this.id : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseId(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// ID
		pattern = Pattern.compile(ID_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String id = matcher.group(1);
			if (id.length() > 0) {
				if (id.equals(NULL)) {
					id = null;
				}
				this.setId(id);
			}
		}
	}

	/* @@author A0088646M */
	private String serializeMainId(String eventString) {
		eventString += String.format(MAIN_ID_STRING, (this.mainId != null) ? this.mainId : NULL);
		return eventString;
	}

	/* @@author A0088646M */
	private void parseMainId(String eventString) {
		Pattern pattern;
		Matcher matcher;
		// Main ID
		pattern = Pattern.compile(MAIN_ID_REGEX);
		matcher = pattern.matcher(eventString);
		if (matcher.find()) {
			String id = matcher.group(1);
			if (id.length() > 0) {
				if (id.equals(NULL)) {
					id = null;
				}
				this.setMainId(id);
			}
		}
	}

	/* @@author A0088646M */
	@Override
	public int compareTo(Event arg0) {
		int result = 0;
		Event that = arg0;

		int thisValue = -1;
		int thatValue = -1;

		if (this.getPriority() != null) {
			thisValue = this.getPriority().ordinal();
		}
		if (that.getPriority() != null) {
			thatValue = that.getPriority().ordinal();
		}

		// Compare
		if (thisValue > thatValue) {
			result = -1;
		} else if (thisValue < thatValue) {
			result = 1;
		}

		return result;
	}

	/**
	 * @@author A0088646M
	 * Gets event object with updated date and time with recurrence
	 * 
	 * @return new event object with next recurrence data and time
	 */
	public Event getRecurredEvent() {
		Calendar now = Calendar.getInstance();
		return getRecurredEvent(now);
	}

	/* @@author A0088646M */
	private Event getRecurredEvent(Calendar now) {
		Event checkedEvent = null;

		if (this.getRecurrence() == null) {
			checkedEvent = this;
		} else {
			checkedEvent = new Event();

			// Clone
			String eventString = this.toString();
			checkedEvent.fromString(eventString);

			Recurrence recurrence = checkedEvent.getRecurrence();
			Calendar startDateTime = checkedEvent.getStartDateTime();
			Calendar endDateTime = checkedEvent.getEndDateTime();

			// Update Start and End Date Time
			updateDateTimeWithRecurrence(startDateTime, endDateTime, now, recurrence);
		}

		return checkedEvent;
	}
	
	/**
	 * @@author A0088646M
	 * Gets event object with updated date and time with recurrence
	 * @param year	year to be limited to
	 * @param month month to be limited to
	 * @return	list of events
	 */
	public List<Event> getRecurredEvents(int year, int month) {
		List<Event> checkedEvents = new ArrayList<>();
		Calendar current = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		// Add actual event
		checkedEvents.add(this);
		
		if(this.getStartDateTime() == null){
			return checkedEvents;
		}
		
		// Set to start of month
		current.setTimeInMillis(0);
		end.setTimeInMillis(0);
		current.set(year, (month + 11) % 12, 1, 0, 0);
		end.set(year, (month + 11) % 12, 1, 0, 0);
		
		// Set to next month
		end.add(Calendar.MONTH, 1);
		
		if(this.getStartDateTime().compareTo(current) > 0){
			current = (Calendar) this.getStartDateTime().clone();
			current.set(Calendar.SECOND, 0);
			current.set(Calendar.MILLISECOND, 0);
			
		}
		
		while(current.before(end)){
			Event event = getRecurredEvent(current);
			Event latestEventInList = checkedEvents.get(checkedEvents.size() - 1);
			
			if(event.getStartDateTime().compareTo(end) >= 0){
				// Not in this month
				break;
			}
			
			if(event.getStartDateTime().after(latestEventInList.getStartDateTime())){
				checkedEvents.add(event);
			}
			
			// Increment to next day
			current.add(Calendar.DATE, 1);
		}
		
		return checkedEvents;
	}

	/* @@author A0088646M */
	private void updateDateTimeWithRecurrence(Calendar start, Calendar end, Calendar now, Recurrence recurrence) {
		int field = getRecurrenceField(recurrence);
		updateCalendar(field, start, end, now);

	}

	/* @@author A0088646M */
	private void updateCalendar(int field, Calendar start, Calendar end, Calendar now) {

		if (start == null) {
			return;
		}

		while (start.before(now)) {
			start.add(field, 1);

			if (end != null) {
				end.add(field, 1);
			}
		}
	}

	/* @@author A0088646M */
	private int getRecurrenceField(Recurrence recurrence) {
		int field = -1;

		switch (recurrence) {
		case DAILY:
			field = Calendar.DATE;
			break;
		case WEEKLY:
			field = Calendar.WEEK_OF_YEAR;
			break;
		case MONTHLY:
			field = Calendar.MONTH;
			break;
		case YEARLY:
			field = Calendar.YEAR;
			break;
		default:
			break;
		}

		return field;
	}
}
