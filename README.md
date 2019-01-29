# Mocker
Assignment for the Advanced Java Programming module
## Mock 
Using `Mocker.mock(AnyClass.class)` you can create a mock object of any desired class.
The mock will return the default value on any method calls.

## Spy
Using `Mocker.spy(anyObject)` you can create spy objects wrapping the specified object.
A spy will pass the method call to the real object and pass along the returned value. So a spy can be used the same way as the real thing.
## Verification
Using `Mocker.verify(mockOrSpyObject, anyIntCompare).anyMethodCall()` you can verify if the called method was invoked the correct number of times on the mock/spy.
Some comparisons are already implemented:

    1. never()
    2. atLeast(int lowerBorder)
    3. times(int amount)
    4. atMost(int upperBorder)
    
Examples can be seen in TestMain.java