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
public class SessionManager {
    private static final String SESSION_ID_NAME = "JSESSIONID";
    private final WebClient webClient;

    @Value("${crawler.book-crawler.university-catalog-main-url}")
    private String universityUrl;

    public Session getActiveSession() {
        Map<String, String> cookies = webClient.getCookies(universityUrl);
        Session session = new Session();
        session.setJsessionId(cookies.get(SESSION_ID_NAME));
        return session;
    }
}
