package com.google.cloud.genomics.cba.httpRequests;


import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * <h1>Google REST APIs - HTTP Requests</h1> This class uses the Google Client
 * HTTP APIs to execute the Google Genomics REST APIs
 * 
 * @version 1.0
 * @since 2016-07-17
 */

public class GoogleREST {

	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private HttpResponse response = null;
	private String responseOutput;


	/**
	 * This method runs the HTTP request. In case of success (Code:200), it sets the responseOutput String. 
	 * 
	 * @param TOKEN
	 *            A piece of data generated by the server which identifies a
	 *            user.
	 * @param URL
	 *            The URL of the request.
	 * @param requestBody
	 *            The body of request.
	 */

	public void run(String TOKEN, String requestBody, String URL) {

		try {
			try {

				HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();

				// generate the REST based URL
				GenericUrl url = new GenericUrl(URL.replaceAll(" ", "%20"));

				// make POST request
				HttpRequest request = requestFactory.buildPostRequest(url,
						ByteArrayContent.fromString(null, requestBody));
				request.getHeaders().setAuthorization("Bearer " + TOKEN);
				request.getHeaders().setContentType("application/json");		
				setResponse(request.execute());
				

				if (getResponse().getStatusCode() == 200) {
					setOutput(getResponse().parseAsString());
					return;
				} else {
					System.out.println("Failed!");
					System.out.println("Request body: ");
					System.out.println(requestBody);
					System.out.println("Response: ");
					System.out.println(getResponse().toString());
				}

			} catch (HttpResponseException e) {
				System.err.println(e.getMessage());
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	

	HttpResponse getResponse() {
		return response;
	}

	void setResponse(HttpResponse response) {
		this.response = response;
	}


	public String getOutput() {
		return responseOutput;
	}


	public void setOutput(String _responseOutput) {
		this.responseOutput = _responseOutput;
	}

}