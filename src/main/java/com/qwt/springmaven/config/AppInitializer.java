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
/*
this is one way to replace web.xml
TODO: another way is to extend AbstractAnnotationConfigDispatcherServletInitializer class --- can't say it's easier
http://www.java-allandsundry.com/2013/07/abstractannotationconfigdispatcherservl.html
Refer to this website for more information of how to use this abstract class
 */
public class AppInitializer implements WebApplicationInitializer {

    Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    private static final String CONFIG_LOCATION = "com.qwt.springmaven.config";
    private static final String MAPPING_URL = "/test/*";

    /*
    看到这个类里面的AnnotationConfigWebApplicationContext和ContextLoaderListener和DispatcherServlet
    就知道这是用来取代web.xml的
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.debug("entering AppInitializer.onStartup()");
        System.out.println("entering AppInitializer.onStartup()");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(context));

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        //https://docs.spring.io/spring/docs/3.1.x/javadoc-api/org/springframework/web/WebApplicationInitializer.html
        /*
        same as the following in web.xml, though we are not using xml now because of this class
        <servlet-mapping>
           <servlet-name>dispatcher</servlet-name>
           <url-pattern>/</url-pattern>
         </servlet-mapping>
         */
        dispatcher.addMapping(MAPPING_URL);
        logger.debug("exiting AppInitializer.onStartup()");
    }

}
