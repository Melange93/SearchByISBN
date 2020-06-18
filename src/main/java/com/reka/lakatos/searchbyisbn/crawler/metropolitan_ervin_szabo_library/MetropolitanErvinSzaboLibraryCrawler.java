package com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ConditionalOnProperty(name = "ervinszabo")
public class MetropolitanErvinSzaboLibraryCrawler implements BookCrawler {
    private static final String ISBN963 = "978963";
    private static final String ISBN615 = "978615";
    private static final String SEARCHING_URL_START = "http://saman.fszek.hu/WebPac/CorvinaWeb?pagesize=&view=short&sort=0&page=";
    private static final String SEARCHING_URL_AFTER_PAGE = "&perpage=";
    private static final String SEARCHING_URL_AFTER_PAGE_SIZE ="&action=page&actualsearchset=FIND+ISBN+%22";
    private static final String SEARCHING_URL_AFTER_ISBN = "%25%22&actualsort=0&currentpage=result&whichform=simplesearchpage";
    private static final String PAGE_SIZE = "50";


    private BookCreator bookCreator = new BookCreator();

    private int page = 0;
    private int isbnSeventhNumber = 0;

    @Override
    public List<Book> getNextBooks() throws IOException {

        getPage();
        //page++;
        //getBook();
        return null;
    }

    private void getPage() throws IOException {
        String url = SEARCHING_URL_START
                + page
                + SEARCHING_URL_AFTER_PAGE
                + PAGE_SIZE
                + SEARCHING_URL_AFTER_PAGE_SIZE
                + ISBN963
                + isbnSeventhNumber
                + SEARCHING_URL_AFTER_ISBN;
        Document document = Jsoup.connect(url).get();
        System.out.println(document);
        System.out.println(getBookDetailsLinkInformation(document));
        System.out.println(url);
    }

    private Map<String, String> getBookDetailsLinkInformation(Document document) {
        Map<String, String> booksInformation = new HashMap<>();

        Elements informationLines = document.select(".short_item_back script");
        for (Element line : informationLines) {
            Pattern lineNumberAndBookId = Pattern.compile("(\\d+,\\d+)");
            Matcher matcher = lineNumberAndBookId.matcher(line.toString());
            if (matcher.find()) {
                String[] information = matcher.group().split(",");
                int lineNumber = 0;
                int bookId = 1;
                booksInformation.put(information[lineNumber], information[bookId]);
            }
        }
        return booksInformation;
    }


    private void getBook() throws IOException {

        Document document1 = Jsoup.connect("http://saman.fszek.hu/WebPac/CorvinaWeb?action=onelong&showtype=longlong&recnum=744130&pos=7494").get();
        Elements bookProperties = document1.select(".long_key");
        Elements bookPropertiesValues = document1.select(".long_value");

        Map<String, String> prepareBook = new HashMap<>();
        for (int i = 0; i < bookProperties.size() - 1; i++) {
            prepareBook.put(bookProperties.get(i).text().trim(),bookPropertiesValues.get(i).text().trim());
        }
        System.out.println(prepareBook);
        bookCreator.createBook(prepareBook);

    }



}
