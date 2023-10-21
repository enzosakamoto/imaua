package restaurants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import checkout.Checkout;
import home.Home;
import shared.domain.entities.Client;
import shared.domain.entities.Restaurant;

public class RestaurantPage extends JFrame implements ActionListener {
    JLabel restaurant_title;
    JLabel menu;
    JButton back;
    JButton meal_1;
    JButton meal_2;
    JButton meal_3;
    JButton meal_4;
    Client client;
    public Restaurant restaurant;
    private ResourceBundle bn = null;

    public RestaurantPage(Restaurant restaurant, Client client) {
        super(restaurant.getName());

        bn = ResourceBundle.getBundle("Bundle", new java.util.Locale(Home.language.split(",")[0],
                Home.language.split(",")[1]));
                
        this.client = client;
        this.restaurant = restaurant;
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Font font = new Font("Arial", Font.PLAIN, 22);

        JPanel header = new JPanel(new GridLayout(2, 2));
        JPanel body = new JPanel(new GridLayout(restaurant.getMenu().length, 1));

        restaurant_title = new JLabel(restaurant.getName());
        restaurant_title.setFont(font);
        back = new JButton(bn.getString("restaurant.button.back"));
        back.setFont(font);
        menu = new JLabel("Menu");
        menu.setFont(font);

        header.add(restaurant_title);
        header.add(back);
        header.add(menu);

        meal_1 = new JButton(restaurant.getMenu()[0] + " - R$ " + restaurant.getPrices()[0]);
        meal_2 = new JButton(restaurant.getMenu()[1] + " - R$ " + restaurant.getPrices()[1]);
        meal_3 = new JButton(restaurant.getMenu()[2] + " - R$ " + restaurant.getPrices()[2]);
        meal_4 = new JButton(restaurant.getMenu()[3] + " - R$ " + restaurant.getPrices()[3]);

        meal_1.setFont(font);
        meal_2.setFont(font);
        meal_3.setFont(font);
        meal_4.setFont(font);

        body.add(meal_1);
        body.add(meal_2);
        body.add(meal_3);
        body.add(meal_4);

        meal_1.addActionListener(this);
        meal_2.addActionListener(this);
        meal_3.addActionListener(this);
        meal_4.addActionListener(this);
        back.addActionListener(this);

        container.add(header, BorderLayout.NORTH);
        container.add(body, BorderLayout.CENTER);

        setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            this.dispose();
        }

        if (e.getSource() == meal_1) {
            Checkout checkout = new Checkout(client, restaurant.getMenu()[0], restaurant.getPrices()[0], this);
            checkout.setVisible(true);
        }

        if (e.getSource() == meal_2) {
            Checkout checkout = new Checkout(client, restaurant.getMenu()[1], restaurant.getPrices()[1], this);
            checkout.setVisible(true);
        }

        if (e.getSource() == meal_3) {
            Checkout checkout = new Checkout(client, restaurant.getMenu()[2], restaurant.getPrices()[2], this);
            checkout.setVisible(true);
        }

        if (e.getSource() == meal_4) {
            Checkout checkout = new Checkout(client, restaurant.getMenu()[3], restaurant.getPrices()[3], this);
            checkout.setVisible(true);
        }
    }

}
