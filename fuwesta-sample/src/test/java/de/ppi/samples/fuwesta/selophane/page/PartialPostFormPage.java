package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.widget.Button;
import org.selophane.elements.widget.TextInput;

/**
 * Pageobject for the Postform.
 *
 */
@Getter
public class PartialPostFormPage extends MainPage {

    /** The title input. */
    @FindBy(id = "title")
    private TextInput titleInput;

    /** The contentinput. */
    @FindBy(id = "content")
    private TextInput contentInput;

    /** The creation time input. */
    @FindBy(id = "creationTime")
    private TextInput creationTimeInput;

    /** Button to save. */
    @FindBy(css = "input[value='Save']")
    private Button save;

    /** Button to reset. */
    @FindBy(css = "input[value='Reset']")
    private Button reset;

    /**
     *
     * Initiates an object of type PostFormPage.
     *
     * @param webDriver the underlying {@link WebDriver}.
     */
    public PartialPostFormPage(WebDriver webDriver) {
        super(webDriver);
    }

}
