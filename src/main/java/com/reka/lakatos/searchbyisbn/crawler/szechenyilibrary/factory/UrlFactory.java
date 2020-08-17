package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.factory;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception.ServerUrlCleaningException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UrlFactory {

    public String getSearchingUrl(final String serverUrl) {
        return serverUrl + "lv_scan.html";
    }

    public String getRelatedBooksUrl(final String serverUrl, final String sessionId, final String bookAmicusId) {
        Optional<String> cleanUrl = cleanUrl(serverUrl);
        if (cleanUrl.isEmpty()) {
            throw new ServerUrlCleaningException("Failed to clean the server url and create related books url.");
        }
        return cleanUrl.get() +
                "kapcsrek_spring/kapcsrek.htm?" +
                "id=" + bookAmicusId +
                "&db_id=2" +
                "&language=hu" +
                "&session_id=" + sessionId +
                "&return_url=" + cleanUrl.get() +
                "&view=1";
    }

    private Optional<String> cleanUrl(final String serverUrl) {
        String basicServerUrlRegex = "http:[\\/]{2}.*?[\\.]hu\\/";
        Matcher matcher = Pattern.compile(basicServerUrlRegex).matcher(serverUrl);
        return matcher.find() ? Optional.of(matcher.group()) : Optional.empty();
    }
}
