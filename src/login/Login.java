package login;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
        password = new JTextField(20);
        password.setFont(font);
        username_label = new JLabel("Usuário: ");
        username_label.setFont(font);
        password_label = new JLabel("Senha: ");
        password_label.setFont(font);
        login = new JButton("Login");
        login.setFont(font);
        register = new JButton("Registrar");
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
            if (username.equals("") || password.equals("") || username.length() < 3) {
                JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos!", "Erro!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    repository.createClient(client);
                    JOptionPane.showMessageDialog(null, "Usuário criado com sucesso!", "Sucesso!",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, error.getMessage(),
                            "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == login) {
            String username = this.username.getText();
            String password = this.password.getText();

            String id = "";

            try {
                id = repository.getIdClientByLogin(username, password);
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Sucesso!",
                        JOptionPane.INFORMATION_MESSAGE);
                Client client = repository.getClientByIdClient(id);
                Home home_interface = new Home(client);
                home_interface.setVisible(true);
                this.setVisible(false);
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
