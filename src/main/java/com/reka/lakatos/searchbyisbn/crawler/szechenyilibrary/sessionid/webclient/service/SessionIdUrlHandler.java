package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.service;

import org.springframework.stereotype.Service;

@Service
public class SessionIdUrlHandler {

    public String getSessionIdProviderUrl() {
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
