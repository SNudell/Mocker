package de.oth.mocker;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MockMethodInterceptor implements MethodInterceptor, VerifyMethodInterceptor {


    //the logger logging all mehtod calls to this object
    private MockerLogger logger;

    //flag to indicate whether or not the object is in a verification state
    private boolean verification;

    //the compare function to be used in verification
    private IntCompare comp;

    public MockMethodInterceptor() {
        this.logger = new MockerLogger();
        this.verification = false;
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
        if (method.getDeclaringClass() == Object.class  )
            return proxy.invokeSuper(obj,args);
        logger.log(obj, method, args);
        return null;

    }


}
