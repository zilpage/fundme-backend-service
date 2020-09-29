package com.skillink.fundme.util;

public class Util {
	public static boolean isEmptyString(String str)
	{
		return str == null || str.equalsIgnoreCase("") || str.equalsIgnoreCase("null");
	}
}
