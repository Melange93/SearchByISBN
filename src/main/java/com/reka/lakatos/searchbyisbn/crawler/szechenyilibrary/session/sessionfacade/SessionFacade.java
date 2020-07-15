package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.sessionfacade;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.SessionActivationException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.SessionDocumentException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.sessionfacade.factory.RequestBodyFactory;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.sessionfacade.factory.URLFactory;
import com.reka.lakatos.searchbyisbn.webclient.WebClient;
import com.reka.lakatos.searchbyisbn.webclient.exception.GetRequestException;
import com.reka.lakatos.searchbyisbn.webclient.exception.PostRequestException;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionFacade {

    private final URLFactory urlFactory;
    private final RequestBodyFactory requestBodyFactory;
    private final WebClient webClient;

    public Document getSessionDocument() {
        final String url = urlFactory.getSessionProviderUrl();
        try {
            return webClient.sendGetRequest(url);
        } catch (GetRequestException e) {
            throw new SessionDocumentException("Failed to get session document.", e);
        }
    }

    public void activateSession(final Session session) {
        final String url = urlFactory.getSessionIdActivationUrl(session.getServerUrl());
        final String requestBody = requestBodyFactory.getSessionActivationRequestBody(session.getId());

        try {
            webClient.sendPostRequest(url, requestBody);
        } catch (PostRequestException e) {
            throw new SessionActivationException("Session activation failed. Server URL: " + session.getServerUrl(), e);
        }
    }

}
