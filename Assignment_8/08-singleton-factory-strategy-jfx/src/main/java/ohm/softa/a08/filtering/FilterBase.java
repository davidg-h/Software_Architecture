package ohm.softa.a08.filtering;

import ohm.softa.a08.model.Meal;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FilterBase implements MealsFilter{

	@Override
	public List<Meal> filter(List<Meal> meals){
		return meals.stream()
			.filter(this::include)
			.collect(Collectors.toList());
	}
	protected abstract boolean include(Meal m);
}
