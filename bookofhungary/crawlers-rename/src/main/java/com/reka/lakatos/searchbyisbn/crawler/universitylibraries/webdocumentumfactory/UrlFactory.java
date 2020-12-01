package com.reka.lakatos.searchbyisbn.crawler.universitylibraries.webdocumentumfactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class UrlFactory {

    @Value("${crawler.book-crawler.university-catalog-main-url}")
    private String universityUrl;

    public String getUniversityUrl() {
        return universityUrl;
    }

    public String getSearchingUrl(String pAuth) {
        return universityUrl +
                "search" +
                "?p_auth=" + pAuth +
                "&p_p_id=GenericSearch_WAR_akfweb" +
                "&p_p_lifecycle=1" +
                "&p_p_state=normal" +
                "&p_p_mode=view" +
                "&_GenericSearch_WAR_akfweb_searchType=advanced";
    }
}
