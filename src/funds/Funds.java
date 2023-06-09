package funds;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.Repository;
import shared.domain.entities.Client;

public class Funds extends JFrame implements ActionListener {
    JLabel funds;
    JTextField credits;
    JButton confirm;
    JButton cancel;
    Repository repository = new Repository();
    String IdClient;

    public Funds(String IdClient) {
        super("Adicionar créditos");
        this.IdClient = IdClient;
        setSize(340, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        Font font = new Font("Arial", Font.PLAIN, 22);

        funds = new JLabel("Adicionar créditos: ");
        funds.setFont(font);
        credits = new JTextField(5);
        credits.setFont(font);
        confirm = new JButton("Adicionar");
        confirm.setFont(font);
        cancel = new JButton("Cancelar");
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
                JOptionPane.showMessageDialog(null, "Créditos adicionados com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "O limite de créditos é de R$ 999,00", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

}
