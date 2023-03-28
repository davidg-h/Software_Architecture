package ohm.softa.a03.tests;

import ohm.softa.a03.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Peter Kurfer
 * Created on 10/7/17.
 */
public class CatTest {

	private static final int Sleep = 10;
	private static final int Awake = 15;
	private static final int Digest = 10;

	private Cat animal;

	@BeforeEach
	void setUp() {
		animal = new Cat("Horst", Sleep, Awake, Digest);
	}

	@Test
	void testFeedHungryAnimal() {
		IntStream.range(0, Sleep).forEach(i -> animal.tick());
		assertTrue(animal.isHungry());
		animal.feed();
		assertTrue(animal.isDigesting());
	}

	@Test
	void testFeedNotHungryAnimal(){
		assertFalse(animal.isHungry());
		assertThrows(IllegalStateException.class, () -> animal.feed());
	}

	@Test
	void testAnimalGettingPlayful() {
		//Waiting while animal is sleeping
		IntStream.range(0, Sleep).forEach(i -> animal.tick());
		animal.feed();
		//Waiting while animal is digesting
		IntStream.range(0, Digest).forEach(i -> animal.tick());
		assertTrue(animal.isPlayful());
	}

	@Test
	void testAnimalDying(){
		//Waiting while animal is sleeping and awake before it's going to die
		IntStream.range(0, Sleep + Awake).forEach(i -> animal.tick());
		assertTrue(animal.isDead());
	}

	@Test
	void testAnimalIsGoingToSleepAgain(){
		//Waiting while animal is sleeping
		IntStream.range(0, Sleep).forEach(i -> animal.tick());
		animal.feed();
		//Waiting while animal is digesting and in playful mood
		IntStream.range(0, Awake).forEach(i -> animal.tick());
		assertTrue(animal.isAsleep());
	}
}
