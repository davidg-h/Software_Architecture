package ohm.softa.a05.collections;

import ohm.softa.a05.model.PlantColor;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
@FunctionalInterface
public interface SimpleFilter<T> {

	/**
	 * Determines if an item matches a condition
	 *
	 * @param item Object to evaluate
	 * @return true if the referenced object should be included.
	 */
	boolean include(T item);
}

