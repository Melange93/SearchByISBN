package com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries.webdocumentumfactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class RequestBodyFactory {

    public String getSearchingBodyDocumentType(String documentType) {
        return "rowCount=3" +
                "&test2=true" +
                "&formclosestatus=true" +
                "&extraparam_fullAdvancedSearch=true" +
                "&term0=" +
                "&type0=author" +
                "&logic1=AND" +
                "&term1=" +
                "&type1=title" +
                "&logic2=AND" +
                "&term2=" +
                "&type2=topic" +
                "&dbid=solr" +
                "&filter0filterintervalbegin=" +
                "&filter0filterintervalend=" +
                "&ftype0=publishDate" +
                "&filter1=" +
                "&ftype1=publishPlace" +
                "&filter2=" +
                "&ftype2=language" +
                "&ftype3=itemStatusFacet" +
                "&flogic3=OR" +
                "&filter4_0=" + documentType +
                "&ftype4=documentType" +
                "&flogic4=OR";
    }
}
