package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebRequest;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebRequestGetException;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebRequestPostException;
import org.jsoup.Jsoup;

import java.io.IOException;

public class JsoupRequest implements WebRequest {

    @Override
    public WebDocument sendGetRequest(String baseUrl) {
        try {
            return new JsoupDocument(Jsoup.connect(baseUrl).get());
        } catch (IOException e) {
            throw new WebRequestGetException("Faild to reach the url: " + baseUrl, e);
        }
    }

    @Override
    public WebDocument sendPostRequest(String baseUrl, String requestBody) {
        try {
            return new JsoupDocument(Jsoup.connect(baseUrl)
                    .requestBody(requestBody)
                    .get());
        } catch (IOException e) {
            throw new WebRequestPostException("Faild to reach the url: " + baseUrl +
                    " with this request body: " + requestBody, e);
        }
    }
}
