package stub;

import java.util.Calendar;
import java.util.TimeZone;

import calendrier.Parser;
import utils.Command;
import utils.ParsedCommand;
import utils.Priority;

public class ParserStub extends Parser {

	@Override
	public ParsedCommand parse(String command) {
		// TODO Auto-generated method stub
		ParsedCommand parsedCommand = null;

		if (command.equals("add addTitle, "

				+ "startdate 2015/09/23, " + "starttime 10.55, " + "enddate 2015/09/23, " + "endtime 10.56, "
				+ "priority very high, " + "location addLocation, " + "notes addNotes, " + "recurring yes, "
				+ "reminderdate 2015/09/19, " + "remindertime 10.33")) {
			parsedCommand = generateAdd();
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
		}

		return parsedCommand;
	}

	private ParsedCommand generateAdd() {
		ParsedCommand parsedCommand = new ParsedCommand();

		Calendar startDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		startDateTime.set(2015, 9, 23, 10, 55, 00);
		Calendar endDateTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		endDateTime.set(2015, 9, 23, 10, 56, 00);
		Calendar reminder = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		reminder.set(2015, 9, 19, 10, 33, 00);

		parsedCommand.setCommand(Command.ADD);
		parsedCommand.setTitle("addTitle");
		parsedCommand.setId("ggId");
		parsedCommand.setStartDateTime(startDateTime);
		parsedCommand.setEndDateTime(endDateTime);
		parsedCommand.setPriority(Priority.VERY_HIGH);
		parsedCommand.setLocation("addLocation");
		parsedCommand.setNotes("addNotes");
		parsedCommand.setIsRecurring(true);
		parsedCommand.setReminder(reminder);

		return parsedCommand;
	}

	private ParsedCommand generateUpdate() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.UPDATE);
		parsedCommand.setId("ggId");
		parsedCommand.setPriority(Priority.LOW);

		return parsedCommand;
	}

	private ParsedCommand generateDelete() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.DELETE);
		parsedCommand.setId("ggId");

		return parsedCommand;
	}

	private ParsedCommand generateView() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.VIEW);
		parsedCommand.setId("ggId");

		return parsedCommand;
	}

	private ParsedCommand generateFilterPriority() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.FILTER);
		parsedCommand.setPriority(Priority.MEDIUM);

		return parsedCommand;
	}

	private ParsedCommand generateFilterGroup() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.FILTER);
		parsedCommand.setGroup("filterGroup");

		return parsedCommand;
	}

	private ParsedCommand generateFilterDate() {
		ParsedCommand parsedCommand = new ParsedCommand();

		Calendar filterDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		filterDate.set(2015, 9, 20, 00, 00, 00);

		parsedCommand.setCommand(Command.FILTER);
		parsedCommand.setStartDateTime(filterDate);

		return parsedCommand;
	}

	private ParsedCommand generateViewAll() {
		ParsedCommand parsedCommand = new ParsedCommand();

		parsedCommand.setCommand(Command.VIEW_ALL);

		return parsedCommand;
	}

}
