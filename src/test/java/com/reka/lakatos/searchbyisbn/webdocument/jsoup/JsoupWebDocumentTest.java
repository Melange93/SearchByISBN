package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.assertj.core.util.Lists;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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

        assertThat(webDocument.text()).isEqualTo(TEST_DOCUMENT_TEXT);
    }

    @Test
    void select() {
        Elements elements = mock(Elements.class);
        Element firstElement = mock(Element.class);
        List<Element> elementsAsList = Lists.newArrayList(firstElement);

        when(firstElement.text())
                .thenReturn("firstElement");

        when(elements.stream())
                .thenReturn(elementsAsList.stream());

        when(document.select("p"))
                .thenReturn(elements);

        List<WebElement> webElements = webDocument.select("p");
        assertThat(webElements)
                .hasSize(1)
                .allMatch(webElement -> webElement.text().equals("firstElement"));
    }

    @Test
    void toStringTest() {
        when(document.toString()).thenReturn(TEST_DOCUMENT_TEXT);
        String result = webDocument.toString();
        assertThat(result).isEqualTo(TEST_DOCUMENT_TEXT);
    }
}