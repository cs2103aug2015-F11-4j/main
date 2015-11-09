# A0088646Munused
###### ./src/stub/EventHandlerStub.java
``` java
 * @author yeehuipoh
 *
 */
public class EventHandlerStub extends EventHandler {
	private ArrayList<Event> events = new ArrayList<>();

	/**
```
###### ./src/stub/EventHandlerStub.java
``` java
	 * Execute Command
	 */
	public ArrayList<Event> execute(ParsedCommand parsedCommand) {
		ArrayList<Event> eventList = new ArrayList<>();

		if (parsedCommand.getCommand() == Command.ADD) {
			eventList = addStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.DELETE) {
			eventList = deleteStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.UPDATE) {
			eventList = updateStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW) {
			eventList = viewStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW_ALL) {
			eventList = events;
		}

		return eventList;
	}

	/**
```
###### ./src/stub/EventHandlerStub.java
``` java
	 * Add mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of added event
	 */
	public ArrayList<Event> addStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();

		Event event = new Event();
		event.setId("ggId");
		event.setTitle(parsedCommand.getTitle());
		event.setStartDateTime(parsedCommand.getStartDateTime());
		event.setEndDateTime(parsedCommand.getEndDateTime());
		event.setPriority(parsedCommand.getPriority());
		event.setLocation(parsedCommand.getLocation());
		event.setNotes(parsedCommand.getNotes());
		event.setReminder(parsedCommand.getReminder());

		events.add(event);
		currentEvents.add(event);

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerStub.java
``` java
	 * Delete mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of deleted event
	 */
	public ArrayList<Event> deleteStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		int position = -1;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getId().equals(parsedCommand.getId())) {
				position = i;
				break;
			}
		}

		if (position >= 0) {
			currentEvents.add(events.get(position));
			events.remove(position);
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerStub.java
``` java
	 * Update mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of updated events
	 */
	public ArrayList<Event> updateStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				event.setPriority(parsedCommand.getPriority());

				currentEvents.add(event);
				break;
			}
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerStub.java
``` java
	 * view mock events
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of event to be viewed
	 */
	public ArrayList<Event> viewStub(ParsedCommand parsedCommand) {
		ArrayList<Event> viewEvents = new ArrayList<>();

		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				viewEvents.add(event);
				break;
			}
		}

		return viewEvents;
	}

	/**
```
###### ./src/stub/EventHandlerStub.java
``` java
	 * Get all events
	 */
	@Override
	public List<Event> getAllEvents() {
		return events;
	}

}
```
###### ./src/stub/EventHandlerUndoAddStub.java
``` java
 * @author yeehuipoh
 *
 */
public class EventHandlerUndoAddStub extends EventHandler {
	private ArrayList<Event> events = new ArrayList<>();

	/**
```
###### ./src/stub/EventHandlerUndoAddStub.java
``` java
	 * Execute command
	 */
	public ArrayList<Event> execute(ParsedCommand parsedCommand) {
		ArrayList<Event> eventList = new ArrayList<>();

		if (parsedCommand.getCommand() == Command.ADD) {
			eventList = addStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.DELETE) {
			eventList = deleteStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.UPDATE) {
			eventList = updateStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW) {
			eventList = viewStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW_ALL) {
			eventList = events;
		} else if (parsedCommand.getCommand() == Command.UNDO) {
			events = new ArrayList<>();
			eventList = events;
		}

		return eventList;
	}

	/**
```
###### ./src/stub/EventHandlerUndoAddStub.java
``` java
	 * Add mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of added events
	 */
	public ArrayList<Event> addStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();

		Event event = new Event();
		event.setId("ggId");
		event.setTitle(parsedCommand.getTitle());
		event.setStartDateTime(parsedCommand.getStartDateTime());
		event.setEndDateTime(parsedCommand.getEndDateTime());
		event.setPriority(parsedCommand.getPriority());
		event.setLocation(parsedCommand.getLocation());
		event.setNotes(parsedCommand.getNotes());
		event.setReminder(parsedCommand.getReminder());

		events.add(event);
		currentEvents.add(event);

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoAddStub.java
``` java
	 * delete mock events
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of deleted events
	 */
	public ArrayList<Event> deleteStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		int position = -1;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getId().equals(parsedCommand.getId())) {
				position = i;
				break;
			}
		}

		if (position >= 0) {
			currentEvents.add(events.get(position));
			events.remove(position);
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoAddStub.java
``` java
	 * update mock events
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of updated events
	 */
	public ArrayList<Event> updateStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				event.setPriority(parsedCommand.getPriority());

				currentEvents.add(event);
				break;
			}
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoAddStub.java
``` java
	 * view mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of events to be viewed
	 */
	public ArrayList<Event> viewStub(ParsedCommand parsedCommand) {
		ArrayList<Event> viewEvents = new ArrayList<>();

		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				viewEvents.add(event);
				break;
			}
		}

		return viewEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoAddStub.java
``` java
	 * Get all events
	 */
	@Override
	public List<Event> getAllEvents() {
		return events;
	}
}
```
###### ./src/stub/EventHandlerUndoDeleteStub.java
``` java
 * @author yeehuipoh
 *
 */
public class EventHandlerUndoDeleteStub extends EventHandler {
	private ArrayList<Event> events = new ArrayList<>();
	private ParsedCommand addParsedCommand = null;

