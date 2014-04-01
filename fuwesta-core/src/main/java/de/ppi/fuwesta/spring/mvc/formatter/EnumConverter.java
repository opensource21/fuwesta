package de.ppi.fuwesta.spring.mvc.formatter;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;

/**
 * Class EnumConversionService
 * 
 */
public class EnumConverter implements Converter<Enum<?>, String> {

    private MessageSource messageSource;

    /**
     * Initiates an object of type EnumConverter.
     * 
     * @param messageSource
     */
    public EnumConverter(MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convert(@SuppressWarnings("rawtypes") Enum source) {
        if (source == null) {
            return "";
        }
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(source.getClass().getSimpleName() + "."
                + source, null, source.toString(), locale);
    }

}
