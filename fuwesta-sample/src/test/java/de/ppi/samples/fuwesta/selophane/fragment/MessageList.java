package de.ppi.samples.fuwesta.selophane.fragment;

import java.util.List;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * Container which holds a lot of messages.
 *
 */
@ImplementedBy(MessageListImpl.class)
public interface MessageList extends Element {

    /**
     * Returns the list of messages.
     *
     * @return the list of messages
     */
    List<Element> getMessages();

}
