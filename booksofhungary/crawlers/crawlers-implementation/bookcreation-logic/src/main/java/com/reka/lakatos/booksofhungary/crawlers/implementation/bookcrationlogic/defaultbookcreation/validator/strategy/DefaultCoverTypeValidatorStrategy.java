package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.strategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.PropertyValidatorStrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultCoverTypeValidatorStrategy implements PropertyValidatorStrategy {

    private static final String COVER_TYPE_REGEX_IN_ISBN_FIELD = "(?![\\[])[^\\[]*?(?=[\\]])";
    private static final Set<String> BANNED= new HashSet<>(Arrays.asList("kotta", "nyomtatott kotta"));

    @Override
    public boolean validateProperty(String property) {
        return !isBannedCoverType(property);
    }

    private boolean isBannedCoverType(String property) {
        Matcher matcher = Pattern.compile(COVER_TYPE_REGEX_IN_ISBN_FIELD).matcher(property);
        while (matcher.find()) {
            String result = matcher.group().trim();
            if (!result.isBlank() && !BANNED.contains(result)) {
                return false;
            }
        }
        return true;
    }
}
