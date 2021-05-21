package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.test.bookcreation.validation;

import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.strategy.DefaultCoverTypeValidatorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoverTyePropertyValidatorStrategyTest {

    private PropertyValidatorStrategy propertyValidatorStrategy;

    @BeforeEach
    void init() {
        propertyValidatorStrategy = new DefaultCoverTypeValidatorStrategy();
    }

    @Test
    void validatePropertyCoverType() {
        String property = "55 kétszólamú énekgyakorlat [kötött] : felső szólam / Kodály Zoltán.";
        boolean result = propertyValidatorStrategy.validateProperty(property);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void validatePropertyCoverTypeBanned1() {
        String property = "55 kétszólamú énekgyakorlat [nyomtatott kotta] : felső szólam / Kodály Zoltán.";
        boolean result = propertyValidatorStrategy.validateProperty(property);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void validatePropertyCoverTypeBanned2() {
        String property = "55 kétszólamú énekgyakorlat [kotta] : felső szólam / Kodály Zoltán.";
        boolean result = propertyValidatorStrategy.validateProperty(property);
        assertThat(result).isEqualTo(false);
    }
}
