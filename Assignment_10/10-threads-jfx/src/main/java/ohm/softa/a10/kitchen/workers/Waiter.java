package ohm.softa.a10.kitchen.workers;

import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.kitchen.KitchenHatch;
import ohm.softa.a10.model.Dish;

import java.util.Random;

public class Waiter implements Runnable{

	private String name;
	private ProgressReporter progressReporter;
	private KitchenHatch kitchenHatch;

	public Waiter(String name,  KitchenHatch kitchenHatch, ProgressReporter progressReporter) {
		this.name = name;
		this.progressReporter = progressReporter;
		this.kitchenHatch = kitchenHatch;
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
		while (kitchenHatch.getOrderCount() > 0 || kitchenHatch.getDishesCount() > 0){
			try {
				Dish dish = kitchenHatch.dequeueDish();
				System.out.println("Dishes in kitchen hatch: " + kitchenHatch.getDishesCount());

				// serving dish
				Thread.sleep((long)(Math.random() * 1000));
				progressReporter.updateProgress();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		progressReporter.notifyWaiterLeaving();
	}
}
