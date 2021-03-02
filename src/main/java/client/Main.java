package client;

public class Main {

    public static void main(String[] args) {
        new Connection(new ArgumentModel(args)).connect();
    }
}