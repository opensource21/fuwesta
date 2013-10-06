package testdata.propties;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A basic-class
 * 
 */
@MappedSuperclass
public class BaseClass {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BaseClass.class);

    @Id
    private String test;

    /**
     * @return the test
     */
    public String getTest() {
        LOG.debug(test);
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(String test) {
        this.test = test;
    }

}
