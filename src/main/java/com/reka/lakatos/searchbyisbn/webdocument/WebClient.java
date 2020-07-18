package com.reka.lakatos.searchbyisbn.webdocument;

public interface WebClient {

    WebDocument sendGetRequest(String baseUrl);

    WebDocument sendPostRequest(String baseUrl, String requestBody);
}
