package de.ppi.fuwesta.jpa.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.fest.reflect.core.Reflection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.MockitoAnnotations;

import de.ppi.fuwesta.jpa.helper.JPAList;

/**
 * Test the non-delegating methods from {@link JPAList}.
 *
 */
@SuppressWarnings({ "boxing", "unchecked" })
public class JPAListTest {

    /**
     * The testobject.
     */
    private JPAList<String, Long> testee;

    /**
     * Internal list.
     */
    private List<String> internalList;

    /**
     * Initialize the test.
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testee = mock(JPAList.class, CALLS_REAL_METHODS);
        doNothing().when(testee).add(any(String.class), any(Long.class));
        doNothing().when(testee).remove(any(String.class), any(Long.class));
        internalList = new ArrayList<>();
        Reflection.field("internalList").ofType(List.class).in(testee)
                .set(internalList);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#set(java.util.List)}.
     */
    @Test
    public void testSetListBothEmpty() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        testee.getInternalList().addAll(oldList);
        // Act
        testee.set(newList);
        // Assert
        verify(testee, times(0)).add(any(String.class), any(Long.class));
        verify(testee, times(0)).remove(any(String.class), any(Long.class));

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#set(java.util.List)}.
     */
    @Test
    public void testSetListOldEmpty() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        newList.add("A");
        testee.getInternalList().addAll(oldList);
        // Act
        testee.set(newList);
        // Assert
        verify(testee, times(1)).add("A");
        verify(testee, times(0)).remove(any(String.class));
        verify(testee, times(0)).remove(any(String.class), any(Long.class));

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#set(java.util.List)}.
     */
    @Test
    public void testSetListNewEmpty() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        oldList.add("A");
        testee.getInternalList().addAll(oldList);
        // Act
        testee.set(newList);
        // Assert
        verify(testee, times(1)).remove(0);
        verify(testee, times(0)).add(any(String.class));
        verify(testee, times(0)).add(any(String.class), any(Long.class));

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#set(java.util.List)}.
     */
    @Test
    public void testSetListNewNull() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = null;
        oldList.add("A");
        oldList.add("B");

        testee.getInternalList().addAll(oldList);
        // Act
        testee.set(newList);
        // Assert
        verify(testee, times(2)).remove(0);
        verify(testee, times(0)).add(any(String.class));
        verify(testee, times(0)).add(any(String.class), any(Long.class));

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#set(java.util.List)}.
     */
    @Test
    public void testSetList() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        oldList.add("old1");
        oldList.add("both");
        newList.add("both");
        newList.add("new1");

