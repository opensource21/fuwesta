package testdata.propties;

import javax.persistence.Entity;

/**
 * A sub-class.
 * 
 */
@Entity
public class SubClass extends BaseClass {

    private String subTest;

    /**
     * @return the subTest
     */
    public String getSubTest() {
        return subTest;
    }

    /**
     * @param subTest the subTest to set
     */
    public void setSubTest(String subTest) {
        this.subTest = subTest;
    }

}
