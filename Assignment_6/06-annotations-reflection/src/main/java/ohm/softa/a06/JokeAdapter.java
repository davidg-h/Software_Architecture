package ohm.softa.a06;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ohm.softa.a06.model.Joke;

import java.awt.*;
import java.io.IOException;

public class JokeAdapter extends TypeAdapter<Joke[]> {
	private final Gson gson;
	public JokeAdapter() {
		this.gson = new Gson();
	}
	@Override
	public void write(JsonWriter out, Joke[] value) throws IOException {
		//Note: There is no need to implement the write method, since we're only consuming the API, but not sending to it.
	}
	@Override
	public Joke[] read(JsonReader in) throws IOException {
		Joke[] result = null;
		/* start to read from JsonReader */
		in.beginObject();

		/* iterate the reader (iterator!) */
		while (in.hasNext()) {

			/* switch-case on String (supported since Java 8) */
			switch (in.nextName()) {

				case "total":
					/* skip the total field */
					in.skipValue();
					break;

				/* serialize the inner value simply by calling Gson because we mapped the fields to JSON keys */
				case "result":
					result = gson.fromJson(in, Joke[].class);
					break;
			}
		}

		/* required to fix JSON document not fully consumed exception */
		in.endObject();

		return result;
	}
}
