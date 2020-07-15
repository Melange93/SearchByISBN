package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SessionActivationChecker {

    private static final String SESSION_TIME_OUT_MESSAGE = "Your session has timed out";

    public boolean isSessionActive(final Document document) {
        Elements elements = document
                .getElementsByAttributeValueMatching("color", "red");

        return !(elements.size() == 1 &&
                elements.stream()
                        .allMatch(element -> Objects.equals(element.text(), SESSION_TIME_OUT_MESSAGE)));
    }
}