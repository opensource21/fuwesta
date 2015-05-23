package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.support.FindBy;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Label;
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

}
