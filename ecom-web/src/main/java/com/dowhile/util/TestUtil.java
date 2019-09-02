/*package com.dowhile.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;


public class TestUtil {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//H4sIAAAAAAAAAAEYAOf/ItCS0YHQtdC8INC/0YDQuNCy0LXRgiEieN0AthgAAAA=
		
//		byte[] decoded = Base64.decodeBase64("H4sIAAAAAAAAAAEYAOf/ItCS0YHQtdC8INC/0YDQuNCy0LXRgiEieN0AthgAAAA=");
//		System.out.println(new String(decoded, "ascii"));
		
		byte[] decodedValue = Base64.getDecoder().decode("H4sIAAAAAAAAAAEYAOf/ItCS0YHQtdC8INC/0YDQuNCy0LXRgiEieN0AthgAAAA=");  // Basic Base64 decoding
		System.out.println(new String(decodedValue, "utf-16"));
		System.out.println(compress("Hello"));
	    
	}

	public static String compress(String str) throws IOException {
	    if (str == null || str.length() == 0) {
	        return str;
	    }
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    GZIPOutputStream gzip = new GZIPOutputStream(out);
	    gzip.write(str.getBytes());
	    gzip.close();
	    String outStr = out.toString("UTF-8");
	    return outStr;
	 }
}
*/