package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
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
        return webClient.getCookies(universityUrl);
    }
}
