package stub;

import java.util.ArrayList;
import java.util.List;

import calendrier.EventHandler;
import utils.Command;
import utils.Event;
import utils.ParsedCommand;

public class EventHandlerUndoAddStub extends EventHandler {
	private ArrayList<Event> events = new ArrayList<>();

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

	@Override
	public List<Event> getAllEvents() {
		return events;
	}
}
