package com.reka.lakatos.searchbyisbn.crawler.universitylibralies.webdocumentumfactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class RequestBodyFactory {

    public String getComplexSearchMainReqBody() {
        return "type=advanced&reset=false&searchId=";
    }

    public String getSearchingBody(String isbn) {
        return "rowCount=3" +
                "&test2=false" +
                "&formclosestatus=true" +
                "&extraparam_fullAdvancedSearch=true" +
                "&term0=" + isbn +
                "&type0=isbn" +
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
                "&ftype2=documentSubtype" +
                "&filter3=" +
                "&ftype3=language" +
                "&ftype4=documentType" +
                "&flogic4=OR";
    }

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
                "&term2=" + documentType +
                "&type2=documentType" +
                "&dbid=solr" +
                "&filter0filterintervalbegin=" +
                "&filter0filterintervalend=" +
                "&ftype0=publishDate" +
                "&filter1=" +
                "&ftype1=publishPlace" +
                "&filter2=" +
                "&ftype2=documentSubtype" +
                "&filter3=" +
                "&ftype3=language" +
                "&ftype4=documentType" +
                "&flogic4=OR";
    }
}
