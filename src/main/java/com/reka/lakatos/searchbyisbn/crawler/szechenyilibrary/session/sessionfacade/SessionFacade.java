package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.sessionfacade;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.SessionActivationException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.SessionDocumentException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.sessionfacade.factory.SessionRequestBodyFactory;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.sessionfacade.factory.SessionURLFactory;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionFacade {

    private final SessionURLFactory urlFactory;
    private final SessionRequestBodyFactory requestBodyFactory;
    private final WebClient webClient;

    public WebDocument getSessionDocument() {
        final String url = urlFactory.getSessionProviderUrl();
        try {
            return webClient.sendGetRequest(url);
        } catch (WebClientException e) {
            throw new SessionDocumentException("Failed to get session document.", e);
        }
    }

    public void activateSession(final Session session) {
        final String url = urlFactory.getSessionIdActivationUrl(session.getServerUrl());
        final String requestBody = requestBodyFactory.getSessionActivationRequestBody(session.getId());

        try {
            webClient.sendPostRequest(url, requestBody);
        } catch (WebClientException e) {
            throw new SessionActivationException("Session activation failed. Server URL: " + session.getServerUrl(), e);
        }
    }

}
