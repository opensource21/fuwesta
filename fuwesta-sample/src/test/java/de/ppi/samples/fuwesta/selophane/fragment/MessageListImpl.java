package de.ppi.samples.fuwesta.selophane.fragment;

import java.util.List;

import lombok.Getter;

import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.Fragment;
import org.selophane.elements.base.UniqueElementLocator;

/**
 * Defualt implementation of {@link MessageList}.
 *
 */
@Getter
public class MessageListImpl extends Fragment implements MessageList {

    /** List of messages. */
    @FindBy(tagName = "li")
    @CacheLookup
    private List<Element> messages;

    /**
     * Initiates an object of type MessageListImpl.
     *
     * @param elementLocator the locator of this element
     */
    public MessageListImpl(UniqueElementLocator elementLocator) {
        super(elementLocator);
    }

}
