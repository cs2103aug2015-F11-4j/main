package calendrier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import utils.OnRemindListener;

/**
 * @@author A0088646M
 * @author yeehuipoh
 *
 */
public class ReminderManager implements Runnable {
	private Thread reminderThread;
	private List<Event> events;
	private OnRemindListener onRemindListener = null;

	/**
	 * @@author A0088646M
	 * Constructor
	 */
	public ReminderManager() {
		this.events = new ArrayList<>();

		// Setup Thread
		this.reminderThread = new Thread(this);
		this.reminderThread.start();
	}

	/**
	 * @@author A0088646M
	 * Thread function
	 */
	@Override
	public void run() {
		while (true) {
			try {
				checkEvents();

				long sleepTime = getSleepTime();

				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/* @@author A0088646M */
	private long getSleepTime() {
		long sleepTime = 60000 - Calendar.getInstance().getTimeInMillis() % 60000;
		return sleepTime;
	}

	/**
	 * @@author A0088646M
	 * Check all the events
	 */
	public void checkEvents() {
		for (int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			checkReminder(event);
		}
	}

	/**
	 * @@author A0088646M
	 * Check event for reminder
	 * 
	 * @param event
	 *            event to be checked
	 */
	private void checkReminder(Event event) {
		List<Calendar> reminders = event.getReminder();
		for (int i = 0; i < reminders.size(); i++) {
			Calendar reminderTime = reminders.get(i);
			Calendar now = Calendar.getInstance();
			if (reminderTime != null) {
				if (compareTime(reminderTime, now)) {
					sendReminder(event);
				}
			}
		}
	}

	/**
	 * @@author A0088646M
	 * Sends reminder through the onRemind listener
	 * 
	 * @param event
	 *            event which to be reminded
	 */
	private void sendReminder(Event event) {
		// System.out.println(event.getTitle());
		if (this.onRemindListener != null) {
			this.onRemindListener.onRemind(event);
		}
	}

	/**
	 * @@author A0088646M
	 * Compares time to current time (accuracy to minute)
	 * 
	 * @param time1
	 *            time as comparison reference
	 * @param time2
	 *            time to be compared to
	 * @return true if the two times are the same, false if otherwise
	 */
	public boolean compareTime(Calendar time1, Calendar time2) {
		long timeDifference = time1.getTimeInMillis() - time2.getTimeInMillis();
		return timeDifference < 60000 && timeDifference >= 0;
	}

	/**
	 * @@author A0088646M
	 * Adds event to reminder
	 * 
	 * @param event
	 *            event to be added
	 */
	public void addReminder(Event event) {
		assert (event.getReminder() != null);
		this.events.add(event);
	}

	/**
	 * @@author A0088646M
	 * Removes event from reminder
	 * 
	 * @param event
	 *            event to be removed
	 */
	public void removeReminder(Event event) {
		int toBeRemovedIndex = -1;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getId().equals(event.getId())) {
				toBeRemovedIndex = i;
				break;
			}
		}

		if (toBeRemovedIndex != -1) {
			events.remove(toBeRemovedIndex);
		}
	}

	/**
	 * @@author A0088646M
	 * Update event in reminder
	 * 
	 * @param event
	 *            event to be updated
	 */
	public void updateReminder(Event event) {
		// Remove and Add again
		removeReminder(event);
		addReminder(event);
	}

	/**
	 * @@author A0088646M
	 * Sets the listener for remind event
	 * 
	 * @param listener
	 *            onRemind listener to be used
	 */
	public void setOnRemindListener(OnRemindListener listener) {
		if (listener != null) {
			this.onRemindListener = listener;
		}
	}
}
