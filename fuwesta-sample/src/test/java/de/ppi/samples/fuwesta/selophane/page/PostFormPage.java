package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.UniqueElementLocator;
import org.selophane.elements.widget.Label;
import org.selophane.elements.widget.LabelImpl;
import org.selophane.elements.widget.Select;

/**
 * Pageobject for the Postform.
 *
 */
@Getter
public class PostFormPage extends MainPage {

    /** The title label. */
    @FindBy(css = "label[for=title]")
    private Label titleLabel;

    /** The title input. */
    @FindBy(id = "title")
    private Element titleInput;

    /** The content label. */
    @FindBy(css = "label[for=content]")
    private Label contentLabel;

    /** The contentinput. */
    @FindBy(id = "content")
    private Element contentinput;

    /** The creation time label. */
    @FindBy(css = "label[for=creationTime]")
    private Label creationTimeLabel;

    /** The creation time input. */
    @FindBy(id = "creationTime")
    private Element creationTimeInput;

    /** The user label. */
    @FindBy(css = "label[for=user]")
    private Label userLabel;

    /** The user input. */
    @FindBy(id = "user")
    private Select userInput;

    /** The tags label. */
    @FindBy(css = "label[for=tags]")
    private Label tagsLabel;

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

    /**
     * Get a label for the given element which must have an id.
     *
     * @param element element for which the element is searched.
     * @return the Label.
     */
    public Label getLabelFor(Element element) {
        final String id = element.getAttribute("id");
        if (id == null) {
            return null;
        }
        final String xpPath = "label[for=" + id + "]";
        return new LabelImpl(new UniqueElementLocator() {

            @Override
            public WebElement findElement() {
                return getWebDriver().findElement(By.xpath(xpPath));
            }

            @Override
            public WebDriver getWebDriver() {
                return getWebDriver();
            }
        });
    }
}
