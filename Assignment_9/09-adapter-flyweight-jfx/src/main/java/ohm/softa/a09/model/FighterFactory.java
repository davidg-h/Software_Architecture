package ohm.softa.a09.model;

import ohm.softa.a09.model.empire.TieBomber;
import ohm.softa.a09.model.empire.TieFighter;
import ohm.softa.a09.model.empire.TieInterceptor;
import ohm.softa.a09.model.rebellion.AWing;
import ohm.softa.a09.model.rebellion.XWing;
import ohm.softa.a09.model.rebellion.YWing;
import ohm.softa.a09.resource.FxImageLoaderAdapter;
import ohm.softa.a09.resource.ResourceLoader;
import ohm.softa.a09.util.NameGenerator;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Factory to create new fighters
 * Creates random fighters
 *
 * @author Peter Kurfer
 */
public final class FighterFactory {

	private static final int NumberOfKnownFighterTypes = 6;
	private final Random random;
	private final NameGenerator nameGenerator;
	private final FxImageLoaderAdapter imageLoader = new FxImageLoaderAdapter();

	public FighterFactory() {
		nameGenerator = new NameGenerator();
		random = new Random();
	}

	/**
	 * Create a random Fighter
	 *
	 * @implNote the method has an implementation flaw because it always loads the fighters image
	 * @return a new Fighter instance
	 */
	public Fighter createFighter() {
		switch (random.nextInt(NumberOfKnownFighterTypes)) {
			case 0:
				return new AWing(nameGenerator.generateName(), loadImage(ClassLoader.getSystemClassLoader(), "fighter/awing.jpg"));
			case 1:
				return new XWing(nameGenerator.generateName(), loadImage(ClassLoader.getSystemClassLoader(), "fighter/xwing.jpg"));
			case 2:
				return new YWing(nameGenerator.generateName(), loadImage(ClassLoader.getSystemClassLoader(), "fighter/ywing.jpg"));
			case 3:
				return new TieBomber(nameGenerator.generateName(), loadImage(ClassLoader.getSystemClassLoader(), "fighter/tiebomber.jpg"));
			case 4:
				return new TieFighter(nameGenerator.generateName(), loadImage(ClassLoader.getSystemClassLoader(), "fighter/tiefighter.jpg"));
			default:
				return new TieInterceptor(nameGenerator.generateName(), loadImage(ClassLoader.getSystemClassLoader(), "fighter/tieinterceptor.jpg"));
		}
	}

	/// store images by pathname, once loaded
	private Map<String, Image> cache = new HashMap<>();

	/// load on demand, return from cache
	private Image loadImage(ClassLoader cl ,String path){
		if (cache.containsKey(path))
			return cache.get(path);

		Image instance = imageLoader.loadImage(path);
		cache.put(path, instance);

		return instance;
	}
}
