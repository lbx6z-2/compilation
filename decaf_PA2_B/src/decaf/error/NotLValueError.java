package decaf.error;

import decaf.Location;

/**
 * string is not a class type.
 */
public class NotLValueError extends DecafError {

	private String type;

	public NotLValueError(Location location, String type) {
		super(location);
		this.type = type;
	}

	@Override
	protected String getErrMsg() {
		return "lvalue required as " + type + " operand";
	}

}
