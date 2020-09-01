package com.reka.lakatos.searchbyisbn.crawler.universitylibraries.webdocumentumfactory;


import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.CookiesManager;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.exception.FurtherSearchingException;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.exception.NavigationException;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.exception.SearchingByDocumentTypeException;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.exception.VisitBookException;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.exception.WebClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class WebDocumentFactory {

    private final CookiesManager cookiesManager;
    private final WebClient webClient;
    private final UrlFactory urlFactory;
    private final RequestBodyFactory requestBodyFactory;

    private Map<String, String> cookies;

    public WebDocument getMainPage() {
        try {
            setCookies();
            final String universityUrl = urlFactory.getUniversityUrl();
            return webClient.sendGetRequestWithCookies(universityUrl, cookies);
        } catch (WebClientException e) {
            throw new NavigationException("Failed to navigate to the main page", e);
        }
    }

    public void navigateToComplexSearch() {
        try {
            final String url = urlFactory.getComplexSearchMainUrl();
            final String body = requestBodyFactory.getComplexSearchMainReqBody();
            webClient.sendPostRequestWithCookies(url, body, cookies);
        } catch (WebClientException e) {
            throw new NavigationException("Failed to navigate to the complex searching page", e);
        }
    }

    public WebDocument searchingByDocumentType(String pAuthorCode, String documentType) {
        try {
            log.info("Start searching by document type: " + documentType);
            final String url = urlFactory.getSearchingUrl(pAuthorCode);
            final String body = requestBodyFactory.getSearchingBodyDocumentType(documentType);
            return webClient.sendPostRequestWithCookies(url, body, cookies);
        } catch (WebClientException e) {
            throw new SearchingByDocumentTypeException("Search failed for this document type: " + documentType, e);
        }
    }

    public WebDocument furtherSearchingByDocumentType(String url) {
        try {
            log.info("Start searching. Url: " + url);
            return webClient.sendGetRequestWithCookies(url, cookies);
        } catch (WebClientException e) {
            throw new FurtherSearchingException("Saerching failed for this url: " + url, e);
        }
    }

    public WebDocument visitBook(String url) {
        try {
            log.info("Visit book: " + url);
            return webClient.sendGetRequestWithCookies(url, cookies);
        } catch (WebClientException e) {
            throw new VisitBookException("Failed to visit this book. Url: " + url, e);
        }
    }

    private void setCookies() {
        cookies = cookiesManager.getCookies();
    }
}
