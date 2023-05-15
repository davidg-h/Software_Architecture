package ohm.softa.a09.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FighterFactoryTest {

	private final FighterFactory fighterFactory;

	FighterFactoryTest() {
		fighterFactory = new FighterFactory();
	}

	@Test
	void createFighterEnsureDifferentNames() {
		var fighters = new HashSet<>();

		for(var i = 0; i < 25; i++){
			var fighter = fighterFactory.createFighter();
			assertFalse(fighters.contains(fighter.getPilot()));
			fighters.add(fighter.getPilot());
		}
	}

	@Test
	void createFighterEnsureSideOfForce() {
		var groupedBySideOfForce = IntStream.range(0, 100)
			.mapToObj(i -> fighterFactory.createFighter())
			.collect(Collectors.groupingBy(Fighter::getSideOfForce));

		assertEquals(2, groupedBySideOfForce.keySet().size());
	}

	@Test
	void createFighterEnsureTypesPerSideOfForce() {
		var groupedBySideOfForce = IntStream.range(0, 100)
			.mapToObj(i -> fighterFactory.createFighter())
			.collect(Collectors.groupingBy(Fighter::getSideOfForce));

		for(var sideOfForce : groupedBySideOfForce.keySet()) {
			var groupedByType = groupedBySideOfForce.get(sideOfForce)
				.stream()
				.collect(Collectors.groupingBy(Object::getClass));

			assertEquals(3, groupedByType.keySet().size());
		}
	}

}
