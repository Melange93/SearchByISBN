package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {

    @Override
    public List<Book> getNextBooks() {
        try {
            log.info("Széchenyi");
            Document document = Jsoup.connect("http://nektar1.oszk.hu/LVbin/LibriVision/lv_libri_url_db_v2.html?USER_LOGIN=Nektar_LV_user&USER_PASSWORD=Nektar&LanguageCode=hu&CountryCode=hu&HtmlSetCode=default&lv_action=LV_Login&HTML_SEARCH_TYPE=SIMPLE&DIRECT_SEARCH_TYPE=BK&DIRECT_SEARCH_TERM=978-963-0&DB_ID=2").get();

            Elements elementsByAttributeValueStarting = document.getElementsByAttributeValueStarting("name", "SESSION_ID");
            System.out.println(elementsByAttributeValueStarting);
/*
            Connection.Response execute = Jsoup.connect("http://nektar2.oszk.hu/LVbin/LibriVision/lv_scan.html").data(
                    "SESSION_ID", "1593591729_1072190528",
                    "lv_action", "LV_Scan",
                    "new_scan", "-1",
                    "SCAN_TERM", "9789630039208",
                    "SCAN_USE_ATTRIBUTE", "7",
                    "SCAN_USE", "BN",
                    "SCAN_PREFERRED_POSITION_IN_RESPONSE", "10",
                    "SCAN_NUMBER_OF_TERMS_REQUESTED", "10",
                    "SCAN_STEP_SIZE", "0")
                    .method(Connection.Method.POST)
                    .execute();
            System.out.println(execute.body());


 */

            return null;
        } catch (IOException e) {
            log.error(String.valueOf(e));
            return null;
        }
    }
}
