package application.controllers;

import java.util.ArrayList;

import application.database.JdbcDA;
import application.models.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookingsController {

	@FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, Integer> bookingIdTC;

    @FXML
    private TableColumn<Booking, String> nameTC;

    @FXML
    private TableColumn<Booking, String> roomTypeTC;

    @FXML
    private TableColumn<Booking, Integer> numRoomsTC;

    @FXML
    private TableColumn<Booking, Integer> numDaysTC;

    @FXML
    private Pane currBookingsLabel;

    @FXML
    private Label numBookingsLabel;
    
    @FXML
    void initialize() {
        bookingIdTC.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        nameTC.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        roomTypeTC.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        numRoomsTC.setCellValueFactory(new PropertyValueFactory<>("num_rooms"));
        numDaysTC.setCellValueFactory(new PropertyValueFactory<>("num_days"));

        JdbcDA da = new JdbcDA();
        ArrayList<Booking> bookings = da.getBookings();
        da.close();

        bookingsTable.getItems().clear();
        bookingsTable.getItems().addAll(bookings);
        numBookingsLabel.setText(Integer.toString(bookingsTable.getItems().size()));
    }

}
