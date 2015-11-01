package utils;

import java.util.*;

public class IdMapper {
	private static IdMapper idMapper = null;
	private HashMap<String, String> map = null;

	private IdMapper() {
		map = new HashMap<>();
	}

	/**
	 * Gets an instance of the mapper
	 * 
	 * @return instance of mapper
	 */
	public static IdMapper getInstance() {
		if (idMapper == null) {
			idMapper = new IdMapper();
		}
		return idMapper;
	}

	/**
	 * Creates Mapping for Short ID to Actual ID
	 * 
	 * @param shortId
	 *            Short ID from the User/GUI
	 * @param actualId
	 *            Actual ID for storage
	 */
	public void set(String shortId, String actualId) {
		if (shortId != null && actualId != null) {
			map.put(shortId, actualId);
		}
	}

	/**
	 * Clear mapping for the short ID
	 * 
	 * @param shortId
	 *            Short ID to be cleared
	 */
	public void clear(String shortId) {
		map.remove(shortId);
	}

	/**
	 * Clears all mappings
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Gets the actual ID (Storage ID)
	 * 
	 * @param shortId
	 *            Short ID from the User/GUI
	 * @return the actual ID for storage
	 */
	public String getActualId(String shortId) {
		String actualId = null;
		if (map.containsKey(shortId)) {
			actualId = map.get(shortId);
		}

		return actualId;
	}

}
