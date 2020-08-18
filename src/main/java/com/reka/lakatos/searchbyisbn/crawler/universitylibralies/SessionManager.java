package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class SessionManager {

    private final WebClient webClient;

    public Session getActiveSession() {
        Map<String, String> cookies = webClient.getCookies("https://hunteka.sze.hu/");
        System.out.println(cookies);
        return null;
    }

}
