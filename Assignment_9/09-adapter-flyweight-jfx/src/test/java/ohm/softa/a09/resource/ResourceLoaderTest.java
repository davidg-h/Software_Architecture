package ohm.softa.a09.resource;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ResourceLoaderTest {

	private final ResourceLoader<String> stringResourceLoader;

	ResourceLoaderTest() {
		stringResourceLoader = new ResourceLoader<>(is -> {
			try(var bufferedReader = new BufferedReader(new InputStreamReader(is))){
				return bufferedReader.lines().collect(Collectors.joining());
			} catch (IOException e) {
				return "";
			}
		});
	}

	@Test
	void loadResource() {
		assertTrue(stringResourceLoader.loadResource(ClassLoader.getSystemClassLoader(), "demoText.txt").startsWith("Lorem ipsum dolor sit amet"));
	}

	@Test
	void loadResourceFailing() {
		assertThrows(NullPointerException.class, () -> stringResourceLoader.loadResource(ClassLoader.getSystemClassLoader(), "asdf.txt"));
	}
}
