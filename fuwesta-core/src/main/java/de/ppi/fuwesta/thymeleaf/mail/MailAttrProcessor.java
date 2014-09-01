package de.ppi.fuwesta.thymeleaf.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.util.Validate;

/**
 * Attribut-Processor for the mail-dialect.
 * 
 */
public class MailAttrProcessor extends AbstractAttrProcessor {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(MailAttrProcessor.class);

    /** The attribute name which should trigger this processor. */
    public static final String ATTRIBUTE_NAME = "to";

    /**
     * Attribute which contains the subject (optional parameter).
     */
    private static final String MAIL_SUBJECT = "mail:subject";

    /**
     * Attribute which contains the body (optional parameter).
     */
    private static final String MAIL_BODY = "mail:body";

    /**
     * The precedence of the Processor. Should by high to make sure that remove
     * or if - conditions are run first.
     **/
    public static final int PRECEDENCE = 10000;

    /**
     * Initiates an object of type MailAttrProcessor.
     */
    public MailAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ProcessorResult processAttribute(Arguments arguments,
            Element element, String attributeName) {
        if (!"a".equals(element.getNormalizedName())) {
            return ProcessorResult.OK;
        }
        final String to = extractAttribute(element, arguments, attributeName);
        final String cc = extractAttribute(element, arguments, "mail:cc");
        final String bcc = extractAttribute(element, arguments, "mail:bcc");
        final String subject =
                extractAttribute(element, arguments, MAIL_SUBJECT);
        final String body = extractAttribute(element, arguments, MAIL_BODY);

        final String mailToLink = createMailToLink(to, bcc, cc, subject, body);

        element.setAttribute("href", mailToLink);
        return ProcessorResult.OK;
    }

    /**
     * Creates the mailto-link.
     * 
     * @param to the to-address
     * @param bcc optional a bcc
     * @param cc optional a cc
     * @param subject optional a subject
     * @param body optional a body
     * @return the link.
     */
    private String createMailToLink(String to, String bcc, String cc,
            String subject, String body) {
        Validate.notNull(to, "You must define a to-address");
        final StringBuilder urlBuilder = new StringBuilder("mailto:");
        addEncodedValue(urlBuilder, "to", to);
        if (bcc != null || cc != null || subject != null || body != null) {
            urlBuilder.append('?');
        }
        addEncodedValue(urlBuilder, "bcc", bcc);
        addEncodedValue(urlBuilder, "cc", cc);
        addEncodedValue(urlBuilder, "subject", subject);
        if (body != null) {
            addEncodedValue(urlBuilder, "body", body.replace("$NL$", "\r\n"));
        }

        return urlBuilder.toString();
    }

    private void addEncodedValue(final StringBuilder urlBuilder,
            final String name, String value) {
        if (value != null) {
            String encodedValue;
            if (!"to".equals(name) && !urlBuilder.toString().endsWith("?")) {
                urlBuilder.append('&');
            }
            try {
                encodedValue =
                        URLEncoder.encode(value, "utf-8").replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                LOG.error("UTF-8 encoding not supported during encoding "
                        + value, e);
                encodedValue = value;
            }
            if (!"to".equals(name)) {
                urlBuilder.append(name).append('=');
            }
            urlBuilder.append(encodedValue);
        }
    }

    private String extractAttribute(Element element, Arguments arguments,
            String attributeName) {
        final String toExpr = element.getAttributeValue(attributeName);
        if (toExpr == null) {
            return null;
        }
        final String to;
        if (toExpr.contains("#") || toExpr.contains("$")) {
            to = parse(arguments, toExpr);
        } else {
            to = toExpr;
        }
        element.removeAttribute(attributeName);
        return to;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }

    private String parse(final Arguments arguments, String input) {
        final Configuration configuration = arguments.getConfiguration();

        final IStandardExpressionParser parser =
                StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression expression =
                parser.parseExpression(configuration, arguments, input);

        return (String) expression.execute(configuration, arguments);
    }

}
