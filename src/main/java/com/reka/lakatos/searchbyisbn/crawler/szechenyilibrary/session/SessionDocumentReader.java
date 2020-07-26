package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.ServerUrlReadingException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.SessionIdReadingException;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SessionDocumentReader {
    private static final String[] SERVERS_URL = {
            "http://nektar1.oszk.hu/LVbin/LibriVision/",
            "http://nektar2.oszk.hu/LVbin/LibriVision/"
    };

    public String getServerUrl(final WebDocument document) {
        Optional<String> serverUrl = Arrays.stream(SERVERS_URL)
                .filter(url -> document
                        .getElementsByAttributeValueMatching("action", url)
                        .size() == 1)
                .findFirst();

        if (serverUrl.isPresent()) {
            return serverUrl.get();
        }
        throw new ServerUrlReadingException(
                "Failed to get server url from document, because the server url is null."
        );
    }

    public String getSessionId(final WebDocument document) {
        List<WebElement> getSessionIdElement =
                document.getElementsByAttributeValueStarting("name", "SESSION_ID");

        if (getSessionIdElement.size() == 1) {
            Optional<String> sessionIdOptinal = getSessionIdElement
                    .stream()
                    .findFirst()
                    .map(webElement -> webElement.attr("value"));
            if (sessionIdOptinal.isPresent()) {
                return sessionIdOptinal.get();
            }
        }
        throw new SessionIdReadingException(
                "Failed to get server session id from document, because the session id is null."
        );
    }
}
