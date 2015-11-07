package utils;

/**
 * @@author A0088646M
 * @author yeehuipoh
 *
 */
@SuppressWarnings("serial")
public class UserCommandException extends Exception {
	private String message = null;
	private String command = null;

	/**
	 * @@author A0088646M
	 * Getter for message returned along with the exception
	 * @return message returned along with the exception
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @@author A0088646M
	 * Setter for message returned along with the exception
	 * @param message	message text
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @@author A0088646M
	 * Getter for user command that causes the exception
	 * @return	user command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @@author A0088646M
	 * Setter for user command that causes the exception
	 * @param command	user command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	
}
