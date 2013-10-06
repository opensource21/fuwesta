package de.ppi.samples.fuwesta.frontend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test the Class {@link URL}.
 * 
 */
public class URLTest {

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.URL#redirect(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testRedirectStringStringArray() {
        // Arrange
        final String templateString = "The {animal} jumped over the {target}.";

        // ACT
        final String result =
                URL.redirect(templateString, "quick brown foxäöü", "lazy dog");
        // ASSERT
        assertThat(result)
                .isEqualTo(
                        "redirect:The%20quick%20brown%20fox%C3%A4%C3%B6%C3%BC%20jumped%20over%20the%20lazy%20dog.");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.URL#filledURL(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testFilledURLStringStringArray() {
        // Arrange
        final String templateString = "The {animal} jumped over the {target}.";

        // ACT
        final String result =
                URL.filledURL(templateString, "quick brown fox", "lazy dog");
        // ASSERT
        assertThat(result)
                .isEqualTo(
                        "The%20quick%20brown%20fox%20jumped%20over%20the%20lazy%20dog.");

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.URL#filledURL(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testFilledURLStringStringArrayWithUnusualString() {
        // Arrange
        final String templateString = "{animal} {target}.";

        // ACT
        final String result =
                URL.filledURL(templateString, "quick brown fox", "lazy dog");
        // ASSERT
        assertThat(result).isEqualTo("quick%20brown%20fox%20lazy%20dog.");

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.URL#filledURL(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testFilledURLStringStringArrayWithEmptyArray() {
        // Arrange
        final String templateString = "quick brown fox lazy dog.";

        // ACT
        final String result = URL.filledURL(templateString);
        // ASSERT
        assertThat(result).isEqualTo("quick brown fox lazy dog.");

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.URL#filledURL(java.lang.String, java.lang.String[])}
     * .
     */
    @Test
    public void testFilledURLStringStringArrayWithrealUrlAndSpecialCharacters() {
        // Arrange
        final String templateString =
                "http://localhost:8080/spring-mvc-sample/example/{name}";

        // ACT
        final String result = URL.filledURL(templateString, "äöü");
        // ASSERT
        assertThat(result)
                .isEqualTo(
                        "http://localhost:8080/spring-mvc-sample/example/%C3%A4%C3%B6%C3%BC");

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.URL#filledURL(java.lang.String, java.util.Map)}
     * .
     */
    @Test
    public void testFilledURLStringMapOfStringString() {
        // Arrange
        final Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("animal", "quick brown fox");
        valuesMap.put("target", "lazy dog");
        valuesMap.put("notused", "notused");
        final String templateString = "The {animal} jumped over the {target}.";

        // ACT
        final String result = URL.filledURL(templateString, valuesMap);
        // ASSERT
        assertThat(result)
                .isEqualTo(
                        "The%20quick%20brown%20fox%20jumped%20over%20the%20lazy%20dog.");

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.URL#redirect(java.lang.String, java.util.Map)}
     * .
     */
    @Test
    public void testRedirectStringMapOfStringString() {
        // Arrange
        final Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("animal", "quick brown fox");
        valuesMap.put("target", "lazy dog");
        final String templateString = "The {animal} jumped over the {target}.";

        // ACT
        final String result = URL.redirect(templateString, valuesMap);
        // ASSERT
        assertThat(result)
                .isEqualTo(
                        "redirect:The%20quick%20brown%20fox%20jumped%20over%20the%20lazy%20dog.");

    }

}
