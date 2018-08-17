package utilities;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

public class ReflectionUtils {
    public static Collection<? extends Class<?>> findAllDomainEntityClasses()
    {
        Set<Class<?>> result = new HashSet<Class<?>>();

        for (String pkgName: new String[] {"domain"}) {
            result.addAll(findCandidatesByAnnotation(Entity.class, pkgName));
        }

        return result;
    }

    public static Collection<? extends Class<?>> findCandidatesByAnnotation(Class<? extends Annotation> aAnnotation, String aRoot)
    {
        List<Class<?>> result = new ArrayList<Class<?>>();

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition)
            {
                return true;
            }
        };

        scanner.addIncludeFilter(new AnnotationTypeFilter(aAnnotation));
        Set<BeanDefinition> canditates = scanner.findCandidateComponents(aRoot);
        for (BeanDefinition beanDefinition : canditates) {
            String classname = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(classname);
                result.add(clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    public static Collection<? extends Class<?>> findAllServiceClasses()
    {
        Set<Class<?>> result = new HashSet<Class<?>>();

        for (String pkgName: new String[] {"services"}) {
            result.addAll(findCandidatesByAnnotation(Service.class, pkgName));
        }
        return result;
    }
}
