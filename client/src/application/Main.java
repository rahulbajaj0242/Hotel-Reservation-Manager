package application;
	
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8000;

    private ClientThread clientThread;
    private Socket socket;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("views/Start.fxml"));
			primaryStage.setTitle("Login");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Connect to the server and start the client thread
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            clientThread = new ClientThread(socket);
            clientThread.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public void stop() {
        // Close the connection to the server when the application is closed
        if (clientThread != null) {
            clientThread.sendMessage("Exit connection from " + socket + " at " + new Date()  + "\n\n"); // Send an exit message to the server
        }
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
