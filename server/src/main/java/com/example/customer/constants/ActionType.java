package com.example.customer.constants;

public enum ActionType {
	LOGIN("LOGIN"), NEW("NEW"), SELECT("SELECT"), SELECT_ALL("SELECT_ALL"), SELECT_ALL_AUDIT("SELECT_ALL_AUDIT"),
	UPDATE("UPDATE"), DELETE("DELETE"), SAVE("SAVE"), SEARCH("SEARCH"), SEARCH_AUDIT("SEARCH_AUDIT");

	private final String actionType;

	private ActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		return actionType;
	}
}
