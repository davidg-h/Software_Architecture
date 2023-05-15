package ohm.softa.a09.controller;

import ohm.softa.a09.internals.displaying.FighterImageCellValueFactory;
import ohm.softa.a09.internals.worker.FighterGenerator;
import ohm.softa.a09.internals.worker.MemoryWatcher;
import ohm.softa.a09.model.Fighter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Peter Kurfer
 */
public final class MainController implements Initializable {

	private static final Logger logger = LogManager.getLogger(MainController.class);

	private final XYChart.Series<Long, Long> memorySeries;
	private Thread fighterGeneratorThread;

	@FXML
	private TableColumn<Fighter, String> darkSideFighterPilotColumn;

	@FXML
	private TableColumn<Fighter, ImageView> darkSideFighterImageColumn;

	@FXML
	private TableColumn<Fighter, String> lightSideFighterPilotColumn;

	@FXML
	private TableColumn<Fighter, ImageView> lightSideFighterImageColumn;

	@FXML
	private ToggleButton generationToggleBtn;

	@FXML
	private TableView<Fighter> lightSideTable;

	@FXML
	private TableView<Fighter> darkSideTable;

	@FXML
	private AreaChart<Long, Long> memoryChart;

	public MainController() {
		memorySeries = new XYChart.Series<>();
	}

	@FXML
	private void handleToggleFighterGeneration() {

		if(generationToggleBtn.isSelected() && fighterGeneratorThread == null) {
			logger.debug("Spawning new FighterGenerator background thread");
			fighterGeneratorThread = new Thread(new FighterGenerator(
				Platform::runLater,
				lightSideTable.getItems(),
				darkSideTable.getItems()),
				"FighterGenerator");

			fighterGeneratorThread.setDaemon(true);
			fighterGeneratorThread.start();

			generationToggleBtn.setText("Stop");
		}else {
			logger.debug("Interrupting FighterGenerator thread");
			fighterGeneratorThread.interrupt();
			fighterGeneratorThread = null;

			generationToggleBtn.setText("Start");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		lightSideFighterImageColumn.setCellValueFactory(new FighterImageCellValueFactory());
		lightSideFighterPilotColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPilot()));

		darkSideFighterImageColumn.setCellValueFactory(new FighterImageCellValueFactory());
		darkSideFighterPilotColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPilot()));

		memoryChart.setTitle("System monitoring");
		memoryChart.getData().add(memorySeries);

		var memoryWatcherThread = new Thread(new MemoryWatcher(memorySeries, Platform::runLater), "MemoryWatcher");
		memoryWatcherThread.setDaemon(true);
		memoryWatcherThread.start();
	}
}
