package ohm.softa.a12.cnjdb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * CNJDB service encapsulating the API instance as singleton
 */
public final class CNJDBService {

    private static final CNJDBApi api;

	private CNJDBService() {}


	/// provide access to singleton
    public static CNJDBApi getInstance() {
        return api;
    }

	/// static initializer for the api instance
    static {
        /* Initialize Retrofit */
        var retrofit = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io")
                .addConverterFactory(GsonConverterFactory.create())
                /* CallAdapterFactory required to wrap calls in CompletableFutures */
                //.addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();

        api = retrofit.create(CNJDBApi.class);
    }
}
