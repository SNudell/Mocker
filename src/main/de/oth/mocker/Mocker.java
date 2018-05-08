package de.oth.mocker;

public interface Mocker {

    /**
     * creates a mock object of given class
     * @param clazz the clazz to create a mock object off
     * @param <T> any class compatible with cglib
     * @return a mock off given class
     */
    public static <T> T mock(Class<T> clazz) {
        return MockerSupervisor.mock(clazz);
    }

    /**
     * creates a spy object of given object
     * @param suspect object to be spied on
     * @param <T> any class compatible with cglib
     * @return a spy object that will pass on calls to the suspect and log all methodcalls
     */
    public static <T> T spy(T suspect) {
        return MockerSupervisor.createSpy(suspect);
    }

    /**
     * will put the object in a verify state
     * @param mock object whose methods will be verified
     * @param <T> any mockable class
     * @return the mock parameter in a verify state
     */
    public static <T> T verify(T mock) {
        return MockerSupervisor.verify(mock, ((a) -> a == 1));
    }

    /**
     * will put the object in a verify state
     * @param mock object, whose method will be verified
     * @param comp the comparefunction to be used in verification process can be createt by calling {@link  #atLeast}, {@link #atMost}, {@link #times},  {@link #never} )
     * @param <T> any mockable class
     * @return the mock parameter in a verify state
     */
    public static <T> T verify(T mock, IntCompare comp) {
        return MockerSupervisor.verify(mock, comp);
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