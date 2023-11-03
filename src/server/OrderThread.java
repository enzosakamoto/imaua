package server;

import javax.swing.JOptionPane;

public class OrderThread extends Thread {
    private String orderId;

    public OrderThread(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            JOptionPane.showMessageDialog(null, "Pedido " + orderId + " finalizado");
        } catch (InterruptedException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }
}
