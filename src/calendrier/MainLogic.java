package calendrier;

import java.util.ArrayList;
import java.util.List;

import utils.Command;
import utils.Event;
import utils.OnRemindListener;
import utils.ParsedCommand;

/**
 * For handling the main logic
 * 
 * @author yeehuipoh
 *
 */
public class MainLogic {
	private Parser parser = null;
	private EventHandler eventHandler = null;
	private Event event = null;
	private List<Event> events = null;
	private List<Event> filteredEvents = null;

	/**
	 * Constructor to initialize the main components of Main Logic
	 */
	public MainLogic() {
		parser = new Parser();
		eventHandler = new EventHandler();
	}

	/**
	 * Inject a stub event handler
	 * 
	 * @param eventHandler
	 *            stub event handler to be injected
	 */
	public void injectEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	/**
	 * Inject a stub parser
	 * 
	 * @param parser
	 *            stub parser to be injected
	 */
	public void injectParser(Parser parser) {
		this.parser = parser;
	}

	/**
	 * Execute command
	 * 
	 * @param command
	 *            Command string input from user
	 * @return command action
	 */
	public Command execute(String command) {

		ParsedCommand parsedCommand = parser.parse(command);

		assert (parsedCommand != null);
		if (parsedCommand.getCommand() != null) {
			performCommand(parsedCommand);
		}

		return parsedCommand.getCommand();
	}

	/**
	 * Perform the parsed command
	 * 
	 * @param parsedCommand
	 *            parsedCommand from the parser
	 */
	private void performCommand(ParsedCommand parsedCommand) {
		List<Event> eventList = new ArrayList<>();

		try {
			eventList = eventHandler.execute(parsedCommand);
			if(parsedCommand.getCommand() == Command.FILTER){
				filteredEvents = eventList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateEvent(eventList);
	}

	/**
	 * Update event of the last action
	 * 
	 * @param eventList
	 *            events returned from event handler after execution
	 */
	private void updateEvent(List<Event> eventList) {
		if (eventList.size() > 0) {
			event = eventList.get(0);
		} else {
			event = null;
		}
	}

	/**
	 * Notifies user about event starting soon (Not Implemented Yet)
	 * 
	 * @param event
	 *            event that is starting soon
	 */
	public void notifyUser(Event event) {
	}

	/**
	 * Gets the event of the last action performed
	 * 
	 * @return event which the last action has performed on
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Gets all available events
	 * 
	 * @return all events
	 */
	public List<Event> getAllEvents() {
		events = eventHandler.getAllEvents();
		return events;
	}
	
	public List<Event> getFilteredEvents(){
		return filteredEvents;
	}
	
	/**
	 * Set OnRemindListener
	 * @param listener	listener for reminder
	 */
	public void setOnRemindListener(OnRemindListener listener){
		// Set in event handler
	}
}
