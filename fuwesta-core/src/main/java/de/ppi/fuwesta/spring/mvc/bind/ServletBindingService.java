package de.ppi.fuwesta.spring.mvc.bind;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Small helber service which uses the {@link ExtendedServletRequestDataBinder}
 * initialize and use it. This is useful if you want partially edit an
 * JPA-Entity. Normally you bind the data to an object with is not under JPA
 * control. If you merge it to the {@link EntityManager} JPA can't know which
 * attributes has changed and think all have changed. To solve this you should
 * read the {@link Entity} first and bind then the data.
 * 
 */
public class ServletBindingService {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * Bind the given request-data to the object. The object and
     * {@link BindingResult} is put to the model under the given name.
     * 
     * @param request the HTTP-request.
     * @param model the model which contains the view-data.
     * @param object the object which should be changed.
     * @param objectName the name of the object in the model.
     * @return the result of the binding.
     */
    public BindingResult bind(HttpServletRequest request, Model model,
            Object object, String objectName) {
        DataBinder binder = bindInternal(request, model, object, objectName);
        return binder.getBindingResult();

    }

    /**
     * Bind the given request-data to the object and validate is. The object and
     * {@link BindingResult} is put to the model under the given name.
     * 
     * @param request the HTTP-request.
     * @param model the model which contains the view-data.
     * @param object the object which should be changed.
     * @param objectName the name of the object in the model.
     * @return the result of the binding and validation.
     */
    public BindingResult bindAndvalidate(HttpServletRequest request,
            Model model, Object object, String objectName) {
        DataBinder binder = bindInternal(request, model, object, objectName);
        binder.validate();
        return binder.getBindingResult();
    }

    private DataBinder bindInternal(HttpServletRequest request, Model model,
            Object object, String objectName) {
        final WebBindingInitializer wbi =
                requestMappingHandlerAdapter.getWebBindingInitializer();
        ExtendedServletRequestDataBinder binder =
                new ExtendedServletRequestDataBinder(object, objectName);
        wbi.initBinder(binder, new ServletWebRequest(request));
        binder.bind(request);
        model.addAttribute(objectName, object);
        model.addAttribute("org.springframework.validation.BindingResult."
                + objectName, binder.getBindingResult());
        return binder;
    }

}
