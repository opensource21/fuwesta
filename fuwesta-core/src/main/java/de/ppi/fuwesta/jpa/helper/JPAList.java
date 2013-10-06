package de.ppi.fuwesta.jpa.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.util.CollectionUtils;

/**
 * A list which handles JPA-bidirectional aspects, furthermore an element can
 * only be once part of the list.
 *
 * @param <E> the type of elements in this list.
 * @param <A> the associated type
 */
public abstract class JPAList<E, A> implements List<E> {

    /** The list which holds the data. */
    private final List<E> internalList;

    /**
     * The entity which holds this list.
     */
    private final A associatedEntity;

    /**
     * Constructs an JPAList which the internalList.
     *
     * @param internalList the list which holds the data.
     * @param associatedEntity the entity which holds this list.
     */
    public JPAList(List<E> internalList, A associatedEntity) {
        this.internalList = internalList;
        this.associatedEntity = associatedEntity;
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     *
     * @param associatedEntity the entity which holds this list.
     */
    public JPAList(A associatedEntity) {
        this(new ArrayList<E>(), associatedEntity);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @param associatedEntity the entity which holds this list.
     * @throws IllegalArgumentException if the specified initial capacity is
     *             negative
     */
    public JPAList(int initialCapacity, A associatedEntity) {
        this(new ArrayList<E>(initialCapacity), associatedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return internalList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return internalList.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return internalList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return internalList.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return internalList.toArray(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        if (!internalList.contains(e)) {
            boolean result = internalList.add(e);
            add(e, associatedEntity);
            return result;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        int pos = indexOf(o);
        if (pos == -1) {
            return false;
        } else {
            remove(indexOf(o));
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return internalList.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean allAdded = true;
        ArrayList<E> internalList = new ArrayList<E>();
        internalList.addAll(c);
        for (E e : internalList) {
            allAdded = add(e) && allAdded;
        }
        return allAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        final int startSize = size();
        int currentIndex = index;
        for (E e : c) {
            if (!internalList.contains(e)) {
                add(currentIndex, e);
                currentIndex++;
            }
        }
        return (startSize + c.size() == size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean allRemoved = true;
        for (Object e : c) {
            allRemoved = remove(e) && allRemoved;
        }
        return allRemoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean allRemoved = true;
        for (Object e : internalList.toArray()) {
            if (!c.contains(e)) {
                allRemoved = remove(e) && allRemoved;
            }
        }
        return allRemoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (Object e : internalList.toArray()) {
            remove(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index) {
        return internalList.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        E oldElement = remove(index);
        add(index, element);
        return oldElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        if (!internalList.contains(element)) {
            internalList.add(index, element);
            add(element, associatedEntity);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        E removedElement = internalList.remove(index);
        if (removedElement != null) {
            remove(removedElement, associatedEntity);
        }
        return removedElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o) {
        return internalList.indexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o) {
        return internalList.lastIndexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator() {
        return internalList.listIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return internalList.listIterator(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return internalList.subList(fromIndex, toIndex);
    }

    /**
     * Set the list to the given list.
     *
     * @param newList the new list.
     */
    public void set(List<E> newList) {
        if (internalList.isEmpty()) {
            if (!CollectionUtils.isEmpty(newList)) {
                addAll(newList);
            }
        } else {
            final List<E> entriesToAdd = new ArrayList<E>();
            if (newList != null) {
                entriesToAdd.addAll(newList);
            }
            final Object[] oldEntries = internalList.toArray();
            for (int i = 0; i < oldEntries.length; i++) {
                final Object entry = oldEntries[i];
                if (entriesToAdd.contains(entry)) {
                    entriesToAdd.remove(entry);
                } else {
                    remove(entry);
                }
            }
            addAll(entriesToAdd);
        }
    }

    /**
     * Returns the internal list.
     *
     * @return the internalList
     */
    public List<E> getInternalList() {
        return internalList;
    }

    /**
     * Does the addional tasks which must be done to handle the relations.
     *
     * @param entity the entity which is added.
     * @param associatedEntity the associated entity.
     */
    public abstract void add(E entity, A associatedEntity);

    /**
     * Does the addional tasks which must be done to handle the relations.
     *
     * @param entity the entity which is added.
     * @param associatedEntity the associated entity.
     */
    public abstract void remove(E entity, A associatedEntity);
}
