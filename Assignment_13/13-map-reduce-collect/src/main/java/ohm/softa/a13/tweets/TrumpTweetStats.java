package ohm.softa.a13.tweets;

import ohm.softa.a13.model.Tweet;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrumpTweetStats {

    public static Map<String, Long> calculateSourceAppStats(Stream<Tweet> tweetStream) {
		return tweetStream
			/* transform every Tweet object to the plain source app name */
			.map(Tweet::getSourceApp)
			/* collect the tweets in a map of String and Integer - short form of map-reduce */
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public static Map<String, Set<Tweet>> calculateTweetsBySourceApp(Stream<Tweet> tweetStream) {
        /* collect the tweets in `Set`s for each source app */
		return tweetStream.collect(Collectors.groupingBy(Tweet::getSourceApp, Collectors.toSet()));
    }

	private static final Pattern ALPHA_NUMERICAL = Pattern.compile("[a-z0-9@]+");
    public static Map<String, Integer> calculateWordCount(Stream<Tweet> tweetStream, List<String> stopWords) {
        return tweetStream
			.map(Tweet::getText)
			.map(tweet -> tweet.split("( )+"))
			.flatMap(x -> Arrays.stream(x))
			.map(String::toLowerCase)
			.filter(w -> stopWords.stream().noneMatch(sw -> sw.equals(w)))
			.reduce(new LinkedHashMap<String, Integer>(), (acc, word) -> {
				/* put the current word to the map - overwrites entry but computes new value first
				 * by calling `.compute` on the map that passes the current value or null to the lambda depending on the key (already exists or not) */
				acc.put(word, acc.compute(word, (k, v) -> v == null ? 1 : v + 1));
				return acc;
			}, (m1, m2) -> m1);
    }
}
