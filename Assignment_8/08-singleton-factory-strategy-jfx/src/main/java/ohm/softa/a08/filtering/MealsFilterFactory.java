package ohm.softa.a08.filtering;

public abstract class MealsFilterFactory {
	public static MealsFilter getStrategy(String key){
		MealsFilter factoryOutput = null;
		switch (key.toLowerCase()){
			case "vegetarian":
			case "no pork":
				factoryOutput = new CategoryFilter(key.toLowerCase());
				break;
			case "no soy":
				factoryOutput = new NotesFilter();
				break;
			default:
				factoryOutput = new NoFilter();
				break;
		}
		if (factoryOutput == null)
			throw new NullPointerException("no strategy selected");

		return factoryOutput;
	}
}
