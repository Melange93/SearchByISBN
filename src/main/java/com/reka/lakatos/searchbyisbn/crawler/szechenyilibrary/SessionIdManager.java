package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SessionIdManager {

    private static final String SESSION_ID_URL = "http://nektar1.oszk.hu/LVbin/LibriVision/lv_libri_url_db_v2.html?USER_LOGIN=Nektar_LV_user&USER_PASSWORD=Nektar&LanguageCode=hu&CountryCode=hu&HtmlSetCode=default&lv_action=LV_Login&HTML_SEARCH_TYPE=SIMPLE&DIRECT_SEARCH_TYPE=BK&DIRECT_SEARCH_TERM=978-963-0&DB_ID=2";
    private static final String SERVER_1_NAME = "nektar1";
    private static final String SERVER_2_NAME = "nektar2";

    public Optional<Map<String, String>> getServerAndSessionId() {
        try {
            Document document = Jsoup.connect(SESSION_ID_URL).get();

            Optional<String> server = getServer(document);
            Optional<String> sessionId = getSessionId(document);

            if (server.isPresent() && sessionId.isPresent()) {
                return Optional.of(Collections.singletonMap(
                        server.get(), sessionId.get()));
            }
            return Optional.empty();

        } catch (IOException e) {
            // ToDo create unique exception
            log.error(String.valueOf(e));
            return Optional.empty();
        }
    }

    private Optional<String> getServer(final Document document) {
        if (document.getElementsByAttributeValueMatching("action", SERVER_1_NAME) == null) {
            return Optional.of(SERVER_1_NAME);
        }
        if (document.getElementsByAttributeValueMatching("action", SERVER_2_NAME) == null) {
            return Optional.of(SERVER_2_NAME);
        }
        return Optional.empty();
    }

    private Optional<String> getSessionId(final Document document) {
        Elements getSessionIdElement =
                document.getElementsByAttributeValueStarting("name", "SESSION_ID");

        if (getSessionIdElement.size() == 1) {
            return Optional.of(getSessionIdElement.get(0).attr("value"));
        }

        return Optional.empty();
    }
}
