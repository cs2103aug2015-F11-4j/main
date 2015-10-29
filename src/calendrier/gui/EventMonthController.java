package calendrier.gui;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import utils.Event;
import utils.Priority;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class EventMonthController extends StackPane {

	@FXML
	private GridPane eventGridPaneMonth;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblEventID1;
	@FXML
	private Label lblEventID2;
	@FXML
	private Label lblEvent1;
	@FXML
	private Label lblEvent2;
	@FXML
	private Label lblEvent3;

	private static final String SINGLE_EVENT_LAYOUT_FXML = "/calendrier/resources/EventMonth.fxml";

	private static final String VALUE_SHOW_EMPTY_DATA = " ";

	public EventMonthController(int date, int month, int year, List<Event> events, List<String> idList) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SINGLE_EVENT_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initEventValue(date, month, year, events, idList);
	}

	@SuppressWarnings("deprecation")
	public void initEventValue(int date, int month, int year, List<Event> events, List<String> idList) {
		
		Calendar cal = Calendar.getInstance();

		lblDate.setText(checkDate(date));
		
		if (events != null && events.size() > 0) {
			if (events.get(0).getTitle() != null) {
				lblEvent1.setText(events.get(0).getTitle());
				lblEventID1.setText(Integer.toString(computeFakeId(idList,events.get(0).getId())));
				changeTextColor(events.get(0).getPriority(), lblEvent1);
				if (events.size() > 1) {
					lblEvent2.setText(events.get(1).getTitle());
					lblEventID2.setText(Integer.toString(computeFakeId(idList,events.get(1).getId())));
					changeTextColor(events.get(1).getPriority(), lblEvent2);
					if (events.size() > 2) {
						lblEvent3.setText("...");
						changeTextColor(Priority.HIGH, lblEvent3);
					}
				}
			} else {
				lblEvent1.setText(VALUE_SHOW_EMPTY_DATA);
				lblEvent2.setText(VALUE_SHOW_EMPTY_DATA);
				lblEvent3.setText(VALUE_SHOW_EMPTY_DATA);
			}
		} else {
			lblEvent1.setText(VALUE_SHOW_EMPTY_DATA);
			lblEvent2.setText(VALUE_SHOW_EMPTY_DATA);
			lblEvent3.setText(VALUE_SHOW_EMPTY_DATA);
		}
		
		//cal.set(2015, 10, 18, 10, 55, 00);
		if (cal.getTime().getDate() == date && cal.getTime().getMonth()==month && (cal.getTime().getYear()+1900)==year) {
			eventGridPaneMonth.setStyle("-fx-border-color: red;-fx-border-width: 2.0px;");
		}
		if(cal.getTime().getDate() > date && cal.getTime().getMonth()>=month && (cal.getTime().getYear()+1900)>=year){
			lblEvent1.setStyle("-fx-text-fill: darkgray;");
			lblEvent2.setStyle("-fx-text-fill: darkgray;");
			lblEvent3.setStyle("-fx-text-fill: darkgray;");
		}
	}
	private int computeFakeId(List<String> idList, String id){
		for(int i=0;i<idList.size();i++){
			if(idList.get(i).equals(id)){
				return i;
			}
		}
		return -1;
	}
	private String checkDate(int date){
		if(date!=0){
			return String.format("%d", date);
		}
		return VALUE_SHOW_EMPTY_DATA;
	}
	
	private void changeTextColor(Priority priority, Label lblEvent) {
		if (priority == Priority.VERY_HIGH) {
			lblEvent.setStyle("-fx-text-fill: red;");
		} else if (priority == Priority.HIGH) {
			lblEvent.setStyle("-fx-text-fill: #FFA07A;");
		} else if (priority == Priority.MEDIUM) {
			lblEvent.setStyle("-fx-text-fill: gold;");
		} else if (priority == Priority.LOW) {
			lblEvent.setStyle("-fx-text-fill: #00FF7F;");
		} else if (priority == Priority.VERY_LOW) {
			lblEvent.setStyle("-fx-text-fill: #2E8B57;");
		} else {
			lblEvent.setStyle("-fx-text-fill: black;");
		}
	}
}
