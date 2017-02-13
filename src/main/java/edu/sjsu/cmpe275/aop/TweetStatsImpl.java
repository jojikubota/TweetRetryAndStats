package edu.sjsu.cmpe275.aop;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * Author:	Joji Kubota
 * Date:	10/08/16
 */

public class TweetStatsImpl implements TweetStats {

    /* Static variables to keep track of stats. */
    public static int lengthOfLongestTweet = 0;
    public static Map<String, List<String>> mostActiveFollower = new TreeMap<String, List<String>>();
    public static Map<String, String> mostProductiveUser = new TreeMap<String, String>();

    /* Reset all the static variables */
    public void resetStats() {
        lengthOfLongestTweet = 0;
        mostActiveFollower.clear();
        mostProductiveUser.clear();
    }

    /* Return the length of the longest tweet. */
    public int getLengthOfLongestTweet() {
        return lengthOfLongestTweet;
    }

    /* Return the user with the biggest list of followees. */
    public String getMostActiveFollower() {
        // Check if anyone followed anyone.
        if (mostActiveFollower.size() == 0) {
            return null;
        }

        // Find out the user having the most followers.
        // Note: mostActiveFollower is sorted already via TreeMap!
        int followeeSize = 0;
        String activeFollower = "";
        for (Map.Entry<String, List<String>> followMap : mostActiveFollower.entrySet()) {
            if (followMap.getValue().size() > followeeSize) {
                followeeSize = followMap.getValue().size();
                activeFollower = followMap.getKey();
            }
        }

        return activeFollower;
    }

    /* Return the user with the longest concatenated tweet. */
    public String getMostProductiveUser() {
        // Check if anyone tweeted.
        if (mostProductiveUser.size() == 0) {
            return null;
        }

        // Find out the user with most tweets.
        // Note: mostProductiveUser is sorted already via TreeMap!
        int tweetSize = 0;
        String productiveUser = "";
        for (Map.Entry<String, String> productiveMap : mostProductiveUser.entrySet()) {
            if (productiveMap.getValue().length() > tweetSize) {
                tweetSize = productiveMap.getValue().length();
                productiveUser = productiveMap.getKey();
            }
        }

        return productiveUser;
    }
}
