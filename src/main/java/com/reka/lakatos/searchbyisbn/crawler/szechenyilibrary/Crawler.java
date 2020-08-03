package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.Session;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.SessionManager;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebClient;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {

    private final SessionManager sessionManager;
    private final SessionActivationChecker sessionActivationChecker;
    private final WebClient webClient;

    private String currentServerUrl;
    private String currentServerSessionId;

    @Override
    public List<Book> getNextBooks() {
        System.out.println(getBookListLinks());
        return null;
    }

    private List<String> getBookListLinks() {
        setCurrentServerUrlAndCurrentServerSessionId();

        WebDocument webDocument = webClient.sendPostRequest(
                currentServerUrl,
                "SESSION_ID="
                        + currentServerSessionId
                        + "&lv_action=LV_Scan&new_scan=-1&SCAN_TERM=978-963-0&SCAN_USE=BN&SCAN_PREFERRED_POSITION_IN_RESPONSE=2&SCAN_NUMBER_OF_TERMS_REQUESTED=10&SCAN_STEP_SIZE=0");

        System.out.println(sessionActivationChecker.isSessionActive(webDocument));

        List<String> elements = webDocument.getElementsByTag("a")
                .stream()
                .filter(element -> element.hasAttr("href")
                        && !element.hasAttr("target")
                        && element.hasText())
                .map(element -> element.attr("href"))
                .collect(Collectors.toList());
        elements.forEach(System.out::println);
        return null;
    }

    private void setCurrentServerUrlAndCurrentServerSessionId() {
        Session session = sessionManager.getActiveSession();
        currentServerUrl = session.getServerUrl() + "lv_scan.html";
        currentServerSessionId = session.getId();
    }
}
