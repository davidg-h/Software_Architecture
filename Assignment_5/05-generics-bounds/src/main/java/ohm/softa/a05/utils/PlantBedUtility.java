package ohm.softa.a05.utils;

import ohm.softa.a05.model.Flower;
import ohm.softa.a05.model.Plant;
import ohm.softa.a05.model.PlantBed;
import ohm.softa.a05.model.PlantColor;

public abstract class PlantBedUtility {
    private PlantBedUtility(){}

    // T = max class Plant (Plant -> children); to = at least T (Super -> Plant)
    public static <T extends Plant> void repot(PlantBed<T> from, PlantColor c, PlantBed<? super T> to){
        for (T p : from.getPlantsByColor(c)){
            from.remove(p);
            to.add(p);
        }
    }
}
