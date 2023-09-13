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
                out.write("Введите два числа и операцию из списка доступных (+, -, *, /) через пробел [для выхода введите \"quit\"]\n");
                out.flush();
                String userMessage = in.readLine();
                if (userMessage == null || userMessage.trim().isEmpty()) {
                    out.write("Введена пустая строка\n");
                    out.flush();
                    continue;
                }
                if (userMessage.equalsIgnoreCase("quit")) {
                    socket.close();
                    in.close();
                    out.close();
                    server.close();
                    System.out.println("Сервер закрыт");
                    break;
                }
                String[] splitUserMessage = userMessage.replaceAll("\\s+", " ").split(" ");
                if (splitUserMessage.length != 3) {
                    out.write("Некорректное число аргументов\n");
                    out.flush();
                    continue;
                }
                int firstOperand = Integer.parseInt(splitUserMessage[0]);
                int secondOperand = Integer.parseInt(splitUserMessage[1]);
                String operation = splitUserMessage[2];
                switch(operation){
                    case "+":
                        out.write(sum(firstOperand, secondOperand) + "\n");
                        out.flush();
                        break;
                    case "-":
                        out.write(dec(firstOperand, secondOperand) + "\n");
                        out.flush();
                        break;
                    case "*":
                        out.write(mul(firstOperand, secondOperand) + "\n");
                        out.flush();
                        break;
                    case "/":
                        out.write(div(firstOperand, secondOperand) + "\n");
                        out.flush();
                        break;
                    default:
                        out.write("Некорректная операция\n");
                        out.flush();
                }
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
}
