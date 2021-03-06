package stub;

import java.util.ArrayList;
import java.util.List;

import calendrier.EventHandler;
import utils.Command;
import utils.Event;
import utils.ParsedCommand;

/**
 * @@author A0088646M-unused
 * @author yeehuipoh
 *
 */
public class EventHandlerUndoDeleteStub extends EventHandler {
	private ArrayList<Event> events = new ArrayList<>();
	private ParsedCommand addParsedCommand = null;

	/**
	 * @@author A0088646M-unused
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
	 * @@author A0088646M-unused
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
	 * @@author A0088646M-unused
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
	 * @@author A0088646M-unused
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
	 * @@author A0088646M-unused
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
	 * @@author A0088646M-unused
	 * Get all events
	 */
	@Override
	public List<Event> getAllEvents() {
		return events;
	}
}
