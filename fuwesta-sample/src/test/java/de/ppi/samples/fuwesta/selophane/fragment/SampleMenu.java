package de.ppi.samples.fuwesta.selophane.fragment;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

import de.ppi.samples.fuwesta.selophane.widget.MenuItem;

/**
 * The menu of fuwesta-sample-app.
 *
 */
@ImplementedBy(SampleMenuImpl.class)
public interface SampleMenu extends Element {

    MenuItem getPost();

    MenuItem getTag();

    MenuItem getUser();

    MenuItem getLogout();

}