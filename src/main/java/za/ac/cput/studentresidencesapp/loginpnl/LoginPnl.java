/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.studentresidencesapp.loginpnl;

import javax.swing.*;
import java.awt.*;
import za.ac.cput.studentresidencesapp.Main;
import za.ac.cput.studentresidencesapp.dao.DatabaseManager;
import za.ac.cput.studentresidencesapp.user.User;
import za.ac.cput.studentresidencesapp.userprofilepnl.UserProfilePnl;

public class LoginPnl extends JPanel {
    public LoginPnl() {
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter email and password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = DatabaseManager.loginUser(email, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Welcome " + user.getName() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

                UserProfilePnl profilePanel = new UserProfilePnl(user.getName(), user.getEmail());
                Main.setUserProfilePnl(profilePanel);

                JFrame frame = Main.getFrame();
                frame.getContentPane().remove(1);
                frame.getContentPane().add(profilePanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel());
        add(loginBtn);
    }
}
