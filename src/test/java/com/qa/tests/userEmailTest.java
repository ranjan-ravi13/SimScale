package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class userEmailTest extends TestBase {

	TestBase testBase;
	String BaseUrl;
	String authUrl;
	String RequestUrl;
	String LoginUrl;
	String userInfoUrl;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	String authToken;
	String userName;
	String expUserEmail = "ranjan.ravi1302@gmail.com";

	
	// in the setup method, login auth api is called, and the token received is stored
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		BaseUrl = prop.getProperty("BaseUrl");
		authUrl = prop.getProperty("authUrl");
		userInfoUrl = prop.getProperty("userInfoUrl");
		LoginUrl = BaseUrl + authUrl;
		RequestUrl = BaseUrl + userInfoUrl;

		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>(); //adding all the headers in the map
																			
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", "Basic cmFuamFuLnJhdmkxMzAyQGdtYWlsLmNvbTpBcHJpbEAzMA==");//hardcoded value for username and password which is constant
																									
		// if required to log the request json, Object to Json file conversion
		// by creating a file and writing the request to it using
		// mapper.writeValue of Jackson API

		closeableHttpResponse = restClient.post(LoginUrl, "", headerMap);

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
	public void verifyUserEmail() throws ClientProtocolException, IOException {

		HashMap<String, String> headerMap = new HashMap<String, String>(); //adding all the headers in the map
																			
		headerMap.put("Content-Type", "application/json");
		headerMap.put("authorization", authToken); //authToken is fetched from the response of auth api 

		String Url = RequestUrl + userName;// userName is fetched from the response of auth api 

		System.out.println("The url to get user info is --->" + Url);

		closeableHttpResponse = restClient.get(Url, headerMap);

		int StatusCode = closeableHttpResponse.getStatusLine().getStatusCode();

		Assert.assertEquals(StatusCode, testBase.RESPONSE_STATUS_CODE_200, "The request to get user info failed");

		// Json String

		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject userInfoObj = new JSONObject(responseString);

		System.out.println("The response from API is --->>" + userInfoObj);

		String actUserEmail = userInfoObj.getString("emailAddress");

		System.out.println("email address from response is --->" + actUserEmail);

		Assert.assertEquals(expUserEmail, actUserEmail, "The actual and expected user email address does not match");

	}

}
