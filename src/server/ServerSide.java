package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerSide {
    public static final String IP = "127.0.0.1";
    public static final int PORT = 3334;

    private static ServerSocket server;
    private static Socket client;
    private static Scanner input;
    private static PrintStream output;
    private static Scanner scanner;

    private static String lastOrderId;

    public static void main(String[] args) {
        try {
            // Clear terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();
            startServer();
            waitClientConnection();
            clientConversation();
            closeConnection();
            closeServer();
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    public static void startServer() throws IOException {
        server = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta " + PORT);
    }

    public static void waitClientConnection() throws IOException {
        client = server.accept();
        System.out.println("Cliente do IP: " + client.getInetAddress().getHostAddress()
                + " conectado ao servidor pela porta " + PORT);
        input = new Scanner(client.getInputStream());
        output = new PrintStream(client.getOutputStream());
    }

    private static void clientConversation() throws IOException {
        String msg;

        while (input.hasNextLine()) {
            msg = messageFromClient();
            sendMessageToClient(msg);
        }
    }

    private static String messageFromClient() throws IOException {
        String msg = input.nextLine();
        lastOrderId = msg.substring(msg.indexOf('|') + 2, msg.length());
        // Clear terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("lastOrderId: " + lastOrderId);
        System.out.println("\n\nChegou do cliente:");
        System.out.println(msg);
        return msg;
    }

    private static void sendMessageToClient(String msg) throws IOException {
        System.out.println("Deseja confirmar o pedido? [S/N]");
        scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("S")) {
            output.println("true");
            System.out.println("Enviado para o cliente: " + true);
            new OrderThread(lastOrderId).start();
        } else {
            output.println("false");
            System.out.println("Enviado para o cliente: " + false);
        }
        System.out.println("\n");
    }

    private static void closeConnection() throws IOException {
        input.close();
        System.out.println("Cliente com conexão encerrada!");
    }

    private static void closeServer() throws IOException {
        scanner.close();
        server.close();
        System.out.println("Servidor finalizado!");
    }
}