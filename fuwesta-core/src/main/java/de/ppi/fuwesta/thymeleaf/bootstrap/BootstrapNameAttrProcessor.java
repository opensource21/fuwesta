package de.ppi.fuwesta.thymeleaf.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;
import org.thymeleaf.util.Validate;

import java.util.HashSet;
import java.util.Set;

/**
 * Attribute processor which makes the work easier with Twitter Bootstrap.
 * 
 */
public class BootstrapNameAttrProcessor extends AbstractAttrProcessor {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BootstrapNameAttrProcessor.class);

    /** The attribute name which should trigger this processor. */
    public static final String ATTRIBUTE_NAME = "name";

    /**
     * Attribute which contains the label (optional parameter).
     */
    private static final String BS_LABEL = "bs:label";

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
     * The precedence of the Processor. Should by high to make sure that remove
     * or if - conditions are run first.
     */
    public static final int PRECEDENCE = 10001;

    /**
     * Instantiates a new bootstrap field attr processor.
     */
    public BootstrapNameAttrProcessor() {
        super(ATTRIBUTE_NAME);

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
            fieldName =
                    (String) StandardExpressionProcessor.processExpression(
                            arguments, fieldNameExpr);
        } else {
            fieldName = fieldNameExpr;
        }

        if (validFieldNodes.contains(element.getNormalizedName())) {
            element.setAttribute("id", fieldName);
            element.setAttribute("name", fieldName);
        }

        element.removeAttribute(attributeName);

        String labelExpr = element.getAttributeValue(BS_LABEL);
        if (labelExpr == null) {
            labelExpr = "#{field." + fieldName + "}+':'";
        }

        final String label;
        if (labelExpr.contains("#") || labelExpr.contains("$")) {
            label =
                    (String) StandardExpressionProcessor.processExpression(
                            arguments, labelExpr);
        } else {
            label = labelExpr;
        }

        element.removeAttribute(BS_LABEL);

        // Reorganize the DOM
        final Node newNode = createBootstrapField(fieldName, label, element);
        parent.insertAfter(element, newNode);
        parent.removeChild(element);

        return ProcessorResult.OK;
    }

    /**
     * Creates a node which represents an Twitter Bootstrap Field. <code>
     * &lt;div class=&quot;control-group&quot;
     * &gt;
     * <br>
     * &lt;label class=&quot;control-label&quot; th:for=&quot;${fId}&quot;
     *      th:text=&quot;#{model.__*{class.simpleName}__.__${fId}__}+':'&quot;&gt; FirstName
     * &lt;/label&gt;<br>
     * &lt;div class=&quot;controls&quot;&gt;<br>
     * &lt;input type=&quot;text&quot;
     * th:class=&quot;${inputclass}&quot; th:field=&quot;*{__${fId}__}&quot;
     * th:disabled=&quot;${disabled}&quot;/&gt;
     * <br>
     * &lt;/div&gt;
     * <br>
     * &lt;/div&gt; </code>
     * 
     * @param fieldName the name of the property.
     * @param labelText the label text.
     * @param input the input-node.
     * @return The bootstrap-field.
     */
    private Node createBootstrapField(String fieldName, String labelText,
            Element input) {
        Validate.notNull(input, "You must define an input-element.");
        Validate.notNull(fieldName, "You must define a fieldName.");
        LOG.trace("Creating Bootstrap field with fieldname = '{}'", fieldName);

        // Create the new elements tags
        final Element controlgroup = new Element("div");
        controlgroup.setAttribute("class", "control-group");

        final Element label = new Element("label");

        label.setAttribute("class", "control-label");
        label.setAttribute("th:for", "'" + fieldName + "'");
        label.addChild(new Text(labelText));

        final Element controls = new Element("div");
        controls.setAttribute("class", "controls");

        // It necessary to clone the element, otherwise it wouldn't recomputed.
        final Node newInput = input.cloneNode(null, false);
        controls.addChild(newInput);

        controlgroup.addChild(label);
        controlgroup.addChild(controls);

        return controlgroup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }
}
