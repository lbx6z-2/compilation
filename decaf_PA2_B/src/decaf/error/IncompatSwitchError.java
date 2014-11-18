package decaf.error;

import decaf.Location;

/**
 * example：incompatible operand: - int[]<br>
 * PA2
 */
public class IncompatSwitchError extends DecafError {

	private String expr;

	public IncompatSwitchError(Location location, String expr) {
		super(location);
		this.expr = expr;
	}

	@Override
	protected String getErrMsg() {
		return "incompatible switch: " + expr + " given,  int expected";
	}

}
