package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;


import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    public WebDocument searchingByISBN(String pAuthorCode, long isbn) {
        String url = urlFactory.getSearchingUrl(pAuthorCode);
        String body = requestBodyFactory.getSearchingBody(isbn);
        return webClient.sendPostRequestWithCookies(url, body, cookies);
    }

    private void setCookies() {
        cookies = cookiesManager.getCookies();
    }
}
