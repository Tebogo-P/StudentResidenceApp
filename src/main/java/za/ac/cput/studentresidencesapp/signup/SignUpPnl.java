/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.studentresidencesapp.signup;

import javax.swing.*;
import java.awt.*;
import za.ac.cput.studentresidencesapp.Main;
import za.ac.cput.studentresidencesapp.dao.DatabaseManager;

public class SignUpPnl extends JPanel {
    public SignUpPnl() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerBtn = new JButton("Register");

        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = DatabaseManager.registerUser(name, email, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                
                Main.refreshUserProfile();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Email might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel());
        add(registerBtn);
    }
}

