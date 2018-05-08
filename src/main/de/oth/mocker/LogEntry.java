package de.oth.mocker;

import java.lang.reflect.Method;

/**
 * an entry in the log of MockerLogger
 */
public class LogEntry {
    //the object that invoked the method
    private Object invoker;
    //the method that was invoked
    private Method invokedMethod;
    //the parameters the method was called with
    private Object[] args;

    /**
     * constructor to set intern variables to parameters
     */
    public LogEntry(Object invoker, Method invokedMethod, Object[] args) {
        this.invoker = invoker;
        this.invokedMethod = invokedMethod;
        this.args = args;
    }

    public Object getInvoker() {
        return invoker;
    }

    public Method getInvokedMethod() {
        return invokedMethod;
    }

    public Object[] getArgs() { return args; }

    /**
     * @param compare entry to compare to
     * @return true if the argument field of both entries matches
     */
    public boolean compareByArgumentsOnly (LogEntry compare) {
        if (args == null && compare.getArgs() ==null)
            return true;
        if (args == null || compare.getArgs() == null)
            return false;
        if(this.args.length != compare.getArgs().length)
            return false;

        for ( int i = 0; i<this.args.length; i++) {
            if( !(this.args[i].equals( compare.getArgs()[i]) ) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param compare entry to compare to
     * @return true if the entries match
     */
    public boolean compareTo(LogEntry compare) {
        if (this.invoker == compare.getInvoker()
                && this.invokedMethod.equals(compare.getInvokedMethod())
                && compareByArgumentsOnly(compare))
            return true;
        return false;
    }
}
