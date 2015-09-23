package com.xirality.worlds.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class MailformedJSONConverter {
	
	private static final String CORRECT_UNICODE_START = "\\u";
	private static final String MAILFORMED_UNICODE_START = "\\U";

	public static OutputStream fixAndConvert(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] chunk = new byte[1];
		
		boolean isString = false;
		boolean commaPending = false; 
		String currentString = "";
		
		while (is.read(chunk) != -1) {
			if (isString) {
				if (chunk[0] == '"') {
					currentString = '"' + currentString + '"'; 
					currentString = currentString.replace(MAILFORMED_UNICODE_START, CORRECT_UNICODE_START);
					out.write(currentString.getBytes());
					currentString = "";
					isString = false;
				} else {
					currentString = currentString + new String(chunk);
				}
			} else {
				if (commaPending && !isWhiteSpace(chunk[0])) {
					if (chunk[0] != '}') {
						out.write(',');
					} 
					commaPending = false;
				} 
				
				if (chunk[0] == '=') {
					out.write(':');
				} else if (chunk[0] == ';') {
					commaPending = true;
				} else if (chunk[0] == '(') {
					out.write('[');
				} else if (chunk[0] == ')') {
					out.write(']');
				} else if (chunk[0] == '"') {
					currentString = "";
					isString = true;
				} else {
					out.write(chunk[0]);
				}
			}
		}
		
		return out;
	}
	
	private static boolean isWhiteSpace(byte c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t';
	}
}
