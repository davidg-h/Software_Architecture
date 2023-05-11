package ohm.softa.a08.services;

import ohm.softa.a08.api.OpenMensaAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenMensaAPIService {
	private static OpenMensaAPIService instance;
	private OpenMensaAPI mensaAPIInstance;
	Retrofit retrofit = new Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl("http://openmensa.org/api/v2/")
		.build();
	private OpenMensaAPIService(){
		mensaAPIInstance = retrofit.create(OpenMensaAPI.class);
	}

	public static OpenMensaAPIService getInstance(){
		if (instance == null)
			instance = new OpenMensaAPIService();

		return instance;
	}

	public OpenMensaAPI getAPI(){
		return mensaAPIInstance;
	}
}
