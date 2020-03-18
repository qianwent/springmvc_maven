package com.qwt.springmaven.config;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

public class FWComponentScanFilter extends AbstractClassTestingTypeFilter {

    //TODO: this is to let Spring know to scan the custom annotation --- check config class, need to include filters
    //TODO: but how exactly, Spring knows to create bean with the impl class? where is the logic in Spring?
    // TODO: as far as I know, JDK dynamic proxy need to use interface to create proxy bean, so it's not really how Spring knows
    // TODO: but actually JDK knows to use impl class?
    @Override
    protected boolean match(ClassMetadata classMetadata) {
        boolean flag = false;
        if (classMetadata.getInterfaceNames().length > 0) {
            String[] interfaces = classMetadata.getInterfaceNames();
            for (String interfaceName : interfaces) {
                try {
                    Class<?> intrface = Class.forName(interfaceName);
                    flag = findAnnotation(intrface, Controller.class) != null || findAnnotation(intrface, Service.class) != null;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    break;
                }
            }
        }
        return flag;
    }
}
