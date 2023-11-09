package pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import shared.entities.Order;
import shared.infra.repository.Repository;

public class Orders extends JFrame implements ActionListener {
    private ArrayList<Order> orders;
    private JLabel orders_title;
    private JButton refresh;
    private JButton quit;
    private JTable table;
    private JScrollPane scrollPane;
    private static ResourceBundle bn = Home.bn;
    private static Repository repository = new Repository(bn);

    public Orders(ArrayList<Order> orders, ResourceBundle homeBn) {
        super(bn.getString("orders.title"));
        this.orders = orders;
        bn = homeBn;
        Collections.sort(this.orders, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        setSize(750, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Object[] columns = {
                bn.getString("orders.label.date"),
                bn.getString("orders.label.restaurant"),
                bn.getString("orders.label.meal"),
                bn.getString("orders.label.status")
        };

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout());
        header.setBorder(BorderFactory.createEmptyBorder(25, 25, 0, 25));
        JPanel body = new JPanel(new GridLayout(1, 1));

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };

        orders_title = new JLabel(bn.getString("orders.label.orders_title"));

        Icon refreshIcon = new ImageIcon("src/assets/refresh.png");

        refresh = new JButton(refreshIcon);

        quit = new JButton(bn.getString("orders.button.quit"));

        header.add(orders_title);
        header.add(Box.createHorizontalStrut(500));
        header.add(refresh);
        header.add(quit);

        if (orders.isEmpty()) {
            JLabel no_orders = new JLabel("                  " + bn.getString("orders.label.no_orders"));
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
                String status = orders.get(i).getIsDone() == 0 ? bn.getString("orders.status.waiting")
                        : bn.getString("orders.status.takeout");

                tableModel.addRow(new Object[] { date, restaurant, meal, status });
            }
        }

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        body.add(scrollPane);
        body.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        quit.addActionListener(this);
        refresh.addActionListener(this);

        container.add(header, BorderLayout.NORTH);
        container.add(body, BorderLayout.CENTER);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            this.dispose();
        }

        if (e.getSource() == refresh) {
            this.dispose();
            try {
                ArrayList<Order> newOrders = repository.getAllOrdersByIdClient(Home.client.getId());
                new Orders(newOrders, bn).setVisible(true);
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

}
