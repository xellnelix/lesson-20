package ru.otus.lesson20;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8088)) {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) try {
                System.out.println(in.readLine());
                String userWord = consoleReader.readLine();
                out.write(userWord + "\n");
                out.flush();
                if (userWord.equalsIgnoreCase("quit")) {
                    System.out.println("Клиент закрыт");
                    break;
                }
                System.out.println(in.readLine());
            } catch (Exception e) {
                System.out.println("Error: " + e);
                break;
            }
        }
    }
}
