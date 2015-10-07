package calendrier.gui;

import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;
import utils.Event;

public class EventBean {
	private Event event;
	private SimpleStringProperty id;
	private SimpleStringProperty title;
	private SimpleStringProperty eventDate;

	private static final String VALUE_SHOW_EMPTY_DATA = "-";

	public EventBean(Event event) {
		this.event = event;
		this.id = new SimpleStringProperty(checkExistValue(event.getId()));
		this.title = new SimpleStringProperty(checkExistValue(event.getTitle()));
		this.eventDate = new SimpleStringProperty(
				checkExistDate(event.getStartDateTime()));
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public SimpleStringProperty idProperty() {
		return id;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public SimpleStringProperty titleProperty() {
		return title;
	}

	public String getEventDate() {
		return eventDate.get();
	}

	public void setEventDate(String eventDate) {
		this.eventDate.set(eventDate);
	}

	public SimpleStringProperty eventDateProperty() {
		return eventDate;
	}

	private static String checkExistValue(String parseInValue) {
		try {
			return parseInValue;
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}

	private static String checkExistDate(Calendar calendar) {
		try {
			return calendar.getTime().toString();
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}
}
