

import java.io.IOException;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.JoinPoint;

@Aspect
public class AspectLogger {

    @Pointcut("execution(public static void Server.main(String[]))")
    public void stuff() {

    }

    @After("stuff()")
    public void after(){
        System.out.println("cum");
    }

    @AfterThrowing (pointcut = "stuff()", throwing = "e")
    private void afterTestCall(JoinPoint thisJoinPoint, Exception e) {
        System.out.println("Logging before APIError:test is called...");
    }
    
}
