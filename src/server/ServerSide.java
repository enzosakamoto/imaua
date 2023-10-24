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

    public static void main(String[] args) {
        try {
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
            input.nextLine();
        }
    }

    private static String messageFromClient() throws IOException {
        String msg = input.nextLine();
        System.out.println("Chegou do cliente:");
        System.out.println(msg);
        return msg;
    }

    private static void sendMessageToClient(String msg) throws IOException {
        System.out.println("Deseja confirmar o pedido? [S/N]");
        input = new Scanner(System.in);
        String resposta = input.nextLine();
        if (resposta.equalsIgnoreCase("S")) {
            output.println("true");
            System.out.println("Enviado para o cliente: " + true);
        } else {
            output.println("false");
            System.out.println("Enviado para o cliente: " + false);
        }

    }

    private static void closeConnection() throws IOException {
        input.close();
        System.out.println("Cliente com conexão encerrada!");
    }

    private static void closeServer() throws IOException {
        server.close();
        System.out.println("Servidor finalizado!");
    }
}