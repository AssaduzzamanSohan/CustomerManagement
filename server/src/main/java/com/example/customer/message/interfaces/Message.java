package com.example.customer.message.interfaces;

import com.example.customer.message.MessageHeader;

public interface Message<T> {

	MessageHeader getHeader();

	T getPayload();
}
