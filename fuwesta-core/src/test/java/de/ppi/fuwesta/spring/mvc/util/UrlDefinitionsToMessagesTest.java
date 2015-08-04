package de.ppi.fuwesta.spring.mvc.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages;
import de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages.ParamFormat;

/**
 * Test for {@link UrlDefinitionsToMessages}.
 *
 */
public class UrlDefinitionsToMessagesTest {

    /**
     * How many parameters are defined.
     */
    private static final int NUMBER_OF_PARAMTER = 4;

    /**
     * How many URLs defined.
     */
    private static final int NUMBER_OF_URLS = 5;

    /**
     * Test class.
     *
     */
    @SuppressWarnings("unused")
    private interface TestClass {
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
         * Parametername for the user-name.
         */
        String P_USERNAME = "user_name";

        /**
         * Parametername with strange definition.
         */
        @ParamFormat(",xyz")
        String P_STRANGE_FORMAT = "strange_format";

        /**
         * P_USERID und P_USERNAME as a group.
         */
        String PG_SOME_PARAMS = P_USERID + ", " + P_USERNAME;

        /**
         * Edit-User-Url.
         */
        String EDIT = HOME + "/edit/{" + P_USERID + ":[A-Z]{1,2}}";

        /**
         * Show-User-Url.
         */
        String SHOW = HOME + "/show/{" + P_USERNAME + "}";

        /**
         * Show-User-Url.
         */
        String STRANGE = HOME + "/show/{" + P_USERNAME + ":.*}/{"
                + P_STRANGE_FORMAT + "}";

        /** List User-URL. */
        String LIST = HOME + "/list";

        /**
         * Some test method.
         */
        void aMethod();

        /**
         * Inner class as interface.
         *
         */
        interface InnerInterface {

            /**
             * Dummy-Parameter.
             */
            @ParamFormat(UrlDefinitionsToMessages.INTEGER)
            String P_DUMMY = "dummy";

            /**
             * Some test method.
             */
            void aMethod();
        }

        /**
         * Inner class as class.
         *
         */
        class InnerClass {

            /**
             * Dummy-Parameter.
             */
            @ParamFormat(UrlDefinitionsToMessages.INTEGER)
            public static final String P_DUMMY2 = "dummy2";

            /**
             * Some test method.
             */
            void aMethod() {
                // Test
            };

        }

    }

    /**
     * Classes which are use in test.
     */
    private final Class<?>[] classes = { TestClass.class };

    /**
     * Testobject.
     */
    private UrlDefinitionsToMessages testee = new UrlDefinitionsToMessages(
            classes);

    /**
     * Test method for {@link
     * de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages#urlsAsMessages(
     * java.lang. Class<?>[])}.
     */
    @Test
    public void testUrlsAsMessages() {
        // Arrange
        // Act
        testee.addUrlsAsMessagesWithPositionedParameters();
        final Properties messages = testee.getMessages();
        // Assert
        assertThat(messages).hasSize(NUMBER_OF_URLS);
        assertThat(messages.getProperty("purl.testclass.home")).isEqualTo(
                TestClass.HOME);
        assertThat(messages.getProperty("purl.testclass.list")).isEqualTo(
                TestClass.LIST);
        assertThat(messages.getProperty("purl.testclass.edit")).isEqualTo(
                "/user/edit/{0,number,##}");
        assertThat(messages.getProperty("purl.testclass.strange")).isEqualTo(
                "/user/show/{0}/{1,xyz}");
        assertThat(messages.getProperty("purl.testclass.show")).isEqualTo(
                "/user/show/{0}");
    }

