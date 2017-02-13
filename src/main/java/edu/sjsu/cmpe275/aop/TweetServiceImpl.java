package edu.sjsu.cmpe275.aop;

import java.io.IOException;

/*
 * Author:	Joji Kubota
 * Date:	10/08/16
 */

public class TweetServiceImpl implements TweetService {
    public void tweet(String user, String message) throws IllegalArgumentException, IOException {
        // For testing
        System.out.println("tweet() method called");

        // When the message is mor than 140 chars.
        if (message.length() > 140) {
            throw new IllegalArgumentException("More than 140 characters.");
        }

        // Random network failure
        if ((int)(Math.random()*5) == 0 || (int)(Math.random()*5) == 1) {
            throw new IOException("Network Failure when tweeting");
        }

//        throw new IOException("Network Failure when tweeting");
    }

    public void follow(String follower, String followee) throws IOException {
        // For testing
        System.out.println("follow() method called");

        // When the followee is 'Bob' simulate network failure.
//        if (followee == "Bob") {
//            throw new IOException("Network Failure when following");
//        }

        // Random network failure
        if ((int)(Math.random()*5) == 0 || (int)(Math.random()*5) == 1) {
            throw new IOException("Network Failure when following");
        }



//        throw new IOException("Network Failure when following");

    }
}
