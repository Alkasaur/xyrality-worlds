package com.xirality.worlds.task;


public class TaskResult<T> {

	private T result;
	private Exception exception = null;
	
	public TaskResult() {
	}
	
	public TaskResult(T wsResponse) {
		this.result = wsResponse;
	}
	
	public TaskResult(Exception e) {
		this.exception = e;
	}
	
	public boolean hadException() {
		return this.exception != null;
	}

	public Exception getException() {
		return exception;
	}

	public T getResult() {
		return result;
	}
	
	

}
