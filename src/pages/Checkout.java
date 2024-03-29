package pages;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import server.ClientSide;
import shared.entities.Client;
import shared.entities.Meal;
import shared.entities.Order;
import shared.infra.repository.Repository;

public class Checkout extends JFrame implements ActionListener {
    private JLabel checkout_title;
    private JLabel credits;
    private JLabel meal;
    private JLabel price;
    private JLabel credits_remain;
    private JLabel credits_remain_price;
    private JButton confirm;
    private JButton cancel;
    private double price_value;
    private String meal_text;
    private Client client = Home.client;
    private ClientSide clientSide = App.clientSide;
    Repository repository = new Repository(Home.bn);
    RestaurantPage restaurantPage;
    private ResourceBundle bn = Home.bn;

    public Checkout(Meal meal, RestaurantPage restaurantPage) {
        super("Checkout");
        this.price_value = meal.getPrice();
        this.meal_text = meal.getName();
        this.restaurantPage = restaurantPage;
        setSize(750, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        container.add(panel);

        checkout_title = new JLabel("Checkout");
        checkout_title.setFont(font);
        credits = new JLabel(bn.getString("checkout.label.credits") + " " + client.getCredits());
        credits.setFont(font);
        this.meal = new JLabel(bn.getString("checkout.label.meal") + " " + meal_text);
        this.meal.setFont(font);
        this.price = new JLabel("R$ " + price_value);
        this.price.setFont(font);
        credits_remain = new JLabel(bn.getString("checkout.label.credits_remain"));
        credits_remain.setFont(font);
        credits_remain_price = new JLabel("R$ " + (client.getCredits() - price_value));
        credits_remain_price.setFont(font);

        confirm = new JButton(bn.getString("checkout.button.confirm"));
        confirm.setFont(font);
        cancel = new JButton(bn.getString("checkout.button.cancel"));
        cancel.setFont(font);

        panel.add(checkout_title);
        panel.add(credits);
        panel.add(this.meal);
        panel.add(this.price);
        panel.add(credits_remain);
        panel.add(credits_remain_price);
        panel.add(confirm);
        panel.add(cancel);

        confirm.addActionListener(this);
        cancel.addActionListener(this);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            if (this.client.getCredits() - this.price_value < 0) {
                JOptionPane.showMessageDialog(null, bn.getString("checkout.error.credits"),
                        bn.getString("checkout.error.title"),
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Order order = new Order(restaurantPage.restaurant.getId(),
                            this.client.getId(),
                            this.meal_text);

                    String msg = "Pedido para " + this.restaurantPage.restaurant.getName()
                            + ", prato "
                            + this.meal_text + " ao preço de R$ " + this.price_value
                            + " pode ser confirmado? | " + order.getId();

                    Boolean response = clientSide.serverToClient(msg)
                            .equalsIgnoreCase("true") ? true
                                    : false;

                    if (response) {
                        repository.updateClientCreditsByIdClient(this.client.getId(),
                                this.client.getCredits() - this.price_value);
                        Home.client = repository.getClientByIdClient(this.client.getId());
                        repository.createOrder(order);

                        JOptionPane.showMessageDialog(null,
                                bn.getString("checkout.success.credits"),
                                bn.getString("checkout.success.title"),
                                JOptionPane.INFORMATION_MESSAGE);
                        int op = JOptionPane.showInternalConfirmDialog(null,
                                bn.getString("checkout.receipt.message"),
                                bn.getString("checkout.receipt.title"),
                                JOptionPane.YES_NO_OPTION);

                        if (op == JOptionPane.YES_OPTION) {
                            FileWriter file = new FileWriter(
                                    "src/receipt/" + Order.getLocalDate() + "-"
                                            + Home.client.getId() + ".txt");
                            try {
                                file.write(bn.getString("checkout.receipt.l1")
                                        + "\n\n");
                                file.write(
                                        bn.getString("checkout.receipt.l2")
                                                + " "
                                                + Order.getLocalDate()
                                                + "\n\n");
                                file.write(
                                        bn.getString("checkout.receipt.l3")
                                                + "\t\t\t"
                                                + this.client.getName()
                                                + "\n");
                                file.write(bn.getString("checkout.receipt.l4") + "\t\t"
                                        + this.restaurantPage.restaurant
                                                .getName()
                                        + "\n");
                                file.write(bn.getString("checkout.receipt.l5")
                                        + "\t\t\t" + this.meal_text + "\n");
                                file.write(
                                        bn.getString("checkout.receipt.l6")
                                                + "\t\t\t\tR$"
                                                + this.price_value
                                                + "\n");
                                file.write(
                                        bn.getString("checkout.receipt.l7")
                                                + " R$"
                                                + (this.client.getCredits()
                                                        - this.price_value)
                                                + "\n");
                                file.close();
                            } catch (Exception exception) {
                                System.out.println(exception.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                bn.getString("checkout.failed.purchase"),
                                bn.getString("funds.title.error"),
                                JOptionPane.ERROR_MESSAGE);
                    }

                    this.dispose();
                    this.restaurantPage.dispose();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, bn.getString("checkout.error.purchase"),
                            bn.getString("checkout.error.title"),
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

}
