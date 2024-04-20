package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public ClientThread(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = inputStream.readUTF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
