import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class LoginForm implements ActionListener{
    JFrame frame = new JFrame("Attendance Proof");
    JLabel lblUser = new JLabel("User: ");
    JLabel lblPass = new JLabel("Pass: ");
    JTextField txtboxUser = new JTextField();
    JPasswordField txtboxPass = new JPasswordField();
    JButton btnLogin = new JButton("Login");
    JButton btnReset = new JButton("Reset");
    JLabel lblVer = new JLabel("Ver: 0.0.0.6 - 07/06/2022");
    JLabel lblStatus = new JLabel();
    JLabel lblJudul = new JLabel("Attendance Proof");
    JLabel lblKet = new JLabel("Please insert your Username and Password!");

    public LoginForm() {
        lblJudul.setBounds(235, 20, 300, 100);
        lblJudul.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
        frame.add(lblJudul);

        lblKet.setBounds(200, 80, 400, 100);
        lblKet.setFont(new Font("Tw Cen MT", 0, 14));
        frame.add(lblKet);

        lblUser.setBounds(200, 170, 100, 30);
        lblUser.setFont(new Font("Tw Cen MT", 0, 18));
        frame.add(lblUser);

        lblPass.setBounds(200, 210, 100, 30);
        lblPass.setFont(new Font("Tw Cen MT", 0, 18));
        frame.add(lblPass);

        txtboxUser.setBounds(250, 170, 240, 30);
        frame.add(txtboxUser);

        txtboxPass.setBounds(250, 210, 240, 30);
        frame.add(txtboxPass);

        btnLogin.setBounds(575, 400, 100, 30);
        btnLogin.addActionListener(this);
        btnLogin.setFocusable(false);
        btnLogin.setFont(new Font("Tw Cen MT", 0, 18));
        btnLogin.setBackground(new Color(204, 153, 0));
        frame.add(btnLogin);

        btnReset.setBounds(450, 400, 100, 30);
        btnReset.addActionListener(this);
        btnReset.setFocusable(false);
        btnReset.setFont(new Font("Tw Cen MT", 0, 18));
        btnReset.setBackground(new Color(204, 153, 0));
        frame.add(btnReset);

        lblVer.setBounds(25, 400, 200, 30);
        frame.add(lblVer);

        lblStatus.setBounds(390, 240, 200, 30);
        frame.add(lblStatus);

        frame.getContentPane().setBackground(new Color(0, 153, 153));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(720, 480);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == btnReset) {
            txtboxPass.setText("");
            txtboxUser.setText("");
        }

        if(ae.getSource() == btnLogin) { 
            String userid = txtboxUser.getText();
            String password = String.valueOf(txtboxPass.getPassword());

            DatabaseUser dbUser = new DatabaseUser(userid, password);
            if(dbUser.getLoginDB() == true) {
                lblStatus.setForeground(Color.green);
                lblStatus.setText("Login Successful");

                MenuForm menu = new MenuForm(userid, password);

                frame.dispose();
            }
            else if(dbUser.getConnectionDB() == true) {
                lblStatus.setForeground(Color.red);
                lblStatus.setText("Database Disconnected");
            }
            else {
                lblStatus.setForeground(Color.red);
                lblStatus.setText("Incorrect Information");
            }
        }
    }
}
