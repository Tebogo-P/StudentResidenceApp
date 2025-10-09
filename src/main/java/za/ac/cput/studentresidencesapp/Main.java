package za.ac.cput.studentresidencesapp;

import javax.swing.*;
import java.awt.*;
import za.ac.cput.studentresidencesapp.dao.DatabaseManager;
import za.ac.cput.studentresidencesapp.homepnl.HomePnl;
import za.ac.cput.studentresidencesapp.sidebar.Sidebar;
import za.ac.cput.studentresidencesapp.userprofilepnl.UserProfilePnl;

public class Main {
    private static JFrame frame;
    private static UserProfilePnl userProfilePnl;
    
    public static JFrame getFrame() {
        return frame;
    }
    
    public static UserProfilePnl getUserProfilePnl() {
        return userProfilePnl;
    }
    
    public static void setUserProfilePnl(UserProfilePnl profilePnl) {
        userProfilePnl = profilePnl;
    }

    public static void refreshUserProfile() {
        if (userProfilePnl != null) {
            System.out.println("User profile refreshed.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseManager.initDatabase();

            frame = new JFrame("Student Residence App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLayout(new BorderLayout());

            Sidebar sidebar = new Sidebar(frame);
            frame.add(sidebar, BorderLayout.WEST);
            frame.add(new HomePnl(), BorderLayout.CENTER);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
