package ohm.softa.a09.resource;

import javafx.scene.image.Image;

public class FxImageLoaderAdapter {
	private final ResourceLoader<Image> resourceLoader;

	public FxImageLoaderAdapter(){
		resourceLoader = new ResourceLoader<>(Image::new);
	}

	public Image loadImage(String resourcePath){
		return resourceLoader.loadResource(ClassLoader.getSystemClassLoader(), resourcePath);
	}

	public Image loadImage(ClassLoader resourceContext, String resourcePath){
		return resourceLoader.loadResource(resourceContext, resourcePath);
	}
}
