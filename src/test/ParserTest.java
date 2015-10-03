package test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

import calendrier.Parser;
import utils.ParsedCommand;

public class ParserTest {
	
	@Test
	public void delete() {
		Parser parser = new Parser();
		String userInput = "delete 23";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "DELETE", pc.getCommand().toString());
		assertEquals("message: ", "23", pc.getEventDetails().getTitle());
	}
	
	@Test
	public void view() {
		Parser parser = new Parser();
		String userInput = "view 20";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "VIEW", pc.getCommand().toString());
		assertEquals("message: ", "20", pc.getEventDetails().getTitle());
	}
	
	@Test
	public void undo() {
		Parser parser = new Parser();
		String userInput = "undo";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UNDO", pc.getCommand().toString());
		assertEquals("message: ", null, pc.getEventDetails());
	}
	
	@Test
	public void undelete() {
		Parser parser = new Parser();
		String userInput = "undelete";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UNDELETE", pc.getCommand().toString());
		assertEquals("message: ", null, pc.getEventDetails());
	}
	
	@Test
	public void update() {
		Parser parser = new Parser();
		String userInput = "update 23, date 2015/11/30, time 12.34, priority very high, location LT 1, recurring no, reminder 2015/11/28";
		ParsedCommand pc = parser.parse(userInput);
		assertEquals("command: ", "UPDATE", pc.getCommand().toString());
		assertEquals("updateTaskno: ", 23, pc.getEventDetails().getUpdateTaskNo());
		
		String date = "2015/11/30";
		java.util.Date myDate = new java.util.Date(date);
		java.sql.Date updateDate = new java.sql.Date(myDate.getTime());
		assertEquals("updateDate: ", updateDate, pc.getEventDetails().getUpdateDate());
		
		assertEquals("updateTime: ", "12.34", pc.getEventDetails().getUpdateTime());
		assertEquals("uodatePriority: ", "very high", pc.getEventDetails().getUpdatePriority());
		assertEquals("updateLocation: ", "LT 1", pc.getEventDetails().getUpdateLocation());
		assertEquals("updateRecurring: ", "no", pc.getEventDetails().getUpdateRecurring());
		
		String date2 = "2015/11/28";
		java.util.Date myDate2 = new java.util.Date(date2);
		java.sql.Date updateReminder = new java.sql.Date(myDate2.getTime());
		assertEquals("updateReminder: ", updateReminder, pc.getEventDetails().getUpdateReminder());
	}
	
	@Test
	public void add() {
		Parser parser = new Parser();
		String userInput = "add eat sleep drink repeat, date 2015/12/31, time 13.37, priority very low, location my home, notes must do, recurring no, reminder 2015/12/30";
		ParsedCommand pc = parser.parse(userInput);
		
		assertEquals("command: ", "ADD", pc.getCommand().toString());
		assertEquals("addTaskDescription: ", "eat sleep drink repeat", pc.getEventDetails().getAddTaskDescription());
		
		String date3 = "2015/12/31";
		java.sql.Date addDate = new java.sql.Date(new java.util.Date(date3).getTime());
		assertEquals("addDate: ", addDate, pc.getEventDetails().getAddDate());
		assertEquals("addTime: ", "13.37", pc.getEventDetails().getAddTime());
		assertEquals("addPriority: ", "very low", pc.getEventDetails().getAddPriority());
		assertEquals("addLocation: ", "my home", pc.getEventDetails().getAddLocation());
		assertEquals("addNotes: ", "must do", pc.getEventDetails().getAddNotes());
		assertEquals("addRecurring: ", "no", pc.getEventDetails().getAddRecurring());
		
		String date4 = "2015/12/30";
		java.sql.Date addReminder = new java.sql.Date(new java.util.Date(date4).getTime());
		assertEquals("addReminder: ", addReminder, pc.getEventDetails().getAddReminder());
	}
	

}
