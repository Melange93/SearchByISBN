package com.reka.lakatos.searchbyisbn.crawler.universitylibralies.webdocumentumfactory;


import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.CookiesManager;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
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
        setCookies();
        String universityUrl = urlFactory.getUniversityUrl();
        return webClient.sendGetRequestWithCookies(universityUrl, cookies);
    }

    public void navigateToComplexSearch() {
        String url = urlFactory.getComplexSearchMainUrl();
        String body = requestBodyFactory.getComplexSearchMainReqBody();
        webClient.sendPostRequestWithCookies(url, body, cookies);
    }

    public WebDocument searchingByDocumentType(String pAuthorCode, String documentType) {
        log.info("Start searching by document type: " + documentType);
        String url = urlFactory.getSearchingUrl(pAuthorCode);
        String body = requestBodyFactory.getSearchingBodyDocumentType(documentType);
        return webClient.sendPostRequestWithCookies(url, body, cookies);
    }

    public WebDocument furtherSearchingByDocumentType(String url) {
        log.info("Start searching. Url: " + url);
        return webClient.sendGetRequestWithCookies(url, cookies);
    }

    public WebDocument visitBook(String url) {
        log.info("Visit book: " + url);
        return webClient.sendGetRequestWithCookies(url, cookies);
    }

    private void setCookies() {
        cookies = cookiesManager.getCookies();
    }
}
