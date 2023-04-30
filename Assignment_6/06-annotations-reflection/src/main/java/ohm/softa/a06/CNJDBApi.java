package ohm.softa.a06;


import ohm.softa.a06.model.Joke;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CNJDBApi {

	@GET("random")
	Call<Joke> getRandomJoke();

	@GET("random")
	Call<Joke> getRandomJoke(@Query("category") String[] categories);
	@GET("search")
	Call<Joke[]> getJokesBySearch(@Query("query") String query);
	@GET("{id}")
	Call<Joke> getJoke(@Path("id") String id);
}
