package com.foodiefinder.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author sargis on 04/22/23
 * @project foodiefinder-mobile-automation
 */

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        iTestAnnotation.setTimeOut(90000);
        retryTransformer(iTestAnnotation);

    }

    private void retryTransformer(ITestAnnotation iTestAnnotation) {
        iTestAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
