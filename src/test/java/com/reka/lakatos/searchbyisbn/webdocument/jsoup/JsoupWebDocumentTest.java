package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsoupWebDocumentTest {

    @Mock
    private Document document;

    @InjectMocks
    private JsoupWebDocument webDocument;

    private static final String TEST_DOCUMENT_TEXT = "<html>documenttext</html>";

    @Test
    void text() {
        when(document.text())
                .thenReturn(TEST_DOCUMENT_TEXT);

        assertThat(webDocument.text(), is(equalTo(TEST_DOCUMENT_TEXT)));
    }
}