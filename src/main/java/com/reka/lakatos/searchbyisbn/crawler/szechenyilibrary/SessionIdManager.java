package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SessionIdManager {

    private static final String SESSION_ID_URL = "http://nektar1.oszk.hu/LVbin/LibriVision/lv_libri_url_db_v2.html?" +
            "USER_LOGIN=Nektar_LV_user&" +
            "USER_PASSWORD=Nektar&" +
            "LanguageCode=hu&" +
            "CountryCode=hu&" +
            "HtmlSetCode=default&" +
            "lv_action=LV_Login&" +
            "HTML_SEARCH_TYPE=SIMPLE&DIRECT_SEARCH_TYPE=BK&" +
            "DIRECT_SEARCH_TERM=978-963-0&" +
            "DB_ID=2";
    private static final String[] SERVERS_URL = {
            "http://nektar1.oszk.hu/LVbin/LibriVision/",
            "http://nektar2.oszk.hu/LVbin/LibriVision/"};

    public Optional<Map<String, String>> getActivatedServerAndSessionId() {
        Optional<Map<String, String>> serverAndSessionId = getServerAndSessionId();

        if (serverAndSessionId.isPresent()) {
            String serverUrl = serverAndSessionId.get().keySet().stream().findFirst().get();
            String sessionId = serverAndSessionId.get().values().stream().findFirst().get();
            activateSessionId(serverUrl, sessionId);
        }
        return serverAndSessionId;
    }

    private Optional<Map<String, String>> getServerAndSessionId() {
        try {
            final Document document = Jsoup.connect(SESSION_ID_URL).get();
            final Optional<String> server = getServer(document);
            final Optional<String> sessionId = getSessionId(document);

            return server.isPresent() && sessionId.isPresent() ?
                    Optional.of(Collections.singletonMap(server.get(), sessionId.get())) :
                    Optional.empty();
        } catch (IOException e) {
            // ToDo create unique exception
            throw new RuntimeException("Failed to get session ID");
        }
    }

    private Optional<String> getServer(final Document document) {
        return Arrays.stream(SERVERS_URL)
                .filter(serverUrl -> document
                        .getElementsByAttributeValueMatching("action", serverUrl)
                        .size() == 1)
                .findFirst();
    }

    private Optional<String> getSessionId(final Document document) {
        Elements getSessionIdElement =
                document.getElementsByAttributeValueStarting("name", "SESSION_ID");

        return getSessionIdElement.size() == 1 ?
                Optional.of(getSessionIdElement.first().attr("value")) :
                Optional.empty();
    }

    private void activateSessionId(String serverUrl, String sessionId) {
        try {
            Jsoup.connect(serverUrl + "lv_libri_url_auto_login_v2.html")
                    .requestBody("USER_LOGIN=demo&" +
                            "USER_PASSWORD=demo98&" +
                            "LanguageCode=hu&" +
                            "CountryCode=hu&" +
                            "HtmlSetCode=default&" +
                            "lv_action=LV_Search_Form&" +
                            "SESSION_ID=" + sessionId + "&" +
                            "HTML_SEARCH_TYPE=SIMPLE&" +
                            "DB_ID=2&" +
                            "DIRECT_SEARCH_TYPE=BK&" +
                            "DIRECT_SEARCH_TERM=978-963-0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .post();
        } catch (IOException e) {
            // ToDo create unique exception
            throw new RuntimeException("Session ID activation failed");
        }
    }
}
