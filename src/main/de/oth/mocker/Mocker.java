package de.oth.mocker;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;

public interface Mocker {

    /**
     *Creates a Mock of a given Class that looses all its Original functionality and just returns null on every methodcall,
     *but loggs all calls to that mocked Object and can verify if a Method with specific Parameters was called
     * @param clazz the class to be mocked
     * @param <T> any class compatible with cglib
     * @return a mock of the given class
     */

    public static <T> T mock(Class<T> clazz) {
        //Enhacer class of cglib
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        MockMethodInterceptor interceptor = new MockMethodInterceptor();
        enhancer.setCallback(interceptor);
        T proxy = (T) enhancer.create();
        return proxy;
    }

    /**
     * creates a spyobject of a given object that will log all method calls and pass on all method calls to the original object
     * @param suspect the object to be spied on
     * @param <T> any class compatible with cglib
     * @return the spy
     */

    public static <T> T spy(T suspect) {
        //Enhancer class of cglib
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(suspect.getClass());
        SpyMethodInterceptor interceptor = new SpyMethodInterceptor(suspect);
        enhancer.setCallback(interceptor);
        //creates a subclass to the suspect with the given MethodInterceptor
        return (T) enhancer.create();
    }

    /**
     * will put the object in a verify state
     * @param mock object whose methods will be verified
     * @param <T> any mockable class
     * @return the mock parameter in a verify state
     */
    public static <T> T verify(T mock) {
        return verify(mock, ((a) -> a == 1));
    }

    /**
     * sets the state to verify, actuall verification is handled in the Method Interceptor implementation
     * @param mock object the following method will be called on
     * @param com compare function to be called during verification
     * @param <T> any class compatible with cglib
     * @return the mock object
     */

    public static <T> T verify(T mock,IntCompare com) {
        //cast to superclass  to get access to the Interceptor to set verify flag and comp
        Factory factory =((Factory) mock);
        VerifyMethodInterceptor interceptor = (VerifyMethodInterceptor) factory.getCallback(0);
        interceptor.setVerification(true);
        interceptor.setComp(com);
        return mock;
    }
    /**
     * will create a lambda that checks equal to amount
     * @param amount Lambda will compare to this value
     * @return implementation of a functional Interface
     * @throws IllegalArgumentException if amount <= 0
     */
    public static IntCompare times(int amount) throws IllegalArgumentException {
        if( amount > 0)
            return (a) -> {
                if( a == amount)
                    return true;
                throw (new AssertionError("was called "+a+((a == 1)?"time " : "times " )
                        + "but should have been called " +amount+((amount == 1)?" time" : " times" )));
            };
        else
            throw (new IllegalArgumentException("Only positiv Integers allowed, for 0 use function never()"));
    }

    /**
     * will create a lambda that checks <= upperBorder
     * @param upperBorder the border that is checked against
     * @return implementation of a functional interface
     */
    public static IntCompare atMost(int upperBorder) {
        return (a) -> {
            if(a <= upperBorder)
                return true;
            throw (new AssertionError("was called "+a+((a == 1)?"time " : "times " )
                    + "but should have been called at Most " +upperBorder+((upperBorder == 1)?" time" : " times" )));
        };
    }

    /**
     * will create a lambda that checks >= lowerBorder
     * @param lowerBorder the border that is checked against
     * @return implementation of a functional interface
     */
    public static IntCompare atLeast(int lowerBorder) {
        return (a) -> {
            if(a >= lowerBorder)
                return true;
            throw (new AssertionError("was called "+a+((a == 1)?"time " : "times " )
                    + "but should have been called at Least " +lowerBorder+((lowerBorder == 1)?" time" : " times" )));
        };
    }

    /**
     * will create a lambda that checks == 0
     * @return implementation of a functional interface
     */
    public static IntCompare never() {
        return (a) -> {
            if(a == 0)
                return true;
            throw (new AssertionError("was called "+a+((a == 1)?"time " : "times " )
                    + "but should have never been called"));
        };
    }
}