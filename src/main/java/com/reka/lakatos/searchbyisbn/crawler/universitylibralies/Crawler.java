package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class Crawler implements BookCrawler {

    private final WebDocumentFactory documentFactory;
    private final DocumentReader documentReader;


    @Override
    public List<Book> getNextBooks() {
        WebDocument webDocument = documentFactory.getMainPage();
        Optional<String> pAuthorCode = documentReader.getPAuthorCode(webDocument);
        documentFactory.navigateToComplexSearch();

        WebDocument webDocument1 = documentFactory.searchingByISBN(pAuthorCode.get(), 9789632440453L);
        System.out.println(webDocument1);
        /*
        Optional<WebElement> searchingForm = documentReader.getSearchingForm(webDocument1);
        WebDocument webDocument2 = webClient.sendGetRequestWithCookies(searchingForm.get().attr("action"), cookies);
        System.out.println(webDocument2);
        List<String> collect = webDocument2.select("a").stream()
                .filter(webElement -> webElement.hasAttr("href")
                        && webElement.attr("href").startsWith(universityUrl)
                        && webElement.text().equals("Részletek"))
                .map(webElement -> webElement.attr("href"))
                .collect(Collectors.toList());
        WebDocument webDocument3 = webClient.sendGetRequestWithCookies(collect.get(0), cookies);
        //System.out.println(webDocument3);

         */
        return null;
    }
}
