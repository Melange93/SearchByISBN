package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import org.springframework.stereotype.Service;

@Service
public class RequestBodyFactory {

    private static final int SEARCHING_BOOK_NUMBER = 10;

    public String getSearchingBody(final String currentServerSessionId) {
        return "SESSION_ID="
                + currentServerSessionId
                + "&lv_action=LV_Scan"
                + "&new_scan=-1"
                + "&SCAN_TERM=978-963-0"
                + "&SCAN_USE=BN"
                + "&SCAN_PREFERRED_POSITION_IN_RESPONSE=2"
                + "&SCAN_NUMBER_OF_TERMS_REQUESTED="
                + SEARCHING_BOOK_NUMBER
                + "&SCAN_STEP_SIZE=0";
    }

}
