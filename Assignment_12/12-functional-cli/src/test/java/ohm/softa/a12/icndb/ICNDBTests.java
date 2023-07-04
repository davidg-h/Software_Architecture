package ohm.softa.a12.icndb;

import ohm.softa.a12.cnjdb.CNJDBApi;
import ohm.softa.a12.cnjdb.CNJDBService;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ICNDBTests {
	private static final Logger logger = Logger.getLogger(ICNDBTests.class.getName());
	private static final int REQUEST_COUNT = 100;

	private CNJDBApi icndbApi;

	public ICNDBTests() {
		this.icndbApi = CNJDBService.getInstance();
	}

	@Test
	void testCollision() throws ExecutionException, InterruptedException {
		var ids = new HashSet<>();
		var requests = 0;
		var collision = false;

		while (requests++ < REQUEST_COUNT) {
			var joke = icndbApi.getRandomJoke().get();

			if (ids.contains(joke.getId())) {
				logger.info(String.format("Collision at joke %s", joke.getId()));
				collision = true;
				break;
			}

			ids.add(joke.getId());
			logger.info(joke.toString());
		}

		assertTrue(collision, String.format("Completed %d requests without collision; consider increasing REQUEST_COUNT", requests));
	}

	@Test
	void testGetRandomJokeByCategory() throws ExecutionException, InterruptedException {
		var j = icndbApi.getRandomJokeByCategory("food").get();
		assertNotNull(j);
		assertTrue(j.getCategories().contains("food"));
		logger.info(j.toString());
	}

	@Test
	void testGetJokeById() throws ExecutionException, InterruptedException {
		var j = icndbApi.getJoke("S5uiluahRM26CTWRZNXfwg").get();
		assertNotNull(j);
		logger.info(j.toString());
	}
}
