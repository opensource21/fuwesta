package de.ppi.fuwesta.spring.mvc.util;

/**
 * Helperclass which removes Regexps from Paramter definition. Class URLCleaner
 *
 */
public class URLCleaner {

    /**
     * Initiates an object of type URLCleaner.
     */
    private URLCleaner() {
        // Utility-Class
    }

    /**
     * Removes from an URL all regexp from path-variables.
     *
     * @param url the url
     * @return the url withoou regexps.
     */
    public static String removeRegexpFromUrl(String url) {
        return url.replaceAll(
                "\\{([A-Za-z0-9_-]+?):([^{}]+?\\{[0-9,]+?\\})*([^{}]+)*\\}",
                "{$1}");
    }

}
