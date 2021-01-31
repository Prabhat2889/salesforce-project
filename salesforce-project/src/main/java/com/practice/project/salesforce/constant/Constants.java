package com.practice.project.salesforce.constant;

/**
 * This class is to keep constant values use in the project!
 *
 */
public class Constants 
{
	public static final String authenticationEndpoint = "https://login.salesforce.com/services/oauth2/token";
	public static final String postMethod = "POST";
	public static final String getMethod = "GET";
	public static final String grantTypeParam = "grant_type";
	public static final String grantTypeParamVal = "password";
	public static final String usernameParam = "username";
    public static final String usernameParamVal = "";
    public static final String passwordParam = "password";
    public static final String passwordParamVal = "";
    public static final String clientIdParam = "client_id";
    public static final String clientIdParamVal = "";
    public static final String clientSecretParam = "client_secret";
    public static final String clientSecretParamVal = "";
    public static final String pathToEntityDef = "/services/data/v50.0/tooling/sobjects/EntityDefinition";
    public static final String pathToFieldDef = "/services/data/v50.0/tooling/sobjects/FieldDefinition";
    public static final String pathToEntityParticle = "/services/data/v50.0/tooling/sobjects/EntityParticle";
    public static final String pathToRelationDomain = "/services/data/v50.0/tooling/sobjects/RelationshipDomain";
    public static final String entityDefFIleName = "EntityDefinitionFile.txt";
    public static final String fieldDefinitionFileName = "FieldDefinitionFile.txt";
    public static final String entityParticleFileName = "EntityParticleFile.txt";
    public static final String relationshipDomainFileName = "RelationshipDomainFile.txt";
    public static final String sqlEvent = "/services/data/v50.0/query/?q=";
    public static final String sqlQueryEvent = "Select ControllingFieldDefinitionId,DataType,IsNameField,Label,QualifiedApiName From FieldDefinition Where EntityDefinition.QualifiedApiName='Event'";
}
