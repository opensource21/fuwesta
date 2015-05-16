package de.ppi.samples.fuwesta.selophane.checks;

import java.util.List;

import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBarButton;
import de.ppi.selenium.assertj.SeleniumSoftAssertions;

/**
 * Checks for the {@link PaginatingBar}.
 *
 */
public final class PaginatingBarChecks {

    /**
     *
     * Initiates an object of type PaginatingBarChecks.
     */
    private PaginatingBarChecks() {

    }

    /**
     * Checks if the paginating is shown correct and that the table has the
     * expected number of items.
     *
     * @param softly softly-assertions.
     * @param paginatingBar the {@link PaginatingBar}.
     * @param currentPage the number of the current page.
     * @param numberOfPages the number of pages, must be greater than 5.
     *
     */
    @SuppressWarnings("boxing")
    public static void checkPaginating(SeleniumSoftAssertions softly,
            final PaginatingBar paginatingBar, final int currentPage,
            int numberOfPages) {
        softly.assertThat(paginatingBar.getNrOfButtons()).isEqualTo(9);
        boolean isFirstPage = currentPage == 1;
        boolean isLastPage = currentPage == numberOfPages;
        softly.assertThat(paginatingBar.getFirst().isDisabled())
                .as("Currentpage=%s, NumberOfPages=%s", currentPage,
                        numberOfPages).isEqualTo(isFirstPage);
        softly.assertThat(paginatingBar.getPrevious().isDisabled())
                .as("Currentpage=%s, NumberOfPages=%s", currentPage,
                        numberOfPages).isEqualTo(isFirstPage);
        softly.assertThat(paginatingBar.getLast().isDisabled())
                .as("Currentpage=%s, NumberOfPages=%s", currentPage,
                        numberOfPages).isEqualTo(isLastPage);
        softly.assertThat(paginatingBar.getNext().isDisabled())
                .as("Currentpage=%s, NumberOfPages=%s", currentPage,
                        numberOfPages).isEqualTo(isLastPage);
        final List<PaginatingBarButton> buttons = paginatingBar.getAllButtons();

        final int currentPageIndex;
        final int startLabel;
        if (currentPage < 3) {
            startLabel = 1;
            currentPageIndex = currentPage + 1;
        } else if (currentPage > numberOfPages - 2) {
            startLabel = numberOfPages - 4;
            currentPageIndex = 6 + currentPage - numberOfPages;
        } else {
            startLabel = currentPage - 2;
            currentPageIndex = 4;
        }
        for (int i = 2; i < buttons.size() - 2; i++) {
            final PaginatingBarButton element = buttons.get(i);
            softly.assertThat(element.getText()).isEqualTo(
                    "" + (startLabel + i - 2));
            if (i == currentPageIndex) {
                softly.assertThat(element.isActive())
                        .as("Currentpage=%s, NumberOfPages=%s and i=%s",
                                currentPage, numberOfPages, i).isTrue();
                softly.assertThat(element.isDisabled())
                        .as("Currentpage=%s, NumberOfPages=%s and i=%s",
                                currentPage, numberOfPages, i).isFalse();
            } else {
                softly.assertThat(element.isDisabled())
                        .as("Currentpage=%s, NumberOfPages=%s and i=%s",
                                currentPage, numberOfPages, i).isFalse();
                softly.assertThat(element.isActive())
                        .as("Currentpage=%s, NumberOfPages=%s and i=%s",
                                currentPage, numberOfPages, i).isFalse();
            }
        }
    }
}
