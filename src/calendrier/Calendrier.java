package calendrier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import utils.Event;

public class Calendrier {

	private static MainLogic mainLogic = new MainLogic();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loop();
	}

	/**
	 * Loop program until no input
	 */
	private static void loop() {
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String input = getInput(bufferedReader);
		
		while (input != null) {
			mainLogic.execute(input);
			showEventListToUser(mainLogic.getAllEvents());
			input = getInput(bufferedReader);
		}
	}

	/**
	 * Get input from user
	 * @param reader input bufferedreader
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
	 * Show event list to user
	 * @param eventList list of events
	 */
	public static void showEventListToUser(List<Event> eventList) {
		for (Event event : eventList) {
			if (event != null) {
				showToUser(event);
			}
		}
	}

	/**
	 * Print event title to console
	 * @param event event to show
	 */
	private static void showToUser(Event event) {
		System.out.println(event.getTitle());
	}
	
	/**
	 * Print output to console
	 * @param output output to show
	 */
	private static void showToUser(String output) {
		System.out.println(output);
	}

}
