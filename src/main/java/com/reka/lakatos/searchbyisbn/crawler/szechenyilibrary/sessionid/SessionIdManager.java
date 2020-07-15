package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.documentreader.DocumentReader;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.model.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionIdManager {

    private final WebClient webClient;
    private final DocumentReader documentReader;

    public Session getActivatedServerAndSessionId() {
        try {
            final Document document = webClient.getServerAndSessionIdDocument();
            final String serverUrl = documentReader.getServerUrl(document);
            final String sessionId = documentReader.getSessionId(document);
            Session session = new Session(serverUrl, sessionId);
            webClient.activateSessionId(session.getServerUrl(), session.getId());
            return session;
        } catch (Exception e) {
            // ToDo create unique exception
            throw new RuntimeException(
                    getClass().getSimpleName() +
                            " can't provide an active Session ID because of "
                            + e);
        }
    }
}
