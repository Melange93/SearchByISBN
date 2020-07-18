package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebClientException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsoupWebClient implements WebClient {

    @Override
    public WebDocument sendGetRequest(String baseUrl) {
        try {
            return new JsoupWebDocument(Jsoup.connect(baseUrl).get());
        } catch (IOException e) {
            throw new WebClientException("Failed to reach the url: " + baseUrl, e);
        }
    }

    @Override
    public WebDocument sendPostRequest(String baseUrl, String requestBody) {
        try {
            return new JsoupWebDocument(
                    Jsoup.connect(baseUrl)
                            .requestBody(requestBody)
                            .get()
            );
        } catch (IOException e) {
            throw new WebClientException("Failed to reach the url: " + baseUrl +
                    " with this request body: " + requestBody, e);
        }
    }
}
