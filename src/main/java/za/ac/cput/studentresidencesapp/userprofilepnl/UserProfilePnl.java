package za.ac.cput.studentresidencesapp.userprofilepnl;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import za.ac.cput.studentresidencesapp.dao.DatabaseManager;

public class UserProfilePnl extends JPanel {
    private JLabel avatarLabel, emailLabel;
    private JTextField fullNameField, IDField, emailField;
    private JButton editBtn, deleteBtn, changePasswordBtn, uploadAvatarBtn;
    private String originalEmail;

    public UserProfilePnl(String fullName, String email) {
        this.originalEmail = email;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(240, 245, 255));
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel profileInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        profileInfo.setOpaque(false);

        avatarLabel = new JLabel(new ImageIcon(
                new ImageIcon("user.png").getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));
        JLabel nameLabel = new JLabel(fullName);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        emailLabel = new JLabel(email);
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailLabel.setForeground(Color.DARK_GRAY);

        JPanel textInfo = new JPanel(new GridLayout(2, 1));
        textInfo.setOpaque(false);
        textInfo.add(nameLabel);
        textInfo.add(emailLabel);

        profileInfo.add(avatarLabel);
        profileInfo.add(textInfo);

        editBtn = new JButton("Edit");
        editBtn.setBackground(new Color(66, 135, 245));
        editBtn.setForeground(Color.WHITE);
        editBtn.addActionListener(e -> toggleEditMode());

        deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(e -> deleteUser());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        header.add(profileInfo, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);

        JPanel form = new JPanel(new GridLayout(3, 2, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        fullNameField = new JTextField(fullName);
        fullNameField.setEditable(false);

        IDField = new JTextField("Not set");
        IDField.setEditable(false);

        emailField = new JTextField(email);
        emailField.setEditable(false);

        form.add(new JLabel("Full Name:"));
        form.add(fullNameField);
        form.add(new JLabel("ID Number:"));
        form.add(IDField);
        form.add(new JLabel("Email:"));
        form.add(emailField);

        JPanel extras = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setBackground(new Color(102, 187, 106));
        changePasswordBtn.setForeground(Color.WHITE);
        changePasswordBtn.addActionListener(e -> openPasswordDialog());

        uploadAvatarBtn = new JButton("Upload Avatar");
        uploadAvatarBtn.setBackground(new Color(255, 193, 7));
        uploadAvatarBtn.setForeground(Color.BLACK);
        uploadAvatarBtn.addActionListener(e -> uploadAvatar());

        extras.add(changePasswordBtn);
        extras.add(uploadAvatarBtn);

        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(extras, BorderLayout.SOUTH);
    }

    public UserProfilePnl() {
        this("Default User", "default@email.com");
    }

    private void toggleEditMode() {
        boolean editing = editBtn.getText().equals("Edit");
        fullNameField.setEditable(editing);
        emailField.setEditable(editing);
        IDField.setEditable(editing);
        deleteBtn.setEnabled(!editing);
        editBtn.setText(editing ? "Save" : "Edit");

        if (!editing) {
            String newName = fullNameField.getText().trim();
            String newEmail = emailField.getText().trim();

            if (newName.isEmpty() || newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean updated = DatabaseManager.updateUser(originalEmail, newName, newEmail);

            if (updated) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
                emailLabel.setText(newEmail);
                originalEmail = newEmail;
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteUser() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this account?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = DatabaseManager.deleteUser(originalEmail);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                fullNameField.setText("");
                emailField.setText("");
                emailLabel.setText("Deleted");
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openPasswordDialog() {
        JPasswordField oldPass = new JPasswordField();
        JPasswordField newPass = new JPasswordField();
        Object[] message = {
            "Old Password:", oldPass,
            "New Password:", newPass
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPass.getPassword());
            String newPassword = new String(newPass.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = DatabaseManager.changePassword(originalEmail, oldPassword, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(this, "Password changed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Password change failed. Incorrect old password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void uploadAvatar() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon newAvatar = new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath())
                    .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
            avatarLabel.setIcon(newAvatar);
            JOptionPane.showMessageDialog(this, "Profile picture updated!");
        }
    }
}

