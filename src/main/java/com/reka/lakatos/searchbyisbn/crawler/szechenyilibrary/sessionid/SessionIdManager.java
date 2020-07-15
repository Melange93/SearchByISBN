package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.webclient.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionIdManager {
    private final WebClient webClient;

    private static final String[] SERVERS_URL = {
            "http://nektar1.oszk.hu/LVbin/LibriVision/",
            "http://nektar2.oszk.hu/LVbin/LibriVision/"};

    public Map.Entry<String, String> getActivatedServerAndSessionId() {
        try {
            Optional<Map.Entry<String, String>> serverAndSessionId = getServerAndSessionId();

            if (serverAndSessionId.isPresent()) {
                String serverUrl = serverAndSessionId.get().getKey();
                String sessionId = serverAndSessionId.get().getValue();
                webClient.activateSessionId(serverUrl, sessionId);
                return serverAndSessionId.get();
            }
            // ToDo create unique exception
            throw new RuntimeException(
                    getClass().getSimpleName() +
                            " can't provide an active Session ID");
        } catch (Exception e) {
            // ToDo create unique exception
            throw new RuntimeException(
                    getClass().getSimpleName() +
                            " can't provide an active Session ID because of "
                            + e);
        }
    }

    private Optional<Map.Entry<String, String>> getServerAndSessionId() {
        final Document document = webClient.getServerAndSessionIdDocument();
        final Optional<String> server = getServer(document);
        final Optional<String> sessionId = getSessionId(document);

        return server.isPresent() && sessionId.isPresent() ?
                Optional.of(Map.entry(server.get(), sessionId.get())) :
                Optional.empty();
    }

    private Optional<String> getServer(final Document document) {
        return Arrays.stream(SERVERS_URL)
                .filter(serverUrl -> document
                        .getElementsByAttributeValueMatching("action", serverUrl)
                        .size() == 1)
                .findFirst();
    }

    private Optional<String> getSessionId(final Document document) {
        Elements getSessionIdElement =
                document.getElementsByAttributeValueStarting("name", "SESSION_ID");

        return getSessionIdElement.size() == 1 ?
                Optional.of(getSessionIdElement.first().attr("value")) :
                Optional.empty();
    }
}
