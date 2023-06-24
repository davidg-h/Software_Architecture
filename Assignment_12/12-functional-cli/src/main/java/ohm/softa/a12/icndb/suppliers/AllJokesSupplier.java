package ohm.softa.a12.icndb.suppliers;

import ohm.softa.a12.icndb.ICNDBApi;
import ohm.softa.a12.icndb.ICNDBService;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 * @author Peter Kurfer
 */

public final class AllJokesSupplier implements Supplier<ResponseWrapper<JokeDto>> {

    /* ICNDB API proxy to retrieve jokes */
    private final ICNDBApi icndbApi;
	private int jokeCount;
	private int retrievedJokes;
	private int latestID;

    public AllJokesSupplier() {
        retrievedJokes = 0;
		latestID = 0;
		icndbApi = ICNDBService.getInstance();

		try {
			jokeCount = icndbApi.getJokeCount().get().getValue();
		} catch (Exception e) {
			jokeCount = 0;
		}
	}

    public ResponseWrapper<JokeDto> get() {
		ResponseWrapper<JokeDto> result = null;

		try {
			result = icndbApi.getJoke(increment()).get();
			retrievedJokes++;
		} catch (Exception e) {
			// ignore Exception
		} finally {
			return result;
		}
    }

	private int increment() {
		/* if all jokes were retrieved - reset counters */
		if(retrievedJokes >= jokeCount){
			retrievedJokes = 0;
			return latestID = 1;
		}
		return ++latestID;
	}

}
