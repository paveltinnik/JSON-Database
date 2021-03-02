package server;

import com.google.gson.Gson;
import server.database.Database;
import server.json.JsonRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {
    private final Database database;
    private final Socket socket;

    public Session(Socket socketForClient, Database database) {
        this.socket = socketForClient;
        this.database = database;
    }

    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            // Read request from client
            String receivedMsg = input.readUTF();

            // Convert JSON request from client to java object
            JsonRequest jsonRequest = new Gson().fromJson(receivedMsg, JsonRequest.class);

            switch (jsonRequest.getType()) {
                case "get":
                    String responseMsg = database.get(jsonRequest.getKey());
                    output.writeUTF(responseMsg);
                    break;
                case "set":
                    responseMsg = database.set(jsonRequest.getKey(), jsonRequest.getValue());
                    output.writeUTF(responseMsg);
                    break;
                case "delete":
                    responseMsg = database.delete(jsonRequest.getKey());
                    output.writeUTF(responseMsg);
                    break;
                case "exit":
                    responseMsg = database.exit();
                    output.writeUTF(responseMsg);
                    socket.close();
                    Server.exit();
                    break;
                default:
                    socket.close();
                    Server.exit();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}