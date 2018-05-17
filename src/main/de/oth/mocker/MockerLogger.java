package de.oth.mocker;

import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Single entity class that will log method calls of all mocks and spies
 */
public class MockerLogger {

    //the logs
    private Vector<LogEntry> history;

    //private constructor due to singleton implementation
    public MockerLogger () {
            this.history = new Vector<LogEntry>();
    }

    /**
     * counts how often a given method call has happend on given object with given parameters and compares it with comp
     * @param obj the object that invoked the method
     * @param method the method that was invoked
     * @param args the parameters of the method call
     * @param comp the compare function to be used on the result of the log search
     * @return true if compare call of comp returns true
     * @throws AssertionError is thrown when compare of comp returns false
     */
    public boolean verify (Object obj, Method method, Object[] args, IntCompare comp) throws AssertionError {
        LogEntry invokeVerification = new LogEntry(obj, method, args);
        int counter = 0;
        //search through the history for matches
        for (LogEntry current : history) {
            if(current.compareTo(invokeVerification)) {
                counter++;
            }
        }
        //call compare with the amount of times the method call occured in logs
        try {
            return comp.compare(counter);
        } catch (AssertionError e) {
            throw (new AssertionError("Verification Error with function : "+method.getName()+" "+e.getMessage()));
        }
    }

    /**
     * adds a log entry to the logs with the given values
     * @param obj the object that invoked the method
     * @param method the method that was invoked
     * @param args the parameters used to invoke the method
     */
    public void log(Object obj, Method method,Object[] args) {
        history.add(new LogEntry(obj, method, args));
    }

    /**
     * deletes all logs
     * @param verification needs to be true if the logs are to be deleted
     */
    public void deleteLogs(boolean verification) {
        if(verification)
            history = new Vector<LogEntry>();
    }

}
