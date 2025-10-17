/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.studentresidencesapp;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

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
            
            // Basic validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all fields.", 
                    "Missing Information", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Name validation
            if (!isValidName(name)) {
                JOptionPane.showMessageDialog(this,
                    "Invalid name. Use only letters, spaces, and hyphens (2-50 characters).",
                    "Name Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // (regex)
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this,
                    "Invalid email format. Example: user@example.com",
                    "Email Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Password strength check (length requirement)
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(this,
                    "Password must be at least 8 characters long.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (password.length() > 128) {
                JOptionPane.showMessageDialog(this,
                    "Password must not exceed 128 characters.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.matches(".*[A-Z].*")) {
                JOptionPane.showMessageDialog(this,
                    "Password must contain at least one uppercase letter.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.matches(".*[a-z].*")) {
                JOptionPane.showMessageDialog(this,
                    "Password must contain at least one lowercase letter.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(this,
                    "Password must contain at least one digit.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
                JOptionPane.showMessageDialog(this,
                    "Password must contain at least one special character (!@#$%^&*).",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Attempt registration
            boolean success = DatabaseManager.registerUser(name, email, password);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Registration successful!", 
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                    "Registration failed. Email might already exist.", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add components to panel
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel());
        add(registerBtn);
    }
    
    private boolean isValidName(String name) {
        if (name.length() < 2 || name.length() > 50) {
            return false;
        }
        String nameRegex = "^[A-Za-zÀ-ÿ]+([ '-][A-Za-zÀ-ÿ]+)*$";
        return Pattern.matches(nameRegex, name);
    }
    
    // Utility: Basic email format validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // improved for stronger email validation and requires (.com or .edu)
        return Pattern.matches(emailRegex, email);
    }
}
