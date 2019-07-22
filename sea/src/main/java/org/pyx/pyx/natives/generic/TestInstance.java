package org.pyx.pyx.natives.generic;

/**
 * 使用instanceof会失败，是因为类型信息已经被擦除，因此我们可以引入类型标签Class< T>，就可以转用动态的isInstance()。
 * @author pyx
 * @date 2018/7/19
 */
public class TestInstance<T> {
    private Class<T> t;
    public TestInstance(Class<T> t){
        this.t = t;
    }

    public boolean compare(Object obj){
        return t.isInstance(obj);
    }

    public static void main(String[] args) {

        TestInstance<A> a = new TestInstance<>(A.class);
        System.out.println(a.compare(new A()));
        System.out.println(a.compare(new B()));

    }

}
class A{}
class B extends A{}