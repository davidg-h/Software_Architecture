package ohm.softa.a12.cnjdb;

import ohm.softa.a12.model.JokeDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

public interface CNJDBApi {
    /**
     * Fetch a random joke
     */
    @GET("/jokes/random")
	CompletableFuture<JokeDto> getRandomJoke();

    /**
     * Fetch a random joke for the given category
     */
    @GET("/jokes/random")
	CompletableFuture<JokeDto> getRandomJokeByCategory(@Query("category") String category);

	/**
	 * Get a list of random jokes matching the search query
	 */
	@GET("/jokes/random")
	CompletableFuture<JokeDto[]> getJokesByQuery(@Query("category") String query);

    /**
     * Fetch the joke corresponding to the given id string
     */
    @GET("/jokes/{id}")
	CompletableFuture<JokeDto> getJoke(@Path("id") String id);
}
