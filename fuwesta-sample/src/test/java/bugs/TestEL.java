package bugs;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Test to demonstrate https://jira.springsource.org/browse/SPR-10486.
 */
public class TestEL {

    /**
     * Test the EL.
     */
    @SuppressWarnings({ "boxing", "deprecation" })
    @Test
    public void testName() {
        // Arrange
        final SpelExpressionParser parser = new SpelExpressionParser();
        final StandardEvaluationContext context =
                new StandardEvaluationContext();

        final String spelExpression1 = "testDate.date";
        final String spelExpression2 = "class";
        final String spelExpression3 = "class.name";
        final String spelExpression4 = "name";

        final Date now = new Date();
        final String name = "Testname";

        final TestObject testObject = new TestObject();
        testObject.setTestDate(now);
        testObject.setName(name);

        final SpelExpression exp1 =
                (SpelExpression) parser.parseExpression(spelExpression1);
        final SpelExpression exp2 =
                (SpelExpression) parser.parseExpression(spelExpression2);
        final SpelExpression exp3 =
                (SpelExpression) parser.parseExpression(spelExpression3);
        final SpelExpression exp4 =
                (SpelExpression) parser.parseExpression(spelExpression4);
        // Act

        final Object value1 = exp1.getValue(context, testObject);

        final Object value2 = exp2.getValue(context, testObject);

        final Object value3 = exp3.getValue(context, testObject);

        Object value4 = null;
        try {
            value4 = exp4.getValue(context, testObject);
        } catch (SpelEvaluationException e) {
            Assert.assertEquals("EL1021E:(pos 0): A problem occurred whilst "
                    + "attempting to access the property 'name': "
                    + "'Unable to access property 'name' through getter'",
                    e.getMessage());
            // CSOFF: RegexpSinglelineJava
            System.err.println("Spring-Bug SPR-9017 happens!");
            // CSON: RegexpSinglelineJava
            value4 = name;
        }

        // Assert
        Assert.assertEquals(value1, now.getDate());
        Assert.assertEquals(value2, TestObject.class);
        Assert.assertEquals(value3, TestObject.class.getName());
        Assert.assertEquals(value4, name);

    }

    /**
     * The Class TestObject.
     */
    public static class TestObject {

        /** The test date. */
        private Date testDate = null;

        /** The name. */
        private String name = null;

        /**
         * Gets the name.
         * 
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name.
         * 
         * @param name the new name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets the test date.
         * 
         * @return the test date
         */
        public Date getTestDate() {
            return this.testDate;
        }

        /**
         * Sets the test date.
         * 
         * @param testDate the new test date
         */
        public void setTestDate(Date testDate) {
            this.testDate = testDate;
        }

    }

}
