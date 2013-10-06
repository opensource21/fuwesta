package de.ppi.fuwesta.thymeleaf.bootstrap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;
import org.thymeleaf.testing.templateengine.context.web.SpringWebProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;
import org.thymeleaf.testing.templateengine.report.ITestReporter;
import org.thymeleaf.testing.templateengine.testable.ITestResult;

import de.ppi.fuwesta.thymeleaf.bootstrap.BootstrapDialect;

/**
 * Test for {@link BootstrapDialect}.
 * 
 */
@RunWith(Parameterized.class)
public class BootstrapDialectThymeleafTest {

    /**
     * Comment for <code>THYMELEAF_BOOTSTRAP_TESTDIR</code>
     */
    private static final String THYMELEAF_BOOTSTRAP_TESTDIR =
            "classpath:/thymeleaf/bootstrap/**/*.thtest";

    private static final TestExecutor executor = new TestExecutor();

    private final File testSpec;

    /**
     * Configures {@link TestExecutor} and define the {@link IDialect}.
     */
    @BeforeClass
    public static void configTestExcecutor() {
        final List<IDialect> dialects = new ArrayList<>();
        dialects.add(new BootstrapDialect());
        dialects.add(new SpringStandardDialect());
        final SpringWebProcessingContextBuilder springPCBuilder =
                new SpringWebProcessingContextBuilder();
        springPCBuilder.setApplicationContextConfigLocation(null);
        executor.setProcessingContextBuilder(springPCBuilder);
        executor.setDialects(dialects);

    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> getFilenames() throws IOException {
        final PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();
        final List<Object[]> result = new ArrayList<>();
        final Resource[] testResources =
                resolver.getResources(THYMELEAF_BOOTSTRAP_TESTDIR);
        for (Resource file : testResources) {
            result.add(new Object[] { file.getFilename(), file.getFile() });
        }
        return result;
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        executor.reset();
    }

    public BootstrapDialectThymeleafTest(String name, File testSpec) {
        this.testSpec = testSpec;
    }

    @Test
    public void testField() {
        executor.execute("file://" + testSpec.getAbsolutePath());
        assertResultOK(executor.getReporter());
    }

    /**
     * @param reporter
     */
    private void assertResultOK(ITestReporter reporter) {
        for (String testName : reporter.getAllTestNames()) {
            final ITestResult result = reporter.getResultByTestName(testName);
            Assert.assertNull(result.getMessage());
        }
    }
}
