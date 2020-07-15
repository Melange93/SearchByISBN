package com.reka.lakatos.searchbyisbn.webclient;

import com.reka.lakatos.searchbyisbn.webclient.exception.GetRequestException;
import com.reka.lakatos.searchbyisbn.webclient.exception.PostRequestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebClient {

    public Document sendGetRequest(final String url, final String exceptionMessage) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new GetRequestException(exceptionMessage, e);
        }
    }

    public Document sendPostRequest(final String url, final String requestBody, final String exceptionMessage) {
        try {
            return Jsoup.connect(url)
                    .requestBody(requestBody)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .post();
        } catch (IOException e) {
            throw new PostRequestException(exceptionMessage, e);
        }
    }
}
