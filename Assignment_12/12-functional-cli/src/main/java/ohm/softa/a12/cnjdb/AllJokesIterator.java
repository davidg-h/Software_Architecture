package ohm.softa.a12.cnjdb;

import ohm.softa.a12.model.JokeDto;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Iterator to retrieve all jokes of the ICNDB until collision.
 */
public final class AllJokesIterator implements Iterator<JokeDto> {

    /* ICNDB API proxy to retrieve jokes */
    private final CNJDBApi icndbApi = CNJDBService.getInstance();;
	private final Set<String> ids = new HashSet<>();
	private JokeDto cur = retrieve();

	@Override
	public boolean hasNext() {
		return cur != null;
	}

	@Override
	public JokeDto next() {
		JokeDto temp = cur;
		cur	= retrieve();
		return temp;
	}

	private JokeDto retrieve(){
		try {
			return icndbApi.getRandomJoke()
				.thenApply((response) -> {
					if (ids.contains(response.getId())) return null;
					else ids.add(response.getId());
					return response;
				})
				.get();
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}
}
