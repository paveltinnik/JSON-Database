package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArgumentModel {

    ArgumentModel(String[] args) {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);
    }

    @Parameter(names = "-t", description = "Type of request")
    private String type;

    @Parameter(names = "-k", description = "Key of request")
    private String key;

    @Parameter(names = "-v", description = "Value of key")
    private String value;

    @Parameter(names = "-in", description = "Name of file")
    private String file;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getFile() {
        return file;
    }

    public boolean isFromFile() {
        return this.file != null;
    }


    public String getJsonString() {
        String json = "";

        if (!isFromFile()) {
            json = new Gson().toJson(this);
        } else {
            String sep = File.separator;
            String path = "src" + sep + "main" + sep + "java" + sep + "client" + sep + "data" + sep + file;
            File file = new File(path);

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                json = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
