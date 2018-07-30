package org.pyx.guava.concurrent;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.TestClass;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * @author pyx
 * @date 2018/7/28
 */
public class BuckerRunner2<T> extends Runner{

    private final TestClass fTestClass;

    public BuckerRunner2(Class clazz){
        this.fTestClass = new TestClass(clazz);
    }
    @Override
    public Description getDescription() {
        Description spec = Description.createSuiteDescription("我自己的测试",this.fTestClass.getJavaClass().getAnnotations());
        return spec;
    }

    @Override
    public void run(final RunNotifier notifier) {
        Iterator i$ = this.fTestClass.getTestMethods().iterator();
        try {
            Object o = fTestClass.getConstructor().newInstance();
            while (i$.hasNext()) {
                Method method = (Method)i$.next();
                MethodRoadie methodRoadie = new MethodRoadie(o,
                    new TestMethod(method, this.fTestClass),
                    notifier, Description.createTestDescription
                    (fTestClass.getJavaClass(), method.getName(), method.getAnnotations()));
                methodRoadie.run();
            }
        }catch (Exception e) {
            e.printStackTrace();

        }
    }
}