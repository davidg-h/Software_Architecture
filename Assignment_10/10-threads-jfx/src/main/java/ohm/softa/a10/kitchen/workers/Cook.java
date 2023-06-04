package ohm.softa.a10.kitchen.workers;

import ohm.softa.a10.kitchen.KitchenHatch;
import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

import ohm.softa.a10.internals.displaying.ProgressReporter;

public class Cook implements Runnable{

	private String name;
	private ProgressReporter progressReporter;
	private KitchenHatch kitchenHatch;

	public Cook(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
		this.name = name;
		this.kitchenHatch = kitchenHatch;
		this.progressReporter = progressReporter;
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
		while (kitchenHatch.getOrderCount() > 0){
			try {
				// create order and corresponding dish
				Order order = kitchenHatch.dequeueOrder();
				Dish dish = new Dish(order.getMealName());

				System.out.println("Orders left: " + kitchenHatch.getOrderCount());
				// cooking time
				Thread.sleep(dish.getCookingTime());
				kitchenHatch.enqueueDish(dish);

				progressReporter.updateProgress();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		progressReporter.notifyCookLeaving();
	}
}
