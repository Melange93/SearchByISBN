package com.reka.lakatos.searchbyisbn.webdocument.jsoup;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.assertj.core.util.Lists;
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
class JsoupWebElementTest {

    @Mock
    private Element element;

    @InjectMocks
    private JsoupWebElement jsoupWebElement;

    @Test
    void text() {
        final String testText = "The text";

        when(element.text())
                .thenReturn(testText);

        assertThat(jsoupWebElement.text()).isEqualTo(testText);
    }

    @Test
    void select() {
        Elements elements = mock(Elements.class);
        Element firstElement = mock(Element.class);

        List<Element> elementList = Lists.newArrayList(firstElement);

        when(element.select("p")).thenReturn(elements);
        when(elements.stream()).thenReturn(elementList.stream());

        List<WebElement> webElements = jsoupWebElement.select("p");

        assertThat(webElements)
                .hasSize(1);
    }
}