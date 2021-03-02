package server;

import server.database.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Server {
    final String ADDRESS = "127.0.0.1";
    final int PORT = 32256;
    private static boolean exit = false;
    Database database = new Database();

    public void connect() {

        System.out.println("Server started!");

        // Create JSON data storage
        createDataStorage();

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (!exit) {
                server.Session session = new server.Session(server.accept(), database);
                session.start();
                session.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exit() {
        exit = true;
    }

    public void createDataStorage() {
        try {
            String path = "src\\main\\java\\server\\data\\db.json";
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write("{}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
