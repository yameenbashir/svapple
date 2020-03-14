package com.dowhile.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 * @author Hafiz Yameen Bashir
 *
 */
public class StringUtils {
	static Logger logger = Logger.getLogger(StringUtils.class.getName());

	public static boolean isNullOrEmptyString(String string) {
		return null == string || "".equals(string) || "null".equals(string) || string.trim().length()==0;
	}

	public static boolean isNullOrEmptyDate(Date dateObj) {
		return (null == dateObj || "".equals(dateObj));

	}

	public static String trim(String str) {
		if (null != str)
			return str.trim();
		else
			return str;
	}

	public static boolean isNumeric(String strNumber) {
		try {
			if(StringUtils.isNullOrEmptyString(strNumber))
			{
				return false;
			}
			Integer.parseInt(strNumber);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	  public static String encode(String str) {
		    String encoded = "";
		    if (str == null)
		      return encoded;
		    try {
		    	encoded = com.dowhile.util.Base64.encode(str);
		    	encoded = URLEncoder.encode(encoded,"UTF8");
		    } catch (Exception e) {
		      // logger.error(e.getClass(),e);
		    }
		    return encoded;
		  } 
		  
		  public static String decode(String str) {
		    String decoded = "";
		    if (str == null)
		      return decoded;
		    try {
		      decoded = URLDecoder.decode(str,"UTF8");
		      decoded = new String(com.dowhile.util.Base64.decode(decoded));
		    } catch (Exception e) {
		      // logger.error(e.getClass(),e);
		    }
		    return decoded;
		 }
	
	public static boolean isDouble(String strNumber) {
		try {
			if(StringUtils.isNullOrEmptyString(strNumber))
			{
				return false;
			}
			Double.parseDouble(strNumber);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	public static boolean isNull(Object object) {
		if (null == object) {
			return true;
		}

		return false;
	}

	public static String fixNullWithDefault(String string, String val_default) {
		if(isNull(string)){
			return val_default;
		}
		return string;
	} // end method

	public static String fixNull(String s) {
		return null == s ? "" : s;
	} // end method

	public static String filterSpecialCharacters(String info,
			long[] specialCharArray) {
		StringBuffer infoBuff = null;
		try {
			if (info != null) {
				infoBuff = new StringBuffer(info.trim());
				//Special Character Array
				for (int j = 0; j < specialCharArray.length; ++j) {
					//Information String
					for (int i = 0; i < infoBuff.length(); ++i) {
						if ((infoBuff.charAt(i)) == specialCharArray[j]) {
							infoBuff.deleteCharAt(i);
							--i;
						} //end if
					} //end information array
				} //end special character array
				info = infoBuff.toString();
			} //end if
		} catch (Exception e) {
		} //end catch
		return info;
	}

    public static byte[] getFileBytes(String filePath) {
        File file = null;
        DataInputStream dis = null;
        byte[] fileBytes = null;
        try {
            if (isNullOrEmptyString(filePath))
                throw new Exception("getFileBytes===>>>filePath is null/emptyString");
            file = new File(filePath);
            dis = new DataInputStream(new FileInputStream(file));
            fileBytes = new byte[dis.available()];
            dis.read(fileBytes);
        } catch (Exception e) {
        	Logger.getLogger("StringUtils").error("Error While getting file bytes",e);
        }

        return fileBytes;
    }

	public static boolean isAlphaNumeric(String strNewPwd) {
		if (strNewPwd.length() >= 7) {
			String strNumbers = "0123456789";
			try {
				Integer.parseInt(strNewPwd);
				return false;
			} catch (Exception err) {
				for (int i = 0; i < strNewPwd.length(); i++) {
					if (strNumbers.indexOf(strNewPwd.charAt(i)) != -1) {
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	
	public static boolean isNumber(String str) 
	{
		if(!isNullOrEmptyString(str)){
			try {				
					Integer.parseInt(str);				
					return true;
			} catch (Exception err) {
				return false;
			}
		}
		return false;
	}

	/**
     * This method matches the string with given regular
     * Expression.
     *
     * @param regEx       Regular Expression
     * @param strToMatch  String to match with regular expression
     * @return            True if string matches other wise return false
     */
    public static boolean testRegex(String regEx,String strToMatch) {
        boolean flag=false;
        Pattern pattern= null;
        Matcher matcher=null;

        if (strToMatch != null) {
            pattern= Pattern.compile(regEx);
            matcher=pattern.matcher(strToMatch);
            flag= matcher.matches();
        } else {
            return flag;
        }
     return flag;
    }

    /**
     * This method validates the email address.
     * isValidEmail
     * @param str
     * @return
     */
    public static boolean isValidEmail(String email){
        StringBuffer  emailRegex = new StringBuffer("^^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");
            if (testRegex(emailRegex.toString(),email)) {
                return true;
            }

    return false;
    }//end isValidEmail

    /**
     * This method validates the alphabets including space.
     * isValidEmail
     * @param str
     * @return
     */
    public static boolean isAlphaString(String str){
        StringBuffer  emailRegex = new StringBuffer("[a-zA-Z ]*");
        if (testRegex(emailRegex.toString(),str)) {
            return true;
        }
        return false;
    }//end isValidEmail

    /**
     * checks if the input a valid decimaal
     * @param decimal
     * @return
     */
    public static boolean isDecimal(String decimal){
    	StringBuffer  emailRegex = new StringBuffer("^\\d*\\.?\\d*$");
    	if (testRegex(emailRegex.toString(),decimal)) {
            return true;
        }
    	return false;
    }

    /**
     * checks if the input is a valid Zip Code (5 or 9 digits)
     *
     * @param zipCode
     * @return
     */
    public static boolean isValidZipCode(String zipCode){
        if(isNullOrEmptyString(zipCode)){
            return false;
        }else if(zipCode.length() !=5 && zipCode.length() != 9){
            return false;
        }else{
            try{
                Integer.parseInt(zipCode);
            }catch(Exception ex){
                return false;
            }
        }
        return true;
    }

    /**
     * function to convert string to initial case
     * @param str
     * @return
     */
    public static String toTitleCase(String str){
		StringBuffer sb = new StringBuffer();
		str = str.toLowerCase();
		StringTokenizer strTitleCase = new StringTokenizer(str);
		while(strTitleCase.hasMoreTokens()){
			String s = strTitleCase.nextToken();
			sb.append(s.replaceFirst(s.substring(0,1),s.substring(0,1).toUpperCase()) + " ");
		}
		return sb.toString();
	}


	/**
	 * return replacement (with) if given str is null or empty
	 * @param str
	 * @param with
	 * @return
	 */
	public static String fixNullOrEmpty(String str,String with){
		if(StringUtils.isNullOrEmptyString(str))
			return with;
		else
			return str;
	}
	
	public static String fixNullOrEmptyWithTrim(String str){
		return fixNullOrEmpty(str, "").trim();
	}


	/**
	 * Prevent Cross Site Scripting by replacing < > wd html codes
	 * @param str
	 * @param with
	 * @return
	 */
	public static String escapeHTML(String str){

		if(!StringUtils.isNullOrEmptyString(str)){
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
		}
		return str;

	}
	
	/**
	 * Compare two strings
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compare(String str1, String str2){
		if(!isNullOrEmptyString(str1) && !isNullOrEmptyString(str2))
			return str1.equalsIgnoreCase(str2);
		return false;
	}
	
	/**
	 * Compare two strings
	 * @param str1
	 * @param str2
	 * @return 
	 * -1 str1 is null
	 * -2 str2 is null
	 * 0 match
	 * 1 unmatched
	 */
	public static int match(String str1, String str2){
		if(isNullOrEmptyString(str1))
			return -1;
		if(isNullOrEmptyString(str2))
			return -2;
		if(str1.equalsIgnoreCase(str2))
			return 0;
		return 1;
	}
	
	/**
	 * match filter value
	 * @param passed value
	 * @param to compare with
	 * @return 
	 * -1 str1 is null or -1
	 * -2 str2 is null
	 * 0 match
	 * 1 unmatched
	 */
	public static int matchFilter(String str1, String str2){
		if(isNullOrEmptyString(str1) || "-1".equalsIgnoreCase(str1))
			return -1;
		if(isNullOrEmptyString(str2))
			return -2;
		if(str1.equalsIgnoreCase(str2))
			return 0;
		return 1;
	}
	
	/**
	 * Replace Substring
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replaceSubstring(String str,String pattern,String replace)
	{
		int strLen = str.length();
		int patternLen = pattern.length();
		int start = 0, end = 0;
		StringBuffer result = new StringBuffer(strLen);
		char[] chars = new char[strLen];

		while ((end = str.indexOf(pattern, start)) >= 0)
		{
			str.getChars(start, end, chars, 0);
			result.append(chars, 0, end - start).append(replace);
			start = end + patternLen;
		}

		str.getChars(start, strLen, chars, 0);
		result.append(chars, 0, strLen - start);

		return result.toString();
	}
	
	/**
	 * Convert String Array to Comma Separated Quoted Values:: used in query IN()
	 * example:
	 * {"1","2","3"} ==> '1','2','3'
	 * @param args
	 * @return
	 */
	public static String converToCSQ(String[] args){
		StringBuffer ret = new StringBuffer();
		try {
			if(args!=null){
				for(String id:args){
						ret.append("'"+id.trim()+"',");
				}
				ret.replace(ret.lastIndexOf(",")-1,ret.lastIndexOf(","),"");
			}
		} catch (Exception e) {
			Logger.getLogger(StringUtils.class).warn("Error parsing String array: "+e.getMessage());
		}
		return ret.toString();
	}
	
	public static String converToCSQ(String args){
		StringBuffer ret = new StringBuffer();
		StringTokenizer stk = null;
		try {
			if(!StringUtils.isNullOrEmptyString(args)){
				stk = new StringTokenizer(args,",");
				while(stk.hasMoreTokens()){
					String value = stk.nextToken().trim();
					ret.append(" '"+value+"' ,");
				}
				ret.replace(ret.lastIndexOf(","),ret.length(),"");
			}
		} catch (Exception e) {
			Logger.getLogger(StringUtils.class).warn("Error parsing String array: "+e.getMessage());
		}
		return ret.toString();
	}

	public static String mapToString(Map<String, String> map) {
		   StringBuilder stringBuilder = new StringBuilder();

		   for (String key : map.keySet()) {
		    if (stringBuilder.length() > 0) {
		     stringBuilder.append("&");
		    }
		    String value = map.get(key);
		    try {
		     stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
		     stringBuilder.append("=");
		     stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
		    } catch (UnsupportedEncodingException e) {
		     throw new RuntimeException("This method requires UTF-8 encoding support", e);
		    }
		   }

		   return stringBuilder.toString();
		  }

		  public static Map<String, String> stringToMap(String input) {
		   Map<String, String> map = new HashMap<String, String>();

		   String[] nameValuePairs = input.split("&");
		   for (String nameValuePair : nameValuePairs) {
		    String[] nameValue = nameValuePair.split("=");
		    try {
		     map.put(URLDecoder.decode(nameValue[0], "UTF-8"), nameValue.length > 1 ? URLDecoder.decode(
		     nameValue[1], "UTF-8") : "");
		    } catch (UnsupportedEncodingException e) {
		     throw new RuntimeException("This method requires UTF-8 encoding support", e);
		    }
		   }

		   return map;
		  }
		  
		  public static String reverseString(String s) 
		  {
			 String reverseStringVariable = "";
			 try
			 {
				 if(isNullOrEmptyString(s))
				 {
					   return s;
				 }
				  String[] arr = s.split(" ");
				  for(int i=arr.length-1; i >=0 ;i--)
				  {
					  reverseStringVariable = reverseStringVariable + " " + arr[i];
				  } 
			 }
			 catch (Exception e)
			 {
				 System.out.println(e);
			 }
			  
			  return reverseStringVariable.trim();
			}

		  public static String getStackTraceAsString(Exception e) 
		  {
			    StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw, true);
			    e.printStackTrace(pw);
			    pw.flush();
			    sw.flush();
			    return sw.toString();
		  }
		  
		  /*public static String[] parseCSVLine(String csvLine) 
		  {
			    String[] parsedLine = null;
			    try {
			      //now parse the argList w/ the csv reader
			      CSVReader csvr = new CSVReader(new StringReader(csvLine+"\r\n"));
			      parsedLine = csvr.getLine();
			    } catch (Exception e) {
			      // logger.error(StringUtils.getStackTraceAsString(e));
			    }
			    return parsedLine;
			  }		*/

		  public static void main(String[] args)
		  {
			System.out.println("hello"); 
		  }
		  
		    public static boolean containsIgnoreCase(String str, String searchStr) {
		        if (str == null || searchStr == null) {
		            return false;
		        }
		        return contains(str.toUpperCase(), searchStr.toUpperCase());
		    }
		    
		    public static boolean contains(String str, String searchStr) {
		        if (str == null || searchStr == null) {
		            return false;
		        }
		        return str.indexOf(searchStr) >= 0;
		    }

			public static boolean isEmpty(String json) {
				// TODO Auto-generated method stub
				return false;
			}
}
