package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.*;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.SessionManager;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebClientException;
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

    // TODO: 2020. 08. 17. Need to catch this
    public WebDocument getSearchingResult(String scanTerm) {
        try {
            setCurrentServerUrlAndCurrentServerSessionId();
            final String searchingUrl = urlFactory.getSearchingUrl(currentServerUrl);
            final String searchingBody = requestBodyFactory.getSearchingBody(currentServerSessionId, scanTerm);
            log.info("Visit the first searching page: " + searchingUrl);
            return webClient.sendPostRequest(searchingUrl, searchingBody);
        } catch (WebClientException e) {
            throw new FirstSearchingPageException("Failed to download the first searching page. ", e);
        }
    }

    public WebDocument visitBookEditionsLink(final String url) {
        try {
            final String extendUrlWithServerUrl = currentServerUrl + url;
            log.info("Visit book edition page: " + extendUrlWithServerUrl);
            return webClient.sendGetRequest(extendUrlWithServerUrl);
        } catch (WebClientException e) {
            throw new BookEditionsPageException("Failed to visit the book editions page. Url: " + url, e);
        }
    }

    public WebDocument visitBook(final String url) {
        try {
            final String extendUrlWithServerUrl = currentServerUrl + url;
            log.info("Visit book page: " + extendUrlWithServerUrl);
            return webClient.sendGetRequest(extendUrlWithServerUrl);
        } catch (WebClientException e) {
            throw new VisitBookException("Failed to visit the book page. Url: " + url, e);
        }
    }

    // TODO: 2020. 08. 17. Need to catch this
    public WebDocument getNextSearchingPage(final String scanTerm) {
        try {
            final String url = urlFactory.getSearchingUrl(currentServerUrl);
            final String requestBody = requestBodyFactory.getNextPageBody(currentServerSessionId, scanTerm);
            log.info("Visit next searching page.");
            return webClient.sendPostRequest(url, requestBody);
        } catch (WebClientException e) {
            throw new NextSearchingPageException(
                    "Failed to download the next searching page. Previous page first element was " + scanTerm,
                    e
            );
        }
    }

    public WebDocument getRelatedBooksPage(final String bookAmicusId) {
        try {
            final String url = urlFactory.getRelatedBooksUrl(currentServerUrl, currentServerSessionId, bookAmicusId);
            return webClient.sendGetRequest(url);
        } catch (WebClientException e) {
            throw new RelatedBookException("Failed to get related books. Book id: " + bookAmicusId, e);
        }
    }

    private void setCurrentServerUrlAndCurrentServerSessionId() {
        Session session = sessionManager.getActiveSession();
        currentServerUrl = session.getServerUrl();
        currentServerSessionId = session.getId();
    }
}
