package de.oth.mocker;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Class that handles the creation and verification of mock objects
 */
public class MockerSupervisor {

    //flag to indicate whether or not method is to be called or verified
    private static boolean verification = false;
    //Singleton of MockerLogger
    private static MockerLogger logger = MockerLogger.getInstance();
    //Compare Lambda to be set according to User call (atLeast/atMost/Times/never)
    private static IntCompare comp;

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
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            //Code that will be executed instead of normal functionality
            if(verification) {
                verification = false;
                return logger.verify(obj, method, args, comp);
            }
            //logs the Methodcall
            logger.log(obj, method, args);
           return null;
        });
        T proxy = (T) enhancer.create();
        return proxy;
    }


    /**
     * sets the state to verify, actuall verification is handled in the Method Interceptor implementation
     * @param mock object the following method will be called on
     * @param com compare function to be called during verification
     * @param <T> any class compatible with cglib
     * @return the mock object
     */

    public static <T> T verify(T mock,IntCompare com) {
        verification = true;
        comp = com;
        return mock;
    }


    /**
     * creates a spyobject of a given object that will log all method calls and pass on all method calls to the original object
     * @param suspect the object to be spied on
     * @param <T> any class compatible with cglib
     * @return the spy
     */

    public static <T> T createSpy(T suspect) {
        //Enhancer class of cglib
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(suspect.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            //Code that will be executed instead of normal functionality
            if(verification) {
                verification = false;
                return logger.verify(obj, method, args, comp);
            }
            logger.log(obj, method, args);
            //returns the result by incoking the method on the original suspect
            return method.invoke(suspect,args);

        });
        //creates a subclass to the suspect with the given MethodInterceptor
        return (T) enhancer.create();
    }
}


