package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	// Get Method

	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); // http get request

		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet); // hit the get url
																							
		return closeableHttpResponse;
	}

	
	// Get method with header
	
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); // http get request

		for (Map.Entry<String, String> entry : headerMap.entrySet()) { // adding Headers to the request

			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet); // hit the get url
																					
		return closeableHttpResponse;
	}
	
	// Post Method
	
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// http Post request
		httpPost.setEntity(new StringEntity(entityString)); // for Payload

		for (Map.Entry<String, String> entry : headerMap.entrySet()) { // adding Headers to the request payload
				
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
		return closeableHttpResponse;
	}

}
