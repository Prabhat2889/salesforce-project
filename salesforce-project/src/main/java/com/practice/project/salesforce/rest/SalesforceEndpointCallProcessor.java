package com.practice.project.salesforce.rest;

import java.io.IOException;

import org.json.simple.JSONObject;

public interface SalesforceEndpointCallProcessor {
	
	JSONObject authenticateUser();

	String getSalesForceDataObjectJson(String instanceUrl, String pathToDataObj, String token);
	
	void createFile(String filename, String data);
	
    void writeToFile(String filename, String data) throws IOException;	
}
