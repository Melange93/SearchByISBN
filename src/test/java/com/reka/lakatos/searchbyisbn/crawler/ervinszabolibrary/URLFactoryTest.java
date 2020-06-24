package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class URLFactoryTest {

    private URLFactory urlFactory;

    @BeforeEach
    private void init() {
        urlFactory = new URLFactory();
    }

    @Test
    void createISBNSearchingUrl() {
        int page = 0;
        String isbn = "9789630";
        String pageSize = "10";

        String isbnSearchingUrl = urlFactory.createISBNSearchingUrl(page, isbn, pageSize);
        assertThat(isbnSearchingUrl).isEqualTo(
                "http://saman.fszek.hu/WebPac/CorvinaWeb?pagesize=10"
                + "&view=short&sort=0&page=0"
                + "&perpage=10"
                + "&action=perpage&actualsearchset=FIND+ISBN+%229789630"
                + "%25%22&actualsort=0&language=&currentpage=result&text0"
                + "=&index0=&whichform=simplesearchpage&showmenu=&resultview=short"
                + "&recnum=&marcposition=&text0=&index0=&ccltext=&resultsize=");

    }

    @Test
    void createBookDetailsUrl() {
        String bookLineNumber = "1";
        String bookId = "7777";

        String bookDetailsUrl = urlFactory.createBookDetailsUrl(bookLineNumber, bookId);
        assertThat(bookDetailsUrl).isEqualTo(
                "http://saman.fszek.hu/WebPac/CorvinaWeb" +
                        "?action=onelong&showtype=longlong&recnum=7777&pos=1");
    }
}