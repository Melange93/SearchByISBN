package com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries;

import com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries.exception.CookieException;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebClient;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.exception.WebClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class CookiesManager {

    private final WebClient webClient;

    @Value("${crawler.book-crawler.university-catalog-main-url}")
    private String universityUrl;

    public Map<String, String> getCookies() {
        try {
            return webClient.getCookies(universityUrl);
        } catch (WebClientException e) {
            throw new CookieException("Failed to get cookies from " + universityUrl, e);
        }
    }
}
