package home;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.domain.entities.Client;
import start.Start;

public class Home extends JFrame implements ActionListener {
    JLabel home_title;
    JButton logoff;
    JButton restaurant_moleza;
    JButton restaurant_biba;
    JButton restaurant_techfood;
    Client client;

    public Home(Client client) {
        super("Home");
        this.client = client;
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel restaurants = new JPanel(new GridLayout(3, 1));
        JPanel header = new JPanel(new GridLayout(1, 2));

        home_title = new JLabel("Bem-vindo, " + client.getName() + "!");
        home_title.setFont(font);
        logoff = new JButton("Logoff");
        logoff.setFont(font);

        restaurant_moleza = new JButton("Restaurant Moleza");
        restaurant_moleza.setFont(font);
        restaurant_biba = new JButton("Restaurant Biba");
        restaurant_biba.setFont(font);
        restaurant_techfood = new JButton("Restaurant TechFood");
        restaurant_techfood.setFont(font);

        header.add(home_title);
        header.add(logoff);

        restaurants.add(restaurant_moleza);
        restaurants.add(restaurant_biba);
        restaurants.add(restaurant_techfood);

        container.add(header, BorderLayout.NORTH);
        container.add(restaurants, BorderLayout.CENTER);

        setVisible(false);

        logoff.addActionListener(this);
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

        if (e.getSource() == restaurant_moleza) {
            System.out.println(client.getId());
            System.out.println(client.getName());
        }
    }

}
