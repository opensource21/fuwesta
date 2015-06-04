package de.ppi.fuwesta.thymeleaf.bootstrap2;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.util.Validate;

/**
 * Attribute processor which makes the work easier with Twitter Bootstrap.
 *
 */
public abstract class AbstractBootstrap2AttrProcessor extends
        AbstractAttrProcessor {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractBootstrap2AttrProcessor.class);

    /**
     * Attribute which contains the label (optional parameter).
     */
    private static final String BS_LABEL = "bs:label";

    /**
     * The attribut "class".
     */
    private static final String ATTR_CLASS = "class";

    /**
     * List of all node names, which are used for th:field.
     */
    private static final String[] VALID_FIELD_NODE_NAMES = { "input", "select",
            "option", "textarea" };

    /**
     * Set of all node names, which are used for th:field.
     */
    private Set<String> validFieldNodes = new HashSet<>();

    /**
     * Instantiates a new bootstrap field attr processor.
     *
     * @param attributeName name of the attribute which triggers the processor.
     */
    public AbstractBootstrap2AttrProcessor(String attributeName) {
        super(attributeName);
        for (String nodeName : VALID_FIELD_NODE_NAMES) {
            validFieldNodes.add(nodeName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ProcessorResult processAttribute(final Arguments arguments,
            final Element element, final String attributeName) {
        NestableNode parent = element.getParent();

        final String fieldNameExpr = element.getAttributeValue(attributeName);
        final String fieldName;
        if (fieldNameExpr.contains("#") || fieldNameExpr.contains("$")) {
            fieldName = parse(arguments, fieldNameExpr);
        } else {
            fieldName = fieldNameExpr;
        }

        if (validFieldNodes.contains(element.getNormalizedName())) {
            addAttributesToInputElement(element, fieldName);
        }
        element.removeAttribute(attributeName);

        String labelExpr = element.getAttributeValue(BS_LABEL);
        if (labelExpr == null) {
            labelExpr = createLabelExpression(fieldName);
        }

        final String label;
        if (labelExpr.contains("#") || labelExpr.contains("$")) {
            label = parse(arguments, labelExpr);
        } else {
            label = labelExpr;
        }

        element.removeAttribute(BS_LABEL);

        // Reorganize the DOM
        final Node newNode =
                createBootstrapField(fieldName, label, element, showError());
        parent.insertAfter(element, newNode);
        parent.removeChild(element);

        return ProcessorResult.OK;
    }

    /**
     * Create an expression to get the label, for example #{fieldName}.
     *
     * @param fieldName name of the field
     * @return an valid thymeleafexpression.
     */
    protected abstract String createLabelExpression(String fieldName);

    /**
     * Manipulate the given element by adding some attributes.
     *
     * @param element the inputElement
     * @param fieldName the name of the field.
     */
    protected abstract void addAttributesToInputElement(final Element element,
            final String fieldName);

    /**
     * Returns true if an error should shown at the field.
     *
     * @return true if the error should be shown.
     */
    protected abstract boolean showError();

    /**
     * Creates a node which represents an Twitter Bootstrap Field. <code>
     * &lt;div class=&quot;control-group&quot;
     * th:classappend=&quot;${#fields.hasErrors('__${fId}__')}? 'error'&quot;&gt;
     * <br>
     * &lt;label class=&quot;control-label&quot; th:for=&quot;${fId}&quot;
     *      th:text=&quot;#{model.__*{class.simpleName}__.__${fId}__}+':'&quot;&gt; FirstName
     * &lt;/label&gt;<br>
     * &lt;div class=&quot;controls&quot;&gt;<br>
     * &lt;input type=&quot;text&quot;
     * th:class=&quot;${inputclass}&quot; th:field=&quot;*{__${fId}__}&quot;
     * th:disabled=&quot;${disabled}&quot;/&gt;
     * <br>
     * &lt;span class=&quot;help-inline&quot;
     * th:if=&quot;${#fields.hasErrors('__${fId}__')}&quot;
     * th:errors=&quot;*{__${fId}__}&quot;&gt;&lt;/span&gt;
     * 
     * <br>
     * &lt;/div&gt;
     * <br>
     * &lt;/div&gt; </code>
     *
     * @param fieldName the name of the property.
     * @param labelText the label text.
     * @param input the input-node.
     * @param showError true, if help-inline should be shown and the class error
     *            should be added.
     * @return The bootstrap-field.
     */
    private Node createBootstrapField(String fieldName, String labelText,
            Element input, boolean showError) {
        Validate.notNull(input, "You must define an input-element.");
        Validate.notNull(fieldName, "You must define a fieldName.");
        LOG.trace("Creating Bootstrap field with fieldname = '{}'", fieldName);
        // Create the new elements tags
        final Element controlgroup = new Element("div");
        controlgroup.setAttribute(ATTR_CLASS, "control-group");
        if (showError) {
            controlgroup.setAttribute("th:classappend", "${#fields.hasErrors('"
                    + fieldName + "')}? 'error'");
        }

        final Element label = new Element("label");

        label.setAttribute(ATTR_CLASS, "control-label");
        label.setAttribute("th:for", "'" + fieldName + "'");
        label.addChild(new Text(labelText));

        final Element controls = new Element("div");
        controls.setAttribute(ATTR_CLASS, "controls");
        // It necessary to clone the element, otherwise it wouldn't recomputed.
        final Node newInput = input.cloneNode(null, false);
        controls.addChild(newInput);
        if (showError) {
            Element help = new Element("span");
            help.setAttribute(ATTR_CLASS, "help-inline");
            help.setAttribute("id", "error_" + fieldName);
            help.setAttribute("th:if", "${#fields.hasErrors('" + fieldName
                    + "')}");
            help.setAttribute("th:errors", "*{" + fieldName + "}");
            controls.addChild(help);
        }

        controlgroup.addChild(label);
        controlgroup.addChild(controls);

        return controlgroup;
    }

    private String parse(final Arguments arguments, String input) {
        final Configuration configuration = arguments.getConfiguration();

        final IStandardExpressionParser parser =
                StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression expression =
                parser.parseExpression(configuration, arguments, input);

        return (String) expression.execute(configuration, arguments);
    }
}
