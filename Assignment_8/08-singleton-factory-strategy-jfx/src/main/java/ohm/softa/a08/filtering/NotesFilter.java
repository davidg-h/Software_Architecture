package ohm.softa.a08.filtering;

import ohm.softa.a08.model.Meal;

public class NotesFilter extends FilterBase{
	@Override
	protected boolean include(Meal m) {
		return !m.getNotes().contains("mit Soja");
	}
}
