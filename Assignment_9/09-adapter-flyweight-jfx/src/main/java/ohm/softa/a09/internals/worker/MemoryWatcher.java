package ohm.softa.a09.internals.worker;

import javafx.scene.chart.XYChart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

/**
 * Runnable implementation that continuously watches the consumed memory of the application and adds the data to a chart series to be able to display the data
 *
 * @author Peter Kurfer
 */
public final class MemoryWatcher implements Runnable {

	private static final Logger logger = LogManager.getLogger(MemoryWatcher.class);
	private final Consumer<Runnable> dispatcher;
	private final XYChart.Series<Long, Long> memorySeries;
	private final Runtime runtime;
	private long secondsSinceStart = 0;

	/**
	 * Default constructor
	 *
	 * @param memorySeries chart series to store data in
	 * @param dispatcher dispatcher reference to be able to run code in UI thread
	 */
	public MemoryWatcher(final XYChart.Series<Long, Long> memorySeries, final Consumer<Runnable> dispatcher) {
		this.memorySeries = memorySeries;
		this.dispatcher = dispatcher;
		this.runtime = Runtime.getRuntime();
	}

	@Override
	public void run() {
		/* run as long as the current thread is not interrupted */
		while (!Thread.currentThread().isInterrupted()) {

			/* local copy is necessary because variables used in lambdas have to effectively final */
			var currentIdx = secondsSinceStart++;

			/* calculate the amount of memory currently used by the process and add it to the chart series */
			dispatcher.accept(() -> memorySeries.getData().add(new XYChart.Data<>(currentIdx, ((runtime.totalMemory() - runtime.freeMemory()) / (1024L * 1024L)))));
			try {
				/* Sleep 1s till next update */
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.info("Got interrupted, exiting now.");
				return;
			}
		}
	}
}
