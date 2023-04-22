package ohm.softa.a05.tests.model;

import ohm.softa.a05.model.PlantBed;
import ohm.softa.a05.collections.SimpleList;
import ohm.softa.a05.model.Flower;
import ohm.softa.a05.model.PlantColor;
import ohm.softa.a05.model.Shrub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlantBedTest {

    private PlantBed<Flower> flowerBed;
    private PlantBed<Shrub> shrubBed;

    @BeforeEach
    void setUp(){
        flowerBed = new PlantBed<>();
        shrubBed = new PlantBed<>();

        flowerBed.add(new Flower("Rosa", "Person Specter Litt", 0.5, PlantColor.BLUE));
        flowerBed.add(new Flower("Jessica", "Person Specter Litt", 0.8, PlantColor.RED));
        flowerBed.add(new Flower("Mike", "Person Specter Litt", 0.3, PlantColor.RED));
        flowerBed.add(new Flower("Harvey", "Person Specter Litt", 0.1, PlantColor.RED));
        flowerBed.add(new Flower("Louis", "Person Specter Litt", 0.33, PlantColor.RED));

        shrubBed.add(new Shrub("Buxus", "Buxus sempervirens", 1.5));
        shrubBed.add(new Shrub("Buxus", "Buxus sempervirens", 1.1));
        shrubBed.add(new Shrub("Buxus", "Buxus sempervirens", 1.2));
        shrubBed.add(new Shrub("Buxus", "Buxus sempervirens", 1.4));
    }
    @Test
    void testGetPlantsByColor(){
        SimpleList<Flower> result = flowerBed.getPlantsByColor(PlantColor.RED);

        for (Flower f : result){
            assertEquals(PlantColor.RED, f.getColor());
        }
    }

    @Test
    void testCreateEmptyPlantBed() {
        PlantBed<Flower> plantBed = new PlantBed<>();
        assertEquals(0, plantBed.size());
    }

    @Test
    void testGetSize() {
        assertEquals(4, shrubBed.size());
        assertEquals(5, flowerBed.size());
    }

    @Test
    void testGetPlantsByColorGreen() {
        SimpleList<Flower> flowers = flowerBed.getPlantsByColor(PlantColor.GREEN);
        assertEquals(0, flowers.size());

        SimpleList<Shrub> shrubs = shrubBed.getPlantsByColor(PlantColor.GREEN);
        assertEquals(4, shrubs.size());
    }
}
