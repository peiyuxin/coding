package org.pyx.pyx.natives.lang.reflect;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author pyx
 * @date 2018/9/3
 */
public class MethodUtilsTest {

    @Test
    public void testGetAllMethodsOfClass(){
        Class cls = ByteArrayOutputStream.class;
        Method[] methods1 = getAllMethodsOfClass(cls);
        List<Method> methodList1 = new ArrayList(Arrays.asList(methods1));
        Method[] methods2 = cls.getMethods();
        List<Method> methodList2 = new ArrayList(Arrays.asList(methods2));

        methodList1.removeAll(methodList2);
        for (Method m : methodList1) {
            System.out.println(m.toGenericString());
        }
        methodList1 = new ArrayList(Arrays.asList(methods1));
        methodList2.removeAll(methodList1);

        for (Method m : methods1) {
            //System.out.println(m.getName() + m.toGenericString());
        }
        System.out.println(methods1.length);
        for (Method m : methodList2) {
            System.out.println(m.toGenericString());
        }
        for (Method m: methods2) {
            //System.out.println(m.getName());
        }
        System.out.println(methods2.length);
        assertEquals(methods1.length, methods2.length);
    }

    /**
     * 获取<code>clazz</code>下所有带指定<code>Annotation</code>的<code>Method</code>集合。
     * <p>
     * 如果 <code>clazz</code>或<code>annotationType</code>为<code>null</code> ，则返还 <code>null</code>
     *
     * @param clazz 要获取的类
     * @param annotationType 指定的<code>Annotation</code>
     * @return <code>clazz</code>下所有带指定<code>Annotation</code>的 <code>Method</code>集合
     */
    public static <T, A extends Annotation> List<Method> getAnnotationMethods(Class<T> clazz, Class<A> annotationType) {
        if (clazz == null || annotationType == null) {
            return null;
        }
        List<Method> list = new ArrayList<>();

        for (Method method : getAllMethodsOfClass(clazz)) {
            A type = method.getAnnotation(annotationType);
            if (type != null) {
                list.add(method);
            }
        }

        return list;
    }

    /**
     * Gets all methods of the given class that are annotated with the given annotation.
     * @param cls
     *            the {@link Class} to query
     * @param annotationCls
     *            the {@link Annotation} that must be present on a method to be matched
     * @return a list of Methods (possibly empty).
     * @throws IllegalArgumentException
     *            if the class or annotation are {@code null}
     * @since 3.4
     */
    public static List<Method> getMethodsListWithAnnotation(final Class<?> cls, final Class<? extends Annotation> annotationCls) {
        Validate.isTrue(cls != null, "The class must not be null");
        Validate.isTrue(annotationCls != null, "The annotation class must not be null");
        final Method[] allMethods = cls.getMethods();
        final List<Method> annotatedMethods = new ArrayList<Method>();
        for (final Method method : allMethods) {
            if (method.getAnnotation(annotationCls) != null) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    /**
     * 获取类的所有<code>Method</code>，不包括<code>java.lang.Object</code>的 <code>Method</code>
     * <p>
     * 如果<code>clazz</code>为<code>null</code>，返回<code>null</code>
     *
     *
     * @param clazz 要获取的类
     * @return <code>Method</code>数组
     */
    public static Method[] getAllMethodsOfClass(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Method[] methods = null;
        for (Class<?> itr = clazz; hasSuperClass(itr);) {
            methods = ArrayUtils.addAll(itr.getDeclaredMethods(), methods);
            itr = itr.getSuperclass();
        }
        return methods;
    }

    /**
     * 判断是否有超类
     *
     * @param clazz 目标类
     * @return 如果有返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean hasSuperClass(Class<?> clazz) {
        return (clazz != null) && !clazz.equals(Object.class);
    }
}