	/**
```
###### ./src/stub/EventHandlerUndoDeleteStub.java
``` java
	 * Execute command
	 */
	public ArrayList<Event> execute(ParsedCommand parsedCommand) {
		ArrayList<Event> eventList = new ArrayList<>();

		if (parsedCommand.getCommand() == Command.ADD) {
			eventList = addStub(parsedCommand);
			addParsedCommand = parsedCommand;
		} else if (parsedCommand.getCommand() == Command.DELETE) {
			eventList = deleteStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.UPDATE) {
			eventList = updateStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW) {
			eventList = viewStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW_ALL) {
			eventList = events;
		} else if (parsedCommand.getCommand() == Command.UNDO) {
			eventList = addStub(addParsedCommand);
		} else if (parsedCommand.getCommand() == Command.UNDELETE) {
			eventList = addStub(addParsedCommand);
		}

		return eventList;
	}

	/**
```
###### ./src/stub/EventHandlerUndoDeleteStub.java
``` java
	 * Add mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of added events
	 */
	public ArrayList<Event> addStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();

		Event event = new Event();
		event.setId("ggId");
		event.setTitle(parsedCommand.getTitle());
		event.setStartDateTime(parsedCommand.getStartDateTime());
		event.setEndDateTime(parsedCommand.getEndDateTime());
		event.setPriority(parsedCommand.getPriority());
		event.setLocation(parsedCommand.getLocation());
		event.setNotes(parsedCommand.getNotes());
		event.setReminder(parsedCommand.getReminder());

		events.add(event);
		currentEvents.add(event);

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoDeleteStub.java
``` java
	 * delete mock events
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of deleted events
	 */
	public ArrayList<Event> deleteStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		int position = -1;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getId().equals(parsedCommand.getId())) {
				position = i;
				break;
			}
		}

		if (position >= 0) {
			currentEvents.add(events.get(position));
			events.remove(position);
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoDeleteStub.java
``` java
	 * update mock events
	 * 
	 * @param parsedCommand
	 *            structure command
	 * @return list of updated events
	 */
	public ArrayList<Event> updateStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				event.setPriority(parsedCommand.getPriority());

				currentEvents.add(event);
				break;
			}
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoDeleteStub.java
``` java
	 * view mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of events to be viewed
	 */
	public ArrayList<Event> viewStub(ParsedCommand parsedCommand) {
		ArrayList<Event> viewEvents = new ArrayList<>();

		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				viewEvents.add(event);
				break;
			}
		}

		return viewEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoDeleteStub.java
``` java
	 * Get all events
	 */
	@Override
	public List<Event> getAllEvents() {
		return events;
	}
}
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
 * @author yeehuipoh
 *
 */
public class EventHandlerUndoUpdateStub extends EventHandler {
	private ArrayList<Event> events = new ArrayList<>();
	private ParsedCommand addParsedCommand = null;

	/**
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
	 * Execute command
	 */
	public ArrayList<Event> execute(ParsedCommand parsedCommand) {
		ArrayList<Event> eventList = new ArrayList<>();

		if (parsedCommand.getCommand() == Command.ADD) {
			eventList = addStub(parsedCommand);
			addParsedCommand = parsedCommand;
		} else if (parsedCommand.getCommand() == Command.DELETE) {
			eventList = deleteStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.UPDATE) {
			eventList = updateStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW) {
			eventList = viewStub(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW_ALL) {
			eventList = events;
		} else if (parsedCommand.getCommand() == Command.UNDO) {
			eventList = undoStub();
		} else if (parsedCommand.getCommand() == Command.UNDELETE) {
			eventList = addStub(addParsedCommand);
		}

		return eventList;
	}

	/**
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
	 * Undo update
	 * 
	 * @return list of events
	 */
	private ArrayList<Event> undoStub() {
		for (Event event : events) {
			event.setPriority(Priority.VERY_HIGH);
		}

		return events;
	}

	/**
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
	 * add mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of events added
	 */
	public ArrayList<Event> addStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();

		Event event = new Event();
		event.setId("ggId");
		event.setTitle(parsedCommand.getTitle());
		event.setStartDateTime(parsedCommand.getStartDateTime());
		event.setEndDateTime(parsedCommand.getEndDateTime());
		event.setPriority(parsedCommand.getPriority());
		event.setLocation(parsedCommand.getLocation());
		event.setNotes(parsedCommand.getNotes());
		event.setReminder(parsedCommand.getReminder());

		events.add(event);
		currentEvents.add(event);

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
	 * delete mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of deleted events
	 */
	public ArrayList<Event> deleteStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		int position = -1;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getId().equals(parsedCommand.getId())) {
				position = i;
				break;
			}
		}

		if (position >= 0) {
			currentEvents.add(events.get(position));
			events.remove(position);
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
	 * update mock event
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of updated events
	 */
	public ArrayList<Event> updateStub(ParsedCommand parsedCommand) {
		ArrayList<Event> currentEvents = new ArrayList<Event>();
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				event.setPriority(parsedCommand.getPriority());

				currentEvents.add(event);
				break;
			}
		}

		return currentEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
	 * view mock events
	 * 
	 * @param parsedCommand
	 *            structured command
	 * @return list of events to be viewed
	 */
	public ArrayList<Event> viewStub(ParsedCommand parsedCommand) {
		ArrayList<Event> viewEvents = new ArrayList<>();

		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				viewEvents.add(event);
				break;
			}
		}

		return viewEvents;
	}

	/**
```
###### ./src/stub/EventHandlerUndoUpdateStub.java
``` java
	 * Get all events
	 */
	@Override
	public List<Event> getAllEvents() {
		return events;
	}
}
```
###### ./src/stub/ParserStub.java
``` java
 * @author yeehuipoh
 *
 */
public class ParserStub extends Parser {

	/**
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
###### ./src/stub/ParserStub.java
``` java
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
```
