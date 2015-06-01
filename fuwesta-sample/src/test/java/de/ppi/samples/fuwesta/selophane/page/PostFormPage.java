package de.ppi.samples.fuwesta.selophane.page;

import java.util.List;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.widget.Link;
import org.selophane.elements.widget.Select;

/**
 * Pageobject for the Postform.
 *
 */
@Getter
public class PostFormPage extends PartialPostFormPage {

    // Visible for edit
    /** The user input. */
    @FindBy(id = "user")
    @CacheLookup
    private Select userInput;

    /** The tags select. */
    @FindBy(id = "tags")
    @CacheLookup
    private Select tagsSelect;

    // Visible at show.
    /** A link to the user. */
    @FindBy(id = "user")
    @CacheLookup
    private Link userLink;

    /** A list of links to the tags. */
    @FindBy(xpath = "//ul[@id='tags']/li/a")
    @CacheLookup
    private List<Link> tagList;

    /**
     *
     * Initiates an object of type PostFormPage.
     *
     * @param webDriver the underlying {@link WebDriver}.
     */
    public PostFormPage(WebDriver webDriver) {
        super(webDriver);
    }

}
