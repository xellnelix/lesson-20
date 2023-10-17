package ru.otus.lesson20;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8088)) {
            System.out.println("Сервер запущен...");
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) try {
                writeFromServer(out, "Введите два числа и операцию из списка доступных (+, -, *, /) через пробел [для выхода введите \"quit\"]\n");
                String userMessage = in.readLine();
                if (userMessage == null || userMessage.trim().isEmpty()) {
                    writeFromServer(out, "Введена пустая строка\n");
                    continue;
                }
                if (userMessage.equalsIgnoreCase("quit")) {
                    closeAll(socket, server, out, in);
                    System.out.println("Сервер закрыт");
                    break;
                }
                String[] splitUserMessage = userMessage.replaceAll("\\s+", " ").split(" ");
                if (splitUserMessage.length != 3) {
                    writeFromServer(out, "Некорректное число аргументов\n");
                    continue;
                }
                int firstOperand = Integer.parseInt(splitUserMessage[0]);
                int secondOperand = Integer.parseInt(splitUserMessage[1]);
                String operation = splitUserMessage[2];
                operationHandle(out, operation, firstOperand, secondOperand);

            } catch (Exception e) {
                System.out.println("Error: " + e);
                break;
            }
        }
    }

    public static int sum(int a, int b) {
        return a + b;
    }

    public static int dec(int a, int b) {
        return a - b;
    }

    public static double div(int a, int b) {
        return (double) a / b;
    }

    public static int mul(int a, int b) {
        return a * b;
    }

    public static void writeFromServer(BufferedWriter out, String message) throws IOException {
        out.write(message);
        out.flush();
    }

    public static void operationHandle(BufferedWriter out, String operation, int firstOperand, int secondOperand) throws IOException {
        switch(operation){
            case "+":
                writeFromServer(out, sum(firstOperand, secondOperand) + "\n");
                break;
            case "-":
                writeFromServer(out, dec(firstOperand, secondOperand) + "\n");
                break;
            case "*":
                writeFromServer(out, mul(firstOperand, secondOperand) + "\n");
                break;
            case "/":
                writeFromServer(out, div(firstOperand, secondOperand) + "\n");
                break;
            default:
                writeFromServer(out, "Некорректная операция\n");
        }
    }

    public static void closeAll(Socket socket, ServerSocket server, BufferedWriter out, BufferedReader in) throws IOException {
        socket.close();
        in.close();
        out.close();
        server.close();
    }
}
