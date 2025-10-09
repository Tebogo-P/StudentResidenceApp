package za.ac.cput.studentresidencesapp.sidebar;

import javax.swing.*;
import java.awt.*;
import za.ac.cput.studentresidencesapp.homepnl.HomePnl;
import za.ac.cput.studentresidencesapp.loginpnl.LoginPnl;
import za.ac.cput.studentresidencesapp.signup.SignUpPnl;
import za.ac.cput.studentresidencesapp.userprofilepnl.UserProfilePnl;

public class Sidebar extends JPanel {
    private JFrame frame;
    
    public Sidebar(JFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(5, 1, 0, 10));
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton homeBtn = new JButton("Home");
        JButton signupBtn = new JButton("Sign Up");
        JButton loginBtn = new JButton("Login");
        JButton profileBtn = new JButton("Profile");

        homeBtn.addActionListener(e -> switchPanel(new HomePnl()));
        signupBtn.addActionListener(e -> switchPanel(new SignUpPnl()));
        loginBtn.addActionListener(e -> switchPanel(new LoginPnl()));
        profileBtn.addActionListener(e -> {
   
            UserProfilePnl userProfilePanel = new UserProfilePnl();
            switchPanel(userProfilePanel);
        });

        add(homeBtn);
        add(signupBtn);
        add(loginBtn);
        add(profileBtn);
    }

    private void switchPanel(JPanel panel) {
        if (frame.getContentPane().getComponentCount() > 1) {
            frame.getContentPane().remove(1);
        }
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
}
