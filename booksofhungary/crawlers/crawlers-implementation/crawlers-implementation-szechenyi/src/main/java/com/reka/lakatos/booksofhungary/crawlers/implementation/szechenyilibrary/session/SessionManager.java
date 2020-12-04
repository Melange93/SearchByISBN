package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.session;

import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.session.exception.SessionException;
import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.session.sessionfacade.SessionFacade;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SessionManager {

    private final SessionDocumentReader documentReader;
    private final SessionFacade sessionFacade;

    public Session getActiveSession() {
        try {
            final WebDocument document = sessionFacade.getSessionDocument();
            final String serverUrl = documentReader.getServerUrl(document);
            final String sessionId = documentReader.getSessionId(document);
            Session session = new Session(serverUrl, sessionId);
            sessionFacade.activateSession(session);
            return session;
        } catch (Exception e) {
            throw new SessionException(
                    getClass().getSimpleName() + " can't provide an active Session.", e);
        }
    }
}
