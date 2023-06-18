package ohm.softa.a11;

import ohm.softa.a11.openmensa.OpenMensaAPI;
import ohm.softa.a11.openmensa.OpenMensaAPIService;
import ohm.softa.a11.openmensa.model.Canteen;
import ohm.softa.a11.openmensa.model.Meal;
import ohm.softa.a11.openmensa.model.PageInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * @author Peter Kurfer
 * Created on 12/16/17.
 */
public class App {
	private static final String OPEN_MENSA_DATE_FORMAT = "yyyy-MM-dd";

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(OPEN_MENSA_DATE_FORMAT, Locale.getDefault());
	private static final Scanner inputScanner = new Scanner(System.in);
	private static final OpenMensaAPI openMensaAPI = OpenMensaAPIService.getInstance().getOpenMensaAPI();
	private static final Calendar currentDate = Calendar.getInstance();
	private static int currentCanteenId = -1;

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		MenuSelection selection;
		/* loop while true to get back to the menu every time an action was performed */
		do {
			selection = menu();
			switch (selection) {
				case SHOW_CANTEENS:
					printCanteens();
					break;
				case SET_CANTEEN:
					readCanteen();
					break;
				case SHOW_MEALS:
					printMeals();
					break;
				case SET_DATE:
					readDate();
					break;
				case QUIT:
					System.exit(0);

			}
		} while (true);
	}

	private static void printCanteens() throws ExecutionException, InterruptedException {
		System.out.print("Fetching canteens [");

		openMensaAPI.getCanteens()
			.thenApply(response -> {
				System.out.print("#");
				PageInfo pageInfo = PageInfo.extractFromResponse(response);
				List<Canteen> allCanteens; // api return a list of canteens

				if (response.body() == null)
					allCanteens = new LinkedList<>();
				else
					allCanteens = response.body();

				/* declare variable to be able to use `thenCombine` */
				CompletableFuture<List<Canteen>> remainingCanteensFuture = null;

				/* iterate all pages
				 * 2 to including 8 because page index is not 0 indexed */
				for (int i = 2; i <= pageInfo.getTotalCountOfPages(); i++) {
					System.out.print("#");
					try {
						/* you can block this thread with `get` because we are already in a
						 * background thread because of `thenApply` */
						allCanteens.addAll(openMensaAPI.getCanteens(i).get());
					} catch (InterruptedException | ExecutionException e) {
						System.out.println("Error while retrieving canteens");
					}
				}
				System.out.println("]");

				/* sort the canteens by their id and return them */
				allCanteens.sort(Comparator.comparing(Canteen::getId));
				return allCanteens;
			})
			.thenAccept(canteenList -> {
				for (Canteen canteen : canteenList) {
					System.out.println(canteen);
				}
			})
			.exceptionally(ex -> { System.out.print("Oops, something went wrong: " + ex); return null;} )
			.get(); /* block the thread by calling `get` to ensure that all results are retrieved when the method is completed */
	}

	private static void printMeals() throws ExecutionException, InterruptedException {
		/* TODO fetch all meals for the currently selected canteen
		 * to avoid errors retrieve at first the state of the canteen and check if the canteen is opened at the selected day
		 * don't forget to check if a canteen was selected previously! */
		if (currentCanteenId <= 0){
			System.out.println("No canteen selected!");
			return;
		}

		final String date = dateFormat.format(currentDate.getTime());

		openMensaAPI.getCanteenState(currentCanteenId, date)
			.thenApply(state -> {
				if (state != null && !state.isClosed()){
					try {
						return openMensaAPI.getMeals(currentCanteenId, date).get();
					} catch (InterruptedException | ExecutionException e) {
						System.out.println("Error while retrieving meals");
					}
				} else {
					/* if canteen is not open - print a message and return */
					System.out.println(String.format("Seems like the canteen (id:%id) has closed on this date: %s", currentCanteenId ,date));
				}
				return new LinkedList<Meal>();
			})
			.thenAccept(meals -> {
				for (Meal m : meals)
					System.out.println(m);
			})
			.exceptionally(ex -> { System.out.print("Oops, something went wrong: " + ex); return null;})
			.get();
	}

	/**
	 * Utility method to select a canteen
	 */
	private static void readCanteen() {
		/* typical input reading pattern */
		boolean readCanteenId = false;
		do {
			try {
				System.out.println("Enter canteen id:");
				currentCanteenId = inputScanner.nextInt();
				readCanteenId = true;
			} catch (Exception e) {
				System.out.println("Sorry could not read the canteen id");
			}
		} while (!readCanteenId);
	}

	/**
	 * Utility method to read a date and update the calendar
	 */
	private static void readDate() {
		/* typical input reading pattern */
		boolean readDate = false;
		do {
			try {
				System.out.println("Pleae enter date in the format yyyy-mm-dd:");
				Date d = dateFormat.parse(inputScanner.next());
				currentDate.setTime(d);
				readDate = true;
			} catch (ParseException p) {
				System.out.println("Sorry, the entered date could not be parsed.");
			}
		} while (!readDate);

	}

	/**
	 * Utility method to print menu and read the user selection
	 *
	 * @return user selection as MenuSelection
	 */
	private static MenuSelection menu() {
		IntStream.range(0, 20).forEach(i -> System.out.print("#"));
		System.out.println();
		System.out.println("1) Show canteens");
		System.out.println("2) Set canteen");
		System.out.println("3) Show meals");
		System.out.println("4) Set date");
		System.out.println("5) Quit");
		IntStream.range(0, 20).forEach(i -> System.out.print("#"));
		System.out.println();

		switch (inputScanner.nextInt()) {
			case 1:
				return MenuSelection.SHOW_CANTEENS;
			case 2:
				return MenuSelection.SET_CANTEEN;
			case 3:
				return MenuSelection.SHOW_MEALS;
			case 4:
				return MenuSelection.SET_DATE;
			default:
				return MenuSelection.QUIT;
		}
	}
}
