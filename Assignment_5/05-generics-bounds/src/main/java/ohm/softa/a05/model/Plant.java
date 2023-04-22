package ohm.softa.a05.model;

public abstract class Plant implements Comparable<Plant>{
    private final double height;
    private final String family;
    private final String name;

    protected Plant(double height, String family, String name){
        if(family == null || family.length() == 0) throw new IllegalArgumentException("Specify a family");
        if(name == null || name.length() == 0) throw new IllegalArgumentException("Specify a name");
        if(height <= 0.0) throw new IllegalArgumentException("Height may not be less or equal zero");
        this.height = height;
        this.family = family;
        this.name = name;
    }

    public double getHeight(){ return height; }
    public String getFamily(){return family;}
    public String getName(){return name;}
    public abstract PlantColor getColor();

    @Override
    public int compareTo(Plant t){
        return Double.compare(this.height, t.getHeight());
    }
}
