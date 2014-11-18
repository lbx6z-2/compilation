package decaf.error;

import decaf.Location;


public class UntermMultiCommentError extends DecafError {

	public UntermMultiCommentError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "unterminated multi comment";
	}

}