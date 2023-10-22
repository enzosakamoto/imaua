package pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.entities.Restaurant;
import shared.infra.repository.Repository;

public class RestaurantPage extends JFrame implements ActionListener {
    private JLabel restaurant_title;
    private JLabel menu;
    private JButton back;
    private JButton meal_1;
    private JButton meal_2;
    private JButton meal_3;
    private JButton meal_4;

    private Repository repository;
    public Restaurant restaurant;

    public RestaurantPage(int restaurantId, ResourceBundle bn) {
        this.repository = new Repository(bn);
        this.restaurant = repository.restaurants.get(restaurantId);

        setTitle(this.restaurant.getName());
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Font font = new Font("Arial", Font.PLAIN, 22);

        JPanel header = new JPanel(new GridLayout(2, 2));
        header.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        JPanel body = new JPanel(new GridLayout(restaurant.getMenu().size(), 1));
        body.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));

        restaurant_title = new JLabel(restaurant.getName());
        restaurant_title.setFont(font);
        back = new JButton(bn.getString("restaurant.button.back"));
        back.setFont(font);
        menu = new JLabel("Menu");
        menu.setFont(font);

        header.add(restaurant_title);
        header.add(back);
        header.add(menu);

        meal_1 = new JButton(restaurant.getMenu().get(0).getName() + " - R$ " + restaurant.getMenu().get(0).getPrice());
        meal_2 = new JButton(restaurant.getMenu().get(1).getName() + " - R$ " + restaurant.getMenu().get(1).getPrice());
        meal_3 = new JButton(restaurant.getMenu().get(2).getName() + " - R$ " + restaurant.getMenu().get(2).getPrice());
        meal_4 = new JButton(restaurant.getMenu().get(3).getName() + " - R$ " + restaurant.getMenu().get(3).getPrice());

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
            Home home = new Home();
            home.setVisible(true);
        }

        if (e.getSource() == meal_1) {
            Checkout checkout = new Checkout(restaurant.getMenu().get(0), this);
            checkout.setVisible(true);
        }

        if (e.getSource() == meal_2) {
            Checkout checkout = new Checkout(restaurant.getMenu().get(1), this);
            checkout.setVisible(true);
        }

        if (e.getSource() == meal_3) {
            Checkout checkout = new Checkout(restaurant.getMenu().get(2), this);
            checkout.setVisible(true);
        }

        if (e.getSource() == meal_4) {
            Checkout checkout = new Checkout(restaurant.getMenu().get(3), this);
            checkout.setVisible(true);
        }
    }

}
