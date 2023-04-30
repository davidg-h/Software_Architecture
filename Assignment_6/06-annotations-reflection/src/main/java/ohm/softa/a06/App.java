package ohm.softa.a06;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ohm.softa.a06.model.Joke;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * @author Peter Kurfer
 * Created on 11/10/17.
 */
public class App {

	public static void main(String[] args) throws IOException {
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(Joke[].class, new JokeAdapter())
			.create();

		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.chucknorris.io/jokes/")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build();

		CNJDBApi cnjdbApi = retrofit.create(CNJDBApi.class);
		Call<Joke> call = cnjdbApi.getRandomJoke();
		Response<Joke> response = call.execute();

		if(!response.isSuccessful()){
			throw new IOException("Request failed: " + response.code());
		}

		Joke j = response.body();

		System.out.println(j.toString());
	}

}
