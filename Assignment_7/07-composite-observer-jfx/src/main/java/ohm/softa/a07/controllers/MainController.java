package ohm.softa.a07.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ohm.softa.a07.api.OpenMensaAPI;
import ohm.softa.a07.model.Meal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	// use annotation to tie to component in XML
	@FXML
	private Button btnRefresh;

	@FXML
	private ListView<String> mealsList;

	@FXML
	private Button btnClose;

	@FXML
	private CheckBox chkVegetarian;

	@FXML
	private ObservableList<String> observableList;

	private OpenMensaAPI openMensaAPI;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

	private List<Meal> meals;

	public MainController(){
		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://openmensa.org/api/v2/")
			.build();

		openMensaAPI = retrofit.create(OpenMensaAPI.class);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set the event handler (callback)
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String today = sdf.format(new Date());
				Call<List<Meal>> mealCall = openMensaAPI.getMeals(today);
				// create a new (observable) list and tie it to the view
				ObservableList<String> list = FXCollections.observableArrayList();
				mealCall.enqueue(new Callback<List<Meal>>() {
					@Override
					public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
						if (response.isSuccessful()) {
							meals = response.body();
							for (Meal meal : meals){
								list.add(meal.toString());
							}
						}
					}

					@Override
					public void onFailure(Call<List<Meal>> call, Throwable t) {
						System.out.println(t);
					}
				});
				mealsList.setItems(list);
			}
		});

		chkVegetarian.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ObservableList<String> temp = FXCollections.observableArrayList();
				if (chkVegetarian.isSelected() && meals != null) {
					for (Meal m : meals) {
						if (m.isVegetarian()){
							temp.add(m.toString());
						}
					}
					observableList = temp;
					mealsList.setItems(observableList);
				} else if (!chkVegetarian.isSelected() && meals != null) {
					btnRefresh.fire();
				} else {
					throw new NullPointerException("Meals leer");
				}
			}
		});

		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage s = (Stage) btnClose.getScene().getWindow();
				s.close();
			}
		});
	}
}
