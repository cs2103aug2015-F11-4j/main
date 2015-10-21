package calendrier;

import java.util.Calendar;
import java.util.Scanner;

import utils.Command;
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

		// Commands without additional arguments
		if (userInput.equals("view all")) {
			pc.setCommand(Command.VIEW_ALL);
			return pc;
		} else if (userInput.equals("help")) {
			pc.setCommand(Command.HELP);
			return pc;
		} else if (userInput.equals("exit")) {
			pc.setCommand(Command.EXIT);
			return pc;
		} else if (userInput.equals("previous")) {
			pc.setCommand(Command.PREVIOUS);
			return pc;
		} else if (userInput.equals("next")) {
			pc.setCommand(Command.NEXT);
			return pc;
		}

		Scanner lineTokens = new Scanner(userInput);
		String command = lineTokens.next();

		if (command.equals("save")) {
			// save in my desktop
			String nextWord = lineTokens.next();
			if (nextWord.equals("in")) {
				String saveLocation = lineTokens.nextLine().trim();
				pc.setCommand(Command.STORAGE_LOCATION);
				pc.setStorageLocation(saveLocation);
				lineTokens.close();
				return pc;
			}
		}
		lineTokens.close();
		String inputAfterCommand = userInput.substring(userInput.indexOf(" ") + 1);

		if (command.equals("undo")) {
			// e.g. undo 2
			pc.setCommand(Command.UNDO);
			pc.setId(inputAfterCommand);
		} else if (command.equals("undelete")) {
			// e.g. undelete 3
			pc.setCommand(Command.UNDELETE);
			pc.setId(inputAfterCommand);
		} else if (command.equals("view")) {
			// e.g. view 2
			pc.setCommand(Command.VIEW);
			pc.setId(inputAfterCommand);
		} else if (command.equals("delete")) {
			// e.g. delete 2
			pc.setCommand(Command.DELETE);
			pc.setId(inputAfterCommand);
		} else if (command.equals("filter")) {
			// e.g. filter group personal OR filter priority very high OR
			// filter startdate yyyy/mm/dd OR filter enddate yyyy/mm/dd
			pc.setCommand(Command.FILTER);
			setFilterParameters(new Scanner(inputAfterCommand), pc);
		} else if (command.equals("update")) {
			/*
			 * Case 1: not a deadline No. of parameters: 12 e.g. update 3, title
			 * repeat sleep drink eat, startdate 2015/12/29, starttime 13.37,
			 * enddate 2015/12/30, endtime 14.44, group my personal group priority very low, 
			 * location my home, notes must do, recurring no, reminderdate 2015/12/30,
			 * remindertime 15.30
			 */
			pc.setCommand(Command.UPDATE);
			
			int idEndIndex;
			String id;
			
			idEndIndex = inputAfterCommand.indexOf(",");
			if (idEndIndex == -1) {
				id = inputAfterCommand.substring(0);
			} else {
				id = inputAfterCommand.substring(0, idEndIndex);
			}
			pc.setId(id);

			String title = getAttributeFromInput(inputAfterCommand, "title", 5);
			if (title != null) {
				pc.setTitle(title);
			}

			String startDate = getAttributeFromInput(inputAfterCommand, "startdate", 9);
			String startTime = getAttributeFromInput(inputAfterCommand, "starttime", 9);
			if (startDate != null && startTime != null) {
				Calendar cal = dateAndTimeToCalendar(startDate, startTime);
				pc.setStartDateTime(cal);
			} else if (startDate != null) {
				Calendar cal = dateToCalendar(startDate);
				pc.setStartDateTime(cal);
			} else if (startTime != null) {
				Calendar cal = timeToCalendar(startTime);
				pc.setStartDateTime(cal);
			}

			String endDate = getAttributeFromInput(inputAfterCommand, "enddate", 7);
			String endTime = getAttributeFromInput(inputAfterCommand, "endtime", 7);
			if (endDate != null && endTime != null) {
				Calendar cal = dateAndTimeToCalendar(endDate, endTime);
				pc.setEndDateTime(cal);
			} else if (endDate != null) {
				Calendar cal = dateToCalendar(endDate);
				pc.setEndDateTime(cal);
			} else if (endTime != null) {
				Calendar cal = timeToCalendar(endTime);
				pc.setEndDateTime(cal);
			}

			String priority = getAttributeFromInput(inputAfterCommand, "priority", 8);
			if (priority != null) {
				setPriority(pc, priority);
			}
			
			String group = getAttributeFromInput(inputAfterCommand, "group", 5);
			if (group != null) {
				pc.setGroup(group);
			}

			String location = getAttributeFromInput(inputAfterCommand, "location", 8);
			if (location != null) {
				pc.setLocation(location);
			}

			String notes = getAttributeFromInput(inputAfterCommand, "notes", 5);
			if (notes != null) {
				pc.setNotes(notes);
			}

			String recurring = getAttributeFromInput(inputAfterCommand, "recurring", 9);
			if (recurring != null) {
				pc.setIsRecurring(recurring.equals("yes"));
			}

			String reminderDate = getAttributeFromInput(inputAfterCommand, "reminderdate", 12);
			String reminderTime = getAttributeFromInput(inputAfterCommand, "remindertime", 12);
			if (reminderDate != null && reminderTime != null) {
				reminderTime = reminderTime.trim();
				Calendar cal = dateAndTimeToCalendar(reminderDate, reminderTime);
				pc.setReminder(cal);
			} else if (reminderDate != null) {
				Calendar cal = dateToCalendar(reminderDate);
				pc.setReminder(cal);
			} else if (reminderTime != null) {
				reminderTime = reminderTime.trim();
				Calendar cal = timeToCalendar(reminderTime);
				pc.setReminder(cal);
			}

		} else if (command.equals("add")) {
			pc.setCommand(Command.ADD);
			int numCurrentTask = ParsedCommand.getNumCurrentTask();
			pc.setId(String.valueOf(numCurrentTask + 1));
			ParsedCommand.setNumCurrentTask(numCurrentTask + 1);

			/*
			 * Case 1: not a deadline No. of parameters: 11 e.g. add eat
			 * sleep drink repeat, startdate 2015/12/29, starttime 13.37,
			 * enddate 2015/12/30, endtime 14.44, priority very low, group my personal group, 
			 * location my home, notes must do, recurring no, reminderdate 2015/12/30,
			 */
			
			int titleEndIndex;
			String title;
			
			titleEndIndex = inputAfterCommand.indexOf(",");
			if (titleEndIndex == -1) {
				title = inputAfterCommand.substring(0);
			} else {
				title = inputAfterCommand.substring(0, titleEndIndex);
			}
			pc.setTitle(title);

			String startDate = getAttributeFromInput(inputAfterCommand, "startdate", 9);
			String startTime = getAttributeFromInput(inputAfterCommand, "starttime", 9);
			if (startDate != null && startTime != null) {
				Calendar cal = dateAndTimeToCalendar(startDate, startTime);
				pc.setStartDateTime(cal);
			} else if (startDate != null) {
				Calendar cal = dateToCalendar(startDate);
				pc.setStartDateTime(cal);
			} else if (startTime != null) {
				Calendar cal = timeToCalendar(startTime);
				pc.setStartDateTime(cal);
			}

			String endDate = getAttributeFromInput(inputAfterCommand, "enddate", 7);
			String endTime = getAttributeFromInput(inputAfterCommand, "endtime", 7);
			if (endDate != null && endTime != null) {
				Calendar cal = dateAndTimeToCalendar(endDate, endTime);
				pc.setEndDateTime(cal);
			} else if (endDate != null) {
				Calendar cal = dateToCalendar(endDate);
				pc.setEndDateTime(cal);
			} else if (endTime != null) {
				Calendar cal = timeToCalendar(endTime);
				pc.setEndDateTime(cal);
			}

			String priority = getAttributeFromInput(inputAfterCommand, "priority", 8);
			if (priority != null) {
				setPriority(pc, priority);
			}
			
			String group = getAttributeFromInput(inputAfterCommand, "group", 5);
			if (group != null) {
				pc.setGroup(group);
			}

			String location = getAttributeFromInput(inputAfterCommand, "location", 8);
			if (location != null) {
				pc.setLocation(location);
			}

			String notes = getAttributeFromInput(inputAfterCommand, "notes", 5);
			if (notes != null) {
				pc.setNotes(notes);
			}

			String recurring = getAttributeFromInput(inputAfterCommand, "recurring", 9);
			if (recurring != null) {
				pc.setIsRecurring(recurring.equals("yes"));
			}

			String reminderDate = getAttributeFromInput(inputAfterCommand, "reminderdate", 12);
			String reminderTime = getAttributeFromInput(inputAfterCommand, "remindertime", 12);
			if (reminderDate != null && reminderTime != null) {
				Calendar cal = dateAndTimeToCalendar(reminderDate, reminderTime);
				pc.setReminder(cal);
			} else if (reminderDate != null) {
				Calendar cal = dateToCalendar(reminderDate);
				pc.setReminder(cal);
			} else if (reminderTime != null) {
				Calendar cal = timeToCalendar(reminderTime);
				pc.setReminder(cal);
			}
		}
		if (pc.getCommand() == null) {
			// parseFlexibleCommand(pc, userInput);
		} 
		return pc;
	}
	
	/*
	public ParsedCommand parseFlexibleCommand(ParsedCommand pc, String userInput) {
		if (userInput.contains("-all")) {
			// e.g. view all: -all
			pc.setCommand(Command.VIEW_ALL);
			return pc;
		} else if (userInput.contains("-h")) {
			// e.g help: -h
			pc.setCommand(Command.HELP);
			return pc;
		} else if (userInput.contains("-e")) {
			// e.g. exit: -e
			pc.setCommand(Command.EXIT);
			return pc;
		} else if (userInput.contains("-p")) {
			// e.g. previous: -p
			pc.setCommand(Command.PREVIOUS);
			return pc;
		} else if (userInput.contains("-n")) {
			// e.g. next: -n
			pc.setCommand(Command.NEXT);
			return pc;
		}
		
		String inputAfterCommand = "";
		if (userInput != null && userInput.length() != 0) {
			inputAfterCommand = userInput.substring(userInput.indexOf(" ")+1);
		}
		if (userInput.contains("-s")) {
			// e.g. save in desktop: -s desktop			
			pc.setCommand(Command.STORAGE_LOCATION);
			pc.setStorageLocation(inputAfterCommand);
		} else if (userInput.contains("-un")) {
			// e.g. undo 3: -un 3
			pc.setCommand(Command.UNDO);
			pc.setId(inputAfterCommand);
		} else if (userInput.contains("-undel")) {
			// e.g. undelete 4: -undel 4
			pc.setCommand(Command.UNDELETE);
			pc.setId(inputAfterCommand);
		} else if (userInput.contains("-v")) {
			// e.g. view 2: -v 2
			pc.setCommand(Command.VIEW);
			pc.setId(inputAfterCommand);
		} else if (userInput.contains("-d")) {
			// e.g. delete 1: -d 1
			pc.setCommand(Command.DELETE);
			pc.setId(inputAfterCommand);
		} else if (userInput.equals("-fil")) {
			// e.g. -fil -g personal stuff OR -fil -p very high OR
			// -fil -sd yyyy/mm/dd OR -fil -ed yyyy/mm/dd
			pc.setCommand(Command.FILTER);
			setFlexibleFilterParameters(new Scanner(inputAfterCommand), pc);
		} else if (userInput.contains('-up")) {
			pc.setCommand(Command.UPDATE);
				
			int idEndIndex;
			String id;
			
			idEndIndex = inputAfterCommand.indexOf(",");
			if (idEndIndex == -1) {
				id = inputAfterCommand.substring(0);
			} else {
				id = inputAfterCommand.substring(0, idEndIndex);
			}
			pc.setId(id);
			
			String title = getAttributeFromInput(inputAfterCommand, "-t", 2);
			if (title != null) {
				pc.setTitle(title);
			}
		
		// e.g. e.g. -up 2, -t do homework, -st 2015/10/30, -ed 2015/11/12, -st 12.34, -et 13.37, 
		//  -g personal circle, -l my home, -p very high,  
		/// -n remember to do, -r yes, -rd 2015/11/11, -rt 11.11 
		 
		return pc;
	}
	
	public void setFlexibleFilterParameters(Scanner inputAfterCommand, ParsedCommand pc) {
		String filterParameter = inputAfterCommand.next();
		String filterValue = inputAfterCommand.nextLine().trim();

		Calendar cal;

		if (filterParameter.contains("-g")) {
			pc.setGroup(filterValue);
		} else if (filterParameter.contains("-sd")) {
			cal = dateToCalendar(filterValue);
			pc.setStartDateTime(cal);
		} else if (filterParameter.contains("-ed")) {
			cal = dateToCalendar(filterValue);
			pc.setEndDateTime(cal);
		} else if (filterParameter.contains("-p")) {
			setPriority(pc, filterValue);
		}
	}
	
	*/

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

	public static Calendar timeToCalendar(String time) {
		Calendar cal = Calendar.getInstance();
		String[] timeParam = time.split("\\.");
		int hour = Integer.parseInt(timeParam[0]);
		int minute = Integer.parseInt(timeParam[1]);

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
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

	public static String getAttributeFromInput(String inputAfterCommand, String attr, int attrLength) {
		String result;
		if (inputAfterCommand.contains(attr)) {
			int index = inputAfterCommand.indexOf(attr) + attrLength + 1;
			int endIndex = inputAfterCommand.indexOf(",", index);
			
			//System.out.println("index of " + attr + ": " + index);
			//System.out.println("endIndex: "  + attr + ": " + endIndex);

			// last attribute input of the line
			if (endIndex == -1) {
				result = inputAfterCommand.substring(index);
			} else {
				result = inputAfterCommand.substring(index, endIndex);
			}
			// System.out.println("result: " + result);
		} else {
			return null;
		}
		//System.out.println(attr + ": " + result);
		return result;
	}

}
