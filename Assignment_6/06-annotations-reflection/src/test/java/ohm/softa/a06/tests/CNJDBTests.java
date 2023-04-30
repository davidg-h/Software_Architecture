package ohm.softa.a06.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ohm.softa.a06.CNJDBApi;
import ohm.softa.a06.JokeAdapter;
import ohm.softa.a06.model.Joke;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Peter Kurfer
 * Created on 11/10/17.
 */
class CNJDBTests {

	private static final Logger logger = LogManager.getLogger(CNJDBTests.class);
	private static final int REQUEST_COUNT = 100;
	private CNJDBApi CNJDBApi;
	@BeforeEach
	void setup() {
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(Joke[].class, new JokeAdapter())
			.create();

		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create(gson))
			.baseUrl("https://api.chucknorris.io/jokes/")
			.build();

		CNJDBApi = retrofit.create(CNJDBApi.class);
	}
	@Test
	void testCollision() throws IOException {
		Set<String> jokeNumbers = new HashSet<>();
		int requests = 0;
		boolean collision = false;

		while (requests++ < REQUEST_COUNT) {
			Call<Joke> jokeCall = CNJDBApi.getRandomJoke();
			Response<Joke> jokeResponse = jokeCall.execute();

			if (!jokeResponse.isSuccessful()) continue;

			Joke j = jokeResponse.body();

			if (jokeNumbers.contains(j.getIdentifier())) {
				logger.info(String.format("Collision at joke %s", j.getIdentifier()));
				collision = true;
				break;
			}

			jokeNumbers.add(j.getIdentifier());
			logger.info(j.toString());
		}

		assertTrue(collision, String.format("Completed %d requests without collision; consider increasing REQUEST_COUNT", requests));
	}

	@Test
	void testJokeByCategory() throws IOException {
		String[] categories = {"animal"};

		Joke joke = CNJDBApi.getRandomJoke(categories).execute().body();

		assertNotNull(joke);
		logger.info(joke.getContent());
		logger.warn(joke.getRubrics());
		assertTrue(joke.getRubrics().contains("animal"));
	}

	@Test
	void testJokeById() throws IOException {
		Joke joke = CNJDBApi.getJoke("1bnwenwlt02zxm8iu7ahsq").execute().body();

		assertNotNull(joke);
		assertEquals("1bnwenwlt02zxm8iu7ahsq", joke.getIdentifier());
		assertTrue(joke.getContent().contains("Once you go Norris"));
	}

	@Test
	void testJokeByQuery() throws IOException {
		Joke[] jokes = CNJDBApi.getJokesBySearch("horse").execute().body();

		assertNotNull(jokes);
		for (Joke j : jokes) {
			assertNotNull(j);
			logger.info(j.getContent());
			assertTrue(j.getContent().toLowerCase().contains("horse"));
		}
	}
}
