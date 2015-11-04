package test;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.IdMapper;

public class IdMapperTest {

	@Test
	public void testGetInstance() {
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
	}
	
	@Test
	public void testSetId(){
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
		
		idMapper.set("shortId", "actualId");
		
		assertEquals("actualId", idMapper.getActualId("shortId"));
	}
	
	@Test
	public void testGetNullId(){
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
		
		idMapper.set("shortId", "actualId");
		
		assertEquals(null, idMapper.getActualId(null));
	}
	
	@Test
	public void testSetNullId(){
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
		
		idMapper.set(null, "actualId");
		
		assertEquals(null, idMapper.getActualId(null));
	}
	
	@Test
	public void testSetNullActualId(){
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
		
		idMapper.set("shortId", null);
		
		assertEquals(null, idMapper.getActualId("shortId"));
	}
	
	@Test
	public void testClearId(){

		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
		
		idMapper.set("shortId", "actualId");
		assertEquals("actualId", idMapper.getActualId("shortId"));
		
		idMapper.clear("shortId");
		assertEquals(null, idMapper.getActualId("shortId"));
	}
	
	@Test
	public void testClearNullId(){

		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
		
		idMapper.set("shortId", "actualId");
		assertEquals("actualId", idMapper.getActualId("shortId"));
		
		idMapper.clear(null);
		assertEquals("actualId", idMapper.getActualId("shortId"));
	}
	
	@Test
	public void testClearAllIds(){

		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
		
		idMapper.set("shortId", "actualId");
		assertEquals("actualId", idMapper.getActualId("shortId"));
		
		idMapper.clear();
		assertEquals(null, idMapper.getActualId("shortId"));
	}

}
