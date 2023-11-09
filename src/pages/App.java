package pages;

import server.ClientSide;

public class App {
    public static ClientSide clientSide;

    public static void main(String[] args) throws Exception {
        try {
            // Clear terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();
            clientSide = new ClientSide();
            clientSide.startClient();
            Home home = new Home();
            home.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}