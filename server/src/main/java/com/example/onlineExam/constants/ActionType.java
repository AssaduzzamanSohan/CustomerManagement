package com.example.onlineExam.constants;

public enum ActionType {
	NEW("NEW"), SELECT("SELECT"), SELECT_ALL("SELECT_ALL"), UPDATE("UPDATE"), DELETE("DELETE"), SAVE("SAVE");

	private final String actionType;

	private ActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		return actionType;
	}
}
