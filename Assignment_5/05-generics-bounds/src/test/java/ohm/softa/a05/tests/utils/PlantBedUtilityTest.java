package ohm.softa.a05.tests.utils;

import ohm.softa.a05.model.Flower;
import ohm.softa.a05.model.Plant;
import ohm.softa.a05.model.PlantBed;
import ohm.softa.a05.model.PlantColor;
import ohm.softa.a05.utils.PlantBedUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlantBedUtilityTest {
    private PlantBed<Flower> flowerBed;

    @BeforeEach
    void setup() {
        flowerBed = new PlantBed<>();

        flowerBed.add(new Flower("Rosa", "Golden Celebration", 0.4, PlantColor.YELLOW));
        flowerBed.add(new Flower("Rosa", "Abracadabra", 0.5, PlantColor.RED));
        flowerBed.add(new Flower("Rosa", "Golden Celebration", 0.3, PlantColor.YELLOW));
        flowerBed.add(new Flower("Rosa", "Golden Celebration", 0.35, PlantColor.YELLOW));
        flowerBed.add(new Flower("Rosa", "Abracadabra", 0.35, PlantColor.RED));
        flowerBed.add(new Flower("Rosa", "Rosa chinensis", 0.35, PlantColor.ORANGE));
        flowerBed.add(new Flower("Rosa", "Rosa as", 0.35, PlantColor.ORANGE));
    }

    @Test
    void testRepot(){
        PlantBed<Plant> result = new PlantBed<>();
        PlantBedUtility.repot(flowerBed, PlantColor.YELLOW, result);

        assertEquals(3, result.size());
        assertEquals(4, flowerBed.size());

        for (Plant p : result.getPlants()){
            assertEquals(PlantColor.YELLOW, p.getColor());
        }

        for (Plant p : flowerBed.getPlants()){
            assertTrue(!p.getColor().equals(PlantColor.YELLOW));
        }
    }
}
