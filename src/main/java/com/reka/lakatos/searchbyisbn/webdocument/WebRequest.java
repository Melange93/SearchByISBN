package com.reka.lakatos.searchbyisbn.webdocument;

public interface WebRequest {
    WebDocument sendGetRequest(String baseUrl);
    WebDocument sendPostRequest(String baseUrl, String requestBody);
}
