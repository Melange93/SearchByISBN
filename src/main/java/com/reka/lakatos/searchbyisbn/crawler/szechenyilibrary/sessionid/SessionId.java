package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class SessionId {
    private String serverUrl;
    private String id;
}
