package checkout;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import database.Repository;
import restaurants.RestaurantPage;
import shared.domain.entities.Client;
import shared.domain.entities.Order;

public class Checkout extends JFrame implements ActionListener {
    Repository repository = new Repository();
    JLabel checkout_title;
    JLabel credits;
    JLabel meal;
    JLabel price;
    JLabel credits_remain;
    JLabel credits_remain_price;
    JButton confirm;
    JButton cancel;
    Client client;
    double price_value;
    String meal_text;
    RestaurantPage restaurantPage;

    public Checkout(Client client, String meal, double price, RestaurantPage restaurantPage) {
        super("Checkout");
        this.client = client;
        this.price_value = price;
        this.meal_text = meal;
        this.restaurantPage = restaurantPage;
        setSize(750, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new GridLayout(4, 2));

        checkout_title = new JLabel("Checkout");
        checkout_title.setFont(font);
        credits = new JLabel("Créditos: " + client.getCredits());
        credits.setFont(font);
        this.meal = new JLabel("Comida: " + meal);
        this.meal.setFont(font);
        this.price = new JLabel("R$ " + price);
        this.price.setFont(font);
        credits_remain = new JLabel("Créditos restantes: ");
        credits_remain.setFont(font);
        credits_remain_price = new JLabel("R$ " + (client.getCredits() - Double.valueOf(price)));
        credits_remain_price.setFont(font);

        confirm = new JButton("Confirmar");
        confirm.setFont(font);
        cancel = new JButton("Cancelar");
        cancel.setFont(font);

        container.add(checkout_title);
        container.add(credits);
        container.add(this.meal);
        container.add(this.price);
        container.add(credits_remain);
        container.add(credits_remain_price);
        container.add(confirm);
        container.add(cancel);

        confirm.addActionListener(this);
        cancel.addActionListener(this);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            if (this.client.getCredits() - this.price_value < 0) {
                JOptionPane.showMessageDialog(null, "Você não tem créditos suficientes para realizar essa compra.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                repository.updateClientCreditsByIdClient(this.client.getId(),
                        this.client.getCredits() - this.price_value);
                repository
                        .createOrder(new Order(restaurantPage.restaurant.getId(), this.client.getId(), this.meal_text));
                JOptionPane.showMessageDialog(null, "Compra realizada com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                this.restaurantPage.dispose();
            }
        }
        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

}
