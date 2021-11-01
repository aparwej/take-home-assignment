package com.marionete.services.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TakeHomeAssignmentServiceUtilTest {

	@Test
	public void test() {
		String input = "Test";
		String encodedString = TakeHomeAssignmentServiceUtil.encodeString(input);
		String decodeString = TakeHomeAssignmentServiceUtil.decodeString(encodedString);
		System.out.println(encodedString);
		assertEquals(input, decodeString);
	}
}
