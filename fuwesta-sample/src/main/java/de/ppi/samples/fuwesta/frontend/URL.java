package de.ppi.samples.fuwesta.frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.ppi.fuwesta.spring.mvc.util.URLCleaner;
import de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages.ParamFormat;

/**
 * List of all URLs.
 *
 */
// CSOFF: InterfaceIsType You must give the Annotations Strings and can't use
// Enums.
public final class URL {

    /**
     * Prefix für Redirect-Urls.
     */
    private static final String REDIRECT_PREFIX = "redirect:";

    /**
     * Map which stores the UriComponents.
     */
    private static final Map<String, UriComponents> URI_MAP =
            new ConcurrentHashMap<String, UriComponents>();

    /**
     * Home-Url.
     */
    public static final String HOME = "/home";

    /**
     *
     * URLs for authentification.
     *
     */
    public interface Auth {

        /**
         * Login-Url.
         */
        String LOGIN = "/login";

    }

    /**
     * Definition for paginating.
     *
     */
    public interface Page {

        /**
         * Parameter for the number of the page.
         */
        @ParamFormat
        String P_NUMBER = "page";

        /**
         * Parameter for the size of the page.
         */
        @ParamFormat
        String P_SIZE = "size";

        /**
         * Parameter-Group for number and size of the page.
         */
        String PG_NUMBER_SIZE = P_NUMBER + ", " + P_SIZE;
    }

    /**
     * All URLS for the User.
     *
     */
    public interface User {
        /**
         * User-Url.
         */
        String HOME = "/user";

        /**
         * Parametername for the user-id.
         */
        @ParamFormat
        String P_USERID = "user_idn";

        /**
         * Edit-User-Url.
         */
        String EDIT = HOME + "/edit/{" + P_USERID + "}";

        /**
         * Show-User-Url.
         */
        String SHOW = HOME + "/show/{" + P_USERID + "}";

        /**
         * Delete-User-Url.
         */
        String DELETE = HOME + "/delete/{" + P_USERID + "}";

        /** List User-URL. */
        String LIST = HOME + "/list";

        /** Create User-URL. */
        String CREATE = HOME + "/create";
    }

    /**
     * All URLS for the {@link de.ppi.fuwesta.samples.springmvc.model.Post}.
     *
     */
    public interface Post {
        /**
         * Post-Url.
         */
        String HOME = "/post";

        /**
         * Parameter for id of the post.
         */
        @ParamFormat
        String P_POSTID = "post_idn";

        /**
         * Edit-Post-Url.
         */
        String EDIT = HOME + "/edit/{" + P_POSTID + "}";

        /**
         * Partial Edit-Post-Url.
         */
        String PARTIALEDIT = HOME + "/partialedit/{" + P_POSTID + "}";

        /**
         * Show-Post-Url.
         */
        String SHOW = HOME + "/show/{" + P_POSTID + "}";

        /**
         * Delete-Post-Url.
         */
        String DELETE = HOME + "/delete/{" + P_POSTID + "}";

        /** List Post-URL. */
        String LIST = HOME + "/list";

        /** Create Post-URL. */
        String CREATE = HOME + "/create";
    }

    /**
     * All URLS for the {@link de.ppi.fuwesta.samples.springmvc.model.Tag}.
     *
     */
    public interface Tag {
        /**
         * Tag-Url.
         */
        String HOME = "/tag";

        /**
         * Parameter for the if of the tag.
         */
        @ParamFormat
        String P_TAGID = "tag_idn";

        /**
         * Edit-Tag-Url.
         */
        String EDIT = HOME + "/edit/{" + P_TAGID + "}";

        /**
         * Show-Tag-Url.
         */
        String SHOW = HOME + "/show/{" + P_TAGID + "}";

        /**
         * Delete-Tag-Url.
         */
        String DELETE = HOME + "/delete/{" + P_TAGID + "}";

        /** List Tag-URL. */
        String LIST = HOME + "/list";

