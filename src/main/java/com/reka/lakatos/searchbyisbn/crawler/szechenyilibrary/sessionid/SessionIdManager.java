package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.model.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.sessionidcreator.SessionIdCreator;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionIdManager {
    private final WebClient webClient;
    private final SessionIdCreator sessionIdCreator;

    public Session getActivatedServerAndSessionId() {
        try {
            Session session = sessionIdCreator.getNewSession();
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
