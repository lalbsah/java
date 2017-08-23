package com.java.util.rest;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class RestClient {
	public String post(String endpoint, String jsonReq, String user, String password) {
		String serResponse = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		user = user == null ? "" : user;
		password = password == null ? "" : password;

		try {
			URI uri = new URI(endpoint);
			HttpPost httpPost = new HttpPost(uri);

			// Set security
			Credentials credentials = new UsernamePasswordCredentials(user, password);
			httpClient.getCredentialsProvider().setCredentials(new AuthScope(uri.getHost(), uri.getPort()), credentials);

			httpPost.setEntity(new StringEntity(jsonReq));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				serResponse = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return serResponse;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
