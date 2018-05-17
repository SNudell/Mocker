package de.oth.mocker;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @param <T> the suspect ot be spied on
 */
public class SpyMethodInterceptor <T> implements MethodInterceptor, VerifyMethodInterceptor {

    //the logger logging all mehtod calls to this object
    private MockerLogger logger;

    //flag to indicate whether or not the object is in a verification state
    private boolean verification;

    //the compare function to be used in verification
    private IntCompare comp;

    //the suspect to be spied on
    private T suspect;

    public SpyMethodInterceptor(T suspect) {
        this.logger = new MockerLogger();
        this.verification = false;
        this.comp = null;
        this.suspect = suspect;
    }

    public void setComp(IntCompare comp) {
        this.comp = comp;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        //Code that will be executed instead of normal functionality
        if(verification) {
            verification = false;
            return logger.verify(obj, method, args, comp);
        }
        logger.log(obj, method, args);
        //returns the result by invoking the method on the original suspect
        return method.invoke(suspect,args);

    }
}
