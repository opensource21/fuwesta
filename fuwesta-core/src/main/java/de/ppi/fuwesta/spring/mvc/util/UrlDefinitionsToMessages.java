package de.ppi.fuwesta.spring.mvc.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class puts definition about Urls to Message-Properties, there are the
 * following constraints:
 * <ul>
 * <li>Fieldname starts with "P_" it is a Parameter. It will added with *
 * {@link #paramsAsMessages(Class[])}</li>
 * 
 * <li>Fieldname starts with "PG_" it is define a Parameter-Group. It will added
 * with {@link #paramGroupAsMessages(Class[])}</li>
 * 
 * <li>All other fields will be added with {@link #urlsAsMessages(Class[])}</li>
 * </ul>
 * 
 * If your parameter isn't a String you should use {@link ParamFormat}, which
 * defines a parameter as a integer, but you can overwrite it.
 * 
 */
public class UrlDefinitionsToMessages {

    /**
     * Praefix for constants which define a parameter.
     */
    private static final String PRAEFIX_PARAMETER = "P_";

    /**
     * Praefix in messagesource-key for constants which define a parameter
     * group.
     */
    private static final String MESSAGE_SOURCE_PRAEFIX_PARAMETER = "par";

    /**
     * Praefix for constants which define a parameter group.
     */
    private static final String PRAEFIX_PARAMETER_GROUP = "PG_";

    /**
     * Praefix in messagesource-key for constants which define a parameter
     * group.
     */
    private static final String MESSAGE_SOURCE_PRAEFIX_PARAMETER_GROUP = "pg";

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UrlDefinitionsToMessages.class);

    /**
     * Formates the parameter as an int with no characters.
     */
    public static final String INTEGER = ",number,##";

    /**
     * Annotation which defines how a parameter should be formatted.
     * 
     */
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface ParamFormat {
        /**
         * The value defines the format for message-formatting. Default is
         * ",number,##".
         * 
         */
        String value() default INTEGER;
    }

    /** the classes with url-infos. */
    private final Class<?>[] classesWithUrlInfos;

    /**
     * the messages.
     */
    private final Properties messages = new Properties();

    /**
     * the format definition.
     */
    private final Map<String, String> formatDefinition = new HashMap<>();

    /**
     * Initiates an object of type UrlDefinitionsToMessages.
     * 
     * @param classes the classes with url-infos.
     */
    public UrlDefinitionsToMessages(Class<?>... classes) {
        classesWithUrlInfos = classes;
        fillFormatDefinitions(classesWithUrlInfos);
    }

    /**
     * Add all URL constants to a {@link Properties}.
     * 
     */
    public void addUrlsAsMessages() {
        addConstantInfosFromClass("url", classesWithUrlInfos);
    }

    /**
     * Add all URL constants to a {@link Properties}.
     * 
     */
    public void addUrlsAsMessagesWithNamedParameters() {
        addConstantInfosFromClass("nurl", classesWithUrlInfos);
    }

    /**
     * Add all Params constants to a {@link Properties}.
     * 
     */
    public void addParamsAsMessages() {
        addConstantInfosFromClass(MESSAGE_SOURCE_PRAEFIX_PARAMETER,
                classesWithUrlInfos);
    }

    /**
     * Add all Paramgroup constants to a {@link Properties} this means something
     * like "user_id = {1}, user_name={2}". This can be used in @{url()} in
     * Thymeleaf.
     * 
     */
    public void addParamGroupAsMessages() {
        addConstantInfosFromClass(MESSAGE_SOURCE_PRAEFIX_PARAMETER_GROUP,
                classesWithUrlInfos);
    }

    /**
     * @return the messages
     */
    public Properties getMessages() {
        return messages;
    }

    /**
     * @return the formatDefinition
     */
    Map<String, String> getFormatDefinition() {
        return formatDefinition;
    }

    /**
     * Added the information of formatting parametes to the given map.
     * 
     * @param classes classes which declares the parmaters.
     */
    private void fillFormatDefinitions(Class<?>[] classes) {
        for (Class<?> class1 : classes) {
            if (Modifier.isInterface(class1.getModifiers())) {
                Field[] fields = class1.getDeclaredFields();
                for (Field field : fields) {
                    try {
                        if (Modifier.isPublic(field.getModifiers())) {
                            if (field.getName().startsWith(PRAEFIX_PARAMETER)) {
                                final ParamFormat format =
                                        field.getAnnotation(ParamFormat.class);
                                final String paramName =
                                        field.get(null).toString();
                                if (formatDefinition.containsKey(paramName)) {
                                    LOG.warn("Parameter {} is defined more "
                                            + "than once.", paramName);
                                } else {
                                    formatDefinition.put(paramName, "");
                                }
                                if (format != null) {
                                    formatDefinition.put(paramName,
                                            format.value());
                                }
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        LOG.error(
                                "Error reading the field "
                                        + field.getDeclaringClass() + "."
                                        + field.getName(), e);
                    }
                }
                fillFormatDefinitions(class1.getDeclaredClasses());
            }

        }

    }

    /**
     * Add all class Constants to a {@link Properties}.
     * 
     * @param prefix the prefix for the key.
     * @param classes the list of classes.
     */
    private void addConstantInfosFromClass(String prefix, Class<?>[] classes) {
        for (Class<?> class1 : classes) {
            if (Modifier.isInterface(class1.getModifiers())) {
                Field[] fields = class1.getDeclaredFields();
                for (Field field : fields) {
                    if (Modifier.isPublic(field.getModifiers())) {
                        addFieldInformation(prefix, formatDefinition, field);
                    }
                }

                addConstantInfosFromClass(
                        prefix + "." + class1.getSimpleName(),
                        class1.getDeclaredClasses());

            }
        }
    }

    /**
     * Analyse the field and add the information to the properties.
     * 
     * @param prefix the prefix each key should get.
     * @param formatDefinition the definition for the format of parameters.
     * @param field the field which contains information.
     */
    private void addFieldInformation(String prefix,
            Map<String, String> formatDefinition, Field field) {
        try {
            if (prefix.startsWith("url")) {
                if (!field.getName().startsWith(PRAEFIX_PARAMETER)
                        && !field.getName().startsWith(PRAEFIX_PARAMETER_GROUP)) {
                    final String keyName =
                            createKey(prefix, field.getDeclaringClass()
                                    .getSimpleName(), field.getName());
                    final String urlValue =
                            createUrl(field.get(null).toString(),
                                    formatDefinition);
                    messages.put(keyName, urlValue);
                }
            } else if (prefix.startsWith("nurl")) {
                if (!field.getName().startsWith(PRAEFIX_PARAMETER)
                        && !field.getName().startsWith(PRAEFIX_PARAMETER_GROUP)) {
                    final String keyName =
                            createKey(prefix, field.getDeclaringClass()
                                    .getSimpleName(), field.getName());
                    final String urlValue =
                            createUrlWithNamedParams(
                                    field.get(null).toString(),
                                    formatDefinition);
                    messages.put(keyName, urlValue);
                }
            } else if (prefix.startsWith(MESSAGE_SOURCE_PRAEFIX_PARAMETER)) {
                if (field.getName().startsWith(PRAEFIX_PARAMETER)) {
                    final String keyName =
                            createKey(prefix, field.getDeclaringClass()
                                    .getSimpleName(), field.getName()
                                    .substring(2));
                    messages.put(keyName, field.get(null).toString());
                }
            } else if (prefix
                    .startsWith(MESSAGE_SOURCE_PRAEFIX_PARAMETER_GROUP)) {
                if (field.getName().startsWith(PRAEFIX_PARAMETER_GROUP)) {
                    final String keyName =
                            createKey(prefix, field.getDeclaringClass()
                                    .getSimpleName(), field.getName()
                                    .substring(3));
                    final String pgValue =
                            createParamGroup(field.get(null).toString(),
                                    formatDefinition);
                    messages.put(keyName, pgValue);
                }

            } else {
                throw new IllegalArgumentException("Invalid Prefix " + prefix);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.error("Error reading the field " + field.getDeclaringClass()
                    + "." + field.getName(), e);
        }
    }

    /**
     * Creates the URL from the constant as a message, i.e. named parameters
     * like {user_id} will be replaced by ${user_id}.
     * 
     * @param urlAsString the url.
     * @param formatDefinition the format definitions.
     * @return the URL as parameterized message.
     */
    private String createUrlWithNamedParams(String urlAsString,
            Map<String, String> formatDefinition) {
        final StringBuilder result = new StringBuilder(urlAsString.length());
        final StringTokenizer tokens = new StringTokenizer(urlAsString, "{}");
        boolean isVariable = (urlAsString.charAt(0) == '{');
        while (tokens.hasMoreTokens()) {
            final String key = tokens.nextToken();
            if (isVariable) {
                String format = formatDefinition.get(key);
                if (format == null) {
                    LOG.warn("In URL {} you use an undefined parameter {}",
                            urlAsString, key);
                    format = "";
                }
                result.append("$'{'").append(key).append("'}'");
            } else {
                result.append(key);
            }

            isVariable = !isVariable;
        }

        return result.toString();
    }

    /**
     * Creates the URL from the constant as a message, i.e. named parameters
     * like {user_id} will be replaced by {0}.
     * 
     * @param urlAsString the url.
     * @param formatDefinition the format definitions.
     * @return the URL as parameterized message.
     */
    private String createUrl(String urlAsString,
            Map<String, String> formatDefinition) {
        final StringBuilder result = new StringBuilder(urlAsString.length());
        final StringTokenizer tokens = new StringTokenizer(urlAsString, "{}");
        boolean isVariable = (urlAsString.charAt(0) == '{');
        int i = 0;
        while (tokens.hasMoreTokens()) {
            final String key = tokens.nextToken();
            if (isVariable) {
                String format = formatDefinition.get(key);
                if (format == null) {
                    LOG.warn("In URL {} you use an undefined parameter {}",
                            urlAsString, key);
                    format = "";
                }
                result.append('{').append(i).append(format).append('}');
                i++;
            } else {
                result.append(key);
            }

            isVariable = !isVariable;
        }

        return result.toString();
    }

    /**
     * Creates the Paramgroup from the constant as a message, i.e. paramaters
     * will be enriched with ={}
     * 
     * @param definitionOfParams the field value.
     * @param formatDefinition the format definition.
     * @return the paramgroup as parameterized message.
     */
    private String createParamGroup(String definitionOfParams,
            Map<String, String> formatDefinition) {
        final StringBuilder result =
                new StringBuilder(2 * definitionOfParams.length());
        final StringTokenizer tokens =
                new StringTokenizer(definitionOfParams, ",");
        int i = 0;
        while (tokens.hasMoreTokens()) {
            final String key = tokens.nextToken().trim();
            String format = formatDefinition.get(key);
            if (format == null) {
                LOG.warn("In Paramgroup {} you use an undefined parameter {}",
                        definitionOfParams, key);
                format = "";
            }
            result.append(key).append("={").append(i).append(format)
                    .append("},");
            i++;
        }

        return result.substring(0, result.length() - 1);
    }

    /**
     * Create a key name.
     * 
     * @param prefix the prefix for the key.
     * @param className the simple name of the declaring class.
     * @param fieldName the field name.
     * @return a message-key.
     */
    private String createKey(String prefix, String className, String fieldName) {
        return (prefix + "." + className + "." + fieldName).toLowerCase();
    }

}
