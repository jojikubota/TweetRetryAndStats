package edu.sjsu.cmpe275.aop;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;

/*
 * Author:	Joji Kubota
 * Date:	10/08/16
 */

@SpringBootApplication
public class TweetAopApplication {

	public static void main(String[] args) {

		// Init the app context
		ApplicationContext context =
				new GenericXmlApplicationContext("beans.xml");

		// Get tweetService & tweetStats beans.
		TweetService tweetService = (TweetService) context.getBean("tweetService");
		TweetStats tweetStats = (TweetStats) context.getBean("tweetStats");

		try {
			/* Test (tweet size > xx) */
//			tweetService.tweet("John", "12345678901234567890");

			/* Test tweet for frequent network failure & retry */
			tweetService.tweet("John", "Hello");
			tweetService.tweet("John", "Hello");
			tweetService.tweet("John", "Hello");
			tweetService.tweet("John", "Hello");
			tweetService.tweet("John", "Hello");

			/* Test follow for frequent network failure & retry */
			tweetService.follow("John", "Paul");
			tweetService.follow("John", "Paul");
			tweetService.follow("John", "Paul");
			tweetService.follow("John", "Paul");
			tweetService.follow("John", "Paul");

			/* Test getLengthOfLongestTweet() */
			tweetService.tweet("John", "12345");
			tweetService.tweet("Paul", "123");
			tweetService.tweet("Tom", "1234567890");
			tweetService.tweet("Bob", "1");

			/* Test getMostActiveFollower() */
			tweetService.follow("John", "Paul");
			tweetService.follow("Paul", "Tom");
			tweetService.follow("Paul", "Bill");
			tweetService.follow("Bill", "Tom");
			tweetService.follow("Bill", "Bob");

			/* Test getMostProductiveUser() */
			tweetService.tweet("John", "12345");
			tweetService.tweet("Paul", "123456");
			tweetService.tweet("Bob", "123");
			tweetService.tweet("Bob", "12345");
			tweetService.tweet("Alice", "12345678");

		} catch (IllegalArgumentException e) {
			System.out.println("Caught in mainApp: " + e);
		} catch (IOException e) {
			System.out.println("Caught in mainApp: " + e);
		}

		/* Test getLengthOfLongestTweet() */
		System.out.println(tweetStats.getLengthOfLongestTweet());
		/* Test getLengthOfLongestTweet() */
		System.out.println(tweetStats.getMostActiveFollower());
		/* Test getMostProductiveUser() */
		System.out.println(tweetStats.getMostProductiveUser());

		/* Test resteStats() */
		tweetStats.resetStats();

		System.out.println(tweetStats.getLengthOfLongestTweet());
		System.out.println(tweetStats.getMostActiveFollower());
		System.out.println(tweetStats.getMostProductiveUser());
	}
}
