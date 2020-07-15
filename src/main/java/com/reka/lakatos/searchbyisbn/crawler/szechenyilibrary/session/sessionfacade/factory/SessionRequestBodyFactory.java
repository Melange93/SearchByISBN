package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.sessionfacade.factory;

import org.springframework.stereotype.Service;

@Service
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
