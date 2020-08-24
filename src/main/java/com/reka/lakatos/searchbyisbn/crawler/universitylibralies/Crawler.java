package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        String url = "https://hunteka.sze.hu/search" +
                "?p_auth=" + pAuth +
                "&p_p_id=GenericSearch_WAR_akfweb&p_p_lifecycle=1&p_p_state=normal&p_p_mode=view&_GenericSearch_WAR_akfweb_searchType=advanced";

        String body = "rowCount=3&test2=false&formclosestatus=true&extraparam_fullAdvancedSearch=true&term0=&type0=author&logic1=AND&term1=T%C3%BCskev%C3%A1r&type1=title&logic2=AND&term2=&type2=topic&dbid=solr&filter0filterintervalbegin=&filter0filterintervalend=&ftype0=publishDate&filter1=&ftype1=publishPlace&filter2=&ftype2=documentSubtype&filter3=&ftype3=language&ftype4=documentType&flogic4=OR";
        WebDocument webDocument1 = webClient.sendPostRequestWithCookies(url, body, cookies);
        Optional<WebElement> first = webDocument1.select("form").stream()
                .filter(webElement -> webElement.hasAttr("name") && webElement.attr("name").equals("selectFacetForm"))
                .findFirst();
        WebDocument webDocument2 = webClient.sendGetRequestWithCookies(first.get().attr("action"), cookies);
        System.out.println(webDocument2);
        List<String> collect = webDocument2.select("a").stream()
                .filter(webElement -> webElement.hasAttr("href")
                        && webElement.attr("href").startsWith(universityUrl)
                        && webElement.text().equals("Részletek"))
                .map(webElement -> webElement.attr("href"))
                .collect(Collectors.toList());
        WebDocument webDocument3 = webClient.sendGetRequestWithCookies(collect.get(0), cookies);
        //System.out.println(webDocument3);
        return null;
    }
}
