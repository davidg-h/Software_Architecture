package ohm.softa.a12.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object (DTO) for chucknorris.io
 */
public class JokeDto {

    /**
     * ID of the joke
     */
    private String id;

    /**
     * joke content
     */
	private String value;

    /**
     * Categories of the joke
     */
    private List<String> categories;

    public JokeDto() {
        categories = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

	public String getValue() { return value; }

	public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) {
        if(categories == null) return;
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JokeDto)) return false;
        return this.id.equals(((JokeDto) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("value", value)
                .append("categories", categories)
                .toString();
    }

}
