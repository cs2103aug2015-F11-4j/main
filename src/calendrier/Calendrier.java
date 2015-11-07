package calendrier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import utils.Event;
import utils.UserCommandException;

/**
 * @@author A0088646M
 * @author yeehuipoh
 *
 */
public class Calendrier {
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("cli")) {
			// Start command line interface loop
			loop();
		} else {
			// Calls the main function of the GUI
			calendrier.gui.UserInterface.main(args);
		}
	}

	/**
	 * @@author A0088646M
	 * Loop program until no input (basic console based UI)
	 */
	private static void loop() {
		MainLogic mainLogic = new MainLogic();
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String input = getInput(bufferedReader);

		while (input != null) {
			try {
				mainLogic.execute(input);
			} catch (UserCommandException userCommandException) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(userCommandException.getMessage());
			}
			showEventListToUser(mainLogic.getAllEvents());
			input = getInput(bufferedReader);
		}
	}

	/**
	 * @@author A0088646M
	 * Get input from user
	 * 
	 * @param reader
	 *            input bufferedreader
	 * @return input line
	 */
	private static String getInput(BufferedReader reader) {
		String content = null;
		try {
			// Read input
			content = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * @@author A0088646M
	 * Show event list to user
	 * 
	 * @param eventList
	 *            list of events
	 */
	public static void showEventListToUser(List<Event> eventList) {
		for (Event event : eventList) {
			if (event != null) {
				showToUser(event);
			}
		}
	}

	/**
	 * @@author A0088646M
	 * Print event title to console
	 * 
	 * @param event
	 *            event to show
	 */
	private static void showToUser(Event event) {
		showToUser(event.getTitle());
	}

	/**
	 * @@author A0088646M
	 * Print output to console
	 * 
	 * @param output
	 *            output to show
	 */
	private static void showToUser(String output) {
		System.out.println(output);
	}

}
