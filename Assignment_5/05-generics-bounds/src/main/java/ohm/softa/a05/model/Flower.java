package ohm.softa.a05.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Flower extends Plant{
    private final PlantColor color;
    protected Flower(double height, String family, String name, PlantColor color) {
        super(height, family, name);
        /* ensure that a flower is never green */
        if(color == PlantColor.GREEN){
            throw new IllegalArgumentException("A plant may not be green!");
        }
        this.color = color;
    }

    @Override
    public PlantColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}

        if (!(o instanceof Plant)) {return false;}

        Plant plant = (Plant) o;

        return new EqualsBuilder()
                .append(getHeight(), plant.getHeight())
                .append(getFamily(), plant.getFamily())
                .append(getName(), plant.getName())
                .append(getColor(), plant.getColor())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(12, 37)
                .append(getHeight())
                .append(getFamily())
                .append(getName())
                .append(getColor())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("family", getFamily())
                .append("name", getName())
                .append("height", getHeight())
                .append("color", getColor())
                .toString();
    }
}
