package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.validation;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtendISBNPropertyValidatorStrategyTest {

    @Mock
    private DefaultISBNPropertyValidatorStrategy defaultISBNValidator;

    @Mock
    private Map<String, Boolean> propertyContainsValueResult;

    private static final Set<String> KEY_SET = Sets.newHashSet(Arrays.asList("hibás", "*"));

    private PropertyValidatorStrategy propertyValidatorStrategy;

    @BeforeEach
    void init() {
        propertyValidatorStrategy = new ExtendISBNPropertyValidatorStrategy(
                defaultISBNValidator,
                propertyContainsValueResult
        );
    }

    @Test
    void validatePropertyStar() {
        String property = "ISBN 978-963-00-7647-0*";
        when(propertyContainsValueResult.keySet()).thenReturn(KEY_SET);
        when(propertyContainsValueResult.get("*")).thenReturn(false);

        boolean result = propertyValidatorStrategy.validateProperty(property);
        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyInvalid() {
        String property = "ISBN 978-963-00-7647-0 [hibás ISBN 963-00-7647-0]";
        when(propertyContainsValueResult.keySet()).thenReturn(KEY_SET);
        when(propertyContainsValueResult.get("hibás")).thenReturn(false);

        boolean result = propertyValidatorStrategy.validateProperty(property);
        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyEmptyProperty() {
        String property = "";
        boolean result = propertyValidatorStrategy.validateProperty(property);
        assertThat(result).isFalse();
    }

    @Test
    void validatePropertyValid() {
        String property = "ISBN 978-963-00-7647-0";
        when(propertyContainsValueResult.keySet()).thenReturn(KEY_SET);
        when(defaultISBNValidator.validateProperty(property)).thenReturn(true);

        boolean result = propertyValidatorStrategy.validateProperty(property);
        assertThat(result).isTrue();
    }
}