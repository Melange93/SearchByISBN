package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.SessionManager;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("Visit the first searching page: " + searchingUrl);
        return webClient.sendPostRequest(searchingUrl, searchingBody);
    }

    public WebDocument visitBookEditionsLink(final String url) {
        final String extendUrlWithServerUrl = currentServerUrl + url;
        log.info("Visit book edition page: " + extendUrlWithServerUrl);
        return webClient.sendGetRequest(extendUrlWithServerUrl);
    }

    public WebDocument visitBook(final String url) {
        final String extendUrlWithServerUrl = currentServerUrl + url;
        log.info("Visit book page: " + extendUrlWithServerUrl);
        return webClient.sendGetRequest(extendUrlWithServerUrl);
    }

    public WebDocument getNextSearchingPage(final String scanTerm) {
        final String url = urlFactory.getSearchingUrl(currentServerUrl);
        final String requestBody = requestBodyFactory.getNextPageBody(currentServerSessionId, scanTerm);
        log.info("Visit next searching page.");
        return webClient.sendPostRequest(url, requestBody);
    }

    private void setCurrentServerUrlAndCurrentServerSessionId() {
        Session session = sessionManager.getActiveSession();
        currentServerUrl = session.getServerUrl();
        currentServerSessionId = session.getId();
    }
}
