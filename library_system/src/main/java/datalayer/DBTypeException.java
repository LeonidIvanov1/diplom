package datalayer;

public class DBTypeException extends RuntimeException {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public DBTypeException() {
		super();
	}

	public DBTypeException(String message) {
		super(message);
	}
}
