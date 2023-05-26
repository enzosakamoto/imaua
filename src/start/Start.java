package start;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import login.Login;

public class Start extends JFrame implements ActionListener {
    JLabel start_title;
    JLabel desc;
    JButton login;
    Login login_interface = new Login();

    public Start() {
        super("Início");

        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.PLAIN, 22);

        Container container = getContentPane();
        container.setLayout(new GridLayout(3, 1));

        start_title = new JLabel("Bem-vindo ao iMauá!");
        start_title.setFont(font);
        desc = new JLabel("Para ter acesso completo ao sistema, faça login.");
        desc.setFont(font);
        login = new JButton("Login");
        login.setFont(font);

        container.add(start_title);
        container.add(desc);
        container.add(login);

        login.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            this.setVisible(false);
            login_interface.setVisible(true);
        }
    }

}