    /**
     * Test method for {@link
     * de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages#urlsAsMessages(
     * java.lang. Class<?>[])}.
     */
    @Test
    public void testUrlsAsMessagesWithParameters() {
        // Arrange
        // Act
        testee.addUrlsAsMessagesWithNamedParameters();
        final Properties messages = testee.getMessages();
        // Assert
        assertThat(messages).hasSize(NUMBER_OF_URLS);
        assertThat(messages.getProperty("nurl.testclass.home")).isEqualTo(
                TestClass.HOME);
        assertThat(messages.getProperty("nurl.testclass.list")).isEqualTo(
                TestClass.LIST);
        assertThat(messages.getProperty("nurl.testclass.edit")).isEqualTo(
                "/user/edit/$'{'user_idn'}'");
        assertThat(messages.getProperty("nurl.testclass.strange")).isEqualTo(
                "/user/show/$'{'user_name'}'/$'{'strange_format'}'");
        assertThat(messages.getProperty("nurl.testclass.show")).isEqualTo(
                "/user/show/$'{'user_name'}'");
    }

    /**
     * Testet die Formatierung von URLs.
     */
    @Test
    public void testFormattedUrl() {
        // Additional check that the formating will be correct.
        final ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        final Long longValue = Long.valueOf(1100L);

        testee.addUrlsAsMessagesWithPositionedParameters();

        messageSource.setCommonMessages(testee.getMessages());
        assertThat(
                messageSource.getMessage("purl.testclass.edit",
                        new Object[] { longValue }, Locale.CANADA)).isEqualTo(
                TestClass.EDIT.replaceAll("\\{" + TestClass.P_USERID
                        + ":\\[A-Z\\]\\{1,2\\}\\}", "1100"));
        assertThat(
                messageSource.getMessage("purl.testclass.show", new Object[] {
                        longValue, longValue }, Locale.CANADA)).isEqualTo(
                TestClass.SHOW.replaceAll("\\{" + TestClass.P_USERNAME + "\\}",
                        "1,100"));
    }

    /**
     * Test method for {@link
     * de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages#paramsAsMessages(
     * java. lang.Class<?>[])}.
     */
    @Test
    public void testParamsAsMessages() {
        // Arrange
        // Act
        testee.addParamsAsMessages();
        final Properties messages = testee.getMessages();
        // Assert
        assertThat(messages.getProperty("par.testclass.innerinterface.dummy"))
                .isEqualTo(TestClass.InnerInterface.P_DUMMY);
        assertThat(messages.getProperty("par.testclass.userid")).isEqualTo(
                TestClass.P_USERID);
        assertThat(messages.getProperty("par.testclass.username")).isEqualTo(
                TestClass.P_USERNAME);
        assertThat(messages.getProperty("par.testclass.strange_format"))
                .isEqualTo(TestClass.P_STRANGE_FORMAT);
        assertThat(messages).hasSize(NUMBER_OF_PARAMTER);
    }

    /**
     * Test method for {@link
     * de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages#
     * paramGroupAsMessages(java .lang.Class<?>[])}.
     */
    @Test
    public void testParamGroupAsMessages() {
        // Arrange
        // Act
        testee.addParamGroupAsMessages();
        final Properties messages = testee.getMessages();
        // Assert
        assertThat(messages).hasSize(1);
        assertThat(messages.getProperty("pg.testclass.some_params")).isEqualTo(
                TestClass.P_USERID + "={0,number,##}," + TestClass.P_USERNAME
                        + "={1}");

    }

    /**
     * Test method for {@link
     * de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages#
     * fillFormatDefinitions( java.lang.Class<?>[], java.util.Map)}.
     */
    @Test
    public void testFillFormatDefinitions() {
        // Arrange
        // Act
        final Map<String, String> formatDefinition =
                testee.getFormatDefinition();

        // Assert
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put(TestClass.P_USERID, UrlDefinitionsToMessages.INTEGER);
        expectedMap.put(TestClass.InnerInterface.P_DUMMY,
                UrlDefinitionsToMessages.INTEGER);
        expectedMap.put(TestClass.P_STRANGE_FORMAT, ",xyz");
        expectedMap.put(TestClass.P_USERNAME, "");
        assertThat(formatDefinition).isEqualTo(expectedMap);
    }
}
