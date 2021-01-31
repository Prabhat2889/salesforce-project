package com.practice.project.salesforce;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;

import com.practice.project.salesforce.constant.Constants;
import com.practice.project.salesforce.rest.SalesforceEndpointCallProcessor;
import com.practice.project.salesforce.rest.SalesforceEndpointCallProcessorImpl;

/**
 * Hello world!
 *
 */
public class AppMain 
{
    public static void main( String[] args )
    {
        System.out.println( "Inside AppMain" );
        SalesforceEndpointCallProcessor salesforceEndpointCallProcessor = new SalesforceEndpointCallProcessorImpl();
        System.out.println("Calling Authentication Endpoint AppMain");
        JSONObject authenticateObj = salesforceEndpointCallProcessor.authenticateUser();
        String instanceUrl = (String) authenticateObj.get("instance_url");
        String token = (String) authenticateObj.get("access_token");
        //fetching EntityDefinnition data
        String entityDefinitionData = salesforceEndpointCallProcessor.getSalesForceDataObjectJson(instanceUrl, Constants.pathToEntityDef, token);
        salesforceEndpointCallProcessor.createFile(Constants.entityDefFIleName, entityDefinitionData);
       // fetching FieldDefinnition data
        String fieldDefinitionData = salesforceEndpointCallProcessor.getSalesForceDataObjectJson(instanceUrl, Constants.pathToFieldDef, token);
        salesforceEndpointCallProcessor.createFile(Constants.fieldDefinitionFileName, fieldDefinitionData);
        //fetching EntityParticle data
        String entityParticleData = salesforceEndpointCallProcessor.getSalesForceDataObjectJson(instanceUrl, Constants.pathToEntityParticle, token);
        salesforceEndpointCallProcessor.createFile(Constants.entityParticleFileName, entityParticleData);
        //fetching Relationship Domain data
        String relationshipDomainData = salesforceEndpointCallProcessor.getSalesForceDataObjectJson(instanceUrl, Constants.pathToRelationDomain, token);
        salesforceEndpointCallProcessor.createFile(Constants.relationshipDomainFileName, relationshipDomainData);
        System.out.println("Writing One EntityDefinitions e.g. “Event” to console :");
        try {
			salesforceEndpointCallProcessor.getSalesForceDataObjectJson(instanceUrl, Constants.sqlEvent+URLEncoder.encode(Constants.sqlQueryEvent, StandardCharsets.UTF_8.toString()), token);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Exception while request encoding data: " + e);
		}
    }
}
