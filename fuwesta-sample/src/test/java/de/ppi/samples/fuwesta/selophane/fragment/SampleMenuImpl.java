package de.ppi.samples.fuwesta.selophane.fragment;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.ppi.samples.fuwesta.selophane.base.Fragment;
import de.ppi.samples.fuwesta.selophane.widget.MenuItem;

/**
 * Fragment Menu.
 *
 */
public class SampleMenuImpl extends Fragment implements SampleMenu {

    @FindBy(id = "nav.post")
    private MenuItem post;
    @FindBy(id = "nav.tag")
    private MenuItem tag;
    @FindBy(id = "nav.user")
    private MenuItem user;
    @FindBy(id = "nav.logout")
    private MenuItem logout;

    /**
     *
     * Initiates an object of type Menu.
     *
     * @param webElement the parent-element.
     */
    public SampleMenuImpl(WebElement webElement) {
        super(webElement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getPost() {
        return post;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getTag() {
        return tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getUser() {
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getLogout() {
        return logout;
    }

}
