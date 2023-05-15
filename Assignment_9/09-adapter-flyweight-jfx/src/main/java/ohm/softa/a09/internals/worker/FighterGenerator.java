package ohm.softa.a09.internals.worker;

import ohm.softa.a09.model.Fighter;
import ohm.softa.a09.model.FighterFactory;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author Peter Kurfer
 */
public final class FighterGenerator implements Runnable {

	private static final Logger logger = LogManager.getLogger(FighterGenerator.class);
	private final FighterFactory fighterFactory;
	private final Random random;
	private final Deque<Fighter> queue;
	private final Consumer<Runnable> dispatcher;
	private final ObservableList<Fighter> lightSideFighters;
	private final ObservableList<Fighter> darkSideFighters;
	private final int insertThreshold;

	/**
	 * Create a new FighterGenerator initialized with a custom threshold when Fighters are moved to the UI
	 *
	 * @param dispatcher dispatcher reference to be able to run code in UI thread
	 * @param lightSideFighters reference list of Fighter of the light side
	 * @param darkSideFighters reference list of Fighter of the dark side
	 * @param insertThreshold threshold when created Fighters should be moved to UI/table views
	 */
	public FighterGenerator(final Consumer<Runnable> dispatcher, final ObservableList<Fighter> lightSideFighters, final ObservableList<Fighter> darkSideFighters, final int insertThreshold) {
		this.dispatcher = dispatcher;
		this.lightSideFighters = lightSideFighters;
		this.darkSideFighters = darkSideFighters;
		this.insertThreshold = insertThreshold;
		this.fighterFactory = new FighterFactory();
		this.random = new Random();
		this.queue = new ArrayDeque<>();
	}

	/**
	 * Create a new FighterGenerator
	 *
	 * @param dispatcher dispatcher reference to be able to run code in UI thread
	 * @param lightSideFighters reference list of Fighter of the light side
	 * @param darkSideFighters reference list of Fighter of the dark side
	 */
	public FighterGenerator(final Consumer<Runnable> dispatcher, final ObservableList<Fighter> lightSideFighters, final ObservableList<Fighter> darkSideFighters) {
		this(dispatcher, lightSideFighters, darkSideFighters, 10);
	}

	@Override
	public void run() {
		/* run as long as the current thread is not interrupted */
		while (!Thread.currentThread().isInterrupted()) {
			try {
				if (queue.size() >= insertThreshold) {
					logger.info("Pushing fighters to view");
					queue.forEach(fighter -> dispatcher.accept(() -> {
						switch (fighter.getSideOfForce()) {
							case DarkSide:
								darkSideFighters.add(fighter);
								break;
							case LightSide:
								lightSideFighters.add(fighter);
								break;
						}
					}));
					queue.clear();
				} else {
					logger.info("Enqueuing new fighter");
					queue.push(this.fighterFactory.createFighter());
				}
				logger.debug("Fighter generator going to sleep now...");
				Thread.sleep(random.nextInt(200));
			} catch (InterruptedException e) {
				logger.info("Got interrupted, exiting now.");
				return;
			}
		}
	}

}
