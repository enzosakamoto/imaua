package server;

import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import pages.Home;
import shared.entities.Order;
import shared.infra.repository.Repository;

public class OrderThread extends Thread {
    private String orderId;
    private final ResourceBundle bn = Home.bn;

    private final Repository repository = new Repository(Home.bn);

    public OrderThread(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            Order order = repository.getOrderByOrderId(orderId);
            repository.updateOrderStatusByOrderId(orderId, 1);
            JOptionPane.showMessageDialog(null,
                    bn.getString("order.thread.message") + " " + order.getMeal() + " "
                            + bn.getString("order.thread.message2"),
                    bn.getString("order.thread.title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }
}
