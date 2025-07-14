package com.aditya.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public final class BookingIdGenerator {
	 private static final AtomicInteger COUNTER = new AtomicInteger(1000);

	    private BookingIdGenerator() { /* utility – prevent instantiation */ }

	    public static int getNextId() {
	        return COUNTER.getAndIncrement();
	    }
}
