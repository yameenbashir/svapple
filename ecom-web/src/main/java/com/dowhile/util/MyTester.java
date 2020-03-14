package com.dowhile.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class MyTester {
	// private static Logger logger = Logger.getLogger(MyTester.class.getName());
	public static final String USER_AGENT = "Mozilla/5.0";
	public static String message ="Hurry Up!! Get special out fits for your children and Get Flat 30% off on entire new arrival.";

	public static void main(String[] args) {
		//readObjectFromFile("C:/temp/CCNobj.ser");
		String response = "";
		String test = "OK ID:9594fb67-8fb4-4dd1-975c-999e0c27e434";
		int count=0;
		for(String data:test.split("OK ID:")){
			 response = doGetVerifyStsatus(data);
			 count = count+1;
			 System.out.println("API Call :: "+count);
			 System.out.println(response);
			if(response.contains("923214179684")||response.contains("923214179684")){
				System.out.println(response);
			}
			
		}
		
		try {
			//sendPost();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

		//print result

	}

	public  static String doGetVerifyStsatus(String id){
//		message = message +"\r" +"I m ok";
//		message = message +"\n" +"I m not ok";
//		
		
		
		//Kites%20Kids
		//Xpressions
		//8023
		String url = "http://sendpk.com/api/delivery.php?username=923214823566&password=9183&id="+id;
		StringBuffer response = new StringBuffer();

		try{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return response.toString();
	}
	public static void doGet(){
//		message = message +"\r" +"I m ok";
//		message = message +"\n" +"I m not ok";
//		
		message = message.replaceAll("%", "%25");
		
		message = message.replaceAll(" ", "%20");
		message = message.replaceAll("\r", "%0A");
		message = message.replaceAll("\n", "%0A");
		message = message.replaceAll("!", "%21");
		message = message.replaceAll("'", "%27");
		message = message.replaceAll(",", "%2C");
		message = message.replaceAll("-", "%2D");
		message = message.replaceAll("'", "%27");
		message = message.replaceAll("’", "%27");
		
		
		//Kites%20Kids
		//Xpressions
		//8023
		String url = "http://sendpk.com/api/sms.php?username=923214823566&password=9183&sender=8023&mobile=03214823566&message="+message;
		try{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response);
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	// HTTP POST request
	public	static void sendPost() throws Exception {
//			message = message +"\r" +"I m ok";
//			message = message +"\n" +"I m not ok";
//			
			message = message.replaceAll("%", "%25");
			
			message = message.replaceAll(" ", "%20");
			message = message.replaceAll("\r", "%0A");
			message = message.replaceAll("\n", "%0A");
			message = message.replaceAll("!", "%21");
			message = message.replaceAll("'", "%27");
			message = message.replaceAll(",", "%2C");
			message = message.replaceAll("-", "%2D");
			message = message.replaceAll("'", "%27");
			message = message.replaceAll("’", "%27");
			String url = "http://sendpk.com/api/sms.php";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
//			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "username=923214823566&password=9183&sender=8023&mobile=03214823566&message="+message;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());

		}
	}
