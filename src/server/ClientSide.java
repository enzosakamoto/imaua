package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSide {
    private static Socket client;
    private static Scanner input;
    private static PrintStream output;

    public void startClient() throws IOException {
        client = new Socket(ServerSide.IP, ServerSide.PORT);
        System.out.println(
                "Cliente: " + ServerSide.IP + ":" + ServerSide.PORT + " conectado ao servidor!");
        input = new Scanner(client.getInputStream());
    }

    public String serverToClient(String msg, String orderId) throws IOException {
        String echo = "";

        if (!echo.equalsIgnoreCase("true") || !echo.equalsIgnoreCase("false")) {
            sendToServer(msg, orderId);
            echo = messageReceived();
        }

        return echo;
    }

    private void sendToServer(String msg, String orderId) throws IOException {
        output = new PrintStream(client.getOutputStream());
        output.println(msg);
        output.println(orderId);
        System.out.println("Enviado para o servidor: " + msg);
    }

    private String messageReceived() throws IOException {
        String msg = input.nextLine();
        System.out.println("Chegou do servidor: " + msg);
        return msg;
    }
}
