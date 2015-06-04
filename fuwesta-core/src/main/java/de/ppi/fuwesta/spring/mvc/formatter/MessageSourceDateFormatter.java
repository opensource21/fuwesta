package de.ppi.fuwesta.spring.mvc.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

/**
 * Creates a date-formatter, where the format is defined in a message-source.
 * This way we can get easily the format for JS-Purposes.
 *
 */
public class MessageSourceDateFormatter implements Formatter<Date> {

    @Resource
    private MessageSource messageSource;

    private final String messageKey;

    /**
     * Initiates an object of type MessageSourceDateFormatter.
     *
     * @param messageKey the key in the message-sources.
     */
    public MessageSourceDateFormatter(String messageKey) {
        super();
        this.messageKey = messageKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String print(Date object, Locale locale) {
        final SimpleDateFormat format = getDateFormat(locale);
        return format.format(object);
    }

    private SimpleDateFormat getDateFormat(Locale locale) {
        final String pattern =
                messageSource
                        .getMessage(messageKey, null, "yyyy-MM-dd", locale);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        final SimpleDateFormat format = getDateFormat(locale);
        return format.parse(text);
    }

}
