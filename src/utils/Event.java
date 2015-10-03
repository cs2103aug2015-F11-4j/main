package utils;

import java.util.Calendar;

public class Event {
	private String id;
	private String title;
	private Calendar startDateTime;
	private Calendar endDateTime;
	private Priority priority;
	private String location;
	private String notes;
	private Calendar reminder;

	public Event() {
		this.id = null;
		this.title = null;
		this.startDateTime = null;
		this.endDateTime = null;
		this.priority = null;
		this.location = null;
		this.notes = null;
		this.reminder = null;
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

	public Calendar getReminder() {
		return reminder;
	}

	public void setReminder(Calendar reminder) {
		this.reminder = reminder;
	}

}
