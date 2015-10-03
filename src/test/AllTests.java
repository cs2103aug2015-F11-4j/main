package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalendrierTest.class, EventHandlerTest.class, EventTest.class, MainLogicTest.class, ParserTest.class,
		ReminderManagerTest.class, StorageManagerTest.class, UserInterfaceTest.class, MainLogicNoEventHandlerStubTest.class, MainLogicNoParserStubTest.class, MainLogicFullTest.class })
public class AllTests {

}
