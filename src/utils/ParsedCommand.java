package utils;

import java.util.Calendar;

public class ParsedCommand {
	private Command command = null;
	private String id = null;	// for update, undo, undelete
	private static int numCurrentTask = 0;	// Used to generate task id for add task
	
	private String title = null;
	private Calendar startDateTime = null;
	private Calendar endDateTime;	// CANT BE NULL FOR NOW
	private Priority priority = null;	// very low, low, medium, high, very high
	private String location = null;	// any string
	private String notes = null;		// any string
	private boolean isRecurring = false;	// not V0.1, revisit in future.....
	private Calendar reminder = null;
	
	private String group = null;	// for filter
	
	private String storageLocation;
	
	public Command getCommand() { return command; }
	public void setCommand(Command command) { this.command = command; }
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id;}
	
	public static int getNumCurrentTask() { return numCurrentTask; }
	public static void setNumCurrentTask(int numCurrentTask) { ParsedCommand.numCurrentTask = numCurrentTask; }
	
	public String getTitle() {return title; }
	public void setTitle(String title) { this.title = title; }
	
	public Calendar getStartDateTime() { return startDateTime; }
	public void setStartDateTime(Calendar startDateTime) { this.startDateTime = startDateTime; }
	
	public Calendar getEndDateTime() { return endDateTime; }
	public void setEndDateTime(Calendar endDateTime) { this.endDateTime = endDateTime; }
	
	public Priority getPriority() { return priority; }
	public void setPriority(Priority priority) { this.priority = priority; }
	
	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
	
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
	
	public boolean isRecurring() { return isRecurring; }
	public void setRecurring(boolean recurring) { this.isRecurring = recurring; }
	
	public Calendar getReminder() { return reminder; }
	public void setReminder(Calendar reminder) { this.reminder = reminder; }
	
	public String getGroup() { return group; }
	public void setGroup(String group) { this.group = group; }
}
	

	