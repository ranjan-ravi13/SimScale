package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.CreateProject;

public class projectCreatedTest extends TestBase {

	TestBase testBase;
	String baseUrl;
	String authUrl;
	String requestUrl;
	String loginUrl;
	String userInfoUrl;
	String createProjUrl;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	String authToken;
	String userName;
	String expUserEmail = "ranjan.ravi1302@gmail.com";
	String projName = "RaAutomation07";
	boolean found = false;

	
	// in the setup method, login auth api is called, and the token received is stored
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		baseUrl = prop.getProperty("BaseUrl");
		authUrl = prop.getProperty("authUrl");
		createProjUrl = prop.getProperty("createProjUrl");
		loginUrl = baseUrl + authUrl;
		requestUrl = baseUrl + createProjUrl;

		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>(); //adding all the headers in the Map
																			
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", "Basic cmFuamFuLnJhdmkxMzAyQGdtYWlsLmNvbTpBcHJpbEAzMA==");//hardcoded value for username and password which is constant
																									
		// if required to log the request json, Object to Json file conversion
		// by creating a file and writing the request to it using
		// mapper.writeValue of Jackson API

		closeableHttpResponse = restClient.post(loginUrl, "", headerMap);

		int StatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(StatusCode, testBase.RESPONSE_STATUS_CODE_200, "Login not successfull");

		// Json response String

		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);

		System.out.println("The response from Login API is --->>" + responseJson);

		// Parsing the above response json to get the values of required keys
		authToken = (String) responseJson.get("token");
		userName = (String) responseJson.get("username");
		// mapper.readValue();

		System.out.println("authToken--->" + authToken);
		System.out.println("userName--->" + userName);
	}
	
	@Test
	public void verifyProjectCreation() throws ClientProtocolException, IOException{
		
		String url = requestUrl + userName; 
		
		System.out.println("The url to create project is ---->" + url);
		
		HashMap<String, String> headerMap = new HashMap<String, String>(); //adding all the headers in map
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", authToken);
		
		//using Jackson API
		ObjectMapper mapper = new ObjectMapper();
		CreateProject createProject = new CreateProject(false, false, false, false, projName, "US_CUSTOMARY", false, "Ravi testAutomation SimScale");
		
		//Object to Json file conversion if required to log the request json
		//by creating a file and writing the request to it using mapper.writeValue
		
		//object to Json in String
		
		String createJsonString = mapper.writeValueAsString(createProject);
		System.out.println("Create project JsonString ---->>" + createJsonString);
		
		closeableHttpResponse = restClient.post(url, createJsonString, headerMap);
		
		int StatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("The response of create project api is ----> "+ StatusCode);
		Assert.assertEquals(StatusCode, testBase.RESPONSE_STATUS_CODE_201, "Project creation not successfull");
		
		String getUrl = requestUrl;
		System.out.println("The url to fetch list of all projects---> " + getUrl);
		
		closeableHttpResponse = restClient.get(getUrl);
		
		StatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("The response of get all project api is ----> "+ StatusCode);
		Assert.assertEquals(StatusCode, testBase.RESPONSE_STATUS_CODE_200, "Project list fetch is not successfull");
		
		
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		//System.out.println("The final response string is ---->" + responseString);
		
		//Assert.assertTrue(responseString.contains(projName)); //if it is just need to verify if your project is present in list or not then this is sufficient
		
		//otherwise we will have to parse the response object and iterate over the list to assert for project name equals the expected value.
		
		JSONArray jsonarray = new JSONArray(responseString);
		for (int i = 0; i < jsonarray.length(); i++) {
		    JSONObject jsonobject = jsonarray.getJSONObject(i);
		    String actProjectName = jsonobject.getString("projectName");
		    if(actProjectName == projName) {
		    	System.out.println("Newly created project is present");
		    	found = true;
		    	break;
		    }
		
		}
		 Assert.assertTrue(found,"Newly created project is not present in the response list");	
		
	}

}
