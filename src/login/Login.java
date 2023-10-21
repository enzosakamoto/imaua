package login;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.Repository;
import home.Home;
import shared.domain.entities.Client;

public class Login extends JFrame implements ActionListener {
    private JTextField username;
    private JTextField password;
    private JLabel username_label;
    private JLabel password_label;
    private JButton login;
    private JButton register;
    Repository repository = new Repository();
    private ResourceBundle bn = null;

    public Login() {
        super("Login");

        bn = ResourceBundle.getBundle("Bundle", new Locale(Home.language.split(",")[0], Home.language.split(",")[1]));

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
        password = new JTextField(20);
        password.setFont(font);
        username_label = new JLabel(bn.getString("login.label.username"));
        username_label.setFont(font);
        password_label = new JLabel(bn.getString("login.label.password"));
        password_label.setFont(font);
        login = new JButton("Login");
        login.setFont(font);
        register = new JButton(bn.getString("login.button.register"));
        register.setFont(font);

        form.add(username_label);
        form.add(username);
        form.add(password_label);
        form.add(password);

        buttons.add(login);
        buttons.add(register);

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
            String password = this.password.getText();

            Client client = new Client(username, password);
            if (username.equals("") || password.equals("") || username.length() < 3 || password.length() < 3) {
                JOptionPane.showMessageDialog(null, bn.getString("login.message.error"), bn.getString("login.title.error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    repository.createClient(client);
                    JOptionPane.showMessageDialog(null, bn.getString("login.message.success"), bn.getString("login.title.success"),
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, error.getMessage(),
                            bn.getString("login.title.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == login) {
            String username = this.username.getText();
            String password = this.password.getText();

            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, bn.getString("login.message.error"), bn.getString("login.title.error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = "";

            try {
                id = repository.getIdClientByLogin(username, password);
                JOptionPane.showMessageDialog(null, bn.getString("login.message.success"), bn.getString("login.title.success"),
                        JOptionPane.INFORMATION_MESSAGE);
                Client client = repository.getClientByIdClient(id);
                Home home_interface = new Home(client);
                home_interface.setVisible(true);
                this.setVisible(false);
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), bn.getString("login.title.error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
