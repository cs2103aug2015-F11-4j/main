package calendrier.gui;

import java.io.IOException;
import java.util.Collection;

import utils.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

public class EventAllController extends StackPane {

	@FXML
	private TableView<EventBean> viewAllTable;
	@FXML
	private TableColumn<EventBean, String> eventIdColumn;
	@FXML
	private TableColumn<EventBean, String> eventTitleColumn;
	@FXML
	private TableColumn<EventBean, String> eventDateColumn;
//	@FXML
//	private TableColumn<EventBean, String> eventTimeColumn;

	private static final String EVENT_ALL_LAYOUT_FXML = "/calendrier/resources/ViewAll.fxml";

	public EventAllController(Collection<Event> inputAllEvents) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				EVENT_ALL_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		drawTable(inputAllEvents);
	}

	private void drawTable(Collection<Event> inputAllEvents) {
		ObservableList<EventBean> allEvents = FXCollections.observableArrayList();
		for (Event event : inputAllEvents) {
			allEvents.add(new EventBean(event));
		}
		
		
		eventIdColumn
				.setCellValueFactory(new PropertyValueFactory<EventBean, String>(
						"id"));
		
		eventTitleColumn
				.setCellValueFactory(new PropertyValueFactory<EventBean, String>(
						"title"));
		
		eventDateColumn
				.setCellValueFactory(new PropertyValueFactory<EventBean, String>(
						"eventDate"));
		
		viewAllTable.setItems(allEvents);
		
		viewAllTable.setVisible(true);
	
		viewAllTable.getColumns().get(0).setVisible(true);
		viewAllTable.getColumns().get(1).setVisible(true);
		viewAllTable.getColumns().get(2).setVisible(true);
		
		System.out.println(eventIdColumn.getCellData(0));
		System.out.println(eventTitleColumn.getCellData(0));
		System.out.println(eventDateColumn.getCellData(0));
		System.out.println(viewAllTable.getColumns().size());
		System.out.println(viewAllTable.getItems().size());
		
		System.out.println(viewAllTable.getWidth());
		System.out.println(viewAllTable.getHeight());
//		eventTimeColumn
//				.setCellValueFactory(new PropertyValueFactory<EventBean, String>(
//						"eventTime"));
	}
}
