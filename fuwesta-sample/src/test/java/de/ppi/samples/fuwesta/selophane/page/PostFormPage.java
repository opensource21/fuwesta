package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.widget.Select;

/**
 * Pageobject for the Postform.
 *
 */
@Getter
public class PostFormPage extends PartialPostFormPage {

    /** The user input. */
    @FindBy(id = "user")
    private Select userInput;

    /** The tags select. */
    @FindBy(id = "tags")
    private Select tagsSelect;

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
