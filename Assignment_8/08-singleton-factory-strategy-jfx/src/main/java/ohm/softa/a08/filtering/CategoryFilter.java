package ohm.softa.a08.filtering;

import ohm.softa.a08.model.Meal;

public class CategoryFilter extends FilterBase{

	String category;

	public CategoryFilter(String category){
		this.category = category;
	}
	@Override
	protected boolean include(Meal m) {
		if (category == "vegetarian")
			return m.isVegetarian();
		return !m.getCategory().contains("Schwein");
	}
}
