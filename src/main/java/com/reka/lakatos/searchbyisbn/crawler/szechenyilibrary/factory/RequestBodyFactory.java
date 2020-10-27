package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class RequestBodyFactory {

    private static final int SEARCHING_BOOK_NUMBER = 10;
    private static final int NUMBER_OF_RECORDS = 50;

    public String getSearchingBody(final String currentServerSessionId, String scanTerm) {
        return "SESSION_ID="
                + currentServerSessionId
                + "&lv_action=LV_Scan"
                + "&new_scan=-1"
                + "&SCAN_TERM=" + scanTerm
                + "&SCAN_USE=BN"
                + "&SCAN_PREFERRED_POSITION_IN_RESPONSE=2"
                + "&SCAN_NUMBER_OF_TERMS_REQUESTED="
                + SEARCHING_BOOK_NUMBER
                + "&SCAN_STEP_SIZE=0";
    }

    public String getNextPageBody(final String currentServerSessionId, final String scanTerm) {
        return "SESSION_ID=" +
                currentServerSessionId +
                "&lv_action=LV_Scan" +
                "&new_scan=-1" +
                "&SCAN_TERM=" +
                scanTerm +
                "&SCAN_USE_ATTRIBUTE=7" +
                "&SCAN_USE=BN" +
                "&SCAN_PREFERRED_POSITION_IN_RESPONSE=10" +
                "&SCAN_NUMBER_OF_TERMS_REQUESTED=" +
                NUMBER_OF_RECORDS +
                "&SCAN_STEP_SIZE=0";
    }
}
