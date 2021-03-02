package server.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ReaderWriter {
    public static String read(String path) {
        String json = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            json = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void write(String path, String json) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
