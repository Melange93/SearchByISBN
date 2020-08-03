package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SessionActivationChecker {

    private static final String SESSION_TIME_OUT_MESSAGE = "Your session has timed out";

    public boolean isSessionActive(final WebDocument document) {
        List<WebElement> elements = document
                .getElementsByAttributeValueMatching("color", "red");

        return !(elements.size() == 1 &&
                elements.stream()
                        .allMatch(element -> Objects.equals(element.text(), SESSION_TIME_OUT_MESSAGE)));
    }
}