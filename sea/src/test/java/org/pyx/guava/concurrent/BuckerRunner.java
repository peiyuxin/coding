package org.pyx.guava.concurrent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.TestMethod;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author pyx
 * @date 2018/7/28
 */
public class BuckerRunner<T> extends Runner{

    private final TestClass testClass;
    private final Object childrenLock = new Object();

    // Guarded by childrenLock
    private volatile Collection<FrameworkMethod> filteredChildren = null;

    private volatile RunnerScheduler scheduler = new RunnerScheduler() {
        @Override
        public void schedule(Runnable childStatement) {
            childStatement.run();
        }

        @Override
        public void finished() {

        }
    };

    private final ConcurrentHashMap<FrameworkMethod, Description> methodDescriptions = new ConcurrentHashMap<FrameworkMethod, Description>();



    public BuckerRunner(Class<?> testClass) throws InitializationError {
        this.testClass = createTestClass(testClass);
        //this.setTest(new TestSuite(testClass.asSubclass(TestCase.class)));

        //validate();
    }
    protected TestClass createTestClass(Class<?> testClass) {
        return new TestClass(testClass);
    }

    /**
     * Returns a {@link TestClass} object wrapping the class to be executed.
     */
    public final TestClass getTestClass() {
        return testClass;
    }

    /**
     * Returns a name used to describe this Runner
     */
    protected String getName() {
        return testClass.getName();
    }

    /**
     * @return the annotations that should be attached to this runner's
     *         description.
     */
    protected Annotation[] getRunnerAnnotations() {
        return testClass.getAnnotations();
    }

    @Override
    public Description getDescription() {
        Description description = Description.createSuiteDescription(getName(),
            getRunnerAnnotations());
        for (FrameworkMethod child : getFilteredChildren()) {
            description.addChild(describeChild(child));
        }
        return description;
    }
    /**
     * Returns a {@link Description} for {@code child}, which can be assumed to
     * be an element of the list returned by {@link ParentRunner#getChildren()}
     */
    protected Description describeChild(FrameworkMethod method){
        Description description = methodDescriptions.get(method);

        if (description == null) {
            description = Description.createTestDescription(getTestClass().getJavaClass(),
                testName(method), method.getAnnotations());
            methodDescriptions.putIfAbsent(method, description);
        }

        return description;
    }

    /**
     * Returns the name that describes {@code method} for {@link Description}s.
     * Default implementation is the method's name
     */
    protected String testName(FrameworkMethod method) {
        return method.getName();
    }

    private Collection<FrameworkMethod> getFilteredChildren() {
        if (filteredChildren == null) {
            synchronized (childrenLock) {
                if (filteredChildren == null) {
                    filteredChildren = Collections.unmodifiableCollection(getChildren());
                }
            }
        }
        return filteredChildren;
    }

    protected List<FrameworkMethod> getChildren() {
        return computeTestMethods();
    }

    /**
     * Returns the methods that run tests. Default implementation returns all
     * methods annotated with {@code @Test} on this class and superclasses that
     * are not overridden.
     */
    protected List<FrameworkMethod> computeTestMethods() {
        return getTestClass().getAnnotatedMethods(org.junit.Test.class);
    }


    @Override
    public void run(RunNotifier notifier) {
        System.out.println(111 + "=====================================");
        EachTestNotifier testNotifier = new EachTestNotifier(notifier,
            getDescription());
        try {
            Statement statement = classBlock(notifier);
            statement.evaluate();
        } catch (AssumptionViolatedException e) {
            testNotifier.addFailedAssumption(e);
        } catch (StoppedByUserException e) {
            throw e;
        } catch (Throwable e) {
            testNotifier.addFailure(e);
        }
        System.out.println(222 + "======================================");
    }

    protected Statement classBlock(final RunNotifier notifier) {
        Statement statement = childrenInvoker(notifier);
        if (!areAllChildrenIgnored()) {
            statement = withBeforeClasses(statement);
            statement = withAfterClasses(statement);
            //statement = withClassRules(statement);
        }
        return statement;
    }

