package com.practice.project.salesforce.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.practice.project.salesforce.constant.Constants;


public class SalesforceEndpointCallProcessorImpl implements SalesforceEndpointCallProcessor {
	
	public JSONObject authenticateUser() {
		System.out.println( "Inside SalesforceEndpointCallProcessorImpl.authenticateUser" );
		JSONObject jsonObject = null;
		try {			
			System.out.println( "Configuring auth url" );
			URL url = new URL(Constants.authenticationEndpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(Constants.postMethod);
			conn.setDoOutput(true);
			Map<String,String> arguments = new HashMap<String,String>();
			arguments.put(Constants.grantTypeParam,Constants.grantTypeParamVal);
			arguments.put(Constants.usernameParam,Constants.usernameParamVal);
			arguments.put(Constants.passwordParam,Constants.passwordParamVal);
			arguments.put(Constants.clientIdParam,Constants.clientIdParamVal);
			arguments.put(Constants.clientSecretParam,Constants.clientSecretParamVal);
			StringJoiner sj = new StringJoiner("&");
			for(Map.Entry<String,String> entry : arguments.entrySet())
			    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
			         + URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			int length = out.length;
			conn.setFixedLengthStreamingMode(length);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			conn.connect();
			try(OutputStream os = conn.getOutputStream()) {
			    os.write(out);
			}
			int responsecode = conn.getResponseCode();
			if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
            	System.out.println("connection successful to auth url");
            	 String inline = "";
	                Scanner scanner = new Scanner(conn.getInputStream());
	                //Write all the JSON data into a string using a scanner
	                while (scanner.hasNext()) {
	                    inline += scanner.nextLine();
	                }
	                scanner.close();
	                JSONParser parse = new JSONParser();
	                jsonObject = (JSONObject) parse.parse(inline);
	            }
		} catch (IOException e) {
			throw new RuntimeException("Exception while reading data: " + e);
		} catch (ParseException e) {
			throw new RuntimeException("Exception occurred while parsing json: " + e);
		}
		return jsonObject;
	}
	
	
	@Override
	public String getSalesForceDataObjectJson(String instanceUrl, String pathToDataObj, String token) {
		StringBuffer response = new StringBuffer();
		try {
				URL url = new URL(instanceUrl+pathToDataObj);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod(Constants.getMethod);
				conn.setRequestProperty("Authorization","Bearer "+token);
				conn.connect();
				int responsecode = conn.getResponseCode();
				if (responsecode != 200) {
	                throw new RuntimeException("HttpResponseCode: " + responsecode);
	            } else {
	                String inline = "";
	                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	                
	                while((inline = in.readLine()) !=null) {
	                	response.append(inline);
	                }
	                in.close();
	                System.out.println("JSON string : " +response.toString());
	            }
		} catch (IOException e) {
			throw new RuntimeException("Exception while reading data: " + e);
		}
		return response.toString();
	}
	
	@Override
	public void createFile(String filename, String data) {
    	try {
    		File file = new File(filename);
			if (file.createNewFile()) {
			    System.out.println("File created: " + file.getName());
			    writeToFile(filename, data);
			  } else {
			    System.out.println("File already exists, so delete and recreate an empty file file");
			    file.delete();
			    new File(filename);
			    writeToFile(filename, data);
			  }
		} catch (IOException e) {
			throw new RuntimeException("Exception while file creation or writing into the file: " + e);
		}
	}
    @Override
	public  void writeToFile(String filename, String data) throws IOException {
	    	FileWriter myWriter = new FileWriter(filename);
	        myWriter.write(data);
	        myWriter.close();
	    }
}
