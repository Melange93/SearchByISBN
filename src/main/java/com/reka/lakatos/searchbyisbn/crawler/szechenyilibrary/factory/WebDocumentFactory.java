package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.SessionManager;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebDocumentFactory {

    private final WebClient webClient;
    private final SessionManager sessionManager;
    private final UrlFactory urlFactory;
    private final RequestBodyFactory requestBodyFactory;

    private String currentServerUrl;
    private String currentServerSessionId;

    public WebDocument getSearchingResult() {
        setCurrentServerUrlAndCurrentServerSessionId();
        final String searchingUrl = urlFactory.getSearchingUrl(currentServerUrl);
        final String searchingBody = requestBodyFactory.getSearchingBody(currentServerSessionId);
        return webClient.sendPostRequest(searchingUrl, searchingBody);
    }

    public WebDocument visitBookEditionsLink(final String url) {
        String extendUrlWithServerUrl = currentServerUrl + url;
        return webClient.sendGetRequest(extendUrlWithServerUrl);
    }

    public WebDocument visitBook(final String url) {
        String extendUrlWithServerUrl = currentServerUrl + url;
        return webClient.sendGetRequest(extendUrlWithServerUrl);
    }

    private void setCurrentServerUrlAndCurrentServerSessionId() {
        Session session = sessionManager.getActiveSession();
        currentServerUrl = session.getServerUrl();
        currentServerSessionId = session.getId();
    }
}
