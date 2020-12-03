package com.reka.lakatos.booksofhungary.crawlers.webdocument.jsoup;

import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebClient;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.exception.WebClientException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
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
    public WebDocument sendGetRequestWithCookies(String baseUrl, Map<String, String> cookies) {
        try {
            return new JsoupWebDocument(Jsoup.connect(baseUrl).cookies(cookies).get());
        } catch (IOException e) {
            throw new WebClientException("Failed to reach the url: " + baseUrl + " with this cookies " + cookies, e);
        }
    }

    @Override
    public WebDocument sendPostRequest(String baseUrl, String requestBody) {
        try {
            return new JsoupWebDocument(
                    Jsoup.connect(baseUrl)
                            .requestBody(requestBody)
                            .post()
            );
        } catch (IOException e) {
            throw new WebClientException("Failed to reach the url: " + baseUrl +
                    " with this request body: " + requestBody, e);
        }
    }

    @Override
    public WebDocument sendPostRequestWithCookies(String baseUrl, String requestBody, Map<String, String> cookies) {
        try {
            return new JsoupWebDocument(
                    Jsoup.connect(baseUrl)
                            .cookies(cookies)
                            .requestBody(requestBody)
                            .post()
            );
        } catch (IOException e) {
            throw new WebClientException(
                    "Failed to reach the url: " + baseUrl +
                            " with this request body: " + requestBody +
                            " and with this cookies: " + cookies,
                    e
            );
        }
    }

    @Override
    public Map<String, String> getCookies(String baseUrl) {
        try {
            Connection.Response res = Jsoup
                    .connect(baseUrl)
                    .method(Connection.Method.GET)
                    .execute();
            return res.cookies();
        } catch (IOException e) {
            throw new WebClientException(
                    "Failed to get the cookies from this url: " + baseUrl + " Request method is GET",
                    e
            );
        }
    }
}
