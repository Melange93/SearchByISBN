package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory;

import org.springframework.stereotype.Service;

@Service
public class UrlFactory {

    public String getSearchingUrl(final String serverUrl) {
        return serverUrl + "lv_scan.html";
    }
}
