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
public class SessionIdFactory {

    private static final String SESSION_ID_URL = "http://nektar1.oszk.hu/LVbin/LibriVision/lv_libri_url_db_v2.html?USER_LOGIN=Nektar_LV_user&USER_PASSWORD=Nektar&LanguageCode=hu&CountryCode=hu&HtmlSetCode=default&lv_action=LV_Login&HTML_SEARCH_TYPE=SIMPLE&DIRECT_SEARCH_TYPE=BK&DIRECT_SEARCH_TERM=978-963-0&DB_ID=2";

    public Optional<Map<String, String>> getServerAndSessionId() {
        try {
            Document document = Jsoup.connect(SESSION_ID_URL).get();
            Elements getSessionIdElement =
                    document.getElementsByAttributeValueStarting("name", "SESSION_ID");

            if (document.getElementsByAttributeValueMatching("action", "nektar1") == null) {
                return Optional.of(
                        Collections.singletonMap(
                                "nektar1", getSessionIdElement.get(0).attr("value")));
            }

            return Optional.of(Collections.singletonMap(
                    "nektar2", getSessionIdElement.get(0).attr("value")));
        } catch (IOException e) {
            // ToDo create unique exception
            log.error(String.valueOf(e));
            return Optional.empty();
        }
    }
}
