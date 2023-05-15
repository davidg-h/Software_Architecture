package ohm.softa.a09.internals.displaying;

import ohm.softa.a09.model.Fighter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * JavaFX CellValueFactory implementation to display Fighter images
 *
 * @author Peter Kurfer
 */
public final class FighterImageCellValueFactory implements Callback<TableColumn.CellDataFeatures<Fighter, ImageView>, ObservableValue<ImageView>> {

	private final double imageWidth;

	/**
	 * Create a FighterImageCellValueFactory with a custom image width
	 *
	 * @param imageWidth image width to scale the images to
	 */
	public FighterImageCellValueFactory(final double imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * Create a FighterImageCellValueFactory
	 */
	public FighterImageCellValueFactory() {
		this(100d);
	}

	@Override
	public ObservableValue<ImageView> call(final TableColumn.CellDataFeatures<Fighter, ImageView> param) {
		var imageView = new ImageView(param.getValue().getFighterImage());

		imageView.setPreserveRatio(true);
		imageView.setFitWidth(this.imageWidth);


		return new SimpleObjectProperty<>(imageView);
	}

}
