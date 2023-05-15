package ohm.softa.a09.resource;

import java.io.InputStream;
import java.util.function.Function;

/**
 * Abstraction layer to load images transparently
 *
 * @param <T> Type of the resource to produce
 */
public class ResourceLoader<T> {

	private final Function<InputStream, T> resourceInstantiator;

	/**
	 * Create a new ResourceLoader instance
	 *
	 * @param resourceInstantiator producer to create a new instance of the resource to load based on a InputStream
	 */
	public ResourceLoader(final Function<InputStream, T> resourceInstantiator) {
		this.resourceInstantiator = resourceInstantiator;
	}

	/**
	 * Load a resource from a given ClassLoader instance an a passed path to the resource (relative or absolute)
	 *
	 * @param resourceContext ClassLoader instance used to load the resource
	 * @param path absolute or relative path to the resource to load
	 * @return
	 */
	public T loadResource(ClassLoader resourceContext, String path) {
		return resourceInstantiator.apply(resourceContext.getResourceAsStream(path));
	}

}
