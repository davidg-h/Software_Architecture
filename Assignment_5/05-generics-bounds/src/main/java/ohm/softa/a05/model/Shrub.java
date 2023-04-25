package ohm.softa.a05.model;

public class Shrub extends Plant{
    public Shrub(String name, String family, double height) {
        super(height, family, name);
    }

    @Override
    public PlantColor getColor() {
        return PlantColor.GREEN;
    }
}
