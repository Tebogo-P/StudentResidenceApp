/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.studentresidencesapp.homepnl;

/**
 *
 * @author User
 */

import javax.swing.*;
import java.awt.*;

public class HomePnl extends JPanel {
    public HomePnl() {
        setLayout(new GridLayout(1, 1));
        JLabel welcome = new JLabel("Welcome to Student Residence Finder!", SwingConstants.CENTER);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(welcome);
    }
}
