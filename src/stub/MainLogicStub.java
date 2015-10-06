package stub;

import java.util.ArrayList;
import java.util.List;

import calendrier.MainLogic;
import utils.Command;
import utils.Event;
import utils.Priority;

public class MainLogicStub extends MainLogic {
	List<Event> events = new ArrayList<>();
	Event event = null;

	/**
	 * Execute command
	 * 
	 * @param command
	 *            command input
	 * @return command type
	 */
	@Override
	public Command execute(String command) {
		Command cmd = null;
		if (command.equals("add abc")) {
			cmd = Command.ADD;
			event = new Event();
			event.setId("abcId");
			event.setTitle("abc");

			events.add(event);
		} else if (command.equals("update id abcId, priority low")) {
			cmd = Command.UPDATE;
			update();
		} else if (command.equals("delete abcId")) {
			cmd = Command.DELETE;
			delete();
		} else if (command.equals("view abcId")) {
			cmd = Command.VIEW;
			view();
		} else if (command.equals("undelete")) {
			cmd = Command.UNDELETE;
			undelete();
		} else if (command.equals("undo")) {
			cmd = Command.UNDO;
			undo();
		} else if (command.equals("save in abcFile.txt")) {
			cmd = Command.STORAGE_LOCATION;
		} else if (command.equals("previous")) {
			cmd = Command.PREVIOUS;
		} else if (command.equals("next")) {
			cmd = Command.NEXT;
		} else if (command.equals("view all")) {
			cmd = Command.VIEW_ALL;
		} else if (command.equals("exit")) {
			cmd = Command.EXIT;
		} else if (command.equals("help")) {
			cmd = Command.HELP;
		}
		return cmd;
	}

	/**
	 * Undo
	 */
	private void undo() {
		if (events.size() > 0) {
			for (Event e : events) {
				if (e.getId().equals("abcId")) {
					if (e.getPriority() == Priority.LOW) {
						// Last action is update
						e.setPriority(null);
					} else {
						// Last action is add
						events.remove(e);
					}
					break;
				}
			}
		} else {
			undelete();
		}
	}

	/**
	 * Undelete event
	 */
	private void undelete() {
		events = new ArrayList<>();

		event = new Event();
		event.setId("abcId");
		event.setTitle("abc");

		events.add(event);
	}

	/**
	 * View event
	 */
	private void view() {
		for (Event e : events) {
			if (e.getId().equals("abcId")) {
				event = e;
				break;
			}
		}
	}

	/**
	 * Delete event
	 */
	private void delete() {
		for (Event e : events) {
			if (e.getId().equals("abcId")) {
				events.remove(e);
				event = e;
				break;
			}
		}
	}

	/**
	 * Update event
	 */
	private void update() {
		for (Event e : events) {
			if (e.getId().equals("abcId")) {
				e.setPriority(Priority.LOW);
				event = e;
				break;
			}
		}
	}

	/**
	 * Get event of last action
	 */
	@Override
	public Event getEvent() {
		return event;
	}

	/**
	 * Get all events
	 * 
	 * @return list of all events
	 */
	@Override
	public List<Event> getAllEvents() {
		return events;
	}

}
