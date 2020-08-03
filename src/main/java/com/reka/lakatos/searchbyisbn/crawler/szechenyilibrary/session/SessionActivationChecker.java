package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SessionActivationChecker {

    private final SessionDocumentReader reader;

    private static final String SESSION_TIME_OUT_MESSAGE = "Your session has timed out";

    public boolean isSessionActive(final WebDocument document) {
        List<WebElement> redErrorMessages = reader.getRedErrorMessages(document);

        return !(redErrorMessages.size() == 1 &&
                redErrorMessages.stream()
                        .allMatch(element -> Objects.equals(element.text(), SESSION_TIME_OUT_MESSAGE)));
    }
}