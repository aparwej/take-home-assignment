package com.marionete.services.util;

import java.util.Base64;

public class TakeHomeAssignmentServiceUtil {
	
	public static String decodeString(String input) {
		return new String(Base64.getDecoder().decode(input));
	}
	
	public static String encodeString(String input) {
		return new String(Base64.getEncoder().encode(input.getBytes()));
	}

}
