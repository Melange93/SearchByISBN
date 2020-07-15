package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.exception.SessionIdActivationFailedException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.exception.SessionIdDocumentFailureException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.service.RequestBodyCreator;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.service.SessionIdUrlHandler;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WebClient {

    private final SessionIdUrlHandler sessionIdUrlHandler;
    private final RequestBodyCreator requestBodyCreator;

    public void activateSessionId(final String serverUrl, final String sessionId) {
        try {
            final String url = sessionIdUrlHandler.getSessionIdActivationUrl(serverUrl);
            final String requestBody = requestBodyCreator.getSessionIdActivationRequestBody(sessionId);
            Jsoup.connect(url)
                    .requestBody(requestBody)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .post();
        } catch (IOException e) {
            throw new SessionIdActivationFailedException("Session ID activation failed. Server URL: " + serverUrl, e);
        }
    }

    public Document getServerAndSessionIdDocument() {
        try {
            final String url = sessionIdUrlHandler.getSessionIdProviderUrl();
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new SessionIdDocumentFailureException("Failed to get session ID document.", e);
        }
    }
}
