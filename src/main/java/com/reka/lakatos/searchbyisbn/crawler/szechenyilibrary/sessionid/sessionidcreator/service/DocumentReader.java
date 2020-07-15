package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.sessionidcreator.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class DocumentReader {
    private static final String[] SERVERS_URL = {
            "http://nektar1.oszk.hu/LVbin/LibriVision/",
            "http://nektar2.oszk.hu/LVbin/LibriVision/"
    };

    public String getServerUrl(final Document document) {
        Optional<String> serverUrl = Arrays.stream(SERVERS_URL)
                .filter(url -> document
                        .getElementsByAttributeValueMatching("action", url)
                        .size() == 1)
                .findFirst();

        if (serverUrl.isPresent()) {
            return serverUrl.get();
        }
        throw new RuntimeException("Failed to get server url from document");
    }

    public String getSessionId(final Document document) {
        Elements getSessionIdElement =
                document.getElementsByAttributeValueStarting("name", "SESSION_ID");

        if (getSessionIdElement.size() == 1) {
            return getSessionIdElement.first().attr("value");
        }
        throw new RuntimeException("Failed to get server session id from document");
    }
}
