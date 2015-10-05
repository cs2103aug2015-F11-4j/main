package calendrier;

import utils.Command;
import utils.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import utils.ParsedCommand;
import utils.Priority;

public class Parser {
	/*
	 * Only view, delete, filter, storage has a title. Other commands(add,
	 * update) are broken down into individual tokens.
	 * 
	 */

	public ParsedCommand parse(String userInput) {
		ParsedCommand pc = new ParsedCommand();

		if (userInput.equals("view all")) {
			pc.setCommand(Command.VIEW_ALL);
			return pc;
		}

		Scanner lineTokens = new Scanner(userInput);
		String command = lineTokens.next();
		String inputAfterCommand = userInput.substring(userInput.indexOf(" ") + 1);

		if (command.equals("undo")) {
			// e.g. undo id 2
			String id = inputAfterCommand.substring(inputAfterCommand.indexOf(" ") + 1);
			pc.setCommand(Command.UNDO);
			pc.setId(id);
		} else if (command.equals("undelete")) {
			// e.g. undelete id 3
			String id = inputAfterCommand.substring(inputAfterCommand.indexOf(" ") + 1);
			pc.setCommand(Command.UNDELETE);
			pc.setId(id);
		} else if (command.equals("view")) {
			// e.g. view id 2
			String id = inputAfterCommand.substring(inputAfterCommand.indexOf(" ") + 1);
			pc.setCommand(Command.VIEW);
			pc.setId(id);
		} else if (command.equals("delete")) {
			// e.g. delete id 2
			String id = inputAfterCommand.substring(inputAfterCommand.indexOf(" ") + 1);
			pc.setCommand(Command.DELETE);
			pc.setId(id);
		} else if (command.equals("filter")) {
			// e.g. filter group personal OR filter priority very high OR
			// filter startdate yyyy/mm/dd OR filter enddate yyyy/mm/dd
			pc.setCommand(Command.FILTER);
			setFilterParameters(new Scanner(inputAfterCommand), pc);
		} else if (command.equals("update")) {
			/*
			 * Case 1: not a deadline
			 * No. of parameters: 12
			 * e.g. update id 3, title repeat sleep drink eat, startdate 2015/12/29, 
			 * 		starttime 13.37, enddate 2015/12/30, endtime 14.44, priority very low,
			 * 		location my home, notes must do, recurring no, reminderdate
			 * 		2015/12/30, remindertime 15.30 
			 * 
			 * Case 2: deadline
			 * No. of parameters: 10 
			 * e.g. update id 4, title repeat sleep drink eat,
			 * 		enddate 2015/12/29, enddate 13.37, priority very low, location my
			 * 		home, notes must do, recurring no, reminderdate 2015/12/30,
			 * 		remindertime 15.30
			 */
			String[] updateInfo = inputAfterCommand.split(",");
			int paramLength = updateInfo.length;
			
			pc.setCommand(Command.UPDATE);

			String id = updateInfo[0].substring(updateInfo[0].indexOf(" ") + 1);
			pc.setId(id);

			updateInfo[1] = updateInfo[1].trim();
			String title = updateInfo[1].substring(updateInfo[1].indexOf(" ") + 1);
			pc.setTitle(title);

			// Task Is not a deadline
			if (paramLength == 12) {
				updateInfo[2] = updateInfo[2].trim();
				String startDate = updateInfo[2].substring(updateInfo[2].indexOf(" ") + 1);

				updateInfo[3] = updateInfo[3].trim();
				String startTime = updateInfo[3].substring(updateInfo[3].indexOf(" ") + 1);

				Calendar cal = dateAndTimeToCalendar(startDate, startTime);
				pc.setStartDateTime(cal);

				updateInfo[4] = updateInfo[4].trim();
				String endDate = updateInfo[4].substring(updateInfo[4].indexOf(" ") + 1);

				updateInfo[5] = updateInfo[5].trim();
				String endTime = updateInfo[5].substring(updateInfo[5].indexOf(" ") + 1);
				
				Calendar cal2 = Calendar.getInstance();
				cal2 = dateAndTimeToCalendar(endDate, endTime);
				pc.setEndDateTime(cal2);
				
				updateInfo[6] = updateInfo[6].trim();
				String priority = updateInfo[6].substring(updateInfo[6].indexOf(" ") + 1);
				setPriority(pc, priority);
				
				updateInfo[7] = updateInfo[7].trim();
				String location = updateInfo[7].substring(updateInfo[7].indexOf(" ") + 1);
				pc.setLocation(location);
				
				updateInfo[8] = updateInfo[8].trim();
				String notes = updateInfo[8].substring(updateInfo[8].indexOf(" ") + 1);
				pc.setNotes(notes);
				
				updateInfo[9] = updateInfo[9].trim();
				String recurring = updateInfo[9].substring(updateInfo[9].indexOf(" ") + 1);
				boolean isRecurring = recurring.equals("yes");
				pc.setIsRecurring(isRecurring);
				
				updateInfo[10] = updateInfo[10].trim();
				String reminderDate = updateInfo[10].substring(updateInfo[10].indexOf(" ") + 1);
				
				updateInfo[11] = updateInfo[11].trim();
				String reminderTime = updateInfo[11].substring(updateInfo[11].indexOf(" ") + 1);
				
				Calendar cal3 = Calendar.getInstance();
				cal3 = dateAndTimeToCalendar(reminderDate, reminderTime);
				pc.setReminder(cal3);
				
			}
			// Task is a deadline
			else if (paramLength == 10) {
				updateInfo[2] = updateInfo[2].trim();
				String endDate = updateInfo[2].substring(updateInfo[2].indexOf(" ") + 1);

				updateInfo[3] = updateInfo[3].trim();
				String endTime = updateInfo[3].substring(updateInfo[3].indexOf(" ") + 1);
				
				Calendar cal4 = Calendar.getInstance();
				cal4 = dateAndTimeToCalendar(endDate, endTime);
				pc.setEndDateTime(cal4);
				
				updateInfo[4] = updateInfo[4].trim();
				String priority = updateInfo[4].substring(updateInfo[4].indexOf(" ") + 1);
				setPriority(pc, priority);
				
				updateInfo[5] = updateInfo[5].trim();
				String location = updateInfo[5].substring(updateInfo[5].indexOf(" ") + 1);
				pc.setLocation(location);
				
				updateInfo[6] = updateInfo[6].trim();
				String notes = updateInfo[6].substring(updateInfo[6].indexOf(" ") + 1);
				pc.setNotes(notes);
				
				updateInfo[7] = updateInfo[7].trim();
				String recurring = updateInfo[7].substring(updateInfo[7].indexOf(" ") + 1);
				boolean isRecurring = recurring.equals("yes");
				pc.setIsRecurring(isRecurring);
				
				updateInfo[8] = updateInfo[8].trim();
				String reminderDate = updateInfo[8].substring(updateInfo[8].indexOf(" ") + 1);
				
				updateInfo[9] = updateInfo[9].trim();
				String reminderTime = updateInfo[9].substring(updateInfo[9].indexOf(" ") + 1);
				
				Calendar cal5 = Calendar.getInstance();
				cal5 = dateAndTimeToCalendar(reminderDate, reminderTime);
				pc.setReminder(cal5);
			}
		} else if (command.equals("savein")) {
			// savein my desktop
			pc.setCommand(Command.STORAGE_LOCATION);
			pc.setStorageLocation(inputAfterCommand);
		} else if (command.equals("add")) {
			int numCurrentTask = ParsedCommand.getNumCurrentTask();
			pc.setId(String.valueOf(numCurrentTask+1));
			ParsedCommand.setNumCurrentTask(numCurrentTask+1);
			
			/*
			 * Case 1: not a deadline 
			 * No. of parameters: 11
			 * e.g. add title eat sleep drink repeat, startdate 2015/12/29, starttime 13.37, 
			 * enddate 2015/12/30, endtime 14.44, priority very low, location my home, 
			 * notes must do, recurring no, reminderdate 2015/12/30, remindertime 15.30
			 * 
			 * Case 2: deadline 
			 * No. of parameters: 9
			 * e.g. add title eat sleep drink repeat, enddate 2015/12/29, enddate 13.37, 
			 * priority very low, location my home, notes must do, recurring no, 
			 * reminderdate 2015/12/30, remindertime 15.30
			 */
			
			
			String[] addInfo = inputAfterCommand.split(",");
			int paramLength = addInfo.length;
			pc.setCommand(Command.ADD);
			
			String title = addInfo[0].substring(addInfo[0].indexOf(" ") + 1);
			pc.setTitle(title);
			
			// Task is not a deadline
			if (paramLength == 11) {
				addInfo[1] = addInfo[1].trim();
				String startDate = addInfo[1].substring(addInfo[1].indexOf(" ") + 1);
				
				addInfo[2] = addInfo[2].trim();
				String startTime = addInfo[2].substring(addInfo[2].indexOf(" ") + 1);
				
				Calendar cal6 = Calendar.getInstance();
				cal6 = dateAndTimeToCalendar(startDate, startTime);
				pc.setStartDateTime(cal6);
	
				addInfo[3] = addInfo[3].trim();
				String endDate = addInfo[3].substring(addInfo[3].indexOf(" ") + 1);
	
				addInfo[4] = addInfo[4].trim();
				String endTime = addInfo[4].substring(addInfo[4].indexOf(" ") + 1);
				
				Calendar cal7 = Calendar.getInstance();
				cal7 = dateAndTimeToCalendar(endDate, endTime);
				pc.setEndDateTime(cal7);
	
				addInfo[5] = addInfo[5].trim();
				String priority = addInfo[5].substring(addInfo[5].indexOf(" ") + 1);
				setPriority(pc, priority);
	
				addInfo[6] = addInfo[6].trim();
				String location = addInfo[6].substring(addInfo[6].indexOf(" ") + 1);
				pc.setLocation(location);
	
				addInfo[7] = addInfo[7].trim();
				String notes = addInfo[7].substring(addInfo[7].indexOf(" ") + 1);
				pc.setNotes(notes);
				
				addInfo[8] = addInfo[8].trim();
				String recurring = addInfo[8].substring(addInfo[8].indexOf(" ") + 1);
				boolean isRecurring = recurring.equals("yes");
				pc.setIsRecurring(isRecurring);
				
				addInfo[9] = addInfo[9].trim();
				String reminderDate = addInfo[9].substring(addInfo[9].indexOf(" ") + 1);
				
				addInfo[10] = addInfo[10].trim();
				String reminderTime = addInfo[10].substring(addInfo[10].indexOf(" ") + 1);
				
				Calendar cal8 = Calendar.getInstance();
				cal8 = dateAndTimeToCalendar(reminderDate, reminderTime);
				pc.setReminder(cal8);
			}
			// Task is a deadline
			else if (paramLength == 9) {
				// 1: endDate
				addInfo[1] = addInfo[1].trim();
				String endDate = addInfo[1].substring(addInfo[1].indexOf(" ") + 1);
				
				// 2: endTime
				addInfo[2] = addInfo[2].trim();
				String endTime = addInfo[2].substring(addInfo[2].indexOf(" ") + 1);
				
				Calendar cal9 = Calendar.getInstance();
				cal9 = dateAndTimeToCalendar(endDate, endTime);
				pc.setEndDateTime(cal9);
				
				// 3: priority
				addInfo[3] = addInfo[3].trim();
				String priority = addInfo[3].substring(addInfo[3].indexOf(" ") + 1);
				setPriority(pc, priority);
				
				// 4: location
				addInfo[4] = addInfo[4].trim();
				String location = addInfo[4].substring(addInfo[4].indexOf(" ") + 1);
				pc.setLocation(location);
				
				// 5: notes
				addInfo[5] = addInfo[5].trim();
				String notes = addInfo[5].substring(addInfo[5].indexOf(" ") + 1);
				pc.setNotes(notes);
				
				// 6: recurring
				addInfo[6] = addInfo[6].trim();
				String recurring = addInfo[6].substring(addInfo[6].indexOf(" ") + 1);
				boolean isRecurring = recurring.equals("yes");
				pc.setIsRecurring(isRecurring);
				
				// 7: reminderdate
				addInfo[7] = addInfo[7].trim();
				String reminderDate = addInfo[7].substring(addInfo[7].indexOf(" ") + 1);
				
				// 8: reminderTime
				addInfo[8] = addInfo[8].trim();
				String reminderTime = addInfo[8].substring(addInfo[8].indexOf(" ") + 1);
				
				Calendar cal10 = Calendar.getInstance();
				cal10 = dateAndTimeToCalendar(reminderDate, reminderTime);
				pc.setReminder(cal10);
			}
		}
	return pc;
	}

