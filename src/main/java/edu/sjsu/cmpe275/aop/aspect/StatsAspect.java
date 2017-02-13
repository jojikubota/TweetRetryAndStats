package edu.sjsu.cmpe275.aop.aspect;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Author:	Joji Kubota
 * Date:	10/08/16
 */

@Aspect
public class StatsAspect {
    /* Define pointcuts */
    /* Every tweet() method */
    @Pointcut("execution(* tweet(..))")
    public void tweet() {}

    /* Every follow() method */
    @Pointcut("execution(* follow(..))")
    public void follow() {}

    /* Define advices */
    /* Update the length of the longest message successfully tweeted */
    @AfterReturning("tweet()")
    public void updateLongestTweet(JoinPoint joinPoint) {
//        System.out.println("AOP updateLongestTweet called");

        // Update the lengthOfLongestTweet if the new tweet is longer than prev.
        String tweet = (String) joinPoint.getArgs()[1];
        if (tweet.length() > TweetStatsImpl.lengthOfLongestTweet) {
            TweetStatsImpl.lengthOfLongestTweet = tweet.length();
        }
    }

    /* Update the user and followee list whether successful or not. */
    @After("follow()")
    public void updateActiveFollower(JoinPoint joinPoint) {
//        System.out.println("AOP updateActiveFollower called");

        // Get the follower & followee
        String follower = (String) joinPoint.getArgs()[0];
        String followee = (String) joinPoint.getArgs()[1];

        // Create a new entry if the follower has no prev followee.
        if (!TweetStatsImpl.mostActiveFollower.containsKey(follower)) {
//            System.out.println("No Follower yet");

            List<String> followeeList = new ArrayList<String>();
            followeeList.add(followee);
            TweetStatsImpl.mostActiveFollower.put(follower, followeeList);
        } else { // Otherwise, add the folowee to the existing list.
//            System.out.println("Followed before");

            // Check for duplicates.
            if (!TweetStatsImpl.mostActiveFollower.get(follower).contains(followee)) {
//                System.out.println("Unique user");
                TweetStatsImpl.mostActiveFollower.get(follower).add(followee);
            }
        }

//        System.out.println("List of followee: " + TweetStatsImpl.mostActiveFollower.get(follower));
    }

    /* Update the total length of all the messages successfully tweeted */
    @AfterReturning("tweet()")
    public void updateProductiveUser(JoinPoint joinPoint) {
//        System.out.println("AOP updateProductiveUser called");

        // Get the user & message
        String user = (String) joinPoint.getArgs()[0];
        String message = (String) joinPoint.getArgs()[1];

        // Create a new entry if the user never tweeted before.
        if (!TweetStatsImpl.mostProductiveUser.containsKey(user)) {
//            System.out.println("First time tweeting");

            TweetStatsImpl.mostProductiveUser.put(user, message);
        } else { // Otherwise, add the concatenated tweet to the list.
//            System.out.println("Tweeted before");

            String concatenatedMessage = TweetStatsImpl.mostProductiveUser.get(user) +
                                message;
            TweetStatsImpl.mostProductiveUser.put(user, concatenatedMessage);

//            System.out.println("All tweets: " + concatenatedMessage);
        }

    }
}
