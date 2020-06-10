package com.glaway.ids.config.util;

public class JsonFromatUtil {

	public static  String formatJsonToList(String json) {
	    json = json.replaceAll("\\\\", "");
	    json = json.substring(1,json.length()-1);

	    return json;
	}

}