	public void setFilterParameters(Scanner inputAfterCommand, ParsedCommand pc) {
		String filterParameter = inputAfterCommand.next();
		String filterValue = inputAfterCommand.nextLine().trim();

		Calendar cal;

		if (filterParameter.equals("group")) {
			pc.setGroup(filterValue);
		} else if (filterParameter.equals("startdate")) {
			cal = dateToCalendar(filterValue);
			pc.setStartDateTime(cal);
		} else if (filterParameter.equals("enddate")) {
			cal = dateToCalendar(filterValue);
			pc.setEndDateTime(cal);
		} else if (filterParameter.equals("priority")) {
			
			setPriority(pc, filterValue);
		}
	}

	public static Calendar dateAndTimeToCalendar(String date, String time) {
		Calendar cal = Calendar.getInstance();
		String[] timeParam = time.split("\\.");
		int hour = Integer.parseInt(timeParam[0]);
		int minute = Integer.parseInt(timeParam[1]);

		String[] dateParam = date.split("/");
		int year = Integer.parseInt(dateParam[0]);
		int month = Integer.parseInt(dateParam[1]) - 1; // month is 0 based
		int day = Integer.parseInt(dateParam[2]);

		cal.set(year, month, day, hour, minute);
		return cal;
	}

	public static Calendar dateToCalendar(String date) {
		Calendar cal = Calendar.getInstance();
		String[] dateParam = date.split("/");
		int year = Integer.parseInt(dateParam[0]);
		int month = Integer.parseInt(dateParam[1]) - 1; // month is 0 based
		int day = Integer.parseInt(dateParam[2]);

		cal.set(year, month, day);
		return cal;
	}
	
	public static void setPriority(ParsedCommand pc, String priority) {
		if (priority.equals("very low")) {
			pc.setPriority(Priority.VERY_LOW);
		} else if (priority.equals("low")) {
			pc.setPriority(Priority.LOW);
		} else if (priority.equals("medium")) {
			pc.setPriority(Priority.MEDIUM);
		} else if (priority.equals("high")) {
			pc.setPriority(Priority.HIGH);
		} else if (priority.equals("very high")) {
			pc.setPriority(Priority.VERY_HIGH);
		}
	}

}
