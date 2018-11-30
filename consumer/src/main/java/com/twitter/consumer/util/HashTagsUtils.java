package com.twitter.consumer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashTagsUtils {
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");

    public static Iterator<String> hashTagsFromTweet(String text) {
        List<String> hashTags = new ArrayList<>();
        Matcher matcher = HASHTAG_PATTERN.matcher(text);
        while (matcher.find()) {
            String handle = matcher.group();
            hashTags.add(handle);
        }
        return hashTags.iterator();
    }
}
