package ohm.softa.a05.model;

public class Shrub extends Plant{
    protected Shrub(double height, String family, String name) {
        super(height, family, name);
    }

    @Override
    public PlantColor getColor() {
        return PlantColor.GREEN;
    }
}
