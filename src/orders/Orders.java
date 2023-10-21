package orders;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import home.Home;
import shared.domain.entities.Order;

public class Orders extends JFrame implements ActionListener {
    ArrayList<Order> orders;
    JLabel orders_title;
    JButton quit;
    private static ResourceBundle bn = null;

    public Orders(ArrayList<Order> orders) {
        super(bn.getString("orders.title"));

        bn = ResourceBundle.getBundle("Bundle", new Locale(Home.language.split(",")[0], Home.language.split(",")[1]));

        this.orders = orders;
        Collections.sort(this.orders, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        setSize(850, (!orders.isEmpty() ? orders.size() * 150 : 500));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridLayout(1, 2));
        JPanel body = new JPanel(new GridLayout(orders.size() + 1, 4));

        orders_title = new JLabel(bn.getString("orders.label.orders_title"));
        orders_title.setFont(font);
        quit = new JButton(bn.getString("orders.button.quit"));
        quit.setFont(font);

        header.add(orders_title);
        header.add(quit);

        if (orders.isEmpty()) {
            JLabel no_orders = new JLabel(bn.getString("orders.label.no_orders"));
            no_orders.setFont(font);
            body.add(no_orders);
        } else {
            JLabel date_label = new JLabel(bn.getString("orders.label.date"));
            date_label.setFont(font);
            JLabel restaurant_label = new JLabel(bn.getString("orders.label.restaurant"));
            restaurant_label.setFont(font);
            JLabel meal_label = new JLabel(bn.getString("orders.label.meal"));
            meal_label.setFont(font);
            JLabel status_label = new JLabel(bn.getString("orders.label.status"));
            status_label.setFont(font);
            body.add(date_label);
            body.add(restaurant_label);
            body.add(meal_label);
            body.add(status_label);
            for (int i = 0; i < orders.size(); i++) {
                JLabel date = new JLabel(orders.get(i).getDate());
                date.setFont(font);
                JLabel restaurant = new JLabel((orders.get(i).getIdRestaurant() == 1 ? "Moleza"
                        : (orders.get(i)
                                .getIdRestaurant() == 2 ? "Biba"
                                        : (orders.get(i)
                                                .getIdRestaurant() == 3 ? "Tech Food" : "Error"))));
                restaurant.setFont(font);
                JLabel meal = new JLabel(orders.get(i).getMeal());
                meal.setFont(font);
                JLabel status = new JLabel(orders.get(i).getIsDone() ? bn.getString("orders.status.ready") : bn.getString("orders.status.takeout"));
                status.setFont(font);
                body.add(date);
                body.add(restaurant);
                body.add(meal);
                body.add(status);
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
