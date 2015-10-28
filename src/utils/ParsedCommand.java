package utils;

import java.util.ArrayList;
import java.util.Calendar;

public class ParsedCommand {
	private Command command = null;
	private String id = null;
	private String mainId = null;	// For subtask to reference to the main task
	
	private static int numCurrentTask = 0;
	
	private String title = null;
	private Calendar startDateTime = null;
	private Calendar endDateTime;	// CANT BE NULL FOR NOW
	private Priority priority = null;
	private String location = null;
	private String notes = null;
	private boolean isRecurring = false;	// not V0.1, revisit in future.....
	private ArrayList<Calendar> reminder = null;
	
	private String group = null;
	
	private String storageLocation = null;
	
	public Command getCommand() { return command; }
	public void setCommand(Command command) { this.command = command; }
	
	public String getId() { return id; }
	public void setId(String id) { this.id= id;}
	
	public String getMainId() { return mainId; }
	public void setMainId(String mainId) { this.mainId = mainId; }
	
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
	
	public boolean getIsRecurring() { return isRecurring; }
	public void setIsRecurring(boolean isRecurring) { this.isRecurring = isRecurring; }
	
	public ArrayList<Calendar> getReminder() { return reminder; }
	public void setReminder(ArrayList<Calendar> reminder) { this.reminder = reminder; }
	
	public String getGroup() { return group; }
	public void setGroup(String group) { this.group = group; }
	
	public String getStorageLocation() { return storageLocation; }
	public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
	
}
	

	