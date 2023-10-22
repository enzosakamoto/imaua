package pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shared.entities.Client;
import shared.infra.repository.Repository;

public class Login extends JFrame implements ActionListener {
    private JTextField username;
    private JPasswordField password;
    private JLabel username_label;
    private JLabel password_label;
    private JButton login;
    private JButton register;
    private ResourceBundle bn = Home.bn;
    Repository repository = new Repository(bn);

    public Login() {
        super("Login");
        setSize(550, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Font font = new Font("Arial", Font.PLAIN, 22);

        JPanel form = new JPanel(new GridLayout(2, 2));
        JPanel buttons = new JPanel(new GridLayout(1, 2));

        username = new JTextField(20);
        username.setFont(font);
        password = new JPasswordField(20);
        password.setFont(font);
        username_label = new JLabel(bn.getString("login.label.username") + " ");
        username_label.setFont(font);
        password_label = new JLabel(bn.getString("login.label.password") + " ");
        password_label.setFont(font);
        login = new JButton("Login");
        login.setFont(font);
        register = new JButton(bn.getString("login.button.register"));
        register.setFont(font);

        form.add(username_label);
        form.add(username);
        form.add(password_label);
        form.add(password);

        form.setBorder(BorderFactory.createEmptyBorder(10, 25, 0, 25));

        buttons.add(login);
        buttons.add(register);

        buttons.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 25));

        container.add(form, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);

        login.addActionListener(this);
        register.addActionListener(this);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == register) {
            String username = this.username.getText();
            String password = repository.arrayCharToString(this.password.getPassword());

            Client client = new Client(username, password);

            if (username.equals("") || password.equals("") || username.length() < 3 || password.length() < 3) {
                JOptionPane.showMessageDialog(null, bn.getString("login.message.error"),
                        bn.getString("login.title.error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    repository.createClient(client);
                    JOptionPane.showMessageDialog(null, bn.getString("login.message.success"),
                            bn.getString("login.title.success"),
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, error.getMessage(),
                            "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == login) {
            String username = this.username.getText();
            String password = repository.arrayCharToString(this.password.getPassword());

            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, bn.getString("login.message.error"),
                        bn.getString("login.title.error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = "";

            try {
                id = repository.getIdClientByLogin(username, password);
                JOptionPane.showMessageDialog(null, bn.getString("login.message.login.success"),
                        bn.getString("login.title.success"),
                        JOptionPane.INFORMATION_MESSAGE);
                Client client = repository.getClientByIdClient(id);
                Home.client = client;
                Home home = new Home();
                home.setVisible(true);
                this.setVisible(false);
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