        testee.getInternalList().addAll(oldList);
        // Act
        testee.set(newList);
        // Assert
        verify(testee, times(1)).remove(0);
        verify(testee, times(1)).add("new1");
        assertThat(testee.size()).isEqualTo(2);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#add(java.lang.Object)}.
     */
    @Test
    public void testAddAgain() {
        // Arrange
        testee.getInternalList().add("A");

        // Act
        boolean result = testee.add("A");
        // Assert
        assertThat(result).isFalse();
        verify(testee, times(0)).add(any(String.class), any(Long.class));
        verify(testee, times(0)).remove(any(String.class), any(Long.class));

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#add(java.lang.Object)}.
     */
    @Test
    public void testAddFirst() {
        // Arrange
        // Act
        boolean result = testee.add("A");
        // Assert
        assertThat(result).isTrue();
        verify(testee, times(1)).add("A", null);
        verify(testee, times(0)).remove(any(String.class), any(Long.class));

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#remove(java.lang.Object)}.
     */
    @Test
    public void testRemoveObjectExist() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        oldList.add("old1");
        oldList.add("both");
        String object = "remove";
        oldList.add(object);

        testee.getInternalList().addAll(oldList);
        // Act
        boolean result = testee.remove(object);
        // Assert
        assertThat(result).isTrue();
        verify(testee, times(1)).remove(2);
        verify(testee, times(0)).add(any(String.class), any(Long.class));
        assertThat(testee.size()).isEqualTo(2);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#remove(java.lang.Object)}.
     */
    @Test
    public void testRemoveObjectNonExist() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        oldList.add("old1");
        oldList.add("both");
        String object = "remove";

        testee.getInternalList().addAll(oldList);
        // Act
        boolean result = testee.remove(object);
        // Assert
        assertThat(result).isFalse();
        verify(testee, times(0)).remove(anyInt());
        verify(testee, times(0)).add(any(String.class), any(Long.class));
        assertThat(testee.size()).isEqualTo(2);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#addAll(java.util.Collection)}.
     */
    @Test
    public void testAddAllCollectionOfQextendsE() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        final int nrOfEntries = 4;
        oldList.add("old1");
        oldList.add("both");
        newList.add("both");
        newList.add("new1");
        newList.add("new2");

        testee.getInternalList().addAll(oldList);
        // Act
        boolean result = testee.addAll(newList);
        // Assert
        assertThat(result).isFalse();
        verify(testee, times(0)).remove(any(String.class), any(Long.class));
        verify(testee, times(1)).add("both");
        verify(testee, times(1)).add("new1");
        verify(testee, times(1)).add("new2");
        assertThat(testee.size()).isEqualTo(nrOfEntries);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#addAll(int, java.util.Collection)}.
     */
    @Test
    public void testAddAllIntCollectionOfQextendsE() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        final List<String> resultList = new ArrayList<String>();
        final int nrOfEntries = 4;
        oldList.add("old1");
        oldList.add("both");
        newList.add("both");
        newList.add("new1");
        newList.add("new2");
        resultList.addAll(oldList);
        resultList.addAll(0, newList.subList(1, newList.size()));

        testee.getInternalList().addAll(oldList);
        // Act
        boolean result = testee.addAll(0, newList);
        // Assert
        assertThat(result).isFalse();
        verify(testee, times(0)).remove(any(String.class), any(Long.class));
        verify(testee, times(0)).add(0, "both");
        verify(testee, times(1)).add(0, "new1");
        verify(testee, times(1)).add(1, "new2");
        assertThat(testee.size()).isEqualTo(nrOfEntries);

        assertThat(testee.getInternalList()).isEqualTo(resultList);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#removeAll(java.util.Collection)}.
     */
    @Test
    public void testRemoveAll() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        final List<String> resultList = new ArrayList<String>();
        final int nrOfEntries = 4;
        oldList.add("old1");
        oldList.add("both");
        oldList.add("new1");
        oldList.add("new2");
        newList.add("new1");
        newList.add("new1");
        newList.add("new2");

        resultList.addAll(oldList);
        resultList.removeAll(newList);

        testee.getInternalList().addAll(oldList);
        assertThat(testee.size()).isEqualTo(nrOfEntries);
        // Act
        boolean result = testee.removeAll(newList);
        // Assert
        assertThat(result).isFalse();
        verify(testee, times(0)).add(any(String.class), any(Long.class));
        verify(testee, times(2)).remove("new1");
        verify(testee, times(1)).remove("new2");
        assertThat(testee.size()).isEqualTo(2);

        assertThat(testee.getInternalList()).isEqualTo(resultList);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#retainAll(java.util.Collection)}.
     */
    @Test
    public void testRetainAll() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> newList = new ArrayList<String>();
        final List<String> resultList = new ArrayList<String>();
        final int nrOfEntries = 5;
        oldList.add("old1");
        oldList.add("old1");
        oldList.add("both");
        oldList.add("new1");
        oldList.add("new2");
        newList.add("new1");
        newList.add("new2");

        resultList.addAll(oldList);
        resultList.retainAll(newList);

        testee.getInternalList().addAll(oldList);
        assertThat(testee.size()).isEqualTo(nrOfEntries);
        // Act
        boolean result = testee.retainAll(newList);
        // Assert
        assertThat(result).isTrue();
        verify(testee, times(0)).add(any(String.class), any(Long.class));
        verify(testee, times(2)).remove("old1");
        verify(testee, times(1)).remove("both");
        assertThat(testee.size()).isEqualTo(2);

        assertThat(testee.getInternalList()).isEqualTo(resultList);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#clear()}.
     */
    @Test
    public void testClear() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final int nrOfEntries = 4;
        oldList.add("old1");
        oldList.add("both");
        oldList.add("new1");
        oldList.add("new2");

        testee.getInternalList().addAll(oldList);
        assertThat(testee.size()).isEqualTo(nrOfEntries);
        // Act
        testee.clear();
        // Assert
        verify(testee, times(0)).add(any(String.class), any(Long.class));
        verify(testee, times(1)).remove("old1");
        verify(testee, times(1)).remove("both");
        verify(testee, times(1)).remove("new1");
        verify(testee, times(1)).remove("new2");
        assertThat(testee.isEmpty()).isTrue();

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#set(int, java.lang.Object)}.
     */
    @Test
    public void testSetIntE() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> resultList = new ArrayList<String>();
        final String addEntry = "new1";
        oldList.add("old1");
        oldList.add("old2");
        oldList.add("both");

        resultList.addAll(oldList);
        resultList.set(1, addEntry);

        testee.getInternalList().addAll(oldList);
        // Act
        String result = testee.set(1, addEntry);
        // Assert
        assertThat(result).isEqualTo("old2");
        verify(testee, times(1)).add(1, addEntry);
        verify(testee, times(1)).remove(1);

        assertThat(testee.getInternalList()).isEqualTo(resultList);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#add(int, java.lang.Object)}.
     */
    @Test
    public void testAddIntE() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> resultList = new ArrayList<String>();
        final String addEntry = "new1";
        oldList.add("old1");
        oldList.add("old2");
        oldList.add("both");

        resultList.addAll(oldList);
        resultList.add(1, addEntry);

        testee.getInternalList().addAll(oldList);
        // Act
        testee.add(1, addEntry);
        // Assert
        verify(testee, times(1)).add(addEntry, null);
        verify(testee, times(0)).remove(anyString(), any(Long.class));

        assertThat(testee.getInternalList()).isEqualTo(resultList);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#remove(int)}.
     */
    @Test
    public void testRemoveInt() {
        // Arrange
        final List<String> oldList = new ArrayList<String>();
        final List<String> resultList = new ArrayList<String>();
        oldList.add("old1");
        oldList.add("old2");
        oldList.add("both");

        resultList.addAll(oldList);
        resultList.remove(1);

        testee.getInternalList().addAll(oldList);
        // Act
        String result = testee.remove(1);
        // Assert
        assertThat(result).isEqualTo("old2");
        verify(testee, times(1)).remove("old2", null);
        verify(testee, times(0)).add(anyString(), any(Long.class));

        assertThat(testee.getInternalList()).isEqualTo(resultList);
    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#JPAList(Object)}.
     */
    @Test
    public void testJPAListA() {
        // Arrange
        // Act
        final JPAList<String, Long> jpaList =
                new JPAList<String, Long>(Long.valueOf(1)) {

                    @Override
                    public void remove(String entity, Long associatedEntity) {
                    	throw new RuntimeException("Not implemented in Test");
                    }

                    @Override
                    public void add(String entity, Long associatedEntity) {
                    	throw new RuntimeException("Not implemented in Test");
                    }
                };
        // Assert
        assertThat(jpaList.getInternalList()).isNotNull().isEmpty();

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#JPAList(int, Object)}.
     */
    @Test
    public void testJPAListIntA() {
        // Arrange
        // Act
        final JPAList<String, Long> jpaList =
                new JPAList<String, Long>(1, Long.valueOf(1)) {

                    @Override
                    public void remove(String entity, Long associatedEntity) {
                    	throw new RuntimeException("Not implemented in Test");
                    }

                    @Override
                    public void add(String entity, Long associatedEntity) {
                    	throw new RuntimeException("Not implemented in Test");
                    }
                };
        // Assert
        assertThat(jpaList.getInternalList()).isNotNull().isEmpty();
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.jpa.helper.JPAList#add(int, java.lang.Object)}.
     */
    @Test
    public void testAddIntEOrder() {
        // Arrange
        List<String> mockList = mock(List.class);
        Reflection.field("internalList").ofType(List.class).in(testee)
                .set(mockList);
        final String newEntry = "new1";
        when(mockList.contains(newEntry)).thenReturn(false);
        final InOrder inorder = inOrder(testee, mockList);
        // Act
        testee.add(1, newEntry);
        // Assert
        inorder.verify(mockList, times(1)).contains(newEntry);
        inorder.verify(mockList, times(1)).add(1, newEntry);
        inorder.verify(testee, times(1)).add(newEntry, null);
        verify(testee, times(0)).remove(anyString(), any(Long.class));

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#add(java.lang.Object)}.
     */
    @Test
    public void testAddEOrder() {
        // Arrange
        List<String> mockList = mock(List.class);
        Reflection.field("internalList").ofType(List.class).in(testee)
                .set(mockList);
        final String newEntry = "new1";
        when(mockList.contains(newEntry)).thenReturn(false);
        final InOrder inorder = inOrder(testee, mockList);
        // Act
        testee.add(newEntry);
        // Assert
        inorder.verify(mockList, times(1)).contains(newEntry);
        inorder.verify(mockList, times(1)).add(newEntry);
        inorder.verify(testee, times(1)).add(newEntry, null);
        verify(testee, times(0)).remove(anyString(), any(Long.class));

    }

    /**
     * Test method for {@link de.ppi.fuwesta.jpa.helper.JPAList#remove(int)}.
     */
    @Test
    public void testRemoveIntOrder() {
        // Arrange
        List<String> mockList = mock(List.class);
        Reflection.field("internalList").ofType(List.class).in(testee)
                .set(mockList);

        final String removeEntry = "FOO";
        when(mockList.remove(1)).thenReturn(removeEntry);
        final InOrder inorder = inOrder(testee, mockList);
        // Act
        testee.remove(1);
        // Assert
        inorder.verify(mockList, times(1)).remove(1);
        inorder.verify(testee, times(1)).remove(removeEntry, null);
        verify(testee, times(0)).add(anyString(), any(Long.class));

    }
}
