package ohm.softa.a10.kitchen;

import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

import java.util.Deque;
import java.util.LinkedList;

public class KitchenHatchImpl implements KitchenHatch{

	private int maxMeals;
	private Deque<Order> orders;
	private Deque<Dish> dishes;

	public KitchenHatchImpl(int maxMeals, Deque<Order> orders){
		this.maxMeals = maxMeals;
		this.orders = orders;
		dishes = new LinkedList<>();
	}

	/**
	 * Get the count how many meals can be placed in the hatch
	 *
	 * @return max count
	 */
	@Override
	public int getMaxDishes() {
		return maxMeals;
	}

	/**
	 * Dequeue an outstanding order
	 *
	 * @param timeout timeout to pass to the wait call if no orders are present
	 * @return an order or null if all orders are done
	 */
	@Override
	public synchronized Order dequeueOrder(long timeout){
		Order o = null;
		synchronized (orders){
			if (orders.size() >= 1){
				o = orders.pop();
			}
			orders.notifyAll();
		}
		return o;
	}

	/**
	 * Get the remaining count of orders
	 *
	 * @return count of orders
	 */
	@Override
	public int getOrderCount() {
		synchronized (orders){
			return orders.size();
		}
	}

	/**
	 * Dequeue a completed dish
	 *
	 * @param timeout timeout to pass to the wait call if no meals are present
	 * @return hopefully hot dish to serve to a guest
	 */
	@Override
	public synchronized Dish dequeueDish(long timeout) {
		synchronized (dishes){
			while (dishes.size() == 0){
				try {
					dishes.wait(timeout);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

			}
			/* dequeue a completed dish */
			Dish result = dishes.pop();

			/* re-enable all waiting waiters */
			dishes.notifyAll();
			return result;
		}
	}

	/**
	 * Enqueue a new completed dish to be served by a waiter
	 *
	 * @param m Dish to enqueue
	 */
	@Override
	public void enqueueDish(Dish m) {
		synchronized (dishes){
			while (dishes.size() >= maxMeals){
				try {
					dishes.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			/* enqueue the prepared dish */
			dishes.push(m);

			/* re-enable all waiting cooks */
			dishes.notifyAll();
		}
	}

	/**
	 * Get the total count of dishes in the kitchen hatch
	 *
	 * @return total count of dishes
	 */
	@Override
	public int getDishesCount() {
		synchronized (dishes){
			return dishes.size();
		}
	}
}
