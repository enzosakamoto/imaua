import pages.Home;
import server.ClientSide;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            ClientSide.startClient();
            Home home = new Home();
            home.setVisible(true);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}