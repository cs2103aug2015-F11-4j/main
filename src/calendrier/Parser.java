package calendrier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.UUID;

import utils.Command;
import utils.ParsedCommand;
import utils.Priority;
import utils.Recurrence;

/**
 * 
 * @@author A0125168E
 * 
 */

public class Parser {
	private static int KEYWORD_NOT_FOUND = -2;
	private static int SPACE_NOT_FOUND = -1;

	private static ArrayList<String> dayKeyword = new ArrayList<>();
	private static ArrayList<String> keywords = new ArrayList<>();

	public Parser() {
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
	}

	public ParsedCommand parse(String userInput) throws NumberFormatException {
		ParsedCommand pc = new ParsedCommand();

		if (userInput.equals("") || userInput == null)
			return pc;

		// Commands without additional arguments
		if (userInput.equalsIgnoreCase("view all")) {
			pc.setCommand(Command.VIEW_ALL);
			return pc;
		} else if (userInput.equalsIgnoreCase("help")) {
			pc.setCommand(Command.HELP);
			return pc;
		} else if (userInput.equalsIgnoreCase("exit")) {
			pc.setCommand(Command.EXIT);
			return pc;
		} else if (userInput.equalsIgnoreCase("previous")) {
			pc.setCommand(Command.PREVIOUS);
			return pc;
		} else if (userInput.equalsIgnoreCase("next")) {
			pc.setCommand(Command.NEXT);
			return pc;
		} else if (userInput.equalsIgnoreCase("undo")) {
			// e.g. undo
			pc.setCommand(Command.UNDO);
			return pc;
		} else if (userInput.equalsIgnoreCase("undelete")) {
			// e.g. undelete
			pc.setCommand(Command.UNDELETE);
			return pc;
		} else if (userInput.equalsIgnoreCase("view month")) {
			// e.g. view month
			pc.setCommand(Command.VIEW_MONTH);
			return pc;
		} else if (userInput.equalsIgnoreCase("view home")) {
			// e.g. view home
			pc.setCommand(Command.VIEW_HOME);
			return pc;
		} else if (userInput.equalsIgnoreCase("view day")) {
			// e.g. view home
			pc.setCommand(Command.VIEW_DAY);
			return pc;
		} else if (userInput.equalsIgnoreCase("next day")) {
			// e.g. next day
			pc.setCommand(Command.NEXT_DAY);
			return pc;
		} else if (userInput.equalsIgnoreCase("next month")) {
			// e.g. next month
			pc.setCommand(Command.NEXT_MONTH);
			return pc;
		} else if (userInput.equalsIgnoreCase("previous day")) {
			// e.g. previous day
			pc.setCommand(Command.PREVIOUS_DAY);
			return pc;
		} else if (userInput.equalsIgnoreCase("previous month")) {
			// e.g. previous month
			pc.setCommand(Command.PREVIOUS_MONTH);
			return pc;
		}

		Scanner lineTokens = new Scanner(userInput);
		String command = lineTokens.next();

		if (command.equalsIgnoreCase("save")) {
			// save in my desktop
			String nextWord = lineTokens.next();
			if (nextWord.equalsIgnoreCase("in")) {
				String saveLocation = lineTokens.nextLine().trim();
				pc.setCommand(Command.STORAGE_LOCATION);
				pc.setStorageLocation(saveLocation);
				lineTokens.close();
				return pc;
			}
		}
		lineTokens.close();
		String inputAfterCommand = null;

		if (userInput.indexOf(" ") != SPACE_NOT_FOUND)
			inputAfterCommand = userInput.substring(userInput.indexOf(" ") + 1);

		if (command.equalsIgnoreCase("view")) {
			// e.g. view 2
			pc.setCommand(Command.VIEW);
			pc.setId(inputAfterCommand);
		} else if (command.equalsIgnoreCase("delete")) {
			// e.g. delete 2
			pc.setCommand(Command.DELETE);
			pc.setId(inputAfterCommand);
		} else if (command.equalsIgnoreCase("filter") || command.equalsIgnoreCase("search")) {
			// e.g. filter group personal OR filter priority very high OR
			// filter startdate yyyy/mm/dd OR filter enddate yyyy/mm/dd
			pc.setCommand(Command.FILTER);
			setFilterParameters(new Scanner(inputAfterCommand), pc);
		} else if (command.equalsIgnoreCase("update")) {
			/*
			 * Case 1: not a deadline No. of parameters: 12 e.g. update 3, title
			 * repeat sleep drink eat, startdate 2015/12/29, starttime 13.37,
			 * enddate 2015/12/30, endtime 14.44, group my personal group
			 * priority very low, location my home, notes must do, recur
			 * monthly, reminderdate 2015/12/30, remindertime 15.30, done no
			 * 
			 * Case 2: a deadline e.g. add eat sleep drink repeat, deadlinedate
			 * 2015/12/30, deadlinetime 14.44, priority very low, group my
			 * personal group, location my home, notes must do, recur daily,
			 * reminderdate 2015/12/29 2015/12/29, remindertime 12.34 23.45,
			 * done yes
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

			String recur = getAttributeFromInput(inputAfterCommand, "recur", 5);
			// if (recur != null) {
			setRecur(pc, recur);
			// }

			String done = getAttributeFromInput(inputAfterCommand, "done", 4);
			if (done != null) {
				if (done.equals("yes")) {
					pc.setDone(true);
				} else if (done.equals("true")) {
					pc.setDone(true);
				} else if (done.equals("no")) {
					pc.setDone(false);
				} else if (done.equals("false")) {
					pc.setDone(false);
				}
				// pc.setDone(done.equalsIgnoreCase("yes"));
			}

			setReminder(pc, inputAfterCommand, "reminderdate", "remindertime", 12);

		} else if (command.equalsIgnoreCase("add")) {
			pc.setCommand(Command.ADD);

			int numCurrentTask = ParsedCommand.getNumCurrentTask();
<<<<<<< HEAD
=======
			// pc.setId(String.valueOf(numCurrentTask + 1));
>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
			pc.setId(UUID.randomUUID().toString());
			ParsedCommand.setNumCurrentTask(numCurrentTask + 1);

			/*
			 * Case 1: not a deadline No. of parameters: 12 e.g. add eat sleep
			 * drink repeat, startdate 2015/12/29, starttime 13.37, enddate
			 * 2015/12/30, endtime 14.44, priority very low, group my personal
			 * group, location my home, notes must do, recur
			 * <daily/weekly/monthly/yearly>, reminderdate 2015/12/29
			 * 2015/12/29, remindertime 12.34 23.45, done yes
			 * 
			 * Case 2: a deadline e.g. add eat sleep drink repeat, deadlinedate
			 * 2015/12/30, deadlinetime 14.44, priority very low, group my
			 * personal group, location my home, notes must do, recur
			 * <daily/weekly/monthly/yearly>, reminderdate 2015/12/29
			 * 2015/12/29, remindertime 12.34 23.45, done no
			 * 
			 * Case 3: subtask e.g. add subtask sleep drink repeat to 1, .......
			 */

			int titleEndIndex, titleIndex;
			String title;

			StringTokenizer st = new StringTokenizer(inputAfterCommand);
			String wordAfterCommand = st.nextToken();
			if (wordAfterCommand.equalsIgnoreCase("subtask")) {
				String mainId;
				int wordAfterCommandIndex = inputAfterCommand.indexOf(wordAfterCommand);
				titleIndex = inputAfterCommand.indexOf(" ", wordAfterCommandIndex) + 1;
				titleEndIndex = inputAfterCommand.indexOf("to") - 1;
				title = inputAfterCommand.substring(titleIndex, titleEndIndex);

				int toIndex = inputAfterCommand.indexOf("to");
				int idIndex = inputAfterCommand.indexOf(" ", toIndex) + 1;
				int idEndIndex = inputAfterCommand.indexOf(",", idIndex);
				if (idEndIndex == SPACE_NOT_FOUND) {
					mainId = inputAfterCommand.substring(idIndex);
				} else {
					mainId = inputAfterCommand.substring(idIndex, idEndIndex);
				}
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

			String recur = getAttributeFromInput(inputAfterCommand, "recur", 5);
			// if (recur != null) {
			setRecur(pc, recur);
			// }

			String done = getAttributeFromInput(inputAfterCommand, "done", 4);
			if (done != null) {
				if (done.equals("yes")) {
					pc.setDone(true);
				} else if (done.equals("true")) {
					pc.setDone(true);
				} else if (done.equals("no")) {
					pc.setDone(false);
				} else if (done.equals("false")) {
					pc.setDone(false);
				}
				// pc.setDone(done.equalsIgnoreCase("yes"));
			}

			setReminder(pc, inputAfterCommand, "reminderdate", "remindertime", 12);

		}
		if (pc.getCommand() == null) {
			parseShortenedCommand(pc, userInput);
		}
		return pc;
	}

	public ParsedCommand parseShortenedCommand(ParsedCommand pc, String userInput) throws NumberFormatException {
		String inputAfterCommand = "";
		String command = "";

		// input has more than 1 word
		if (userInput.indexOf(" ") != SPACE_NOT_FOUND) {
			inputAfterCommand = userInput.substring(userInput.indexOf(" ") + 1);
			command = userInput.substring(0, userInput.indexOf(" "));
		} else {
			command = userInput;
		}

		if (command.equalsIgnoreCase("-all")) {
			pc.setCommand(Command.VIEW_ALL);
			return pc;
		} else if (command.equalsIgnoreCase("-h")) {
			pc.setCommand(Command.HELP);
			return pc;
		} else if (command.equalsIgnoreCase("-e")) {
			pc.setCommand(Command.EXIT);
			return pc;
		} else if (command.equalsIgnoreCase("-prev")) {
			pc.setCommand(Command.PREVIOUS);
			return pc;
		} else if (command.equalsIgnoreCase("-prevm")) {
			pc.setCommand(Command.PREVIOUS_MONTH);
			return pc;
		} else if (command.equalsIgnoreCase("-prevd")) {
			pc.setCommand(Command.PREVIOUS_DAY);
			return pc;
		} else if (command.equalsIgnoreCase("-nxt")) {
			pc.setCommand(Command.NEXT);
			return pc;
		} else if (command.equalsIgnoreCase("-nxtd")) {
			pc.setCommand(Command.NEXT_DAY);
			return pc;
		} else if (command.equalsIgnoreCase("-nxtm")) {
			pc.setCommand(Command.NEXT_MONTH);
			return pc;
		} else if (command.equalsIgnoreCase("-u")) {
			pc.setCommand(Command.UNDO);
			return pc;
		} else if (command.equalsIgnoreCase("-vm")) {
			pc.setCommand(Command.VIEW_MONTH);
			return pc;
<<<<<<< HEAD
=======
		} else if (command.equalsIgnoreCase("-vd")) {
			pc.setCommand(Command.VIEW_DAY);
			return pc;
>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
		} else if (command.equalsIgnoreCase("-vh")) {
			pc.setCommand(Command.VIEW_HOME);
			return pc;
		} else if (command.equalsIgnoreCase("-vd")) {
			pc.setCommand(Command.VIEW_DAY);
			return pc;
		}

		if (command.equalsIgnoreCase("-s")) {
			// e.g. save in desktop: -s desktop
			pc.setCommand(Command.STORAGE_LOCATION);
			pc.setStorageLocation(inputAfterCommand);
		} else if (command.equalsIgnoreCase("-undel")) {
			// e.g. undelete 4: -undel
			pc.setCommand(Command.UNDELETE);
		} else if (command.equalsIgnoreCase("-v")) {
			// e.g. view 2: -v 2
			pc.setCommand(Command.VIEW);
			pc.setId(inputAfterCommand);
		} else if (command.equalsIgnoreCase("-d")) {
			// e.g. delete 1: -d 1
			pc.setCommand(Command.DELETE);
			pc.setId(inputAfterCommand);
		} else if (command.equalsIgnoreCase("-f")) {
			// e.g. -f -g personal stuff OR -f -p very high OR
			// -f -sd yyyy/mm/dd OR -f -ed yyyy/mm/dd
			pc.setCommand(Command.FILTER);
			setShortenedFilterParameters(new Scanner(inputAfterCommand), pc);
		} else if (command.equalsIgnoreCase("-up")) {
			// Case 1: Not deadline
			// e.g. -up 2, -t do homework, -sd 2015/10/30, -st 12.34, -ed
			// 2015/11/12,
			// -et 13.37, -g personal circle, -l my home, -p very high,
			// -n remember to do, -r yes, -rd 2015/11/11, -rt 11.11, -dne yes

			// Case 2: Deadline
			// e.g. -up 2, -t do homework, -dd 2015/11/12,
			// -dt 13.37, -g personal circle, -l my home, -p very high,
			// -n remember to do, -r yes, -rd 2015/11/11, -rt 11.11, -dne no

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

			String recur = getAttributeFromInput(inputAfterCommand, "-r", 2);
			// if (recur != null) {
			setRecur(pc, recur);
			// }

			String done = getAttributeFromInput(inputAfterCommand, "-dne", 4);
			if (done != null) {
				if (done.equals("yes")) {
					pc.setDone(true);
				} else if (done.equals("true")) {
					pc.setDone(true);
				} else if (done.equals("no")) {
					pc.setDone(false);
				} else if (done.equals("false")) {
					pc.setDone(false);
				}
				// pc.setDone(done.equalsIgnoreCase("yes"));
			}

			setReminder(pc, inputAfterCommand, "-rd", "-rt", 3);
		}

		else if (command.contains("-a")) {
			// Case 1: Not deadline
			// e.g. -a eat drink sleep repeat, -sd 2015/10/12, -st 12.34, ed
			// 2015/10/14,
			// -et 13.37, -p very high, -g secret group, -l my home, -n must do,
			// -r no, -rd 2015/10/13 2015/10/14, -rt 13.37 11.11, -dne no

			// Case 2: Deadline
			// e.g. -a eat drink sleep repeat, -dd 2015/10/14, -dt 13.37, -p
			// very high,
			// -g secret group, -l my home, -n must do, -r no, -rd 2015/10/13
			// 2015/10/14,
			// -rt 13.37 11.11, -dne yes

			// Case 3: Subtask
<<<<<<< HEAD
			// e.g. -a subtask drink repeat to 1, -st 2015/10/12, .......
			
=======
			// e.g. -a subtask drink repeat to 1, .......

>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
			pc.setCommand(Command.ADD);

			int titleEndIndex, titleIndex;
			String title;

			StringTokenizer st = new StringTokenizer(inputAfterCommand);
			String wordAfterCommand = st.nextToken();
			if (wordAfterCommand.equalsIgnoreCase("subtask")) {
				String mainId;

				int wordAfterCommandIndex = inputAfterCommand.indexOf(wordAfterCommand);
				titleIndex = inputAfterCommand.indexOf(" ", wordAfterCommandIndex) + 1;
				titleEndIndex = inputAfterCommand.indexOf("to") - 1;
				title = inputAfterCommand.substring(titleIndex, titleEndIndex);

				int toIndex = inputAfterCommand.indexOf("to");
				int idIndex = inputAfterCommand.indexOf(" ", toIndex) + 1;
				int idEndIndex = inputAfterCommand.indexOf(",", idIndex);
				if (idEndIndex == SPACE_NOT_FOUND) {
					mainId = inputAfterCommand.substring(idIndex);
				} else {
					mainId = inputAfterCommand.substring(idIndex, idEndIndex);
				}
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

			String recur = getAttributeFromInput(inputAfterCommand, "-r", 2);
			// if (recur != null) {
			setRecur(pc, recur);
			// }

			String done = getAttributeFromInput(inputAfterCommand, "-dne", 4);
			if (done != null) {
				if (done.equals("yes")) {
					pc.setDone(true);
				} else if (done.equals("true")) {
					pc.setDone(true);
				} else if (done.equals("no")) {
					pc.setDone(false);
				} else if (done.equals("false")) {
					pc.setDone(false);
				}
				// pc.setDone(done.equalsIgnoreCase("yes"));
			}

			setReminder(pc, inputAfterCommand, "-rd", "-rt", 3);
		}
		if (pc.getCommand() == null) {
			parseFlexibleCommand(pc, userInput);
		}
		return pc;
	}
<<<<<<< HEAD
	
	
	public static ParsedCommand parseFlexibleCommand(ParsedCommand pc, String userInput) {
		String title = null, inputAfterKeyword = null;
=======

	/*
	 * Tell user format of flexible command, e.g <command> <title> <location>
	 * <priority> ... <reminderdate> <remindertime>
	 * 
	 * Assumption: Title must come first!!!
	 * 
	 * FOR NOW NO PRIORITY, GROUP, NOTES, RECURRING, REMINDER
	 * 
	 * e.g. 1. meeting with colleagues on monday from 12pm to 2pm at my house 2.
	 * part time job from 2015/11/12 to 2015/11/15 from 9am to 6pm at orchard
	 * road
	 */
	public static ParsedCommand parseFlexibleCommand(ParsedCommand pc, String userInput) throws NumberFormatException {
		String title = null, inputAfterKeyword = null, day = null;
>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
		String resultingString = null, nextToken = null, keyword = null;
		String startTime = null, endTime = null, startDate = null, endDate = null, location = null;
		StringTokenizer tokens = null;

		// String userInput2 = "part time job from 2015/11/12 to 2015/11/15 from
		// 9am to 6pm at orchard road";
		// String userInput = "meeting with colleagues on monday from 12pm to
		// 2pm at my house";

		int titleEndIndex = getFirstKeywordIndex(userInput, keywords) - 1;
		// Invalid command
		if (titleEndIndex == KEYWORD_NOT_FOUND)
			return pc;
		title = userInput.substring(0, titleEndIndex);
		pc.setTitle(title);

		resultingString = userInput.substring(titleEndIndex + 1);

		tokens = new StringTokenizer(resultingString);
		keyword = tokens.nextToken();

		int nextKeywordIndex = SPACE_NOT_FOUND;
<<<<<<< HEAD
		
		while(!resultingString.equals("")) {
			// keyword 'from': for date and time
			// e.g. from 2015/11/12 to 2015/11/15, from 12.50pm to 9.33pm
			
=======
		int runCount = 0;

		while (!resultingString.equals("")) {
			// keyword 'from': for date and time
			// e.g. from 2015/11/12 to 2015/11/15, from 12.50pm to 9.33pm

			// System.out.println("Before: " + resultingString);
			// System.out.println("keyword: " + keyword);

>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
			if (keyword.equalsIgnoreCase("from")) {
				nextToken = tokens.nextToken();
				// token is time
				if (nextToken.contains("am") || nextToken.contains("pm")) {
					startTime = nextToken;
					nextToken = tokens.nextToken();
					if (nextToken.equalsIgnoreCase("to")) {
						nextToken = tokens.nextToken();
						endTime = nextToken;
<<<<<<< HEAD
						nextKeywordIndex = getNextKeywordIndex(resultingString, endTime);
=======

						// System.out.println("startTime: " + startTime);
						// System.out.println("endTime: " + endTime);
						// System.out.println("endTimeIndex: " + endTimeIndex);

						nextKeywordIndex = getNextKeywordIndex(resultingString, endTime);

						// System.out.println("nextkeywordindex: " +
						// nextKeywordIndex);
>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
					}
				} else if (nextToken.contains("/")) {
					startDate = nextToken;
					nextToken = tokens.nextToken();
					if (nextToken.equalsIgnoreCase("to")) {
						nextToken = tokens.nextToken();
						endDate = nextToken;
<<<<<<< HEAD
=======
						// System.out.println("startDate: " + startDate);
						// System.out.println("endDate: " + endDate);

>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
						nextKeywordIndex = getNextKeywordIndex(resultingString, endDate);
					}
				}

				resultingString = getResultingString(nextKeywordIndex, resultingString);

			}
			// keyword 'at': for location
			// e.g. at nus soc
			else if (keyword.equalsIgnoreCase("at")) {
				int afterKeywordIndex = resultingString.indexOf(" ") + 1;
				inputAfterKeyword = resultingString.substring(afterKeywordIndex);

				nextKeywordIndex = getFirstKeywordIndex(inputAfterKeyword, keywords);
				// there are no more keyword, this is the last token of the line
				if (nextKeywordIndex == -1) {
					location = resultingString.substring(afterKeywordIndex);
					resultingString = "";
<<<<<<< HEAD
				}
				else {
					location = inputAfterKeyword.substring(0, nextKeywordIndex-1);
=======
				} else {
					// System.out.println("inputafterkeyword: " +
					// inputAfterKeyword);
					location = inputAfterKeyword.substring(0, nextKeywordIndex - 1);
					// System.out.println("nextkeywordindex: " +
					// nextKeywordIndex);
>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
					resultingString = resultingString.substring(nextKeywordIndex);
				}

			}
			// keyword 'on': for day of the week, or date
			// e.g. meeting on monday, meeting on 2015/11/22
			else if (keyword.equalsIgnoreCase("on")) {
				nextToken = tokens.nextToken();

				// token is date
				if (nextToken.contains("/")) {
					startDate = nextToken;
					int endDateIndex = resultingString.indexOf(startDate);
					nextKeywordIndex = resultingString.indexOf(" ", endDateIndex) + 1;
				} else if (dayKeyword.contains(nextToken)) {
					startDate = getFlexibleDateFromDay(nextToken);
					// System.out.println("resultant day: " + resultantDay);

					nextKeywordIndex = getNextKeywordIndex(resultingString, nextToken);
				}

				resultingString = getResultingString(nextKeywordIndex, resultingString);
			}
			// keyword 'by': for end date, end time
			// e.g. by tuesday, by 10.35am
			else if (keyword.equalsIgnoreCase("by")) {
				nextToken = tokens.nextToken();
				// token is a day
				if (dayKeyword.contains(nextToken)) {
					endDate = getFlexibleDateFromDay(nextToken);
					nextKeywordIndex = getNextKeywordIndex(resultingString, nextToken);
				} else if (nextToken.contains("am") || nextToken.contains("pm")) {
					endTime = nextToken;
					int endTimeIndex = resultingString.indexOf(endTime);
					nextKeywordIndex = resultingString.indexOf(" ", endTimeIndex) + 1;
				}
				resultingString = getResultingString(nextKeywordIndex, resultingString);
			}
<<<<<<< HEAD
			
=======

			// System.out.println("After: " + resultingString);

>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
			if (!resultingString.equals("")) {
				tokens = new StringTokenizer(resultingString);
				keyword = tokens.nextToken();
			}
		}

		if (startDate != null && startTime != null) {
			startTime = getTimeWithoutAmPm(startTime);
			startTime = padWithZero(startTime);
			Calendar cal = dateAndTimeToCalendar(startDate, startTime);
			pc.setStartDateTime(cal);
		} else if (startDate != null) {
			Calendar cal = dateToCalendar(startDate);
			pc.setStartDateTime(cal);
		} else if (startTime != null) {
			startTime = getTimeWithoutAmPm(startTime);
			startTime = padWithZero(startTime);
			Calendar cal = timeToCalendar(startTime);
			pc.setStartDateTime(cal);
		}

		if (endDate != null && endTime != null) {
			endTime = getTimeWithoutAmPm(endTime);
			endTime = padWithZero(endTime);
			Calendar cal = dateAndTimeToCalendar(endDate, endTime);
			pc.setEndDateTime(cal);
		} else if (endDate != null) {
			Calendar cal = dateToCalendar(endDate);
			pc.setEndDateTime(cal);
		} else if (endTime != null) {
			endTime = getTimeWithoutAmPm(endTime);
			endTime = padWithZero(endTime);
			Calendar cal = timeToCalendar(endTime);
			pc.setEndDateTime(cal);
		}

		if (location != null) {
			pc.setLocation(location);
		}
		return pc;
	}

	/*
	 * FUNCTIONS FOR FLEXIBLE COMMANDS
	 * 
	 */
	public static int getFirstKeywordIndex(String input, ArrayList<String> keywords) {
		int result = 100;
		boolean found = false;
		int current = 0;

		for (int i = 0; i < keywords.size(); i++) {
			current = input.indexOf(keywords.get(i));
			if (current != -1) {
				found = true;
			}
			if (current < result && current != -1) {
				result = current;
			}
		}
		if (!found) {
			return -1;
		}
		return result;
	}

	public static int getNextKeywordIndex(String currentString, String attributeEnd) {
		int attributeEndIndex = currentString.lastIndexOf(attributeEnd);
		return currentString.indexOf(" ", attributeEndIndex) + 1;
	}

	public static String getResultingString(int nextKeywordIndex, String currentString) {
		String resultingString;
		if (nextKeywordIndex == 0) {
			resultingString = "";
		} else {
			resultingString = currentString.substring(nextKeywordIndex);
		}
		return resultingString;
	}

	public static String getFlexibleDateFromDay(String nextToken) {
		int[] daysInMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		final int NUM_DAYS_IN_WEEK = 7;
		int projectedDay = 0, diffInDay = 0;

		Calendar today = Calendar.getInstance();
		int dayOfWeek = today.get(Calendar.DAY_OF_WEEK) - 1;
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
<<<<<<< HEAD
		
=======

		// System.out.println("curr year: " + year);
		// System.out.println("curr month: " + month);
		// System.out.println("curr day: " + dayOfMonth);

>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
		if (nextToken.equalsIgnoreCase("monday")) {
			projectedDay = 1;
		} else if (nextToken.equalsIgnoreCase("tuesday")) {
			projectedDay = 2;
		} else if (nextToken.equalsIgnoreCase("wednesday")) {
			projectedDay = 3;
		} else if (nextToken.equalsIgnoreCase("thursday")) {
			projectedDay = 4;
		} else if (nextToken.equalsIgnoreCase("friday")) {
			projectedDay = 5;
		} else if (nextToken.equalsIgnoreCase("saturday")) {
			projectedDay = 6;
		} else if (nextToken.equalsIgnoreCase("sunday")) {
			projectedDay = 7;
		}

		// today is not sunday
		if (dayOfWeek != 0) {
			if (projectedDay >= dayOfWeek) {
				diffInDay = projectedDay - dayOfWeek;
			} else if (projectedDay < dayOfWeek) {
				diffInDay = NUM_DAYS_IN_WEEK - dayOfWeek + projectedDay;
			}
		}
		// today is sunday
		else if (dayOfWeek == 0) {
			diffInDay = projectedDay;
		}

		int resultantDay = 0;
		if (diffInDay + dayOfMonth > daysInMonth[month]) {
			resultantDay = (diffInDay + dayOfMonth) - daysInMonth[month];
			month++;
		} else if (diffInDay + dayOfMonth <= daysInMonth[month]) {
			resultantDay = diffInDay + dayOfMonth;
		}

		return (String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(resultantDay));
	}

	public static String padWithZero(String time) {
		try {
			int timeInt = Integer.parseInt(time);
			String timeS = String.valueOf(timeInt) + ".00";
			return timeS;
		} catch (Exception e) {
			return time;
		}
	}

	public static String getTimeWithoutAmPm(String time) {
		if (time.contains("am")) {
			time = time.replace("am", "");
		} else if (time.contains("pm")) {
			time = time.replace("pm", "");
			String hour;
			if (time.indexOf(".") != -1) {
				hour = time.substring(0, time.indexOf("."));
			} else {
				hour = time;
			}

			if (Integer.parseInt(hour) == 12) {
				return time;
			} else {
				time = String.valueOf(Float.parseFloat(time) + 12.0);
			}
		}
		return time;
	}

	public void determineDeadlineAndSettle(ParsedCommand pc, String inputAfterCommand) {
		// Check if it is deadline
		if (!inputAfterCommand.contains("deadlinedate") && !inputAfterCommand.contains("-dd")) {
			if (inputAfterCommand.contains("startdate") || inputAfterCommand.contains("enddate")) {
				noDeadlineNormal(pc, inputAfterCommand);
			} else if (inputAfterCommand.contains("-sd") || inputAfterCommand.contains("-st")) {
				noDeadlineShortened(pc, inputAfterCommand);
			}

		} else {
			if (inputAfterCommand.contains("deadlinedate") || inputAfterCommand.contains("deadlinetime")) {
				deadlineAndSettleNormal(pc, inputAfterCommand);
			} else if (inputAfterCommand.contains("-dd") || inputAfterCommand.contains("-dt")) {
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
			Calendar cal = dateAndTimeToCalendar(startDate, "00.00");
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
			Calendar cal = dateAndTimeToCalendar(endDate, "00.00");
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
			Calendar cal = dateAndTimeToCalendar(startDate, "00.00");
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
			Calendar cal = dateAndTimeToCalendar(endDate, "00.00");
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
		} else if (deadlineDate != null) {
			Calendar cal = dateAndTimeToCalendar(deadlineDate, "00.00");
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
		} else if (deadlineDate != null) {
			Calendar cal = dateAndTimeToCalendar(deadlineDate, "00.00");
			pc.setStartDateTime(cal);
			pc.setEndDateTime(cal);
		}
	}

	public void setReminder(ParsedCommand pc, String inputAfterCommand, String rd, String rt, int attrLength) {
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
				calList.add(dateAndTimeToCalendar(reminderDate.get(i), "00.00"));
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
<<<<<<< HEAD
			
=======

			// String userInput2 = "add eat sleep, reminderdate 2015/11/12
			// 2015/11/13, "
			// + "remindertime 12.34 23.45";

>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16
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
					result.add(reminder);
				}
			} else {
				result.add(reminder);
			}
		} else {
			return null;
		}

		return result;
	}

	public void setShortenedFilterParameters(Scanner inputAfterCommand, ParsedCommand pc) throws NumberFormatException{
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

	public void setFilterParameters(Scanner inputAfterCommand, ParsedCommand pc) throws NumberFormatException {
		String filterParameter = inputAfterCommand.next();
		String filterValue = inputAfterCommand.nextLine().trim();

		Calendar cal;

		if (filterParameter.equalsIgnoreCase("group")) {
			pc.setGroup(filterValue);
		} else if (filterParameter.equalsIgnoreCase("startdate")) {
			cal = dateToCalendar(filterValue);
			pc.setStartDateTime(cal);
		} else if (filterParameter.equalsIgnoreCase("enddate")) {
			cal = dateToCalendar(filterValue);
			pc.setEndDateTime(cal);
		} else if (filterParameter.equalsIgnoreCase("priority")) {
			setPriority(pc, filterValue);
		}
	}

	public static Calendar dateAndTimeToCalendar(String date, String time) throws NumberFormatException {
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
	public static Calendar dateToCalendar(String date) throws NumberFormatException {
		Calendar cal = Calendar.getInstance();
		String[] dateParam = date.split("/");
		int year = Integer.parseInt(dateParam[0]);
		int month = Integer.parseInt(dateParam[1]) - 1; // month is 0 based
		int day = Integer.parseInt(dateParam[2]);

		cal.set(year, month, day);
		return cal;
	}

	// time in hh.mm
	public static Calendar timeToCalendar(String time) throws NumberFormatException {
		Calendar cal = Calendar.getInstance();
		String[] timeParam = time.split("\\.");
		int hour = Integer.parseInt(timeParam[0]);
		int minute = Integer.parseInt(timeParam[1]);

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		return cal;
	}

	public static void setPriority(ParsedCommand pc, String priority) {
		if (priority.equalsIgnoreCase("very low")) {
			pc.setPriority(Priority.VERY_LOW);
		} else if (priority.equalsIgnoreCase("low")) {
			pc.setPriority(Priority.LOW);
		} else if (priority.equalsIgnoreCase("medium")) {
			pc.setPriority(Priority.MEDIUM);
		} else if (priority.equalsIgnoreCase("high")) {
			pc.setPriority(Priority.HIGH);
		} else if (priority.equalsIgnoreCase("very high")) {
			pc.setPriority(Priority.VERY_HIGH);
		}
	}

	public static void setRecur(ParsedCommand pc, String recur) {
		if (recur == null) {
			pc.setRecurFreq(null);
		} else if (recur.equalsIgnoreCase("daily")) {
			pc.setRecurFreq(Recurrence.DAILY);
		} else if (recur.equalsIgnoreCase("weekly")) {
			pc.setRecurFreq(Recurrence.WEEKLY);
		} else if (recur.equalsIgnoreCase("monthly")) {
			pc.setRecurFreq(Recurrence.MONTHLY);
		} else if (recur.equalsIgnoreCase("yearly")) {
			pc.setRecurFreq(Recurrence.YEARLY);
		}
	}

	public static String getAttributeFromInput(String inputAfterCommand, String attr, int attrLength) {
		String result;

		if (inputAfterCommand.contains(attr)) {
			int index = inputAfterCommand.indexOf(attr) + attrLength + 1;
			int endIndex = inputAfterCommand.indexOf(",", index);
<<<<<<< HEAD
=======

			// System.out.println("index of " + attr + ": " + index);
			// System.out.println("endIndex: " + attr + ": " + endIndex);
>>>>>>> 94e9b290276475987e5c08861fe3fa43e0944f16

			// last attribute input of the line
			if (endIndex == -1) {
				result = inputAfterCommand.substring(index);
			} else {
				result = inputAfterCommand.substring(index, endIndex);
			}
		} else {
			return null;
		}
		return result;
	}

	public void println(String line) {
		System.out.println(line);
	}

}
