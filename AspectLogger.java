import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.JoinPoint;

@Aspect
public class AspectLogger {

    // I think this just see everything in its folder, no idea if it sees sub
    // folders
    @Pointcut("within(*) && execution(* *(..))")
    public void matchAllMyMethods() {
    }

    /**
     * Sees exceptions, does not handle exceptions
     */
    @AfterThrowing(value = "matchAllMyMethods()", throwing = "exception")
    public void doSomethingWithException(JoinPoint joinPoint, Throwable exception) {
        System.out.println(exception);
        // TODO: log exception to file
    }
}
