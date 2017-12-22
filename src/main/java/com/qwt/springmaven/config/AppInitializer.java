package com.qwt.springmaven.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Wentao Qian on 12/22/2017.
 */
public class AppInitializer implements WebApplicationInitializer {

    Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    private static final String CONFIG_LOCATION = "com.qwt.springmaven.config";
    private static final String MAPPING_URL = "/springmvc/*";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.debug("entering AppInitializer.onStartup()");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(context));

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(MAPPING_URL);
        logger.debug("exiting AppInitializer.onStartup()");
    }

}
