package ohm.softa.a09.util;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.util.Random;

/**
 * Generator for random pilot names
 * uses the Docker algorithm to generate names
 * @see <a href="https://github.com/moby/moby/blob/master/pkg/namesgenerator/names-generator.go">Docker implementation in go</a>
 * @author Peter Kurfer
 */

public final class NameGenerator {

	private final Random random = new Random();
	private final String[] adjectives;
	private final String[] names;

	/**
	 * Default constructor
	 * fills two the lists of names and adjectives to generate random names
	 */
	public NameGenerator() {
		var gson = new Gson();

		adjectives = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/nameGenerator/adjectives.json")), String[].class);
		names = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/nameGenerator/well-known-persons.json")), String[].class);
	}

	/**
	 * Generate a random name for a pilot
	 * @return random name in the format '[adjective] [well known person]'
	 */
	public String generateName(){
		var randomAdjective = adjectives[random.nextInt(adjectives.length - 1)];
		var randomName = names[random.nextInt(names.length - 1)];
		return String.format("%s %s", randomAdjective, randomName);
	}
}
