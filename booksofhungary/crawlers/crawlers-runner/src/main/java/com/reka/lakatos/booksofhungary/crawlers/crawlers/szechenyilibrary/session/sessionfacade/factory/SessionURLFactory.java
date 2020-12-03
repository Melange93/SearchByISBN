package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.session.sessionfacade.factory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SessionURLFactory {

    public String getSessionProviderUrl() {
        return "http://nektar1.oszk.hu/LVbin/LibriVision/lv_libri_url_db_v2.html?" +
                "USER_LOGIN=Nektar_LV_user&" +
                "USER_PASSWORD=Nektar&" +
                "LanguageCode=hu&" +
                "CountryCode=hu&" +
                "HtmlSetCode=default&" +
                "lv_action=LV_Login&" +
                "HTML_SEARCH_TYPE=SIMPLE&DIRECT_SEARCH_TYPE=BK&" +
                "DIRECT_SEARCH_TERM=978-963-0&" +
                "DB_ID=2";
    }

    public String getSessionIdActivationUrl(final String serverUrl) {
        return serverUrl + "lv_libri_url_auto_login_v2.html";
    }
}
