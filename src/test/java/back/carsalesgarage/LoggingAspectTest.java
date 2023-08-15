package back.carsalesgarage;

import back.carsalesgarage.aspects.LoggingAspect;
import com.fasterxml.classmate.Annotations;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@Aspect
public class LoggingAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Signature signature;

    @InjectMocks
    private LoggingAspect loggingAspect;

    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream consoleContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("MockedMethod");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2"});

        consoleContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleContent));
    }

    @After
    public void cleanup() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void testLogMethodExecutionTime() throws Throwable {
        Object result = "mockedResult";
        when(joinPoint.proceed()).thenReturn(result);

        Object returnedResult = loggingAspect.logMethodExecutionTime(joinPoint);

        assertEquals(result, returnedResult);
        verify(joinPoint, times(1)).proceed();

        String expectedLog = "INFO Method: MockedMethod | Inputs: [arg1, arg2] | Output: mockedResult | Execution Time: ";
        assertTrue(consoleContent.toString().contains(expectedLog));
    }
}