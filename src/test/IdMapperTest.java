package test;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.IdMapper;

/**
 * @@author A0088646M
 * @author yeehuipoh
 *
 */
public class IdMapperTest {

	/* @@author A0088646M */
	@Test
	public void testGetInstance() {
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);
	}

	/* @@author A0088646M */
	@Test
	public void testSetId() {
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);

		idMapper.set("shortId", "actualId");

		assertEquals("actualId", idMapper.getActualId("shortId"));
	}

	/* @@author A0088646M */
	@Test
	public void testGetNullId() {
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);

		idMapper.set("shortId", "actualId");

		assertEquals(null, idMapper.getActualId(null));
	}

	/* @@author A0088646M */
	@Test
	public void testSetNullId() {
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);

		idMapper.set(null, "actualId");

		assertEquals(null, idMapper.getActualId(null));
	}

	/* @@author A0088646M */
	@Test
	public void testSetNullActualId() {
		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);

		idMapper.set("shortId", null);

		assertEquals(null, idMapper.getActualId("shortId"));
	}

	/* @@author A0088646M */
	@Test
	public void testClearId() {

		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);

		idMapper.set("shortId", "actualId");
		assertEquals("actualId", idMapper.getActualId("shortId"));

		idMapper.clear("shortId");
		assertEquals(null, idMapper.getActualId("shortId"));
	}

	/* @@author A0088646M */
	@Test
	public void testClearNullId() {

		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);

		idMapper.set("shortId", "actualId");
		assertEquals("actualId", idMapper.getActualId("shortId"));

		idMapper.clear(null);
		assertEquals("actualId", idMapper.getActualId("shortId"));
	}

	/* @@author A0088646M */
	@Test
	public void testClearAllIds() {

		IdMapper idMapper = IdMapper.getInstance();
		assertTrue(idMapper != null);

		idMapper.set("shortId", "actualId");
		assertEquals("actualId", idMapper.getActualId("shortId"));

		idMapper.clear();
		assertEquals(null, idMapper.getActualId("shortId"));
	}

}