        /** Create Tag-URL. */
        String CREATE = HOME + "/create";
    }

    /**
     * Replace the parameter in the URL with the given value.
     *
     * @param url the URL.
     * @param parameter the parameter
     * @return the URL with parameters filled in
     */
    public static String filledURL(String url, Object parameter) {
        final UriComponents uricomponent = getUriComponent(url);
        return uricomponent.expand(parameter).encode().toUriString();
    }

    /**
     * Encode the given URL.
     *
     * @param url the URL.
     * @return the URL with parameters filled in
     */
    public static String filledURL(String url) {
        final UriComponents uricomponent = getUriComponent(url);
        return uricomponent.expand().encode().toUriString();
    }

    /**
     * Replace all parameters in the URL with the given values.
     *
     * @param url the URL.
     * @param keyValuePairs the parameters as pair of name and value.
     * @return the URL with parameters filled in
     */
    public static String filledURLWithNamedParams(String url,
            Object... keyValuePairs) {
        if ((keyValuePairs == null) || (keyValuePairs.length == 0)) {
            return url;
        }
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "The array has to be of an even size - size is "
                            + keyValuePairs.length);
        }

        final Map<String, Object> values = new HashMap<String, Object>();

        for (int x = 0; x < keyValuePairs.length; x += 2) {
            values.put((String) keyValuePairs[x], keyValuePairs[x + 1]);
        }

        final UriComponents uricomponent = getUriComponent(url);
        return uricomponent.expand(values).encode().toUriString();
    }

    /**
     * Give a {@link UriComponents} to the URL.
     *
     * @param url a url as String
     * @return the {@link UriComponents}
     */
    private static UriComponents getUriComponent(String url) {
        if (!URI_MAP.containsKey(url)) {
            URI_MAP.put(
                    url,
                    UriComponentsBuilder.fromUriString(
                            URLCleaner.removeRegexpFromUrl(url)).build());
        }

        return URI_MAP.get(url);
    }

    /**
     * Replace all parameters in the URL with the given values.
     *
     * @param url the URL.
     * @param parameters the parameters
     * @return the URL with parameters filled in
     */
    public static String filledURL(String url, Map<String, ?> parameters) {
        if ((parameters == null) || (parameters.size() == 0)) {
            return url;
        }

        final UriComponents uricomponent = getUriComponent(url);
        return uricomponent.expand(parameters).encode().toUriString();
    }

    /**
     * Replace all parameters in the URL with the given values and make a
     * redirect.
     *
     * @param url the URL.
     * @param parameter the parameter
     * @return the redirect URL with parameters filled in
     */
    public static String redirect(String url, Object parameter) {
        return REDIRECT_PREFIX + filledURL(url, parameter);
    }

    /**
     * Replace all parameters in the URL with the given values and make a
     * redirect.
     *
     * @param url the URL.
     * @return the redirect URL with parameters filled in
     */
    public static String redirect(String url) {
        return REDIRECT_PREFIX + filledURL(url);
    }

    /**
     * Replace all parameters in the URL with the given values and make a
     * redirect.
     *
     * @param url the URL.
     * @param keyValuePairs the parameters as pair of name and value
     * @return the redirect URL with parameters filled in
     */
    public static String redirectWithNamedParams(String url,
            Object... keyValuePairs) {
        return REDIRECT_PREFIX + filledURLWithNamedParams(url, keyValuePairs);
    }

    /**
     * Replace all parameters in the URL with the given values and make a
     * redirect.
     *
     * @param url the URL.
     * @param namedParameters the parameters
     * @return the redirect URL with parameters filled in.
     */
    public static String redirect(String url,
            Map<String, String> namedParameters) {
        return REDIRECT_PREFIX + filledURL(url, namedParameters);
    }

    /**
     * Initiates an object of type URL.
     */
    private URL() {
        // UTILITY-CONSTRUCTOR
    };

}
// CSON: InterfaceIsType
