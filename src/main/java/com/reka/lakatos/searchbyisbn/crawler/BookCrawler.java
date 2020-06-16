package com.reka.lakatos.searchbyisbn.crawler;

import com.reka.lakatos.searchbyisbn.document.Book;

import java.io.IOException;
import java.util.List;

public interface BookCrawler {

    List<Book> getNextBooks() throws IOException;

}
