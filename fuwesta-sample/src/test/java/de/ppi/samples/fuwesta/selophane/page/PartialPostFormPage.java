package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Button;
import org.selophane.elements.widget.TextInput;

import de.ppi.samples.fuwesta.selophane.widget.MessageList;

/**
 * Pageobject for the Postform.
 *
 */
@Getter
public class PartialPostFormPage extends MainPage {

    /** List with global errors. */
    @FindBy(id = "globalErrors")
    @CacheLookup
    private MessageList globalErrors;

    /** The title input. */
    @FindBy(id = "title")
    @CacheLookup
    private TextInput titleInput;

    /** The contentinput. */
    @FindBy(id = "content")
    @CacheLookup
    private TextInput contentInput;

    /** The creation time input. */
    @FindBy(id = "creationTime")
    @CacheLookup
    private TextInput creationTimeInput;

    /** Button to save. */
    @FindBy(css = "input[value='Save']")
    @CacheLookup
    private Button save;

    /** Button to reset. */
    @FindBy(css = "input[value='Reset']")
    @CacheLookup
    private Element reset;

    /** Button to reset. */
    @FindBy(css = "html/body/a[1]")
    @CacheLookup
    private Button home;

    /** Button to reset. */
    @FindBy(xpath = "html/body/a[2]")
    @CacheLookup
    private Button list;

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
