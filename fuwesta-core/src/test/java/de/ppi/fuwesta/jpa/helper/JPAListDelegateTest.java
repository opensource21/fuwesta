package de.ppi.fuwesta.jpa.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.ppi.fuwesta.jpa.helper.JPAList;

/**
 * Test the delegating methods from {@link JPAList}.
 *
 */
@SuppressWarnings({ "boxing", "unchecked" })
public class JPAListDelegateTest {

    /**
     * The delegate list as a mock.
     */
    @Mock
    private List<String> delegateMock;

    /**
     * The testobject.
     */
    private JPAList<String, String> testee;

    /**
     * Initialize the test.
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testee = new JPAList<String, String>(delegateMock, new String()) {

            @Override
            public void add(String entity, String associatedEntity) {
                throw new RuntimeException("Not implemented in Test");
            }

            @Override
            public void remove(String entity, String associatedEntity) {
            	throw new RuntimeException("Not implemented in Test");
            }

        };
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#size()}.
     */

    @Test
    public void testSize() {
        // Arrange
        final int result = 2;
        when(delegateMock.size()).thenReturn(result);
        // Act
        final int testResult = testee.size();
        // Assert
        assertThat(testResult).isEqualTo(result);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#isEmpty()}.
     */
    @Test
    public void testIsEmpty() {
        // Arrange
        final boolean result = true;
        when(delegateMock.isEmpty()).thenReturn(result);
        // Act
        final boolean testResult = testee.isEmpty();
        // Assert
        assertThat(testResult).isEqualTo(result);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#contains(java.lang.Object)}.
     */
    @Test
    public void testContains() {
        // Arrange
        final boolean result = true;
        final Object obj = new Object();
        when(delegateMock.contains(obj)).thenReturn(result);
        // Act
        final boolean testResult = testee.contains(obj);
        // Assert
        assertThat(testResult).isEqualTo(result);
        verify(delegateMock).contains(obj);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#iterator()}.
     */

    @Test
    public void testIterator() {
        // Arrange
        final Iterator<String> result = mock(Iterator.class);
        when(delegateMock.iterator()).thenReturn(result);
        // Act
        final Iterator<String> testResult = testee.iterator();
        // Assert
        Assert.assertEquals(result, testResult);
        // See https://github.com/alexruiz/fest-assert-1.x/issues/2
        // assertThat(testResult).isEqualTo(result);

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#toArray()}.
     */
    @Test
    public void testToArray() {
        // Arrange
        final Object[] result = new Object[2];
        when(delegateMock.toArray()).thenReturn(result);
        // Act
        final Object[] testResult = testee.toArray();
        // Assert
        assertThat(testResult).isSameAs(result);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#toArray(T[])}.
     */
    @Test
    public void testToArrayTArray() {
        // Arrange
        final Object[] result = new Object[2];
        when(delegateMock.toArray(result)).thenReturn(result);
        // Act
        final Object[] testResult = testee.toArray(result);
        // Assert
        assertThat(testResult).isSameAs(result);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#containsAll(java.util.Collection)}.
     */
    @Test
    public void testContainsAll() {
        // Arrange
        final boolean result = true;
        final Collection<String> testData = mock(Collection.class);
        when(delegateMock.containsAll(testData)).thenReturn(result);
        // Act
        final boolean testResult = testee.containsAll(testData);
        // Assert
        assertThat(testResult).isSameAs(result);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#get(int)}.
     */
    @Test
    public void testGet() {
        // Arrange
        final String result = "Foo";
        final int testData = 5;
        when(delegateMock.get(testData)).thenReturn(result);
        // Act
        final String testResult = testee.get(testData);
        // Assert
        assertThat(testResult).isSameAs(result);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#indexOf(java.lang.Object)}.
     */
    @Test
    public void testIndexOf() {
        // Arrange
        final int result = 5;
        final String testData = "Foo";
        when(delegateMock.indexOf(testData)).thenReturn(result);
        // Act
        final int testResult = testee.indexOf(testData);
        // Assert
        assertThat(testResult).isSameAs(result);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#lastIndexOf(java.lang.Object)}.
     */
    @Test
    public void testLastIndexOf() {
        // Arrange
        final int result = 5;
        final String testData = "Foo";
        when(delegateMock.lastIndexOf(testData)).thenReturn(result);
        // Act
        final int testResult = testee.lastIndexOf(testData);
        // Assert
        assertThat(testResult).isSameAs(result);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#listIterator()}.
     */

    @Test
    public void testListIterator() {
        // Arrange
        final ListIterator<String> result = mock(ListIterator.class);
        when(delegateMock.listIterator()).thenReturn(result);
        // Act
        final ListIterator<String> testResult = testee.listIterator();
        // Assert
        Assert.assertSame(result, testResult);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#listIterator(int)}.
     */

    @Test
    public void testListIteratorInt() {
        // Arrange
        final ListIterator<String> result = mock(ListIterator.class);
        final int testData = 5;
        when(delegateMock.listIterator(testData)).thenReturn(result);
        // Act
        final ListIterator<String> testResult = testee.listIterator(testData);
        // Assert
        Assert.assertSame(result, testResult);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#subList(int, int)}.
     */
    @Test
    public void testSubList() {
        // Arrange
        final List<String> result = mock(List.class);
        final int testData1 = 3;
        final int testData2 = 7;
        when(delegateMock.subList(testData1, testData2)).thenReturn(result);
        // Act
        final List<String> testResult = testee.subList(testData1, testData2);
        // Assert
        assertThat(testResult).isSameAs(result);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#getInternalList()}.
     */
    @Test
    public void testGetInternalList() {
        // Arrange
        // Act
        final List<String> testResult = testee.getInternalList();
        // Assert
        assertThat(testResult).isSameAs(delegateMock);
    }
}
