package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.SessionIdManager;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {

    private final SessionIdManager sessionIdFactory;

    private String currentServerUrl;
    private String currentServerSessionId;

    @Override
    public List<Book> getNextBooks() {
        System.out.println(getBookListLinks());
        return null;
    }

    private List<String> getBookListLinks() {
        setCurrentServerUrlAndCurrentServerSessionId();

        try {
            Document document = Jsoup.connect(currentServerUrl)
                    .requestBody("SESSION_ID=" + currentServerSessionId + "&lv_action=LV_Scan&new_scan=-1&SCAN_TERM=978-963-0&SCAN_USE=BN&SCAN_PREFERRED_POSITION_IN_RESPONSE=2&SCAN_NUMBER_OF_TERMS_REQUESTED=10&SCAN_STEP_SIZE=0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .post();

            System.out.println(document);
            List<String> elements = document.getElementsByTag("a")
                    .stream()
                    .filter(element -> element.hasAttr("href")
                            && !element.hasAttr("target")
                            && element.hasText())
                    .map(element -> element.attr("href"))
                    .collect(Collectors.toList());
            elements.forEach(System.out::println);

        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
        return null;
    }

    private void setCurrentServerUrlAndCurrentServerSessionId() {
        Map.Entry<String, String> serverUrlAndSessionId = sessionIdFactory.getActivatedServerAndSessionId();
        currentServerUrl = serverUrlAndSessionId.getKey() + "lv_scan.html";
        currentServerSessionId = serverUrlAndSessionId.getValue();
    }
}
