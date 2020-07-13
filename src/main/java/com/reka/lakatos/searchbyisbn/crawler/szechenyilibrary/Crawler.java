package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
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
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {

    private final SessionIdManager sessionIdFactory;

    private static final String SERVER_1_URL = "http://nektar1.oszk.hu/LVbin/LibriVision/lv_scan.html";
    private static final String SERVER_2_URL = "http://nektar2.oszk.hu/LVbin/LibriVision/lv_scan.html";
    private static final String SERVER_1_NAME = "nektar1";
    private static final String SERVER_2_NAME = "nektar2";

    private String currentServerUrl;
    private String currentServerSessionId;

    @Override
    public List<Book> getNextBooks() {
        System.out.println(sessionIdFactory.getServerAndSessionId());
        System.out.println(getBookListLinks());
        return null;
    }

    private List<String> getBookListLinks() {

        Optional<Map<String, String>> sessionId = sessionIdFactory.getServerAndSessionId();
        if (sessionId.isPresent()) {
            Map<String, String> serverSessionId = sessionId.get();
            if (serverSessionId.containsKey(SERVER_1_NAME)) {
                currentServerUrl = SERVER_1_URL;
                currentServerSessionId = serverSessionId.get(SERVER_1_NAME);
            }
            currentServerUrl = SERVER_2_URL;
            currentServerSessionId = serverSessionId.get(SERVER_2_NAME);
        }

        System.out.println(currentServerSessionId);
        System.out.println(currentServerUrl);


        try {
            Document document = Jsoup.connect(currentServerUrl)
                    .requestBody("SESSION_ID=" + currentServerSessionId + "&lv_action=LV_Scan&new_scan=-1&SCAN_TERM=978-963-0&SCAN_USE=BN&SCAN_PREFERRED_POSITION_IN_RESPONSE=2&SCAN_NUMBER_OF_TERMS_REQUESTED=10&SCAN_STEP_SIZE=0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .post();

            System.out.println(document);
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
        return null;
    }
}
