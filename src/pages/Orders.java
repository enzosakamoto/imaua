package pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import shared.entities.Order;

public class Orders extends JFrame implements ActionListener {
    private ArrayList<Order> orders;
    private JLabel orders_title;
    private JButton quit;
    private JTable table;
    private static ResourceBundle bn = Home.bn;

    Object[] columns = {
            bn.getString("orders.label.date"),
            bn.getString("orders.label.restaurant"),
            bn.getString("orders.label.meal"),
            bn.getString("orders.label.status")
    };

    public Orders(ArrayList<Order> orders) {
        super(bn.getString("orders.title"));
        this.orders = orders;
        Collections.sort(this.orders, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        setSize(750, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridLayout(1, 2));
        header.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        JPanel body = new JPanel(new GridLayout(1, 1));

        DefaultTableModel tableModel = new DefaultTableModel(columns, columns.length);

        table = new JTable(tableModel);

        table.setFont(font);

        orders_title = new JLabel(bn.getString("orders.label.orders_title"));
        orders_title.setFont(font);
        quit = new JButton(bn.getString("orders.button.quit"));
        quit.setFont(font);

        header.add(orders_title);
        header.add(quit);

        if (orders.isEmpty()) {
            JLabel no_orders = new JLabel("                  " + bn.getString("orders.label.no_orders"));
            no_orders.setFont(font);
            body.add(no_orders);
        } else {
            for (int i = 0; i < orders.size(); i++) {
                String date = orders.get(i).getDate();
                String restaurant = (orders.get(i).getIdRestaurant() == 1) ? "Moleza"
                        : (orders.get(i)
                                .getIdRestaurant() == 2) ? "Biba"
                                        : (orders.get(i)
                                                .getIdRestaurant() == 3) ? "Tech Food" : "Error";
                String meal = orders.get(i).getMeal();
                String status = orders.get(i).getIsDone() == 0 ? bn.getString("orders.status.ready")
                        : bn.getString("orders.status.takeout");

                table.setValueAt(date, i, 0);
                table.setValueAt(restaurant, i, 1);
                table.setValueAt(meal, i, 2);
                table.setValueAt(status, i, 3);
            }
            body.add(table);
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
