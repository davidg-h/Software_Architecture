package ohm.softa.a05.model;

import ohm.softa.a05.collections.SimpleFilter;
import ohm.softa.a05.collections.SimpleList;
import ohm.softa.a05.collections.SimpleListImpl;

import java.awt.*;
import java.util.Objects;

public class PlantBed <T extends Plant>  {
    private SimpleList<T> plants;

    public PlantBed(){
        plants = new SimpleListImpl<>();
    }

    public SimpleList<T> getPlants() {return plants;}
    public void add(T plant){
        plants.add(plant);
    }

    public int size(){
        return plants.size();
    }

    public SimpleList<T> getPlantsByColor(PlantColor color){
        return plants.filter(new SimpleFilter<T>() {
            @Override
            public boolean include(T plant) {
                return plant.getColor() == color;
            }
        });
    }

    public void remove(T plant){
        plants = plants.filter(o -> !o.equals(plant));
    }
}
