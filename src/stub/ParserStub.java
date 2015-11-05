package stub;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import calendrier.Parser;
import utils.Command;
import utils.ParsedCommand;
import utils.Priority;

/**
 * @@author A0088646M-unused
 * @author yeehuipoh
 *
 */
public class ParserStub extends Parser {

	/**
	 * @@author A0088646M-unused
	 * Parse command
	 */
	@Override
	public ParsedCommand parse(String command) {
		ParsedCommand parsedCommand = null;

		if (command.equals("add addTitle, "

				+ "startdate 2015/09/23, " + "starttime 10.55, " + "enddate 2015/09/23, " + "endtime 10.56, "
				+ "priority very high, " + "location addLocation, " + "notes addNotes, " + "recurring yes, "
				+ "reminderdate 2015/09/19, " + "remindertime 10.33")) {
			parsedCommand = generateAdd();
		} else if (command.equals("add addTitle")) {
			parsedCommand = generateAddOnlyTitle();
		} else if (command.equals("delete ggId")) {
			parsedCommand = generateDelete();
		} else if (command.equals("update ggId, priority low")) {
			parsedCommand = generateUpdate();
		} else if (command.equals("view all")) {
			parsedCommand = generateViewAll();
		} else if (command.equals("view ggId")) {
			parsedCommand = generateView();
		} else if (command.equals("filter group filterGroup")) {
			parsedCommand = generateFilterGroup();
		} else if (command.equals("filter priority medium")) {
			parsedCommand = generateFilterPriority();
		} else if (command.equals("filter date 2015/09/20")) {
			parsedCommand = generateFilterDate();
		} else if (command.equals("undelete")) {
			parsedCommand = generateUndelete();
		} else if (command.equals("undo")) {
			parsedCommand = generateUndo();
		} else if (command.equals("previous")) {
			parsedCommand = generatePrevious();
		} else if (command.equals("next")) {
			parsedCommand = generateNext();
		} else if (command.equals("save in ggFile.txt")) {
			parsedCommand = generateSaveLocation();
		} else if (command.equals("exit")) {
			parsedCommand = generateExit();
		} else if (command.equals("help")) {
			parsedCommand = generateHelp();
		}

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate add structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateAdd() {
		ParsedCommand parsedCommand = new ParsedCommand();

		Calendar startDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDateTime.set(2015, 9, 23, 10, 55, 00);
		Calendar endDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		endDateTime.set(2015, 9, 23, 10, 56, 00);
		Calendar reminder = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		reminder.set(2015, 9, 19, 10, 33, 00);
		
		ArrayList<Calendar> reminders = new ArrayList<>();
		reminders.add(reminder);

		parsedCommand.setCommand(Command.ADD);
		parsedCommand.setTitle("addTitle");
		parsedCommand.setId("ggId");
		parsedCommand.setStartDateTime(startDateTime);
		parsedCommand.setEndDateTime(endDateTime);
		parsedCommand.setPriority(Priority.VERY_HIGH);
		parsedCommand.setLocation("addLocation");
		parsedCommand.setNotes("addNotes");
//		parsedCommand.setIsRecurring(true);
		parsedCommand.setReminder(reminders);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate add structured command (with only title)
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateAddOnlyTitle() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.ADD);
		parsedCommand.setTitle("addTitle");
		parsedCommand.setId("ggId");

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate update structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateUpdate() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.UPDATE);
		parsedCommand.setId("ggId");
		parsedCommand.setPriority(Priority.LOW);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate delete structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateDelete() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.DELETE);
		parsedCommand.setId("ggId");

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate view structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateView() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.VIEW);
		parsedCommand.setId("ggId");

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate filter priority structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateFilterPriority() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.FILTER);
		parsedCommand.setPriority(Priority.MEDIUM);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate filter group structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateFilterGroup() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.FILTER);
		parsedCommand.setGroup("filterGroup");

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate filter date structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateFilterDate() {
		ParsedCommand parsedCommand = new ParsedCommand();

		Calendar filterDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		filterDate.set(2015, 9, 20, 00, 00, 00);

		parsedCommand.setCommand(Command.FILTER);
		parsedCommand.setStartDateTime(filterDate);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate view all structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateViewAll() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.VIEW_ALL);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate undelete structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateUndelete() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.UNDELETE);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate undo structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateUndo() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.UNDO);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate previous structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generatePrevious() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.PREVIOUS);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate next structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateNext() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.NEXT);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * generate save in structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateSaveLocation() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.STORAGE_LOCATION);
		parsedCommand.setStorageLocation("ggFile.txt");

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate exit structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateExit() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.EXIT);

		return parsedCommand;
	}

	/**
	 * @@author A0088646M-unused
	 * Generate help structured command
	 * 
	 * @return structured command
	 */
	private ParsedCommand generateHelp() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.HELP);

		return parsedCommand;
	}

}
