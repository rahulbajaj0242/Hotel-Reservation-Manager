package application;
	

import javafx.scene.layout.BorderPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HotelServer extends Application {
	
	private TextArea ta = new TextArea();
	private Hashtable outputStream = new Hashtable();
	private ServerSocket ss;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ta.setWrapText(true);//true
			
			Scene sc = new Scene(new ScrollPane(ta),500,180);
			primaryStage.setTitle("Server for Hotel Reservation System");
			primaryStage.setScene(sc);
			primaryStage.show();
			
			new Thread(()->listen()).start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	private void listen() {
		try {
			ss = new ServerSocket(8000);
			
			Platform.runLater(()->
							ta.appendText("Multi-Threaded Server for Hotel Reservation System started: "+new Date()+"\n\n"));
			while(true) {
			
				Socket s = ss.accept();
				
				Platform.runLater(()-> ta.appendText("Connection from " + s + " at " + new Date()  + "\n\n") );
				
				DataOutputStream dout = new DataOutputStream(s.getOutputStream());
				
				outputStream.put(s, dout);
				
				new ServerThread(this, s);
				
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	class ServerThread extends Thread{
		private HotelServer server;
		private Socket socket;
		
		public ServerThread(HotelServer server, Socket socket) {
			this.socket = socket;
			this.server = server;
			start();
		}
		
		public void run() {
			try {
				DataInputStream din = new DataInputStream(socket.getInputStream());
				while(true) {
					String s = din.readUTF();
					
					ta.appendText(s + "\n");
				}
			}catch(IOException e) {
//				e.printStackTrace();
			}
		}
	
	}
	
	
}
