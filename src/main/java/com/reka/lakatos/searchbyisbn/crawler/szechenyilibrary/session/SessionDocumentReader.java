package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.ServerUrlReadingException;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception.SessionIdReadingException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class SessionDocumentReader {
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
        throw new ServerUrlReadingException(
                "Failed to get server url from document, because the server url is null."
        );
    }

    public String getSessionId(final Document document) {
        Elements getSessionIdElement =
                document.getElementsByAttributeValueStarting("name", "SESSION_ID");

        if (getSessionIdElement.size() == 1) {
            return getSessionIdElement.first().attr("value");
        }
        throw new SessionIdReadingException(
                "Failed to get server session id from document, because the session id is null."
        );
    }
}
