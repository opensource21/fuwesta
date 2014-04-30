package de.ppi.fuwesta.spring.mvc.formatter;

import org.springframework.core.convert.converter.Converter;

/**
 * Converter which trims all Strings.
 * 
 */
public class StringTrimmerConverter implements Converter<String, String> {

    private final boolean emptyAsNull;

    /**
     * Initiates an object of type StringTrimmingConverter.
     * 
     * @param emptyAsNull true if an empty String should converted to null.
     */
    public StringTrimmerConverter(boolean emptyAsNull) {
        super();
        this.emptyAsNull = emptyAsNull;
    }

    @Override
    public String convert(String source) {
        if (source == null) {
            return null;
        }
        String trimmedString = source.trim();
        if (emptyAsNull && trimmedString.length() == 0) {
            return null;
        } else {
            return trimmedString;
        }
    }
}
