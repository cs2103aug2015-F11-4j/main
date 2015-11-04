package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
		String userInput = "view 3";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "VIEW", pc.getCommand().toString());
		assertEquals("id: ", "3", pc.getId());
		
		Parser parser2 = new Parser();
		String userInput2 = "view";
		ParsedCommand pc2 = parser2.parse(userInput2);
		assertEquals("command: ", "VIEW", pc2.getCommand().toString());
		assertNull("id: ", pc2.getId());
		
		// Boundary value for view input
		Parser parser3 = new Parser();
		String userInput3 = "";
		ParsedCommand pc3 = parser3.parse(userInput3);
		assertNull("command: ", pc3.getCommand());
		assertNull("id: ", pc3.getId());
		
	}

	@Test
	public void delete() {
		Parser parser = new Parser();
		String userInput = "delete 4";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "DELETE", pc.getCommand().toString());
		assertEquals("id: ", "4", pc.getId());
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
	public void saveIn() {
		Parser parser = new Parser();
		String userInput = "save in my desktop";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "STORAGE_LOCATION", pc.getCommand().toString());
		assertEquals("storage location: ", "my desktop", pc.getStorageLocation());
	}

	@Test
	public void update() {
		Parser parser = new Parser();
		String userInput = "update 3, title repeat sleep drink eat, "
				+ "startdate 2015/12/29, starttime 13.37, enddate 2015/12/30, "
				+ "endtime 14.44, priority very low, group my personal group, "
				+ "location my home, notes must do, "
				+ "recur monthly, reminderdate 2015/12/29 2015/12/29 2015/12/30, "
				+ "remindertime 14.44 15.55 12.00";

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
		assertEquals("group: ", "my personal group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recur: ", "MONTHLY", pc.getRecurFreq().toString());

		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}
	
	@Test
	public void updateDeadline() {
		Parser parser = new Parser();
		String userInput = "update 3, title repeat sleep drink eat, "
				+ "deadlinedate 2015/12/30, "
				+ "deadlinetime 14.44, priority very low, group my personal group, "
				+ "location my home, notes must do, "
				+ "reminderdate 2015/12/29 2015/12/29 2015/12/30, "
				+ "remindertime 14.44 15.55 12.00";

		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UPDATE", pc.getCommand().toString());
		assertEquals("id: ", "3", pc.getId());
		assertEquals("title: ", "repeat sleep drink eat", pc.getTitle());

		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("deadline start date: ", "2015/12/30", startDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("deadline start time: ", "14.44", startTime);

		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("deadline end date: ", "2015/12/30", endDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("deadline end time: ", "14.44", endTime);

		assertEquals("priority: ", "VERY_LOW", pc.getPriority().toString());
		assertEquals("group: ", "my personal group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recur: ", null, pc.getRecurFreq());

		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}

	@Test
	// Task is not a deadline
	public void add() {
		Parser parser = new Parser();
		String userInput = "add eat sleep drink repeat, startdate 2015/12/29, "
				+ "starttime 13.37, enddate 2015/12/30, endtime 14.44, group my personal group, "
				+ "priority very low, location my home, notes must do, "
				+ "recur daily, reminderdate 2015/12/29 2015/12/29 2015/12/30, "
				+ "remindertime 14.44 15.55 12.00";

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
		assertEquals("group: ", "my personal group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", "DAILY", pc.getRecurFreq().toString());

		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}
	
	@Test
	public void addDeadline() {
		Parser parser = new Parser();
		String userInput = "add eat sleep drink repeat, deadlinedate 2015/12/30, "
				+ "deadlinetime 14.44, group my personal group, "
				+ "priority very low, location my home, notes must do, "
				+ "recur weekly, reminderdate 2015/12/29 2015/12/29 2015/12/30, "
				+ "remindertime 14.44 15.55 12.00";
	
		ParsedCommand pc = parser.parse(userInput);
		
		assertEquals("command: ", "ADD", pc.getCommand().toString());
		assertEquals("title: ", "eat sleep drink repeat", pc.getTitle());

		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("deadline start date: ", "2015/12/30", startDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("deadline start time: ", "14.44", startTime);

		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("deadline end date: ", "2015/12/30", endDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "14.44", endTime);

		assertEquals("priority: ", "VERY_LOW", pc.getPriority().toString());
		assertEquals("group: ", "my personal group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", "WEEKLY", pc.getRecurFreq().toString());

		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}

	@Test
	public void addSubtask() {
		Parser parser = new Parser();
		String userInput = "add subtask my first subtask! to 1, startdate 2015/11/11, "
				+ "starttime 13.37, enddate 2015/11/15, endtime 14.44, priority high, "
				+ "group it's a secret, location changi airport, notes I believe I can fly, "
				+ "recur yearly, reminderdate 2015/11/12 2015/11/13, remindertime 12.34 23.45";
		ParsedCommand pc = parser.parse(userInput);
		
		assertEquals("subtask: ", "my first subtask!", pc.getTitle());
		assertEquals("main id: ", "1", pc.getMainId());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/11/11", startDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "13.37", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/11/15", endDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "14.44", endTime);
		
		assertEquals("priority: ", "HIGH", pc.getPriority().toString());
		assertEquals("group: ", "it's a secret", pc.getGroup());
		assertEquals("location: ", "changi airport", pc.getLocation());
		assertEquals("notes: ", "I believe I can fly", pc.getNotes());
		assertEquals("recurring: ", "YEARLY", pc.getRecurFreq().toString());
	
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		assertEquals("reminder date1: ", "2015/11/12", reminderDate);
		assertEquals("reminder date2: ", "2015/11/13", reminderDate2);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);
		
		assertEquals("reminder time: ", "12.34", reminderTime);
		assertEquals("reminder time: ", "23.45", reminderTime2);
	}
	
	@Test
	public void addSubtaskDeadline() {
		Parser parser = new Parser();
		String userInput = "add subtask my first subtask! to 1, deadlinedate 2015/11/15, "
				+ "deadlinetime 14.44, priority high, "
				+ "group it's a secret, location changi airport, notes I believe I can fly, "
				+ "reminderdate 2015/11/12 2015/11/13, remindertime 12.34 23.45";
		ParsedCommand pc = parser.parse(userInput);
		
		assertEquals("subtask: ", "my first subtask!", pc.getTitle());
		assertEquals("main id: ", "1", pc.getMainId());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("deadline start date: ", "2015/11/15", startDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("deadline start time: ", "14.44", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("deadline end date: ", "2015/11/15", endDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("deadline end time: ", "14.44", endTime);
		
		assertEquals("priority: ", "HIGH", pc.getPriority().toString());
		assertEquals("group: ", "it's a secret", pc.getGroup());
		assertEquals("location: ", "changi airport", pc.getLocation());
		assertEquals("notes: ", "I believe I can fly", pc.getNotes());
		assertEquals("recurring: ", null, pc.getRecurFreq());
	
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		assertEquals("reminder date1: ", "2015/11/12", reminderDate);
		assertEquals("reminder date2: ", "2015/11/13", reminderDate2);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);
		
		assertEquals("reminder time: ", "12.34", reminderTime);
		assertEquals("reminder time: ", "23.45", reminderTime2);
	}
	
	
	
	
	/*************************** 
	 * SHORTENED COMMANDS 
	 ***************************/
	
	@Test
	public void viewAllShortened() {
		Parser parser = new Parser();
		String input = "-all";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "VIEW_ALL", pc.getCommand().toString());
	}
	
	@Test
	public void helpShortened() {
		Parser parser = new Parser();
		String input = "-h";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "HELP", pc.getCommand().toString());
	}
	
	@Test
	public void exitShortened() {
		Parser parser = new Parser();
		String input = "-e";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "EXIT", pc.getCommand().toString());
	}
	
	@Test
	public void previousShortened() {
		Parser parser = new Parser();
		String input = "-prev";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "PREVIOUS", pc.getCommand().toString());
	}
	
	@Test
	public void nextShortened() {
		Parser parser = new Parser();
		String input = "-nxt";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "NEXT", pc.getCommand().toString());
	}
	
	@Test
	public void saveInShortened() {
		Parser parser = new Parser();
		String input = "-s desktop";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "STORAGE_LOCATION", pc.getCommand().toString());
		assertEquals("title: ", "desktop", pc.getStorageLocation());
	}
	
	@Test
	public void undoShortened() {
		Parser parser = new Parser();
		String input = "-u";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "UNDO", pc.getCommand().toString());
	}
	
	@Test
	public void undeleteShortened() {
		Parser parser = new Parser();
		String input = "-undel 4";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "UNDELETE", pc.getCommand().toString());
		assertEquals("id: ", "4", pc.getId());
	}
	
	@Test
	public void viewShortened() {
		Parser parser = new Parser();
		String input = "-v 1";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "VIEW", pc.getCommand().toString());
		assertEquals("id: ", "1", pc.getId());
	}
	
	@Test
	public void deleteShortened() {
		Parser parser = new Parser();
		String input = "-d 5";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "DELETE", pc.getCommand().toString());
		assertEquals("id: ", "5", pc.getId());
	}
	
	@Test
	public void filterShortenedByGroup() {
		Parser parser = new Parser();
		String input = "-f -g personal stuff";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "FILTER", pc.getCommand().toString());
		assertEquals("group: ", "personal stuff", pc.getGroup());
	}
	
	@Test
	public void filterShortenedByPriority() {
		Parser parser = new Parser();
		String input = "-f -p very high";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "FILTER", pc.getCommand().toString());
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());	
	}
	
	@Test
	public void filterShortenedByStartDate() {
		Parser parser = new Parser();
		String input = "-f -sd 2015/10/11";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "FILTER", pc.getCommand().toString());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/10/11", startDate);
	}
	
	@Test
	public void filterShortenedByEndDate() {
		Parser parser = new Parser();
		String input = "-f -ed 2015/10/15";
		ParsedCommand pc = parser.parse(input);
		assertEquals("command: ", "FILTER", pc.getCommand().toString());
		
		Calendar cal = pc.getEndDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("end date: ", "2015/10/15", endDate);
	}
	
	@Test
	public void updateShortened() {
		Parser parser = new Parser();
		String input = "-up 2, -t do homework, -sd 2015/10/30, -st 12.34, -ed 2015/11/12, "
				+ "-et 13.37, -g personal circle, -l my home, -p very high, "
				+ "-n remember to do, -r daily, -rd 2015/12/29 2015/12/29 2015/12/30, "
				+ "-rt 14.44 15.55 12.00";
		ParsedCommand pc = parser.parse(input);
		
		assertEquals("command: ", "UPDATE", pc.getCommand().toString());
		assertEquals("id: ", "2", pc.getId());
		assertEquals("title: ", "do homework", pc.getTitle());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/10/30", startDate);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "12.34", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/11/12", endDate);
		
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "13.37", endTime);
		
		assertEquals("group: ", "personal circle", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());
		assertEquals("notes: ", "remember to do", pc.getNotes());
		assertEquals("recurring: ", "DAILY", pc.getRecurFreq().toString());
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}
	
	@Test
	public void updateShortenedDeadline() {
		Parser parser = new Parser();
		String input = "-up 2, -t do homework, -dd 2015/11/12, "
				+ "-dt 13.37, -g personal circle, -l my home, -p very high, "
				+ "-n remember to do, -r weekly, -rd 2015/12/29 2015/12/29 2015/12/30, "
				+ "-rt 14.44 15.55 12.00";
		ParsedCommand pc = parser.parse(input);
		
		assertEquals("command: ", "UPDATE", pc.getCommand().toString());
		assertEquals("id: ", "2", pc.getId());
		assertEquals("title: ", "do homework", pc.getTitle());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("deadline start date: ", "2015/11/12", startDate);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("deadline start time: ", "13.37", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("deadline end date: ", "2015/11/12", endDate);
		
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("deadline end time: ", "13.37", endTime);
		
		assertEquals("group: ", "personal circle", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());
		assertEquals("notes: ", "remember to do", pc.getNotes());
		assertEquals("recurring: ", "WEEKLY", pc.getRecurFreq().toString());
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}
	
	@Test
	public void addShortened() {
		Parser parser = new Parser();
		String input = "-a eat drink sleep repeat, -sd 2015/10/12, -st 12.34, -ed 2015/10/14, "
				+ "-et 13.37, -p very high, -g secret group, -l my home, -n must do, "
				+ "-r monthly, -rd 2015/12/29 2015/12/29 2015/12/30, "
				+ "-rt 14.44 15.55 12.00";
		ParsedCommand pc = parser.parse(input);
		
		assertEquals("command: ", "ADD", pc.getCommand().toString());
		assertEquals("title: ", "eat drink sleep repeat", pc.getTitle());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/10/12", startDate);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "12.34", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/10/14", endDate);
		
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "13.37", endTime);
		
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());
		assertEquals("group: ", "secret group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", "MONTHLY", pc.getRecurFreq().toString());
		
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}
	
	@Test
	public void addShortenedSubTask() {
		Parser parser = new Parser();
		String input = "-a subtask drink repeat to 2, -sd 2015/10/12, -st 12.34, -ed 2015/10/14, "
				+ "-et 13.37, -p very high, -g secret group, -l my home, -n must do, "
				+ "-r yearly, -rd 2015/12/29 2015/12/29 2015/12/30, "
				+ "-rt 14.44 15.55 12.00";
		ParsedCommand pc = parser.parse(input);
		
		assertEquals("command: ", "ADD", pc.getCommand().toString());
		assertEquals("title: ", "drink repeat", pc.getTitle());
		assertEquals("main id: ", "2", pc.getMainId());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/10/12", startDate);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "12.34", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/10/14", endDate);
		
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "13.37", endTime);
		
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());
		assertEquals("group: ", "secret group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recurring: ", "YEARLY", pc.getRecurFreq().toString());
		
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}
	
	@Test
	public void addShortenedDeadine() {
		Parser parser = new Parser();
		String input = "-a eat drink sleep repeat, -dd 2015/10/14, "
				+ "-dt 13.37, -p very high, -g secret group, -l my home, -n must do, "
				+ "-rd 2015/12/29 2015/12/29 2015/12/30, "
				+ "-rt 14.44 15.55 12.00";
		ParsedCommand pc = parser.parse(input);
		
		assertEquals("command: ", "ADD", pc.getCommand().toString());
		assertEquals("title: ", "eat drink sleep repeat", pc.getTitle());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("deadline start date: ", "2015/10/14", startDate);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("deadline start time: ", "13.37", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("deadline end date: ", "2015/10/14", endDate);
		
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("deadline end time: ", "13.37", endTime);
		
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());
		assertEquals("group: ", "secret group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recur: ", null, pc.getRecurFreq());
		
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date3: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);

		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time: ", "14.44", reminderTime);
		assertEquals("reminder time: ", "15.55", reminderTime2);
		assertEquals("reminder time: ", "12.0", reminderTime3);
	}
	
	@Test
	public void addShortenedSubtaskDeadline() {
		Parser parser = new Parser();
		String userInput = "-a subtask drink repeat to 2, -dd 2015/10/14, "
				+ "-dt 13.37, -p very high, -g secret group, -l my home, -n must do, "
				+ "-rd 2015/12/29 2015/12/29 2015/12/30, "
				+ "-rt 14.44 15.55 12.00";
		ParsedCommand pc = parser.parse(userInput);
		
		assertEquals("subtask: ", "drink repeat", pc.getTitle());
		assertEquals("main id: ", "2", pc.getMainId());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/10/14", startDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "13.37", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/10/14", endDate);

		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "13.37", endTime);
		
		assertEquals("priority: ", "VERY_HIGH", pc.getPriority().toString());
		assertEquals("group: ", "secret group", pc.getGroup());
		assertEquals("location: ", "my home", pc.getLocation());
		assertEquals("notes: ", "must do", pc.getNotes());
		assertEquals("recur: ", null, pc.getRecurFreq());
	
		
		ArrayList<Calendar> cal3 = pc.getReminder();
		int year3 = cal3.get(0).get(Calendar.YEAR);
		int month3 = cal3.get(0).get(Calendar.MONTH) + 1;
		int day3 = cal3.get(0).get(Calendar.DAY_OF_MONTH);
		String reminderDate = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		
		int year4 = cal3.get(1).get(Calendar.YEAR);
		int month4 = cal3.get(1).get(Calendar.MONTH) + 1;
		int day4 = cal3.get(1).get(Calendar.DAY_OF_MONTH);
		String reminderDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		
		int year5 = cal3.get(2).get(Calendar.YEAR);
		int month5 = cal3.get(2).get(Calendar.MONTH) + 1;
		int day5 = cal3.get(2).get(Calendar.DAY_OF_MONTH);
		String reminderDate3 = String.valueOf(year5) + "/" + String.valueOf(month5) + "/" + String.valueOf(day5);
		
		assertEquals("reminder date1: ", "2015/12/29", reminderDate);
		assertEquals("reminder date2: ", "2015/12/29", reminderDate2);
		assertEquals("reminder date2: ", "2015/12/30", reminderDate3);
		
		
		int hour3 = cal3.get(0).get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(0).get(Calendar.MINUTE);
		String reminderTime = String.valueOf(hour3) + "." + String.valueOf(minute3);

		int hour4 = cal3.get(1).get(Calendar.HOUR_OF_DAY);
		int minute4 = cal3.get(1).get(Calendar.MINUTE);
		String reminderTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);
		
		int hour5 = cal3.get(2).get(Calendar.HOUR_OF_DAY);
		int minute5 = cal3.get(2).get(Calendar.MINUTE);
		String reminderTime3 = String.valueOf(hour5) + "." + String.valueOf(minute5);
		
		assertEquals("reminder time1: ", "14.44", reminderTime);
		assertEquals("reminder time2: ", "15.55", reminderTime2);
		assertEquals("reminder time3: ", "12.0", reminderTime3);
	}
	
	/**********************
	 * FLEXIBLE COMMANDS
	 **********************/
	
	@Test
	public void addFlexible() {
		Parser parser = new Parser();
		String userInput = "part time job from 2015/11/12 to 2015/11/15 from 9am to "
				+ "6pm at orchard road";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("title", "part time job", pc.getTitle());
		
		Calendar cal = pc.getStartDateTime();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String startDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
		assertEquals("start date: ", "2015/11/12", startDate);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String startTime = String.valueOf(hour) + "." + String.valueOf(minute);
		assertEquals("start time: ", "9.0", startTime);
		
		Calendar cal2 = pc.getEndDateTime();
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH) + 1;
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate = String.valueOf(year2) + "/" + String.valueOf(month2) + "/" + String.valueOf(day2);
		assertEquals("end date: ", "2015/11/15", endDate);
		
		int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal2.get(Calendar.MINUTE);
		String endTime = String.valueOf(hour2) + "." + String.valueOf(minute2);
		assertEquals("end time: ", "18.0", endTime);
		
		
		
		String userInput2 = "meeting with colleagues on monday from 12pm to 2pm at my house";
		ParsedCommand pc2 = parser.parse(userInput2);
		assertEquals("title", "meeting with colleagues", pc2.getTitle());
		
		Calendar cal3 = pc2.getStartDateTime();
		int year3 = cal3.get(Calendar.YEAR);
		int month3 = cal3.get(Calendar.MONTH) + 1;
		int day3 = cal3.get(Calendar.DAY_OF_MONTH);
		String startDate2 = String.valueOf(year3) + "/" + String.valueOf(month3) + "/" + String.valueOf(day3);
		assertEquals("start date: ", "2015/11/9", startDate2);
		
		int hour3 = cal3.get(Calendar.HOUR_OF_DAY);
		int minute3 = cal3.get(Calendar.MINUTE);
		String startTime3 = String.valueOf(hour3) + "." + String.valueOf(minute3);
		assertEquals("start time: ", "12.0", startTime3);
		
		/*
		Calendar cal4 = pc2.getEndDateTime();
		int year4 = cal2.get(Calendar.YEAR);
		int month4 = cal2.get(Calendar.MONTH) + 1;
		int day4 = cal2.get(Calendar.DAY_OF_MONTH);
		String endDate2 = String.valueOf(year4) + "/" + String.valueOf(month4) + "/" + String.valueOf(day4);
		assertEquals("end date: ", "2015/11/15", endDate);
		
		
		int hour4 = cal4.get(Calendar.HOUR_OF_DAY);
		int minute4 = cal4.get(Calendar.MINUTE);
		String endTime2 = String.valueOf(hour4) + "." + String.valueOf(minute4);
		assertEquals("end time: ", "18.0", endTime2);
		*/
	}
}

