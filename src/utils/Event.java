package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import sun.rmi.runtime.Log;

public class Event {
	private String id;
	private String title;
	private Calendar startDateTime;
	private Calendar endDateTime;
	private Priority priority;
	private String location;
	private String notes;
	private Calendar reminder;
	private List<String> groups;

	public Event() {
		this.id = null;
		this.title = null;
		this.startDateTime = null;
		this.endDateTime = null;
		this.priority = null;
		this.location = null;
		this.notes = null;
		this.reminder = null;
		this.groups = new ArrayList<String>();
	}

	public String toString() {
		String eventString = "";

		eventString += String.format("id: %s, ", (this.id != null) ? this.id : "null");
		eventString += String.format("title: %s, ", (this.title != null) ? this.title : "null");
		eventString += String.format("startDateTime: %s, ", (this.startDateTime != null) ? this.startDateTime.getTime() : "null");
		eventString += String.format("endDateTime: %s, ", (this.endDateTime != null) ? this.endDateTime.getTime() : "null");
		eventString += String.format("priority: %s, ", (this.priority != null) ? this.priority.name() : "null");
		eventString += String.format("location: %s, ", (this.location != null) ? this.location : "null");
		eventString += String.format("notes: %s, ", (this.notes != null) ? this.notes : "null");
		eventString += String.format("reminder: %s, ", (this.reminder != null) ? this.reminder.getTime() : "null");
		eventString += String.format("groups: %s, ", Arrays.toString(this.groups.toArray()));

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

	public Calendar getReminder() {
		return reminder;
	}

	public void setReminder(Calendar reminder) {
		this.reminder = reminder;
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

}
