package edu.sjsu.cmpe275.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import java.io.IOException;

/*
 * Author:	Joji Kubota
 * Date:	10/08/16
 */

@Aspect
public class RetryAspect {
    /* Define pointcut. */
    /* Every method in TweetServiceImpl class. */
    @Pointcut("within(edu.sjsu.cmpe275.aop.TweetServiceImpl)")
    public void allTweetServiceMethods() {}

    /* Define advices */
    /* Retry the method when IOException / network failure occurs */
    @Around("allTweetServiceMethods()")
    public Object retryAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Run the method up to maxRetry times.
        int maxRetry = 2;
        IOException ioException = null;
        for (int i = 0; i <= maxRetry; i++) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (IOException ex) {
                ioException = ex;
//                System.out.println("IOException during retry: " + i);
            }
        }

        // Failed to run the method, throw exception.
        throw ioException;
    }

//    /* Define static retry counter. */
//    private static int retryCounter = 0;
//
//    @AfterReturning(pointcut = "allTweetServiceMethods()")
//    public void succeedAdvice() {
//        // Reset the counter
//        retryCounter = 0;
//
//        System.out.println("Counter Reset");
//    }
//
//    @AfterThrowing(pointcut = "allTweetServiceMethods()", throwing = "ex")
//    public void retryAdvice(JoinPoint joinPoint, IOException ex) throws IOException {
//        // Get the calling instance.
//        TweetService tweetService = (TweetService) joinPoint.getTarget();
//        String methodName = joinPoint.getSignature().getName();
//        String arg0 = (String) joinPoint.getArgs()[0];
//        String arg1 = (String) joinPoint.getArgs()[1];
//
//        System.out.println(arg0 + " " + arg1);
//        System.out.println("retryCounter: " + retryCounter);
//
//        // Retry twice
//        if (retryCounter <= 2) {
//            // Increment the counter
//            retryCounter++;
//
//            System.out.println("Retrying: " + retryCounter);
//
//            // When the calling method is tweet()
//            if (methodName == "tweet") {
//                tweetService.tweet(arg0, arg1);
//            }
//
//            // When the calling method is follow()
//            if (methodName == "follow") {
//                tweetService.follow(arg0, arg1);
//            }
//        } else {
//            // Reset the counter.
//            retryCounter = 0;
//        }
//    }
}
