package net.inzoe.domain;

public class Result {
	private boolean vaild;
	
	private String errorMessage;
	
	private Result(boolean valid, String errorMessage) {
		this.vaild = valid;
		this.errorMessage = errorMessage;
	}

	public boolean isVaild() {
		return vaild;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static Result ok() {
		return new Result(true, null);
	}
	
	public static Result fail(String errorMessage) {
		return new Result(false, errorMessage);
	}
}
