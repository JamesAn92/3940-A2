
import java.io.IOException;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.JoinPoint;

@Aspect
public class AspectLogger {

    @Pointcut("within(*) && execution(* *(..))")
    public void matchAllMyMethods() {
    }

    @AfterThrowing(value = "matchAllMyMethods()", throwing = "exception")
    public void doSomethingWithException(JoinPoint joinPoint, Throwable exception) {
        // get access to the actual exception thrown
        System.out.println(exception);
    }
}
