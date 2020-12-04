package com.reka.lakatos.booksofhungary.crawlers.implementation.ervinszabolibrary;

import org.springframework.stereotype.Service;

@Service
public class URLFactory {

    public String createISBNSearchingUrl(int pageNumber, String isbn, String pageSize) {
        return "http://saman.fszek.hu/WebPac/CorvinaWeb?pagesize="
                + pageSize
                + "&view=short&sort=0&page="
                + pageNumber
                + "&perpage=0"
                + "&action=perpage&actualsearchset=FIND+ISBN+%22"
                + isbn
                + "%25%22&actualsort=0&language=&currentpage=result&text0=&index0=&whichform=simplesearchpage&showmenu=&resultview=short&recnum=&marcposition=&text0=&index0=&ccltext=&resultsize=";
    }

    public String createBookPropertiesUrl(String bookLineNumber, String bookId) {
        return "http://saman.fszek.hu/WebPac/CorvinaWeb?action=onelong&showtype=longlong&recnum="
                + bookId
                + "&pos="
                + bookLineNumber;
    }

}
