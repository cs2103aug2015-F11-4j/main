package stub;

import java.util.ArrayList;
import java.util.List;

import calendrier.EventHandler;
import utils.Command;
import utils.Event;
import utils.ParsedCommand;
import utils.Priority;

public class EventHandlerStub extends EventHandler {
	private List<Event> events = new ArrayList<>();

	public EventHandlerStub() {
		Event deleteEvent = new Event();
		deleteEvent.setId("deleteId");
		deleteEvent.setTitle("deleteTitle");
		deleteEvent.setPriority(Priority.HIGH);
		deleteEvent.setLocation("deleteLocation");
		deleteEvent.setNotes("deleteNotes");
		events.add(deleteEvent);

		Event updateEvent = new Event();
		updateEvent.setId("updateId");
		updateEvent.setTitle("updateTitle");
		updateEvent.setPriority(Priority.VERY_HIGH);
		updateEvent.setLocation("updateLocation");
		updateEvent.setNotes("updateNotes");
		events.add(updateEvent);
		
		Event viewEvent = new Event();
		viewEvent.setId("viewId");
		viewEvent.setTitle("viewTitle");
		viewEvent.setPriority(Priority.VERY_HIGH);
		viewEvent.setLocation("viewLocation");
		viewEvent.setNotes("viewNotes");
		events.add(viewEvent);
	}

	public List<Event> execute(ParsedCommand parsedCommand) {
		List<Event> eventList = new ArrayList<>();

		if (parsedCommand.getCommand() == Command.ADD) {
			eventList = add(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.DELETE) {
			eventList = delete(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.UPDATE) {
			eventList = update(parsedCommand);
		} else if (parsedCommand.getCommand() == Command.VIEW) {
			eventList = view(parsedCommand);
		}

		return eventList;
	}

	public List<Event> add(ParsedCommand parsedCommand) {
		Event event = new Event();
		event.setId(parsedCommand.getTitle() + "Id");
		event.setTitle(parsedCommand.getTitle());
		event.setStartDateTime(parsedCommand.getStartDateTime());
		event.setEndDateTime(parsedCommand.getEndDateTime());
		event.setPriority(parsedCommand.getPriority());
		event.setLocation(parsedCommand.getLocation());
		event.setNotes(parsedCommand.getNotes());
		event.setReminder(parsedCommand.getReminder());

		events.add(event);
		
		return events;
	}

	public List<Event> delete(ParsedCommand parsedCommand) {
		// Do nothing (delete is not there)
		int position = -1;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getId().equals(parsedCommand.getId())) {
				position = i;
				break;
			}
		}

		if (position >= 0) {
			events.remove(position);
		}

		return events;
	}

	public List<Event> update(ParsedCommand parsedCommand) {
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (event.getId().equals(parsedCommand.getId())) {
				event.setTitle(parsedCommand.getTitle());
				event.setStartDateTime(parsedCommand.getStartDateTime());
				event.setEndDateTime(parsedCommand.getEndDateTime());
				event.setPriority(parsedCommand.getPriority());
				event.setLocation(parsedCommand.getLocation());
				event.setNotes(parsedCommand.getNotes());
				event.setReminder(parsedCommand.getReminder());
				break;
			}
		}

		return events;
	}

	public List<Event> view(ParsedCommand parsedCommand) {
		List<Event> viewEvents = new ArrayList<>();
		
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if(event.getId().equals(parsedCommand.getId())){
				viewEvents.add(event);
				break;
			}
		}

		return viewEvents;
	}

}
