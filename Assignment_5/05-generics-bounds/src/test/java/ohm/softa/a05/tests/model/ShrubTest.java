package ohm.softa.a05.tests.model;

import ohm.softa.a05.model.PlantColor;
import ohm.softa.a05.model.Shrub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShrubTest {
    @Test
    void testShrubColor(){
        Shrub s = new Shrub("Bob", "Aga", 1.5);
        assertEquals(PlantColor.GREEN, s.getColor());
    }
}
