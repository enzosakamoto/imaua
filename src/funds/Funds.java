package funds;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.Repository;
import home.Home;
import shared.domain.entities.Client;

public class Funds extends JFrame implements ActionListener {
    JLabel funds;
    JTextField credits;
    JButton confirm;
    JButton cancel;
    Repository repository = new Repository();
    String IdClient;
    private static ResourceBundle bn = null;

    public Funds(String IdClient) {
        super(bn.getString("funds.title"));

        bn = ResourceBundle.getBundle("Bundle", new Locale(Home.language.split(",")[0], Home.language.split(",")[1]));

        this.IdClient = IdClient;
        setSize(340, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        Font font = new Font("Arial", Font.PLAIN, 22);

        funds = new JLabel(bn.getString("funds.label.funds "));
        funds.setFont(font);
        credits = new JTextField(5);
        credits.setFont(font);
        confirm = new JButton(bn.getString("funds.button.confirm"));
        confirm.setFont(font);
        cancel = new JButton(bn.getString("funds.button.cancel"));
        cancel.setFont(font);

        container.add(funds);
        container.add(credits);
        container.add(confirm);
        container.add(cancel);

        confirm.addActionListener(this);
        cancel.addActionListener(this);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            Client client = repository.getClientByIdClient(this.IdClient);
            if (client.getCredits() + Double.valueOf(credits.getText()) <= 999) {
                repository.updateClientCreditsByIdClient(this.IdClient,
                        client.getCredits() + Double.valueOf(credits.getText()));
                System.out.println(client.getCredits() + Double.valueOf(credits.getText()));
                JOptionPane.showMessageDialog(null, bn.getString("funds.message.success"), bn.getString("funds.title.success"),
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, bn.getString("funds.message.error"), bn.getString("funds.title.error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

}
