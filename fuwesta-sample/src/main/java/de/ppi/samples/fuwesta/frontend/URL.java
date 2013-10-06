package de.ppi.samples.fuwesta.frontend;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages.ParamFormat;

/**
 * List of all URLs.
 * 
 */
// CSOFF: InterfaceIsType You must give the Annotations Strings and can't use
// Enums.
public final class URL {

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
     * Definition for paginating.
     * 
     */
    public interface Page {

        /**
         * Parameter for the number of the page.
         */
        @ParamFormat
        String P_NUMBER = "page.page";

        /**
         * Parameter for the size of the page.
         */
        @ParamFormat
        String P_SIZE = "page.size";

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
     * Replace all parameters in the URL with the given values.
     * 
     * @param url the URL.
     * @param parameters the parameters
     * @return the URL with parameters filled in
     */
    public static String filledURL(String url, Object... parameters) {
        if (parameters == null || parameters.length == 0) {
            return url;
        }
        final UriComponents uricomponent = getUriComponent(url);
        return uricomponent.expand(parameters).encode().toUriString();
    }

    /**
     * Give a {@link UriComponents} to the URL.
     * 
     * @param url a url as String
     * @return the {@link UriComponents}
     */
    private static UriComponents getUriComponent(String url) {
        if (!URI_MAP.containsKey(url)) {
            URI_MAP.put(url, UriComponentsBuilder.fromUriString(url).build());
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
    public static String filledURL(String url, Map<String, String> parameters) {
        if (parameters == null || parameters.size() == 0) {
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
     * @param parameters the parameters
     * @return the redirect URL with parameters filled in
     */
    public static String redirect(String url, Object... parameters) {
        return "redirect:" + filledURL(url, parameters);
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
        return "redirect:" + filledURL(url, namedParameters);
    }

    /**
     * Initiates an object of type URL.
     */
    private URL() {
        // UTILITY-CONSTRUCTOR
    };

}
// CSON: InterfaceIsType
