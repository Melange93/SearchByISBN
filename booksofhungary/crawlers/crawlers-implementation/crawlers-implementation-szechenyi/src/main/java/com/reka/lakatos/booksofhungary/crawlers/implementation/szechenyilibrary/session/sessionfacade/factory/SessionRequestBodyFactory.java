package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.session.sessionfacade.factory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SessionRequestBodyFactory {

    public String getSessionActivationRequestBody(final String sessionId) {
        return "USER_LOGIN=demo&" +
                "USER_PASSWORD=demo98&" +
                "LanguageCode=hu&" +
                "CountryCode=hu&" +
                "HtmlSetCode=default&" +
                "lv_action=LV_Search_Form&" +
                "SESSION_ID=" + sessionId + "&" +
                "HTML_SEARCH_TYPE=SIMPLE&" +
                "DB_ID=2&" +
                "DIRECT_SEARCH_TYPE=BK&" +
                "DIRECT_SEARCH_TERM=978-963-0";
    }
}
