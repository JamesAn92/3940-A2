package exceptions;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.JoinPoint;

@Aspect
public class AspectLogger {

    // Sees all packages
    @Pointcut("within(*) && execution(* *(..))")
    public void matchAllMyMethods() {
    }

    /**
     * Sees exceptions thrown by methods that throw exceptions
     */
    @AfterThrowing(value = "matchAllMyMethods()", throwing = "exception")
    public void doSomethingWithException(JoinPoint joinPoint, Throwable exception) {
        System.out.println("AspectJ " + exception);
        // TODO: log exception to file
    }
}
