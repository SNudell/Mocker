package de.oth.mocker;


/**
 * Interface to allow both SpyMethodInterceptor and MockMethodInterceptor to be handled in the same verify function
 */
public interface VerifyMethodInterceptor {
    public void setVerification(boolean verification);
    public void setComp(IntCompare comp);
}
