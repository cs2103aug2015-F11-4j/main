package test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import calendrier.Parser;
import utils.ParsedCommand;

public class ParserTest {

	@Test
	public void viewAll() {
		Parser parser = new Parser();
		String userInput = "view all";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "VIEW_ALL", pc.getCommand().toString());
	}
	
	@Test
	public void help() {
		Parser parser = new Parser();
		String userInput = "help";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "HELP", pc.getCommand().toString());
	}
	
	@Test
	public void exit() {
		Parser parser = new Parser();
		String userInput = "exit";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "EXIT", pc.getCommand().toString());
	}
	
	@Test
	public void undo() {
		Parser parser = new Parser();
		String userInput = "undo";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UNDO", pc.getCommand().toString());
	}

	@Test
	public void undelete() {
		Parser parser = new Parser();
		String userInput = "undelete";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UNDELETE", pc.getCommand().toString());
	}

	@Test
	public void view() {
		Parser parser = new Parser();
		String userInput = "view";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "VIEW", pc.getCommand().toString());
	}

	@Test
	public void delete() {
		Parser parser = new Parser();
		String userInput = "delete";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "DELETE", pc.getCommand().toString());
	}

	@Test
	public void filterByStartDate() {
		Parser parser = new Parser();
		String userInput = "filter startdate 2015/12/29";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "FILTER", pc.getCommand().toString());

		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/12/29", startDate);
	}

	@Test
	public void filterByEndDate() {
		Parser parser = new Parser();
		String userInput = "filter enddate 2015/12/30";
		ParsedCommand pc = parser.parse(userInput);

		Calendar cal = pc.getEndDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("end date: ", "2015/12/30", endDate);
	}

	@Test
	public void filterByGroup() {
		Parser parser = new Parser();
		String userInput = "filter group personal stuff";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "FILTER", pc.getCommand().toString());
		assertEquals("group: ", "personal stuff", pc.getGroup());
	}

	@Test
	public void filterByPriority() {
		Parser parser = new Parser();
		String userInput = "filter priority very high ";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());
	}

	@Test
	public void save() {
		Parser parser = new Parser();
		String userInput = "savein my desktop";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "STORAGE_LOCATION", pc.getCommand().toString());
		assertEquals("storage location: ", "my desktop", pc.getStorageLocation());
	}

	@Test
	// Task is not a deadline
	public void update1() {
		Parser parser = new Parser();
		String userInput = "update id 3, title repeat sleep drink eat, "
				+ "startdate 2015/12/29, starttime 13.37, enddate 2015/12/30, "
				+ "endtime 14.44, priority very low, location my home, notes must do, "
				+ "recurring no, reminderdate 2015/12/30, remindertime 15.30 ";

		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UPDATE", pc.getCommand().toString());
		assertEquals("id: ", "3", pc.getId());
		assertEquals("title: ", "repeat sleep drink eat", pc.getTitle());

		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/12/29", startDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "13.37", startTime);

		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/12/30", endDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "14.44", endTime);

		assertEquals("priority: ", "VERY_LOW", pc.getPriority().toString());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", false, pc.getIsRecurring());

		Calendar cal3 = pc.getReminder();
		int year3 = cal3.get(Calendar.YEAR);
		int month3 = cal3.get(Calendar.MONTH) + 1;
		int day3 = cal3.get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		assertEquals("reminder date: ", "2015/12/30", reminderDate);

		int hour3 = cal3.get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);
		assertEquals("reminder time: ", "15.30", reminderTime);
	}

	@Test
	// Task is a deadline
	public void update2() {
		Parser parser = new Parser();
		String userInput = "update id 3, title repeat sleep drink eat, " + "enddate 2015/12/30, "
				+ "endtime 14.44, priority very low, location my home, notes must do, "
				+ "recurring no, reminderdate 2015/12/30, remindertime 15.30 ";

		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UPDATE", pc.getCommand().toString());
		assertEquals("id: ", "3", pc.getId());
		assertEquals("title: ", "repeat sleep drink eat", pc.getTitle());

		Calendar cal = pc.getEndDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("end date: ", "2015/12/30", endDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("end time: ", "14.44", endTime);

		assertEquals("priority: ", "VERY_LOW", pc.getPriority().toString());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", false, pc.getIsRecurring());

		Calendar cal3 = pc.getReminder();
		int year3 = cal3.get(Calendar.YEAR);
		int month3 = cal3.get(Calendar.MONTH) + 1;
		int day3 = cal3.get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		assertEquals("reminder date: ", "2015/12/30", reminderDate);

		int hour3 = cal3.get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);
		assertEquals("reminder time: ", "15.30", reminderTime);
	}

	@Test
	// Task is not a deadline
	public void add1() {
		Parser parser = new Parser();
		String userInput = "add title eat sleep drink repeat, startdate 2015/12/29, "
				+ "starttime 13.37, enddate 2015/12/30, endtime 14.44, priority very low, "
				+ "location my home, notes must do, recurring no, reminderdate 2015/12/30, " + "remindertime 15.30";

		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "ADD", pc.getCommand().toString());
		assertEquals("title: ", "eat sleep drink repeat", pc.getTitle());

		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/12/29", startDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "13.37", startTime);

		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/12/30", endDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "14.44", endTime);

		assertEquals("priority: ", "VERY_LOW", pc.getPriority().toString());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", false, pc.getIsRecurring());

		Calendar cal3 = pc.getReminder();
		int year3 = cal3.get(Calendar.YEAR);
		int month3 = cal3.get(Calendar.MONTH) + 1;
		int day3 = cal3.get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		assertEquals("reminder date: ", "2015/12/30", reminderDate);

		int hour3 = cal3.get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);
		assertEquals("reminder time: ", "15.30", reminderTime);
	}

	@Test
	// Task is a deadline
	public void add2() {
		Parser parser = new Parser();
		String userInput = "add title eat sleep drink repeat, "
				+ "enddate 2015/12/30, endtime 14.44, priority very low, "
				+ "location my home, notes must do, recurring no, reminderdate 2015/12/30, " + "remindertime 15.30";

		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "ADD", pc.getCommand().toString());
		assertEquals("title: ", "eat sleep drink repeat", pc.getTitle());

		Calendar cal = pc.getEndDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("end date: ", "2015/12/30", endDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("end time: ", "14.44", endTime);

		assertEquals("priority: ", "VERY_LOW", pc.getPriority().toString());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", false, pc.getIsRecurring());

		Calendar cal2 = pc.getReminder();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("reminder date: ", "2015/12/30", reminderDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("reminder time: ", "15.30", reminderTime);
	}

}
