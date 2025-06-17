/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentmanagementsystem;

/**
 *
 * @author Vihanga_Rodrigo
 */
import javax.swing.*;
import java.awt.*;

public class StudentManagementSystem extends JFrame {
    

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        setVisible(true);
    }

    // Load data from the Student table
   

    

    public static void main(String[] args) {
        Login login = new Login();  // replace StudentForm with your actual class name for the student form
            login.setVisible(true);
    }
}
