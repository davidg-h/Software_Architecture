_This is an assignment to the [Software Architecture](https://ohm-softa.github.io) class at the [Technische Hochschule Nürnberg](http://www.th-nuernberg.de)._

# Assignment 13: Map-Reduce and Collect

This assignment covers some of the more advanced concepts of the Java 8 `Stream`s API:

* _Map-Reduce_
* _Collecting_
* _Grouping-By_

We'll use _Map-Reduce_ to implement the classic _word count_ example.
The sample repository contains 3.000 tweets of Donald Trump which we will analyze in this assignment.

A clever data scientist discovered that most of the angrier tweets came from Android where the nicer ones were written with an iPhone ([first article](http://varianceexplained.org/r/trump-tweets/) and [follow up article](http://varianceexplained.org/r/trump-followup/)).
We will have a look if we can use Java's `Stream`s to group the tweets by the kind of client which was used to create them.


## Setup

1. Create a fork of this repository (button in the right upper corner)
1. Clone the project (get the link by clicking the green _Clone or download button_)
1. Import the project to your **IntelliJ**
1. **Read the whole assignment spec!**

_Remark: the given test suite is incomplete and **will not** succeed right after checkout!_


## Generators

To be able to analyze the tweets, we need a generator which loads the given tweets from a JSON file and exposes them as a `Stream`.

The following UML shows the class structure of the generator and the factory function `fromJson`.

![Generator class spec](./assets/images/GeneratorSpec.svg)

_Remark: the UML is not complete but just meant as implementation guideline and for orientation._

_Hint: the dependency to GSON is already added to your `build.gradle` and GSON can be used to deserialize tweets:_

```java
Gson gson = new Gson();
Tweet[] tweets = gson.fromJson(reader, Tweet[].class);
```

Where `reader` is an instance of the abstract class `Reader`.
To access files from the `resources` folder, use the `<Class class="getResourceAsStream` method:

```java
getClass().getResourceAsStream("/path/to/trump_tweets.json");
```

_Side note: this is only possible if you are in a non `static` context, otherwise you have to write something like this: `MyClass.class.getResourceAsStream(...)`._

Implement the `TweetStreamGenerator` by using GSON; the class already contains a `List<Tweet>` to store the tweets and to get the stream.

Advanced variants:

- Use the `try-with-resources` statement for the deserialization of the tweets
- For a more functional way, read up on this [Gist](https://gist.github.com/baez90/659d121064ff102a4e1e6a31bcf639c4).


## Collecting

Up to now, `Stream`s are nice to have but printing all results to the command line with the terminator `forEach(...)` is not really practicable.

To be able to process the data we need to `collect` them, e.g. in a `List<>` or a `Set<>` or any other _Collection_.

Fortunately, the `Stream` API already defines a method `.collect(...)` and the JDK contains the utility class `Collectors` which defines the most common collectors.

Once the `OfflineTweetStreamGenerator` works, we can start to analyze the tweets in the class `TrumpTweetStats`.
The following UML contains the signatures of the methods to be implemented in this assignment.

![TrumpTweetStats spec](./assets/images/TrumpTweetStats.svg)

The method stubs are given already; the UML is only meant to keep the signatures in mind while you're reading the spec.

The first two tasks are the implementation of the methods:

* `calculateSourceAppStats`
* `calculateTweetsBySourceApp`

### `calculateSourceAppStats`

This method groups the tweets by the app they were created with and counts how many tweets were created with which app.
In SQL you would write it like this:

```sql
SELECT source, count(*) FROM tweets GROUP BY source
```

If you want to try it on your own: the repository contains a SQL script that creates a `tweet` table and insert all the tweets to it.
The script has been tested on MSSQL and PostgreSQL but if you want to use it on MySQL or MariaDB some additional work might be necessary.

Every `Tweet` instance has two methods to access the `source`:

* `getSource()`
* `getSourceApp()`

The first one returns a string like this: `<a href="http://twitter.com/download/iphone" rel="nofollow">Twitter for iPhone</a>`.
The second one extracts the actual name of the app `Twitter for iPhone`.
It does not matter for the assignment which one you choose but the second one is a little bit prettier when the result is printed.

_Side note: the task is thought to be solved with `collect` but it's also possible using `reduce`!_

### `calculateTweetsBySourceApp`

This method is very similar to `calculateSourceAppStats` but instead of just counting the tweets, it collects them as a `Set<Tweet>` for further analysis.

While the method `calculateSourceAppStats` was very easy to implement with SQL, this method is impossible to implement in SQL because SQL does not define a map of lists (i.e. a tuple of tuples)!

_Side note: the task is thought to be solved with `collect` but it's also possible using `reduce`!_

## Map-Reduce

A classical algorithm used to process huge amounts of data is _Map-Reduce_.
As the name already indicates the algorithm consists of two steps:

* _Map_ - transform the data (in parallel)
* _Reduce_ - retrieve all partial results and aggregate them

If you're looking for examples of _Map-Reduce_, the first hit will most likely be the word count problem.
It's relatively simple to implement as there's not much transformation required and it demonstrates the concept very well.

We want to analyze which words are the most common in the given tweets.
The following flow chart is meant as orientation how to implement the _Map-Reduce_ algorithm.

![Map-Reduce flow chart](./assets/images/MapReduce-WordCount.svg)

The text of the tweet can be split like this:

```java
String[] split = "Hello World".split("( )+");
```

The `reduce` method requires a so called accumulator.
For this method, an instance of `HashMap<>` or `LinkedHashMap<>` seems to be a good idea.
The next part is the reduction step and should be an instance of `BiFunction<>`: a function where the accumulator and a single value is passed in and the accumulator is returned after the value is processed (e.g. inserted to a list).
(The last part is a _combiner_ that combines two accumulator values but you won't need it this time.)

_Debugging hint: the latest IntelliJ Idea ships with the plugin [Java Stream Debugger](https://plugins.jetbrains.com/plugin/9696-java-stream-debugger). The plugin visualizes how the stream is transformed step by step (including the actual data). That's very helpful if something is happening you don't expect to happen!_

_Another hint: the given 3225 tweets are a little bit too much to debug your `Stream` if something is going wrong. It may help to `limit` the `Stream` you're passing to the method `calculateWordCount` to e.g. 200 elements!_

_Last but not least: the Map-Reduce algorithm is a little bit tricky when you implement it for the first time. If you're stuck, Google can help you!_

There are already some unit tests but it might be a good idea to extend the test suite.
