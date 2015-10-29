package calendrier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.UUID;

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
		
		if (userInput.equals("") || userInput == null)
			return pc;
		
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
		} else if (userInput.equals("undo")) {
			// e.g. undo
			pc.setCommand(Command.UNDO);
			return pc;
		} else if (userInput.equals("undelete")) {
			// e.g. undelete
			pc.setCommand(Command.UNDELETE);
			return pc;
		} else if (userInput.equals("view month")) {
			// e.g. view month
			pc.setCommand(Command.VIEW_MONTH);
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
		String inputAfterCommand = null;
		
		if (userInput.indexOf(" ") != -1)
			inputAfterCommand = userInput.substring(userInput.indexOf(" ") + 1);

		if (command.equals("view")) {
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
			 * 
			 * Case 2: a deadline
			 * e.g. add eat sleep drink repeat, deadlinedate 2015/12/30, deadlinetime 14.44, 
			 * priority very low, group my personal group, location my home, notes must do, 
			 * recurring no, reminderdate 2015/12/29 2015/12/29, remindertime 12.34 23.45
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

			determineDeadlineAndSettle(pc, inputAfterCommand);

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

			setReminder(pc, inputAfterCommand, "reminderdate", "remindertime", 12);

		} else if (command.equals("add")) {
			pc.setCommand(Command.ADD);
			
			int numCurrentTask = ParsedCommand.getNumCurrentTask();
//			pc.setId(String.valueOf(numCurrentTask + 1));
			pc.setId(UUID.randomUUID().toString());
			ParsedCommand.setNumCurrentTask(numCurrentTask + 1);
			

			/*
			 * Case 1: not a deadline No. of parameters: 12 e.g. add eat
			 * sleep drink repeat, startdate 2015/12/29, starttime 13.37,
			 * enddate 2015/12/30, endtime 14.44, priority very low, group my personal group, 
			 * location my home, notes must do, recurring no, reminderdate 2015/12/29 2015/12/29, 
			 * remindertime 12.34 23.45
			 * 
			 * Case 2: a deadline
			 * e.g. add eat sleep drink repeat, deadlinedate 2015/12/30, deadlinetime 14.44, 
			 * priority very low, group my personal group, location my home, notes must do, 
			 * recurring no, reminderdate 2015/12/29 2015/12/29, remindertime 12.34 23.45
			 * 
			 * Case 3: subtask
			 * e.g. add subtask sleep drink repeat to 1, .......
			 */
			
			int titleEndIndex, titleIndex;
			String title;
			
			StringTokenizer st = new StringTokenizer(inputAfterCommand);
			String wordAfterCommand = st.nextToken();
			if (wordAfterCommand.equals("subtask")) {
				int wordAfterCommandIndex = inputAfterCommand.indexOf(wordAfterCommand);
				titleIndex = inputAfterCommand.indexOf(" ", wordAfterCommandIndex) + 1;
				titleEndIndex = inputAfterCommand.indexOf("to")-1;
				title = inputAfterCommand.substring(titleIndex, titleEndIndex);
				
				int toIndex = inputAfterCommand.indexOf("to");
				int idIndex = inputAfterCommand.indexOf(" ", toIndex) + 1;
				int idEndIndex = inputAfterCommand.indexOf(",", idIndex);
				String mainId = inputAfterCommand.substring(idIndex, idEndIndex);
				pc.setMainId(mainId);
			} else {
				titleEndIndex = inputAfterCommand.indexOf(",");
				if (titleEndIndex == -1) {
					title = inputAfterCommand.substring(0);
				} else {
					title = inputAfterCommand.substring(0, titleEndIndex);
				}
			}
			pc.setTitle(title);
			
			determineDeadlineAndSettle(pc, inputAfterCommand);

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
			
			setReminder(pc, inputAfterCommand, "reminderdate", "remindertime", 12);
			
		}
		if (pc.getCommand() == null) {
			parseShortenedCommand(pc, userInput);
		} 
		return pc;
	}
	
	
	public ParsedCommand parseShortenedCommand(ParsedCommand pc, String userInput) {
		String inputAfterCommand = "";
		String command = "";
		
		// input has more than 1 word
		if (userInput.indexOf(" ") != -1) {
			inputAfterCommand = userInput.substring(userInput.indexOf(" ")+1);
			command = userInput.substring(0, userInput.indexOf(" "));
		} else {
			command = userInput;
		}
		
		if (command.equals("-all")) {
			// e.g. view all: -all
			pc.setCommand(Command.VIEW_ALL);
			return pc;
		} else if (command.equals("-h")) {
			// e.g help: -h
			pc.setCommand(Command.HELP);
			return pc;
		} else if (command.equals("-e")) {
			// e.g. exit: -e
			pc.setCommand(Command.EXIT);
			return pc;
		} else if (command.equals("-prev")) {
			// e.g. previous: -prev
			pc.setCommand(Command.PREVIOUS);
			return pc;
		} else if (command.equals("-nxt")) {
			// e.g. next: -nxt
			pc.setCommand(Command.NEXT);
			return pc;
		} else if (command.equals("-u")) {
			// e.g. undo: -u
			pc.setCommand(Command.UNDO);
			return pc;
		} else if (command.equals("-vm")) {
			// e.g. view month: -vm
			pc.setCommand(Command.VIEW_MONTH);
			return pc;
		}
		
		if (command.equals("-s")) {
			// e.g. save in desktop: -s desktop			
			pc.setCommand(Command.STORAGE_LOCATION);
			pc.setStorageLocation(inputAfterCommand);
		}  else if (command.equals("-undel")) {
			// e.g. undelete 4: -undel 4
			pc.setCommand(Command.UNDELETE);
			pc.setId(inputAfterCommand);
		} else if (command.equals("-v")) {
			// e.g. view 2: -v 2
			pc.setCommand(Command.VIEW);
			pc.setId(inputAfterCommand);
		} else if (command.equals("-d")) {
			// e.g. delete 1: -d 1
			pc.setCommand(Command.DELETE);
			pc.setId(inputAfterCommand);
		} else if (command.equals("-f")) {
			// e.g. -f -g personal stuff OR -f -p very high OR
			// -f -sd yyyy/mm/dd OR -f -ed yyyy/mm/dd
			pc.setCommand(Command.FILTER);
			setShortenedFilterParameters(new Scanner(inputAfterCommand), pc);
		} else if (command.equals("-up")) {
			// Case 1: Not deadline
			// e.g. -up 2, -t do homework, -sd 2015/10/30, -st 12.34, -ed 2015/11/12, 
			// -et 13.37, -g personal circle, -l my home, -p very high,  
			// -n remember to do, -r yes, -rd 2015/11/11, -rt 11.11 
			
			// Case 2: Deadline
			// e.g. -up 2, -t do homework, -dd 2015/11/12, 
			// -dt 13.37, -g personal circle, -l my home, -p very high,  
			// -n remember to do, -r yes, -rd 2015/11/11, -rt 11.11
			
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
			
			determineDeadlineAndSettle(pc, inputAfterCommand);
			
			String priority = getAttributeFromInput(inputAfterCommand, "-p", 2);
			if (priority != null) {
				setPriority(pc, priority);
			}
			
			String group = getAttributeFromInput(inputAfterCommand, "-g", 2);
			if (group != null) {
				pc.setGroup(group);
			}

			String location = getAttributeFromInput(inputAfterCommand, "-l", 2);
			if (location != null) {
				pc.setLocation(location);
			}

			String notes = getAttributeFromInput(inputAfterCommand, "-n", 2);
			if (notes != null) {
				pc.setNotes(notes);
			}

			String recurring = getAttributeFromInput(inputAfterCommand, "-r", 2);
			if (recurring != null) {
				pc.setIsRecurring(recurring.equals("yes"));
			}

			setReminder(pc, inputAfterCommand, "-rd", "-rt", 3);	
		}
		
		else if (command.contains("-a")) {
			// Case 1: Not deadline
			// e.g. -a eat drink sleep repeat, -sd 2015/10/12, -st 12.34, ed 2015/10/14, 
			// -et 13.37, -p very high, -g secret group, -l my home, -n must do, 
			// -r no, -rd 2015/10/13 2015/10/14, -rt 13.37 11.11
			
			// Case 2: Deadline
			// e.g. -a eat drink sleep repeat, -dd 2015/10/14, -dt 13.37, -p very high, 
			// -g secret group, -l my home, -n must do, -r no, -rd 2015/10/13 2015/10/14, 
			// -rt 13.37 11.11
			
			// Case 3: Subtask
			// e.g. -a subtask drink repeat to 1, .......
			
			pc.setCommand(Command.ADD);
			
			
			int titleEndIndex, titleIndex;
			String title;
			
			StringTokenizer st = new StringTokenizer(inputAfterCommand);
			String wordAfterCommand = st.nextToken();
			if (wordAfterCommand.equals("subtask")) {
				int wordAfterCommandIndex = inputAfterCommand.indexOf(wordAfterCommand);
				titleIndex = inputAfterCommand.indexOf(" ", wordAfterCommandIndex) + 1;
				titleEndIndex = inputAfterCommand.indexOf("to")-1;
				title = inputAfterCommand.substring(titleIndex, titleEndIndex);
				
				int toIndex = inputAfterCommand.indexOf("to");
				int idIndex = inputAfterCommand.indexOf(" ", toIndex) + 1;
				int idEndIndex = inputAfterCommand.indexOf(",", idIndex);
				String mainId = inputAfterCommand.substring(idIndex, idEndIndex);
				pc.setMainId(mainId);
			} else {
				int numCurrentTask = ParsedCommand.getNumCurrentTask();
				pc.setId(String.valueOf(numCurrentTask + 1));
				ParsedCommand.setNumCurrentTask(numCurrentTask + 1);
				
				titleEndIndex = inputAfterCommand.indexOf(",");
				if (titleEndIndex == -1) {
					title = inputAfterCommand.substring(0);
				} else {
					title = inputAfterCommand.substring(0, titleEndIndex);
				}
			}
			pc.setTitle(title);

			determineDeadlineAndSettle(pc, inputAfterCommand);

			String priority = getAttributeFromInput(inputAfterCommand, "-p", 2);
			if (priority != null) {
				setPriority(pc, priority);
			}
			
			String group = getAttributeFromInput(inputAfterCommand, "-g", 2);
			if (group != null) {
				pc.setGroup(group);
			}

			String location = getAttributeFromInput(inputAfterCommand, "-l", 2);
			if (location != null) {
				pc.setLocation(location);
			}

			String notes = getAttributeFromInput(inputAfterCommand, "-n", 2);
			if (notes != null) {
				pc.setNotes(notes);
			}

			String recurring = getAttributeFromInput(inputAfterCommand, "-r", 2);
			if (recurring != null) {
				pc.setIsRecurring(recurring.equals("yes"));
			}

			setReminder(pc, inputAfterCommand, "-rd", "-rt", 3);
		}
		if (pc.getCommand() == null) {
			//parseFlexibleCommand(pc, userInput);
		} 
		return pc;
	}
	
	/* Tell user format of flexible command, e.g <command> <title> <location> <priority>
	 *  ... <reminderdate> <remindertime>
	 *  
	 *  Assumption: Title must come first
	 *  
	 *  FOR NOW NO PRIORITY, GROUP, NOTES, RECURRING, REMINDER
	 *  
	 * e.g.
	 * 1. meeting with colleagues on 2015/11/12 from 12pm to 2pm at my house
	 * 2. part time job from 2015/11/12 to 2015/11/15 from 9am to 6pm at orchard road
	 */
	public static void parseFlexibleCommand(ParsedCommand pc, String userinput) {
		ArrayList<String> dayKeyword = new ArrayList<>();
		ArrayList<String> keywords = new ArrayList<>();
		StringTokenizer tokens;
		String userInput = "part time job from 2015/11/12 to 2015/11/15 from 9am to 6pm at orchard road";
		
		String title = null;
		
		keywords.add("on");
		keywords.add("at");
		keywords.add("by");
		keywords.add("from");
		
		dayKeyword.add("monday");
		dayKeyword.add("tuesday");
		dayKeyword.add("wednesday");
		dayKeyword.add("thursday");
		dayKeyword.add("friday");
		dayKeyword.add("saturday");
		dayKeyword.add("sunday");
		
		int titleEndIndex = getFirstKeywordIndex(userInput, keywords)-1;
		title = userInput.substring(0, titleEndIndex);
		
		
		String inputAfterTitle = userInput.substring(titleEndIndex+1);
		tokens = new StringTokenizer(inputAfterTitle);
		
		String keyword = tokens.nextToken();
		String next;
		String resultingString = inputAfterTitle;
		String startTime = null, endTime = null, startDate = null, endDate = null;
		String location = null;
		int attributeEndIndex = 0;
		
		while(!resultingString.equals("")) {
			if (keyword.equals("from")) {
				next = tokens.nextToken();
				// token is time
				if (next.contains("am") || next.contains("pm")) {
					startTime = next;
					next = tokens.nextToken();
					if (next.equals("to")) {
						next = tokens.nextToken();
						endTime = next;
						int endTimeIndex = resultingString.indexOf(endTime);
						attributeEndIndex = resultingString.indexOf(" ", endTimeIndex)+1;
					}
				} else {
					startDate = next;
					next = tokens.nextToken();
					if (next.equals("to")) {
						next = tokens.nextToken();
						endDate = next;
						int endDateIndex = resultingString.indexOf(endDate);
						attributeEndIndex = resultingString.indexOf(" ", endDateIndex)+1;
					}
				}
				resultingString = resultingString.substring(attributeEndIndex);
				tokens = new StringTokenizer(resultingString);
				keyword = resultingString.substring(0, resultingString.indexOf(" "));
			} else if (keyword.equals("at")) {
				int afterKeywordIndex = resultingString.indexOf(" ")+1;
				String afterKeyword = resultingString.substring(afterKeywordIndex);
				int nextKeywordIndex = getFirstKeywordIndex(afterKeyword, keywords);
				location = resultingString.substring(afterKeywordIndex, nextKeywordIndex-1);
			}
		}
		
		// check for time keyword e.g. at 2pm / from 2pm to 4pm / by 2pm
		int timeIndex = userInput.indexOf("at"); 
				
		// check for location keyword e.g. at my house /
		
		// check for date e.g. on 2015/11/12
		
	}
	
	/*
	 * FUNCTIONS FOR FLEXIBLE COMMANDS
	 * 
	 */
	public static int getFirstKeywordIndex(String input, ArrayList<String> keywords) {
		int result = 100;
		int current = 0;
		for (int i = 0; i < keywords.size(); i++) {
			current = input.indexOf(keywords.get(i));
			if (current < result && current != -1) {
				result = current;
			}
		}
		return result;

	}
	
	public void determineDeadlineAndSettle(ParsedCommand pc, String inputAfterCommand) {
		// Check if it is deadline
		if (!inputAfterCommand.contains("deadlinedate") && !inputAfterCommand.contains("-dd")) {
			if (inputAfterCommand.contains("startdate")) {
				noDeadlineNormal(pc, inputAfterCommand);
			} else if (inputAfterCommand.contains("-sd")) {
				noDeadlineShortened(pc, inputAfterCommand);
			}
			
		} else {
			if (inputAfterCommand.contains("deadlinedate")) {
				deadlineAndSettleNormal(pc, inputAfterCommand);
			} else if (inputAfterCommand.contains("-dd")) {
				deadlineAndSettleShortened(pc, inputAfterCommand);
			}
		}
	}
	
	public void noDeadlineNormal(ParsedCommand pc, String inputAfterCommand) {
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
	}
	
	public void noDeadlineShortened(ParsedCommand pc, String inputAfterCommand) {
		String startDate = getAttributeFromInput(inputAfterCommand, "-sd", 3);
		String startTime = getAttributeFromInput(inputAfterCommand, "-st", 3);
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

		String endDate = getAttributeFromInput(inputAfterCommand, "-ed", 3);
		String endTime = getAttributeFromInput(inputAfterCommand, "-et", 3);
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
	}
	
	public void deadlineAndSettleNormal(ParsedCommand pc, String inputAfterCommand) {
		String deadlineDate = getAttributeFromInput(inputAfterCommand, "deadlinedate", 12);
		String deadlineTime = getAttributeFromInput(inputAfterCommand, "deadlinetime", 12);
		if (deadlineDate != null && deadlineTime != null) {
			Calendar cal = dateAndTimeToCalendar(deadlineDate, deadlineTime);
			pc.setStartDateTime(cal);
			pc.setEndDateTime(cal);
		} 
	}
	
	public void deadlineAndSettleShortened(ParsedCommand pc, String inputAfterCommand) {
		String deadlineDate = getAttributeFromInput(inputAfterCommand, "-dd", 3);
		String deadlineTime = getAttributeFromInput(inputAfterCommand, "-dt", 3);
		if (deadlineDate != null && deadlineTime != null) {
			Calendar cal = dateAndTimeToCalendar(deadlineDate, deadlineTime);
			pc.setStartDateTime(cal);
			pc.setEndDateTime(cal);
		}
	}
	
	public void setReminder(ParsedCommand pc, String inputAfterCommand, String rd, String rt, 
			int attrLength) {
		ArrayList<String> reminderDate = getReminderFromInput(inputAfterCommand, rd, attrLength);
		ArrayList<String> reminderTime = getReminderFromInput(inputAfterCommand, rt, attrLength);
		ArrayList<Calendar> calList = new ArrayList<Calendar>();
		
		if (reminderDate != null && reminderTime != null) {
			for (int i = 0; i < reminderDate.size(); i++) {
				calList.add(dateAndTimeToCalendar(reminderDate.get(i), reminderTime.get(i)));
			}
			pc.setReminder(calList);
		} else if (reminderDate != null) {
			for (int i = 0; i < reminderDate.size(); i++) {
				calList.add(dateToCalendar(reminderDate.get(i)));
			}
			pc.setReminder(calList);
		} else if (reminderTime != null) {
			for (int i = 0; i < reminderTime.size(); i++) {
				calList.add(timeToCalendar(reminderTime.get(i)));
			}
			pc.setReminder(calList);
		}
	}
	
	public static ArrayList<String> getReminderFromInput(String inputAfterCommand, String attr, int attrLength) {
		ArrayList<String> result = new ArrayList<String>();
		String reminder = "";
		String[] reminderSplit = null;
		int reminderSplitLength = 0;
		
		if (inputAfterCommand.contains(attr)) {
			int index = inputAfterCommand.indexOf(attr) + attrLength + 1;
			int endIndex = inputAfterCommand.indexOf(",", index);
	
			// String userInput2 = "add eat sleep, reminderdate 2015/11/12 2015/11/13, "
			//	+ "remindertime 12.34 23.45";
			
			// Last list of user input
			if (endIndex == -1) {
				reminder = inputAfterCommand.substring(index);
			} else {
				reminder = inputAfterCommand.substring(index, endIndex);
			}
			
			// There are more than 1 parameter in the list
			if (inputAfterCommand.indexOf(" ", index) != -1) {
				reminderSplit = reminder.split(" ");
				reminderSplitLength = reminderSplit.length;
			}
			
			if (reminderSplitLength > 0) {
				for (int i = 0; i < reminderSplitLength; i++) {
					reminder = reminderSplit[i];
					// println(reminder);
					result.add(reminder);
				}
			} else {
				// println(reminder);
				result.add(reminder);
			}
		} else {
			return null;
		}
		
		return result;
	}
	
	
	public void setShortenedFilterParameters(Scanner inputAfterCommand, ParsedCommand pc) {
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

	// date in yyyy/mm/dd
	public static Calendar dateToCalendar(String date) {
		Calendar cal = Calendar.getInstance();
		String[] dateParam = date.split("/");
		int year = Integer.parseInt(dateParam[0]);
		int month = Integer.parseInt(dateParam[1]) - 1; // month is 0 based
		int day = Integer.parseInt(dateParam[2]);

		cal.set(year, month, day);
		return cal;
	}

	// time in hh.mm
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
		// System.out.println(attr + ": " + result);
		return result;
	}
	
	public void println(String line) {
		System.out.println(line);
	}

}
