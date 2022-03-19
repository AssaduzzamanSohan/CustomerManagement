package com.example.customer.message;

import com.example.customer.message.enums.StatusType;

public class Status {
	private StatusType statusType;
	private String status;
	private String statusText;
	private Throwable exception;

	public Status() {
	}

	public Status(final StatusType statusType) {
		this(statusType, null);
	}

	public Status(final StatusType statusType, final String statusText) {
		this.statusType = statusType;
		this.statusText = statusText;
		this.getStatus();
	}

	public Status(final Throwable exception) {
		this.setException(exception);
	}

	public StatusType getStatusType() {
		return this.statusType;
	}

	public void setStatusType(final StatusType statusType) {
		this.statusType = statusType;
		this.getStatus();
	}

	public String getStatus() {
		return this.status;
	}

	public String getStatusText() {
		return this.statusText;
	}

	public void setStatusText(final String statusText) {
		this.statusText = statusText;
	}

	public Throwable getException() {
		return this.exception;
	}

	public void setException(final Throwable t) {
		if (t != null) {
			this.statusType = StatusType.ERROR;
			this.status = this.statusType.toString();
			if (this.statusText == null) {
				this.setStatusText(t.toString());
			}
		} else {
			this.statusType = StatusType.WARN;
			this.status = this.statusType.toString();
		}
	}

	public void setAsWarning() {
		this.setStatusType(StatusType.WARN);
	}

	public void setAsError() {
		this.setStatusType(StatusType.ERROR);
	}

	public boolean isWarning() {
		return this.statusType == StatusType.WARN;
	}

	public boolean isError() {
		return this.statusType == StatusType.ERROR;
	}
}
