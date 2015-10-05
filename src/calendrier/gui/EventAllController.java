package calendrier.gui;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import utils.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class EventAllController extends BorderPane {

	@FXML
	private TableView<Event> viewAllTable;
	@FXML
	private TableColumn<Event, String> eventIdColumn;
	@FXML
	private TableColumn<Event, String> eventTitleColumn;
	@FXML
	private TableColumn<Event, Date> eventDateColumn;
	@FXML
	private TableColumn<Event, Date> eventTimeColumn;

	private static final String EVENT_ALL_LAYOUT_FXML = "/calendrier/resources/ViewAll.fxml";
	private static final String NO_EVENT_LAYOUT_FXML = "/calendrier/resources/NoEvent.fxml";

	public EventAllController(Collection<Event> inputAllEvents) {
		FXMLLoader loader = getLoader(inputAllEvents);
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(isEventsInCollection(inputAllEvents)) {
			drawTable(inputAllEvents);
		}
	}

	public boolean isEventsInCollection(Collection<Event> inputAllEvents) {

		if (inputAllEvents.size() == 0) {
			return false;
		}
		return true;
	}

	private FXMLLoader getLoader(Collection<Event> inputAllEvents) {
		if (isEventsInCollection(inputAllEvents)) {
			return new FXMLLoader(getClass().getResource(EVENT_ALL_LAYOUT_FXML));
		} else {
			return new FXMLLoader(getClass().getResource(NO_EVENT_LAYOUT_FXML));
		}
	}
	
	private void drawTable(Collection<Event> inputAllEvents) {
		ObservableList<Event> allEvents = FXCollections.observableArrayList();
		for (Event event : inputAllEvents) {
			allEvents.add(event);
		}

		viewAllTable.setItems(allEvents);
		eventIdColumn
				.setCellValueFactory(new PropertyValueFactory<Event, String>(
						"id"));
		eventTitleColumn
				.setCellValueFactory(new PropertyValueFactory<Event, String>(
						"title"));
		eventDateColumn
				.setCellValueFactory(new PropertyValueFactory<Event, Date>(
						"startDateTime"));
		eventTimeColumn
				.setCellValueFactory(new PropertyValueFactory<Event, Date>(
						"startDateTime"));
	}
}
