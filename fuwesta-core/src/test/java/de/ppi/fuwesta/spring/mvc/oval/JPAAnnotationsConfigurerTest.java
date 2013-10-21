/*******************************************************************************
 * Portions created by Sebastian Thomschke are copyright (c) 2005-2010 Sebastian
 * Thomschke.
 *
 * All Rights Reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Sebastian Thomschke - initial implementation.
 * Niels  - make some changes
 *******************************************************************************/
package de.ppi.fuwesta.spring.mvc.oval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sebastian Thomschke
 * 
 */
public class JPAAnnotationsConfigurerTest {
    @Entity
    protected static class TestEntity {
        // -> @NotNull
        @Basic(optional = false)
        // -> @MaxLength(4)
        @Column(length = 4)
        public String code;
        public String description;
        // -> @NotNull & @AssertValid
        @ManyToOne(optional = false)
        public TestEntity ref1;
        // -> @AssertValid
        @OneToOne(optional = true)
        public TestEntity ref2;
        // -> @AssertValid
        @OneToMany
        public Collection<TestEntity> refs;

        // -> @NotNull
        @Column(nullable = false)
        public String getDescription() {
            return description;
        }
    }

    @Test
    public void testJPAAnnotationsConfigurerAssertValid() {
        testJPAAnnotationsConfigurer(true);
    }

    @Test
    public void testJPAAnnotationsConfigurerAssertNotValid() {
        testJPAAnnotationsConfigurer(false);
    }

    /**
     * @param addAssertValid
     */
    private void testJPAAnnotationsConfigurer(final boolean addAssertValid) {
        final Validator v =
                new Validator(new JPAAnnotationsConfigurer(addAssertValid));
        List<ConstraintViolation> violations;
        TestEntity entity;
        {
            entity = new TestEntity();
            violations = v.validate(entity);
            // code is null
            // description is null
            // ref1 is null
            Assert.assertEquals(3, violations.size());
            Assert.assertNull(violations.get(0).getInvalidValue());
            Assert.assertNull(violations.get(1).getInvalidValue());
            Assert.assertNull(violations.get(2).getInvalidValue());
        }
        {
            entity.code = "";
            entity.description = "";
            entity.ref1 = new TestEntity();
            violations = v.validate(entity);
            // ref1 is invalid
            if (addAssertValid) {
                Assert.assertEquals(1, violations.size());
            } else {
                Assert.assertEquals(0, violations.size());
            }
        }
        {
            entity.ref1.code = "";
            entity.ref1.description = "";
            entity.ref1.ref1 = entity;
            violations = v.validate(entity);
            Assert.assertEquals(0, violations.size());
        }
        {
            entity.ref2 = new TestEntity();
            violations = v.validate(entity);
            // ref2 is invalid
            if (addAssertValid) {
                Assert.assertEquals(1, violations.size());
            } else {
                Assert.assertEquals(0, violations.size());
            }
        }
        {
            entity.ref2.code = "";
            entity.ref2.description = "";
            entity.ref2.ref1 = entity;
            violations = v.validate(entity);
            Assert.assertEquals(0, violations.size());
        }
        // Column length test
        {
            entity.code = "12345";
            violations = v.validate(entity);
            // code is too long
            Assert.assertEquals(1, violations.size());
            entity.code = "";
        }
        // OneToMany test
        {
            entity.refs = new ArrayList<TestEntity>();
            TestEntity d = new TestEntity();
            entity.refs.add(d);
            violations = v.validate(entity);
            if (addAssertValid) {
                Assert.assertEquals(1, violations.size());
            } else {
                Assert.assertEquals(0, violations.size());
            }
            d.code = "";
            d.description = "";
            d.ref1 = entity;
            violations = v.validate(entity);
            Assert.assertEquals(0, violations.size());
        }
    }

}
