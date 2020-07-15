package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.sessionidcreator;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.model.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.sessionidcreator.service.DocumentReader;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.WebClient;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionIdCreator {

    private final WebClient webClient;
    private final DocumentReader documentReader;

    public Session getNewSession() {
        final Document document = webClient.getServerAndSessionIdDocument();
        final String serverUrl = documentReader.getServerUrl(document);
        final String sessionId = documentReader.getSessionId(document);
        return new Session(serverUrl, sessionId);
    }
}
