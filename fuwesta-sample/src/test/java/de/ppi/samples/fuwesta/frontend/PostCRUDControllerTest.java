package de.ppi.samples.fuwesta.frontend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;

import de.ppi.fuwesta.spring.mvc.util.PageWrapper;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.service.api.PostService;

/**
 * Test of {@link PostCRUDController}.
 * 
 */
public class PostCRUDControllerTest {

    /**
     * The PostService instance.
     */
    @Mock
    private PostService postService;

    /**
     * The generic validator.
     */
    @Mock
    private Validator validator;

    /**
     * The test object.
     */
    @InjectMocks
    private final PostCRUDController testee = new PostCRUDController();

    /**
     * The default model.
     */
    private Model model = new ExtendedModelMap();

    /**
     * Setup of the test.
     * 
     * @throws java.lang.Exception if something goes wrong.
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#list(org.springframework.ui.Model, org.springframework.data.domain.Pageable)}
     * .
     */
    @SuppressWarnings("boxing")
    @Test
    public void testListEmptyList() {
        // Arrange
        final Pageable pageRequest = mock(Pageable.class);
        @SuppressWarnings("unchecked")
        final Page<Post> pageResult = mock(Page.class);
        when(postService.getPost(pageRequest)).thenReturn(pageResult);
        when(pageResult.getSize()).thenReturn(0);
        // Act
        final String result = testee.list(model, pageRequest);
        // Assert
        assertThat(result).isEqualTo(URL.redirect(URL.Post.CREATE));
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#list(org.springframework.ui.Model, org.springframework.data.domain.Pageable)}
     * .
     */

    @SuppressWarnings({ "unchecked", "boxing" })
    @Test
    public void testList() {
        // Arrange
        final int nrOfEntries = 100;
        final Pageable pageRequest = mock(Pageable.class);
        final Page<Post> pageResult = mock(Page.class);
        when(postService.getPost(pageRequest)).thenReturn(pageResult);
        when(pageResult.getSize()).thenReturn(nrOfEntries);
        // Act
        final String result = testee.list(model, pageRequest);
        // Assert
        assertThat(result).isEqualTo("example/post/list");
        assertThat(model.containsAttribute("postList")).isTrue();
        assertThat(model.containsAttribute("pageRequest")).isTrue();
        final Map<String, Object> map = model.asMap();
        assertThat(map.get("postList")).isInstanceOf(PageWrapper.class);
        assertThat(map.get("pageRequest")).isSameAs(pageRequest);

        final PageWrapper<Post> pageWrapper =
                (PageWrapper<Post>) map.get("postList");
        assertThat(pageWrapper.getSize()).isEqualTo(nrOfEntries);
        assertThat(pageWrapper.getUrl()).isEqualTo(URL.Post.LIST);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#create(org.springframework.ui.Model)}
     * .
     */
    // TODO @Test
    public void testCreate() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#insert(de.ppi.fuwesta.samples.springmvc.model.Post, org.springframework.validation.BindingResult, org.springframework.ui.Model)}
     * .
     */
    // TODO @Test
    public void testInsert() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#deleteConfirm(java.lang.Long, org.springframework.ui.Model)}
     * .
     */
    // TODO @Test
    public void testDeleteConfirm() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#delete(java.lang.Long)}
     * .
     */
    // TODO @Test
    public void testDelete() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#show(java.lang.Long, org.springframework.ui.Model)}
     * .
     */
    // TODO @Test
    public void testShow() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#edit(java.lang.Long, org.springframework.ui.Model)}
     * .
     */
    // TODO @Test
    public void testEdit() {
        Assert.fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.frontend.PostCRUDController#update(de.ppi.fuwesta.samples.springmvc.model.Post, org.springframework.validation.BindingResult, org.springframework.ui.Model)}
     * .
     */
    // TODO @Test
    public void testUpdate() {
        Assert.fail("Not yet implemented");
    }

}
