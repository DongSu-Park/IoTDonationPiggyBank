package com.flore.iotdonationpiggybank.util.nfcreader;

public interface ParsedRecord {

	public static final int TYPE_TEXT = 1;
	public static final int TYPE_URI = 2;

	public int getType();

}
