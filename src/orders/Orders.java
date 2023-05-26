package orders;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.domain.entities.Order;

public class Orders extends JFrame implements ActionListener {
    ArrayList<Order> orders;
    JLabel orders_title;
    JButton quit;

    public Orders(ArrayList<Order> orders) {
        super("Pedidos");
        this.orders = orders;
        setSize(700, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridLayout(1, 2));
        JPanel body = new JPanel(new GridLayout(orders.size() + 1, 1));

        orders_title = new JLabel("Pedidos: ");
        orders_title.setFont(font);
        quit = new JButton("Sair");
        quit.setFont(font);

        header.add(orders_title);
        header.add(quit);

        if (orders.isEmpty()) {
            JLabel no_orders = new JLabel("                  Nenhum pedido encontrado.");
            no_orders.setFont(font);
            body.add(no_orders);
        } else {
            for (Order order : orders) {
                JLabel order_label = new JLabel("    " + order.getDate()
                        + " - " + (order.getIdRestaurant() == 1 ? "Moleza"
                                : (order
                                        .getIdRestaurant() == 2 ? "Biba"
                                                : (order
                                                        .getIdRestaurant() == 3 ? "Tech Food" : "Error")))
                        + " - "
                        + order.getMeal() + " - " + (order.getIsDone() ? "Pronto" : "Em andamento"));
                order_label.setFont(font);
                body.add(order_label);
            }
        }

        quit.addActionListener(this);

        container.add(header, BorderLayout.NORTH);
        container.add(body, BorderLayout.CENTER);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            this.dispose();
        }
    }

}
