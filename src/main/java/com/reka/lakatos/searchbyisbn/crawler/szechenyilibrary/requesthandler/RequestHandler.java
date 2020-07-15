package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.requesthandler;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.requesthandler.factory.RequestBodyFactory;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.requesthandler.factory.URLFactory;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.model.Session;
import com.reka.lakatos.searchbyisbn.webclient.WebClient;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final URLFactory urlFactory;
    private final RequestBodyFactory requestBodyFactory;
    private final WebClient webClient;

    public Document getSessionDocument() {
        final String url = urlFactory.getSessionProviderUrl();
        final String exceptionMessage = "Failed to get session document.";
        return webClient.sendGetRequest(url, exceptionMessage);
    }

    public void activateSession(final Session session) {
        final String url = urlFactory.getSessionIdActivationUrl(session.getServerUrl());
        final String requestBody = requestBodyFactory.getSessionActivationRequestBody(session.getId());
        final String exceptionMessage = "Session activation failed. Server URL: " + session.getServerUrl();
        webClient.sendPostRequest(url, requestBody, exceptionMessage);
    }

}
