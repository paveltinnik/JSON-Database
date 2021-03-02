package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
    client.ArgumentModel argumentModel;
    private final String ADDRESS = "127.0.0.1";
    private final int PORT = 32256;

    public Connection(client.ArgumentModel argumentModel) {
        this.argumentModel = argumentModel;
    }

    public void connect() {
        System.out.println("\nClient started!");

        try(Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            String message = argumentModel.getJsonString();
            System.out.println("Sent: " + argumentModel.getJsonString());

            output.writeUTF(message);

            String response = input.readUTF();
            System.out.println("Received: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
