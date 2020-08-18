package com.reka.lakatos.searchbyisbn.webdocument;

import java.util.Map;

public interface WebClient {

    WebDocument sendGetRequest(String baseUrl);

    WebDocument sendPostRequest(String baseUrl, String requestBody);

    Map<String, String> getCookies(String baseUrl);
}
