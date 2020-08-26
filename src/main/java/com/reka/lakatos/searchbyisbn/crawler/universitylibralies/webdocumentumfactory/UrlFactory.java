package com.reka.lakatos.searchbyisbn.crawler.universitylibralies.webdocumentumfactory;

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

    public String getComplexSearchMainUrl() {
        return universityUrl +
                "search?" +
                "p_p_id=GenericSearch_WAR_akfweb" +
                "&p_p_lifecycle=2" +
                "&p_p_state=normal" +
                "&p_p_mode=view" +
                "&p_p_resource_id=getSearch" +
                "&p_p_cacheability=cacheLevelPage" +
                "&p_p_col_id=column-1" +
                "&p_p_col_pos=2" +
                "&p_p_col_count=5";
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
