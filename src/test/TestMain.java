import org.junit.Assert;
import org.junit.Test;

import javax.accessibility.AccessibleContext;

import de.oth.mocker.LogEntry;
import static de.oth.mocker.Mocker.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestMain {

    @Test
    public void TestMockCreation () {
        ArrayList<String> testMock = mock(ArrayList.class);
        Assert.assertNotNull(testMock);
    }

    @Test
    public void TestMockReturn() {
        ArrayList<String> testMock =  mock(ArrayList.class);
        Assert.assertTrue(testMock.add("") == false);
        Assert.assertTrue(testMock.iterator() == null);
    }

    @Test
    public void TestLogCompare() {
        try {
            LogEntry a = new LogEntry(this, this.getClass().getMethod("toString"),null);
            LogEntry b = new LogEntry(this, this.getClass().getMethod("toString"),null);
            Assert.assertTrue(a.compareTo(b));
        } catch (NoSuchMethodException e) {
            System.err.println("Should have hardcoded better");
        }
    }

    @Test
    public void TestVerifyNever() {
        ArrayList<String> testMock =  mock(ArrayList.class);
        verify(testMock,never()).add("Backfisch");
    }

    @Test
    public void TestVerifyOnce() {
        ArrayList<String> testMock = mock(ArrayList.class);
        testMock.clear();
        verify(testMock).clear();
    }

    @Test
    public void TestVerifyWithArgument() {
        ArrayList<String> testMock = mock(ArrayList.class);
        testMock.add("Backfisch");
        verify(testMock).add("Backfisch");
    }

    @Test
    public void TestVerifyWithArgumentTwice() {
        ArrayList<String> testMock = mock(ArrayList.class);
        testMock.add("Backfisch");
        testMock.add("Backfisch");
        verify(testMock,times(2)).add("Backfisch");
    }

    @Test
    public	void	mockTest1()	{
        List<String> mockObject	= mock(ArrayList.class);

        mockObject.add("John	Doe");
        mockObject.add("Max	Muster");
        mockObject.add("John	Doe");
        mockObject.size();			//	would	return	0	as	mock’s	default	return	value

        verify(mockObject,	times(2)).add("John	Doe");
        verify(mockObject).add("Max	Muster");		//	same	as	times(1)
    }

    @Test (expected = AssertionError.class)
    public void mockTest2() {
        List<String> mockObject	= mock(ArrayList.class);
        mockObject.clear();
        verify(mockObject, never()).clear();

    }

    @Test
    public void atLeastTest() {
        List<String> mockList = mock(ArrayList.class);
            mockList.contains("Backfisch");
            mockList.contains("Backfisch");
        verify(mockList, atLeast(1)).contains("Backfisch");
    }

    @Test (expected = AssertionError.class)
    public void atLeastTest2() {
        List<String> mockList = mock(ArrayList.class);
        mockList.contains("Backfisch");
        mockList.contains("Backfisch");
        verify(mockList, atLeast(4)).contains("Backfisch");
    }

    @Test
    public void atMostTest() {
        List<String> mockList = mock(ArrayList.class);
        mockList.contains("Backfisch");
        mockList.contains("Backfisch");
        verify(mockList, atMost(14)).contains("Backfisch");
    }

    @Test (expected = AssertionError.class)
    public void atMostTest2() {
        List<String> mockList = mock(ArrayList.class);
        mockList.contains("Backfisch");
        mockList.contains("Backfisch");
        verify(mockList, atMost(1)).contains("Backfisch");
    }

    @Test
    public void mockDifferentClasses() {
        Date dateMock = mock(Date.class);
        AccessibleContext aC = mock(AccessibleContext.class);
    }


    @Test
    public void testSpy() {
        List<String> list = new ArrayList<String>();
        List<String> spyList = spy(list);
        spyList.add("Backfisch");
        Assert.assertTrue((spyList.contains("Backfisch")));
        Assert.assertTrue((list.contains("Backfisch")));
    }

    @Test
    public void testSpyVerify() {
        List<String> list = new ArrayList<String>();
        List<String> spyList = spy(list);
        spyList.add("Backfisch");
        spyList.add("Backfisch");
        spyList.contains("Backfisch");
        spyList.get(1);
        spyList.get(1);
        spyList.get(1);
        spyList.get(1);
        for(int i = 0; i< 100; i++) {
            spyList.isEmpty();
        }
        verify(spyList,atMost(5)).get(1);
        verify(spyList, never()).get(0);
        verify(spyList,atLeast(99)).isEmpty();
        verify(spyList,times(2)).add("Backfisch");
    }

}