    private boolean areAllChildrenIgnored() {
        for (FrameworkMethod child : getFilteredChildren()) {
            if (!isIgnored(child)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isIgnored(FrameworkMethod child) {
        return child.getAnnotation(Ignore.class) != null;
    }
    /**
     * Returns a {@link Statement}: Call {@link #runChild(FrameworkMethod, RunNotifier)}
     * on each object returned by {@link #getChildren()} (subject to any imposed
     * filter and sort)
     */
    protected Statement childrenInvoker(final RunNotifier notifier) {
        return new Statement() {
            @Override
            public void evaluate() {
                runChildren(notifier);
            }
        };
    }

    private void runChildren(final RunNotifier notifier) {
        final RunnerScheduler currentScheduler = scheduler;
        try {
            for (final FrameworkMethod each : getFilteredChildren()) {
                currentScheduler.schedule(new Runnable() {
                    public void run() {
                        BuckerRunner.this.runChild(each, notifier);
                    }
                });
            }
        } finally {
            currentScheduler.finished();
        }
    }

    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);
        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else {
            runLeaf(methodBlock(method), description, notifier);
        }
    }

    private Statement methodBlock(FrameworkMethod method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = methodInvoker(method, test);
        //statement = possiblyExpectingExceptions(method, test, statement);
        //statement = withPotentialTimeout(method, test, statement);
        statement = withBefores(method, test, statement);
        statement = withAfters(method, test, statement);
        //statement = withRules(method, test, statement);
        return statement;
    }

    /**
     * Returns a {@link Statement} that invokes {@code method} on {@code test}
     */
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new InvokeMethod(method, test);
    }

    /**
     * Returns a new fixture for running a test. Default implementation executes
     * the test class's no-argument constructor (validation should have ensured
     * one exists).
     */
    protected Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance();
    }

    /**
     * Runs a {@link Statement} that represents a leaf (aka atomic) test.
     */
    protected final void runLeaf(Statement statement, Description description,
                                 RunNotifier notifier) {
        EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
        eachNotifier.fireTestStarted();
        try {
            statement.evaluate();
        } catch (AssumptionViolatedException e) {
            eachNotifier.addFailedAssumption(e);
        } catch (Throwable e) {
            eachNotifier.addFailure(e);
        } finally {
            eachNotifier.fireTestFinished();
        }

    }
    /**
     * Returns a {@link Statement}: run all non-overridden {@code @BeforeClass} methods on this class
     * and superclasses before executing {@code statement}; if any throws an
     * Exception, stop execution and pass the exception on.
     */
    protected Statement withBeforeClasses(Statement statement) {
        List<FrameworkMethod> befores = testClass
            .getAnnotatedMethods(BeforeClass.class);
        return befores.isEmpty() ? statement :
            new RunBefores(statement, befores, null);
    }

    /**
     * Returns a {@link Statement}: run all non-overridden {@code @AfterClass} methods on this class
     * and superclasses before executing {@code statement}; all AfterClass methods are
     * always executed: exceptions thrown by previous steps are combined, if
     * necessary, with exceptions from AfterClass methods into a
     * {@link org.junit.runners.model.MultipleFailureException}.
     */
    protected Statement withAfterClasses(Statement statement) {
        List<FrameworkMethod> afters = testClass
            .getAnnotatedMethods(AfterClass.class);
        return afters.isEmpty() ? statement :
            new RunAfters(statement, afters, null);
    }

    /**
     * Returns a {@link Statement}: run all non-overridden {@code @Before}
     * methods on this class and superclasses before running {@code next}; if
     * any throws an Exception, stop execution and pass the exception on.
     */
    protected Statement withBefores(FrameworkMethod method, Object target,
                                    Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(
            Before.class);
        return befores.isEmpty() ? statement : new RunBefores(statement,
            befores, target);
    }

    protected Statement withAfters(FrameworkMethod method, Object target,
                                   Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(After.class);
        return afters.isEmpty() ? statement : new RunAfters(statement, afters, target);
    }

    /*protected Statement withPotentialTimeout(FrameworkMethod method, Object test, Statement next) {
        Test annotation = method.getAnnotation(Test.class);
        return expectsException(annotation) ? new ExpectException(next,
            getExpectedException(annotation)) : next;
        //List<FrameworkMethod>
    }*/

    /**
     * Returns a {@link Statement}: run all non-overridden {@code @After}
     * methods on this class and superclasses before running {@code next}; all
     * After methods are always executed: exceptions thrown by previous steps
     * are combined, if necessary, with exceptions from After methods into a
     * {@link MultipleFailureException}.
     */

    public void run1(final RunNotifier notifier) {

        Iterator iter = this.testClass.getAnnotatedMethods(org.junit.Test.class).iterator();
        try {
            Object o = testClass.getJavaClass().getConstructor().newInstance();
            while (iter.hasNext()) {
                Method method = (Method)iter.next();
                org.junit.internal.runners.TestClass tc = new org.junit.internal.runners.TestClass(testClass.getJavaClass());
                MethodRoadie methodRoadie = new MethodRoadie(o,
                    new TestMethod(method, tc),
                    notifier, Description.createTestDescription
                    (testClass.getJavaClass(), method.getName(), method.getAnnotations()));
                methodRoadie.run();
            }
        }catch (Exception e) {
            e.printStackTrace();

        }
    }
}