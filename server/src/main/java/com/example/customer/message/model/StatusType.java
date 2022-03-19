package com.example.customer.message.model;

public enum StatusType {
	OK("OK"), INFO("INFO"), WARN("WARN"), ERROR("ERROR");

	private final String statusType;

	private StatusType(String statusType) {
		this.statusType = statusType;
	}

	@Override
	public String toString() {
		return this.statusType;
	}
}
