package com.reka.lakatos.searchbyisbn.webdocument;

import java.util.Map;

public interface WebClient {

    WebDocument sendGetRequest(String baseUrl);

    WebDocument sendGetRequestWithCookies(String baseUrl, Map<String, String> cookies);

    WebDocument sendPostRequest(String baseUrl, String requestBody);

    WebDocument sendPostRequestWithCookies(String baseUrl, String requestBody, Map<String, String> cookies);

    Map<String, String> getCookies(String baseUrl);
}
