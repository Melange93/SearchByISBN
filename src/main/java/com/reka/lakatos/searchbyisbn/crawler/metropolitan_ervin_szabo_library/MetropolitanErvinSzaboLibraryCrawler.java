package com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConditionalOnProperty(name = "ervinszabo")
public class MetropolitanErvinSzaboLibraryCrawler implements BookCrawler {
    private static final String ISBN963 = "978963";
    private static final String ISBN615 = "978615";
    private static final String SEARCHING_URL_START = "http://saman.fszek.hu/WebPac/CorvinaWeb?pagesize=";
    private static final String SEARCHING_URL_AFTER_PAGE_SIZE = "&view=short&sort=0&page=";
    private static final String SEARCHING_URL_AFTER_PAGE = "&perpage=0&action=page&actualsearchset=FIND+ISBN+%22";
    private static final String SEARCHING_URL_AFTER_ISBN = "%25%22&actualsort=0&currentpage=result&whichform=simplesearchpage";
    private static final String PAGE_SIZE = "10";


    private BookCreator bookCreator = new BookCreator();

    private int page = 0;
    private int isbnSeventhNumber = 0;

    @Override
    public List<Book> getNextBooks() throws IOException {
        /*
        Document document = Jsoup.connect(
                SEARCHING_URL_START
                        + PAGE_SIZE
                        + SEARCHING_URL_AFTER_PAGE_SIZE
                        + page
                        + SEARCHING_URL_AFTER_PAGE
                        + ISBN963
                        + isbnSeventhNumber
                        + SEARCHING_URL_AFTER_ISBN).get();
        //System.out.println(document);
        page++;
         */
        getBook();
        return null;
    }

    private void getBook() throws IOException {

        Document document1 = Jsoup.connect("http://saman.fszek.hu/WebPac/CorvinaWeb?action=onelong&showtype=longlong&recnum=744130&pos=7494").get();
        Elements long_key = document1.select(".long_key");
        Elements long_value = document1.select(".long_value");

        Map<String, String> prepareBook = new HashMap<>();
        for (int i = 0; i < long_key.size() - 1; i++) {
            prepareBook.put(long_key.get(i).text().trim(),long_value.get(i).text().trim());
        }
        System.out.println(prepareBook);
        bookCreator.createBook(prepareBook);

    }



}
