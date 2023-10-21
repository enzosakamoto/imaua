package home;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.Repository;
import funds.Funds;
import orders.Orders;
import restaurants.RestaurantPage;
import shared.domain.entities.Client;
import shared.domain.entities.Order;
import start.Start;

public class Home extends JFrame implements ActionListener {
    JLabel home_title;
    JButton add_credits;
    JButton orders;
    JButton logoff;
    JButton restaurant_moleza;
    JButton restaurant_biba;
    JButton restaurant_techfood;
    Client client;
    Repository repository = new Repository();
    private ResourceBundle bn = null;
    public static String language = null;

    public Home(Client client) {
        super("Home");
        
        int op = Integer.parseInt(JOptionPane.showInputDialog("Idioma - Language - Langue - Lingua\n1 - Português\n2 - English\n3 - Français\n - Italiano\n - Espanhol\n"));

        switch(op) {
            case 1 : bn = ResourceBundle.getBundle("Bundle", new Locale("pt", "BR"));
            language = "pt,BR";
            break;
            case 2 : bn = ResourceBundle.getBundle("Bundle", new Locale("en", "US"));
            language = "en,US";
            break;
            case 3 : bn = ResourceBundle.getBundle("Bundle", new Locale("fr", "FR"));
            language = "fr,FR";
            break;
            case 4 : bn = ResourceBundle.getBundle("Bundle", new Locale("it", "IT"));
            language = "it,IT";
            break;
            case 5 : bn = ResourceBundle.getBundle("Bundle", new Locale("es", "ES"));
            language = "es,ES";
            break;
        }

        this.client = client;
        setSize(900, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel restaurants = new JPanel(new GridLayout(3, 1));
        JPanel header = new JPanel(new GridLayout(1, 3));

        home_title = new JLabel(bn.getString("home.label.title ") + client.getName() + "!");
        home_title.setFont(font);
        add_credits = new JButton(bn.getString("home.button.add_credits"));
        add_credits.setFont(font);
        orders = new JButton(bn.getString("home.button.orders"));
        orders.setFont(font);
        logoff = new JButton("Logoff");
        logoff.setFont(font);

        restaurant_moleza = new JButton(bn.getString("home.button.restaurant_moleza"));
        restaurant_moleza.setFont(font);
        restaurant_biba = new JButton(bn.getString("home.button.restaurant_biba"));
        restaurant_biba.setFont(font);
        restaurant_techfood = new JButton(bn.getString("home.button.restaurant_techfood"));
        restaurant_techfood.setFont(font);

        header.add(home_title);
        header.add(add_credits);
        header.add(orders);
        header.add(logoff);

        restaurants.add(restaurant_moleza);
        restaurants.add(restaurant_biba);
        restaurants.add(restaurant_techfood);

        container.add(header, BorderLayout.NORTH);
        container.add(restaurants, BorderLayout.CENTER);

        setVisible(false);

        logoff.addActionListener(this);
        add_credits.addActionListener(this);
        orders.addActionListener(this);
        restaurant_moleza.addActionListener(this);
        restaurant_biba.addActionListener(this);
        restaurant_techfood.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoff) {
            this.setVisible(false);
            this.client = null;
            Start start = new Start();
            start.setVisible(true);
        }

        if (e.getSource() == add_credits) {
            Funds add_credits = new Funds(this.client.getId());
            add_credits.setVisible(true);
        }

        if (e.getSource() == orders) {
            ArrayList<Order> orders_array = repository.getAllOrdersByIdClient(client.getId());
            Orders orders = new Orders(orders_array);
            orders.setVisible(true);
        }

        if (e.getSource() == restaurant_moleza) {
            Client client = repository.getClientByIdClient(this.client.getId());
            RestaurantPage restaurant_page = new RestaurantPage(repository.restaurant_moleza, client);
            restaurant_page.setVisible(true);
        }

        if (e.getSource() == restaurant_biba) {
            Client client = repository.getClientByIdClient(this.client.getId());
            RestaurantPage restaurant_page = new RestaurantPage(repository.restaurant_biba, client);
            restaurant_page.setVisible(true);
        }

        if (e.getSource() == restaurant_techfood) {
            Client client = repository.getClientByIdClient(this.client.getId());
            RestaurantPage restaurant_page = new RestaurantPage(repository.restaurant_techfood, client);
            restaurant_page.setVisible(true);
        }

    }

}
