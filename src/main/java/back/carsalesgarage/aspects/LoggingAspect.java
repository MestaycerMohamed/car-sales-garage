package back.carsalesgarage.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* back.carsalesgarage.*.*.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("INFO Method: " + joinPoint.getSignature().toShortString() +
                " | Inputs: " + Arrays.toString(joinPoint.getArgs()) +
                " | Output: " + result +
                " | Execution Time: " + executionTime + "ms");

        return result;
    }
}