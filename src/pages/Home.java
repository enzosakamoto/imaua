package pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;

import shared.entities.Client;
import shared.entities.Order;
import shared.infra.repository.Repository;

public class Home extends JFrame implements ActionListener {
    private JLabel home_title;
    private JButton add_credits;
    private JButton orders;
    private JButton logoff;
    private JButton login;
    private JButton restaurant_moleza;
    private JButton restaurant_biba;
    private JButton restaurant_techfood;
    private JMenuBar menuBar;
    private JMenu languages;
    private JMenuItem portuguese = new JMenuItem("Portuguese");
    private JMenuItem english = new JMenuItem("English");
    private JMenuItem italian = new JMenuItem("Italian");
    private JMenuItem french = new JMenuItem("French");
    private JMenuItem espanish = new JMenuItem("Espanish");
    private JSeparator separator = new JSeparator();

    public static ResourceBundle bn = ResourceBundle.getBundle("shared/languages/Bundle", Locale.of("pt", "BR"));

    private Repository repository = new Repository(bn);

    public static Client client = null;

    public Home() {
        super("Home");
        setSize(900, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel restaurants = new JPanel(new GridLayout(3, 1));

        menuBar = new JMenuBar();

        home_title = new JLabel(client != null ? bn.getString("home.label.title") + ", " + client.getName() + "!"
                : bn.getString("home.label.title") + "!");
        home_title.setFont(font);
        add_credits = new JButton(bn.getString("home.button.add_credits"));
        add_credits.setFont(font);
        orders = new JButton(bn.getString("home.button.orders"));
        orders.setFont(font);
        logoff = new JButton("Logoff");
        logoff.setFont(font);
        login = new JButton("Login");
        login.setFont(font);
        languages = new JMenu(bn.getString("home.menu.languages"));
        languages.setFont(font);
        languages.add(portuguese);
        languages.add(english);
        languages.add(italian);
        languages.add(french);
        languages.add(espanish);

        menuBar.add(home_title);
        menuBar.add(separator);
        menuBar.add(languages);

        if (client != null) {
            menuBar.add(add_credits);
            menuBar.add(orders);
        }

        menuBar.add(client != null ? logoff : login);

        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));

        setJMenuBar(menuBar);

        restaurant_moleza = new JButton(bn.getString("home.button.restaurante_moleza"));
        restaurant_moleza.setFont(font);
        restaurant_biba = new JButton(bn.getString("home.button.restaurante_biba"));
        restaurant_biba.setFont(font);
        restaurant_techfood = new JButton(bn.getString("home.button.restaurante_techfood"));
        restaurant_techfood.setFont(font);

        restaurants.add(restaurant_moleza);
        restaurants.add(restaurant_biba);
        restaurants.add(restaurant_techfood);

        Border padding = BorderFactory.createEmptyBorder(25, 25, 25, 25);

        restaurants.setBorder(padding);

        container.add(restaurants, BorderLayout.CENTER);

        setVisible(false);

        login.addActionListener(this);
        logoff.addActionListener(this);
        add_credits.addActionListener(this);
        orders.addActionListener(this);
        restaurant_moleza.addActionListener(this);
        restaurant_biba.addActionListener(this);
        restaurant_techfood.addActionListener(this);
        portuguese.addActionListener(this);
        english.addActionListener(this);
        italian.addActionListener(this);
        french.addActionListener(this);
        espanish.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            this.dispose();
            Login login = new Login();
            login.setVisible(true);
        }

        if (e.getSource() == logoff) {
            this.dispose();
            client = null;
            Home home = new Home();
            home.setVisible(true);
        }

        if (e.getSource() == add_credits) {
            Funds add_credits = new Funds();
            add_credits.setVisible(true);
        }

        if (e.getSource() == orders) {
            try {
                ArrayList<Order> orders_array = repository.getAllOrdersByIdClient(client.getId());
                Orders orders = new Orders(orders_array);
                orders.setVisible(true);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        if (e.getSource() == restaurant_moleza) {
            if (client == null) {
                JOptionPane.showMessageDialog(null, "Faça login primeiro!", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                RestaurantPage restaurant_page = new RestaurantPage(0, bn);
                restaurant_page.setVisible(true);
            }
        }

        if (e.getSource() == restaurant_biba) {
            if (client == null) {
                JOptionPane.showMessageDialog(null, "Faça login primeiro!", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                RestaurantPage restaurant_page = new RestaurantPage(1, bn);
                restaurant_page.setVisible(true);
            }
        }

        if (e.getSource() == restaurant_techfood) {
            if (client == null) {
                JOptionPane.showMessageDialog(null, "Faça login primeiro!", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                this.dispose();
                RestaurantPage restaurant_page = new RestaurantPage(2, bn);
                restaurant_page.setVisible(true);
            }
        }

        if (e.getSource() == portuguese) {
            bn = ResourceBundle.getBundle("shared/languages/Bundle", Locale.of("pt", "BR"));
            this.dispose();
            Home home = new Home();
            home.setVisible(true);
        }

        if (e.getSource() == english) {
            bn = ResourceBundle.getBundle("shared/languages/Bundle", Locale.US);
            this.dispose();
            Home home = new Home();
            home.setVisible(true);
        }

        if (e.getSource() == italian) {
            bn = ResourceBundle.getBundle("shared/languages/Bundle", Locale.ITALY);
            this.dispose();
            Home home = new Home();
            home.setVisible(true);
        }

        if (e.getSource() == french) {
            bn = ResourceBundle.getBundle("shared/languages/Bundle", Locale.FRANCE);
            this.dispose();
            Home home = new Home();
            home.setVisible(true);
        }

        if (e.getSource() == espanish) {
            bn = ResourceBundle.getBundle("shared/languages/Bundle", Locale.of("es", "ES"));
            this.dispose();
            Home home = new Home();
            home.setVisible(true);
        }
    }
}
