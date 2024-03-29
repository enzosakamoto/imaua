package pages;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import shared.entities.Client;
import shared.infra.repository.Repository;

public class Funds extends JFrame implements ActionListener {
    private JLabel funds;
    private JTextField credits;
    private JButton confirm;
    private JButton cancel;

    private static ResourceBundle bn = Home.bn;
    private Repository repository = new Repository(bn);
    private String IdClient = Home.client.getId();

    public Funds(ResourceBundle bnHome) {
        super(bn.getString("funds.title"));
        setSize(340, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bn = bnHome;

        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        Font font = new Font("Arial", Font.PLAIN, 22);

        funds = new JLabel(bn.getString("funds.label.funds") + " ");
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
            try {
                Client client = repository.getClientByIdClient(this.IdClient);
                if (client.getCredits() + Double.valueOf(credits.getText()) <= 999) {
                    repository.updateClientCreditsByIdClient(this.IdClient,
                            client.getCredits() + Double.valueOf(credits.getText()));
                    System.out.println(client.getCredits() + Double.valueOf(credits.getText()));
                    JOptionPane.showMessageDialog(null, bn.getString("funds.message.success"),
                            bn.getString("funds.title.success"),
                            JOptionPane.INFORMATION_MESSAGE);
                    client = repository.getClientByIdClient(this.IdClient);
                    Home.client = client;
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, bn.getString("funds.message.error"),
                            bn.getString("funds.title.error"),
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, bn.getString("login.message.error"),
                        bn.getString("funds.title.error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

}
