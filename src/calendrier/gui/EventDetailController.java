package calendrier.gui;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import utils.Event;
import utils.Priority;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class EventDetailController extends BorderPane {

	@FXML
	private ImageView imgType;
	@FXML
	private Label lblID;
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblStartDateTime;
	@FXML
	private Label lblEndDateTime;
	@FXML
	private Label lblLocation;
	@FXML
	private Label lblReminder;
	@FXML
	private Label lblNotes;
	@FXML
	private Label lblPriority;
	@FXML 
	private Label lblGroup;
	
	private static final String EVENT_DETAIL_LAYOUT_FXML = "/calendrier/resources/ViewEventDetail.fxml";
	
	private static final String DEFAULT_EVENT_TYPE_IMAGE = "/calendrier/resources/information.jpeg";
	private static final String REPORT_EVENT_TYPE_IMAGE = "/calendrier/resources/report.png";
	private static final String DINNER_EVENT_TYPE_IMAGE = "/calendrier/resources/dinner.png.";
	private static final String READING_EVENT_TYPE_IMAGE = "/calendrier/resources/reading.png";
	private static final String MEETING_EVENT_TYPE_IMAGE = "/calendrier/resources/meeting.png";
	
	private static final int VALUE_EMPTY_SIZE = 0;
	private static final int VALUE_GET_INDEX = 0;
	
	private static final String VALUE_SHOW_EMPTY_DATA = "-";
	
	public EventDetailController(Event event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(EVENT_DETAIL_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initEventValue(event);
	}
	
	public void initEventValue(Event event){
		lblID.setText(checkExistValue(event.getId()));
		lblTitle.setText(checkExistValue(event.getTitle()));
		lblStartDateTime.setText(checkExistDate(event.getStartDateTime()));
		lblStartDateTime.setText(checkExistDate(event.getEndDateTime()));
		lblLocation.setText(checkExistValue(event.getLocation()));
		lblReminder.setText(checkExistDate(event.getReminder()));
		lblNotes.setText(checkExistValue(event.getNotes()));
		lblPriority.setText(checkExistPriority(event.getPriority()));
		
		Image img;
		if(checkExistValue(event.getGroups().toString()).equalsIgnoreCase(VALUE_SHOW_EMPTY_DATA)) {
			lblGroup.setText(VALUE_SHOW_EMPTY_DATA);
			img = new Image(DEFAULT_EVENT_TYPE_IMAGE);
		} else {
			String strGroup = convertGroupToString(event.getGroups());
			lblGroup.setText(strGroup);
			String strImage = getGrpImage(strGroup);
			img = new Image(strImage);
			
		}
		imgType.setImage(img);
	}
	
	private static String checkExistPriority(Priority priority) {
		try {
			changeBorderColor(priority);
			return priority.toString();
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}
	
	private static void changeBorderColor(Priority priority) {
		if(priority == Priority.VERY_HIGH) {
			
		} else if(priority == Priority.HIGH) {
			
		} else if(priority == Priority.MEDIUM) {
			
		} else if(priority == Priority.LOW) {
			
		} else if(priority == Priority.VERY_LOW) {
			
		} else {
			
		}
	}
	
	private static String checkExistValue(String parseInValue) {
		try {
			return parseInValue;
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}
	
	private static String checkExistDate(Calendar calender) {
		try {
			return calender.toString();
		} catch (NullPointerException e) {
			return VALUE_SHOW_EMPTY_DATA;
		}
	}
	
	private static String convertGroupToString(Collection<String> groups) {
		String strGrp = "";
		if(groups.size() != VALUE_EMPTY_SIZE) {
			for (String str : groups) {
				strGrp += str + ", ";	
			}
		}
		return strGrp;	
	}
	
	private static String getGrpImage(String strGrp) {
		String[] groups = strGrp.split(",");
		String mainGrp = groups[VALUE_GET_INDEX];
		
		if(mainGrp.equalsIgnoreCase("report")) {
			return REPORT_EVENT_TYPE_IMAGE;
		} else if(mainGrp.equalsIgnoreCase("dinner")) {
			return DINNER_EVENT_TYPE_IMAGE;
		} else if(mainGrp.equalsIgnoreCase("meeting")) {
			return MEETING_EVENT_TYPE_IMAGE;
		} else if(mainGrp.equalsIgnoreCase("reading")) {
			return READING_EVENT_TYPE_IMAGE;
		} else {
			return DEFAULT_EVENT_TYPE_IMAGE;
		}
	}
}
