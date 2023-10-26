package pages;

import javax.swing.JOptionPane;

import server.ClientSide;

public class App {
    public static ClientSide clientSide;

    public static void main(String[] args) throws Exception {
        try {
            clientSide = new ClientSide();
            clientSide.startClient();
            Home home = new Home();
            home.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}