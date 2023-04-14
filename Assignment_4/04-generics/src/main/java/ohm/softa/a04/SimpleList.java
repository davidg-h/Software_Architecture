package ohm.softa.a04;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public interface SimpleList<T> extends Iterable<T> {
    /**
     * Add a given object to the back of the list.
     */
    void add(T o);

    /**
     * @return current size of the list
     */
    int size();

    /**
     * Generate a new list using the given filter instance.
     *
     * @return a new, filtered list
     */
    default SimpleList<T> filter(SimpleFilter<T> filter) {
        SimpleList<T> result;

        try {
            result = (SimpleList<T>) getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            result = new SimpleListImpl<>();
        }

        for (T object : this) {
            if (filter.include(object)) {
                result.add(object);
            }
        }
        return result;
    }

    default <R> SimpleList<R> map(Function<T, R> transform) {
        SimpleList<R> result;
        try {
            result = (SimpleList<R>) this.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            result = new SimpleListImpl<>();
        }

        for (T item : this) {
            result.add(transform.apply(item));
        }
        return result;
    }

    /**
     * @param clazz Class instance to solve the instantiation problem for non-primitive datatype (Class, Object, ...)
     */
    @SuppressWarnings("unchecked")
    default void addDefault(Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        try {
            this.add(clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
